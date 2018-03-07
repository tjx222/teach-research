define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min'], function (require) {
	var jq=require("jquery");
	jq(function(){
		list();
	});
	
function  list() {
	var we = _WEB_CONTEXT_;
		jq('.close').click(function(){
			if(jq(this).parent().html().indexOf('添加成功')==-1){
				location.replace(location.href);
			}else{
				window.location.href=window.location.href.split('?')[0];
			}
		})

		jq('.add').click(function (){
//			jq.blockUI({ message: jq('#_add'),css:{width: '546px',height: '360px',top:'20%',left:'30%'}});
			jq("#_add").dialog({width:546,height:360});
		});
		
		
        jq("#formular").validationEngine('attach', {'showPrompts': false });
        jq("#button").click(function(){
             //点击新增提交按钮
             jq("#formular").submit();
        });
		
		jq("#formular2").validationEngine('attach', {'showPrompts': false });
        jq("#button2").click(function(){
             //点击新增提交按钮
             jq("#formular2").submit();
        });

				
		jq('.li_del').click(function(){
			//点击删除按钮
			var id = jq(this).attr("id");
			var name = jq(this).attr("name");
			var status = jq(this).attr("status");
			if(status==0){
				//未评论
				if(confirm("删除后将删掉所有内容，您确定要删除“"+name+"”档案袋吗？"))
				location.href = we + "/jy/record/delRecord?id="+id;
			}else{
				//已评论
				if(confirm("此档案袋已有人评论，删除后将删掉所有内容，您确定要删除“"+name+"”档案袋吗？"))
					location.href = we + "/jy/record/delRecord?id="+id;
			}
		})
		
		jq('.li_edit').click(function(){
			//点击修改按钮
			var id = jq(this).attr("id");
			var name = jq(this).attr("name");
			var desc = jq(this).attr("desc");
			jq("#id").val(id);
			jq("#formular2 input[name='name']").val(name);
			if(desc!=null&&desc!=""){
				jq("#formular2 textarea[name='desc']").val(desc);
			}else{
				jq("#formular2 textarea[name='desc']").val('');
			}
			$("#_modify").dialog({width:546,height:360});
		})
		
		jq('.li_share').click(function(){
			//点击分享按钮
			var id = jq(this).attr("id");
			//检验是否有内容
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/record/check",
				data:{"id":id},
				success:function(data){
					if(data){
						var type = 1;
						if(confirm("您确定要分享吗？分享成功后，您的小伙伴们就可以看到喽！"))
						location.href = we + "/jy/record/shareRecord?id="+id+"&type="+type;
					}else{
						alert("空档案袋不能分享！");
					}
				}
			});
			
			
		
		})
		
		jq('.li_share_cancle').click(function(){
			//点击取消分享按钮
			var id = jq(this).attr("id");
			var type = 0;
			if(confirm("您确定要取消分享吗？"))
			location.href = we + "/jy/record/shareRecord?id="+id+"&type="+type;
		})
		
		jq("#buttonn").click(function(){
			//点击添加完成后提示中“否”的按钮
			jq.unblockUI();
			jq(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
			window.location.href=window.location.href.split('?')[0];
		})
		
	  jq("#buttony").click(function(){
		  //点击添加完成后提示中“是”的按钮
			location.href = we + "/jy/record/findRList?id="+local+"&type=1";
		})
		
		jq(".main").click(function(){
			//点击档案袋跳转到相应的精选页面
			var type = jq(this).attr("type");
			var id = jq(this).attr("id");
			if(type==1)
				location.href = we + "/jy/record/findRList?id="+id+"&type=1";
			else
				location.href = we + "/jy/record/sysList?id="+id+"&type=0";
		})
	}
	
	window.showCommentListBox = function (planType,planId,isUpdate){
		$("#box_comment").dialog({width:940,height:660});
		var author = $('#authorId').val();
		$("#iframe_comment").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=1&resType="+planType+"&resId="+planId+"&authorId="+author);
	 	if(isUpdate){//更新为已查看
	 		$.ajax({  
	 	        async : false,  
	 	        cache:true,  
	 	        type: 'POST',  
	 	        dataType : "json",  
	 	        data:{id:planId},
	 	        url:  _WEB_CONTEXT_+"/jy/record/updatePingLun"
	 	    });
	 	}
	 }

	
	

});