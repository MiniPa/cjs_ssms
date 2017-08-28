package com.chengjs.cjsssmsweb.service.master;

import com.chengjs.cjsssmsweb.dao.master.SysUseRolePermissionrDao;
import com.chengjs.cjsssmsweb.pojo.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * ISysUserServiceImpl:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/24
 */
@Service("sysUserService")
public class ISysUserServiceImpl implements ISysUserService {

  @Resource
  private SysUseRolePermissionrDao sURPdao;

  @Override
  public Set<String> findRoleNames(String sysUserName) {
    Set<String> roleNames = sURPdao.findRoleNamesBySysUserName(sysUserName);
    return roleNames;
  }

  @Override
  public Set<String> findPermissionNames(Set<String> roleNames) {
    Set<String> permissionNames = sURPdao.findPermissionNamesByRoleNames(roleNames);
    return permissionNames;
  }

  @Override
  public SysUser findSysUserBySysUserName(String sysUserName) {
    SysUser sysUser = sURPdao.findSysUserBySysUserName(sysUserName);
    return sysUser;
  }
}
