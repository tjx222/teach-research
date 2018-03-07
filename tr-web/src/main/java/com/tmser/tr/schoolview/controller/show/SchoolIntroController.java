package com.tmser.tr.schoolview.controller.show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.school.show.bo.SchoolShow;
import com.tmser.tr.school.show.service.SchoolShowService;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;

/**
 * 学校简介信息和学校要闻控制器
 * <pre>
 *		返回学校简介信息和学校要闻的数据
 * </pre>
 *
 * @author yangchao
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/schoolview/show/")
public class SchoolIntroController extends CommonController{
	
	@Autowired
	private SchoolShowService schoolShowService;
	@Autowired
	private ResourcesService resourcesService;
	
	/**
	 * 校长风采或学校概况数据
	 * @param m
	 * @param orgID
	 * @param type
	 * @return json
	 */
	@RequestMapping("/viewShow")
	private String viewShow(CommonModel cm,Model m,SchoolShow model) {
		//校长风采
		model.setOrgId(cm.getOrgID());
		model.setEnable(1);
		
		List<SchoolShow> schoolshows=new ArrayList<>();
		if("master".equals(model.getType())){
			schoolshows=schoolShowService.find(model,3);
			for(SchoolShow master:schoolshows){
				if(!StringUtils.isBlank(master.getImages())){
					for(String resoursId:master.getImages().split(",")){
						Resources resource=resourcesService.findOne(resoursId);
						if(resource!=null)
							master.setImages(resource.getPath());
						break; //只展示第一张图片
					}
				}
			}
			m.addAttribute("masters", schoolshows);
			return "/schoolview/show/xxjj/indexMaster";
		}else if("overview".equals(model.getType())){
			schoolshows=schoolShowService.find(model, 1);
			for(SchoolShow overview:schoolshows){
				if(!StringUtils.isBlank(overview.getImages())){
					for(String resoursId:overview.getImages().split(",")){
						Resources resource=resourcesService.findOne(resoursId);
						if(resource!=null)
							overview.setImages(resource.getPath());
						break; //只展示第一张图片
					}
				}
			}
			m.addAttribute("overviews", schoolshows);
			return "/schoolview/show/xxjj/indexOverview";
		}else if("bignews".equals(model.getType())){
			model.addCustomCondition(" order by topTag desc,crtDttm desc", new HashMap<String, Object>());

			schoolshows=schoolShowService.find(model, 5);
			m.addAttribute("bignews", schoolshows);
			return "/schoolview/show/xxjj/indexBigNews";
		}
		   return null;
	}
	
	/**
	 * 学校简介信息查看页面
	 * @param m
	 * @param showId schoolShow。id
	 * @return
	 */
	@RequestMapping("/school_survey")
	public String school_survey(Model m,CommonModel cm,String showId){
		if(showId!=null){
			SchoolShow schoolShow=schoolShowService.findOne(showId);
			
			Map<String, Object> paramMap=new HashMap<>();
			paramMap.put("datetime", schoolShow.getCrtDttm());
			SchoolShow model=new SchoolShow();
			model.setEnable(1);
			model.setOrgId(cm.getOrgID());
			model.setType(schoolShow.getType());
			model.addOrder("crt_dttm desc");
			model.addCustomCondition("and crt_dttm<:datetime", paramMap);
			List<SchoolShow> nextShow=schoolShowService.find(model, 1);
			SchoolShow model1=new SchoolShow();
			model1.setEnable(1);
			model1.setOrgId(cm.getOrgID());
			model1.setType(schoolShow.getType());
			model1.addOrder("crt_dttm asc");
			model1.addCustomCondition("and crt_dttm>:datetime", paramMap);
			List<SchoolShow> previousShow=schoolShowService.find(model1, 1);
			
			m.addAttribute("nextShow",nextShow);
			m.addAttribute("previousShow",previousShow);
			
			m.addAttribute("schoolShow", schoolShow);
			if(!StringUtils.isBlank(schoolShow.getImages())){
				List<String> imgUrls=new ArrayList<>();
				for(String resoursId:schoolShow.getImages().split(",")){
					Resources resource=resourcesService.findOne(resoursId);
					if(resource!=null)
						imgUrls.add(resource.getPath());
				}
				m.addAttribute("imgUrls", imgUrls);
			}
		}
		handleCommonVo(cm, m);
		return "/schoolview/show/xxjj/school_survey";
	}
	/**
	 * 学校要闻列表
	 * @param m
	 * @param showId schoolShow。id
	 * @return
	 */
	@RequestMapping("/bigNewsList")
	public String bigNewsList(Model m,CommonModel cm,SchoolShow model){
		if(cm.getOrgID()!=null){
			model.addCustomCondition(" order by topTag desc,crtDttm desc", new HashMap<String, Object>());

			model.setOrgId(cm.getOrgID());
			model.pageSize(10);
			model.setType("bignews");
			model.setEnable(1);//已发布
			PageList<SchoolShow> newsList=schoolShowService.findByPage(model);
			int count=schoolShowService.findAll(model).size();
			m.addAttribute("count", count);
			m.addAttribute("data", newsList);
		}
		handleCommonVo(cm, m);
		return "/schoolview/show/xxjj/bigNewsList";
	}
}
