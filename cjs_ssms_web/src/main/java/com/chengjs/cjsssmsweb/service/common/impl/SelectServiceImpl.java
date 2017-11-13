package com.chengjs.cjsssmsweb.service.common.impl;

import com.chengjs.cjsssmsweb.common.util.StringUtil;
import com.chengjs.cjsssmsweb.mybatis.MybatisHelper;
import com.chengjs.cjsssmsweb.mybatis.mapper.common.ISelectDao;
import com.chengjs.cjsssmsweb.service.common.ISelectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageRowBounds;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SelectServiceImpl:
 * 1.通用 select 结果构造 (text val)
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/13
 */
@Service
public class SelectServiceImpl implements ISelectService {

  private static final Logger log = LoggerFactory.getLogger(SelectServiceImpl.class);
  private static final String MAPPER = "com.chengjs.cjsssmsweb.mybatis.mapper.master.";
  private static final String POJO = "com.chengjs.cjsssmsweb.mybatis.pojo.master.";

  @Resource
  private ISelectDao selectDao;

  @Resource(name = "sqlSessionFactory")
  private SqlSessionFactory factory;

  /**
   * 通用selected方法查询
   * 反射方式执行mybatis Mapper接口的代理实现对象的方法,
   *
   * @param method ISelectDao中定义的方法名
   * @param params ISelectDao方法中的参数
   * @return
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   */
  @Override
  public List<Map<String, String>> commonSelect(String method, HashMap<String, String> params)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

    SqlSession sqlSession = factory.openSession();
    ISelectDao mapper = sqlSession.getMapper(ISelectDao.class);

    Method m = mapper.getClass().getMethod(method, new Class[]{Map.class});
    Object result = m.invoke(mapper, params);

    /*此处强转获取类型
      mybatis的interface中的的select查询返回值都必须是List
      mybatis的mapper.xml中的的select查询返回值都必须是Map
      Mybatis会自动根据返回值是多条纪录还是单条纪录,来返回值*/
    return (List<Map<String, String>>) result;
  }

  @Override
  public Map<String, Object> queryGridKey(int pageNum, int pageSize,
                                          String field, String sort,
                                          HashMap<String, String> params)
      throws ClassNotFoundException, IllegalAccessException,
      InstantiationException, NoSuchMethodException, InvocationTargetException {

    String[] strs = params.get("key").split("_"); /*1.strs[0]:接口, strs[1]:方法*/
    SqlSession sqlSession = MybatisHelper.getSqlSession(); /*2.sqlSession*/
    PageRowBounds rowBounds = new PageRowBounds(pageNum, pageSize); /*3.rowBounds*/

    /*
    0 = {HashMap$Node@7360} "rolename" -> "0001"
    1 = {HashMap$Node@7361} "key" -> "UUserMapper_gridUsers"
    2 = {HashMap$Node@7362} "username" -> "0001"
    * */

    /*1.Mapper*/
    Class mapper_clz = Class.forName(MAPPER + strs[0]);
    Mapper mapper = (Mapper) sqlSession.getMapper(mapper_clz);

    /*2.Page分页操作*/
    // List<Object> list = pagePojo(params, strs, mapper, pageNum, pageSize); // pojo
    List<Object> list = pageMap(params, strs, mapper, pageNum, pageSize); // map
    PageInfo page = new PageInfo(list);

    /*4.构造适合miniui_grid展示的map*/
    Map<String, Object> map_grid = new HashedMap();
    map_grid.put("total", page.getTotal());
    map_grid.put("data", list);

    return map_grid;
  }

  /**
   * 2. 2)params==>pojo 属性归档, Map参数更加通用
   *
   * @param params   分页查询参数
   * @param strs     分页查询接口,方法
   * @param mapper   mybatis反射dao/mapper对象
   * @param pageNum
   * @param pageSize
   * @return
   */
  private List<Object> pageMap(HashMap<String, String> params, String[] strs,
                               Mapper mapper, int pageNum, int pageSize)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method mapper_method = mapper.getClass().getMethod(strs[1], new Class[]{Map.class});
    /*miniui page从0开始 pagehelper从1开始*/
//    PageHelper.startPage(pageNum + 1, pageSize);

//    mapper.selectByRowBounds()
    return (List<Object>) mapper_method.invoke(mapper, params);
  }

  /**
   * 2. 1)params==>pojo 属性归档, POJO不合适多表关联查询, 条件录入--pass
   *
   * @param params   分页查询参数
   * @param strs     分页查询接口,方法
   * @param mapper   mybatis反射dao/mapper对象
   * @param pageNum
   * @param pageSize
   * @return
   */
  private List<Object> pagePojo(HashMap<String, String> params,
                                String[] strs, Mapper mapper, int pageNum, int pageSize)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException,
      NoSuchMethodException, InvocationTargetException {

    Class pojo_clz = Class.forName(POJO + strs[0].replace("Mapper", ""));
    Object pojo = pojo_clz.newInstance();
    for (String s : params.keySet()) {
      Method pojo_method = null;
      try {
        pojo_method = pojo_clz.getMethod("set" + StringUtil.reSqlLikeStr(StringUtil.captureName(s)), String.class);
        pojo_method.invoke(pojo, params.get(s));
      } catch (NoSuchMethodException e) {
        log.warn("POJO不存在属性" + s + ".跳过.");
      } catch (SecurityException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        log.warn("POJO方法" + pojo_method + "不存在.");
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }

    /*3. 1) 反射调用mapper方法 POJO 参数*/
    Method mapper_method = mapper.getClass().getMethod(strs[1], new Class[]{pojo_clz});

    /*miniui page从0开始 pagehelper从1开始*/
    /*PageHelper方式似乎对反射调用方法没有起到效果*/
    PageHelper.startPage(pageNum + 1, pageSize);
    return (List<Object>) mapper_method.invoke(mapper, pojo);


  }



}

/* miniui_grid 查询出来的数据结构
{
    "total": 55931,
    "data": [{
        "city": "",
        "age": 25,
        "gender": 1,
        "dept_id": "js",
    },
    {
        "city": "",
        "age": 25,
        "gender": 1,
        "dept_id": "rs",
    }]
}
*/
