package com.chengjs.cjsssmsweb.service.common;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * ISelectService:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/13
 */
public interface ISelectService {

  /**
   * 通用<selected/>查询
   * @return
   */
  Map<String,String> commonSelect(String method, HashMap<String, String> params)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;


}
