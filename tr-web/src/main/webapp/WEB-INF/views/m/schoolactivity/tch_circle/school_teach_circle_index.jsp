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
</head>
<body>
<div class="partake_school_wrap1">
	<div class="partake_school_wrap">
		<div class="partake_school_title">
			<h3>教研圈名称:校级教研圈1</h3>
			<span class="close"></span>
		</div>
		<div class="partake_school_content">
			<div>
				<ul id="ul3">
				    <li>北京市海淀区上地第一实验小学<q>已拒绝</q></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="del_upload_wrap del_upload_wrap1">
	<div class="del_upload">
		<div class="del_upload_title">
			<h3>删除校际教研圈</h3>
			<span class="close"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除该校际教研圈吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" id="deleteActivity" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="del_upload_wrap" style="z-index:104;">
	<div class="del_upload">
		<div class="del_upload_title">
			<h3>删除学校</h3>
			<span class="close2"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除已选择学校吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" id="deleteOrg" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel1" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="del_upload_wrap2" style="z-index:104;">
	<div class="del_upload">
		<div class="del_upload_title">
			<h3>退出校际教研圈</h3>
			<span class="close"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width" style="width:21rem;">
				<span>您确定要退出该校际教研圈吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" id="quitSchoolActivity" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="del_upload_wrap3" style="z-index:104;">
	<div class="del_upload">
		<div class="del_upload_title">
			<h3>恢复校际教研圈</h3>
			<span class="close"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width" style="width:21rem;">
				<span>您确定要恢复该校际教研圈吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" id="recoverySchoolActivity" class="btn_confirm" value="确定">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="option_school_wrap">
    <form id="teach_circle" action="${ctx}jy/schoolactivity/circle/save" method="post">
    <ui:token></ui:token>
	<input type="hidden" id="createCircleOrgs" name="circleOrgs">
	<input type="hidden" id="circleId" name="id">
	<div class="option_school">
		<div class="option_school_title">
			<h3>创建校际教研圈</h3>
			<span class="close"></span>
		</div>
		<div class="option_school_content">
			<div class="option-school">
				<div class="option_school1">教研圈名称：</div>
				<input type="text" id="circleName" class="txt" name="name" value="校际教研圈">
				<div class="select_threee">
					<input type="hidden" id="areaIds" name="areaIds" value=",${not empty provinceList ? provinceList[0].id :'' }," />
					<span>省</span> 
					<select id="province" name="province" >
						<c:forEach items="${provinceList }" var="province" >
							<option value="${province.id }">${province.name }</option>
						</c:forEach>
					</select> 
					<span>市</span>
					<select id="city" name="city" >
					</select> 
					<span>区</span>
					<select id="county" name="county" >
					</select> 
				</div>
				<div class="option_school1">选择学校：</div>
				<div class="serch">
					<input type="search" class="search"  id="searchContent" placeholder="输入学校名称进行查找" value="${vo.userName}">
					<input type="button" class="search_btn">
				</div>
			</div>
			<div class="option_school_cont">
				<div class="option_school_cont1" >
					<div>
						<ul id="ul">
						</ul>
					</div>
				</div>
				<div class="option_school_cont2" >
					<dl>
						<dd></dd>
						<dt>请输入学校名称进行查找。</dt>
						<!-- <dt>没有查找到您要找的学校。</dt> -->
					</dl>
				</div>
			</div>
			<div class="o_s_b"></div>
			<div class="option-school_btn">
				<div class="y_option-school">已选择学校：<span>0</span>所</div> 
				<input type="button" id="saveStc" value="完成">
				<input type="button" id="edit" value="修改">
				<input type="button" id="b_edit" value="不改了">
				<div class="yxz_school">
					<div class="yxz_school_title">
						<h3>已选择学校</h3>
						<span class="close1"></span>
					</div> 
					<div class="yxz_school_cont">
						<div class="yxz_school_cont1">
							<div>
								<ul id="ul2">
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</form>
</div>
<div class="mask1"></div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>校际教研圈
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
							<div class="add_cour_div_bottom">创建校际教研圈</div>
						</div>
					</div>
				    <c:if test="${!empty stcList }">
				        <c:forEach items="${stcList }" var="stc" varStatus="status">
					        <div class="courseware_ppt"  data-id="${stc.id}" data-orgId="${stc.orgId}" data-name="${stc.name}">
								<div class="courseware_img_01"></div>
								<h5>
									<span>${stc.name }</span>
									<span><fmt:formatDate value="${stc.crtDttm }" pattern="yyyy-MM-dd"/></span>
								</h5>
								<c:forEach items="${stc.stcoList }" var="stco" varStatus="st">
									<c:if test="${_CURRENT_USER_.orgId == stco.orgId }">
									    <c:if test="${stco.state==4 }"><p><span></span></p></c:if>
										<c:if test="${stco.state!=4 }"><p><img src="${ctxStatic }/m/schoolactivity/images/word_x.png" /></p></c:if>
										<c:if test="${stco.state==4 }"><c:set var="isTuiChu" value="true"></c:set></c:if>
										<c:if test="${stco.state!=4 }"><c:set var="isTuiChu" value="false"></c:set></c:if>
									</c:if>
									<c:if test="${st.index > 0 }">
										<c:if test="${stco.state>=2 &&  stco.state!=3 }"><c:set var="isJSState" value="true"></c:set></c:if>
									</c:if>
								</c:forEach>
								<c:if test="${!isTuiChu }">
									<c:if test="${isJSState }">
									     <c:set var="isJSState" value="false"></c:set>
									     <div class="courseware_img_quit" title="退出"></div>
									</c:if>
								</c:if>
								<c:if test="${isTuiChu }">
								    <div class="courseware_img_recovery" title="恢复"></div>
								</c:if> 
								  
								<c:if test="${_CURRENT_USER_.id==stc.crtId}">
									<c:if test="${isTuiChu }">
									     <div class="courseware_img_jz_edit" title="禁止修改"></div>
										 <div class="courseware_img_jz_del" title="禁止删除"></div>
									</c:if>
									<c:if test="${!isTuiChu }">
									     <div class="courseware_img_edit" title="修改"></div>
									     <c:if test="${stc.isDelete }">
									          <div class="courseware_img_jz_del" title="禁止删除"></div>
									     </c:if>
									     <c:if test="${!stc.isDelete }">
									          <div class="courseware_img_del" title="删除"></div>
									     </c:if>
									</c:if>
								</c:if>  
					       </div> 
				       </c:forEach>
				    </c:if>
				</div>
				<c:if test="${empty stcList }">
				     <div class="content_k"><dl><dd></dd><dt>您还没有创建校际教研圈哟，赶紧去左上角“创建校际教研圈”吧！</dt></dl></div>
				</c:if>
				<div style="height:2rem;clear:both;"></div>
			</div>
		</div>
	</section>
