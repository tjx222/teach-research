/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.clss.service;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
/**
 * 班级教师批量操作
 * @version $Id: RegisterService.java
 */
public interface SchClassBatchService {

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
	 * 批量导入用户
	 * 
	 * @param orgId
	 * @param phaseId
	 * @param file
	 * @param response
	 */
	StringBuilder batchImportTeacher(Integer orgId, Integer phaseId, MultipartFile file);
}
