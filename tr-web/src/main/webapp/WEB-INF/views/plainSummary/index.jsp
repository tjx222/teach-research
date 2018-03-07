<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="计划总结"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/planSummary/css/plainSummary.css">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<ui:require module="planSummary/js"></ui:require>
	<style>
		.txt_txt{
		width:300px;
		height:21px;
		}
	</style>
</head>

<div id="planSummaryWin"  class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">撰写计划总结</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<form id="planSummaryForm" method="post">
			<input type="hidden" id="id" name="id">
			<input type="hidden" id="userSpaceId" name="userSpaceId">
			<input type="hidden" id="sysRoleId">
			<input type="hidden" id="subject">
			<input type="hidden" id="grade">
			<input type="hidden" name="contentFileName" id="form_contentFileName">
			<input type="hidden" name="contentFileType" id="form_contentFileType">
			<input type="hidden" id="categoryFen">
			<table style="width:95%;margin-left:15px;margin-top:30px;margin-bottom:15px;layout:fixted">
				<tr style="height:40px;text-align:center">
					<td style="vertical-align: middle;">
						<span style="color:red;font-size:14px;line-height: 17px;display: block;float: left;">*</span>学期：
					</td>
					<td style="text-align:left">
						<div style="display: inline-block;height:18px;" id="term-box">
						<input style="margin-top:-2px;" type="radio" name="term" data-term="上学期" value="0" >上学期
						<input style="margin-top:-2px;margin-left:40px;" type="radio"  name="term" data-term="下学期" value="1" >下学期
						</div>
					</td>
				</tr>
				<tr  style="height:40px;text-align:center">
					<td style="vertical-align: middle;">
						<span style="color:red;font-size:14px;line-height: 17px;display: block;float: left;">*</span>分类：
					</td>
					<td style="text-align:left">
						<div style="display: inline-block;height:18px;" id="category"></div>
					</td>
				</tr>
				<tr  style="height:40px;text-align:center">
					<td>
						<span style="font-size:14px;line-height:20px;display: block;float: left;">&nbsp;&nbsp;</span>添加标签：
					</td>
					<td style="text-align:left">
						<input name="label" id="plainSummaryLabel_input" data-default="true" style="width:300px;margin-top:14px;height:21px;margin-left:3px;border:1px #c1c1c1 solid;">
						<span id="plainSummaryLabel" style="color:#a1a0a0;display:block;height:20px;line-height:25px;margin-left:3px;font-size:14px;"></span>
					</td>
				</tr>
				<tr  style="height:40px;text-align:center">
					<td>
						<span style="color:red;font-size:14px;line-height:20px;display: block;float: left;">*</span>标题：
					</td>
					<td style="text-align:left">
						<input name="title" id="title" data-default="true" style="width:300px;margin-top:14px;height:21px;margin-left:3px;border:1px #c1c1c1 solid;">
						<span style="color:#a1a0a0;display:block;height:20px;line-height:25px;margin-left:3px;font-size:14px;">请根据实际情况修改标题</span>
					</td>
				</tr>
			</table>
			<div id="uploadUiId" style="margin-left:15px;">
			<label for="" style="width: 75px;display: inline-block;text-align: center;"><span style="color:red;font-size:14px;line-height:17px;display: block;float: left;">*</span>文件：</label>
<!-- 			<input id="uploadFile" style="">
			<span id="file_process" class="mes_file_process" style="line-height:22px;"></span> -->
		    <ui:upload name="contentFileKey" originFileName="uploadFile"
					fileType="doc,docx,ppt,pptx,pdf" fileSize="50" 
					relativePath="plansummary/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }" startElementId="savePs" beforeupload="valid"
					containerID="uploadUiId" isWebRes="false" callback="savePs"/>
	
			</div>
			<div class="clear"></div>
		</form>
		<div id="saveBtnDiv">
		<button id="savePs" class="savePs" style="margin-top:30px;">保存</button>
		<button id="cancelSave">取消</button> 
		</div>
		</div>
	</div>
</div>

