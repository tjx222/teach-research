define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min','jp/jquery.placeholder.min'], function (require) {
	var jq=require("jquery");	
	require('jp/jquery.placeholder.min');
	jq(document).ready(function () {
		init();
	});

function init(){
		jq('input, textarea').placeholder();
		jq(".jx_btn").click(function(){
			  //点击精选按钮
			  location.href = we + "/jy/record/findSysList?id="+id+"&type="+type;
			
		})
		jq(".del").click(function(){
			var one = jq(this).attr("id");
			var name = jq(this).attr("name");
			  //点击删除按钮
			if(confirm("您确定要删除“"+name+"”吗？"))
				location.href = we + "/jy/record/delRecords?id="+one+"&bagId="+id+"&type="+type+"&page="+page;
			
		})
		
		jq(".dl").click(function(){
			  //点击详情
			var resId = jq(this).attr("resId");
			  scanResFile(resId);
			
		})
		
		jq(".tspan1").click(function(event){
			  //点击详情
			  event.stopPropagation();
			  var one = jq(this).attr("resId");
			  var name = jq(this).attr("resName");
			  var desc = jq(this).attr("title");
			  jq("#name").html(name);
			  jq("#one").val(one);
			  jq("#desc").val(desc); 
			  $("#_jx").dialog({width:536,height:350});
		});
		jq(".tspan").click(function(event){
			  //点击详情
			  event.stopPropagation();
			  var one = jq(this).attr("resId");
			  var name = jq(this).attr("resName");
			  var desc = jq(this).attr("title");
			  jq("#name").html(name);
			  jq("#one").val(one);
			  jq("#desc").val(desc); 
			  $("#_jx").dialog({width:536,height:350});
		});
		
		jq("#button").click(function(){
		    //点击提交
			  var one = jq("#one").val();
			  var desc = jq("#desc").val();
			  if(jq("#formular").validationEngine('validate'))
			  location.href = we + "/jy/record/sysJxUpdate?id="+id+"&type="+type+"&one="+one+"&page="+page+"&desc="+encodeURIComponent(desc);
		})
	
	

	}


});