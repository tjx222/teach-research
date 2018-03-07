<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					出版社名称筛选：<input type="text" name="cbs_srcname" id="cbs_srcname"/>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="pageContent">
	<form method="post" action="${ctx }/jy/back/jxtx/saveCbs" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	<div class="panelBar" style="height: 0px;">
	</div>
	<table class="table" width="100%" layoutH="80">
		<thead>
			<tr>
				<th width="60" style="text-align: center;">选择</th>
				<th width="240">出版社全称</th>
				<th width="180">出版社简称</th>
				<th width="100">显示顺序</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${cbslist }" var="cbs" varStatus="st">
				<tr data-name="${cbs.name }">
					<td style="text-align: center;"><input type="checkbox" name="cbs1"  value="${cbs.id }" /></td>
					<td><input type="hidden" value="${cbs.name }"/>${cbs.name }</td>
					<td><input type="text" value="${cbs.name }" maxlength="10"/></td>
					<td><input type="text" style="width:50px;" value="${st.index+1 }"   maxlength="2"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="formBar">
		<label style="float:left"><input type="checkbox" class="checkboxCtrl" group="cbs1" />全选</label>
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" onclick="saveCbs();">保存</button></div></div></li>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
			</li>
		</ul>
	</div>
	</form>
</div>
<script type="text/javascript">
	//保存操作提交
	function saveCbs(){
		var subArr = {};
		var selectCount = 0;
		var isreturn = false;
		$("[name='cbs1']:checked").each(function(index,obj){
		  selectCount = index+1;
		  var otr =  $(obj).parents("tr");
		  var tdArr = $(otr).children("td");
		  $.each(tdArr,function(i,o){
			  var inp = $(o).find("input");
			  if(i==0){
				  subArr["publishs["+index+"].eid"] = $(inp).val();
			  }else if(i==1){
				  subArr["publishs["+index+"].name"] = $(inp).val();
			  }else if(i==2){
				  if($.trim($(inp).val())==""){
					  alertMsg.warn("选择项必须输入出版社简称！");
					  isreturn = true;
					  return false;
				  }
				  subArr["publishs["+index+"].shortName"] = $(inp).val();
			  }else if(i==3){
				  if($.trim($(inp).val())=="" || !/^[0-9]*$/.test($.trim($(inp).val()))){
					  alertMsg.warn("选择项必须输入数字类型的排序值！");
					  isreturn = true;
					  return false;
				  }
				  subArr["publishs["+index+"].sort"] = $(inp).val();
				  subArr["publishs["+index+"].phaseId"] = "${pr.phaseId }";
				  subArr["publishs["+index+"].subjectId"] = "${pr.subjectId }";
			  }
		  });
		});
		if(isreturn){
			return false;
		}
		if(selectCount>0){
			$.ajax({
				type : "POST",
				url  : _WEB_CONTEXT_+"/jy/back/jxtx/saveCbs",
				data : subArr,
				beforeSend : function() {
					return true;
				},
				success:function(){
					$.pdialog.closeCurrent();
					alertMsg.correct("添加成功！");
					parent.reloadCbsBox("${pr.phaseId }","${pr.subjectId }");
				}
			});
		}else{
			alertMsg.warn("请您至少选择一项！");
		}
	}
	var _selectCbs = function(obj){
		  var name = $(obj).val();
		  var ftr = $("div.pageContent tbody >tr:first-child", $.pdialog._current);
		  var fline = 0;
		  $("div.pageContent tbody tr",$.pdialog._current).each(function(index){
			  $this = $(this);
			  if($this.attr("data-name").indexOf(name) == -1){
				  $this.hide();
			  }else{
				  $this.show();
				  if(index != 0 && fline == 0){
					  $(">td",$this).each(function(i){
							$(this).width($($(">td",ftr)[i]).width());
						});
					  fline = 1;
				  }
			  }
		  });
	};
	$("#cbs_srcname",$.pdialog._current).focus(false).click(false).keyup(function(event){
		var $items = $(this);
		switch(event.keyCode){
			case DWZ.keyCode.ESC:
			case DWZ.keyCode.TAB:
			case DWZ.keyCode.SHIFT:
			case DWZ.keyCode.HOME:
			case DWZ.keyCode.END:
			case DWZ.keyCode.LEFT:
			case DWZ.keyCode.RIGHT:
			case DWZ.keyCode.ENTER:
			case DWZ.keyCode.DOWN:
			case DWZ.keyCode.UP:
				break;
			default:
				 _selectCbs($items);
		}
	});

</script>
