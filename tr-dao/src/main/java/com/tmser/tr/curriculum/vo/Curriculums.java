package com.tmser.tr.curriculum.vo;

import java.io.Serializable;
import java.util.List;

import com.tmser.tr.curriculum.bo.Curriculum;

public class Curriculums implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4271573970734849880L;
	private List<Curriculum> curList;

	public List<Curriculum> getCurList() {
		return curList;
	}

	public void setCurList(List<Curriculum> curList) {
		this.curList = curList;
	}
	
	
	
}
