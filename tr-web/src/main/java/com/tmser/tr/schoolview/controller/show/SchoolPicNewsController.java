package com.tmser.tr.schoolview.controller.show;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.picturenew.service.PictureNewsService;
import com.tmser.tr.ptgg.bo.PictureNews;
import com.tmser.tr.schoolview.controller.CommonController;
import com.tmser.tr.schoolview.vo.CommonModel;

/**
 * 首页图片新闻
 * 
 * @author ljh
 *
 */
@Controller
@RequestMapping("/jy/schoolview/show/")
public class SchoolPicNewsController extends CommonController {
	
	@Autowired
	private PictureNewsService pictureNewsService;
	@Autowired
	private ResourcesService resourcesService;

	/**
	 * 加载首页图片新闻
	 * 
	 * @param picture
	 * @param m
	 * @return
	 */
	@RequestMapping("/loadIndexPicNews")
	public String loadIndexPicNews(CommonModel cm, Model m) {
		PictureNews picture = getPicSet(cm);
		picture.addGroup("parentid");
		picture.addOrder("istop desc,parentid desc,sort asc");
		m.addAttribute("pictureNew_data", pictureNewsService.find(picture, 5));// 分页查询数据
		handleCommonVo(cm, m);
		return "/schoolview/show/ttxw/indexPicNews";
	}

	/**
	 * 图片新闻列表
	 * 
	 * @param picture
	 * @param m
	 * @param pageNum
	 * @param pageSize
	 * @return 
	 */
	@RequestMapping("/pic_new")
	public String pic_new(CommonModel cm,PictureNews picture,Model m,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "8") Integer pageSize) {
		picture = getPicSet(cm);
		picture.pageSize(pageSize);// 设置每页的展示数
		picture.addGroup("parentid");
		picture.addOrder("istop desc,parentid desc,sort asc");
		PageList<PictureNews> page = this.pictureNewsService.findByPage(picture);
		m.addAttribute("pictureNew_data", page);// 分页查询数据
		handleCommonVo(cm, m);
		return "schoolview/show/ttxw/index2_pic_new/pic_new";

	}
	/**
	 * 新闻图片详情
	 * 
	 * @param m
	 * @param id
	 * @return
	 */
	@RequestMapping("/atlas")
	public String atlas(CommonModel cm, Model m, Integer id) {
		getPicNewDetail(cm, m, id);
		return "/schoolview/show/ttxw/index2_pic_new/atlas";
	}

	/**
	 * 获取上一篇图片新闻
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("prenextPictureNews")
	public String prenextPictureNews(CommonModel cm, Model model, Integer id) {
		PictureNews nextPicture = getOtherPic(cm,id,"pre");
		getPicNewDetail(cm, model, nextPicture.getId());
		return "/schoolview/show/ttxw/index2_pic_new/atlas";
	}

	/**
	 * 获取下一篇图片新闻
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("nextPictureNews")
	public String nextPictureNews(CommonModel cm, Model model, Integer id) {
		PictureNews nextPicture = getOtherPic(cm,id,"next");
		getPicNewDetail(cm, model, nextPicture.getId());
		return "/schoolview/show/ttxw/index2_pic_new/atlas";
	}
	/**
	 * 获取上、下一篇通用
	 * @param id
	 * @param type
	 * @return
	 */
	private PictureNews getOtherPic(CommonModel cm,Integer id,String type){
		//所有图片新闻集合
		PictureNews picture = getPicSet(cm);
		picture.addGroup("parentid");
		picture.addOrder("istop desc,parentid desc,sort asc");
		List<PictureNews> list = this.pictureNewsService.findAll(picture);
		//筛选下一篇
		List<Integer> indexList = this.getValues(list, "id");
		int index = indexList.indexOf(id);
		PictureNews nextPicture = new PictureNews();
		if(index>-1){
			if(type.equals("next")){
				nextPicture = list.get(index+1);
			}else{
				nextPicture = list.get(index-1);
			}
		}
		return nextPicture;
	}
	
	
	

	/**
	 * 获取第一篇图片新闻
	 * 
	 * @param id
	 * @return
	 */
	public PictureNews getFirstPictureNews(CommonModel cm) {
		PictureNews picture = getPicSet(cm);
		picture.addGroup("parentid");
		picture.addOrder("istop desc,parentid desc,sort asc");
		List<PictureNews> jList = pictureNewsService.findAll(picture);
		if (!CollectionUtils.isEmpty(jList)) {
			return jList.get(0);
		}
		return null;
	}

	/**
	 * 获取最后一篇图片新闻
	 * 
	 * @param id
	 * @return
	 */
	public PictureNews getLastPictureNews(CommonModel cm) {
		PictureNews picture = getPicSet(cm);
		picture.addGroup("parentid");
		picture.addOrder("istop desc,parentid desc,sort asc");
		List<PictureNews> jList = pictureNewsService.findAll(picture);
		if (!CollectionUtils.isEmpty(jList)) {
			return jList.get(jList.size()-1);
		}
		return null;
	}

	/**
	 * 图片详情
	 * 
	 * @param m
	 * @param id
	 */
	public void getPicNewDetail(CommonModel cm, Model m, Integer id) {
		PictureNews pictureNews = new PictureNews();
		pictureNews.setParentid(pictureNewsService.findOne(id).getParentid());
		pictureNews.addOrder("istop desc,parentid desc,sort asc");
		List<PictureNews> list = pictureNewsService.findAll(pictureNews);
		if (!CollectionUtils.isEmpty(list)) {
			for (PictureNews data : list) {
				if (data.getAttachs() != null) {
					data.setPath(resourcesService.findOne(data.getAttachs()).getPath());
				}
			}
		}
		m.addAttribute("detailList", list);
		m.addAttribute("count", list.size());
		m.addAttribute("pictureNews", list.get(0));// 用于前台显示title和date
		// 判断是否是第一篇或者最后一篇图片新闻
		PictureNews first = getFirstPictureNews(cm);
		PictureNews last = getLastPictureNews(cm);
		if (null != first && pictureNews.getParentid().equals(first.getParentid())) {
			m.addAttribute("isFirst", true);
		} else {
			// 获取上一篇内容
			PictureNews nextPicture = getOtherPic(cm,id,"pre");
			nextPicture.setPath(resourcesService.findOne(nextPicture.getAttachs()).getPath());
			m.addAttribute("isFirst", false);
			m.addAttribute("predata", nextPicture);
		}
		if (null != last && id.equals(last.getId())) {
			m.addAttribute("isLast", true);
		} else {
			PictureNews nextPicture = getOtherPic(cm,id,"next");
			nextPicture.setPath(resourcesService.findOne(nextPicture.getAttachs()).getPath());
			m.addAttribute("isLast", false);
			m.addAttribute("nextdata", nextPicture);
		}
		handleCommonVo(cm, m);
	}
	/**
	 * 共用方法
	 * 
	 * @param cm
	 * @return
	 */
	public PictureNews getPicSet(CommonModel cm) {
		PictureNews pic = new PictureNews();
		pic.setIsdelete(0);// 未删除
		pic.setIsDisplay(1);// 展示
		pic.setStatus(1);// 已发布
		pic.setOrgId(cm.getOrgID());
		return pic;
	}
	/**
	 * 获取集合元素中指定属性的集合,可以重复,不排序
	 * @param list
	 * @param field
	 * @return
	 */
	private  List<Integer> getValues(List<PictureNews> list,String field){
		List<Integer> List = new ArrayList<Integer>();
		//遍历list获取元素属性
		for(PictureNews elem:list){
			List.add(elem.getId());
		}
		return List;
	}
}
