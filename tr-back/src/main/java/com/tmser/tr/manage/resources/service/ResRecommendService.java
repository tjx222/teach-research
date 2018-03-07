package com.tmser.tr.manage.resources.service;

import java.io.File;
import java.io.IOException;
import com.tmser.tr.back.zygl.vo.ResourcesVo;
import com.tmser.tr.common.service.BaseService;
import com.tmser.tr.manage.resources.bo.ResRecommend;

/**
 * 推荐资源 导入导出
 * 
 * @author csj
 * @version $Id: ResRecommendService.java, v 1.0 2015年2月11日 上午11:12:43 csj Exp $
 */
public interface ResRecommendService extends BaseService<ResRecommend, Integer>{

	/**
	 * 上传资源文件并保存
	 * 扫描本地资源文件夹，将资源导入数据库
	 * @return
	 */
	public boolean saveComRes(File file,String path) throws IOException ;
	
	/**
	 * 批量导入课题资源
	 * @param resdList
	 * @param lessonId
	 * @param comId
	 */
	void batchInsert(ResourcesVo resVo,String lessonId);
	
	/**
	 * 批量删除推荐资源
	 * @param resdList
	 * @param lessonId
	 * @param comId
	 */
	void batchDelete(Integer[] ids);
	
	
	boolean editResRecommend(ResRecommend newRes);
}
