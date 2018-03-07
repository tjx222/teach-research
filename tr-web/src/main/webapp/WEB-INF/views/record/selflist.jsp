<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="${name }"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/record/css/recordCss.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/modules/record/css/dlog_submit.css" media="screen">
<!-- 	<link rel="stylesheet" href="${ctxStatic }/modules/record/css/list.css" media="screen"> -->
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	
    <ui:require module="record/js"></ui:require>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
	<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript">
	we = '${ctx}';
	page='${page}';
		$(function(){
			var id = '${id}';
			$("#kj_form").validationEngine();
		});
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
					<input type="hidden"  id="bagId">
					<p>
						<label for="">名称：</label>
						<span id="name1"></span><strong></strong>
					</p>
					<div class="clear"></div>
					<p>
						<label for="" id="lab">微评：</label>
						<textarea style="float: left;padding: 5px;font-size: 12px;" placeholder="您可以给精选文件添加微评，如不想添加，请直接点击“保存”" name="desc" id="desc" cols="38" rows="4" class="validate[optional,maxSize[50]] text-input"></textarea>
						<strong >注：最多可输入50个字</strong>
					</p>
					<div class="clear"></div>
					<div class="btn_bottom" style="margin-top:25px;">
						<input id="button" type="button" class="btn_bottom_3" value="修改"> 
						<input type="button" class="btn_bottom_4" value="不改了" onclick="$('.dialog_close').click();">
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<body> 
	<div class="wrapper"> 
		<div class="jyyl_top">
			<ui:tchTop style="1" modelName="成长档案袋"></ui:tchTop>
		</div>  
		<div class="jyyl_nav">
			<h3>当前位置：<jy:nav id="czdad"></jy:nav>&nbsp;>&nbsp;${name }</h3>
			<!-- <h4>当前学年：<select name="" id=""><option value="">2013-2014学年</option><option value="">2014-2015学年</option></select></h4> -->
		</div>
		<div class="clear"></div>
		<form id="hiddenForm" action="${ctx}jy/record/findRList" method="post">
			<input  type="hidden" name="id"  value="${id }">
			<input  type="hidden" name="page"  value="${page }">
		</form>
		<div class="Growth_Portfolio_cont">
			<div class="Reflect_cont_left">
				<h3>添加内容</h3>
				<div class="Reflect_cont_left_1">
					<form id="kj_form" action="${ctx}jy/record/save" method="post">
						<input type="hidden" name="id" value="${id }">
						<input type="hidden" name="recordId"  id="recordId">
						<input type="hidden" name="page" value="${page }">
						<p>	
							<span class="courseware_title_p_span">*</span>
							<label for="">标题：</label>
							<input type="text" id="name" name="name" class="input_txt validate[required,maxSize[30]]" >
						</p>
						<div id="scfj_to"></div>
						<div id="fileuploadContainer" style="margin-left:18px;">
							<span class="courseware_title_p_span">*</span>
							<label for="">文件：</label>
							<ui:upload containerID="fileuploadContainer" relativePath="record/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }" fileType="doc,docx,ppt,pptx,pdf,jpg,jpeg,gif,png,mp3,mp4,flv,swf"  fileSize="50"  startElementId="save"  beforeupload="start"   callback="backSave" name="resId" ></ui:upload>
						</div>
						<p>
							<label for=""  style="margin-left:8px;">微评：</label>
							<textarea  id="kjms" style="float: left;padding: 5px;font-size: 12px;" cols="21" rows="10" name="desc" class="validate[optional,maxSize[50]] " ></textarea>
							<br/><br/><br/>
							<strong style="margin-left:54px;display:inline-block;margin-top:82px;line-height:25px;">注：最多可输入50个字</strong>
						</p>
						<div class="btn_bottom" style="margin-top:240px;">
								<input id="save"  type="button" class="btn_bottom_1" value="保存"> 
								<input id="empty"  type="button" class="btn_bottom_2" value="清空" onclick="notUpdate()">
						</div>
					</form>
				</div>
			</div>
			<div class="Reflect_cont_right ">
				<h3>
					<span>${name }列表</span>
				</h3>

				<div class="clear"></div>
				
				<div class="Reflect_cont_right_1_big">
					<div class="Reflect_cont_right_1">
						<c:choose>
		           			<c:when test="${!empty data.datalist }">
							<div class="Reflect_cont_right_1_wid">
								
								<c:forEach items="${data.datalist  }" var="data">
									
									<div class="Reflect_cont_right_1_dl Growth_jx_1" style="height:145px;margin:25px 10px;">
										<dl class="dl" onclick="scanResFile('${data.path }')" style="height:145px;">
												<c:choose>
													<c:when test="${data.desc!=null&&data.desc!='' }">
														<dd><ui:icon ext="${data.flags }"></ui:icon><span class="tspan"  resId="${data.recordId }"  rId="${data.bagId }" resName="${data.recordName }"   title="${data.desc }"></span></dd>
													</c:when>
													<c:otherwise>
														<dd><ui:icon ext="${data.flags }"></ui:icon></dd>
													</c:otherwise>
												</c:choose>
											<dt style="height:40px;">
												<span class="title" title="${data.recordName }.${data.flags }"><ui:sout value="${data.recordName }.${data.flags }" length="30"  needEllipsis="true"></ui:sout></span></br>
												<span><fmt:formatDate value="${data.createTime  }" pattern="yyyy-MM-dd"/></span>
											</dt>
										</dl>
										<div class="show_p">
											<ol>
												<c:choose>
													<c:when test="${data.status==0}">
														<li title="禁止修改" class="jz_li_edit"></li>
														<li title="禁止删除" class="jz_li_del"></li>
													</c:when>
													<c:otherwise>
														<li title="修改" class="li_edit" onclick="updateThis('${data.recordId}','${data.bagId }','${data.path }','${data.recordName }','${data.desc }')"></li>
														<li title="删除" class="li_del" onclick="deleteThis('${data.recordId}','${data.bagId }')"></li>
													</c:otherwise>
												</c:choose>
												<a title="下载" href="<ui:download resid='${data.path}' filename='${data.recordName }'></ui:download>"><li class="li_down"></li></a>
											</ol>
										</div>
									</div>
									
								</c:forEach>
								<div class="clear"></div>
								</br>
								</br>
								</br>
								</br>
								<form name="pageForm" method="post">
									<ui:page url="${ctx}jy/record/findRList" data="${data}"  />
									<input type="hidden" class="currentPage" name="page.currentPage">
									<input type="hidden" name="id" value="${id}" >
								</form>
								</div>
						
		           			</c:when>
		           			<c:otherwise>
		           				<!-- 无文件 -->
	           					<div class="empty_wrap"> 
									<div class="empty_info">
										您的档案袋里还没有内容哟，赶快去左边"添加"吧！
									</div>
								</div>
		           			</c:otherwise>
		           		</c:choose>
					</div>
				</div>
				
			</div>
		</div>
		<div class="clear"></div>
		<ui:htmlFooter></ui:htmlFooter>
	</div>
	
<script type="text/javascript">
require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','jp/jquery.form.min','jp/jquery.validationEngine-zh_CN','jp/jquery.validationEngine.min','selfjx'],function(){});
</script>
</body>
</html>
