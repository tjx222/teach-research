<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<form id="updateSchoolyearList">
<div class="pageContent">
	<div class="pageFormContent" layoutH="56">
		 <c:choose>
		 	<c:when test="${code==0 }">
		 		<span style="color: red;">系统繁忙，请稍后重试！</span>
		 	</c:when>
		 	<c:when test="${code==1 }">
		 		<c:if test="${result.msg=='forbid' }">
		 			<span style="color: red;">${result.content }</span>
		 		</c:if>
		 		<c:if test="${result.msg=='allow' }">
		 			<c:if test="${empty result.content }">
		 				<span style="color: red;">所有的用户已是最新学年，没有要更新学年的用户。</span>
		 			</c:if>
		 			<c:if test="${not empty result.content }">
		 				<div style="width: 563px;">
		 					<c:forEach items="${result.content }" var="us">
			 					<div style="width: 112px;height: 28px;float: left;">
			 					  <input type="checkbox" name="userIds" value="${us.userId }">${us.username }
			 					</div>
		 					</c:forEach>
		 				</div>
		 			</c:if>
		 		</c:if>
		 	</c:when>
		 </c:choose>
	</div>
	<input type="hidden" name="orgId" value="${orgId}"/>
	<div class="formBar">
		<label style="float:left"><input type="checkbox" class="checkboxCtrl" group="userIds" />全选</label>
		<label style="float:left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否升级年级:
		<input type="radio" name="ugflag" value="true" checked="checked" />是 
		<input type="radio" name="ugflag" value="false" />否</label>
		<ul>
			<c:if test="${code==1 }">
				<c:if test="${result.msg=='allow' }">
					<c:if test="${not empty result.content }">
						<li><div class="button"><div class="buttonContent"><button type="button" onclick="saveUpSchYear()">更新</button></div></div></li>
					</c:if>
				</c:if>
			</c:if>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>
</form>
<script type="text/javascript">
	$.pdialog.resizeDialog({style: {height: 300,width:600}}, $.pdialog.getCurrent(), "");
	//学年更新保存修改
	function saveUpSchYear(){
		var userids = "";
		$.each($("input[name='userIds']:checked"),function(index,obj){
				userids += $(obj).val()+",";
		})
		if(userids!=""){
			$.ajax({
				type:"post",
				dataType : "json",
				data:$('#updateSchoolyearList').serialize(),
				url:_WEB_CONTEXT_+"/jy/back/yhgl/saveUpSchYear",
				success:function(data){
					$.pdialog.closeCurrent();
					if(data.statusCode == 200){
						if(data.message){
							alertMsg.confirm("更新成功！<br/>"+data.message);
						}else{
							alertMsg.correct("更新成功！");
						}
					}else{
						alertMsg.error("系统忙，请稍后重试！");
					}
				}
			});
		}else{
			alertMsg.info("请至少选择一个用户进行操作！");
		}
	}
</script>
