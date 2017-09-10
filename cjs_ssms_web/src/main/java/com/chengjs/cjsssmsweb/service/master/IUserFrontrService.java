package com.chengjs.cjsssmsweb.service.master;

import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import com.chengjs.cjsssmsweb.util.page.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * IUserFrontrService:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 21:18
 */
public interface IUserFrontrService {

    public UUser getUserById(String userid);
    public UUser findByLogin(UUser user) ;

    public int createUser(UUser user) ;
    public Page<UUser> findByParams(UUser user, int pageNo, int limit) ;

    int deleteByPrimaryKey(String userid);
    int updateByPrimaryKeySelective(UUser user);

    int findAllCount(UUser user) ;
    List<UUser> findHotUser() ;
    List<UUser> findAllByQuery(UUser user) ;

    /**
     * 分页查询
     * @param map
     * @return
     */
    public List<UUser> list(Map<String, Object> map) ;

    public Long getTotal(Map<String, Object> map);


    /**
     * Shiro的登录验证，通过用户名查询用户信息
     * @param username
     * @return
     */
    public UUser findUserByUsername(String username) ;

  Set<String> findRoleNames(String username);

  Set<String> findPermissionNames(Set<String> roleNames);

  boolean registerUser(UUser user);
}
