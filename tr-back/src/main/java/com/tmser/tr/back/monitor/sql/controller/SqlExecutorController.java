/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.monitor.sql.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.bo.QueryObject;
import com.tmser.tr.common.dao.AbstractQuery;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.Constants;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SqlExecutorController.java, v 1.0 2015年12月24日 下午4:11:31 tmser Exp $
 */
@Controller
@RequestMapping("/jy/back/monitor/sql")
public class SqlExecutorController extends AbstractController {
 
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private volatile SqlExecutor sqlExecutor;
	
    @RequestMapping()
    public String index() {
        return viewName("index");
    }
    
    @RequestMapping(value = "/sql", method = RequestMethod.POST)
    public String executeQL(
            final @RequestParam("sql") String sql, final Model model,
            final SqlPage pageInfo
    ) {
    	if(StringUtils.isNotEmpty(sql)){
    		LoggerUtils.searchLogger(LoggerModule.MONITOR, "执行sql: {}", sql);
	    		try{
	    		 String lowerCaseSQL = sql.trim().toLowerCase();
	    	        final boolean isDML = lowerCaseSQL.startsWith("insert") || lowerCaseSQL.startsWith("update") || lowerCaseSQL.startsWith("delete");
	    	        final boolean isDQL = lowerCaseSQL.startsWith("select");
	
	    	        if(!isDML && !isDQL) {
	    	            model.addAttribute(Constants.ERROR, "您执行的SQL不允许，只允许insert、update、delete、select");
	    	        }else{
	    	        	 if (isDML) {
	    	        		 model.addAttribute("updateCount", getSqlExecutor().excuteDml(lowerCaseSQL));
	    	        	 }else{
	    	        		 PageList<Map<String,Object>> rs = getSqlExecutor().excuteDql(lowerCaseSQL, pageInfo.getPage());
	    	        		 if(rs.getDatalist().size() > 0){
	    	        			 List<String> names = new ArrayList<String>();
	    	        			 for(String name : rs.getDatalist().get(0).keySet()){
	    	        				 names.add(name);
	    	        			 }
	    	        			 model.addAttribute("names", names);
	    	        		 }
	    	        		 model.addAttribute("data", rs);
	    	        	 }
	    	        }
	    	} catch (Exception e) {
	            StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            model.addAttribute(Constants.ERROR, sw.toString());
	        }
    	}
    	model.addAttribute("sql",sql);
    	return index();
    }
    
    /** 
	 * Getter method for property <tt>sqlExecutor</tt>. 
	 * @return property value of sqlExecutor 
	 */
	public SqlExecutor getSqlExecutor() {
		if(sqlExecutor == null){
			sqlExecutor = new SqlExecutor();
			sqlExecutor.setJdbcTemplate(jdbcTemplate);
			
		}
		return sqlExecutor;
	}
    
    class SqlExecutor extends AbstractQuery{
    	
    	Integer excuteDml(String sql){
    		return update(sql);
    	}
    	
    	public PageList<Map<String,Object>> excuteDql(String sql,Page pageInfo){
    		return queryPage(sql, null, pageInfo);
    	}
    }
    
   public static class SqlPage extends QueryObject{

		private static final long serialVersionUID = 1L;

		@Override
		public boolean equals(Object obj) {
			return obj != null && obj == this ;
		}

		@Override
		public int hashCode() {
			return 1;
		}
    	
    }
}
