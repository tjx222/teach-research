define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min','editor'], function (require) {
	var $=require("jquery");
	var editor;
	//更新主备人list
	window.checkMainUserList = function() {
		var editMainUserId = $("#editmainuserid").val();
//		if($("#mainUserSubjectId").val()!='' && $("#mainUserGradeId").val()!='') {
			var mid = "subjectId="+$("#mainUserSubjectId").val() + "&gradeId=" + $("#mainUserGradeId").val();
			$.getJSON("jy/activity/mainUserList?"+mid,function(data){
				var lis = data.userSpaceList;
				if(lis.length > 0){
					$("#mainUserId").html('<option value="">请选择主备人</option>');
					for (var i = 0; i < lis.length; i++) {
						if(lis[i].userId==editMainUserId){
							$("#mainUserId").append('<option value="'+lis[i].userId+'" selected="selected">' +lis[i].username+ '</option>');
						}else{
							$("#mainUserId").append('<option value="'+lis[i].userId+'">' +lis[i].username+ '</option>');
						}
					}
				}else{
					$("#mainUserId").html('<option value="">请选择主备人</option>');
				}
				$("#mainUserId").trigger("chosen:updated");
			});
//		}
	}
	$(document).ready(function () {
		init();
	});
	
	function init(){
		webEditorOptions = {width:"860px",height:"160px",style:0,afterChange : function() {
	    	var txtcount = this.count('text');
	    	if(txtcount > 200){
	    		$('#w_count').html("<font color='red'>"+txtcount+"</font>");
	    	}else{
	    		$('#w_count').html(txtcount);
	    	}
		}
	    };
		editor = $("#remark").editor(webEditorOptions)[0];
	  $(".files_wrap_right").click(function(){
			removeIt1(this);
	  });
	  
	  //可选年级唯一的时候，同步主备人
	  if($(".grade_sel_wrap").find("select").length<=0){
		  checkMainUserList();
	  }
	}

/**
 * 同备教案  ------start-----
 */

//更新章节list
window.checkChapterList = function() {
	$("#chapterId").val("");
	var subjectId = $("#mainUserSubjectId").val();
	var gradeId = $("#mainUserGradeId").val();
	if($("#mainUserId").val()!='') {
		var mid = "userId="+$("#mainUserId").val()+"&gradeId="+gradeId+"&subjectId="+subjectId;
		$.getJSON("jy/activity/chapterList?"+mid,function(data){
			var lis = data.lessonInfoList;
			if(lis.length > 0){
				$("#chapterId").html('<option value="">请选择主备课题</option>');
				for (var i = 0; i < lis.length; i++) {
					$("#chapterId").append('<option value="'+lis[i].id+'" id="option_'+lis[i].id+'">' +lis[i].lessonName+ '</option>');
				}
			}else{
				$("#chapterId").html('<option value="">请选择主备课题</option>');
			}
			$("#chapterId").trigger("chosen:updated");
		});
	}
}
//保存备课活动
window.saveActivity = function(isDraft) {
	editor.sync();
	getCheckboxValue();
	if($("#remark").val().length>0 && $("#w_count").text()>200) {
		alert("请输入活动要求少于200个汉字或字符");
		$("#remark").focus();
			return false;
	}
	//先取消disable属性，使其可以被序列化
	if(isDraft){
		var mid = $("#tbjaForm").serialize();	
		if($("#activityName").val()=='') {
			alert("请输入活动主题");
			$("#activityName").focus();
			return false;
		}
		if($("#activityName").val().length>80) {
			alert("请输入活动主题少于80个汉字或字符");
			$("#activityName").focus();
			return false;
		}
		if(confirm("为保证活动时限的准确性，您下次发布活动的时候在设定开始时间或结束时间吧，本次就不给您记录喽！")){
			$.ajax({
				type:"post",
				dataType:"json",
				url:"jy/activity/saveActivityTbja?status=0",
				data: mid,
				error: function () {
		            alert('请求失败');  
		        },
				success:function(data){
					var rc = data.result;
					if(rc == "success"){
						alert("已存至草稿箱");
						parent.location.href = _WEB_CONTEXT_ + "/jy/activity/index";
					}
					else {
						alert("提交失败，请重试！");
						return false;
					}
				}
			});
		}
	}else{
		if(!$("#tbjaForm").validationEngine('validate')) {
			return false;
		}
		if($("#chapterId").val()==''){
			alert("请选择主备教案");
			return false;
		}
		if(confirm("此集体备课将发布给参与人，如果您还没有撰写好，请存草稿，您是否确定发布？")){
			parent.waiting();
			$("#tbjaForm").find('input').removeAttr("disabled");
			$("#tbjaForm").find('select').removeAttr("disabled");
			var mid = $("#tbjaForm").serialize();	
			$.ajax({
				type:"post",
				dataType:"json",
				url:"jy/activity/saveActivityTbja?status=1",
				data: mid,
				error: function () {
		            alert('请求失败');  
		        },
				success:function(data){
					var rc = data.result;
					if(rc == "success")
						parent.location.href = _WEB_CONTEXT_ + "/jy/activity/index";
					else {
						alert("提交失败，请重试！");
						return false;
					}
				}
			});
		}
	}
	
}