<div id="punishWin"  class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">发布计划总结</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<div  style="margin:30px auto;width:400px;height:50px;">
				<div class="fb_img"></div>
				<div style="margin-top:12px;font-size:12px;line-height:20px;width:400px;height:45px;">
					<span style="float:left;">发布至：</span>
					<span style="float:left;">
						<input type="radio" name="punishRange" style="margin-top:-3px;" value="0">校内</span><br />
				    <span style="float:left;margin-left:48px;">
				   	<input type="radio" name="punishRange" style="margin-top:-3px;" value="1">校际教研圈 </span>
					<span style="float:left;margin-left:5px;">
						<select name="circleId" id="circleIds" class="chosen-select-deselect" style="width:143px;" onchange="lookTeachCircle()">
							<option value="">请选择</option>
						</select>
					</span>
					<span class="see" id="seeShow" style="display: none;"> 
						查看
						<div class="school" id="circleSchoolNames"></div>
					 </span>
				</div>
			</div>
			<div style="margin:10px auto 0 auto;width:400px;height:60px;font-size:14px;text-align:left;line-height:20px;">
				发布成功后，您校级教研圈的小伙伴们在“<span style="color:#014efd;">教研动态</span>”中就可以看到喽！您确定要发布吗？
			</div>
			<div id="saveBtnDiv">
				<button id="punsihSubmit" class="savePs">保存</button>
				<button id="cancelPunish">取消</button>
			</div>
		</div>
	</div>
</div>
<!-- 查阅窗口 -->
<div id="checkWin" class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">审阅查看</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<div style="height:480px;overflow-y:auto;overflow-x:hidden;">
				<iframe id="checkedBox" style="width:100%;height:100%" frameborder="0" onload="setCwinHeight(this,false,100)" ></iframe>
			</div>
		</div>
	</div>
</div>
<!-- 评论窗口 -->
<div id="reviewWin" class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">评论查看</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<div style="height:480px;overflow-y:auto;overflow-x:hidden;">
			<iframe id="commentBox" style="width:100%;height:100%" frameborder="0" onload="setCwinHeight(this,false,100)" ></iframe>
			</div>
		</div>
	</div>
