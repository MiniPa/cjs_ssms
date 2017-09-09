package com.chengjs.cjsssmsweb.service.master;

import com.chengjs.cjsssmsweb.mybatis.mapper.dao.SocketContentDao;
import com.chengjs.cjsssmsweb.mybatis.mapper.master.SocketContentMapper;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.SocketContent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * SocketContentServiceImpl:
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs</a>, version:1.0.0, 22:14
 */
@Service
public class SocketContentServiceImpl implements ISocketContentService {

  @Resource
  private SocketContentDao socketContentDao;

  @Resource
  private SocketContentMapper socketContentMapper;

  @Override
  public List<SocketContent> findSocketContentList() {
    return socketContentDao.findSocketContentList();
  }

  @Override
  public int insertSelective(SocketContent socketContent) {
    return socketContentMapper.insertSelective(socketContent);

  }
}
