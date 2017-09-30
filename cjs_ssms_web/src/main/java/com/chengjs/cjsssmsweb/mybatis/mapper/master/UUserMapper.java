package com.chengjs.cjsssmsweb.mybatis.mapper.master;

import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UUserMapper extends Mapper<UUser> {

  List<UUser> gridUsers(UUser uuser);

}