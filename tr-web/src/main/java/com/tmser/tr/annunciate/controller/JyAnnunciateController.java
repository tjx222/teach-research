/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.annunciate.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.annunciate.bo.AnnunciatePunishView;
import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.bo.JyRedTitle;
import com.tmser.tr.annunciate.service.AnnunciatePunishViewService;
import com.tmser.tr.annunciate.service.JyAnnunciateService;
import com.tmser.tr.annunciate.service.JyRedTitleService;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.MobileUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.StringUtils;

/**
 * 通告控制器接口
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyAnnunciate.java, v 1.0 2015-06-12 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/annunciate/")
public class JyAnnunciateController extends AbstractController {

	@Autowired
	private JyAnnunciateService jyAnnunciateService;
	
	@Autowired
	private JyRedTitleService jyRedTitleService;
		
	@Autowired
	private ResourcesService resourcesService;
	
	@Autowired
	private AnnunciatePunishViewService annunciatePunishViewService;
	
	@Autowired
	private OrganizationService organizationService;
	
	
	/**
	 * 发布的通知公告列表
	 * @param vo
	 * @return
	 */
	@RequestMapping("/punishs")
	public String getPunishs(Model m,JyAnnunciateVo vo,Page page, Integer orgID){
		vo.currentPage(1);
		vo.setStatus(1);
		page.setPageSize(10);
		vo.addPage(page);
		vo.addOrder("isTop desc,lastupDttm desc");
		//草稿箱中通知公告数目 draftNum
		JyAnnunciate ja=new JyAnnunciate();
		ja.setStatus(0);
		ja.setUserId(CurrentUserContext.getCurrentUserId());
		ja.setIsDelete(0);
		if (orgID==null) {
			ja.setOrgId(CurrentUserContext.getCurrentSpace().getOrgId());
		}else {
			ja.setOrgId(orgID);
		}
		Integer draftNum=jyAnnunciateService.count(ja);
		PageList<JyAnnunciate> pagelist = jyAnnunciateService.getAnnunciateList(vo,m);
		m.addAttribute("pagelist", pagelist);
		m.addAttribute("draftNum", draftNum);
		m.addAttribute("userId", CurrentUserContext.getCurrentUserId());
		return viewName("annunciate_index");
	}
	
	/**
	 * 未转发的通知公告列表
	 * @param vo
	 * @return
	 */
	@RequestMapping("/notForwardIndex")
	public String notForwardIndex(Model m,JyAnnunciateVo vo,Page page, Integer orgID){
		Integer orgId=CurrentUserContext.getCurrentUser().getOrgId();
		vo.currentPage(1);
		vo.setStatus(1);
		page.setPageSize(10);
		vo.addPage(page);
		vo.addOrder("isTop desc,lastupDttm desc");
		vo.addCustomCondition(" and orgsJoinIds like '%,"+orgId+",%' and forwardDescription not like '%,"+orgId+",%'", new HashMap<String, Object>());
		PageList<JyAnnunciate> pagelist = jyAnnunciateService.findByPage(vo);
		m.addAttribute("pagelist", pagelist);
		m.addAttribute("userId", CurrentUserContext.getCurrentUserId());
		return viewName("annunciate_forward_index");
	}
	
	/**
	 * 进入发布通告页面
	 * 
	 * @param annunciate
	 * @return
	 */
	@RequestMapping(value = "release", method = RequestMethod.GET)
	@UseToken
	public String enterRelease(Model m,Integer id,Integer type) {
		if (id!=null) {
			JyAnnunciate jaAnnunciate=jyAnnunciateService.findOne(id);
			List<Organization> orgList = organizationService.getOrgListByIdsStr(jaAnnunciate.getOrgsJoinIds());
			m.addAttribute("orgList", orgList);
			m.addAttribute("jaAnnunciate", jaAnnunciate);
			if (jaAnnunciate.getRedTitleId()!=0) {
				JyRedTitle jrt=jyRedTitleService.findOne(jaAnnunciate.getRedTitleId());
		    	 m.addAttribute("jrt", jrt);
			}
			getAnnunciateAttachs(jaAnnunciate.getAttachs(), m);
		}
		JyRedTitle search = new JyRedTitle();
		search.setIsEnable(1);
		search.setIsDelete(0);
		search.setOrgId(CurrentUserContext.getCurrentSpace().getOrgId());
		search.addOrder("lastupDttm desc");
		List<JyRedTitle> list = jyRedTitleService.findAll(search);
		m.addAttribute("list", list);
		m.addAttribute("type", type);
		return viewName("annunciate_release");
	}
	/**
	 * 新增通告
	 * 
	 * @param annunciate
	 * @return
	 */
	@RequestMapping(value = "annunciates", method = RequestMethod.POST)
	@UseToken
	public Result addAnnunciates(JyAnnunciate annunciate,Integer status) {
		Result result=new Result();
		try {
			jyAnnunciateService.saveAnnunciate(annunciate, status);
			result.setCode(1);
		} catch (Exception e) {
			logger.error("新增通告失败", e);
			result.setCode(0);
			result.setMsg("新增通告失败");
		}

		return result;
	}

	/**
	 * 获取通告详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("view")
	public String getAnnunciate(Integer id,Integer status,Model m,Integer type) {
		JyAnnunciate j1=getFirstAnnunciates(id, status,0,type);
		JyAnnunciate j2=getLastAnnunciates(id, status,0,type);
		if (id!=null) {
			if (id.equals(j1.getId())) {
				m.addAttribute("isFirst", true);
			}
			if (id.equals(j2.getId())) {
				m.addAttribute("isLast", true);
			}
			JyAnnunciate  ja=jyAnnunciateService.findOne(id);
		    m.addAttribute("ja", ja);
		    m.addAttribute("type", type);
		    if (ja.getRedTitleId()!=0) {
		    	JyRedTitle jrt=jyRedTitleService.findOne(ja.getRedTitleId());
		    	 m.addAttribute("jrt", jrt);
			}
		    getAnnunciateAttachs(ja.getAttachs(), m);
		}
		return "/annunciate/annunciate_view";
	}
	
	/**
	 * 获取通告附件
	 * 
	 * @param id
	 * @return
	 */
	public  void  getAnnunciateAttachs(String attachs,Model m){
		if (StringUtils.isNotEmpty(attachs)) {
	    	String att[]=attachs.split("#");
		    List<Resources> rList= new ArrayList<Resources>();
		    for (int i = 0; i < att.length; i++) {
		    	Resources resource=resourcesService.findOne(att[i]);
		    	rList.add(resource);
			}
		    m.addAttribute("attachSum", att.length);
		    m.addAttribute("rList", rList);
		}
	}  

	/**
	 * 更新通告详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "annunciates/{id}", method = RequestMethod.PUT)
	public Result updateAnnunciate(@PathVariable("id") Integer id,
			 JyAnnunciate annunciate) {
		Result result=new Result();
		annunciate.setId(id);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("uerId", CurrentUserContext.getCurrentUserId());
		annunciate.addCustomCondition("userId=:userId", paramMap);
		try {
			jyAnnunciateService.update(annunciate);
		} catch (Exception e) {
			logger.error("修改失败",e);
			result.setMsg("修改失败");
			result.setCode(0);		
		}

		return result;
	}
	/**
	 * 进入草稿箱中
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("draft")
	public String annunciateDraft(Model m,JyAnnunciateVo vo,Page page) {
		vo.currentPage(1);
		vo.setStatus(0);
		vo.setUserId(CurrentUserContext.getCurrentUserId());
		if(MobileUtils.isNormal()){
			page.setPageSize(10);
    	}else{
    		page.setPageSize(1000);
    		
    	}
		vo.addPage(page);
		vo.addOrder("lastupDttm desc");
		PageList<JyAnnunciate> draftlist = jyAnnunciateService.getAnnunciateList(vo,m);
		m.addAttribute("draftlist", draftlist);
		return viewName("annunciate_draft");
	}

	/**
	 * 删除草稿箱中的通告
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteAnnunciateDraft")
	public Result deleteAnnunciate(Integer id) {
		Result result = new Result();
		try {
			JyAnnunciate ja2=jyAnnunciateService.findOne(id);
			if (ja2!=null) {
				String atta[]=ja2.getAttachs().split("#");
				for (int i = 0; i < atta.length; i++) {
					resourcesService.deleteResources(atta[i]);
				}
			}
			JyAnnunciate ja=new JyAnnunciate();
			ja.setId(id);
			ja.setIsDelete(1);
			jyAnnunciateService.update(ja);
			result.setCode(1);
		} catch (Exception e) {
			result.setCode(0);
			logger.error("删除失败");
		}
		return result;
	}
	/**
	 * 移动端—删除草稿箱中的通告
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("deleteDraft")
	public Result deleteAnnunciateDraft(Integer id) {
		Result result=new Result();
		try {
			JyAnnunciate ja=new JyAnnunciate();
			ja.setId(id);
			ja.setIsDelete(1);
			jyAnnunciateService.update(ja);
		} catch (Exception e) {
			logger.error("删除失败",e);
			result.setMsg("删除失败");
			result.setCode(0);
		}
		return result;
	}
	
	/**
	 * 更新通告详情状态
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "annunciates/{id}/status", method = RequestMethod.PUT)
	public Result updateAnnunciateStatus(@PathVariable("id") Integer id,
			@RequestParam("status") Integer status) {
		Result result=new Result();
		try {
			JyAnnunciate model = new JyAnnunciate();
			model.setId(id);
			model.setStatus(status);
			jyAnnunciateService.update(model);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("修改失败",e);
			result.setMsg("修改失败");
			result.setCode(0);
		}
		return result;
	}
	/**
	 * 获取上一篇通告详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("previousAnnunciates")
	public String getPreviousAnnunciates(Model model,Integer id,Integer status,Integer type){
		JyAnnunciate jyAnnunciate=getFirstAnnunciates(id, status,1,type);
		if (id.equals(jyAnnunciate.getId())) {
			model.addAttribute("isFirst", true);
		}
		JyAnnunciate ja=jyAnnunciateService.getPreAnnunciate(id, CurrentUserContext.getCurrentUser().getOrgId(), status,type);
		if (ja==null) {
			ja=jyAnnunciateService.findOne(id);
			model.addAttribute("isFirst", true);
		}
		if (ja.getRedTitleId()!=0) {
			model.addAttribute("jrt", jyRedTitleService.findOne(ja.getRedTitleId()));
		}
		model.addAttribute("ja", ja);
		if (StringUtils.isNotEmpty(ja.getAttachs())) {
			getAnnunciateAttachs(ja.getAttachs(), model);
		}
		if (type!=null&&type!=' ') {
			getReadPunishAnnunciate(ja.getId(), ja.getStatus());
		}
		model.addAttribute("type", type);
		return "/annunciate/annunciate_view";
	}
	/**
	 * 获取下一篇通告详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("nextAnnunciates")
	public String getNextAnnunciates(Model model,Integer id,Integer status,Integer type){
		JyAnnunciate jyAnnunciate=getLastAnnunciates(id, status,1,type);
		if (id.equals(jyAnnunciate.getId())) {
			model.addAttribute("isLast", true);
		} 
		JyAnnunciate ja=jyAnnunciateService.getNextAnnunciate(id, CurrentUserContext.getCurrentUser().getOrgId(), status,type);
		if (ja==null) {
			ja=jyAnnunciateService.findOne(id);
			model.addAttribute("isLast", true);
		}
		if (ja.getRedTitleId()!=0) {
			model.addAttribute("jrt", jyRedTitleService.findOne(ja.getRedTitleId()));
		}
		model.addAttribute("ja", ja);
		if (StringUtils.isNotEmpty(ja.getAttachs())) {
			getAnnunciateAttachs(ja.getAttachs(), model);
		}	
		if (type!=null&&type!=' ') {
			getReadPunishAnnunciate(ja.getId(), ja.getStatus());
		}
		model.addAttribute("type", type);
		return "/annunciate/annunciate_view";
	}
	
	/**
	 * 获取第一篇通告详情
	 * 
	 * @param id
	 * @return
	 */
	public JyAnnunciate getFirstAnnunciates(Integer id,Integer status,Integer i,Integer type){
		JyAnnunciate jyAnnunciate=new JyAnnunciate();
		jyAnnunciate.setStatus(status);
		jyAnnunciate.setIsDelete(0);
		if (type==0) {
			jyAnnunciate.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		}else {
			jyAnnunciate.addCustomCondition("and orgId in ("+CurrentUserContext.getCurrentSpace().getOrgId()+",-1)", new HashMap<String, Object>());
		}
		jyAnnunciate.addOrder("lastupDttm desc");
		if (status==0) {
			jyAnnunciate.setUserId(CurrentUserContext.getCurrentUserId());
		}
		List<JyAnnunciate> jList=jyAnnunciateService.find(jyAnnunciate, i+1);
		if(!CollectionUtils.isEmpty(jList)){
			return jList.get(i);
		}
		return null;
	}
	
	/**
	 * 获取最后一篇通告详情
	 * 
	 * @param id
	 * @return
	 */
	public JyAnnunciate getLastAnnunciates(Integer id,Integer status,Integer i,Integer type){
		JyAnnunciate jyAnnunciate=new JyAnnunciate();
		jyAnnunciate.setStatus(status);
		jyAnnunciate.setIsDelete(0);
		if (type==0) {
			jyAnnunciate.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		}else {
			jyAnnunciate.addCustomCondition("and orgId in ("+CurrentUserContext.getCurrentSpace().getOrgId()+",-1)", new HashMap<String, Object>());
		}
		jyAnnunciate.addOrder("lastupDttm");
		if (status==0) {
			jyAnnunciate.setUserId(CurrentUserContext.getCurrentUserId());
		}
		List<JyAnnunciate> jList=jyAnnunciateService.find(jyAnnunciate, i+1);
		if(!CollectionUtils.isEmpty(jList)){
			return jList.get(i);
		}
		return null;
	}
	
	/**
	 *添加红头
	 * @param 
	 * @return
	 */
	@RequestMapping("/addRedHead")
	public String addRedHead(){
		return viewName("annunciate_redhead");
	}
	/**
	 * 翻页新增查看的记录
	 * @param id
	 * @return
	 */
	public void getReadPunishAnnunciate(Integer id,Integer status){
		AnnunciatePunishView view = new AnnunciatePunishView();
		view.setAnnunciateId(id);
		view.setUserId(CurrentUserContext.getCurrentUserId());
		int count = annunciatePunishViewService.count(view);
		if(count==0){
			try{
				view.setViewTime(new Date());
				annunciatePunishViewService.save(view);
			}catch(Exception e){
				logger.info("新增查阅记录失败!",e);
			}
		}
	}
	/**
	 *添加置顶
	 * @param 
	 * @return
	 */
	@RequestMapping("/isTop")
	public String isTop(JyAnnunciate jAnnunciate){
		if (jAnnunciate.getId()!=null&&jAnnunciate.getIsTop()!=null) {
			JyAnnunciate model=jyAnnunciateService.findOne(jAnnunciate.getId());
			if(jAnnunciate.getIsTop()==true){
				model.setIsTop(false);
				model.setLastupDttm(model.getCrtDttm());
			}else {
				model.setIsTop(true);
				model.setLastupDttm(new Date());
			}
			jyAnnunciateService.update(model);
		}
		return "redirect:/jy/annunciate/punishs";
	}
	/**
	 * 跳到转发区域通知公告
	 * @param 
	 * @return
	 */
	@RequestMapping("/toForwardAnnunciate")
	public String toForwardAnnunciate(Integer id,Model m){
		if (id!=null) {
			JyAnnunciate model=jyAnnunciateService.findOne(id);
			m.addAttribute("ja", model);
			if (model.getRedTitleId()!=0) {
		    	JyRedTitle jrt=jyRedTitleService.findOne(model.getRedTitleId());
		    	 m.addAttribute("jrt", jrt);
			}
		    getAnnunciateAttachs(model.getAttachs(), m);
		}
		return "/annunciate/annunciate_forward";
	}
	
	/**
	 * 转发区域通知公告
	 * @param 
	 * @return
	 */
	@RequestMapping("/forwardAnnunciate")
	public Result forwardAnnunciate(JyAnnunciate model,Model m){
		Result result=new Result();
		if (model.getId()!=null) {
			try {
				jyAnnunciateService.forwardAnnunciate(model);
				result.setCode(1);
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("转发失败");
				result.setCode(0);
			}
		}
		return result;
	}
	
	/**
	 * 获取参与的机构集合
	 * @param activityId
	 */
	@RequestMapping(value="/getJoinOrgs")
	public void getJoinOrgsOfActivity(@RequestParam(value="id",required=false)Integer id,Model m){
		JyAnnunciate ja = jyAnnunciateService.findOne(id);
		List<Organization> orgList = organizationService.getOrgListByIdsStr(ja.getOrgsJoinIds());
		m.addAttribute("orgList", orgList);
	}
	/**
	 * 获取当前区域下的机构集合
	 * @param m
	 */
	@RequestMapping("getOrgListOfArea")
	private void getOrgListOfRegion(String search,Model m){
		List<Organization> orgList = jyAnnunciateService.getOrgListOfArea(search);
		m.addAttribute("orgList", orgList);
	}
	
	/**
	 * 获取区域教研动态
	 * @param m
	 */
	@RequestMapping("getAreaAnnuciateList")
	public void getAreaAnnuciateList(Model m){
		JyAnnunciate ja=new JyAnnunciate();
		ja.setAnnunciateType(1);
		ja.setStatus(1);
		ja.setIsDelete(0);
		ja.setOrgId(CurrentUserContext.getCurrentUser().getOrgId());
		ja.addOrder("isTop desc,lastupDttm desc");
		List<JyAnnunciate> jList=jyAnnunciateService.findAll(ja);
		m.addAttribute("jList", jList);
	}
	
	/**
	 * 区域一览-获取通告详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/summaryAnnunciateView")
	public String getSummaryAnnunciateView(Integer id,Integer status,Model m,Integer type) {
		JyAnnunciate j1=getFirstAnnunciates(id, status,0,type);
		JyAnnunciate j2=getLastAnnunciates(id, status,0,type);
		if (id!=null) {
			if (id.equals(j1.getId())) {
				m.addAttribute("isFirst", true);
			}
			if (id.equals(j2.getId())) {
				m.addAttribute("isLast", true);
			}
			JyAnnunciate  ja=jyAnnunciateService.findOne(id);
		    m.addAttribute("ja", ja);
		    m.addAttribute("type", type);
		    if (ja.getRedTitleId()!=0) {
		    	JyRedTitle jrt=jyRedTitleService.findOne(ja.getRedTitleId());
		    	 m.addAttribute("jrt", jrt);
			}
		    getAnnunciateAttachs(ja.getAttachs(), m);
		}
		return "/summary/annunciate/view";
	}
}

