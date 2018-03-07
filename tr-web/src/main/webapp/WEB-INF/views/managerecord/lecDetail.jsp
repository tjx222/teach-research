<%@page import="com.tmser.tr.uc.bo.UserSpace"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="听课记录"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/lecturerecords.css" media="screen" />
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<ui:require module="lecturerecords/js"></ui:require>
</head>
<body>
<div class="wrapper"> 
	<div class='jyyl_top'>
		<ui:tchTop style="1" modelName="听课记录"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：<jy:nav id="jxgljl_tkjl"></jy:nav>
	</div>
	<div id="draft_box" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">草稿箱</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				
			</div>
		</div>
	</div>
	<div id="commentList_box" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">评论列表</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<iframe id="commentBox" frameBorder="0" scrolling="auto" style="width:100%;height:467px;"></iframe>
			</div>
		</div>
	</div>
	<div id="replayList_box" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">回复列表</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
			<iframe id="hfym" frameBorder="0" scrolling="auto" style="width:100%;height:467px;"></iframe>
			</div>
		</div>
	</div>
	<div > 
		<div class='teaching_research_list_cont'>
			<div class='t_r_l_c'>
				<div class='t_r_l_c_cont_tab'>  
					<div class="t_r_l_c_select"> 
						<ol class="submitTop">
							<li>
							</li>
						</ol>
		                <div class="t_r_l_c_right">
		                <form action="jy/managerecord/lecDetail"  id="form">
		                	<span>学期:</span><div class='fq_teaching_btn'>
		                		<select name="term1" id="" class="sel chosen-select-deselect" onchange="$('#form').submit();" style="width:120px;">
									<option value="0" ${term==0?'selected="selected"':""}>上学期</option>
									<option value="1" ${term==1?'selected="selected"':""}>下学期</option>
								</select>
		                	</div>
		                	</form>
						</div>
	                </div> 
					<div class='t_r_l_c_cont'> 
						<div class='t_r_l_c_cont_tab'>
							<div class='t_r_l_c_cont_table' >
								<c:if test="${empty data.datalist }">
									<div class="empty_wrap">
										<div class="empty_img"></div>
										<div style="text-align: center;" class="empty_info">您还没有撰写“听课记录”<br />哟，赶紧点击左上角<br />“撰写听课记录”吧！</div>
									</div>
								</c:if>
								<c:if test="${not empty data.datalist }" >
								<table> 
									<tr>
										<th style="width:250px;">课题</th>
									    <th style="width:110px;">年级学科</th>
									    <th style="width:110px;">授课人</th>
									    <th style="width:110px;">听课节数</th>
									    <th style="width:100px;">听课时间</th>
									   <th style="width:100px;">分享状态</th>
									</tr>
						<c:forEach var="kt" items="${data.datalist }">
							<tr id="${kt.id}">
						     <td style="text-align:left;"><strong>${kt.type==0?'【校内】':'【校外】'}</strong><span  onclick="jxgljlSeetopic(this);" title="${kt.topic}"><ui:sout value="${kt.topic}" length="26" needEllipsis="true"/></span>
						    <c:if test="${kt.isReply=='1'}"><!-- 判断是否有回复 -->
						    	<a class="a1" onclick="replylist(${kt.lecturepeopleId},${kt.id},${kt.teachingpeopleId},this);"><c:if test="${kt.replyUp=='1'}"><b></b></c:if></a>
						    </c:if>
							 <c:if test="${kt.isComment=='1'}"><!-- 判断是否有评论 -->
						     	<a class="a3" style="cursor: pointer;" onclick="commentlist(${kt.lecturepeopleId},${kt.resType},${kt.id},this);"><c:if test="${kt.commentUp=='1'}"><b></b></c:if></a>
						     </c:if>
						     </td>
						    <td>
						    	<c:if test="${kt.type=='0'}">
						    		${kt.grade}${kt.subject}
						    	</c:if>
						    	<c:if test="${kt.type=='1'}">
						    		${empty kt.gradeSubject?'-':kt.gradeSubject}
						    	</c:if>
						    </td>
						    <td>${empty kt.teachingPeople?'-':kt.teachingPeople}</td>
						    <td>${kt.numberLectures}</td>
						    <td>
						    	<fmt:setLocale value="zh"/>
 								<fmt:formatDate value="${kt.lectureTime}" pattern="MM-dd"/>
						    </td>
						    <td>
							    	<c:if test="${kt.isShare==0}">
							    		   未分享
							    	</c:if>
							    	<c:if test="${kt.isShare==1}">
							               已分享
							    	</c:if>
						    </td>
						  </tr>
						</c:forEach>
					</table>
					</c:if>
				</div>
						</div>
					</div> 
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
<ui:htmlFooter style="1"></ui:htmlFooter>
	<div class="pages">
		<!--设置分页信息 -->
					<form name="pageForm" method="post">
						<ui:page url="jy/managerecord/lecDetail" data="${data}" />
						<input type="hidden" class="currentPage" name="page.currentPage">
						<input type="hidden"   name="term1" value="${term }">
						<input type="hidden"   name="subject1"  value="${subject }">
						<input type="hidden"  name="grade1" value="${grade }">
						<input type="hidden"  name="phaseId1" value="${phaseId}">
					</form>
	</div>
</div>
<script type="text/javascript">
	$(document) .ready( function() {
		require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','lecturerecords','common/comm'],function(){});
		//下拉列表
		  var config = {
		      '.chosen-select'           : {},
		      '.chosen-select-deselect'  : {allow_single_deselect: true},
		      '.chosen-select-deselect' : {disable_search:true}
		    };
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
	});
	</script>
</body>
</html>