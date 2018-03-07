/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.service;

import java.util.List;

import org.springframework.ui.Model;

import com.tmser.tr.annunciate.bo.AnnunciatePunishView;
import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.BaseService;

/**
 * 通告发布查看 服务类
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: AnnunciatePunishView.java, v 1.0 2015-07-01 Generate Tools Exp $
 */

public interface AnnunciatePunishViewService extends BaseService<AnnunciatePunishView, Integer>{
	
	/**
	 * 分页查询已经发布的通告
	 * @param vo
	 * @return
	 */
	PageList<JyAnnunciateVo> findPunishsByPage(JyAnnunciateVo vo,Model m);
	
	/**
	 * 删除发布的通知公告
	 * @param id
	 */
	void deletePunishAnnunciate(Integer id);
	
	/**
	 * 未被查看通知公告消息数量
	 * @param id
	 */
	Integer getAnnunciateNum();
	
	/**
	 * 已发布未被查看通知公告list
	 * @param id
	 */
	List<JyAnnunciate> getNotReadJyList();
}
