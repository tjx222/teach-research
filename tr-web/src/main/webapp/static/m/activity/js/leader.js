define(["require","zepto","iscroll",'common/dateFormat'], function (require) {
	var dateFormat = require("common/dateFormat"); 
	var jq= Zepto;  
	var myScroll;
	 jq(function(){
			bindEvent();
			init();
		});
	function init() { 
		if(jq("#wrap").length>0){
		    myScroll = new IScroll('#wrap',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		 click:true,
	      	});
		}
		if(jq("#wrap_draft").length>0){
			var wrap_draft = new IScroll('#wrap_draft',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		 click:true,
	      	});
		}
		jq('.range_class p').click(function (){
		    if(jq(this).hasClass('class_act')){
		    	jq(this).removeClass('class_act');
			}else{
				jq(this).addClass('class_act');
			}
		});
		jq('.act_participants_content ul li').click(function (){
			jq( this ).addClass("hour_act").siblings().removeClass("hour_act");
		})
		jq('.content_bottom1_left_1').click(function (){
			jq('.act_info_wrap').show();
			jq('.mask').show();  
		});
		jq('.draft').click(function (){
			toActivityDraft();
		});
		jq('.close').click(function (){
			jq('.act_draft_wrap').hide();
			jq('.act_participants_wrap').hide(); 
			jq('.act_info_wrap').hide();
			jq('.act_modify_wrap').hide();
			jq('.view_comments_wrap').hide();
			jq('.partake_discussion_vrap').hide();
          	jq('.mask').hide(); 
		}); 
		jq('.activity_option_close').click(function (){
			$('.activity_option').hide();
		});
		jq('.btn_launch').click(function (){
			jq('.fq_option_wrap').show();
          	jq('.mask').show(); 
		});
		jq('.fq_option_wrap').click(function(){
			jq('.fq_option_wrap').hide();
			jq('.mask').hide();
		});
		jq('.fq_option div').click(function (){
			jq('.fq_option_wrap').hide();
          	jq('.mask').hide(); 
		});
		jq('.cy_list1').click(function (){
			jq('.content_bottom_center_ck').hide();
			jq('.content_bottom_center_zt').show();
		})
		jq('.ck_list1').click(function (){
			jq('.content_bottom_center_zt').hide();
			jq('.content_bottom_center_ck').show();
		});
		jq('.class_hour ul li').click(function (){ 
			jq(this).children().addClass("class_hour_act1").parent().siblings().children().removeClass("class_hour_act1");
		});
		jq('.show_btn_left').click(function (){
			jq('.class_hour_wrap').slideUp();
			jq('.show_btn').show();
		});
		jq('.show_btn').click(function (){
			jq('.class_hour_wrap').slideDown();
			jq('.show_btn').hide();
		});
	}
	
	function bindEvent(){
//		jq("#wrapper").find("li").click(function(){
//			if(jq(this).find("a").attr("class")=="header_act"){
//				return false;
//			}else{
//				var listType = jq(this).attr("listType");
//				window.location.href = _WEB_CONTEXT_+"/jy/activity/index?listType="+listType;
//			}
//		});
		
		jq(".fq_option_tb").click(function(e){ //绑定同备教案
			window.location.href = _WEB_CONTEXT_ + "/jy/activity/toEditActivityTbja";
			//e.preventDefault(); // 阻止“默认行为”
		});
		jq(".fq_option_zt").click(function(e){ //绑定主题研讨
			window.location.href = _WEB_CONTEXT_ + "/jy/activity/toEditActivityZtyt";
			e.preventDefault(); // 阻止“默认行为”
		});
		jq(".fq_option_sp").click(function(e){ //绑定视频教研
			window.location.href = _WEB_CONTEXT_ + "/jy/activity/toEditActivitySpjy";
			e.preventDefault(); // 阻止“默认行为”
		});
		jq(".draft_list_right").find(".edit").click(function(){ //绑定草稿箱的修改按钮
			var activityId = jq(this).attr("activityId");
			var typeId = jq(this).attr("typeId");
			editActivity(activityId,typeId);
		});
		jq(".draft_list_right").find(".del").click(function(){ //绑定草稿箱的删除按钮
			var activityId = jq(this).attr("activityId");
			delActivityDraft(activityId);
		});
		jq("#listdiv_0").find(".activity_gl_right").click(function(e){ //为已发布列表绑定整理事件
			var activityId = jq(this).attr("activityId");
			var typeId = jq(this).attr("typeId");
			var startDate = jq(this).attr("startDate");
			zhengli(activityId,typeId,startDate);
			
		});
		jq("#listdiv_1").find(".activity_tch").click(function(){ //为可参与列表绑定参与事件
			var activityId = jq(this).attr("activityId");
			var typeId = jq(this).attr("typeId");
			var isOver = jq(this).attr("isOver");
			var startDate = jq(this).attr("startDate");
			canyu_chakan(activityId,typeId,isOver,startDate);
		});
		jq(".activity_gl_btn").find(".gl_btn").click(function(){ //绑定管理按钮
			jq(this).parent().find('.activity_option').show();
		});
		jq(".activity_option").each(function(){ //绑定管理按钮
			var activityId = jq(this).attr("activityId");
			var typeId = jq(this).attr("typeId");
			jq(this).find(".activity_option_edit").click(function(){
				editActivity(activityId,typeId);
			});
			jq(this).find(".activity_option_del").click(function(){
				delActivity(activityId);
			});
			
			jq(this).find("div").eq(3).click(function(){
				fenxiang_quxiao(activityId,jq(this).attr("class"));
			});
			jq(this).find("div").eq(2).click(function(){
				tijiao_quxiao(activityId,jq(this).attr("class"));
			});
		});
	}
	
