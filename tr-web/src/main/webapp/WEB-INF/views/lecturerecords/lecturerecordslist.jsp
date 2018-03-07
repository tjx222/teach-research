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
		当前位置：<jy:nav id="tkjl"></jy:nav>
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
	<div id="dialog_tip" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title"></span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
				</div>
				<div class="BtnWrap">
					<input type="button" value="确定" class="confirm">
				    <input type="button" value="取消" class="cancel" onclick="dialogTipClose()">  
				</div> 
			</div>
		</div>
	</div>
	<div id="issubmit_box" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">提交上级</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="upload-bottom">
				<div class="upload-bottom_tab">
					<ul>
						<li class="upload-bottom_tab_blue" id="not_submit">未提交</li><li id="is_submit">已提交</li>
					</ul>
				</div>
				<div class="clear"></div>
					<iframe name="iframe2" id="iframe2" frameBorder="0" scrolling="no" style="width:100%;height:467px;"></iframe>
				</div>
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
				<iframe id="commentBox" frameBorder="0" scrolling="auto" style="width:100%;height:516px;"></iframe>
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
								<input type="button" class="zx1" value="撰写听课记录">
							</li>
							<div class="school">
								<span class="in_school" onclick="findPeople();">校内</span>
								<span class="outside_school" onclick="writeLectureRecordsOuteInput();">校外</span>
							</div>
							<c:if test="${ifCanSubmit}">
							<li>
								<input type="button" value="提交给上级" class="submit-sj" onclick="showSubmitBox();">
							</li>
							</c:if> 
						</ol>
		                <div class="t_r_l_c_right">
		                	<div class='fq_teaching_btn'>
		                		<select name="" id="" class="sel chosen-select-deselect" onchange="selectlecture(this);" style="width:120px;">
									<option value="">全部听课记录</option>
									<option value="0" ${flags==0?'selected="selected"':""}>校内听课记录</option>
									<option value="1" ${flags==1?'selected="selected"':""}>校外听课记录</option>
								</select>
		                	</div>
							<div class='drafts'>
								<span class='drafts_img'></span>
								<a onclick="draftBox(${caogaoSize});" href='javascript:void(0);'>草稿箱<c:if test="${caogaoSize!=0}"><span></span></c:if></a>
							</div>
							
						</div>
	                </div> 
					<div class='t_r_l_c_cont'> 
						<div class='t_r_l_c_cont_tab'>
							<div class='t_r_l_c_cont_table' >
								<c:if test="${empty data.datalist }">
									<div class="empty_wrap">
										<div class="empty_img"></div>
										<div style="text-align: center;" class="empty_info">您还没有撰写“听课记录”哟，赶紧点击左上角“撰写听课记录”吧！</div>
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
									    <th style="width:100px;">操作</th> 
									</tr>
						<c:forEach var="kt" items="${data.datalist }">
							<tr id="${kt.id}">
						     <td style="text-align:left;"><strong>${kt.type==0?'【校内】':'【校外】'}</strong><span onclick="seetopic(this);" title="${kt.topic}"><ui:sout value="${kt.topic}" length="26" needEllipsis="true"/></span>
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
						    <td style="position: relative;">
						    	<c:if test="${kt.isShare==0}">
						    		<b class="share_btn" onclick="changeShare(this);" title="分享" topic="${kt.type==0?'【校内】':'【校外】'}${kt.topic}"></b>
					    			<c:if test="${kt.isSubmit!=1}">
					    				<b class="del_btn" onclick="deletelecture(this);" title="删除" topic="${kt.type==0?'【校内】':'【校外】'}${kt.topic}"></b> 
						    		</c:if>
						    		<c:if test="${kt.isSubmit==1}">
						    			<b class="jz_del_btn" title="禁止删除"></b>
						    		</c:if>
						    	</c:if>
						    	<c:if test="${kt.isShare==1}"><!-- 分享或提交了 -->
						    		<c:if test="${kt.isComment==0}">
						    			<b class="qx_share_btn" onclick="changeShare(this);" title="取消分享" topic="${kt.type==0?'【校内】':'【校外】'}${kt.topic}"></b>
						    			<b class="jz_del_btn" title="禁止删除"></b>
						    		</c:if>
						    		<c:if test="${kt.isComment==1}">
						    			<b class="jz_share_btn" title="禁止分享"></b>
						    			<b class="jz_del_btn" title="禁止删除"></b>
						    		</c:if>
						    	</c:if>
						    </td>
						  </tr>
						</c:forEach>
					</table>
					</c:if>
				</div>
				<div class="pages">
					<!--设置分页信息 -->
					<form name="pageForm" method="post">
						<ui:page url="jy/lecturerecords/list?flags=${flags}" data="${data}" views="5"/>
						<input type="hidden" class="currentPage" name="page.currentPage">
					</form>
				</div>
						</div>
					</div> 
				</div>
			</div>
		</div>
	</div>
<div class="clear"></div>
<ui:htmlFooter style="1"></ui:htmlFooter>
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