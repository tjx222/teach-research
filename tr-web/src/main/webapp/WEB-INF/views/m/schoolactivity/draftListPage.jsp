<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="校际教研"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/schoolactivity/css/schoolactivity.css" media="screen" />
	<ui:require module="../m/schoolactivity/js"></ui:require>	
</head>
<body>
<div class="act_draft_content" style="top: 0;">
	<div> 
	<c:if test="${ !empty activityDraftList}">
		 <c:forEach items="${activityDraftList.datalist}" var="activity">
		 	<div class="draft_list"> 
				<c:if test="${activity.typeId==1}">
					<div class="activity_tch_left">同<br />备<br />教<br />案</div>
				</c:if>
				<c:if test="${activity.typeId==2}">
					<div class="activity_tch_left1">主<br />题<br />研<br />讨</div>
				</c:if>  
				<c:if test="${activity.typeId==3}">
					<div class="activity_tch_left2">视<br />频<br />研<br />讨</div>
				</c:if>
				<c:if test="${activity.typeId==4}">
					<div class="activity_tch_left3">直<br />播<br />课<br />堂</div>
				</c:if> 
				<div class="draft_list_right">
					<h3><span class="title">${activity.activityName}</span></h3> 
					<ul>
						<li class="edit" data-actid="${activity.id }" data-acttype="${activity.typeId }" ><span>修改</span></li>
						<li class="del" data-actid="${activity.id }"><span>删除</span></li>
					</ul>
				</div>
			</div> 
		 </c:forEach>
	  </c:if>
	  <c:if test="${empty activityDraftList.datalist}">
	  	<!-- 无文件 -->
		<div class="content_k" style="margin-top: 1rem;">
			<dl>
				<dd></dd>
				<dt>草稿箱中还没有任何东西哟！</dt>
			</dl>
		</div>
	  </c:if>
	</div>
</div>
</body>
<script type="text/javascript">
	require(["zepto","iscroll"],function(){
		var act_draft_content = new IScroll('.act_draft_content',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true, 
      		click:true
      	});
		$(".edit").click(function(){
			var id = $(this).attr("data-actid");
			var type = $(this).attr("data-acttype");
			window.parent.location.href = _WEB_CONTEXT_+"/jy/schoolactivity/editActivity?id="+id+"&typeId="+type;
// 			window.parent.location.href = _WEB_CONTEXT_+"/jy/schoolactivity/fqEditSchoolActivity?id="+id+"&typeId="+type;
		});
		$(".del").click(function(){
			var id = $(this).attr("data-actid");
			if(confirm("您确认要删除该草稿吗？")){
				 $.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/schoolactivity/delSchoolActivity.json",
					data:{"id":id},
					success:function(data){
						if(data.isOk){
							window.parent.setdrafnum();
							successAlert("删除成功！");
							//刷新页面
							window.location.reload();
						}else{
							successAlert("删除出错！");
						}
					}
				});
			}
		});
	}); 
</script>
</html>