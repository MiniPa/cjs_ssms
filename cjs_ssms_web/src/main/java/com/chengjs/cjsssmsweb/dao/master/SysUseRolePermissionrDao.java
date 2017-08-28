package com.chengjs.cjsssmsweb.dao.master;

import com.chengjs.cjsssmsweb.pojo.SysPermission;
import com.chengjs.cjsssmsweb.pojo.SysRole;
import com.chengjs.cjsssmsweb.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * SysUseRolePermissionrDao:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/24
 */
public interface SysUseRolePermissionrDao {

  Set<String> findRoleNamesBySysUserName(@Param("sysUserName")String sysUserName);

  Set<String> findPermissionNamesByRoleNames(@Param("set")Set<String> roleNames);

  SysUser findSysUserBySysUserName(@Param("sysUserName")String sysUserName);


}
