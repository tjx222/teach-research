package com.tmser.tr.comment.controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.tmser.tr.comment.bo.Discuss;
import com.tmser.tr.comment.service.DiscussService;
import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.notice.service.bo.MessageNumCacheEntity;

/**
 * <pre>
 *		区域教研中各活动的讨论相关功能controller
 * </pre>
 *
 * @author tmser
 * @version $Id: AreaActivityDiscussController.java, v 1.0 2016年9月29日 下午4:01:31 tmser Exp $
 */
@Controller
@RequestMapping("/jy/comment")
public class DiscussController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(DiscussController.class);
	
	// 超时时间5分钟
	private static final Long TIME_OUT = 20000L;
	
	@Resource
	private DiscussService discussService;
	
	@Resource(name = "cacheManger")
	private CacheManager cacheManager;

	private Cache messageNumCache;

	@PostConstruct
	public void init() {
		messageNumCache = cacheManager.getCache("messageNumCache");
	}
	
	/**
	 * 查询活动下面的讨论列表
	 */
	@UseToken
	@RequestMapping("/discussIndex")
	public String discussIndex(Discuss dis,Boolean canReply,String searchName,Model m,Page page){
		dis.addPage(page);
		PageList<Discuss> discussList = discussService.discussIndex(dis,searchName);
		m.addAttribute("discussList", discussList);
		m.addAttribute("searchName", searchName);
		m.addAttribute("activityDiscuss", dis);
		m.addAttribute("canReply",canReply);
		return  viewName("discuss");
	}
	/**
	 * 保存活动讨论
	 */
	@RequestMapping("/discussSave")
	@UseToken
	public void discussSave(Discuss aad,Model m){
		Boolean isSuccess = true ;
		try {
			String msg = discussService.saveDiscuss(aad);
			m.addAttribute("msg", msg);
		} catch (Exception e) {
			isSuccess = false ;
			logger.error("---活动发表讨论失败---",e);
		}
		m.addAttribute("isSuccess", isSuccess);
	}
	
	/**
	 * 移动端-查询活动下面的讨论列表
	 */
	@RequestMapping("/discussIndexTB")
	public String discussIndexTB(Discuss dis,Boolean canReply,String searchName,Model m,Page page){
		dis.addPage(page);
		PageList<Discuss> discussList = discussService.discussIndex(dis,searchName);
		m.addAttribute("discussList", discussList);
		m.addAttribute("searchName", searchName);
		m.addAttribute("activityDiscuss", dis);
		m.addAttribute("canReply",canReply);
		return  viewName("discussTB");
	}
	
	/**
	 * 获取未阅读消息数目
	 * 
	 * @param timestamp
	 * @return
	 */
	@RequestMapping(value = "/isupdate", method = RequestMethod.GET)
	public @ResponseBody DeferredResult<Result> listener(Discuss dis,
			@RequestParam(value="timestamp",required=false) Long timestamp) {
		DeferredResult<Result> result = new DeferredResult<Result>(TIME_OUT,new Result("timeOut"));
		StringBuilder key = new StringBuilder("DIS_T_").append(dis.getTypeId()).append("_R_").append(dis.getActivityId());
		ValueWrapper valueWrapper = messageNumCache.get(key.toString());
		// 设置缓存
		if (valueWrapper == null) {
			MessageNumCacheEntity entity = new MessageNumCacheEntity();
			entity.increaseNotice(0);
			entity.addResult(timestamp, result);
			// 放入缓存
			messageNumCache.put(key.toString(), entity);
		} else {
			MessageNumCacheEntity entity = (MessageNumCacheEntity) valueWrapper.get();
			entity.addResult(timestamp, result);
		}
		return result;
	}
}
