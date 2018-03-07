package com.tmser.tr.back.solution.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.solution.service.OrgSolutionService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.dao.MenuDao;
import com.tmser.tr.uc.bo.RoleMenu;
import com.tmser.tr.uc.bo.SysOrgSolution;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.RoleMenuDao;
import com.tmser.tr.uc.dao.SysOrgSolutionDao;
import com.tmser.tr.uc.dao.UserMenuDao;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.utils.SessionKey;

@Service
@Transactional
public class OrgSolutionServiceImpl extends AbstractService<SysOrgSolution, Integer> implements OrgSolutionService {

	@Autowired
	private SysOrgSolutionDao orgSolutionDao;

	@Autowired
	private UserSpaceDao userspaceDao;

	@Override
	public BaseDAO<SysOrgSolution, Integer> getDAO() {
		return orgSolutionDao;
	}
	
	@Autowired
	private RoleMenuDao roleMenuDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private UserMenuDao userMenuDao;

	@Override
	public void editOrgSolution(String ids, Integer solId) {
		List<SysOrgSolution> list = new ArrayList<SysOrgSolution>();
		if (StringUtils.isNotBlank(ids)) {
			String[] orgIdArray = ids.split(",");
			SysOrgSolution model = new SysOrgSolution();
			model.setSolutionId(solId);
			List<SysOrgSolution> oldOrgs = findAll(model);
			add: for (String orgId : orgIdArray) {
				Integer orgid = null;
				try {
					orgid = Integer.valueOf(orgId);
				} catch (NumberFormatException e) {
					continue;
				}

				for (int i = 0; i < oldOrgs.size(); i++) {
					SysOrgSolution so = oldOrgs.get(i);
					if (so.getOrgId().equals(orgid)) {// 已经存在
						if (so.getIsDelete()) {
							SysOrgSolution sm = new SysOrgSolution();
							sm.setId(so.getId());
							sm.setIsDelete(false);
							update(sm);
							updateUserSolution(solId, orgid);
						} else {
							oldOrgs.remove(i);
						}
						continue add;
					}
				}

				SysOrgSolution os = new SysOrgSolution();
				os.setOrgId(orgid);
				os.setSolutionId(solId);
				os.setIsDelete(false);
				list.add(os);
				updateUserSolution(solId, orgid);
			}
			orgSolutionDao.batchInsert(list);// 新增范围
			for (SysOrgSolution so : oldOrgs) {// 删除多余的范围
				if (!so.getIsDelete()) {
					SysOrgSolution sm = new SysOrgSolution();
					sm.setId(so.getId());
					sm.setIsDelete(true);
					update(sm);
					updateUserSolution(0, so.getOrgId());
				}
			}
		}
	}

