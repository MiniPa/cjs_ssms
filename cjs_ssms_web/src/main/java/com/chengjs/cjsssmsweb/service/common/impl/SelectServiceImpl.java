package com.chengjs.cjsssmsweb.service.common.impl;

import com.chengjs.cjsssmsweb.mybatis.mapper.dao.ISelectDao;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import com.chengjs.cjsssmsweb.service.common.ISelectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * SelectServiceImpl:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/13
 */
@Service
public class SelectServiceImpl implements ISelectService {

  @Resource
  private ISelectDao selectDao;

  @Override
  public Map<String, String> selectUser(UUser user) {
    Map<String, String> map = selectDao.selectUser(user);
    return map;
  }

}
