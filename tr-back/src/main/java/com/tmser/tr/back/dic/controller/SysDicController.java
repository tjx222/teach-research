/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */

package com.tmser.tr.back.dic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.dic.service.SysDicService;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.SysDic;

/**
 * <pre>
 *
 * </pre>
 *
 * @author 3020mt
 * @version $Id: SysDicController.java, v 1.0 2017年12月13日 下午4:40:19 3020mt Exp $
 */
@Controller
@RequestMapping("jy/back/dic")
public class SysDicController extends AbstractController {
  @Autowired
  private SysDicService dicService;
  
  @RequestMapping("exists")
  @ResponseBody
  public JuiResult isExists (SysDic dic) {
    JuiResult re = new JuiResult();
    SysDic findOne = dicService.findOne(dic);
    if (findOne != null) {
      re.setData(findOne);
    }
    return re;
  }

}
