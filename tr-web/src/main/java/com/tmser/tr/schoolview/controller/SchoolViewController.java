package com.tmser.tr.schoolview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.schoolview.vo.CommonModel;
import com.tmser.tr.uc.utils.SessionKey;

/**
 * 学校首页页面容器样式控制器
 * <pre>
 *		返回学校首页展示样式，初始化数据容器
 * </pre>
 *
 * @author yangchao
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/schoolview")
public class SchoolViewController extends CommonController {
	

	/**
	 * 学校首页
	 * @param m
	 * @param orgID
	 * @param xdid
	 * @return
	 */
	@RequestMapping("/index")
	public String index(Model m,CommonModel cm,@RequestParam(required=true)Integer orgID){
		//控制页面展示的模块（未完成）
		m.addAttribute("views", "views");
		
		cm.setOrgID(orgID);
		
		cm.setDh("1");//设置导航菜单栏状态标识
		handleCommonVo(cm, m);
		return "/schoolview/index";
	}
	
	/**
	 * 退出
	 * @param m
	 * @param request
	 * @return
	 */
	@RequestMapping("out")
	public String out(Integer orgID){
		WebThreadLocalUtils.removeSessionAttrbitue(SessionKey.CURRENT_USER);//注销掉session
		return "redirect:index?orgID="+orgID;
	}

}
