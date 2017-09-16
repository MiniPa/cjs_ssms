package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.service.common.ISelectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
   * commonSelect
   * 通用select查询, text='text' value='value'
   * 对象反射机制调用方法,不支持传入除方法名外的任何参数
   *
   * @return
   */
  @RequestMapping("/commonSelect")
  public String commonSelect(@RequestParam(value = "selectmethod") String selectmethod) {
/*

    try {
      Method method = selectDao.getClass().getMethod(selectmethod, null);
      Object result = method.invoke(selectmethod, null);

    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } finally {

    }
*/

    return null;
  }


  /**
   * commonGridQuery
   * grid数据查询
   *
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/commonGridQuery", method = RequestMethod.POST)
  @ResponseBody
  public String commonGridQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {

    /*查询条件*/
    String key = request.getParameter("key");
    /*分页*/
    int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
    int pageSize = Integer.parseInt(request.getParameter("pageSize"));
    /*字段排序*/
    String sortField = request.getParameter("sortField");
    String sortOrder = request.getParameter("sortOrder");


    System.out.printf(sortField);

/*    HashMap result = new ResolverUtil.Test.TestDB().SearchEmployees(key, pageIndex, pageSize, sortField, sortOrder);
    String json = ResolverUtil.Test.JSON.Encode(result);*/
    return "";

  }

  private Map<String, String> queryGridByTask(String task, int pageNum, int pageSize, HashMap<String, String> params) {
    HashMap<String, String> map = new HashMap<>();
    /**
     * 1.运用反射获取 task="userService.selectById()" 的 Service对象,并进入到对应的方法(------------配置1)
     *
     * 2.Service里通过example等方式,设定查询条件,如Excample，Selective等---------配置2
     *
     * 3.1.单表,直接调用通用Mapper查询
     * 3.2.多表,对应Mapper接口和XML配置sql-------------配置3
     *
     * 4.总结：和logic,task工作量类似
     *  单表轻松些,
     *  多表复杂,表嵌套,各级表条件不同,如何实现
     *
     **/

    return map;
  }

  /*========================== url ==========================*/

  @RequestMapping("/usergrid")
  public String usergrid() {
    log.debug("user/usergrid");
    return "user/usergrid";
  }


}