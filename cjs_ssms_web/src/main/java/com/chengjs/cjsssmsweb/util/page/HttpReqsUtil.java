package com.chengjs.cjsssmsweb.util.page;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * HttpReqsUtil:
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/9/17
 */
public class HttpReqsUtil {

  /**
   * 获取HttpRequest 参数
   * @return
   */
  public static HashMap<String,String> getRequestVals(HttpServletRequest request) throws UnsupportedEncodingException {
    HashMap<String, String> map = new HashMap<>();
    for (Enumeration<String> names = request.getParameterNames(); names.hasMoreElements();) {
      String key = names.nextElement();
      map.put(key, URLDecoder.decode(
          request.getParameter(key).replace("%", "%25"), "utf-8"));
    }
    return map;
  }



}