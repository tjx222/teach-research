/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.meta.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.manage.meta.vo.BookLessonVo;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: BookChapterHerperService.java, v 1.0 2015年2月3日 上午9:29:31 tmser Exp $
 */
public interface BookChapterHerperService {
	/**
	 * 通过书籍id（com_id）获取对应的目录章节数据
	 * @param comId
	 * @return
	 */
	public List<BookLessonVo> getBookChapterByComId(String comId);
	
	/**
	 * 根据书id从缓存中获取课题map
	 * @param comId
	 * @return
	 */
	public Map<String,BookLessonVo> getBookChapterMapByComId(String comId);
	
	/**
	 * 根据书id从缓存中获取课题map的层级结构，并返回对象集合
	 * @param comId
	 * @return
	 */
	public List<BookLessonVo> getBookChapterTreeByComId(String comId);
}
