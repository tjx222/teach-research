define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init(); 
	});
    function init() { 
    	var content_bottom1 = new IScroll('.content_bottom1',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    	$('.semester').click(function (){
			 $('.semester_wrapper').show(); 
		     $('.mask').show();
		     if($('#hid_term').val()==0||$('#hid_term').val()==null){
		    	 $('.semester_wrapper p').eq(0).addClass("act"); 
		     }else{
		    	 $('.semester_wrapper p').eq(1).addClass("act");
		     }
		}); 
    	//切换学期
    	$(".semester_wrap1 p").click(function () { 
		 	 $( this ).addClass("act").siblings().removeClass("act"); 
		 	 $('.semester_wrapper').hide(); 
		 	 $('.mask').hide();
		 	 $('#hid_term').val($(this).attr('data-term'));
		 	 $("#hiddenForm").submit();
		 });
    	$('.semester_wrapper').click(function(){
            $('.semester_wrapper').hide(); 
            $('.mask').hide();
        });
    	//瀑布流加载下一页
    	window.addData=function(data){
			var content = $("#loadmore",data).html();
			$("#loadmore").append(content);
			var content_bottom1 = new IScroll('.content_bottom1',{
	      		scrollbars:true,
	      		mouseWheel:true,
	      		fadeScrollbars:true,
	      		click:true,
	      	});
		}
    	//参与或查看
    	$('.activity_tch').click(function(){
    		var listType=$('#listType').val();
    		var activityId=$(this).attr('data-id');
    		var typeId=$(this).attr('data-typeId');
    		var isOver=$(this).attr('data-isOver');
    		var startDate=$(this).attr('data-startDate');
    		var activity=$(this).attr('data-activity');
    		/*if(listType==0){
    			if(ifActivityStart(startDate)){
        			if(typeId==1){//同备教案
        				window.location.href(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"");
        			}else if(typeId==2){//主题研讨
        				window.location.href(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"");
        			}
        		}
    		}else{*/
			if(activity=="activity"){
				if(typeId==1){//同备教案
        			if(isOver){//已结束，则查看
        				window.location.href=_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId;
        			}else{//参与
        				if(ifActivityStart(startDate)){
        					window.location.href=_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId;
        				}
        			}
        		}else if(typeId==2){//主题研讨
        			if(isOver){//已结束，则查看
        				window.location.href=_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId;
        			}else{//参与
        				if(ifActivityStart(startDate)){
        					window.location.href=_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId;
        				}
        			}
        		}else if(typeId == 3){
        			if(isOver){//已结束，则查看
        				window.location.href=_WEB_CONTEXT_+"/jy/activity/viewSpjyActivity?id="+activityId;
        			}else{//参与
        				if(ifActivityStart(startDate)){
        					window.location.href=_WEB_CONTEXT_+"/jy/activity/joinSpjyActivity?id="+activityId;
        				}
        			}
        		}
			}else if(activity=="scool"){
				if(typeId==1){//同备教案
					if(isOver){//已结束，则查看
						window.location.href=_WEB_CONTEXT_+"/jy/schoolactivity/viewTbjaSchoolActivity?id="+activityId;
					}else{//参与
						window.location.href=_WEB_CONTEXT_+"/jy/schoolactivity/joinTbjaSchoolActivity?id="+activityId;
					}
				}else if(typeId==2 || typeId == 3){//主题研讨
					if(isOver){//已结束，则查看
						window.location.href=_WEB_CONTEXT_+"/jy/schoolactivity/viewZtytSchoolActivity?id="+activityId;
					}else{//参与
						window.location.href=_WEB_CONTEXT_+"/jy/schoolactivity/joinZtytSchoolActivity?id="+activityId;
					}
				}else if (typeId == 4) {
					window.open(_WEB_CONTEXT_ + "/jy/schoolactivity/viewZbkt?id=" + activityId + "&listType=" + listType, "_self");
				}
			}else{
				if(typeId==1){//同备教案
					if(isOver){//已结束，则查看
						window.location.href=_WEB_CONTEXT_+"/jy/region_activity/viewTbjaRegionActivity?id="+activityId;
					}else{//参与
						if(ifActivityStart(startDate)){
							window.location.href=_WEB_CONTEXT_+"/jy/region_activity/joinTbjaRegionActivity?id="+activityId;
						}
					}
				}else if(typeId==2){//主题研讨
					if(isOver){//已结束，则查看
						window.location.href=_WEB_CONTEXT_+"/jy/region_activity/viewZtytRegionActivity?id="+activityId;
					}else{//参与
						if(ifActivityStart(startDate)){
							window.location.href=_WEB_CONTEXT_+"/jy/region_activity/joinZtytRegionActivity?id="+activityId;
						}
					}
				}
			}
    			
    	});
    	//判断活动是否开始
    	window.ifActivityStart = function(startDate){
    		 if(startDate.length > 0){
    			var currentDate = new Date(+new Date()+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
    		    var startDateTemp = startDate.split(" ");    
    		    var arrStartDate = startDateTemp[0].split("-");   
    		    var arrStartTime = startDateTemp[1].split(":"); 

    			var currentDateTemp = currentDate.split(" ");    
    		    var arrStartDate1 = currentDateTemp[0].split("-");   
    		    var arrStartTime1 = currentDateTemp[1].split(":");   

    			var allStartDate = new Date(arrStartDate[0], arrStartDate[1], arrStartDate[2], arrStartTime[0], arrStartTime[1], arrStartTime[2]); 
    			var allcurrentDate = new Date(arrStartDate1[0], arrStartDate1[1], arrStartDate1[2], arrStartTime1[0], arrStartTime1[1], arrStartTime1[2]); 
    			if (allStartDate.getTime() > allcurrentDate.getTime()) {   
    		        successAlert("该活动还未开始，请于"+startDate+"来准时参加活动");   
    		        return false;   
    			} else {   
    			    //successAlert("活动开始了");   
    			    return true;   
    			       }   
    			} else {   
    			    return true;   
    			}   

    	}
    }
});