package com.chengjs.cjsssmsweb.util;

import com.chengjs.cjsssmsweb.enums.StatusEnum;
import org.slf4j.Logger;
import org.springframework.ui.Model;

import java.util.Map;

/**
 * ExceptionUtil: 异常输出,日志打印,其他异常出现后的处理操作
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/8/28
 */
public class ExceptionUtil {


  /**
   * Controller Exception Handle ==> return string
   *
   * @param model     spring mvc view model
   * @param msg       exception message
   * @param e         exception
   * @param re_status 操作状态
   * @param log       sl4j logger of Controller class
   */
  public static void controllerEH(Model model, String msg, Exception e, Logger log, StatusEnum re_status) {
    if (null != model) {
      model.addAttribute("error", msg);
    }
    re_status = StatusEnum.FAIL;
    log.debug(msg);
    e.printStackTrace();
  }

  /**
   * Controller Exception Handle ==> return Map
   * @param msg  exception message
   * @param e    exception
   * @param log  sl4j logger of Controller class
   * @param map  object need be a JSONObject, otherwise ajax will be error
   * @param re_status
   */
  public static void controllerEHJson(String msg, Exception e, Logger log, Map<String, Object> map, StatusEnum re_status) {
    controllerEH(null, msg, e, log, re_status);
    HttpRespUtil.buildRespStatus(msg, map, re_status);
  }


}
