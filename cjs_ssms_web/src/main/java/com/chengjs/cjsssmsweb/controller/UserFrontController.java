package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.enums.EnvEnum;
import com.chengjs.cjsssmsweb.enums.StatusEnum;
import com.chengjs.cjsssmsweb.enums.TestEnv;
import com.chengjs.cjsssmsweb.components.lucene.IndexerTest;
import com.chengjs.cjsssmsweb.components.lucene.SearcherTest;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import com.chengjs.cjsssmsweb.service.master.IUserFrontrService;
import com.chengjs.cjsssmsweb.service.master.IUserManagerService;
import com.chengjs.cjsssmsweb.util.ExceptionUtil;
import com.chengjs.cjsssmsweb.util.HttpRespUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 前台用户Controller
 */
@Controller
@RequestMapping("/user")
public class UserFrontController {

  private Logger log = LoggerFactory.getLogger(UserFrontController.class);

  @Autowired
  private IUserFrontrService userFService;
  @Autowired
  private IUserManagerService userMService;

  /**
   * @param userid
   * @param model
   * @return
   */
  @RequestMapping("/frontUserSet/{userid}")
  public String frontUserSet(@PathVariable String userid, Model model) {
    UUser user = userFService.getUserById(userid);
    model.addAttribute("currentUser", user);
    /*TODO 给用户添加头像属性*/
/*    String img_id = user.getImg_id();
    if (img_id != null) {
      String path = imgService.selectByPrimaryKey(Integer.parseInt(img_id)).getPath();
      model.addAttribute("headimg", path);
    }*/
    return "front/user/userSet";
  }

  /**
   * 当前用户信息展示
   *
   * @param request
   * @param model
   * @return
   */
  @RequestMapping("/showUser")
  public String toIndex(HttpServletRequest request, Model model) {
    String userid = request.getParameter("userid");
    UUser user = this.userFService.getUserById(userid);
    model.addAttribute("user", user);
    return "showUser";
  }

  /**
   * 查询用户信息
   *
   * @param userid
   * @param request
   * @return
   */
  @RequestMapping("/queryUser/{userid}")
  public String queryUser(@PathVariable String userid, HttpServletRequest request) {
    UUser user = userFService.getUserById(userid);
    request.setAttribute("user", user);
    return "showUser";
  }

  @RequestMapping("/turnToIndex")
  public String turnToIndex() {
    return "index";
  }

  @RequestMapping("/turnToUserList")
  public String turnToUserList() {
    return "user/userList";
  }


  @RequestMapping("/queryUser")
  public String queryUser(String querykey) throws Exception {
    querykey = new String(querykey.getBytes("ISO-8859-1"), "UTF-8");
    UUser u = new UUser();
    List<UUser> users = userFService.findAllByQuery(u);

    List<String> usernames = new ArrayList<>();
    List<String> cities = new ArrayList<String>();
    List<String> descriptions = new ArrayList<String>();
    for (UUser user : users) {
      usernames.add(user.getUsername());
      descriptions.add(user.getDescription());
    }

    IndexerTest indexer = new IndexerTest();
    String[] usernames2 = (String[]) usernames.toArray(new String[usernames.size()]);
    String[] descriptions2 = (String[]) descriptions.toArray(new String[descriptions.size()]);
    indexer.setUsernames(usernames2);
    indexer.setDescriptions(descriptions2);

    indexer.index(EnvEnum.LUCENE_INDEX_PATH.val());
    try {
      SearcherTest.search(EnvEnum.LUCENE_INDEX_PATH.val(), querykey);
    } catch (Exception e) {
      log.error("系统异常", e);
    }

    return null;
  }

  @RequestMapping("/createUser")
  public void createUser(UUser user, HttpServletResponse response) throws IOException {
    try {
      userFService.createUser(user);
      response.getWriter().print("true");
    } catch (Exception e) {
      log.error("系统异常", e);
      response.getWriter().print("false");
    }
  }

