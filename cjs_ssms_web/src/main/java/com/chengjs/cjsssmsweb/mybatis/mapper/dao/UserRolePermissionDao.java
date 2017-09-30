package com.chengjs.cjsssmsweb.mybatis.mapper.dao;

import com.chengjs.cjsssmsweb.mybatis.pojo.master.Country;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserRolePermissionDao extends Mapper<Country> {

  UUser findByLogin(UUser user);

  int findAllCount(UUser user);

  List<UUser> findHotUser();

  List<UUser> findByParams(UUser user, RowBounds rowBound);

  List<UUser> findAllByQuery(UUser user);

  List<UUser> list(Map<String, Object> map);

  Long getTotal(Map<String, Object> map);

  UUser findUserByUsername(String username);

  Set<String> findRoleNames(String username);

  Set<String> findPermissionNames(Set<String> roleNames);

  Set<String> findRoleNamesByUserName(@Param("userName")String userName);

  Set<String> findPermissionNamesByRoleNames(@Param("set")Set<String> roleNames);

  UUser findUserByUserName(@Param("userName")String userName);

}