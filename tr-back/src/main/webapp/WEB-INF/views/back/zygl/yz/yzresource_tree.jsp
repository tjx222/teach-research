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
					<li><a href="javascript:;"><span>教材书籍选择</span></a></li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<div>
				<div class="ul_none"  layoutH="50" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #CCC; line-height:21px; background:#fff">
				
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
															<c:forEach items="${cbs.child }" var="nj">
																<li><a >${nj.name }</a>
																	<ul>
																		<c:forEach items="${nj.child }" var="jc">
																			<li><a title="${jc.comName }" id="yzTree_${jc.comId }" href="${ctx }/jy/back/zygl/yz/kt_tree?comId=${jc.comId }" target="ajax" rel="keti_ml_box">${jc.comName }</a>
																			</li>
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
							</li>
				    	
				    	</c:forEach>
				     </ul>
				</div>
				<div class="show"></div>
				<div id="keti_ml_box" class="unitBox" style="margin-left:246px;">
					<div class="prompt">
						<p>
							<span>
								请先在左侧选择-->学段-->学科-->出版社-->年级-->教材-->课题，然后再查相关资源
							</span>
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
		$("#yzTree_"+comId).click();
	};
	
	$('.tree li ul li ul li ul li ul li a').click(function (){
		$('.ul_none').hide();
		$('.show').show();
		$('#keti_ml_box').css("margin-left","25px");
		
	});
	$('.show').click(function (){
		$('.ul_none').show();
		$('.show').hide();
		$('#keti_ml_box').css("margin-left","246px");
	});
</script>

	

	