  @RequestMapping("/deleteUser")
  public void deleteUser(String userids, HttpServletResponse response) throws IOException {
    String[] str_ids = userids.split(",");
    for (String id : str_ids) {
      userFService.deleteByPrimaryKey(id);
      response.getWriter().print("true");
    }
  }

  @RequestMapping("/edit")
  public void edit(UUser user, HttpServletResponse response) {
    try {
      userFService.updateByPrimaryKeySelective(user);
      response.getWriter().print("true");
    } catch (Exception e) {
      log.error("系统异常", e);
    }
  }

  /**
   * 登录,(不跳转页面，ajax请求)
   * <p>
   * 测试环境中，这里的User登录其实也是使用User来登录的
   * <p>
   * EAO issue: 此处的login仍然使用UserRealm 如何切分,或者从设计上来说，管理平台和App应当分开为2套
   *
   * @param username
   * @param password
   * @param response
   * @return
   */
  @RequestMapping("/loginUser")
  public void login(String username, String password, HttpServletResponse response) {

    /*===== tpja ajax请求mvc响应 =====*/

    Map<String, Object> remap = new HashedMap();

    UUser user = new UUser();
    user.setUsername(username);
    user.setPassword(password);

    //用户视图相关操作尽在Subject
    Subject subject = SecurityUtils.getSubject();
    StatusEnum re_status = StatusEnum.FAIL; // 请求操作状态
    if (subject.isAuthenticated()) {
      remap.put("msg", "已经登录");
      HttpRespUtil.respJson(StatusEnum.SUCCESS, remap, response);
    } else {
      UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
      token.setRememberMe(TestEnv.onTFalse);//default rememberMe true
      try {
        subject.login(token);
        String principal_username = subject.getPrincipal().toString();
        log.info("User [" + principal_username + "] 普通用户 登录成功.");

        //1.进行session相关事项,可为webSession也可为其他session
        Session session = subject.getSession();
        session.setAttribute("userName", user.getUsername());

        re_status = StatusEnum.SUCCESS;
        HttpRespUtil.buildRespStatus("恭喜" + user.getUsername() + "登录成功", remap, re_status);
      } catch (UnknownAccountException e) {
        ExceptionUtil.controllerEHJson("用户名不存在", e, log, remap, re_status);
      } catch (IncorrectCredentialsException e) {
        ExceptionUtil.controllerEHJson("密码错误请重试", e, log, remap, re_status);
      } catch (LockedAccountException e) {
        ExceptionUtil.controllerEHJson("账号已被锁定", e, log, remap, re_status);
      } catch (AuthenticationException e) {
        ExceptionUtil.controllerEHJson("用户或密码错误", e, log, remap, re_status);
      } catch (Exception e) {
        ExceptionUtil.controllerEHJson("未知错误", e, log, remap, re_status);
      } finally {
        HttpRespUtil.respJson(re_status, remap, response);
      }
    }

  }

  /**
   * 注册
   * JavaBean: User这种的bean会自动返回给前端
   *
   * @param user
   * @param model
   * @param response
   */
  @RequestMapping("/register")
  public void register(UUser user, Model model, HttpServletResponse response) {
    Map<String, Object> remap = new HashedMap();
    try {
      boolean b = userFService.registerUser(user);
      if (b) {
        log.info("注册普通用户" + user.getUsername() + "成功。");
        remap.put("msg", "恭喜您，" + user.getUsername() + "注册成功。");
        HttpRespUtil.respJson(StatusEnum.SUCCESS, remap, response);
      } else {
        remap.put("msg", "用户名已存在。");
        HttpRespUtil.respJson(StatusEnum.SUCCESS, remap, response);
      }

    } catch (Exception e) {
      log.debug("注册普通用户" + user.getUsername() + "异常");
      remap.put("msg","注册普通用户异常");
      HttpRespUtil.respJson(StatusEnum.FAIL, remap, response);
      e.printStackTrace();
    }
  }

  @RequestMapping("/logoutUser")
  public String logout(UUser user, Model model) throws IOException {
    Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession();
    //session.removeAttribute("userName");
    session.removeAttribute("sysbUserName");
    return "redirect:/index.jsp";
  }

}
