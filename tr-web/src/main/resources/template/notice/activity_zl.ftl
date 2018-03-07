<#if activityType=='activity'><#--集体备课-->
	<#if isOver>
	您参与的集体备课“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/activity/viewTbjaActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，发起人已对教案进行了整理，您可以去“集体备课”中进行查看
	<#else>
	您参与的集体备课“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/activity/joinTbjaActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，发起人已对教案进行了整理，您可以去“集体备课”中进行查看
	</#if>
<#elseif activityType=='regionActivity'> <#--区域教研-->
	<#if isOver>
	您参与的区域教研“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/region_activity/viewTbjaRegionActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，发起人已对教案进行了整理，您可以去“区域教研”中进行查看
	<#else>
	您参与的区域教研“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/region_activity/joinTbjaRegionActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，发起人已对教案进行了整理，您可以去“区域教研”中进行查看
	</#if>
<#elseif activityType=='schoolActivity'> <#--校际教研-->
	<#if isOver>
	您参与的校际教研“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/schoolactivity/viewTbjaSchoolActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，发起人已对教案进行了整理，您可以去“校际教研”中进行查看
	<#else>
	您参与的校际教研“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/schoolactivity/joinTbjaSchoolActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，发起人已对教案进行了整理，您可以去“校际教研”中进行查看
	</#if>
</#if>

<script type="text/javascript">
function joinIt(urlStr,activityId,activityType){
	var ajaxUrl = "";
	if(activityType == 'activity'){
		ajaxUrl = _WEB_CONTEXT_+"/jy/activity/ifActivityValid.json";
	}else if(activityType == 'regionActivity'){
		ajaxUrl = _WEB_CONTEXT_+"/jy/region_activity/ifActivityValid.json";
	}else if(activityType == 'schoolActivity'){
		ajaxUrl = _WEB_CONTEXT_+"/jy/schoolactivity/ifActivityValid.json";
	}
	$.ajax({  
        async : false,  
        cache:true,  
        type: 'POST',  
        dataType : "json",  
        data:{id:activityId},
        url:   ajaxUrl,
        error: function () {
            alert('操作失败，请稍后重试');  
        },  
        success:function(data){
        	if(data.result=="success"){
				window.open(urlStr,'_blank');
        	}else if(data.result=="fail1"){
        		alert("该教研活动已被发起人删除！");
        	}else if(data.result=="fail2"){
        		alert("该教研活动已被发起人删除！");
        	}
        }  
    });
}

</script>