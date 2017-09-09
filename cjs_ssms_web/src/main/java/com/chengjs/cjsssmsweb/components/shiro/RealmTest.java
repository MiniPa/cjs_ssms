package com.chengjs.cjsssmsweb.components.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RealmTest:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/26
 */
public class RealmTest {
  private static final Logger LOG = LoggerFactory.getLogger(RealmTest.class);

  public static void main(String[] args) {
    //此处从ini文件来实现用用户角色权限配置，实际多从数据库表来实现
    Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini.bak");

    //SercurityManager 对象
    SecurityManager instance = factory.getInstance();
    SecurityUtils.setSecurityManager(instance);

    //测试用户
    Subject currentUser = SecurityUtils.getSubject();
    UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");

    boolean result = false;
    try {
      currentUser.login(token);
      result = true;
      LOG.debug("认证成功");
    } catch (Exception e) {
      result = false;
      LOG.debug("认证失败");
    }

  }
}
