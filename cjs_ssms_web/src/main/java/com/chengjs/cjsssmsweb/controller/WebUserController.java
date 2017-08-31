package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.enums.EnvEnum;
import com.chengjs.cjsssmsweb.lucene.IndexerTest;
import com.chengjs.cjsssmsweb.lucene.SearcherTest;
import com.chengjs.cjsssmsweb.pojo.SysUser;
import com.chengjs.cjsssmsweb.pojo.WebUser;
import com.chengjs.cjsssmsweb.service.master.IWebUserService;
import com.chengjs.cjsssmsweb.service.master.WebUserServiceImpl;
import com.chengjs.cjsssmsweb.util.ExceptionUtil;
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

@Controller
@RequestMapping("/user")
public class WebUserController {

  private Logger log = LoggerFactory.getLogger(WebUserController.class);

  @Autowired
  private IWebUserService webUserService;

  /**
   *
   * @param webUserid
   * @param model
   * @return
   */
  @RequestMapping("/frontUserSet/{webUserid}")
  public String frontUserSet(@PathVariable String webUserid, Model model) {
    WebUser user = webUserService.getWebUserById(webUserid);
    model.addAttribute("currentWebUser", user);
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
   * @param request
   * @param model
   * @return
   */
  @RequestMapping("/showUser")
  public String toIndex(HttpServletRequest request, Model model) {
    String webUserid = request.getParameter("userid");
    WebUser user = this.webUserService.getWebUserById(webUserid);
    model.addAttribute("webUser", user);
    return "showUser";
  }

  /**
   * 查询用户信息
   * @param userid
   * @param request
   * @return
   */
  @RequestMapping("/queryWebUser/{userid}")
  public String queryWebUser(@PathVariable String userid, HttpServletRequest request) {
    WebUser user = webUserService.getWebUserById(userid);
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
    WebUser u = new WebUser();
    List<WebUser> users = webUserService.findAllByQuery(u);

    List<String> userids = new ArrayList<>();
    List<String> cities = new ArrayList<String>();
    List<String> descs = new ArrayList<String>();
    for (WebUser user : users) {
      userids.add(user.getUserid());
      cities.add(user.getUsername());
      descs.add(user.getDescription());
    }

    IndexerTest indexer = new IndexerTest();
    String[] userids2 = (String[]) userids.toArray(new String[userids.size()]);
    String[] cities2 = (String[]) cities.toArray(new String[cities.size()]);
    String[] descs2 = (String[]) descs.toArray(new String[descs.size()]);
    indexer.setUserids(userids2);
    indexer.setCities(cities2);
    indexer.setDescs(descs2);

    indexer.index(EnvEnum.LUCENE_INDEX_PATH.val());
    try {
      SearcherTest.search(EnvEnum.LUCENE_INDEX_PATH.val(), querykey);
    } catch (Exception e) {
      log.error("系统异常", e);
    }

    return null;
  }

  @RequestMapping("/createWebUser")
  public void createWebUser(WebUser user, HttpServletResponse response) throws IOException {
    try {
      webUserService.createWebUser(user);
      response.getWriter().print("true");
    } catch (Exception e) {
      log.error("系统异常", e);
      response.getWriter().print("false");
    }
  }

  @RequestMapping("/deleteWebUser")
  public void deleteWebUser(String userids, HttpServletResponse response) throws IOException {
    String[] str_ids = userids.split(",");
    for (String id : str_ids) {
      webUserService.deleteByPrimaryKey(id);
      response.getWriter().print("true");
    }
  }

  @RequestMapping("/edit")
  public void edit(WebUser webUser, HttpServletResponse response) {
    try {
      webUserService.updateByPrimaryKeySelective(webUser);
      response.getWriter().print("true");
    } catch (Exception e) {
      log.error("系统异常", e);
    }
  }


  /**
   * EAO issue: 此处的login仍然使用SysUserRealm 如何切分,或者从设计上来说，管理平台和App应当分开为2套
   *
   * @param sysUser
   * @param model
   * @return
   */
  @RequestMapping("/loginWebUser")
  public String login(SysUser sysUser, Model model) {
    //用户视图相关操作尽在Subject
    Subject subject = SecurityUtils.getSubject();
    if (!subject.isAuthenticated()) {
      UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
      token.setRememberMe(true);//default rememberMe true
      String out = "index";
      try {
        subject.login(token);
        String principal_username = subject.getPrincipal().toString();
        log.info("User [" + principal_username + "] 普通用户 登录成功.");
        model.addAttribute("success", "恭喜"+ principal_username +"登录成功");

        //1.进行session相关事项,可为webSession也可为其他session
        Session session = subject.getSession();
        //session.setAttribute("webUserName", sysUser.getUsername());
        session.setAttribute("sysUserName", sysUser.getUsername());

        //2.一个(非常强大)的实例级别的权限
        if (subject.isPermitted("winnebago:drive:eagle5")) {
          log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  Here are the keys - have fun!");
        } else {
          log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

      } catch (UnknownAccountException e) {
        ExceptionUtil.controllerEH(model,"用户名不存在",e,log);
      } catch (IncorrectCredentialsException e) {
        ExceptionUtil.controllerEH(model,"密码错误请重试",e,log);
      } catch (LockedAccountException e) {
        ExceptionUtil.controllerEH(model,"账号已被锁定",e,log);
      } catch (AuthenticationException e) {
        ExceptionUtil.controllerEH(model,"用户或密码错误",e,log);
      } catch (Exception e) {
        ExceptionUtil.controllerEH(model,"未知错误",e,log);
      } finally {
        return out;
      }
    } else {
      model.addAttribute("success", "已登录");
      return "index";
    }
  }

  @RequestMapping("/logoutWebUser")
  public String logout(WebUser webUser, Model model) throws IOException {
    Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession();
    //session.removeAttribute("webUserName");
    session.removeAttribute("sysbUserName");
    return "redirect:/index.jsp";
  }


}
