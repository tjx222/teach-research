<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="校际教研"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/schoolactivity/css/schoolactivity.css" media="screen" />
	<ui:require module="../m/schoolactivity/js"></ui:require>	
	<style>
		#fileToUpload_1 {
		    margin-left: -10.5rem;
		}
	</style>
</head>
<body>
<div class="partake_school_wrap1" style="z-index:104;">
	<div class="partake_school_wrap">
		<div class="partake_school_title">
			<h3>教研圈名称:校级教研圈1</h3>
			<span class="close1"></span>
		</div>
		<div class="partake_school_content">
			<div>
				<ul id="ul3">
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="add_upload_wrap">
	<div class="add_upload_wrap1"></div>
	<div class="add_upload">
		<div class="add_upload_title">
			<h3>上传教研进度表</h3>
			<span class="close"></span>
		</div>
		<div class="add_upload_content">
		   <!--  <form id="index" action="jy/teachschedule/index" method="post"></form> -->
			<form id="jy_from" action="jy/teachschedule/save" method="post"> 
			    <ui:token></ui:token>
		     	<input type="hidden" id="id" name="id" value=""/>
			    <input type="hidden" id="schoolTeachCircleId" name="schoolTeachCircleId" value=""/>
			    <input type="hidden" id="subjectId" name="subjectId" value=""/>
			    <input type="hidden" id="gradeId" name="gradeId" value=""/>
			    <input type="hidden" name="resId" id="resId" value="">
			    <input type="hidden" name="originFileName" id="originFileName" value="">
				<div class="form_input">
					<label>教研圈</label>
					<strong class="select1" id="uploadLesson">请选择<q></q></strong>
					<div class="menu_list1" >
						<span class="menu_list_top"></span>
							<div class="menu_list_wrap1">
								<div> 
									<c:forEach var="s" items="${stlist}">
										<p data-value="${s.id}">${s.name}</p>
								    </c:forEach>
								</div>
							</div>
					</div> 
					<div class="viewOrg" style="display:none;" id="viewOrg">查看</div>
				</div>
				<div class="form_input">
					<label>学科</label>
					<strong class="select2" id="uploadLesson">请选择<q></q></strong>
					<div class="menu_list2" >
						<span class="menu_list_top"></span>
						<div class="menu_list_wrap2">
							<div> 
							    <ui:relation var="list" type="xdToXk" id="${_CURRENT_SPACE_.phaseId}">
								<c:forEach var="xk" items="${list}">
									<p data-value="${xk.id}">${xk.name}</p>
								</c:forEach>
							    </ui:relation>
							</div>
						</div>
					</div>
				</div>
				<div class="form_input">
					<label>年级</label>
					<strong class="select3" id="uploadLesson">请选择<q></q></strong>
					<div class="menu_list3" >
						<span class="menu_list_top"></span>
						<div class="menu_list_wrap3">
							<div> 
							    <ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
								<c:forEach items="${grades }" var="g" varStatus="st">
									<p data-value="${g.id}">${g.name }</p>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="form_input">
					<label>标题</label>
					<strong>
						<input type="text" id="qt_planName" name="name" maxlength="30">
						</strong>
				</div>
				<div id="fileuploadContainer" class="form_input">
					<label style="background-color: #fff; position: absolute;left: -3px;z-index: 1;width: 6.7rem;text-align: center; padding-left: 1.8rem;">上传附件</label>
					<div class="enclosure_name" style="display:none;">
					     <q></q>
					     <span id="uploadFileName">最大长度为十五个字.doc</span>
				         <div class="enclosure_del"></div>
				    </div>
					<strong id="uploadId" style="margin-left:8.3rem;"> 
						<ui:upload_m fileType="doc,docx,ppt,pptx,pdf" fileSize="50" startElementId="save" beforeupload="start" callback="afterUpload" relativePath="teachschedule/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }"></ui:upload_m>
					</strong>
				</div>
				<div> 
					<input id="save" type="button" class="btn_upload" value="上传"> 
					<!-- <input type="button" id="save" class="edit1" value="修改"> -->
				    <input type="button" id="b_edit1" value="不改了">
				</div>
				<div class="btn_sc"style="margin:3rem auto;display: none;" >
					<div class="spinner ">
					  <div class="rect1"></div>
					  <div class="rect2"></div>
					  <div class="rect3"></div>
					  <div class="rect4"></div>
					  <div class="rect5"></div>
					</div>
					<span>上传中...</span>
				</div>
			</form> 
		</div>
	</div>
