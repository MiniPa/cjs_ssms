package com.chengjs.cjsssmsweb.service.master;

import com.chengjs.cjsssmsweb.common.util.UUIDUtil;
import com.chengjs.cjsssmsweb.common.util.codec.MD5Util;
import com.chengjs.cjsssmsweb.mybatis.mapper.dao.UserRolePermissionDao;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.UUserMapper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
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
 * IUserServiceImpl:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/8/29
 */
@Service
public class UserFrontServiceImpl implements IUserFrontService{

  @Autowired
  private DataSourceTransactionManager transactionManager;

  /*========================== userMapper ==========================*/
  @Autowired
  private UUserMapper userMapper;

  @Override
  public UUser getUserById(String userid) { return userMapper.selectByPrimaryKey(userid); }

  @Override
  public int deleteByPrimaryKey(String userid) { return userMapper.deleteByPrimaryKey(userid); }

  @Override
  public int updateByPrimaryKeySelective(UUser user) { return userMapper.updateByPrimaryKeySelective(user); }

  @Override
  public int createUser(UUser user) {
    return userMapper.insertSelective(user);
  }



  /*========================== userDao ==========================*/
  @Autowired
  private UserRolePermissionDao userRolePermissionDao;

  @Override
  public UUser findByLogin(UUser user) {
    return userRolePermissionDao.findByLogin(user);
  }

  /**
   * 分页查询
   * @param user
   * @param pageNo
   * @param limit
   * @return
   */
  @Override
  public Page<UUser> findByParams(UUser user, int pageNo, int limit) {
    Page<UUser> page = new Page<UUser>();
    page.setPageNo(pageNo);
    page.setLimit(limit);

    int offset = page.getOffsets();
    RowBounds rowBound = new RowBounds(offset, limit);

    List<UUser> users = userRolePermissionDao.findByParams(user,rowBound);
    page.setRows(users);
    int total = userRolePermissionDao.findAllCount(user) ;
    page.setTotal(total) ;
    if(offset >= page.getTotal()){
      page.setPageNo(page.getTotalPages());
    }
    return page ;
  }

  @Override
  public int findAllCount(UUser user) {
    return userRolePermissionDao.findAllCount(user);
  }

  @Override
  public List<UUser> findHotUser() {
    return userRolePermissionDao.findHotUser();
  }

  @Override
  public List<UUser> findAllByQuery(UUser user) {
    return userRolePermissionDao.findAllByQuery(user);
  }

  @Override
  public List<UUser> list(Map<String, Object> map) {
    return userRolePermissionDao.list(map);
  }

  @Override
  public Long getTotal(Map<String, Object> map) {
    return userRolePermissionDao.getTotal(map);
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
  public UUser findUserByUsername(String username) {
    return userRolePermissionDao.findUserByUsername(username);
  }

  @Override
  public Set<String> findRoleNames(String username) {
    return userRolePermissionDao.findRoleNames(username);
  }

  @Override
  public Set<String> findPermissionNames(Set<String> roleNames) {
    return userRolePermissionDao.findPermissionNames(roleNames);
  }

  /**
   * 开启事务:代码形式开启
   * 其实这里不是n个DDL 是1个DQL&1DDL可以不用transaction
   *
   * @param user
   * @return
   */
  @Override
  public boolean registerUser(UUser user) {
    /*开启事务 -- 提取了个新方法, TODO 此处transactionManager不能再Transactioner中注入么??*/
    Transactioner tr = new Transactioner(transactionManager);
    boolean commit = false;
    boolean result = false;
    try {
      UUser re_user = userMapper.selectByPrimaryKey(user.getUsername());
      if (null == re_user) {
        user.setId(UUIDUtil.uuid());
        user.setPassword(MD5Util.md5(user.getPassword()));
        userMapper.insertSelective(user);
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
