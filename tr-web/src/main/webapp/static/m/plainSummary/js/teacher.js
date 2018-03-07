define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;	
	$(function(){ 
		init();
		initQueryParam();
		loadData();
	});
    function init() {    	
    	$('.check_content_block span').click(function (){ 
    		$('.check_menu_wrap').show();
    		$('.mask').show(); 
    		var myScroll2 = new IScroll('#wrap2',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true, 
          		click:true
          	});
    	});
    	$('.check_content_block1 span').click(function (){ 
    		$('.check_menu1_wrap').show();
    		$('.mask').show(); 
    		var myScroll3 = new IScroll('#wrap3',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true, 
          		click:true
          	});
    	});
    	 $("#wrap2 p").click(function () { 
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu_wrap').hide();
		 	 $('.mask').hide(); 
		 	 $('.check_content_block span').html($(this).text());
		 	 $('#selectsubject').attr("data",$(this).attr('data'));
		 	 loadData(); 
		  });
    	
    	$('#wrap3 p').click(function(){
             $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.check_menu1_wrap').hide();
		 	 $('.mask').hide();
		 	 $('.check_content_block1 span').html($(this).text());
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
			var gradeId = $('#selectgrade').attr('data');
			var subjectId = $('#selectsubject').attr('data');
			var name = "s"+subjectId+"g"+gradeId;
    		var html = HTMLDATA[name];
    		if(!html){
				$.get('./jy/planSummaryCheck/teacher/userlist?gradeId='+ gradeId + '&subjectId=' + subjectId,
						function(data) {
							HTMLDATA[name] = data;
							$('#userListContent').html(data);
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
    	}
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
    
	//获取查询参数
	function getQueryString(name) {
	    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
	    var r = window.location.search.substr(1).match(reg);
	    if (r != null && typeof(r) != "undefined") {
	        return unescape(r[2]);
	    }
	    return "";
	}
	
});
