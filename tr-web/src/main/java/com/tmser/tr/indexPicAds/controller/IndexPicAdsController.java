package com.tmser.tr.indexPicAds.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.picturenew.service.PictureNewsService;
import com.tmser.tr.ptgg.bo.PictureNews;
@Controller
@RequestMapping("/jy/indexPicAds")
public class IndexPicAdsController extends AbstractController {
	@Autowired
	private ResourcesService resourcesService; 
	/**
	 * 获取首页图片广告列表
	 */
	@Autowired
	private PictureNewsService pictureNewsService;
	/**
	 *二期静态页面跳转图片新闻
	 * @return
	 */
	@RequestMapping("/pic_new")
	public String pic_new(PictureNews picture, Model m,@RequestParam(value="pageNum",defaultValue="1") Integer pageNum,@RequestParam(value="pageSize",defaultValue="8") Integer pageSize){
		picture.pageSize(pageSize);//设置每页的展示数
		picture.addOrder("crt_dttm desc");//倒序
		PageList<PictureNews> page = this.pictureNewsService.findByPage(picture);
		m.addAttribute("pictureNew_data", page);//分页查询数据
		return "/schoolresindex/index2_pic_new/pic_new";
	}
	
	/**
	 *二期静态页面跳转图片新闻_查看图集
	 * @return
	 */
	@RequestMapping("/atlas")
	public String atlas(Model m,String id){
		PictureNews pictureNews = pictureNewsService.findOne(Integer.parseInt(id));
		m.addAttribute("pictureNews", pictureNews);
		if(!StringUtils.isBlank(pictureNews.getAttachs())){
			List<String> imgUrls=new ArrayList<>();
			for(String resoursId:pictureNews.getAttachs().split("#")){
				imgUrls.add(resourcesService.findOne(resoursId).getPath());
			}
			m.addAttribute("imgUrls", imgUrls);
		}
		return "/schoolresindex/index2_pic_new/atlas";
	}
}
