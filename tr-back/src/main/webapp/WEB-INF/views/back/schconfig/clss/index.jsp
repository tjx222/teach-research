<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<div class="pageContent" >
	<div class="tabs" style="margin-top: 5px;" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul id="sch_clss_ids">
				<ui:relation var="phases" type="xzToXd" orgId="${requestScope.orgId }" />
				<c:forEach var="phase" items="${phases }" varStatus="stat">
					<li><a href="${ctx}/jy/back/schconfig/clss/list?orgId=${orgId }&phase=${phase.id }" class="j-ajax" rel="sch_clss_phase${phase.id }" id="cls_a_${stat.index }"><span>${phase.name }</span></a></li>
				</c:forEach>
				</ul>
			</div>
		</div>
		<div class="tabsContent" >
		   <c:forEach var="phase" items="${phases }" varStatus="stat">
		   	<div class="sch_clss_content_ph${stat.index }" id="cls_phase_${phase.id }">
			</div>
		   </c:forEach>
		</div>
	</div>
</div>
<script type="text/javascript">
	var initclsGroup = $("div.sch_clss_content_ph0");
	initclsGroup.loadUrl($("#cls_a_0").attr("href"),{},function(){
		initclsGroup.find("[layoutH]").layoutH();
		initclsGroup.attr("loaded",true);
	});
</script>