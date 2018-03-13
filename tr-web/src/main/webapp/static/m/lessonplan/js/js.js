define(["require","zepto","iscroll"], function (require) {
	var jq= Zepto; 
	jq(function(){
		init();
		bindEvent();
		huixian();
	});
    function init() { 
    	if(jq("#wrap").length>0){
    		var  myScroll = new IScroll('#wrap',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true,
          	});
    	}
    	
    	jq("#spacelist").change(function () {
    		var so = jq(this);
            location.href="jy/lessonplan/index?spaceId="+so.val();
    	});
    	
    	jq(".lessonplan_img_2").click(function () {
    		var plan = jq(this).parent();
    		var planId = plan.attr("planId");
    		var lessonId = plan.attr("lessonId");
    		var resId = plan.attr("resId");
    		var lessonName = plan.find("h3").eq(0).html();
    		plan.addClass('border').siblings().removeClass('border');
          	plan.find(".cw_option_mask").show();
          	plan.find('.cw_option').show();
          	plan.find(".cw_option_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_edit").click(function(){
          		location.href="jy/lessonplan/toEditLessonPlan?planId="+planId;
          	});
          	plan.find(".cw_option_del").css({'-webkit-animation':'del .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_del").click(function(){
          		deleteIt(planId);
          	});
          	plan.find(".cw_option_submit").css({'-webkit-animation': 'submit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_submit").click(function(){
          		submitIt(planId,0);
          	});
          	plan.find(".cw_option_qx_submit").click(function(){
          		unSubmitIt(planId,1);
          	});
          	plan.find(".cw_option_share").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_share").click(function(){
          		shareIt(planId,1);
          	});
          	plan.find(".cw_option_qx_share").click(function(){
          		unShareIt(planId,0);
          	});
          	plan.find(".cw_option_down").css({'-webkit-animation': 'down .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_down").click(function(){
          		downloadIt(jq(this).attr("href"));
          	});
          });
    	
    	  jq(".lessonplan_ppt").each(function(){
    		  var resId = jq(this).attr("resId");
    		  jq(this).find("p").click(function(){
    			  scanResFile_m(resId);
    		  });
    	  });
    	
          jq(".cw_option_close").click(function () {
        	  var plan = jq(this).parent().parent();
        	  plan.removeClass('border');
     		 plan.find('.cw_option_mask').hide();
     		 plan.find('.cw_option').hide(); 
     		 plan.find(".cw_option_edit").css('-webkit-animation', 'edit .5s');
     		 plan.find(".cw_option_del").css('-webkit-animation', 'del .5s');
     		 plan.find(".cw_option_submit").css('-webkit-animation', 'submit .5s');
     		 plan.find(".cw_option_share").css('-webkit-animation', 'share .5s');
     		 plan.find(".cw_option_down").css('-webkit-animation', 'down .5s');
         });
          jq('.close').click(function (){
          	jq('.add_upload_wrap').hide();
          	jq('.del_upload_wrap').hide();
          	jq('.share_upload_wrap').hide()
          	jq('.submit_upload_wrap').hide();
          	jq('.opinions_upload_wrap').hide();
          	jq('.mask').hide();
          	clearFileText_1();
          });
          jq('.btn_cencel').click(function (){
            	jq('.add_upload_wrap').hide();
            	jq('.del_upload_wrap').hide();
            	jq('.share_upload_wrap').hide()
            	jq('.submit_upload_wrap').hide();
            	jq('.opinions_upload_wrap').hide();
            	jq('.mask').hide();
            	clearFileText_1();
            });
          jq('.content_bottom_width>div').click(function (){
        	  if(jq(this).find('.cw_option_mask_act1').length>0){
        		  jq(this).find('.cw_option_mask_act1').attr("class","cw_option_mask_act2");
        	  }else if(jq(this).find('.cw_option_mask_act2').length>0){
        		  jq(this).find('.cw_option_mask_act2').attr("class","cw_option_mask_act1");
        	  }
          });
          jq('.cw_option_submit').click(function (){
          	jq('.submit_upload_wrap').show();
          	jq('.mask').show();
          }); 
          jq('#chapter.content_top_right').click(function (){
          	jq('.mask').show();
          	jq('.cw_menu_wrap').show();
          	var  myScroll2 = new IScroll('.cw_menu_list_wrap1',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true,
          	}); 
          }); 
          jq('.cw_menu_wrap').click(function (){
        	  jq('.cw_menu_wrap').hide();
        	  jq('.mask').hide();
            }); 
          jq('.select').click(function (){
        	  jq('.menu_list').toggle();
        	  jq(this).addClass('select1');
        	  if(jq('.menu_list').css("display") == 'block' ){
        		  jq('.add_upload_wrap1').show();   //如果元素为隐藏,则将它显现
        		  var  myScroll1 = new IScroll('#wrap1',{
                		scrollbars:true,
                		mouseWheel:true,
                		fadeScrollbars:true,
                		click:true,
                	});
        	  }else{
        		  jq('.add_upload_wrap1').hide();     //如果元素为显现,则将其隐藏
        	  } 
        	  var  myScroll1 = new IScroll('#wrap1',{
            		scrollbars:true,
            		mouseWheel:true,
            		fadeScrollbars:true,
            		click:true,
            	});
        	  
          }); 
          jq('.add_upload_wrap1').click(function(){ 
        	  jq('.menu_list').hide();
          }); 
    }
    
    //绑定事件
    function bindEvent(){
    	jq("#wrap2").find("p[level='leaf']").each(function(){
    		jq(this).click(function(){ 
    			jq(this).addClass("act").siblings().removeClass("act");
    			jq('.mask').hide();
             	jq('.cw_menu_wrap').hide();
             	jq("#currentLesson").html(jq(this).html());
             	$("#form_lessonId").val(jq(this).attr("value"));
             	$("#hiddenForm").submit();
    		});
    	});
    	jq("#wrap1").find("p[level='leaf']").each(function(){
    		jq(this).click(function(){
    			jq(this).addClass("act").siblings().removeClass("act");
    			jq( ".menu_list ").hide();
             	jq("#uploadLesson").html(jq(this).html());
             	jq("#lessonId").val(jq(this).attr("value"));
             	jq("#planName").val(jq(this).html());
    		});
    	});
    	jq(".enclosure_del").click(function(){
    		jq(".enclosure_name").hide();
        	jq("#uploadId").show();
    		jq("#save_edit").hide();
    		jq("#save").show();
    	});
    	jq("#save_edit").click(function(){
			save();
		});
    	jq(".btn_submit").click(function(){
    		var selectedlessonId = jq("#selectedlessonId").val();
    		jq("#submitDiv").find("iframe").attr("src",_WEB_CONTEXT_+"/jy/lessonplan/submitIndex_mobile?spaceId="+$("#spacelist").val()+"&lessonId="+selectedlessonId+"&site_preference=mobile");
    		jq("#submitDiv").show();
    		
    	});
    	 $('.lessonplan_img_3').click(function (){ //绑定查阅意见
         	var planType = $(this).attr("planType");
         	var isUpdate = $(this).attr("isUpdate");
     		var infoId = $(this).attr("infoId");
     		showScanListBox(planType,infoId,isUpdate);
          });
       //评论意见
       $('.lessonplan_img_4').click(function (){
     	  var planType = $(this).attr("planType");
     	  var isUpdate = $(this).attr("isUpdate");
     	  var planId = $(this).attr("infoId");
     	  showCommentListBox(planType,planId,isUpdate);
       });
       
       jq(".opinions_comment_wrap").find(".close").click(function(){
    	   jq(".opinions_comment_wrap").hide();
       });
    }
    //回显课题
    function huixian(){
    	var lessonId = $("#selectedlessonId").val();
    	jq("#wrap2").find("p[value='"+lessonId+"']").addClass("act");
    	jq("#currentLesson").html(jq("#wrap2").find("p[value='"+lessonId+"']").html());
    }
    
    //上传教案
    window.afterUpload = function(data){
    	jq("#resId").val(data.data);
		if(data.code==0){
			save();
		}
    }
    
    //上传前
    window.uploading = function(){
    	if(jq("#wrap1").find("p.act").length==0){
    		alert("请选择课题");
    		return false;
    	}else if(jq("input[name='file']")[0].files.length==0){
    		alert("请选择文件");
    		return false;
    	}
    	$("#save").hide();
    	$("#editButton").hide();
    	$(".btn_sc").show();
    	$(".close").hide();
    	return true;
    }
    
    //删除教案
    window.deleteIt = function(planId){
    	var selectedlessonId = jq("#selectedlessonId").val();
    	jq('.del_upload_wrap').find(".btn_confirm").on("click",function(){
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/lessonplan/delete.json",
    			data:{"planId":planId},
    			success:function(data){
    				if(data.isOk){
    					location.href = _WEB_CONTEXT_+"/jy/lessonplan/index?spaceId="+$("#spacelist").val()+"&lessonId="+selectedlessonId+"&site_preference=mobile&pageSize=1000";
    				}else{
    					successAlert("删除失败！");
    				}
    			}
    		});
    	});
    	jq('.del_upload_wrap').show();
      	jq('.mask').show();
    }
    //分享教案
    window.shareIt = function(planId,isShare){
    	jq('.share_upload_wrap').find(".btn_confirm").on("click",function(){
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/lessonplan/sharing.json",
    			data:{"planId":planId,"isShare":isShare},
    			success:function(data){
    				if(data.isOk==0){
    					successAlert("分享成功",false,function(){
    						location.href = location.href;
    					});
    				}else{
    					successAlert("分享失败！");
    				}
    			}
    		});
    	});
    	jq(".share_upload_wrap h3").html("分享教案");
    	jq(".share_upload_wrap .share_width span").html("您确定要分享该教案吗？分享后，您的小伙伴就可以看到喽！");
    	jq('.share_upload_wrap').show();
      	jq('.mask').show();
    }
  //取消分享教案
    window.unShareIt = function(planId,isShare){
    	jq('.share_upload_wrap').find("q").attr("class","dlog_qx_share");
    	jq('.share_upload_wrap').find(".btn_confirm").on("click",function(){
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/lessonplan/sharing.json",
    			data:{"planId":planId,"isShare":isShare},
    			success:function(data){
    				if(data.isOk==0){
    					successAlert("取消分享成功！",false,function(){
    						location.href = location.href;
    					})
    				}else if(data.isOk==1){
    					successAlert("该教案已被评论，禁止取消分享",false,function(){
    						location.href = location.href;
    					});
    				}else{
    					successAlert("取消分享失败！");
    				}
    			}
    		});
    	});
    	jq(".share_upload_wrap h3").html("取消分享教案");
    	jq(".share_upload_wrap .share_width span").html("您确定要取消分享该教案吗？");
    	jq('.share_upload_wrap').show();
      	jq('.mask').show();
    }
  //提交教案
    window.submitIt = function(planId,isSubmit){
    	jq('.submit_upload_wrap').find(".btn_confirm").on("click",function(){
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/lessonplan/submitLessonPlanByOne.json",
    			data:{"planId":planId,"isSubmit":isSubmit},
    			success:function(data){
    				if(data.isOk==0){
    					successAlert("您提交成功，已提交到学校管理室",false,function(){
    						location.href = location.href;
    					});
    				}else{
    					successAlert("提交失败！");
    				}
    			}
    		});
    	});
    	jq(".submit_upload_wrap h3").html("提交教案");
    	jq(".submit_upload_wrap .submit_width span").html("您确定要提交该教案吗？提交后，学校管理者将看到这些内容！");
    	jq('.submit_upload_wrap').show();
      	jq('.mask').show();
    }
  //取消提交教案
    window.unSubmitIt = function(planId,isSubmit){
    	var selectedlessonId = jq("#selectedlessonId").val();
    	jq('.submit_upload_wrap').find("q").attr("class","dlog_qx_submit");
    	jq('.submit_upload_wrap').find(".btn_confirm").on("click",function(){ 
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/lessonplan/submitLessonPlanByOne.json",
    			data:{"planId":planId,"isSubmit":isSubmit},
    			success:function(data){
    				if(data.isOk==0){
    					successAlert("取消提交成功！",false,function(){
    						location.href = location.href;
    					});
    				}else if(data.isOk==1){
    					successAlert("该教案已被查阅，禁止取消提交",false,function(){
    						location.href = location.href;
    					});
    				}else{
    					successAlert("取消提交失败！");
    				}
    			}
    		});
    	});
    	jq(".submit_upload_wrap h3").html("取消提交教案");
    	jq(".submit_upload_wrap .submit_width span").html("您确定要取消提交该教案吗？");
    	jq('.submit_upload_wrap').show();
      	jq('.mask').show();
    }
    //下载教案
    window.downloadIt = function(loadPath){
    	location.href = loadPath;
    }
    //刷新
    window.refreshIt = function(){
    	var selectedlessonId = jq("#selectedlessonId").val();
    	location.href = _WEB_CONTEXT_+"/jy/lessonplan/index?spaceId="+$("#spacelist").val()+"&lessonId="+selectedlessonId+"&site_preference=mobile&pageSize=1000";
    }
    //关闭提交窗口
    window.closeSubmitWindow = function(){
    	jq("#submitDiv").hide();
    }
    
    window.showScanListBox = function (planType,infoId,isUpdate){
      	$('.mask').show();
		$("#checkComment").show();
    	$("#iframe_scan").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndex?site_preference=mobile&flags=false&resType="+planType+"&resId="+infoId);
    	if(isUpdate){//更新为已查看
    		$.ajax({  
    	        async : false,  
    	        cache:true,  
    	        type: 'POST',  
    	        dataType : "json",  
    	        data:{planType:planType,infoId:infoId},
    	        url:  _WEB_CONTEXT_+"/jy/lessonplan/setScanKJFASI.json?site_preference=mobile",
    	    });
    	}
    };
    window.showCommentListBox = function (planType,planId,isUpdate){
    	$('.mask').show();
		$("#comment_div").show();
    	$("#iframe_comment").attr("src",_WEB_CONTEXT_+"/jy/comment/list?site_preference=mobile&flags=0&resType="+planType+"&resId="+planId);
     	if(isUpdate){//更新为已查看
     		$.ajax({  
     	        async : false,  
     	        cache:true,  
     	        type: 'POST',  
     	        dataType : "json",  
     	        data:{planId:planId},
     	        url:  _WEB_CONTEXT_+"/jy/lessonplan/setCommentListAlreadyShowByType.json?site_preference=mobile",
     	    });
     	}
     };
     
});