package com.tmser.tr.webservice.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.common.service.MailService;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.vo.Email;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.dao.LoginDao;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.utils.DateUtils;
import com.tmser.tr.webservice.user.data.ResultModel;
import com.tmser.tr.webservice.user.data.UserInfo;
import com.tmser.tr.webservice.user.service.UserInfoService;
import com.tmser.tr.webservice.user.statics.ErrorCodeEnum;
import com.tmser.tr.webservice.user.utils.ConnectUtils;
import com.tmser.tr.webservice.user.utils.PropertiesUtils;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
	private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	OrganizationDao orgDao;
	@Autowired
	private MailService mailService;
	
	@Override
	public User saveUserInfo(Integer userType,UserInfo userInfo) {
		Login login=new Login();
		try{
			// 新增登陆信息
			login.setPassword(userInfo.getPassword());
			//login.setSalt(salt);
			if (userType.intValue() == User.SYS_USER.intValue()) {
				login.setIsAdmin(true);
			} else {
				login.setIsAdmin(false);
			}
			//第三方应用自动接入的账号默认冻结
			login.setEnable(0);
			login.setDeleted(false);
			login.setCellphone(userInfo.getCallphone());
			login.setMail(userInfo.getMail());
			//第三方登陆code
			login.setLogincode(userInfo.getAccount());
			login.setLoginname(userInfo.getAccount());
			login = loginDao.insert(login);
			// 新增用户信息
			User user=new User();
			user.setId(login.getId());
			if (StringUtils.isEmpty(userInfo.getNickname())) {
				user.setNickname(userInfo.getNickname());
			}
			user.setName(userInfo.getUsername());
			//user.set
			//u课组织结构ID
			String orgCode=userInfo.getOrgCode();
			if(StringUtils.isNotEmpty(orgCode)){
				Organization model=new Organization();
				model.setCode(orgCode);
				List<Organization> orgBuffer=orgDao.list(model, 1);
				user.setOrgId(orgBuffer.get(0).getId());
				user.setOrgName(orgBuffer.get(0).getName());
			}
			user.setUserType(userType);
			user.setId(login.getId());
			user.setCellphoneValid(true);
			user.setCellphoneView(false);
			//用户来源
			user.setAppId(userInfo.getAppKey());
			user.setMail(userInfo.getMail());
			user.setCellphone(userInfo.getCallphone());
			user.setMailValid(true);
			user.setMailView(false);
			user.setIsFamousTeacher(0);
			user.setCrtDttm(new Date());
			user.setLastupDttm(new Date());
			user = userDao.insert(user);
			//记录日志
			LoggerUtils.insertLogger("成功:新增第三方账号[userid={}]", user.getId());
			return user;	
		}catch(Exception e){
			logger.error("同步新增账号错误！",e);
			//存储用户信息异常处理
			if(login.getId()!=null){
				loginDao.delete(login.getId());
			}
			//记录日志
			LoggerUtils.insertLogger("异常:新增第三方账号失败[errormsg={}]", e.getMessage());
			throw e;
		}
	}
	@Override
	public ResultModel updateUserInfo(UserInfo oldUserInfo,UserInfo newUserInfo){
		ResultModel resultModel=new ResultModel();
		//首先校验平台是否存在旧账号数据
		if(oldUserInfo.getAccount()!=null&&oldUserInfo.getAppKey()!=null){
			Login model=new Login();
			model.setLoginname(oldUserInfo.getAccount());
			if(StringUtils.isNotEmpty(oldUserInfo.getCallphone())){
				model.setCellphone(oldUserInfo.getCallphone());
			}
			if(StringUtils.isNotEmpty(oldUserInfo.getMail())){
				model.setMail(oldUserInfo.getMail());
			} 
			if(StringUtils.isNotEmpty(oldUserInfo.getPassword())){
				model.setPassword(oldUserInfo.getPassword());
			}
			Login userLogin=loginDao.getOne(model);
			
			if(userLogin==null){
				resultModel.setErrorCode(ErrorCodeEnum.ERROR_CODE_5X011.getErrorCode());
				resultModel.setMessage(ErrorCodeEnum.ERROR_CODE_5X011.getErrorMsg());
				resultModel.setResultFail();
				return resultModel;
			}else{
				User localUserInfo=userDao.get(userLogin.getId());
				if(localUserInfo.getAppId().equals(oldUserInfo.getAppKey())){
					resultModel.setErrorCode(ErrorCodeEnum.ERROR_CODE_5X015.getErrorCode());
					resultModel.setMessage(ErrorCodeEnum.ERROR_CODE_5X015.getErrorMsg());
					resultModel.setResultFail();
					return resultModel;
				}else{
					model=new Login();
					if(StringUtils.isNotEmpty(newUserInfo.getCallphone())){
						model.setCellphone(newUserInfo.getCallphone());
						Login  buffer=loginDao.getOne(model);
						if (buffer!=null) {
							if(!buffer.getLoginname().equals(oldUserInfo.getAccount())){
								resultModel.setErrorCode(ErrorCodeEnum.MSG_CODE_2X001.getErrorCode());
								resultModel.setMessage(ErrorCodeEnum.MSG_CODE_2X001.getErrorMsg());
								resultModel.setResultFail();
								return resultModel;
							}
						}
					}
					if(StringUtils.isNotEmpty(newUserInfo.getMail())){
						model.setMail(newUserInfo.getMail());
						Login  buffer=loginDao.getOne(model);
						if (buffer!=null) {
							if(!buffer.getLoginname().equals(oldUserInfo.getAccount())){
								resultModel.setErrorCode(ErrorCodeEnum.MSG_CODE_2X002.getErrorCode());
								resultModel.setMessage(ErrorCodeEnum.MSG_CODE_2X002.getErrorMsg());
								resultModel.setResultFail();
								return resultModel;
							}
							
						}
					} 
					//更新用户登陆信息
					userLogin.setCellphone(newUserInfo.getCallphone());
					userLogin.setMail(newUserInfo.getMail());
					userLogin.setLoginname(newUserInfo.getAccount());
					userLogin.setPassword(newUserInfo.getPassword());
					loginDao.update(userLogin);
					//更新用户信息
					localUserInfo.setCellphone(newUserInfo.getCallphone());
					localUserInfo.setMail(newUserInfo.getMail());
					localUserInfo.setName(newUserInfo.getUsername());
					localUserInfo.setNickname(newUserInfo.getNickname());
					localUserInfo.setLastupDttm(new Date());
					localUserInfo.setLastupId(-1);
					userDao.update(localUserInfo);
					resultModel.setResultSuccess();
					//记录日志
					LoggerUtils.updateLogger("成功:更新第三方账号[userid={}]", localUserInfo.getId());
				}
			}
		}else{
			resultModel.setErrorCode(ErrorCodeEnum.ERROR_CODE_5X024.getErrorCode());
			resultModel.setMessage(ErrorCodeEnum.ERROR_CODE_5X024.getErrorMsg());
			resultModel.setResultFail();
			//记录日志
			LoggerUtils.updateLogger("|异常:更新第三方账号失败[={}]，必要信息缺失！", oldUserInfo.getAccount());
		}
		return resultModel;
	}
	
	@Override
	public int countLoginInfo(Login model) {
		// TODO 登陆信息统计个数
		return loginDao.count(model);
	}

	@Override
	public Map<String, Object> syncUserInfoToUclass(Integer []userIds,Integer orgId) throws Exception{
		List<UserInfo> userInfos = new ArrayList<>();
		Map<String, Object>status=new HashMap<String, Object>();
		// TODO 将本地数据同步到优课
		if(userIds!=null&&userIds.length>0){
			userInfos=getUserInfosByIds(userIds,orgId);
			String userListStr=JSON.toJSONString(userInfos);
			Map<String, String> paramMap=new HashMap<>();
			paramMap.put("userList", userListStr);
			int connectTimeout=Integer.parseInt(PropertiesUtils.getValueByKey(PropertiesUtils.proper, "connectTimeout"));
			int socketTimeout=Integer.parseInt(PropertiesUtils.getValueByKey(PropertiesUtils.proper, "socketTimeout"));
			try{
				String url=PropertiesUtils.getValueByKey(PropertiesUtils.proper, "server_insert_url");
				ResultModel result=ConnectUtils.post(url,paramMap,connectTimeout,socketTimeout);
				if(result!=null){
					if("200".equals(result.getRestStatus())&&CollectionUtils.isEmpty(result.getDataMap())){
						//更新用户账号信息
						updateUserInfo(userInfos);
					}else if(!CollectionUtils.isEmpty(result.getDataMap())){
						status=result.getDataMap();
						String userListStrTable="<table><tr><td>账号</td><td>失败原因</td></tr>";
						for(String loginName:status.keySet()){
							//记录日志
							String msg=ErrorCodeEnum.valueOf("ERROR_CODE_"+(String)status.get(loginName)).getErrorMsg();
							LoggerUtils.insertLogger("异常:账号[loginName={}]同步到优课失败，原因是：{}",loginName, msg);
							status.put(loginName, msg);
							//拼凑邮件正文
							userListStrTable+="<tr><td>"+loginName+"</td><td>"+msg+"</td></tr>";
							
						}
						userListStrTable+="</table>";
						sendMail(userListStrTable);
						//剔除userInfos里发送失败的账号,并更新账号状态
						updateUserInfo(userInfos,status.keySet());
						
					}else if(result.getErrorCode()!=null) {
						String msg=ErrorCodeEnum.valueOf("ERROR_CODE_"+result.getErrorCode()).getErrorMsg();
						status.put("同步账号异常", msg);
					}
				}else{
					status.put("链接优课异常", "网络连接超时或无法连通");
					return status;
				}
			}catch(Exception e){
				status.put("链接优课异常", e.getMessage());
				return status;
			}
			return status;
		}else{
			return null;
		}
	}
	public List<UserInfo>  getUserInfosByOrgId(Integer orgId) {
		List<UserInfo> userInfos = new ArrayList<>();
		if(orgId!=null){
			//获取机构ID
			Organization org=orgDao.get(orgId);
			String orgCode= org.getCode();
			//页面没有选择，默认查询当前学校所有系统原有账号
			User model=new User();
			model.setAppId(0);
			model.setOrgId(orgId);
			List<User>users=userDao.listAll(model);
			
			userInfos=new ArrayList<>();
			for(User user:users){
				Login login=loginDao.get(user.getId());
				if(login!=null){
					UserInfo userInfo=new UserInfo();
					userInfo.setNickname(user.getNickname());
					userInfo.setUsername(user.getName());
					userInfo.setCallphone(login.getCellphone());
					userInfo.setMail(login.getMail());
					userInfo.setAccount(login.getLoginname());
					userInfo.setAppKey(UserInfo.AppId_jiaoyan);
					userInfo.setOrgCode(orgCode);	
					userInfo.setPassword(login.getPassword());
					userInfos.add(userInfo);
				}
			}
		}
		return userInfos;
	}
	public List<UserInfo>  getUserInfosByIds(Integer []userIds,Integer orgId) {
		List<UserInfo> userInfos = new ArrayList<>();
		if(userIds!=null){
			//获取机构ID
			Organization org=orgDao.get(orgId);
			String orgCode= org.getCode();
			userInfos=new ArrayList<>();
			for(Integer userId:userIds){
				User user=userDao.get(userId);
				Login login=loginDao.get(userId);
				if(login!=null){
					UserInfo userInfo=new UserInfo();
					userInfo.setNickname(user.getNickname());
					userInfo.setUsername(user.getName());
					userInfo.setCallphone(login.getCellphone());
					userInfo.setMail(login.getMail());
					userInfo.setAccount(login.getLoginname());
					userInfo.setAppKey(UserInfo.AppId_jiaoyan);
					userInfo.setOrgCode(orgCode);	
					userInfo.setPassword(login.getPassword());
					userInfos.add(userInfo);
				}
			}
		}
		return userInfos;
	}
	/**
	 * 发送邮件
	 */
	private void sendMail(String userListStr) {
		// TODO 发送邮件通知失败账号列表
		Email email = new Email();
		email.setToAddress("jiaoyan@mainbo.com");
		email.setFromAddress("jiaoyan@mainbo.com");
		email.setSubject("教研账号同步到优课系统失败列表");
		StringBuilder content = new StringBuilder("<table cellpadding=\"0\" cellspacing=\"0\" width=\"96%\" style=\"background:#ffffff;border:1px solid rgb(204,204,204);margin:2%;\">")
		.append("<tbody><tr><td width=\"30px;\">&nbsp;</td><td align=\"\">")
		.append("<div style=\"line-height:40px;height:40px;\">&nbsp;</div><p style=\"margin:0px;padding:0px;\">")
		.append("<strong style=\"font-size:14px;line-height:30px;color:#333333;font-family:arial,sans-serif;\">亲爱的：")
		.append("</strong></p><div style=\"line-height:20px;height:20px;\">&nbsp;</div><p style=\"margin:0px;padding:0px;line-height:30px;font-size:14px;color:#333333;font-family:'宋体',arial,sans-serif;\">")
		.append("您好！同步失败的账号为：</p><p style=\"margin:0px;padding:0px;line-height:30px;font-size:14px;color:#333333;font-family:'宋体',arial,sans-serif;\">")
		.append("<b style=\"font-size:18px;color:#ff9900\">")
		.append(userListStr).append("</b><span style=\"margin:0px;padding:0px;margin-left:10px;line-height:30px;font-size:14px;color:#979797;font-family:'宋体',arial,sans-serif;\">")
		.append("(为了保障您帐号的安全性，请在1小时内完成验证。)</span></p><div style=\"line-height:80px;height:80px;\">&nbsp;</div><p style=\"margin:0px;padding:0px;line-height:30px;font-size:14px;color:#333333;font-family:'宋体',arial,sans-serif;\">")
		.append("教研平台服务中心</p><p style=\"margin:0px;padding:0px;line-height:30px;font-size:14px;color:#333333;font-family:'宋体',arial,sans-serif;\">")
		.append(DateUtils.getDate())
		.append("</p><div style=\"line-height:20px;height:20px;\">&nbsp;</div></td><td width=\"30px;\">&nbsp;</td></tr></tbody></table>");
		
		email.setContent(content.toString());
		try {
			mailService.sendMailByAsynchronousMode(email);
		} catch (Exception e) {
			LoggerUtils.insertLogger("邮件发送失败："+e.getMessage());
			logger.warn("邮件发送失败",e);
		}
		
		
	}
	private void updateUserInfo(List<UserInfo> userInfos) {
		// TODO 更新用户账号信息
		for(UserInfo userInfo:userInfos){
			if(userInfo.getAccount()!=null){
				
				Login model=new Login();
				model.setLoginname(userInfo.getAccount());
				Login login=loginDao.getOne(model);
				if(login!=null){
					User user =userDao.get(login.getId());
					user.setAppId(UserInfo.AppId_jiaoyan);
					userDao.update(user);
					//更新logincode
					login.setLogincode(login.getLoginname());
					loginDao.update(login);
					//记录日志
					LoggerUtils.insertLogger("账号[loginName={}]成功同步到优课", login.getLoginname());
				}
			}
		}
	}
	private void updateUserInfo(List<UserInfo> userInfos,Set<String> failLoginNameSet) {
		// TODO 剔除failLoginNameSet中的用户并 更新其余用户账号信息
		for(UserInfo userInfo:userInfos){
			if(userInfo.getAccount()!=null&&failLoginNameSet.contains(userInfo.getAccount())==false){
				Login model=new Login();
				model.setLoginname(userInfo.getAccount());
				Login login=loginDao.getOne(model);
				if(login!=null){
					User user =userDao.get(login.getId());
					user.setAppId(UserInfo.AppId_jiaoyan);
					userDao.update(user);
					//更新logincode
					login.setLogincode(login.getLoginname());
					loginDao.update(login);
					//记录日志
					LoggerUtils.insertLogger("账号[loginName={}]成功同步到优课", login.getLoginname());
				}
			}
		}
	}
	/**
	 * 更新学年验证查询
	 * 
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.back.yhgl.service.BackUserManageService#findUpUsers(java.lang.Integer)
	 */
	@Override
	public Map<String, Object> findUsers(Integer orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (orgId!=null) {
			User model=new User();
			model.setAppId(0);
			model.setOrgId(orgId);
			List<User>users=userDao.listAll(model);
			map.put("msg", "allow");
			map.put("content", users);
		} else {
			map.put("msg", "forbid");
			map.put("content", "没有指定学校或组织ID");
		}
		return map;
	}
	@Override
	public void updateUserInfoToUclass(UserInfo oldUserInfo,UserInfo newUserInfo) {
		String url=PropertiesUtils.getValueByKey(PropertiesUtils.proper, "server_update_url");
		if(oldUserInfo!=null){
			String oldUserInfoStr=JSON.toJSONString(oldUserInfo);
			String newUserInfoStr=JSON.toJSONString(newUserInfo);
			Map<String, String> paramMap=new HashMap<>();
			paramMap.put("oldUserInfo", oldUserInfoStr);
			paramMap.put("newUserInfo", newUserInfoStr);
			int connectTimeout=Integer.parseInt(PropertiesUtils.getValueByKey(PropertiesUtils.proper, "connectTimeout"));
			int socketTimeout=Integer.parseInt(PropertiesUtils.getValueByKey(PropertiesUtils.proper, "socketTimeout"));
			try{
				ResultModel result=ConnectUtils.post(url,paramMap,connectTimeout,socketTimeout);
				if(result!=null){
					if("200".equals(result.getRestStatus())){
						LoggerUtils.updateLogger("成功:账号[loginName={}]更新到优课成功",oldUserInfo.getAccount());
					}else if(result.getMessage()!=null){
						//记录日志
						LoggerUtils.updateLogger("异常:账号[loginName={}]更新到优课失败，原因是：{}",oldUserInfo.getAccount(), result.getMessage());
					}
				}
			}catch(Exception e){
				//记录日志
				LoggerUtils.updateLogger("异常:账号[loginName={}]更新到优课失败，原因是：{}",oldUserInfo.getAccount(),e.getMessage());
				logger.error("账号同步异常",e);
			}
		}
	}

	
}
