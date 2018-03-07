<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<c:if test="${empty listType || listType==0 }"><ui:htmlHeader title="查阅教案 已查阅"></ui:htmlHeader></c:if>
	<c:if test="${listType==1 }"><ui:htmlHeader title="查阅教案 查阅意见"></ui:htmlHeader></c:if>
	<link rel="stylesheet" href="${ctxStatic }/modules/managerecord/css/check_detail.css" media="screen">
	<style type="text/css">
		.check-bottom_2 .check-bottom_2_right .check-bottom_2_right_botm span{
			color: #838383;
			float: right;
			margin-right:0;
		}
	</style>
</head>
<body>
	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="查阅教案"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		<h3>当前位置：<jy:nav id="cyjl_cyja"></jy:nav></h3>
	</div>
	<div class="clear"></div>
	
	<div class="wrap">
		<div class="record_reco">
			<h3>
				<ul id="UL">
					<c:if test='${empty listType || listType==0 }'>
						<li class="record_act1" value="0" ${checkInfoCount>0 ? 'onclick="toCheckInfo(this.value);"':''}>已查阅（${checkInfoCount}）</li>
						<li class="record_act" value="1" ${optionCount>0 ? 'onclick="toCheckInfo(this.value);"':''}>查阅意见（${optionCount }）</li>
					</c:if>
					<c:if test='${listType==1 }'>
						<li class="record_act" value="0" ${checkInfoCount>0 ? 'onclick="toCheckInfo(this.value);"':''}>已查阅（${checkInfoCount}）</li>
						<li class="record_act1" value="1" ${optionCount>0 ? 'onclick="toCheckInfo(this.value);"':''}>查阅意见（${optionCount }）</li>
					</c:if>
				</ul>
				<label style="float: right;margin-right:10px;margin-top:15px;"> 
					<select style="display: none;" >
					</select>
					学期： 
					<input name="term"  style="vertical-align:middle;margin-top:-3px;" type="radio" value="0"  <c:if test="${term==0}">checked="checked" </c:if> onclick="toCheckInfo1(this);"  >上学期
				    <input name="term"  style="vertical-align:middle;margin-top:-3px;" type="radio" value="1"  <c:if test="${term==1}">checked="checked" </c:if> onclick="toCheckInfo1(this);"  >下学期
				</label>
			</h3>
			<div class="clear"></div>
			<c:if test="${empty listType || listType==0 }">
				<div class="record_reco_cont" >
					<c:forEach var="checkInfo" items="${checkInfoPageList.datalist }">
						<div class="record_dl" onclick="viewCheckInfo('${checkInfo.resId}');">
							<dl>
								<dd><ui:icon ext="doc"></ui:icon></dd>
								<dt>
									<span title="${checkInfo.title }"><ui:sout value="${checkInfo.title }" length="12" needEllipsis="true"></ui:sout></span>
									<span><fmt:formatDate value="${checkInfo.createtime }" pattern="yyyy-MM-dd"/></span>
								</dt>
							</dl>
							<strong class="trans"></strong>
						</div>
					</c:forEach>
				</div>
			</c:if>
			<c:if test="${listType==1}">
				<div class="record_reco_cont">
				<c:forEach var="checkMap" items="${checkMapList}">
				<div class="record_reco_cont_1">
					<div class="check-bottom_1">
						<h5>课题名称：<span onclick="viewCheckInfo('${checkMap.checkInfo.resId}');" style="cursor: pointer;">${checkMap.checkInfo.title }</span></h5>
						<c:forEach var="optionMap" items="${checkMap.optionMapList}">
							<div class="check-bottom_1_right">
								<!-- 意见 -->
								<div class="check-bottom_1_right_top_1">
									<div class="check-bottom_1_right_top">
										${optionMap.parent.content }
									</div>
									<div class="check-bottom_1_right_botm">
										<span><fmt:formatDate value="${optionMap.parent.crtTime }" pattern="yyyy-MM-dd"/></span>
									</div>
								</div>
								<div class="clear"></div>
					            <div style="border-bottom:1px #bdbdbd dashed;;width:1100px;margin:5px auto;"></div>
								<!-- 回复 -->
								<c:forEach var="child" items="${optionMap.childList }">
									<div class="clear"></div>
									<div class="check-bottom_2">
										<div class="check-bottom_2_left">
											<jy:di var="tempUser" key="${child.authorId }" className="com.tmser.tr.uc.service.UserService"></jy:di>
											<ui:photo src="${tempUser.photo }" width="60" height="65"></ui:photo>
										</div>
										<div class="check-bottom_2_right">
											<div class="check-bottom_2_right_top1">
												${child.content }
											</div>
											<div class="check-bottom_2_right_botm">
												<span><fmt:formatDate value="${child.crtTime }" pattern="yyyy-MM-dd"/></span>
											</div>
										</div>
									</div>
								</c:forEach>
							</div>
						</c:forEach>
						<div class="clear"></div>
					</div>
				</div>
					<div class="clear"></div>
				</c:forEach>
			</div>
			</c:if>
		</div>
		<form  name="pageForm" method="post">
			<ui:page url="${pageContext.request.contextPath }/jy/managerecord/check/0" data="${checkInfoPageList}"  />
			<input type="hidden" class="currentPage" name="currentPage">
			<input type="hidden" id="listType" name="listType" value="${listType}"> 
			<input type="hidden" id="currentTerm" name="term" value="${term}"> 
		</form>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
function toCheckInfo(listType){
	var currentTerm = $("#currentTerm").val();
	location.href = _WEB_CONTEXT_ + "/jy/managerecord/check/0?listType="+listType+"&term="+currentTerm
}
function toCheckInfo1(obj){
	var listType = $("#listType").val();
	var term = $(obj).val();
	location.href = _WEB_CONTEXT_ + "/jy/managerecord/check/0?listType="+listType+"&term="+term;
}
function viewCheckInfo(planInfoId){
	window.open( _WEB_CONTEXT_ + "/jy/managerecord/check/0/viewLessonPlanCheckInfo?planInfoId="+planInfoId);
}
</script>
</html>