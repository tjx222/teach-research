require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min'],function(){
	var $ = require("jquery");
	$(function(){
		init();
	});
	function init() { 
		//限制字符个数
		$(".td_name").each(function(){
		   var maxwidth=46;
		   if($(this).text().length>maxwidth){
		      $(this).text($(this).text().substring(0,maxwidth));
		      $(this).html($(this).html()+'…');
		   }
	  	}); 
		/*
		 * 点击进入发布通告页面
		 */
		$('.fq_teaching_btn').click(function(){
			window.location.href = _WEB_CONTEXT_+"/jy/annunciate/release";
		});
		//进入草稿箱
		$('.drafts').click(function (){
	    	var draftNum=$(this).attr("data-num");
	    	if(draftNum<=0){
				alert("您的草稿箱还没有任何内容哟！");
				return false;
			}else{
				$("#draft_box").dialog({width:600,height:460});
				$("#annunciate_iframe").attr("src",_WEB_CONTEXT_ + "/jy/annunciate/draft?"+ Math.random());
			}
		});
		//草稿箱中编辑通知公告
		$('.continue_edit_btn').click(function(){
			var id=$(this).attr("data-id");
			var annunciateType=$(this).attr("data-annunciateType");
			var roleId=$(this).attr("data-roleId");
			if(id!=null){
				if(roleId==1378||roleId==1380){
					parent.location.href=_WEB_CONTEXT_+"/jy/annunciate/release?id="+id;
				}else{
					if(annunciateType==0){
						parent.location.href=_WEB_CONTEXT_+"/jy/annunciate/release?id="+id;
					}else{
						parent.location.href=_WEB_CONTEXT_+"/jy/annunciate/toForwardAnnunciate?id="+id;
					}
				}
			}
		});
		//删除通知公告
		$('.deleteAnnunciate').click(function(){
			var id=$(this).attr("data-id");
			if(confirm("此通知公告已有人看到，删除后通知范围内人将不再能看到此次通知公告，可以在草稿箱中找回此次通知公告，您确定要删除吗？")){
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/annunciate/deleteAnnunciate",
					data: {"id": id},
					success:function(data){
						if(data.result.code==1){
							alert("通知公告删除成功");
							window.location.href = _WEB_CONTEXT_+"/jy/annunciate/punishs"
						}else{
							alert("通知公告删除失败");
						}
						
					}
				});
			}
		});
		//删除草稿箱中的通知公告
		$('.deleteDraft').click(function(){
			var id=$(this).attr("data-id");
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/annunciate/deleteAnnunciateDraft",
				data: {"id": id},
				success:function(data){
					if(data.result.code==1){
						alert("删除成功");
					}else{
						alert("删除失败");
					}
					
				}
			});
			$(this).parent().parent().remove();
		});
		//置顶
		$('.stick_btn').click(function(){
			var id=$(this).attr("data-id");
			var isTop=$(this).attr("data-isTop");
			window.parent.location.href = _WEB_CONTEXT_+"/jy/annunciate/isTop?id="+id+"&isTop="+isTop;
		});
		//取消置顶
		$('.qx_stick_btn').click(function(){
			var id=$(this).attr("data-id");
			var isTop=$(this).attr("data-isTop");
			window.parent.location.href = _WEB_CONTEXT_+"/jy/annunciate/isTop?id="+id+"&isTop="+isTop;
		});
		//悬停显示机构
		$('.school_num').mousemove(function(){
			var id=$(this).attr("data-id");
			$.ajax({  
		        async : false,  
		        cache:true,  
		        type: 'POST',  
		        dataType : "json",  
		        data:{'id':id},
		        url:_WEB_CONTEXT_+"/jy/annunciate/getJoinOrgs.json",
		        error: function () {
		            //alert('操作失败，请稍后重试');  
		        },  
		        success:function(data){
		        	orgList = data.orgList;
		        	var htmlStr = "";
					for(var i=0;i<orgList.length;i++){
						htmlStr += "<li title='"+orgList[i].name+"'>"+cutStr(orgList[i].name,24,true)+"</li>";
					}
					$("#orgids_"+id).html(htmlStr);
		        }  
		    });	
		});
	}
});
//进入转发区域通知
function forwardAnnunciate(id){
	window.parent.location.href = _WEB_CONTEXT_+"/jy/annunciate/toForwardAnnunciate?id="+id;
}
//未转发的区域通知
function unForwardAnnounciate(){
	window.parent.location.href = _WEB_CONTEXT_+"/jy/annunciate/notForwardIndex"
}
