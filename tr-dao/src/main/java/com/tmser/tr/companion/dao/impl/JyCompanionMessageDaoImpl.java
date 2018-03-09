/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.companion.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.tmser.tr.common.dao.AbstractDAO;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.companion.bo.JyCompanion;
import com.tmser.tr.companion.bo.JyCompanionMessage;
import com.tmser.tr.companion.dao.JyCompanionMessageDao;

/**
 * 同伴互助留言 Dao 实现类
 * 
 * <pre>
 *
 * </pre>
 *
 * @author Generate Tools
 * @version $Id: JyCompanionMessage.java, v 1.0 2015-05-20 Generate Tools Exp $
 */
@Repository
public class JyCompanionMessageDaoImpl extends AbstractDAO<JyCompanionMessage, Integer>
    implements JyCompanionMessageDao {

  @Override
  public List<JyCompanionMessage> findMessage(Integer userId, Integer userIdCompanion, Date startDate, Integer pageSize,
      Integer pageNum) {
    String sql = "select * from JyCompanionMessage where senderTime>=? and"
        + "((userIdSender=? and userIdReceiver=? ) or (userIdSender=? and userIdReceiver=?)) order by senderTime desc limit ?,?";
    Object[] args = { startDate, userId, userIdCompanion, userIdCompanion, userId, pageSize * (pageNum - 1), pageSize };
    return this.query(sql, args, this.mapper);
  }

  @Override
  public PageList<JyCompanionMessage> findMessageWithPage(JyCompanionMessage message) {
    String sql = "";
    if (message.getSenderTime() != null) {
      sql = "select * from JyCompanionMessage where senderTime>=:senderTime and "
          + "(userIdSender=:userIdSender and userIdReceiver=:userIdReceiver ) or "
          + "(userIdSender=:userIdReceiver and userIdReceiver=:userIdSender) order by senderTime desc";
    } else {
      sql = "select * from JyCompanionMessage where "
          + "(userIdSender=:userIdSender and userIdReceiver=:userIdReceiver ) or "
          + "(userIdSender=:userIdReceiver and userIdReceiver=:userIdSender) order by senderTime desc";
    }
    Map<String, Object> argMap = new HashMap<>();
    argMap.put("senderTime", message.getSenderTime());
    argMap.put("userIdSender", message.getUserIdSender());
    argMap.put("userIdReceiver", message.getUserIdReceiver());
    return this.queryPageByNamedSql(sql, argMap, this.mapper, message.getPage());
  }

  @Override
  public List<Date> findAllComunicateDates(JyCompanionMessage message) {
    String sql = "select senderTime senderTime from JyCompanionMessage where "
        + "(userIdSender=:userIdSender and userIdReceiver=:userIdReceiver ) "
        + "or (userIdSender=:userIdReceiver and userIdReceiver=:userIdSender)";
    Map<String, Object> argsMap = new HashMap<String, Object>();
    argsMap.put("userIdSender", message.getUserIdSender());
    argsMap.put("userIdReceiver", message.getUserIdReceiver());

    return this.queryByNamedSql(sql, argsMap, new RowMapper<Date>() {
      @Override
      public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getDate("senderTime");
      }
    });
  }

  /**
   * 获取指定时间前消息
   * 
   * @param userId
   * @param date
   * @param pageSize
   * @return
   * @see com.tmser.tr.companion.dao.JyCompanionMessageDao#getPreMessages(java.lang.Integer,
   *      java.util.Date, java.lang.Integer)
   */
  @Override
  public List<JyCompanionMessage> getPreMessages(Integer currentUserId, Integer companionUserId, Date date,
      Integer pageSize) {
    String sql = "select * from JyCompanionMessage where senderTime<:sdate and ((userIdSender=:currentUserId and userIdReceiver=:companionUserId) "
        + "or (userIdSender=:companionUserId and userIdReceiver=:currentUserId)) order by senderTime desc ";
    Map<String, Object> argMap = new HashMap<String, Object>();
    argMap.put("currentUserId", currentUserId);
    argMap.put("companionUserId", companionUserId);
    argMap.put("sdate", date);
    return this.queryByNamedSqlWithLimit(sql, argMap, this.mapper, pageSize);
  }

  /**
   * 获取指定时间后消息
   * 
   * @param userId
   * @param date
   * @param pageSize
   * @return
   * @see com.tmser.tr.companion.dao.JyCompanionMessageDao#getNextMessages(java.lang.Integer,
   *      java.util.Date, java.lang.Integer)
   */
  @Override
  public List<JyCompanionMessage> getNextMessages(Integer currentUserId, Integer companionUserId, Date date,
      Integer pageSize) {
    String sql = "select * from JyCompanionMessage where senderTime>:sdate and ((userIdSender=:currentUserId and userIdReceiver=:companionUserId) "
        + "or (userIdSender=:companionUserId and userIdReceiver=:currentUserId))  order by senderTime ";
    Map<String, Object> argMap = new HashMap<String, Object>();
    argMap.put("currentUserId", currentUserId);
    argMap.put("companionUserId", companionUserId);
    argMap.put("sdate", date);
    return this.queryByNamedSqlWithLimit(sql, argMap, this.mapper, pageSize);
  }

  /**
   * 第一次沟通时间
   * 
   * @param currentUserId
   * @param userId
   * @return
   * @see com.tmser.tr.companion.dao.JyCompanionMessageDao#getFirstMessageDate(java.lang.Integer,
   *      java.lang.Integer)
   */
  @Override
  public Date getFirstMessageDate(Integer currentUserId, Integer userId) {
    String sql = "select senderTime senderTime from JyCompanionMessage  where "
        + "(userIdSender=:currentUserId and userIdReceiver=:companionUserId) "
        + "or (userIdSender=:companionUserId and userIdReceiver=:currentUserId) order by senderTime limit 1";
    Map<String, Object> argMap = new HashMap<String, Object>();
    argMap.put("currentUserId", currentUserId);
    argMap.put("companionUserId", userId);
    return this.queryByNamedSqlForSingle(sql, argMap, new RowMapper<Date>() {
      @Override
      public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Date(rs.getTimestamp("senderTime").getTime());
      }
    });
  }

  /**
   * 最后一次沟通时间
   * 
   * @param currentUserId
   * @param userId
   * @return
   * @see com.tmser.tr.companion.dao.JyCompanionMessageDao#getLatestMessageDate(java.lang.Integer,
   *      java.lang.Integer)
   */
  @Override
  public Date getLatestMessageDate(Integer currentUserId, Integer userId) {
    String sql = "select senderTime senderTime from JyCompanionMessage  where "
        + "(userIdSender=:currentUserId and userIdReceiver=:companionUserId) "
        + "or (userIdSender=:companionUserId and userIdReceiver=:currentUserId) order by senderTime desc limit 1";
    Map<String, Object> argMap = new HashMap<String, Object>();
    argMap.put("currentUserId", currentUserId);
    argMap.put("companionUserId", userId);
    return this.queryByNamedSqlForSingle(sql, argMap, new RowMapper<Date>() {
      @Override
      public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Date(rs.getTimestamp("senderTime").getTime());
      }
    });
  }

  /**
   * 和我有消息沟通的人，按时间最近排序
   * 
   * @param id
   * @param username
   * @see com.tmser.tr.companion.dao.JyCompanionMessageDao#findMsgUser(java.lang.Integer,
   *      java.lang.String)
   */
  @Override
  public List<JyCompanion> findMsgUser(Integer id, String username) {
    String hql = "";
    RowMapper<JyCompanion> jc = new BeanPropertyRowMapper<JyCompanion>(JyCompanion.class);
    if (StringUtils.isNotEmpty(username)) {
      hql = "SELECT DISTINCT userIdSender as userIdCompanion FROM JyCompanionMessage where userIdReceiver=? and userNameSender like '%"
          + username + "%' ORDER BY senderTime DESC";
      return this.query(hql, new Object[] { id }, jc);
    } else {
      hql = "SELECT DISTINCT userIdSender as userIdCompanion FROM JyCompanionMessage where userIdReceiver=? ORDER BY senderTime DESC";
      return this.query(hql, new Object[] { id }, jc);
    }

  }

}
