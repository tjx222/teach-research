define(["require","zepto","iscroll"], function (require) {
	var jq= Zepto; 
	jq(function(){
		init();
		bindEvent();
		huixian();
	});
    function init() {
    	 var  myScroll = new IScroll('#wrap',{
	    	 scrollbars: true, 
	    	 mouseWheel:true,
	    	 fadeScrollbars:true,
	    	 click:true,
	    });
    	 myScroll.on('scrollEnd',demo); 
	    function demo(){ 
	    	 if ((this.y - this.maxScrollY) <= -(500+this.maxScrollY)) {  
	   	     	  $(".return_1").show(); 
	   	     	  $(".return_1").click(function (){
	   	     		myScroll.scrollTo(0, 0, 1000, IScroll.utils.ease.circular);
	 	          });
	   	     }else{
	   	     	 $(".return_1").hide();      
	   	     }
	    };
    	 jq('.cw_menu_wrap').click(function(){
     		jq(this).hide();
     		jq('.mask').hide();
     	 }); 
          jq( ".menu_list_wrap1 p" ).click(function () {
    	 	 jq( this ).addClass("act").siblings().removeClass("act");
    	 	jq( ".menu_list ").hide();
    	  });
          jq('.select').click(function (){
        	  jq('.menu_list').show(); 
          });
          jq('.close').click(function (){
            	jq('.submit_upload_wrap').hide();
            	jq('.mask').hide();
            });
        jq('.btn_cencel').click(function (){
          	jq('.submit_upload_wrap').hide();
          	jq('.mask').hide();
          });
          jq('.content_bottom_width>div').click(function (){
        	  if(jq(this).find('.cw_option_mask_act1').length>0){
        		  jq(this).find('.cw_option_mask_act1').attr("class","cw_option_mask_act2");
        	  }else if(jq(this).find('.cw_option_mask_act2').length>0){
        		  jq(this).find('.cw_option_mask_act2').attr("class","cw_option_mask_act1");
        	  }
          });
          jq('.content_top_right').click(function (){
          	jq('.mask').show();
          	jq('.cw_menu_wrap').show();
          	var myScroll2 = new IScroll('#wrap2',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true
          	});
          });
        jq(".content_top_left span").click(function(){ //全选
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
        jq(".btn_cencel_r").click(function(){//关闭提交窗口
        	window.parent.closeSubmitWindow();
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
             	location.href = _WEB_CONTEXT_+"/jy/lessonplan/submitIndex_mobile?lessonId="+jq(this).attr("value")+"&site_preference=mobile";
    		});
    	});
    	
    	jq(".btn_submit").click(function(){
    		var idsStr = "";
        	jq(".cw_option_mask_act2").each(function(){
        		idsStr = idsStr+jq(this).attr("id")+","
        	});
        	if(idsStr != ""){
        		batchSubmit(idsStr);
        	}else{
        		successAlert("请选择需要提交的教案");
        	}
    	});
    	
	}
    //回显课题
    function huixian(){
    	var lessonId = $("#selectedlessonId").val();
    	jq("#wrap2").find("p[value='"+lessonId+"']").addClass("act");
    	jq("#currentLesson").html(jq("#wrap2").find("p[value='"+lessonId+"']").html());
    }
    //批量提交上级
    window.batchSubmit = function(idsStr){
    	
		idsStr = idsStr.substring(0,idsStr.length-1); 
		jq(".btn_confirm").on("click",function(){
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/lessonplan/submitLessonPlan.json?site_preference=mobile",
				data:{"planIds":idsStr,"isSubmit":0},
				success:function(data){
					if(data.isOk){
						successAlert("提交成功！",false,function(){
							window.parent.refreshIt();
						});
					}else{
						successAlert("提交失败！");
					}
				}
			});
		});
    	
    	jq('.submit_upload_wrap').show();
      	jq('.mask').show();
    }
});