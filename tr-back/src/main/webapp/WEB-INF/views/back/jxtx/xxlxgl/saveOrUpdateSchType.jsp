<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="pageContent"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
	<form id="form" class="required-validate">
		<div class="pageFormContent" layoutH="56">
			<div class="unit">
				<input type="hidden" id="mrpId" value="${mrp.id }">
				<label>&nbsp;&nbsp;学校类型名称：</label> 
				<input id="lxname" class="required textInput" maxlength="20"   name="name" type="text" size="36" value="${mrp.name }"/>
			</div>
			<div class="unit">
				<label>&nbsp;&nbsp;排&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;序：</label>
				<input id="lxsort" class="required number" name="sort" type="text" size="36" value="${mrp.sort }" />
			</div>

				<div class="unit">	
				<label>&nbsp;&nbsp;选择学段：</label>
					<ul style="width:350;float: left;">
					<c:forEach items="${xdList}" var="xd">
						<li style="line-height: 28px">
							<input id="xd_${xd.id }" type="checkbox" name="ids" class="xdBox required" data="${xd.id }" value="${xd.id }" attrName="${xd.name }"/>
							<span>&nbsp;${xd.name }&nbsp;&nbsp;</span>
							<span id="grades_${xd.id }" style="border-bottom: 1px solid #aaa;padding:2px; display: none" data="${xd.id }" data-name="${xd.name }"></span>
						</li>
					</c:forEach>
					</ul>
				</div>
				
			<div class="unit">
				<label>学校类型描述:</label>
				<textarea id="ds" name="descs" cols="28" rows="5" maxlength="80">${mrp.descs }</textarea>
			</div>
		</div>

		<div class="formBar">

			<ul>

				<li>

					<div class="buttonActive">
						<div class="buttonContent">
							<button type="Button" onclick="saveSchType();">保存</button>
						</div>
					</div>

				</li>

				<li>

					<div class="button">
						<div class="buttonContent">
							<button type="Button" class="close">取消</button>
						</div>
					</div>

				</li>

			</ul>

		</div>

	</form>

</div>
<script type="text/javascript">

	$("#lxname").keyup(function(){
		if($("#lxname").val().length == 20){
			alertMsg.info('最多输入20个字！')
		}
	})
	
	$("#ds").keyup(function(){
		if($("#ds").val().length == 80){
			alertMsg.info('最多输入80个字！')
		}
	})
	
	//初始化页面显示年级
	$(".xdBox").each(function(){
		var xd = $(this).next().next().attr("data");//学段
		var data = ${xdnjMap};
		var njList = data[$(this).val()];
		var html = '';
		if(njList && njList.length > 0){
			$.map(njList,function(nj,i){
				html += '<input id="nj_'+nj.id+'" type="checkbox" name="nj_'+xd+'" class="njBox" value="'+nj.id+'" attrName="'+nj.name+'"/>&nbsp;'+nj.name+'&nbsp;&nbsp;';
			});
			$("#grades_"+$(this).val()).html(html);
		}
	});
	
	var ids = '${mrp.ids}';
	if(ids!=""){
		var xd_nj_arr = JSON.parse(ids);
		for (var i = 0; i < xd_nj_arr.length; i++) {
			var item = xd_nj_arr[i];
			for (var key in item) {
				if(!item.hasOwnProperty(key)){
					continue;
				}
				$("#xd_"+key).attr("checked",true);
				$("#grades_"+$("#xd_"+key).val()).show();
				var njs = item[key];
				for (var j=0; j<njs.length; j++) {
					var njid = njs[j];
					$("#grades_"+$("#xd_"+key).val()).find("#nj_"+njid).attr("checked",true);
				}
			}
		}
		
	}
	
	$(".xdBox").click(function(){
		if($(this).is(':checked')){
			$("#grades_"+$(this).val()).show();
		} else {
			$("#grades_"+$(this).val()).hide();
			$("#grades_"+$(this).val()).find(".njBox").attr("checked",false);
		}
	});

	function saveSchType(){
		var name = $("#lxname").val();
		var sort = $("#lxsort").val();
		var xd_id = "";
		var xd_nj_arr = [];
		$(".xdBox:checked").map(function(){
			xd_id = $(this).val();
			//获取年级
			var xd = $(this).next().next().attr("data");//学段
			var xd_name = $(this).next().next().attr("data-name");//学段
			var grades=[];
			var njBoxes = $(this).next().next().find(".njBox:checked");
			njBoxes.map(function(){
				grades.push(parseInt($(this).val()));
			});
			var xd_nj = {};
			xd_nj[xd_id] = grades;
			xd_nj_arr.push(xd_nj);
		});
		var idsstr = JSON.stringify(xd_nj_arr);
		var descs = $("#ds").val();
		var id = $("#mrpId").val();
		var data = {name:name,sort:sort,ids:idsstr,descs:descs,id:id};
		if(!$("#form").valid()){
			return false;
		}
		$.ajax({
			url:_WEB_CONTEXT_+"/jy/back/jxtx/addOrUpdateSchType",
			type:"POST",
			data:data,
			dataType:"json",
			success:function(result){
				dialogAjaxDone(result);
			},
			error:DWZ.ajaxError
		});
		
	}
</script>

