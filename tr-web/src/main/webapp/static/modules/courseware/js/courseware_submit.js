
//下面所有子集的元素全选和反选
function oneLevelSelect(obj,lessonId){
	//设置选中或者取消选中 
	var parent = $(obj).parents("li:first");
	parent.find("input.kj[type='checkbox']:enabled").prop("checked",$(obj).prop('checked'));
}
//全部选中或取消
function selectAll(obj){
	//获取是否选中 
	var isChecked = $(obj).prop('checked'); 
	$("input[type='checkbox']:enabled").prop('checked',isChecked);
}
//提交操作
function submitThis(params){
	var kj = ''; //课件
	$('input[child="check_kejian"]:checked').each(function(){ 
		if($(this).val() != 'on'){
			kj += $(this).val()+','; 
		}
	}); 
  	if (kj.length > 0) { 
		//得到选中的checkbox值序列 
		kj = kj.substring(0,kj.length - 1); 
		if(params==0){//提交
			if(confirm("您确定要提交课件吗？")){
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/courseware/submitCourseware.json",
					data:{"planIds":kj,"isSubmit":params},
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
			if(confirm("您确定要取消提交课件吗？")){
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/courseware/submitCourseware.json",
					data:{"planIds":kj,"isSubmit":params},
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
  	} else alert("请选择课件！"); 
}


$(document).ready(function(){
	$(".menu1").each(function(){
		var kejianNum = $(this).find("li[typename='kejian']").length;
		if(kejianNum>0){
			$("#span_"+$(this).prop("id")+"_kejian_num").html(kejianNum);
			$("#span_"+$(this).prop("id")+"_kejian").show();
		}
		if(kejianNum<=0){
			$("#li_"+$(this).prop("id")).remove();
		}
	});
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
	parent.find("input[child='"+$(obj).attr("child")+"kejianAll']").prop("checked",ischecked);
	parent.find("input[child='"+$(obj).attr("child")+"kejian']:enabled").prop("checked",ischecked);
}