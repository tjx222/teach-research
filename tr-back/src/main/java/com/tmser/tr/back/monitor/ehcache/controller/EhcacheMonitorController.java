/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.monitor.ehcache.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.monitor.MoitorBaseController;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.utils.PrettyMemoryUtils;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: EhcacheMonitorController.java, v 1.0 2015年9月28日 下午4:12:04 tmser Exp $
 */
@Controller
@RequestMapping("/jy/back/monitor/ehcache")
public class EhcacheMonitorController extends MoitorBaseController {
	@Resource(name="cacheManger")
    private CacheManager cacheManager;
	
    @RequestMapping()
    public String index(Model model) {
    	model.addAttribute("cacheManager", cacheManager);
        return viewName("/index");
    }
    
    @RequestMapping("/front")
    public String front(Model model) {
    	model.addAttribute("src", StringUtils.isEmpty(defaultFrontWebUrl) ? "" :
    		defaultFrontWebUrl+"/jy/ws/monitor/ehcache");
        return front();
    }

    @RequestMapping("/{cacheName}/details")
    public String details(
            @PathVariable("cacheName") String cacheName,
            @RequestParam(value = "searchText", required = false, defaultValue = "") String searchText,
            Model model) {

        model.addAttribute("cacheName", cacheName);
        Cache cache = (Cache)cacheManager.getCache(cacheName).getNativeCache();
        List<?> allKeys = cache.getKeys();
       
        List<String> showKeys = new ArrayList<String>();

        for(Object key : allKeys) {
            if(key.toString().contains(searchText)) {
                showKeys.add(key.toString());
            }
        }
        Map<String,Map<String,Object>> keydetails = new HashMap<String,Map<String,Object>>();
        for(Object key : allKeys){
        	keydetails.put(String.valueOf(key), keyDetail(cacheName,key));
        }
        model.addAttribute("keys", showKeys);
        model.addAttribute("keydetails", keydetails);
        return "/back/monitor/ehcache/details";
    }

    private Map<String, Object> keyDetail(String cacheName, Object key) {
    	Cache cache = (Cache)cacheManager.getCache(cacheName).getNativeCache();
        Element element = cache.get(key);
        Map<String, Object> data = new HashMap<String,Object>();
        if(element != null){
        	 String dataPattern = "yyyy-MM-dd HH:mm:ss";	
        	data.put("size", PrettyMemoryUtils.prettyByteSize(element.getSerializedSize()));
            data.put("hitCount", element.getHitCount());

            Date latestOfCreationAndUpdateTime = new Date(element.getLatestOfCreationAndUpdateTime());
            data.put("latestOfCreationAndUpdateTime", DateUtils.formatDate(latestOfCreationAndUpdateTime, dataPattern));
            Date lastAccessTime = new Date(element.getLastAccessTime());
            data.put("lastAccessTime", DateUtils.formatDate(lastAccessTime, dataPattern));
            if(element.getExpirationTime() == Long.MAX_VALUE) {
                data.put("expirationTime", "不过期");
            } else {
                Date expirationTime = new Date(element.getExpirationTime());
                data.put("expirationTime", DateUtils.formatDate(expirationTime, dataPattern));
            }

            data.put("timeToIdle", element.getTimeToIdle());
            data.put("timeToLive", element.getTimeToLive());
            data.put("version", element.getVersion());
        }
        
        return data;
    }


    @RequestMapping("/{cacheName}/{key}/delete")
    @ResponseBody
    public JuiResult delete(
            @PathVariable("cacheName") String cacheName,
            @PathVariable("key") String key
    ) {
    	JuiResult rs = JuiResult.forwardInstance();
    	org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
    	if(cache != null)
    		cache.evict(key);
        logger.debug("evict cache:{},  key:{}", cacheName, key);
        return rs;
    }

    @RequestMapping("/{cacheName}/clear")
    @ResponseBody
    public JuiResult clear(
            @PathVariable("cacheName") String cacheName
    ) {
    	JuiResult rs = JuiResult.forwardInstance();
    	rs.setMessage("缓存清除成功");
    	org.springframework.cache.Cache cache = cacheManager.getCache(cacheName);
    	if(cache != null){
    		((Cache)cache.getNativeCache()).clearStatistics();
    		cache.clear();
    	}
        logger.debug("clear cache:{}", cacheName);
        return rs;
    }

}
