package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.mybatis.MybatisHelper;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.UUserMapper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import com.chengjs.cjsssmsweb.service.common.ISelectService;
import com.chengjs.cjsssmsweb.util.page.HttpReqsUtil;
import com.github.pagehelper.PageRowBounds;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * GridController: miniui类表单 后端请求类
 * <p>
 * 需实现的功能：
 * 1.通用grid表格查询,导出: IN(taskName,params), 匹配参数信息,页面信息,排序信息, 映射mybatis_sqlxml, OUT(grid json)
 * 2.通用select下拉查询: IN(taskName),匹配组织sql,查询结果
 * <p>
 * 功能现状：
 * 一、原公司框架逻辑：配置logic + task,在所有logic和task中去匹配然后组织参数,执行sql获取返回值.
 * 优势: 简洁好扩展,参数为空控制在logic中,logic,task分离可重用,直接关联数据源,清楚明了
 * 借助2者分离,实现一些通用查询(比如说此处grid,select的通用查询).
 * 缺憾: logic task实际业务中重用率非常低,要分开写多一套手续
 * 二、现mybatis设计：接口+xml,
 * 1)目标页面传入taskName("WebUserRolePermissionDao_WebUserGrid"),解析到interface("WebUserRolePermissionDao")--"WebUserGrid方法"
 * 根据spring来获取接口实现对象,调用对应方法来获取结果--反射
 * 2)
 * 三、开源通用查询:
 * 1.单表 多表
 * 2.grid select
 * <p>
 * 四、思路
 * 1.Mapper框架的
 * <p>
 * <p>
 * <p>
 * 四、参考文章
 * http://www.cnblogs.com/svili/p/6057452.html
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/3
 */
@Controller
@RequestMapping("/select")
public class SelectController {
  private static final Logger log = LoggerFactory.getLogger(SelectController.class);

  @Autowired
  private ISelectService selectService;

  /**
   * common select 查询
   *
   * @param method  查询方法名和ISelectDao中配置的方法名一致
   * @param request
   * @return
   * @throws UnsupportedEncodingException
   * @throws IllegalAccessException
   * @throws NoSuchMethodException
   * @throws InvocationTargetException
   */
  @RequestMapping("/comSelect")
  public @ResponseBody
  List<Map<String, String>> commonSelect(@RequestParam(value = "method") String method, HttpServletRequest request)
      throws UnsupportedEncodingException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
    /*TODO_不同模块select集成于同一个mapper.xml中,耦合太大理应进行分解*/

    HashMap<String, String> params = HttpReqsUtil.getRequestParams(request);
    List<Map<String, String>> list = selectService.select(method, params);
    return list;
  }

  /**
   * common grid 查询
   *
   * USE grid通用查询使用方式: 1.Dao编写或生成 2.grid.url="../select/grid" 3.key="Dao接口名_方法名"
   *
   * key：查询条件书写规范: "UUserMapper_gridUsers"(Dao接口名_方法名)
   *
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/grid", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, Object> grid(HttpServletRequest request, HttpServletResponse response)
      throws Exception {
    //1.查询条件
    /*data*/
    String data = request.getParameter("data");
    JSONObject obj = JSONObject.fromObject(data);
    HashMap<String, String> paramsMap = (HashMap<String, String>) JSONObject.toBean(JSONObject.fromObject(obj), HashMap.class);
    /*分页*/
    int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
    int pageSize = Integer.parseInt(request.getParameter("pageSize"));
    /*字段排序*/
    String sortField = request.getParameter("sortField");
    String sortOrder = request.getParameter("sortOrder");
    if (log.isInfoEnabled()) {
      log.debug("/grid paramsMap：==>" + paramsMap);
    }

    //2.返回结果
    Map<String, Object> resultMap = selectService.grid(pageIndex, pageSize, sortField, sortOrder, paramsMap);
    if (log.isInfoEnabled()) {
      log.debug("/grid resultMap：==>" + resultMap);
    }

    return resultMap;
  }

  /**
   * users gird表查询
   * 不足：gridc查询与Controller.Method(), IService.Method()耦合,导致新增代码复杂
   *
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/userGridQuery", method = RequestMethod.POST)
  public @ResponseBody
  Map<String, Object> usersGridQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {

    HashMap<String, String> params = HttpReqsUtil.getRequestParams(request);
    log.debug("grid通用查询参数：==>" + String.valueOf(params));

    String data = params.get("data");
    JSONObject obj = JSONObject.fromObject(data);//查询条件
    HashMap<String, String> paramsMap = (HashMap<String, String>) JSONObject.toBean(JSONObject.fromObject(obj), HashMap.class);

    Map<String, Object> resultMap = null;

    /*分页*/
    int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
    int pageSize = Integer.parseInt(request.getParameter("pageSize"));
    /*字段排序*/
    String sortField = request.getParameter("sortField");
    String sortOrder = request.getParameter("sortOrder");

    Map<String, Object> gridData = getGrids(pageIndex, pageSize, paramsMap);
    return gridData;
  }

  /**
   * 数据表grid查询 It's not good enough
   *
   * @param pageIndex
   * @param pageSize
   * @param paramsMap 给criteria添加参数使用
   * @return
   */
  private Map<String, Object> getGrids(int pageIndex, int pageSize, HashMap<String, String> paramsMap) {
    PageRowBounds rowBounds = new PageRowBounds(pageIndex + 1, pageSize);
    SqlSession sqlSession = MybatisHelper.getSqlSession();
    Mapper mapper = (Mapper) sqlSession.getMapper(UUserMapper.class);
    Example example = new Example(UUser.class);
    Example.Criteria criteria = example.createCriteria();
    /*criteria增加条件...*/
    List<UUser> users = (List<UUser>) mapper.selectByExampleAndRowBounds(example, rowBounds);

    /*4.构造适合miniui_grid展示的map*/
    Map<String, Object> map_grid = new HashedMap();
    map_grid.put("total", users.size());
    map_grid.put("data", users);

    log.debug("getGrids ==>" + map_grid);
    return map_grid;
  }

  /*========================== url ==========================*/

  @RequestMapping("/usergrid")
  public String usergrid() {
    return "user/usergrid";
  }


}