/**
 * 查找所有的学校
 */
function getVIPSchools(){
	$.ajax( {    
	    url:_WEB_ROOT_+'/jy/schoolview/res/vipschool/index/getVIPSchools',// 跳转到 action    
	    data:{    
	    	orgID:orgID,
	    	xdid:xdid
	    },    
	    async:false,
	    type:'post',    
	    cache:false,    
	    dataType:'html',    
	    success:function(data) {
	    	$('#list_1').html(data);
	    },    
	    error : function() {    
	    	alert("请求异常:"+this.url);   
	    }    
	});
}

/**
 * 查找该所学校的备课资源
 */
function getPreparationRes(){
	$.ajax( {    
	    url:_WEB_ROOT_+'/jy/schoolview/res/lessonres/index/getPreparationRes',// 跳转到 action    
	    data:{    
	    	orgID:orgID,
	    	xdid:xdid
	    }, 
	    async:false,
	    type:'post',    
	    cache:false,    
	    dataType:'html',    
	    success:function(data) {
	    	$('.lessoninfo').html(data);
	    },    
	    error : function() {    
	    	alert("请求异常:"+this.url);   
	    }    
	});
}

/**
 * 得到某一个具体的资源
 * @param planType
 */
function getSpecificRes(obj){
	$('.cont_3_right_cont_act').removeClass("cont_3_right_cont_act");
	$(obj).addClass("cont_3_right_cont_act");
	var restype=$(obj).attr("restype");
	if(restype=="all"){//所有资源
		getPreparationRes();
	}else{//教案、课件、反思
		$.ajax( {    
		url:_WEB_ROOT_+'/jy/schoolview/res/lessonres/index/getPreparationRes',// 跳转到 action    
		data:{    
			orgID:orgID,
			xdid:xdid,
			restype:restype
		}, 
		async:false,
		type:'post',    
		cache:false,    
		dataType:'html',    
		success:function(data) {
			$('.lessoninfo').html(data);
		},    
		error : function() {    
			alert("请求异常:"+this.url);   
		}    
	  });
	}
}

/**
 * 得到教研活动
 */
function getTeachActive(restype,obj){
	$('.cont_3_right_cont1_act').removeClass("cont_3_right_cont1_act");
	$(obj).addClass("cont_3_right_cont1_act");
	$.ajax(	{    
		url:_WEB_ROOT_+'/jy/schoolview/res/teachactive/index/refulshactive',// 跳转到 action    
		data:{    
			orgID:orgID,
			xdid:xdid,
			restype:restype
		}, 
		async:false,
		type:'post',    
		cache:false,    
		dataType:'html',    
		success:function(data) {
			$('.actvie').html(data);
		},    
		error : function() {    
			alert("请求异常:"+this.url);   
		}    
	});
}

/**
 * 得到专业成长
 * @param restype
 * @param obj
 */
function getProfession(restype,obj){
	$('.cont_3_right_cont1_pro').removeClass("cont_3_right_cont1_pro");
	$(obj).addClass("cont_3_right_cont1_pro");
	$.ajax( {    
		url:_WEB_ROOT_+'/jy/schoolview/res/progrowth/index/getProfession',// 跳转到 action    
		data:{    
			orgID:orgID,
			xdid:xdid,
			restype:restype
		}, 
		async:false,
		type:'post',    
		cache:false,    
		dataType:'html',    
		success:function(data) {
			$('.profession').html(data);
		},    
		error : function() {    
			alert("请求异常:"+this.url);   
		}    
	});
}

/**
 * 得到成长档案带前六条
 * @param restype
 * @param obj
 */
function getRecordBag(){
	$.ajax( {    
		url:_WEB_ROOT_+'/jy/schoolview/res/recordbags/index/getRecordBag',// 跳转到 action    
		data:{    
			orgID:orgID,
			xdid:xdid
		}, 
		async:false,
		type:'post',    
		cache:false,    
		dataType:'html',    
		success:function(data) {
			$('.bags').html(data);
		},    
		error : function() {    
			alert("请求异常:"+this.url);   
		}    
	});
}
/**
 * 得到热点排行前12条
 * @param restype
 * @param obj
 */
function getTopRes(){
	$.ajax( {    
		url:_WEB_ROOT_+'/jy/schoolview/res/topres/index/getTopRes?restype=1',// 跳转到 action    
		data:{    
			orgID:orgID,
			xdid:xdid
		}, 
		async:false,
		type:'post',    
		cache:false,    
		dataType:'html',    
		success:function(data) {
			$('#box1').html(data);
		},    
		error : function() {    
			alert("请求异常:"+this.url);   
		}    
	});
}
