package com.tmser.tr.webservice.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.activity.controller.ActivityController;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.dao.OrganizationDao;
import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.dao.LoginDao;
import com.tmser.tr.uc.dao.UserDao;
import com.tmser.tr.webservice.user.data.ResultModel;
import com.tmser.tr.webservice.user.data.UserInfo;
import com.tmser.tr.webservice.user.service.UserInfoService;
import com.tmser.tr.webservice.user.statics.ErrorCodeEnum;
import com.tmser.tr.webservice.user.utils.ConnectUtils;
import com.tmser.tr.webservice.user.utils.PropertiesUtils;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private LoginDao loginDao;
	@Autowired
	OrganizationDao orgDao;
	
	private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);
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
				List<Organization> orgBuffer=orgDao.list(model, 5);
				user.setOrgId(orgBuffer.get(0).getId());
				user.setOrgName(orgBuffer.get(0).getName());
			}else{
				
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
			logger.info("成功:新增第三方账号[userid={}]", user.getId());
			return user;	
		}catch(Exception e){
			e.printStackTrace();
			//存储用户信息异常处理
			if(login.getId()!=null){
				loginDao.delete(login.getId());
			}
			//记录日志
			logger.error("异常:新增第三方账号失败[errormsg={}]", e.getMessage());
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
				if(localUserInfo!=null&&!localUserInfo.getAppId().equals(oldUserInfo.getAppKey())){
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
					logger.info("成功:更新第三方账号[userid={}]", localUserInfo.getId());
				}
			}
		}else{
			resultModel.setErrorCode(ErrorCodeEnum.ERROR_CODE_5X024.getErrorCode());
			resultModel.setMessage(ErrorCodeEnum.ERROR_CODE_5X024.getErrorMsg());
			resultModel.setResultFail();
			//记录日志
			logger.error("异常:更新第三方账号失败[={}]，必要信息缺失！", oldUserInfo.getAccount());
		}
		return resultModel;
	}
	
	@Override
	public int countLoginInfo(Login model) {
		// TODO 登陆信息统计个数
		return loginDao.count(model);
	}

	@Override
	public Map<String, Object> syncUserInfoToUclass(List<Map<String, Object>> userinfos,Integer orgId) throws Exception{
		// TODO 将本地数据同步到优课
		if(userinfos==null&&orgId!=null){
			//获取机构ID
			Organization org=orgDao.get(orgId);
			String orgCode= org.getCode();
			//页面没有选择，默认查询当前学校所有系统原有账号
			User model=new User();
			model.setAppId(0);
			model.setOrgId(orgId);
			List<User>users=userDao.listAll(model);
			Map<String, Object>status=new HashMap<String, Object>();
			List<UserInfo> userInfos=new ArrayList<>();
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
			String url=PropertiesUtils.getValueByKey(PropertiesUtils.proper, "server_insert_url");
			if(userInfos.size()>0){
				String userListStr=JSON.toJSONString(userInfos);
				Map<String, String> paramMap=new HashMap<>();
				paramMap.put("userList", userListStr);
				int connectTimeout=Integer.parseInt(PropertiesUtils.getValueByKey(PropertiesUtils.proper, "connectTimeout"));
				int socketTimeout=Integer.parseInt(PropertiesUtils.getValueByKey(PropertiesUtils.proper, "socketTimeout"));
				ResultModel result=ConnectUtils.post(url,paramMap,connectTimeout,socketTimeout);
				if(result!=null){
					if("200".equals(result.getRestStatus())){
						//更新用户账号信息
						updateUserInfo(userInfos);
					}else if(result.getDataMap()!=null){
						status=result.getDataMap();
						for(String loginName:status.keySet()){
							//记录日志
							String msg=ErrorCodeEnum.valueOf("ERROR_CODE_"+(String)status.get(loginName)).getErrorMsg();
							logger.error("异常:账号[loginName={}]同步到优课失败，原因是：{}",loginName, msg);
							status.put(loginName, msg);
						}
					}
				}
			}
			return status;
		}else{
			return null;
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
					//记录日志
					logger.error("账号[loginName={}]成功同步到优课", login.getLoginname());
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
						logger.info("成功:账号[loginName={}]更新到优课成功",oldUserInfo.getAccount());
					}else if(result.getMessage()!=null){
						//记录日志
						logger.info("异常:账号[loginName={}]更新到优课失败，原因是：{}",oldUserInfo.getAccount(), result.getMessage());
					}
				}
			}catch(Exception e){
				//记录日志
				logger.error("异常:账号[loginName={}]更新到优课失败，原因是：{}",oldUserInfo.getAccount(),e.getMessage());
			}
		}
	}

	
}
