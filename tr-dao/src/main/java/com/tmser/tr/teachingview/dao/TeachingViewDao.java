package com.tmser.tr.teachingview.dao;

import com.tmser.tr.teachingview.vo.SearchVo;

/**
 * 
 * <pre>
 * 教研一览dao
 * </pre>
 *
 * @author wangdawei
 * @version $Id: TeachingViewDao.java, v 1.0 2016年4月11日 下午3:30:17 wangdawei Exp $
 */
public interface TeachingViewDao {

	Integer getCount(SearchVo searchVo, String id) throws Exception;

}
