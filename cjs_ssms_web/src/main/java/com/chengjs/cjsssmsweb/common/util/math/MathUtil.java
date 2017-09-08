package com.chengjs.cjsssmsweb.common.util.math;

import java.security.SecureRandom;

/**
 * MathUtil: 数字相关工具类
 * author: Chengjs, version:1.0.0, 2017-09-08
 */
public class MathUtil {

  /**
   * 生成随机数字
   *
   * @param iLength 随机数位数
   * @return 生成的随机数
   */
  public static String genRandNum(int iLength) {
    StringBuffer result = new StringBuffer();
    SecureRandom tRandom = new SecureRandom();
    long tLong = getAbsRandom(tRandom);
    result.append(tLong);
    // aString = (String.valueOf(tLong)).trim();
    while (result.length() < iLength) {
      tLong = getAbsRandom(tRandom);
      result.append(tLong);
      // aString += (String.valueOf(tLong)).trim();
    }
    // result = result.substring(0,aLength);
    return result.substring(0, iLength);
  }

  /**
   * 生成正数随机数
   * @param tRandom
   * @return
   */
  private static long getAbsRandom(SecureRandom tRandom) {
    long l = tRandom.nextLong();
    if (l == Long.MIN_VALUE) {
      l = Long.MAX_VALUE;
    } else {
      l = Math.abs(l);
    }
    return l;
  }

}
