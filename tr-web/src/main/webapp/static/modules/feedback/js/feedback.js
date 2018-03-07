function beforeUpload(){
	if($("#resTable tr").length<4){
		return true;
	}else{
		alert("最多允许上传4个参考附件");
		return false;
	}
	
}
function backUpload(data){
	var rid = "'" +$("input[name='backresId']").val()+ "'";
	var originname = $("#originFileName").val();
	var spidFileName = "";
	if(originname.length>10){
		spidFileName = originname.substring(0,7)
	}else{
		spidFileName=originname;
	}
	var htmlString = "";
	htmlString += '<tr style="width:160px;" value="'+data.data+'">';
	htmlString += '<td style="width:120px;float:left;" title="'+originname+'"> ';
	htmlString += '<strong class="delete_fj"></strong>';
	htmlString += "<a href='jy/scanResFile?resId="+data.data+"' target='_blank'>"+spidFileName+"</a>";
	htmlString += '</td></a>';
	htmlString += '<td class="delete_d" onclick="removeRes('+ rid +',$(this));"> </td>';
	htmlString += '</tr>';
	$("#resTable").append(htmlString);
	updateZtytRes();
}
function removeRes(id,el){
	var url = "jy/feedback/deleteImg";
	$.post(url,{imgId:id,isweb:false},null,"json");
	el.parent().remove();
	updateZtytRes();
	
}
//更新资源ids
function updateZtytRes() {
	var res = "";
	$('#resTable').find('tr').each(function () { 
		if(res!="")res += ",";
		res += $(this).attr("value");
	});
	$('#ztytRes').val(res);
}
function saveFeedBack(){
		var fid = $("#feedForm").serialize();
		//验证是否为空提交
		var message = $(".txterea1").val().trim();
		if(message==""){
			alert("请输入反馈内容");
			return false;
		}else if(message.length>100){
			alert("输入限制：最多100个字符");
			return false;
		}
		$.ajax({
			type:"post",
			dataType:"json",
			url:"jy/feedback/saveFeedBack",
			data: fid,
			error: function () {
	            alert('请求失败');  
	        },
			success:function(data){
				if(data.code==1){
					alert(data.msg);
					parent.location.href = _WEB_CONTEXT_ + "/jy/feedback/index";
				}
				else {
					alert(data.msg);
					return false;
				}
			}
		});
	}
function feedpage(data){
	$("#right_1").html(data);
}
