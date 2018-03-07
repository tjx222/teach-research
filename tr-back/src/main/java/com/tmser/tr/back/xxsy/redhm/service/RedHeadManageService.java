/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.redhm.service;

import java.util.List;

import org.springframework.ui.Model;

import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.redheadmanage.bo.RedHeadManage;


/**
 * 红头管理 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: RedHeadManage.java, v 1.0 2015-08-26 Generate Tools Exp $
 */

public interface RedHeadManageService extends BaseService<RedHeadManage, Integer> {
	/*
	 *list 学校下红头列表的集合
	 */
	List<RedHeadManage> getredhmlist(Model m,RedHeadManage rhm);
	
	/*
	 *  修改或者保存红头标题
	 */
	public void updateOrSaveRedHead(RedHeadManage rhm);
	
	/*
	 *  删除红头标题
	 */
	public void deleteRedHead(Integer id);
	
	/*
	 *  编辑红头标题
	 */
	public RedHeadManage updateRedHead(Integer id);
}
