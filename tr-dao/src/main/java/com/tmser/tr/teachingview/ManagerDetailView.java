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
	ACTIVITY_READ("activity_read","集体备课查阅数"),
	ACTIVITY_ALLOW_READ("activity_allow_read","集体备课可查阅数"),
	
	ACTIVITY_ORIGINATION("activity_origination","集体备课发起数"),
	ACTIVITY_SHARE("activity_share","集体备课分享数"),
	
	ACTIVITY_ALLOW_PART_IN("activity_allow_part_in","集体备课可参与数"),
	ACTIVITY_PART_IN("activity_part_in","集体备课参与数");
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
