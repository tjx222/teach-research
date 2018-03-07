package com.tmser.tr.annunciate.vo;

import com.tmser.tr.annunciate.bo.JyAnnunciate;

public class JyAnnunciateVo extends JyAnnunciate{
	
	/**
	 * 是学校领导
	 */
	public static final Integer IS_SCHOOL_LEADER_TRUE=1;
	
	/**
	 * 不是学校领导
	 */
	public static final Integer IS_SCHOOL_LEADER_FALSE=0;
	
	/**
	 * 查看过
	 */
	public static final Integer IS_VIEWED_TURE=1;
	
	/**
	 * 未查看过
	 */
	public static final Integer IS_VIEWED_FLASE=0;
	
	/**
	 * 包含自身数据
	 */
	public static final Integer IS_CANTIAN_SELF_TRUE=1;
	
	/**
	 * 不包含自身数据
	 */
	public static final Integer IS_CANTIAN_SELF_FLASE=0;
	
	
	/**
	 * <pre>
	 *
	 * </pre>
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 是否时领导
	 */
	private Integer isSchoolLeader;
	
	/**
	 * 是否被查看过
	 */
	private Integer isViewed;
	
	/**
	 * 用户名称
	 */
	private String userName;
	
	/**
	 * 是否包含自身数据
	 */
	private Integer isCantainSelf;

	public Integer getIsSchoolLeader() {
		return isSchoolLeader;
	}

	public void setIsSchoolLeader(Integer isSchoolLeader) {
		this.isSchoolLeader = isSchoolLeader;
	}

	public Integer getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Integer isViewed) {
		this.isViewed = isViewed;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getIsCantainSelf() {
		return isCantainSelf;
	}

	public void setIsCantainSelf(Integer isCantainSelf) {
		this.isCantainSelf = isCantainSelf;
	}
}
