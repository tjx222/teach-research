/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.activity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.activity.bo.SchoolTeachCircle;
import com.tmser.tr.activity.bo.SchoolTeachCircleOrg;
import com.tmser.tr.activity.dao.SchoolTeachCircleDao;
import com.tmser.tr.activity.dao.SchoolTeachCircleOrgDao;
import com.tmser.tr.activity.service.SchoolTeachCircleService;
import com.tmser.tr.activity.vo.SchoolTeachCircleVo;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.notice.bo.JyNotice;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.dao.JyNoticeDao;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyCollectionUtils;
import com.tmser.tr.utils.StringUtils;

/**
 * 校际教研圈 服务实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SchoolTeachCircle.java, v 1.0 2015-05-12 Generate Tools Exp $
 */
@Service
@Transactional
public class SchoolTeachCircleServiceImpl extends AbstractService<SchoolTeachCircle, Integer> implements SchoolTeachCircleService {

	@Autowired
	private SchoolTeachCircleDao schoolTeachCircleDao;
	@Autowired
	private SchoolTeachCircleOrgDao schoolTeachCircleOrgDao;
	@Autowired
	private OrganizationDao organizationDao;
	@Autowired
	private UserSpaceDao userSpaceDao;
	@Autowired
	private JyNoticeDao jyNoticeDao;

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<SchoolTeachCircle, Integer> getDAO() {
		return schoolTeachCircleDao;
	}

	/**
	 * 保存校际教研圈
	 * 
	 * @param stc
	 * @param circleOrgs
	 * @see com.tmser.tr.schoolactivity.service.SchoolTeachCircleService#saveCircle(com.tmser.tr.schoolactivity.bo.SchoolTeachCircle,
	 *      java.lang.String)
	 */
	@Override
	public String saveCircle(SchoolTeachCircle stc, String circleOrgs) {
		if (stc != null) {
			UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
			Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);// 学年
			List<Integer> orgIds = new ArrayList<Integer>();
			if (stc.getId() != null) {// 修改教研圈
				stc.setLastupDttm(new Date());
				schoolTeachCircleDao.update(stc);
				stc = schoolTeachCircleDao.get(stc.getId());

				String[] orgStrs = circleOrgs.split(",");
				int i = 1;
				List<Integer> tempList = new ArrayList<Integer>();
				tempList.add(stc.getOrgId());
				for (String orgStr : orgStrs) {
					String[] orgDatas = orgStr.split("@");
					if (StringUtils.isNotEmpty(orgDatas[0])) {
						SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
						stco.setStcId(stc.getId());
						stco.setOrgId(Integer.parseInt(orgDatas[0]));
						SchoolTeachCircleOrg schoolTeachCircleOrg = schoolTeachCircleOrgDao.getOne(stco);
						if (schoolTeachCircleOrg != null) {
							if (schoolTeachCircleOrg.getOrgId().intValue() != stc.getOrgId().intValue()) {
								schoolTeachCircleOrg.setSort(i);
								schoolTeachCircleOrgDao.update(schoolTeachCircleOrg);
								tempList.add(Integer.parseInt(orgDatas[0]));
							}
						} else {
							stco.setOrgName(orgDatas[1]);
							stco.setState(Integer.parseInt(orgDatas[2]));
							stco.setSort(i);
							stco.setSchoolYear(schoolYear);
							schoolTeachCircleOrgDao.insert(stco);
							tempList.add(Integer.parseInt(orgDatas[0]));
							orgIds.add(Integer.parseInt(orgDatas[0]));
						}
						i++;
					}
				}
				// 删除多余的学校机构
				SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
				stco.setStcId(stc.getId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orgids", tempList);
				stco.addCustomCondition("and orgId not in (:orgids)", map);
				List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
				for (SchoolTeachCircleOrg stcoTemp : listAll) {
					schoolTeachCircleOrgDao.delete(stcoTemp.getId());
				}
			} else {// 创建教研圈
				stc.setOrgId(userSpace.getOrgId());
				stc.setSchoolYear(schoolYear);
				stc.setCrtDttm(new Date());
				stc.setCrtId(userSpace.getUserId());
				stc.setLastupDttm(new Date());
				stc.setIsDelete(false);

				Organization organization = organizationDao.get(userSpace.getOrgId());
				if (organization != null) {
					stc.setAreaIds(organization.getAreaIds());
				}

				stc = schoolTeachCircleDao.insert(stc);
				String[] orgStrs = circleOrgs.split(",");
				int i = 1;
				for (String orgStr : orgStrs) {
					String[] orgDatas = orgStr.split("@");
					SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
					String orgId = orgDatas[0];
					if (StringUtils.isNotEmpty(orgId)) {
						stco.setStcId(stc.getId());
						stco.setOrgId(Integer.parseInt(orgId));
						stco.setOrgName(orgDatas[1]);
						stco.setState(Integer.parseInt(orgDatas[2]));
						stco.setSort(i);
						stco.setSchoolYear(schoolYear);
						schoolTeachCircleOrgDao.insert(stco);
						i++;
						orgIds.add(Integer.parseInt(orgId));
					}
				}
				// 添加本机构数据
				SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
				stco.setStcId(stc.getId());
				stco.setOrgId(stc.getOrgId());
				if (organization != null) {
					stco.setOrgName(organization.getName());
				}
				stco.setState(SchoolTeachCircleOrg.YI_TONG_YI);
				stco.setSort(0);
				stco.setSchoolYear(schoolYear);
				schoolTeachCircleOrgDao.insert(stco);
			}
			// 添加消息通知
			addMessageNotice(orgIds, stc);
		}
		return "ok";
	}

