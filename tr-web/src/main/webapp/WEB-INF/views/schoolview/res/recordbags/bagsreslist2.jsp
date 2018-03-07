<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="成长档案袋"></ui:htmlHeader>
<script type="text/javascript">
/**
 * 集体备课查看入口
 */
function chakan(activityId,typeId,isOver,orgID,xdid){//集体备课
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
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/schoolview/res/teachactive/havePowerOfActivity.json",
					data:{"id":activityId},
					success:function(data){
						if(data.havePower){//有参与的权限
							if(typeId==1){//同备教案
								if(isOver==1){//已结束，则查看
									window.open(_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId, "_blank");
								}else{//参与
									window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId, "_blank");
								}
							}else if(typeId==2){//主题研讨
								if(isOver==1){//已结束，则查看
									window.open(_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId, "_blank");
								}else{//参与
									window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId, "_blank");
								}
							}
						}else{//查看校际教研资源
							window.location.href=_WEB_CONTEXT_+"/jy/schoolview/res/viewActivity?id="+activityId+"&orgID="+orgID+"&xdid="+xdid+"";
						}
					}
				});
			}
		}
	});
		
}
</script>	
</head>
	
<body>
<div class="box1"></div>
<div class="wraper">
		<div class="top">
			<jsp:include page="../../common/top.jsp"></jsp:include>
		</div>
	<div class="gro_cont">
		<div class="top_nav">
			当前位置:<jy:nav id="zylx">
						<jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
						<jy:param name="subjectId" value="${cm.subjectId}"></jy:param>
					    <jy:param name="gradeId" value="${cm.gradeId}"></jy:param>
					    <jy:param name="teacherId" value="${cm.teacherId}"></jy:param>
						<jy:di key="${userID}" className="com.tmser.tr.uc.service.UserService" var="u">
							<jy:param name="userId" value="${u.id}"></jy:param>
							<jy:param name="bagmaster" value="${u.name}"></jy:param>
						</jy:di>
						<jy:param name="id" value="${recordbag.id}"></jy:param>
						<jy:param name="zyname" value="${recordbag.name}"></jy:param>
					</jy:nav>
		</div>	
		<div class="preparation_resources">
			<div class="preparation_resources_1">
				<div class="preparation_resources_2">
					<c:if test="${empty data.datalist }" >
						暂无资源!
					</c:if>
					<c:if test="${not empty data.datalist}" >
						<c:forEach var="kt" items="${data.datalist }">
							<div class="cont_3_right_cont1_tab_w">
								<div class="c_3_r_c_t_l">
									<img src="${ctxStatic}/modules/schoolview/images/school/zy.png" alt="">
								</div>
								<div class="c_3_r_c_t_c">
								    <h5>
								    <img src="${ctxStatic}/common/icon/base/word.png" alt="">
									<span><a href="javascript:void(0)"  onclick="chakan('${kt.resId}','${kt.lastupId}','${kt.schoolYear}','${cm.orgID}','${cm.xdid}')">${kt.recordName}</a></span>
									<strong>
											<jy:di key="${userID}" className="com.tmser.tr.uc.service.UserService" var="u">
												${u.name}
											</jy:di>
										</strong>
									</h5>
									<h5><b>参与学科:${kt.flago}</b><b>参与年级:${kt.flags}</b><u><fmt:formatDate value="${kt.lastupDttm}" pattern="yyyy-MM-dd"/></u></h5>
								</div>
							</div>
						</c:forEach>
					</c:if>
					
				</div>
			</div>
		</div>
	</div>
</div>

					<div class="clear"></div>
						<div class="pages">
							<!--设置分页信息 -->
							<form name="pageForm" method="post">
								<ui:page url="jy/schoolview/res/getSpecificAvtive?orgID=${cm.orgID}&xdid=${cm.xdid}&restype=${restype}&subject=${subject}" data="${data}" views="5"/>
								<input type="hidden" class="currentPage" name="page.currentPage">
							</form>
						</div>
		<%@include file="../../common/bottom.jsp" %>
	</body>
	<script type="text/javascript" src="${ctxStatic}/common/js/comm.js"></script>
</html>