</div> 
</body>
<script type="text/javascript">
	require(['zepto','circle'],function(){	
		$(function(){ 
			//修改教研进度表
			$('.courseware_img_edit').click(function(){
				var circleId=$(this).parent().attr("data-id");
				var circleName=$(this).parent().attr("data-name");
				var orgId=$(this).parent().attr("data-orgId");
				$('.mask').show();
				$('.option_school_wrap').show();
				$('#saveStc').hide();
	    		$('#edit').show();
	    		$('#b_edit').show();
				$('.option_school_title h3').text("修改校际教研圈");
				$('#circleId').val(circleId);
				$('#circleName').val(circleName);
				<c:forEach items="${stcList }" var="stc">
				   var id = "${stc.id }";
				   if(circleId == id){
					  var orgStr = "";
					   <c:forEach items="${stc.stcoList }" var="stco">
					    	if(orgId != "${stco.orgId }"){
						   		var id = "${stco.orgId }@${stco.orgName}@${stco.state}";
						   		var name = "${stco.orgName}";
						   		var orgId = "${stco.orgId}";
						   		if("${stco.state}"=="1"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'</strong>'+'<span></span><q class="s1">待接受</q></li>';
						   		}else if("${stco.state}"=="2"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'<strong>'+'<span></span><a class="s2">已接受</a></li>';
						   		}else if("${stco.state}"=="3"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'<strong>'+'<span></span><a class="s3">已拒绝</a></li>';
						   		}else if("${stco.state}"=="4"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'<strong>'+'<span></span><a class="s4">已退出</a></li>';
						   		}else if("${stco.state}"=="5"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'<strong>'+'<span></span><a class="s2">已恢复</a></li>';
						   		} 
					    	}
					   </c:forEach>
					  $('#ul2').html(orgStr);
				   }
			  </c:forEach> 
			  $('.y_option-school span').text($('#ul2').find('li').length);
			});
			//显示机构信息
	    	$('.courseware_ppt p img').click(function (){
	    		var circleId=$(this).parent().parent().attr("data-id");
	    		var circleName=$(this).parent().parent().attr("data-name");
	    		var orgId=$(this).parent().parent().attr("data-orgId");
	    		$('.partake_school_title h3').text(circleName);
	    		$('.partake_school_wrap1').show();
	    		$('.mask').show();
	    		<c:forEach items="${stcList }" var="stc">
				   var id = "${stc.id }";
				   if(circleId == id){
					  var orgStr = "";
					   <c:forEach items="${stc.stcoList }" var="stco">
					    	if(orgId != "${stco.orgId }"){
						   		var id = "${stco.orgId }@${stco.orgName}@${stco.state}";
						   		var name = "${stco.orgName}";
						   		var orgId = "${stco.orgId}";
						   		if("${stco.state}"=="1"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'</strong>'+'<q class="s1">待接受</q></li>';
						   		}else if("${stco.state}"=="2"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'<strong>'+'<span class="s2">已接受</span></li>';
						   		}else if("${stco.state}"=="3"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'<strong>'+'<q class="s3">已拒绝</q></li>';
						   		}else if("${stco.state}"=="4"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'<strong>'+'<a class="s4">已退出</a></li>';
						   		}else if("${stco.state}"=="5"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+'<strong>'+name+'<strong>'+'<span class="s2">已恢复</span></li>';
						   		}
					    	}
					   </c:forEach>
					  $('#ul3').html(orgStr);
				   }
			  </c:forEach> 
	    	});
		});
	});  
</script>
</html>