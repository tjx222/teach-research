<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="成长档案袋"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen"> 
	<link rel="stylesheet" href="${ctxStatic }/modules/record/css/recordCss.css" media="screen">
	<ui:require module="record/js"></ui:require>
	<script type="text/javascript">
	var we = '${ctx}';
	var id = '${id}';
	var type = '${type}';
	var page = '${page}';
	//参与或查看
	function canyu_chakan(activityId){
				window.open(_WEB_CONTEXT_+"/jy/lecturerecords/seetopic?id="+activityId,"_blank");
	}
	</script>
</head>
<div id="_jx"  class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">修改微评</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<form id="formular" >
				<div class="Growth_Portfolio_bottom">
				<input type="hidden"   id="one">
					<p>
						<label for="" >名称：</label>
						<span id="name"></span><strong></strong>
					</p>
							
					<div class="clear"></div>
					<p>
						<label for="" id="lab">微评：</label>
						<textarea name="desc" id="desc" style="float: left;padding: 5px;font-size: 12px;" placeholder="您可以给精选文件添加微评，如不想添加，请直接点击“保存”" cols="38" rows="4" class="validate[optional,maxSize[50]] text-input"></textarea>
						<strong class="strong_sty" >注：最多可输入50个字</strong>
					</p>
					<div class="clear"></div>
					<div class="btn_bottom" style="margin-top:25px;">
							<input id="button" type="button" class="btn_bottom_3" value="修改"> 
							<input id="empty" type="button" class="btn_bottom_4" onclick="$('.dialog_close').click();"  value="不改了">
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<body>
	<div class="wrapper">
		<div class="jyyl_top">
			<ui:tchTop style="1" modelName="成长档案袋" ></ui:tchTop>
		</div> 
		<div class="jyyl_nav">
			<h3>当前位置：<jy:nav id="czdad"></jy:nav>&nbsp;>&nbsp;${name }</h3>
		</div> 
		<div class="jx_list_content"> 
			<div class="Growth_Portfolio_jx">
					<div class="Growth_Portfolio_jx1">
					<h3 class="Growth_Portfolio_content1_h3">${name }</h3>
					<input type="button" class="jx_btn">
					</div>
			</div> 
			<div class="Growth_Portfolio_tab">
				<div class="Growth_Portfolio_table">
					<table>
						<tbody>
							<tr>
						    <th style="width:350px;">课题</th>
				    		<th style="width:100px;">年级学科</th>
				    		<th style="width:80px;">授课人</th>
				    		<th style="width:100px;">听课节数</th>
				   			<th style="width:100px;">听课时间</th>
				    		<th style="width:60px;">操作</th>
						  </tr>
						<c:forEach  items="${data.datalist  }" var="activity"><!-- 有精选记录 -->
							  	<tr>
								    <td title="${activity.recordName}" style="text-align:left;" >${activity.flago}<a onclick="canyu_chakan(${activity.resId})" style="color:#014efd;cursor:pointer;"><ui:sout value="${activity.recordName}" length="28" ></ui:sout><c:if test="${activity.desc !=''}"><span class="tspan1"  resId="${activity.recordId }"  resName="${activity.flago }${activity.recordName }"    id="sp" title="${activity.desc }"></span></c:if></a></td>
								    <td title="${activity.ext.grade}">${activity.ext.grade}  </td>
								    <td title="${activity.ext.teachPeople}">${activity.ext.teachPeople}</td>
								   <td>${activity.ext.num}</td>
								    <td>${activity.time}</td>
								   	<td class="del"  id="${activity.recordId }" name="${activity.flago }${activity.recordName }">
							    		<span class="li_del1 img2" title="删除"></span>
								    </td>
								  </tr>
							</c:forEach>
						</tbody>
						  
					</table>
					<c:if test="${data.datalist =='[]' }"><!--未精选 -->
					<div class="empty_wrap"> 
						<div class="empty_info">您还没有精选“教学设计”哟<br />，赶紧去“精选”吧！</div>
					</div>
					</c:if>
					</div>
					<div class="pages">
						<form name="pageForm"  method="post">
								<ui:page url="${ctx}jy/record/sysList?id=${id}&type=${type}"  data="${data }"  />
								<input type="hidden" class="currentPage" name="page.currentPage">
								<input type="hidden" name="id" value="${id}">
								<input type="hidden" name="type" value="${type}">
						</form>
					</div>
				</div>
		</div> 
		<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
</div>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','jp/jquery.form.min','jp/jquery.validationEngine-zh_CN','jp/jquery.validationEngine.min','common/comm','jingxuan'],function(){});
</script>
</body>
</html>