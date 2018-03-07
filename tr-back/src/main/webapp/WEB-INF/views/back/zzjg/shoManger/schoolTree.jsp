<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script>
		var set_sch_tree = {
			data: {
				key: {
					title: "name"
				},
				simpleData: {
					enable : true
				}				
			},
			callback : {
				onClick: onClick_sch
			}
		};

		function onClick_sch(event, treeId, treeNode){
			if(!treeNode.isParent && treeNode.flag=="zzjg_sch"){
				$("#zzjh_sch_l").hide();
				$("#sch_load").loadUrl("${pageContext.request.contextPath}/jy/back/zzjg/schInfoFind?areaId="+treeNode.id+"&type=0",{},function(){
					$("#sch_load").find("[layoutH]").layoutH();
				});
			}else{
				$("#zzjh_sch_l").hide();
				$("#sch_load").loadUrl("${pageContext.request.contextPath}/jy/back/zzjg/schInfoFind?areaIds="+(treeNode.id == '0'?'':treeNode.id)+"&type=0",{},function(){
					$("#sch_load").find("[layoutH]").layoutH();
				});
			}
	    }
		
		$().ready(function(){
			loadZzjgOrgTree("area_SchTree",set_sch_tree,"zzjg_sch");
		});
		function searZZjgSch(){
			$("#sch_load").empty();
			$("#zzjh_sch_l").show();
			var zz_sch_tree = [];
			searchAreaC("area_SchTree",set_sch_tree,"zzjg_sch_look",zz_sch_tree,"zzjg_sch");
		}
	</script>
	<div class="pageHeader">
		<div class="searchBar">
			<div class="subBar">
				<label>学校管理</label>
				<ul>
					<li><a class="button" href="${ctx}/jy/back/zzjg/direcInfo" target="navTab" rel="orgNa_zs"><span>直属校管理</span></a></li>
					<li><a class="button" href="${ctx}/jy/back/zzjg/syncSch" target="navTab" rel="orgNa_sync"><span>同步学校</span></a></li>
				</ul>
			</div>
		</div>
	</div>
	<ul class="info">
		<li style="height:30px;">
			<p>
				<label style="float: left;height: 25px;line-height: 26px;padding-left: 5px;">搜索名称：</label>
				<input type="text" id="zzjg_sch_look" value="" class="empty" style="width: 108px;float:left;margin-top: 4px;"/>
				<a class="btnLook" onclick="searZZjgSch()" style="margin-top: 4px;margin-left:5px;cursor:pointer;"></a>
			</p>
		</li>
	</ul>
	<div class="pageContent" layoutH="60">
		<div style="float:left; display:block;overflow:hidden; width:222px;line-height:21px; background:#fff">
			<ul id="area_SchTree" class="ztree" layoutH="90"></ul>
		</div>
		<div id="sch_load" class="unitBox" style="margin-left:226px;">
			<div class="prompt" id="sch_prompt">
				<p>
					<span>
						请先选择区域，然后再查看信息
					</span>
				</p>
			</div>
		</div>
		<div class="unitBox" id="zzjh_sch_l" style="margin-left:226px;display: none" >
			<div class="prompt">
				<p>
					<span>
						请先选择区域，然后再查看信息
					</span>
				</p>
			</div>
		</div>
	</div>
