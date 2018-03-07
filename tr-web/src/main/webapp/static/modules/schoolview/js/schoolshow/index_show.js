/**
 * 加载学校横幅广告（banner）
 * @param orgID
 * @param xdid
 */
function loadSchoolBanner(){
		$.ajax( {
		    url:_WEB_CONTEXT_+'/jy/schoolview/show/loadSchoolBanner', 
		    data:{
		    	orgID:orgID,
		    },
		    async:false,
		    type:'post',    
		    cache:false,    
		    dataType:'html',    
		    success:function(data) {
		    	$('.top4').html(data);
		    	loadSchoolBannerStyle();
		    },    
		    error : function() {    
		    	alert("操作异常！"+this.url);   
		    }    
		});
	}
/**
 * 加载首页通知公告
 */
function loadIndexNotice(){
		$.ajax( {    
		    url:_WEB_CONTEXT_+'/jy/schoolview/show/loadIndexNotice',// 跳转到 action  
		    data:{
		    	orgID:orgID,
		    },
		    async:false,
		    type:'post',    
		    cache:false,    
		    dataType:'html',    
		    success:function(data) {
		    	$('.cont_3_right_1').html(data);
		    },    
		    error : function() {    
		    	alert("操作异常！"+this.url);   
		    }    
		});
	}
/**
 * 加载首页图片新闻
 */
function loadIndexPicNews(){
	$.ajax( {    
	    url:_WEB_CONTEXT_+'/jy/schoolview/show/loadIndexPicNews', 
	    data:{
	    	orgID:orgID,
	    },
	    async:false,
	    type:'post',    
	    cache:false,    
	    dataType:'html',    
	    success:function(data) {
	    	$('.slide_tab').html(data);
	    },    
	    error : function() {    
	    	alert("操作异常！"+this.url);    
	    }    
	});
}

/**
 * 加载首页校長风采
 */
function loadIndexMaster(){
	$.ajax({    
	    url:_WEB_CONTEXT_+'/jy/schoolview/show/viewShow',   
	    data:{    
	    	orgID:orgID,
	    	xdid:xdid,
	    	type:"master"
	    },
	    async:false,
	    type:'post',    
	    cache:false,    
	    dataType:'html',    
	    success:function(data) {
	    	$('#masterShow').html(data);
	    },    
	    error : function() {    
	    	alert("操作异常！"+this.url);    
	    }    
	});
}

/**
 * 加载学校要闻
 */
function loadIndexBigNews(){
	$.ajax({    
	    url:_WEB_CONTEXT_+'/jy/schoolview/show/viewShow',   
	    data:{    
	    	orgID:orgID,
	    	xdid:xdid,
	    	type:"bignews"
	    },
	    async:false,
	    type:'post',    
	    cache:false,    
	    dataType:'html',    
	    success:function(data) {
	    	$('#bignewsShow').html(data);
	    },    
	    error : function() {    
	    	alert("操作异常！"+this.url);    
	    }    
	});
}
/**
 * 加载首页广告鼠标事件
 */
function loadIndexADsStyle(){
	$('.pic_hide_small').hide();
	$('.pic_hide_close').click(function (){
		$('.pic_hide').fadeOut();
		$('.bg_box').fadeOut();
		$('.pic_hide_small').show();
		/* fadeOut */
	})
	$('.pic_hide_small').click(function (){
		$('.pic_hide_small').hide();
		$('.pic_hide').fadeIn();
		$('.bg_box').fadeIn();
	})
}
function loadSchoolBannerStyle(){
	$('#slides').slidesjs({
		width: 1002,
		height: 161,
		play: {
		  active: false,
		  auto: true,
		  interval: 5000,
		  effect: "slide",
		  swap: true,
		  pauseOnHover: true,
		  restartDelay: 2500
		}
	  });
}
	/**
	 * 加载首页学校概况
	 */
	function loadIndexOverview(){
		$.ajax({    
		    url:_WEB_CONTEXT_+'/jy/schoolview/show/viewShow',   
		    data:{    
		    	orgID:orgID,
		    	xdid:xdid,
		    	type:"overview"
		    },
		    async:false,
		    type:'post',    
		    cache:false,    
		    dataType:'html',    
		    success:function(data) {
		    	$('#overviewShow').html(data);
		    },    
		    error : function() {    
		    	alert("操作异常！"+this.url);    
		    }    
		});
	}
		/**
		 * 加载首页滚动系统通知
		 */
		function loadSlideNoticeIndex(){
			$.ajax({    
			    url:_WEB_CONTEXT_+'/jy/schoolview/show/loadSlideNoticeIndex',   
			    data:{    
			    	orgID:orgID,
			    	xdid:xdid,
			    },
			    async:false,
			    type:'post',    
			    cache:false,    
			    dataType:'html',    
			    success:function(data) {
			    	$('.cont').prepend(data);
			    	calcLength();
			    },    
			    error : function() {    
			    	alert("操作异常！"+this.url);    
			    }    
			});
}
		/*
		 * 获取滚动元素长度
		 * */
		function calcLength(){
			var baselength=0;
			$("#notice_index_load a").each(function(){
			      baselength =baselength+$(this).parent().width()+70;//默认滚动间隔宽度70
			    });
			$("#notice_index_load").width(baselength);
		}
		
define(["require","jquery",'jp/jquery.cookie.min'], function (require) {
	$=require("jquery");
	/**
	 * 加载首页广告
	 */
	window.loadIndexADs = function (){
		if(!$.cookie("_indexAds_")){
			$.ajax({
			    url:_WEB_CONTEXT_+'/jy/schoolview/show/indexPicAds', 
			    async:false,
			    type:'post',    
			    cache:false,    
			    dataType:'html',    
			    success:function(data) {
			    	$('._sygg').html(data);
					loadIndexADsStyle();//加载首页广告图片样式
			    },    
			    error : function() {    
			    	alert("操作异常！"+this.url);    
			    }    
			});
			$.cookie("_indexAds_",true,{expires:7});
		}
	}
});