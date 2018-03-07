define(["require","zepto","iscroll","placeholder"], function (require) {
	var jq= Zepto; 
	var authorId = jq("#authorId").val();
	jq(function(){
		init();
		bindEvent();
	});
    function init() {
    	$(".desc").placeholder({
			 word: '请输入档案袋简介'
		});
    	var  record_cont = new IScroll('#record_cont',{
      		 scrollbars:true,
      		 mouseWheel:true,
      		 fadeScrollbars:true,
      	 	 click:true,
      	});
    	jq('.record_share').click(function (event){
    		var id = jq(this).attr("id");
    		jq(".share_upload_title").find("h3").html("分享");
    		jq(".share_width").find("span").html("您确定要分享吗？分享后，您的小伙伴就可以看到喽！");
    		jq("#share_confirm").click(function(){
    			shareIt(id);
    		});
    		jq('.share_upload_wrap').show();
    		jq('.mask').show(); 
    		event.stopPropagation();//阻止点透
    	});
    	jq('.record_share1').click(function (event){
    		var id = jq(this).attr("id");
    		jq(".share_upload_title").find("h3").html("分享");
    		jq(".share_width").find("span").html("您确定要分享吗？分享后，您的小伙伴就可以看到喽！");
    		jq("#share_confirm").click(function(){
    			shareIt(id);
    		});
    		jq('.share_upload_wrap').show();
    		jq('.mask').show();
    		event.stopPropagation();//阻止点透
    	});
    	jq('.qx_record_share').click(function (event){
    		var id = jq(this).attr("id");
    		jq(".share_upload_title").find("h3").html("取消分享");
    		jq(".share_width").find("span").html("您确定要取消分享吗？");
    		jq("#share_confirm").click(function(){
    			unshareIt(id);
    		});
    		jq('.share_upload_wrap').show();
    		jq('.mask').show();
    		event.stopPropagation();//阻止点透
    	});
    	jq('.qx_record_share1').click(function (event){
    		var id = jq(this).attr("id");
    		jq(".share_upload_title").find("h3").html("取消分享");
    		jq(".share_width").find("span").html("您确定要取消分享吗？");
    		jq("#share_confirm").click(function(){
    			unshareIt(id);
    		});
    		jq('.share_upload_wrap').show();
    		jq('.mask').show();
    		event.stopPropagation();//阻止点透
    	});
    	jq('.close').click(function (){
    		jq('.share_upload_wrap').hide();
    		jq('.del_upload_wrap').hide();
    		jq('.edit_portfolio_wrap').hide();
    		jq('.add_portfolio_wrap').hide();
    		jq(".opinions_comment_wrap").hide();
    		jq('.mask').hide();
    	});
    	jq('.btn_cencel').click(function (){
    		jq('.share_upload_wrap').hide();
    		jq('.del_upload_wrap').hide();
    		jq('.edit_portfolio_wrap').hide();
    		jq('.add_portfolio_wrap').hide();
    		jq('.mask').hide();
    	});
    	jq('.record_more').click(function (event){
    		var plan = jq(this).parent();
    		plan.find('.record_option').show();
          	plan.find(".record_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".record_del").css({'-webkit-animation':'del .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".record_share").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
          	event.stopPropagation();//阻止点透
    	});
    	jq('.record_close').click(function (event){
    		 var plan = jq(this).parent().parent();
    		 plan.find('.record_option').hide(); 
     		 plan.find(".record_edit").css('-webkit-animation', 'edit .5s');
     		 plan.find(".record_del").css('-webkit-animation', 'del .5s');
     		 plan.find(".record_share").css('-webkit-animation', 'share .5s');
     		 event.stopPropagation();//阻止点透
    	});
    	jq('.record_edit').click(function (event){
    		var id = jq(this).attr("id");
			var name = jq(this).attr("name");
			var desc = jq(this).attr("desc");
			jq("#id").val(id);
			jq("#name").val(name);
			if(desc!=null&&desc!=""){
				jq("#desc").val(desc);
			}else{
				jq("#desc").val('');
			}
			jq(".btn_confirm").click(function(){
				editBag();
			});
    		jq('.edit_portfolio_wrap').show();
    		jq('.mask').show();
    		jq('.record_option').hide(); 
    		event.stopPropagation();//阻止点透
    	});
    	jq('.record_del').click(function (event){
    		var id = jq(this).attr("id");
    		var name = jq(this).attr("name");
			var status = jq(this).attr("status");
    		jq("#del_confirm").click(function(){
    			deleteIt(id);
    		});
    		if(status==0){
    			jq(".del_width").find("span").html("删除后将删掉所有内容，您确定要删除“"+name+"”档案袋吗？");
    		}else{
    			jq(".del_width").find("span").html("此档案袋已有人评论，删除后将删掉所有内容，您确定要删除“"+name+"”档案袋吗？");
    		}
    		jq('.del_upload_wrap').show();
    		jq('.mask').show();
    		jq('.record_option').hide(); 
    		event.stopPropagation();//阻止点透
    	});
//    	jq('.record_share1').click(function (){
//    		jq('.share_upload_wrap').show();
//    		jq('.mask').show();
//    		jq('.record_option').hide(); 
//    	});
    	jq('.add_cour').click(function (){
    		jq('.add_portfolio_wrap').show();
    		jq('.mask').show(); 
    	});
    }
    function bindEvent(){
    	jq(".btn_preserve").click(function(){
    		if(jq("#formular").find("input[name='name']").val()!=""){
    			jq("#formular").submit();
    		}
    	});
    	jq(".record_list").click(function(){
    		var type = jq(this).attr("type");
			var id = jq(this).attr("id");
			if(type==1)
				location.href = _WEB_CONTEXT_ + "/jy/record/findRList?id="+id+"&type=1"; //自定义档案袋
			else
				location.href = _WEB_CONTEXT_ + "/jy/record/sysList?id="+id+"&type=0";  //系统档案袋
    	});
    	jq(".record_ping").click(function(event){
    		var bagId = jq(this).attr("bagId");
    		var flag = jq(this).attr("flag");
    		showCommentListBox(7,bagId,flag);
    		event.stopPropagation();//阻止点透
    	});
    }
    
    function shareIt(id){
		//检验是否有内容
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/record/check",
			data:{"id":id},
			success:function(data){
				if(data){
					var type = 1;
					location.href = _WEB_CONTEXT_ + "/jy/record/shareRecord?id="+id+"&type="+type;
				}else{
					successAlert("空档案袋不能分享！");
					jq("#share_confirm").unbind("click");
				}
			}
		});
    }
    
    function unshareIt(id){
		var type = 0;
		location.href = _WEB_CONTEXT_ + "/jy/record/shareRecord?id="+id+"&type="+type;
    }
    
    function deleteIt(id){
		location.href = _WEB_CONTEXT_ + "/jy/record/delRecord?id="+id;
    }
    
    function editBag(){ //修改档案袋
    	jq("#formular2").submit();
    }
    
    window.showCommentListBox = function (planType,planId,isUpdate){
    	$('.mask').show();
		$("#comment_div").show();
		$("#iframe_comment").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=0&resType="+planType+"&resId="+planId+"&authorId="+authorId);
	 	if(isUpdate=="true"){//更新为已查看
	 		$.ajax({  
	 	        async : false,  
	 	        cache:true,  
	 	        type: 'POST',  
	 	        dataType : "json",  
	 	        data:{id:planId},
	 	        url:  _WEB_CONTEXT_+"/jy/record/updatePingLun"
	 	    });
	 	}
     };
    
    
});