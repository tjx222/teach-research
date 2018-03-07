package com.tmser.tr.plainsummary.vo;

import java.io.Serializable;

/**
 * 
 * <pre>
 *	计划总结简单统计
 * </pre>
 *
 * @author wanzheng
 * @version $Id: PlainSummarySimpleCountVo.java, v 1.0 Jun 23, 2015 7:41:40 PM wanzheng Exp $
 */
public class PlainSummarySimpleCountVo implements Serializable{
	
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 撰写数目
	 */
	private Integer PlainSummaryNum;
	
	/**
	 * 提交数目
	 */
	private Integer PlainSummarySubmitNum;
	
	/**
	 * 查阅数目
	 */
	private Integer PlainSummaryCheckedNum;

	public Integer getPlainSummaryNum() {
		return PlainSummaryNum;
	}

	public void setPlainSummaryNum(Integer plainSummaryNum) {
		PlainSummaryNum = plainSummaryNum;
	}

	public Integer getPlainSummarySubmitNum() {
		return PlainSummarySubmitNum;
	}

	public void setPlainSummarySubmitNum(Integer plainSummarySubmitNum) {
		PlainSummarySubmitNum = plainSummarySubmitNum;
	}

	public Integer getPlainSummaryCheckedNum() {
		return PlainSummaryCheckedNum;
	}

	public void setPlainSummaryCheckedNum(Integer plainSummaryCheckedNum) {
		PlainSummaryCheckedNum = plainSummaryCheckedNum;
	}

	
}
