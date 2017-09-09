package com.chengjs.cjsssmsweb.service.master;

import com.chengjs.cjsssmsweb.common.util.UUIDUtil;
import com.chengjs.cjsssmsweb.common.util.codec.MD5Util;
import com.chengjs.cjsssmsweb.mybatis.mapper.dao.WebUserRolePermissionDao;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.WebUserMapper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.WebUser;
import com.chengjs.cjsssmsweb.util.Transactioner;
import com.chengjs.cjsssmsweb.util.page.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * IWebUserServiceImpl:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/8/29
 */
@Service
public class WebUserServiceImpl implements IWebUserService {

  @Autowired
  private DataSourceTransactionManager transactionManager;

  /*========================== webUserMapper ==========================*/
  @Autowired
  private WebUserMapper webUserMapper;

  @Override
  public WebUser getWebUserById(String userid) { return webUserMapper.selectByPrimaryKey(userid); }

  @Override
  public int deleteByPrimaryKey(String userid) { return webUserMapper.deleteByPrimaryKey(userid); }

  @Override
  public int updateByPrimaryKeySelective(WebUser webUser) { return webUserMapper.updateByPrimaryKeySelective(webUser); }

  @Override
  public int createWebUser(WebUser user) {
    return webUserMapper.insertSelective(user);
  }



  /*========================== webUserDao ==========================*/
  @Autowired
  private WebUserRolePermissionDao webUserRolePermissionDao;

  @Override
  public WebUser findByLogin(WebUser user) {
    return webUserRolePermissionDao.findByLogin(user);
  }

  /**
   * 分页查询
   * @param webUser
   * @param pageNo
   * @param limit
   * @return
   */
  @Override
  public Page<WebUser> findByParams(WebUser webUser, int pageNo, int limit) {
    Page<WebUser> page = new Page<WebUser>();
    page.setPageNo(pageNo);
    page.setLimit(limit);

    int offset = page.getOffsets();
    RowBounds rowBound = new RowBounds(offset, limit);

    List<WebUser> users = webUserRolePermissionDao.findByParams(webUser,rowBound);
    page.setRows(users);
    int total = webUserRolePermissionDao.findAllCount(webUser) ;
    page.setTotal(total) ;
    if(offset >= page.getTotal()){
      page.setPageNo(page.getTotalPages());
    }
    return page ;
  }

  @Override
  public int findAllCount(WebUser webUser) {
    return webUserRolePermissionDao.findAllCount(webUser);
  }

  @Override
  public List<WebUser> findHotWebUser() {
    return webUserRolePermissionDao.findHotWebUser();
  }

  @Override
  public List<WebUser> findAllByQuery(WebUser webUser) {
    return webUserRolePermissionDao.findAllByQuery(webUser);
  }

  @Override
  public List<WebUser> list(Map<String, Object> map) {
    return webUserRolePermissionDao.list(map);
  }

  @Override
  public Long getTotal(Map<String, Object> map) {
    return webUserRolePermissionDao.getTotal(map);
  }

  /**
   * Shiro的登录验证，通过用户名查询用户信息
   *
   * EAO issue: 同一个app只能用一个shiro密码校验么? 注入多个daoManager,Realm等可实现多重的登录校验么??
   *
   * @param username
   * @return
   */

  @Override
  public WebUser findWebUserByWebUsername(String username) {
    return webUserRolePermissionDao.findWebUserByWebUsername(username);
  }

  @Override
  public Set<String> findRoleNames(String username) {
    return webUserRolePermissionDao.findRoleNames(username);
  }

  @Override
  public Set<String> findPermissionNames(Set<String> roleNames) {
    return webUserRolePermissionDao.findPermissionNames(roleNames);
  }

  /**
   * 开启事务:代码形式开启
   * 其实这里不是n个DDL 是1个DQL&1DDL可以不用transaction
   *
   * @param webUser
   * @return
   */
  @Override
  public boolean registerWebUser(WebUser webUser) {
    /*开启事务 -- 提取了个新方法, TODO 此处transactionManager不能再Transactioner中注入么??*/
    Transactioner tr = new Transactioner(transactionManager);
    boolean commit = false;
    boolean result = false;
    try {
      WebUser re_webUser = webUserMapper.selectByPrimaryKey(webUser.getUsername());
      if (null == re_webUser) {
        webUser.setUserid(UUIDUtil.uuid());
        webUser.setPassword(MD5Util.md5(webUser.getPassword()));
        webUserMapper.insertSelective(webUser);
        result = true;
      } else {
        result = false;
      }
      commit = true;
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      tr.end(commit);
    }
    return result;
  }



}
