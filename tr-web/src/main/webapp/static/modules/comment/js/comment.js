define(["require","zxui/lib","hogan","jquery",'editor'], function (require) {
	var lib= require("zxui/lib"),$=require("jquery"),hogan = require("hogan"),replyEditors;
	var defautcontent = "_______";
	$(function(){
		var dom = lib.dom;
		function onDocClick(i) {
			var n = $("div.rp_textarea:visible"),r = lib.getTarget(i);
			n.each(function(){
				var $this = $(this);
				if($this.attr("data-opinionid") !== $(r).attr("data-opinionid") && n.length > 0 && n[0] !== r && !dom.contains(n[0], r)){
					if(replyEditors.count('text') <= 0
							|| replyEditors.text() == defautcontent)
						$this.hide();
						window.parent.setCwinHeight("commentBox",false);
				}
			});
		}
		lib.on(document, "click", onDocClick);
		lib.on(window.parent.document, "click", onDocClick);
		var showlist = true;
		$("#hide_comment").click(function(){
			var $this = $(this);
			$("div[class='word_tab_3_cont']").toggle(0,function(){
				showlist = !showlist;
				if(showlist){
					$this.text("收起").append("<img src=\""+_STATIC_BASEPATH_+"/common/images/sq.png\" >");
					window.parent.setCwinHeight("commentBox",false);
				}else{
					$this.text("展开").append("<img src=\""+_STATIC_BASEPATH_+"/common/images/sd.png\" >");
					 window.parent.setCwinHeight("commentBox",false);
				}
			});
		});
		$(".toggle").on("click",function(){
			if($(this).find('strong').html()=="展开"){
				$(this).find('strong').html("收起");
				$(this).find('span').css({"background-position":"-130px -2px"}); 
				$('.consult_opinion_list1').show(); 
				$('.pages1').show();
				window.parent.setCwinHeight("commentBox");
			}else{ 
				$(this).find('strong').html("展开");
				$(this).find('span').css({"background-position":"-141px -2px"}); 
				$('.consult_opinion_list1').hide(); 
				$('.pages1').hide();
				window.parent.setCwinHeight("commentBox");
			} 
		}); 
		/*$(".consult_opinion_list_h4").click(function(){
			$(this).text($("div.div_option").is(":hidden") ? "收起" : "展开");
			$("div.div_option").slideToggle(10,function(){
				if($("div.div_option").is(":hidden")){
					window.parent.setCwinHeight("checkedBox",false);
				}else{
					window.parent.setCwinHeight("checkedBox",false);
				}
			});
		});*/
		$("div.consult_opinion_right_b").on("click","b.reply_rq",function(){
			var $reply = $(this),current =	replyid = $reply.attr("data-id"),
			authorid=$reply.attr("data-authorid"),opinionid = $reply.attr("data-opinionid");
			var n = $("div.rp_textarea:visible");
			$('.reply_btn').attr("data-authorid",authorid);
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
			var editorContainer = $("#rp_"+opinionid);
			$("#cont_"+replyid).after(editorContainer);
			defautcontent = "回复"+$reply.attr("data-uname");
			editorContainer.attr("data-index",current).show().find("input.reply_btn").attr("data-pid",replyid);
			replyEditors = editorContainer.find('textarea[name="replyContent"]').editor({width:"1030px",float:'right',height:"70px",style:0,
				afterBlur:function(){
					if(this.count('text') == 0)
						this.html("<span style='color:#999;'>"+defautcontent+"</span>");
				},afterFocus:function(){
						if(this.text() == defautcontent)
							this.text("");
				},initContent:"<span style='color:#999;'>"+defautcontent+"</span>"})[0];
			window.parent.setCwinHeight("commentBox",false);
			var sctop = $("div[class='word_tab_3_cont']").scrollTop();
			var top = $("#rp_"+opinionid).position().top - 200 + sctop;
			if(top > 400)
				$("div[class='word_tab_3_cont']").animate({scrollTop:top}, 1000);
		});
		$("input.reply_btn").click(function(){
			if(!$("#option_form").validationEngine("validate")){					
				return false;
			}
			var $this = $(this),
			index = $this.attr("data-index"),
			pid=$this.attr("data-pid"),
			opinionid = $this.attr("data-opinionid"),
			content=replyEditors.text();
			var authorId=$(this).attr("data-authorid");
			var txtcount = replyEditors.count('text');
			if(txtcount == 0 || replyEditors.text() == defautcontent){
				alert("回复内容不能为空！");
				return;
			}
			if(txtcount > 300){
				alert("您最多可以输入300个字！"); 
				return;
			}
			var key = $("input[name='_TOKEN_']").val();
			var value = $("input[name='"+key+"']").val();
			var dataobject ={"content":replyEditors.html(),"opinionId":opinionid,"parentId":pid,"authorId":authorId,"_TOKEN_":key};
			dataobject[key]=value;
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/comment/relyComment",
				data:dataobject,
				success:function(data){
					document.location.reload();
				}
			});
		});
	});
});

