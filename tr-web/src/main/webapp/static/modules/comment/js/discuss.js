define(["require","zxui/lib","jquery",'editor'], function (require) {
	var lib= require("zxui/lib"),$=require("jquery"),replyEditors;
	var defautcontent = "_______";
	$(function(){
		//收起
		$(".toggle").on("click",function(){
			if($(this).find('strong').html()=="展开"){
				$(this).find('strong').html("收起");
				$(this).find('span').css({"background-position":"-130px -2px"}); 
				$('.discuss_list_cont').show();
				$('.discuss_list_border').show();
				$('#pageForm').show();
				window.parent.setCwinHeight("discuss_iframe");
			}else{ 
				$(this).find('strong').html("展开");
				$(this).find('span').css({"background-position":"-141px -2px"}); 
				$('.discuss_list_cont').hide();
				$('.discuss_list_border').hide();
				$('#pageForm').hide();
				window.parent.setCwinHeight("discuss_iframe");
			} 
		}); 
		//用户姓名进行查找
		$(".search_btn").on("click",function(){
			var searchTxt = $.trim($(".search_txt").val());
			if(searchTxt!=""){
				$(".currentPage").val(1);
				$("#searchName").val($(".search_txt").val());
				$("#pageForm").submit();
			}else{
				alert('请输入用户姓名进行查找！');
			}
		});
		//查找全部
		$(".whole").on("click",function(){
			$(".currentPage").val(1);
			$("#searchName").val("");
			$("#pageForm").submit();
		});
		//回复
		var dom = lib.dom;
		function onDocClick(i) {
			var n = $("div.rp_textarea:visible"),r = lib.getTarget(i);
			n.each(function(){
				var $this = $(this);
				if($this.attr("data-pid") !== $(r).attr("data-pid") && n.length > 0 && n[0] !== r && !dom.contains(n[0], r)){
					if(replyEditors.count('text') <= 0
							|| replyEditors.text() == defautcontent)
						$this.hide();
						window.parent.setCwinHeight("discuss_iframe",false);
				}
			});
		}
		lib.on(document, "click", onDocClick);
		lib.on(window.parent.document, "click", onDocClick);
		$("div.discuss_list_right_b").on("click",".reply",function(){
			var $reply = $(this),current =	replyid = $reply.attr("data-id")
			,pid = $reply.attr("data-pid");
			var n = $("div.rp_textarea:visible");
			if(n.length>0){
				var hastext = false,isself=false;
				var ce;
				n.each(function(){
					$this = $(this);
					var index = $this.attr("data-index");
					if(current !== index && replyEditors.count('text') > 0 && replyEditors.text() !== defautcontent){
						hastext = true;
						ce = index;
						return;
					}
					if(current == index){isself=true;return;}
				});
				if(isself){return false;}
				if(hastext){
					if(!confirm("您确定要放弃正在编辑的内容吗？")){
						return false;
					}
			   }
			}
			if(replyEditors)replyEditors.remove();
			var editorContainer = $("#rp_"+pid);
			$(this).parent().after(editorContainer);
			defautcontent = "回复"+$reply.attr("data-uname");
			editorContainer.attr("data-index",current).show().find("input.reply_textarea_btn").attr("data-pid",pid);
			replyEditors = editorContainer.find('textarea[name="replyContent"]').editor({width:"1030px",float:'right',height:"70px",style:0,
				afterBlur:function(){
					if(this.count('text') == 0)
						this.html("<span style='color:#999;'>"+defautcontent+"</span>");
				},afterFocus:function(){
						if(this.text() == defautcontent)
							this.text("");
				},initContent:"<span style='color:#999;'>"+defautcontent+"</span>"})[0];
			window.parent.setCwinHeight("discuss_iframe",false);
			var sctop = $("div[class='word_tab_3_cont']").scrollTop();
			var top = $("#rp_"+pid).position().top - 200 + sctop;
			if(top > 400)
				$("div[class='word_tab_3_cont']").animate({scrollTop:top}, 1000);
		});
		
		$("input.reply_btn").click(function(){
			var parentid = $(this).attr("data-pid");
			var replycontent = replyEditors.text();
			var txtcount = replyEditors.count('text');
			if(txtcount > 0 && replycontent != defautcontent){
				if(txtcount > 300){
					alert("您最多可以输入300个字！"); 
					return;
				}
				$("#parentId").val(parentid);
				$("#contHiddenId").val(replyEditors.html());
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/comment/discussSave.json",
					data:$("#replayForm").serialize(),
					success:function(data){
						if(data.isSuccess){
							if(data.msg == "ok"){
								$(".reply_text").val("");
								//刷新讨论列表数据
								window.location.reload();
							}else if(data.msg == "isOver"){
								alert("活动已经结束，不能再回复讨论！");
							}
						}else{
							alert("保存失败!");
						}
					}
				}); 
			}else{
				alert('请输入回复内容！');
			}
		});
	}); 
});

