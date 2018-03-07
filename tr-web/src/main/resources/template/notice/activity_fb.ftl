<#if activityType=='activity'><#--集体备课-->
	<#if typeId==1>
	“${fq_role}_${fq_userName}”发布了集体备课“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/activity/joinTbjaActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，您可以点击标题直接参加活动，也可以点击“集体备课”去参加活动。
	</#if>
	<#if typeId gt 1>
	“${fq_role}_${fq_userName}”发布了集体备课“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/activity/joinZtytActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，您可以点击标题直接参加活动，也可以点击“集体备课”去参加活动。
	</#if>
<#elseif activityType=='regionActivity'> <#--区域教研-->
	<#if typeId==1>
	“${fq_role}_${fq_userName}”发布了区域教研“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/region_activity/joinTbjaRegionActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，您可以点击标题直接参加活动，也可以点击“区域教研”去参加活动。
	</#if>
	<#if typeId==2>
	“${fq_role}_${fq_userName}”发布了区域教研“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/region_activity/joinZtytRegionActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，您可以点击标题直接参加活动，也可以点击“区域教研”去参加活动。
	</#if>
<#elseif activityType=='schoolActivity'> <#--校际教研-->
	<#if typeId==1>
	“${fq_role}_${fq_userName}”发布了校际教研“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/schoolactivity/joinTbjaSchoolActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，您可以点击标题直接参加活动，也可以点击“校际教研”去参加活动。
	</#if>
	<#if typeId==2 && typeId==3>
	“${fq_role}_${fq_userName}”发布了校际教研“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/schoolactivity/joinZtytSchoolActivity?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，您可以点击标题直接参加活动，也可以点击“校际教研”去参加活动。
	</#if>
	<#if typeId==4>
	“${fq_role}_${fq_userName}”发布了校际教研“<a style="cursor:pointer;color:#014efd;" onclick="joinIt('${ctx}/jy/schoolactivity/joinZbkt?id=${activityId}','${activityId}','${activityType}');">【${typeName}】${lessonName}</a>”，您可以点击标题直接参加活动，也可以点击“校际教研”去参加活动。
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
        	}else if(data.result=="fail3"){
        		alert("该活动还未开始，请于"+data.startTime+"来准时参加活动");  
        	}
        }  
    });
}
</script>