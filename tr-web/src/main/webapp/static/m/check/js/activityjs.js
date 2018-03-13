define(["require","zepto","iscroll"], function (require) {
	var $= Zepto; 
	 $(function(){
			init();
			bindEvent();
		});
	function init() {
		if($("#zt_head_wrap").length>0){ 
			var zt_head_wrap = new IScroll('#zt_head_wrap',{
	     		 scrollbars:true,
	     		 mouseWheel:true,
	     		 fadeScrollbars:true,
	     		
	     	});
		}
		$('.content_list figure').each(function(){
    		var that=this;
    		$(this).click(function(){
    			$(this).addClass("figure_bg").siblings().removeClass("figure_bg");
    			var index=$(that).index();  
    			if($('.figure_wrap .wrap1').eq(index).css("display") == "block"){ 
    				$(".figure_wrap .wrap1").eq(index).hide();
    	          	$("#iframe1").css({"width":"100%"}); 
    			}else{ 
    				$(".figure_wrap .wrap1").eq(index).show().siblings().hide(); 
    	          	$("#iframe1").css({'width':'50%','-webkit-transition':'width .3s',"-webkit-animation-fill-mode":"forwards"});
    	          	var act_info_content = new IScroll('.act_info_content',{
    		      		 scrollbars:true,
    		      		 mouseWheel:true,
    		      		 fadeScrollbars:true,
    		      	});
    			}
    			if(index == 1){
    				$("#iframe2").attr("src",$("#iframe2").attr("src"));
    			}else if(index == 2){
    				if($("#iframe3").length>0){
    					$("#iframe3").attr("src",$("#iframe3").attr("src"));
    				}
    				if($("#act_modify").length>0){
    					var act_modify = new IScroll('#act_modify',{
    			      		 scrollbars:true,
    			      		 mouseWheel:true,
    			      		 fadeScrollbars:true,
    			      		 click:true,
    			      	});
    				}
    			}else if(index == 3){
    				var activityId = $(this).attr("activityId");
    				var canReply = $(this).attr("canReply");
    				var discussUrl = _WEB_CONTEXT_+"/jy/comment/discussIndexTB?activityId="+activityId+"&typeId=5&canReply="+canReply+"&"+ Math.random();
    				$("#iframe_discuss").attr("src",discussUrl); 
					var partake_discussion = new IScroll('#partake_discussion',{
			      		 scrollbars:true,
			      		 mouseWheel:true,
			      		 fadeScrollbars:true, 
			      		 click:true,
			      	});
    			}else if(index == 4){
    				if($("#iframe_checklist").attr("src")==""){
    	    			var title = $("#checklistobj").attr("title");
    	    			var resType = $("#checklistobj").attr("resType");
    	    			var authorId = $("#checklistobj").attr("authorId");
    	    			var resId = $("#checklistobj").attr("resId");
    	    			if($(this).attr("data-option")==1){
    	    				$("#iframe_checklist").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndex?flags=false&title="+title+"&resType="+resType+"&authorId="+authorId+"&resId="+resId);
    	    			}else{
    	    				$("#iframe_checklist").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndexMjb?flags=true&title="+title+"&resType="+resType+"&authorId="+authorId+"&resId="+resId);
    	    			}
    	    		}
    			}
    		})
    	});
		
		$(".canyutaolun").click(function(){
			var activityId = $(this).attr("activityId");
			var canReply = $(this).attr("canReply");
			var discussUrl = _WEB_CONTEXT_+"/jy/comment/discussIndexTB?activityId="+activityId+"&typeId=5&canReply="+canReply+"&"+ Math.random();
			$("#iframe_discuss").attr("src",discussUrl); 
			var partake_discussion = new IScroll('#partake_discussion',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true, 
	      		 click:true,
	      	});
		});
		
		$(".publish1_btn").click(function(){
			$("#discussLevel").val(1);
			$("#parentId").val(0);
			saveTBDiscuss("publish1");
		});
		$('.head').click(function (){
			var activityId = $(this).attr('activityId');
			var userId = $(this).attr('userId');
			var canReply = $(this).attr('canReply');
			$("#discuss_iframe").attr("src",_WEB_CONTEXT_+"/jy/commment/discussIndex?activityId="+activityId+"&userId="+userId+"&canReply="+canReply+"&typeId=5");
		});
		$('.range_class p').click(function (){
		    if($(this).hasClass('class_act')){
		    	$(this).removeClass('class_act');
			}else{
				$(this).addClass('class_act');
			}
		});
		$('.act_participants_content ul li').click(function (){
			$( this ).addClass("hour_act").siblings().removeClass("hour_act");
		});
		
		$('.par_head div').click(function (){
			$('.par_head').hide();
			$('#userPhoto').html($(this).html());
			$(".head_independent_r").html($(this).attr("userName")+"的全部留言");
			$('.par_head_1').show();
			var activityId = $(this).attr("activityId");
			var userId = $(this).attr("userId");
			var canReply = $(this).attr("canReply");
			var discussUrl = _WEB_CONTEXT_+"/jy/comment/discussIndexTB?crtId="+userId+"&activityId="+activityId+"&typeId=5&canReply="+canReply+"&time="+ Math.random();
			$("#iframe_discuss").attr("src",discussUrl); 
			var partake_discussion = new IScroll('#partake_discussion',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true, 
	      		 click:true,
	      	});
		});
		$('.par_head_float>div div').click(function (){
			$('.par_head_float').hide();
			$('.par_head').hide();
			if($('.par_head_r').html()=='收起'){ 
				$('.par_head_r').html('更多'); 
			}
			$('#userPhoto').html($(this).html());
			$(".head_independent_r").html($(this).attr("userName")+"的全部留言");
			$('.par_head_1').show();
			var activityId = $(this).attr("activityId");
			var userId = $(this).attr("userId");
			var canReply = $(this).attr("canReply");
			var discussUrl = _WEB_CONTEXT_+"/jy/comment/discussIndexTB?crtId="+userId+"&typeId=5&activityId="+activityId+"&canReply="+canReply+"&time="+ Math.random();
			$("#iframe_discuss").attr("src",discussUrl); 
			var partake_discussion = new IScroll('#partake_discussion',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true, 
	      		 click:true,
	      	});
		});
		$('.par_head_r1').click(function (){
			$('.par_head_1').hide();
			$('.par_head').show();
			var activityId = $(this).attr("activityId");
			var canReply = $(this).attr("canReply");
			var discussUrl = _WEB_CONTEXT_+"/jy/comment/discussIndexTB?&activityId="+activityId+"&typeId=5&canReply="+canReply+"&time="+ Math.random();
			$("#iframe_discuss").attr("src",discussUrl); 
			var partake_discussion = new IScroll('#partake_discussion',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true, 
	      		 click:true,
	      	});
			
		});
		$('.par_head_r').click(function (){
			if($(this).html()=='收起'){ 
				$(this).html('更多');
				$('.par_head_float').hide(); 
			}else if($(this).html()=='更多'){
				$(this).html('收起');
				$('.par_head_float').show(); 
			}
			var par_head_float = new IScroll('.par_head_float',{ 
				 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      	});
		});
		$('.close').click(function (){
			$('.act_draft_wrap').hide();
			$('.act_participants_wrap').hide(); 
			$('.act_info_wrap').hide();
			$('.act_modify_wrap').hide();
			$('.look_opinion_list_wrap').hide();
			$('.partake_discussion_vrap').hide();
			$('.look_op_l_wrap').hide();
          	$('.mask').hide(); 
          	$("#iframe1").css({"width":"100%"});
          	
		});
		$(".act_info_close").click(function (){ 
			window.location.reload();//刷新当前页面.
		});
		$('.content_list div').click(function (){
			$(this).addClass("figure_bg").siblings().removeClass("figure_bg");
		});
		$('.cy_list1').click(function (){
			$('.mask').show();
			$('.partake_discussion_vrap').show();
			$('.partake_discussion_vrap').css({"z-index":"102","width":"100%","height":"100%","position":"fixed"}); 
			var zt_head_wrap = new IScroll('#zt_head_wrap',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		
	      	});
		})
		$('.ck_list1').click(function (){
			$('.content_bottom_center_zt').hide();
			if($("#iframe_checklist").attr("src")==""){
    			var title = $("#checklistobj").attr("title");
    			var resType = $("#checklistobj").attr("resType");
    			var authorId = $("#checklistobj").attr("authorId");
    			var resId = $("#checklistobj").attr("resId");
    			$("#iframe_checklist").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndexMjb?flags=true&title="+title+"&resType="+resType+"&authorId="+authorId+"&resId="+resId);
    		}
			$('.content_bottom_center_ck').show();
		});
		$('.show_btn_left').click(function (){
			$('.class_hour_wrap').slideUp();
			$('.show_btn').show();
		});
		$('.show_btn').click(function (){
			$('.class_hour_wrap').slideDown();
			$('.show_btn').hide();
		});
		$('.xx_list1').click(function (){
			$('.act_info_wrap').show();
			$('.mask').show();
			var act_info_left = new IScroll('.act_info_left',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		
	      	});
		});
		$('.ce_list1').click(function (){
			if($("#iframe_checklist").attr("src")==""){
    			var title = $("#checklistobj").attr("title");
    			var resType = $("#checklistobj").attr("resType");
    			var authorId = $("#checklistobj").attr("authorId");
    			var resId = $("#checklistobj").attr("resId");
    			if($(this).attr("data-option")==1){
    				$("#iframe_checklist").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndex?flags=false&title="+title+"&resType="+resType+"&authorId="+authorId+"&resId="+resId);
    			}else{
    				$("#iframe_checklist").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndexMjb?flags=true&title="+title+"&resType="+resType+"&authorId="+authorId+"&resId="+resId);
    			}
    		}
			$('.look_op_l_wrap').show();
			$('.mask').show();  
		});
	}
	
	function bindEvent(){
		$(".content_bottom1_left").find("li").click(function(){
			var hourId = $(this).attr("hourId");
			var activityId = $(this).attr("activityId");
			showLessonPlanTrack_chakan(hourId,activityId);
			$(this).attr("class","ul_li_act");
			$(this).siblings("li").removeClass("ul_li_act");
		});
		
		$(".act_participants_content").find("li").click(function(){ //绑定修改列表的课时
			var zhubeiId = $(this).attr("zhubeiId");
			var activityId = $(this).attr("activityId");
			showTrackList(zhubeiId,activityId);
		});
		
		$(".enclosure_content").each(function(){ //绑定参与主题研讨的附件查看
			var resId = $(this).attr("resId");
			$(this).find("li").eq(0).click(function(){
				scanResFile_m(resId);
			});
		});
		
		$(".act_modify_btn").find("#fasong").click(function(){ //绑定发送教案按钮
			sendToJoiners($(this).attr("activityId"));
		});
		$(".act_modify_btn").find("#jieshoujiaoan").click(function(){ //绑定发送教案按钮
			receiveLessonPlan($(this).attr("activityId"));
		});
		$("#overIt").click(function(){ //绑定结束活动按钮
			overActivity($(this).attr("activityId"));
		});
		$("#overZtyt").click(function(){  //绑定结束主题研讨按钮
			overActivityZtyt($(this).attr("activityId"));
		});
		
		$(".act_modify_content1").find(".hour").click(function(){ //绑定参与页的整理教案的查看
			scanLessonPlanTrack($(this).attr("resId"));
		});
	}
	
