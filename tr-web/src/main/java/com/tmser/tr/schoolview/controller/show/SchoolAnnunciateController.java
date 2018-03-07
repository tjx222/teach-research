package com.tmser.tr.schoolview.controller.show;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.annunciate.service.JyAnnunciateService;
import com.tmser.tr.annunciate.service.JyRedTitleService;
import com.tmser.tr.annunciate.vo.JyAnnunciateVo;
import com.tmser.tr.browse.BaseResTypes;
import com.tmser.tr.browse.utils.BrowseRecordUtils;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.school.show.service.SchoolShowService;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;
import com.tmser.tr.utils.StringUtils;

/**
 * 通知公告控制器
 * <pre>
 *		返回学通知公告的数据
 * </pre>
 *
 * @author
 * @version $Id: LectureRecords.java, v 1.0 2015-03-30 Generate Tools Exp $
 */
@Controller
@RequestMapping("/jy/schoolview/show/")
public class SchoolAnnunciateController extends CommonController{

	@Autowired
	private SchoolShowService schoolShowService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private JyAnnunciateService jyAnnunciateService;
	@Autowired
	private JyRedTitleService jyRedTitleService;
	@Autowired
	private OrganizationService orgService;
	
	/**
	 * 加载首页通知公告
	 * 
	 */
	@RequestMapping("/loadIndexNotice")
	public String loadIndexNotice(CommonModel cm,JyAnnunciate jy,Model model){
		jy.addCustomCondition(" and orgId in("+cm.getOrgID()+",-1 )  order by crtDttm desc", new HashMap<String, Object>());
		jy.setStatus(1);//已发布
		jy.setIsDisplay(1);//首页显示
		jy.setIsDelete(0);//未删除
		jy.setIsEnable(1);//可用
		List<JyAnnunciate> list= this.jyAnnunciateService.find(jy, 5);
		List<JyAnnunciate> JyAnnunciateNumList  = new ArrayList<JyAnnunciate>();
		int i = 1;
		for(JyAnnunciate jyAnnunciate :list){
			jyAnnunciate.setFlago(String.valueOf(i));
			JyAnnunciateNumList.add(jyAnnunciate);
			i++;
		}
		model.addAttribute("annunciateList",list);
		handleCommonVo(cm, model);
		return "/schoolview/show/tzgg/indexNotice";

	}

	/**
	 *通知公告列表展示
	 * @return
	 */
	@RequestMapping("notice")
	public String notice(JyAnnunciateVo vo,Page page,Model m,CommonModel cm){
		vo.currentPage(1);
		vo.setStatus(1);
		vo.setIsDisplay(1);
		vo.setIsDelete(0);
		vo.addPage(page);
		PageList<JyAnnunciate> pagelist = schoolShowService.getPageList(vo);
		m.addAttribute("cm", cm);
		m.addAttribute("pagelist", pagelist);
		return "/schoolview/show/tzgg/notice_list";
	}
	
	/**
	 *（陕西定制）登录页公告滚动5条
	 * @return
	 */
	@RequestMapping("getAnnouncementSch")
	@ResponseBody
	public List<JyAnnunciate> getAnnouncementSch(JyAnnunciate vo){
		vo.setStatus(1);
		vo.setIsDisplay(1);//学校首页显示
		vo.setIsDelete(0);
		vo.setAnnunciateType(0);//学校公告
		vo.addCustomCulomn("id,orgId,title");
		vo.addOrder("crtDttm desc");
		vo.buildCondition(" and orgId != :orgid").put("orgid", -1);
		List<JyAnnunciate> list = jyAnnunciateService.find(vo, 5);
		for(JyAnnunciate ja : list){
			Organization one = orgService.findOne(ja.getOrgId());
			if(one != null){
				ja.setContent(one.getName());
			}
		}
		return list;
	}

