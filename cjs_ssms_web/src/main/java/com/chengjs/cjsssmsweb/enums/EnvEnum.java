package com.chengjs.cjsssmsweb.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * EnvEnum: 系统环境Enum
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/8/29
 */
public enum EnvEnum {

  /** 系统环境默认配置properties文件位置 */
  CJS_SSMS_PROP_PATH("/env-config.properties", "系统环境默认配置properties文件位置"),

  /** lucene索引位置--env-config.properties 里配置此路径实际位置 */
  LUCENE_INDEX_PATH("lucene.store", "lucene索引文件位置"),

  /** 系统请求路径配置 8080*/
  IP8080("http://localhost:8080/","系统请求路径配置 8080"),

  /** 系统请求路径配置 9000*/
  IP9000("http://localhost:9000/","系统请求路径配置 9000")








  ;

  /*===================================== string Enum General Method =====================================*/

  /** 枚举值 */
  private final String val;

  /** 枚举描述 */
  private final String message;

  /**
   * 构建一个 EvnEnum 。
   * @param val 枚举值。
   * @param message 枚举描述。
   */
  private EnvEnum(String val, String message) {
    this.val = val;
    this.message = message;
  }

  /**
   * 得到枚举值。
   * @return 枚举值。
   */
  public String getVal() {
    return val;
  }

  /**
   * 得到枚举描述。
   * @return 枚举描述。
   */
  public String getMessage() {
    return message;
  }

  /**
   * 得到枚举值。
   * @return 枚举值。
   */
  public String val() {
    return val;
  }

  /**
   * 得到枚举描述。
   * @return 枚举描述。
   */
  public String message() {
    return message;
  }

  /**
   * 通过枚举值查找枚举值。
   * @param val 查找枚举值的枚举值。
   * @return 枚举值对应的枚举值。
   * @throws IllegalArgumentException 如果 val 没有对应的 EvnEnum 。
   */
  public static EnvEnum findStatus(String val) {
    for (EnvEnum status : values()) {
      if (status.getVal().equals(val)) {
        return status;
      }
    }
    throw new IllegalArgumentException("ResultInfo EvnEnum not legal:" + val);
  }

  /**
   * 获取全部枚举值。
   *
   * @return 全部枚举值。
   */
  public static List<EnvEnum> getAllStatus() {
    List<EnvEnum> list = new ArrayList<EnvEnum>();
    for (EnvEnum status : values()) {
      list.add(status);
    }
    return list;
  }

  /**
   * 获取全部枚举值。
   *
   * @return 全部枚举值。
   */
  public static List<String> getAllStatusVal() {
    List<String> list = new ArrayList<String>();
    for (EnvEnum status : values()) {
      list.add(status.val());
    }
    return list;
  }


}
