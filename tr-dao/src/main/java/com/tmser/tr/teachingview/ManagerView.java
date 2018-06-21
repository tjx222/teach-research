package com.tmser.tr.teachingview;

import java.util.ArrayList;
import java.util.List;

public enum ManagerView {
	
	
	JIAOAN_READ("jiaoan_read","教案查阅数"),
	KEJIAN_READ("kejian_read","课件查阅数"),
	FANSI_READ("fansi_read","反思查阅数"),
	ACTIVITY_READ("activity_read","集体备课查阅数"),
	ACTIVITY_ORIGINATION("activity_origination","集体备课发起数");
	
	private String id;//统计项的id
	private String name;//统计项的名称
	
	public String getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	ManagerView(String id,String name){
		this.id = id;
		this.name= name;
	}
	
	public static List<String> getIdsList(){
		List<String> idsList = new ArrayList<String>();
		for(ManagerView tv:values()){
			idsList.add(tv.getId());
		}
		return idsList;
	}
	
}