	/**
	 * 发送消息通知，邀请加入校际教研圈
	 * 
	 * @param orgStrs
	 * @param stc
	 */
	private void addMessageNotice(List<Integer> orgIds, SchoolTeachCircle stc) {
		if (orgIds != null && orgIds.size() > 0 && stc != null) {
			List<Integer> sysRoleIds = new ArrayList<Integer>();
			sysRoleIds.add(SysRole.XZ.getId().intValue());// 校长
			sysRoleIds.add(SysRole.FXZ.getId().intValue());//副校长
			sysRoleIds.add(SysRole.ZR.getId().intValue());// 主任
			UserSpace us = new UserSpace();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orgIds", orgIds);
			paramMap.put("sysRoleIds", sysRoleIds);
			us.addCustomCondition(" and orgId in (:orgIds) and sysRoleId in (:sysRoleIds)", paramMap);
			us.addCustomCulomn(" distinct(userId) ");
			List<UserSpace> listAll = userSpaceDao.listAll(us);
			if (listAll != null && listAll.size() > 0) {
				UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
				Map<String, Object> noticeInfo = new HashMap<String, Object>();
				String title = "邀请加入校际教研圈";
				noticeInfo.put("stcId", stc.getId());
				noticeInfo.put("spaceId", userSpace.getId());
				for (UserSpace usTemp : listAll) {
					NoticeUtils.sendNotice(NoticeType.SCHOOL_ACTIVITY_YQ, title, userSpace.getUserId(), usTemp.getUserId(), noticeInfo);
				}
			}
		}
	}

	/**
	 * 查询校际教研圈
	 * 
	 * @return
	 * @see com.tmser.tr.schoolactivity.service.SchoolTeachCircleService#findAllCircleByOrg()
	 */
	@Override
	public List<SchoolTeachCircle> findAllCircleByOrg() {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); // 学年
		Integer sysRoleId = userSpace.getSysRoleId();

		SchoolTeachCircle stc = new SchoolTeachCircle();
		if (sysRoleId.intValue() == SysRole.XZ.getId().intValue() || sysRoleId.intValue() == SysRole.ZR.getId().intValue()||sysRoleId.intValue() == SysRole.FXZ.getId().intValue()) {
			// 校长或者主任
			SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
			stco.setOrgId(userSpace.getOrgId());
			stco.setSchoolYear(schoolYear);
			stco.addCustomCulomn("stcId");
			stco.addGroup("stcId");
			stco.addCustomCondition(" and state>1 and state!=3 ", new HashMap<String, Object>());// 可以查看的
			List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
			if (listAll != null && listAll.size() > 0) {
				List<Integer> tempList = new ArrayList<Integer>();
				for (SchoolTeachCircleOrg stcoT : listAll) {
					tempList.add(stcoT.getStcId());
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("stcids", tempList);
				stc.addCustomCondition("and id in (:stcids)", map);
				stc.addOrder("lastupDttm desc");
			} else {
				stc.setId(-1);
			}
		} else {
			stc.setId(-1);
		}
		List<SchoolTeachCircle> stcList = schoolTeachCircleDao.listAll(stc);
		if (stcList != null && stcList.size() > 0) {
			for (SchoolTeachCircle stcTemp : stcList) {
				SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
				stco.setStcId(stcTemp.getId());
				stco.addOrder(" sort asc ");
				List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
				stcTemp.setStcoList(listAll);
			}
		}
		return stcList;
	}

