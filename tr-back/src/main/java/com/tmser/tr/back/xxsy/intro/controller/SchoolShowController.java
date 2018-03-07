/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.xxsy.intro.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.xxsy.intro.service.SchoolShowService;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.ParseHtmlStrUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.school.show.bo.SchoolShow;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.Identities;

/**
 * 学校展示数据控制器接口
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: SchoolShow.java, v 1.0 2015-09-22 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/back/xxsy/show")
public class SchoolShowController extends AbstractController{
	
	@Autowired 
	private SchoolShowService schoolShowService;
	@Autowired
	private ResourcesService resourcesService;
	
	@Value("#{config.getProperty('front_web_url','')}")
	private String defaultFrontWebUrl;
	
	/**
	 * 学校展示资源列表
	 * @param model
	 * @param m 查询SchoolShow参数容器
	 * @param orgId 学校id
	 * @return
	 */
	@RequestMapping(value="/list")
	public String showList(Model m,SchoolShow model,Integer orgId){
	
		PageList<SchoolShow> schoolShowPageList;
		if(orgId==null){
			Organization org = (Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG); //学校
			if(org==null){
				//模块列表url
				m.addAttribute("orgurl", "/jy/back/xxsy/show/list?type="+model.getType()+"&orgId=");
				//模块加载divId
				m.addAttribute("divId", "schoolShow");
				logger.error("SchoolSHow获取学校ID信息失败");
				return "/back/xxsy/selectOrg";
			}else{
				orgId=org.getId();
			}
		}
		if (orgId!=null) {
			model.setOrgId(orgId);
			schoolShowPageList=schoolShowService.findByPage(model);
		}else{
			schoolShowPageList=schoolShowService.findByPage(model);
		}
		//剔除内容中的HTML标签，形成概览
		List<SchoolShow> datalist=new ArrayList<>();
		for(SchoolShow show:schoolShowPageList.getDatalist()){
			show.setIntroduction(ParseHtmlStrUtils.getTextFromHtml(show.getIntroduction(), "。",100));
			datalist.add(show);
		}
		schoolShowPageList.setDatalist(datalist);
		
		m.addAttribute("orgId", orgId);
		m.addAttribute("schoolShow",model);
		m.addAttribute("schoolShowList", schoolShowPageList);
		m.addAttribute("page",schoolShowPageList.getPage());
		m.addAttribute("type",model.getType());
		return viewName("schoolShowList");
	}
	/**
	 * 
	 * @param m
	 * @param id schoolshow数据ID
	 * @param type schoolshow.type
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEditSchool(Model m,SchoolShow model){
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); //用户
		LoggerUtils.insertLogger(LoggerModule.XXSY, "学校首页——编辑条目，用户："+user.getName()+"ID:"+user.getId()+"条目ID:"+model.getId());
		if (model.getId()!=null) {
			SchoolShow schoolShow=schoolShowService.findOne(model.getId());
			m.addAttribute("schoolShow", schoolShow);
			if(!StringUtils.isBlank(schoolShow.getImages())){
				Map<String,String> imgs=new HashMap<>();
				//图片对应的资源ID
				String [] imgIds=schoolShow.getImages().split(",");
				for(String resoursId:imgIds){
					imgs.put(resoursId, resourcesService.viewResources(resoursId));
				}
				m.addAttribute("imgs", imgs);
			}
		}
		//图片数量限制
		if("master".equals(model.getType())){
			m.addAttribute("imgCount", 1);
		}else if("overview".equals(model.getType())){
			m.addAttribute("imgCount", 10);
		}else if("bignews".equals(model.getType())){
			m.addAttribute("imgCount", 5);
		}
		if(model.getOrgId()==null){
			Organization org = (Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG); //学校
			if(org!=null){
				m.addAttribute("orgId", org.getId());
			}else{
				return "/back/xxsy/error";
			}
		}else{
			m.addAttribute("orgId",model.getOrgId());
		}
		m.addAttribute("id", model.getId());
		m.addAttribute("type", model.getType());
		return viewName("addOrEditSchoolShow");
	}
	/**
	 * 
	 * @param m
	 * @param id schoolshow数据ID
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Model m,String id,@RequestParam(value="type",defaultValue="master") String type){
		if (id!=null) {
			SchoolShow schoolShow=schoolShowService.findOne(id);
			m.addAttribute("shoolShow", schoolShow);
			if(!StringUtils.isBlank(schoolShow.getImages())){
				List<String> imgUrls=new ArrayList<>();
				for(String resoursId:schoolShow.getImages().split(",")){
					imgUrls.add(resourcesService.viewResources(resoursId));
				}
				m.addAttribute("imgUrls", imgUrls);
			}
		}
		m.addAttribute("id", id);
		m.addAttribute("type", type);
		return viewName("viewShow");
	}
	/**
	 * 
	 * @param m
	 * @param id schoolshow数据ID
	 * @return
	 */
	@RequestMapping("/delete")
	public JuiResult delete(String id){
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); //用户
		LoggerUtils.insertLogger(LoggerModule.XXSY, "学校首页——删除条目，用户："+user.getName()+"ID:"+user.getId()+"条目ID:"+id);
		JuiResult rs = new JuiResult();
		try{
			if (id!=null) {
				SchoolShow schoolShow=schoolShowService.findOne(id);
				if(schoolShow!=null){
					for(String resoursId:schoolShow.getImages().split(",")){
						resourcesService.deleteResources(resoursId);
					}
					schoolShowService.delete(id);
				}
				rs.setRel("schoolshow");
				rs.setMessage("删除成功！");
			}else{
				rs.setStatusCode(JuiResult.FAILED);
				rs.setMessage("没有选择任何条目，删除失败！");
			}
		}catch(Exception e){
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败！");
			logger.error("删除学校展示信息失败", e);
		}
		return rs;
	}
	/**
	 * 
	 * @param m
	 * @param imgId img对应的resId
	 * @return
	 */
	@RequestMapping("/deleteImg")
	public JuiResult deleteImg(String imgId){
		JuiResult rs = new JuiResult();
		try{
			if (imgId!=null) {
				resourcesService.deleteResources(imgId);
				rs.setRel("schoolshow");
				rs.setMessage("删除成功！");
			}else{
				rs.setStatusCode(JuiResult.FAILED);
				rs.setMessage("没有选择任何图片，删除失败！");
			}
		}catch(Exception e){
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败！");
			logger.error("学校首页模块操作失败", e);
		}
		return rs;
	}
	/**
	 * 
	 * @param model
	 * @param id schoolshow数据ID
	 * @return
	 */
	@RequestMapping("/batchDelete")
	public JuiResult batchDelete(String ids){
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); //用户
		LoggerUtils.insertLogger(LoggerModule.XXSY, "学校首页——批量删除条目，用户："+user.getName()+"ID:"+user.getId());
		JuiResult rs = new JuiResult();
		try{
			if (ids!=null) {
				for(String id:ids.split(",")){
					try{
						SchoolShow schoolShow=schoolShowService.findOne(id);
						if(schoolShow!=null){
							for(String resoursId:schoolShow.getImages().split(",")){
								resourcesService.deleteResources(resoursId);
							}
							schoolShowService.delete(id);
						}
						rs.setMessage("删除成功！");
					}catch(Exception e){
						rs.setMessage("部分删除成功！");
					}
				}
				rs.setRel("schoolshow");
			}else{
				rs.setStatusCode(JuiResult.FAILED);
				rs.setMessage("没有选择任何条目，删除失败！");
			}
		}catch(Exception e){
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败！");
			logger.error("删除学校展示信息失败", e);
		}
		return rs;
	}
	/**
	 * 
	 * @param m
	 * @param imgFile
	 * @param orgId 学校ID
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public JuiResult saveOrUpdate(SchoolShow m,Integer orgId){
		JuiResult rs = new JuiResult();
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); //用户
		LoggerUtils.insertLogger(LoggerModule.XXSY, "学校首页——新增条目，用户："+user.getName()+"ID:"+user.getId()+";学校ID："+orgId);
		try{
			//处理images（更新resource状态）
			if(m.getFlags()!=null){
				for(String resourceId:m.getFlags().split(",")){
					resourcesService.deleteResources(resourceId);
				}
			}
			if (m.getImages() != null) {
				for(String resourceId:m.getImages().split(",")){
					resourcesService.updateTmptResources(resourceId);
				}
			}
			if(orgId==null){
				Organization org = (Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG); //学校
				orgId=org.getId();
			}
			m.setOrgId(orgId);
			if(m.getId()==null){
				//新增
				m.setCrtDttm(new Date());
				m.setCrtId(user.getId());
				m.setAuthor(user.getName());
				m.setId(Identities.uuid2());
				schoolShowService.save(m);
			}else{
				//更新
				m.setAuthor(user.getName());
				m.setLastupId(user.getId());
				m.setLastupDttm(new Date());
				schoolShowService.update(m);
			}
			rs.setRel("schoolshow");
			rs.setMessage("保存成功！");
		} catch (Exception e) {
			logger.error("学校首页模块操作失败", e);
			rs.setStatusCode(200);
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("保存失败！");
			
		}
		return rs;
	}
	/**
	 * 发布学校展示信息（type：校长风采可发布三条，学校概况只能发布一条）
	 * @param id schoolShow ID
	 * @param orgId 学校ID
	 */
	@RequestMapping("/publish")
	public JuiResult publishItems(String id,Integer orgId,@RequestParam(value="type",defaultValue="master") String type){
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); //用户
		LoggerUtils.insertLogger(LoggerModule.XXSY, "学校首页——发布条目，用户："+user.getName()+"ID:"+user.getId()+";学校ID："+orgId+";类型："+type);
		JuiResult rs = new JuiResult();
		SchoolShow model=new SchoolShow();
		//1表示该条目已发布
		model.setEnable(1);
		model.setOrgId(orgId);
		model.setType(type);
		
		int itemCount=schoolShowService.count(model);
		//条目数量限制（校长风采：<3;学校概况<=1）
		if("overview".equals(type)){
			if(itemCount>=1){
				List<SchoolShow> list = schoolShowService.findAll(model);
				for(SchoolShow show:list){
					show.setEnable(0);
					schoolShowService.update(show);
				}
			}
			//始终发布一条数据
				SchoolShow schoolShow=schoolShowService.findOne(id);
				schoolShow.setEnable(1);
				schoolShowService.update(schoolShow);
			
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("发布成功！");
			//前台访问的路径
			rs.setMessage(defaultFrontWebUrl+"/jy/schoolview/show/school_survey"+"?orgID="+orgId+"&showId="+schoolShow.getId());
		}else if("master".equals(type)){
			if(itemCount>=3){
				rs.setStatusCode(JuiResult.FAILED);
				rs.setMessage("该学校校长风采已发布了三条，发布的条目个数超过上限！");
			}else{
				SchoolShow schoolShow=schoolShowService.findOne(id);
				schoolShow.setEnable(1);
				schoolShowService.update(schoolShow);
				
				rs.setMessage(defaultFrontWebUrl+"/jy/schoolview/show/school_survey"+"?orgID="+orgId+"&showId="+schoolShow.getId());
			}
		}else if("bignews".equals(type)){
			SchoolShow schoolShow=schoolShowService.findOne(id);
			schoolShow.setEnable(1);
			schoolShowService.update(schoolShow);
			
			rs.setMessage(defaultFrontWebUrl+"/jy/schoolview/show/school_survey"+"?orgID="+orgId+"&showId="+schoolShow.getId());
		}
		return rs;
	} 
	/**
	 * 发布学校展示信息（type：校长风采可发布三条，学校概况只能发布一条）
	 * @param id schoolShow ID
	 * @param orgId 学校ID
	 */
	@RequestMapping("/unpublish")
	public JuiResult unpublish(String id,Integer orgId,@RequestParam(value="type",defaultValue="master") String type){
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); //用户
		LoggerUtils.insertLogger(LoggerModule.XXSY, "学校首页——取消发布条目，用户："+user.getName()+"ID:"+user.getId()+";学校ID："+orgId+";类型："+type);
		JuiResult rs = new JuiResult();
		try{
			
			SchoolShow schoolShow=schoolShowService.findOne(id);
			schoolShow.setEnable(0);
			schoolShowService.update(schoolShow);
			
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("取消发布成功！");
		}catch(Exception e){
			logger.error("学校首页模块操作失败", e);
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("取消发布失败！");
		}
		return rs;
	}
	/**
	 * 学校要闻置顶
	 * @param id
	 * @param orgId
	 * @param type
	 * @return
	 */
	@RequestMapping("/setTop")
	public JuiResult setTop(String id,Integer orgId,@RequestParam(value="type",defaultValue="master") String type){
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); //用户
		LoggerUtils.insertLogger(LoggerModule.XXSY, "学校首页——置顶条目，用户："+user.getName()+"ID:"+user.getId()+";学校ID："+orgId+";类型："+type);
		JuiResult rs = new JuiResult();
		try{
			SchoolShow model=new SchoolShow();
			model.addCustomCondition("order by topTag desc", new HashMap<String, Object>());
			List<SchoolShow> schoolShowlist=schoolShowService.find(model, 1);
			
			int topTag=1;
			if(schoolShowlist!=null){
				if(schoolShowlist.get(0)!=null){
					if(schoolShowlist.get(0).getTopTag()!=null)
						topTag=schoolShowlist.get(0).getTopTag()+1;
				}
			}
			SchoolShow schoolShow=schoolShowService.findOne(id);
			schoolShow.setTopTag(topTag);
			schoolShowService.update(schoolShow);
			
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("成功置顶！");
		}catch(Exception e){
			logger.error("学校首页模块操作失败", e);
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("取消置顶失败！");
		}
		return rs;
	}
	/**
	 * 学校要闻取消置顶
	 * @param id
	 * @param orgId
	 * @param type
	 * @return
	 */
	@RequestMapping("/cancelTop")
	public JuiResult cancelTop(String id,Integer orgId,@RequestParam(value="type",defaultValue="master") String type){
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); //用户
		LoggerUtils.insertLogger(LoggerModule.XXSY, "学校首页——取消置顶条目，用户："+user.getName()+"ID:"+user.getId()+";学校ID："+orgId+";类型："+type);
		JuiResult rs = new JuiResult();
		try{
			
			SchoolShow schoolShow=schoolShowService.findOne(id);
			schoolShow.setTopTag(0);
			schoolShowService.update(schoolShow);
			
			rs.setStatusCode(JuiResult.SUCCESS);
			rs.setMessage("成功取消置顶！");
		}catch(Exception e){
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("取消置顶失败！");
			logger.error("学校首页模块操作失败", e);
		}
		return rs;
	}
}