//	window.showLessonPlanTrack = function(planId,activityId){
//		if(window.frames["iframe1"].wordObj.IsDirty){
//			if(window.confirm("您修改的教案还未保存，是否保存当前修改的教案内容？")){
//				//wantToEdit1();
//				//saveEdit();
//			}
//		}
//		$("#iframe1").attr("src",_WEB_CONTEXT_+"/jy/activity/showLessonPlanTracks?site_preference=mobile&editType=0&planId="+planId+"&activityId="+activityId);
//	}
	
	window.showLessonPlanTrack_chakan = function(planId,activityId){
		$("#iframe1").attr("src",_WEB_CONTEXT_+"/jy/activity/showLessonPlan?planId="+planId);
	};
	
	//显示主备教案的意见教案集合
	function showTrackList(planId,activityId){
		$("#iframe2").attr("src",_WEB_CONTEXT_+"/jy/activity/getYijianTrackList?site_preference=mobile&planId="+planId+"&activityId="+activityId);
	}
	
	//发送给参与人
	function sendToJoiners(activityId){
		if(confirm("发送后，所有集备参与人将收到您发送的集备教案，您需要将该课题下的所有教案都整理好后在发送。如果还未全部整理好，请继续整理。您确定发送吗？")){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{id:activityId},
		        url:   _WEB_CONTEXT_+"/jy/activity/sendToJoiner.json?site_preference=mobile",
		        error: function () {
		        	successAlert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	if(data.result=="success"){
		        		successAlert("发送成功！",false,function(){
							window.location.href = _WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?site_preference=mobile&id="+activityId;
						});
		        	}else{
		        		successAlert("系统出错");
		        	}
		        }  
		    });
		}
	}
	
	//接收教案
	function receiveLessonPlan(activityId){
		if(window.confirm("该操作会使您相应课题下的所有教案被覆盖，确定要接收吗？")){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{'activityId':activityId},
		        url:   _WEB_CONTEXT_+"/jy/lessonplan/receiveLessonPlanOfActivity.json?site_preference=mobile",
		        error: function () {
		        	successAlert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	successAlert(data.info);
		        	if(data.result=="fail3"){
		        		window.location.href = _WEB_CONTEXT_+"/jy/activity/index?site_preference=mobile";
		        	}else{
		        		window.location.reload();
		        	}
		        }  
		    });
		}
	}
	
	//结束活动
	function overActivity(activityId){
		if(window.confirm("您确定要结束吗？结束后，所有人将不能参与此集备活动！")){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{id:activityId},
		        url:   _WEB_CONTEXT_+"/jy/activity/overActivity.json?site_preference=mobile",
		        error: function () {
		        	successAlert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	if(data.result=="success"){
		        		window.location.href = window.location.href;
		        	}else{
		        		successAlert("系统出错");
		        	}
		        }  
		    });
		}
	}
	
	//结束活动
	function overActivityZtyt(activityId){
		if(window.confirm("您确定要结束吗？结束后，所有人将不能参与此集备活动！")){
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{id:activityId},
		        url:   _WEB_CONTEXT_+"/jy/activity/overActivity.json?site_preference=mobile",
		        error: function () {
		        	successAlert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	if(data.result=="success"){
		        		window.location.href = _WEB_CONTEXT_+"/jy/activity/viewZtytActivity?site_preference=mobile&id="+activityId;
		        	}else{
		        		successAlert("系统出错");
		        	}
		        }  
		    });
		}
	}
	
	function scanLessonPlanTrack(resId){
		window.open(_WEB_CONTEXT_+"/jy/activity/scanLessonPlanTrack?resId="+resId,"_self");
	}
	
	function saveTBDiscuss(clssname){
		var content = $.trim($("."+clssname).val());
		if(content!="" && content.length>0){
			if(content.length<=300){
				$("#content_hidden").val(content);
				if(confirm("您确定要发送讨论意见吗？")){
					$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/comment/discussSave",
						data:$("#jbdiscussform").serializeArray(),
						success:function(data){
							$("."+clssname).val("");
							if(data.msg=="ok"){
								successAlert("发送讨论成功！",false,function(){
									var activityId = $("#activityId").val();
									var discussUrl = _WEB_CONTEXT_+"/jy/comment/discussIndexTB?activityId="+activityId+"&typeId=5&canReply=true&"+ Math.random();
									$("#iframe_discuss").attr("src",discussUrl); 
									var partake_discussion = new IScroll('#partake_discussion',{
							      		 scrollbars:true,
							      		 mouseWheel:true,
							      		 fadeScrollbars:true, 
							      		 click:true,
							      	});
								});
							}else{
								successAlert(data.msg);
							}
						}
					});
				}
			}else{
				successAlert("讨论信息不能超过300字！");
			}
		}else{
			successAlert("请您填写讨论信息！");
		}
	}
});