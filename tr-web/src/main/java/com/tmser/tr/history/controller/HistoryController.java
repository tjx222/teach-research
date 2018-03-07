/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.history.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.history.service.HistoryCount;
import com.tmser.tr.history.vo.HistoryColumn;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.bo.UsermenuHistory;
import com.tmser.tr.uc.service.UserSpaceService;
import com.tmser.tr.uc.service.UsermenuHistoryService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.utils.SpringContextHolder;

/**
 * <pre>
 * 历史学年记录模块入口
 * </pre>
 *
 * @author tmser
 * @version $Id: HistoryController.java, v 1.0 2016年5月18日 下午3:14:27 tmser Exp $
 */
@Controller
@RequestMapping("/jy/history")
public class HistoryController extends AbstractController{
	
	@Resource
	private UsermenuHistoryService usermenuHistoryService;
	
	@Resource
	private UserSpaceService userSpaceService;
	
	@Resource(name="cacheManger")
    private CacheManager cacheManager;

    private Cache historyColumnsCache;
    
    private List<HistoryCount> historyCountList;
   
    
    /** 
	 * Getter method for property <tt>historyColumnsCache</tt>. 
	 * @return property value of historyColumnsCache 
	 */
	public Cache getHistoryColumnsCache() {
		if(historyColumnsCache == null && cacheManager != null){
			historyColumnsCache = cacheManager.getCache("historyColumns");
		}
		return historyColumnsCache;
	}

	/**
	 * 首页
	 * @param m
	 * @return
	 */
	@RequestMapping(value={"/{year}/index","/{year}/"})
	public String index(@PathVariable("year")Integer schoolYear, 
			@RequestParam(value="code",defaultValue="") String code,
			Model m){
		User user = CurrentUserContext.getCurrentUser();
		m.addAttribute("userSpaces",userSpaceService.listUserSpaceBySchoolYear(user.getId(), schoolYear));
		m.addAttribute("year",schoolYear);
		m.addAttribute("code",code);
		return viewName("index");
	}
	
	/**
	 * 首页
	 * @param m
	 * @return
	 */
	@RequestMapping(value={"/{year}/data"})
	@ResponseBody
	public Result data(@PathVariable("year")Integer schoolYear, 
			@RequestParam(value="userCache",defaultValue="true") Boolean useCache){
		User user = CurrentUserContext.getCurrentUser();
		Result rs = Result.newInstance();
		rs.setData(parseColumns(user.getId(),schoolYear,useCache));
		return rs;
	}
	@ResponseBody
	@RequestMapping(value={"/getYear"})
	public Object getYear(){
		List<Integer> values = new ArrayList<Integer>();
		User user = CurrentUserContext.getCurrentUser();
		UsermenuHistory historyYear = new UsermenuHistory();
		historyYear.setUserId(user.getId());
		historyYear.addOrder("schoolYear desc");
		List<UsermenuHistory> findAll = usermenuHistoryService.findAll(historyYear);
		if(!CollectionUtils.isEmpty(findAll)){
			for (UsermenuHistory usermenuHistory : findAll) {
				values.add(usermenuHistory.getSchoolYear());
			}
		}
		return values;
	}
	/*
	 * 解析用户历史学年栏目
	 */
	@SuppressWarnings("unchecked")
	private List<HistoryColumn> parseColumns(Integer userId,Integer schoolYear,Boolean useCache){
		String cacheKey = new StringBuilder("hc").append(userId).append("-").append(schoolYear).toString();
		List<HistoryColumn>  hcLst;
		if(getHistoryColumnsCache() != null && useCache){
			ValueWrapper cacheElement = historyColumnsCache.get(cacheKey);
			if(cacheElement != null){
				hcLst = (List<HistoryColumn>)cacheElement.get();
				if(hcLst != null)
					return hcLst;
			}
		}
		
	    hcLst = new ArrayList<HistoryColumn>();
		Set<String> hisMenuSet = usermenuHistoryService.listUserHistory(userId, schoolYear);
		if(hisMenuSet.size() > 0){
			for(HistoryCount hc : getHistoryCountBeanList()){
				for(HistoryColumn clm : hc.getColumns()){
					if(hisMenuSet.contains(clm.getCode())){
						HistoryColumn newClm = clm.copy();
						newClm.setCount(hc.count(userId,newClm.getCode(), schoolYear));
						hcLst.add(newClm);
					}
				}
			}
		}
		
		if(hcLst.size() > 1 )
			Collections.sort(hcLst);

		historyColumnsCache.put(cacheKey,hcLst);
		
		return hcLst;
	}

	private List<HistoryCount> getHistoryCountBeanList(){
		if(historyCountList == null){
			 historyCountList = SpringContextHolder.getBeanNamesForType(HistoryCount.class);
		}
		return historyCountList;
	}
}
