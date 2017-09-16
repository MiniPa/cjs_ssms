package com.chengjs.cjsssmsweb.service.common;

import com.chengjs.cjsssmsweb.mybatis.pojo.master.UUser;

import java.util.Map;

/**
 * ISelectService:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/13
 */
public interface ISelectService {

  Map<String,String> selectUser(UUser user);


}
