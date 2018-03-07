define(["require","zxui/lib","hogan","jquery",'editor'], function (require) {
	var lib= require("zxui/lib"),jq=require("jquery"),hogan = require("hogan"),replyEditors;
	jq(function(){
		replyEditors = jq('textarea[name="replyContent"]').editor({width:"834px",height:"70px",style:0});
		var dom = lib.dom;
		function onDocClick(i) {
			var n = jq("div.rp_textarea:visible"),r = lib.getTarget(i);
			n.each(function(){
				var $this = jq(this);
				if($this.attr("data-opinionid") !== $(r).attr("data-opinionid") && n.length > 0 && n[0] !== r && !dom.contains(n[0], r)){
					if(replyEditors[$this.attr('data-index')].count('text') <= 0)
						jq(this).hide();
				}
			});
		}
		lib.on(document, "click", onDocClick);
		lib.on(window.parent.document, "click", onDocClick);
		
		$("div.consult_opinion_right_b").on("click","b.reply_rq",function(){
			var $reply = jq(this),current = $reply.attr("data-index"),
			replyid = $reply.attr("data-id"),
			opinionid = $reply.attr("data-opinionid");
			var n = $("div.rp_textarea:visible");
			if(n.length>0){
				var hastext = false;
				var ce;
				n.each(function(){
					$this = $(this);
					var index = $this.attr("data-index");
					if(current !== index && replyEditors[index].count('text') > 0){
						hastext = true;
						ce = index;
						return;
					}
				});
				if(hastext){
					if(confirm("您确定要放弃正在编辑的内容吗？")){
						replyEditors[ce].html('');
					}else{
						return false;
					}
			   }
			}
			replyEditors[current].html('');
			replyEditors[current].remove();
			var editorContainer = jq("#rp_"+opinionid);
			jq("#cont_"+replyid).after(editorContainer);
			editorContainer.show().find("input.reply_btn").attr("data-pid",replyid);
			replyEditors[current] = editorContainer.find('textarea[name="replyContent"]').editor({width:"834px",height:"70px",style:0})[0];
			window.parent.setCwinHeight("commentBox",false);
			var sctop = jq("div[class='word_tab_3_cont']").scrollTop();
			var top = jq("#rp_"+opinionid).position().top - 200 + sctop;
			if(top > 400)
				jq("div[class='word_tab_3_cont']").animate({scrollTop:top}, 1000);
		});
		
		var showlist = true;		
		jq("#hide_comment").click(function(){
			var $this = jq(this);
			jq("div[class='word_tab_3_cont']").toggle(0,function(){
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
		
		jq("input.reply_btn").click(function(){
			var $this = $(this),
			 index = $this.attr("data-index"),
			 pid=$this.attr("data-pid"),
			 opinionid = $this.attr("data-opinionid"),
			 content=replyEditors[index].text();
			if(jq.trim(content) === ""){
				alert("回复内容不能为空！");
				return;
			}
			if(content.length > 300){
				alert("您最多可以输入300个字！"); 
				return;
			}
			jq.post(_WEB_CONTEXT_+"/jy/lecturereply/replyreply",{"content":content,"type":"1","parentId":pid},function(data){
				document.location.reload();
			});
		});
	});
});

