/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.service;

import java.util.List;

import org.springframework.ui.Model;

import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.org.bo.Organization;

/**
 * 通告 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyAnnunciate.java, v 1.0 2015-06-12 Generate Tools Exp $
 */

public interface JyAnnunciateService extends BaseService<JyAnnunciate, Integer>{
 
	/**
	 * 通知公告列表
	 */
	PageList<JyAnnunciate> getAnnunciateList(JyAnnunciateVo jyAnnunciateVo,Model m);
	
	/**
    * 新增通告
	*/
	
	public void saveAnnunciate(JyAnnunciate annunciate,Integer status);
	
	/**
	 * 获取上一篇通告详情
	 * @param 
	 * @return
	 */
    JyAnnunciate  getPreAnnunciate(Integer id,Integer orgId,Integer status,Integer type);
    
    /**
	 * 获取下一篇通告详情
	 * @param 
	 * @return
	 */
    JyAnnunciate  getNextAnnunciate(Integer id,Integer orgId,Integer status,Integer type);
    
    /**
	 * 获取当前区域下的机构列表
	 * @param search
	 * @return
	 */
	List<Organization> getOrgListOfArea(String search);
	
	/**
   	 * 转发通知公告
   	 * @param 
   	 * @return
   	 */
    public void forwardAnnunciate(JyAnnunciate jyAnnunciate);
}
