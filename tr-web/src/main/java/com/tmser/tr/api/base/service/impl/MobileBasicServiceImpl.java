/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.api.base.dao.MobileBasicDao;
import com.tmser.tr.api.base.service.MobileBasicService;
import com.tmser.tr.api.service.SchoolYearUtilService;
import com.tmser.tr.api.utils.ApiUtils;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.bo.Icon;
import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.dao.BookChapterDao;
import com.tmser.tr.manage.meta.dao.BookDao;
import com.tmser.tr.manage.meta.dao.IconDao;
import com.tmser.tr.manage.meta.dao.MenuDao;
import com.tmser.tr.manage.meta.dao.MetaRelationshipDao;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserMenu;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.RoleDao;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.dao.UserMenuDao;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 * 移动离线端基础服务实现。
 * </pre>
 *
 * @author zpp
 * @version $Id: MobileBasicServiceImpl.java, v 1.0 2016年4月11日 下午2:12:32 zpp Exp
 *          $
 */
@Service
@Transactional
public class MobileBasicServiceImpl implements MobileBasicService {

  @Resource
  private UserDao userDao;
  @Resource
  private UserSpaceDao userSpaceDao;
  @Resource
  private SchoolYearUtilService schoolYearUtilService;
  @Resource
  private UserMenuDao userMenuDao;
  @Resource
  private MenuDao menuDao;
  @Resource
  private IconDao iconDao;
  @Resource
  private BookDao bookDao;
  @Resource
  private RoleDao roleDao;
  @Resource
  private MetaRelationshipDao metaRelationshipDao;
  @Resource
  private OrganizationDao organizationDao;
  @Resource
  private BookChapterDao bookChapterDao;
  @Resource
  private MobileBasicDao mobileBasicDao;

