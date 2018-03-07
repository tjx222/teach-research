/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.dao.JyAnnunciateDao;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.page.PageList;

/**
 * 通告 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyAnnunciate.java, v 1.0 2015-06-12 Generate Tools Exp $
 */
@Repository
public class JyAnnunciateDaoImpl extends AbstractDAO<JyAnnunciate, Integer> implements JyAnnunciateDao {

	/**
	 * 查阅通知公告，暂时不考虑区域
	 * 
	 * @param vo
	 * @return
	 * @see com.tmser.tr.annunciate.dao.JyAnnunciateDao#findPunishsByPage(com.tmser.tr.annunciate.vo.JyAnnunciateVo)
	 */
	@Override
	public PageList<JyAnnunciate> findPunishsByPage(JyAnnunciateVo vo) {
		String sql = "select * from JyAnnunciate j where j.orgId in(:orgId,-1) and j.status=:status and j.isDelete=:isDelete order by j.isTop desc, j.lastupDttm desc";
		// 是校领导可以查看区域和学校的
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("orgId", vo.getOrgId());
		argMap.put("status", vo.getStatus());
		argMap.put("isDelete", vo.getIsDelete());
		return this.queryPageByNamedSql(sql, argMap, this.mapper, vo.getPage());
	}

	/**
	 * 获取上一篇通知公告
	 * 
	 * @param id
	 * @param orgid
	 * @param status
	 * @return
	 * @see com.tmser.tr.annunciate.dao.JyAnnunciateDao#getPreAnnunciate(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public JyAnnunciate getPreAnnunciate(JyAnnunciate jAnnunciate, Integer type) {
		// TODO Auto-generated method stub
		String sql;
		if (jAnnunciate.getStatus() == 0) {
			sql = "select * from JyAnnunciate ja where ja.lastupDttm>(select ja.lastupDttm from JyAnnunciate ja where ja.id=:id) and ja.status=:status and ja.orgId=:orgId and ja.isDelete=0 and ja.userId=:userId order by ja.isTop desc, ja.lastupDttm";
		} else {
			if (type == 0) {
				sql = "select * from JyAnnunciate ja where ja.lastupDttm>(select ja.lastupDttm from JyAnnunciate ja where ja.id=:id) and ja.status=:status and ja.orgId=:orgId and ja.isDelete=0 order by ja.isTop desc, ja.lastupDttm";
			} else if (type == 1) {
				sql = "select * from JyAnnunciate ja where ja.lastupDttm>(select ja.lastupDttm from JyAnnunciate ja where ja.id=:id) and ja.status=:status and ja.orgId in(:orgId,-1) and ja.isDelete=0 order by ja.isTop desc, ja.lastupDttm";
			} else {
				sql = "select * from JyAnnunciate ja where ja.lastupDttm>(select ja.lastupDttm from JyAnnunciate ja where ja.id=:id) and ja.status=:status and ja.orgId in(:orgId,-1) and ja.isDelete=0 and ja.isDisplay=1 order by ja.isTop desc, ja.lastupDttm";
			}
		}
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("id", jAnnunciate.getId());
		argMap.put("orgId", jAnnunciate.getOrgId());
		argMap.put("status", jAnnunciate.getStatus());
		argMap.put("userId", jAnnunciate.getUserId());
		return this.queryByNamedSqlForSingle(sql, argMap, this.mapper);
	}

	/**
	 * 获取下一篇通知公告
	 * 
	 * @param id
	 * @param orgid
	 * @param status
	 * @return
	 * @see com.tmser.tr.annunciate.dao.JyAnnunciateDao#getNextAnnunciate(java.lang.Integer,
	 *      java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public JyAnnunciate getNextAnnunciate(JyAnnunciate jAnnunciate, Integer type) {
		// TODO Auto-generated method stub
		String sql;
		if (jAnnunciate.getStatus() == 0) {
			sql = "select * from JyAnnunciate ja where ja.lastupDttm<(select ja.lastupDttm from JyAnnunciate ja where ja.id=:id) and ja.status=:status and ja.orgId=:orgId and ja.userId=:userId and ja.isDelete=0 order by ja.isTop desc, ja.lastupDttm desc";
		} else {
			if (type == 0) {
				sql = "select * from JyAnnunciate ja where ja.lastupDttm<(select ja.lastupDttm from JyAnnunciate ja where ja.id=:id) and ja.status=:status and ja.orgId=:orgId and ja.isDelete=0 order by ja.isTop desc, ja.lastupDttm desc";
			} else if (type == 1) {
				sql = "select * from JyAnnunciate ja where ja.lastupDttm<(select ja.lastupDttm from JyAnnunciate ja where ja.id=:id) and ja.status=:status and ja.orgId in(:orgId,-1) and ja.isDelete=0 order by ja.isTop desc, ja.lastupDttm desc";
			} else {
				sql = "select * from JyAnnunciate ja where ja.lastupDttm<(select ja.lastupDttm from JyAnnunciate ja where ja.id=:id) and ja.status=:status and ja.orgId in(:orgId,-1) and ja.isDelete=0 and ja.isDisplay=1 order by ja.isTop desc, ja.lastupDttm desc";
			}
		}
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("id", jAnnunciate.getId());
		argMap.put("orgId", jAnnunciate.getOrgId());
		argMap.put("status", jAnnunciate.getStatus());
		argMap.put("userId", jAnnunciate.getUserId());
		return this.queryByNamedSqlForSingle(sql, argMap, this.mapper);
	}

	/**
	 * 查询已发布未被查看的通知公告集合
	 * 
	 * @return
	 * @see com.tmser.tr.annunciate.dao.JyAnnunciateDao#getNotReadJyList()
	 */
	@Override
	public List<JyAnnunciate> getNotReadJyList(Integer orgId, Integer userId) {
		// TODO Auto-generated method stub
		String sql = "select * from JyAnnunciate ja where ja.id not in (select distinct ap.annunciateId from AnnunciatePunishView ap where ap.userId=:userId) and ja.status=1 and ja.isDelete=0 and ja.orgId in(:orgId,-1) order by ja.isTop desc, ja.lastupDttm desc";
		Map<String, Object> argMap = new HashMap<String, Object>();
		argMap.put("orgId", orgId);
		argMap.put("userId", userId);
		return this.queryByNamedSqlWithLimit(sql, argMap, this.mapper, 2);
	}

}
