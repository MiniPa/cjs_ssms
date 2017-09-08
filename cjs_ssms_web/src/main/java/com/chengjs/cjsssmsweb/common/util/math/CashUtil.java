package com.chengjs.cjsssmsweb.common.util.math;

import com.chengjs.cjsssmsweb.common.util.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * CashUtil: 涉及金额的计算,以及double类型的计算
 * author: Chengjs, version:1.0.0, 2017-09-08
 */
public class CashUtil {

  /*格式化2位小数*/
  private static java.text.DecimalFormat df2 = new java.text.DecimalFormat("############0.00");

  /*格式化4位小数*/
  private static java.text.DecimalFormat df4 = new java.text.DecimalFormat("############0.0000");

  /*格式化6位小数*/
  private static java.text.DecimalFormat df6 = new java.text.DecimalFormat("############0.000000");

  /*如果有4位小数格式化4位小数，否则格式化成2位小数*/
  private static java.text.DecimalFormat df2or4 = new java.text.DecimalFormat("############0.00##");

  /**
   * @param d d
   * @param v v
   * @return boolean
   */
  public static boolean isDoubleEquals(double d, double v) {
    return Math.abs(d - v) < 0.0000001;
  }

  /**
   * 转换null为0.0d,主要用于从map中get数据时,避免空指针异常
   *
   * @param o 对象
   * @return double
   */
  public static double nvlDouble(Object o) {
    double d = 0d;
    if (!StringUtil.isNullOrEmpty(nvl(o))) {
      d = Double.parseDouble(o.toString());
    }
    return d;
  }

  /**
   * 转化null为空字串,主要用于从map中get数据时,避免空指针异常
   * 公司架构提供的StringUtil.nvl(String)只针对String参数,而多数情况我们传入的是Object,因此需要新写一个
   *
   * @param o object
   * @return 字符串
   */
  public static String nvl(Object o) {
    return (null != o) ? o.toString() : "";
  }

