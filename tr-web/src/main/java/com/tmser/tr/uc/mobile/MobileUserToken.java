package com.tmser.tr.uc.mobile;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 
 * <pre>
 * 
 * </pre>
 *
 * @author tmser
 * @version $Id: MobileUserToken.java, v 1.0 2016年4月12日 下午5:13:26 3020mt Exp $
 */
public class MobileUserToken implements AuthenticationToken{
	
	private static final long serialVersionUID = -8134332435660272704L;
	/**
	 * 登录用户名
	 */
	private String username;
	
	/**
	 * 登录标示，使用password 加密用户名
	 */
	private String token;
	
	/**
	 * 移动端当前的用户空间id
	 */
	private Integer spaceid;
	
	/**
	 * 移动端当前用户学段id
	 */
	private Integer phaseid;

	@Override
	public Object getPrincipal() {
		return getUsername();
	}

	@Override
	public Object getCredentials() {
		return getToken();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getSpaceid() {
		return spaceid;
	}

	public void setSpaceid(Integer spaceid) {
		this.spaceid = spaceid;
	}

	public Integer getPhaseid() {
		return phaseid;
	}

	public void setPhaseid(Integer phaseid) {
		this.phaseid = phaseid;
	}
	
	

}
