/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.zygl.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.service.ResRecommendService;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * <pre>
 *  资源保存入库
 * </pre>
 *
 * @author 
 * @version $Id: RecommendResController.java, v 1.0 2015-3-31 10:09:35
 */
@Controller
@RequestMapping("/jy/back/zygl/batch/")
public class YZResourceBatchController extends AbstractController{
	
	@Autowired
	private ResRecommendService recomService;
	
	/**
	 * 保存资源文件
	 * @param type
	 * @return
	 */
	@RequestMapping(value="showUpload")
	public String showUpload(){
		return viewName("/resUpload");
	}
	
	/**
	 * 保存资源文件
	 * @param type
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping(value="resUpload")
	public String resUpload(@RequestParam(value = "resfile", required = false) String resfile,Model model) throws IOException{
		String flag="true";
		try{
			UserSpace userSpace = (UserSpace)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); //用户空间
			if(userSpace!=null){
				File file = new File(resfile);
				boolean bool = recomService.saveComRes(file,file.getAbsolutePath());
				flag = String.valueOf(bool);
			}
		}catch(Exception e){
			flag = "false";
		}

		return  flag;
	}
	
}
