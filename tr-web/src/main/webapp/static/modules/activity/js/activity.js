define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min'], function (require) {
 var jq=require("jquery");
 var $=jq;
 jq(document).ready(function(){
	jq('.t_r_l_c_select li').click(function(){
		if(!$(this).hasClass("t_r_l_c_li_act")){
			listActivity(this);
		}
    });
	$(".drafts").click(function(){
		toActivityDraft($(this).attr("count"));
	});
	$(".span_yue").click(function(){
		if($(this).is(":empty")){
			showScanListBox($(this).attr("activityId"),false);
		}else{
			showScanListBox($(this).attr("activityId"),true);
		}
		$("#check_box").dialog({width:945,height:560});
	});
	
 });
 
//进入草稿箱
 window.toActivityDraft = function(draftNum) {
 	if(draftNum<=0){
 		alert("您的草稿箱还没有任何内容哟！");
 		return false;
 	}
 	$("#activity_iframe").attr("src",_WEB_CONTEXT_ + "/jy/activity/indexDraft?"+ Math.random());
 	$("#draft_box").dialog({width:600,height:460});
 }
//列表加载
window.listActivity = function(thi) {
 	jq("#listType").val(thi.value);
 	jq("#activityForm").submit();
 }
//删除草稿箱
window.delActivityDraft = function(id) {
 	if(id=='')
 		return false;
 	if(confirm("您确认要删除该草稿吗？"))
 		location.href = _WEB_CONTEXT_ + "/jy/activity/delActivityDraft?id="+id;
 }
//发起备课
window.toActivityEdit = function() {
 	location.href = _WEB_CONTEXT_ + "/jy/activity/toEditActivity";
 }
//修改活动
window.editActivity = function(id) {
 	if(id==''){
 		return false;
 	}
	parent.location.href = _WEB_CONTEXT_ + "/jy/activity/toEditActivity?id="+id;
 	
 }
//删除活动
window.delActivity = function(id) {
 	if(id=='')
 		return false;
 	if(confirm("您确认要删除该记录吗？")){
 		$.ajax({  
	        async : false,  
	        cache:false,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{'activity_id':id},
	        url:   _WEB_CONTEXT_+"/jy/activity/delActivity.json",
	        error: function () {
	            alert('操作失败，请稍后重试');  
	        },  
	        success:function(data){
	        	if(data.result=="success"){
	        		alert("操作成功");
	        		window.location.href=window.location.href;
	        	}else if(data.result=="fail1"){
	        		alert("该活动已被参与讨论，不可删除！");
	        		window.location.href=window.location.href;
	        	}else if(data.result=="fail2"){
	        		alert("该活动已提交，不可删除！");
	        		window.location.href=window.location.href;
	        	}else if(data.result=="fail3"){
	        		alert("系统出错，操作失败");
	        	}
	        }  
	    });
 	}
 }
//提交或取消提交
window.tijiao_quxiao = function(activityId,obj){
	if($(obj).prop("class")=='submit1_btn'){
		if(confirm("您确认要提交吗？")){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{id:activityId},
		        url:   _WEB_CONTEXT_+"/jy/activity/submitActivity.json",
		        error: function () {
		            alert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	if(data.result=="success"){
		        		alert("恭喜您提交成功，管理者可以进行查阅啦！");
		        		window.location.reload();
		        	}else{
		        		alert("系统出错");
		        	}
		        }  
		    });
		}
	}else if($(obj).prop("class")=='qx_submit1_btn'){
		if(confirm("您确认要取消提交吗？")){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{id:activityId},
		        url:   _WEB_CONTEXT_+"/jy/activity/unSubmitActivity.json",
		        error: function () {
		            alert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	if(data.result=="success"){
		        		alert("操作成功");
		        		window.location.reload();
		        	}else if(data.result=="fail"){
		        		alert("操作失败，该活动已被查阅，请刷新页面");
		        	}else{
		        		alert("系统出错");
		        	}
		        }  
		    });
		}
	}
}
//分享或取消分享
window.fenxiang_quxiao = function(activityId,obj){
	if($(obj).prop("class")=='share_btn'){
		if(confirm("分享后，所有小伙伴都可以看到此活动啦，您确定分享吗？")){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{id:activityId},
		        url:   _WEB_CONTEXT_+"/jy/activity/shareActivity.json",
		        error: function () {
		            alert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	if(data.result=="success"){
		        		alert("操作成功");
		        		window.location.reload();
		        	}else{
		        		alert("系统出错");
		        	}
		        }  
		    });
		}
	}else if($(obj).prop("class")=='qx_share_btn'){
		if(confirm("您确定要取消该活动的分享吗？")){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{id:activityId},
		        url:   _WEB_CONTEXT_+"/jy/activity/unShareActivity.json",
		        error: function () {
		            alert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	if(data.result=="success"){
		        		alert("操作成功");
		        		window.location.reload();
		        	}else if(data.result=="fail"){
		        		alert("操作失败，该活动已被评论");
		        	}else{
		        		alert("系统出错");
		        	}
		        }  
		    });
		}
	}
	
}
//判断活动是否开始
window.ifActivityStart = function(currentDate,startDate){
	 if(startDate.length > 0){
		//var currentDate = new Date(+new Date()+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
	    var startDateTemp = startDate.split(" ");    
	    var arrStartDate = startDateTemp[0].split("-");   
	    var arrStartTime = startDateTemp[1].split(":"); 

		var currentDateTemp = currentDate.split(" ");    
	    var arrStartDate1 = currentDateTemp[0].split("-");   
	    var arrStartTime1 = currentDateTemp[1].split(":");   

		var allStartDate = new Date(arrStartDate[0], arrStartDate[1]-1, arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]); 
		var allcurrentDate = new Date(arrStartDate1[0], arrStartDate1[1]-1, arrStartDate1[2], arrStartTime1[0], arrStartTime1[1], arrStartTime1[2]); 
		if (allStartDate.getTime() > allcurrentDate.getTime()) {   
	        alert("该活动还未开始，请于"+startDate+"来准时参加活动");   
	        return false;   
		} else {   
		    //alert("活动开始了");   
		    return true;   
		       }   
		} else {   
		    return true;   
		}   

}

//显示查阅意见box(支持集体备课，教案、课件、反思，其他反思除外 isUpdate:是否更新有新查阅意见状态)
window.showScanListBox = function(activityId,isUpdate){
	jq("#yue_iframe").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndex?flags=false&resType=5&resId="+activityId);
	if(isUpdate){//更新为已查看
		jq.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{id:activityId},
	        url:  _WEB_CONTEXT_+"/jy/activity/setScanListAlreadyShow.json"
	    });
	}
}
});
