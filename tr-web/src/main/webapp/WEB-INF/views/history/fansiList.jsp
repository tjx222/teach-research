<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="">教学反思</ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen">   
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script> 
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98%;
	}
	</style>
	<ui:require module="history/js"></ui:require>
</head>
<body style="background: #fff;">
<!-- 查看 -->
<div id="look_up_dialog" class="dialog" style="display: none;"> 
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">查看</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<div class="cont_list"> 
			资源名称：教案名称
			</div>
			<div class="cont_list"> 
  			类别：
  			</div>
			<div class="cont_list"> 
  			所属课题：课题名称
  			</div>
 			<div class="cont_list"> 
 			教材版本：人教版
 			</div>
			<div class="cont_list"> 
			撰写日期：2015-06-25
			</div>
			<div class="cont_list"> 
			提交状态：已提交
			</div>
			<div class="cont_list"> 
			分享状态：已分享
			</div>
			<div class="cont_list"> 
			文件大小：800k
			</div>
			<div class="cont_list"> 
			文件格式：docx 
			</div> 
		</div>
	</div> 
</div>
<div class="calendar_year_center">
<form id="pageForm"  name="pageForm" method="post" action="${ctx}jy/history/${searchVo.schoolYear }/jxfs/index" onsubmit="dosearch();">
	<div class="page_option"> 
		<div class="a">
			<select class="chosen-select-deselect class_teacher" style="width: 132px; height: 25px;" name="spaceId">
				<c:choose>
					<c:when test="${not empty userSpaceList }">
						<c:forEach var="us" items="${userSpaceList }">
							<option value="${us.id }" <c:if test="${searchVo.spaceId==us.id }">selected="selected"</c:if> >${us.spaceName }</option>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<option>请选择职务</option>
					</c:otherwise>
				</c:choose>
			</select>
		</div>
		<div class="left_border"></div>
		<div class="a1">
			<select class="chosen-select-deselect full_year " style="width: 90px; height: 25px;" name="termId">
				<option value="">全学年</option>
				<option value="0"  <c:if test="${searchVo.termId==0 }">selected="selected"</c:if>>上学期</option>
				<option value="1"  <c:if test="${searchVo.termId==1 }">selected="selected"</c:if>>下学期</option>
			</select>
		</div>  
		<div class="left_border"></div>
		<div class="a1">
			<select class="chosen-select-deselect full_year " style="width: 101px; height: 25px;" name="typeId">
				<option value="">全部反思</option>
				<option value="2"  <c:if test="${searchVo.typeId==2 }">selected="selected"</c:if>>课后反思</option>
				<option value="3"  <c:if test="${searchVo.typeId==3 }">selected="selected"</c:if>>其他反思</option>
			</select>
		</div>  
		<div class="serach">
			<input type="text" class="ser_txt" name="searchStr" value="${searchVo.searchStr }"/>
			<input type="button" class="ser_btn" />
		</div>
	</div>
	<c:if test="${!empty fansiList.datalist}">
	<div class="resources_table_wrap">
		<table class="resources_table">
			<tr>
				<th style="width:323px;text-align:left;padding-left:30px;">资源名称</th>
				<th style="width:100px;">类别</th>
				<th style="width:112px;">日期</th>
				<th style="width:115px;">提交状态</th>
				<th style="width:100px;">分享状态</th>
				<th style="width:95px;">操作</th>
			</tr>
			<c:forEach var="plan" items="${fansiList.datalist }">
				<tr>
					<td style="text-align:left;">
						<input type="checkbox" class="check" resId="${plan.resId }"/>
						<a href="${ctx}jy/scanResFile?resId=${plan.resId}" target="_blank">${plan.planName }</a>
						<c:if test="${plan.isScan }"><span class="yue" planType="${plan.planType }" infoId="${plan.planType==2?plan.infoId:plan.planId }"></span></c:if>
						<c:if test="${plan.isComment }"><span class="ping" planType="${plan.planType }" planId=${plan.planId }></span></c:if>
					</td>
					<td>${plan.planType==2?'课后反思':'其他反思' }</td>
					<td><fmt:formatDate value="${plan.crtDttm}" pattern="yyyy-MM-dd"/></td>
					<td>${plan.isSubmit?'已提交':'未提交' }</td>
					<td>${plan.isShare?'已分享':'未分享' }</td>
					<td>
						<span class="look_up" planId="${plan.planId }" planType="${plan.planType }"></span>
						<span class="download" resId="${plan.resId }" planName="${plan.planName }"></span>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="resources_table_bottom">
			<div class="resources_table_check">
				<input type="checkbox" class="all" />
				全选
			</div>
			<input type="button" class="batch_edit" value="批量下载" />
		</div>
			<ui:page url="${ctx}jy/history/${searchVo.schoolYear }/jxfs/index" data="${fansiList}"  />
			<input id="currentPage" type="hidden" class="currentPage" name="page.currentPage" value="1">
			<%-- <input type="hidden" name="termId" value="${searchVo.termId }"> --%>
			<input type="hidden" name="spaceId" value="${searchVo.spaceId }">
		
	</div>
	</c:if>
	<c:if test="${empty fansiList.datalist}">
		<div class="cont_empty">
			<div class="cont_empty_img"></div>
			<div class="cont_empty_words">您此学年没有资源哟！</div> 
		</div>
    </c:if>
	</form>
</div>
</body>
<script type="text/javascript">
	require(['jquery','lessonplan'],function(){
		//调用方法修改面包屑
		window.parent.changeNav("教学反思");
		 /*文本框提示语*/
	     $('.ser_txt').placeholder({
	       	 word: '输入关键词进行搜索'
	     });
	     $(".resources_table tr th").last().css({"border-right":"none"});
	     /* $(".look_up").click(function(){
			$('#look_up_dialog').dialog({
				width: 420,
				height: 317
			});
		}); */
	});
</script>
</html>