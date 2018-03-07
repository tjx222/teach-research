package com.tmser.tr.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre>
 *  资源类型常量
 * </pre>
 *
 * @author tmser
 * @version $Id: ResTypeConstants.java, v 1.0 2015年3月28日 下午4:48:26 tmser Exp $
 */
public final class ResTypeConstants{
	
public static Map<Integer,String> resNameMap = new HashMap<Integer, String>();
	
	
	/**
	 * 查阅资源类型
	 * 课题-教案
	 */
	public static final int JIAOAN = 0;
	
	/**
	 * 查阅资源类型
	 * 课题-课件
	 */
	public static final int KEJIAN = 1;
	
	/**
	 * 查阅资源类型
	 * 课题-反思
	 */
	public static final int FANSI = 2;
	
	/**
	 * 查阅资源类型
	 * 其他反思
	 */
	public static final int FANSI_OTHER = 3;
	

	/**计划总结-计划**/
	public static final int TPPLAIN_SUMMARY_PLIAN=8;
	
	/**计划总结--总结**/
	public static final int TPPLAIN_SUMMARY_SUMMARY=9;
	
	/**集体备课**/
	public static final int ACTIVITY = 5;
	
	/**
	 * 听课记录
	 */
	public static final int LECTURE = 6;
	
	/**
	 * 成长档案袋
	 */
	public static final int RECORD_BAG = 7;
	
	/**
	 * 教学文章
	 */
	public static final int JIAOXUELUNWEN = 10;
	
	/**
	 * 校际教研
	 */
	public static final int SCHOOLTEACH = 12;
	
	/**
	 * 自制档案
	 */
	public static final int ZIZHIRECORD = 13;
	
	/**
	 * 教学研讨
	 */
	public static final int AREA_JXYT = 31;
	
	/**
	 * 在线观课
	 */
	public static final int AREA_ZXGK = 32;
	
	/**
	 * 专家指导
	 */
	public static final int AREA_ZJZD = 33;
	/**
	 * 专家指导——实时直播
	 */
	public static final int AREA_ZJZD_SSZB = 331;
	
	/**
	 * 专家指导——视频研讨
	 */
	public static final int AREA_ZJZD_SPYT = 332;
	
	/**
	 * 课堂评价
	 */
	public static final int AREA_KTPJ = 34;
	/**
	 * 同伴互助
	 */
	public static final int COMPANION = 35;
	
	/**
	 * 通知公告
	 */
	public static final int ANNUNCIATE = 36;
	/**
	 * 课题研究
	 */
	public static final int AREA_KTYJ = 37;

	
	static{
		resNameMap.put(JIAOAN, "教案");
		resNameMap.put(KEJIAN, "课件");
		resNameMap.put(FANSI, "反思");
		resNameMap.put(FANSI_OTHER, "其它反思");
		resNameMap.put(LECTURE, "听课记录");
		resNameMap.put(TPPLAIN_SUMMARY_PLIAN, "计划总结");
		resNameMap.put(TPPLAIN_SUMMARY_SUMMARY, "计划总结");
		resNameMap.put(ACTIVITY, "集体备课");
		resNameMap.put(RECORD_BAG, "档案袋");
		resNameMap.put(JIAOXUELUNWEN, "教学文章");
		resNameMap.put(SCHOOLTEACH, "校际教研");
		resNameMap.put(ZIZHIRECORD, "自制档案");
		resNameMap.put(COMPANION, "同伴互助");
		resNameMap.put(ANNUNCIATE, "通知公告");
		resNameMap.put(AREA_JXYT, "教学研讨");
		resNameMap.put(AREA_ZXGK, "在线观课");
		resNameMap.put(AREA_ZJZD, "专家指导");
		resNameMap.put(AREA_KTPJ, "课堂评价");
		resNameMap.put(AREA_KTYJ, "课题研究");
	}
}

