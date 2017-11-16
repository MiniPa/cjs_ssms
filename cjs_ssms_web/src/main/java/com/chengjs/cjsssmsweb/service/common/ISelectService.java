package com.chengjs.cjsssmsweb.service.common;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
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
  List<Map<String, String>> select(String method, HashMap<String, String> params)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;

  /**
   * 通用grid查询
   * @param params
   * @return
   */
  Map<String, Object> grid(int pageNum, int pageSize, String field, String sort, HashMap<String, String> params)
      throws ClassNotFoundException, IllegalAccessException,
      InstantiationException, NoSuchMethodException, InvocationTargetException;

}
