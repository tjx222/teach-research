<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="学校资源展示页"></ui:htmlHeader>
	<script src="${ctxStatic}/modules/schoolview/js/jquery_imgslider.js"></script>
	<script type="text/javascript">
	 $(document).ready(function(){
		 var t_List=$(".topImg li"),
			 b_List=$(".showbox1 li");
		 var len=t_List.length;
		 var index=1;
		 var orgID='${cm.orgID}';
		 $(".pre").on("click",function(){
				if(index<=1){
					 //进入上一图集
					 var isFirst='${isFirst}';
					 var preid = '${pictureNews.id}';
					 if(isFirst=="true"){
						 alert("已经是第一篇了");
						 return ;
					 }
					 if(confirm("是否进入上一图集?")){
						 if(preid!=null){
						 		location.href=_WEB_CONTEXT_+"/jy/schoolview/show/prenextPictureNews?orgID="+orgID+"&&id="+preid;
						 	}
						}
					return ;
				}else{
					index--;
					move(index,"on");
					var topImg_height = $(".topImg ul li.on").height();
					$(".topImg").height(topImg_height);
				}
			})
		
		$(".next").on("click",function(){ 
		 if(index>=len){
			 //进入下一图集
			 var islast='${isLast}';
			 var id = '${pictureNews.id}';
			 if(islast=="true"){
				 alert("已经是最后一篇了");
				 return ;
			 }
			 if(confirm("是否进入下一图集?")){
				 if(id!=null){
				 		location.href=_WEB_CONTEXT_+"/jy/schoolview/show/nextPictureNews?orgID="+orgID+"&&id="+id;
				 	}
				}
				return ;
			 }else{
				index++;
				move(index,"on");
				var topImg_height = $(".topImg ul li.on").height();
				$(".topImg").height(topImg_height);
			 }
		 
		})
		b_List.on("click",function(){
			index=$(this).attr("data-index");
			move(index,"on");
			var topImg_height = $(".topImg ul li.on").height();
			$(".topImg").height(topImg_height);
		})
		function move(i,cla){
			t_List.eq(i-1).addClass(cla).siblings().removeClass(cla);
			b_List.eq(i-1).addClass(cla).siblings().removeClass(cla);
		}
		 /*新增*/
		$(".pic_dl_").each(function(i,item){
				$(this).attr("data-index", i+1);
		});
		/*默认选中第一个*/
		 function loadfirstSelected(){
			 move(1,"on")
		 }
		 loadfirstSelected();
		$(".data-firstpic_").each(function(i,item){
			$(this).text(i+1);
		}); 
		var topImg_height = $(".topImg ul li.on").height();
		$(".topImg").height(topImg_height);
  })
	</script>
</head>

<body>
<div class="wraper">
	<div class="top">
		<jsp:include page="../../../common/top.jsp"></jsp:include>
	</div>
	<div class="atl_cont">
		<div class="top_nav">
			当前位置:<jy:nav id="tpxw">
						<jy:param name="orgID" value="${cm.orgID}"></jy:param>
						<jy:param name="xdid" value="${cm.xdid}"></jy:param>
					</jy:nav> > 查看
		</div>
		<ol>
			<li class="cont_3_right_cont_act">图片新闻</li>
		</ol>
		<div class="clear"></div>
		<div class="atl_cont_1">
			<h3>${pictureNews.title}</h3>
			<div class="atl_cont_2">
				<div class="topImg">
					<ul>
						<c:if test="${detailList ne null}">
					    	<c:forEach items="${detailList}" var="imgUrl">
							    <li class="on" >
									<div><ui:photo src="${imgUrl.path}"/></div>
									<p><span><strong class="data-firstpic_"></strong>/<strong class="data-countpic">${count}</strong></span>${imgUrl.content}</p>
								</li>
						    </c:forEach>
	    				</c:if>
					</ul>
					<div class="pre"></div>
					<div class="next"></div>
				</div>
				<div class="atl_cont_3">
					<div class="atl_cont_left">
						<c:choose>
					<c:when test="${isFirst!='true'}">
						<dl>
							<dd>
								<a href="jy/schoolview/show/prenextPictureNews?orgID=${cm.orgID}&&id=${pictureNews.id}&&"><ui:photo src="${predata.path}" width="46" height="38"/></a>
							</dd>
							<dt>上一图集</dt>
						</dl>
					</c:when>
					<c:otherwise>
						<dl>
							<dd>
								<img src="${ctxStatic }/modules/schoolview/images/school/no_pic.png"  alt="">
							</dd>
							<dt>无更多了</dt>
						</dl>
					</c:otherwise>
					</c:choose>
					</div>
					<div class="showbox1_wrap"  id="img-slider">
						<div class="pre1 btn-left"></div>
						<div class="showbox1 slider-area">
							<ul>
										<c:if test="${detailList ne null}">
									    	<c:forEach items="${detailList}" var="imgUrl">
											    <li class="pic_dl_" data-index="0">
													<ui:photo src="${imgUrl.path}"/>
												</li>
										    </c:forEach>
	    								</c:if>
							</ul>
						</div>
						<div class="next1 btn-right"></div>
					</div>
					<div class="atl_cont_right">
					<c:choose>
					<c:when test="${isLast!='true'}">
						<dl>
							<dd>
								<a href="jy/schoolview/show/nextPictureNews?orgID=${cm.orgID}&&id=${pictureNews.id}"><ui:photo src="${nextdata.path}" width="46" height="38"/></a>
							</dd>
							<dt>下一图集</dt>
						</dl>
					</c:when>
					<c:otherwise>
						<dl>
							<dd>
								<img src="${ctxStatic }/modules/schoolview/images/school/no_pic.png"  alt="">
							</dd>
							<dt>无更多了</dt>
						</dl>
					</c:otherwise>
					</c:choose>
					
					</div>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<%@include file="../../../common/bottom.jsp" %>
</div>
</body>
</html>