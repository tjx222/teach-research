<div>
	<#if type==1><#--校际教研圈消息信息通知-->
		${content}
	<#elseif type==2> <#--邀请专家通知-->
		您已被“${areaName!''}_${spaceName}_${userName}”邀请为校际教研
		“【${typeName}】
		<#if activityTypeId==1>
			<a style="color:#014efd;cursor:pointer;" onclick="joinIt('${ctx}/jy/schoolactivity/joinTbjaSchoolActivity?id=${activityId}','${activityId}');" >${activityName}</a>
		<#elseif activityTypeId==2>
			<a style="color:#014efd;cursor:pointer;" onclick="joinIt('${ctx}/jy/schoolactivity/joinZtytSchoolActivity?id=${activityId}','${activityId}');" >${activityName}</a>
		</#if>”
		的专家，您可以点击标题直接参加活动，也可以点击“校际教研”去参加活动。
	<#elseif type==3><#-- 教研进度表通知 -->
		${content}发布了<a style="color:#014efd;cursor:pointer;" onclick="viewSchedule('${ctx}/jy/teachschedule/view?id=${id}','${id}')" >${name}</a>教研进度表，您可以点击标题直接查看，也可以去“校际教研”中进行查看！
	</#if>
</div>

<script type="text/javascript">
function joinIt(urlStr,activityId){
	 $.ajax({
		type:"post",
		dataType:"json",
		url:_WEB_CONTEXT_+"/jy/schoolactivity/activityIsDelete.json",
		data:{"activityId":activityId},
		success:function(data){
			if(data.result.state == "delete"){
				alert("该教研活动已被发起人删除！");
			}else if(data.result.state == "not-at-time"){
				alert(data.result.msg);
			}else{
				window.open(urlStr,'_blank');
			}
		}
	});
	
}
function viewSchedule(urlStr,id){
	 $.ajax({
		type:"post",
		dataType:"json",
		url:_WEB_CONTEXT_+"/jy/teachschedule/dataIsDelete.json",
		data:{"id":id},
		success:function(data){
			if(data.result == "delete"){
				alert("该教研进度表已被发起人删除！");
			}else{
				window.open(urlStr,'_blank');
			}
		}
	});
	
}
</script>