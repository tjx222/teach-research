/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.service.impl;

import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.jxtx.service.JXTXSubjectManageService;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.utils.FrontCacheUtils;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaConstants;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.service.impl.MetaRelationshipServiceImpl;
import com.tmser.tr.utils.StringUtils;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author wy
 * @version $Id: JXTXSubjectManageServiceImpl.java, v 1.0 2015年8月28日 下午5:19:47
 *          wy Exp $
 */
@Service
@Transactional
public class JXTXSubjectManageServiceImpl extends MetaRelationshipServiceImpl implements JXTXSubjectManageService {

	/**
	 * 根据学段查询学科
	 * 
	 * @param eid
	 * @return
	 * @see com.tmser.tr.back.jxtx.service.JXTXSubjectManageService#querySubjectByEid(java.lang.Integer)
	 */
	@Override
	public List<Meta> querySubjectByEid(Integer eid) {
		return MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectByPhaseId(eid);
	}


	/**
	 * 根据ID查询未添加的学科
	 * 
	 * @param id
	 * @return
	 * @see com.tmser.tr.back.jxtx.service.JXTXSubjectManageService#queryIMeta(java.lang.Integer)
	 */
	@Override
	public List<Meta> queryMeta(Integer eid) {
		List<Meta> idsAry = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectByPhaseId(eid);
		List<Meta> allMetaList = MetaUtils.getPhaseSubjectMetaProvider().listAllSubjectMeta();
		
		if (idsAry != null) {
			Iterator<Meta> idsit = idsAry.iterator();
			while(idsit.hasNext()){
				Meta m = idsit.next();
				allMetaList.remove(m);
			}
		}
		return allMetaList;
	}

	/**
	 * 保存未添加的学科
	 * 
	 * @param metas
	 * @see com.tmser.tr.back.jxtx.service.JXTXSubjectManageService#savePhaseMate(com.tmser.tr.common.vo.Datas)
	 */
	@Override
	public void saveSubjectMate(Integer[] ids, Integer phaseId) {
		if(ids == null || ids.length == 0){
			return;
		}
		
		MetaRelationship meta = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByPhaseId(phaseId);
		StringBuilder ides = new StringBuilder();
		StringBuilder descs = new StringBuilder();
		if(meta == null){
			meta = new MetaRelationship();
			meta.setEid(phaseId);
			meta.setType(MetaRelationship.T_XD_XK);
		}else{
			ides.append(StringUtils.isNotBlank(meta.getIds()) ? meta.getIds()+StringUtils.COMMA : "");
			descs.append(StringUtils.isNotBlank(meta.getDescs()) ? meta.getDescs() + StringUtils.COMMA: "");
		}
		
		for (Integer str : ids) {
			if (str != null) {
				ides.append(str).append(StringUtils.COMMA);
				descs.append(MetaUtils.getMeta(str).getName()).append(StringUtils.COMMA);
			}
		}
		meta.setIds(ides.length() > 0 ? ides.substring(0,ides.length()-1) : ides.toString());
		meta.setDescs(descs.length() > 0 ? descs.substring(0,descs.length()-1) : descs.toString());
		
		if (meta.getId() != null) {
			update(meta);
			MetaUtils.getPhaseSubjectMetaProvider().evictCache(meta.getId());
			FrontCacheUtils.clear(MetaConstants.META_CACHE_NAME);
			LoggerUtils.updateLogger(LoggerModule.JXTX, "组织机构管理——修改学科，元数据id：" + meta.getId());
		} else {
			MetaRelationship insertOne = save(meta);
			LoggerUtils.insertLogger(LoggerModule.JXTX, "组织机构管理——增加学科，元数据id：" + insertOne.getId());
		}
	}

	/**
	 * 删除已添加的学科
	 * 
	 * @param metaship
	 * @see com.tmser.tr.back.jxtx.service.JXTXSubjectManageService#deleteById(com.tmser.tr.manage.meta.bo.MetaRelationship)
	 */
	@Override
	public void deleteById(Integer phaseId, Integer xkid) {
		if (phaseId != null && xkid != null) {
			MetaRelationship meta = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByPhaseId(phaseId);
			StringBuilder ids = new StringBuilder(StringUtils.COMMA).append(meta.getIds()).append(StringUtils.COMMA);
			StringBuilder descs = new StringBuilder(StringUtils.COMMA).append(meta.getDescs()).append(StringUtils.COMMA);
			Meta deleteMeta = MetaUtils.getMeta(xkid);
			String deleteId = StringUtils.COMMA+xkid+StringUtils.COMMA;
			String deleteName = StringUtils.COMMA+deleteMeta.getName()+StringUtils.COMMA;
			
			int start = ids.indexOf(deleteId);
			if(start != -1)
				ids.delete(start+1, start+deleteId.length());
			
			start = descs.indexOf(deleteName);
			if(start != -1)
				descs.delete(start+1, start+deleteName.length());
			
			meta.setIds(ids.length() > 0 ? ids.substring(1, ids.length()-1).toString() : ids.toString());
			meta.setDescs(descs.length() > 0 ? descs.substring(1, descs.length()-1).toString() : descs.toString());
			update(meta);
			MetaUtils.getPhaseSubjectMetaProvider().evictCache(meta.getId());
			FrontCacheUtils.clear(MetaConstants.META_CACHE_NAME);
			LoggerUtils.updateLogger(LoggerModule.JXTX, "组织机构管理——删除已添加的学科，元数据id：" + meta.getId());
		}

	}

	/**
	 * 排序
	 * 
	 * @param subjectIds
	 * @param descs
	 * @param eid
	 * @return
	 * @see com.tmser.tr.back.jxtx.service.JXTXSubjectManageService#sortGradeMate(java.util.List,
	 *      java.lang.String, java.lang.Integer)
	 */
	@Override
	public boolean sortSubjectMate(List<Integer> subjectIds, String descs, Integer eid) {
		boolean rs = false;
		MetaRelationship oldMeta = MetaUtils.getPhaseSubjectMetaProvider().getMetaRelationshipByPhaseId(eid);
		if (oldMeta != null) {
			String oldIds = oldMeta.getIds();
			oldMeta.setIds(subjectIds);
			oldMeta.setDescs(descs);
			if (oldIds != null && !oldIds.equals(oldMeta.getIds())) {
				rs = update(oldMeta) > 0;
				MetaUtils.getPhaseSubjectMetaProvider().evictCache(oldMeta.getId());
				FrontCacheUtils.clear(MetaConstants.META_CACHE_NAME);
				LoggerUtils.updateLogger(LoggerModule.JXTX, "组织机构管理——已添加的学科排序，元数据id：" + oldMeta.getId());
			}
		}
		return rs;
	}

}
