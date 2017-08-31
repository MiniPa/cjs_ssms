package com.chengjs.cjsssmsweb.dao.master;

import com.chengjs.cjsssmsweb.pojo.WebUser;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface WebUserRolePermissionDao {

  WebUser findByLogin(WebUser user);

  int findAllCount(WebUser webUser);

  List<WebUser> findHotWebUser();

  List<WebUser> findByParams(WebUser webUser, RowBounds rowBound);

  List<WebUser> findAllByQuery(WebUser webUser);

  List<WebUser> list(Map<String, Object> map);

  Long getTotal(Map<String, Object> map);

  WebUser findWebUserByWebUsername(String username);

  Set<String> findRoleNames(String webusername);

  Set<String> findPermissionNames(Set<String> roleNames);

}