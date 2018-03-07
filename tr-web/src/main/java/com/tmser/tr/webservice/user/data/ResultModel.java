package com.tmser.tr.webservice.user.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
 * @className:RestModelMap.java
 * @classDescription:
 * @author:zhangfangzhi
 * @createTime:2015年11月3日
 */
public class ResultModel{
	public static final String REST_STATUS_SUCCESS = "200";
	public static final String REST_STATUS_FAIL_EXCEPTION = "500";//
	public static final String REST_STATUS_FAIL_NOT_FOUNT = "404";//
	public static final String REST_STATUS_FAIL_NO_AUTHORITY = "403";
	
	private String restStatus;//响应状态
	private String message;//响应信息
	private List<Object> dateList;//响应数据List
	private Map<String,Object> dataMap;//响应数据Map
	private String errorCode;//错误代码
	
	/**
	 * 状态成功
	 */
	public void setResultSuccess(){
		this.restStatus = ResultModel.REST_STATUS_SUCCESS;
	}
	
	/**
	 * 设置响应状态码 404 500 403
	 * @param restStatus
	 */
	public void setResultFail(String restStatus){
		List<String> restStatusList = new ArrayList<String>();
		/*Field[] fieldArr = RestModelMap.class.getDeclaredFields();
		for (int i = 0; i < fieldArr.length; i++) {
			Field field = fieldArr[i];
			String fieldName = field.getName();
			if(fieldName.indexOf("REST_STATUS_FAIL_") > -1){
				try {
					restStatusList.add((String) field.get(RestModelMap.class));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}*/
		restStatusList.add(REST_STATUS_FAIL_EXCEPTION);
		restStatusList.add(REST_STATUS_FAIL_NOT_FOUNT);
		restStatusList.add(REST_STATUS_FAIL_NO_AUTHORITY);
		if(!restStatusList.contains(restStatus)){//当restStatus不存在时，默认为500
			restStatus = ResultModel.REST_STATUS_FAIL_EXCEPTION;
		}
		this.restStatus = restStatus;
	}
	
	/**
	 * 设置响应状态码 404 500 403
	 * @param restStatus
	 */
	public void setResultFail(){
		restStatus = ResultModel.REST_STATUS_FAIL_EXCEPTION;
	}
	

	public String getRestStatus() {
		return restStatus;
	}

	public void setRestStatus(String restStatus) {
		this.restStatus = restStatus;
	}

	public List<Object> getDateList() {
		return dateList;
	}

	public void setDateList(List<Object> dateList) {
		this.dateList = dateList;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	
}

