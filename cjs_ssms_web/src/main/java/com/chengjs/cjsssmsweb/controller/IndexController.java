package com.chengjs.cjsssmsweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Index 首页
 */
@Controller
@RequestMapping("/")
public class IndexController {
  private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

  /**
   * model.addAttribute(attr1) ---- jsp ${attr1}
   *
   * @param page    直接取和页面上参数名相同的参数 @RequestParam
   * @param request
   * @param model
   * @return 重定向的方式 return "redirect:index"
   * @throws Exception
   */
  @RequestMapping("/index")
  public String index(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                      HttpServletRequest request, Model model) throws Exception {

    return "index";
  }


}
