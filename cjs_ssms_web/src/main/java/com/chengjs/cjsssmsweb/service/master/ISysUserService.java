package com.chengjs.cjsssmsweb.service.master;

import com.chengjs.cjsssmsweb.pojo.SysUser;

import java.util.Set;

/**
 * ISysUserService:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/24
 */
public interface ISysUserService {


  Set<String> findRoleNames(String sysUserName);

  Set<String> findPermissionNames(Set<String> roleNames);

  SysUser findSysUserBySysUserName(String sysUserName);

}
