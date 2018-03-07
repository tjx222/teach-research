<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<script>
		var searchState = true;
		var yhgl_sch_ztree;
		var setting_yh_sch = {
			data: {
				key: {title: "title"},
				simpleData: {enable : true}				
			},
			callback : {
				onClick: yhgl_sc_click
			}
		};

		function yhgl_sc_click(event, treeId, treeNode){
				if(treeNode.flag=="school" || treeNode.flag =="schSearch" ){
					$("#yh_sch_copy").hide();
					$("#yh_sch_load").loadUrl(_WEB_CONTEXT_+"/jy/back/yhgl/schoolUserIndex?orgId="+treeNode.id,{},function(){
						$("#yh_sch_load").find("[layoutH]").layoutH();
					});
				}else{
					$("#yh_sch_copy").hide();
					$("#yh_sch_load").loadUrl(_WEB_CONTEXT_+"/jy/back/yhgl/schoolUserIndex?areaId="+treeNode.id,{},function(){
						$("#yh_sch_load").find("[layoutH]").layoutH();
					});
					if(!treeNode.isParent){
					$.ajax({
						type : "post",
						dataType : "json",
						url : "${pageContext.request.contextPath}/jy/back/yhgl/findSchool", 
						data : {"areaId":treeNode.id,"type":0},
						success : function(data){ 
							$.each(data,function(index,obj){ 
								yhgl_sch_ztree = $.fn.zTree.getZTreeObj("yhgl_school_areaTree");
								yhgl_sch_ztree.addNodes(treeNode, {id:obj.id, pId:treeNode.id, name:obj.shortName,title:obj.name,flag:"school",open:true}); 
							}); 
						}
					});
					}
				}
	    }
		
		var yhgl_sch_ZNode =[]; 
		yhgl_sch_ZNode.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"root",open:true});
		$(document).ready(function(){
			loadZzjgOrgTree("yhgl_school_areaTree",setting_yh_sch,"area");
// 			yhgl_sch_ztree = $.fn.zTree.getZTreeObj("yhgl_school_areaTree");
		});
		//搜索学校
		function searAllSch(){
			$("#yh_sch_copy").show();
			$("#yh_sch_load").empty();
			if($.trim($("#key_look").val())!=""){
				$.ajax({ 
					type : "post",
					async : false,
					dataType : "json",
					data : {"name":$.trim($("#key_look").val()),"type":0},
					url : "${pageContext.request.contextPath}/jy/back/yhgl/searAllSch", 
					success : function(data){ 
						var yhgl_sch_ZNod = [];
						yhgl_sch_ZNod.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"root",open:true});
						$.each(data.areaList,function(n,obj){ 
							yhgl_sch_ZNod.push({ 
								id:obj.id, 
								name:obj.name, 
								title:obj.name, 
								pId:obj.parentId ,
								flag:"area"
							}); 
							
						}); 
						var eload = $.fn.zTree.init($("#yhgl_school_areaTree"), setting_yh_sch, yhgl_sch_ZNod);
						yhgl_sch_ztree = $.fn.zTree.getZTreeObj("yhgl_school_areaTree");
						$.each(data.orgList,function(index,obj){ 
							var areaNode = eload.getNodesByFilter(function(node){
								return (node.flag=='area' && node.id==obj.areaId);
							},true);
							eload.addNodes(areaNode, {id:obj.id, pId:obj.areaId, name:obj.shortName,title:obj.name,flag:"schSearch",open:false},false); 
						})
						searchState = false;
					}
				});
			}else{
				if(searchState){
					alertMsg.info("请您输入学校名称！");
				}else{
					yhgl_sch_ZNode =[]; 
					yhgl_sch_ZNode.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"root",open:true});
					$.ajax({ 
						type : "post",
						dataType : "json",
						url : "${pageContext.request.contextPath}/jy/back/zzjg/orgFindTree", 
						success : function(data){ 
							$.each(data,function(n,d){ 
								yhgl_sch_ZNode.push({ 
									id:d.id, 
									name:d.name, 
									title:d.name, 
									pId:d.parentId ,
									flag:"area"
								}); 
							}); 
							$.fn.zTree.init($("#yhgl_school_areaTree"), setting_yh_sch, yhgl_sch_ZNode);
							yhgl_sch_ztree = $.fn.zTree.getZTreeObj("yhgl_school_areaTree");
							searchState = true;
						}
					});
				}
			}
		}
	</script>
	<ul class="info">
		<li style="height:30px;">
			<p>
				<label style="float: left;height: 25px;line-height: 26px;padding-left: 5px;">学校名称：</label>
				<input type="text" id="key_look" value="" maxlength="10" class="empty" style="width: 125px;float:left;margin-top: 4px;"/>
				<a class="btnLook" onclick="searAllSch()" style="margin-top: 4px;margin-left:5px;cursor: pointer;"></a>
			</p>
		</li>
	</ul>
	<div class="pageContent" layoutH="15">
		<div style="float:left; display:block;overflow:hidden; width:222px;line-height:21px; background:#fff">
			<ul id="yhgl_school_areaTree" class="ztree" layoutH="46"></ul>
		</div>
		<div class="unitBox" style="margin-left:226px;" id="yh_sch_load" >
			<div class="prompt">
				<p>
					<span>
						请先选择区域下的学校，然后再查看信息
					</span>
				</p>
			</div>
		</div>
		<div class="unitBox" id="yh_sch_copy" style="margin-left:226px;display: none">
			<div class="prompt">
				<p>
					<span>
						请先选择区域下的学校，然后再查看信息
					</span>
				</p>
			</div>
		</div>
	</div>
	
