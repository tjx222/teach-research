package com.tmser.tr.webservice.user.service;

import java.util.Map;

import com.tmser.tr.uc.bo.Login;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.webservice.user.data.ResultModel;
import com.tmser.tr.webservice.user.data.UserInfo;

public interface UserInfoService {
	public User saveUserInfo(Integer userType,UserInfo userInfo);
	public int countLoginInfo(Login model);
	public Map<String, Object> syncUserInfoToUclass(Integer []userIds,Integer orgId) throws Exception;
	public void updateUserInfoToUclass(UserInfo oldUserInfo,UserInfo newUserInfo);
	public ResultModel updateUserInfo(UserInfo oldUserInfo,UserInfo newUserInfo) throws Exception;
	public Map<String, Object> findUsers(Integer orgId);
}
