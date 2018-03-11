<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.List"%>
<%@page import="com.tmser.tr.uc.bo.UserSpace"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="个人中心"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/uc/css/uc.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/datetime/css/zepto.mdatetimer.css" media="screen">
	<ui:require module="../m/uc/js"></ui:require>
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>个人中心
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="content">
			<div class="personal_center_l">
				<div class="personal_center_l1">
					<dl>
						<dd>
							<ui:photo src="${_CURRENT_USER_.photo}" alt="" ></ui:photo>
							<span></span>
							<ui:upload_m isWebRes="true" num="0" callback="headerUpload" fileType="jpg,png,gif" relativePath="photo/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }"></ui:upload_m>
						</dd>
						<dt>${_CURRENT_USER_.name}</dt>
					</dl>
				</div>
				<ul>
					<li class="center_li_act"><span>个人信息</span><a href="#"></a></li>
					<li><span>修改密码</span><a href="#"></a></li>
					<li><span>账号管理</span><a href="#"></a></li>
					<li><span>任职信息管理</span><a href="#"></a></li>
				</ul>
				<input type="button" value="退出登录" class="quit_login" onclick="javascript:location.href='/jy/logout'">
			</div>
			<div class="personal_center_r">
				<div class="personal_center_r1 personal_info">
					<div>
						<div class="personal_info_cont1">
							<div class="com_personal_content">
								<div class="com_personal_cont_head_cont">
									 <div class="com_per_img">
									 </div>
									 <div class="com_per_cont">
									  	学校：${_CURRENT_USER_.orgName}
									 </div>
								</div>
								<div class="com_personal_cont_head_cont">
									 <div class="com_per_img1">
									 </div>
									  <div class="com_per_cont">
									  <%
										Set<Integer> gradeids=new HashSet<Integer>(); 
				        				Set<Integer> subjectids=new HashSet<Integer>();
				       					Set<String> bookIds=new HashSet<String>();
				     					List<UserSpace> spaces = (List<UserSpace>)request.getSession().getAttribute("_USER_SPACE_LIST_");
				     					for(UserSpace us : spaces){
				     						if(us.getGradeId() != 0)
				     							gradeids.add(us.getGradeId());
				     						if(us.getSubjectId() != 0)
				     							subjectids.add(us.getSubjectId());
				     						if(us.getBookId() != null)
				     							bookIds.add(us.getBookId());
				     					}
									%>
									  	<span>学科：
									  		<%
												if(subjectids.size() > 0){ 
													for(Integer sid : subjectids){
											%><span><jy:dic key="<%=sid%>"></jy:dic></span>
											<%
												}}else{
											%>
												<span>暂无</span>
											<%
												}
											%>
									  	</span>    
									  	版本：<span id="bbspan" style="display: none;">
											<%
												if(bookIds.size() > 0){ 
													for(String bid : bookIds){
											%>
											<jy:ds key="<%=bid%>" className="com.tmser.tr.manage.meta.service.BookService" var="book"></jy:ds>
			             					<span class="formatName" id="${book.formatName }"> ${book.formatName }</span>
											<%
												}}else{
											%>
												<span>暂无</span>
											<%
													}
											%>
											</span>
									 </div>
								</div>
								<div class="com_personal_cont_head_cont">
									 <div class="com_per_img2">
									 </div>
									  <div class="com_per_cont">
									  	年级：
									  	<%
											if(gradeids.size() > 0){ 
												for(Integer gid : gradeids){
										%>
										<span><jy:dic key="<%=gid%>"></jy:dic></span>
										<%
											}}else{
										%>
										<span>暂无</span>
										<%
											}
										%>
									 </div>
								</div>
								<div class="com_personal_cont_head_cont">
									 <div class="com_per_img3">
									 </div>
									  <div class="com_per_cont" style="border-bottom:0;">
									  	职务：
									  	<c:forEach items="${_USER_SPACE_LIST_}" var="date">
											<span>${date.spaceName}</span>
										</c:forEach>
									 </div>
								</div> 
								
							</div>
						</div>
						<div class="clear"></div>
						<div class="personal_info_cont2">
							<div class="edit"></div>
							<!-- 保存状态 -->
							<!-- <div class="sava"></div> -->
							<div class="info">
								<div class="info_l"><span></span>昵称</div>
								<div class="info_r">
									<input type="text" name="nickname" id="nickname" value="${user.nickname}" readOnly="readonly" maxlength="5">	
								</div>
							</div>
							<div class="info">
								<div class="info_l"><span></span>教龄</div>
								<div class="info_r">
									<input type="text" name="schoolAge" id="schoolAge" value="${user.schoolAge}" readOnly="readonly" maxlength="2">
								</div>
							</div>
							<div class="info">
								<div class="info_l"><span></span>职称</div>
								<div class="info_r">
									<input type="text" name="profession" id="profession" value="${user.profession}" readOnly="readonly" maxlength="6">
								</div>
							</div>
							<div class="info">
								<div class="info_l"><span></span>荣誉称号</div>
								<div class="info_r">
									<input type="text" name="honorary" id="honorary" value="${user.honorary}" readOnly="readonly" maxlength="15">
								</div>
							</div>
							<div class="info">
								<div class="info_l"><span></span>性别</div>
								<div class="info_r">
									<p><span class="${user.sex==0?'act':'' }" data="0"></span><q>男</q></p>
									<p><span class="${user.sex!=0?'act':'' }" data="1"></span><q>女</q></p>
								</div>
							</div>
							<div class="info">
								<div class="info_l"><span></span>联系电话</div>
								<div class="info_r">
									<input type="text" name="cellphone" id="cellphone" value="${user.cellphone}" readOnly="readonly" maxlength="11">
								</div>
							</div>
							<div class="info">
								<div class="info_l"><span></span>出生日期</div>
								<div class="info_r">
									<input type="text" id="picktime" placeholder="出生日期" value="<fmt:formatDate value="${_CURRENT_USER_.birthday}" pattern="yyyy-MM-dd"/>" readOnly="readonly" />
								</div>
							</div>
						</div>
						<div class="clear"></div>
						<div class="personal_info_cont3"> 
							<div class="edit"></div>
							<div class="info">
								<div class="info_l1">
									<img src="static/m/uc/images/img.png" alt="">
								</div>
								<div class="info_r1">个人简介</div>
							</div>
							<div class="about"><!-- about -->
								<textarea style="width:95%" name="explains" id="explains" rows="5" readonly="readonly" maxlength="500">${user.explains}</textarea>
							</div>
						</div>
					</div>
				</div> 
				<div class="personal_center_r1 edit_password"  style="display:none;">
					<div>
						<div class="edit_password_cont">
							<div class="com_personal_content">
								<div class="com_personal_cont_head_cont">
									  <div class="com_per_cont1" >
									  	<div class="info_l2">用户名</div>
								     	<div class="info_r2">${login.loginname}</div>
									 </div>
								</div> 
								<div class="com_personal_cont_head_cont">
									  <div class="com_per_cont1" >
									  	<div class="info_l2">原密码</div>
								     	<div class="info_r2">
								     		<input id="password" type="password" placeholder="6~16个字符，区分大小写" autocomplete="off" >
								     	</div>
									 </div>
								</div> 
								<div class="com_personal_cont_head_cont">
									  <div class="com_per_cont1" >
									  	<div class="info_l2">新密码</div>
								     	<div class="info_r2">
								     		<input id="newpassword" type="password" placeholder="6~16个字符，区分大小写" autocomplete="off" >
								     	</div>
									 </div>
								</div>
								<div class="com_personal_cont_head_cont">
									  <div class="com_per_cont1" >
									  	<div class="info_l2">确认密码</div>
								     	<div class="info_r2">
								     		<input id="renewpassword" type="password" autocomplete="off">
								     	</div>
									 </div>
								</div> 
								<input type="button" class="save_edit" value="确认修改">
							</div>
						</div>
					</div>
				</div>
				<div class="personal_center_r1 account_manmgement" style="display:none;">
					<div>
						<div class="account_manmgement_cont">
							<div class="info_1">
								如果您使用邮箱账号进行登录，请完善以下邮箱信息，并进行验证
							</div>
								<p>
									<label>邮箱地址</label>
									<input type="hidden" name="uid" value="${user.id}"/>
									<input type="text" id="mails" class="validate[required,custom[email],ajax[ajaxUserEmail]] txt" data-old="${user.mail}" name="mails" value="${user.mail}">
									<input type="button" id="btnSendCode" class="btn" value="获取验证码">&nbsp;&nbsp;<span id="isCodeSend"></span>
								</p>
								<p>
									<label>邮箱验证码</label>
									<input type="text" class="validate[required] txt1" id="verificationcode"  name="code" placeholder="请填写邮箱中收到的验证码"/>
								</p>
								
								<input type="button" class="save_1" value="保存" id="saveverificationcode">
						</div>
					</div>
				</div>
				<div class="personal_center_r1 feedback"  style="display:none;">
					<div>
						<div class="feedback_cont">
							<div class="info_2">
								反馈内容
							</div>
							<ul>
							<c:forEach items="${_USER_SPACE_LIST_}" var="date">
							<li><span><jy:di key="${date.roleId}" className="com.tmser.tr.uc.service.RoleService" prop="roleName"></jy:di>${date.roleId}</span><span><jy:dic key="${date.phaseId}"></jy:dic></span>
							<span><jy:dic key="${date.gradeId}"></jy:dic></span>
							<span><jy:dic key="${date.subjectId}"></jy:dic></span>
							<span><jy:ds key="${date.bookId}"></jy:ds></span><b></b></li>
							</c:forEach>
							</ul>
							</div>
							<div class="study_additional_content">
								<div class="zd">最多上传 4 个</div>
								<div class="study_additional_content_l">
									<div class="add_study" id="addphoto">
										<p></p> 
										<span>添加图片</span>
									</div>
									<ui:upload_m num="1" isWebRes="true" callback="feedbackUpload" relativePath="front_feedbackPic/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }" fileType="png,jpg,gif" beforeupload="beforeUpload" fileSize="5"  
									 ></ui:upload_m>
								</div>
								<div class="study_additional_content_r">
								</div>
							</div>
							<div class="act_ztyt_btn">
								<input type="button" class="submit_1" value="提交反馈" id ='submitFeedback'>
								<input type="button" class="submit_btn" value="提交中..." />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','js'],function($){	
	}); 
</script>
</html>