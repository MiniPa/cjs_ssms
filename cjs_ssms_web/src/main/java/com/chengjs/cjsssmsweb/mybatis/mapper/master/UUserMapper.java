package com.chengjs.cjsssmsweb.mybatis.mapper.master;

import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UUserMapper extends Mapper<UUser> {

  /**
   * 测试通过POJO参数方式获取page
   * @param uuser
   * @return
   */
  List<UUser> gridUsers(UUser uuser);

  /**
   * 测试通过Map参数方式获取page
   * @param params
   * @return
   */
  List<Map<String,String>> users(Map<String,String> params);

}