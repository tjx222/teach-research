package com.tmser.tr.schoolview.controller.resource;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.ResTypeConstants;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.lessonplan.bo.LessonInfo;
import com.tmser.tr.schoolres.service.SchoolResCountService;
import com.tmser.tr.schoolres.service.SchoolResService;
import com.tmser.tr.schoolres.vo.TopResourceVo;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;

/**
 * 热点排行资源控制器
 * <pre>
 *		返回资源的热点排行数据
 * </pre>
 *
 * @author yangchao
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/schoolview/res/topres")
public class TopResourceViewController extends CommonController{	
	@Autowired
	protected SchoolResService schoolResService;
	@Autowired
	protected SchoolResCountService schoolResCountService;
	/**
	 * 得到热点资源排行TopN
	 * @return
	 */
	@RequestMapping("/index/getTopRes")
	public String getPreparationRes(CommonModel cm,Model m){
		//topN
		int N=10;//首页展示Top10
		if(cm.getRestype()==null){
			//查询所有资源类型的排行
			
		}else{
			//查询固定指定类型资源 的排行
			if(cm.getRestype().equals(ResTypeConstants.KEJIAN+"")){
				//课件
				LessonInfo res=new LessonInfo();
				
				TopResourceVo topResourceVo=new TopResourceVo();
				topResourceVo.setResObj(res);
				topResourceVo.setOrgID(cm.getOrgID());
				List<TopResourceVo> ls=schoolResCountService.getTopN(topResourceVo, N);
				if(ls!=null){
					List<TopResourceVo> topRes1=new ArrayList<TopResourceVo>();
					int topRes1Size=ls.size()>5?5:ls.size();
					for(int i=0;i<topRes1Size;i++){
						topRes1.add(ls.get(i));
					}
					m.addAttribute("topRes1",topRes1);
					if(ls.size()>5){
						List<TopResourceVo> topRes2=new ArrayList<TopResourceVo>();
						for(int i=5;i<ls.size();i++){
							topRes2.add(ls.get(i));
						}
						m.addAttribute("topRes2",topRes2);
					}
				}
			}
		}
		handleCommonVo(cm,m);
		return viewName("topRes");
	}

	/**
	 * 热点排行资源详细页(带分页)
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("/getTopResDetailed")
	public String getPreparationResDetailed(CommonModel cm,Model m,TopResourceVo model){
		//cm.setDh("2");
		PageList<TopResourceVo> topResList=schoolResCountService.findByPage(model);
		m.addAttribute("data", topResList);
		handleCommonVo(cm,m);
		return viewName("/topResList");
	}

	
	
}
