<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script>
		var se_unit_state = true;
		var yhgl_unit_obj;
		var settingUnit = {
			data: {
				key: {title: "title"},
				simpleData: {enable : true}				
			},
			callback : {
				onClick: zTreeOnClickUnit
			}
		};

		function zTreeOnClickUnit(event, treeId, treeNode){
			if(treeNode.id != 0){
				if(treeNode.flag=="area"){
					$("#yh_unit_l").show();
					$("#yh_unit_load").empty();
					$("#yh_unit_l").hide();
					$("#yh_unit_load").loadUrl(_WEB_CONTEXT_+"/jy/back/yhgl/unitUserIndex?areaId="+treeNode.id+"&userType=1",{},function(){
						$("#yh_unit_load").find("[layoutH]").layoutH();
					});
				}
			}else{
				$("#yh_unit_l").show();
				$("#yh_unit_load").empty();
			}
	    }
		
		var yhgl_unit_ZNode =[]; 
		yhgl_unit_ZNode.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"root",open:true});
		$(document).ready(function(){
			loadZzjgOrgTree("yhgl_unit_areaTree",settingUnit,"area");
			yhgl_unit_obj = $.fn.zTree.getZTreeObj("yhgl_unit_areaTree");
		});
		//搜索部门
		function yhglSearchAllUni(){
			$("#yh_unit_load").empty();
			$("#yh_unit_l").show();
			var zz_sch_tree = [];
			searchAreaC("yhgl_unit_areaTree",settingUnit,"unit_area_look",zz_sch_tree,"area");
		}
		$.validator.addMethod("mobile", function(value, element) {
			var mobilecheck = /^(13|15|18)\d{9}$/i;
			return this.optional(element) || mobilecheck.test(value);
		}, "手机号码格式不正确");
	</script>
	<ul class="info" id="sli_h">
		<li style="height:30px;">
			<p>
				<label style="float: left;height: 25px;line-height: 26px;padding-left: 5px;">部门名称：</label>
				<input type="text" id="unit_area_look" value="" maxlength="10" class="empty" style="width: 125px;float:left;margin-top: 4px;"/>
				<a class="btnLook" onclick="yhglSearchAllUni()" style="margin-top: 4px;margin-left:5px;cursor: pointer;"></a>
			</p>
		</li>
	</ul>
	<div class="pageContent" layoutH="15">
		<div style="float:left; display:block;overflow:hidden; width:222px;line-height:21px; background:#fff">
			<ul id="yhgl_unit_areaTree" class="ztree" layoutH="45"></ul>
		</div>
		<div class="unitBox" style="margin-left:226px;" id="yh_unit_load">
			<div class="prompt">
				<p>
					<span>
						请先选择区域，然后再查看信息
					</span>
				</p>
			</div>
		</div>
		<div class="right" id="yh_unit_l" style="display: none">
			<div class="prompt">
				<p>
					<span>
						请先选择区域，然后再查看信息
					</span>
				</p>
			</div>
		</div>
	</div>
