package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.common.enums.StatusEnum;
import com.chengjs.cjsssmsweb.req.BaseResponse;
import com.chengjs.cjsssmsweb.vo.NULLBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * ClassName: ErrorController <br/>
 * Function: 错误异常统一处理. <br/>
 *
 * @author chengjs
 * @since JDK 1.7
 */
@ControllerAdvice
public class ErrorController {

  private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Object processUnauthenticatedException(NativeWebRequest request, Exception e) {
    LOG.error("请求出现异常:", e);

    BaseResponse<NULLBody> response = new BaseResponse<NULLBody>();
    response.setCode(StatusEnum.FAIL.getCode());
    if (e instanceof RuntimeException) {
      response.setMessage(e.getMessage());
    } else {
      response.setMessage(StatusEnum.FAIL.getMessage());
    }
    return response;
  }

}
