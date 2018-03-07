/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;

/**
 * <pre>
 * 注册服务接口
 * </pre>
 * 
 * @author daweiwbs
 * @version $Id: RegisterService.java, v 1.0 2015年8月25日 下午5:58:25 daweiwbs Exp $
 */
public interface RegisterService {

	/**
	 * 获取批量注册模板的文件流
	 * 
	 * @param phaseId
	 * @param orgId
	 * @param response
	 * @throws Exception
	 */
	void getRegisterTemplateFileStream(String templateType, Integer phaseId, Integer orgId, HttpServletResponse response);

	/**
	 * 导出带用户的模板
	 * 
	 * @param phaseId
	 * @param orgId
	 * @param response
	 * @throws Exception
	 */
	void exportTemplateWithUser(String templateType, Integer phaseId, Integer orgId, HttpServletResponse response);

	/**
	 * 批量注册学校用户
	 * 
	 * @param orgId
	 * @param phaseId
	 * @param file
	 * @param response
	 */
	StringBuilder batchRegiter_xxyh(Integer orgId, Integer phaseId, MultipartFile file);

	/**
	 * 批量注册区域用户
	 * 
	 * @param orgId
	 * @param registerFile
	 * @return
	 */
	StringBuilder batchRegiter_qyyh(Integer orgId, MultipartFile registerFile);
	
	void saveUser(Integer roleId, Integer userId, User user, Login login);

}
