<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<div class="grid" id="schoolShow"
		style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
		<div class="panelBar">
			<ul class="toolBar">
				<li><a class="add" href="${ctx}/jy/back/xxsy/show/addOrEdit?type=${type}&orgId=${orgId}"   callback="schoolShow.reload" 
					target="dialog" mask="true" width="780" height="600"><span>新建</span></a></li>
				<li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids"  postType="string"  callback="schoolShow.reload"
					href="${ctx}/jy/back/xxsy/show/batchDelete" class="delete"><span>批量删除</span></a></li>
				<%-- <li><a title="编辑学校信息" target="dialog" 
					href="${pageContext.request.contextPath}/jy/back/zzjg/schInfoManager?orgId=${orgId}"  value="" class="edit"><span>学校信息维护</span></a></li> --%>
				<li class="line">line</li>
			</ul>
		</div>
		<form id="pagerForm" action="${ctx}/jy/back/xxsy/show/list" method="post" onsubmit="return validateCallback(this, schoolShow.reload);">
			<table class="table" width="100%" layoutH="110" >
				<thead>
					<tr align="center">
						<th width="22"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
						<th width="10%">标题</th>
						<th width="10%">展示类型</th>
						<th width="20%" orderField="crtDttm" class="${schoolShow.flago == 'crtDttm' ?  schoolShow.flags == 'desc' ? 'desc' : 'asc' :'cansort'}">创建时间</th>
						<th width="10%">作者</th>
						<th width="30%">概览</th>
						<th width="20%">操作 </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${schoolShowList.datalist}" var="obj">
						<tr target="sid_obj" rel="${obj.id }" align="center">
							<td><input name="ids" value="${obj.id }" type="checkbox"></td>
							<td>${obj.title}</td>
							<c:if test="${obj.type eq 'master'}">
									<td>校长风采</td>
							</c:if>
							<c:if test="${obj.type eq 'overview'}">
									<td>学校概况</td>
							</c:if>
							<c:if test="${obj.type eq 'bignews'}">
									<td>学校要闻</td>
							</c:if>
							<c:if test="${obj.type ne 'overview' && obj.type ne 'master' && obj.type ne 'bignews'}">
									<td>其他 </td>
							</c:if>
						
							<td><fmt:formatDate value="${obj.crtDttm }" pattern="yyyy-MM-dd HH:mm"/></td>
							<td>${obj.author}</td>
							<td>${obj.introduction} </td>
							<td>
							    <a title="查看" target="dialog" href="${ctx}/jy/back/xxsy/show/view?id=${obj.id}&type=${type}" value="" class="btnSee" callback="schoolShow.reload" mask="true" width="780" height="600"></a>
								<a title="编辑" target="dialog" href="${ctx}/jy/back/xxsy/show/addOrEdit?id=${obj.id}&type=${type}&orgId=${orgId}" value="" class="btn_Edit" callback="schoolShow.reload" mask="true" width="780" height="600"></a>
								<a title="确认要删除吗?" target="ajaxTodo" href="${ctx}/jy/back/xxsy/show/delete?id=${obj.id}&type=${type}"  class="btnDelete schDel" callback="schoolShow.reload" mask="true" width="780" height="600"></a>
								
								
								<c:if test="${obj.enable ne 1}">
									<a title="确认要发布吗?" target="ajaxTodo" href="${ctx}/jy/back/xxsy/show/publish?id=${obj.id}&type=${type}&orgId=${obj.orgId}"  class="btnPunish" callback="schoolShow.viewSchoolShow" mask="true" width="780" height="600"></a>
								</c:if>
								<c:if test="${obj.enable eq 1}">
									<a title="要取消发布吗?" target="ajaxTodo" href="${ctx}/jy/back/xxsy/show/unpublish?id=${obj.id}&type=${type}&orgId=${obj.orgId}"  class="btnPunish2" callback="schoolShow.reload" mask="true" width="780" height="600"></a>
								</c:if>
								<c:if test="${obj.type eq 'bignews'}">
									<c:choose>
										<c:when test="${obj.topTag eq 0|| obj.topTag eq null}">
											<a title="要置顶吗?" target="ajaxTodo" href="${ctx}/jy/back/xxsy/show/setTop?id=${obj.id}&type=${type}&orgId=${obj.orgId}"  class="btnTop" callback="schoolShow.reload" mask="true" width="780" height="600"></a>
										</c:when>
										<c:otherwise>
											<a title="要取消置顶吗?" target="ajaxTodo" href="${ctx}/jy/back/xxsy/show/cancelTop?id=${obj.id}&type=${type}&orgId=${obj.orgId}"  class="btnTop2" callback="schoolShow.reload" mask="true" width="780" height="600"></a>
										</c:otherwise>
									</c:choose>
								</c:if>
							 </td>
						</tr>
					</c:forEach>
				</tbody>
				
			</table>
			<div class="panelBar">
				<div class="pages">
						<span>显示</span>
						<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'schoolShow')">
							<option value="10" ${page.pageSize == 10 ? 'selected':''}>10</option>
							<option value="20" ${page.pageSize == 20 ? 'selected':''}>20</option>
							<option value="50" ${page.pageSize == 50 ? 'selected':''}>50</option>
							<option value="100" ${page.pageSize == 100 ? 'selected':''}>100</option>
						</select>
						<span>条，共${page.totalCount }条</span>
				  </div>
			    <input type="hidden" name="orgId" value="${orgId}" />
				<input type="hidden" name="page.currentPage" value="1" />
			    <input type="hidden" name="page.pageSize" value="${page.pageSize }" />
			    <input type="hidden" name="order" value="" />
			    <input type="hidden" name="flago" value="" />
				<input type="hidden" name="flags" value="" />
				<input type="hidden" name="type" value="${type}" />
				<input type="hidden" name="pageNum" value="1" /><!--当前页-->
				<div class="pagination" rel="schoolShow" totalCount="${page.totalCount }" numPerPage="${page.pageSize }" currentPage="${page.currentPage }"></div>
			</div>
		</form> 
	</div>
	<script type="text/javascript">
      var schoolShow={
    		  //展示类型{校长风采：master,学校概况：overview}
    		  showType:"",
    		  //图片数量限制{校长风采：1,学校概况：10}
    		  imgCount:0,
    		  
    		  changeUploadLimit:function(maxCount){
    			  $('#photoFileInput').uploadify('settings','uploadLimit', maxCount-schoolShow.imgCount);  
    		  },
    		  checkImgCount:function(){
    			  if((this.showType=="master"&&this.imgCount>=1)||(this.showType=="overview"&&this.imgCount>=10)){
	    			  $("#photoFileInput").parent().hide();
    			  }else{
    				  $("#photoFileInput").parent().show();
    			  }
    		  },
    		  uploadphoto:function (file, data, response){
    			  	//schoolShow.imgCount++;
    			  	//alert(schoolShow.imgCount);
    				var data = eval('(' + data + ')');
    				
    				if(data.code!=0){
    				//上传失败
    					alert("文件上传失败,请稍后再试或联系管理员！");
    				}
    				var images=$("#schoolShowImages").val();
    				
    				images+=data.data+",";    				
    				$("#schoolShowImages").val(images);
    				//限制上传图片队列大小
      			 	if(schoolShow.showType=="master"){
      				 	$('#photoFileInput').uploadify('settings','uploadLimit', 1);
      		    	}else if(schoolShow.showType=="overview"){
      		    	 	$('#photoFileInput').uploadify('settings','uploadLimit', 10-schoolShow.imgCount);
      		    	}
    				//绑定删除队列中文件按钮事件（取消上传）
      			 	 $('div[class=cancel]>a:last').on('click',function(e){
      			 		schoolShow.deleteimg(data.data);
      			    });
    			},
    			reload:function (result){
    				try{
	    				dialogAjaxDone(result);
	    				$.pdialog.closeCurrent();
    				}catch(e){
    					//暂不处理~
    				}
    				$("#schoolShow").loadUrl(_WEB_CONTEXT_+"/jy/back/xxsy/show/list?type=${type}&orgId=${orgId}");
    			},
    			decreaseimg:function(args){
    				
    				this.imgCount--;
    				var images=$("#schoolShowImages").val();
    				var images_del=$("#schoolShowImages_del").val();
    				$("#"+args).hide();
    				$("#"+args + "_btn" ).hide();
    				$("#schoolShowImages").val(images.replace(args+',',''));
    				this.checkImgCount();
    				//限制上传图片队列大小
     			 	 if(schoolShow.showType=="master"){
     				 	$('#photoFileInput').uploadify('settings','uploadLimit', 1-schoolShow.imgCount);
     		    	}else if(schoolShow.showType=="overview"){
     		    	 	$('#photoFileInput').uploadify('settings','uploadLimit', 10-schoolShow.imgCount);
     		    	}
     			 	$("#schoolShowImages_del").val(args+','+images_del);
    			},
    			deleteimg:function(args){
    				this.imgCount--;
    				var images=$("#schoolShowImages").val();
    				$("#"+args).hide();
    				$("#"+args + "_btn" ).hide();
    				$("#schoolShowImages").val(images.replace(args+',',''));
    				this.checkImgCount();
    				//限制上传图片队列大小
     			 	 if(schoolShow.showType=="master"){
     				 	$('#photoFileInput').uploadify('settings','uploadLimit', 1-schoolShow.imgCount);
     		    	}else if(schoolShow.showType=="overview"){
     		    	 	$('#photoFileInput').uploadify('settings','uploadLimit', 10-schoolShow.imgCount);
     		    	}
    				//后台删除图片（更改图片状态）
    				$.post(
    						"${ctx}/jy/back/xxsy/show/deleteImg",
    						{imgId:args},
    						null,
    						"json"
    				);
    			},
    			viewSchoolShow:function(result){
    				if(result.juiResult.statusCode==200){
    					if(confirm("已经发布成功，您是否需要去学校首页中查看效果?")){
    						window.open(result.juiResult.message);
    					}
    				}else{
    					alert(result.juiResult.message);
    				}
    				$("#schoolShow").loadUrl(_WEB_CONTEXT_+"/jy/back/xxsy/show/list?type=${type}&orgId=${orgId}");
      			}
      };
		
</script>