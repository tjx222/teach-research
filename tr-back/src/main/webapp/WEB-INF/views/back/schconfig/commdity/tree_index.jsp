<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="pageContent" style="padding:5px;" >
	<div layoutH="30" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #b8d0d6; line-height:21px; background:#fff">
				    <ul class="tree treeFolder collapse">
				    	<c:forEach items="${list }" var="xd">
				    		<li><a >${xd.name }</a>
								<ul>
									<c:forEach items="${xd.child }" var="xk">
										<li><a >${xk.name }</a>
											<ul>
												<c:forEach items="${xk.child }" var="cbs">
													<li><a >${cbs.name }</a>
														<ul>
															<c:forEach items="${xd.njlist }" var="nj">
																<li><a onclick="saveCbsShortName(this)" saveCbsShortName="${cbs.name }" id="nj_${xd.id }_${xk.id }_${cbs.id}_${nj.id}" href="${ctx }jy/back/schconfig/commdity/jcList?phaseId=${xd.id}&subjectId=${xk.id}&publisherId=${cbs.id}&gradeLevelId=${nj.id}&orgId=${orgId}&areaId=${areaId}" target="ajax" rel="org_jiaocai_box" title="${nj.name }">${nj.name }</a></li>
															</c:forEach>
														</ul>
													</li>
												</c:forEach>
											</ul>
										</li>
									</c:forEach>
								</ul>
							</li>
				    	
				    	</c:forEach>
				     </ul>
				     
				</div>
				<div id="org_jiaocai_box" class="unitBox" style="margin-left:246px;">
					<div class="prompt">
						<p>
							<span>请先在左侧选择-->学段-->学科-->出版社-->年级，然后再查看信息！ </span>
						</p>
					</div>
				</div>
		</div>
</div>
<div class="tabsFooter">
		<div class="tabsFooterContent"></div>
</div>
<input type="hidden" id="saveCbsShortNameId">
<script type="text/javascript">
function reload_Jc_Box(phaseId,subjectId,publisherId,gradeLevelId){
		$("#nj_"+phaseId+"_"+subjectId+"_"+publisherId+"_"+gradeLevelId).click();
}
function saveCbsShortName(o){
	$("#saveCbsShortNameId").val($(o).attr("saveCbsShortName"));
}
function getSaveCbsShortName(){
	return $("#saveCbsShortNameId").val();
}
</script>

	

	