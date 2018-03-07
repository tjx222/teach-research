<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="查阅计划总结"></ui:htmlHeader> 
	<link rel="stylesheet" href="${ctxStatic }/modules/planSummary/css/ps_check.css" media="screen">
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css"> 
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen"> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(window).scroll(function() {
				$("#kongdiv").toggle();
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
	<form id="f_plan_submit" action="" target="_blank">
		<input type="hidden" name="planSummayNum" id="f_plannum" value="">
		<input type="hidden" name="planSummaySubmitNum" id="f_plansnum" value="">
		<input type="hidden" name="planSummayCheckedNum" id="f_plancnum" value="">
		<input type="hidden" name="ids" id="f_planids" value="">
	</form>
	<input type="hidden" id="workSpaceId" value="${us.id }">
	<input type="hidden" id="userId" value="${user.id }">
	<div class="jyyl_top">
		<ui:tchTop style="1" hideMenuList="false"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
			当前位置：
			<jy:nav id="jhzj_check_teacher_name" hidden="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')?null:2 }">
				<jy:param name="teacherName" value="${user.name }"></jy:param>
				<jy:param name="workSpaceId" value="${us.id }"></jy:param>
				<jy:param name="userId" value="${user.id }"></jy:param>
			</jy:nav>
	</div>
	<div class="clear"></div>
	<div class="wrap" style="margin-bottom:20px;">
		
		<div class="check_lesson">
			<div class="check_lesson_wrap_1" style="margin-bottom:20px;"> 
				<div class="teacher_news_head">
					<div class="teacher_news_head_bg"></div>	
					<img src="${user.photo}" alt="" width="54" height="54" class="teacher_news_head_img" />  
				</div>
				<b style="height: 0;">${user.name}</b>
				<strong style="height: 0;"><jy:dic key="${us.gradeId }"></jy:dic></strong>
				<strong style="height: 0;"><jy:dic key="${us.subjectId }"></jy:dic></strong>
				<strong style="height: 0;">撰写：<div style="display:inline" id="planSummaryNum"></div></strong>
				<strong style="height: 0;">提交：<div style="display:inline" id="planSummarySubmitNum"></div></strong>
				<strong style="height: 0;">已查阅：<div style="display:inline" id="planSummaryCheckedNum"></div></strong>
				<div class="check_sel">
					<label for="">分类</label>
					<select name="checkStatus" id="checkStatus" class="chosen-select-deselect" style="width:120px;">
						<option value="">全部</option>
						<option value="0">未查阅</option>
						<option value="1">已查阅</option>
					</select> 
				</div>
			</div>
			<div class="clear"></div>
			<div id="content" class="check_lesson_wrap_2">
			</div>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>
	<script type="text/x-Hanldebars" id="contentTemplate">
		
	<div class="check_lesson_wrap_2_left">
	<h4>个人计划</h4>
	<strong>撰写：{{total.plainNum }}</strong>
	<strong>提交：{{total.plainSubmitNum}}</strong>
	<strong>已查阅：{{total.plainCheckedNum}}</strong>
	<div class="clear"></div>
	{{#planItems}}
			<div class="check_lesson_dl">
				<a class="planSummary" data-planSummaryId="{{id}}"  data-planSummayNum="{{planSummaryNum}}" data-planSummaySubmitNum="{{planSummarySubmitNum}}" data-planSummayCheckedNum="{{planSummaryCheckedNum}}">
				<dl>
					<dd>
						<img src="./static/common/icon/base/{{contentFileType}}.png"></img>
					</dd>
					<dt>
						<span> {{title}}</span> 
						<span> {{crtDttm}}</span>
					</dt>
					{{#checkStatus}}
						<strong></strong>
					{{/checkStatus}}
				</dl>
				</a>
			</div>
	{{/planItems}}
	{{#noPlanItems}}
		<div class="nofile">
			<div class="nofile1">该教师还未提交“个人计划”，您可以提醒他提交，或者查阅其他教师的工作！</div>
		</div>
	{{/noPlanItems}}
	</div>
	<div class="check_lesson_wrap_2_right">
	<h4>个人总结</h4>
	<strong>撰写：{{total.summaryNum }}</strong>
	<strong>提交：{{total.summarySubmitNum}}</strong>
	<strong>已查阅：{{total.summaryCheckedNum}}</strong>
	<div class="clear"></div>
	{{#summaryItems}}
			<div class="check_lesson_dl">
				<a class="planSummary" data-planSummaryId="{{id}}"  data-planSummayNum="{{planSummaryNum}}" data-planSummaySubmitNum="{{planSummarySubmitNum}}" data-planSummayCheckedNum="{{planSummaryCheckedNum}}">
				<dl>         
					<dd>
						<img src="./static/common/icon/base/{{contentFileType}}.png"></img>
					</dd>
					<dt>
						<span> {{title}}</span> 
						<span> {{crtDttm}}</span>
					</dt>
					{{#checkStatus}}
						<strong></strong>
					{{/checkStatus}}
				</dl>
				</a>
			</div>
	{{/summaryItems}}
	{{#noSummaryItems}}
		<div class="nofile">
			<div class="nofile1">该教师还未提交“个人总结”，您可以提醒他提交，或者查阅其他教师的工作！</div>
		</div>
	{{/noSummaryItems}}
	</div>
	
	</script>
	
	<ui:require module="planSummary/js">
	</ui:require>
	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
	<%-- <script type="text/javascript"
		src="${ctxStatic }/modules/planSummary/js/planSummary_singleWorkSpace.js"></script>
	<script type="text/javascript"
		src="${ctxStatic }/lib/handlebars/handlebars-v3.0.0.js"></script> --%>
	<script>
		require([ 'planSummary_singleWorkSpace' ]);
	</script>
	<script type="text/javascript">
		$(document) .ready( function() {
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
