package com.tmser.tr.back.ptgg.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.ptgg.service.PictureNewsService;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.ptgg.bo.PictureNews;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * 平台公告---->图片新闻
 * @author tmser
 * @date:2015-10-12
 *
 */
@Controller
@RequestMapping("/jy/back/ptgg/tpxw")
public class PictureNewsController  extends AbstractController {
	@Autowired
	private PictureNewsService pictureNewsService;
	@Autowired
	private ResourcesService resourcesService;
	@Autowired
	private final static Logger logger = LoggerFactory.getLogger(PictureNewsController.class);

	
	/**
	 * 跳转首页
	 * @param pictureNews
	 * @param m
	 * @return
	 */
	@RequestMapping("/tpxw_index")
	public String tpxw_index(PictureNews pictureNews,Model m){
		getDraftNum(pictureNews,m);
		return viewName("tpxw_index");
	}
	/**
	 * 跳转图片新闻列表
	 * @param pictureNews
	 * @param m
	 * @param pageNum
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/picturenewsList")
	public String picturenewsList(PictureNews pictureNews, Model m){
		getDraftNum(pictureNews,m);
		pictureNews.addCustomCondition("group by parentid order by istop desc,parentid desc",new HashMap<String, Object>());
		pictureNews.setStatus(1);//已发布
		pictureNews.setIsdelete(0);//未删除
		pictureNews.setOrgId(-1);
		if(pictureNews.getTitle()!=null){
			String title="";
			try {
				title=java.net.URLDecoder.decode(pictureNews.getTitle(), "UTF-8");
			} catch (Exception e) {
				LoggerUtils.insertLogger("搜索中文转码失败");
			}
			pictureNews.setTitle(SqlMapping.LIKE_PRFIX +title+SqlMapping.LIKE_PRFIX);
		}
		PageList<PictureNews> pictureNewsList = this.pictureNewsService.findByPage(pictureNews);
		m.addAttribute("pictureNewsList", pictureNewsList);
		return viewName("picturenewsList");
	}
	
	/**
	 * 跳转到发布图片新闻
	 * @param m
	 * @return
	 */
	@RequestMapping("/addPicturenews")
	public String addPicturenews(Model m){
		return viewName("addPicturenews");
	}
	/**
	 * 新增图片新闻
	 * @param pictureNews
	 * @param flag
	 * @return
	 */
	@RequestMapping("/savePictureNews")
	@ResponseBody
	public JuiResult savePictureNews(PictureNews pictureNews,Boolean flag){
		JuiResult juiResult=new JuiResult();
		try {
			pictureNews.setIsDisplay(1);//显示
			pictureNews.setIsdelete(0);
			pictureNews.setCrtDttm(new Date());
			pictureNews.setCrtId(CurrentUserContext.getCurrentUserId());
			pictureNews.setCrtname(CurrentUserContext.getCurrentUser().getName());
			pictureNews.setOrgId(-1);
			if(flag!=null){//是否是存草稿标志，非空：存草稿
				pictureNews.setStatus(0);//1:已发布 0：草稿
			}else{
				pictureNews.setStatus(1);//1:已发布 0：草稿
			}
			juiResult.setMessage("保存成功");
			juiResult.setStatusCode(JuiResult.SUCCESS);
			if(pictureNews.getAttachs()!=null){
				for(String resourceId:pictureNews.getAttachs().split("#")){
					resourcesService.updateTmptResources(resourceId);
				}
			}
			pictureNewsService.save(pictureNews);
			LoggerUtils.insertLogger(LoggerModule.PTGG, "教学首页——图片新闻——新增图片新闻，操作者ID："+CurrentUserContext.getCurrentUser().getId());
		} catch (Exception e) {
			// TODO: handle exception
			juiResult.setStatusCode(JuiResult.FAILED);
				juiResult.setMessage("保存失败");
				logger.error("保存图片新闻保存异常",e);
		}
		return juiResult ;
	}
	/**
	 * 查看图片新闻
	 * @param m
	 * @param id
	 * @return
	 */
	@RequestMapping("/viewPictureNews")
	public String view(Model m,String id){
		if (id!=null) {
			PictureNews pictureNews=pictureNewsService.findOne(Integer.parseInt(id));
			m.addAttribute("pictureNews", pictureNews);
			if(!StringUtils.isBlank(pictureNews.getAttachs())){
				List<String> imgUrls=new ArrayList<>();
				try {
					for(String resoursId:pictureNews.getAttachs().split("#")){
						imgUrls.add(resourcesService.viewResources(resoursId));
					}
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("resourceID对应的资源不存在：PictureNewsController-->viewPictureNews（）");
				}
				m.addAttribute("imgUrls", imgUrls);
			}
		}
		m.addAttribute("id", id);
		return viewName("viewPictureNews");
	}
	/**
	 * 刪除图片新闻
	 * @param ids
	 * @return
	 */
	@RequestMapping("/batchdelete")
	public JuiResult batchdelete(String ids){
		JuiResult juiResult = new JuiResult();
		try {
			if(ids!=null){
				for(String id:ids.split(",")){
					this.pictureNewsService.delete(Integer.parseInt(id));
					this.deleteImg(id, false);
					juiResult.setMessage("删除成功！");
					LoggerUtils.deleteLogger(LoggerModule.PTGG, "教学首页——图片新闻——删除图片新闻，操作者ID："+CurrentUserContext.getCurrentUser().getId());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("删除失败！");
			logger.error("刪除图片新闻失败--PictureNewsController---batchdelete");
		}
		return juiResult;
	}
	
	
	/**
	 * 跳转图片新闻_草稿箱
	 * @param jyAnnunciate
	 * @param m
	 * @param pageNum
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/picturenewsDraft")
	public String picturenewsDraft(PictureNews pictureNews, Model m) throws UnsupportedEncodingException{
		getDraftNum(pictureNews,m);
		if(pictureNews.order()==null || "".equals(pictureNews.order())){
			pictureNews.addOrder("crtDttm desc");
		}
		pictureNews.setStatus(0);
		pictureNews.setIsdelete(0);
		pictureNews.setOrgId(-1);
		PageList<PictureNews> pictureNewsDraft = this.pictureNewsService.findByPage(pictureNews);
		m.addAttribute("pictureNewsDraft", pictureNewsDraft);
		return viewName("picturenewsDraft");
	}
	/**
	 * 跳转编辑修改图片新闻
	 * @param pictureNews
	 * @param m
	 * @return
	 */
	@RequestMapping("/editPictureNews")
	public String editPictureNews(PictureNews pictureNews,Model m){
		PictureNews pic = this.pictureNewsService.findOne(pictureNews.getId());
		m.addAttribute("pictureNews", pic);
		if(!StringUtils.isBlank(pic.getAttachs())){
			Map<String,String> imgs=new HashMap<>();
			//图片对应的资源ID
			String [] imgIds=pic.getAttachs().split("#");
			try {
				for(String resoursId:imgIds){
					imgs.put(resoursId, resourcesService.viewResources(resoursId));
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("resourceID对应的资源不存在：PictureNewsController-->editPictureNewsDraft（）");
			}
			m.addAttribute("imgs", imgs);
		}
		return viewName("editPictureNews");
//		
	}
	/**
	 * 更新图片新闻
	 * @param pictureNews
	 * @return
	 * flag=true:更新为草稿，flag=null:更新为发布状态
	 */
	@RequestMapping("/updatePictureNews")
	@ResponseBody
	public JuiResult updatePictureNews(PictureNews pictureNews,Boolean flag){
		JuiResult juiResult = new JuiResult();
		try {
			juiResult.setStatusCode(JuiResult.SUCCESS);
			if(StringUtils.isNotBlank(pictureNews.getFlags())){
				for(String resourceId:pictureNews.getFlags().split("#")){
					this.deleteImg(resourceId, true);
				}
			}
			if (pictureNews.getAttachs() != null) {
				for(String resourceId:pictureNews.getAttachs().split("#")){
					resourcesService.updateTmptResources(resourceId);
				}
			}
			PictureNews pic = this.pictureNewsService.findOne(pictureNews.getId());
			pic.setTitle(pictureNews.getTitle());
			pic.setContent(pictureNews.getContent());
			pic.setAttachs(pictureNews.getAttachs());
			pic.setContent(pictureNews.getContent());
			pic.setIstop(pictureNews.getIstop());
			if(null!=flag){
				pic.setStatus(0);//1:已发布 0：草稿
			}else{
				pic.setStatus(1);
			}
			if(pic.getAttachs()!=null){
				for(String resourceId:pic.getAttachs().split("#")){
					resourcesService.updateTmptResources(resourceId);
				}
			}
			this.pictureNewsService.update(pic);
			LoggerUtils.updateLogger(LoggerModule.PTGG, "教学首页——图片新闻——编辑图片新闻，操作者ID："+CurrentUserContext.getCurrentUser().getId());
		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("更新失败！");
			logger.error("更新通知公告失败",e);
		}
		return juiResult;
	}
	public  int getDraftNum(PictureNews pictureNews,Model m){
		pictureNews.setStatus(0);//未发布
		pictureNews.setIsdelete(0);//未删除
		Organization org = (Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		if(null!=org){
			pictureNews.setOrgId(org.getId());
		}else{
			pictureNews.setOrgId(-1);
		}
		List<PictureNews> list  = this.pictureNewsService.findAll(pictureNews);
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
