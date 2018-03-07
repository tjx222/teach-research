<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
	ul.rightTools {float:right; display:block;}
	ul.rightTools li{float:left; display:block; margin-left:5px}
</style>
<div class="pageContent" style="padding:5px" >
				<div layoutH="30" style="float:left; display:block; overflow:auto; width:240px; border:solid 1px #b8d0d6; line-height:21px; background:#fff">
				    <ul class="tree treeFolder">
						<li><a href="javascript">选择学段</a>
							<ul>
								<c:forEach items="${XDlist }" var="xd">
									<li><a id="xueduan_nj_${xd.id }" name="xud_nj" href="${ctx }/jy/back/jxtx/queryGrade?eid=${xd.id }" target="ajax" rel="njglBox">${xd.name }</a></li>
								</c:forEach>
							</ul>
						</li>
						
				     </ul>
				</div>
				
				<div  id="njglBox" class="unitBox" style="margin-left:246px;" >
					
				</div>
				<div class="prompt">
					<p>
						<span>
							请先选择学段，然后再查看信息
						</span>
					</p>
				</div>
			</div>
			
		<div class="tabsFooter">
			<div class="tabsFooterContent"></div>
		</div>
<script type="text/javascript">
	function reloadNjglBox(eid){
		$("#xueduan_nj_"+eid).click();
// 		$("#njglBox").loadUrl(_WEB_CONTEXT_+"/jy/back/jxtx/queryGrade?eid="+eid,null,null );
	}
	$(document).ready(function(){
		  $("a[name='xud_nj']").click(function(){
			  $("div[class='prompt']").hide();
		  });
	});
</script>

	

	