  /**
   * 数字金额转化成中文大写的金额
   *
   * @param value value
   * @return String
   */
  public static String changeToBig(double value) {
    boolean fsFlag = false;
    if (value < 0) {
      fsFlag = true;
      value = value * (-1.0);
    }
    char[] hunit = {'拾', '佰', '仟'}; // 段内位置表示
    char[] vunit = {'万', '亿'}; // 段名表示
    char[] digit = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'}; // 数字表示

    DecimalFormat df = new DecimalFormat("##0");

    String valStr = df.format(value * 100); // 转化成字符串

    if (valStr.length() == 1)
      valStr = "0" + valStr;
    String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
    String rail = valStr.substring(valStr.length() - 2); // 取小数部分

    // String prefix = ""; // 整数部分转化的结果
    StringBuffer sb = new StringBuffer();
    String suffix = ""; // 小数部分转化的结果

    // 处理小数点后面的数
    if (rail.equals("00")) { // 如果小数部分为0
      suffix = "整";
    } else {
      // 否则把角分转化出来
      if (rail.charAt(0) == '0') {
        if ("0".equals(head) || "".equals(head)) {
          suffix = digit[rail.charAt(1) - '0'] + "分";
        } else {
          suffix = "零" + digit[rail.charAt(1) - '0'] + "分"; //
        }
      } else if (rail.charAt(1) == '0') {
        suffix = digit[rail.charAt(0) - '0'] + "角整"; //
      } else
        suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
    }
    // 处理小数点前面的数
    if ("0".equals(head) || "".equals(head)) {
      if (fsFlag) {
        return "負" + suffix;
      }
      return suffix;
    }

    char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
    char zero = '0'; // 标志'0'表示出现过0
    byte zeroSerNum = 0; // 连续出现0的次数
    for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
      int idx = (chDig.length - i - 1) % 4; // 取段内位置
      int vidx = (chDig.length - i - 1) / 4; // 取段位置
      if (chDig[i] == '0') { // 如果当前字符是0
        zeroSerNum++; // 连续0次数递增
        if (zero == '0') { // 标志
          zero = digit[0];
        }
        if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
          // prefix += vunit[vidx - 1];
          sb.append(vunit[vidx - 1]);
          zero = '0';
        }
        continue;
      }
      zeroSerNum = 0; // 连续0次数清零
      if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
        // prefix += zero;
        sb.append(zero);
        zero = '0';
      }
      // prefix += digit[chDig[i] - '0']; // 转化该数字表示
      sb.append(digit[chDig[i] - '0']);
      if (idx > 0)
        sb.append(hunit[idx - 1]);
      // prefix += hunit[idx - 1];

      if (idx == 0 && vidx > 0) {
        // prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
        sb.append(vunit[vidx - 1]);
      }
    }

    if (sb.toString().length() > 0)
      sb.append("圆");
    // prefix += '圆'; // 如果整数部分存在,则有圆的字样
    if (StringUtil.isNullOrEmpty(sb.toString()))
      sb = new StringBuffer(digit[0] + "圆");
    if (fsFlag) {
      return "負" + sb.toString() + suffix;
    } else {
      return sb.toString() + suffix; // 返回正确表示
    }
  }

  /**
   * 判断传入的double是不是整数，如果是整数，就不要显示.0，返回int模样的字符串
   *
   * @param number number
   * @return String
   */
  public static String getNumberSring(double number) {
    String tempString = String.valueOf(double2String(number));
    if (tempString.indexOf(".") == -1) {
      return tempString;
    } else {
      String[] numbers = tempString.split("\\.");
      if (Integer.parseInt(numbers[1]) == 0) {
        return numbers[0];
      } else if (numbers[1].endsWith("0")) {
        return tempString.substring(0, tempString.length() - 1);
      } else {
        return tempString;
      }
    }
  }

  /**
   * 将传入的金额格式化，防止在使用String.valueOf(je)时得到科学计数法格式的字符串
   *
   * @param je double
   * @return String 格式化后的金额，保留2位小数
   */
  public static String double2String(double je) {
    return df2.format(je);
  }

  /**
   * 将传入的金额格式化，防止在使用String.valueOf(je)时得到科学计数法格式的字符串
   *
   * @param je 金额
   * @return String
   */
  public static String doubledj2String(double je) {
    return df2or4.format(je);
  }

  /**
   * 将传入的金额格式化，防止在使用String.valueOf(je)时得到科学计数法格式的字符串
   *
   * @param je double
   * @return String 格式化后的金额，保留4位小数
   */
  public static String double2String4(double je) {
    return df4.format(je);
  }

  /**
   * 将传入的金额格式化，防止在使用String.valueOf(je)时得到科学计数法格式的字符串
   *
   * @param je double
   * @return String 格式化后的金额，保留6位小数
   */
  public static String double2String6(double je) {
    return df6.format(je);
  }

  /**
   * 四舍五入
   * @param v     v
   * @param scale scale
   * @return double
   */
  public static double round(double v, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b = new BigDecimal(Double.toString(v));
    BigDecimal one = new BigDecimal("1");
    return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
  }

  /**
   * 两个double数相加
   *
   * @param v v
   * @param d d
   * @return double
   */
  public static double add(double v, double d) {
    BigDecimal b1 = new BigDecimal(v);
    BigDecimal b2 = new BigDecimal(d);
    double d2 = b1.add(b2).doubleValue();
    return b1.add(b2).doubleValue();
  }

  /**
   * 两个double数相加(适合商业运算)
   *
   * @param s   s
   * @param str str
   * @return 结果值
   */
  public static double addString(String s, String str) {
    BigDecimal b1 = new BigDecimal(s);
    BigDecimal b2 = new BigDecimal(str);
    return b1.add(b2).doubleValue();
  }

  /**
   * double + double，并保留scale位小数
   *
   * @param v     v
   * @param d     d
   * @param scale scale
   * @return double
   */
  public static double add(double v, double d, int scale) {
    BigDecimal b1 = new BigDecimal(v);
    BigDecimal b2 = new BigDecimal(d);
    return CashUtil.round(b1.add(b2).doubleValue(), scale);
  }

  /**
   * double - double
   *
   * @param v v
   * @param d d
   * @return double
   */
  public static double sub(double v, double d) {
    BigDecimal b1 = new BigDecimal(v);
    BigDecimal b2 = new BigDecimal(d);
    return b1.subtract(b2).doubleValue();
  }

  /**
   * 两个double数相减(适合商业运算)
   *
   * @param v1 v1
   * @param v2 v2
   * @return double
   */
  public static double subString(String v1, String v2) {
    BigDecimal b1 = new BigDecimal(v1);
    BigDecimal b2 = new BigDecimal(v2);
    return b1.subtract(b2).doubleValue();
  }

  /**
   * 两个double数相减，并保留scale位小数
   *
   * @param v     v
   * @param d     d
   * @param scale scale
   * @return double
   */
  public static double sub(double v, double d, int scale) {
    BigDecimal b1 = new BigDecimal(v);
    BigDecimal b2 = new BigDecimal(d);
    return CashUtil.round(b1.subtract(b2).doubleValue(), scale);
  }

  /**
   * 两个double数相乘
   *
   * @param v v
   * @param d d
   * @return Double
   */
  public static double mul(double v, double d) {
    BigDecimal b1 = new BigDecimal(v);
    BigDecimal b2 = new BigDecimal(d);
    return b1.multiply(b2).doubleValue();
  }

  /**
   * 两个double数相乘
   *
   * @param v     v
   * @param d     d
   * @param scale scale
   * @return Double
   */
  public static double mul(double v, double d, int scale) {
    BigDecimal b1 = new BigDecimal(v);
    BigDecimal b2 = new BigDecimal(d);
    return CashUtil.round(b1.multiply(b2).doubleValue(), scale);
  }

  /**
   * 两个double类型相乘,用String类型构造(适合商业运算)
   *
   * @param s   s
   * @param str str
   * @return double
   */
  public static double mulString(String s, String str) {
    BigDecimal b1 = new BigDecimal(s);
    BigDecimal b2 = new BigDecimal(str);
    return b1.multiply(b2).doubleValue();
  }

  /**
   * 两个double类型相乘,用String类型构造,用String类型构造(适合商业运算)
   *
   * @param s     s
   * @param str   s2
   * @param scale scale
   * @return double
   */
  public static double mulString(String s, String str, int scale) {
    BigDecimal b1 = new BigDecimal(s);
    BigDecimal b2 = new BigDecimal(str);
    return round(b1.multiply(b2).doubleValue(), scale);
  }

  /**
   * 两个double数相除
   *
   * @param v v
   * @param d d
   * @return double
   */
  public static double div(double v, double d) {
    BigDecimal b1 = new BigDecimal(v);
    BigDecimal b2 = new BigDecimal(d);
    return b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
  }

  /**
   * 两个double数相除，并保留scale位小数
   *
   * @param v     v
   * @param d     d
   * @param scale scale
   * @return double
   */
  public static double div(double v, double d, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("The scale must be a positive integer or zero");
    }
    BigDecimal b1 = new BigDecimal(v);
    BigDecimal b2 = new BigDecimal(d);
    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
  }



}