  /**
   * 移动教研平台离线端请求在线登录成功后，同步用户基础信息。
   * 
   * @param userid
   * @return
   * @see com.tmser.tr.mobile.base.service.MobileBasicService#getBaseUserInfo(java.lang.Integer)
   */
  @Override
  public Map<String, Object> getBaseUserInfo(Integer userid) {
    Map<String, Object> data = new HashMap<String, Object>();
    User user = userDao.get(userid);
    if (user != null) {
      data.put("user_id", user.getId());
      data.put("user_nickname", user.getNickname());
      data.put("user_name", user.getName());
      data.put("user_photo", ApiUtils.formatPath(user.getPhoto()));
      data.put("org_id", user.getOrgId());
      data.put("org_name", user.getOrgName());
      data.put("school_age", user.getSchoolAge());
      data.put("explains", user.getExplains());
      data.put("profession", user.getProfession());
      data.put("honorary", user.getHonorary());
      data.put("sex", user.getSex());
      data.put("birthday", user.getBirthday());
      data.put("cellphone", user.getCellphone());
      data.put("mail", user.getMail());
      if (user.getAppId() != null && user.getAppId() > 0) {
        data.put("is_bst", true);
      } else {
        data.put("is_bst", false);
      }
      data.put("school_year", schoolYearUtilService.getCurrentSchoolYear());
      Organization org = organizationDao.get(user.getOrgId());
      if (org != null) {
        String phaseTypes = org.getPhaseTypes();
        if (StringUtils.isNotEmpty(phaseTypes)) {
          String[] phaseIds = org.getPhaseTypes().split(",");
          List<Map<String, Object>> phaselist = new ArrayList<Map<String, Object>>();
          for (int i = 0; i < phaseIds.length; i++) {
            if (StringUtils.isNotEmpty(phaseIds[i])) {
              Integer phaseid = Integer.parseInt(phaseIds[i]);
              Map<String, Object> map = new HashMap<String, Object>();
              map.put("phase_id", phaseid);
              map.put("phase_name", metaRelationshipDao.get(phaseid).getName());
              phaselist.add(map);
            }
          }
          data.put("phases", phaselist);
        } else {
          data.put("phases", null);
        }
      }
      // 查询用户工作空间集合
      UserSpace us = new UserSpace();
      us.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());
      us.setUserId(userid);
      us.setEnable(1);
      List<UserSpace> uslist = userSpaceDao.listAll(us);
      if (!CollectionUtils.isEmpty(uslist)) {
        // 封装系统菜单数据
        List<Map<String, Object>> spacelist = new ArrayList<Map<String, Object>>();
        Map<String, Object> sysMenuMap = getSysMenuDatasInfo();
        for (UserSpace ustemp : uslist) {
          Map<String, Object> spcemap = new HashMap<String, Object>();
          spcemap.put("space_id", ustemp.getId());
          spcemap.put("space_name", ustemp.getSpaceName());
          spcemap.put("book_id", ustemp.getBookId());
          if (StringUtils.isNotEmpty(ustemp.getBookId())) {
            spcemap.put("book_name", bookDao.get(ustemp.getBookId()).getComName());
          } else {
            spcemap.put("book_name", "");
          }
          spcemap.put("phase_id", ustemp.getPhaseId());
          if (ustemp.getPhaseId() != null && ustemp.getPhaseId() != 0) {
            spcemap.put("phase_name", metaRelationshipDao.get(ustemp.getPhaseId()).getName());
          } else {
            spcemap.put("phase_name", "");
          }
          spcemap.put("grade_id", ustemp.getGradeId());
          if (ustemp.getGradeId() != null && ustemp.getGradeId() != 0) {
            spcemap.put("grade_name", MetaUtils.getMeta(ustemp.getGradeId()).getName());
          } else {
            spcemap.put("grade_name", "");
          }
          spcemap.put("subject_id", ustemp.getSubjectId());
          if (ustemp.getSubjectId() != null && ustemp.getSubjectId() != 0) {
            spcemap.put("subject_name", MetaUtils.getMeta(ustemp.getSubjectId()).getName());
          } else {
            spcemap.put("subject_name", "");
          }
          spcemap.put("sys_role_id", ustemp.getSysRoleId());
          spcemap.put("role_id", ustemp.getRoleId());
          if (ustemp.getRoleId() != null && ustemp.getRoleId() != 0) {
            spcemap.put("sys_role_name", roleDao.get(ustemp.getRoleId()).getRoleName());
          } else {
            spcemap.put("sys_role_name", "");
          }
          spcemap.put("space_sort", ustemp.getSort());
          spcemap.put("is_default", ustemp.getIsDefault());
          UserMenu um = new UserMenu();
          um.setSysRoleId(ustemp.getRoleId());
          um.setUserId(userid);
          um.addCustomCulomn("menuId,name,sort");
          List<UserMenu> umlist = userMenuDao.listAll(um);
          List<Map<String, Object>> menulist = getListMenuDatas(umlist, sysMenuMap);
          spcemap.put("menus", menulist);
          if (ustemp.getSysRoleId().intValue() == SysRole.XZ.getId().intValue()
              || ustemp.getSysRoleId().intValue() == SysRole.FXZ.getId().intValue()
              || ustemp.getSysRoleId().intValue() == SysRole.ZR.getId().intValue()) {
            spcemap.put("phase_isoption", true);// 校长、副校长、主任需要选择学段
          } else {
            spcemap.put("phase_isoption", false);
          }
          spacelist.add(spcemap);
        }
        data.put("user_space", spacelist);
      }

    }
    return data;
  }

  /**
   * 封装系统菜单数据
   * 
   * @return
   */
  private Map<String, Object> getSysMenuDatasInfo() {
    Menu m = new Menu();
    Map<String, Object> data = new HashMap<String, Object>();
    data.put("role1", 1);
    data.put("role2", 2);
    m.setIsTablet(true);
    m.addCustomCondition("and (sysRoleId=:role1 or sysRoleId=:role2)", data);
    m.addCustomCulomn("id,parentid,icoId,url,target");
    List<Menu> mlist = menuDao.listAll(m);
    data = new HashMap<String, Object>();
    for (Menu mtemp : mlist) {
      Map<String, String> map = new HashMap<String, String>();
      map.put("menu_url", ApiUtils.formatPath(mtemp.getUrl()));
      if (StringUtils.isNotEmpty(mtemp.getTarget())) {
        map.put("menu_target", mtemp.getTarget());
      } else {
        map.put("menu_target", "_self");
      }
      if (mtemp.getIcoId() != null && mtemp.getIcoId() != 0) {
        Icon icon = iconDao.get(mtemp.getIcoId());
        map.put("menu_ico", icon.getImgSrc());
      }
      if (mtemp.getParentid() != null && mtemp.getParentid() != 0) {
        map.put("parentid", String.valueOf(mtemp.getParentid()));
        data.put("child_" + mtemp.getId(), map);
      } else {
        data.put(String.valueOf(mtemp.getId()), map);
      }
    }
    return data;
  }

  /**
   * 获取封装后的menu菜单信息,附带上图标信息和子菜单信息
   * 
   * @param umlist
   * @return
   */
  @SuppressWarnings("unchecked")
  private List<Map<String, Object>> getListMenuDatas(List<UserMenu> umlist, Map<String, Object> smMap) {
    List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
    Map<String, Object> childmap = new HashMap<String, Object>();
    for (UserMenu um : umlist) {
      Map<String, String> sysmenu = (Map<String, String>) smMap.get(String.valueOf(um.getMenuId()));
      if (CollectionUtils.isEmpty(sysmenu)) {
        sysmenu = (Map<String, String>) smMap.get("child_" + um.getMenuId());
        if (!CollectionUtils.isEmpty(sysmenu)) {
          String parentid = sysmenu.get("parentid");
          if (childmap.get(parentid) != null) {
            List<Map<String, Object>> maplist = (List<Map<String, Object>>) childmap.get(parentid);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("menu_id", um.getMenuId());
            map.put("menu_name", um.getName());
            map.put("menu_sort", um.getSort());
            map.put("menu_ico", sysmenu.get("menu_ico"));
            map.put("menu_url", ApiUtils.formatPath(sysmenu.get("menu_url")));
            map.put("menu_target", sysmenu.get("menu_target"));
            maplist.add(map);
          } else {
            List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("menu_id", um.getMenuId());
            map.put("menu_name", um.getName());
            map.put("menu_sort", um.getSort());
            map.put("menu_ico", sysmenu.get("menu_ico"));
            map.put("menu_url", ApiUtils.formatPath(sysmenu.get("menu_url")));
            map.put("menu_target", sysmenu.get("menu_target"));
            maplist.add(map);
            childmap.put(parentid, maplist);
          }
        }
      }
    }
    for (UserMenu um : umlist) {
      Map<String, String> sysmenu = (Map<String, String>) smMap.get(String.valueOf(um.getMenuId()));
      if (!CollectionUtils.isEmpty(sysmenu)) {
        Map<String, Object> mapmenu = new HashMap<String, Object>();
        mapmenu.put("menu_id", um.getMenuId());
        mapmenu.put("menu_name", um.getName());
        mapmenu.put("menu_sort", um.getSort());
        mapmenu.put("menu_ico", sysmenu.get("menu_ico"));
        mapmenu.put("menu_url", ApiUtils.formatPath(sysmenu.get("menu_url")));
        mapmenu.put("menu_target", sysmenu.get("menu_target"));
        if (childmap.get(String.valueOf(um.getMenuId())) != null) {
          mapmenu.put("menu_child", childmap.get(String.valueOf(um.getMenuId())));
        }
        returnlist.add(mapmenu);
      }
    }
    return returnlist;
  }

  /**
   * 移动教研平台离线端请求在线登录成功后，同步机构-学科\年级\用户\的信息。
   * 
   * @param orgid
   * @return
   * @see com.tmser.tr.api.base.service.MobileBasicService#getOrgInfo(java.lang.Integer)
   */
  @Override
  public Map<String, Object> getOrgInfo(Integer orgid) {
    Organization org = organizationDao.get(orgid);
    if (org != null) {
      Map<String, Object> data = new HashMap<String, Object>();
      data.put("org_id", org.getId());
      data.put("org_name", org.getName());
      String phaseTypes = org.getPhaseTypes();
      if (StringUtils.isNotEmpty(phaseTypes)) {
        String[] phaseids = phaseTypes.split(",");
        List<Map<String, Object>> phases = new ArrayList<Map<String, Object>>();
        for (String phaseid : phaseids) {
          if (StringUtils.isNotEmpty(phaseid)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("phase_id", Integer.parseInt(phaseid));
            map.put("phase_name", metaRelationshipDao.get(Integer.parseInt(phaseid)).getName());
            UserSpace us = new UserSpace();
            us.setOrgId(org.getId());
            us.setPhaseId(Integer.parseInt(phaseid));
            us.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());
            map.put("grades", getGradesByXZXD(org.getSchoolings(), Integer.parseInt(phaseid)));
            map.put("subjects", getSubjectsByXD(Integer.parseInt(phaseid), org));
            map.put("users", mobileBasicDao.findUsersMap(us));
            phases.add(map);
          }
        }
        data.put("phases", phases);
      } else {
        data.put("phases", null);
      }
      return data;
    }
    return null;
  }

  /**
   * 通过学段来获得学科集合
   * 
   * @param phaseId
   * @return
   */
  private List<Map<String, Object>> getSubjectsByXD(Integer phaseId, Organization org) {
    List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    Integer[] areaIds = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")),
        ",");
    List<Meta> list = MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(), phaseId, areaIds);
    if (list != null) {
      for (Meta m : list) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("subject_id", m.getId());
        map.put("subject_name", m.getName());
        data.add(map);
      }
    }
    return data;
  }

  /**
   * 通过学制学段来获得年级集合
   * 
   * @param systemId
   * @param phaseId
   * @return
   */
  private List<Map<String, Object>> getGradesByXZXD(Integer schoolings, Integer phaseId) {
    List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    List<Meta> listAllGrade = MetaUtils.getOrgTypeMetaProvider().listAllGrade(schoolings, phaseId);
    if (listAllGrade != null) {
      for (Meta m : listAllGrade) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("grade_id", m.getId());
        map.put("grade_name", m.getName());
        data.add(map);
      }
    }
    return data;
  }

  /**
   * 移动教研平台离线端请求在线登录成功后，同步用户目录章节信息。
   * 
   * @param userid
   * @return
   * @see com.tmser.tr.api.base.service.MobileBasicService#getBookInfo(java.lang.Integer)
   */
  @Override
  public List<Map<String, Object>> getBookInfo(Integer userid) {
    UserSpace us = new UserSpace();
    us.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());
    us.setUserId(userid);
    List<UserSpace> uslist = userSpaceDao.listAll(us);
    List<Book> booklist = getBookListByUserSpace(uslist);
    if (!CollectionUtils.isEmpty(booklist)) {
      List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
      for (Book book : booklist) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("com_id", book.getComId());
        map.put("com_name", book.getComName());
        map.put("fascicule_id", book.getFasciculeId());
        map.put("relation_com_id", book.getRelationComId());
        map.put("chapters", getChaptersTreeByComId(book.getComId()));
        data.add(map);
      }
      return data;
    } else {
      return null;
    }
  }

  /**
   * 通过书籍ID获取章节目录结构
   * 
   * @param comId
   * @return
   */
  @SuppressWarnings("unchecked")
  private List<Map<String, Object>> getChaptersTreeByComId(String comId) {
    BookChapter bc = new BookChapter();
    bc.setComId(comId);
    bc.addCustomCulomn("chapterId,chapterName,parentId,orderNum,chapterLevel");
    bc.addOrder("chapterLevel asc,orderNum asc");
    List<BookChapter> listone = bookChapterDao.listAll(bc);
    List<Map<String, Object>> returnMap = new ArrayList<Map<String, Object>>();

    Map<String, Object> top = new HashMap<String, Object>();
    for (BookChapter bcone : listone) {
      Map<String, Object> mapone = new HashMap<String, Object>();
      mapone.put("chapter_id", bcone.getChapterId());
      mapone.put("chapter_name", bcone.getChapterName());
      mapone.put("childs", null);
      if ("-1".equals(bcone.getParentId())) {
        top.put(bcone.getChapterId(), mapone);
        returnMap.add(mapone);
      } else {
        Object parent = top.get(bcone.getParentId());
        if (parent instanceof Map) {
          Object childs = ((Map<String, Object>) parent).get("childs");
          if (childs == null) {
            List<Map<String, Object>> childlist = new ArrayList<Map<String, Object>>();
            childlist.add(mapone);
            ((Map<String, Object>) parent).put("childs", childlist);
          } else {
            List<Map<String, Object>> childlist = (List<Map<String, Object>>) ((Map<String, Object>) parent)
                .get("childs");
            childlist.add(mapone);
          }
          top.put(bcone.getChapterId(), mapone);
        }
      }
    }
    return returnMap;
  }

  /**
   * 通过用户空间集合查询出相关书籍集合
   * 
   * @param us
   * @return
   */
  private List<Book> getBookListByUserSpace(List<UserSpace> uslist) {
    List<String> bookids = new ArrayList<String>();
    for (UserSpace ust : uslist) {
      if (StringUtils.isNotEmpty(ust.getBookId())
          && (SysRole.TEACHER.getId() == ust.getSysRoleId() || SysRole.BKZZ.getId().equals(ust.getSysRoleId()))) {
        bookids.add(ust.getBookId());
      }
    }
    if (!CollectionUtils.isEmpty(bookids)) {
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("bookids", bookids);
      Book book = new Book();
      book.addCustomCondition("and comId in (:bookids)", params);
      book.addCustomCulomn("relationComId");
      List<Book> booklist = bookDao.listAll(book);
      for (Book bookt : booklist) {
        if (StringUtils.isNotEmpty(bookt.getRelationComId())) {
          bookids.add(bookt.getRelationComId());
        }
      }
      book.addCustomCulomn("comId,comName,fasciculeId,relationComId");
      List<Book> data = bookDao.listAll(book);
      return data;
    } else {
      return null;
    }
  }

  /**
   * 获得当前机构下所用到的书籍数据集合
   * 
   * @param orgid
   * @return
   * @see com.tmser.tr.api.base.service.MobileBasicService#getOrgBooksInfo(java.lang.Integer)
   */
  @Override
  public Map<String, Object> getOrgBooksInfo(Integer orgid) {
    UserSpace us = new UserSpace();
    us.setSchoolYear(schoolYearUtilService.getCurrentSchoolYear());
    us.setSysRoleId(SysRole.TEACHER.getId());
    us.addCustomCulomn("bookId");
    us.setOrgId(orgid);
    List<UserSpace> uslist = userSpaceDao.listAll(us);
    Set<String> bookIds = new HashSet<String>();
    for (UserSpace ust : uslist) {
      bookIds.add(ust.getBookId());
    }
    Iterator<String> iterator = bookIds.iterator();
    Map<String, Object> data = new HashMap<>();
    while (iterator.hasNext()) {
      String bookid = iterator.next();
      if (StringUtils.isNotEmpty(bookid)) {
        Book book = bookDao.get(bookid);
        Map<String, Object> map = new HashMap<>();
        map.put("fasciculeid", book.getFasciculeId());
        map.put("chapter", getChaptersTreeByComId(bookid));
        if (book.getFasciculeId() == 176 || book.getFasciculeId() == 177) {// 上册或者下册
          map.put("other_book_id", book.getRelationComId());
          // 添加另一册书
          Map<String, Object> maptwo = new HashMap<>();
          if (book.getFasciculeId() == 176) {
            maptwo.put("fasciculeid", 177);
          } else {
            maptwo.put("fasciculeid", 176);
          }
          maptwo.put("chapter", getChaptersTreeByComId(book.getRelationComId()));
          maptwo.put("other_book_id", bookid);
          data.put(book.getRelationComId(), maptwo);
        }
        data.put(bookid, map);
      }
    }
    return data;
  }

}
