package com.tmser.tr.back.ptgg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.ptgg.service.FlatformAnnouncementService;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.ptgg.bo.FlatformAnnouncement;
import com.tmser.tr.uc.utils.CurrentUserContext;
/**
 * 平台公告---->首页广告
 * @author tmser
 * @date:2015-9-28
 *
 */
@Controller
@RequestMapping("/jy/back/ptgg/sygg")
public class FlatformAnnouncementController  extends AbstractController {
	@Autowired
	private FlatformAnnouncementService flatformAnnouncementService;
	@Autowired
	private ResourcesService resourcesService; 
	private final static Logger logger = LoggerFactory.getLogger(FlatformAnnouncementController.class);

	/**
	 * 展示首页广告列表
	 */
	@RequestMapping("/flatformAnnouncementList")
	public String flatformAnnouncementList(FlatformAnnouncement flatformAnnouncement, Model m){
		if(flatformAnnouncement.order()==null || "".equals(flatformAnnouncement.order())){
			flatformAnnouncement.addOrder("cdate desc");
		}
		PageList<FlatformAnnouncement> flatformAnnouncementList = this.flatformAnnouncementService.findByPage(flatformAnnouncement);
		m.addAttribute("flatformAnnouncementList", flatformAnnouncementList);
		m.addAttribute("flatformAnnouncement", flatformAnnouncement);
		return  viewName("/flatformAnnouncementList");
	}
	/**
	 * 删除首页广告
	 */
	@RequestMapping(value="/batchdelete")
	@ResponseBody
	public JuiResult batchdelete(String ids){
		JuiResult rs = new JuiResult();
		try {
			if(ids!=null){
				for(String id:ids.split(",")){
					//删除文件资源
					FlatformAnnouncement temp = flatformAnnouncementService.findOne(Integer.parseInt(id));
					if(temp!=null){
						deleteImg(temp.getLittlepictureId(),true);
						deleteImg(temp.getPictureid(),true);
					}  
					flatformAnnouncementService.delete(Integer.parseInt(id));
					LoggerUtils.deleteLogger(LoggerModule.PTGG, "平台公告——首页广告——删除首页广告，操作人ID："+CurrentUserContext.getCurrentUser().getId());
				}
			}
			rs.setMessage("删除成功！");
			rs.setStatusCode(JuiResult.SUCCESS);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("删除失败！");
			logger.error("删除失败",e);
		}
		return rs;
	}
	/**
	 * 首页广告的显示或隐藏
	 * 当前图片显示或隐藏，其他图片相反状态。全部隐藏则前台不显示
	 */
	@RequestMapping("/isShowpic")
	@ResponseBody
	public JuiResult isShowpic(FlatformAnnouncement flatformAnnouncement,String flag){
		JuiResult rs = new JuiResult();
		try {
			if(flag.equalsIgnoreCase("0")){//隐藏图片
				flatformAnnouncement.setIsview(0);
				flatformAnnouncementService.update(flatformAnnouncement);
				LoggerUtils.updateLogger(LoggerModule.PTGG, "平台公告——更新广告——更新首页广告(隐藏)，操作人ID："+CurrentUserContext.getCurrentUser().getId());
				rs.setMessage("操作成功");
				rs.setStatusCode(JuiResult.SUCCESS);
			}else{
				flatformAnnouncementService.updatePic(flatformAnnouncement);
				LoggerUtils.updateLogger(LoggerModule.PTGG, "平台公告——更新广告——更新首页广告(显示)，操作人ID："+CurrentUserContext.getCurrentUser().getId());
				rs.setMessage("操作成功！");
				rs.setStatusCode(JuiResult.SUCCESS);
			}
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("操作失败！");
			logger.error("显示或隐藏图片失败",e);
		}
		return rs;
	}
	/**
	 * 跳转发布或个修改首页广告页面
	 * @return
	 */
	@RequestMapping("/toReleaseHomeAds")
	public String releaseHomeAds(FlatformAnnouncement flatformAnnouncement,Model m){
		if(flatformAnnouncement.getId()!=null){
			FlatformAnnouncement flat = flatformAnnouncementService.findOne(flatformAnnouncement.getId());
			m.addAttribute("flat",flat);
		}
		return viewName("releaseHomeAds");
	}
	/**
	 * 保存或更新首页广告(大图和缩略图)
	 * @return
	 */
	@RequestMapping("/saveHomeAds")
	@ResponseBody
	public JuiResult saveHomeAds(FlatformAnnouncement flatformAnnouncement){
		JuiResult rs = new JuiResult();
		try {
			//维护resources表图片id状态
			if(flatformAnnouncement.getPictureid()!=null){
				resourcesService.updateTmptResources(flatformAnnouncement.getPictureid());
			}
			if(flatformAnnouncement.getLittlepictureId()!=null){
				resourcesService.updateTmptResources(flatformAnnouncement.getLittlepictureId());
			}
			if(flatformAnnouncement.getId()!=null){//编辑
				String resId = flatformAnnouncement.getFlags();
				for(String id : resId.split(",")){
					this.deleteImg(id, true);
				}
				flatformAnnouncementService.update(flatformAnnouncement);
				//更新文件状态
				LoggerUtils.updateLogger(LoggerModule.PTGG, "平台公告——更新广告——编辑首页广告，操作人ID："+CurrentUserContext.getCurrentUser().getId());
			}else{//添加
				flatformAnnouncementService.saveHomeAds(flatformAnnouncement);
				LoggerUtils.insertLogger(LoggerModule.PTGG, "平台公告——更新广告——新增首页广告，操作人ID："+CurrentUserContext.getCurrentUser().getId());
			}
			for(String resourceId:flatformAnnouncement.getPictureid().split(",")){
				resourcesService.updateTmptResources(resourceId);
			}
			for(String resourceId:flatformAnnouncement.getLittlepictureId().split(",")){
				resourcesService.updateTmptResources(resourceId);
			}
			rs.setMessage("保存成功！");
			rs.setStatusCode(JuiResult.SUCCESS);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("保存失败！");
			logger.error("保存新闻广告图片失败",e);
		}
		return  rs;
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
