<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>

<div class="pageContent" style="padding:5px">
	<div class="tabs">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li><a href="javascript:;"><span>教材目录管理</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
				<div class="ul_none" layoutH="50" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				
				    <ul class="tree treeFolder collapse">
				    	<c:forEach items="${list }" var="xd">
				    		<li><a >${xd.name }</a>
					    		<c:if test="${not empty xd.child }">
									<ul>
										<c:forEach items="${xd.child }" var="xk">
											<li><a >${xk.name }</a>
												<c:if test="${not empty xk.child }">
													<ul>
														<c:forEach items="${xk.child }" var="cbs">
															<li><a >${cbs.name }</a>
																<c:if test="${not empty cbs.child }">
																	<ul>
																		<c:forEach items="${cbs.child }" var="nj">
																			<li><a >${nj.name }</a>
																				<c:if test="${not empty nj.child }">
																					<ul>
																						<c:forEach items="${nj.child }" var="jc">
																							<li><a title="${jc.comName }" id="jcTree_${jc.comId }" href="${ctx }jy/back/schconfig/catalog/catalog_tree?comId=${jc.comId }&publisherId=${jc.publisherId}&orgId=${jc.orgId}&areaId=${jc.areaId}" target="ajax" rel="jiaocai_ml_org_box">${jc.comName }</a></li>
																						</c:forEach>
																					</ul>
																				
																				</c:if>
																			</li>
																		</c:forEach>
																	</ul>
																
																</c:if>
															</li>
														</c:forEach>
													</ul>
												
												</c:if>
											</li>
										</c:forEach>
									</ul>
					    		</c:if>
							</li>
				    	
				    	</c:forEach>
				     </ul> 
				</div>
				<div class="show"></div>
				<div id="jiaocai_ml_org_box" class="unitBox" style="margin-left:246px;overflow:hidden;">
					<div class="prompt">
						<p>
							<span>请先在左侧选择-->学段-->学科-->出版社-->年级-->教材，然后再查看信息! </span>
						</p>
					</div>
				</div>
	
			</div>
			
		</div>
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
	</div>
	
</div>
<script type="text/javascript">
	function reload_catalog_tree(comId){
		$("#jcTree_"+comId).click();
	};
	$('.tree li ul li ul li ul li ul li a').click(function (){
		$('.ul_none').hide();
		$('.show').show();
		$('#jiaocai_ml_org_box').css("margin-left","25px");
		
	});
	$('.show').click(function (){
		$('.ul_none').show();
		$('.show').hide();
		$('#jiaocai_ml_org_box').css("margin-left","246px");
	});
</script>