//修改备课活动
window.editActivity = function() {
	editor.sync();
	getCheckboxValue();
	
	if(!$("#tbjaForm").validationEngine('validate')) {
		return false;
	}
	if($("#chapterId").val()==''){
		alert("请选择主备教案");
		return false;
	}
	if($("#remark").val().length>0 && $("#w_count").text()>200) {
		alert("请输入活动要求少于200个汉字或字符");
		$("#remark").focus();
			return false;
	}
	if(confirm("您确定修改吗？")){
		$("#tbjaForm").find('input').removeAttr("disabled");
		$("#tbjaForm").find('select').removeAttr("disabled");
		var mid = $("#tbjaForm").serialize();	
		$.ajax({
			type:"post",
			dataType:"json",
			url:"jy/activity/saveActivityTbja?status=1",
			data: mid,
			success:function(data){
				var rc = data.result;
				if(rc == "success"){
					alert("恭喜您，修改成功！");
					parent.location.href = _WEB_CONTEXT_ + "/jy/activity/index";
				}else if(rc == "isHaveDiscuss"){
					alert("活动已被讨论，只能修改部分数据");
					window.location.reload();
				}else if(rc == "isHaveTracks"){
					alert("活动已被参与，只能修改部分数据");
					window.location.reload();
				}else {
					alert("修改失败，请重试！");
					return false;
				}
			}
		});
	}
	
}

//取复选框值
function getCheckboxValue() {
	var va = ",";
	$("[name='grades']").each(function(){
		  if($(this).is(":checked")) {
			  va += $(this).val() + ",";
		  }
	});
	$("#gradeIds").val(va==","?"":va);
	var va2 = ",";
	$("[name='subjects']").each(function(){
		  if($(this).is(":checked")) {
			  va2 += $(this).val() + ",";
		  }
	});
	$("#subjectIds").val(va2==","?"":va2);
	
} 

/**
 * 同备教案  ------end-----
 */

/**
 * 主题研讨  ------start-----
 */
