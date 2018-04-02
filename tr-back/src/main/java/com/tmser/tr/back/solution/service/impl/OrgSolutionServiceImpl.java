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
import com.tmser.tr.uc.bo.RoleMenu;
import com.tmser.tr.uc.bo.SysOrgSolution;
import com.tmser.tr.uc.dao.RoleMenuDao;
import com.tmser.tr.uc.dao.SysOrgSolutionDao;
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
  public void deleteSolutionFw(Integer orgId, Integer sid) {
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
    userspaceDao.updateUserSolution(solutionId, orgId,
        (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR));

    // 修正用户菜单
    RoleMenu rm = new RoleMenu();
    rm.addCustomCulomn("r.roleId, r.menuId");
    rm.setIsDel(0);
    rm.addAlias("r");
    rm.buildCondition(
        " and r.roleId in (select ur.id from Role ur where ur.solutionId = :solutionId and ur.isDel = :isDel)")
        .put("solutionId", solutionId).put("isDel", false);
    List<RoleMenu> roleMenus = roleMenuDao.listAll(rm);// 所有新方案菜单
    Map<Integer, Set<Integer>> roleMenuMap = new HashMap<Integer, Set<Integer>>();
    for (RoleMenu r : roleMenus) {
      Set<Integer> menuSet = roleMenuMap.get(r.getRoleId());
      if (menuSet == null) {
        menuSet = new HashSet<Integer>();
      }
      menuSet.add(r.getMenuId());

      roleMenuMap.put(r.getRoleId(), menuSet);
    }

  }

}
