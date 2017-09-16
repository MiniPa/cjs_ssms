package com.chengjs.cjsssmsweb.mybatis.mapper.dao;

import com.chengjs.cjsssmsweb.mybatis.pojo.master.Country;
import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.Map;

/**
 * ISelectDao: 通用select查询方法
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/12
 */
public interface ISelectDao extends Mapper<UUser> {

  Map<String,String> selectUser(UUser user);

}
