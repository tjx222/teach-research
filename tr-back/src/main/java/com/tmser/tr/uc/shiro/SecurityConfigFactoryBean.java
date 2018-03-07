/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.shiro;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import com.tmser.tr.manage.meta.bo.Menu;
import com.tmser.tr.manage.meta.service.MenuService;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SecurityConfigFactoryBean.java, v 1.0 2015年5月21日 上午11:01:45 tmser Exp $
 */
public class SecurityConfigFactoryBean implements FactoryBean<Section>, InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfigFactoryBean.class);

	private boolean singleton = true;

	private Section singletonInstance;
	
	private Resource location;
	
	private String filterPattern;
	
	@Autowired
	private MenuService ms;
	
	/**
	 *  所有请求需要通过的过滤器
	 */
	private String necessaryFilter;
	
    /**
     * 默认premission字符串
     */
    public static final String PREMISSION_STRING="perms[\"{0}\"]";
    
    /**
     * 前置过滤器
     */
    private String preFilter;
    
    /**
     * 后置过滤器
     */
    private String postFilter;
    
    
	public String getPreFilter() {
		return preFilter;
	}
	

	public String getNecessaryFilter() {
		return necessaryFilter;
	}

	public void setNecessaryFilter(String necessaryFilter) {
		this.necessaryFilter = necessaryFilter;
	}

	public void setPreFilter(String preFilter) {
		this.preFilter = preFilter;
	}
	
	public String getPostFilter() {
		return postFilter;
	}

	public void setPostFilter(String postFilter) {
		this.postFilter = postFilter;
	}

	@Override
	public final void afterPropertiesSet() throws IOException {
		if (this.singleton) {
			initFilterPattern();
			this.singletonInstance = createSection();
		}
	}

	@Override
	public final Section getObject() throws IOException {
		if (this.singleton) {
			return this.singletonInstance;
		}
		else {
			initFilterPattern();
			return createSection();
		}
	}

	protected void initFilterPattern(){
		StringBuilder filterPattern = new StringBuilder();
		filterPattern.append(StringUtils.isEmpty(getPreFilter()) ? "" : getPreFilter()+",")
		.append(PREMISSION_STRING)
		.append(StringUtils.isEmpty(getPostFilter()) ? "" :"," + getPostFilter());
		this.filterPattern = filterPattern.toString();
	}
	
	@Override
	public Class<?> getObjectType() {
		return Section.class;
	}

	@Override
	public final boolean isSingleton() {
		return this.singleton;
	}
	
	protected Section createSection() throws IOException{
		return loadSection();
	}
	
	public void setLocation(Resource location) {
		this.location = location;
	}
	
	/**
	 * Load properties into the given instance.
	 * @param props the Properties instance to load into
	 * @throws IOException in case of I/O errors
	 * @see #setLocations
	 */
	protected Section loadSection() throws IOException {
		Ini ini = new Ini();
		if (this.location != null) {
			if (logger.isInfoEnabled()) {
					logger.info("Loading ini file from {}", location);
			}
			try {
				ini.load(this.location.getInputStream());
			}
			catch (IOException ex) {
				logger.warn("Could not load properties from  {} : {}" ,location, ex.getMessage());
			}
		}
		Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
    	if(ms != null){
    		loadSectionFromDb(section);
    	}
    	
    	if(StringUtils.isNotEmpty(getNecessaryFilter())){
    		section.put("/**", necessaryFilter);
    	}
		return section;
	}
	
	  /**
	   * 加载数据库中menu相应的配置
	   * @param section
	   */
	  private void loadSectionFromDb(Ini.Section section){
	        try{
	        	Menu model = new Menu();
	        	model.addCustomCulomn("code,url");
	        	List<Menu> rs = ms.findAll(model);
	        	for(Menu m : rs){
	        		String key = m.getUrl();
		        	String value = m.getCode();
		            if(StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)
		            		&& !"javascript:;".equalsIgnoreCase(key)) {
		            	section.put(key,MessageFormat.format(filterPattern,value));
		            	logger.debug("load permission [ {} : {} ]. ",key,value);
		            }
	        	}
	        }catch(Exception e){
	        	logger.warn("Could not load properties from  db ", e);
	        }
	    }
}
