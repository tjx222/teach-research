<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageContent" style="padding:5px;" >
	<div layoutH="30" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #b8d0d6; line-height:21px; background:#fff">
				    <ul class="tree treeFolder collapse">
				    	<c:forEach items="${xklist }" var="xd">
							<li><a href="javascript:;">${xd.name }</a>
								<ul>
									<c:forEach items="${xd.child }" var="xk">
										<li><a id="p_xk_${xd.id }_${xk.id }" href="${ctx }jy/back/schconfig/publisher/list?phaseId=${xd.id}&subjectId=${xk.id}&orgId=${orgId}" target="ajax" rel="publisherCbsBox">${xk.name }</a></li>
									</c:forEach>
								</ul>
							</li>
				    	</c:forEach>
				     </ul>
	</div>
	<div id="publisherCbsBox" class="unitBox" style="margin-left:246px;">
		<div class="prompt">
			<p>
				<span>请先在左侧选择-->学段-->学科，然后再查看信息！ </span>
			</p>
		</div>
	 </div>
</div>
			
<div class="tabsFooter">
	<div class="tabsFooterContent"></div>
</div>
	
<script type="text/javascript">
	function reloadpublisherCbsBox(phaseId,subjectId){
		$("#p_xk_"+phaseId+"_"+subjectId).click();
	}
</script>

	

	