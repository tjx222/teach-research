<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
	.this_content_ml{width:400px;height:200px; float: left;border:dashed 1px #ddd;}
	.this_content_ml p{width: 100%; height: 35px;line-height: 35px;}
	.this_content_ml p label{width:100px; height: 35px;line-height: 35px;text-align: right;margin-left: 10px;display: block;float: left;margin-right: 10px;}
</style>
<div layoutH="50" style="float:left; display:block; overflow:auto; width:100%; border:solid 1px #CCC; line-height:21px; background:#fff;overflow: scroll;">
	<div layoutH="52"  style="float:left; display:block; overflow:auto; width:240px;border:dashed 1px #ddd;">
		<div style="padding: 15px 0px 2px 5px;font-weight: bolder;">教材课题树形结构</div>			
	    <ul class="tree treeFolder collapse" >
	    	<c:forEach items="${list }" var="one">
	    		<c:if test="${empty one.child }"><li><a title="${one.name }" chaptername="${one.name }" chapterLevel="${one.chapterLevel }"  parentid="${one.parentId }" ordernum="${one.orderNum }" target="ajax"  rel="resourcesByLid_zy" href="${ctx}jy/back/zygl/yz/show_zylist?lessonId=${one.id}" >${one.name }</a></li></c:if>
	    		<c:if test="${not empty one.child }">
		    		<li><a title="${one.name }" chaptername="${one.name }" chapterLevel="${one.chapterLevel }" parentid="${one.parentId }" ordernum="${one.orderNum }" onclick="setValue('${one.id }',1,0,this)" >${one.name }</a>
						<ul>
							<c:forEach items="${one.child }" var="two">
								<c:if test="${empty two.child }"><li><a title="${two.name }" chaptername="${two.name }" chapterLevel="${two.chapterLevel }" parentid="${two.parentId }" ordernum="${two.orderNum }" target="ajax"  rel="resourcesByLid_zy" href="${ctx}jy/back/zygl/yz/show_zylist?lessonId=${two.id}" >${two.name }</a></li></c:if>
								<c:if test="${not empty two.child }">
									<li><a title="${two.name }" chaptername="${two.name }" chapterLevel="${two.chapterLevel }" parentid="${two.parentId }" ordernum="${two.orderNum }" onclick="setValue('${two.id }',2,0,this)" >${two.name }</a>
										<ul>
											<c:forEach items="${two.child }" var="three">
												<c:if test="${empty three.child }"><li><a title="${three.name }" chaptername="${three.name }" chapterLevel="${three.chapterLevel }" parentid="${three.parentId }" ordernum="${three.orderNum }" target="ajax"  rel="resourcesByLid_zy"  href="${ctx}jy/back/zygl/yz/show_zylist?lessonId=${three.id}" >${three.name }</a></li></c:if>
												<c:if test="${not empty three.child }">
													<li><a title="${three.name }" chaptername="${three.name }" chapterLevel="${three.chapterLevel }" parentid="${three.parentId }" ordernum="${three.orderNum }" onclick="setValue('${three.id }',3,0,this)" >${three.name }</a>
														<ul>
															<c:forEach items="${three.child }" var="four">
																<li><a title="${four.name }" chaptername="${four.name }" chapterLevel="${four.chapterLevel }" parentid="${four.parentId }" ordernum="${four.orderNum }" target="ajax"  rel="resourcesByLid_zy"  href="${ctx}jy/back/zygl/yz/show_zylist?lessonId=${four.id}" onclick="setValue('${four.id }',4,1,this)" >${four.name }</a></li>
															</c:forEach> 
														</ul>
													</li>
												</c:if>
											</c:forEach>
										</ul>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</li>
	    		</c:if>
	    	</c:forEach>
	     </ul>
	</div>
	<div id="resourcesByLid_zy" style="margin-left: 255px">
					<div class="prompt" style="margin-left: 100px">
						<p>
							<span>
								请先在左侧选择-->课题，然后再查相关资源
							</span>
						</p>
					</div>
	</div>
     
</div>

<script type="text/javascript">
	//选中操作
	function setValue(id,level,candel,obj){
		//admin_user_id=1
		levelnum = level;
		parentId = $(obj).attr("parentid");
		orderNum = $(obj).attr("ordernum");
		chapterName = $(obj).attr("chaptername");
		chapterLevel = $(obj).attr("chapterLevel");
	}
	
</script>
