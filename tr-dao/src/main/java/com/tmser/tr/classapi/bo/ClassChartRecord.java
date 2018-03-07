package com.tmser.tr.classapi.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import com.tmser.tr.common.bo.QueryObject;

/**
 * 保存聊天记录bo
 * <pre>
 *
 * </pre>
 *
 * @author ljh
 * @version $Id: ChartRecord.java, v 1.0 2017年2月13日 下午2:07:14 ljh Exp $
 */
@SuppressWarnings("serial")
@Entity
@Table(name = ClassChartRecord.TABLE_NAME)
public class ClassChartRecord extends QueryObject {
public static final String TABLE_NAME="area_video_chartrecord";
	
	public ClassChartRecord(){
	}
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",length=32)
	private Integer id;
	/**
	 * 课堂id
	 */
	@Column(name="mtg_key",length=32)
	private String mtgKey;
	/**
	 * 消息内容
	 */
	@Column(name="context",length=2048)
	private String context;
	/**
	 * 发送时间
	 */
	@Column(name="record_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date recordTime;
	
	/**
	 * 发送人id
	 */
	@Column(name="from_user_id")
	private Integer fromUserId;
	/**
	 * 发送人name
	 */
	@Column(name="from_user_name")
	private String fromUserName;
	/**
	 * 接收者那id
	 */
	@Column(name="to_user_id")
	private Integer toUserId;
	/**
	 * 接收者那name
	 */
	@Column(name="to_user_name")
	private String toUserName;
	
	@Column(name="meeting_number")
	private String meetingNumber;
	/**
	 * 类型  0：普通  1：主持人
	 */
	@Column(name="is_normal")
	private Integer isNormal;
	
	@Column(name="owner_id")
	private Integer ownerId;
	
	public Integer getIsNormal() {
		return isNormal;
	}
	public void setIsNormal(Integer isNormal) {
		this.isNormal = isNormal;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMtgKey() {
		return mtgKey;
	}
	public void setMtgKey(String mtgKey) {
		this.mtgKey = mtgKey;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
	}
	public Integer getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(Integer fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public Integer getToUserId() {
		return toUserId;
	}
	public void setToUserId(Integer toUserId) {
		this.toUserId = toUserId;
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getMeetingNumber() {
		return meetingNumber;
	}
	public void setMeetingNumber(String meetingNumber) {
		this.meetingNumber = meetingNumber;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	@Override
	public boolean equals(final Object other) {
			if (!(other instanceof ClassChartRecord))
				return false;
			ClassChartRecord castOther = (ClassChartRecord) other;
			return new EqualsBuilder().append(id, castOther.id).isEquals();
	}

	@Override
	public int hashCode() {
			return new HashCodeBuilder().append(id).toHashCode();
	}
}
