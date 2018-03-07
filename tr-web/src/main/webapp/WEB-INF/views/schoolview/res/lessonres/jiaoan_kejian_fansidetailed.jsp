<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="备课资源"></ui:htmlHeader>
<!-- 设置资源类型 -->
<script type="text/javascript">
	var restype="${cm.restype}";
	$().ready(function(){
		$('li').each(function(){
			if($(this).attr("restype")==restype){
				$(this).addClass("cont_3_right_cont_act");//追加标题的CSS
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
		<div class="les_cont">
			<div class="top_nav">
				当前位置:<jy:nav id="beikeziyuan">
							<jy:param name="orgID" value="${cm.orgID}"></jy:param>
							<jy:param name="xdid" value="${cm.xdid}"></jy:param>
						</jy:nav>
			</div>	
		<div class="cont_3_right_cont1">
					<div class="cont_3_right_cont_ul">
						<ol>
							<li style="cursor: pointer;" restype="all" onclick="clickschoolres(${cm.orgID},${cm.xdid},'all');" >配套资源</li>
							<li style="cursor: pointer;" restype="0" onclick="clickschoolres(${cm.orgID},${cm.xdid},'0');">教案</li>
							<li style="cursor: pointer;" restype="1" onclick="clickschoolres(${cm.orgID},${cm.xdid},'1');">课件</li>
							<li style="cursor: pointer;" restype="fansi" onclick="clickschoolres(${cm.orgID},${cm.xdid},'fansi');">反思</li>
						</ol>
						<form action="" class="form">
							<label for="">选择要查找的学科：</label>
							<select name="" id="" class="chosen-select-deselect" onchange="changeSubject(this,${cm.orgID},${cm.xdid},'${cm.restype}');" class="chosen-select-deselect" style="width:100px;">
								<option value="0">全部</option>
								<c:forEach items="${subjectsID}" var="sub">
									<option value="${sub}" ${sub==cm.subject?'selected="selected"':''}><jy:dic key="${sub}"></jy:dic></option>
								</c:forEach>
							</select>
						</form>
					</div>
					<div class="clear"></div>
					<c:if test="${empty data.datalist }" >
						<div style="text-align: center;padding-top: 10px;">暂无资源!</div>
					</c:if>
					<c:if test="${not empty data.datalist}" >
						<c:forEach var="kt" items="${data.datalist }">
							<div class="cont_3_right_cont1_tab_w">
								
								<div class="c_3_r_c_t_c" style="width:962px;">
									<h5>
										<c:choose>
											<c:when test="${kt.planType=='1'}"><!-- 判断资源类型所显示的图片不一样 -->
												<img src="${ctxStatic}/common/icon/base/ppt.png" alt="">
											</c:when>
											<c:otherwise>
												<img src="${ctxStatic}/common/icon/base/word.png" alt="">
											</c:otherwise>
										</c:choose>
										<span><a href="javascript:" onclick="opearDom(this,'_blank')" class="user_login" data-url="jy/schoolview/res/lessonres/view?lesid=${kt.planId}">${kt.planName}</a></span>
										<strong>
											<jy:di key="${kt.userId }" className="com.tmser.tr.uc.service.UserService" var="u">
												${u.name}
											</jy:di>
										</strong>
									</h5>
									<h5><b>【<jy:dic key="${kt.subjectId}"></jy:dic>】【${kt.bookShortname}】【<jy:dic key="${kt.gradeId}"></jy:dic><jy:dic key="${kt.fasciculeId}"></jy:dic>】</b><u><fmt:formatDate value="${kt.shareTime}"  pattern="yyyy-MM-dd"/></u></h5>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<div class="clear"></div>
					<div class="pages">
						<!--设置分页信息 -->
						<form name="pageForm" method="post">
							<ui:page url="jy/schoolview/res/lessonres/getSpecificResjiaoankejianfansi?orgID=${cm.orgID}&xdid=${cm.xdid}&restype=${cm.restype}&subject=${cm.subject}" data="${data}" views="5"/>
							<input type="hidden" class="currentPage" name="page.currentPage">
						</form>
					</div>
					<div class="clear"></div>
<!-- 				</div> -->
			</div>
		</div>
		<%@include file="../../common/bottom.jsp" %>
	</div>
	</body>
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
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