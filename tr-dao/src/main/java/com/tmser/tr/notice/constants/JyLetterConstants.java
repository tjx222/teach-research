package com.tmser.tr.notice.constants;

/**
 * 
 * <pre>
 * 站内信常量
 * </pre>
 *
 * @author wanzheng
 * @version $Id: JyLetterConstants.java, v 1.0 May 13, 2015 9:30:08 AM wanzheng Exp $
 */
public class JyLetterConstants {

	/**
	 * 站内信接受，未阅读状态
	 */
	public static final Integer RECIEVER_STATE_UNREAD=0;
	
	/**
	 * 站内信接受，已阅读状态
	 */
	public static final Integer RECIEVER_STATE_READED=2;
	
	/**
	 * 发送站内信，未删除
	 */
	public static final Integer SEND_STATE_UNDELETE=0;
	
	/**
	 * 站内信接受，已删除
	 */
	public static final Integer SEND_STATE_DELETE=1;
}