	@Override
	public void deleteSolutionFw(Integer orgId, Integer sid){
		SysOrgSolution osl = new SysOrgSolution();
		osl.setOrgId(orgId);
		osl.setSolutionId(sid);
		List<SysOrgSolution> fslist = findAll(osl);
		for (SysOrgSolution sysOrgSolution : fslist) {
			SysOrgSolution model = new SysOrgSolution();
			model.setId(sysOrgSolution.getId());
			model.setIsDelete(true);
			update(model);
			LoggerUtils.deleteLogger("销售方案应用范围[id={}]", sysOrgSolution.getId());
		}
		
		updateUserSolution(0, orgId);
	}
	/**
	 * @param solutionId
	 * @param orgId
	 * @see com.tmser.tr.back.solution.service.OrgSolutionService#updateUserSolution(java.lang.Integer,
	 *      java.lang.Integer)
	 */
	@Override
	public void updateUserSolution(Integer solutionId, Integer orgId) {
		userspaceDao.updateUserSolution(solutionId, orgId, (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
		
		//修正用户菜单
		RoleMenu rm = new RoleMenu();
		rm.addCustomCulomn("r.roleId, r.menuId");
		rm.setIsDel(0);
		rm.addAlias("r");
		rm.buildCondition(" and r.roleId in (select ur.id from Role ur where ur.solutionId = :solutionId and ur.isDel = :isDel)")
		  .put("solutionId", solutionId)
		  .put("isDel", false);
		List<RoleMenu> roleMenus = roleMenuDao.listAll(rm);//所有新方案菜单
		Map<Integer,Set<Integer>> roleMenuMap = new HashMap<Integer,Set<Integer>>();
		for(RoleMenu r : roleMenus){
			Set<Integer> menuSet = roleMenuMap.get(r.getRoleId());
			if(menuSet ==  null){
				menuSet = new HashSet<Integer>();
			}
			menuSet.add(r.getMenuId());
			
			roleMenuMap.put(r.getRoleId(), menuSet);
		}
		
		UserSpace uspModel = new UserSpace();
		uspModel.addCustomCulomn("distinct userId, roleId");
		uspModel.setOrgId(orgId);
		uspModel.setSchoolYear((Integer)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));
		uspModel.setEnable(UserSpace.ENABLE);
		List<UserSpace> userList = userspaceDao.listAll(uspModel);
		
		UserMenu um = new UserMenu();
		um.addAlias("um");
		um.addCustomCulomn("um.id,um.menuId,um.sysRoleId,um.userId");
		um.buildCondition(" and um.userId in (select distinct us.userId from UserSpace us where us.orgId = :orgId and "
				+ "us.schoolYear=:schoolYear and us.enable = :enable) ")
		  .put("orgId", orgId)
		  .put("schoolYear", WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR))
		  .put("enable", 1);
		
		List<UserMenu> userMenus = userMenuDao.listAll(um);//需要检查的用户菜单
		List<UserMenu> addMenus = new ArrayList<UserMenu>();//新增菜单
		List<Integer> deleteMenus = new ArrayList<Integer>();//要删除的菜单
		Map<Integer,Map<Integer,Set<Integer>>> oldUserMenuMap = new HashMap<Integer,Map<Integer,Set<Integer>>>();
		
		for(UserMenu umenu : userMenus){
			Set<Integer> menuSet = roleMenuMap.get(umenu.getSysRoleId());
			if(menuSet != null){
				if(!menuSet.contains(umenu.getMenuId())){//要删除的
					deleteMenus.add(umenu.getId());
				}else{
					Map<Integer,Set<Integer>> urms = oldUserMenuMap.get(umenu.getUserId());
					if(urms == null){
						urms = new HashMap<Integer,Set<Integer>>();
					}
					Set<Integer> rms = urms.get(umenu.getSysRoleId());
					if(rms == null){
						rms = new HashSet<Integer>();
					}
					rms.add(umenu.getMenuId());
					urms.put(umenu.getSysRoleId(), rms);
					oldUserMenuMap.put(umenu.getUserId(), urms);
				}
			}else{//要删除的
				deleteMenus.add(umenu.getId());
			}
		}
		
		for (UserSpace us : userList) {
			if(oldUserMenuMap.get(us.getUserId()) == null){
				oldUserMenuMap.put(us.getUserId(), new HashMap<Integer,Set<Integer>>());
			}else if(oldUserMenuMap.get(us.getUserId()).get(us.getRoleId()) == null){
				oldUserMenuMap.get(us.getUserId()).put(us.getRoleId(),new HashSet<Integer>());
			}
		}
		
		//处理要添加的菜单
		for(Integer ouid : oldUserMenuMap.keySet()){
			Map<Integer,Set<Integer>> urmap = oldUserMenuMap.get(ouid);
			for(Integer orid : urmap.keySet()){
				Set<Integer> omset = urmap.get(orid);
				Set<Integer> nmset = roleMenuMap.get(orid);
				if(nmset.size() > omset.size()){//新菜单多于旧菜单
					for(Integer nmid : nmset){
						if(!omset.contains(nmid)){
							UserMenu newmenu = new UserMenu();
							newmenu.setMenuId(nmid);
							newmenu.setSysRoleId(orid);
							newmenu.setUserId(ouid);
							Menu m = menuDao.get(nmid);
							newmenu.setDisplay(true);
							newmenu.setSort(m.getSort());
							newmenu.setName(m.getName());
							addMenus.add(newmenu);
						}
					}
				}
			}
		}
	
		userMenuDao.batchInsert(addMenus);
		for(Integer mid : deleteMenus){
			userMenuDao.delete(mid);
		}
			
	}

}
