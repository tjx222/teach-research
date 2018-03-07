package com.tmser.tr.schoolres.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.browse.bo.BrowsingCount;
import com.tmser.tr.browse.dao.BrowsingCountDao;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.lessonplan.bo.LessonPlan;
import com.tmser.tr.schoolres.service.SchoolResCountService;
import com.tmser.tr.schoolres.vo.TopResourceVo;
import com.tmser.tr.writelessonplan.service.LessonPlanService;

@Service
@Transactional
public class SchoolResCountServiceImpl implements SchoolResCountService {
	
	@Autowired
	private BrowsingCountDao browsingCountDao;

	@Autowired
	private LessonPlanService lessonPlanService;
	

	@Override
	public List<TopResourceVo> getTopN(TopResourceVo t, int N) {
		// TODO 获取TopN的资源数据详情(只统计出单种类型的资源TOP集合)
		List<TopResourceVo> ts=new ArrayList<>();
		BrowsingCount model=new BrowsingCount();
		model.setResShare(true);//已分享
		model.setOrgId(t.getOrgID());
		model.addOrder("count desc");
		if(t.getResObj() instanceof LessonInfo){
			model.setType(1);
			List<BrowsingCount> bs=browsingCountDao.list(model, N);
			for(BrowsingCount bc:bs){
				TopResourceVo tv=new TopResourceVo();
				LessonPlan lessonPlan=lessonPlanService.findOne(bc.getResId());
				if(lessonPlan!=null){
					tv.setResObj(lessonPlan);
				}
				tv.setBc(bc);
				ts.add(tv);
				
			}
		}
		return ts;
	}


	@Override
	public PageList<TopResourceVo> findByPage(TopResourceVo t) {
		// TODO 分页查询(需要转换对象)
		List<TopResourceVo> datalist = new ArrayList<>();
		
		//查询model
		BrowsingCount model=new BrowsingCount();
		model.setResShare(true);//已分享
		model.setOrgId(t.getOrgID());
		model.addOrder("count desc");
		model.setType(1);
		int totalCount=browsingCountDao.count(model);
		t.getPage().setTotalCount(totalCount);
		model.currentPage(t.getPage().getCurrentPage());
		model.pageSize(t.getPage().getPageSize());
		
		PageList<BrowsingCount> bs=browsingCountDao.listPage(model);
		
		for(BrowsingCount bc:bs.getDatalist()){
			TopResourceVo tv=new TopResourceVo();
			LessonPlan lessonPlan=lessonPlanService.findOne(bc.getResId());
			if(lessonPlan!=null){
				tv.setResObj(lessonPlan);
			}
			tv.setBc(bc);
			datalist.add(tv);
			
		}
		PageList<TopResourceVo> pageList=new PageList<>(datalist, t.getPage());
		return pageList;
	}

}
