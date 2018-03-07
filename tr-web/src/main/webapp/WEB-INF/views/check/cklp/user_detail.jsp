<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="查阅${type == 0 ? '教案': type == 1? '课件':'反思' }"></ui:htmlHeader>
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<style>
	.chosen-container-single .chosen-single{
		border:none; 
		background: #f2f1f1;
	}
	.chosen-container.chosen-with-drop .chosen-drop{
		width: 99%;
	}
	</style>
</head>
<body>


<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅${type == 0 ? '教案': type == 1? '课件':'反思' }"></ui:tchTop>
</div>
<div class="jyyl_nav">
	<jy:di key="${userId}" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
	当前位置：
		<jy:nav id="ckkt_list">
			<jy:param name="name"
				value="查阅${type == 0 ? '教案': type == 1? '课件':'反思' }"></jy:param>
			<jy:param name="indexHref"
				value="jy/check/lesson/${type }/index?grade=${grade }&subject=${subject }"></jy:param>
			<jy:param name="listName" value="${u.name }"></jy:param>
		</jy:nav>
</div>
<div class="check_teacher_wrap">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_top check_teacher_top1" style="height:auto;">
			<div class="teacher_news"> 
				<div class="teacher_news_head">
					<div class="teacher_news_head_bg"></div>
					<ui:photo src="${u.photo}" width="63" height="65"></ui:photo>
				</div>
				<div class="name">${u.name}</div>
				<div class="name_right">
					<span>撰写：${writeCount1}篇/${writeCount}课</span>
					<span>提交：<c:if test="${type == 0}">${submitCount1}篇/</c:if>${submitCount}课</span>
					<span>已查阅：<c:if test="${type == 0}">${checkCount1}篇/</c:if>${checkCount}课</span>
				</div>
			</div>
			<div class="semester_sel_wrap semester_sel_wrap1" style="width:224px;">
				<div class="semester_sel" style="margin-right:4px;">
					<select name="searchType" id="searchType"  class="chosen-select-deselect semester" style="width: 110px; height: 25px;">
						<option value="0" ${searchType == 0 ?'selected':'' }>按所属册别</option>
						<option value="1" ${searchType == 1 ?'selected':'' }>按撰写学期</option>
					</select>
				</div>
				<div id="div_1" class="semester_sel" <c:if test="${searchType==0 }">style="display:none;"</c:if>>
					<select name="termId" id="term"  class="chosen-select-deselect semester" style="width: 110px; height: 25px;">
						<option value="0" ${termId == 0 ?'selected':'' }>上学期</option>
						<option value="1" ${termId == 1 ?'selected':'' }>下学期</option>
					</select>
				</div>
				<div id="div_0" class="semester_sel" <c:if test="${searchType==1 }">style="display:none;"</c:if>>
					<select name="fasciculeId" id="fasciculeId"  class="chosen-select-deselect semester" style="width: 110px; height: 25px;">
						<c:if test="${fasciculeId!=178 }">
							<option value="176" ${fasciculeId == 176 ?'selected':'' }>上册</option>
							<option value="177" ${fasciculeId == 177 ?'selected':'' }>下册</option>
						</c:if>
						<c:if test="${fasciculeId==178 }">
							<option value="178" ${fasciculeId == 178 ?'selected':'' }>全册</option>
						</c:if>
					</select>
				</div>
			</div>
			<div class="clear"></div>
			<c:if test="${type==2}">
				<ol class="check_ol">
					<li><a class="check_ol_act" href="javascript:void(0);" onclick="javascript:void(0)"	>课后反思</a></li>
					<li><a href="jy/check/lesson/3/tch/other/${u.id}?termId=${termId }&searchType=1&wc=${writeCount }&wc1=${writeCount1 }&sc=${submitCount }&cc=${checkCount }&grade=${grade}&subject=${subject}">其他反思</a></li>
				</ol>
			</c:if>
		</div> 
		
		<div class="check_teacher_bottom1" style="border-top:none;" >
			<c:choose>
				<c:when test="${!empty resList }">
					<c:forEach items="${resList }" var="res" varStatus="st">
						<div class="doc_dl">
							<dl>
								<dd>
									<a href="jy/check/lesson/${type}/tch/${userId}/view?fasciculeId=${fasciculeId }&lesInfoId=${res.id}
							&zx=${writeCount}&tj=${submitCount}&cy=${checkCount}" target="_blank">
										<ui:icon ext="${type==1?'ppt':'doc'}"></ui:icon>
									</a>
									<c:if test="${not empty checkIds[res.id] }">
										<c:if test="${checkIds[res.id]['isUpdate'] }">
											<span class="spot"></span>
										</c:if>
										<a href="jy/check/lesson/${type}/tch/${userId}/view?fasciculeId=${fasciculeId }&lesInfoId=${res.id}
							&zx=${writeCount}&tj=${submitCount}&cy=${checkCount}" target="_blank">
											<span class="have_access"></span>
										</a>
									</c:if>
								</dd>
								<dt>
									<span title="${res.lessonName }" class="doc_title">
										<a href="jy/check/lesson/${type}/tch/${userId}/view?fasciculeId=${fasciculeId }&lesInfoId=${res.id}
							&zx=${writeCount }&tj=${submitCount}&cy=${checkCount }" target="_blank">
											<ui:sout value="${res.lessonName}" length="16"
													needEllipsis="true" />
										</a>
									</span>
									<span class="doc_date"><fmt:formatDate value="${res.submitTime }"
											pattern="yyyy-MM-dd" /></span>
								</dt>
							</dl>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<!-- 无文件 -->
					<div class="cont_empty">
					    <div class="cont_empty_img"></div>
					    <div class="cont_empty_words">
					    	该教师还未提交“${type == 0 ? '教案': type == 1? '课件':'反思' }”，您可以提醒他提交，或者查阅其他教师的工作 !
					    </div> 
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>
	<script type="text/javascript">
		$(document) .ready( function() {
			//下拉列表
			$(".chosen-select-deselect").chosen({disable_search : true});  
			//$("#check_count").html("已查阅：${checkCount}课");
			$('#searchType').change( function() {
				//$("#div_0").hide();
				//$("#div_1").hide();
				//$("#div_"+$(this).val()).show();
				window.location.href = _WEB_CONTEXT_ + "/jy/check/lesson/${type}/tch/${userId}?grade=${grade}&subject=${subject}&termId="+$("#term").val()+"&fasciculeId=" + $("#fasciculeId").val()+"&searchType="+$(this).val();//页面跳转并传参 
			});
			$("#term").change(function(){
				window.location.href = _WEB_CONTEXT_ + "/jy/check/lesson/${type}/tch/${userId}?fasciculeId=${fasciculeId}&grade=${grade}&subject=${subject}&termId=" + $(this).val()+"&searchType="+$("#searchType").val();//页面跳转并传参 
			});
			$("#fasciculeId").change(function(){
				window.location.href = _WEB_CONTEXT_ + "/jy/check/lesson/${type}/tch/${userId}?grade=${grade}&subject=${subject}&fasciculeId=" + $(this).val()+"&searchType="+$("#searchType").val();//页面跳转并传参 
			});
		});
	</script>
</body>
</html>