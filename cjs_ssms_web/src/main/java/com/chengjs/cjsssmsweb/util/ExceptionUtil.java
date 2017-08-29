package com.chengjs.cjsssmsweb.util;

import org.slf4j.Logger;
import org.springframework.ui.Model;

/**
 * ExceptionUtil: 异常输出,日志打印,其他异常出现后的处理操作
 * author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>, version:1.0.0, 2017/8/28
 */
public class ExceptionUtil {


  /**
   * Controller Exception Handle
   * @param model spring mvc view model
   * @param msg exception message
   * @param e exception
   * @param log sl4j logger of Controller class
   */
  public static void controllerEH(Model model, String msg, Exception e, Logger log) {
    model.addAttribute("error", msg);
    log.debug(msg);
    e.printStackTrace();
  }

}
