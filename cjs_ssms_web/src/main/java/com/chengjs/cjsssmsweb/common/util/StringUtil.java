package com.chengjs.cjsssmsweb.common.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * 字符串工具类
 *
 * @author
 */
public class StringUtil {

  /**
   * 字符串是否为空，包括blank
   *
   * @param str
   * @return
   */
  public static boolean isNullOrEmpty(String str) {
    return null != str && 0 != str.trim().length() ? false : true;
  }

  /**
   * 返回"null" 或者 toString
   *
   * @param obj
   * @return
   */
  public static boolean reNullOrEmpty(Object obj) {
    return obj == null || "".equals(obj.toString());
  }

  /**
   * 判断是否是空
   *
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    if (str == null || "".equals(str.trim())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 判断是否不是空
   *
   * @param str
   * @return
   */
  public static boolean isNotEmpty(String str) {
    if ((str != null) && !"".equals(str.trim())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 格式化模糊查询
   *
   * @param str
   * @return
   */
  public static String formatLike(String str) {
    if (isNotEmpty(str)) {
      return "%" + str + "%";
    } else {
      return null;
    }
  }

  /**
   * 返回"null"或者toString
   *
   * @param obj
   * @return
   */
  public static String toString(Object obj) {
    if (obj == null) return "null";
    return obj.toString();
  }

  /**
   * 用标签来连接string
   *
   * @param s
   * @param delimiter
   * @return
   */
  public static String join(Collection s, String delimiter) {
    StringBuffer buffer = new StringBuffer();
    Iterator iter = s.iterator();
    while (iter.hasNext()) {
      buffer.append(iter.next());
      if (iter.hasNext()) {
        buffer.append(delimiter);
      }
    }
    return buffer.toString();
  }

  /**
   * 首字母变大写
   * @param name
   * @return
   */
  public static String captureName(String name) {
    /*效率低的方法*/
    /* name = name.substring(0, 1).toUpperCase() + name.substring(1);
    return name;*/

    /*高效率方法*/
    char[] cs = name.toCharArray();
    cs[0] -= 32;
    return String.valueOf(cs);
  }

  /**
   * 去除%
   * @param str
   * @return
   */
  public static String reSqlLikeStr(String str) {
    return str.replace("%", "");
  }


}
