package com.chengjs.cjsssmsweb.components.shiro;

import com.chengjs.cjsssmsweb.mybatis.pojo.master.SysUser;
import com.chengjs.cjsssmsweb.service.master.ISysUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.Set;

/**
 * SysUserRealm:
 * Realm: 其实现的数据模型规定了如何进行授权 与RDBMS LDAP等交流, 完全控制授权模型的创建和定义
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/25
 */
public class SysUserRealm extends AuthorizingRealm {

  @Resource
  private ISysUserService sysUserService;

  /**
   * 权限认证
   *
   * @param principals
   * @return AuthorizationInfo
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String principal_username = principals.getPrimaryPrincipal().toString();
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    Set<String> roleNames = sysUserService.findRoleNames(principal_username);
    Set<String> permissionNames = sysUserService.findPermissionNames(roleNames);
    info.setRoles(roleNames);

    //基于权限的授权相比基于角色的授权更好,更灵活,更符合实际情况
    info.setStringPermissions(permissionNames);
    return info;
  }

  /**
   * 登录认证,在权限认证前执行
   *
   * @param token
   * @return AuthenticationInfo
   * @throws AuthenticationException
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String sysUserName = token.getPrincipal().toString();
    SysUser sysUser = sysUserService.findSysUserBySysUserName(sysUserName);
    if (null == sysUser) {
      return null;
    } else {
      /**
       * info中principal选择方案：1.username, 2.sysUser, 3.UserWithRoleAndPermission
       * 各有优劣,这里选择使用username
       *
       * EAO isssue: 新建对象WholeUser,有属性roles,permissions,登录时产生此对象作为principals,则authorization时无需再和sql交互
       * 1.优势: 减少sql交互,
       * 2.劣势：缓存大,对变更的用户信息反馈不及时
       * 适用： 变化不大信息量少,但权限校验频繁的用户类型.
       *
       * SimpleAuthorizationInfo: param: principal检查源码最后被强转为Collection不知何意??
       */
      SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser.getUsername(), sysUser.getPassword(), "sysUserRealm");
      return info;
    }
  }
}
