	//其他反思的全选和取消全选
	function selectQTfansi(obj){
		//获取是否选中 
		var isChecked = $(obj).prop('checked'); 
		//设置选中或者取消选中 
		$(".qtfs").prop('checked',isChecked); 
	}
	//课后反思的全选和反选
	function selectKHfansi(obj){
		//获取是否选中 
		var isChecked = $(obj).prop('checked'); 
		//设置选中或者取消选中 
		$(obj).parents("div:first").find("input[type='checkbox']:enabled").prop('checked',isChecked); 
	}
	//全部选中或取消
	function selectAll(obj){
		//获取是否选中 
		var isChecked = $(obj).prop('checked'); 
		$("input[type='checkbox']:enabled").prop('checked',isChecked); 
		//设置选中或者取消选中 
		$(".qtfs").prop('checked',isChecked); 
	}
	//提交操作
	function submitThis(params){
		var kh = ''; //课后反思
		var qt = ''; //其他反思
		$('input[child="check_fansi"]:checked').each(function(){ 
			if($(this).val() != 'on'){
				kh += $(this).val()+','; 
			}
		}); 
		$('input[name="qtfs"]:checked').each(function(){ 
			if($(this).val() != 'on'){
				qt += $(this).val()+','; 
			}
		}); 
	  	if (kh.length > 0 || qt.length > 0) { 
			//得到选中的checkbox值序列 
			if(kh.length > 0){
				kh = kh.substring(0,kh.length - 1); 
			}
			if(qt.length > 0){
				qt = qt.substring(0,qt.length - 1); 
			}
			if(params==0){//提交
				if(confirm("您确定要提交反思吗？")){
					$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/rethink/submitRethink.json",
						data:{"planIds":kh,"qtFanSiIds":qt,"isSubmit":params},
						success:function(data){
							if(data.isOk){
								alert("提交成功！");
								window.parent.document.getElementById("ytjButton").click();
							}else{
								alert("提交失败！");
							}
						}
					});
				}
			}else if(params==1){//取消提交
				if(confirm("您确定要取消提交反思吗？")){
					$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/rethink/submitRethink.json",
						data:{"planIds":kh,"qtFanSiIds":qt,"isSubmit":params},
						success:function(data){
							if(data.isOk){
								alert("取消提交成功！");
								$("#quanxuan").attr("checked",true);
								$("#quanxuan").click();
								location.reload();
							}else{
								alert("取消提交失败！");
							}
						}
					});
				}
			}
	  	} else alert("请选择反思！"); 
	}
	
$(document).ready(function(){
	/* 滑动/展开 */
	$("ul.expmenu li > a.header > span.label").click(function(){
												   
		var arrow = $(this).parent().find("span.arrow");	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}
		if(arrow.hasClass("up")){
			$(this).parent().removeClass("headers");
		}else if(arrow.hasClass("down")){
			$(this).parent().addClass("headers");
		}
		$(this).parent().next('ul.menu').slideToggle();
		
	});
	$("ul.expmenu li > a.header > span.arrow").click(function(){
		   
		var arrow = $(this).parent().find("span.arrow");	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}	
		if(arrow.hasClass("up")){
			$(this).parent().removeClass("headers");
		}else if(arrow.hasClass("down")){
			$(this).parent().addClass("headers");
		}
		$(this).parent().next('ul.menu').slideToggle();
		
	});
	/* 滑动/展开 */
	$("ul.expmenu li > a.headertag > span.label").click(function(){

		var arrow = $(this).parent().find("span.arrow");
	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}

		$(this).parent().next("ul.menu").slideToggle();
		
	});
	$("ul.expmenu li > a.headertag > span.arrow").click(function(){

		var arrow = $(this).parent().find("span.arrow");
	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}

		$(this).parent().next("ul.menu").slideToggle();
		
	});	
	$(".menu1").each(function(){
		var fansiNum = $(this).find("li[typename='fansi']").length;
		if(fansiNum>0){
			$("#span_"+$(this).prop("id")+"_fansi_num").html(fansiNum);
			$("#span_"+$(this).prop("id")+"_fansi").show();
		}
		if(fansiNum<=0){
			$("#li_"+$(this).prop("id")).remove();
		}
	});
	//未checkbox添加事件
	$("input[level='leaf']").bind("click",function(){
		checkLeaf($(this));
	});
	$("input[level='parent']").bind("click",function(){
		checkLeaf($(this));
	});
	
});	
function checkOrNot(obj,childName){
	var ischecked = $(obj).prop("checked");
	var parent = $(obj).parents("li:first");
	parent.find("input[child='"+childName+"']:enabled").prop("checked",ischecked);
}
//操作叶子节点复选框
function checkLeaf(obj){
	var ischecked = $(obj).prop("checked");
	var parent = $(obj).parents("li:first");
	parent.find("input[child='"+$(obj).attr("child")+"']").prop("checked",ischecked);
	parent.find("input[child='"+$(obj).attr("child")+"fansiAll']").prop("checked",ischecked);
	parent.find("input[child='"+$(obj).attr("child")+"fansi']:enabled").prop("checked",ischecked);
}