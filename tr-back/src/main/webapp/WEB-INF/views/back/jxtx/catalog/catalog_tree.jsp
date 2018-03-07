<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.this_content_ml{width:400px;height:200px; float: left;border:dashed 1px #ddd;}
	.this_content_ml p{width: 100%; height: 35px;line-height: 35px;}
	.this_content_ml p label{width:100px; height: 35px;line-height: 35px;text-align: right;margin-left: 10px;display: block;float: left;margin-right: 10px;}
</style>
<div layoutH="50" style="float:left; display:block; overflow:auto; width:100%; border:solid 1px #CCC; line-height:21px; background:#fff;overflow: scroll;">
	<div layoutH="52"  style="float:left; display:block; overflow:auto; width:240px;border:dashed 1px #ddd;">
		<div style="padding: 15px 0px 2px 5px;font-weight: bolder;">教材目录树型结构</div>			
	    <ul class="tree treeFolder collapse" >
	    	<c:forEach items="${list }" var="one">
	    		<c:if test="${empty one.child }"><li><a title="${one.name }" chaptername="${one.name }" chapterLevel="${one.chapterLevel }"  parentid="${one.parentId }" ordernum="${one.orderNum }" onclick="setValue('${one.id }',1,1,this)">${one.name }</a></li></c:if>
	    		<c:if test="${not empty one.child }">
		    		<li><a title="${one.name }" chaptername="${one.name }" chapterLevel="${one.chapterLevel }" parentid="${one.parentId }" ordernum="${one.orderNum }" onclick="setValue('${one.id }',1,0,this)" >${one.name }</a>
						<ul>
							<c:forEach items="${one.child }" var="two">
								<c:if test="${empty two.child }"><li><a title="${two.name }" chaptername="${two.name }" chapterLevel="${two.chapterLevel }" parentid="${two.parentId }" ordernum="${two.orderNum }" onclick="setValue('${two.id }',2,1,this)" >${two.name }</a></li></c:if>
								<c:if test="${not empty two.child }">
									<li><a title="${two.name }" chaptername="${two.name }" chapterLevel="${two.chapterLevel }" parentid="${two.parentId }" ordernum="${two.orderNum }" onclick="setValue('${two.id }',2,0,this)" >${two.name }</a>
										<ul>
											<c:forEach items="${two.child }" var="three">
												<c:if test="${empty three.child }"><li><a title="${three.name }" chaptername="${three.name }" chapterLevel="${three.chapterLevel }" parentid="${three.parentId }" ordernum="${three.orderNum }" onclick="setValue('${three.id }',3,1,this)" >${three.name }</a></li></c:if>
												<c:if test="${not empty three.child }">
													<li><a title="${three.name }" chaptername="${three.name }" chapterLevel="${three.chapterLevel }" parentid="${three.parentId }" ordernum="${three.orderNum }" onclick="setValue('${three.id }',3,0,this)" >${three.name }</a>
														<ul>
															<c:forEach items="${three.child }" var="four">
																<li><a title="${four.name }" chaptername="${four.name }" chapterLevel="${four.chapterLevel }" parentid="${four.parentId }" ordernum="${four.orderNum }" onclick="setValue('${four.id }',4,1,this)" >${four.name }</a></li>
															</c:forEach>
														</ul>
													</li>
												</c:if>
											</c:forEach>
										</ul>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</li>
	    		</c:if>
	    	</c:forEach>
	     </ul>
	</div>
	<div style="height: 20px;"></div>
	<div style="width: 120px;float: left;line-height:21px;">
		<a class="button" style="margin-left: 10px;" href="javascript:dothis(1)" ><span>新增同级</span></a><br/><br/>
		<a id="zengziji" class="button" style="margin-left: 10px;" href="javascript:dothis(2)" ><span>新增子级</span></a><br/><br/>
		<a class="button" style="margin-left: 10px;" href="javascript:dothis(3)" ><span>修改节点</span></a><br/><br/>
		<a id="deljiedian" class="button" style="margin-left: 10px;" href="javascript:dothis(4)" ><span>删除节点</span></a><br/><br/>
		<a class="button" style="margin-left: 10px;" onclick="updataChapter()" ><span>更新目录</span></a><br/><br/>
		<a class="button" style="margin-left: 10px;" onclick="refreshCache()" ><span>刷新缓存</span></a><br/><br/>
		<a class="button" style="margin-left: 10px;" href="${ctx }jy/back/jxtx/exportChapter?&comId=${comId }" ><span>导出目录</span></a>
	</div>
	<div id="this_content_ml_id" class="this_content_ml" style="display: none;" >
			<input type="hidden" id="comId" value="${comId }"/>
			<input type="hidden" id="chapterId"/>
			<input type="hidden" id="parentId"/>
			<input type="hidden" id="chapterLevel"/>
			<p style="text-align: center;font-weight: bolder;margin-bottom: 3px;">目录节点信息维护</p>
			<p>
				<label>目录的层级：</label>
				<span id="level_span" style="position: relative;top:10px;"></span>
			</p>
			<p>
				<label>目录的名称：</label>
				<input id="chapterName" name="chapterName"  type="text" maxlength="100" value="" style="width:200px;position: relative;top: 3px;" />
			</p>
			<p>
				<label>目录的排序：</label>
				<input type="text" id="orderNum" name="orderNum"  value=""  maxlength="2" style="width: 60px;position: relative;top: 3px;"/>
			</p>
			<p style="padding-left: 140px;width: 160px;margin-top: 15px;">
				<a class="button"  onclick="saveChapter()"><span>保 存</span></a>
				<a class="button" style="margin-left: 30px;" href="javascript:hid_this_div()" ><span>取 消</span></a>
			</p>
	</div>
	
     
</div>

<script type="text/javascript">
	var chapterId = "";
	var parentId = "";
	var orderNum = "";
	var levelnum = "";
	var chapterName = "";
	var chapterLevel="";
	//目录节点操作
	function dothis(type){
		if(chapterId==""){
			alertMsg.info("请您先在左边选择一个节点再进行操作！");
		}else{
			$("#level_span",navTab.getCurrentPanel()).html("");
			if(type==1){//增加同级
				$("#level_span",navTab.getCurrentPanel()).html("第"+levelnum+"层");
				$("#chapterLevel",navTab.getCurrentPanel()).val(chapterLevel);
				$("#chapterId",navTab.getCurrentPanel()).val("");
				$("#parentId",navTab.getCurrentPanel()).val(parentId);
				$("#chapterName",navTab.getCurrentPanel()).val("个性化教案");
				$("#orderNum",navTab.getCurrentPanel()).val("");
				$("#this_content_ml_id",navTab.getCurrentPanel()).css("display","");
			}else if(type==2){//增加子级
				var newlevel = levelnum*1+1;
				$("#level_span",navTab.getCurrentPanel()).html("第"+newlevel+"层");
				$("#chapterLevel",navTab.getCurrentPanel()).val(chapterLevel*1+1);
				$("#chapterId",navTab.getCurrentPanel()).val("");
				$("#parentId",navTab.getCurrentPanel()).val(chapterId);
				$("#chapterName",navTab.getCurrentPanel()).val("个性化教案");
				$("#orderNum",navTab.getCurrentPanel()).val("");
				$("#this_content_ml_id",navTab.getCurrentPanel()).css("display","");
			}else if(type==3){//修改节点
				$("#level_span",navTab.getCurrentPanel()).html("第"+levelnum+"层");
				$("#chapterLevel",navTab.getCurrentPanel()).val(chapterLevel);
				$("#chapterId",navTab.getCurrentPanel()).val(chapterId);
				$("#parentId",navTab.getCurrentPanel()).val(parentId);
				$("#chapterName",navTab.getCurrentPanel()).val(chapterName);
				$("#orderNum",navTab.getCurrentPanel()).val(orderNum);
				$("#this_content_ml_id",navTab.getCurrentPanel()).css("display","");
			}else if(type==4){//删除节点
				alertMsg.confirm("您确定要删除该节点吗？", {
	                okCall: function(){
                         $.ajax({
     						type : "POST",
     						url  : _WEB_CONTEXT_+"/jy/back/jxtx/delete_catalog.json",
     						data : {"chapterId":chapterId},
     						success:function(data){
     							if(data.result == "success"){
     								alertMsg.correct("删除成功！");
     								parent.reload_catalog_tree("${comId }");
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
	//选中操作
	function setValue(id,level,candel,obj){
		levelnum = level;
		parentId = $(obj).attr("parentid");
		orderNum = $(obj).attr("ordernum");
		chapterName = $(obj).attr("chaptername");
		chapterLevel = $(obj).attr("chapterLevel");
		if(level==4){//不能增加子节点
			$("#zengziji").attr({"class":"buttonDisabled","href":"javascript:;"});
		}else{
			$("#zengziji").attr({"class":"button","href":"javascript:dothis(2);"});
		}
		if(candel==1){//可以删除此节点
			$("#deljiedian").attr({"class":"button","href":"javascript:dothis(4);"});
		}else{//不能删除此节点
			$("#deljiedian").attr({"class":"buttonDisabled","href":"javascript:;"});
		}
		chapterId = id;
	}
	//隐藏节点编辑div
	function hid_this_div(){
		$("#this_content_ml_id",navTab.getCurrentPanel()).css("display","none");
	}
	//保存目录节点数据
	function saveChapter(){
		var chapter_name = $.trim($("#chapterName",navTab.getCurrentPanel()).val());
		var order_num = $.trim($("#orderNum",navTab.getCurrentPanel()).val());
		if(chapter_name!=null && chapter_name!=""){
			if(order_num!=null && order_num!=""){
				if(/^[0-9]*$/.test(order_num)){
					$.ajax({
						type : "POST",
						url  : _WEB_CONTEXT_+"/jy/back/jxtx/save_catalog.json",
						data : {"chapterId":$("#chapterId",navTab.getCurrentPanel()).val(),"parentId":$("#parentId",navTab.getCurrentPanel()).val(),"chapterName":chapter_name,"orderNum":order_num,"comId":$("#comId",navTab.getCurrentPanel()).val(),"chapterLevel":$("#chapterLevel",navTab.getCurrentPanel()).val()},
						success:function(data){
							if(data.result == "success"){
								alertMsg.correct("操作成功！");
								$("#this_content_ml_id",navTab.getCurrentPanel()).css("display","none");
								parent.reload_catalog_tree("${comId }");
							}else if(data.result == "fail"){
								alertMsg.error("操作失败，请稍后重试！");
							}
						}
					});
				}else{
					alertMsg.info("排序值必须是数字形式！");
					$("#orderNum",navTab.getCurrentPanel()).focus();
				}
			}else{
				alertMsg.info("请您输入排序值！");
				$("#orderNum",navTab.getCurrentPanel()).focus();
			}
		}else{
			alertMsg.info("请您输入目录名称！");
			$("#chapterName",navTab.getCurrentPanel()).focus();
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
</script>

	

	