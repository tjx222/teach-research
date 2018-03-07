define(["require","jquery",'jp/jquery-ui.min','jp/jquery.blockui.min','jp/jquery.placeholder.min'], function (require) {
	var jq=require("jquery");
	require('jp/jquery.placeholder.min');
	jq(document).ready(function () {
		init();
	});

function init(){
		jq('input, textarea').placeholder();
		if(jq("#planName").val()==''){
			jq('#all').html('');
		}else{
			jq("#all").html('全部 <');
		}
		jq(".txt_btn").click(function(){
			  $('#planName').val($.trim($('#planName').val()));
			  //点击搜索按钮
			  var form = jq('#searchForm');
			  form.submit();
		})
		jq("#all").click(function(){
			  //点击全部
			  var form = jq('#searchForm');
			  jq('#planName').val('');
			  form.submit();
		})
		
		jq(".img1").click(function(){
			  //点击精选
				  var one = jq(this).attr('id');
				  var name = jq(this).attr('name');
				  jq("#name").html(name);
				  jq("#one").val(one);
				  $("#_jx").dialog({width:536,height:350});
		})
		
		jq(".td").click(function(){
			  //点击详情
			var resId = jq(this).attr("resId");
			  scanResFile(resId);
			
		})
		
		
		jq(".td1").click(function(){
			  //点击详情
			var id = jq(this).attr("id");
			window.open(_WEB_CONTEXT_+"/jy/lecturerecords/seetopic?id="+id,"_blank");
		})
		
		
		jq("#button").click(function(){
			    //点击提交
				  var one = jq("#one").val();
				  var desc = jq("#desc").val();
				  if(jq("#formular").validationEngine('validate'))
				  location.href = we + "/jy/record/sysJx?id="+id+"&type="+type+"&one="+one+"&page="+page+"&desc="+encodeURIComponent(desc);
		})
		
		jq("#planName").bind("propertychange input",function(){
			if(jq(this).val()==''){
				jq("#all").html('');
			}else{
				jq("#all").html('全部 <');
			}
		})
		
		jq('.cen_btn').click(function(){
			var id=$(this).attr("data-id");
			var type=$(this).attr("data-type");
			location.href =_WEB_CONTEXT_+"/jy/record/sysList?id="+id+"&type="+type;
		})
		
	}

});