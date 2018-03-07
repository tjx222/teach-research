package com.tmser.tr.back.ptgg.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.annunciate.bo.JyAnnunciate;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.ptgg.service.NoticeAnnouncementService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.utils.CurrentUserContext;
/**
 * 平台公告---->通知公告
 * @author lijianghu
 * @date:2015-9-30
 *
 */
@Controller
@RequestMapping("/jy/back/ptgg/tzgg")
public class NoticeAnnouncementController  extends AbstractController {
	@Autowired
	private NoticeAnnouncementService noticeAnnouncementService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private UserService userService;
	 private final static Logger logger = LoggerFactory.getLogger(NoticeAnnouncementController.class);
	/**
	 * 跳转首页
	 */
	@RequestMapping("/ptgg_index")
	public String toIndex(JyAnnunciate jyan,Model m){
		getDraftNum(jyan,m);
		return viewName("ptgg_index");
	}
	
	/**
	 * 跳转通知公告列表
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/toNoticeAnnouncementList")
	public String toNoticeAnnouncementList(JyAnnunciate jyAnnunciate, Model m){
		JyAnnunciate countjyAnnunciate = new JyAnnunciate();
		countjyAnnunciate.setTitle(null);
		getDraftNum(countjyAnnunciate,m);
		if(jyAnnunciate.order()==null || "".equals(jyAnnunciate.order())){
			jyAnnunciate.addOrder("crtDttm desc");
		}
		jyAnnunciate.setStatus(1);//已发布
		jyAnnunciate.setIsDelete(0);//未删除
		jyAnnunciate.setOrgId(-1);
		if(jyAnnunciate.getTitle()!=null){//查询
			String title="";
			try {
				title=java.net.URLDecoder.decode(jyAnnunciate.getTitle(), "UTF-8");
			} catch (Exception e) {
				logger.error("通知公告查询中文转码失败",e);
			}
			jyAnnunciate.setTitle(SqlMapping.LIKE_PRFIX +title+SqlMapping.LIKE_PRFIX);
		}
		PageList<JyAnnunciate> noticeAnnouncementList = this.noticeAnnouncementService.findByPage(jyAnnunciate);
		m.addAttribute("noticeAnnouncementList", noticeAnnouncementList);
		return viewName("noticeAnnouncementList");
	}
	
	/**
	 * 跳转到发布通知公告
	 */
	@RequestMapping("/addNoticeAnnouncement")
	public String toReleaseNotice(Model m){
		return viewName("addNoticeAnnouncement");
	}
	/**
	 * 新增系统通知公告
	 */
	@RequestMapping("/saveNotice")
	@ResponseBody
	public JuiResult saveNotice(JyAnnunciate jyan,Boolean flag){
		JuiResult juiResult=new JuiResult();
		try {
			jyan.setOrgId(-1);//系统用户设置orgid-1
			jyan.setSpaceId(0);//区域ID：0
			jyan.setRedTitleId(0);//非红头文件
			jyan.setAnnunciateRange(1);//区域
			jyan.setIsEnable(1);//可用
			jyan.setAnnunciateType(1);//区域
			jyan.setIsForward(1);//未转发
			if(flag!=null){//是否是存草稿标志，非空：存草稿
				jyan.setStatus(0);//1:已发布 0：草稿
			}else{
				jyan.setStatus(1);//1:已发布 0：草稿
			}
			jyan.setIsDelete(0);//1：已删除
			jyan.setType("0");//普通文件
			jyan.setIsDisplay(1);//发布
			jyan.setCrtDttm(new Date());
			jyan.setLastupDttm(new Date());
			jyan.setUserId(CurrentUserContext.getCurrentUser().getId());
			jyan.setCrtId(CurrentUserContext.getCurrentUser().getId());
			juiResult.setMessage("保存成功");
			juiResult.setStatusCode(JuiResult.SUCCESS);
			if(jyan.getAttachs()!=null){
				for(String resourceId:jyan.getAttachs().split("#")){
					resourcesService.updateTmptResources(resourceId);
				}
			}
			noticeAnnouncementService.save(jyan);
			LoggerUtils.insertLogger(LoggerModule.PTGG, "平台公告——通知公告——新增，操作人ID："+CurrentUserContext.getCurrentUser().getId());
		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
				juiResult.setMessage("保存失败");
				logger.error("平台公告-通知公告保存失败",e);
		}
		return juiResult ;
	}
	/**
	 * 查看公告
	 * @param m
	 * @param id
	 * @return
	 */
	@RequestMapping("/viewNoticeAnnouncement")
	public String view(Model m,String id){
		if (id!=null) {
			JyAnnunciate jyAnnunciate=noticeAnnouncementService.findOne(Integer.parseInt(id));
			if(jyAnnunciate.getCrtId()!=null){
				jyAnnunciate.setCrtName(userService.findOne(jyAnnunciate.getCrtId()).getName());
			}
			m.addAttribute("jyAnnunciate", jyAnnunciate);
			getAnnunciateAttachs(jyAnnunciate.getAttachs(),m);
		}
		m.addAttribute("id", id);
		return viewName("viewNoticeAnnouncement");
	}
	