	/**
	 *通知公告-详情页
	 * @return
	 */
	@RequestMapping("/notice_announcement")
	public String notice_announcement(Model m,CommonModel cm,Integer id){
		cm.setDh("1");//设置导航菜单栏状态标识
		handleCommonVo(cm, m);
		JyAnnunciate j1=getFirstAnnunciates(cm,id,0);
		JyAnnunciate j2=getLastAnnunciates(cm,id,0);
		if (id!=null) {
			if (id!=null&&id.equals(j1.getId())) {
				m.addAttribute("isFirst", true);
			}
			if (id.equals(j2.getId())) {
				m.addAttribute("isLast", true);
			}
			JyAnnunciate  ja=jyAnnunciateService.findOne(id);
			m.addAttribute("ja", ja);
			m.addAttribute("cm",cm);
			getAnnunciateAttachs(ja.getAttachs(), m);
			if (ja.getRedTitleId()!=0) {
				 m.addAttribute("redHeadTitle", jyRedTitleService.findOne(ja.getRedTitleId()).getTitle());
			}
			//点击量统计
			BrowseRecordUtils.addBrowseRecord(BaseResTypes.TZGG, ja.getId());
			int count = BrowseRecordUtils.getResBrowseCount(BaseResTypes.TZGG, ja.getId());
			m.addAttribute("count",count);
		}
		return "/schoolview/show/tzgg/notice_view";
	}
	/**
	 * 获取上一篇通告详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("previousAnnunciates")
	public String getPreviousAnnunciates(Model model,CommonModel cm,Integer id){
		JyAnnunciate jyAnnunciate=getFirstAnnunciates(cm,id,1);
		if (id!=null&&id.equals(jyAnnunciate.getId())) {
			model.addAttribute("isFirst", true);
		}
		JyAnnunciate ja=jyAnnunciateService.getPreAnnunciate(id, cm.getOrgID(), 1,2);
		if (ja==null) {
			ja=jyAnnunciateService.findOne(id);
			model.addAttribute("isFirst", true);
		}else{
			//点击量统计
			BrowseRecordUtils.addBrowseRecord(BaseResTypes.TZGG, ja.getId());
			int count = BrowseRecordUtils.getResBrowseCount(BaseResTypes.TZGG, ja.getId());
			model.addAttribute("count",count);
		}
		model.addAttribute("ja", ja);
		if (StringUtils.isNotEmpty(ja.getAttachs())) {
			getAnnunciateAttachs(ja.getAttachs(), model);
		}
		if (ja.getRedTitleId()!=0) {
			 model.addAttribute("redHeadTitle", jyRedTitleService.findOne(ja.getRedTitleId()).getTitle());
		}
		model.addAttribute("cm", cm);
		return "/schoolview/show/tzgg/notice_view";
	}
	/**
	 * 获取下一篇通告详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("nextAnnunciates")
	public String getNextAnnunciates(Model model,CommonModel cm,Integer id){
		JyAnnunciate jyAnnunciate=getLastAnnunciates(cm,id,1);
		if (id!=null&&id.equals(jyAnnunciate.getId())) {
			model.addAttribute("isLast", true);
		}
		JyAnnunciate ja=jyAnnunciateService.getNextAnnunciate(id, cm.getOrgID(), 1,2);
		if (ja==null) {
			ja=jyAnnunciateService.findOne(id);
			model.addAttribute("isLast", true);
		}else{
			//点击量统计
			BrowseRecordUtils.addBrowseRecord(BaseResTypes.TZGG, ja.getId());
			int count = BrowseRecordUtils.getResBrowseCount(BaseResTypes.TZGG, ja.getId());
			model.addAttribute("count",count);
		}
		model.addAttribute("ja", ja);
		if (!StringUtils.isEmpty(ja.getAttachs())) {
			getAnnunciateAttachs(ja.getAttachs(), model);
		}
		if (ja.getRedTitleId()!=0) {
			 model.addAttribute("redHeadTitle", jyRedTitleService.findOne(ja.getRedTitleId()).getTitle());
		}
		model.addAttribute("cm", cm);
		return "/schoolview/show/tzgg/notice_view";
	}

	/**
	 * 获取第一篇通告详情
	 * 
	 * @param id
	 * @return
	 */
	public JyAnnunciate getFirstAnnunciates(CommonModel cm,Integer id,Integer i){
		JyAnnunciate jyAnnunciate=new JyAnnunciate();
		jyAnnunciate.setStatus(1);
		jyAnnunciate.setIsDisplay(1);
		jyAnnunciate.setIsDelete(0);
		jyAnnunciate.addCustomCondition("and orgId in("+cm.getOrgID()+",-1)", new HashMap<String, Object>());
		jyAnnunciate.addOrder("crtDttm desc");
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
	public JyAnnunciate getLastAnnunciates(CommonModel cm,Integer id,Integer i){
		JyAnnunciate jyAnnunciate=new JyAnnunciate();
		jyAnnunciate.setStatus(1);
		jyAnnunciate.setIsDisplay(1);
		jyAnnunciate.setIsDelete(0);
		jyAnnunciate.addCustomCondition("and orgId in("+cm.getOrgID()+",-1)", new HashMap<String, Object>());
		jyAnnunciate.addOrder("crtDttm");
		List<JyAnnunciate> jList=jyAnnunciateService.find(jyAnnunciate, i+1);
		if(!CollectionUtils.isEmpty(jList)){
			return jList.get(i);
		}
		return null;
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
			m.addAttribute("rList", rList);
		}
	}
	/**
	 * 首页滚动系统通知
	 * @param cm
	 * @param m
	 * @param jy
	 * @return
	 */
	@RequestMapping("/loadSlideNoticeIndex")
	public String loadSlideNoticeIndex(CommonModel cm, Model m,JyAnnunciate jy){
		jy.addCustomCondition(" and orgId in("+cm.getOrgID()+",-1 )  order by crtDttm desc", new HashMap<String, Object>());
		jy.setIsEnable(1);//可用
		jy.setStatus(1);//已发布
		jy.setIsDisplay(1);//首页显示
		jy.setIsDelete(0);//未删除
		List<JyAnnunciate> yAnnunciateList= this.jyAnnunciateService.find(jy, 3);
		m.addAttribute("slideNoticeDate", yAnnunciateList);
		handleCommonVo(cm, m);
		return viewName("tzgg/slideNotice");
	}
}
