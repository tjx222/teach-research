define(["require","zepto","iscroll"], function (require) {
	require("zepto");
	var $ = Zepto;
	$(function(){
		init();
		bindEvent();
		huixian();
	});
    function init() {
    	var myScroll = new IScroll('#wrap',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    	$(".courseware_img_2").click(function () {
    		var plan = $(this).parent();
    		var planId = plan.attr("planId");
    		var planType = plan.attr("planType");
    		var lessonId = plan.attr("lessonId");
    		var resId = plan.attr("resId");
    		var lessonName = plan.find("h3").eq(0).html();
    		plan.addClass('border').siblings().removeClass('border');
          	plan.find(".cw_option_mask").show();
          	plan.find('.cw_option').show();
          	plan.find(".cw_option_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_edit").click(function(){
          		editIt(planId,lessonId,resId,lessonName,planType);
          	});
          	plan.find(".cw_option_del").css({'-webkit-animation':'del .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_del").click(function(){
          		deleteIt(planId,planType);
          	});
          	plan.find(".cw_option_submit").css({'-webkit-animation': 'submit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_submit").click(function(){
          		submitIt(planId,planType,0);
          	});
          	plan.find(".cw_option_qx_submit").click(function(){
          		unSubmitIt(planId,planType,1);
          	});
          	plan.find(".cw_option_share").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_share").click(function(){
          		shareIt(planId,1,planType);
          	});
          	plan.find(".cw_option_qx_share").click(function(){
          		unShareIt(planId,0,planType);
          	});
          	plan.find(".cw_option_down").css({'-webkit-animation': 'down .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_down").click(function(){
          		downloadIt($(this).attr("href"));
          	});
          });
          $(".cw_option_close").click(function () {
        	  var plan = $(this).parent().parent();
        	  plan.removeClass('border');
     		 plan.find('.cw_option_mask').hide();
     		 plan.find('.cw_option').hide(); 
     		 plan.find(".cw_option_edit").css('-webkit-animation', 'edit .5s');
     		 plan.find(".cw_option_del").css('-webkit-animation', 'del .5s');
     		 plan.find(".cw_option_submit").css('-webkit-animation', 'submit .5s');
     		 plan.find(".cw_option_share").css('-webkit-animation', 'share .5s');
     		 plan.find(".cw_option_down").css('-webkit-animation', 'down .5s');
         });
          $( ".menu_list_wrap1 p" ).click(function () {
    	 	 $( this ).addClass("act").siblings().removeClass("act");
    	 	$( ".menu_list ").hide();
    	  });
    	
          $('.select').click(function (){
              $('.menu_list').toggle();
              if($('.menu_list').css("display") == 'block' ){
        		  $('.add_upload_wrap1').show();   //如果元素为隐藏,则将它显现
        	  }else{
        		  $('.add_upload_wrap1').hide();     //如果元素为显现,则将其隐藏
        	  } 
              var  myScroll1 = new IScroll('#wrap1',{
            		scrollbars:true,
            		mouseWheel:true,
            		fadeScrollbars:true,
            		click:true,
            	}); 
          });
          $('.add_upload_wrap1').click(function(){ 
        	  $('.menu_list').hide();
          }); 
          $('.close').click(function (){
          	$('.add_upload_wrap').hide();
          	$('.del_upload_wrap').hide();
          	$('.share_upload_wrap').hide();
          	$('.submit_upload_wrap').hide();
          	$('.opinions_upload_wrap').hide();
          	$('.opinions_comment_wrap').hide();
          	$('.mask').hide();
          });
          $('.btn_cencel').click(function (){
            	$('.add_upload_wrap').hide();
            	$('.del_upload_wrap').hide();
            	$('.share_upload_wrap').hide();
            	$('.submit_upload_wrap').hide();
            	$('.opinions_upload_wrap').hide();
            	$('.mask').hide();
            });
          $('.content_bottom_width>div').click(function (){
        	  if($(this).find('.cw_option_mask_act1').length>0){
        		  $(this).find('.cw_option_mask_act1').attr("class","cw_option_mask_act2");
        	  }else if($(this).find('.cw_option_mask_act2').length>0){
        		  $(this).find('.cw_option_mask_act2').attr("class","cw_option_mask_act1");
        	  }
          });
          $('.cw_option_submit').click(function (){
          	$('.submit_upload_wrap').show();
          	$('.mask').show();
          });
          $('.content_top_right').click(function (){
          	$('.mask').show();
          	$('.cw_menu_wrap').show(); 
          	 var  myScroll2 = new IScroll('#wrap2',{
         		scrollbars:true,
         		mouseWheel:true,
         		click:true,
         		fadeScrollbars:true,
         	});
          	
          });
          $('.cw_menu_wrap').click(function (){
        	$('.cw_menu_wrap').hide();
        	$('.mask').hide();
          }); 
          //查阅意见
          $('.courseware_img_3').click(function (){
        	var planType = $(this).attr("planType");
        	var isUpdate = $(this).attr("isUpdate");
        	if(planType=="2"){
        		var infoId = $(this).attr("infoId");
        		showScanListBox(planType,infoId,isUpdate);
        	}else if(planType=="3"){
        		var planId = $(this).attr("planId");
        		showScanListBoxOthers(planType,planId,isUpdate);
        	}else{
        		successAlert("数据错误！");
        	}
        	if(isUpdate){
      		  $(this).html("");
  		  	}
         });
          //评论意见
          $('.courseware_img_4').click(function (){
        	  var planType = $(this).attr("planType");
        	  var isUpdate = $(this).attr("isUpdate");
        	  var planId = $(this).attr("planId");
        	  showCommentListBox(planType,planId,isUpdate);
        	  if(isUpdate){
        		  $(this).html("");
    		  }
          });
          $(".courseware_ppt").each(function(){
    		  var resId = $(this).attr("resId");
    		  $(this).find("p").click(function(){
    			  scanResFile_m(resId);
    		  });
    	  });
    }
    
    //绑定事件
    function bindEvent(){
    	$("#wrap2").find("p[level='leaf']").each(function(){
    		$(this).click(function(){
    			$(this).addClass("act").siblings().removeClass("act");
    			$('.mask').hide();
             	$('.cw_menu_wrap').hide();
             	$("#currentLesson").html($(this).html());
             	$("#form_planType").val($(this).attr("value"));
             	$("#hiddenForm").submit();
    		});
    	});
    	$("#wrap1").find("p[level='leaf']").each(function(){
    		$(this).click(function(){
    			$(this).addClass("act").siblings().removeClass("act");
             	$('.cw_menu_wrap').hide();
             	$("#uploadLesson").html($(this).html());
             	$("#lessonId").val($(this).attr("value"));
             	$("#planName").val($(this).html());
    		});
    	});
    	
    	$(".enclosure_del").click(function(){
    		$(".enclosure_name").hide();
        	$("#uploadId").show();
        	$("#resId").val("");
    	});
    	$(".btn_submit").click(function(){
    		$("#submitDiv").find("iframe").attr("src",_WEB_CONTEXT_+"/jy/rethink/submitIndex_mobile");
    		$("#submitDiv").show();
    	});
    	$(".add_cour").click(function(){ //撰写反思按钮绑定
    		$('.zx_option_wrap').show();
        	$('.mask').show(); 
    	});
  		$('.zx_option_kh').click(function (event){
  			$('.zx_option_wrap').hide();
  			addIt("kh");
  			event.stopPropagation();
  		});
  		$('.zx_option_qt').click(function (event){
  			$('.zx_option_wrap').hide(); 
  			addIt("qt"); 
 			event.stopPropagation();
  		});
  		$('.zx_option_wrap').click(function (){ 
  			$('.zx_option_wrap').hide();
  			$('.mask').hide();  
  		});
    }
    //回显课题
    function huixian(){
    	var planType = $("#planTypeIdHid").val();
    	$("#wrap2").find("p[value='"+planType+"']").addClass("act");
    	$("#currentLesson").html($("#wrap2").find("p[value='"+planType+"']").html());
    }
    
  //显示查阅意见box(支持教案、课件、反思，其他反思除外 isUpdate:是否更新有新查阅意见状态)
    window.showScanListBox = function (planType,infoId,isUpdate){
      	$('.mask').show();
		$("#checkComment").css("display","block");
    	$("#iframe_scan").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndex?flags=false&resType="+planType+"&resId="+infoId);
    	if(isUpdate){//更新为已查看
    		$.ajax({  
    	        async : false,  
    	        cache:true,  
    	        type: 'POST',  
    	        dataType : "json",  
    	        data:{planType:planType,infoId:infoId},
    	        url:  _WEB_CONTEXT_+"/jy/myplanbook/setScanKJFASI.json",
    	    });
    	}
    };
  //显示查阅意见box(其他反思)
    window.showScanListBoxOthers = function (planType,planId,isUpdate){
    	$('.mask').show();
		$("#checkComment").css("display","block");
    	$("#iframe_scan").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndex?flags=false&resType="+planType+"&resId="+planId);
    	if(isUpdate){//更新为已查看
    		$.ajax({  
    			async : false,  
    			cache:true,  
    			type: 'POST',  
    			dataType : "json",  
    			data:{planType:planType,planId:planId},
    			url:  _WEB_CONTEXT_+"/jy/myplanbook/setScanKJFASI.json",
    		});
    	}
    };
  //显示评论意见列表box（支持教案、课件、反思和其他反思）
    window.showCommentListBox = function (planType,planId,isUpdate){
    	$('.mask').show();
		$("#comment_div").css("display","block");
    	$("#iframe_comment").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=0&resType="+planType+"&resId="+planId);
     	if(isUpdate){//更新为已查看
     		$.ajax({  
     	        async : false,  
     	        cache:true,  
     	        type: 'POST',  
     	        dataType : "json",  
     	        data:{planId:planId},
     	        url:  _WEB_CONTEXT_+"/jy/myplanbook/setCommentListAlreadyShowByType.json",
     	    });
     	}
     };
     
    //上传反思前的验证
    window.start = function(){
    	var planType = $("#planType").val();
    	if(planType=="2"){
    		var lessonId = $("#lessonId").val();
    		if(lessonId==null || lessonId ==""){
    			successAlert("请选择课题！");
    			return false;
    		}
    	}else if(planType=="3"){
    		var planName = $("#qt_planName").val();
    		if(planName==null || planName ==""){
    			successAlert("请输入反思标题！");
    			return false;
    		}
    	}
    	var resId = $("#resId").val();
    	var file = document.getElementById("fileToUpload_1").files[0];
    	if(file){
    		return true;
    	}else if(!file && resId!=null && resId!=""){
    		save();
    		return false;
    	}else{
    		successAlert("请选择要上传的文件！");
    		return false;
    	}
    };
    //上传反思
    window.afterUpload = function(data){
		if(data.code==0){
			$("#resId").val(data.data);
			save();
		}
    };
    //保存
    window.save = function(){
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/rethink/save.json",
			data:$("#fs_form").serializeArray(),
			success:function(data){
				successAlert("保存成功",false,function(){
					//刷新页面
					var planType = $("#planType").val();
					location.href =  _WEB_CONTEXT_+"/jy/rethink/index?planType="+planType;
				});
			}
		});
    };
    //撰写反思
    window.addIt = function(typeStr){
    	$("#planId").val("");
    	$("#lessonId").val("");
    	$("#resId").val("");
    	$(".enclosure_name").hide();
    	$("#uploadId").show();
    	$("#save").val("上传");
    	if(typeStr=="kh"){
    		$("#kh_kt").show();
    		$("#qt_kt").hide();
    		$("#planType").val(2);
    		$("#planName").val("");
    		$("#planName").attr("name","planName");
    		$("#qt_planName").attr("name","xxxx");
        	$("#uploadLesson").html("请选择<q></q>");
        	$("#wrap1").find("p").removeClass("act");
        	$(".add_upload_title h3").html("撰写课后反思");
          	$('.menu_list').hide();
    	}else if(typeStr=="qt"){
    		$("#planName").attr("name","xxxx");
    		$("#qt_planName").attr("name","planName");
    		$("#qt_planName").val("");
    		$("#planType").val(3);
    		$("#kh_kt").hide();
    		$("#qt_kt").show();
    		$(".add_upload_title h3").html("撰写其他反思");
    	}
    	$('.add_upload_wrap').show();
      	$('.mask').show(); 
      	var myScroll1 = new IScroll('#wrap1',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    };
    //修改反思
    window.editIt = function(planId,lessonId,resId,lessonName,planType){
    	if(planType=="2"){
    		$("#kh_kt").show();
    		$("#qt_kt").hide();
    		$("#lessonId").val(lessonId);
    		$("#planName").val(lessonName);
    		$("#planName").attr("name","planName");
    		$("#qt_planName").attr("name","xxxx");
    		$("#planType").val(2);
    		$(".add_upload_title h3").html("修改课后反思");
    	}else if(planType=="3"){
    		$("#qt_planName").val(lessonName);
    		$("#planName").attr("name","xxxx");
    		$("#qt_planName").attr("name","planName");
    		$("#planType").val(3);
    		$("#kh_kt").hide();
    		$("#qt_kt").show();
    		$(".add_upload_title h3").html("修改其他反思");
    	}
    	if(resId!=""){
    		$.ajax({
    			async : false,
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/rethink/getFileById.json",
    			data:{"resId":resId},
    			success:function(data){
    				if(data.res!=null){
    					$("#uploadFileName").html(data.res.name+"."+data.res.ext);
    					$("#resId").val(resId);
    					if(lessonId!=null && lessonId!=""){
    						$("#wrap1").find("p[value='"+lessonId+"']").addClass("act");
    						$("#uploadLesson").html($("#wrap1").find("p[value='"+lessonId+"']").html()+"<q></q>");
    					}
    				}
    			}
    		});
    	}
    	$("#planId").val(planId);
    	$(".enclosure_name").show();
    	$("#uploadId").hide();
    	$("#save").val("修改");
    	
    	$('.add_upload_wrap').show();
      	$('.mask').show(); 
      	var  myScroll1 = new IScroll('#wrap1',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
      	$('.menu_list').hide();
    };
    //删除反思
    window.deleteIt = function(planId,planType){
    	$('.del_upload_wrap').find(".btn_confirm").click(function(){
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/rethink/delete.json",
    			data:{"planId":planId,"planType":planType},
    			success:function(data){
    				if(data.isOk){
    					location.href = _WEB_CONTEXT_+"/jy/rethink/index?planType="+planType;
    				}else{
    					successAlert("删除失败！");
    				}
    			}
    		});
    	});
    	$('.del_upload_wrap').show();
      	$('.mask').show();
    };
    //分享反思
    window.shareIt = function(planId,isShare,planType){
    	$('.share_upload_wrap').find(".btn_confirm").click(function(){
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/rethink/sharing.json",
    			data:{"planId":planId,"isShare":isShare,"planType":planType},
    			success:function(data){
    				if(data.isOk==0){
    					location.href = _WEB_CONTEXT_+"/jy/rethink/index?planType="+planType;
    				}else{
    					successAlert("分享失败！");
    				}
    			}
    		});
    	});
    	$(".share_upload_wrap h3").html("分享反思");
    	$(".share_upload_wrap .share_width span").html("您确定要分享该反思吗？分享后，您的小伙伴就可以看到喽！");
    	$('.share_upload_wrap').show();
      	$('.mask').show();
    };
  //取消分享反思
    window.unShareIt = function(planId,isShare,planType){
    	$('.share_upload_wrap').find(".btn_confirm").click(function(){
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/rethink/sharing.json",
    			data:{"planId":planId,"isShare":isShare,"planType":planType},
    			success:function(data){
    				if(data.isOk==0){
    					location.href = _WEB_CONTEXT_+"/jy/rethink/index?planType="+planType;
    				}else if(data.isOk==1){
    					successAlert("该反思已被评论，禁止取消分享",false,function(){
							location.href = _WEB_CONTEXT_+"/jy/rethink/index?planType="+planType;
						});
    				}else{
    					successAlert("取消分享失败！");
    				}
    			}
    		});
    	});
    	$(".share_upload_wrap h3").html("取消分享反思");
    	$(".share_upload_wrap .share_width span").html("您确定要取消分享该反思吗？");
    	$('.share_upload_wrap').show();
      	$('.mask').show();
    };
  //提交反思
    window.submitIt = function(planId,planType,isSubmit){
    	$('.submit_upload_wrap').find(".btn_confirm").click(function(){
    		if(planType=="2"){
    			$.ajax({
    				type:"post",
    				dataType:"json",
    				url:_WEB_CONTEXT_+"/jy/rethink/submitRethink.json",
    				data:{"planIds":planId,"isSubmit":isSubmit,"qtFanSiIds":""},
    				success:function(data){
    					if(data.isOk){
    						location.href = _WEB_CONTEXT_+"/jy/rethink/index?planType="+planType;
    					}else{
    						successAlert("提交失败！");
    					}
    				}
    			});
    		}else{
    			$.ajax({
    				type:"post",
    				dataType:"json",
    				url:_WEB_CONTEXT_+"/jy/rethink/submitRethink.json",
    				data:{"qtFanSiIds":planId,"isSubmit":isSubmit,"planIds":""},
    				success:function(data){
    					if(data.isOk){
    						location.href = _WEB_CONTEXT_+"/jy/rethink/index?planType=3";
    					}else{
    						successAlert("提交失败！");
    					}
    				}
    			});
    		}
    	});
    	$(".submit_upload_wrap h3").html("提交反思");
    	$(".submit_upload_wrap .submit_width span").html("您确定要提交该反思吗？提交后，学校管理者将看到这些内容！");
    	$('.submit_upload_wrap').show();
      	$('.mask').show();
    };
  //取消提交反思
    window.unSubmitIt = function(planId,planType,isSubmit){
    	$('.submit_upload_wrap').find(".btn_confirm").click(function(){
    		if(planType=="2"){
    			$.ajax({
    				type:"post",
    				dataType:"json",
    				url:_WEB_CONTEXT_+"/jy/rethink/submitRethink.json",
    				data:{"planIds":planId,"isSubmit":isSubmit,"qtFanSiIds":""},
    				success:function(data){
    					if(data.isOk){
    						location.href = _WEB_CONTEXT_+"/jy/rethink/index?planType="+planType;
    					}else{
    						successAlert("取消提交失败！");
    					}
    				}
    			});
    		}else{
    			$.ajax({
    				type:"post",
    				dataType:"json",
    				url:_WEB_CONTEXT_+"/jy/rethink/submitRethink.json",
    				data:{"qtFanSiIds":planId,"isSubmit":isSubmit,"planIds":""},
    				success:function(data){
    					if(data.isOk){
    						location.href = _WEB_CONTEXT_+"/jy/rethink/index?planType=3";
    					}else{
    						successAlert("取消提交失败！");
    					}
    				}
    			});
    		}
    	});
    	$(".submit_upload_wrap h3").html("取消提交反思");
    	$(".submit_upload_wrap .submit_width span").html("您确定要取消提交该反思吗？");
    	$('.submit_upload_wrap').show();
      	$('.mask').show();
    };
    //下载反思
    window.downloadIt = function(loadPath){
    	location.href = loadPath;
    };
    //刷新
    window.refreshIt = function(){
    	location.href = _WEB_CONTEXT_+"/jy/rethink/index";
    };
    //关闭提交窗口
    window.closeSubmitWindow = function(){
    	$("#submitDiv").hide();
    };
});