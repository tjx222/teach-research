<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
		
<style>
	.uploadify-queue{float:left;width:600px;}
</style>
	<div class="pageContent">
		<form id="schoolShow_form" method="post" action="${ctx }/jy/back/xxsy/show/save"
			class="pageForm required-validate"
			onsubmit="return iframeCallback(this,schoolShow.reload);"  >
			<input type="hidden" name="orgId" value="${orgId}" />
			<div class="pageFormContent" layoutH="58">

				<div style="display:none;">
					<c:if test="${!empty id}">
					      <label>编号：</label>
						  <h3>${id}</h3>
						  <input type="hidden" name="id" value="${id}"/>
				    </c:if>
				</div>
				<div class="unit">
					<label>标题：</label>
					<input type="text" name="title" size="50" maxlength="50"
						   class="required" value="${schoolShow.title}"/>
				</div>
				<div class="unit">
					<label>所属类型：</label>
					<spna ${type=='master' ? 'checked':'style="display:none"'}><input type="radio" name="type" size="40" ${type=='master' ? 'checked':'disabled'}  class="required" value="master"/>校长风采</spna>
				    <spna ${type=='overview' ? 'checked':'style="display:none"'} ><input type="radio" name="type" size="40" ${type=='overview' ? 'checked':'disabled'}  class="required" value="overview"/>学校概况</spna>
				     <spna ${type=='bignews' ? 'checked':'style="display:none"'} ><input type="radio" name="type" size="40" ${type=='bignews' ? 'checked':'disabled'}  class="required" value="bignews"/>学校要闻</spna>
				</div>
				<div class="unit">		   
				    <label>图片：</label>
				    <input type="hidden" name="images" id="schoolShowImages" value="${schoolShow.images}"  class="required" />
				    <input type="hidden" name="flags" id="schoolShowImages_del" value=""/>
						<div class="uploadify-queue">
							<div>	
								<c:if test="${imgs ne null}">
							    	<c:forEach items="${imgs}" var="imgObj">
								    	<ui:photo_del src="${imgObj.value}" function="schoolShow.decreaseimg" args="${imgObj.key}" width="128" height="134" />
								    </c:forEach>
							    </c:if>
						 	</div>
						 	<div>
							   <input id="photoFileInput" type="file" name="file"  class="required"
									uploaderOption="{
										swf:'${ctxStatic }/lib/uploadify/scripts/uploadify.swf',
										uploader:'${ctx }jy/manage/res/upload;jsessionid=<%=session.getId() %>',
										formData:{isWebRes:true,relativePath:'school/o_${orgId}'},
										buttonText:'请选择图片',
										fileSizeLimit:'1000KB',
										fileTypeDesc:'*.jpg;*.jpeg;*.gif;*.png;*.bmp',
										fileTypeExts:'*.jpg;*.jpeg;*.gif;*.png;*.bmp',
										auto:true,
										multi:false,
										<c:if test="${imgCount ne null}">
											uploadLimit:${imgCount},
										</c:if>
										onUploadSuccess:schoolShow.uploadphoto,
										removeCompleted : false			
									}"
								/>
							</div>
						</div>
				</div>
				<%-- <div class="unit">		   
				    <label>概述：</label>
				    <textarea  name="introductionMini" size="40" maxlength="120" value="" cols="42" rows="3">${schoolShow.introductionMini}</textarea>
				</div> --%>
				<div class="unit">		   
				    <label>详细内容：</label>
				    <div  style="float:left;">	
				    	<textarea  class="editor required"  maxlength="5000"  eidtorOption="{width:'100%',height:'300px'}" name="introduction" >${schoolShow.introduction}</textarea>
				    </div>
				</div>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button  type="submit">提交</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">取消</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
<script type="text/javascript">

    $('input[type=radio]').on('click',function(e){
    	schoolShow.showType=$(this).val();
    });
    $(document).ready(function(){
    	schoolShow.showType="${type}";
    	//图片的数量
    	schoolShow.imgCount='${schoolShow.images}'.split(",").length-1;
    	schoolShow.checkImgCount();
    });
</script>