	/**
	 * 获取通告附件
	 * 
	 * @param id
	 * @return
	 */
	public  void  getAnnunciateAttachs(String attachs,Model m){
		if (StringUtils.isNotBlank(attachs)) {
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
	 * 预览通知公告
	 * @param m
	 * @param jyAnnunciate
	 * @return
	 */
	@RequestMapping("/skipNotice")
	public String skipNotice(Model m,JyAnnunciate jyAnnunciate){
		m.addAttribute("jyAnnunciate", jyAnnunciate);
		if(!StringUtils.isBlank(jyAnnunciate.getAttachs())){
			List<String> imgUrls=new ArrayList<>();
			try {
				for(String resoursId:jyAnnunciate.getAttachs().split("#")){
					imgUrls.add(resourcesService.viewResources(resoursId));
				}
			} catch (Exception e) {
				logger.error("resourceID对应的资源不存在：NoticeAnnouncementController-->view（）");
			}
			m.addAttribute("imgUrls", imgUrls);
		}
		return viewName("viewNoticeAnnouncement");
	}
	
	/**
	 * 刪除通知公告
	 * @param ids
	 * @return
	 */
	@RequestMapping("/batchdelete")
	public JuiResult batchdelete(String ids){
		JuiResult juiResult = new JuiResult();
			try {
				JyAnnunciate model = new JyAnnunciate();
				if(ids!=null){
					for(String id:ids.split(",")){
							model = noticeAnnouncementService.findOne(Integer.parseInt(id));
							if(!StringUtils.isEmpty(model.getAttachs())){
								for(String resId:model.getAttachs().split("#")){
									//处理附件信息
									this.deleteImg(resId, false);
								}
							}
							this.noticeAnnouncementService.delete(Integer.parseInt(id));
						}
						LoggerUtils.deleteLogger(LoggerModule.XXSY, "平台公告——通知公告——删除，操作人ID："+CurrentUserContext.getCurrentUser().getId());
						juiResult.setMessage("删除成功！");
					}
				} catch (Exception e) {
			// TODO: handle exception
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("删除失败！");
			logger.error("刪除通知公告失败",e);
		}
		return juiResult;
	}
	
	
	/**
	 * 跳转通知公告_草稿箱
	 * @param jyAnnunciate
	 * @param m
	 * @param pageNum
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/toNoticeAnnouncementDraftList")
	public String toNoticeAnnouncementListDraftList(JyAnnunciate jyAnnunciate, Model m) throws UnsupportedEncodingException{
		if(jyAnnunciate.order()==null || "".equals(jyAnnunciate.order())){
			jyAnnunciate.addOrder("crtDttm desc");
		}
		jyAnnunciate.setStatus(0);
		jyAnnunciate.setIsDelete(0);
		jyAnnunciate.setOrgId(-1);//管理员,只取orgid=-1
		if(StringUtils.isNotBlank(jyAnnunciate.getTitle())){
			jyAnnunciate.setTitle(SqlMapping.LIKE_PRFIX +jyAnnunciate.getTitle()+SqlMapping.LIKE_PRFIX);
		}
		getDraftNum(jyAnnunciate,m);
		PageList<JyAnnunciate> noticeAnnouncementListDraft = this.noticeAnnouncementService.findByPage(jyAnnunciate);
		m.addAttribute("noticeAnnouncementListDraft", noticeAnnouncementListDraft);
		return viewName("noticeAnnouncementDraft");
	}
	/**
	 * 编辑公告——草稿箱
	 * @param jyAnnunciate
	 * @param m
	 * @return
	 */
	@RequestMapping("/editNoticeAnnouncementDrift")
	public String resourcesService(JyAnnunciate jyAnnunciate,Model m){
		JyAnnunciate jy = this.noticeAnnouncementService.findOne(jyAnnunciate.getId());
		m.addAttribute("jyAnnunciate", jy);
		getAnnunciateAttachs(jy.getAttachs(),m);
		return viewName("editNoticeAnnouncementDrift");
		
	}
	/**
	 * 更新草稿
	 * @param jyAnnunciate
	 * @return
	 * flag=true:更新为草稿，flag=null:更新为发布状态
	 */
	@RequestMapping("/editNoticeDrift")
	@ResponseBody
	public JuiResult editNoticeDrift(JyAnnunciate jyAnnunciate,Boolean flag){
		JuiResult juiResult = new JuiResult();
		try {
			juiResult.setStatusCode(JuiResult.SUCCESS);
			if(StringUtils.isNotBlank(jyAnnunciate.getFlags())){
				for(String resourceId:jyAnnunciate.getFlags().split("#")){
					this.deleteImg(resourceId, false);
				}
			}
			if (jyAnnunciate.getAttachs() != null) {
				for(String resourceId:jyAnnunciate.getAttachs().split("#")){
					resourcesService.updateTmptResources(resourceId);
				}
			}
			JyAnnunciate _jyAnnunciate = this.noticeAnnouncementService.findOne(jyAnnunciate.getId());
			_jyAnnunciate.setTitle(jyAnnunciate.getTitle());
			_jyAnnunciate.setContent(jyAnnunciate.getContent());
			_jyAnnunciate.setAttachs(jyAnnunciate.getAttachs());
			_jyAnnunciate.setContent(jyAnnunciate.getContent());
			if(null!=flag){//是否是存草稿标志，非空：存草稿
				_jyAnnunciate.setStatus(0);//1:已发布 0：草稿
			}else{
				_jyAnnunciate.setStatus(1);//1:已发布 0：草稿
			}
			this.noticeAnnouncementService.update(_jyAnnunciate);
			if(jyAnnunciate.getAttachs()!=null){
				for(String resourceId:jyAnnunciate.getAttachs().split("#")){
					resourcesService.updateTmptResources(resourceId);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("更新失败！");
			logger.error("更新通知公告失败--NoticeAnnouncementController---editNoticeDrift");
			e.printStackTrace();
		}
		return juiResult;
	}
	/**
	 * 获取草稿箱数量
	 * @param jyan
	 * @param m
	 * @return
	 */
	public  int getDraftNum(JyAnnunciate jyan,Model m){
		jyan.setStatus(0);//未发布
		jyan.setIsDelete(0);//未删除
		jyan.setOrgId(-1);
		List<JyAnnunciate> list  = this.noticeAnnouncementService.findAll(jyan);
		if(null!=list&&list.size()>0){
			m.addAttribute("_count", list.size());
			return list.size();
		}else{
			m.addAttribute("_count", 0);
		}
		return 0;
	}
	/**
	 * 删除文件
	 * @param imgId 资源id
	 * @param isweb 是否是web下资源
	 * @return
	 */
	@RequestMapping("/deleteImg")
	public void deleteImg(String imgId,Boolean isweb){
		resourcesService.deleteResources(imgId);
	} 
}
