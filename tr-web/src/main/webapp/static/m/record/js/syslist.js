define(["require","zepto","iscroll","placeholder"], function (require) {
	var jq= Zepto; 
	var id = jq("#bagId").val();
	var type = jq("#bagType").val();
	var c_b_w_list;
	jq(function(){
		init();
		bindEvent();
	});
    function init() { 
    	c_b_w_list = new IScroll('#c_b_w_list',{
    		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
    		}); 
    	jq('.content_bottom_width').on("click",".record_word_2",function (){
    		var one = jq(this).attr("id");
    		var name = jq(this).attr("name");
    		jq(".del_width").find("span").html("您确定要删除该文件吗？");
    		jq(".del_upload_wrap").find(".btn_confirm").click(function(){
    			deleteIt(one);
    		});
    		jq('.del_upload_wrap').show(); 
    		jq('.mask').show();
    	});
    	jq('.close').click(function (){ 
    		jq('.edit_portfolio_wrap').hide();
    		jq('.del_upload_wrap').hide(); 
    		jq('.mask').hide();
    		jq(".btn_confirm").unbind("click");
    		jq('.edit1_portfolio_wrap').hide();
    	});
    	jq('.record_word_21').click(function (event){
    		var plan = jq(this).parent();
    		plan.find('.record_option').show();
          	event.stopPropagation();//阻止点透
    	});
    	jq('.record_close').click(function (event){
			 var plan = jq(this).parent().parent();
			 plan.find('.record_option').hide(); 
				 event.stopPropagation();//阻止点透
		});
    	jq(".btn_cencel").click(function(){
    		jq('.edit_portfolio_wrap').hide();
    		jq('.del_upload_wrap').hide(); 
    		jq('.mask').hide();
    		jq(".btn_confirm").unbind("click");
    	});
    	jq('.content_bottom_width').on("click",'.record_word_3',function (){
    		var one = jq(this).attr("resId");
  		    var name = jq(this).attr("resName");
  		    var desc = jq(this).attr("title");
  		    jq(".name_txt").val(name);
  		    jq("#desc").val(desc);
  		    jq(".btn_confirm").click(function(){
  		    	updateWeiping(one);
  		    });
    		jq('.edit_portfolio_wrap').show();
    		jq('.mask').show();
    		$(".desc").placeholder({
	   			 word: '请输入微评'
	   		});
    	});
    	jq('.portfolio_edit').click(function (){
    		jq(this).parent().siblings().find('.name_txt').css({'border':'0.083rem #dbdbdb solid'});
    		jq(this).parent().siblings().find('.portfolio_btn').show();
    		jq(this).parent().siblings().find('.border_bottom').show();
    		jq(this).parent().siblings().find('.note').show();
    	});
    	
    	jq('.content_bottom_width').on("click","p",function (){ //浏览资源
    		scanResFile_m(jq(this).attr("resId"));
    	});
    	
    }
    function bindEvent(){
    	jq(".add_cour_wrap").click(function(){
    		location.href = _WEB_CONTEXT_ + "/jy/record/findSysList?id="+id+"&type="+type;
    	});
    	jq(".record_word_2").click(function(){ //绑定删除
    		var one = jq(this).attr("id");
    		var name = jq(this).attr("name");
    			location.href = we + "/jy/record/delRecords?id="+one+"&bagId="+id+"&type="+type+"&page="+page;
    	});
    }
    
    function deleteIt(one){
    	location.href = _WEB_CONTEXT_ + "/jy/record/delRecords?id="+one+"&bagId="+id+"&type="+type;
    }
    
    function updateWeiping(one){ //更新微评
    	var desc = jq("#desc").val();
    	location.href = _WEB_CONTEXT_ + "/jy/record/sysJxUpdate?id="+id+"&type="+type+"&one="+one+"&desc="+encodeURIComponent(encodeURIComponent(desc));
    }
    
    window.addData = function(data){
    	var content = jq(".content_bottom_width",data);
    	content.find(".add_cour_wrap").remove();
    	jq(".content_bottom_width").append(jq(content.html()));
    	c_b_w_list.refresh();
    }
    
});