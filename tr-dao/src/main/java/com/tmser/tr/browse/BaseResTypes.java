package com.tmser.tr.browse;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <pre>
 *  系统基本资源类型
 * </pre>
 *
 * @author tmser
 * @version $Id: BaseResTypes.java, v 1.0 2015年11月10日 下午4:48:26 tmser Exp $
 */
public final class BaseResTypes{

	public static Map<Integer,String> baseResNameMap = new HashMap<Integer, String>();

	/**
	 * 备课资源
	 */
	public static final int BKZY = 1;

	/**
	 * 计划总结
	 */
	public static final int JHZJ = 2;

	/**
	 * 听课记录
	 */
	public static final int TKJL = 3;

	/**
	 * 教学论文
	 */
	public static final int JXLW = 4;

	/**
	 * 成长档案袋
	 */
	public static final int CZDAD = 5;

	/**
	 * 集体备课
	 */
	public static final int JTBK = 6;

	/**
	 * 校际教研
	 */
	public static final int XJJY = 7;

	/**
	 * 区域教研
	 */
	public static final int QYJY = 8;

	/**
	 * 通知公告
	 */
	public static final int TZGG = 9;


	static{
		baseResNameMap.put(BKZY, "备课资源");
		baseResNameMap.put(JHZJ, "计划总结");
		baseResNameMap.put(TKJL, "听课记录");
		baseResNameMap.put(JXLW, "教学论文");
		baseResNameMap.put(CZDAD, "成长档案袋");
		baseResNameMap.put(JTBK, "集体备课");
		baseResNameMap.put(XJJY, "校际教研");
		baseResNameMap.put(QYJY, "区域教研");
		baseResNameMap.put(TZGG, "通知公告");
	}
}

