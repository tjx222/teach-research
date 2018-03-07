<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<head></head>
	<script type="text/javascript">
	
</script>
<body>
<div class="pageContent">
	
	<form  class="pageForm required-validate">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label  style="color:red">**反馈详情**</label>
			</div>
			<div class="unit">
				<label>反馈人：</label>
				<input disabled="disabled" value="${recieve.userNameSender}"></input>
			</div>
			<div class="unit">
				<label>反馈内容：</label>
				<textarea rows="10" cols="50" disabled="disabled">${recieve.message}</textarea>
			</div>
			<div class="unit">
				<label>反馈时间：</label>
				<fmt:formatDate value="${recieve.senderTime}" pattern="yyyy-MM-dd HH:mm"/>
			</div>
			<div class="unit">
				<label>反馈附件：</label>
				<c:forEach items="${recieve.list}" var="recv">
				<a href='${ctx}/jy/scanResFile?resId=${recv.id}' target='_blank'><img  alt="资源不存在" title="点击查看原图"  width='100' height='100' src='${ctx }jy/manage/res/download/${recv.id}?isweb=false'></a>
				</c:forEach>
			</div>
			<!-- 回复 -->
			<div class="unit">
				<label style="color:red">**回复详情**</label>
			</div>
			
			<c:forEach items="${voList}" var="vt">
			<div class="unit">
				<label>回复人：</label>
				${vt.userNameReceiver}
			</div>
			<div class="unit">
				<label>回复内容：</label>
				<textarea rows="10" cols="50" disabled="disabled">${vt.replyContent}</textarea>
			</div>
			<div class="unit">
				<label>回复时间：</label>
				<fmt:formatDate value="${vt.replyDate}" pattern="yyyy-MM-dd HH:mm"/>
			</div>
			<div class="unit">
				<label>回复附件：</label>
				<c:if test="${vt.list ne '[null]'}">
				<c:forEach items="${vt.list}" var="atta">
				<a href='${ctx}/jy/scanResFile?resId=${atta.id}' target='_blank'>
				<img  alt="资源不存在" title="点击查看原图"  width='100' height='100' src='${ctx }jy/manage/res/download/${atta.id}?isweb=false'>
				</a>
				</c:forEach>
				</c:if>
				<c:if test="${vt.list eq '[null]'}">
					<div style="margin-top: 4px;"><span>暂无附件</span></div>
				</c:if>
			</div>
			<div class="unit">
				<label style="color:red">-------------------------</label>
			</div>
			</c:forEach>
		</div>
	</form>
</div>
</body>
