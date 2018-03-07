define(["require","zepto","iscroll",'common/dateFormat'], function (require) {
	var dateFormat = require("common/dateFormat"); 
	var jq= Zepto; 
	var  myScroll;
	 jq(function(){
			init();
			bindEvent();
		});
	function init() {  
		myScroll = new IScroll('#wrap',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
	}
	
	function bindEvent(){
		jq("#listdiv").find(".activity_tch_right").click(function(){
			canyu_chakan(jq(this).attr("activityId"),jq(this).attr("typeId"),jq(this).attr("isOver"),jq(this).attr("startDate"));
		});
	}
	
	//瀑布流加载下一页资源
	window.addData = function(data){
		var activityList = data.activityList.datalist;
		for(var i=0;i<activityList.length;i++){
			var activity = activityList[i];
			var startTime = activity.startTime;
			var endTime = activity.endTime;
			if(startTime!=null && startTime!=""){
				startTime = startTime.substring(5,16);
			}else{
				startTime = "~";
			}
			if(endTime!=null && endTime!=""){
				endTime = endTime.substring(5,16);
			}else{
				endTime = "~";
			}
			var htmlStr = "";
			htmlStr = htmlStr+'<div class="activity_tch" activityId="'+activity.id+'" typeId="'+activity.typeId+'" isOver="'+activity.isOver+'" startDate="'+activity.startTime+'">';
			if(activity.typeId == 1){
				htmlStr = htmlStr+'<div class="activity_tch_left">同<br />备<br />教<br />案</div>';
			}else if(activity.typeId == 2){
				htmlStr = htmlStr+'<div class="activity_tch_left1">主<br />题<br />研<br />讨</div>';
			}
			htmlStr = htmlStr + '<div class="activity_tch_right">'+
								'<h3><span class="title">'+activity.activityName+'</span>';
			if(activity.isOver){
				htmlStr = htmlStr + '<span class="end"></span>';
			}
			htmlStr = htmlStr +'</h3><div class="option">'+
								'<div class="promoter"><strong></strong>发起人：<span>'+activity.organizeUserName+'</span></div>'+
								'<div class="partake_sub"><strong></strong>参与学科：<span>'+activity.subjectName+'</span></div>'+
								'<div class="partake_class"><strong></strong>参与年级：<span>'+activity.gradeName+'</span></div></div><div class="option"><div class="time"><strong></strong>'+
								'<span>'+startTime+'至'+endTime+'</span></div>'+
								'<div class="discussion_number"><strong></strong>讨论数：<span>'+activity.commentsNum+'</span></div></div></div></div>';
			var addDom = jq(htmlStr);
			addDom.click(function(){
				canyu_chakan(jq(this).attr("activityId"),jq(this).attr("typeId"),jq(this).attr("isOver"),jq(this).attr("startDate"));
			});
			jq("#listdiv").append(addDom);
			myScroll.refresh();
		}
	}
	
	window.canyu_chakan = function(activityId,typeId,isOver,startDateStr){
		if(typeId==1){//同备教案
			if(isOver=='true'){//已结束，则查看
				window.open(_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId,"_self");
			}else if(isOver=='false'){//参与
				if(ifActivityStart(dateFormat.format(new Date(),"yyyy-MM-dd hh:ff:ss"),startDateStr)){
					window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"_self");
				}
			}
		}else if(typeId==2){//主题研讨
			if(isOver=='true'){//已结束，则查看
				window.open(_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId,"_self");
			}else if(isOver=='false'){//参与
				if(ifActivityStart(dateFormat.format(new Date(),"yyyy-MM-dd hh:ff:ss"),startDateStr)){
					window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_self");
				}
			}
		}else if(typeId==3){//视频教研
			if(isOver=='true'){//已结束，则查看
				window.open(_WEB_CONTEXT_+"/jy/activity/viewSpjyActivity?id="+activityId,"_self");
			}else if(isOver=='false'){//参与
				if(ifActivityStart(dateFormat.format(new Date(),"yyyy-MM-dd hh:ff:ss"),startDateStr)){
					window.open(_WEB_CONTEXT_+"/jy/activity/joinSpjyActivity?id="+activityId,"_self");
				}
			}
		}
	}
	
	//判断活动是否开始
	window.ifActivityStart = function(currentDate,startDate){
		 if(startDate.length > 0){
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
			    return true;   
	        }   
		} else {   
		    return true;   
		}   

	}
	
})