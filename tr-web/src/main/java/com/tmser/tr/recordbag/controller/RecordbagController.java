/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.recordbag.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.lessonplan.service.LessonPlanService;
import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.service.BookService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.recordbag.bo.Record;
import com.tmser.tr.recordbag.bo.Recordbag;
import com.tmser.tr.recordbag.service.RecordService;
import com.tmser.tr.recordbag.service.RecordbagService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.DateUtils;

/**
 * 成长档案袋控制器接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: Recordbag.java, v 1.0 2015-04-13 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/record")
public class RecordbagController extends AbstractController {

  @Autowired
  private RecordbagService recordbagService;// 档案袋service
  @Autowired
  private RecordService recordService;// 精选档案service
  @Autowired
  private LessonPlanService lpService; // 教案service
  @Autowired
  private BookService bookService;// 获取上下册书 service
  @Autowired
  private ResourcesService resService;// 获取文件的后缀

  /**
   * 成长档案袋列表页
   * 
   * @param m
   * @return
   */
  @RequestMapping("findList")
  public String findRecordBagList(Model m) {
    // 初始化用户的系统成长
    recordbagService.initRecordBags();
    List<Recordbag> list = recordbagService.findAll();
    User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    m.addAttribute("authorId", user.getId());
    m.addAttribute("recordBagList", list);
    return "record/list";
  }

  /**
   * 添加用户自定义档案袋
   * 
   * @param name
   * @param desc
   * @param m
   * @return
   */
  @RequestMapping("addRecord")
  public String addRecordBag(@RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "desc", required = false) String desc, Model m) {
    Recordbag bag = recordbagService.addBag(name, desc);
    m.addAttribute("id", bag.getId());
    return "redirect:/jy/record/findList?param=add";
  }

  /**
   * 删除用户自定义档案袋
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("delRecord")
  public String delRecordBag(@RequestParam(value = "id", required = false) Integer id, Model m) {

    Record record = new Record();
    record.setBagId(id);
    List<Record> list = recordService.findAll(record);
    for (Record r : list) {
      recordService.delete(r.getRecordId());
    }
    recordbagService.delete(id);
    return "redirect:/jy/record/findList";
  }

  /**
   * 删除精选内容
   * 
   * @param id
   * @param m
   * @return
   */
  @RequestMapping("delRecords")
  public String delRecords(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "bagId", required = false) Integer bagId,
      @RequestParam(value = "type", required = false) Integer type, Model m) {
    recordbagService.deleteBagRecord(id);
    m.addAttribute("id", bagId);
    m.addAttribute("type", type);
    m.addAttribute("page.currentPage", page);
    return "redirect:/jy/record/sysList";
  }

  /**
   * 编辑档案袋
   * 
   * @param id
   * @param name
   * @param desc
   * @param m
   * @return
   */
  @RequestMapping("updateRecord")
  public String updateRecordBag(@RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "name", required = false) String name,
      @RequestParam(value = "desc", required = false) String desc, Model m) {
    Recordbag bag = recordbagService.findOne(id);
    bag.setName(name);
    bag.setDesc(desc);
    recordbagService.update(bag);
    return "redirect:/jy/record/findList";
  }

  /**
   * 分享或取消分享档案袋
   * 
   * @param id
   * @param type
   * @param m
   * @return
   */
  @RequestMapping("shareRecord")
  public String shareRecord(@RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "type", required = false) Integer type, Model m) {
    Recordbag bag = recordbagService.findOne(id);
    bag.setShare(type);
    bag.setShareTime(new Date());
    bag.setModifyTime(new Date());
    recordbagService.update(bag);
    // 更新档案袋下的精选资源
    recordService.shareRecordByBagId(id, type);
    return "redirect:/jy/record/findList";
  }

  /**
   * 跳转系统档案袋精选列表页面
   * 
   * @param id
   * @param type
   * @param m
   * @return
   */
  @RequestMapping("findSysList")
  public String findSysList(LessonPlan plan, @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "type", required = false) Integer type, Model m) {
    Recordbag bag = recordbagService.findOne(id);
    String name = plan.getPlanName();
    List<Record> recordList = new ArrayList<Record>();
    User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
    plan.getPage().setPageSize(15);

    plan.setOrgId(user.getOrgId());
    plan.setUserId(user.getId());
    recordbagService.initPlan(plan, bag, m);
    m.addAttribute("word", name);
    if (bag.getName().equals(Recordbag.JYHD)) {
      plan.pageSize(7);
      plan.setPlanName(name);
      recordbagService.initActivity(plan, bag, m, id, recordList, type);
      return "record/activitylist";
    }

    plan.setEnable(1);
    PageList<LessonPlan> planList = lpService.findByPage(plan);
    for (LessonPlan less : planList.getDatalist()) {
      Book book = bookService.findOne(less.getBookId());
      if (book != null) {
        String cebie = book.getFascicule();// 获得册别名称
        Integer cebieId = book.getFasciculeId();// 获得册别Id
        String grade = book.getGradeLevel();// 获得年级名称
        Record record = new Record();
        record.setResId(less.getPlanId());
        record.setResType(Recordbag.switchResType(bag.getName()));
        record.setBagId(id);
        List<Record> list = recordService.find(record, 1);
        if (list == null || list.size() == 0)
          record.setStatus(0);
        else
          record.setStatus(1);
        record.setRecordName(less.getPlanName());
        String pType = "";
        if (less.getPlanType() == ResTypeConstants.FANSI) {
          pType = "课后反思";
        }
        if (less.getPlanType() == ResTypeConstants.FANSI_OTHER) {
          pType = "其他反思";
        }
        if (pType.equals(""))
          record.setFlago("【" + grade + cebie + "】");
        else
          record.setFlago("【" + grade + cebie + "】【" + pType + "】");
        record.setVolume(cebieId);
        record.setCreateTime(less.getCrtDttm());
        String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
        record.setTime(time);
        record.setResType(0);
        record.setPath(less.getResId());// 将资源id放入此字段中负责显示详情
        recordList.add(record);
      }
    }
    PageList<Record> reList = new PageList<Record>(recordList, planList.getPage());// 构造Record
    m.addAttribute("data", reList);// 按照分页进行查询
    m.addAttribute("page", planList.getPage());// 分页
    m.addAttribute("id", id);
    m.addAttribute("type", type);
    m.addAttribute("name1", bag.getName());
    return "record/jxlist";
  }

  /**
   * 跳转系统档案袋精选页面
   * 
   * @param id
   * @param type
   * @param m
   * @return
   */
  @RequestMapping("sysList")
  public String sysList(Record model, @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "type", required = false) Integer type, Model m) {
    Recordbag bag = recordbagService.findOne(id);
    model.setBagId(id);
    model.addOrder("createTime desc");
    if (3 == Recordbag.switchResType(bag.getName()) || 6 == Recordbag.switchResType(bag.getName()))
      model.getPage().setPageSize(15);
    else
      model.getPage().setPageSize(12);
    PageList<Record> reList = recordService.findByPage(model);
    int flag = 0;
    for (Record record : reList.getDatalist()) {
      String time = DateUtils.formatDate(record.getCreateTime(), "yyyy-MM-dd");
      record.setTime(time);
      if (0 == Recordbag.switchResType(bag.getName()) || 1 == Recordbag.switchResType(bag.getName())
          || 2 == Recordbag.switchResType(bag.getName())) {
        recordbagService.saveLessonPlan(record);

      }
      if (3 == Recordbag.switchResType(bag.getName())) {
        flag = recordbagService.saveActivity(record, flag, id);
      }

    }

    m.addAttribute("data", reList);// 按照分页进行查询
    m.addAttribute("page", reList.getCurrentPage());
    m.addAttribute("id", id);
    m.addAttribute("type", type);
    m.addAttribute("name", bag.getName());
    if (flag == 1) {
      return "record/sysActlist";
    } else if (flag == 2) {
      return "record/sysLeclist";
    }
    return "record/syslist";
  }

  /**
   * 精选记录页面
   * 
   * @param id
   * @param type
   * @param m
   * @return
   */
  @RequestMapping("sysJx")
  public String sysJx(@RequestParam(value = "desc", required = false) String desc,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "type", required = false) Integer type,
      @RequestParam(value = "one", required = false) Integer one, Model m) {
    Recordbag bag = recordbagService.findOne(id);
    if (bag.getName().equals(Recordbag.JXSJ) || bag.getName().equals(Recordbag.ZZKJ)
        || bag.getName().equals(Recordbag.JXFS)) {
      recordbagService.savePlan(one, id, type, desc, bag);
    } else if (bag.getName().equals(Recordbag.JYHD)) {
      recordbagService.saveActivity(one, id, type, desc, bag);
    }
    // 新增内容后更新时间
    m.addAttribute("page.currentPage", page);
    m.addAttribute("id", id);
    m.addAttribute("type", type);
    return "redirect:/jy/record/findSysList";
  }

  /**
   * 精选记录页面
   * 
   * @param id
   * @param type
   * @param m
   * @return
   */
  @RequestMapping("sysJxUpdate")
  public String sysJxUpdate(@RequestParam(value = "desc", required = false) String desc,
      @RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "type", required = false) Integer type,
      @RequestParam(value = "one", required = false) Integer one, Model m) {
    Record record = recordService.findOne(one);
    try {
      record.setDesc(URLDecoder.decode(desc, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      logger.error("更新微评失败", e);
    }
    recordService.update(record);
    m.addAttribute("id", record.getBagId());
    m.addAttribute("type", type);
    m.addAttribute("page.currentPage", page);
    return "redirect:/jy/record/sysList";
  }

  /**
   * 跳转自定义档案袋添加页
   * 
   * @param m
   * @return
   */
  @RequestMapping("findRList")
  public String findRList(Record record, @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "type", required = false) Integer type,
      @RequestParam(value = "page", required = false) Integer p, Model m) {
    Recordbag bag = recordbagService.findOne(id);
    record.setBagId(bag.getId());
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    record.setSchoolYear(schoolYear);
    Page page = record.getPage();
    if (p != null)
      page.setCurrentPage(p);
    page.setPageSize(8);
    PageList<Record> list = recordService.findByPage(record);
    for (Record record1 : list.getDatalist()) {
      Resources re = resService.findOne(record1.getPath());
      if (re != null && re.getExt() != null)
        record1.setFlags(re.getExt());
    }
    m.addAttribute("id", bag.getId());
    m.addAttribute("page", list.getCurrentPage());
    m.addAttribute("name", bag.getName());
    m.addAttribute("data", list);
    return "record/selflist";
  }

  /**
   * 保存自己定义的档案资源
   * 
   * @param m
   * @return
   */
  @RequestMapping("save")
  public String save(@RequestParam("id") Integer id, @RequestParam("desc") String desc,
      @RequestParam("resId") String rId, @RequestParam("name") String name,
      @RequestParam(value = "recordId", required = false) Integer recordId, @RequestParam("page") Integer page,
      Model m) {
    Integer schoolYear = (Integer) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_SCHOOLYEAR);
    Recordbag bag = recordbagService.findOne(id);
    Record record = new Record();
    record.setShare(bag.getShare());
    if (recordId == null) {
      record.setStatus(1);
      record.setRecordName(name);
      record.setCreateTime(new Date());
      String time = DateUtils.formatDate(record.getCreateTime(), "MM-dd");
      record.setTime(time);
      record.setBagId(id);
      record.setVolume(1);
      record.setResType(7);
      record.setPath(rId);// 将资源id放入此字段中负责显示详情
      record.setDesc(desc.trim());
      record.setSchoolYear(schoolYear);
      recordbagService.saveSelfRecord(record);
    } else {
      record.setRecordId(recordId);
      record.setRecordName(name);
      record.setDesc(desc);
      record.setPath(rId);// 将资源id放入此字段中负责显示详情
      recordService.updateSelfRecord(record);
    }
    m.addAttribute("id", bag.getId());
    m.addAttribute("page", page);
    return "redirect:/jy/record/findRList";
  }

  /**
   * 保存自己定义的档案资源
   * 
   * @param m
   * @return
   */
  @RequestMapping("updateDesc")
  public String updateDesc(@RequestParam("id") Integer id, @RequestParam("desc") String desc,
      @RequestParam("page") Integer page, Model m) {
    Record record = recordService.findOne(id);
    try {
      record.setDesc(URLDecoder.decode(desc, "UTF-8"));
    } catch (UnsupportedEncodingException e) {
      logger.error("更新微评失败", e);
    }
    recordService.update(record);
    m.addAttribute("id", record.getBagId());
    m.addAttribute("page", page);
    return "redirect:/jy/record/findRList";
  }

  /**
   * 删除精选内容
   * 
   * @param id
   * @param m
   * @return
   */
  @ResponseBody
  @RequestMapping("delete")
  public String delete(@RequestParam(value = "page", required = false) Integer page,
      @RequestParam(value = "id", required = false) Integer id,
      @RequestParam(value = "bagId", required = false) Integer bagId,
      @RequestParam(value = "type", required = false) Integer type, Model m) {
    recordbagService.deleteBagRecord(id);
    m.addAttribute("page", page);
    m.addAttribute("id", bagId);
    m.addAttribute("type", type);
    m.addAttribute("page.currentPage", page);
    return "true";
  }

  /**
   * 更新评论已读状态
   * 
   * @param id
   * @param m
   * @return
   */
  @ResponseBody
  @RequestMapping("updatePingLun")
  public String updatePingLun(@RequestParam(value = "id", required = false) Integer id, Model m) {
    Recordbag bag = recordbagService.findOne(id);
    bag.setPinglun(0);
    recordbagService.update(bag);
    return "true";
  }

  /**
   * 更新阅读已读状态
   * 
   * @param id
   * @param m
   * @return
   */
  @ResponseBody
  @RequestMapping("updateComment")
  public String updateComment(@RequestParam(value = "id", required = false) Integer id, Model m) {
    Recordbag bag = recordbagService.findOne(id);
    bag.setIsStatus(0);
    recordbagService.update(bag);
    return "true";
  }

  /**
   * 检查档案袋中是否有内同
   * 
   * @param id
   * @param m
   * @return
   */
  @ResponseBody
  @RequestMapping("check")
  public String check(@RequestParam(value = "id", required = false) Integer id, Model m) {
    int count = recordbagService.findOne(id).getResCount();
    if (count == 0) {
      return "false";
    }
    return "true";
  }

}