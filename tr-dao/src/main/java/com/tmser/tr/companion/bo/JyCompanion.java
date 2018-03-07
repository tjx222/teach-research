/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.QueryObject;

/**
 * 同伴互助 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyCompanion.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = JyCompanion.TABLE_NAME)
public class JyCompanion extends QueryObject {
	public static final String TABLE_NAME="jy_companion";

	@Id
	@Column(name="id")
	private Integer id;

	/**
	 *用户id
	 **/
	@Column(name="user_id")
	private Integer userId;

	/**
	 *用户名称
	 **/
	@Column(name="user_name",length=32)
	private String userName;

	/**
	 *用户昵称
	 **/
	@Column(name="user_nickname",length=32)
	private String userNickname;

	/**
	 *同伴用户id
	 **/
	@Column(name="user_id_companion")
	private Integer userIdCompanion;

	/**
	 *同伴名称
	 **/
	@Column(name="user_name_companion",length=32)
	private String userNameCompanion;

	/**
	 *同伴昵称
	 **/
	@Column(name="user_nickname_companion",length=32)
	private String userNicknameCompanion;

	/**
	 *最近一次联系时间
	 **/
	@Column(name="last_communicate_time")
	private Date lastCommunicateTime;

	/**
	 *是否时统一学校，1、是；0、否
	 **/
	@Column(name="is_same_org")
	private Integer isSameOrg;

	/**
	 *是否是朋友，1、是；0、否
	 **/
	@Column(name="is_friend")
	private Integer isFriend;

	/**
	 *最近一条的消息记录
	 **/
	private String lastMsg;


	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return this.id;
	}

	public void setUserId(Integer userId){
		this.userId = userId;
	}

	public Integer getUserId(){
		return this.userId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setUserNickname(String userNickname){
		this.userNickname = userNickname;
	}

	public String getUserNickname(){
		return this.userNickname;
	}

	public void setUserIdCompanion(Integer userIdCompanion){
		this.userIdCompanion = userIdCompanion;
	}

	public Integer getUserIdCompanion(){
		return this.userIdCompanion;
	}

	public void setUserNameCompanion(String userNameCompanion){
		this.userNameCompanion = userNameCompanion;
	}

	public String getUserNameCompanion(){
		return this.userNameCompanion;
	}

	public void setUserNicknameCompanion(String userNicknameCompanion){
		this.userNicknameCompanion = userNicknameCompanion;
	}

	public String getUserNicknameCompanion(){
		return this.userNicknameCompanion;
	}

	public void setLastCommunicateTime(Date lastCommunicateTime){
		this.lastCommunicateTime = lastCommunicateTime;
	}

	public Date getLastCommunicateTime(){
		return this.lastCommunicateTime;
	}

	public void setIsSameOrg(Integer isSameOrg){
		this.isSameOrg = isSameOrg;
	}

	public Integer getIsSameOrg(){
		return this.isSameOrg;
	}

	public void setIsFriend(Integer isFriend){
		this.isFriend = isFriend;
	}

	public Integer getIsFriend(){
		return this.isFriend;
	}



	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof JyCompanion))
			return false;
		JyCompanion castOther = (JyCompanion) other;
		return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	/**
	 * Getter method for property <tt>lastMsg</tt>.
	 * @return property value of lastMsg
	 */
	public String getLastMsg() {
		return lastMsg;
	}

	/**
	 * Setter method for property <tt>lastMsg</tt>.
	 * @param lastMsg value to be assigned to property lastMsg
	 */
	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
	}

}


