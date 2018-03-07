package com.tmser.tr.back.xxsy.picNews.controller;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.back.xxsy.picNews.service.PictureNewsIndexService;
import com.tmser.tr.back.xxsy.picNews.vo.PicNewsVo;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.FrontCacheUtils;
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
 * 学校首页---->图片新闻
 * 
 * @author lijianghu
 * @date:2015-11-02
 *
 */
@Controller
@RequestMapping("/jy/back/xxsy/tpxw")
public class PictureNewsIndexController extends AbstractController {
	@Autowired
	private PictureNewsIndexService pictureNewsIndexService;
	@Autowired
	private ResourcesService resourcesService;
	@Value("#{config.getProperty('front_web_url','')}")
	private String defaultFrontWebUrl;
	private final static Logger logger = LoggerFactory.getLogger(PictureNewsIndexController.class);
	
	@RequestMapping("/index")
	public String index(PictureNews pictureNews, Model m) {
		m.addAttribute("orgId", pictureNews.getOrgId());
		return viewName("index");
	}

	/**
	 * 跳转首页
	 * 
	 * @param pictureNews
	 * @param m
	 * @return
	 */
	@RequestMapping("/tpxw_index")
	public String tpxw_index(PictureNews pictureNews, Model m) {
		Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		if (null != org) {
			pictureNews.setOrgId(org.getId());
			m.addAttribute("orgId", org.getId());
		} else {
			// 判断是否已经选择orgid
			if (pictureNews == null || pictureNews.getOrgId() == null) {
				// 模块列表url
				m.addAttribute("orgurl", "/jy/back/xxsy/tpxw/index?orgId=");
				// 模块加载divId
				m.addAttribute("divId", "picIndexId");
				return "/back/xxsy/selectOrg";
			} else {
				m.addAttribute("orgId", pictureNews.getOrgId());
			}
		}
		getDraftNum(pictureNews, m);
		return viewName("tpxw_index");
	}

	/**
	 * 跳转图片新闻列表
	 * @param pictureNews
	 * @param m
	 * @return
	 */
	@RequestMapping("/picturenewsList")
	public String picturenewsList(PictureNews pictureNews, Model m) {
		PictureNews countpictureNews = new PictureNews();// 计算草稿数
		countpictureNews.setOrgId(pictureNews.getOrgId());
		getDraftNum(countpictureNews, m);
		pictureNews.addCustomCondition("group by parentid order by istop desc,parentid desc",new HashMap<String, Object>());
		pictureNews.setStatus(1);// 已发布
		pictureNews.setIsdelete(0);// 未删除
		if (pictureNews.getTitle() != null) {
			String title = "";
			try {
				title = java.net.URLDecoder.decode(pictureNews.getTitle(),"UTF-8");
			} catch (Exception e) {
				LoggerUtils.insertLogger("搜索中文转码失败");
			}
			pictureNews.setTitle(SqlMapping.LIKE_PRFIX + title+ SqlMapping.LIKE_PRFIX);
		}
		PageList<PictureNews> pictureNewsList = this.pictureNewsIndexService.findByPage(pictureNews);
		m.addAttribute("pictureNewsList", pictureNewsList);
		m.addAttribute("orgId", pictureNews.getOrgId());
		return viewName("picturenewsList");
	}

	/**
	 * 跳转到发布图片新闻
	 * 
	 * @param m
	 * @return
	 */
	@RequestMapping("/addPicturenews")
	public String addPicturenews(Model m, PictureNews pictureNews) {
		m.addAttribute("orgId", pictureNews.getOrgId());
		return viewName("new/addPic");
	}

