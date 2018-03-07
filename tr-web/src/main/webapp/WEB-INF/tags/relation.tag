<%@tag pageEncoding="UTF-8"%>
<%--  
		元数据显示标签。type: 
		xdToXk -- 根据学段关联学科 
		xzToXd -- 查询学制关联的学段
		xdToNj -- 学段关联的年级 
					   
--%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ tag import="com.tmser.tr.utils.SpringContextHolder" %>
<%@ tag import="com.tmser.tr.manage.meta.MetaUtils" %>
<%@ tag import="com.tmser.tr.uc.bo.UserSpace" %>
<%@ tag import="java.util.*" %>
<%@ tag import="com.tmser.tr.manage.org.service.OrganizationService" %>
<%@ tag import="com.tmser.tr.utils.StringUtils" %>
<%@ tag import="com.tmser.tr.manage.org.bo.Organization" %>
<%@ tag import="com.tmser.tr.uc.bo.UserSpace" %>
<%@ tag import="com.tmser.tr.uc.utils.SessionKey" %>
<%@ tag import="com.tmser.tr.common.utils.WebThreadLocalUtils" %>
<%@ tag import="com.tmser.tr.manage.meta.MetaUtils,com.tmser.tr.manage.meta.bo.MetaRelationship" %>
<%@ attribute name="id" type="java.lang.Integer" required="false" description="要显示元数据id" %>
<%@ attribute name="type" type="java.lang.String" required="true" description="显示数据类型" %>
<%@ attribute name="var" type="java.lang.String" required="true" description="设置到变量名称" %>
<%!
   private OrganizationService organizationService;
%>
<%
    if(organizationService == null)
    	organizationService = SpringContextHolder.getBean(OrganizationService.class);
		UserSpace us = ((UserSpace) session.getAttribute("_CURRENT_SPACE_"));
%>
<c:choose>
	 <c:when test="${not empty type && type == 'xdToNj'}">
	 	<%
	 	if(id == null)
	 		id = us.getPhaseId();
	 	Organization org = organizationService.findOne(us.getOrgId());
	 	
	 	
	 	
	 	request.setAttribute(var,MetaUtils.getOrgTypeMetaProvider().listAllGrade(org.getSchoolings(), id));
	 	%>
	 </c:when>
	 <c:when test="${not empty type && type == 'xdToXk'}">
	 	<%
	 	if(id == null)
	 	id = us.getPhaseId();
	 	Organization org = organizationService.findOne(us.getOrgId());
	 	Integer[] areaIds = StringUtils.toIntegerArray(org.getAreaIds().substring(1, org.getAreaIds().lastIndexOf(",")), ",");
	 	request.setAttribute(var,MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(org.getId(),id,areaIds));
	 	%>
	 </c:when>
	  <c:when test="${not empty type && type == 'xzToXd'}">
	 	<%
	 	List<MetaRelationship> phase = new ArrayList<MetaRelationship>();
	 	Organization org = organizationService.findOne(us.getOrgId());
	 	if(org != null){
	 		phase = MetaUtils.getOrgTypeMetaProvider().listAllPhase(org.getSchoolings());
	 	}
	 	request.setAttribute(var,phase);
	 	%>
	 </c:when>
</c:choose>
<jsp:doBody/>
