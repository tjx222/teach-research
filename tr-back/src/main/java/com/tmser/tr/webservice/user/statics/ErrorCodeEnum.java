package com.tmser.tr.webservice.user.statics;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/** 
 * @className:ErrorCodeEnum.java
 * @classDescription:
 * @author:zhangfangzhi
 * @createTime:2015年11月16日
 */
public enum ErrorCodeEnum {
	ERROR_CODE_5X001("5X001","账号已在当前平台中被注册"),
	ERROR_CODE_5X002("5X002","账号已在其他平台中被注册"),
	ERROR_CODE_5X003("5X003","手机号已被当前平台绑定"),
	ERROR_CODE_5X004("5X004","手机号已被其他用户绑定"),
	ERROR_CODE_5X005("5X005","邮箱已被当前平台用户绑定"),
	ERROR_CODE_5X006("5X006","邮箱已被其他平台用户绑定"),
	ERROR_CODE_5X007("5X007","账号注册互通失败"),
	ERROR_CODE_5X008("5X008","手机注册互通失败"),
	ERROR_CODE_5X009("5X009","邮箱注册互通失败"),
	ERROR_CODE_5X010("5X010","用户登录校验通过，但账号的平台Key已存在，可进行同步密码"),
	ERROR_CODE_5X011("5X011","账号密码校验失败"),
	ERROR_CODE_5X012("5X012","用户验证失败,账号appAuthority已存在于中心用户中"),
	ERROR_CODE_5X013("5X013","账号登录互通失败"),
	ERROR_CODE_5X014("5X014","短信次数已超限制"),
	ERROR_CODE_5X015("5X015","修改用户信息校验失败：appKey无效"),
	ERROR_CODE_5X016("5X016","手机号格式不正确"),
	ERROR_CODE_5X017("5X017","邮箱格式不正确"),
	ERROR_CODE_5X018("5X018","强制解绑的手机不存在用户中心中"),
	ERROR_CODE_5X019("5X019","强制解绑的邮箱不存在用户中心中"),
	ERROR_CODE_5X020("5X020","解绑的手机号与账号不匹配"),
	ERROR_CODE_5X021("5X021","解绑的邮箱号与账号不匹配"),
	ERROR_CODE_5X022("5X022","请求IP格式不对"),
	ERROR_CODE_5X023("5X023","无法匹配到对应机构"),
	ERROR_CODE_5X024("5X024","账号信息缺失"),
	ERROR_CODE_5X025("5X025","JSON数据格式异常或为空"),
	ERROR_CODE_5X026("5X026","账号同步异常，可能无法访问第三方平台或第三方平台执行异常"),
	MSG_CODE_2X001("2X001","手机号已经绑定在账号中"),
	MSG_CODE_2X002("2X002","邮箱已经绑定在账号中"),
	MSG_CODE_2X003("2X003","手机号未绑定在中心账号"),
	MSG_CODE_2X004("2X004","邮箱未绑定在中心账号");
	
	public static final Map<String, String> lookup = new HashMap<String, String>();  
	  
    static {  
        for (ErrorCodeEnum s : EnumSet.allOf(ErrorCodeEnum.class)) {  
        	lookup.put(s.getErrorCode(), s.getErrorMsg());  
        }  
    }  
	
	private ErrorCodeEnum(){
		
	}
	
	private ErrorCodeEnum(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	private String errorCode;
	private String errorMsg;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}