</div>
<div class="del_upload_wrap">
	<div class="del_upload">
		<div class="del_upload_title">
			<h3>删除教研进度表</h3>
			<span class="close"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除该教研进度表吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="release_upload_wrap">
	<div class="release_upload">
		<div class="release_upload_title">
			<h3>发布教研进度表</h3>
			<span class="close"></span>
		</div>
		<div class="release_upload_content">
			<div class="release_width">
				<q></q>
				<span>您确定要发布该教研进度表吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm_rel" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="mask"></div>
<div class="mask1"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>教研进度表
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="content_bottom2">
			<div>
				<div class="content_bottom_width">
					<div class="add_cour">
						<div class="add_cour_div">
							<div class="add_cour_div_top">
								<div class="add_cour_div_top_img"></div> 
							</div>
							<div class="add_cour_div_bottom">上传教研进度表</div>
						</div>
					</div>
					<c:if test="${!empty tSchedules}">
				    	<c:forEach items="${tSchedules}" var="data">
							<div class="courseware_ppt" >
								<div class="courseware_img_0"></div>
								<h4><jy:dic key="${data.gradeId}"></jy:dic><jy:dic key="${data.subjectId}"></jy:dic></h4>
								<h4 title="${data.name}"><ui:sout value="${data.name}" length="18" needEllipsis="true"></ui:sout></h4>
								<p data-id="${data.id}">
								<img src="${ctxStatic }/m/activity/images/word_d.png" />
								</p>
								<div class="courseware_img_2" title="操作"></div>
								<div class="cw_option_mask" style="display:none;"></div>
								<jy:di var="st" key="${data.schoolTeachCircleId}" className="com.tmser.tr.activity.service.SchoolTeachCircleService"/>
								<div class="cw_option" style="display:none;" data-resId="${data.resId}" data-id="${data.id}" data-name="${data.name}" data-gradeName="<jy:dic key='${data.gradeId}'/>" data-subjectName="<jy:dic key='${data.subjectId}'/>"
								 data-circleName="${st.name}" data-circleId="${data.schoolTeachCircleId}" data-subjectId="${data.subjectId}" data-gradeId="${data.gradeId}">
									<c:if test="${!data.isRelease}"><div class="cw_option_edit" title="编辑"></div></c:if>
									<c:if test="${data.isRelease}"><div class="cw_option_jz_edit" title="禁止编辑"></div></c:if>
									<div class="cw_option_del" title="删除"></div>
									<c:if test="${!data.isRelease}"><div class="cw_option_publish" data-isRelease="true" title="发布"></div></c:if> 
									<c:if test="${data.isRelease}"><div class="cw_option_qx_publish" data-isRelease="false" title="取消发布"></div></c:if> 
									<a href="<ui:download filename="${data.name}" resid="${data.resId}"></ui:download>"><div class="cw_option_down" title="下载"></div></a>
									<div class="cw_option_close" ></div>
								</div>
							</div> 
						</c:forEach>
					</c:if>
				</div>
				<c:if test="${empty tSchedules}"><div class="content_k" style="margin:3rem auto;"><dl><dd></dd><dt>您还没有上传教研进度表哟，赶紧去左上角“上传教研进度表”吧！</dt></dl></div></c:if>
				<div style="height:2rem;clear:both;"></div>
			</div>
		</div> 
	</section>
</div>
</body>
<script type="text/javascript">
	require(['zepto','teach'],function(){
        $(function(){
        	//显示机构信息
	    	$('.viewOrg').click(function(){
	    		var circleId=$(this).attr("data-circleId");
	    		var circleName=$(this).attr("data-name");
	    		$('.partake_school_title h3').text(circleName);
	    		$('.partake_school_wrap1').show();
	    		$('.mask1').show();
	    		<c:forEach items="${stlist }" var="stc">
				   var id = "${stc.id }";
				   if(circleId == id){
					  var orgStr = "";
					   <c:forEach items="${stc.stcoList }" var="stco">
					    	if(orgId != "${stco.orgId }"){
						   		var id = "${stco.orgId }@${stco.orgName}@${stco.state}";
						   		var name = "${stco.orgName}";
						   		var orgId = "${stco.orgId}";
						   		if("${stco.state}"=="1"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<q>待接受</q></li>';
						   		}else if("${stco.state}"=="2"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<span>已接受</span></li>';
						   		}else if("${stco.state}"=="3"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<q>已拒绝</q></li>';
						   		}else if("${stco.state}"=="4"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<a>已退出</a></li>';
						   		}else if("${stco.state}"=="5"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<span>已恢复</span></li>';
						   		}
					    	}
					   </c:forEach>
					  $('#ul3').html(orgStr);
				   }
			  </c:forEach> 
	    	});
        })
	});  
</script>
</html>