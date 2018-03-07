package com.tmser.tr.teachingview;

import java.util.ArrayList;
import java.util.List;

public enum TeacherDetailView {

  JIAOANWRITE("jiaoanWrite", "教案撰写数"),
  JIAOANTOTAL("jiaoanTotal", "教案撰写总篇数"),
  JIAOANSHARE("jiaoanShare", "教案分享数"),
  KEJIANWRITE("kejianWrite", "课件撰写数"),
  KEJIANTOTAL("kejianTotal", "课件撰写总篇数"),
  KEJIANSHARE("kejianShare", "课件分享数"),
  FANSIWRITE("fansiWrite", "反思撰写数"),
  FANSITOTAL("fansiTotal", "反思撰写总篇数"),
  FANSISHARE("fansiShare", "反思分享数"),
  LISTEN("listen", "听课记录节数"),
  LISTENSHARE("listenShare", "听课记录分享数"),
  ACTIVITYJOIN("activityJoin", "集体备课参与数"),
  ACTIVITYCANJOIN("activityCanJoin", "集体备课可参与数"),
  SUMMARYWRITE("summaryWrite", "计划总结撰写数"),
  SUMMARYSHARE("summaryShare", "计划总结分享数"),
  THESISWRITE("thesisWrite", "教学文章撰写数"),
  THESISSHARE("thesisShare", "教学文章分享数"),
  COMPANIONMESSAGE("companionMessage", "同伴留言数"),
  COMPANIONRES("companionRes", "同伴资源交流数"),
  TEACHERRECORD("teacherRecordRes", "成长档案袋精选数"),
  SCHOOLACTIVITYCANJOIN("schoolActivityCanJoin", "校际教研可参与数"),
  SCHOOLACTIVITYJOIN("schoolActivityJoin", "校际教研参与数");

  private String id;// 统计项的id
  private String name;// 统计项的名称

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  TeacherDetailView(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public static List<String> getIdsList() {
    List<String> idsList = new ArrayList<String>();
    for (TeacherDetailView tv : values()) {
      idsList.add(tv.getId());
    }
    return idsList;
  }
}
