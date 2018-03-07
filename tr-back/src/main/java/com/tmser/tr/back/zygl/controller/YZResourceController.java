package com.tmser.tr.back.zygl.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.jxtx.service.JXTXBaseManageService;
import com.tmser.tr.back.zygl.vo.ResourcesVo;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.resources.bo.ResRecommend;
import com.tmser.tr.manage.resources.service.ResRecommendService;

/**
 * 
 * <pre>
 *
 * </pre>
 * 预制资源管理
 * @author 川子
 * @version $Id: YZResourceController.java, v 1.0 2015年10月8日 上午10:04:08 川子 Exp $
 */
@Controller
@RequestMapping("/jy/back/zygl/yz/")
public class YZResourceController extends AbstractController{
	
	@Resource(name="resourcesType")
	private List<Object> resourcesType;
	
	@Autowired
	private ResRecommendService resRecommendService;
	@Autowired
	private JXTXBaseManageService jXTXBaseManageService;
	
	@Autowired
	private BookChapterService bookChapterService;
	/**
	 * 根据课题找资源首页
	 * @return
	 */
	@RequestMapping("yzresourceIndex")
	public String yzresource(Model m){
		List<Map<String,Object>> list = jXTXBaseManageService.findCatalogTree();
		m.addAttribute("list", list);
		return viewName("yzresource_tree");
	}
	
	@RequestMapping("kt_tree")
	public String kt_tree(Model m,String comId){
		List<Map<String,Object>> list = jXTXBaseManageService.findBookCatalogTree(comId);
		m.addAttribute("list", list);
		m.addAttribute("comId", comId);
		return viewName("kt_tree");
	}
	
	@RequestMapping("show_zylist")
	public String show_resources(Model m,ResRecommend ssc){
		if(ssc.order() == null||ssc.order().equals("")){
			ssc.addOrder(" qualify desc , sort desc");
		}
		PageList<ResRecommend> pageResRecommend = resRecommendService.findByPage(ssc);
		m.addAttribute("data", pageResRecommend);
		m.addAttribute("model", ssc);
		m.addAttribute("resourcesType", resourcesType);
		return viewName("yz_resource");
	}
	
	@RequestMapping("goDaoRuZiYuan")
	public String daoRuZiYuan(Model m,String id){
		m.addAttribute("bc", bookChapterService.findOne(id));
		return viewName("daoRuZy");
	}
	
	@RequestMapping("lookSelect")
	public String lookSelect(Model m){
		return viewName("dwzOrgLookup");
	}
	
	@RequestMapping("toResource")
	public String toResource(Model m){
		return viewName("db_attachmentLookup");
	}
	
	@RequestMapping("checkLX")
	public String checkLX(Model m){
		m.addAttribute("resourcesType", resourcesType);
		return viewName("checkLX");
	}
	
	/**
	 * 导入资源  
	 * @param m
	 * @param id
	 * @return
	 */
	@RequestMapping("dRuZiYuan")
	@ResponseBody
	public JuiResult dRuZiYuan(Model m,String lessonId,ResourcesVo resVo){
		JuiResult jr = new JuiResult();
		try {
			resRecommendService.batchInsert(resVo,lessonId);
			jr.setRel("resourcesByLid_zy");
			jr.setMessage("导入成功！");
		} catch (Exception e) {
			jr.setMessage("导入失败！");
			logger.error("资源导入失败", e);
		}
		return jr;
	}
	
	/**
	 * 修改资源
	 * @param m
	 * @param lessonId
	 * @param resVo
	 * @return
	 */
	@RequestMapping("editResources")
	@ResponseBody
	public JuiResult editResources(ResRecommend resRecommend){
		JuiResult jr = new JuiResult();
		try {
			resRecommendService.editResRecommend(resRecommend);
			jr.setRel("show_yzResources");
			jr.setMessage("修改成功！");
		} catch (Exception e) {
			jr.setMessage("修改失败！");
			e.printStackTrace();
			logger.error("资源修改失败", e);
		}
		return jr;
	}
	
	/**
	 * 去修改页面
	 * @param m
	 * @param lessonId
	 * @param resVo
	 * @return
	 */
	@RequestMapping("goEdit")
	public String goEdit(Model m,Integer id){
		ResRecommend resRecommend = resRecommendService.findOne(id);
		m.addAttribute("resourcesType", resourcesType);
		m.addAttribute("resRecommend", resRecommend);
		return viewName("editResources");
	}
	
	/**
	 * 批量删除--资源
	 * @param m
	 * @param ids
	 * @return
	 */
	@RequestMapping("batchDelete")
	@ResponseBody
	public JuiResult batchDelete(Integer[] ids){
		JuiResult jr = new JuiResult();
		try {
			if (ids!=null) {
				resRecommendService.batchDelete(ids);
				jr.setMessage("删除成功！");
				jr.setRel("show_yzResources");
			}else{
				jr.setStatusCode(JuiResult.FAILED);
				jr.setMessage("没有选择任何条目，删除失败！");
			}
		} catch (Exception e) {
			jr.setMessage("资源删除失败！");
			logger.error("资源删除失败", e);
		}
		return jr;
	}
	
}
