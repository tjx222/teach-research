<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="校际教研圈"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/schoolactivity/css/schoolactivity.css" media="screen" />
	<ui:require module="../m/schoolactivity/js"></ui:require>	
</head>
<body>
<div class="partake_school_wrap1">
	<div class="partake_school_wrap">
		<div class="partake_school_title">
			<h3></h3>
			<span class="close"></span>
		</div>
		<div class="partake_school_content">
			<div>
				<ul id="ul3">
				</ul>
			</div>
		</div>
	</div>
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
				    <c:if test="${!empty stcList }">
				        <c:forEach items="${stcList }" var="stc">
					        <div class="courseware_ppt"  data-id="${stc.id}" data-orgId="${stc.orgId}" data-name="${stc.name}">
								<div class="courseware_img_01"></div>
								<h5>
									<span>${stc.name }</span>
									<span><fmt:formatDate value="${stc.crtDttm }" pattern="yyyy-MM-dd"/></span>
								</h5>
								<p><img src="${ctxStatic }/m/schoolactivity/images/word_x.png" /></p>
					       </div> 
				       </c:forEach>
				    </c:if>
				</div>
				 <c:if test="${empty stcList }"><div class="content_k"><dl><dd></dd><dt>您还没有创建校际教研圈哟，赶紧去“创建校际教研圈”吧！</dt></dl></div></c:if>
				<div style="height:2rem;clear:both;"></div>
			</div>
		</div>
	</section>
</div> 
</body>
<script type="text/javascript">
	require(['zepto','circle'],function(){	
		$(function(){ 
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
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<q class="s1">待接受</q></li>';
						   		}else if("${stco.state}"=="2"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<span class="s2">已接受</span></li>';
						   		}else if("${stco.state}"=="3"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<q class="s2">已拒绝</q></li>';
						   		}else if("${stco.state}"=="4"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<a class="s4">已退出</a></li>';
						   		}else if("${stco.state}"=="5"){
						   			orgStr += '<li isone="'+orgId+'" title="'+name+'" id="'+id+'">'+name+'<span class="s2">已恢复</span></li>';
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