package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.pojo.SysUser;
import com.chengjs.cjsssmsweb.service.master.ISysUserService;
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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SysUserController 管理台权限管理模块
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>
 */
@Controller
@RequestMapping("/")
public class SysUserController {

  private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

  @Autowired
  private ISysUserService sysUserService;

  @RequestMapping("/loginSysUser")
  public String login(SysUser sysUser, Model model) {
    //用户视图相关操作尽在Subject
    Subject subject = SecurityUtils.getSubject();
    if (!subject.isAuthenticated()) {
      UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
      token.setRememberMe(true);//default rememberMe true
      String out = "../../login";
      try {
        subject.login(token);
        String principal_username = subject.getPrincipal().toString();
        log.info("User [" + principal_username + "] 登录成功.");
        model.addAttribute("success", "恭喜"+ principal_username +"登录成功");

        //1.进行session相关事项,可为webSession也可为其他session
        Session session = subject.getSession();
        session.setAttribute("sysUserName", sysUser.getUsername());

        //2.一个(非常强大)的实例级别的权限
        if (subject.isPermitted("winnebago:drive:eagle5")) {
          log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  Here are the keys - have fun!");
        } else {
          log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        out = "admin";
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
      return "admin";
    }
  }

  @RequestMapping("/logoutSysUser")
  public String logout(SysUser sysUser, Model model) {
    Subject subject = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(), sysUser.getPassword());
    return null;
  }


}
