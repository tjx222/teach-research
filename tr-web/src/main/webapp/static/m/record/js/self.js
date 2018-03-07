define(["require","zepto","iscroll","placeholder"], function (require) {
	var jq= Zepto; 
	var id = jq("#bagId").val();
	var type = jq("#bagType").val();
	jq(function(){
		init();
		bindEvent();
	});
    function init() { 
    	var c_b_w_list = new IScroll('#c_b_w_list',{
    		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true,
    		}); 
    	jq('.record_word_2').click(function (){
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
    	jq(".content_bottom_width").on("click",'.record_word_21',function (event){
    		var plan = jq(this).parent();
    		plan.find('.record_option').show();
          	plan.find(".record_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".record_del").css({'-webkit-animation':'del .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".record_down").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
          	event.stopPropagation();//阻止点透
    	});
    	jq(".content_bottom_width").on("click",'.record_close',function (event){
			 var plan = jq(this).parent().parent();
			 plan.find('.record_option').hide(); 
				 plan.find(".record_edit").css('-webkit-animation', 'edit .5s');
				 plan.find(".record_del").css('-webkit-animation', 'del .5s');
				 plan.find(".record_down").css('-webkit-animation', 'share .5s');
				 event.stopPropagation();//阻止点透
		});
    	$('.add_cour_wrap_div').click(function (){
    		jq("#uploadId").show();
			jq("#save").show();
			jq(".edit_strong").hide();
			jq("#save1").hide();
			jq("#name").val("");
			jq("#kjms").val("");
    		jq('.edit1_portfolio_wrap').show();
    		jq('.mask').show();
    	});
    	jq(".btn_cencel").click(function(){
    		jq('.edit_portfolio_wrap').hide();
    		jq('.del_upload_wrap').hide(); 
    		jq('.mask').hide();
    		jq(".btn_confirm").unbind("click");
    	});
    	jq(".content_bottom_width").on("click",'.record_word_3',function (){
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
    	jq(".record_del").click(function(event){ //绑定删除
    		toDeleteIt(this);
    		event.stopPropagation();//阻止点透
    	});
    	jq(".record_edit").click(function(event){ //绑定修改
    		toEditIt(this);
    		event.stopPropagation();//阻止点透
    	});
    }
    function bindEvent(){
    	jq(".edit_strong").find("span").click(function(){
    		jq(this).parent().hide();
    		jq("#uploadId").show();
    		jq("#save").show();
			jq("#save1").hide();
    	});
    }
    
    function updateWeiping(one){ //更新微评
    	var desc = jq("#desc").val();
    	location.href = _WEB_CONTEXT_ + "/jy/record/updateDesc?id="+one+"&page=&desc="+encodeURIComponent(encodeURIComponent(desc));
    }
    
    window.afterUpload = function(data){
    	jq("#resId").val(data.data);
		if(data.code==0){
			successAlert("保存成功！",false,function(){
				save();
			});
		}
    }
    
    //上传前
    window.uploading = function(){
    	if(jq("#name").val()==""){
    		successAlert("请输入名称");
    		return false;
    	}else if(jq("input[name='file']")[0].files.length==0){
    		successAlert("请选择文件");
    		return false;
    	}
    	$("#save").hide();
    	$(".btn_sc").show();
    	$(".close").click(function(){
    		window.location.reload();
    	});
    	return true;
    }
    
    window.save = function(){
    	jq("#kj_form").submit();
    }
    
    function deleteIt(recordId,id){
    	$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/record/delete",
			data:{"id":recordId,"bagId":id},
			success:function(data){
				if(data){
					successAlert("删除成功！",false,function(){
						location.href = location.href;
					});
				}else{
					successAlert("删除失败！");
				}
			}
		});
    }
    
    window.addData = function(data){
    	var content = jq(".content_bottom_width",data);
    	jq(content).find(".record_del").click(function(){
    		toDeleteIt(this);
    	});
    	jq(content).find(".record_edit").click(function(){
    		toEditIt(this);
    	});
    	jq(content).find(".add_cour_wrap").remove();
    	jq(".content_bottom_width").append(jq(content).find(".record_word"));
    }
    
    function toDeleteIt(obj){
    	var recordId = jq(obj).attr("recordId");
		var bagId = jq(obj).attr("bagId");
		jq(".btn_confirm").click(function(){
			deleteIt(recordId,bagId);
		});
		jq(".del_upload_wrap").show();
		jq(".mask").show();
    }
    
    function toEditIt(obj){
    	var recordId = jq(obj).attr("recordId");
		var bagId = jq(obj).attr("bagId");
		var resId = jq(obj).attr("resId");
		var recordName = jq(obj).attr("recordName");
		var desc = jq(obj).attr("desc");
		if(resId!=""){
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/courseware/getFileById.json",
				data:{"resId":resId},
				success:function(data){
					if(data.res!=null){
					    jq(".edit_strong").find("a").html(cutStr(data.res.name,32,true));
						jq("#resId").val(resId);
						jq("#uploadId").hide();
						jq("#save").hide();
						jq(".edit_strong").show();
						jq("#save1").click(function(){
							save();
						});
						jq("#save1").show();
					}
				}
			});
		}
		jq("#recordId").val(recordId);
		jq("#id").val(bagId);
		jq("#name").val(recordName);
		jq("#kjms").val(desc);
		jq(".edit1_portfolio_title").find("h3").html("修改文件");
		jq(".edit1_portfolio_wrap").show();
		jq(".mask").show();
    }
    
    jq('.content_bottom_width').on("click","p",function (){ //浏览资源
		scanResFile_m(jq(this).attr("resId"));
	});
    
});