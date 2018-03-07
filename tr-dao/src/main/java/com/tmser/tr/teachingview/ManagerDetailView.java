package com.tmser.tr.teachingview;

import java.util.ArrayList;
import java.util.List;

public enum ManagerDetailView {
	
	
	JIAOAN_READ("jiaoan_read","教案查阅数"),
	JIAOAN_ALLOW_READ("jiaoan_allow_read","教案可查阅数"),
	KEJIAN_READ("kejian_read","课件查阅数"),
	KEJIAN_ALLOW_READ("kejian_allow_read","课件可查阅数"),
	FANSI_READ("fansi_read","反思查阅数"),
	FANSI_ALLOW_READ("fansi_allow_read","反思可查阅数"),
	PLAN_SUMMAREY_READ("plan_summary_read","计划总结查阅数"),
	PLAN_SUMMAREY_ALLOW_READ("plan_summary_allow_read","计划总结可查阅数"),
	PERSONPLAN_WRITE("personplan_write","计划总结撰写数"),
	PERSONPLAN_SHARE("personplan_share","计划总结分享数"),
	ACTIVITY_READ("activity_read","集体备课查阅数"),
	ACTIVITY_ALLOW_READ("activity_allow_read","集体备课可查阅数"),
	
	ACTIVITY_ORIGINATION("activity_origination","集体备课发起数"),
	ACTIVITY_SHARE("activity_share","集体备课分享数"),
	
	ACTIVITY_ALLOW_PART_IN("activity_allow_part_in","集体备课可参与数"),
	ACTIVITY_PART_IN("activity_part_in","集体备课参与数"),
	
	LECTURE_HOURS("lecture_hours","听课记录节数"),
	LECTURE_SHARE("lecture_share","听课记录分享数"),
	
	LECTURE_READ("lecture_read","听课记录查阅数"),
	LECTURE_ALLOW_READ("lecture_allow_read","听课记录可查阅数"),
	
	THESIS_WRITE("thesis_write","教学文章撰写数"),
	THESIS_SHARE("thesis_share","教学文章分享数"),
	
	THESIS_READ("thesis_read","教学文章查阅数"),
	THESIS_ALLOW_READ("thesis_allow_read","教学文章可查阅数"),
	
	COMPANION_MESSAGE("companion_message","同伴互助留言数"),
	COMPANION_DISCUSS("companion_discuss","同伴互助资源交流数"),
	SCHOOL_ACTIVITY_LAUNCH("school_activity_launch","校际教研发起数"),
	SCHOOL_ACTIVITY_SHARE("school_activity_share","校际教研分享数"),
	
	SCHOOL_ACTIVITY_ALLOW_PART_IN("school_activity_allow_part_in","校际教研可参与数"),
	SCHOOL_ACTIVITY_PART_IN("school_activity_part_in","校际教研参与数");
	private String id;//统计项的id
	private String name;//统计项的名称
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	ManagerDetailView(String id,String name){
		this.id = id;
		this.name= name;
	}
	
	public static List<String> getIdsList(){
		List<String> idsList = new ArrayList<String>();
		for(ManagerDetailView tv:values()){
			idsList.add(tv.getId());
		}
		return idsList;
	}
	public static List<String> getActivityList(){
		List<String> idsList = new ArrayList<String>();
		for(ManagerDetailView tv:values()){
			idsList.add(tv.getId());
		}
		return idsList;
	}
}
