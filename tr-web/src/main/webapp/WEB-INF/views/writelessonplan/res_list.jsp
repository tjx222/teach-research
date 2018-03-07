<%@ page language="java" contentType="text/html; charset=utf-8" import="java.util.*"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
		      	<div class="menu_body_tab" style="height:465px;overflow:hidden;width:220px;" id="commend_0">
		      		<% Map<Integer,Integer> tpcount = new HashMap<Integer,Integer>();
		      		   Map<Integer,String> tpnames = new HashMap<Integer,String>();
		      		   tpnames.put(2, "习题");
		      		   tpnames.put(1,"课件");
		      		   tpnames.put(0, "教案");
		      		   tpnames.put(3,"素材");
		      		   pageContext.setAttribute("tpcount", tpcount); 
		      		   pageContext.setAttribute("tpnames", tpnames);%>
		      		<c:forEach var="res" items="${commendResList.datalist }">
		      			<c:set var="tp" value="${res.resType }"></c:set>
					<% tpcount = (Map<Integer,Integer>)pageContext.getAttribute("tpcount");
					   Integer tp = (Integer)pageContext.getAttribute("tp");
					   Integer tcount = tpcount.get(tp);
					   tpcount.put(tp, tcount == null?1:tcount+1);
					%> 
					<div class="menu_body_tab_li">
			      		<dl>
			      			<dd onclick="scanFile('${res.resId}');"><ui:icon ext="${res.ext }" title="${res.title }"></ui:icon><c:if test="${res.qualify>0 }"><span></span></c:if></dd>
			      			<dt><span>${tpnames[res.resType] }${tpcount[res.resType] }</span></dt>
			      		</dl>
			      		<div class="show_p">
				      		<ol>
				      			<ui:isView ext="${res.ext }">
				      				<li class="show_p_1" title="查看" onclick="scanFile('${res.resId}');"></li>
				      			</ui:isView>
								<li><a class="show_p_2" title="下载" href="<ui:download resid="${res.resId }" filename="${res.title }"></ui:download>" target="_blank"></a></li>
							</ol>
						</div>
					</div>
		      		</c:forEach>
		      	</div>
		      	<div style="height:50px;line-height:50px;" class="page"><ol>
		      		<c:if test="${commendResList.currentPage>1 }">
			      		<li class="one_page">
			      			<a style="cursor: pointer;" onclick="getCommendRes('${resType}','${commendResList.currentPage-1 }');">上一页</a>
			      		</li>
		      		</c:if>
		      		<c:if test="${commendResList.currentPage<=1 }">
			      		<li class="on_one_page"><a disabled="disabled">上一页</a></li>
		      		</c:if>
		      		<c:if test="${commendResList.currentPage < commendResList.totalPages}">
			      		<li class="next_page">
			      			<a style="cursor: pointer;" onclick="getCommendRes('${resType}','${commendResList.currentPage+1 }');">下一页</a>
			      		</li>
		      		</c:if>
		      		
		      		<c:if test="${commendResList.currentPage >= commendResList.totalPages}">
		      			<li class="the_next_page"><a disabled="disabled">下一页</a></li>
		      		</c:if>
		      	</ol></div>
