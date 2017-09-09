package com.chengjs.cjsssmsweb.components.shiro;

import com.chengjs.cjsssmsweb.common.util.codec.MD5Util;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CustomCredentialsMatcher:
 * 密码校验方法继承 SimpleCredentialsMatcher或HashedCredentialsMatcher 实现doCredentialsMatch()
 *
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/8/28
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
  private static final Logger log = LoggerFactory.getLogger(CustomCredentialsMatcher.class);

  @Override
  public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
    UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

    Object tokenCredentials = encrypt(String.valueOf(token.getPassword()));
    Object accountCredentials = getCredentials(info);
    //将密码加密与系统加密后的密码校验，内容一致就返回true,不一致就返回false
    return equals(tokenCredentials, accountCredentials);
  }

  /**
   * 将传进来密码加密方法
   * @param data
   * @return
   */
  private String encrypt(String data) {
    String encrypt = MD5Util.md5(data, "");
    //String encrypt = new Sha384Hash(data).toBase64();
    log.debug(data + ":" + encrypt);
    return encrypt;
  }

}
