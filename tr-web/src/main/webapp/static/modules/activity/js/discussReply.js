require(['jquery',"zxui/lib",'editor'],function(){
	var lib= require("zxui/lib"),jq=require("jquery"),replyEditors;
	var defautcontent = "_______";
	jq(function(){
		var dom = lib.dom;
		function onDocClick(i) {
			var n = jq("div.replay:visible"),r = lib.getTarget(i);
			n.each(function(){
				var $this = jq(this);
				if($this.attr("data-opinionid") !== $(r).attr("data-opinionid") && n.length > 0 && n[0] !== r && !dom.contains(n[0], r)){
					if(replyEditors.count('text') <= 0
							|| replyEditors.text() == defautcontent){
						$this.hide();
						window.parent.setCwinHeight("discuss_iframe",false);
					}
				}
			});
		}
		lib.on(document, "click", onDocClick);
		lib.on(window.parent.document, "click", onDocClick);
		
		
		$("div.word_tab_3_cont").on("click","strong.reply_rq",function(){
			var $reply = jq(this),
			current = replyid = $reply.attr("data-id"),
			opinionid = $reply.attr("data-opinionid");
			var n = $("div.replay:visible");
			if(n.length>0){
				var hastext = false,isself=false;
				n.each(function(){
					$this = $(this);
					var index = $this.attr("data-index");
					if(current !== index && replyEditors.count('text') > 0 && replyEditors.text() !== defautcontent){
							hastext = true;
							return;
					}
					if(current == index){isself = true;return;}
				});
				if(isself) return false;
				if(hastext){
					if(!confirm("您确定要放弃正在编辑的内容吗？")){
						return false;
					}
			   }
			}
			
			if(replyEditors)replyEditors.remove();
			var editorContainer = jq("#replay_"+opinionid);
			jq("#cont_"+replyid).after(editorContainer);
			defautcontent = "回复"+$reply.attr("data-uname");
			editorContainer.attr("data-index",current).show().find("input.reply_btn").attr("data-pid",replyid);
			replyEditors = editorContainer.find('textarea[name="content_reply"]').editor({width:"700px",height:"70px",style:0,
				afterBlur:function(){
					if(this.count('text') == 0)
						this.html("<span style='color:#999;'>"+defautcontent+"</span>");
				},afterFocus:function(){
						if(this.text() == defautcontent)
							this.text("");
				},initContent:"<span style='color:#999;'>"+defautcontent+"</span>"})[0];
			window.parent.setCwinHeight("discuss_iframe",false);
		});
		
		
		jq("input.reply_btn").click(function(){
			var $this = $(this),
			 opinionid = $this.attr("data-opinionid"),
			 content=replyEditors.text();
			if(jq.trim(content) === "" || jq.trim(content) === defautcontent){
				alert("回复内容不能为空！");
				return false;
			}
			if(content.length > 300){
				alert("您最多可以输入300个字！"); 
				return false;
			}
			$("#contHidden_"+opinionid).val(content);
			$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/comment/discussSave.json",
				data:$("#replayForm_"+opinionid).serialize(),
				success:function(data){
					if(data.isSuccess){
						if(data.msg == "ok"){
							replyEditors.html("");
//							alert("信息保存成功！");
							$("#submitReply").submit();
						}else if(data.msg!=null && data.msg!=""){
							alert(data.msg);
						}
					}else{
						alert("保存失败");
					}
				}
			});
		});
		
		
	});
});