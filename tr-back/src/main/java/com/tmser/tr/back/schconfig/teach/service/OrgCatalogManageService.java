package com.tmser.tr.back.schconfig.teach.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.uc.bo.UserManagescope;

public interface OrgCatalogManageService {

  /**
   * 查找教材树形结构
   * 
   * @param orgId
   *          学校id
   * @return list of map
   */
  List<Map<String, Object>> findOrgJCtree(Integer orgId);

  /**
   * 查找教材树形结构
   * 
   * @param areaId
   *          区域id
   * @return list of map
   */
  List<Map<String, Object>> findAreaJCtree(Integer areaId);

  /**
   * 
   * @param accountId
   *          账号id
   * @param orgId
   *          学校id
   * @return com.mainbo.platform.uc.bo.UserManagescope
   */
  UserManagescope findUserManagescopes(Integer accountId, Integer orgId);

}
