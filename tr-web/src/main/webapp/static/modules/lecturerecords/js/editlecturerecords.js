require(['jquery','editor'],function(){
	var jq = require("jquery");
	jq(function(){
		//网络编辑器
		var uploadParams={relativePath:'lecturerecords/'+jq("#web_editor").attr("data-orgid")};
		webEditorOptions = {
			width:"100%",
			height:'375px',
			style:"1",
			extraFileUploadParams:uploadParams,
			needimage:true
		};
		editor = jq("#web_editor").editor(webEditorOptions);
	});
});

/**
 * 保存听课记录
 */
function handle(obj,f){
	$("form").validationEngine('detach');//移除validationEngine验证框架
	var html = editor[0].html();//文本框的html代码
	var text = editor[0].text();//文本框的文字
	$('#web_editor').text(html);//设置上html代码
	$('input[name=isEpub]').remove();
	var kt = $.trim($("#lessonId").find("option:selected").text());//课题内容
	
	if(kt == "请选择"){
		alert("请选择课题！");
		return;
	}else{
		kt = kt.replace(/&nbsp;/g,'');
	}
	$("#topic_sel").val(kt);
	var n=(text.split('>')).length-1;
	if(n>400){
		alert("最多可输入400个表情！");
		return;
	}
	if(text.length==0){
		alert("听课意见不能为空！");
		return;
	}
	if(text.length-113*n>40000){
		alert("最多可输入40000个字，请调整您的听课意见！");
		return;
	}
	
	var isfb=$(obj).attr("isfb");
	if(isfb=="1"){//发布
		$("form").validationEngine({
			autoHidePrompt:true,//支持错误提示自动消失。默认10s
			autoHideDelay:5000//设置错误消失时间5s
		});//加载validationEngine验证框架
		
		var flag=$('form').validationEngine('validate');//先进行验证，返回成功或者失败
		if(flag){
			var r=false;
			if(f=='0'){//校内听课
				r=confirm("保存后，不允许修改，同时该听课意见将发送给授课教师。如果还没有写好，您可以先保存到草稿箱中。您确定要保存吗？");
			}else if(f=='1'){//校外听课
				r=confirm("保存后，不允许修改。如果还没有写好，您可以先保存到草稿箱中。您确定要保存吗？");
			}
			if(r){
				$(obj).after("<input name=\"isEpub\" type=\"hidden\" value=\"1\"/>");
				$("form").submit();
			}else{
				return;
			}
		}else{
			return;
		}
	}else if(isfb=="0"){//草稿
		$("form").validationEngine('detach');//移除validationEngine验证框架
		$(obj).after("<input name=\"isEpub\" type=\"hidden\" value=\"0\"/>");
		$("form").submit();
	}
}

/**
 * 查看授课人教案
 */
function seelesson(){
	var topicid=$('#topicid').val();
	var url=_WEB_CONTEXT_+"/jy/lecturerecords/seelesson?topicid="+topicid;
	var ww = $(window).width();
	offsetWith = ww - 500 > 0?ww - 500:0;
	offsetWith=offsetWith+50;
	var ops="height=500,width=500,scrollbars=yes,titlebar=yes,left="+offsetWith+",top=10,toolbar=no,resizable=yes,location=no";
	window.open (url,"_blank",ops);
}