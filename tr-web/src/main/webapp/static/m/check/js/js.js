define(["require","zepto","iscroll"], function (require) {
	require("zepto");
	var $ = Zepto;
	$(function(){ 
		init();
		//判断是否加载查阅数据
		var load=$('#wrap2').attr("data_load");
		if(load==1){
			initQueryParam();
			loadData(); 
		}
		if($(".check_content_bottom").length>0){ 
			var check_content_bottom = new IScroll('.check_content_bottom',{
				scrollbars:true,
	      		mouseWheel:true,
	      		fadeScrollbars:true,
	      		click:true, 
	      	});
		 }
	}); 
    function init() {
    	
    	$('.check_content_block span').click(function (){ 
    		$('.check_menu_wrap').show();
    		$('.mask').show(); 
    		var myScroll2 = new IScroll('#wrap2',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
    	$('.check_content_block1 span').click(function (){ 
    		$('.check_menu1_wrap').show();
    		$('.mask').show(); 
    		var myScroll3 = new IScroll('#wrap3',{
    			scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true, 
	      	});	
    	});
		 $("#wrap2 p").click(function () { 
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu_wrap').hide();
		 	 $('.mask').hide(); 
		 	 $('.check_content_block span').html($(this).text()+"<strong></strong>");
		 	 $('#selectsubject').attr("data",$(this).attr('data'));
		 	 loadData(); 
		  });
    	//切换学期
    	$(".semester_wrap1 p").click(function () { 
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.semester_wrapper').hide(); 
		 	 $('.mask').hide(); 
		 	 var p1=$(this).attr("data-val");
		 	 var s=$('.semester_wrap1');
		 	 var type=s.attr("data-type");
		 	 if(type==3){
		 		 type=2;
		 	 } 
		 	 var userid=s.attr("data-userId");
		 	 var grade=s.attr("data-grade");
		 	 var subject=s.attr("data-subject");
		 	 location.href = _WEB_CONTEXT_ + "/jy/check/lesson/"+type+"/tch/"+userid+"?grade="+grade+"&subject="+subject+"&fasciculeId=" + p1;//页面跳转并传参
		  });
    	$('#wrap3 p').click(function(){
             $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu1_wrap').hide();
		 	 $('.mask').hide();
		 	 $('.check_content_block1 span').html($(this).text()+"<strong></strong>");
		 	 $('#selectgrade').attr("data",$(this).attr('data'));
		 	 loadData();
        }); 
    	$('.semester_wrapper').click(function(){
            $('.semester_wrapper').hide(); 
            $('.mask').hide();
        }); 
    	$('.check_menu1_wrap').click(function(){
            $('.check_menu1_wrap').hide(); 
            $('.mask').hide();
        });
		$('.check_menu_wrap').click(function(){
            $('.check_menu_wrap').hide(); 
            $('.mask').hide();
        }); 
    	$('.semester').click(function (){
    		 $('.semester_wrapper').show(); 
             $('.mask').show();
    	}); 
        $('.close').click(function (){
    		
    	});
		window.loadData=function(){
        	var HTMLDATA = {};
    		var sub =$("#selectsubject").attr("data");
    		var grade = $("#selectgrade").attr("data");
    		var type=$("#wrap2").attr("data_type");
    		var name = "s"+sub+"g"+grade;
    		var html = HTMLDATA[name];
    		if(!html){
    			$.get("jy/check/lesson/"+type+"/tchlist?subject="+sub+"&grade="+grade,function(data){
    				HTMLDATA[name] = data;
    				$("#userListContent").html(data);
    				var check_c_b = new IScroll('#check_c_b',{
    		      		scrollbars:true,
    		      		mouseWheel:true,
    		      		fadeScrollbars:true,
    		      		click:true,
    		      	});	
    			});
    		}else{
    			$("#userListContent").html(html);
    		}
    		
    	};
    	
    }
    //初始化查询参数
    function initQueryParam(){
    	//初始化数据
    	var subject = getQueryString("subject");
    	var grade = getQueryString("grade");
    	if(subject == ""){
    		subject = $('#wrap2 p').attr("data");
    	}
    	if(grade == ""){
    		grade = $('#wrap3 p').attr("data");
    	}
    	 $('#subjectSelect').html($('#wrap2 p[data="'+subject+'"]').html()+"<strong></strong>");
    	 $('#gradeSelect').html($('#wrap3 p[data="'+grade+'"]').html()+"<strong></strong>");
    	 $('#selectgrade').attr("data",grade);
    	 $('#selectsubject').attr("data",subject);
    }
    
  //查阅活动
    window.chayuejb = function(obj){
		var activityId = $(obj).attr("activityId");
		var typeId = $(obj).attr("typeId");
		var grade = $(obj).attr("grade");
		var subject = $(obj).attr("subject");
		var term = $(obj).attr("term");
		var params = "activityId="+activityId+"&typeId="+typeId+"&grade="+grade+"&subject="+subject+"&term="+term;
		window.parent.location.href = _WEB_CONTEXT_+"/jy/check/activity/chayueActivity?"+params;
	};
    
	//获取查询参数
	function getQueryString(name) {
	    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null && typeof(r) != "undefined") {
	        return unescape(r[2]);
	    }
	    return "";
	}
})