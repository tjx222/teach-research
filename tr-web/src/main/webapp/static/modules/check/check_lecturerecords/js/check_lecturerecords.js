define(["require","jquery"], function (require) {
	$(document).ready(function () {
		$(".semester").chosen({disable_search : true});
	    $(".ol_grade li").click(function (){ 
	    	//$(this).addClass("li_act").siblings().removeClass("li_act");  
	    	$("#gradeId").val($(this).attr("gradeId"));
	    	$("#form1").submit();
	    });
	    $(".ol_subject li").click(function (){ 
	    	//$(this).addClass("ol_subject_li_act").siblings().removeClass("ol_subject_li_act");  
	    	$("#subjectId").val($(this).attr("subjectId"));
	    	$("#form1").submit();
	    });
	    
	    $(".teacher_info").click(function(){
	    	window.location.href = _WEB_CONTEXT_+"/jy/check/lectureRecords/toLectureRecordsList?flago=0&lecturepeopleId="+$(this).attr("userId")+"&term="+$(this).attr("termId")+"&gradeId="+$("#gradeId").val()+"&subjectId="+$("#subjectId").val();
	    });
	    
	    $(".page_wprd").find("div").click(function(){
	    	var num = $(this).attr("num");
	    	if(num=='noPre'){
	    		alert("没有上一篇了");
	    		return false;
	    	}else if(num=='noNext'){
	    		alert("没有下一篇了");
	    		return false;
	    	}
	    	window.location.href = _WEB_CONTEXT_+"/jy/check/lectureRecords/toCheckLectureRecords?lecturepeopleId="+$(this).attr("userId")+"&term="+$(this).attr("term")+"&flags="+num;
	    });
	    
	    $(".check_select_wrap_l").click(function(){
	    	window.location.href = _WEB_CONTEXT_+"/jy/check/lectureRecords/toCheckTeacherIndex";
	    });
	    $(".check_select_wrap_r").click(function(){
	    	window.location.href = _WEB_CONTEXT_+"/jy/check/lectureRecords/toCheckLeaderIndex";
	    });
	    
	    $(".olgrade li").click(function (){ 
	    	$("#gradeId").val($(this).attr("gradeId"));
	    	$("#form1").submit();
	    });
	    $(".olsubject li").click(function (){ 
	    	$("#sysRoleId").val($(this).attr("roleId"));
	    	$("#form1").submit();
	    });
	    
	    $(".leader_info").click(function(){
	    	window.location.href = _WEB_CONTEXT_+"/jy/check/lectureRecords/toLectureRecordsList?flago=1&lecturepeopleId="+$(this).attr("userId")+"&term="+$(this).attr("termId")+"&sysRoleId="+$("#sysRoleId").val()+"&gradeId="+$(this).attr("gradeId");
	    }); 
		$(".table_th th").last().css({"border-right":"none"});
		$(".table_td tr").each(function (){
			$(this).find("td").last().css({"border-right":"none"});
		});
		$(".table_td tr").last().css({"border-bottom":"none"});
		$(".table_td_wrap table tr:even").css({"background":"#f9f9f9"}); 
	    
		$('.ser_btn1').click(function(){
			var search=$('.ser_txt').val();
			window.open("https://www.baidu.com/s?q1="+search+"&gpc=stf");
		});
		
		$("#grade_subject").html($(".li_act").html()+$(".ol_subject_li_act").html());
		
		var li1 = $(".in_reconsideration_see_title_box .li_bg ");
		var window1 = $(".out_reconsideration_see_title_box ol");
		var left1 = $(".out_reconsideration_see_title_box .scroll_leftBtn");
		var right1 = $(".out_reconsideration_see_title_box .scroll_rightBtn"); 
		window1.css("width", li1.length*95+"px");  
		if(li1.length >= 7){
			left1.show();
			right1.show();
		}else{
			left1.css({"visibility":"hidden"});
			right1.css({"visibility":"hidden"});
		} 
		var lc1 = 0;
		var rc1 = li1.length-7; 
		left1.click(function(){
			if (lc1 < 1) {
				return;
			}
			lc1--;
			rc1++;
			window1.animate({left:'+=90px'}, 500);  
		});

		right1.click(function(){
			if (rc1 < 1){
				return;
			}
			lc1++;
			rc1--;
			window1.animate({left:'-=90px'}, 500); 
		});
	    
	});
	
});