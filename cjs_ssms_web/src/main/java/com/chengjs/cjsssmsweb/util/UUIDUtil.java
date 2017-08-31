package com.chengjs.cjsssmsweb.util;

import java.util.UUID;

/**
 * UUIDUtil:
 * author: Chengjs, version:1.0.0, 2017-08-06
 */
public class UUIDUtil {
  public static String uuid(){
    UUID uuid=UUID.randomUUID();
    String str = uuid.toString();
    String uuidStr=str.replace("-", "");
    return uuidStr;
  }
}
