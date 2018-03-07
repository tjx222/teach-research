/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.uc.bo;

import java.util.Date;


import javax.persistence.*;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.tmser.tr.common.bo.BaseObject;

 /**
 * 保存邮箱验证码 Entity
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: VerificationCode.java, v 1.0 2015-08-05 Generate Tools Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = VerificationCode.TABLE_NAME)
public class VerificationCode extends BaseObject {
	public static final String TABLE_NAME="sys_mail_verify_code";
	
		@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="user_id")
	private Integer userId;

	@Column(name="verification_code",length=32)
	private String verificationCode;

	@Column(name="creat_time")
	private Date creatTime;


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

	public void setVerificationCode(String verificationCode){
		this.verificationCode = verificationCode;
	}

	public String getVerificationCode(){
		return this.verificationCode;
	}

	public void setCreatTime(Date creatTime){
		this.creatTime = creatTime;
	}

	public Date getCreatTime(){
		return this.creatTime;
	}


	
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof VerificationCode))
				return false;
			VerificationCode castOther = (VerificationCode) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}


