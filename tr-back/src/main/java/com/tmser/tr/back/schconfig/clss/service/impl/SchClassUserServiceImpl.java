/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.schconfig.clss.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.schconfig.clss.service.SchClassUserService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.schconfig.clss.bo.SchClassUser;
import com.tmser.tr.schconfig.clss.dao.SchClassUserDao;
import com.tmser.tr.schconfig.clss.vo.SchClassUserVo;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 学校班级用户关联 服务实现类
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchClassUser.java, v 1.0 2016-02-19 tmser Exp $
 */
@Service
@Transactional
public class SchClassUserServiceImpl extends AbstractService<SchClassUser, Integer> implements SchClassUserService {

	@Autowired
	private SchClassUserDao schClassUserDao;
	
	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchClassUser, Integer> getDAO() {
		return schClassUserDao;
	}

	/**
	 * @param vo
	 * @see com.tmser.tr.back.schconfig.clss.service.SchClassUserService#editSchClassUser(com.tmser.tr.schconfig.clss.vo.SchClassUserVo)
	 */
	@Override
	public void editSchClassUser(SchClassUserVo vo) {
		Integer schoolYear = (Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		
		SchClassUser model = new SchClassUser();
		model.setClassId(vo.getClsid());
		model.setSchoolYear(schoolYear);
		model.setEnable(SchClassUser.ENABLE);
		model.addCustomCulomn("id,classId,username,tchId,subjectId,type");
		model.buildCondition(" and (type = :tch or type = :master)")
		.put("tch", SchClassUser.T_TEACHER)
		.put("master", SchClassUser.T_MASTER);
		
		Map<Integer,SchClassUser> subjectUserMap = new HashMap<Integer,SchClassUser>();
		List<SchClassUser> culist = findAll(model);
		SchClassUser currentMaster = null;
		for(SchClassUser cu : culist){
			if(SchClassUser.T_TEACHER.equals(cu.getType())){
				subjectUserMap.put(cu.getSubjectId(), cu);
			}else if(SchClassUser.T_MASTER.equals(cu.getType())){
				currentMaster = cu;
			}
		}
		
		Date now = new Date();
		SchClassUser master = vo.getMaster();
		if(master != null && master.getTchId() != null){//班主任
			master.setClassId(vo.getClsid());
			master.setLastupDttm(now);
			master.setLastupId(user.getId());
			if(currentMaster != null){//有旧数据，更新
				if(!master.getTchId().equals(currentMaster.getTchId())){
					master.setId(currentMaster.getId());
					update(master);
				}
			}else{//没有旧数据插入
				master.setEnable(SchClassUser.ENABLE);
				master.setCrtDttm(now);
				master.setCrtId(user.getId());
				master.setType(SchClassUser.T_MASTER);
				master.setSchoolYear(schoolYear);
				save(master);
			}
		}else{
			if(currentMaster != null){//有数据，且没有传递新数据删除旧数据
				delete(currentMaster.getId());
			}
		}
		
		
		for(SchClassUser cu : vo.getUserList()){//任课教师
			if(cu.getTchId() != null && cu.getUsername() != null &&
					cu.getSubjectId() != null){
				cu.setClassId(vo.getClsid());
				cu.setLastupDttm(now);
				cu.setLastupId(user.getId());
				
				SchClassUser current = subjectUserMap.get(cu.getSubjectId());
				if(current != null){//有旧数据
					subjectUserMap.remove(cu.getSubjectId());
					if(cu.getTchId().equals(current.getTchId())){
						continue;
					}else{
						cu.setId(current.getId());
						update(cu);
					}
				}else{
					cu.setEnable(SchClassUser.ENABLE);
					cu.setCrtDttm(now);
					cu.setCrtId(user.getId());
					cu.setType(SchClassUser.T_TEACHER);
					cu.setSchoolYear(schoolYear);
					save(cu);
				}
			}
		}
		
		for(Integer key : subjectUserMap.keySet()){//删除无效的用户
			delete(subjectUserMap.get(key).getId());
		}
		
	}

}
