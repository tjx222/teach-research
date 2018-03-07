define(["require","zepto","iscroll","datetime","placeholder"], function (require) {
	var $= Zepto; 
	 $(function(){
		 if($(".ja_content_class").length>0 && $(".ja_content_class").find("option").length<=0){
			 checkMainUserList();
		 } 
		 if($("#wrap").length>0){ 
			 var  myScroll = new IScroll('#wrap',{
	      		  scrollbars:true,
	      	      mouseWheel:true,
	      		  fadeScrollbars:true,
		      });
		 } 
		$('#picktime_start').mdatetimer({ 
			mode : 2, //时间选择器模式：1：年月日，2：年月日时分（24小时），3：年月日时分（12小时），4：年月日时分秒。默认：1
			format : 2, //时间格式化方式：1：2015年06月10日 17时30分46秒，2：2015-05-10  17:30:46。默认：2
			years : [2000, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030], //年份数组
			nowbtn : true, //是否显示现在按钮
			onOk : null, 	
			onCancel:null 
			});
		$('#picktime_end').mdatetimer({ 
			mode : 2, //时间选择器模式：1：年月日，2：年月日时分（24小时），3：年月日时分（12小时），4：年月日时分秒。默认：1
			format : 2, //时间格式化方式：1：2015年06月10日 17时30分46秒，2：2015-05-10  17:30:46。默认：2
			years : [2000, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030], //年份数组
			nowbtn : true, //是否显示现在按钮
			onOk : null, 	
			onCancel:null 
		});
		bindEvent();
		});
	function bindEvent(){
		
		$(".remark").placeholder({
	   		 word: '输入活动要求（200字内）'
	   	});
		$('.p_option').click(function (){
		    if($(this).hasClass('p_act')){
		    	$(this).removeClass('p_act');
			}else{
				$(this).addClass('p_act');
			}
		});
		$(".edit_text").click(function(){
			wordObj.Document.Application.ActiveWindow.View.ReadingLayout = false;
			fadeInOrOut(true);
			$(this).attr("class","sava_text");
			$(".sava_text").click(function(){
				var editType = $('#iframe1',window.parent.document).attr("editType");
				saveLessonPlanTracks(editType);
			});
		});
		
		$("#mainUserGradeId").on("change",function(){
			checkMainUserList();
		});
		
		$("#mainUserId").on("change",function(){
			checkChapterList();
		});
		
		$("#chapterId").on("change",function(){
			if($(this).val()!=""){
				$("#activityName").val($("#option_"+$(this).val()).html()+"集体备课");
			}
		});
		
		$(".act_tbja_btn").find(".release").click(function(){ //绑定集备发布按钮
			saveActivity(false);
		});
		$(".act_tbja_btn").find(".deposit_draft").click(function(){ //绑定集备存草稿按钮
			saveActivity(true);
		});
		$(".act_ztyt_btn").find(".release").click(function(){ //绑定主题研讨发布按钮
			saveActivityZtyt(false);
		});
		$(".act_ztyt_btn").find(".deposit_draft").click(function(){ //绑定主题研讨存草稿按钮
			saveActivityZtyt(true);
		});
		$(".act_spjy_btn").find(".release").click(function(){ //绑定视频教研发布按钮
			saveActivitySpjy(false);
		});
		$(".act_spjy_btn").find(".deposit_draft").click(function(){ //绑定视频教研存草稿按钮
			saveActivitySpjy(true);
		});
		$(".act_tbja_btn").find(".modify").click(function(){ //绑定集备修改按钮
			editActivity();
		});
		$(".act_tbja_btn").find(".no-modify").click(function(){ //绑定集备不改了按钮
			history.go(-1);
		});
		$(".act_ztyt_btn").find(".modify").click(function(){ //绑定主题研讨修改按钮
			editActivityZtyt();
		});
		$(".act_ztyt_btn").find(".no-modify").click(function(){ //绑定主题研讨不改了按钮
			history.go(-1);
		});
		$(".act_spjy_btn").find(".modify").click(function(){ //绑定视频教研修改按钮
			editActivitySpjy();
		});
		$(".act_spjy_btn").find(".no-modify").click(function(){ //绑定视频教研不改了按钮
			history.go(-1);
		});
		$(".study_additional_content_r").find(".add_study_content_r").click(function(){ //绑定附件的删除事件
			$(this).parent().remove();
		});
	}
	
	//工具栏显示隐藏
	function fadeInOrOut(flag){
		wordObj.OfficeToolbars = flag;
	}
	//保存修改的教案
	window.saveLessonPlanTracks = function(editType){
		$("#editType").val(editType);
		wordObj.WebSave();
		//将返回的教案id赋值
		if(wordObj.CustomSaveResult =='isOver'){
			successAlert("活动已结束，您不可再修改");
		}else if(wordObj.CustomSaveResult =='isSend'){
			successAlert("活动的整理教案已被发送，您不可再整理");
		}else if(wordObj.CustomSaveResult =='zhubeiIsEdit'){
			successAlert("该活动的主备教案已被发起人修改",false,function(){
				parent.location.reload();
			});
		}else{
			if($("#trackId").val() != wordObj.CustomSaveResult){
				window.location.reload();
			}
			$("#trackId").val(wordObj.CustomSaveResult);
		}
	}

	/**
	 * 每隔一段时间请求一次后台，保证session有效
	 */
	window.requestAtInterval = function(timeRange){
		var dingshi = window.setInterval(function(){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        url: _WEB_CONTEXT_+"/jy/toEmptyMethod.json?id="+Math.random(),
		        error: function () {
		        	window.clearInterval(dingshi);
		        },  
		        success:function(data){
		        }  
		    });
		},timeRange);
	}
	
	//更新主备人list
	 function checkMainUserList(){
		var editMainUserId = $("#editmainuserid").val();
			var mid = "?subjectId="+$("#mainUserSubjectId").val() + "&gradeId=" + $("#mainUserGradeId").val();
			$.getJSON("jy/activity/mainUserList"+mid,function(data){
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
			});
		}
	//更新章节list
	 function checkChapterList() {
	 	$("#chapterId").val("");
	 	var subjectId = $("#mainUserSubjectId").val();
	 	var gradeId = $("#mainUserGradeId").val();
	 	if($("#mainUserId").val()!='') {
	 		var mid = "?userId="+$("#mainUserId").val()+"&gradeId="+gradeId+"&subjectId="+subjectId;
	 		$.getJSON("jy/activity/chapterList"+mid,function(data){
	 			var lis = data.lessonInfoList;
	 			if(lis.length > 0){
	 				$("#chapterId").html('<option value="">请选择主备课题</option>');
	 				for (var i = 0; i < lis.length; i++) {
	 					$("#chapterId").append('<option value="'+lis[i].id+'" id="option_'+lis[i].id+'">' +lis[i].lessonName+ '</option>');
	 				}
	 			}else{
	 				$("#chapterId").html('<option value="">请选择主备课题</option>');
	 			}
	 		});
	 	}
	 }
	 
	//保存备课活动
	 window.saveActivity = function(isDraft) {
		var gradeIds = ",";
		$(".range_class").find(".p_act").each(function(){
			gradeIds = gradeIds+$(this).attr("gradeId")+",";
		});
		$("#gradeIds").val(gradeIds==","?"":gradeIds);
		
		if($("#activityName").val()=='') {
			successAlert("请输入活动主题");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#activityName").val().length>80) {
 			successAlert("请输入活动主题少于80个汉字或字符");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#picktime_end").val()!=""){
 			if($("#picktime_start").val()>$("#picktime_end").val()){
 				successAlert("结束时间要大于开始时间！");
 				return false;
 			}
 			if(!compareWithCurrentDate($("#picktime_end").val())){
 				successAlert("结束时间要大于当前时间！");
 				return false;
 			}
 		}
	 	if(isDraft){
	 		var mid = $("#tbjaForm").serialize();	
	 		if(confirm("为保证活动时限的准确性，您下次发布活动的时候在设定开始时间或结束时间吧，本次就不给您记录喽！")){
	 			$.ajax({
	 				type:"post",
	 				dataType:"json",
	 				url:"jy/activity/saveActivityTbja?status=0",
	 				data: mid,
	 				error: function () {
	 					successAlert('请求失败');  
	 		        },
	 				success:function(data){
	 					var rc = data.result;
	 					if(rc == "success"){
	 						successAlert("已存至草稿箱",false,function(){
	 						     location.href = _WEB_CONTEXT_ + "/jy/activity/index";
	 						});
	 					}
	 					else {
	 						successAlert("提交失败，请重试！");
	 						return false;
	 					}
	 				}
	 			});
	 		}
	 	}else{
	 		if($("#chapterId").val()==''){
	 			successAlert("请选择主备教案");
	 			return false;
	 		}
	 		if($("#gradeIds").val()=='') {
	 			successAlert("请选择年级");
	 			return false;
	 		}
	 		if($("#remark").val().length>200) {
	 			successAlert("请输入活动要求少于200个汉字或字符");
	 			$("#remark").focus();
	 				return false;
	 		}
	 		if(confirm("此集体备课将发布给参与人，如果您还没有撰写好，请存草稿，您是否确定发布？")){
	 			$("#tbjaForm").find('input').removeAttr("disabled");
	 			$("#tbjaForm").find('select').removeAttr("disabled");
	 			var mid = $("#tbjaForm").serialize();	
	 			$.ajax({
	 				type:"post",
	 				dataType:"json",
	 				url:"jy/activity/saveActivityTbja?status=1",
	 				data: mid,
	 				error: function () {
	 					successAlert('请求失败');  
	 		        },
	 				success:function(data){
	 					var rc = data.result;
	 					if(rc == "success")
	 						successAlert("发布成功！",false,function(){
	 							location.href = _WEB_CONTEXT_ + "/jy/activity/index";
	 						});
	 					else {
	 						successAlert("发布失败，请重试！");
	 						return false;
	 					}
	 				}
	 			});
	 		}
	 	}
	 }
	 
	//增加备课活动-主题研讨
	 window.saveActivityZtyt = function(isDraft) {
		 var gradeIds = ",";
		$(".range_class").find(".p_act").each(function(){
			gradeIds = gradeIds+$(this).attr("gradeId")+",";
		});
		$("#gradeIds").val(gradeIds==","?"":gradeIds);
		var resIds = "";
		$(".study_additional_content_r").find(".add_study_content").each(function(){
			resIds = resIds+$(this).attr("resId")+",";
		});
		$("#ztytRes").val(resIds.substring(0,resIds.length-1))
		if($("#activityName").val()=='') {
			successAlert("请输入活动主题");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#activityName").val().length>80) {
 			successAlert("请输入活动主题少于80个汉字或字符");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#picktime_end").val()!=""){
 			if($("#picktime_start").val()>$("#picktime_end").val()){
 				successAlert("结束时间要大于开始时间！");
 				return false;
 			}
 			if(!compareWithCurrentDate($("#picktime_end").val())){
 				successAlert("结束时间要大于当前时间！");
 				return false;
 			}
 		}
	 	if(isDraft){
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
	 						successAlert("已存至草稿箱",false,function(){
	 							location.href = _WEB_CONTEXT_ + "/jy/activity/index";
	 						});
	 					}
	 					else {
	 						successAlert("提交失败，请重试！");
	 						return false;
	 					}
	 				}
	 			});
	 		}
	 	}else{
	 		if($("#gradeIds").val()=='') {
	 			successAlert("请选择年级");
	 			return false;
	 		}
	 		if($("#remark").val().length>200) {
	 			successAlert("请输入活动要求少于200个汉字或字符");
	 			$("#remark").focus();
	 				return false;
	 		}
	 		if(confirm("此集体备课将发布给参与人，如果您还没有撰写好，请存草稿，您是否确定发布？")){
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
	 						successAlert("发布成功！",false,function(){
	 							location.href = _WEB_CONTEXT_ + "/jy/activity/index";
	 						});
	 					else {
	 						successAlert("提交失败，请重试！");
	 						return false;
	 					}
	 				}
	 			});
	 		}
	 	}
	 }
	//增加备课活动-视频教研
	 window.saveActivitySpjy = function(isDraft) {
		 var gradeIds = ",";
		$(".range_class").find(".p_act").each(function(){
			gradeIds = gradeIds+$(this).attr("gradeId")+",";
		});
		$("#gradeIds").val(gradeIds==","?"":gradeIds);
		var resIds = "";
		$(".study_additional_content_r").find(".add_study_content").each(function(){
			resIds = resIds+$(this).attr("resId")+",";
		});
		$("#ztytRes").val(resIds.substring(0,resIds.length-1))
		if($("#activityName").val()=='') {
			successAlert("请输入活动主题");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#activityName").val().length>80) {
 			successAlert("请输入活动主题少于80个汉字或字符");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#picktime_end").val()!=""){
 			if($("#picktime_start").val()>$("#picktime_end").val()){
 				successAlert("结束时间要大于开始时间！");
 				return false;
 			}
 			if(!compareWithCurrentDate($("#picktime_end").val())){
 				successAlert("结束时间要大于当前时间！");
 				return false;
 			}
 		}
	 	if(isDraft){
	 		if(confirm("为保证活动时限的准确性，您下次发布活动的时候在设定开始时间或结束时间吧，本次就不给您记录喽！")){
	 			var mid = $("#spjyForm").serialize();
	 			$.ajax({
	 				type:"post",
	 				dataType:"json",
	 				url:"jy/activity/saveActivitySpjy?status=0",
	 				data: mid,
	 				success:function(data){
	 					var rc = data.result;
	 					if(rc == "success"){
	 						successAlert("已存至草稿箱",false,function(){
	 							location.href = _WEB_CONTEXT_ + "/jy/activity/index";
	 						});
	 					}
	 					else {
	 						successAlert("提交失败，请重试！");
	 						return false;
	 					}
	 				}
	 			});
	 		}
	 	}else{
	 		if($("#gradeIds").val()=='') {
	 			successAlert("请选择年级");
	 			return false;
	 		}
	 		if($("#remark").val().length>200) {
	 			successAlert("请输入活动要求少于200个汉字或字符");
	 			$("#remark").focus();
	 				return false;
	 		}
	 		if($("#url").val()==""){
	 			successAlert("请输入视频地址");
	 			$("#url").focus();
	 			return false;
	 		}else if(!isUrl($("#url").val())){
	 			successAlert("视频地址的格式错误");
	 			$("#url").focus();
	 			return false;
	 		}
	 		if(confirm("此集体备课将发布给参与人，如果您还没有撰写好，请存草稿，您是否确定发布？")){
	 			$("#spjyForm").find('input').removeAttr("disabled");
	 			$("#spjyForm").find('select').removeAttr("disabled");
	 			var mid = $("#spjyForm").serialize();
	 			$.ajax({
	 				type:"post",
	 				dataType:"json",
	 				url:"jy/activity/saveActivitySpjy?status=1",
	 				data: mid,
	 				success:function(data){
	 					var rc = data.result;
	 					if(rc == "success")
	 						successAlert("发布成功！",false,function(){
	 							location.href = _WEB_CONTEXT_ + "/jy/activity/index";
	 						});
	 					else {
	 						successAlert("提交失败，请重试！");
	 						return false;
	 					}
	 				}
	 			});
	 		}
	 	}
	 }
	//修改备课活动
	 function editActivity() {
		 var gradeIds = ",";
		$(".range_class").find(".p_act").each(function(){
			gradeIds = gradeIds+$(this).attr("gradeId")+",";
		});
		$("#gradeIds").val(gradeIds==","?"":gradeIds);
	 	
	 	if($("#chapterId").val()==''){
	 		successAlert("请选择主备教案");
	 		return false;
	 	}
	 	if($("#gradeIds").val()=='') {
	 		successAlert("请选择年级");
 			return false;
 		}
	 	if($("#activityName").val()=='') {
	 		successAlert("请输入活动主题");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#activityName").val().length>80) {
 			successAlert("请输入活动主题少于80个汉字或字符");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#remark").val().length>200) {
 			successAlert("请输入活动要求少于200个汉字或字符");
 			$("#remark").focus();
 				return false;
 		}
 		if($("#picktime_end").val()!=""){
 			if($("#picktime_start").val()>$("#picktime_end").val()){
 				successAlert("结束时间要大于开始时间！");
 				return false;
 			}
 			if(!compareWithCurrentDate($("#picktime_end").val())){
 				successAlert("结束时间要大于当前时间！");
 				return false;
 			}
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
	 					successAlert("修改成功！",false,function(){
	 						location.href = _WEB_CONTEXT_ + "/jy/activity/index";
	 					});
	 				}else if(rc == "isHaveDiscuss"){
	 					successAlert("活动已被讨论，只能修改部分数据",false,function(){
	 						window.location.reload();
	 					});
	 				}else if(rc == "isHaveTracks"){
	 					successAlert("活动已被参与，只能修改部分数据",false,function(){
	 						window.location.reload();
	 					});
	 				}else {
	 					successAlert("修改失败，请重试！");
	 					return false;
	 				}
	 			}
	 		});
	 	}
	 	
	 }
	 
	//修改备课活动-主题研讨
	 function editActivityZtyt() {
		 var gradeIds = ",";
		$(".range_class").find(".p_act").each(function(){
			gradeIds = gradeIds+$(this).attr("gradeId")+",";
		});
		$("#gradeIds").val(gradeIds==","?"":gradeIds);
		var resIds = "";
		$(".study_additional_content_r").find(".add_study_content").each(function(){
			resIds = resIds+$(this).attr("resId")+",";
		});
		$("#ztytRes").val(resIds.substring(0,resIds.length-1))
		
		if($("#gradeIds").val()=='') {
			successAlert("请选择年级");
 			return false;
 		}
		if($("#activityName").val()=='') {
			successAlert("请输入活动主题");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#activityName").val().length>80) {
 			successAlert("请输入活动主题少于80个汉字或字符");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#remark").val().length>200) {
 			successAlert("请输入活动要求少于200个汉字或字符");
 			$("#remark").focus();
 				return false;
 		}
 		if($("#picktime_end").val()!=""){
 			if($("#picktime_start").val()>$("#picktime_end").val()){
 				successAlert("结束时间要大于开始时间！");
 				return false;
 			}
 			if(!compareWithCurrentDate($("#picktime_end").val())){
 				successAlert("结束时间要大于当前时间！");
 				return false;
 			}
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
	 					successAlert("修改成功！",false,function(){
	 						location.href = _WEB_CONTEXT_ + "/jy/activity/index";
	 					});
	 				}else if(rc == "isHaveDiscuss"){
	 					successAlert("活动已被讨论，只能修改部分数据",false,function(){
	 						window.location.reload();
	 					});
	 				}else {
	 					successAlert("修改失败，请重试！");
	 					return false;
	 				}
	 			}
	 		});
	 	}
	 	
	 }
	 
	//修改备课活动-视频教研
	 function editActivitySpjy() {
		 var gradeIds = ",";
		$(".range_class").find(".p_act").each(function(){
			gradeIds = gradeIds+$(this).attr("gradeId")+",";
		});
		$("#gradeIds").val(gradeIds==","?"":gradeIds);
		var resIds = "";
		$(".study_additional_content_r").find(".add_study_content").each(function(){
			resIds = resIds+$(this).attr("resId")+",";
		});
		$("#ztytRes").val(resIds.substring(0,resIds.length-1))
		
		if($("#gradeIds").val()=='') {
			successAlert("请选择年级");
 			return false;
 		}
		if($("#activityName").val()=='') {
			successAlert("请输入活动主题");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#activityName").val().length>80) {
 			successAlert("请输入活动主题少于80个汉字或字符");
 			$("#activityName").focus();
 			return false;
 		}
 		if($("#remark").val().length>200) {
 			successAlert("请输入活动要求少于200个汉字或字符");
 			$("#remark").focus();
 				return false;
 		}
 		if($("#url").val()==""){
 			successAlert("请输入视频地址");
 			$("#url").focus();
 			return false;
 		}else if(!isUrl($("#url").val())){
 			successAlert("视频地址的格式错误");
 			$("#url").focus();
 			return false;
 		}
 		if($("#picktime_end").val()!=""){
 			if($("#picktime_start").val()>$("#picktime_end").val()){
 				successAlert("结束时间要大于开始时间！");
 				return false;
 			}
 			if(!compareWithCurrentDate($("#picktime_end").val())){
 				successAlert("结束时间要大于当前时间！");
 				return false;
 			}
 		}
	 	if(confirm("您确定修改吗？")){
	 		$("#ztytForm").find('input').removeAttr("disabled");
	 		$("#ztytForm").find('select').removeAttr("disabled");
	 		var mid = $("#ztytForm").serialize();
	 		$.ajax({
	 			type:"post",
	 			dataType:"json",
	 			url:"jy/activity/saveActivitySpjy?status=1",
	 			data: mid,
	 			success:function(data){
	 				var rc = data.result;
	 				if(rc == "success"){
	 					successAlert("修改成功！",false,function(){
	 						location.href = _WEB_CONTEXT_ + "/jy/activity/index";
	 					});
	 				}else if(rc == "isHaveDiscuss"){
	 					successAlert("活动已被讨论，只能修改部分数据",false,function(){
	 						window.location.reload();
	 					});
	 				}else {
	 					successAlert("修改失败，请重试！");
	 					return false;
	 				}
	 			}
	 		});
	 	}
	 	
	 }
	 //主研上传附件回调
	 $('.submit_btn').hide();
	 window.afterUpload = function(obj){
		 var htmlStr = '<div class="add_study_content" resId="'+obj.data+'" >'+
						'<div class="add_study_content_l"></div>'+
						'<div class="add_study_content_c">'+
						'<span>'+cutStr(obj.filename,36,true)+'</span>'+
						'<div class="complete">上传完成</div></div>'+
						'<input type="button" class="add_study_content_r" value="删除"/></div>';
		 var dom = $(htmlStr);
		 dom.find(".add_study_content_r").click(function(){
			 $(this).parent().remove();
		 });
		 $(".study_additional_content_r").append(dom);
		 $('.release').show();
		 $('.deposit_draft').show();
		 $('.modify').show();
		 $('.no-modify').show();
		 $('.submit_btn').hide();
	 }
	
	 window.beforeUpload = function(){
		 $('.release').hide();
		 $('.deposit_draft').hide();
		 $('.modify').hide();
		 $('.no-modify').hide();
		 $('.submit_btn').show();
		 if($(".study_additional_content_r").find(".add_study_content").length==6){
			 successAlert("最多允许上传6个附件");
			 return false;
		 }
		 return true;
	 }
	 
})