package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.common.enums.StatusEnum;
import com.chengjs.cjsssmsweb.enums.TestEnv;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import com.chengjs.cjsssmsweb.service.master.IUserManagerService;
import com.chengjs.cjsssmsweb.util.ExceptionUtil;
import com.chengjs.cjsssmsweb.util.HttpRespUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * UserController 管理台权限管理模块
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>
 */
@Controller
@RequestMapping("/muser")
public class UserManagerController {

  private static final Logger log = LoggerFactory.getLogger(UserManagerController.class);

  @Autowired
  private IUserManagerService userMService;

  /**
   * 注册
   * JavaBean: User这种的bean会自动返回给前端
   *
   * @param user
   * @param model
   * @param response
   * @return
   */
  @RequestMapping("/muser/register")
  public void register(UUser user, Model model, HttpServletResponse response) {
    try {
      userMService.registerUser(user);
      log.info("用户：" + user.getUsername() + "注册管理台成功。");
      HttpRespUtil.respJson(StatusEnum.SUCCESS, response);
    } catch (Exception e) {
      log.debug("注册管理台用户异常");
      e.printStackTrace();
    }
  }

  /**
   * 登录
   * <p>
   * 非JavaBean: 要和前端传递的参数对象的参数名,保持一致 var params = {param1:v1},这里的参数 (String param1)
   * 其不会自动返回给前端,用response或model增加属性方式返回给前端
   *
   * @param model
   * @return
   */
  @RequestMapping("/mloginUser")
  public String login(UUser user, Model model) {

    //用户视图相关操作尽在Subject
    Subject subject = SecurityUtils.getSubject();
    if (!subject.isAuthenticated()) {
      UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
      token.setRememberMe(TestEnv.onTFalse);//default rememberMe true
      String out = "../../login";
      try {
        subject.login(token);
        String principal_username = subject.getPrincipal().toString();
        log.info("User [" + principal_username + "] 后台用户 登录成功.");
        model.addAttribute("success", "恭喜" + principal_username + "登录成功");

        //1.进行session相关事项,可为webSession也可为其他session
        Session session = subject.getSession();
        session.setAttribute("UserName", user.getUsername());

        //2.一个(非常强大)的实例级别的权限
        if (subject.isPermitted("winnebago:drive:eagle5")) {
          log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  Here are the keys - have fun!");
        } else {
          log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        model.addAttribute("User", user);
        out = "admin";
      } catch (UnknownAccountException e) {
        ExceptionUtil.controllerEH(model, "用户名不存在", e, log, StatusEnum.FAIL);
      } catch (IncorrectCredentialsException e) {
        ExceptionUtil.controllerEH(model, "密码错误请重试", e, log, StatusEnum.FAIL);
      } catch (LockedAccountException e) {
        ExceptionUtil.controllerEH(model, "账号已被锁定", e, log, StatusEnum.FAIL);
      } catch (AuthenticationException e) {
        ExceptionUtil.controllerEH(model, "用户或密码错误", e, log, StatusEnum.FAIL);
      } catch (Exception e) {
        ExceptionUtil.controllerEH(model, "未知错误", e, log, StatusEnum.FAIL);
      } finally {
        return out;
      }
    } else {
      model.addAttribute("success", "已登录");
      return "admin";
    }
  }

  @RequestMapping("/mlogoutUser")
  public String logout(UUser user, Model model) throws IOException {
    Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession();
    session.removeAttribute("UserName");
    return "redirect:../login.jsp";
  }


}
