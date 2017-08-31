package com.chengjs.cjsssmsweb.util;

import com.chengjs.cjsssmsweb.enums.EnvEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * PropertiesUtil:
 * author: Chengjs, version:1.0.0, 2017-08-05
 */
public class PropertiesUtil {

  private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

  private static Properties prop = new Properties();

  private Long lastModified = 0l;// TODO 热加载

  static {
    try {
      prop.load(PropertiesUtil.class.getResourceAsStream(EnvEnum.CJS_SSMS_PROP_PATH.val()));
      //转码处理
      Set<Object> keyset = prop.keySet();
      Iterator<Object> iter = keyset.iterator();
      while (iter.hasNext()) {
        String key = (String) iter.next();
        String newValue = null;
        try {
          //属性配置文件自身的编码
          String propertiesFileEncode = "utf-8";
          newValue = new String(prop.getProperty(key).getBytes("ISO-8859-1"), propertiesFileEncode);
        } catch (UnsupportedEncodingException e) {
          newValue = prop.getProperty(key);
        }
        prop.setProperty(key, newValue);
      }

    } catch (Exception e) {
      log.error("读取"+EnvEnum.CJS_SSMS_PROP_PATH.val()+"出错！", e);
    }
  }

  public static Properties getProp() {
    return prop;
  }

  public static void main(String[] args) {
    log.debug(String.valueOf(getProp()));
  }

}
