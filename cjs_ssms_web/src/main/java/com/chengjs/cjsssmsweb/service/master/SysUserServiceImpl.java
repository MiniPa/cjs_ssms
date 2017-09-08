package com.chengjs.cjsssmsweb.service.master;

import com.chengjs.cjsssmsweb.dao.master.SysUseRolePermissionrDao;
import com.chengjs.cjsssmsweb.dao.master.SysUserMapper;
import com.chengjs.cjsssmsweb.pojo.SysUser;
import com.chengjs.cjsssmsweb.common.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * SysUserServiceImpl:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/24
 */
@Service("sysUserService")
public class SysUserServiceImpl implements ISysUserService {

  @Resource
  private SysUseRolePermissionrDao sURPdao;

  @Autowired
  private SysUserMapper sysUserMapper;

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

  @Override
  public void registerSysUser(SysUser sysUser) {
    SysUser re_sysUser = sysUserMapper.selectByPrimaryKey(sysUser.getUsername());
    if (null == sysUser) {
      sysUser.setUserid(UUIDUtil.uuid());
      sysUserMapper.insertSelective(sysUser);
    }
  }
}
