package com.chengjs.cjsssmsweb.service.master;

import com.chengjs.cjsssmsweb.common.util.UUIDUtil;
import com.chengjs.cjsssmsweb.mybatis.mapper.dao.UserRolePermissionrDao;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.UUserMapper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * UserServiceImpl:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/24
 */
@Service("userService")
public class UserManagerServiceImpl implements IUserManagerService {

  @Resource
  private UserRolePermissionrDao sURPdao;

  @Autowired
  private UUserMapper userMapper;

  @Override
  public Set<String> findRoleNames(String UserName) {
    Set<String> roleNames = sURPdao.findRoleNamesByUserName(UserName);
    return roleNames;
  }

  @Override
  public Set<String> findPermissionNames(Set<String> roleNames) {
    Set<String> permissionNames = sURPdao.findPermissionNamesByRoleNames(roleNames);
    return permissionNames;
  }

  @Override
  public UUser findUserByUserName(String userName) {
    UUser user = sURPdao.findUserByUserName(userName);
    return user;
  }

  @Override
  public void registerUser(UUser user) {
    UUser re_User = userMapper.selectByPrimaryKey(user.getUsername());
    if (null == re_User) {
      user.setId(UUIDUtil.uuid());
      userMapper.insertSelective(user);
    }
  }
}
