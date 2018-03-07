define(["require","zepto","iscroll","placeholder"], function (require) {
	var jq= Zepto; 
	var act_list
	jq(function(){
		init();
	}); 
	var id= jq("input[name='id']").val();
	var type= jq("input[name='type']").val();
    function init() {
    	
    	act_list = new IScroll('#act_list',{
    		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true
    		}); 
    	jq("strong[status='jingxuan']").click(function(event){
    		tojingxuan(this);
			event.stopPropagation();//阻止点透
    	});
    	jq('.close').click(function (){ 
    		jq('.edit_portfolio_wrap').hide();
    		jq("#desc").val("");
    		jq('.mask').hide();
    		jq(".btn_confirm").unbind("click");
    	});
    	jq(".btn_cencel").click(function(){
    		jq('.edit_portfolio_wrap').hide();
    		jq("#desc").val("");
    		jq('.mask').hide();
    		jq(".btn_confirm").unbind("click");
    	});
    	
    	jq(".search_btn").click(function(){ //绑定查询按钮
		    jq('#planName').val(jq.trim(jq('#planName').val()));
  		    var form = jq('#searchForm');
  		    form.submit();
    	});
    	
    	jq(".activity_tch_right").click(function(){
    		canyu_chakan(this);
    	});
    }
    
    function jingxuan(){
    	 var one = jq("#one").val();
	     var desc = jq("#desc").val();
	     location.href = _WEB_CONTEXT_ + "/jy/record/sysJx?id="+id+"&type="+type+"&one="+one+"&desc="+encodeURIComponent(desc);
    }
    
    window.addData = function(data){
    	var content = jq(".record_cont_bottom_win",data);
    	jq(content).find("strong[status='jingxuan']").click(function(event){
    		tojingxuan(this);
			event.stopPropagation();//阻止点透
    	});
    	jq(content).find(".activity_tch_right").click(function(event){
    		canyu_chakan(this);
    	});
    	jq(".record_cont_bottom_win").append(jq(content).find(".activity_tch"));
    	act_list.refresh();
    }
    
    function canyu_chakan(obj){
    	var activityId = jq(obj).attr("activityId");
		var typeId = jq(obj).attr("typeId");
		var isOver = jq(obj).attr("isOver");
		if(typeId==1){//同备教案
			if(isOver){//已结束，则查看
				window.open(_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId,"_self");
			}else{//参与
				window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId,"_self");
			}
		}else if(typeId==2){//主题研讨
			if(isOver){//已结束，则查看
				window.open(_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId,"_self");
			}else{//参与
				window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId,"_self");
			}
		}
    }
    
    function tojingxuan(obj){
    	var one = jq(obj).attr('id');
		var name = jq(obj).attr('name');
		jq(".name_txt").val(name);
		jq("#one").val(one);
		jq(".btn_confirm").click(function(){
			jingxuan();
		});
		jq(".edit_portfolio_wrap").show();
		jq(".mask").show();
		$(".desc").placeholder({
			 word: '请输入微评'
		});
    }
    
    
});