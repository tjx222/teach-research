<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<c:if test="${empty listType || listType==0 }"><ui:htmlHeader title="查阅课件 已查阅"></ui:htmlHeader></c:if>
	<c:if test="${listType==1 }"><ui:htmlHeader title="查阅课件 查阅意见"></ui:htmlHeader></c:if>
	<link rel="stylesheet" href="${ctxStatic }/modules/managerecord/css/check_detail.css" media="screen">
	<script type="text/javascript">
		//切换选项
		function changeListType(listType){
			$("#hid_listType").val(listType);
			$("#hiddenForm").submit();
		}
		//切换学期
		function changTerm(obj){
			$("#hid_term").val($(obj).val());
			$("#hiddenForm").submit();
		}
		//查看课件
		function chakanthesis(resId,userId){
// 			alert("跳转查阅教学文章页面");
			window.open(_WEB_CONTEXT_+"/jy/managerecord/check/10/thesisView?userId="+userId+"&id="+resId,"_blank");
		}
	</script>
</head>
<body>
	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="查阅教学文章"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		<h3>当前位置：<jy:nav id="cyjl_jxwz"></jy:nav></h3>
	</div>
	<div class="clear"></div>
	
	<div class="box" style="display:none;"></div>
	<form id="hiddenForm" action="${ctx }jy/managerecord/check/10" method="post">
		<input id="hid_term" type="hidden" name="term" value="${term }">
		<input id="hid_listType" type="hidden" name="listType" value="${listType }">
	</form>
	<div class="wrap">
		<div class="record_reco">
			<h3>
				<ul id="UL">
					<li ${listType==0?'class="record_act1"':''}${listType==1?'class="record_act"':''} ${listType==1&&checkCount>0?'onclick="changeListType(0)"':''} >
					已查阅（${checkCount }）
					</li>
					<li ${listType==1?'class="record_act1"':''}${listType==0?'class="record_act"':''} ${listType==0&&yijianCount>0?'onclick="changeListType(1)"':''} >
					查阅意见（${yijianCount }）
					</li>
				</ul>
				<label style="float: right;margin-right:10px;margin-top:15px;"> 
				 	学期： 
					<input name="term"  style="vertical-align:middle;margin-top:-3px;" type="radio" value="0"  <c:if test="${term==0}">checked="checked" </c:if> onclick="changTerm(this);"  >上学期
				    <input name="term"  style="vertical-align:middle;margin-top:-3px;" type="radio" value="1"  <c:if test="${term==1}">checked="checked" </c:if> onclick="changTerm(this);"  >下学期
				</label>
			</h3>
			<div class="clear"></div>
			
			<c:if test="${listType==0 }">
				<div class="record_reco_cont" >
					<c:forEach items="${checkInfoList.datalist }" var="checkInfo">
						<div class="record_dl">
							<dl onclick="chakanthesis(${checkInfo.resId },${checkInfo.authorId })">
								<dd><ui:icon ext="ppt"></ui:icon></dd>
								<dt>
									<span title="${checkInfo.title }"><ui:sout value="${checkInfo.title }" length="12" needEllipsis="true"></ui:sout></span>
									<span><fmt:formatDate value="${checkInfo.createtime  }" pattern="yyyy-MM-dd"/></span>
								</dt>
							</dl>
<!-- 							<span class="d"></span> -->
							<strong class="trans"></strong>
						</div>
					</c:forEach>
				</div>
					<form name="pageForm" method="post">
						<ui:page url="${ctx}jy/managerecord/check/10" data="${checkInfoList}"  />
						<input type="hidden" class="currentPage" name="currentPage">
						<input type="hidden" name="term" value="${term }">
						<input type="hidden" name="listType" value="${listType }">
					</form>
			</c:if>
			
			<c:if test="${listType==1 }">
				<div class="record_reco_cont">
				<c:forEach items="${checkMapList }" var="checkMap">
					<div class="record_reco_cont_1">
					
						<div class="check-bottom_1">
							<h5>文章名称：<span onclick="chakanthesis(${checkMap.checkInfo.resId },${checkMap.checkInfo.authorId })" style="cursor: pointer;">${checkMap.checkInfo.title }</span></h5>
							<c:forEach items="${checkMap.optionMapList }" var="optionMap">
								<div class="check-bottom_1_right">
									<div class="check-bottom_1_right_top_1">
										<div class="check-bottom_1_right_top">
											${optionMap.parent.content }
										</div>
										<div class="check-bottom_1_right_botm">
											<span><fmt:formatDate value="${optionMap.parent.crtTime  }" pattern="yyyy-MM-dd"/></span>
										</div>
									</div>
									<div class="clear"></div>
					            	<div style="border-bottom:1px #bdbdbd dashed;;width:1100px;margin:5px auto;"></div>
									<c:forEach items="${optionMap.childList }" var="child">
										<div class="check-bottom_2">
											<jy:di key="${child.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
											<div class="check-bottom_2_left">
												<ui:photo src="${u.photo }" width="60" height="65"></ui:photo>
											</div>
											<div class="check-bottom_2_right">
												<div class="check-bottom_2_right_top1">
													${u.name }说：${child.content }
												</div>
												<div class="check-bottom_2_right_botm">
													<span><fmt:formatDate value="${child.crtTime   }" pattern="yyyy-MM-dd"/></span>
												</div>
											</div>
										</div>
										<div class="clear"></div>
									</c:forEach>
								</div>
							</c:forEach>
						</div>
						<div class="clear"></div>
					</div>
					</c:forEach>
				</div>
					<div class="clear"></div>
					<form name="pageForm" method="post">
						<ui:page url="${ctx}jy/managerecord/check/10" data="${checkInfoList}"  />
						<input type="hidden" class="currentPage" name="currentPage">
						<input type="hidden" name="term" value="${term }">
						<input type="hidden" name="listType" value="${listType }">
					</form>
			</c:if>
			
		</div>
	</div>
		<div class="clear"></div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
</html>