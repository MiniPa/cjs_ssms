package com.chengjs.cjsssmsweb.enums;

import com.chengjs.cjsssmsweb.util.PropertiesUtil;

/**
 * TestEnv: 测试环境各种开关类
 *
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/8/31
 */
public class TestEnv {

  /**
   * 当测试开启时,onTTrue为 true
   */
  public static boolean onTTrue = true;

  /**
   * 当测试开启时, onTFalse为 false
   */
  public static boolean onTFalse = false;

  public TestEnv() {
    String testEnv = PropertiesUtil.getValue("test");
    onTTrue = "true".equals(testEnv) ? true : false;
    onTFalse = "true".equals(testEnv) ? false : true;
  }
}
