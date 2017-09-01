package com.chengjs.cjsssmsweb.service.master;

import com.chengjs.cjsssmsweb.pojo.WebUser;
import com.chengjs.cjsssmsweb.util.page.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * IWebWebUserService:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 21:18
 */
public interface IWebUserService {

    public WebUser getWebUserById(String userid);
    public WebUser findByLogin(WebUser user) ;

    public int createWebUser(WebUser user) ;
    public Page<WebUser> findByParams(WebUser webUser, int pageNo, int limit) ;

    int deleteByPrimaryKey(String userid);
    int updateByPrimaryKeySelective(WebUser webUser);

    int findAllCount(WebUser u) ;
    List<WebUser> findHotWebUser() ;
    List<WebUser> findAllByQuery(WebUser user) ;

    /**
     * 分页查询
     * @param map
     * @return
     */
    public List<WebUser> list(Map<String, Object> map) ;

    public Long getTotal(Map<String, Object> map);


    /**
     * Shiro的登录验证，通过用户名查询用户信息
     * @param username
     * @return
     */
    public WebUser findWebUserByWebUsername(String username) ;

  Set<String> findRoleNames(String username);

  Set<String> findPermissionNames(Set<String> roleNames);

  boolean registerWebUser(WebUser webUser);
}
