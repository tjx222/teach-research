define(["require","zepto","iscroll"], function (require) {
	var jq= Zepto; 
	 jq(function(){
		 if(jq(".ja_content_class").length>0 && jq(".ja_content_class").find("option").length<=0){
			 checkMainUserList();
		 }
		 if(jq("#wrap").length>0){ 
			 var  myScroll = new IScroll('#wrap',{
		      		scrollbars:true,
		      		mouseWheel:true,
		      		fadeScrollbars:true,
		      		click:true,
		      	});
		 }
			bindEvent();
		});
	function bindEvent(){
		jq('.range_class p').click(function (){
		    if(jq(this).hasClass('class_act')){
		    	jq(this).removeClass('class_act');
			}else{
				jq(this).addClass('class_act');
			}
		});
		jq(".edit_text").click(function(){
			wordObj.Document.Application.ActiveWindow.View.ReadingLayout = false;
			fadeInOrOut(true);
			jq(this).attr("class","sava_text");
			jq(".sava_text").click(function(){
				var editType = $('#iframe1',window.parent.document).attr("editType");
				saveLessonPlanTracks(editType);
			});
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
			alert("活动已结束，您不可再修改");
		}else if(wordObj.CustomSaveResult =='isSend'){
			alert("活动的整理教案已被发送，您不可再整理");
		}else if(wordObj.CustomSaveResult =='zhubeiIsEdit'){
			alert("该活动的主备教案已被发起人修改");
			parent.location.reload();
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
	
	 
})