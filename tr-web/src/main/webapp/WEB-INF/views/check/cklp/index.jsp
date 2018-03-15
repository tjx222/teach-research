<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta charset="UTF-8">
<ui:mHtmlHeader title="查阅${type == 0 ? '教案': type == 1? '课件':'反思' }"></ui:mHtmlHeader>
<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css"
	media="screen">
<ui:require module="../m/check/js"></ui:require>
</head>
<body>
	<div class="check_menu_wrap" id="sublist">
		<div class="check_block_menu">
			<span class="check_menu_top"></span>
			<div id="sublistwrap" class="check_menu_wrap1" >
				<div id="scroller">
					<c:if test="${not empty subjects }">
						<c:forEach items="${subjects }" var="s" varStatus="st">
							<p data="${s.id }">${s.name }</p>
						</c:forEach>
					</c:if>
					<c:if test="${empty subjects }">
						<shiro:hasAnyRoles name="xz,fxz,zr">
								<ui:relation var="subjects" type="xdToXk" id="${phaseId }"></ui:relation>
								<c:forEach items="${subjects }" var="s" varStatus="st">
									<p data="${s.id }">${s.name }</p>
								</c:forEach>
						</shiro:hasAnyRoles>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class="check_menu1_wrap" id="gradelist">
		<div class="check_block_menu1">
			<span class="check_menu_top"></span>
			<div id="gradelistwrap" class="check_menu_wrap1">
				<div id="scroller">
				  <c:if test="${not empty grades }">
            <c:forEach items="${grades }" var="g" varStatus="st">
              <p data="${g.id }">${g.name }</p>
            </c:forEach>
          </c:if>
          <c:if test="${empty grades }">
          <shiro:hasAnyRoles name="xz,fxz,zr">
							<ui:relation var="grades" type="xdToNj"
								id="${phaseId }"></ui:relation>
							<c:forEach items="${grades }" var="g" varStatus="st">
								<p data="${g.id }">${g.name }</p>
							</c:forEach>
						</shiro:hasAnyRoles>
				</c:if>
				</div>
			</div>
		</div>
	</div>
	
	 <div class="check_menu_wrap" id="phaselist">
    <div class="check_block_menu2">
      <span class="check_menu_top"></span>
      <div id="phaselistwrap" class="check_menu_wrap2">
        <div id="scroller">
            <c:forEach items="${phases }" var="p">
              <p data="${p.id }">${p.name }</p>
            </c:forEach>
        </div>
      </div>
    </div>
  </div>
	
	<div class="mask"></div>
	<div class="more_wrap_hide" onclick='moreHide()'></div>
	<div id="wrapper">
		<header>
			<span onclick="javascript:window.history.go(-1);"></span>
			<c:if test="${type==0}">查阅教案</c:if>
			<c:if test="${type==1}">查阅课件</c:if>
			<c:if test="${type==2}">查阅反思</c:if>
			<div class="more" onclick="more()"></div>
		</header>
		<section>
			<div class="check_content">
				<div class="check_content_top">
					<h3>筛选：</h3>
					<c:if test="${fn:length(phases) > 1 }">
					<div class="check_content_block">
	            <label>学段</label>
	            <span id="phaseSelect">${phase.name }<strong></strong></span>
	            <div id="selectphase" data="${phase.id }"></div>
	        </div>
					</c:if>
					<c:if test="${fn:length(phases) < 2 }">
					 <div class="check_content_block" style="display:none"> <div id="selectphase" data="${phase.id }"></div></div>
					</c:if>
					<div class="check_content_block">
            <label>年级</label>
                <span id="gradeSelect">${grades[0].name } <strong></strong></span>
                <div id="selectgrade" data="${grades[0].id }"></div>
          </div>
					<div class="check_content_block">
						<label>学科</label>
								<span id="subjectSelect">${subjects[0].name }<strong></strong></span>
								<div id="selectsubject" data="${subjects[0].id }"></div>
					</div>
				</div>
				<div id="userListContent"></div>
			</div>
		</section>
	</div>
</body>
<script type="text/javascript">
  var CURRENT_PHASE ="${phase.id}";
  var TYPE = "${type}";
	require([ "zepto", 'js' ], function($) {
	});
</script>
</html>