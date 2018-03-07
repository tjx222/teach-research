<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

	<script type="text/javascript" src="${ctxStatic }/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript">
	var set_dir_look = {
			view: {
				dblClickExpand: false,
			},
			data: {
				key: {
					title: "name"
				},
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick: onClickDir
			},
		};
	function onClickDir(e, treeId, treeNode) {
		$("#dir_prompt").hide();
		if(treeNode.level==1){
			$("#zzjh_dir_l").hide();
			$("#dirt_load").loadUrl("${pageContext.request.contextPath}/jy/back/zzjg/dirtSchFind?directAreaId="+treeNode.id,{},function(){
				$("#dirt_load").find("[layoutH]").layoutH();
			});
		}
		if(treeNode.level==2){
			$("#zzjh_dir_l").hide();
			$("#dirt_load").loadUrl("${pageContext.request.contextPath}/jy/back/zzjg/dirtSchFind?directAreaId="+treeNode.id,{},function(){
				$("#dirt_load").find("[layoutH]").layoutH();
			});
		}
		if(treeNode.level==0){
			$("#dirt_load").empty();
			$("#zzjh_dir_l").show();
		}
	}
	$().ready(function(){
		loadDirTree();
	});
	function loadDirTree(){
		$("#dir_SchTree").empty();
		var dirZNode = [];
		dirZNode.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"org",open:true});
		$.ajax({ 
			dataType : "json",
			url : "${pageContext.request.contextPath}/jy/back/zzjg/directSchTree", 
			success : function(data){ 
				$.each(data,function(n,d){ 
					dirZNode.push({ 
						id:d.id, 
						name:d.name, 
						pId:d.parentId 
					}) 
				}); 
				$.fn.zTree.init($("#dir_SchTree"), set_dir_look, dirZNode);
				var zTreeDir = $.fn.zTree.getZTreeObj("dir_SchTree");
			}
		});
		
	}
	function searDirtZzjg(){
		$("#dirt_load").empty();
		$("#zzjh_dir_l").show();
		$("#dir_SchTree").empty();
		$.fn.zTree.destroy("dir_SchTree");
		if($.trim($("#zzj_di_ser").val())!=""){
			$.ajax({ 
				type : "post",
				dataType : "json",
				cache:false,
				data : {"name":$.trim($("#zzj_di_ser").val())},
				url : "${pageContext.request.contextPath}/jy/back/zzjg/searCodition?type=1", 
				success : function(data){ 
					var zNodeData = [];
					zNodeData.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"org",open:true});
					$.each(data,function(n,obj){ 
						zNodeData.push({ 
							id:obj.id, 
							name:obj.name, 
							pId:obj.parentId,
							flag:"dir"
						}); 
					}); 
					$("#dir_SchTree").addClass("ztree");
					$.fn.zTree.init($("#dir_SchTree"), set_dir_look,zNodeData);
					var treeObj = $.fn.zTree.getZTreeObj("dir_SchTree");
					treeObj.expandAll(true);
				}
			});
		}else{
			loadDirTree();
		}
	}
	
	</script>
	<div class="pageHeader">
		<div class="searchBar">
			<div class="subBar">
				<label>直属校管理</label>
				<ul>
					<li><a class="button" href="${ctx}jy/back/zzjg/goSchTree" target="navTab" rel="orgNa"><span>学校管理</span></a></li>
				</ul>
			</div>
		</div>
	</div>
	<ul class="info">
		<li style="height:30px;">
			<p>
				<label style="float: left;height: 25px;line-height: 26px;padding-left: 5px;">搜索名称：</label>
				<input type="text" id="zzj_di_ser" value="" class="empty" style="width: 108px;float:left;margin-top: 4px;"/>
				<a class="btnLook" onclick="searDirtZzjg()"style="margin-top: 4px;margin-left:5px;cursor:pointer;"></a><br/>
			</p>
		</li>
	</ul>
	<div class="content_wrap" layoutH="50" >
		<div style="float:left; display:block;overflow:hidden; width:222px;line-height:21px; background:#fff" >
			<ul id="dir_SchTree" class="ztree" layoutH="90"></ul>
		</div>
		<div class="unitBox" style="margin-left:228px;" id="dirt_load">
			<div class="prompt" id="dir_prompt">
				<p>
					<span>
						请先选择区域，然后再查看信息
					</span>
				</p>
			</div>
		</div>
		<div id="zzjh_dir_l" class="unitBox" style="margin-left:228px;display: none">
			<div class="prompt">
				<p>
					<span>
						请先选择区域，然后再查看信息
					</span>
				</p>
			</div>
		</div>
	</div>
