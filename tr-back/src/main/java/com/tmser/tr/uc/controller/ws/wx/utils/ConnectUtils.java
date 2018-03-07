/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.controller.ws.wx.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author tmser
 * @version $Id: ConnectUtils.java, v 1.0 2015年11月30日 下午5:31:44 tmser Exp $
 */
public class ConnectUtils {

  private static Logger logger = LoggerFactory.getLogger(ConnectUtils.class);
  
  
  
  static final Pattern PARAM_PATTERN = Pattern.compile("\\{\\s*([a-zA-Z0-9_]+)\\s*\\}");
  
  /**
   * get 方式调用服务接口
   * @param url
   * @return
   */
  public static final String get(String url){
	 return get(url,null);
  }
  
  
  /**
   * get 方式调用服务接口
   * @param url
   * @return
   */
  public static final String get(String url,Map<String,String> paramMap){
	return get(url,paramMap,30000,30000);
  }
  
  /**
   * 
   * @param url
   * @param paramMap
   * @param connectTimeout
   * @param socketTimeout
   * @return
   */
  public static final String get(String url,Map<String,String> paramMap,
		  int connectTimeout,int socketTimeout){
	try {
		return Request.Get(formatUrl(url,paramMap))
				.connectTimeout(connectTimeout)
				.socketTimeout(socketTimeout)
				.execute().returnContent().asString();
	}catch (Exception e) {
		logger.error("请求中心服务失败，url = [{}]", url);
		logger.error("", e);
	}
	return null;
  }
  
  
   static String formatUrl(String url,Map<String,String> params){
	  if(StringUtils.isNotEmpty(url) && params != null){
		  StringBuffer sb = new StringBuffer();
		  Matcher m = PARAM_PATTERN.matcher(url);
		  String paramValue = "";
		  while(m.find()) {
			  String key = m.group(1).trim();
			  paramValue = params.get(key);
			  if(StringUtils.isNotBlank(paramValue)){
					 m.appendReplacement(sb, paramValue); 
					 params.remove(key);
			  }
		   }
		  m.appendTail(sb);
		  List<NameValuePair> ps = new ArrayList<NameValuePair>();
		  for(String key : params.keySet()){
			  ps.add(new BasicNameValuePair(key,params.get(key)));
		  }
		  try {
				String str = EntityUtils.toString(new UrlEncodedFormEntity(ps));
				sb.append(sb.lastIndexOf("?") > 0 ? "&" : "?")
				  .append(str);
			} catch (Exception e) {
				logger.error("解析错误", e);
			} 
		  return  sb.toString();
	  }
	  return url;
  }
   
  
}