	/**
	 * 删除校际教研圈
	 * 
	 * @param stc
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleService#deleteCircle(com.tmser.tr.activity.bo.SchoolTeachCircle)
	 */
	@Override
	public void deleteCircle(SchoolTeachCircle stc) {
		SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
		stco.setStcId(stc.getId());
		List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
		for (SchoolTeachCircleOrg stcoTemp : listAll) {
			schoolTeachCircleOrgDao.delete(stcoTemp.getId());
		}
		schoolTeachCircleDao.delete(stc.getId());
	}

	/**
	 * 退出或者恢复校际教研圈
	 * 
	 * @param stc
	 * @param i
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleService#setCircleOrgState(com.tmser.tr.activity.bo.SchoolTeachCircle,
	 *      int)
	 */
	@Override
	public void setCircleOrgState(SchoolTeachCircle stc, int state) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
		stc = schoolTeachCircleDao.get(stc.getId());
		if (stc != null) {
			stco.setStcId(stc.getId());
			stco.setOrgId(userSpace.getOrgId());
			stco = schoolTeachCircleOrgDao.getOne(stco);
			if (stco != null) {
				stco.setState(state);
				stco.setLastupId(userSpace.getUserId());
				stco.setLastupDttm(new Date());
				schoolTeachCircleOrgDao.update(stco);
				if (stc.getOrgId().intValue() != userSpace.getOrgId().intValue()) {// 退出自己创建的，不发消息
					sendXXTZ(state, stc.getId());
				}
			}
		}

	}

	/**
	 * 判断教研圈名称是否重复
	 * 
	 * @param name
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleService#checkCircleName()
	 */
	@Override
	public String checkCircleName(SchoolTeachCircle stc) {
		if (stc.getId() != null) {
			SchoolTeachCircle stcOld = schoolTeachCircleDao.get(stc.getId());
			if (stc.getName().equals(stcOld.getName())) {
				return "ok";
			}
		}
		UserSpace us = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
		SchoolTeachCircle stcTemp = new SchoolTeachCircle();
		stcTemp.setCrtId(us.getUserId());
		stcTemp.setSchoolYear(schoolYear);
		stcTemp.setName(stc.getName());
		SchoolTeachCircle one = schoolTeachCircleDao.getOne(stcTemp);
		if (one != null) {
			return "yicunzai";
		} else {
			return "ok";
		}
	}

	/**
	 * 查看
	 * 
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleService#findCircleByOrg()
	 */
	@Override
	public List<SchoolTeachCircle> findCircleByOrg() {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); // 学年
		Integer sysRoleId = userSpace.getSysRoleId();
		SchoolTeachCircle stc = new SchoolTeachCircle();
		if (sysRoleId.intValue() == SysRole.JYY.getId().intValue() || sysRoleId.intValue() == SysRole.JYZR.getId().intValue()) {
			// 教研员和教研主任
			Organization org = organizationDao.get(userSpace.getOrgId());
			stc.setAreaIds(SqlMapping.LIKE_PRFIX + "," + org.getAreaId() + "," + SqlMapping.LIKE_PRFIX);
			stc.setSchoolYear(schoolYear);
			stc.addOrder("lastupDttm desc");
		} else {
			SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
			stco.setOrgId(userSpace.getOrgId());
			stco.setSchoolYear(schoolYear);
			stco.addCustomCulomn("distinct(stcId)");
			stco.addCustomCondition(" and state in (" + SchoolTeachCircleOrg.YI_TONG_YI + "," + SchoolTeachCircleOrg.YI_HUI_FU + ")", new HashMap<String, Object>());
			List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
			if (listAll != null && listAll.size() > 0) {
				List<Integer> tempList = new ArrayList<Integer>();
				for (SchoolTeachCircleOrg stcoT : listAll) {
					tempList.add(stcoT.getStcId());
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("stcids", tempList);
				stc.addCustomCondition("and id in (:stcids)", map);
				stc.addOrder("lastupDttm desc");
			} else {
				stc.setId(-1);
			}
		}
		List<SchoolTeachCircle> stcList = schoolTeachCircleDao.listAll(stc);
		if (stcList != null && stcList.size() > 0) {
			for (SchoolTeachCircle stct : stcList) {
				SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
				stco.setStcId(stct.getId());
				stco.addCustomCondition(" and state in (" + SchoolTeachCircleOrg.DAI_JIE_SHOU + "," + SchoolTeachCircleOrg.YI_TONG_YI + "," + SchoolTeachCircleOrg.YI_HUI_FU + ")",
						new HashMap<String, Object>());
				List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
				stct.setStcoList(listAll);
			}
		}
		return stcList;
	}

	/**
	 * 校际教研圈邀请回应操作
	 * 
	 * @param state
	 * @param stcId
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleService#saveYaoQing(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public Boolean saveYaoQing(Integer state, Integer stcId, Long noticeId, String content) {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
		stco.setOrgId(userSpace.getOrgId());
		stco.setStcId(stcId);
		SchoolTeachCircleOrg one = schoolTeachCircleOrgDao.getOne(stco);
		one.setState(state);
		one.setLastupDttm(new Date());
		schoolTeachCircleOrgDao.update(one);
		if (state.intValue() == SchoolTeachCircleOrg.YI_TONG_YI) {
			SchoolTeachCircle schoolTeachCircle = schoolTeachCircleDao.get(stcId);
			if (!schoolTeachCircle.getIsDelete()) {
				schoolTeachCircle.setIsDelete(true);
				schoolTeachCircleDao.update(schoolTeachCircle);
			}
		}
		// 修改消息内容
		JyNotice jyNotice = jyNoticeDao.get(noticeId);
		if (jyNotice != null) {
			jyNotice.setDetail(content);
			jyNotice.setLastupDttm(new Date());
			jyNoticeDao.update(jyNotice);
		}
		// 发送消息通知
		sendXXTZ(state, stcId);
		return true;
	}

	/**
	 * 给教研圈创建者发送状态消息
	 * 
	 * @param state
	 * @param stcId
	 */
	private void sendXXTZ(Integer state, Integer stcId) {
		if (state != null && stcId != null) {
			SchoolTeachCircle stc = schoolTeachCircleDao.get(stcId);
			if (stc != null) {
				UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
				Map<String, Object> noticeInfo = new HashMap<String, Object>();
				String stateStr = "待接受";
				if (state.intValue() == SchoolTeachCircleOrg.YI_TONG_YI.intValue()) {
					stateStr = "已加入";
				} else if (state.intValue() == SchoolTeachCircleOrg.YI_JU_JUE.intValue()) {
					stateStr = "已拒绝加入";
				} else if (state.intValue() == SchoolTeachCircleOrg.YI_TUI_CHU.intValue()) {
					stateStr = "已退出";
				} else if (state.intValue() == SchoolTeachCircleOrg.YI_HUI_FU.intValue()) {
					stateStr = "已恢复加入";
				}
				UserSpace us = new UserSpace();
				us.setUserId(userSpace.getUserId());
				us.setSysRoleId(SysRole.XZ.getId());// 校长
				UserSpace usTemp = userSpaceDao.getOne(us);
				String roleName = "校长";
				if (usTemp == null) {
					roleName = "主任";
				}
				Organization organization = organizationDao.get(userSpace.getOrgId());
				if (organization != null) {
					String title = organization.getName() + stateStr + "“" + stc.getName() + "”校际教研圈";
					String content = organization.getAreaName() + organization.getName() + roleName + "“" + userSpace.getUsername() + "”" + stateStr + "您创建的“" + stc.getName() + "”校际教研圈！";
					noticeInfo.put("type", 1);
					noticeInfo.put("content", content);
					NoticeUtils.sendNotice(NoticeType.SCHOOL_ACTIVITY_TZ, title, userSpace.getUserId(), stc.getCrtId(), noticeInfo);
				}

			}

		}
	}

	/**
	 * 查看校际教研圈
	 * 
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleService#lookTeachCircle()
	 */
	@Override
	public List<SchoolTeachCircle> lookTeachCircle() {
		UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
		Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR); // 学年
		Integer sysRoleId = userSpace.getSysRoleId();
		SchoolTeachCircle stc = new SchoolTeachCircle();
		if (sysRoleId.intValue() == SysRole.JYY.getId().intValue() || sysRoleId.intValue() == SysRole.JYZR.getId().intValue()) {
			// 教研员和教研主任
			Organization org = organizationDao.get(userSpace.getOrgId());
			stc.setAreaIds(SqlMapping.LIKE_PRFIX + "," + org.getAreaId() + "," + SqlMapping.LIKE_PRFIX);
			stc.setSchoolYear(schoolYear);
			stc.addOrder("lastupDttm desc");
		} else {
			SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
			stco.setOrgId(userSpace.getOrgId());
			stco.setSchoolYear(schoolYear);
			stco.addCustomCulomn("distinct(stcId)");
			stco.addCustomCondition(" and state in (" + SchoolTeachCircleOrg.YI_TONG_YI + "," + SchoolTeachCircleOrg.YI_HUI_FU + ")", new HashMap<String, Object>());
			List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
			if (listAll != null && listAll.size() > 0) {
				List<Integer> tempList = new ArrayList<Integer>();
				for (SchoolTeachCircleOrg stcoT : listAll) {
					tempList.add(stcoT.getStcId());
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("stcids", tempList);
				stc.addCustomCondition("and id in (:stcids)", map);
				stc.addOrder("lastupDttm desc");
			} else {
				stc.setId(-1);
			}
		}
		List<SchoolTeachCircle> stcList = schoolTeachCircleDao.listAll(stc);
		if (stcList != null && stcList.size() > 0) {
			for (SchoolTeachCircle stct : stcList) {
				SchoolTeachCircleOrg stco = new SchoolTeachCircleOrg();
				stco.setStcId(stct.getId());
				stco.addOrder(" sort asc ");
				List<SchoolTeachCircleOrg> listAll = schoolTeachCircleOrgDao.listAll(stco);
				stct.setStcoList(listAll);
			}
		}
		return stcList;
	}

	/**
	 * 查询当前学校参加的教研圈信息
	 * 
	 * @return
	 * @see com.tmser.tr.activity.service.SchoolTeachCircleService#getCurrentSchoolJoinCicles()
	 */
	@Override
	public List<SchoolTeachCircleVo> getCurrentSchoolJoinCicles() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SchoolTeachCircleOrg search = new SchoolTeachCircleOrg();
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		search.setOrgId(user.getOrgId());
		List<Integer> states = new ArrayList<Integer>();
		states.add(SchoolTeachCircleOrg.YI_TONG_YI);
		states.add(SchoolTeachCircleOrg.YI_HUI_FU);
		paramMap.put("states", states);
		search.addCustomCondition(" and state in (:states)", paramMap);
		List<SchoolTeachCircleOrg> orgCircles = schoolTeachCircleOrgDao.listAll(search);
		if (CollectionUtils.isEmpty(orgCircles)) {
			return new ArrayList<SchoolTeachCircleVo>();
		}
		List<Integer> techIds = JyCollectionUtils.getValues(orgCircles, "stcId");
		SchoolTeachCircle circleSearch = new SchoolTeachCircle();
		circleSearch.setSchoolYear((Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
		paramMap.put("ids", techIds);
		circleSearch.addCustomCondition(" and id in (:ids) ", paramMap);
		List<SchoolTeachCircle> circles = schoolTeachCircleDao.listAll(circleSearch);
		List<SchoolTeachCircleVo> vos = JyCollectionUtils.convertList(circles, SchoolTeachCircleVo.class);
		// 填充对应机构的信息
		fillOrgs(vos);
		return vos;
	}

	/**
	 * 填充对应机构的信息
	 * 
	 * @param vos
	 */
	private void fillOrgs(List<SchoolTeachCircleVo> vos) {
		if (CollectionUtils.isEmpty(vos)) {
			return;
		}
		SchoolTeachCircleOrg search = new SchoolTeachCircleOrg();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Integer> states = new ArrayList<Integer>();
		states.add(SchoolTeachCircleOrg.YI_TONG_YI);
		states.add(SchoolTeachCircleOrg.YI_HUI_FU);
		paramMap.put("states", states);
		search.addCustomCondition(" and state in (:states)", paramMap);
		for (SchoolTeachCircleVo vo : vos) {
			search.setStcId(vo.getId());
			List<SchoolTeachCircleOrg> orgs = schoolTeachCircleOrgDao.listAll(search);
			vo.setOrgs(orgs);
		}
	}

}
