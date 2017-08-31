package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.enums.StatusEnum;
import com.chengjs.cjsssmsweb.lucene.LuceneIndex;
import com.chengjs.cjsssmsweb.pojo.SocketContent;
import com.chengjs.cjsssmsweb.pojo.WebUser;
import com.chengjs.cjsssmsweb.service.master.SocketContentServiceImpl;
import com.chengjs.cjsssmsweb.service.master.WebUserServiceImpl;
import com.chengjs.cjsssmsweb.util.CommonUtil;
import com.chengjs.cjsssmsweb.util.page.PageEntity;
import com.chengjs.cjsssmsweb.util.page.PageUtil;
import com.fasterxml.jackson.databind.util.JSONPObject;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IndexController:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/24
 */
@Controller
@RequestMapping("/")
public class IndexController {
  private static final Logger log = LoggerFactory.getLogger(IndexController.class);

  @Autowired
  private WebUserServiceImpl webUserService;

  @Autowired
  private SocketContentServiceImpl socketContentService;


  /**
   * model.addAttribute(attr1) ---- jsp ${attr1}
   *
   * @param page    直接取和页面上参数名相同的参数 @RequestParam
   * @param request
   * @param model
   * @return 重定向的方式 return "redirect:index"
   * @throws Exception
   */
  @RequestMapping("/index")
  public String index(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                      HttpServletRequest request, Model model) throws Exception {

    PageEntity pageEntity = new PageEntity(page, 10);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("start", pageEntity.getStart());
    map.put("size", pageEntity.getPageSize());

    List<WebUser> webUsers = webUserService.list(map);
    Long total = webUserService.getTotal(map);

    model.addAttribute("webusers", webUsers);
    StringBuffer param = new StringBuffer();

    String pageHtml = PageUtil.genPagination(request.getContextPath() + "/index", total, page, 10, param.toString());
    model.addAttribute("pageHtml", pageHtml);

    return "index";
  }

  @RequestMapping("/turnToWebSocketIndex")
  public String turnToWebSocketIndex() {
    return "websocket/websocket";
  }

  /**
   * 加载聊天记录
   *
   * @param response
   */
  @RequestMapping("/content_load")
  public void content_load(HttpServletResponse response) {
    JSONObject jsonObject = new JSONObject();
    try {
      JSONObject jo = new JSONObject();
      List<SocketContent> list = socketContentService.findSocketContentList();
      jo.put("contents", list);
      jsonObject = CommonUtil.parseJson(StatusEnum.HANDLE_SUCCESS, jo);
    } catch (Exception e) {
      log.error("操作异常", e);
      CommonUtil.parseJson(StatusEnum.HANDLE_FAIL, "");
    }
    CommonUtil.responseBuildJson(response, jsonObject);
  }

  /**
   * 查询lucene索引下的blog Html
   * @param query_key
   * @param page
   * @param model
   * @param request
   * @return
   * @throws Exception
   */
  @RequestMapping("/queryLuceneByKey")
  public String search(@RequestParam(value = "query_key", required = false, defaultValue = "") String query_key,
                       @RequestParam(value = "page", required = false, defaultValue = "1") String page,
                       Model model,
                       HttpServletRequest request) throws Exception {
    LuceneIndex luceneIndex = new LuceneIndex();
    List<WebUser> userList = luceneIndex.searchBlog(query_key);
    /**
     * 此处查询后分页,采用空间换时间,查出所有,截取分页
     */
    Integer toIndex = userList.size() >= Integer.parseInt(page) * 5 ? Integer.parseInt(page) * 5 : userList.size();
    List<WebUser> newList = userList.subList((Integer.parseInt(page) - 1) * 5, toIndex);
    model.addAttribute("userList", newList);
    String pageHtml = this.genUpAndDownPageCode(Integer.parseInt(page), userList.size(), query_key, 5, "");
    model.addAttribute("pageHtml", pageHtml);
    model.addAttribute("query_key", query_key);
    model.addAttribute("resultTotal", userList.size());
    model.addAttribute("pageTitle", "搜索关键字'" + query_key + "'结果页面");

    return "queryResult";
  }

  @RequestMapping(value = "/jsonpInfo", method = {RequestMethod.GET})
  @ResponseBody
  public Object jsonpInfo(String callback, String webUserId) throws IOException {
    WebUser webUser = webUserService.getWebUserById(webUserId);
    JSONPObject jsonpObject = new JSONPObject(callback, webUser);
    return jsonpObject;
  }

  @RequestMapping("/createAllIndex")
  public void createAllIndex(HttpServletResponse response) throws Exception {
    JSONObject jsonObject = new JSONObject();
    try {
      JSONObject jo = new JSONObject();
      Map<String, Object> map = new HashMap<String, Object>();
      List<WebUser> users = webUserService.list(map);

      /*先删除原有的索引再创建新的*/
      for (WebUser user : users) {
        LuceneIndex luceneIndex = new LuceneIndex();
        luceneIndex.deleteIndex(user.getUserid() + "");
      }
      for (WebUser user : users) {
        LuceneIndex luceneIndex = new LuceneIndex();
        luceneIndex.addIndex(user);
      }
      jsonObject = CommonUtil.parseJson(StatusEnum.HANDLE_SUCCESS, jo);
    } catch (Exception e) {
      log.error("操作异常", e);
      jsonObject = CommonUtil.parseJson(StatusEnum.HANDLE_FAIL, "");
    }
    /*返回构建的json*/
    CommonUtil.responseBuildJson(response, jsonObject);
  }

  /**
   * 查询之后的分页
   *
   * @param page
   * @param totalNum
   * @param q
   * @param pageSize
   * @param projectContext
   * @return
   */
  private String genUpAndDownPageCode(int page, Integer totalNum, String q, Integer pageSize, String projectContext) {
    long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
    StringBuffer pageCode = new StringBuffer();
    if (totalPage == 0) {
      return "";
    } else {
      pageCode.append("<nav>");
      pageCode.append("<ul class='pager' >");
      if (page > 1) {
        pageCode.append("<li><a href='" + projectContext + "/queryLuceneByKey?page=" + (page - 1) + "&q=" + q + "'>上一页</a></li>");
      } else {
        pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
      }
      if (page < totalPage) {
        pageCode.append("<li><a href='" + projectContext + "/queryLuceneByKey?page=" + (page + 1) + "&q=" + q + "'>下一页</a></li>");
      } else {
        pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
      }
      pageCode.append("</ul>");
      pageCode.append("</nav>");
    }
    return pageCode.toString();
  }

}
