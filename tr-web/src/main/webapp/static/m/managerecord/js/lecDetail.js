define(["require","zepto","iscroll"], function (require) {
	require("zepto");
	var $ = Zepto;
	$(function(){ 
		init();
		if($(".lecturerecords_cont_bottom").length>0){ 
			var check_content_bottom = new IScroll('.lecturerecords_cont_bottom',{
				scrollbars:true,
	      		mouseWheel:true,
	      		fadeScrollbars:true,
	      		click:true, 
	      	});
		 }
	}); 
    function init() {
    	$('.check_content_block0 span').click(function (){ 
    		$('.check_menu0_wrap').show();
    		$('.mask').show(); 
    		var check_menu0_wrap = new IScroll('#wrap0',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
    	$('.check_content_block2 span').click(function (){ 
    		$('.check_menu2_wrap').show();
    		$('.mask').show(); 
    		var check_menu2_wrap = new IScroll('#wrap2',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
     	$('.check_content_block1 span').click(function (){ 
    		$('.check_menu1_wrap').show();
    		$('.mask').show(); 
    		var check_menu2_wrap = new IScroll('#wrap2',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
    	$('.check_content_block span').click(function (){ 
    		$('.check_menu_wrap').show();
    		$('.mask').show(); 
    		var check_content_block = new IScroll('#wrap2',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
    	$('.check_content_block1 span').click(function (){ 
    		$('.check_menu1_wrap').show();
    		$('.mask').show(); 
    		var check_menu1_wrap = new IScroll('#wrap3',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
    	//听课记录查询
    	$("#wrap0 p").click(function () { 
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu0_wrap').hide();
		 	 $('.mask').hide(); 
		 	 $('.check_content_block0 span').html($(this).text()+"<strong></strong>"); 
		 	 $('input[name=term1]').val($(this).attr("data-term"));
		 	 change();
		  });
		 $("#wrap2 p").click(function () { 
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu_wrap').hide();
		 	 $('.mask').hide(); 
		 	 $('.check_content_block span').html($(this).text()+"<strong></strong>");
		 	 $('input[name=subject1]').val($(this).attr("data-subject"));
		 	 change();
		  });
    	$('#wrap3 p').click(function(){
             $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu1_wrap').hide();
		 	 $('.mask').hide();
		 	 $('.check_content_block1 span').html($(this).text()+"<strong></strong>");
		 	 $('input[name=grade1]').val($(this).attr("data-grade"));
		 	 change();
        }); 
    	$('#wrap1 p').click(function(){
            $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu2_wrap').hide();
		 	 $('.mask').hide();
		 	 $('.check_content_block2 span').html($(this).text()+"<strong></strong>");
		 	 $('.check_content_block1 span').html("全部年级<strong></strong>");
		 	 $('input[name=phaseId1]').val($(this).attr("data-phase"));
		   	 $('input[name=grade1]').val("");
		 	 change();
       }); 
    	
    	$('.check_menu1_wrap').click(function(){
            $('.check_menu1_wrap').hide(); 
            $('.mask').hide();
        });
		$('.check_menu_wrap').click(function(){
            $('.check_menu_wrap').hide(); 
            $('.mask').hide();
        }); 
		$('.check_menu0_wrap').click(function(){
            $('.check_menu0_wrap').hide(); 
            $('.mask').hide();
        }); 
		$('.check_menu2_wrap').click(function(){
            $('.check_menu2_wrap').hide(); 
            $('.mask').hide();
        }); 
		//听课记录查看
		$('.border1 p').click(function(){
			window.location.href=_WEB_CONTEXT_+"/jy/managerecord/lecView?id="+$(this).parent().attr("data-id");
		});
		//年级学科初始化
		/*$('.check_content_block span').html($('#wrap2 p').eq(0).html()+"<strong></strong>");
		$('.check_content_block1 span').html($('#wrap3 p').eq(0).html()+"<strong></strong>");*/
		//听课记录查询
		window.change=function(){
			$("#form").submit();
    	}
		//回复列表
		$('.courseware_img_5').click(function(){
			var authorId=$(this).parent().attr("data-authorId");
			var id=$(this).parent().attr("data-id");
			var teacherId=$(this).parent().attr("data-teacherId");
			$('.opinions_comment_wrap1').show();
			$('.mask').show();
			$('#iframe_reply').attr("src","jy/lecturereply/reply?authorId="+authorId+"&resId="+id+"&teacherId="+teacherId+"&flags=1");
		})
		//评论列表
		$('.courseware_img_4').click(function(){
			var authorId=$(this).parent().attr("data-authorId");
			var id=$(this).parent().attr("data-id");
			var resType=$(this).parent().attr("data-resType");
			$('.opinions_comment_wrap').show();
			$('.mask').show();
			$('#iframe_comment').attr("src","jy/comment/list?authorId="+authorId+"&resType="+resType+"&resId="+id+"&flags=1");
		})
		//关闭评论、回复弹出框
		$('.close').click(function(){
			$('.opinions_comment_wrap').hide();
			$('.opinions_comment_wrap1').hide();
			$('.mask').hide();
		})
    }
})