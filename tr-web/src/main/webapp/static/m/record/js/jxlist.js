define(["require","zepto","iscroll","placeholder"], function (require) {
	var jq= Zepto; 
	var record_cont_2_list;
	jq(function(){
		init();
	}); 
	var id= jq("input[name='id']").val();
	var type= jq("input[name='type']").val();
    function init() {
    	$(".desc").placeholder({
			 word: '请输入微评'
		});
    	if(jq("#record_cont_2_list").length>0){
    		record_cont_2_list = new IScroll('#record_cont_2_list',{
        		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true,
        		}); 
    	}
    	jq("#listTable").on("click","strong[status='jingxuan']",function(){
    		var one = jq(this).attr('id');
			var name = jq(this).attr('name');
			jq(".name_txt").val(name);
			jq("#one").val(one);
			jq(".btn_confirm").click(function(){
				jingxuan();
			});
			jq(".edit_portfolio_wrap").show();
			jq(".mask").show();
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
		    $('#planName').val($.trim($('#planName').val()));
  		    var form = jq('#searchForm');
  		    form.submit();
    	});
    	
    	jq("#listTable").on("click","span[status='name']",function(){ //查看单个听课记录
    		location.href = _WEB_CONTEXT_+"/jy/lecturerecords/seetopic?id="+$(this).attr("resId");
    	});
    }
    
    function jingxuan(){
    	 var one = jq("#one").val();
	     var desc = jq("#desc").val();
	     location.href = _WEB_CONTEXT_ + "/jy/record/sysJx?id="+id+"&type="+type+"&one="+one+"&desc="+encodeURIComponent(desc);
    }
    
    window.addData = function(data){
    	var content = jq("#listTable",data).html();
    	jq("#listTable").append(content);
    	record_cont_2_list.refresh();
    }
    
});