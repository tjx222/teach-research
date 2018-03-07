<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.this_content_ml{width:400px;height:200px; float: left;border:dashed 1px #ddd;}
	.this_content_ml p{width: 100%; height: 35px;line-height: 35px;}
	.this_content_ml p label{width:100px; height: 35px;line-height: 35px;text-align: right;margin-left: 10px;display: block;float: left;margin-right: 10px;}
</style>
<div layoutH="80" style="float:left; display:block; overflow:auto; width:100%; border:solid 1px #CCC; line-height:21px; background:#fff;overflow: scroll;">
	<div style="float:left; display:block; overflow:auto; width:240px;border:dashed 1px #ddd;">
	    <div class="content_wrap">
		<div class="zTreeDemoBackground left">
			<ul id="chapter_tree_org" class="ztree" layoutH="100"></ul>
		</div>
	</div>
	</div>
	<div style="height: 20px;"></div>
	<c:if test="${canEdit }">
		<div style="width: 120px;float: left;line-height:21px;">
			<a id="zengziji" class="button" style="margin-left: 10px;" href="javascript:orgChapterOperate(2)" ><span>新增子级</span></a><br/><br/>
			<a id="xgjiedian" class="button" style="margin-left: 10px;" href="javascript:orgChapterOperate(3)" ><span>修改节点</span></a><br/><br/>
			<a id="deljiedian" class="button" style="margin-left: 10px;" href="javascript:orgChapterOperate(4)" ><span>删除节点</span></a><br/><br/>
			<a class="button" style="margin-left: 10px;" href="${ctx }jy/back/jxtx/exportChapter?comId=${comId }" ><span>导出目录</span></a><br/><br/>
			<a class="button" style="margin-left: 10px;" onclick="updataChapter()" ><span>刷新目录</span></a>
		</div>
	</c:if>
	<div id="this_content_ml_id" class="this_content_ml" style="display: none;" >
			<input type="hidden" id="comId" value="${comId }"/>
			<input type="hidden" id="orgchapterId"/>
			<input type="hidden" id="orgparentId"/>
			<input type="hidden" id="orgchapterLevel"/>
			<p style="text-align: center;font-weight: bolder;margin-bottom: 3px;">目录节点信息维护</p>
			<p>
				<label>目录的层级：</label>
				<span id="level_span" style="position: relative;top:10px;"></span>
			</p>
			<p>
				<label>目录的名称：</label>
				<input id="orgchapterName" name="chapterName"  type="text" maxlength="100" value="" style="width:200px;position: relative;top: 3px;" />
			</p>
			<p>
				<label>目录的排序：</label>
				<input type="text" id="orgorderNum" name="orderNum"  value=""  maxlength="2" style="width: 60px;position: relative;top: 3px;"/>
			</p>
			<p style="padding-left: 140px;width: 160px;margin-top: 15px;">
				<a class="button"  onclick="orgsaveChapter()"><span>保 存</span></a>
				<a class="button" style="margin-left: 30px;" href="javascript:hid_this_div()" ><span>取 消</span></a>
			</p>
	</div>
	
     
</div>

<script type="text/javascript">
	var orgchapterId = "";
	var orgparentId = "";
	var orglevelnum = "";
	var orgchapterName = "";
	var orgchapterLevel="";
	var orgdotype="";
	var orgoperatetreeNode;
	
	//目录节点操作
	function orgChapterOperate(type){
		orgdotype = type;
		var orgZtree = $.fn.zTree.getZTreeObj("chapter_tree_org",navTab.getCurrentPanel());
		if(orgchapterId==""){
			alertMsg.info("请您先在左边选择一个节点再进行操作！");
		}else{
			$("#level_span",navTab.getCurrentPanel()).html("");
			if(type==2){//增加子级
				var newlevel = orglevelnum*1+1;
				$("#level_span",navTab.getCurrentPanel()).html("第"+newlevel+"层");
				$("#orgchapterLevel",navTab.getCurrentPanel()).val(orgchapterLevel*1+1);
				$("#orgchapterId",navTab.getCurrentPanel()).val("");
				$("#orgparentId",navTab.getCurrentPanel()).val(orgchapterId);
				$("#orgchapterName",navTab.getCurrentPanel()).val("");
				$("#orgorderNum",navTab.getCurrentPanel()).val("");
				$("#this_content_ml_id",navTab.getCurrentPanel()).css("display","");
			}else if(type==3){//修改节点
				$("#level_span",navTab.getCurrentPanel()).html("第"+orglevelnum+"层");
				$("#orgchapterLevel",navTab.getCurrentPanel()).val(orgchapterLevel);
				$("#orgchapterId",navTab.getCurrentPanel()).val(orgchapterId);
				$("#orgparentId",navTab.getCurrentPanel()).val(orgparentId);
				$("#orgchapterName",navTab.getCurrentPanel()).val(orgchapterName);
				$("#orgorderNum",navTab.getCurrentPanel()).val(orgorderNum);
				$("#this_content_ml_id",navTab.getCurrentPanel()).css("display","");
			}else if(type==4){//删除节点
				alertMsg.confirm("您确定要删除该节点吗？", {
	                okCall: function(){
                         $.ajax({
     						type : "POST",
     						url  : _WEB_CONTEXT_+"/jy/back/jxtx/delete_catalog.json",
     						data : {"chapterId":orgchapterId},
     						success:function(data){
     							if(data.result == "success"){
     								alertMsg.correct("删除成功！");
     								orgZtree.removeNode(orgoperatetreeNode);
     								orgoperatetreeNode="";
     							}else if(data.result == "fail"){
     								alertMsg.error("删除失败，请稍后重试！");
     							}
     						}
     					});
	                }
				});

			}
		}
	}
	//隐藏节点编辑div
	function hid_this_div(){
		$("#this_content_ml_id",navTab.getCurrentPanel()).css("display","none");
	}
	//保存目录节点数据
	function orgsaveChapter(){
		var orgZtree = $.fn.zTree.getZTreeObj("chapter_tree_org",navTab.getCurrentPanel());
		var chapter_name = $.trim($("#orgchapterName",navTab.getCurrentPanel()).val());
		var order_num = $.trim($("#orgorderNum",navTab.getCurrentPanel()).val());
		if(chapter_name!=null && chapter_name!=""){
			if(order_num!=null && order_num!=""){
				if(/^[0-9]*$/.test(order_num)){
					$.ajax({
						type : "POST",
						url  : _WEB_CONTEXT_+"/jy/back/jxtx/save_catalog.json",
						data : {"chapterId":$("#orgchapterId",navTab.getCurrentPanel()).val(),"parentId":$("#orgparentId",navTab.getCurrentPanel()).val(),"chapterName":chapter_name,"orderNum":order_num,"comId":$("#comId",navTab.getCurrentPanel()).val(),"chapterLevel":$("#orgchapterLevel",navTab.getCurrentPanel()).val()},
						success:function(data){
							if(data.result == "success"){
								debugger;
								alertMsg.correct("操作成功！");
								$("#this_content_ml_id",navTab.getCurrentPanel()).css("display","none");
								if (orgdotype==2) {
									var node = data.bookChapter;
									orgZtree.addNodes(orgoperatetreeNode,{
										id:node.chapterId, 
										name:node.chapterName, 
										title:node.chapterName,
										pId:node.parentId,
										chapterLevel:node.chapterLevel,
										orderNumber:node.orderNum
									});
								} else if (orgdotype==3) {
									var node = data.bookChapter;
									orgoperatetreeNode.name=node.chapterName;
									orgZtree.updateNode(orgoperatetreeNode);
								}
							}else if(data.result == "fail"){
								alertMsg.error("操作失败，请稍后重试！");
							}
						}
					});
				}else{
					alertMsg.info("排序值必须是数字形式！");
					$("#orgorderNum",navTab.getCurrentPanel()).focus();
				}
			}else{
				alertMsg.info("请您输入排序值！");
				$("#orgorderNum",navTab.getCurrentPanel()).focus();
			}
		}else{
			alertMsg.info("请您输入目录名称！");
			$("#orgchapterName",navTab.getCurrentPanel()).focus();
		}
	}
	//刷新数据缓存
	function refreshCache(){
		$.ajax({
			type : "POST",
			url  : _WEB_CONTEXT_+"/jy/back/jxtx/refreshCache.json",
			data : {"bookId":"${comId }"},
			success:function(data){
				if(data.result == "success"){
					alertMsg.correct("刷新缓存成功！");
				}else if(data.result == "fail"){
					alertMsg.error("刷新缓存失败！");
				}
			}
		});
	}
// 	更新目录节点信息
	function updataChapter(){
		$.ajax({
			type : "POST",
			url  : _WEB_CONTEXT_+"/jy/back/jxtx/delExportChapter.json",
			data : {"comId":"${comId}"},
			success:function(data){
				if(data.result == "success"){
					alertMsg.correct("目录更新成功！");
				}else if(data.result == "fail"){
					alertMsg.error("目录更新失败！");
				}
			}
		});
	}
	
	
	var chapter_setting = {
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				onClick:orgClickChapterTree
			}
		};
		var chapter_znodes =[];
		chapter_znodes.push({id:-1,pId:-2,chapterLevel:-1,name:"教材目录树型结构",title:"教材目录树型结构",open:true});
		$(document).ready(function(){
			$.ajax({
				type:"post",
				async : false,
				cache:false,
				dataType : "json",
				data:{comId:'${comId}'},
				url : "${pageContext.request.contextPath}/jy/back/schconfig/catalog/catalogTree", 
				success : function(data){
					$.each(data,function(n,d){
						chapter_znodes.push({ 
							id:d.chapterId, 
							name:d.chapterName, 
							title:d.chapterName,
							pId:d.parentId,
							chapterLevel:d.chapterLevel,
							orderNumber:d.orderNum,
						});
					});
				}
			});
			$.fn.zTree.init($("#chapter_tree_org",navTab.getCurrentPanel()), chapter_setting, chapter_znodes);
		});
		function orgClickChapterTree(event, treeId, treeNode){
			orgoperatetreeNode = treeNode;
			orgchapterId=treeNode.id;
			orgchapterName = treeNode.name;
			chapterLevel = treeNode.chapterLevel;
			orgorderNum=treeNode.orderNumber;
			orglevelnum = chapterLevel+1;
			orgparentId=treeNode.pId;
			if (treeNode.children && treeNode.children.length > 0) {//不能删除此节点
				$("#deljiedian",navTab.getCurrentPanel()).attr({"class":"buttonDisabled","href":"javascript:;"});
			}else{
				$("#deljiedian",navTab.getCurrentPanel()).attr({"class":"button","href":"javascript:orgChapterOperate(4);"});
			}
			if (treeNode.id == -1) {//不能修改节点
				$("#xgjiedian",navTab.getCurrentPanel()).attr({"class":"buttonDisabled","href":"javascript:;"});
			}else{
				$("#xgjiedian",navTab.getCurrentPanel()).attr({"class":"button","href":"javascript:orgChapterOperate(3);"});
			}
		}
		
</script>