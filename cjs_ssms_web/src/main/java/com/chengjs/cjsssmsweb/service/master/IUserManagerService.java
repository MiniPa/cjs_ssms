package com.chengjs.cjsssmsweb.service.master;


import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;

import java.util.Set;

/**
 * ISelectService:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 2017/8/24
 */
public interface IUserManagerService {

  Set<String> findRoleNames(String UserName);

  Set<String> findPermissionNames(Set<String> roleNames);

  UUser findUserByUserName(String UserName);

  void registerUser(UUser uUser);

}
