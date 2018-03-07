<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="意见反馈"></ui:htmlHeader> 
	<link rel="stylesheet" href="${ctxStatic }/modules/feedback/css/feedback.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/modules/feedback/js/js.js"></script>
	<script type="text/javascript" src="${ctxStatic }/modules/feedback/js/feedback.js"></script>
	<script type="text/javascript" src="${ctxStatic }/modules/feedback/js/feedbackAjax.js"></script>
	<script type="text/javascript" src="${ctxStatic}/common/js/comm.js"></script>
</head>
<body>
<div class="wrapper"> 
	<div class='jyyl_top'>
		<ui:tchTop style="1" modelName="意见反馈"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：<jy:nav id="jyfk"></jy:nav>
	</div>
	<div class="clear"></div>
	<div class="feedback">
		<div class="feedback_l"> 
			<h2>
				<span class="feedback_l_span"></span>
				<strong>意见反馈</strong>
			</h2>
			
			<form id="feedForm" method="post">
				<h3><span>*</span>反馈内容</h3>
				<textarea cols="50" rows="8" name="message" maxlength="5000" class="txterea1" placeholder="请输入问题"></textarea>
				<div class="tab_bor">
					<h6 id="fileuploadContainer">
						<span class="h3">图片附件</span>
						<input type="hidden" id="ztytRes" name="attachment1" value="${resIds}">
						<c:if test="${empty act.commentsNum || act.commentsNum<=0 }">
						<div id="span1">
							<ui:upload containerID="span1" relativePath="front_feedbackPic/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }" fileType="png,jpg,gif" fileSize="5" startElementId="uploadZtyt" beforeupload="beforeUpload" callback="backUpload" name="backresId"></ui:upload>
						</div>
						<b class="shangc" id="uploadZtyt">上传</b>
						</c:if>
					</h6>
					<table border="0" id="resTable" style="width:320px;margin:0 auto;"><tbody>
					</tbody></table>
					
				</div>
				<input type="button" class="submit_fk" onclick="saveFeedBack();" value='提交反馈'><!-- 提交反馈 -->
			</form>
		</div>
		
		<div class="feedback_l_1"  style="display:none;"> 
			<h2>
				<span>反馈内容</span>
				<strong></strong>
			</h2>
			<div class="feedback_l_12" id="left_detail"></div>
			<!-- 反馈详情 -->
		</div>
		<div id="right_1"></div>
		<!-- 反馈列表 -->
	</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</div> 
</body>
</html>
