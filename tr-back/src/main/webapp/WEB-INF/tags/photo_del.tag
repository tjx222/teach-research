<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="src" type="java.lang.String" required="true" description="要显示的图像地址" %>
<%@ attribute name="style" type="java.lang.String" required="false" description="图像属性" %>
<%@ attribute name="width" type="java.lang.String" required="false" description="图像宽" %>
<%@ attribute name="height" type="java.lang.String" required="false" description="图像高" %>
<%@ attribute name="alt" type="java.lang.String" required="false" description="图像alt" %>
<%@ attribute name="function" type="java.lang.String" required="false" description="单击响应的方法" %>
<%@ attribute name="args" type="java.lang.String" required="false" description="单击传递的参数" %>
<c:if test="${empty src }">
	<c:set var="src" value="static/common/images/default.jpg"/>
</c:if>
<div style="position: relative;float: left;margin-right:20px;"><!-- onmouseover="$('.img_editblock').show();"	onmouseout="$('.img_editblock').hide();" -->
	<img  id="${args}" src ="${ctx }/jy/manage/res/download/path?path=${src }&isweb=true" height="${height }" width="${width }" alt="${alt }" style="cursor:pointer;" />

	<div class="img_editblock" id="${args}_btn"  onclick="${function}('${args}')" > 
	</div>
</div>