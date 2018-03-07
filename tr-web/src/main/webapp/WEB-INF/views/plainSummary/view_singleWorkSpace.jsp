<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:set value="<%=request.getSession(false).getId() %>" var="sessionId"></c:set>
<ui:htmlHeader title="查阅"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/modules/planSummary/css/ps_check.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">  
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript">
	var sessionId = "${sessionId}";
	$(document).ready(function() {
		$(window).scroll(function() {
			$("#kongdiv").toggle();
		});
		$('.ser_btn').click(function(){
			var search=$('#searchFcx').val();
			window.open("https://www.baidu.com/s?q1="+search+"&gpc=stf");
		});
	});
</script>
<style type="text/css">
	.chosen-container-single .chosen-single{
		border:none; 
		background: #f2f1f1; 
		margin-top: 40px;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
	}
</style>
</head>
<body>
	<input type="hidden" id="userSpaceId" value="${userSpaceId }"/>
	<input type="hidden" id="crtDttm" value="${ps.crtDttm.time}"/>
	<input type="hidden" id="submitTime" value="${ps.submitTime.time}"/>
	<input type="hidden" id="authorId" value="${ps.userId}"/>
	<input type="hidden" id="planSummaryId" value="${ps.id}"/>
	<input type="hidden" id="gradeId" value="${ps.gradeId}"/>
	<input type="hidden" id="subjectId" value="${ps.subjectId}"/>
	<input type="hidden" id="roleId" value="${ps.roleId}"/>
	<input type="hidden" id="category" value="${ps.category}">
	<input type="hidden" id="ids" value="${ids}">
	<div class="check_teacher_wrap" style="padding-top:20px;">
		<div class="clear"></div>
		<div class="check_lesson1">
			<div class="check_lesson_wrap_1"> 
				<div class="teacher_news_head">
					<div class="teacher_news_head_bg"></div>	
					<img src="${user.photo}" alt="" width="54" height="54" class="teacher_news_head_img" />  
				</div>
				<b style="height: 0;">${user.name}</b>
				<strong style="height: 0;"><jy:dic key="${us.gradeId }"></jy:dic></strong>
				<strong style="height: 0;"><jy:dic key="${us.subjectId }"></jy:dic></strong>
				<strong style="height: 0;">撰写：${countVo.plainSummaryNum==null?0:countVo.plainSummaryNum}</strong>
				<strong style="height: 0;">提交：${countVo.plainSummarySubmitNum==null?0:countVo.plainSummarySubmitNum}</strong>
				<strong style="height: 0;">已查阅：${countVo.plainSummaryCheckedNum==null?0:countVo.plainSummaryCheckedNum}</strong>
				 <!-- <div class="check_sel">
					<label for="">分类</label>
					<select name="checkStatus" id="checkStatus" class="chosen-select-deselect" style="width:120px;">
						<option value="">全部</option>
						<option value="0">未查阅</option>
						<option value="1">已查阅</option>
					</select> 
				</div>  --> 
			</div> 
			<div class="clear"></div>
			<div class="check_lesson_wrap_11">
				<%-- <ol style="float:left;">
					<li class="category ${(ps.category==1||ps.category==3)?'check_lesson_wrap_11_act':'' } " data-category="1">个人计划</li>
					<li class="category ${(ps.category==2||ps.category==4)?'check_lesson_wrap_11_act':'' }" data-category="2" style="border-right:0;">个人总结</li>
				</ol> --%>
				<div class="ser_in" style="margin-right:20px;margin-top:23px;">
					<label style="width:51px;padding-top:0;">反抄袭：</label>
					<div class="ser_bor1"> 
						<input type="search" id="searchFcx" class="ser_w"/>
						<input type="button" class="ser_btn"/>
					</div>
				</div>
				<div class="clear"></div>
				<div class="check_teacher_wrap2" style="min-height:0;"> 
					<h3 class="file_title">${ps.title }</h3>
					<div id="content">
						
					</div>
					<div class="refer">
					    <c:choose>
					    	<c:when test="${ps.isCheck==0 }"><b></b></c:when>
					    	<c:otherwise><span></span></c:otherwise>
					    </c:choose>
					</div> 
					<div class="see_word">
						<div style="width: 0px; height: 0px; display: none;" id="kongdiv"></div>
						<iframe id="fileView"  width="850px" height="650px;" border="0" style="border: none;margin: 0 auto;display: block;">
						</iframe>
					</div> 
					<!-- <div class="page_wprd">
						<div class="page_wprd_l" id="pre" >
							<span></span>
							上一篇
						</div>
						<div class="page_wprd_r" id="next"> 
							下一篇
							<span></span>
						</div>
					</div> -->
				</div>
				<div class="same_submission" style="margin-top:20px;">
					<h3 class="same_submission_h3"><span></span>他们也提交了同类文章：</h3>
					<iframe id="lessonOthers" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no" ></iframe>
				</div>
				
				<iframe id="checkedBox" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" height="650" scrolling="no"></iframe>
			</div>
			<div class="clear"></div>
		</div>
</div>
<div class="clear"></div>
<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
<ui:require module="planSummary/js"></ui:require>
<script type="text/x-Hanldebars" id="contentTemplate"> 
		<div class="file_info" style="margin-left:320px;">
			<div class="file_info_l">
				<span></span>
				作者：{{ps.editName }}
			</div>
			<div class="file_info_r">
				<span></span>
				提交日期：{{submitTime}}
			</div>
		</div>
</script>
<script type="text/x-Hanldebars" id="emptyTemplate">
			<div class="nofile">
				<div class="nofile1">
					{{errorMsg}}
				</div>
			</div>
		</script>
	<script type="text/javascript">
		require(['view_singleWorkSpace']);
	</script>
	<script type="text/javascript">
		$(document) .ready( function() {
			//获取同时提交这个计划总结的其他人
			$("#lessonOthers").attr("src","jy/planSummary/resourceSubmitOthers?orgId=${ps.orgId}&subjectId=${ps.subjectId}&gradeId=${ps.gradeId}&schoolYear=${ps.schoolYear}&userId=${ps.userId}&category=${ps.category}");
			//下拉列表
			  var config = {
			      '.chosen-select'           : {},
			      '.chosen-select-deselect'  : {allow_single_deselect: true},
			      '.chosen-select-deselect' : {disable_search:true}
			    };
			    for (var selector in config) {
			      $(selector).chosen(config[selector]);
			    }
		})
	</script>
</body>
</html>