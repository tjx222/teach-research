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
          $('.close').click(function (){
          	$('.submit_upload_wrap').hide();
            $('.mask').hide();
          });
          $('.btn_cencel').click(function (){
          	$('.submit_upload_wrap').hide();
          	$('.mask').hide();
          });
          $('.content_bottom_width>div').click(function (){
        	  if($(this).find('.cw_option_mask_act1').length>0){
        		  $(this).find('.cw_option_mask_act1').attr("class","cw_option_mask_act2");
        	  }else if($(this).find('.cw_option_mask_act2').length>0){
        		  $(this).find('.cw_option_mask_act2').attr("class","cw_option_mask_act1");
        	  }
          });
          $('.cw_menu_wrap').click(function(){
       		$(this).hide();
       		$('.mask').hide();
       	 }); 
          $('.content_top_right').click(function (){
          	$('.mask').show();
          	$('.cw_menu_wrap').show(); 
          	var myScroll2 = new IScroll('#wrap2',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true,
          	});
          	$('body').one('tap',function(){
                $('.cw_menu_wrap').hide();
                $('.mask').hide();
            });
            return false;
          });
        $('.courseware_img_3').click(function (){
          	$('.mask').show();
          	$('.opinions_upload_wrap').show(); 
          	var myScroll3 = new IScroll('#wrap3',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          	});
          });
        $(".content_top_left span").click(function(){ //全选
        	if($(this).attr("act")=="qx"){
        		$(".content_bottom_width").find('.cw_option_mask_act1').attr("class","cw_option_mask_act2");
        		$(this).attr("act","qxqx");
        		$(this).html("取消全选");
        	}else if($(this).attr("act")=="qxqx"){
        		$(".content_bottom_width").find('.cw_option_mask_act2').attr("class","cw_option_mask_act1");
        		$(this).attr("act","qx");
        		$(this).html("全选");
        	}
        });
        
        $(".btn_cencel_r").click(function(){//关闭提交窗口
        	window.parent.closeSubmitWindow();
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
             	location.href = _WEB_CONTEXT_+"/jy/rethink/submitIndex_mobile?planType="+$(this).attr("value");
    		});
    	});
    	
    	$(".btn_submit").click(function(){
    		var idsStr = "";
        	$(".cw_option_mask_act2").each(function(){
        		idsStr = idsStr+$(this).attr("id")+","
        	});
        	if(idsStr != ""){
        		batchSubmit(idsStr);
        	}else{
        		successAlert("请选择需要提交的反思");
        	}
    	});
    	
	}
    //回显课题
    function huixian(){
    	var planType = $("#planTypeIdHid").val();
    	$("#wrap2").find("p[value='"+planType+"']").addClass("act");
    	$("#currentLesson").html($("#wrap2").find("p[value='"+planType+"']").html());
    }
    //批量提交上级
    window.batchSubmit = function(idsStr){
    	
		idsStr = idsStr.substring(0,idsStr.length-1); 
		$(".btn_confirm").click(function(){
			var planType = $("#planTypeIdHid").val();
			if(planType == "2"){
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/rethink/submitRethink.json",
					data:{"planIds":idsStr,"isSubmit":0,"qtFanSiIds":""},
					success:function(data){
						if(data.isOk){
							window.parent.refreshIt();
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
					data:{"planIds":"","isSubmit":0,"qtFanSiIds":idsStr},
					success:function(data){
						if(data.isOk){
							window.parent.refreshIt();
						}else{
							successAlert("提交失败！");
						}
					}
				});
			}
			
		});
    	
    	$('.submit_upload_wrap').show();
      	$('.mask').show();
    }
});