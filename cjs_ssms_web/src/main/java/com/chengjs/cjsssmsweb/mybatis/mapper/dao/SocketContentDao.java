package com.chengjs.cjsssmsweb.mybatis.mapper.dao;

import com.chengjs.cjsssmsweb.mybatis.pojo.master.Country;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.SocketContent;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SocketContentDao  extends Mapper<Country>  {

  List<SocketContent> findSocketContentList();

}