<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="成长档案袋"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
	<ui:require module="record/js"></ui:require>
	<script type="text/javascript">
	var we = '${ctx}';
	var param = '${param}';
	var local = '${param.id}';
	//显示查阅意见box(支持教案、课件、反思，其他反思除外 isUpdate:是否更新有新查阅意见状态)
	function showScanListBox(planType,infoId,isUpdate){
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/uc/isSessionOk.json",
			success:function(data){
				if(data.invalidated){//失效
					$('.login_wrap').show();
					$('.box1').show();
					$('body').css("overflow","hidden");
				}else{//没有失效
					$("#box_scan .dlog-top").css("width","793");
					$("#iframe_scan").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndex?flags=false&resType="+planType+"&resId="+infoId+"&authorId="+${authorId});
					$(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
					$.blockUI({ message: $('#box_scan'),css:{width: '793px',height: '420px',top:'100px',left:'50%',marginLeft:'-397px'}});
					if(isUpdate){//更新为已查看
						$.ajax({  
					        async : false,  
					        cache:true,  
					        type: 'POST',  
					        dataType : "json",  
					        data:{id:infoId},
					        url:  _WEB_CONTEXT_+"/jy/record/updateComment"
					    });
					}
				}
			}
		});
		
	}
	//显示评论意见列表box（支持教案、课件、反思和其他反思）
	function showCommentListBox(planType,planId,bagName,isUpdate){
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/uc/isSessionOk.json",
			success:function(data){
				if(data.invalidated){//失效
					$('.login_wrap').show();
					$('.box1').show();
					$('body').css("overflow","hidden");
				}else{//没有失效
					$("#box_comment .dlog-top").css("width","900");
					$("#iframe_comment").attr("src",_WEB_CONTEXT_+"/jy/comment/list?resType="+planType+"&resId="+planId+"&authorId="+${authorId}+"&title="+encodeURI(bagName));
				 	$(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
					$.blockUI({ message: $('#box_comment'),css:{width: '900px',height: 'auto',top:'100px',left:'50%',marginLeft:'-450px'}});
				 	if(isUpdate){//更新为已查看
				 		$.ajax({  
				 	        async : false,  
				 	        cache:true,  
				 	        type: 'POST',  
				 	        dataType : "json",  
				 	        data:{id:planId},
				 	        url:  _WEB_CONTEXT_+"/jy/record/updatePingLun"
				 	    });
				 	}
				}
			}
		});
		
	 }
	
	</script>
</head>

<%-- <ui:popBoxWrap items="查阅意见" id="box_scan" style="width:900px" iframeId="iframe_scan">
<iframe name="iframe_scan" id="iframe_scan" style="width:900px;height:416px;border:0;"></iframe>
</ui:popBoxWrap>
<ui:popBoxWrap items="评论意见" id="box_comment" style="width:900px" iframeId="iframe_scan">
	<iframe name="iframe_scan" id="iframe_comment" style="width:900px;height:416px;border:0;" ></iframe>
</ui:popBoxWrap> --%>
<body>
<div class="box1"></div>
	<div class="showmsg_box" style="display:none;"></div>
	<div class="wraper">
		<div class="top">
			<jsp:include page="../../common/top.jsp"></jsp:include>
		</div>
		<div class="gro_cont">
		<div class="top_nav">
			<h3>当前位置：
				<jy:nav id="bagmaster">
					<jy:param name="orgID" value="${cm.orgID}"></jy:param>
					<jy:param name="xdid" value="${cm.xdid}"></jy:param>
					<jy:param name="subjectId" value="${rb.subjectId}"></jy:param>
					<jy:param name="gradeId" value="${rb.gradeId}"></jy:param>
					  <jy:param name="teacherId" value="${rb.teacherId}"></jy:param>
					<jy:di key="${rb.teacherId}" className="com.tmser.tr.uc.service.UserService" var="u">
						<jy:param name="bagmaster" value="${u.name}"></jy:param>
					</jy:di>
				</jy:nav>
			</h3>
		</div>
		<div class="clear"></div>
		<div class="Growth_Portfolio">
			<h3>
				<span>
					<c:choose>
				      <c:when test="${rb.subjectId==0}">
				                         学科：<span>无</span>
				      </c:when>
				      <c:otherwise>
					              		学科：<span><jy:dic key="${rb.subjectId}"></jy:dic></span>
				      </c:otherwise>
				   </c:choose>
				</span>
				<span>
					<c:choose>
					      <c:when test="${rb.gradeId==0}">
					                         年级：<span>无</span>
					      </c:when>
					      <c:otherwise>
						              年级：<span><jy:dic key="${rb.gradeId}"></jy:dic></span>
					      </c:otherwise>
				   </c:choose>
			</span>
			<span>
	   				<c:choose>
				      <c:when test="${!empty rb.spaceId}">
				                         版本：<span><span><jy:ds key="${rb.spaceId}" className="com.tmser.tr.uc.service.UserSpaceService" var="us">
								           <jy:ds key="${us.bookId}" className="com.tmser.tr.manage.meta.service.BookService" var="book">
								            	  ${book.formatName }
								              </jy:ds>
					              </jy:ds></span></span>
				      </c:when>
				      <c:otherwise>
					              版本：<span><jy:ds key="${rb.subject}" className="com.tmser.tr.manage.meta.service.BookService" var="book">
					              ${book.formatName }
					              </jy:ds></span>
				      </c:otherwise>
				   </c:choose>
			</span>
			</h3>
			<div class="Growth_Portfolio_cont">
			<c:forEach  items="${recordBagList }" var="bag">
				<div class="Growth_Portfolio_model">
					<div class="Growth_Portfolio_model_bg" data-url="jy/schoolview/res/recordbags/findresByBagID?userID=${authorId}&id=${bag.id}&subjectId=${rb.subjectId}&gradeId=${rb.gradeId}&teacherId=${rb.teacherId}" onclick="opearDom(this)">
						<span title="${bag.desc }"  type="${bag.type}"  id="${bag.id}" class="main">
							${bag.name }
						</span>
					</div>
					<p>
						<ul>
							<c:if  test="${bag.pinglun==0||bag.pinglun==null}"><!-- 评论状态 -->
								<li class="rating"  onclick="showCommentListBox('7','${bag.id}','${bag.name}','true')"></li>
							</c:if>
							<c:if  test="${bag.pinglun==1}"><!-- 评论状态 -->
								<li class="rating_1"  onclick="showCommentListBox('7','${bag.id}','${bag.name}','true')"></li>
							</c:if>
						</ul>
					</p>
				</div>
			</c:forEach>
			<div class="clear"></div>
			</div>
			</div>
		</div>
		<div class="clear"></div>
	<%@include file="../../common/bottom.jsp" %>
<script type="text/javascript">
require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','jp/jquery.form.min','jp/jquery.validationEngine-zh_CN','jp/jquery.validationEngine.min','list'],function(){});
</script>
	</div>
</body>
</html>