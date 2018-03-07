/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.service.impl;

import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.jxtx.service.JXTXGradeManageService;
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
 * @version $Id: JXTXGradeManageServiceImpl.java, v 1.0 2015年8月26日 上午10:32:14 wy
 *          Exp $
 */
@Service
@Transactional
public class JXTXGradeManageServiceImpl extends MetaRelationshipServiceImpl implements JXTXGradeManageService {
	
	/**
	 * 根据学段查询年级
	 * 
	 * @param eid
	 * @return
	 * @see com.tmser.tr.back.jxtx.service.JXTXGradeManageService#queryGradeByEid(java.lang.Integer)
	 */
	@Override
	public List<Meta> queryGradeByEid(Integer eid) {
		return MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(eid);
	}

	/**
	 * 根据ID查询未添加的年级
	 * 
	 * @param id
	 * @return
	 * @see com.tmser.tr.back.jxtx.service.JXTXGradeManageService#queryIMeta(java.lang.Integer)
	 */
	@Override
	public List<Meta> queryMeta(Integer eid) {
		// 找出已添加的
		List<Meta> idsAry = MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(eid);
		List<Meta> allMetaList = MetaUtils.getPhaseGradeMetaProvider().listAllGradeMeta();
		
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
	 * 保存未添加的年级
	 * 
	 * @param metas
	 * @see com.tmser.tr.back.jxtx.service.JXTXGradeManageService#savePhaseMate(com.tmser.tr.common.vo.Datas)
	 */
	@Override
	public void saveGradeMate(Integer[] ids, Integer phaseId) {
		if(ids == null || ids.length == 0){
			return;
		}
		
		MetaRelationship meta = MetaUtils.getPhaseGradeMetaProvider().getMetaRelationshipByPhaseId(phaseId);
		StringBuilder ides = new StringBuilder();
		StringBuilder descs = new StringBuilder();
		if(meta == null){
			meta = new MetaRelationship();
			meta.setEid(phaseId);
			meta.setType(MetaRelationship.T_XD_NJ);
		}else{
			ides.append(StringUtils.isNotBlank(meta.getIds()) ? meta.getIds()+StringUtils.COMMA : "");
			descs.append(StringUtils.isNotBlank(meta.getDescs()) ? meta.getDescs() + StringUtils.COMMA: "");
		}
		
		for (Integer str : ids) {
			if (str != null) {
				ides.append(str).append(StringUtils.COMMA);
				descs.append(MetaUtils.getPhaseSubjectMetaProvider().getMeta(str).getName()).append(StringUtils.COMMA);
			}
		}
		meta.setIds(ides.length() > 0 ? ides.substring(0,ides.length()-1) : ides.toString());
		meta.setDescs(descs.length() > 0 ? descs.substring(0,descs.length()-1) : descs.toString());
		
		if (meta.getId() != null) {
			update(meta);
			MetaUtils.getPhaseGradeMetaProvider().evictCache(meta.getId());
			LoggerUtils.updateLogger(LoggerModule.JXTX, "组织机构管理——修改年级，元数据id：" + meta.getId());
		} else {
			MetaRelationship insertOne = save(meta);
			LoggerUtils.insertLogger(LoggerModule.JXTX, "组织机构管理——增加年级，元数据id：" + insertOne.getId());
		}
		FrontCacheUtils.clear(MetaConstants.META_CACHE_NAME);
	}

	/**
	 * 删除已添加的年级
	 * 
	 * @param metaship
	 * @see com.tmser.tr.back.jxtx.service.JXTXGradeManageService#deleteById(com.tmser.tr.manage.meta.bo.MetaRelationship)
	 */
	@Override
	public void deleteById(Integer phaseId, Integer njid) {
		if (phaseId != null && njid != null) {
			MetaRelationship meta = MetaUtils.getPhaseGradeMetaProvider().getMetaRelationshipByPhaseId(phaseId);
			StringBuilder ids = new StringBuilder(StringUtils.COMMA).append(meta.getIds()).append(StringUtils.COMMA);
			StringBuilder descs = new StringBuilder(StringUtils.COMMA).append(meta.getDescs()).append(StringUtils.COMMA);
			Meta deleteMeta = MetaUtils.getMeta(njid);
			String deleteId = StringUtils.COMMA+njid+StringUtils.COMMA;
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
			MetaUtils.getPhaseGradeMetaProvider().evictCache(meta.getId());
			FrontCacheUtils.clear(MetaConstants.META_CACHE_NAME);
			LoggerUtils.updateLogger(LoggerModule.JXTX, "组织机构管理——删除已添加的年级，元数据id：" + meta.getId());
		}

	}
	
	/**
	 * @param gradeIds
	 * @param eid
	 * @return
	 * @see com.tmser.tr.back.jxtx.service.JXTXGradeManageService#sortGradeMate(java.util.Set,
	 *      java.lang.Integer)
	 */
	@Override
	public boolean sortGradeMate(List<Integer> gradeIds, String gradeNames, Integer phaseId) {
		boolean rs = false;
		MetaRelationship oldMeta = MetaUtils.getPhaseGradeMetaProvider().getMetaRelationshipByPhaseId(phaseId);
		if (oldMeta != null) {
			String oldIds = oldMeta.getIds();
			oldMeta.setIds(gradeIds);
			oldMeta.setDescs(gradeNames);
			if (oldIds != null && !oldIds.equals(oldMeta.getIds())) {
				rs = update(oldMeta) > 0;
				MetaUtils.getPhaseGradeMetaProvider().evictCache(oldMeta.getId());
				FrontCacheUtils.clear(MetaConstants.META_CACHE_NAME);
				LoggerUtils.updateLogger(LoggerModule.JXTX, "组织机构管理——已添加的年级排序，元数据id：" + oldMeta.getId());
			}
		}
		return rs;
	}

}
