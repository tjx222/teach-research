<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		 				<span style="color: red;">所有的用户已经同步到优课平台，没有需要同步的用户。</span>
		 			</c:if>
		 			<c:if test="${not empty result.content }">
		 				<div style="width: 563px;">
		 					<c:forEach items="${result.content }" var="us">
			 					<div style="width: 112px;height: 28px;float: left;">
			 					  <input type="checkbox" name="userid" value="${us.id }">${us.name }
			 					</div>
		 					</c:forEach>
		 				</div>
		 			</c:if>
		 		</c:if>
		 	</c:when>
		 </c:choose>
	</div>
	<div class="formBar">
		<label style="float:left"><input type="checkbox" class="checkboxCtrl" group="userid" />全选</label>
		<ul>
			<c:if test="${code==1 }">
				<c:if test="${result.msg=='allow' }">
					<c:if test="${not empty result.content }">
						<li><div class="button"><div class="buttonContent"><button type="button" onclick="syncUserInfoToUclass()">同步</button></div></div></li>
					</c:if>
				</c:if>
			</c:if>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	$.pdialog.resizeDialog({style: {height: 300,width:600}}, $.pdialog.getCurrent(), "");
	//学年更新保存修改
	function syncUserInfoToUclass(){
		var userids = "";
		$.each($("input[name='userid']:checked"),function(index,obj){
				userids += $(obj).val()+",";
		})
		if(userids!=""){
			userids = userids.substring(0,userids.length-1);
			$.ajax({
				type:"post",
				dataType : "json",
				data:{"userIds":userids,"orgId":"${orgId}"},
				url:_WEB_CONTEXT_+"/jy/ws/user/syncUserInfoToUclass",
				success:function(data){
					console.log(data);
					$.pdialog.closeCurrent();
					if(data.juiResult.statusCode == 200){
						var msg="";
						if(data.status){
							for(var i in data.status){
								msg+=i+":"+data.status[i]+"<br/>";
							}
						}
						if(msg==""){
							msg="账号同步成功！";
						}
						alertMsg.info(msg);
					}else{
						alertMsg.error(data.juiResult.message);
					}
				}
			});
		}else{
			alertMsg.info("请至少选择一个用户进行操作！");
		}
	}
</script>
