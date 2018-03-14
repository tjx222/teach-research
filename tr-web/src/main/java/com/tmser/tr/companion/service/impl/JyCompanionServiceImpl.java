/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.companion.bo.JyCompanion;
import com.tmser.tr.companion.bo.JyCompanionMessage;
import com.tmser.tr.companion.constants.JyCompanionConstants;
import com.tmser.tr.companion.dao.JyCompanionDao;
import com.tmser.tr.companion.dao.JyCompanionMessageDao;
import com.tmser.tr.companion.service.JyCompanionService;
import com.tmser.tr.companion.vo.JyCompanionSearchVo;
import com.tmser.tr.companion.vo.JyCompanionVo;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.dao.BookDao;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.notice.constants.NoticeType;
import com.tmser.tr.notice.service.impl.NoticeUtils;
import com.tmser.tr.uc.SysRole;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.uc.dao.UserSpaceDao;
import com.tmser.tr.uc.service.SchoolYearService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.JyCollectionUtils;

/**
 * 同伴互助 服务实现类
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author Generate Tools
 * @version $Id: JyCompanion.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
@Service
@Transactional
public class JyCompanionServiceImpl extends AbstractService<JyCompanion, Integer> implements JyCompanionService {

  @Autowired
  private JyCompanionDao jyCompanionDao;
  @Autowired
  private JyCompanionMessageDao jyCompanionMessageDao;
  @Autowired
  private UserDao userDao;
  @Autowired
  private UserSpaceDao userSpaceDao;
  @Autowired
  private OrganizationService organizationService;
  @Autowired
  private SchoolYearService schoolYearService;
  @Autowired
  private BookDao bookDao;

  /**
   * @return
   * @see com.tmser.tr.common.service.BaseService#getDAO()
   */
  @Override
  public BaseDAO<JyCompanion, Integer> getDAO() {
    return jyCompanionDao;
  }

  /**
   * 分页查询同伴列表
   */
  @Override
  public PageList<JyCompanionVo> findCompanions(JyCompanionSearchVo searchVo) {
    // 根据学校名称查询，将学校名称转换为学校id
    if (!searchVo.getIsSameSchool()) {
      Organization myOrganization = organizationService.findOne(CurrentUserContext.getCurrentUser().getOrgId());
      Organization om = new Organization();
      om.addCustomCulomn("id");
      if (StringUtils.isNotBlank(searchVo.getSchoolName())) {
        om.setName(SqlMapping.LIKE_PRFIX + searchVo.getSchoolName() + SqlMapping.LIKE_PRFIX);
      }
      om.setOrgType(myOrganization.getOrgType());
      om.setEnable(1);
      // om.setPhaseTypes(SqlMapping.LIKE_PRFIX + "," +
      // CurrentUserContext.getCurrentUser().getPhaseId() + "," +
      // SqlMapping.LIKE_PRFIX);
      om.setType(myOrganization.getType());

      // 通过学校名称模糊查询学校id
      List<Organization> list = organizationService.findAll(om);
      // 查询结果为空，直接返回
      if (CollectionUtils.isEmpty(list)) {
        return new PageList<JyCompanionVo>(new ArrayList<JyCompanionVo>(), new Page());
      } else {
        List<Integer> schoolIds = JyCollectionUtils.getValues(list, "id");
        schoolIds.remove(myOrganization.getId());
        searchVo.setSchoolIds(schoolIds);
      }
    }
    // 查询同伴的用户id
    PageList<Integer> userIdPage = jyCompanionDao.findUserIds(searchVo);
    if (CollectionUtils.isEmpty(userIdPage.getDatalist())) {
      return new PageList<JyCompanionVo>(new ArrayList<JyCompanionVo>(), userIdPage.getPage());
    }
    List<JyCompanionVo> companions = new ArrayList<>();
    for (Integer userId : userIdPage.getDatalist()) {
      JyCompanionVo vo = new JyCompanionVo();
      vo.setUserIdCompanion(userId);
      companions.add(vo);
    }
    // 填充用户简介信息
    fillUserSimpleInfo(companions);
    // 填充朋友关系
    fillFriendRelation(companions);
    return new PageList<JyCompanionVo>(companions, userIdPage.getPage());
  }

  /**
   * 填充朋友关系
   * 
   * @param result
   */
  private void fillFriendRelation(List<JyCompanionVo> vos) {
    List<Integer> copanionIds = JyCollectionUtils.getValues(vos, "userIdCompanion");
    // 返回是朋友关系的id
    List<Integer> friendIds = jyCompanionDao.validFriendRelation(CurrentUserContext.getCurrentUserId(), copanionIds);
    for (JyCompanionVo vo : vos) {
      if (friendIds.contains(vo.getUserIdCompanion())) {
        vo.setIsFriend(JyCompanionConstants.IS_FRIEND_TRUE);
      } else {
        vo.setIsFriend(JyCompanionConstants.IS_FRIEND_FALSE);
      }
    }
  }

  /**
   * 填充用户简介信息
   * 
   * @param list
   */
  private void fillUserSimpleInfo(List<JyCompanionVo> vos) {
    if (CollectionUtils.isEmpty(vos)) {
      return;
    }
    List<Integer> userIds = JyCollectionUtils.getValues(vos, "userIdCompanion");

    List<UserSpace> spaces = userSpaceDao.findByUserIds(userIds);
    // 遍历设置用户的简介信息
    for (JyCompanionVo vo : vos) {
      Set<Integer> gradeIds = new HashSet<Integer>();
      for (UserSpace space : spaces) {

        if (space.getUserId().equals(vo.getUserIdCompanion())) {
          // 设置最优先职务
          fillHighestRole(vo, space);
          // 如果时教师，获取年级、学科、版本消息
          if (SysRole.TEACHER.getId().equals(space.getSysRoleId())) {
            gradeIds.add(space.getGradeId());
            // 设置最优先年级
            // fillHighestGrade(vo, space);
            // 设置最优先学科，及版本
            fillHighestSubject(vo, space);
          }
          vo.setSchoolIdCompanion(space.getOrgId());
        }
      }
      fillGradeInfo(gradeIds, vo);
    }
    List<User> users = userDao.findByIds(userIds);

    // 设置职称，姓名，昵称
    for (User user : users) {
      for (JyCompanionVo vo : vos) {
        if (user.getId().equals(vo.getUserIdCompanion())) {
          vo.setUserNameCompanion(user.getName());
          vo.setUserNicknameCompanion(user.getNickname());
          vo.setProfession(user.getProfession());
          vo.setPhoto(user.getPhoto());
        }
      }
    }
    List<Integer> schoolIds = JyCollectionUtils.getValues(vos, "schoolIdCompanion");
    Map<String, Object> paramMap = new HashMap<String, Object>();
    Organization om = new Organization();
    om.addCustomCulomn("id,name");
    om.addCustomCondition(" and id in (:ids) ", paramMap);
    paramMap.put("ids", schoolIds);
    List<Organization> orgs = organizationService.findAll(om);
    // 设置学校名称
    for (Organization org : orgs) {
      for (JyCompanionVo vo : vos) {
        if (org.getId().equals(vo.getSchoolIdCompanion())) {
          vo.setSchoolNameCompanion(org.getName());
        }
      }
    }
  }

  /**
   * 填充年纪信息
   * 
   * @param gradeIds
   * @param vo
   */
  private void fillGradeInfo(Set<Integer> gradeIds, JyCompanionVo vo) {
    if (CollectionUtils.isEmpty(gradeIds)) {
      return;
    }
    List<Integer> grdeIdList = new ArrayList<Integer>(gradeIds);
    Collections.sort(grdeIdList);
    Map<Integer, Meta> map = new HashMap<>();
    for (Integer grdeId : grdeIdList) {
      Meta meta = MetaUtils.getMeta(grdeId);
      map.put(meta.getId(), meta);
    }
    String gradeStr = "";
    for (Integer gradeId : grdeIdList) {
      if (map.get(gradeId) != null) {
        gradeStr += map.get(gradeId).getName();
        gradeStr += ",";
      }
    }
    if (!"".equals(gradeStr)) {
      gradeStr = gradeStr.substring(0, gradeStr.length() - 1);
      vo.setHighestGradeName(gradeStr);
    }

  }

  /**
   * 设置最优先学科名称，学科id越小越优先
   * 
   * @param vo
   * @param space
   */
  private void fillHighestSubject(JyCompanionVo vo, UserSpace space) {
    // 如果最高职务未设置，取工作空间名
    if (vo.getHighestSubjectId() == null || vo.getHighestSubjectId() > space.getSubjectId()) {// 取最小学科id名称，为优先学科名称
      vo.setHighestSubjectId(space.getSubjectId());
      Meta dic = MetaUtils.getMeta(space.getSubjectId());
      vo.setHighestSubjectName(dic.getName());
      Book book = bookDao.get(space.getBookId());
      // 设置版本名称
      if (book != null) {
        vo.setHighestFormatName(book.getFormatName());
      }
    }
  }

  /**
   * 设置最优先职务
   * 
   * @param vo
   * @param space
   */
  private void fillHighestRole(JyCompanionVo vo, UserSpace space) {
    // 如果最高职务未设置，取工作空间名
    if (vo.getHighestRoleId() == null) {
      vo.setHighestRoleId(space.getSysRoleId());
      vo.setHighestRoleName(space.getSpaceName());
    } else if (vo.getHighestRoleId().equals(space.getSysRoleId())) {// 如果最高职务冲突,取角色名
      vo.setHighestRoleName(SysRole.getCname(space.getSysRoleId()));
    } else if (vo.getHighestRoleId() < space.getSysRoleId()) {// 设置为最高职务，取工作空间名
      vo.setHighestRoleId(space.getSysRoleId());
      vo.setHighestRoleName(space.getSpaceName());
    }
  }

  /**
   * 获取所有好友信息
   */
  @Override
  public List<JyCompanionVo> findAllFriends() {
    List<JyCompanionVo> result = new ArrayList<JyCompanionVo>();

    JyCompanion model = new JyCompanion();
    model.setIsFriend(JyCompanionConstants.IS_FRIEND_TRUE);
    model.setUserId(CurrentUserContext.getCurrentUserId());
    // 获取所有好友
    List<JyCompanion> companions = jyCompanionDao.listAll(model);
    if (CollectionUtils.isEmpty(companions)) {
      return result;
    }
    // 对象转换
    for (JyCompanion companion : companions) {
      JyCompanionVo vo = new JyCompanionVo();
      BeanUtils.copyProperties(companion, vo);
      result.add(vo);
    }
    // 填充用户简介信息
    fillUserSimpleInfo(result);
    return result;
  }

  /**
   * 获取本学期的联系人
   * 
   * @param userId
   * @return
   * @see com.tmser.tr.companion.service.JyCompanionService#findLatestConmunicateCompanions(java.lang.Integer)
   */
  @Override
  public List<JyCompanionVo> findLatestConmunicateCompanions() {
    // 获取本学期开始时间
    Date startDate = schoolYearService.getCurrentTermStartTime();
    // 查询本学期联系过的人员
    List<JyCompanionVo> result = jyCompanionDao.findLatestConmunicateCompanions(CurrentUserContext.getCurrentUserId(),
        startDate);
    // 填充用户简介信息
    fillUserSimpleInfo(result);
    return result;
  }

  /**
   * 查询同伴详情
   * 
   * @param userId
   * @return
   * @see com.tmser.tr.companion.service.JyCompanionService#findCompanionDetail(java.lang.Integer)
   */
  @Override
  public JyCompanionVo findCompanionDetail(Integer userIdCompanion) {
    JyCompanionVo result = new JyCompanionVo();
    // 查询用户信息
    User user = userDao.get(userIdCompanion);
    result.setUserIdCompanion(userIdCompanion);
    result.setUserNameCompanion(user.getName());
    result.setUserNicknameCompanion(user.getNickname());
    result.setSex(user.getSex());
    // 职称
    result.setProfession(user.getProfession());
    // 教龄
    result.setSchoolAge(user.getSchoolAge());
    result.setPhoto(user.getPhoto());
    result.setIsSameOrg(user.getOrgId().equals(CurrentUserContext.getCurrentUser().getOrgId()) ? 1 : 0);
    // 学校
    result.setSchoolNameCompanion(user.getOrgName());
    UserSpace model = new UserSpace();
    model.setUserId(userIdCompanion);
    // model.setSchoolYear(CurrentUserContext.getCurrentSpace().getSchoolYear());
    // 查询工作空间
    List<UserSpace> spaces = userSpaceDao.listAll(model);
    List<Integer> dicIds = new ArrayList<Integer>();
    Set<Integer> subjectIds = new HashSet<Integer>();
    List<UserSpace> cantianBookSpaces = new ArrayList<>();
    Set<Integer> gradeIds = new HashSet<Integer>();
    // 获取学科id和年级id
    Map<Integer, UserSpace> umap = new HashMap<Integer, UserSpace>();
    for (UserSpace space : spaces) {
      umap.put(space.getSysRoleId(), space);
    }
    spaces.clear();
    spaces.addAll(umap.values());
    for (UserSpace space : spaces) {
      if (SysRole.TEACHER.getId().equals(space.getSysRoleId())) {
        subjectIds.add(space.getSubjectId());
        gradeIds.add(space.getGradeId());
        cantianBookSpaces.add(space);
        dicIds.add(space.getSubjectId());
        dicIds.add(space.getGradeId());
      }
    }
    // 存在学科id或年纪id
    if (!CollectionUtils.isEmpty(dicIds)) {
      Collections.sort(dicIds);
      Map<Integer, Meta> dicMap = new HashMap<>();
      for (Integer id : dicIds) {
        Meta meta = MetaUtils.getMeta(id);
        dicMap.put(meta.getId(), meta);
      }

      // 获取学科名称列表
      String sbujectNames = getSubjectNames(subjectIds, dicMap);
      result.setSubjectNames(sbujectNames);
      // 获取版本名称列表
      String formtNames = getFormtNames(cantianBookSpaces);
      result.setFormatNames(formtNames);
      // 获取年级名称列表
      String gradeNames = getGradeNames(gradeIds, dicMap);
      result.setGradeNames(gradeNames);
    }
    String roleNames = getRoleNames(spaces);
    result.setRoleNames(roleNames);
    JyCompanion companion = new JyCompanion();
    companion.setUserId(CurrentUserContext.getCurrentUserId());
    companion.setUserIdCompanion(userIdCompanion);
    companion.setIsFriend(JyCompanionConstants.IS_FRIEND_TRUE);
    // 查询朋友关系
    Integer count = jyCompanionDao.count(companion);
    if (count > 0) {
      result.setIsFriend(JyCompanionConstants.IS_FRIEND_TRUE);
    } else {
      result.setIsFriend(JyCompanionConstants.IS_FRIEND_FALSE);
    }
    return result;
  }

  /**
   * 获取角色名称列表，并满足如下条件： 1、显示用户的全部身份； 2、职务排序为：校长、主任、学科组长、年级组长、备课组长、教师；
   * 3、若该用有教师身份，不论有几个，则在“职务”处只显示“教师”二个字即可。其他身份均显示出全部信息。
   * 
   * @param spaces
   * @return
   */
  private String getRoleNames(List<UserSpace> spaces) {
    // 教师角色个数
    int teacherNum = 0;
    // 降序排列职务
    Collections.sort(spaces, new Comparator<UserSpace>() {
      @Override
      public int compare(UserSpace o1, UserSpace o2) {
        if (o1.getSysRoleId() > o2.getSysRoleId()) {
          return -1;
        }
        return 0;
      }
    });
    // 教研是否有多个教师角色
    for (UserSpace space : spaces) {
      if (SysRole.TEACHER.getId().equals(space.getSysRoleId())) {
        teacherNum += 1;
      }
    }
    // 职务列表
    String roleNames = "";
    for (UserSpace space : spaces) {
      if (!SysRole.TEACHER.getId().equals(space.getSysRoleId())) {
        roleNames += space.getSpaceName();
        roleNames += "、";
      }
    }
    // 教师职务拼接
    if (teacherNum == 1) {
      roleNames += spaces.get(spaces.size() - 1).getSpaceName();
    } else if (teacherNum > 1) {
      roleNames += SysRole.TEACHER.getCname();
    } else {
      if (StringUtils.isNotBlank(roleNames)) {
        // 去除最后一个“、“号
        roleNames = roleNames.substring(0, roleNames.length() - 1);
      }
    }
    return roleNames;
  }

  /**
   * 获取年纪名称列表,按年级id升序排列
   * 
   * @param gradeIds
   * @param dicMap
   * @return
   */
  private String getGradeNames(Set<Integer> gradeIds, Map<Integer, Meta> dicMap) {
    List<Integer> gradeList = new ArrayList<>(gradeIds);
    // 按学科id升序排列
    Collections.sort(gradeList);
    String gradeNames = "";
    for (Integer id : gradeList) {
      gradeNames += dicMap.get(id).getName();
      gradeNames += "、";
    }
    if (StringUtils.isNotBlank(gradeNames)) {
      // 去除最后一个“、“号
      gradeNames = gradeNames.substring(0, gradeNames.length() - 1);
    }
    return gradeNames;
  }

  /**
   * 获取版本名称列表,多门学科，则将所教学科的教材版本依次按学科的顺序显示出来
   * 
   * @param subjectIds
   * @return
   */
  private String getFormtNames(List<UserSpace> spaces) {

    List<String> bookIds = JyCollectionUtils.getValues(spaces, "bookId");
    // 查询图书信息
    List<Book> books = bookDao.findByComIds(bookIds);
    Map<String, Book> bookMap = new HashMap<>();
    for (Book book : books) {
      bookMap.put(book.getComId(), book);
    }

    // 按学科id升序排列
    Collections.sort(spaces, new Comparator<UserSpace>() {
      @Override
      public int compare(UserSpace o1, UserSpace o2) {
        if (o1.getSubjectId() > o2.getSubjectId()) {
          return -1;
        }
        return 0;
      }
    });
    Set<String> formatNameSet = new HashSet<>();
    // 变量获取图书版本
    for (UserSpace space : spaces) {
      String fm = bookMap.get(space.getBookId()).getFormatName();
      if (StringUtils.isNotBlank(fm)) {
        formatNameSet.add(fm);
      }
    }
    String formatNames = "";
    for (String formatName : formatNameSet) {
      formatNames += formatName;
      formatNames += "、";
    }
    if (StringUtils.isNotBlank(formatNames)) {
      // 去除最后一个“、“号
      formatNames = formatNames.substring(0, formatNames.length() - 1);
    }
    return formatNames;
  }

  /**
   * 获取学科名称列表
   * 
   * @param subjectIds
   * @param dicMap
   * @return
   */
  private String getSubjectNames(Set<Integer> subjectIds, Map<Integer, Meta> dicMap) {
    List<Integer> subjectList = new ArrayList<>(subjectIds);
    // 按学科id升序排列
    Collections.sort(subjectList);
    String subjectNames = "";
    for (Integer id : subjectList) {
      subjectNames += dicMap.get(id).getName();
      subjectNames += "、";
    }
    if (StringUtils.isNotBlank(subjectNames)) {
      // 去除最后一个“、“号
      subjectNames = subjectNames.substring(0, subjectNames.length() - 1);
    }
    return subjectNames;
  }

  /**
   * 将同伴加为好友
   * 
   * @param userIdCompanion
   * @see com.tmser.tr.companion.service.JyCompanionService#addFriend(java.lang.Integer)
   */
  @Override
  public void addFriend(Integer userIdCompanion) {

    // 将同伴设置为好友
    Integer count = jyCompanionDao.updateCompanionToFriend(CurrentUserContext.getCurrentUserId(), userIdCompanion);
    // 如果表中没有同伴记录，则新增好友记录
    if (count == 0) {
      this.addCompanion(userIdCompanion, true, null);
    }

    // 发送新增好友消息
    sendAddFriendNotice(userIdCompanion);
  }

  /**
   * 发送新增好友消息
   * 
   * @param userIdCompanion
   */
  private void sendAddFriendNotice(Integer userIdCompanion) {
    User user = userDao.get(userIdCompanion);
    // 设置通知参数
    String title = "添加";
    if (String.valueOf(user.getOrgId()).equals(String.valueOf(CurrentUserContext.getCurrentUser().getOrgId()))) {
      title += CurrentUserContext.getCurrentUser().getName();
    } else {
      title += CurrentUserContext.getCurrentUser().getNickname();
    }
    title += "好友";
    JyCompanionVo vo = findUserSimpleInfo(CurrentUserContext.getCurrentUserId());
    // 用户简介为空，无需发通知
    if (vo == null) {
      return;
    }

    vo.setIsSameOrg(
        String.valueOf(user.getOrgId()).equals(String.valueOf(CurrentUserContext.getCurrentUser().getOrgId())) ? 1 : 0);
    Map<String, Object> noticeInfo = new HashMap<String, Object>();
    noticeInfo.put("type", 1);
    noticeInfo.put("companion", vo);
    // 校外同伴
    if (vo.getIsSameOrg() == 0) {
      String schollNameEllipsis = vo.getSchoolNameCompanion();
      if (StringUtils.isNotBlank(schollNameEllipsis) && schollNameEllipsis.length() > 8) {
        schollNameEllipsis = schollNameEllipsis.substring(0, 7);
        schollNameEllipsis += "...";
      }
      noticeInfo.put("schollNameEllipsis", schollNameEllipsis);
      noticeInfo.put("userName", vo.getUserNicknameCompanion());
    } else {
      noticeInfo.put("userName", vo.getUserNameCompanion());
    }
    // 角色名简写
    String highestRoleNameEllipsis = vo.getHighestRoleName();
    if (StringUtils.isNotBlank(highestRoleNameEllipsis) && highestRoleNameEllipsis.length() > 8) {
      highestRoleNameEllipsis = highestRoleNameEllipsis.substring(0, 7);
      highestRoleNameEllipsis += "...";
    }
    noticeInfo.put("highestRoleNameEllipsis", highestRoleNameEllipsis);
    // 职称简写
    String professionEllipsis = vo.getProfession();
    if (StringUtils.isNotBlank(professionEllipsis) && professionEllipsis.length() > 8) {
      professionEllipsis = professionEllipsis.substring(0, 7);
      professionEllipsis += "...";
    }
    noticeInfo.put("professionEllipsis", professionEllipsis);
    // 发送通知
    NoticeUtils.sendNotice(NoticeType.COMPANION, title, CurrentUserContext.getCurrentUserId(), userIdCompanion,
        noticeInfo);
  }

  /**
   * 将同伴从好友关系中去除
   * 
   * @param userIdCompanion
   * @see com.tmser.tr.companion.service.JyCompanionService#deleteFriend(java.lang.Integer)
   */
  @Override
  public void deleteFriend(Integer userIdCompanion) {
    // 删除好友关系
    jyCompanionDao.deleteCompanion(CurrentUserContext.getCurrentUserId(), userIdCompanion);
    User user = userDao.get(userIdCompanion);
    if (user != null) {
      User currentUser = CurrentUserContext.getCurrentUser();
      String title = "删除";
      // 同一学校,用真是姓名
      if (String.valueOf(user.getOrgId()).equals(String.valueOf(currentUser.getOrgId()))) {
        title += currentUser.getName();
      } else {
        title += currentUser.getNickname();
      }
      Organization org = organizationService.findOne(currentUser.getOrgId());
      title += "好友";
      Map<String, Object> noticeInfo = new HashMap<String, Object>();
      noticeInfo.put("userName", String.valueOf(user.getOrgId()).equals(String.valueOf(currentUser.getOrgId()))
          ? currentUser.getName() : currentUser.getNickname());
      noticeInfo.put("userId", CurrentUserContext.getCurrentUserId());
      noticeInfo.put("type", 2);
      noticeInfo.put("isSameOrg", String.valueOf(user.getOrgId()).equals(String.valueOf(currentUser.getOrgId())));
      noticeInfo.put("schooleName", org.getName());
      // 发送通知
      NoticeUtils.sendNotice(NoticeType.COMPANION, title, CurrentUserContext.getCurrentUserId(), userIdCompanion,
          noticeInfo);
    }
  }

  /**
   * 新增同伴关系
   * 
   * @param userIdCompanion
   * @param isFriend
   * @param latestComunicateTime
   * @see com.tmser.tr.companion.service.JyCompanionService#addCompanion(java.lang.Integer,
   *      java.lang.Boolean, java.util.Date)
   */
  @Override
  public void addCompanion(Integer userIdCompanion, Boolean isFriend, Date latestCommunicateTime) {
    UserSpace model = new UserSpace();
    model.setUserId(userIdCompanion);
    UserSpace spaceCompanion = userSpaceDao.getOne(model);
    User userCompanion = userDao.get(userIdCompanion);

    // 组装新增对象
    JyCompanion companion = new JyCompanion();
    if (isFriend) {
      companion.setIsFriend(JyCompanionConstants.IS_FRIEND_TRUE);
    } else {
      companion.setIsFriend(JyCompanionConstants.IS_FRIEND_FALSE);
    }
    companion.setUserId(CurrentUserContext.getCurrentUserId());
    companion.setUserName(CurrentUserContext.getCurrentUser().getName());
    companion.setUserNickname(CurrentUserContext.getCurrentUser().getNickname());
    companion.setUserIdCompanion(userIdCompanion);
    companion.setUserNameCompanion(userCompanion.getName());
    companion.setUserNicknameCompanion(userCompanion.getNickname());
    companion.setLastCommunicateTime(latestCommunicateTime);
    if (CurrentUserContext.getCurrentUser().getOrgId().equals(spaceCompanion.getOrgId())) {
      companion.setIsSameOrg(JyCompanionConstants.IS_SAME_ORG_TRUE);
    } else {
      companion.setIsSameOrg(JyCompanionConstants.IS_SAME_ORG_FALSE);
    }
    // 新增同伴关系
    jyCompanionDao.addCompanion(companion);

  }

  public void setUserDao(UserDao userDao) {
    this.userDao = userDao;
  }

  public void setUserSpaceDao(UserSpaceDao userSpaceDao) {
    this.userSpaceDao = userSpaceDao;
  }

  public void setSchoolYearService(SchoolYearService schoolYearService) {
    this.schoolYearService = schoolYearService;
  }

  public void setBookDao(BookDao bookDao) {
    this.bookDao = bookDao;
  }

  /**
   * 查找用户简介信息
   * 
   * @param userIds
   * @return
   * @see com.tmser.tr.companion.service.JyCompanionService#findUserSimpleInfos(java.util.List)
   */
  @Override
  public List<JyCompanionVo> findUserSimpleInfos(List<Integer> userIds) {
    List<JyCompanionVo> result = new ArrayList<JyCompanionVo>();
    for (Integer userId : userIds) {
      JyCompanionVo vo = new JyCompanionVo();
      User user = userDao.get(userId);
      vo.setUserIdCompanion(userId);
      vo.setPhoto(user.getPhoto());
      result.add(vo);
    }
    fillUserSimpleInfo(result);
    return result;
  }

  /**
   * 查询单个用户的简介
   * 
   * @param userId
   * @return
   */
  private JyCompanionVo findUserSimpleInfo(Integer userId) {
    List<Integer> userIds = new ArrayList<Integer>();
    userIds.add(userId);
    List<JyCompanionVo> list = findUserSimpleInfos(userIds);
    if (CollectionUtils.isEmpty(list)) {
      return null;
    }
    return list.get(0);
  }

  /**
   * 同伴互助我的关注用户信息
   * 
   * @param username
   * @param iscare
   * @return
   * @see com.tmser.tr.companion.service.JyCompanionService#getMyCareFriend()
   */
  @Override
  public Map<String, Object> getMyCareFriend(String username, Boolean iscare) {
    Map<String, Object> result = new HashMap<String, Object>();
    User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    if (iscare) {// 我的关注
      // 校内关注
      JyCompanion jc = new JyCompanion();
      jc.setUserId(user.getId());
      jc.setIsFriend(JyCompanionConstants.IS_FRIEND_TRUE);
      jc.setIsSameOrg(JyCompanionConstants.IS_SAME_ORG_TRUE);
      if (StringUtils.isNotEmpty(username)) {
        jc.setUserNameCompanion(SqlMapping.LIKE_PRFIX + username + SqlMapping.LIKE_PRFIX);
      }
      jc.addCustomCulomn("id,userIdCompanion,userNameCompanion");
      List<JyCompanion> sameOrg = jyCompanionDao.listAll(jc);
      sameOrg = setLastMsg(sameOrg);
      result.put("sameOrg", sameOrg);
      result.put("sameOrgCount", sameOrg.size());
      // 校外关注
      jc.setIsSameOrg(JyCompanionConstants.IS_SAME_ORG_FALSE);
      List<JyCompanion> notSameOrg = jyCompanionDao.listAll(jc);
      notSameOrg = setLastMsg(notSameOrg);
      result.put("notSameOrg", notSameOrg);
      result.put("notSameOrgCount", notSameOrg.size());
    } else {// 我的消息
      List<JyCompanion> allCompMsg = jyCompanionMessageDao.findMsgUser(user.getId(), username);
      allCompMsg = setLastMsg(allCompMsg);
      result.put("allCompMsg", allCompMsg);
    }
    return result;
  }

  /**
   * 封装关注好友最要消息留言（暂时，待优化）
   * 
   * @param jylist
   * @return
   */
  private List<JyCompanion> setLastMsg(List<JyCompanion> jylist) {
    if (!CollectionUtils.isEmpty(jylist)) {
      User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
      for (JyCompanion jy : jylist) {
        Integer companionId = jy.getUserIdCompanion();
        JyCompanionMessage jcm = new JyCompanionMessage();
        jcm.setUserIdSender(companionId);
        jcm.setUserIdReceiver(user.getId());
        jcm.addOrder(" senderTime desc ");
        jcm = jyCompanionMessageDao.getOne(jcm);
        if (jcm != null) {
          if (StringUtils.isNotEmpty(jcm.getMessage())) {
            jy.setLastMsg(jcm.getMessage());
          } else {
            jy.setLastMsg("文件...");
          }

        }
      }
    }
    return jylist;
  }

  /**
   * 同伴的信息和同伴的分享
   * 
   * @param selfinfo
   * @param companionId
   * @return
   * @see com.tmser.tr.companion.service.JyCompanionService#getCompanionShares(java.lang.Boolean,
   *      java.lang.Integer)
   */
  @Override
  public Map<String, Object> getCompanionShares(Boolean selfinfo, Integer companionId) {
    Map<String, Object> map = new HashMap<String, Object>();
    if (selfinfo != null && selfinfo && companionId != null) {
      User user = userDao.get(companionId);
      if (user != null) {
        UserSpace us = new UserSpace();
        us.setUserId(user.getId());
        us.setSchoolYear(schoolYearService.getCurrentSchoolYear());
        List<UserSpace> uslist = userSpaceDao.listAll(us);
        if (!CollectionUtils.isEmpty(uslist)) {
          Set<Integer> subjectSet = new TreeSet<Integer>();
          Set<Integer> gradeSet = new TreeSet<Integer>();
          Set<Integer> roleSet = new TreeSet<Integer>();
          Set<String> bookIdSet = new TreeSet<String>();
          for (UserSpace ustemp : uslist) {
            if (ustemp.getSubjectId() != null && ustemp.getSubjectId() != 0) {
              subjectSet.add(ustemp.getSubjectId());
            }
            if (ustemp.getGradeId() != null && ustemp.getGradeId() != 0) {
              gradeSet.add(ustemp.getGradeId());
            }
            if (ustemp.getSysRoleId() != null && ustemp.getSysRoleId() != 0) {
              roleSet.add(ustemp.getSysRoleId());
            }
            if (StringUtils.isNotEmpty(ustemp.getBookId())) {
              bookIdSet.add(ustemp.getBookId());
            }
          }
          if (!CollectionUtils.isEmpty(subjectSet)) {
            String subject = getSysDicStr(subjectSet);
            map.put("subject", subject);
          } else {
            map.put("subject", "暂无学科");
          }
          if (!CollectionUtils.isEmpty(gradeSet)) {
            String grade = getSysDicStr(gradeSet);
            map.put("grade", grade);
          } else {
            map.put("grade", "暂无年级");
          }
          if (!CollectionUtils.isEmpty(roleSet)) {
            String role = getSysDicStr(roleSet);
            map.put("role", role);
          } else {
            map.put("role", "暂无职务");
          }
          if (!CollectionUtils.isEmpty(bookIdSet)) {
            Iterator<String> iterator = bookIdSet.iterator();
            Set<String> bbset = new HashSet<String>();
            while (iterator.hasNext()) {
              String name = bookDao.get(iterator.next()).getFormatName();
              bbset.add(name);
            }
            String str = "";
            Iterator<String> bbs = bbset.iterator();
            while (bbs.hasNext()) {
              str += bbs.next() + ",";
            }
            str = str.substring(0, str.length() - 1);
            map.put("formatName", str);
          } else {
            map.put("formatName", "暂无版本");
          }
          map.put("orgName", user.getOrgName());
        }
        // 是否已关注
        JyCompanion jc = new JyCompanion();
        jc.setUserId(CurrentUserContext.getCurrentUserId());
        jc.setUserIdCompanion(companionId);
        jc.setIsFriend(JyCompanionConstants.IS_FRIEND_TRUE);
        jc = jyCompanionDao.getOne(jc);
        if (jc != null) {
          map.put("isCare", true);
        } else {
          map.put("isCare", false);
        }
      }
    }
    return map;
  }

  /**
   * 获得元数据的集合
   * 
   * @param dataSet
   * @return
   */
  private String getSysDicStr(Set<Integer> dataSet) {
    Iterator<Integer> iterator = dataSet.iterator();
    String str = "";
    while (iterator.hasNext()) {
      String name = MetaUtils.getMeta(iterator.next()).getName();
      str += name + ",";
    }
    str = str.substring(0, str.length() - 1);
    return str;
  }

}
