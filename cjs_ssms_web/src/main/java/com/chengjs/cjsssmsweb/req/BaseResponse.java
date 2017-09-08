package com.chengjs.cjsssmsweb.req;


import com.chengjs.cjsssmsweb.enums.StatusEnum;
import com.chengjs.cjsssmsweb.common.util.StringUtil;

import java.io.Serializable;

/**
 * http请求响应对象
 *
 * @param <T>
 * @author minipa_chengjs
 */
public class BaseResponse<T> implements Serializable {

  /**
   * 状态码
   */
  private String code;

  /**
   * 响应消息
   */
  private String message;

  /**
   * 请求号
   */
  private String reqNo;

  /**
   * 响应体
   */
  private T messageBody;

  public BaseResponse() {
  }

  public BaseResponse(T messageBody) {
    this.messageBody = messageBody;
  }

  public BaseResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public BaseResponse(String code, String message, T messageBody) {
    this.code = code;
    this.message = message;
    this.messageBody = messageBody;
  }

  public BaseResponse(String code, String message, String reqNo, T messageBody) {
    this.code = code;
    this.message = message;
    this.reqNo = reqNo;
    this.messageBody = messageBody;
  }

  public static <T> BaseResponse<T> create(T t) {
    return new BaseResponse<T>(t);
  }

  public static <T> BaseResponse<T> create(T t, StatusEnum statusEnum) {
    return new BaseResponse<T>(statusEnum.getCode(), statusEnum.getMessage(), t);
  }

  public static <T> BaseResponse<T> createSuccess(T t, String message) {
    return new BaseResponse<T>(StatusEnum.SUCCESS.getCode(),
        StringUtil.isNullOrEmpty(message) ? StatusEnum.SUCCESS.getMessage() : message, t);
  }

  public static <T> BaseResponse<T> createFail(T t, String message) {
    return new BaseResponse<T>(StatusEnum.FAIL.getCode(),
        StringUtil.isNullOrEmpty(message) ? StatusEnum.FAIL.getMessage() : message, t);
  }

  public static <T> BaseResponse<T> create(T t, StatusEnum statusEnum, String message) {
    return new BaseResponse<T>(statusEnum.getCode(), message, t);
  }


  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(T messageBody) {
    this.messageBody = messageBody;
  }

  public String getReqNo() {
    return reqNo;
  }

  public void setReqNo(String reqNo) {
    this.reqNo = reqNo;
  }

  @Override
  public String toString() {
    return "BaseResponse{" +
        "code=" + code +
        ", message='" + message + '\'' +
        ", reqNo='" + reqNo + '\'' +
        ", messageBody=" + messageBody +
        '}';
  }
}
