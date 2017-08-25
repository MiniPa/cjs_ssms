package com.chengjs.cjsssmsweb.controller;

import com.chengjs.cjsssmsweb.service.master.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysuser")
public class SysUserController {

  private static final Logger LOG = LoggerFactory.getLogger(SysUserController.class);

  @Autowired
  private ISysUserService sysUserService;


}
