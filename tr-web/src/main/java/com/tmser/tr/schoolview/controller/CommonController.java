package com.tmser.tr.schoolview.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.schoolview.vo.CommonModel;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

public class CommonController extends AbstractController {
  @Autowired
  private OrganizationService organizationService;

  /**
   * 通过学段得到科目(公共方法)
   * 
   * @param xdid
   * @return
   */
  protected List<Integer> getSubjectsByxdid(Integer xdid) {
    // 根据学段查找科目
    User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    Organization org = organizationService.findOne(user.getOrgId());
    Integer[] areaIds = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")),
        ",");
    List<Meta> mrlist = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(), xdid, areaIds);
    List<Integer> subList = new ArrayList<Integer>();
    for (Meta sub : mrlist) {
      subList.add(sub.getId());
    }
    return subList;
  }

  /**
   * 参数回传
   * 
   * @param cm
   * @param m
   */
  protected void handleCommonVo(CommonModel cm, Model m) {
    // 学段
    checkPhase(cm);
    m.addAttribute("cm", cm);

  }

  /**
   * 检查学段信息是否为空,如果为空则赋值
   */
  public void checkPhase(CommonModel cm) {
    UserSpace userSpace = (UserSpace) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SPACE); // 用户空间
    @SuppressWarnings("unchecked")
    List<MetaRelationship> xueduans = (List<MetaRelationship>) WebThreadLocalUtils
        .getSessionAttrbitue(SessionKey.CURRENT_PHASE_LIST);
    MetaRelationship xd = (MetaRelationship) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_PHASE);
    if (cm.getOrgID() != null) {
      if (xueduans == null) {
        Organization org = organizationService.findOne(cm.getOrgID());
        /*
         * 返回organization,用于top页面学校logo信息
         */
        if (org != null) {
          String id = org.getPhaseTypes();
          String[] ids = id != null ? id.split(",") : new String[] {};
          xueduans = new ArrayList<MetaRelationship>(ids.length);
          int tag = 0;
          for (String pid : ids) {
            try {
              if (!StringUtils.isBlank(pid)) {
                xueduans.add(MetaUtils.getMetaRelation(Integer.parseInt(pid)));
                if (tag == 0 && xd == null) {
                  xd = MetaUtils.getMetaRelation(Integer.parseInt(pid));
                  tag++;
                }
              }
            } catch (NumberFormatException e) {
              logger.error("add phase failed ", e);
            }
          }
          if (xueduans.size() > 1) {
            cm.setXueduans(xueduans);
          }
          WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_PHASE_LIST, xueduans);
        }

      }
      if (cm.getXdid() != null) {
        try {
          UserSpace newUs = null;
          if (userSpace == null) {
            newUs = new UserSpace();
          } else {
            newUs = userSpace;
          }
          newUs.setPhaseId(cm.getXdid());
          if (xd == null || (xd != null && !cm.getXdid().equals(xd.getId()))) {
            xd = MetaUtils.getMetaRelation(cm.getXdid());
          }
          if (xd != null) {
            newUs.setPhaseType(xd.getEid());
          }
          WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_SPACE, newUs);
        } catch (Exception e) {
          logger.error("", e);
        }
      }
      cm.setXddefault(xd);
      WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_PHASE, xd);
    }
  }

}
