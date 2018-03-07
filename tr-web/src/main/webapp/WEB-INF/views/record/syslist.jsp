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
					<label for="">名称：</label>
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
					<input id="empty" type="button" class="btn_bottom_4" onclick="$('.dialog_close').click();" value="不改了">
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
		<div class="Growth_Portfolio_content">
			<div class="Growth_Portfolio_content1">
				<div class="Growth_Portfolio_jx">
					<div class="Growth_Portfolio_jx1">
						<h3 class="Growth_Portfolio_content1_h3">${name }</h3>
						<input type="button" class="jx_btn">
					</div>
				</div>
				
				<div class="Growth_Portfolio_jx_cont">
					<div style="height:560px;overflow:hidden;">
					<c:forEach  items="${data.datalist  }" var="record"><!-- 有精选记录 -->
						<div class="Growth_jx_1" >
							<dl class="dl"  resId="${record.path }">
							<c:choose>
							<c:when test="${record.desc!=null&&record.desc!='' }">
								<dd><ui:icon ext="${record.flags }"></ui:icon><span class="tspan"  resId="${record.recordId }"  resName="${record.flago }${record.recordName }"   title="${record.desc }"></span></dd>
							</c:when>
							<c:otherwise>
								<dd><ui:icon ext="${record.flags }"></ui:icon></dd>
							</c:otherwise>
							</c:choose>
								<dt>
									<span title="${record.flago }${record.recordName }"><ui:sout value="${record.flago }${record.recordName }" length="40"  needEllipsis="true"></ui:sout></span>
									<span>${record.time }</span>
								</dt>
							</dl>
							<div class="show_p">
								<ol>
									<li class="del li_del"  id="${record.recordId }" name="${record.flago }${record.recordName }"></li>
								</ol>
							</div> 
						</div>
						</c:forEach>
						<c:if test="${data.datalist =='[]' }"><!--未精选 -->
						<div class="empty_wrap" style="margin-top:150px;"> 
							<div class="empty_info">您还没有精选“${name }”哟,赶快去“精选”吧！</div>
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
		</div>
		<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
</div>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','jp/jquery.form.min','jp/jquery.validationEngine-zh_CN','jp/jquery.validationEngine.min','common/comm','jingxuan'],function(){});
</script>
</body>
</html>