package com.tmser.tr.back.monitor.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.bo.App;
import com.tmser.tr.uc.bo.AppParam;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.AppParamService;
import com.tmser.tr.uc.service.AppService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.Identities;

@Controller
@RequestMapping("/jy/back/monitor/app")
public class AppController extends AbstractController {
	
	private final static Logger logger = LoggerFactory.getLogger(AppController.class);

	@Resource
	private AppService appService;
	@Resource
	private AppParamService appParamService;
	
	@RequestMapping("/index")
	public String appIndex(Model m,App app){
		app.pageSize(20);
		String appname = app.getAppname();
		if(!StringUtils.isEmpty(appname)){
			app.setAppname(SqlMapping.LIKE_PRFIX +appname+SqlMapping.LIKE_PRFIX);
		}
		if(app.order() == null || "".equals(app.order())){
			app.addOrder(" crtDttm desc");
		}
		PageList<App> findByPage = appService.findByPage(app);
		app.setAppname(appname);
		m.addAttribute("data", findByPage);
		m.addAttribute("model", app);
		return viewName("index");
	}
	@RequestMapping("/addOrEditApp")
	public String addOrEditApp(Model m,App app){
		if(app.getId() != null){
			m.addAttribute("model", appService.findOne(app.getId()));
		}else{
			app.setAppid(Identities.uuid());
			app.setAppkey(Identities.uuid());
			app.setUrl("addrole");
			m.addAttribute("model", app);
		}
		return viewName("addOrEditApp");
	}
	@RequestMapping("/getDataByType")
	public String getAppParamByType(Model m,App model){
		m.addAttribute("model", model);
		Map<String,Object> appMap = new HashMap<String,Object>();
		AppParam param = new AppParam();
		param.setAppid(model.getAppid());
		List<AppParam> findAll = appParamService.findAll(param);
		for (AppParam appParam : findAll) {
			appMap.put(appParam.getName(), appParam.getVal());
		}
		m.addAttribute("appParam", appMap);
		return viewName("selectApp");
	}
	@ResponseBody
	@RequestMapping("/deleteApp")
	public Object deleteApp(Model m,App app){
		JuiResult rs = new JuiResult();
		try {
			if(app.getId() != null){
				App appOne = appService.findOne(app.getId());
				appService.delete(app.getId());
				this.deleteAppParam(appOne);
			}
			rs.setMessage("app删除失败！");
			rs.setStatusCode(JuiResult.SUCCESS);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("app添加或删除失败！");
			logger.error("app添加或删除失败",e);
		}
		return rs;
	}
	private void deleteAppParam(App appOne){
		if(appOne != null && appOne.getAppid() != null){
			AppParam param = new AppParam();
			param.setAppid(appOne.getAppid());
			List<AppParam> findAll = appParamService.findAll(param);
			if(!CollectionUtils.isEmpty(findAll)){
				for (AppParam appParam : findAll) {
					appParamService.delete(appParam.getId());
				}
			}
		}
	}
	private void saveAppParam(App app){
		List<AppParam> param = app.getParam();
		if(!CollectionUtils.isEmpty(param) && app.getAppid() != null){
			for (AppParam appParam : param) {
				appParam.setAppid(app.getAppid());
				appParamService.save(appParam);
			}
		}
	}
	@ResponseBody
	@RequestMapping("/saveApp")
	public Object saveApp(Model m,App app){
		JuiResult rs = new JuiResult();
		User user = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		try {
			if(app.getId() != null){
				App findOne = appService.findOne(app.getId());
				this.deleteAppParam(findOne);
				this.saveAppParam(app);
				app.setLastupDttm(new Date());
				app.setLastupId(user.getId());
				appService.update(app);
				rs.setMessage("app修改成功！");
			}else{
				app.setCrtDttm(new Date());
				app.setCrtId(user.getId());
				appService.save(app);
				this.saveAppParam(app);
				rs.setMessage("app添加成功！");
			}
			
			rs.setStatusCode(JuiResult.SUCCESS);
		} catch (Exception e) {
			rs.setStatusCode(JuiResult.FAILED);
			rs.setMessage("app添加或修改失败！");
			logger.error("app添加或修改失败",e);
		}
		return rs;
	}
}
