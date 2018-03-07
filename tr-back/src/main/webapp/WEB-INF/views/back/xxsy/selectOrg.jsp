<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script type="text/javascript" src="${ctxStatic }/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
	<script>
	var ${divId}_tree={
		se_unit_state:true,
		yhgl_unit_obj:{}
	};
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
			if(!treeNode.isParent){
				if(treeNode.flag=="area"){
					$("#yh_unit_l_${divId}").show();
					$("#${divId}").empty();
					$.ajax({
						type : "post",
						dataType : "json",
						url : "${pageContext.request.contextPath}/jy/back/yhgl/findUnit", 
						data : {"areaId":treeNode.id,type:0},
						success : function(data){ 
							$.each(data,function(index,obj){ 
								${divId}_tree.yhgl_unit_obj.addNodes(treeNode, {id:obj.id, pId:treeNode.id, name:obj.shortName,title:obj.name,flag:"unit",open:true}); 
							}); 
						}
					});
					
				}else if(treeNode.flag=="unit"){
					$("#yh_unit_l_${divId}").hide();
					$("#${divId}").loadUrl(_WEB_CONTEXT_+"${orgurl}"+treeNode.id,{},function(){
						$("#${divId}").find("[layoutH]").layoutH();
					});
				}else if(treeNode.flag=="unitSearch"){
					$("#yh_unit_l_${divId}").hide();
					$("#${divId}").loadUrl(_WEB_CONTEXT_+"${orgurl}"+treeNode.id);
				}
			}else{
				$("#yh_unit_l_${divId}").show();
				$("#${divId}").empty();
			}
	    }
		
		var yhgl_unit_ZNode =[]; 
		yhgl_unit_ZNode.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"root",open:true});
		$(document).ready(function(){
			$.ajax({ 
				type : "post",
				dataType : "json",
				url : "${pageContext.request.contextPath}/jy/back/zzjg/orgFindTree", 
				success : function(data){ 
					$.each(data,function(n,d){ 
						yhgl_unit_ZNode.push({ 
							id:d.id, 
							name:d.name, 
							title:d.name, 
							pId:d.parentId ,
							flag:"area"
						}); 
					}); 
					$.fn.zTree.init($("#yhgl_unit_obj_${divId}"), settingUnit, yhgl_unit_ZNode);
					${divId}_tree.yhgl_unit_obj = $.fn.zTree.getZTreeObj("yhgl_unit_obj_${divId}");
				}
			});
		});
		//搜索部门
		function yhglSearchAllUnit(){
			$("#yh_unit_l_${divId}").show();
			$("#${divId}").empty();
			if($.trim($("#unit_area_look").val())!=""){
				$.ajax({ 
					type : "post",
					dataType : "json",
					async : false,
					data : {"name":$.trim($("#unit_area_look").val()),type:1},
					url : "${pageContext.request.contextPath}/jy/back/yhgl/searAllUnit", 
					success : function(data){ 
						yhgl_unit_ZNode = [];
						yhgl_unit_ZNode.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"root",open:true});
						$.each(data.areaList,function(n,obj){ 
							yhgl_unit_ZNode.push({ 
								id:obj.id, 
								name:obj.name, 
								title:obj.name, 
								pId:obj.parentId ,
								flag:"area",
								open:false
							}); 
						}); 
						var eload = $.fn.zTree.init($("#yhgl_unit_obj_${divId}"), settingUnit, yhgl_unit_ZNode);
						${divId}_tree.yhgl_unit_obj = $.fn.zTree.getZTreeObj("yhgl_unit_obj_${divId}");
						$.each(data.orgList,function(index,obj){ 
							var areaNode = eload.getNodesByFilter(function(node){
								return (node.flag=='area' && node.id==obj.areaId);
							},true);
							eload.addNodes(areaNode, {id:obj.id, pId:obj.areaId, name:obj.shortName,title:obj.name,flag:"unitSearch",open:false},false); 
						})
						se_unit_state = false;
					}
				});
			}else{
				if(se_unit_state){
					alertMsg.info("请您输入部门名称！");
				}else{
					yhgl_unit_ZNode =[]; 
					yhgl_unit_ZNode.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"root",open:true});
					$.ajax({ 
						type : "post",
						dataType : "json",
						url : "${pageContext.request.contextPath}/jy/back/zzjg/orgFindTree", 
						success : function(data){ 
							$.each(data,function(n,d){ 
								yhgl_unit_ZNode.push({ 
									id:d.id, 
									name:d.name, 
									title:d.name, 
									pId:d.parentId ,
									flag:"area"
								}); 
							}); 
							$.fn.zTree.init($("#yhgl_unit_obj_${divId}"), settingUnit, yhgl_unit_ZNode);
							${divId}_tree.yhgl_unit_obj = $.fn.zTree.getZTreeObj("yhgl_unit_obj_${divId}");
							se_unit_state = true;
						}
					});
				}
			}
		}
	</script>
	<ul class="info" id="sli_h">
		<li style="height:30px;">
			<p>
				<label style="float: left;height: 25px;line-height: 26px;padding-left: 5px;">部门名称：</label>
				<input type="text" id="unit_area_look" value="" maxlength="10" class="empty" style="width: 125px;float:left;margin-top: 4px;"/>
				<a class="btnLook" onclick="yhglSearchAllUnit()" style="margin-top: 4px;margin-left:5px;cursor: pointer;"></a>
			</p>
		</li>
	</ul>
	<div class="pageContent" layoutH="15">
		<div style="float:left; display:block;overflow:hidden; width:222px;line-height:21px; background:#fff">
			<ul id="yhgl_unit_obj_${divId}" class="ztree" layoutH="45"></ul>
		</div>
		<div id="${divId}" class="unitBox" style="margin-left:226px;">
			<div class="prompt"  >
				<p>
					<span>
						请先选择机构!
					</span>
				</p>
			</div>
		</div>
		<div  id="yh_unit_l_${divId}" class="unitBox" style="margin-left:226px;display: none;">
			<div class="prompt">
				<p>
					<span>
						请先选择机构!
					</span>
				</p>
			</div>
		</div>
	</div>
