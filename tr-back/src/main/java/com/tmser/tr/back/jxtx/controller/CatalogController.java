/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.jxtx.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.jxtx.service.JXTXBaseManageService;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.utils.FrontCacheUtils;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.utils.FileUtils;

/**
 * <pre>
 * 	目录管理Controller
 * </pre>
 * 
 * @author zpp
 * @version $Id: CatalogController.java, v 1.0 2015年9月7日 上午11:37:35 zpp Exp $
 */
@Controller
@RequestMapping("/jy/back/jxtx")
public class CatalogController extends AbstractController {

	@Value("#{config.getProperty('container_url_encode','iso-8859-1')}")
	private String urlEncode;
	@Autowired
	private JXTXBaseManageService jXTXBaseManageService;
	@Autowired
	private BookChapterService bookChapterService;

	/**
	 * 目录管理入口地址
	 * @param m
	 * @return
	 */
	@RequestMapping("/catalog_index")
	public String catalog_index(Model m) {
		List<Map<String,Object>> list = jXTXBaseManageService.findCatalogTree();
		m.addAttribute("list", list);
		return viewName("catalog/tree_index");
	}

	/**
	 * 获得章节目录树型结构
	 * @param m
	 * @param comId
	 * @return
	 */
	@RequestMapping("/catalog_tree")
	public String catalog_tree(Model m,String comId) {
		List<Map<String,Object>> list = jXTXBaseManageService.findBookCatalogTree(comId);
		m.addAttribute("list", list);
		m.addAttribute("comId", comId);
		return viewName("catalog/catalog_tree");
	}

	/**
	 * 删除章节目录数据
	 * @param bc
	 */
	@RequestMapping("/delete_catalog")
	public void delete_catalog(Model m,String chapterId) {
		try {
			bookChapterService.delete(chapterId);
			LoggerUtils.deleteLogger(LoggerModule.JXTX, "教学体系——目录管理——删除目录，目录ID："+chapterId);
			m.addAttribute("result", "success");
		} catch (Exception e) {
			m.addAttribute("result", "fail");
			logger.error("目录章节保存出错！",e);
		}
	}

	/**
	 * 删除原有的目录节点信息
	 * @param comId
	 */
	@RequestMapping("/delExportChapter")
	public void delExportChapter(Model m,@RequestParam(value = "comId") String comId) {
		try {
			jXTXBaseManageService.delExportChapter(comId);
			LoggerUtils.deleteLogger(LoggerModule.JXTX, "教学体系——目录管理——删除目录导出的zip包，包名："+comId);
			m.addAttribute("result", "success");
		} catch (Exception e) {
			m.addAttribute("result", "fail");
			logger.error("--删除目录节点出错--",e);
		}
	}

	protected ResponseEntity<byte[]> downloadFile(File file ,String userAgent) throws IOException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String fn = file.getName();
		String encode = urlEncode.toLowerCase();
		if (null != userAgent) {
			userAgent = userAgent.toLowerCase();
			if(userAgent.indexOf("firefox") == -1  &&  userAgent.indexOf("chrome") == -1){
				if(!"utf-8".equals(encode))
					fn = new String(fn.getBytes(encode),"utf-8");
				fn = java.net.URLEncoder.encode(fn, "utf-8").replace("+", "%20");
			}else{
				if(!"iso-8859-1".equals(encode))
					fn = new String(fn.getBytes(encode),"iso-8859-1");
			}

		}
		List<Charset> charList = new ArrayList<Charset>();
		headers.setAcceptCharset(charList);
		headers.setContentDispositionFormData("attachment", fn);

		return new ResponseEntity<byte[]>(
				FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}

	/**
	 * 导出教材目录
	 * @param comId
	 */
	@RequestMapping("/exportChapter")
	@ResponseBody
	public ResponseEntity<byte[]> downloadWithPath(
			@RequestParam(value = "comId") String comId,
			@RequestHeader("User-Agent") String userAgent) throws IOException {
		File file = jXTXBaseManageService.exportChapter(comId);
		if(file != null ){
			LoggerUtils.exportLogger(LoggerModule.JXTX, "教学体系——目录管理——导出书籍目录的zip包，包名："+comId);
			return downloadFile(file,userAgent);
		}
		return null;
	}
	/**
	 * 刷新缓存
	 * @param bc
	 */
	@RequestMapping("/refreshCache")
	public void refreshCache(Model m,@RequestParam(value="bookId",required=true)String bookId) {
		try {
			//刷新前台目录章节缓存
			FrontCacheUtils.delete("bookChapterData", bookId+"_list");
			FrontCacheUtils.delete("bookChapterData", bookId+"_map");
			FrontCacheUtils.delete("bookChapterData", bookId+"_tree");
			m.addAttribute("result", "success");
		} catch (Exception e) {
			m.addAttribute("result", "fail");
			logger.error("刷新缓存出错！",e);
		}
	}
	/**
	 * 保存修改章节目录数据
	 * @param bc
	 */
	@RequestMapping("/save_catalog")
	public void save_catalog(Model m,BookChapter bc) {
		try {
			bookChapterService.saveUpCatalog(bc);
			m.addAttribute("result", "success");
		} catch (Exception e) {
			m.addAttribute("result", "fail");
			logger.error("目录章节保存出错！",e);
		}
	}

}