</div>
<body>
	<input type="hidden" id="schoolYear" value="${schoolYear }">
	<input type="hidden" id="currentUserId" value="${_CURRENT_USER_.id}">
	<input type="hidden" id="schoolTerm" value="${schoolTerm }">
	<div class="wrapper"> 
		<div class="jyyl_top"><ui:tchTop modelName="计划总结" style="1" ></ui:tchTop></div>
		<div class="jyyl_nav">
			<h3>当前位置：<jy:nav id="jhzj"></jy:nav></h3>
		</div>
		<div class='plainSummary_cont'>
			<div class='plainSummary_cont1'>
				<c:forEach items="${usl }" var="us">
					<div class="plainSummary_cont_wrap">
						<h3 class="plainSummary_wrap_h3">
							<span></span>
							<strong>${us.spaceName }</strong>
							<input type="button" class="composeBtn addPlanSummary"
								data-sysRoleId=${us.sysRoleId }
								data-userSpaceId="${us.id }" 
								data-subject="<jy:dic key="${us.subjectId}"></jy:dic>"
								data-grade="<jy:dic key="${us.gradeId}"></jy:dic>"
							/>
						</h3>
						<div class="Pre_cont_right_1">  
							<c:choose>
								<c:when test="${plianSummaryMap[us.id]==null }"> 
									<div class="empty_wrap">
										<div class="empty_img"></div>
										<div class="empty_info">您还没有撰写“计划总结”哟， 赶紧去撰写吧！</div>
									</div>
								</c:when>
								<c:otherwise>
									<div class="PreCont clearfix">
										<div class="PreCont_left">
											<span>
											<c:if test="${jfn:checkSysRole(us.sysRoleId, 'teacher') }">个人计划</c:if>
												<c:if test="${jfn:checkSysRole(us.sysRoleId, 'xkzz')}">学科计划</c:if>
												<c:if test="${jfn:checkSysRole(us.sysRoleId, 'bkzz')}">备课计划</c:if>
												<c:if test="${jfn:checkSysRole(us.sysRoleId, 'njzz')}">年级计划</c:if>
												<c:if test="${jfn:checkSysRole(us.sysRoleId, 'xz')||jfn:checkSysRole(us.sysRoleId, 'zr') || jfn:checkSysRole(us.sysRoleId, 'fxz')}">计划</c:if>
											</span>
										</div>
										<div class="PreCont_right">
											<c:forEach items="${plianSummaryMap[us.id] }" var="ps">
												<c:if test="${ps.category==1 || ps.category==3 }"> 
													<div class="Pre_cont_right_1_dl">
														<dl> 
															<dd class="fileIcon" data-id="${ps.id }" data-resId="${ps.contentFileKey }"> 
											       				 <ui:icon ext="${ps.contentFileType }"></ui:icon>
											                </dd>
															<dt>
																<span>
																	<a href="javascript:void(0);" title='<ui:sout value="${ps.title}" escapeXml="true"/>'>
																		<ui:sout value="${ps.title}" escapeXml="true" length="12" needEllipsis="true"/>
																	</a>
																</span><br>
																<strong><fmt:formatDate value="${ps.lastupDttm}" pattern="yyyy-MM-dd"/> </strong>
															</dt>
														</dl> 
														<div class="show_p">
															<ol>
																<c:set var="unableEdit" value="${(ps.isPunish==0&&ps.isShare==0&&ps.isSubmit==0)?0:1}"/>
																<li class="${unableEdit==1?'li_no_edit':'li_edit' } menu_li_edit menu_li_edit_${unableEdit }"
																	data-status=${unableEdit }
																	data-category="${ps.category }"
																	data-title="${ps.title }" data-id="${ps.id }"
																	data-sysRoleId="${us.sysRoleId }"
																	data-userSpaceId="${us.id }"
																	data-fileName="${ps.contentFileName }"
																	data-term="${ps.term }"
																	data-label="${ps.label }"
																	data-fileId="${ps.contentFileKey }" title="${unableEdit==0?'编辑':'禁止编辑' }">
																</li>
																	
																<c:set var="unableDelete" value="${(ps.isPunish==0&&ps.isShare==0&&ps.isSubmit==0)?0:1}"/>
																<li class="${unableDelete==0?'li_del':'li_no_del' } menu_li_del menu_li_del_${unableDelete }"
																	data-id="${ps.id }" data-title="${ps.title }" title="${unableDelete==0?'删除':'禁止删除' }">
																</li>
																
																<c:if test="${!jfn:checkSysRole(us.sysRoleId,'ZR') && !jfn:checkSysRole(us.sysRoleId,'XZ') && !jfn:checkSysRole(us.sysRoleId,'FXZ')}">
																	<c:set var="submitState" value="${(ps.isSubmit==1&&ps.isCheck!=0)?2:ps.isSubmit }"/>
																	<li class="${submitState==0?'li_submit':(submitState==2?'li_not_cancle_submit':'li_cancle_submit')} menu_li_submit menu_li_submit_${submitState }"
																		data-id="${ps.id }" title="${submitState==0?'提交':(submitState==2?'禁止取消提交':'取消提交') }">
																	</li>
																</c:if>
																
																<c:set var="shareState" value="${(ps.isShare==1&&ps.isReview!=0)?2:ps.isShare }"/>
																<li class="${shareState==0?'li_share':(shareState==2?'li_no_cancle_share':'li_cancle_share') } menu_li_share menu_li_share_${shareState }"
																	data-id="${ps.id }" data-title="${ps.title }" title='${shareState==0?"分享":(shareState==2?"禁止取消分享":"取消分享") }'>
																</li>
																
																<c:if test="${!jfn:checkSysRole(us.sysRoleId,'TEACHER')&&!(!jfn:checkSysRole(us.sysRoleId,'ZR')&&(ps.category==1||ps.category==2))&&!(jfn:checkSysRole(us.sysRoleId,'XZ')&&(ps.category==1||ps.category==2))}">
																	<li class="${ps.isPunish==0?'li_punish':'li_cancle_punish' } menu_li_punish menu_li_punish_${ps.isPunish }"
																		data-id="${ps.id }" data-title="${ps.title }" title="${ps.isPunish==0?'发布':'取消发布' }"></li>
																</c:if>
																
																<c:if test="${!jfn:checkSysRole(us.sysRoleId,'XZ')&&!jfn:checkSysRole(us.sysRoleId,'ZR')&&!jfn:checkSysRole(us.sysRoleId,'FXZ')&&ps.isCheck!=0}">
																	<li class='menu_li_check event_check_${ps.isCheck}' data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }" title="查阅"><span class="menu_li_check_${ps.isCheck }" data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }"></span></li>
																</c:if>
																<c:if test="${!jfn:checkSysRole(us.sysRoleId,'ZR')&&!jfn:checkSysRole(us.sysRoleId,'XZ')&&ps.isReview!=0&&!jfn:checkSysRole(us.sysRoleId,'FXZ') }">
																	<li class="menu_li_review event_review_${ps.isReview}" data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }" title="评论"><span class="menu_li_review_${ps.isReview }" data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }"></span></li>
																</c:if>
															</ol>
														</div>
													</div>
												</c:if> 
											</c:forEach>
										</div>
									</div>
									
									<div class="PreCont clearfix">
										<div class="PreCont_left">
											<span>
												<c:if test="${jfn:checkSysRole(us.sysRoleId, 'teacher') }">个人总结</c:if>
												<c:if test="${jfn:checkSysRole(us.sysRoleId, 'xkzz')}">学科总结</c:if>
												<c:if test="${jfn:checkSysRole(us.sysRoleId, 'bkzz')}">备课总结</c:if>
												<c:if test="${jfn:checkSysRole(us.sysRoleId, 'njzz')}">年级总结</c:if>
												<c:if test="${jfn:checkSysRole(us.sysRoleId, 'xz')||jfn:checkSysRole(us.sysRoleId, 'zr')|| jfn:checkSysRole(us.sysRoleId, 'fxz')}">总结</c:if>
											</span>
										</div>
										<div class="PreCont_right">
											<c:forEach items="${plianSummaryMap[us.id] }" var="ps">
												<c:if test="${ps.category==2 || ps.category==4 }"> 
													<div class="Pre_cont_right_1_dl">
														<dl> 
															<dd class="fileIcon" data-id="${ps.id }" data-resId="${ps.contentFileKey }"> 
											       				 <ui:icon ext="${ps.contentFileType }"></ui:icon>
											                </dd>
															<dt>
																<span>
																	<a href="javascript:void(0);" title='<ui:sout value="${ps.title}" escapeXml="true"/>'>
																		<ui:sout value="${ps.title}" escapeXml="true" length="12" needEllipsis="true"/>
																	</a>
																</span><br>
																<strong><fmt:formatDate value="${ps.lastupDttm}" pattern="yyyy-MM-dd"/> </strong>
															</dt>
														</dl> 
														<div class="show_p">
															<ol>
																<c:set var="unableEdit" value="${(ps.isPunish==0&&ps.isShare==0&&ps.isSubmit==0)?0:1}"/>
																<li class="${unableEdit==1?'li_no_edit':'li_edit' } menu_li_edit menu_li_edit_${unableEdit }"
																	data-status=${unableEdit }
																	data-category="${ps.category }"
																	data-title="${ps.title }" data-id="${ps.id }"
																	data-sysRoleId="${us.sysRoleId }"
																	data-userSpaceId="${us.id }"
																	data-fileName="${ps.contentFileName }"
																	data-term="${ps.term }"
																	data-label="${ps.label }"
																	data-fileId="${ps.contentFileKey }" title="${unableEdit==0?'编辑':'禁止编辑' }">
																</li>
																	
																<c:set var="unableDelete" value="${(ps.isPunish==0&&ps.isShare==0&&ps.isSubmit==0)?0:1}"/>
																<li class="${unableDelete==0?'li_del':'li_no_del' } menu_li_del menu_li_del_${unableDelete }"
																	data-id="${ps.id }" data-title="${ps.title }" title="${unableDelete==0?'删除':'禁止删除' }">
																</li>
																
																<c:if test="${!jfn:checkSysRole(us.sysRoleId,'ZR') && !jfn:checkSysRole(us.sysRoleId,'XZ') && !jfn:checkSysRole(us.sysRoleId,'FXZ')}">
																	<c:set var="submitState" value="${(ps.isSubmit==1&&ps.isCheck!=0)?2:ps.isSubmit }"/>
																	<li class="${submitState==0?'li_submit':(submitState==2?'li_not_cancle_submit':'li_cancle_submit')} menu_li_submit menu_li_submit_${submitState }"
																		data-id="${ps.id }" title="${submitState==0?'提交':(submitState==2?'禁止取消提交':'取消提交') }">
																	</li>
																</c:if>
																
																<c:set var="shareState" value="${(ps.isShare==1&&ps.isReview!=0)?2:ps.isShare }"/>
																<li class="${shareState==0?'li_share':(shareState==2?'li_no_cancle_share':'li_cancle_share') } menu_li_share menu_li_share_${shareState }"
																	data-id="${ps.id }" data-title="${ps.title }" title='${shareState==0?"分享":(shareState==2?"禁止取消分享":"取消分享") }'>
																</li>
																
																<c:if test="${!jfn:checkSysRole(us.sysRoleId,'TEACHER')&&!(!jfn:checkSysRole(us.sysRoleId,'ZR')&&(ps.category==1||ps.category==2))&&!(jfn:checkSysRole(us.sysRoleId,'XZ')&&(ps.category==1||ps.category==2))}">
																	<li class="${ps.isPunish==0?'li_punish':'li_cancle_punish' } menu_li_punish menu_li_punish_${ps.isPunish }"
																		data-id="${ps.id }" data-title="${ps.title }" title="${ps.isPunish==0?'发布':'取消发布' }"></li>
																</c:if>
																
																<c:if test="${!jfn:checkSysRole(us.sysRoleId,'XZ')&&!jfn:checkSysRole(us.sysRoleId,'ZR')&&!jfn:checkSysRole(us.sysRoleId,'FXZ')&&ps.isCheck!=0}">
																	<li class='menu_li_check event_check_${ps.isCheck}' data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }" title="查阅"><span class="menu_li_check_${ps.isCheck }" data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }"></span></li>
																</c:if>
																<c:if test="${!jfn:checkSysRole(us.sysRoleId,'ZR')&&!jfn:checkSysRole(us.sysRoleId,'XZ')&&ps.isReview!=0&&!jfn:checkSysRole(us.sysRoleId,'FXZ') }">
																	<li class="menu_li_review event_review_${ps.isReview}" data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }" title="评论"><span class="menu_li_review_${ps.isReview }" data-id="${ps.id }" data-resType="${(ps.category==1||ps.category==3)?8:9 }"></span></li>
																</c:if>
															</ol>
														</div>
													</div>
												</c:if> 
											</c:forEach>
										</div>
									</div>
									 
								</c:otherwise>
							</c:choose> 
						</div> <div class="clear"></div>
						
					</div><div class="clear"></div>
				</c:forEach>
			</div>
			
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div> 
	<script type="text/x-Hanldebars" id="categoryTemplate">
		{{#TEACHER}}
			<input style="margin-top:-2px;" type="radio" name="category" {{#personPlan}}checked="true"{{/personPlan}} value="1" data-category="个人计划">个人计划
			<input style="margin-top:-2px;margin-left:40px;" type="radio" name="category" {{#personSummary }}checked="true"{{/personSummary}} value="2" data-category="个人总结">个人总结
		{{/TEACHER}}		
		{{#NJZZ}}
			<input style="margin-top:-2px;" type="radio" name="category" {{#rolePlan}}checked="true"{{/rolePlan}} value="3" data-category="年级计划">年级计划
			<input style="margin-top:-2px;margin-left:40px;" type="radio" name="category" {{#roleSummary }}checked="true"{{/roleSummary}} value="4" data-category="年级总结">年级总结
		{{/NJZZ}}
		{{#XKZZ }}
			<input style="margin-top:-2px;" type="radio" name="category" {{#rolePlan }}checked="true"{{/rolePlan}} value="3" data-category="学科计划">学科计划
			<input style="margin-top:-2px;margin-left:40px;" type="radio" name="category" {{#roleSummary }}checked="true"{{/roleSummary}} value="4" data-category="学科总结">学科总结
		{{/XKZZ }}
		{{#BKZZ}}
			<input style="margin-top:-2px;" type="radio" name="category" {{#rolePlan }}checked="true"{{/rolePlan}} value="3" data-category="备课计划">备课计划
			<input style="margin-top:-2px;margin-left:40px;" type="radio" name="category" {{#roleSummary }}checked="true"{{/roleSummary}} value="4" data-category="备课总结">备课总结
		{{/BKZZ}}
		{{#DIRECTOR-HEADMASTER }}
			<div>
				<input style="margin-top:-2px;" type="radio" name="category" {{#personPlan }}checked="true"{{/personPlan}} value="1" data-category="个人计划">个人计划
				<input style="margin-top:-2px;margin-left:40px;" type="radio" name="category" {{#personSummary }}checked="true"{{/personSummary}} value="2" data-category="个人总结">个人总结
			</div>
			<div style="margin-top:6px;">
				<input style="margin-top:-2px;" type="radio" name="category" {{#rolePlan }}checked="true"{{/rolePlan}} value="3" data-category="学校计划">学校计划
				<input style="margin-top:-2px;margin-left:40px;" type="radio" name="category" {{#roleSummary }}checked="true"{{/roleSummary}} value="4" data-category="学校总结">学校总结
			</div>
		{{/DIRECTOR-HEADMASTER }}
	</script>
	<script type="text/javascript">
		require(['jp/jquery.form.min','jp/jquery-ui.min','planSummary']);
	</script>
</body>
</html>