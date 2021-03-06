<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅集备"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/check/css/check.css" media="screen">
	<ui:require module="../m/check/js"></ui:require>
</head> 
<body>
<%-- <input type="hidden" id="term" value="${term }" > --%>
<%-- <input type="hidden" id="subject" value="${subject }" > --%>
<%-- <input type="hidden" id="grade" value="${grade }" > --%>
<div class="check_menu_wrap menuzdy1">
	<div class="check_block_menu" style="margin-left:14rem;">
		<span class="check_menu_top"></span>
		<div id="wrap1" class="check_menu_wrap1" style="height:6.3rem;"> 
			<div id="scroller">
				<c:if test="${not empty term }">
					<c:if test="${term==0 }"><p data="0" class="act" dataname="上学期">上学期</p><p data="1" dataname="下学期">下学期</p> </c:if>
					<c:if test="${term==1 }"><p data="0" dataname="上学期">上学期</p><p data="1" class="act" dataname="下学期">下学期</p> </c:if>
				</c:if>
			  	<c:if test="${empty term }">
			  		<c:if test="${not empty currentterm }">
				  		<c:if test="${currentterm==0 }"><p data="0" class="act" dataname="上学期">上学期</p><p data="1" dataname="下学期">下学期</p> </c:if>
						<c:if test="${currentterm==1 }"><p data="0" dataname="上学期">上学期</p><p data="1" class="act" dataname="下学期">下学期</p> </c:if>
					</c:if>
					<c:if test="${empty currentterm }">
						<p data="0" class="act"  dataname="上学期">上学期</p> 
					  	<p data="1" dataname="下学期">下学期</p> 
					</c:if>
					
				</c:if>
			</div>
		</div>
	</div>
</div>
<div class="check_menu_wrap menuzdy2">
	<div class="check_block_menu">
		<span class="check_menu_top"></span>
		<div id="wrap2" class="check_menu_wrap1"> 
			<div id="scroller">
			<c:choose>
			<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ') }">
				<ui:relation var="subjects" type="xdToXk" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
				<c:if test="${not empty subject }">
					<c:forEach items="${subjects }" var="s" varStatus="st">
						<c:if test="${subject==s.id }"><p data="${s.id }" class="act" dataname="${s.name }">${s.name }</p></c:if>
						<c:if test="${subject!=s.id }"><p data="${s.id }" dataname="${s.name }">${s.name }</p></c:if>
					</c:forEach>
				</c:if>
				<c:if test="${empty subject }">
					<c:forEach items="${subjects }" var="s" varStatus="st">
						<c:if test="${st.index==0 }"><p data="${s.id }" class="act" dataname="${s.name }">${s.name }</p> </c:if>
						<c:if test="${st.index!=0 }"><p data="${s.id }" dataname="${s.name }">${s.name }</p> </c:if>
					</c:forEach>
				</c:if>
			</c:when>
			<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
				<p data="${_CURRENT_SPACE_.subjectId }" class="act" dataname="<jy:dic key='${_CURRENT_SPACE_.subjectId }'/>"><jy:dic key="${_CURRENT_SPACE_.subjectId }"/></p> 
			</c:when>
			<c:otherwise></c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
</div>
<div class="check_menu1_wrap menuzdy3">
	<div class="check_block_menu1">
		<span class="check_menu_top"></span>
		<div id="wrap3" class="check_menu_wrap1"> 
			<div id="scroller">
			<c:choose>
				<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ') }">
					<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
					<c:if test="${not empty grade }">
						<c:forEach items="${grades }" var="g" varStatus="st">
							<c:if test="${grade==g.id }"><p data="${g.id }" class="act" dataname="${g.name }">${g.name }</p></c:if>
							<c:if test="${grade!=g.id }"><p data="${g.id }" dataname="${g.name }">${g.name }</p></c:if>  
						</c:forEach>
					</c:if>
					<c:if test="${empty grade }">
						<c:forEach items="${grades }" var="g" varStatus="st">
							<c:if test="${st.index==0 }"><p data="${g.id }" class="act" dataname="${g.name }">${g.name }</p></c:if>
							<c:if test="${st.index!=0 }"><p data="${g.id }" dataname="${g.name }">${g.name }</p></c:if>   
						</c:forEach>
					</c:if>
				</c:when>
				<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
					  <p data="${_CURRENT_SPACE_.gradeId }" class="act"  dataname="<jy:dic key='${_CURRENT_SPACE_.gradeId }'/>"><jy:dic key="${_CURRENT_SPACE_.gradeId }"/></p> 
				</c:when>
				<c:otherwise></c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>查阅集体备课     
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="check_content">
			<div class="check_content_top">
				<h3>筛选：</h3>
				<div class="check_content_block">
					<label>学科</label>
					<span id="subjectcontent"></span> 
				</div> 
				<div class="check_content_block">
					<label>年级</label>
					<span id="gradecontent" style="width:8.5rem;"></span>
				</div>
				<div class="check_content_block" >
					<label>学期</label>
					<span id="termcontent" style="width:8.5rem;"></span>
				</div>
			</div>
			<iframe id="activitylist"  frameborder="0" scrolling="no" style="border:none;width: 100%;height:90%"  ></iframe>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'activity'],function($){	
	}); 
</script>
</html>