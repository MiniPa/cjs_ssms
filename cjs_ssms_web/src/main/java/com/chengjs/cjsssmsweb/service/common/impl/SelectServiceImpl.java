package com.chengjs.cjsssmsweb.service.common.impl;

import com.chengjs.cjsssmsweb.mybatis.mapper.common.ISelectDao;
import com.chengjs.cjsssmsweb.service.common.ISelectService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * SelectServiceImpl:
 * 1.通用 select 结果构造 (text val)
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/13
 */
@Service
public class SelectServiceImpl implements ISelectService {

  @Resource
  private ISelectDao selectDao;

  @Resource(name = "sqlSessionFactory")
  private SqlSessionFactory factory;

  /**
   * 反射方式执行mybatis Mapper接口的代理实现对象的方法,
   *
   * @param method
   * @param params
   * @return
   * @throws InvocationTargetException
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   */
  @Override
  public Map<String, String> commonSelect(String method, HashMap<String, String> params)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

    SqlSession sqlSession = factory.openSession();
    ISelectDao mapper = sqlSession.getMapper(ISelectDao.class);

    Method m = mapper.getClass().getMethod(method, new Class[] {Map.class});
    Object result = m.invoke(mapper, params);

    /*此处强转要求mybatis配置的select查询返回值都必须是Map*/
    return (Map<String, String>) result;

  }

}

/* grid 查询出来的数据结构
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
