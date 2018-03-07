<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<c:set value="<%=request.getSession().getId() %>" var="sessionId" scope="session"></c:set>
<ui:htmlHeader title="查阅"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/thesis/css/thesis.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/modules/planSummary/css/ps_check.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">  
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
<script type="text/javascript">
var sessionId = "${sessionId}";
	$(document).ready(function() {
		$(window).scroll(function() {
			$("#kongdiv").toggle();
		});
		$('.ser_btn').click(function(){
			var search=$('#searchFcx').val();
			window.open("https://www.baidu.com/s?q1="+search+"&gpc=stf");
		});
	});
</script>
<style type="text/css">
	.chosen-container{margin-top:3px;float:right;margin-right:3px;}
	.chosen-container-single .chosen-single span{ padding-top:0;color:#474747;font-size:14px;}
</style>
</head>
<body>
	<input type="hidden" id="roleId" value="${roleId }" />
	<input type="hidden" id="gradeId" value="${gradeId }" />
	<input type="hidden" id="subjectId" value="${subjectId }" />
	<input type="hidden" id="crtDttm" value="${ps.crtDttm.time}">
	<input type="hidden" id="submitTime" value="${ps.submitTime.time}">
	<input type="hidden" id="authorId" value="${ps.userId}">
	<input type="hidden" id="planSummaryId" value="${ps.id}">
	<input type="hidden" id="category" value="${ps.category}">
	<input type="hidden" id="ids" value="${ids}">
	<div class="clear"></div>
	<div class="check_teacher_wrap" >
		<div class="check_lesson_primary" >
			<!-- 非年级组长才展示学科信息 -->
		<%-- 	<c:if test="${roleId!=1374 }">
				<div class="check_lesson_primary_l ">
					<h3>计划总结</h3>
					<ui:relation var="subjects" type="xdToXk"
						id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
					<c:forEach items="${subjects }" var="s" varStatus="st">
						<h4>
							<span data-subjectId="${s.id }"
								class="${(ps.subjectId == s.id || (empty ps.subjectId && st.index == 0)) ? 'primary_act1' :'' } subject">${s.name }</span>
						</h4>
					</c:forEach>
				</div>
			</c:if> --%>
			<div class="check_lesson_primary_r" style="width:100%;">
				<%-- ${roleId!=1374?'':'style="width:100%;"' } --%>
			 	<%-- <h3>
					<ol>
						<!-- 非学科组长才展示年级信息 -->
						<c:if test="${roleId!=1375 }">
							<ui:relation var="grades" type="xdToNj"
								id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
							<c:forEach items="${grades }" var="g" varStatus="st">
								<li data-gradeId="${g.id }"
									class="grade ${(ps.gradeId == g.id || (empty ps.gradeId && st.index == 0)) ? 'nj_act':'' }">${g.name }</li>
							</c:forEach>
						</c:if>
					</ol>
					<select name="checkStatus" id="checkStatus" class="chosen-select-deselect" style="width:120px;">
						<option value="">全部</option>
						<option value="0">未查阅</option>
						<option value="1">已查阅</option>
					</select> <label for="checkStatus">分类</label> 
				</h3>  --%>
				<div class="check_lesson_wrap_111">
				<%-- 	<ol style="float:left;">
						<li
							class="category ${(ps.category==1||ps.category==3)?'check_lesson_wrap_11_act':'' }"
							data-category="3">
							<c:if test="${roleId==1375}">
								学科计划
							</c:if> <c:if test="${roleId==1374}">
								年级计划
							</c:if> <c:if test="${roleId==1373}">
								备课组计划
							</c:if> 
						</li>
						<li
							class="category ${(ps.category==2||ps.category==4)?'check_lesson_wrap_11_act':'' }"
							data-category="4">
							<c:if test="${roleId==1375}">
								学科总结
							</c:if> <c:if test="${roleId==1374}">
								年级总结
							</c:if> <c:if test="${roleId==1373}">
								备课组总结
							</c:if> 
						</li>
					</ol> --%>
					<div class="ser_in" style="margin-top:25px;">
						<label style="width:61px;padding-top:0;">反抄袭：</label>
						<div class="ser_bor1"> 
							<input type="search" id="searchFcx" class="ser_w"/>
							<input type="button" class="ser_btn"/>
						</div>
					</div>
					<div class="clear"></div>
					<div class="lecture_record_see">
						<div id="content"></div>
						<div class="refer" style="margin-left: 705px;margin-top: -20px;">
						    <c:choose>
						    	<c:when test="${ps.isCheck==0 }"><b></b></c:when>
						    	<c:otherwise><span></span></c:otherwise>
						    </c:choose>
						</div> 
						<div class="see_word">
							<div style="width: 0px; height: 0px; display: none;" id="kongdiv"></div>
							<iframe id="fileView" width="100%" height="700px;" border="0" frameborder="0"scrolling="no" style="margin: 0 auto;display: block;border: none;"> </iframe>
						</div> 
						<!-- <div class="page_wprd">
						<div class="page_wprd_l" id="pre" >
							<span></span>
							上一篇
						</div>
						<div class="page_wprd_r" id="next"> 
							下一篇
							<span></span>
						</div>
					</div> -->
					</div>
					<div class="clear"></div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
		<div class="bg">
			<div class="same_submission" style="margin-top:20px;">
					<h3 class="same_submission_h3"><span></span>他们也提交了同类文章：</h3>
					<iframe id="lessonOthers" onload="setCwinHeight(this,false,100)" style="border:none;width:100%;" frameborder="0"scrolling="no" ></iframe>
				</div>
			<iframe id="checkedBox" onload="setCwinHeight(this,false,100)" style="border: none; width: 100%;" scrolling="no" frameborder="0"></iframe>
		</div>
</div>
<div class="clear"></div>
	<script type="text/x-Hanldebars" id="contentTemplate">
			<h3>
				{{ps.title }}
			</h3>
			<h4>
				<span>作者：{{ps.editName }}</span>
				<span>提交日期：{{submitTime}}</span>
			</h4>
		</script>

	<script type="text/x-Hanldebars" id="emptyTemplate">
			<div class="nofile">
				<div class="nofile1">
					{{errorMsg}}
				</div>
			</div>
		</script>

	<script src="${ctxStatic }/lib/jquery/jquery.blockui.min.js"></script>
	<ui:require module="planSummary/js"></ui:require>
	<script type="text/javascript">
		require([ 'view_multiWorkSpace' ]);
	</script>
	<script type="text/javascript">
		$(document) .ready( function() {
			//获取同时提交这个计划总结的其他人
			$("#lessonOthers").attr("src","jy/planSummary/resourceSubmitOthers?orgId=${ps.orgId}&subjectId=${ps.subjectId}&gradeId=${ps.gradeId}&schoolYear=${ps.schoolYear}&userId=${ps.userId}&category=${ps.category}");
			//下拉列表
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
</body>
</html>
