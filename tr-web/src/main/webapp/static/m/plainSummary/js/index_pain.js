define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init();
	});
    function init() { 
    	var annunciate_b_wrap = new IScroll('#annunciate_b_wrap',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
      	});
    	$('.viewPlanSummary').click(function(){
    		var id=$(this).attr("data-id");
    		//scanResFile_m(resId); 
    		$('#view_'+id).css("display","none");
    		scanResFile_m_url(_WEB_CONTEXT_+"/jy/planSummary/scanFiles/"+id);
    		$.ajax({
				type:'put',
				dataType:'json',
				url:_WEB_CONTEXT_+'/jy/planSummary/punishViews/'+id,
				data:{'psId':id},
				success : function(data) {}
			});
    	});
    	//瀑布流加载
    	window.addData=function(data){
    		var content = $("#pageContent",data).html();
			$("#pageContent").append(content);
			var annunciate_b_wrap = new IScroll('#annunciate_b_wrap',{
	      		 scrollbars:true,
	      		 mouseWheel:true,
	      		 fadeScrollbars:true,
	      		 click:true,
	      	});
    	}
    }
});