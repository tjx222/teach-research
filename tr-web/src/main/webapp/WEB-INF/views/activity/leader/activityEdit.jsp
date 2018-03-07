<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="集体备课"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/activity/css/activity.css" media="all">
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript">
//活动类型选择
function checkType(thi) {
	changeType($(thi).val());
}
//切换活动类型
function changeType(typeId){
	var id = $("#id").val();
	var cs = "";
	if(id!=''){
		cs = "?id="+id;
	}
	if(typeId==2){
		$("#div_tbja").hide();
		$("#div_spjy").hide();
		if($("#iframe_ztyt").attr("src")==""){
			$("#iframe_ztyt").attr("src", _WEB_CONTEXT_ + "/jy/activity/toEditActivityZtyt"+cs);
		}
		$("#div_ztyt").show();
	}else if(typeId==3){
		$("#div_ztyt").hide();
		$("#div_tbja").hide();
		if($("#iframe_spjy").attr("src")==""){
			$("#iframe_spjy").attr("src",_WEB_CONTEXT_ + "/jy/activity/toEditActivitySpjy"+cs);
		}
		$("#div_spjy").show();
	}else{
		$("#div_ztyt").hide();
		$("#div_spjy").hide();
		if($("#iframe_tbja").attr("src")==""){
			$("#iframe_tbja").attr("src",_WEB_CONTEXT_ + "/jy/activity/toEditActivityTbja"+cs);
		}
		$("#div_tbja").show();
	}
}
</script>	
</head>
<body>
	<div id="wrapper">
	<div class='jyyl_top'>
	<ui:tchTop style="1" modelName="集体备课编辑页"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
	<c:if test="${act.id==null || act.status==0 }">
	当前位置：<jy:nav id="fqhd"></jy:nav>
	</c:if>
	<c:if test="${act.id!=null && act.status!=0 }">
	当前位置：<jy:nav id="xghd"></jy:nav>
	</c:if>
	</div>
	<input value="${act.id}" id="id" name="id" type="hidden">
	<div class='fq_activity_cont'>
		<div class='activity_info_wrap'>
			<c:if test="${act.status!=1 }">
			<div class='activity_type'>
				<div class='activity_type_icon'></div>
				<div class='activity_type_right'>
					<h3 class='activity_type_right_h3'>活动类型：</h3>
					<div class='tbja'> 
						<input type='radio' id='tbja' name="typeId" value="1" onclick="checkType(this);"  <c:if test="${empty typeId || typeId==1}">checked="checked"</c:if> />
						<label for='tbja'>同备教案</label>
					</div>
					<div class='ztyt'> 
						<input type='radio' id='ztyt' name="typeId" value="2" onclick="checkType(this);" <c:if test="${typeId==2}">checked="checked"</c:if> />
						<label for='ztyt'>主题研讨</label>
					</div>
					<div class='spyt'> 
						<input type='radio' id='spyt' name="typeId" value="3" onclick="checkType(this);" <c:if test="${typeId==3}">checked="checked"</c:if> />
						<label for='spyt'>视频研讨</label>
					</div>
						
				</div>
			</div>
			</c:if>
			<div class="launch1" id="div_tbja" style="display: none;">
				<iframe id="iframe_tbja" src=""  style="margin-left:-21px;" width="950px" height="100%" frameborder="0" scrolling="no" allowtransparency="true"  onload="setCwinHeight(this,false,100)"></iframe>
			</div>
			<div class="launch1" id="div_ztyt" style="display: none;">
				<iframe id="iframe_ztyt" src="" style="margin-left:-21px;" width="950px" height="100%" frameborder="0" scrolling="no"  onload="setCwinHeight(this,false,100)"></iframe>
			</div>
			<div class="launch1" id="div_spjy" style="display: none;">
				<iframe id="iframe_spjy" src="" style="margin-left:-21px;" width="950px" height="100%" frameborder="0" scrolling="no"  onload="setCwinHeight(this,false,100)"></iframe>
			</div>
		</div>
	</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</div> 
</body>
<script type="text/javascript">
$(document).ready(function(){
	changeType(${act.typeId});
	requestAtInterval(600000);
});
</script>
<script type="text/javascript" >
    /**
     * 每隔一段时间请求一次后台，保证session有效
     */
    function requestAtInterval(timeRange){
    	var dingshi = window.setInterval(function(){
    		$.ajax({  
    	        async : false,  
    	        cache:true,  
    	        type: 'POST',  
    	        dataType : "json",  
    	        url: _WEB_CONTEXT_+"/jy/toEmptyMethod.json?id="+Math.random(),
    	        error: function () {
    	        	window.clearInterval(dingshi);
    	        },  
    	        success:function(data){
    	        }  
    	    });
    	},timeRange);
    }
</script> 
</html>