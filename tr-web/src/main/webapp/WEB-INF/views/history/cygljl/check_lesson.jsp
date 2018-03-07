<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="听课记录信息"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css"media="screen">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<ui:require module="history/js"></ui:require>
	<style>
	.chosen-container-single .chosen-single{
		border:none;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 98.5%;
	}
	</style>
</head>
<body style="background: #fff;"> 
	<div class="calendar_year_center">
	<div class="page_option"> 
		<div class="left_title">
			<c:if test="${searchVo.typeId==0}">查阅教案</c:if>
			<c:if test="${searchVo.typeId==1}">查阅课件</c:if>
		</div>
		<form id="search_form" action="jy/history/${year}/cygljl/check_lesson" method="post">
			<div class="a">
				<select id="sear_term" class="chosen-select-deselect class_teacher" name="termId" style="width: 101px; height: 25px;">
					<option value="" ${empty searchVo.termId? 'selected':'' }>全学年</option>
					<option value="0" ${searchVo.termId==0? 'selected' : '' }>上学期</option>
					<option value="1" ${searchVo.termId==1? 'selected' : '' }>下学期</option>
				</select>
			</div>  
			<div class="serach">
				<input type="text" name="searchStr" class="ser_txt" value="${searchVo.searchStr}"/>
				<input type="button" class="ser_btn"/>
			</div>
			<input type="hidden" class="ja_flago" name="flago" value="${checkInfo.flago}"> 
			<input type="hidden" name="spaceId" value="${searchVo.spaceId}"> 
			<input type="hidden" name="typeId" id="typeId" value="${searchVo.typeId}"> 
			<input type="hidden" name="year" id="year" value="${year}"> 
		</form>
	</div>
	<div class="manage_check_wrap">
		<div class="manage_check_tab">
			<div class="menu_switch">
				<span class="menu_switch_fq <c:if test="${checkInfo.flago==0 || checkInfo.flago==null}">menu_switch_act</c:if>">已查阅(${totalCount})</span>
				<span class="menu_switch_cy <c:if test="${checkInfo.flago==1}">menu_switch_act</c:if>">查阅意见(${optionCount})</span>
			</div>
			<div class="resources_table_wrap">
				<div class="resources_table_wrap_tab down_resource" <c:if test="${checkInfo.flago==1}">style="display:none;"</c:if>>
					<div class="check_lesson_wrap">
						<c:if test="${empty checkInfoList.datalist}">
							<div class="cont_empty">
							    <div class="cont_empty_img"></div>
							    <div class="cont_empty_words">没有资源哟！</div> 
							</div>
						</c:if>
						<c:if test="${not empty checkInfoList.datalist}">
							<c:forEach items="${checkInfoList.datalist}" var="checkInfo">
								<div class="check_lesson">
									<dl>
										<a href="${ctx}/jy/teachingView/view/lesson?type=${checkInfo.resType}&infoId=${checkInfo.resId}&showType=manager" target="_blank">
										<dd><img src="${ctxStatic }/modules/history/images/doc.png" alt=""></dd>
										<dt>
											<span title="${checkInfo.title}">
												${checkInfo.title}
											</span>
											<span><fmt:formatDate value="${checkInfo.createtime}" pattern="yyyy-MM-dd"/></span>
										</dt>
										</a>
									</dl>
									<input type="checkbox" class="checkbox_lesson single_down" />
									<div class="operation">
										<div class="download" title="下载" data-id="${resIdsMap[checkInfo.id]}"></div>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</div>
					<div class="clear"></div>
					<c:if test="${not empty checkInfoList.datalist}">
						<div class="resources_table_bottom">
							<div class="resources_table_check">
								<input type="checkbox" class="all" />
								全选
							</div>
							<input type="button" class="batch_edit" value="批量下载" />
						</div>
					</c:if>
					<div class="">
						<form class="pageForm" name="pageForm" method="post">
							<ui:page url="${ctx}/jy/history/${year}/cygljl/check_lesson" data="${checkInfoList}"/>
							<input type="hidden" class="currentPage" name="currentPage">
							<input type="hidden" name="flago" value="0">
							<input type="hidden" name="spaceId" value="${searchVo.spaceId}"> 
							<input type="hidden" class="ja_searchStr" name="searchStr" value="${searchVo.searchStr}">
							<input type="hidden" class="ja_termId" name="termId" value="${searchVo.termId}"> 
							<input type="hidden" class="" name="typeId" value="${searchVo.typeId}"> 
						</form>
					</div>
				</div>
				<div class="resources_table_wrap_tab" <c:if test="${checkInfo.flago==0 || checkInfo.flago==null}">style="display:none;"</c:if>>   
					<c:if test="${empty checkMapList}">
						<div class="cont_empty">
						    <div class="cont_empty_img"></div>
						    <div class="cont_empty_words">没有资源哟！</div> 
						</div>
					</c:if>
					<c:if test="${not empty checkMapList}">
						<c:forEach items="${checkMapList }" var="checkMap">
							<div class="consult_opinion">
								<div class="consult_opinion_title"><span>
									<a href="${ctx}/jy/teachingView/view/lesson?type=${checkMap.checkInfo.resType}&infoId=${checkMap.checkInfo.resId}&showType=manager" target="_blank">
										${checkMap.checkInfo.title}
									</a>
								 </span></div>
									<c:forEach items="${checkMap.optionMapList }" var="optionMap">
										<div class="consult_opinion_wrap">
											<div class="consult_opinion_cont">
												${optionMap.parent.content }
											</div>
											<div class="consult_opinion_date">
												<fmt:formatDate value="${optionMap.parent.crtTime  }" pattern="yyyy-MM-dd"/>
											</div> 
										</div>
										<div class="border_dashed"></div>
										<c:forEach items="${optionMap.childList }" var="child">
										<div class="consult_opinion_reply">
											<jy:di key="${child.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
											<ui:photo src="${u.photo }" width="60" height="65"></ui:photo>
											<div class="consult_opinion_reply_cont">
											${u.name }说：${child.content }
											</div>
											<div class="consult_opinion_reply_date">
											<fmt:formatDate value="${child.crtTime   }" pattern="yyyy-MM-dd"/>
											</div> 
										</div> 
										</c:forEach>
										<div class="clear"></div>
									</c:forEach>
								</div>
						</c:forEach>
					</c:if>
					<div class="">
						<form class="pageForm" name="pageForm1" method="post">
							<ui:page url="${ctx}/jy/history/${year}/cygljl/check_lesson" data="${checkOptionList}"  />
							<input type="hidden" class="currentPage" name="currentPage">
							<input type="hidden" id="" name="flago" value="1"> 
							<input type="hidden" class="ja_searchStr" name="searchStr" value="${searchVo.searchStr}"> 
							<input type="hidden" name="spaceId" value="${searchVo.spaceId}"> 
							<input type="hidden" class="ja_termId" name="termId" value="${searchVo.termId}"> 
							<input type="hidden" class="" name="typeId" value="${searchVo.typeId}"> 
						</form>
					</div>
				</div>	
				</div>
			</div>
		</div>
	</div>
	
</body>
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
<script type="text/javascript" src="${ctxStatic }/common/js/placeholder.js"></script>

<script type="text/javascript">
require(['jquery','managerCheck'],function(){});
</script>
</html>