//	//瀑布流加载下一页资源
//	window.addData = function(data){
//		var activityList = data.activityList.datalist;
//		for(var i=0;i<activityList.length;i++){
//			var activity = activityList[i];
//			var startTime = activity.startTime;
//			var endTime = activity.endTime;
//			if(startTime!=null && startTime!=""){
//				startTime = startTime.substring(5,16);
//			}else{
//				startTime = "~";
//			}
//			if(endTime!=null && endTime!=""){
//				endTime = endTime.substring(5,16);
//			}else{
//				endTime = "~";
//			}
//			var htmlStr = "";
//			htmlStr = htmlStr+'<div class="activity_tch">';
//			if(activity.typeId == 1){
//				htmlStr = htmlStr+'<div class="activity_tch_left">同<br />备<br />教<br />案</div>';
//			}else if(activity.typeId == 2){
//				htmlStr = htmlStr+'<div class="activity_tch_left1">主<br />题<br />研<br />讨</div>';
//			}
//			htmlStr = htmlStr + '<div class="activity_gl_right" activityId="'+activity.id+'" typeId="'+activity.typeId+'" isOver="'+activity.isOver+'" startDate="'+activity.startTime+'">'+
//								'<h3><span class="title">'+activity.activityName+'</span>';
//			if(activity.isOver){
//				htmlStr = htmlStr + '<span class="end"></span>';
//			}
//			htmlStr = htmlStr +'</h3><div class="option">'+
//								'<div class="partake_sub"><strong></strong>参与学科：<span>'+activity.subjectName+'</span></div>'+
//								'<div class="partake_class"><strong></strong>参与年级：<span>'+activity.gradeName+'</span></div></div><div class="option"><div class="time"><strong></strong>'+
//								'<span>'+startTime+'至'+endTime+'</span></div>'+
//								'<div class="discussion_number"><strong></strong>讨论数：<span>'+activity.commentsNum+'</span></div></div></div>'+
//								'<div class="activity_gl_btn">'+
//								'<input type="button" class="gl_btn" value="管理">'+
//								'<div class="activity_option" activityId="'+activity.id+'" typeId="'+activity.typeId+'">'+
//									'<div class="activity_option_edit"></div>'+
//									'<div class="activity_option_del"></div>';
//			if(!activity.isOver){
//				htmlStr = htmlStr +'<div class="cw_option_jz_sb"></div>';
//			}else if(activity.isOver && !activity.isSubmit){
//				htmlStr = htmlStr +'<div class="activity_option_submit"></div>';
//			}else if(activity.isSubmit && !activity.isAudit){
//				htmlStr = htmlStr +'<div class="cw_option_qx_submit"></div>';
//			}else if(activity.isSubmit && activity.isAudit){
//				htmlStr = htmlStr +'<div class="cw_option_jz_submit"></div>';
//			}
//			if(!activity.isShare){
//				htmlStr = htmlStr + '<div class="activity_option_share"></div>';
//			}else if(activity.isShare && !activity.isComment){
//				htmlStr = htmlStr + '<div class="cw_option_qx_share"></div>';
//			}else if(activity.isShare && activity.isComment){
//				htmlStr = htmlStr + '<div class="cw_option_jz_share"></div>';
//			}
//			htmlStr = htmlStr + '<div class="activity_option_close"></div></div></div></div>';
//			var addDom = jq(htmlStr);
//			addDom.find(".activity_gl_right").click(function(){
//				zhengli(jq(this).attr("activityId"),jq(this).attr("typeId"),jq(this).attr("startDate"));
//			});
//			addDom.find(".activity_gl_btn").find(".gl_btn").click(function(){ //绑定管理按钮
//				jq(this).parent().find('.activity_option').show();
//			});
//			addDom.find(".activity_option").each(function(){ //绑定管理按钮
//				var activityId = jq(this).attr("activityId");
//				var typeId = jq(this).attr("typeId");
//				jq(this).find("div").eq(0).click(function(){
//					editActivity(activityId,typeId);
//				});
//				jq(this).find("div").eq(1).click(function(){
//					delActivity(activityId);
//				});
//				jq(this).find("div").eq(3).click(function(){
//					fenxiang_quxiao(activityId,jq(this).attr("class"));
//				});
//				jq(this).find("div").eq(2).click(function(){
//					tijiao_quxiao(activityId,jq(this).attr("class"));
//				});
//			});
//			jq("#listdiv_0").append(addDom);
//			myScroll.refresh();
//		}
//	}
	
	window.addData = function(data){
		var content = jq("#listdiv_0",data);
		content.find(".activity_gl_right").click(function(){
			var activityId = jq(this).attr("activityId");
			var typeId = jq(this).attr("typeId");
			var startDate = jq(this).attr("startDate");
			zhengli(activityId,typeId,startDate);
		});
		content.find(".gl_btn").click(function(){ //绑定管理按钮
			jq(this).parent().find('.activity_option').show();
		});
		content.find('.activity_option_close').click(function (){
			$('.activity_option').hide();
		});
		content.find(".activity_option").each(function(){ //绑定管理按钮
			var activityId = jq(this).attr("activityId");
			var typeId = jq(this).attr("typeId");
			jq(this).find(".activity_option_edit").click(function(){
				editActivity(activityId,typeId);
			});
			jq(this).find(".activity_option_del").click(function(){
				delActivity(activityId);
			});
			
			jq(this).find("div").eq(3).click(function(){
				fenxiang_quxiao(activityId,jq(this).attr("class"));
			});
			jq(this).find("div").eq(2).click(function(){
				tijiao_quxiao(activityId,jq(this).attr("class"));
			});
		});
		jq("#listdiv_0").append(content.find(".activity_tch"));
		myScroll.refresh();
	}
	
	
	//瀑布流加载下一页资源
	window.addData1 = function(data){
		var activityList = data.activityList.datalist;
		for(var i=0;i<activityList.length;i++){
			var activity = activityList[i];
			var startTime = activity.startTime;
			var endTime = activity.endTime;
			if(startTime!=null && startTime!=""){
				startTime = startTime.substring(5,16);
			}else{
				startTime = "~";
			}
			if(endTime!=null && endTime!=""){
				endTime = endTime.substring(5,16);
			}else{
				endTime = "~";
			}
			var htmlStr = "";
			htmlStr = htmlStr+'<div class="activity_tch" activityId="'+activity.id+'" typeId="'+activity.typeId+'" isOver="'+activity.isOver+'" startDate="'+activity.startTime+'">';
			if(activity.typeId == 1){
				htmlStr = htmlStr+'<div class="activity_tch_left">同<br />备<br />教<br />案</div>';
			}else if(activity.typeId == 2){
				htmlStr = htmlStr+'<div class="activity_tch_left1">主<br />题<br />研<br />讨</div>';
			}
			htmlStr = htmlStr + '<div class="activity_ck_right">'+
								'<h3><span class="title">'+activity.activityName+'</span>';
			if(activity.isOver){
				htmlStr = htmlStr + '<span class="end"></span>';
			}
			htmlStr = htmlStr +'</h3><div class="option">'+
								'<div class="promoter"><strong></strong>发起人：<span>'+activity.organizeUserName+'</span></div>'+
								'<div class="partake_sub"><strong></strong>参与学科：<span>'+activity.subjectName+'</span></div>'+
								'<div class="partake_class"><strong></strong>参与年级：<span>'+activity.gradeName+'</span></div></div><div class="option"><div class="time"><strong></strong>'+
								'<span>'+startTime+'至'+endTime+'</span></div>'+
								'<div class="discussion_number"><strong></strong>讨论数：<span>'+activity.commentsNum+'</span></div></div></div></div>';
			var addDom = jq(htmlStr);
			addDom.click(function(){
				canyu_chakan(jq(this).attr("activityId"),jq(this).attr("typeId"),jq(this).attr("isOver"),jq(this).attr("startDate"));
			});
			jq("#listdiv_1").append(addDom);
			myScroll.refresh();
		}
	}
	
	window.canyu_chakan = function(activityId,typeId,isOver,startDateStr){
		if(typeId==1){//同备教案
			if(isOver=='true'){//已结束，则查看
				window.open(_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId,"_self");
			}else if(isOver=='false'){//参与
				if(ifActivityStart(dateFormat.format(new Date(),"yyyy-MM-dd hh:ff:ss"),startDateStr)){
					window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"_self");
				}
			}
		}else if(typeId==2){//主题研讨
			if(isOver=='true'){//已结束，则查看
				window.open(_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId,"_self");
			}else if(isOver=='false'){//参与
				if(ifActivityStart(dateFormat.format(new Date(),"yyyy-MM-dd hh:ff:ss"),startDateStr)){
					window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_self");
				}
			}
		}else if(typeId==3){//视频教研
			if(isOver=='true'){//已结束，则查看
				window.open(_WEB_CONTEXT_+"/jy/activity/viewSpjyActivity?id="+activityId,"_self");
			}else if(isOver=='false'){//参与
				if(ifActivityStart(dateFormat.format(new Date(),"yyyy-MM-dd hh:ff:ss"),startDateStr)){
					window.open(_WEB_CONTEXT_+"/jy/activity/joinSpjyActivity?id="+activityId,"_self");
				}
			}
		}
	}
	//整理
	function zhengli(activityId,typeId,startDateStr){
		if(ifActivityStart(dateFormat.format(new Date(),"yyyy-MM-dd hh:ff:ss"),startDateStr)){
			if(typeId==1){//同备教案
				window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"_self");
			}else if(typeId==2){//主题研讨
				window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_self");
			}else if(typeId==3){//视频教研
				window.open(_WEB_CONTEXT_+"/jy/activity/joinSpjyActivity?id="+activityId,"_self");
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

			var allStartDate = new Date(arrStartDate[0], arrStartDate[1], arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]); 
			var allcurrentDate = new Date(arrStartDate1[0], arrStartDate1[1], arrStartDate1[2], arrStartTime1[0], arrStartTime1[1], arrStartTime1[2]); 
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
	
	//进入草稿箱
	function toActivityDraft() {
		var draftNum = jq("#draftNumId").val();
		if(draftNum<=0){
			alert("您的草稿箱还没有任何内容哟！");
			return false;
		}
	 	jq("#activity_iframe").attr("src",_WEB_CONTEXT_ + "/jy/activity/indexDraft?ran="+ Math.random());
	 	jq('.act_draft_wrap').show();
      	jq('.mask').show(); 
	 }
	 //修改活动
	 function editActivity(id,type_id) {
	 	if(id=='' || type_id==''){
	 		return false;
	 	}
	 	if(type_id==1){
	 		parent.location.href = _WEB_CONTEXT_ + "/jy/activity/toEditActivityTbja?id="+id;
	 	}else if(type_id==2){
	 		parent.location.href = _WEB_CONTEXT_ + "/jy/activity/toEditActivityZtyt?id="+id;
	 	}else if(type_id==3){
	 		parent.location.href = _WEB_CONTEXT_ + "/jy/activity/toEditActivitySpjy?id="+id;
	 	}
	 }
	 
	 //删除草稿箱
	  function delActivityDraft(id) {
	  	if(id=='')
	  		return false;
	  	if(confirm("您确认要删除该草稿吗？"))
	  		location.href = _WEB_CONTEXT_ + "/jy/activity/delActivityDraft?id="+id;
	  }
	 
	  //删除活动
	  function delActivity(id) {
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
	  	        	successAlert('操作失败，请稍后重试');  
	  	        },  
	  	        success:function(data){
	  	        	if(data.result=="success"){
	  	        		successAlert("删除成功！",false,function(){
	  	        			window.location.href=window.location.href;
	  	        		});
	  	        	}else if(data.result=="fail1"){
	  	        		successAlert("该活动已被参与讨论，不可删除！",false,function(){
	  	        			window.location.href=window.location.href;
	  	        		});
	  	        	}else if(data.result=="fail2"){
	  	        		successAlert("该活动已提交，不可删除！",false,function(){
	  	        			window.location.href=window.location.href;
	  	        		});
	  	        	}else if(data.result=="fail3"){
	  	        		successAlert("系统出错，操作失败");
	  	        	}
	  	        }  
	  	    });
	   	}
	   }
	  
	//提交或取消提交
	 function tijiao_quxiao(activityId,theClass){
	  	if(theClass=='activity_option_submit'){
	  		if(confirm("您确认要提交吗？")){
	  			$.ajax({  
	  		        async : false,  
	  		        type: 'POST',  
	  		        dataType : "json",  
	  		        data:{id:activityId},
	  		        url:   _WEB_CONTEXT_+"/jy/activity/submitActivity.json",
	  		        error: function () {
	  		        	successAlert('操作失败，请稍后重试');  
	  		        },  
	  		        success:function(data){
	  		        	if(data.result=="success"){
	  		        		successAlert("恭喜您提交成功，管理者可以进行查阅啦！",false,function(){
	  		        			window.location.reload();
	  		        		});
	  		        	}else{
	  		        		successAlert("系统出错");
	  		        	}
	  		        }  
	  		    });
	  		}
	  	}else if(theClass=='cw_option_qx_submit'){
	  		if(confirm("您确认要取消提交吗？")){
	  			$.ajax({  
	  		        async : false,  
	  		        type: 'POST',  
	  		        dataType : "json",  
	  		        data:{id:activityId},
	  		        url:   _WEB_CONTEXT_+"/jy/activity/unSubmitActivity.json",
	  		        error: function () {
	  		        	successAlert('操作失败，请稍后重试');  
	  		        },  
	  		        success:function(data){
	  		        	if(data.result=="success"){
	  		        		successAlert("操作成功",false,function(){
	  		        			window.location.reload();
	  		        		});
	  		        	}else if(data.result=="fail"){
	  		        		successAlert("操作失败，该活动已被查阅",false,function(){
	  		        			window.location.reload();
	  		        		});
	  		        	}else{
	  		        		successAlert("系统出错");
	  		        	}
	  		        }  
	  		    });
	  		}
	  	}
	  	
	  }
	  //分享或取消分享
	  function fenxiang_quxiao(activityId,theClass){
	  	if(theClass=='activity_option_share'){
	  		if(confirm("分享后，所有小伙伴都可以看到此活动啦，您确定分享吗？")){
	  			$.ajax({  
	  		        async : false,  
	  		        type: 'POST',  
	  		        dataType : "json",  
	  		        data:{id:activityId},
	  		        url:   _WEB_CONTEXT_+"/jy/activity/shareActivity.json",
	  		        error: function () {
	  		        	successAlert('操作失败，请稍后重试');  
	  		        },  
	  		        success:function(data){
	  		        	if(data.result=="success"){
	  		        		successAlert("操作成功",false,function(){
	  		        			window.location.reload();
	  		        		});
	  		        	}else{
	  		        		successAlert("系统出错");
	  		        	}
	  		        }  
	  		    });
	  		}
	  	}else if(theClass=='activity_option_qx_share1'){
	  		if(confirm("您确定要取消该活动的分享吗？")){
	  			$.ajax({  
	  		        async : false,  
	  		        type: 'POST',  
	  		        dataType : "json",  
	  		        data:{id:activityId},
	  		        url:   _WEB_CONTEXT_+"/jy/activity/unShareActivity.json",
	  		        error: function () {
	  		        	successAlert('操作失败，请稍后重试');  
	  		        },  
	  		        success:function(data){
	  		        	if(data.result=="success"){
	  		        		successAlert("操作成功",false,function(){
	  		        			window.location.reload();
	  		        		});
	  		        	}else if(data.result=="fail"){
	  		        		successAlert("操作失败，该活动已被评论",false,function(){
	  		        			window.location.reload();
	  		        		});
	  		        	}else{
	  		        		successAlert("系统出错");
	  		        	}
	  		        }  
	  		    });
	  		}
	  	}
	  }
	  
	  
	  
	  
	  
	  
})