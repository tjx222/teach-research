/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.dao;

import java.util.List;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;

 /**
 * 通告 DAO接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyAnnunciate.java, v 1.0 2015-06-12 Generate Tools Exp $
 */
public interface JyAnnunciateDao extends BaseDAO<JyAnnunciate, Integer>{
	
	/**
	 * 分页查询发布的通告
	 * @param vo
	 * @return
	 */
	PageList<JyAnnunciate> findPunishsByPage(JyAnnunciateVo vo);
	
	/**
	 * 获取上一篇通告详情
	 * @param 
	 * @return
	 */
    JyAnnunciate  getPreAnnunciate(JyAnnunciate jAnnunciate,Integer type);
    
    /**
	 * 获取下一篇通告详情
	 * @param 
	 * @return
	 */
    JyAnnunciate  getNextAnnunciate(JyAnnunciate jAnnunciate,Integer type);
    
    /**
   	 * 查询已发布未被查看的通知公告集合
   	 * @param 
   	 * @return
   	 */
    List<JyAnnunciate> getNotReadJyList(Integer orgId,Integer userId);
}