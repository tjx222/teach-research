<%@tag pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ attribute name="src" type="java.lang.String" required="true" description="要显示的图像地址" %>
<%@ attribute name="style" type="java.lang.String" required="false" description="图像属性" %>
<%@ attribute name="width" type="java.lang.String" required="false" description="图像宽" %>
<%@ attribute name="height" type="java.lang.String" required="false" description="图像高" %>
<%@ attribute name="alt" type="java.lang.String" required="false" description="图像alt" %>
<c:if test="${empty src }">
	<img src ="${ctx }/static/common/images/default.jpg" height="${height }" width="${width }" alt="${alt }"/>
</c:if>
<c:if test="${not empty src }">
<img src ="${ctx }/jy/manage/res/download/path?path=${src }&isweb=true" height="${height }" width="${width }" alt="${alt }"/>
</c:if>