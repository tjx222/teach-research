define(["require","zepto","iscroll","placeholder"], function (require) {
	var jq= Zepto; 
	var id = jq("#bagId").val();
	var type = jq("#bagType").val();
	var act_list_yjx;
	jq(function(){
		init();
		bindEvent();
	});
    function init() { 
    	act_list_yjx = new IScroll('#act_list_yjx',{
    		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
    		}); 
    	jq(".btn_cencel").click(function(){
    		jq(".btn_confirm").unbind("click");
    		jq(".del_upload_wrap").hide();
    		jq(".mask").hide();
    	});
    	jq(".close").click(function(){
    		jq(".btn_confirm").unbind("click");
    		jq(".del_upload_wrap").hide();
    		jq(".edit_portfolio_wrap").hide();
    		jq(".mask").hide();
    	});
    	jq('.portfolio_edit').click(function (){
    		jq(this).parent().siblings().find('.name_txt').css({'border':'0.083rem #dbdbdb solid'});
    		jq(this).parent().siblings().find('.portfolio_btn').show();
    		jq(this).parent().siblings().find('.border_bottom').show();
    		jq(this).parent().siblings().find('.note').show();
    	});
    	jq("#jingxuan").click(function(){
  		  	location.href = _WEB_CONTEXT_ + "/jy/record/findSysList?id="+id+"&type="+type;
    	});
    }
    function bindEvent(){
    	jq(".del").click(function(event){ //绑定删除
    		var one = jq(this).attr("id");
    		jq(".btn_confirm").click(function(){
    			deleteIt(one);
    		});
    		jq(".del_upload_wrap").show();
    		jq(".mask").show();
    		event.stopPropagation();//阻止点透
    	});
    	jq(".wei").click(function(event){ //绑定微评
    		  var one = jq(this).attr("resId");
	  		  var name = jq(this).attr("resName");
	  		  var desc = jq(this).attr("title");
	  		  jq("#name").val(name);
	  		  jq("#one").val(one);
	  		  jq("#desc").val(desc);
	  		  jq(".btn_confirm").click(function(){
	  			  saveWeiping();
	  		  });
	  		  jq(".edit_portfolio_wrap").show();
	  		  jq(".mask").show();
		  		$(".desc").placeholder({
		   			 word: '请输入微评'
		   		});
	  		  event.stopPropagation();//阻止点透
    	});
    	jq(".record_cont_bottom_win").on("click",".activity_tch_right",function(){
    		var activityId = jq(this).attr("activityId");
    		var typeId = jq(this).attr("typeId");
    		if(typeId==1){//同备教案
				window.open(_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId,"_self");
			}else if(typeId==2){//主题研讨
				window.open(_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId,"_self");
			}
    	});
    }
    
    
    function deleteIt(one){
    	location.href = _WEB_CONTEXT_ + "/jy/record/delRecords?id="+one+"&bagId="+id+"&type="+type+"&page=";
    }
    function saveWeiping(){
    	var one = jq("#one").val();
		var desc = jq("#desc").val();
		location.href = _WEB_CONTEXT_ + "/jy/record/sysJxUpdate?id="+id+"&type="+type+"&one="+one+"&page=&desc="+encodeURIComponent(encodeURIComponent(desc));
    }
    
    window.addData = function(data){
    	var content = jq(".record_cont_bottom_win",data).html();
    	content.find(".del").click(function(event){ //绑定删除
    		var one = jq(this).attr("id");
    		jq(".btn_confirm").click(function(){
    			deleteIt(one);
    		});
    		jq(".del_upload_wrap").show();
    		jq(".mask").show();
    		event.stopPropagation();//阻止点透
    	});
    	content.find(".wei").click(function(event){ //绑定微评
	  		  var one = jq(this).attr("resId");
		  		  var name = jq(this).attr("resName");
		  		  var desc = jq(this).attr("title");
		  		  jq("#name").val(name);
		  		  jq("#one").val(one);
		  		  jq("#desc").val(desc);
		  		  jq(".btn_confirm").click(function(){
		  			  saveWeiping();
		  		  });
		  		  jq(".edit_portfolio_wrap").show();
		  		  jq(".mask").show();
		  		  event.stopPropagation();//阻止点透
	  	});
    	jq(".record_cont_bottom_win").append(content);
    	act_list_yjx.refresh();
    }
    
});