	/**
	 * 新增图片新闻/编辑子模块新闻
	 * 
	 * @param pictureNews(attachs,title)
	 * @return
	 */
	@RequestMapping("/savePictureNews")
	@ResponseBody
	public JuiResult savePictureNews(PictureNews pictureNews,PicNewsVo resVo) {
		JuiResult juiResult = new JuiResult();
		juiResult.setMessage("操作成功");
		try {
				int topNum=0;
				Organization org = (Organization) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
				if (org != null) {
					pictureNews.setOrgId(org.getId());
				}
				if (pictureNews.getFlago().equalsIgnoreCase("true")) {
					pictureNews.setStatus(0);// 1:已发布 0：草稿
				} else {
					pictureNews.setStatus(1);
					juiResult.setRel("1");
					juiResult.setMessage(defaultFrontWebUrl+ "/jy/schoolview/index?orgID="+ pictureNews.getOrgId());
				}
				if(pictureNews.getIstop()!=null&&pictureNews.getIstop()==1){//置顶
					 topNum = setTopUtil("1");
				}
				PictureNews picForQueryCount = new PictureNews();
				picForQueryCount.addOrder("crtDttm desc");
				List<PictureNews> count= pictureNewsIndexService.find(picForQueryCount, 1);
				List<PictureNews> rcdVo = resVo.getRcdVo();
				for (PictureNews model : rcdVo) {
					model.setIsDisplay(1);// 显示
					model.setIsdelete(0);// 非删除
					model.setCrtDttm(new Date());
					model.setCrtId(CurrentUserContext.getCurrentUserId());
					model.setCrtname(CurrentUserContext.getCurrentUser().getName());
					model.setTitle(pictureNews.getTitle());
					model.setOrgId(pictureNews.getOrgId());
					model.setStatus(pictureNews.getStatus());
					model.setAttachs(model.getResId());
					model.setIstop(topNum);
					if(!CollectionUtils.isEmpty(count)){
						model.setParentid(count.get(0).getId());
					}else{
						model.setParentid(1);
					}
					resourcesService.updateTmptResources(model.getResId());
					pictureNewsIndexService.save(model);
				}
				LoggerUtils.insertLogger(LoggerModule.XXSY,"教学首页——图片新闻——新增图片新闻，操作者ID："+ CurrentUserContext.getCurrentUser().getId());
			}catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("保存失败");
			logger.error("后台图片新闻保存异常", e);
			e.printStackTrace();
		}
		return juiResult;
	}

	/**
	 * 查看图片新闻
	 * 
	 * @param m
	 * @param id
	 * @return
	 */
	@RequestMapping("/viewPictureNews")
	public String view(Model m, String id) {
		if (id != null) {
			PictureNews pictureNews = pictureNewsIndexService.findOne(Integer.parseInt(id));
			m.addAttribute("pictureNews", pictureNews);
			//查询内容
			PictureNews parentPictureNews  = new PictureNews();
			parentPictureNews.setParentid(pictureNews.getParentid());
			List<PictureNews> list = pictureNewsIndexService.findAll(parentPictureNews);
			List<PictureNews> datalist = new ArrayList<PictureNews>();
			for(PictureNews model:list){
				model.setPath(getImgUrls(model.getAttachs()));
				datalist.add(model);
			}
			m.addAttribute("datalist", datalist);
			}
			return viewName("viewPictureNews");
		}

	public String getImgUrls(String id){
		if (!StringUtils.isBlank(id)) {
			return resourcesService.viewResources(id);
			}
		return null;
	}
	/**
	 * 刪除图片新闻
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/batchdelete")
	@ResponseBody
	public JuiResult batchdelete(String ids) {
		JuiResult juiResult = new JuiResult();
		try {
			if (ids != null) {
				for (String id : ids.split(",")) {
					deletePicUtil(id);
					juiResult.setMessage("删除成功！");
					LoggerUtils.updateLogger(LoggerModule.XXSY,"教学首页——图片新闻——删除图片新闻，操作者ID："+ CurrentUserContext.getCurrentUser().getId());
				}
			}
		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("删除失败！");
			logger.error("刪除图片新闻失败", e);
		}
		return juiResult;
	}

	/**
	 * 跳转图片新闻_草稿箱
	 * 
	 * @param jyAnnunciate
	 * @param m
	 * @param pageNum
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/picturenewsDraft")
	public String picturenewsDraft(PictureNews pictureNews, Model m) {
		pictureNews.addCustomCondition("group by parentid order by istop desc,crtDttm desc",new HashMap<String, Object>());
		pictureNews.setStatus(0);
		pictureNews.setIsdelete(0);
		getDraftNum(pictureNews, m);
		PageList<PictureNews> pictureNewsDraft = this.pictureNewsIndexService.findByPage(pictureNews);
		m.addAttribute("pictureNewsDraft", pictureNewsDraft);
		m.addAttribute("orgId", pictureNews.getOrgId());
		return viewName("picturenewsDraft");
	}

	/**
	 * 跳转编辑修改图片新闻
	 * 
	 * @param pictureNews
	 * @param m
	 * @return
	 */
	@RequestMapping("/editPictureNews")
	public String editPictureNews(PictureNews pictureNews, Model m) {
		PictureNews pic = this.pictureNewsIndexService.findOne(pictureNews
				.getId());
		m.addAttribute("pictureNews", pic);
		if (!StringUtils.isBlank(pic.getAttachs())) {
			Map<String, String> imgs = new HashMap<>();
			// 图片对应的资源ID
			String[] imgIds = pic.getAttachs().split("#");
			try {
				for (String resoursId : imgIds) {
					imgs.put(resoursId,resourcesService.viewResources(resoursId));
				}
			} catch (Exception e) {
				logger.error("更新图片新闻失败");
			}
			m.addAttribute("imgs", imgs);
		}
		return viewName("editPictureNews");
	}

	/**
	 * 更新图片新闻
	 * 
	 * @param pictureNews
	 * @return flag=true:更新为草稿
	 */
	@RequestMapping("/updatePictureNews")
	@ResponseBody
	public JuiResult updatePictureNews(PictureNews pictureNews, Boolean flag) {
		JuiResult juiResult = new JuiResult();
		juiResult.setMessage("操作成功");
		try {
			juiResult.setStatusCode(JuiResult.SUCCESS);
			if (StringUtils.isNotBlank(pictureNews.getFlags())) {
				for (String resourceId : pictureNews.getFlags().split("#")) {
					this.deleteImg(resourceId, true);
				}
			}
			PictureNews pic = this.pictureNewsIndexService.findOne(pictureNews.getId());
			pic.setTitle(pictureNews.getTitle());
			pic.setContent(pictureNews.getContent());
			pic.setAttachs(pictureNews.getAttachs());
			if (null != flag) {
				pic.setStatus(0);// 1:已发布 0：草稿
			} else {
				juiResult.setRel("1");
				juiResult.setMessage(defaultFrontWebUrl+ "/jy/schoolview/index?orgID=" + pic.getOrgId());
				pic.setStatus(1);
			}
			this.pictureNewsIndexService.update(pic);
			if (pic.getAttachs() != null) {
				for (String resourceId : pic.getAttachs().split("#")) {
					resourcesService.updateTmptResources(resourceId);
				}
			}
			// 刷新前台缓存
			FrontCacheUtils.delete(PictureNews.class, pic.getId());
			LoggerUtils.deleteLogger(LoggerModule.XXSY,"教学首页——图片新闻——更新图片新闻，操作者ID："+ CurrentUserContext.getCurrentUser().getId());
		} catch (Exception e) {
			juiResult.setStatusCode(JuiResult.FAILED);
			juiResult.setMessage("更新失败！");
			logger.error("更新图片新闻失败", e);
		}
		return juiResult;
	}

	/**
	 * 设置首页横幅广告置顶或非置顶
	 * @param pictureNews
	 * @param flag 1:置顶
	 * @return
	 */
	@RequestMapping("/setTop")
	@ResponseBody
	public JuiResult isShowBanner(PictureNews pictureNews, String flag) {
		JuiResult rs = new JuiResult();
		rs.setMessage("操作成功！");
		int topNum  = 0;
		try{
			PictureNews model  = this.pictureNewsIndexService.findOne(pictureNews.getId());
			PictureNews model2  = new PictureNews();
			model2.setParentid(model.getParentid());
			if(flag.equalsIgnoreCase("1")){//置顶
				topNum = setTopUtil("1");
			}
			for(PictureNews p : pictureNewsIndexService.findAll(model2)){
				p.setIstop(topNum);
				pictureNewsIndexService.update(p);
			}
			LoggerUtils.deleteLogger(LoggerModule.XXSY,"教学首页——图片新闻——更新图片新闻(操作置顶)，操作者ID："+ CurrentUserContext.getCurrentUser().getId());
		} catch (Exception e) {
			logger.error("更新通知公告失败", e);
		}
		return rs;
	}
	
	/**
	 * 设置置顶公用
	 * @param pictureNews
	 * @param flag
	 * @return
	 */
	public int setTopUtil(String flag) {
		PictureNews picsetTop = new PictureNews();
		Integer istop = 0;
		if (flag.equalsIgnoreCase("1")) {
			picsetTop.addCustomCondition("order by istop desc",new HashMap<String, Object>());
			List<PictureNews> list = pictureNewsIndexService.find(picsetTop, 1);
			if (!CollectionUtils.isEmpty(list)) {
				istop = list.get(0).getIstop()+1;
			}
		} 
		return istop;
	}
	/**
	 * 设置首页横幅广告显示或隐藏
	 */
	@RequestMapping("/setView")
	@ResponseBody
	public JuiResult setView(PictureNews pictureNews, String flag) {
		JuiResult rs = new JuiResult();
		rs.setMessage("操作成功！");
		
		try {
			if (flag.equalsIgnoreCase("1")) {
				pictureNews.setIsDisplay(1);// 已发布状态
				rs.setRel("1");
				rs.setMessage(defaultFrontWebUrl+ "/jy/schoolview/index?orgID="+ pictureNews.getOrgId());
			} else {
				pictureNews.setIsDisplay(0);
			}
			PictureNews existNews = pictureNewsIndexService.findOne(pictureNews.getId());
			if(existNews!=null){
				PictureNews childModel = new PictureNews();
				childModel.setParentid(existNews.getParentid());
				List<PictureNews> newsList = pictureNewsIndexService.findAll(childModel);
				if(!CollectionUtils.isEmpty(newsList)){//全部更新
					for (PictureNews pn : newsList) {
						pn.setIsDisplay(pictureNews.getIsDisplay());
						pictureNewsIndexService.update(pn);
					}
				}
			}
			LoggerUtils.deleteLogger(LoggerModule.XXSY,"教学首页——图片新闻——更新图片新闻，操作者ID："+ CurrentUserContext.getCurrentUser().getId());

		} catch (Exception e) {
			logger.error("更新通知公告失败", e);
		}
		return rs;
	}

	/**
	 * 草稿数量
	 * @param pictureNews
	 * @param m
	 * @return
	 */
	public int getDraftNum(PictureNews pictureNews, Model m) {
		PictureNews model = new PictureNews();
		model.setStatus(0);// 未发布
		model.setIsdelete(0);// 未删除
		model.setOrgId(pictureNews.getOrgId());
		model.addCustomCondition("group by parentid",new HashMap<String, Object>());
		List<PictureNews> list = this.pictureNewsIndexService.findAll(model);
		if (null != list && list.size() > 0) {
			m.addAttribute("_count", list.size());
			return list.size();
		} else {
			m.addAttribute("_count", 0);
		}
		return 0;
	}
	
	/**
	 * 删除图片新闻实现方法
	 * @param id
	 * @return
	 */
	public boolean deletePicUtil(String id){
		PictureNews model  =pictureNewsIndexService.findOne(Integer.parseInt(id));
		Integer pid = model.getParentid();
		PictureNews parent= new PictureNews();
		parent.setParentid(pid);
		for(PictureNews p : pictureNewsIndexService.findAll(parent)){
			this.pictureNewsIndexService.delete(p.getId());
			this.deleteImg(p.getAttachs(), true);
		}
		return true;
	}
	
	/**
	 * 删除文件
	 * 
	 * @param imgId
	 *            资源id
	 * @param isweb
	 *            是否是web下资源
	 * @return
	 */
	@RequestMapping("/deleteImg")
	public void deleteImg(String imgId, Boolean isweb) {
		resourcesService.deleteResources(imgId);
	}

}
