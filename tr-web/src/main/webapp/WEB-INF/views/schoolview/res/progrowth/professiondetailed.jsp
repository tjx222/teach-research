<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="专业成长"></ui:htmlHeader>
<script type="text/javascript">
	var restype="${cm.restype}";
	$().ready(function(){
		$('li').each(function(){
			if($(this).attr("restype")==restype){
				$(this).addClass("cont_3_right_profession_pro");//追加标题的CSS
				$(this).text($(this).text()+"(${count})");//改写资源数量
			}
		});
	});
</script>
</head>
<body>
	<div class="return_1"></div>
	<div class="wraper">
		<div class="top">
			<jsp:include page="../../common/top.jsp"></jsp:include>
		</div>
		<div class="pro_cont">
			<div class="top_nav">
				当前位置:
				<jy:nav id="zycz">
					<jy:param name="orgID" value="${cm.orgID}"></jy:param>
					<jy:param name="xdid" value="${cm.xdid}"></jy:param>
				</jy:nav>
			</div>
			<div class="cont_3_right_profession1">
				<div class="cont_3_right_profession_ul">
					<ol>
						<li style="cursor: pointer;"
							data-url="jy/schoolview/res/progrowth/getSpecificProfession?orgID=${cm.orgID}&xdid=${cm.xdid}&restype=1"
							restype="1" onclick="opearDom(this,'_self',true)">计划总结</li>
						<li style="cursor: pointer;"
							data-url="jy/schoolview/res/progrowth/getSpecificProfession?orgID=${cm.orgID}&xdid=${cm.xdid}&restype=2"
							restype="2" onclick="opearDom(this,'_self',true)">教学文章</li>
						<li style="cursor: pointer;"
							data-url="jy/schoolview/res/progrowth/getSpecificProfession?orgID=${cm.orgID}&xdid=${cm.xdid}&restype=3"
							restype="3" onclick="opearDom(this,'_self',true)">听课记录</li>
					</ol>
					<form action="" class="form">
						<label for="">选择要查找的学科：</label> <select name="" id=""
							onchange="professionChangeSubject(this,${cm.orgID},${cm.xdid},${cm.restype});"
							class="chosen-select-deselect" style="width: 100px;">
							<option value="">全部</option>
							<c:forEach items="${subjectsID}" var="sub">
								<option value="${sub}"
									${sub==cm.subject?'selected="selected"':''}><jy:dic
										key="${sub}"></jy:dic></option>
							</c:forEach>
						</select>
					</form>
				</div>

				<div class="profession">
					<c:if test="${empty data.datalist }">
						<div class="resources_empty">
							<dl>
								<dd></dd>
								<dt>暂时没有分享的资源</dt>
							</dl>
						</div>
					</c:if>


					<c:if test="${not empty data.datalist}">
						<c:if test="${cm.restype==1}">
							<c:forEach var="kt" items="${data.datalist }">
								<div class="cont_3_right_cont1_pro11">
									<div class="c_3_r_c_t_c">
										<h5>
											<img
												src="${ctxStatic}/common/icon/base/word.png"
												alt=""><span style="margin-left: 0; color: #51c7f8;"
												onclick="viewprofress(${kt.id},${cm.orgID},${cm.xdid},${cm.restype});">${kt.title}</span>
											<strong> <jy:di key="${kt.userId}"
													className="com.tmser.tr.uc.service.UserService" var="u">
													${u.name}
												</jy:di>
											</strong>
										</h5>
										<h5>
											<b style="margin-left: 0;">
												<c:if test="${not empty kt.label }">【${kt.label }】</c:if>
												<c:if test="${kt.subjectId!=0}">【<jy:dic key="${kt.subjectId}"></jy:dic>】</c:if>
												<c:if test="${kt.gradeId!=0}">【<jy:dic	key="${kt.gradeId}"></jy:dic>${kt.term==0?"上":"下"}】</c:if>
											</b>
											<u>
											<fmt:formatDate	value="${kt.shareTime}" pattern="yyyy-MM-dd" /></u>
										</h5>
									</div>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${cm.restype==2}">
							<c:forEach var="kt" items="${data.datalist }">
								<div class="cont_3_right_cont1_pro11">
									<div class="c_3_r_c_t_c">
										<h5>
											<img
												src="${ctxStatic}/common/icon/base/word.png"
												alt=""><span style="margin-left: 0; color: #51c7f8;"
												onclick="viewprofress(${kt.id},${cm.orgID},${cm.xdid},${cm.restype});">${kt.thesisTitle}</span>
											<strong> <jy:di key="${kt.userId}"
													className="com.tmser.tr.uc.service.UserService" var="u">
													${u.name}
												</jy:di>
											</strong>
										</h5>
										<h5>
											<b>【<jy:dic key="${kt.subjectId}"></jy:dic>】【${kt.thesisType}】
											</b><u><fmt:formatDate value="${kt.shareTime}" pattern="yyyy-MM-dd" /></u>
										</h5>
									</div>
								</div>
							</c:forEach>
						</c:if>
						<c:if test="${cm.restype==3}">
							<c:forEach var="kt" items="${data.datalist }">
								<div class="cont_3_right_cont1_pro11">
									<div class="c_3_r_c_t_c">
										<h5>
											<img
												src="${ctxStatic}/common/icon/base/word.png"
												alt=""><span style="margin-left: 0; color: #51c7f8;"
												onclick="viewprofress(${kt.id},${cm.orgID},${cm.xdid},${cm.restype});">${kt.topic}</span>
											<strong>${kt.lecturePeople}</strong>
										</h5>
										<h5>
											<c:if test="${kt.type==0}">
												<b>【<jy:dic key="${kt.subjectId}"></jy:dic>】【<jy:dic
														key="${kt.gradeId}"></jy:dic>${kt.term==0?"上":"下"}】【${kt.type==0?"校内":"校外"}】</b>
												<u><fmt:formatDate value="${kt.shareTime}" pattern="yyyy-MM-dd" /></u>
											</c:if>
											<!-- 校外听课记录 -->
											<c:if test="${kt.type!=0}">
												<b>【${kt.gradeSubject}】【${kt.type==0?"校内":"校外"}】</b>
												<u><fmt:formatDate value="${kt.shareTime}" pattern="yyyy-MM-dd" /></u>
											</c:if>
										</h5>
									</div>
								</div>
							</c:forEach>
						</c:if>
					</c:if>
				</div>
				<div class="clear"></div>
				<div class="pages">
					<!--设置分页信息 -->
					<form name="pageForm" method="post">
						<ui:page
							url="jy/schoolview/res/progrowth/getSpecificProfession?orgID=${cm.orgID}&xdid=${cm.xdid}&restype=${cm.restype}&subject=${cm.subject}"
							data="${data}" views="5" />
						<input type="hidden" class="currentPage" name="page.currentPage">
					</form>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		<%@include file="../../common/bottom.jsp"%>
	</div>
</body>
<link rel="stylesheet"
	href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript">
		$(document).ready(function(){
			//下拉框
			var config = {
			   '.chosen-select'           : {},
			   '.chosen-select-deselect'  : {allow_single_deselect: true},
			   '.chosen-select-deselect' : {disable_search:true}
			 };
			 for (var selector in config) {
			   $(selector).chosen(config[selector]);
			 }
		})
		</script>
<script type="text/javascript" src="${ctxStatic}/common/js/comm.js"></script>
</html>