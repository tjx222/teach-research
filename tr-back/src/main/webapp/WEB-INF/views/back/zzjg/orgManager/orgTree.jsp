<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
	<SCRIPT type="text/javascript">
		var set_zzjg_org = {
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
				onClick: zTreeOnClickOrg
			}
		};
		function zTreeOnClickOrg(event, treeId, treeNode){
			if(treeNode.level != 0){
				$("#org_load").hide();
				$("#area_show").show();
		        $("#areaName").html(treeNode.name);
		        var id = treeNode.id;
		        $.getJSON("${ctx }jy/back/zzjg/getPostCodeById",{id:id},
	        		function(data){
	        			$("#areaPostcode").html(data.code);
	        		},"json");
		        
			}else{
				$("#org_load").show();
				$("#area_show").hide();
			}
	     }
		
		$().ready(function(){
			loadZzjgOrgTree("areaTree_org",set_zzjg_org,"zzj_or");
		});
		function searZZjgOrg(){
			$("#area_show").hide();
			$("#org_load").hide();
			var or_no_ztree = [];
			searchAreaC("areaTree_org",set_zzjg_org,"key_area_zzjg",or_no_ztree,"zzj_or");
		}
	</SCRIPT>
		<ul class="info">
		   <li style="height:30px;">
				<p>
					<label style="float: left;height: 25px;line-height: 26px;padding-left: 5px;">搜索名称：</label>
					<input type="text" id="key_area_zzjg" value="" style="width: 108px;float:left;margin-top: 4px;" class="empty" />
					<a class="btnLook" id="are_a" style="margin-top: 4px;margin-left:5px;cursor:pointer;" onclick="searZZjgOrg()">查找带回</a>
				</p>
			</li>
		</ul>
		
	<div class="pageContent" layoutH="20">
		<div style="float:left; display:block;overflow:hidden; width:222px;line-height:21px; background:#fff">
			<ul id="areaTree_org" class="ztree" layoutH="50"></ul>
		</div>
			<div class="unitBox"  id="org_load">
				<div class="prompt" id="org_prompt">
				<p>
					<span>
						请先选择区域，然后再查看信息
					</span>
					</p>
				</div>
			</div>
		<div style="display:none" class="unitBox" id="area_show">
				<div class="unit1">
						<label style="line-height:50px;">区域代码：</label><span id="areaPostcode">
						</span>
				</div>
				<div class="unit1">
						<label style="line-height:40px;">区域名称：</label><span id="areaName"></span>
				</div>
		</div>
	</div>
	<style>
	#area_show{
		width: 200px;
		height: 90px;
		border: 1px #ccc solid;
		margin: 50px auto;
	}
	.unit1{
		height:45px;
		text-align:center;
	}
	.unit1 label{
		font-size:14px;
		height:45px;
	}
	</style>