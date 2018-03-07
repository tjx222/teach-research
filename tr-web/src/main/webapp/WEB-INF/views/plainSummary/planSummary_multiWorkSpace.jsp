<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="查阅计划总结"></ui:htmlHeader> 
	<link rel="stylesheet" href="${ctxStatic }/modules/planSummary/css/ps_check.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen"> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<style type="text/css">
		.hidden{
			display:none;
		}
		.chosen-container{margin-top:3px;float:right;margin-right:3px;}
	.chosen-container-single .chosen-single span{ padding-top:0;color:#474747;font-size:14px;}
	</style>
</head>
<body>
	<form id="fm_plan_submit" action="" target="_blank">
		<input type="hidden" name="subjectId" id="fm_subjectId" value="">
		<input type="hidden" name="gradeId" id="fm_gradeId" value="">
		<input type="hidden" name="checkStatus" id="fm_checkStatus" value="">
		<input type="hidden" name="ids" id="fm_ids" value="">
	</form>
	<input type="hidden" id="roleId" value="${roleId }"/>
	<div class="jyyl_top">
		<ui:tchTop style="1" hideMenuList="false"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jhzj_check_multi">
			<c:if test="${roleId==1375}">
				<jy:param name="roleType" value="查阅学科"></jy:param>
			</c:if>
			<c:if test="${roleId==1374}">
				<jy:param name="roleType" value="查阅年级"></jy:param>
			</c:if>
			<c:if test="${roleId==1373}">
				<jy:param name="roleType" value="查阅备课组"></jy:param>
			</c:if>
			<jy:param name="roleId" value="${roleId }"></jy:param>
		</jy:nav>
	</div>
	<div class="clear"></div>
	<div class="wrap" style="margin-bottom:20px;">
		<div class="check_lesson_primary" style="padding:0px;">
			
				<!-- 非年级组长才展示学科信息 -->
				<c:if test="${roleId!=1374 }">
					<div class="check_lesson_primary_l ">
					<h3>计划总结</h3>
					<ui:relation var="subjects" type="xdToXk" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
					<c:forEach items="${subjects }" var="s" varStatus="st">
						<h4>
							<span data-subjectId="${s.id }" data-subjectName="${s.name }"
								class="${subjectId == s.id || (empty subjectId && st.index == 0) ? 'primary_act' :'' } subject">${s.name }</span>
						</h4>
					</c:forEach>
					</div>
				</c:if>
			<div class="check_lesson_primary_r" ${roleId!=1374?'':'style="width:100%;"' }>
				<h3 style="	border-bottom:1px #e0e2e7 solid;">
					<ol>
						<!-- 非学科组长才展示年级信息 -->
						<c:if test="${roleId!=1375 }">
							<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
							<c:forEach items="${grades }" var="g" varStatus="st">
								<li data-gradeId="${g.id }" data-gradeName="${g.name }" class="grade ${gradeId == g.id || (empty gradeId && st.index == 0) ? 'nj_act':'' }">${g.name }</li>
							</c:forEach>
						</c:if>
					</ol>
					<select name="checkStatus" id="checkStatus" class="chosen-select-deselect" style="width:120px;">
						<option value="">全部</option>
						<option value="0">未查阅</option>
						<option value="1">已查阅</option>
					</select> <label for="checkStatus">分类</label>
				</h3>
				<div class="check_lesson_wrap_21" id="content"></div>
			</div>
		</div>



	</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
	<script type="text/x-Hanldebars" id="contentTemplate">

<div class="check_lesson_wrap_2_left1">
	<h4>
	<c:if test="${roleId==1375}">
		学科计划
	</c:if>
	<c:if test="${roleId==1374}">
		年级计划
	</c:if>
	<c:if test="${roleId==1373}">
		备课组计划
	</c:if>
	</h4>
	<strong>{{us.subjectId }}</strong> 
	<strong>撰写：{{total.plainNum }}</strong>
	<strong>提交：{{total.plainSubmitNum}}</strong>
	<strong>已查阅：{{total.plainCheckedNum}}</strong>
	<div class="clear"></div>
	{{#planItems }}
			<div class="check_lesson_dl">
			<a class="planSummary" data-planSummaryId="{{id}}">
				<dl>
					<dd>
						<img src="./static/common/icon/base/{{contentFileType}}.png"></img>
					</dd>
					<dt>
						<span> {{title}}</span> 
						<span> {{submitTime}}</span>
					</dt>
					{{#checkStatus}}
						<strong></strong>
					{{/checkStatus}}
				</dl>
			</a>
			</div>
	{{/planItems}}
	{{#noPlanItems}}
		<div class="empty_wrap">
			<div class="empty_info">
						<c:if test="${roleId==1375}">
							该学科还未提交“学科计划”，您可以提醒他提交，或者查阅其他学科的工作！
						</c:if>
						<c:if test="${roleId==1374}">
							该年级还未提交“年级计划”，您可以提醒他提交，或者查阅其他年级的工作！
						</c:if>
						<c:if test="${roleId==1373}">
							该备课组还未提交“备课组××”，您可以提醒他提交，或者查阅其他备课组的工作！
						</c:if>
			</div>
		</div>
	{{/noPlanItems}}
	</div>
	<div class="check_lesson_wrap_2_right1">
	<h4>
	<c:if test="${roleId==1375}">
		学科总结
	</c:if>
	<c:if test="${roleId==1374}">
		年级总结
	</c:if>
	<c:if test="${roleId==1373}">
	备课组总结
	</c:if>
	</h4>
	<strong>{{us.subjectId }}</strong> 
	<strong>撰写：{{total.summaryNum }}</strong>
	<strong>提交：{{total.summarySubmitNum}}</strong>
	<strong>已查阅：{{total.summaryCheckedNum}}</strong>
	<div class="clear"></div>
	{{#summaryItems}}
			<div class="check_lesson_dl">
				<a class="planSummary" data-planSummaryId="{{id}}">
				<dl>
					<dd>
						<img src="./static/common/icon/base/{{contentFileType}}.png"></img>
					</dd>
					<dt>
						<span> {{title}}</span> 
						<span> {{submitTime}}</span>
					</dt>
					{{#checkStatus}}
						<strong></strong>
					{{/checkStatus}}
				</dl>
				</a>
			</div>
	{{/summaryItems}}
	{{#noSummaryItems}}
		<div class="empty_wrap">
			<div class="empty_info">
						<c:if test="${roleId==1375}">
							该学科还未提交“学科总结”，您可以提醒他提交，或者查阅其他学科的工作！
						</c:if>
						<c:if test="${roleId==1374}">
							该年级还未提交“年级总结”，您可以提醒他提交，或者查阅其他年级的工作！
						</c:if>
						<c:if test="${roleId==1373}">
							该备课组还未提交“备课组总结”，您可以提醒他提交，或者查阅其他备课组的工作！
						</c:if>
			</div>
		</div>
	{{/noSummaryItems}}
</div>

</script>

<ui:require module="planSummary/js"></ui:require>
<script type="text/javascript">
	require([ 'planSummary_multiWorkSpace' ]);
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

