<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<script type="text/javascript">
		var settings_zz_unit = {
			data: {
				key: {
					title: "name"
				},
				simpleData: {
					enable : true
					
				}				
			},
			callback : 
			{
				onClick: zTreeOnUnitClick
			}
		};

		function zTreeOnUnitClick(event, treeId, treeNode){
				$("#zzjh_unit_l").hide();
				if(treeNode.id  == '0'){
					var arearsch = "";
					$("#zzjg_load").loadUrl("${ctx}/jy/back/zzjg/unitInfoFind?type=1&areaIds="+arearsch+"",{},function(){
						$("#zzjg_load").find("[layoutH]").layoutH();
					});
				}else{
					$("#zzjg_load").loadUrl("${ctx}/jy/back/zzjg/unitInfoFind?type=1&areaId="+treeNode.id+"",{},function(){
						$("#zzjg_load").find("[layoutH]").layoutH();
					});
				}
	     }
		$().ready(function(){
			loadZzjgOrgTree("zzjg_Tree",settings_zz_unit,"xzjg");
		});
		function searZZjgUni(){
			$("#zzjg_load").empty();
			$("#zzjh_unit_l").show();
			var zzjg_unit_tree = [];
			searchAreaC("zzjg_Tree",settings_zz_unit,"key_unit",zzjg_unit_tree,"xzjg");
		}
	</script>
<div id="unit_info">
		<ul class="info">
				<li style="height:30px;">
					<p>
						<label style="float: left;height: 25px;line-height: 26px;padding-left: 5px;">搜索名称：</label>
						<input type="text" id="key_unit" value="" class="empty" style="width: 108px;float:left;margin-top: 4px;"/>
						<a class="btnLook" onclick="searZZjgUni()" style="margin-top: 4px;margin-left:5px;cursor:pointer;"></a><br/>
						
					</p>
				</li>
		</ul>

		<div class="pageContent" layoutH="30">
			<div style="float:left; display:block;overflow:hidden; width:222px;line-height:21px; background:#fff">
				<ul id="zzjg_Tree" class="ztree" layoutH="53"></ul>
			</div>
			<div class="unitBox" style="margin-left:226px;" id="zzjg_load">
				<div class="prompt" id="zzjg_prompt">
					<p>
						<span>
							请先选择区域，然后再查看信息
						</span>
					</p>
				</div>
			</div>
			<div class="unitBox" id="zzjh_unit_l" style="display:none;margin-left:226px;">
			<div class="prompt">
				<p>
					<span>
						请先选择区域，然后再查看信息
					</span>
				</p>
			</div>
		</div>
		</div>
</div>
