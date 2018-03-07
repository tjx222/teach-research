package com.tmser.tr.webservice.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.webservice.user.data.ResultModel;
import com.tmser.tr.webservice.user.data.UserInfo;
import com.tmser.tr.webservice.user.service.UserInfoService;
import com.tmser.tr.webservice.user.statics.ErrorCodeEnum;

@Controller
@RequestMapping("/jy/ws/user")
public class SyncUserInfoController extends AbstractController {
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	OrganizationDao orgDao;
	
	@ResponseBody
	@RequestMapping(value = "/insertUserInfo", method = RequestMethod.POST)
	public ResultModel insert(Model m, String userList) {
		ResultModel result = new ResultModel();
		List<UserInfo> userInfos=new ArrayList<>();
		try {
			List<UserInfo> buffer = JSON.parseArray(userList, UserInfo.class);
			if(buffer!=null){
				userInfos=buffer;
			}
		} catch (Exception e) {
			result.setErrorCode(ErrorCodeEnum.ERROR_CODE_5X025.getErrorCode());
			result.setResultFail();
			return result;
		}
		Map<String, Object> userStatus = new HashMap<String, Object>();
		for (UserInfo userInfo : userInfos) {
			try {
				// 验证非空项
				if(StringUtils.isEmpty(userInfo.getOrgCode())){
					userStatus.put(userInfo.getAccount(),ErrorCodeEnum.ERROR_CODE_5X024);
				}
			} catch (Exception e) {
				userStatus.put(userInfo.getAccount(),
						ErrorCodeEnum.ERROR_CODE_5X024);
			}
			ErrorCodeEnum codeEnum = validateUserInfo(userInfo);
			if (codeEnum == null) {
				//数据合规
				try{
					userInfoService.saveUserInfo(User.SCHOOL_USER, userInfo);
				}catch(Exception e){
					userStatus.put(userInfo.getAccount(),e.getMessage());
				}
			} else {
				userStatus.put(userInfo.getAccount(), codeEnum.getErrorCode());
			}
		}
		if(userStatus.keySet().size()==0){
			result.setMessage("success");
		}else{
			result.setDataMap(userStatus);
		}
		result.setResultSuccess();
		return result;
	}

	/**
	 * 优课平台账号同步到教研系统
	 * @param m
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public ResultModel updateUserInfo(Model m,String oldUserInfo,String newUserInfo,HttpServletRequest request) {
		ResultModel result = new ResultModel();
		UserInfo newUserInfoObj;
		UserInfo oldUserInfoObj;
		try {
			newUserInfoObj = JSON.parseObject(newUserInfo, UserInfo.class);
			oldUserInfoObj = JSON.parseObject(oldUserInfo, UserInfo.class);
		} catch (Exception e) {
			result.setErrorCode(ErrorCodeEnum.ERROR_CODE_5X025.getErrorCode());
			result.setResultFail();
			return result;
		}
		if(newUserInfoObj==null){
			result.setErrorCode(ErrorCodeEnum.ERROR_CODE_5X024.getErrorCode());
			result.setResultFail();
			return result;
		}
		// 验证非空项
		if(oldUserInfoObj!=null&&oldUserInfoObj.getAccount()==null){
			//账号字段必填
			result.setErrorCode(ErrorCodeEnum.ERROR_CODE_5X024.getErrorCode());
		}else{
			try{
				result=userInfoService.updateUserInfo(oldUserInfoObj,newUserInfoObj);
			}catch(Exception e){
				result.setResultFail();
				result.setErrorCode(e.getMessage());
			}
		}
		return result;
	}

	@RequestMapping(value = "/syncUserInfoToUclass", method = {	RequestMethod.POST, RequestMethod.GET })
	public JuiResult syncUserInfoToUclass(Model m, Integer orgId,Integer []userIds) {
		JuiResult result=new JuiResult();
		try {
			if (orgId == null) {
				throw new Exception("未指定学校或组织ID");
			}
			Map<String, Object> status;
			if(userIds==null){
				result.setMessage("您未勾选任何账户？");
				result.setStatusCode(500);
			}else{
				status= userInfoService.syncUserInfoToUclass(userIds, orgId);
				if(status.containsKey("链接优课异常")){
					result.setMessage("链接优课异常");
					result.setStatusCode(500);
				}else{
					m.addAttribute("status", status);
					result.setMessage(status.keySet().toString());
					result.setStatusCode(200);
				}
			}
		} catch (Exception e) {
			result.setStatusCode(500);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	@RequestMapping(value = "/choiceUserInfo", method = {RequestMethod.POST, RequestMethod.GET })
	public String choiceUserInfo(Model m, Integer orgId) {
		try {
			Map<String,Object> result =  userInfoService.findUsers(orgId);
			m.addAttribute("code", "1");
			m.addAttribute("result", result);
			m.addAttribute("orgId", orgId);
		} catch (Exception e) {
			m.addAttribute("code", "0");
			logger.error("--查询需要同步的账号信息出错--",e);
		}
		return viewName("choiceUserInfo");
	}
	public ErrorCodeEnum validateUserInfo(UserInfo userInfo) {
		String orgCode=userInfo.getOrgCode();
		String account = userInfo.getAccount();
		String callphone = userInfo.getCallphone();
		String mail = userInfo.getMail();
		int count=0;
		if(StringUtils.isNotEmpty(orgCode)){
			Organization model=new Organization();
			model.setCode(orgCode);
			List<Organization> orgBuffer=orgDao.list(model, 5);
			if(orgBuffer!=null){
				count=orgBuffer.size();
			}
		}
		int count1 = 0 ;
		if(StringUtils.isNotEmpty(account)){
			Login model1 = new Login();
			model1.setLoginname(account);
			count1=userInfoService.countLoginInfo(model1);
		}
		int count2 =0;
		if(StringUtils.isNotEmpty(callphone)){
			Login model2 = new Login();
			model2.setCellphone(callphone);
			count2= userInfoService.countLoginInfo(model2);
		}
		int count3 =0;
		if(StringUtils.isNotBlank(mail)){
			Login model3 = new Login();
			model3.setMail(mail);
			count3= userInfoService.countLoginInfo(model3);
		}
		if(count==0){
			return ErrorCodeEnum.ERROR_CODE_5X026;
		}
		if (count1 > 0) {
			return ErrorCodeEnum.ERROR_CODE_5X001;
		}
		if (count2 > 0) {
			return ErrorCodeEnum.ERROR_CODE_5X003;
		}
		if (count3 > 0) {
			return ErrorCodeEnum.ERROR_CODE_5X005;
		}
		return null;
	}
}
