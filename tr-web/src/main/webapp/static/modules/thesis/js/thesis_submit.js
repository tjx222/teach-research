define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min'], function (require) {
	var isReload = "";
	var jq=require("jquery");
	jq(document).ready(function () {
		jq('.all_select').click(function (){
			$("input[name='thesis']:enabled").prop("checked",$(this).prop('checked'));
		});
		jq('input[name="thesis"]').click(function (){
			var box = $("input[name='thesis']").size();
			var checkBox = $("input[name='thesis']:checked").size();
			$(".all_select").prop("checked",box==checkBox);
		});
		jq('.submit_up').click(function (){
			$("#submit_thesis").dialog({width:800,height:680});
			jq("#submit_iframe").attr("src","jy/thesis/preSubmit?isSubmit=0&"+ Math.random());
			isReload = "ok";
		});
		jq('.submit_select').click(function(){
			var params = jq(this).attr("data-value");
			var thesis = []; //课件
			jq("input[name='thesis']:enabled:checked").each(function(){
				if(jq(this).val() != 'on'){
					thesis.push(jq(this).val()); 
				}
			}); 
		  	if (thesis.length > 0) {
		  		var infoStr = "";
				//得到选中的checkbox值序列 
		  		if(params==0){
		  			infoStr = "您确定要提交吗？";
		  		}else{
		  			infoStr = "您确定要取消提交吗？";
		  		}
				if(confirm(infoStr)){
					jq.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/thesis/submitThesis.json",
						data:{"resIds":thesis,"isSubmit":params==0?1:0},
						traditional:true,//参数以传统方式传递
						success:function(data){
							if(params==0){
								if(data){
									alert("提交成功！");
									window.parent.document.getElementById("ytjButton").click();
								}else{
									alert("提交失败！");
								}
							}else if(params==1){
								if(data){
									alert("取消提交成功！");
									location.reload();
								}else{
									alert("取消提交失败！");
								}
							}
						}
					});
				}
		  	} else{
		  		alert("请选择教学文章！"); 
		  	}
		})
		jq(".check_option_t").on("click",function(){
			var id = $(this).attr("data-id");
			var isUpdate = $(this).attr("data-scan");
			var resType = $(this).attr("data-type");
			jq("#checkedBox").attr("src",_WEB_CONTEXT_+"/jy/check/lookCheckOption?flags=false&resType="+resType+"&resId="+id);
			$("#thesis_option").dialog({width:1125,height:680});
			isReload = "ok";
			if(isUpdate){//更新为已查看
				jq.ajax({  
					async : false,  
					cache:true,  
					type: 'POST',  
					dataType : "json",  
					data:{id:id},
					url:  _WEB_CONTEXT_+"/jy/check/thesis/setScan.json"
				});
			}
		})
	});
})
