package com.tmser.tr.webservice.user.data;

import java.io.Serializable;

public class UserInfo implements Serializable{
    /**
	 * <pre>
	 * 用户帐号信息传输中的数据模型
	 * </pre>
	 */
	private static final long serialVersionUID = 1L;
	public static final int AppId_jiaoyan=4;
	public static final int AppId_youke=1;
	
	private String account;  //": "SS186138697753",
	private Integer appKey;  //": "1",
	private String callphone;  //": "18613869775",
	private String createHost;  //": "127.0.0.1",
	private String mail;  //": "mail@163.com",
	private String nickname;  //": " allow null ",
	private String password;  //": " passwordMD5 ",
	private String username;  //": " allow null ",
	private String orgCode;  //":"not null",
	private String userType;  //":"教师",
	private String accountType;  //":"正式"
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getAppKey() {
		return appKey;
	}
	public void setAppKey(Integer appKey) {
		this.appKey = appKey;
	}
	public String getCallphone() {
		return callphone;
	}
	public void setCallphone(String callphone) {
		this.callphone = callphone;
	}
	public String getCreateHost() {
		return createHost;
	}
	public void setCreateHost(String createHost) {
		this.createHost = createHost;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
}