//参考资料附件上传回调
window.backUpload_fj = function(data){
	if(data.code==0){
		var htmlStr = "<div class='files_wrap'><div class='files_wrap_left'></div><div class='files_wrap_center'>"+
							"<div id='"+$("input[name=fjResId]").val()+"' class='files_wrap_center_t' title='"+$("#fileName").val()+"'><span>"+$("#fileName").val()+"</span></div>"+
										"<div class='files_wrap_center_b'>上传完成</div></div><div class='files_wrap_right' onclick='removeIt1(this)'>删除</div></div>";
		$("#attachListDiv").prepend(htmlStr);
	}else{
		alert("文件上传失败");
	}
	window.parent.setCwinHeight("iframe_ztyt",false);
	window.parent.setCwinHeight("iframe_spjy",false);
}
//附件上传之前调用
window.beforeUpload_fj = function(){
	if($("#attachListDiv").find(".files_wrap").length<8){
		return true;
	}else{
		alert("最多允许上传8个参考附件");
		return false;
	}
}
//移除文件
window.removeIt1 = function(obj){
	$(obj).parent().remove();
}
//取参考附件的resId值
function getAttachResIds(){
	var attachResIds = "";
	$("#attachListDiv").find(".files_wrap_center_t").each(function(){
		attachResIds += $(this).prop('id')+",";
	});
	$("#ztytRes").val(attachResIds.substr(0,attachResIds.length-1));
}
//取复选框值
//function getSubjectsCheckboxValue() {
//	var va = ",";
//	$("[name='subjects']").each(function(){
//		  if($(this).is(":checked")) {
//			  va += $(this).val() + ",";
//		  }
//	});
//	$("#subjectIds").val(va==","?"":va);
//} 
//增加备课活动-主题研讨
window.saveActivityZtyt = function(isDraft) {
	editor.sync();
	getCheckboxValue();
	getAttachResIds();
	if($("#remark").val().length>0 && $("#w_count").text()>200) {
		alert("请输入活动要求少于200个汉字或字符");
		$("#remark").focus();
			return false;
	}
	if(isDraft){
		if($("#activityName").val()=='') {
			alert("请输入活动主题");
			$("#activityName").focus();
			return false;
		}
		if($("#activityName").val().length>80) {
			alert("请输入活动主题少于80个汉字或字符");
			$("#activityName").focus();
			return false;
		}
		if(confirm("为保证活动时限的准确性，您下次发布活动的时候在设定开始时间或结束时间吧，本次就不给您记录喽！")){
			var mid = $("#ztytForm").serialize();
			$.ajax({
				type:"post",
				dataType:"json",
				url:"jy/activity/saveActivityZtyt?status=0",
				data: mid,
				success:function(data){
					var rc = data.result;
					if(rc == "success"){
						alert("已存至草稿箱");
						parent.location.href = _WEB_CONTEXT_ + "/jy/activity/index";
					}
					else {
						alert("提交失败，请重试！");
						return false;
					}
				}
			});
		}
	}else{
		if(!$("#ztytForm").validationEngine('validate')) {
			return false;
		}
		if(confirm("此集体备课将发布给参与人，如果您还没有撰写好，请存草稿，您是否确定发布？")){
			parent.waiting();
			$("#ztytForm").find('input').removeAttr("disabled");
			$("#ztytForm").find('select').removeAttr("disabled");
			var mid = $("#ztytForm").serialize();
			$.ajax({
				type:"post",
				dataType:"json",
				url:"jy/activity/saveActivityZtyt?status=1",
				data: mid,
				success:function(data){
					var rc = data.result;
					if(rc == "success")
						parent.location.href = _WEB_CONTEXT_ + "/jy/activity/index";
					else {
						alert("提交失败，请重试！");
						return false;
					}
				}
			});
		}
	}
}

//修改备课活动-主题研讨
window.editActivityZtyt = function() {
	editor.sync();
	getCheckboxValue();
	getAttachResIds();
	if(!$("#ztytForm").validationEngine('validate')) {
		return false;
	}
	if($("#remark").val().length>0 && $("#w_count").text()>200) {
		alert("请输入活动要求少于200个汉字或字符");
		$("#remark").focus();
			return false;
	}
	if(confirm("您确定修改吗？")){
		$("#ztytForm").find('input').removeAttr("disabled");
		$("#ztytForm").find('select').removeAttr("disabled");
		var mid = $("#ztytForm").serialize();
		$.ajax({
			type:"post",
			dataType:"json",
			url:"jy/activity/saveActivityZtyt?status=1",
			data: mid,
			success:function(data){
				var rc = data.result;
				if(rc == "success"){
					alert("恭喜您，修改成功！");
					parent.location.href = _WEB_CONTEXT_ + "/jy/activity/index";
				}else if(rc == "isHaveDiscuss"){
					alert("活动已被讨论，只能修改部分数据");
					window.location.reload();
				}else {
					alert("修改失败，请重试！");
					return false;
				}
			}
		});
	}
	
}
});

