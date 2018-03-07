define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min'], function (require) {
	var jq=require("jquery");
	jq(document).ready(function () {
		jq('#ytjButton').click(function(){
			jq(this).addClass("upload-bottom_tab_blue");
			jq("#wtjButton").removeClass("upload-bottom_tab_blue");
			jq("#submit_iframe").attr("src","jy/thesis/preSubmit?isSubmit=1&"+ Math.random());
		});
		jq('#wtjButton').click(function(){ 
		    jq(this).addClass("upload-bottom_tab_blue");
		    jq("#ytjButton").removeClass("upload-bottom_tab_blue");
		    jq("#submit_iframe").attr("src","jy/thesis/preSubmit?isSubmit=0&"+ Math.random());
	    });
		jq('.sub_up').click(function (){
			jq(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
			jq.blockUI({ message: jq('#submit_rethink'),css:{width: '800px',height: '480px',top:'15%',left:'20%'}});
		});
		jq('.menu_li_6').click(function(){
			var id = jq(this).attr('data-id');
			var userId=jq(this).attr('data-userId');
			//状态设置为已查看
			jq.ajax(_WEB_CONTEXT_+'/jy/thesis/reviewState?isComment=1&id='+id,{
				type:'put',
				'dataType':'json'
			});
			jq('#commentBox').attr(
					'src',
					'jy/comment/list?authorId='+userId+'&flags=true&resType=10&resId=' + id);
			$("#thesis_review").dialog({width:1125,height:680});
		});
		jq('.close').click(function (){
			  location.reload();
		});
	});
	
});
