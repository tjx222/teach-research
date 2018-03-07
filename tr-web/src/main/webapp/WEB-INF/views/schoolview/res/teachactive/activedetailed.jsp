<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="教研活动"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript">
	var restype="${restype}";
	$().ready(function(){
		$('li').each(function(){
			if($(this).attr("restype")==restype){
				$(this).addClass("preparation_resources_act");//追加标题的CSS
				$(this).text($(this).text()+"(${count})");//改写资源数量
			}
		});
		//下拉框
		var config = {
		   '.chosen-select'           : {},
		   '.chosen-select-deselect'  : {allow_single_deselect: true},
		   '.chosen-select-deselect' : {disable_search:true}
		 };
		 for (var selector in config) {
		   $(selector).chosen(config[selector]);
		 }
	});
</script>
</head>

<body>
<div class="return_1"></div>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../common/top.jsp"></jsp:include>
	</div>
	<div class="dep_cont">
		<div class="top_nav">
			当前位置:<jy:nav id="jyhd">
				<jy:param name="orgID" value="${cm.orgID}"></jy:param>
				<jy:param name="xdid" value="${cm.xdid}"></jy:param>
			</jy:nav>
		</div>
		<div class="cont_3_right_cont1">
			<div class="cont_3_right_cont1_ul">
				<ol>
					<li ${cm.restype==1 ? 'class="cont_3_right_cont1_act"' : '' }  onclick="clickActive(${cm.orgID},${cm.xdid},1);" restype="1">集体备课<c:if test="${cm.restype==1 }">(${count })</c:if></li>
					<li ${cm.restype==2 ? 'class="cont_3_right_cont1_act"' : '' } onclick="clickActive(${cm.orgID},${cm.xdid},2);" restype="2" >校际教研<c:if test="${cm.restype==2 }">(${count })</c:if></li>
				</ol>
				<form action="" class="form">
					<label for="">选择要查找的学科：</label>
					<select name="" id="" class="chosen-select-deselect" onchange="activeChangeSubject(this,${cm.orgID},${cm.xdid},${cm.restype});">
						<option value="0">全部</option>
						<c:forEach items="${subjectsID}" var="sub">
							<option value="${sub}" ${sub==cm.subject?'selected="selected"':''}><jy:dic key="${sub}"></jy:dic></option>
						</c:forEach>
					</select>
				</form>
			</div>
			<div class="actvie">
				<div class="clear"></div>
					<c:if test="${empty data.datalist }" >
						<div style="text-align: center;padding-top: 10px;">暂无资源!</div>
					</c:if>
					<c:if test="${not empty data.datalist}" >
						<c:forEach var="kt" items="${data.datalist }">
							<div class="cont_3_right_cont1_tab_w" >
								<div class="c_3_r_c_t_l">
									<c:if test="${kt.typeId==1}">
										<img src="${ctxStatic}/modules/schoolview/images/school/tb1.png" alt="">
									</c:if>
									<c:if test="${kt.typeId==2}">
										<img src="${ctxStatic}/modules/schoolview/images/school/zt1.png" alt="">
									</c:if>
									<c:if test="${kt.typeId==3}">
										<img src="${ctxStatic}/modules/schoolview/images/school/sp.png" alt="">
									</c:if>
									<c:if test="${kt.typeId==4}">
										<img src="${ctxStatic}/modules/schoolview/images/school/zb.png" alt="">
									</c:if>
								</div>
								<div class="c_3_r_c_t_c">
									<h5><i>【${kt.typeName}】</i><span title='${kt.activityName}' onclick="canyu_chakan('${kt.id}','${kt.typeId}','${kt.isOver}','${cm.orgID}','${cm.xdid}','${cm.restype}');">${kt.activityName}</span><strong>${kt.organizeUserName}</strong></h5>
									<h5 title='${kt.subjectName}'><b>参与学科:${kt.subjectName}</b><u><fmt:formatDate value="${kt.createTime}" pattern="yyyy-MM-dd"/></u></h5>
									<h5 title='${kt.gradeName}'><b>参与年级:${kt.gradeName}</b></h5>
								</div>
							</div>
						</c:forEach>
					</c:if>
					<div class="clear"></div>
					<div class="pages">
						<!--设置分页信息 -->
						<form name="pageForm" method="post">
							<ui:page url="jy/schoolview/res/teachactive/getSpecificAvtive?orgID=${cm.orgID}&xdid=${cm.xdid}&restype=${cm.restype}&subject=${cm.subject}" data="${data}" views="5"/>
							<input type="hidden" class="currentPage" name="currentPage">
						</form>
					</div>
					<div class="clear"></div>
				
			</div>
		</div>
	</div>
	<%@include file="../../common/bottom.jsp" %>
</div>
</body>
</html>
