define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init(); 
	});
	function init() {
		var com_personal_cont = new IScroll('#com_personal_cont',{
	      		scrollbars:true,
	      		mouseWheel:true,
	      		fadeScrollbars:true,
	      		click:true,
	      	});
		$('.right_option div a .right_option_act').each(function(index,obj){
			if(index==0){
				$(this).addClass('right_option_act1');
			}
			var that=this;
			$(this).click(function(){
				var index=$(that).parent().index()+1;
				$(that).addClass('right_option_act1').parent().siblings().find(".right_option_act").removeClass('right_option_act1');
				com_personal_cont.scrollToElement(document.querySelector('#scroll>div:nth-child('+index+')'));
			});
			$('.right_option div a').last().find('.border1').remove(); 
		});
		
		//加关注或者取消关注
		$(".com_personal_cont_head_btn span").click(function(){
			var act = $(this).attr("act");
			var userIdCompanion = $(this).attr("userIdCompanion");
			if(act=="add"){//加
				var url = _WEB_CONTEXT_+'/jy/companion/friends/'+userIdCompanion;
				$.post(url,{},function(result){
					if(result.result.code==1){
						successAlert('恭喜您，添加成功！',false,function(){
							document.location.reload();
						});
					}else{
						successAlert('添加关注失败：'+result.result.errorMsg);
					}
				},'json');
			}else if(act=="revoke"){//取消
				var userIdCompanion = $(this).attr('userIdCompanion');
				var userNameCompanion = $(this).attr('userNameCompanion');
				var r = confirm('您确定要取消关注好友' + userNameCompanion);
				if (r){
					$.ajax({
						dataType : 'json',
						type : 'delete',
						url : _WEB_CONTEXT_+"/jy/companion/friends/" + userIdCompanion + ".json",
						success : function(result) {
							if (result.result.code == 1) {
								successAlert('取消关注成功',false,function(){
									document.location.reload();
								});
							} else {
								successAlert(result.result.msg);
							}
						}
					});
				}
			}
		});
	}
	$('.companion_content_l dl').eq(0).click(function (){ 
		location.href = _WEB_CONTEXT_+"/jy/companion/companions/index";
	});
	$('.companion_content_l dl').eq(1).click(function (){ 
		location.href = _WEB_CONTEXT_+"/jy/companion/companions/mymsg";
	});
	$('.companion_content_l dl').eq(2).click(function (){ 
		location.href = _WEB_CONTEXT_+"/jy/companion/companions/mycare";
	});
});