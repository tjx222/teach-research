<%@tag pageEncoding="UTF-8"%>
<%--  
		元数据显示标签。type: 
		xdToXk -- 根据学段关联学科 
		xzToXd -- 查询学校关联的学段
		xdToNj -- 学段关联的年级 
					   
--%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ tag import="com.tmser.tr.utils.SpringContextHolder" %>
<%@ tag import="com.tmser.tr.manage.meta.MetaUtils,com.tmser.tr.manage.meta.bo.MetaRelationship" %>
<%@ tag import="java.util.*" %>
<%@ tag import="com.tmser.tr.manage.org.service.OrganizationService" %>
<%@ tag import="com.tmser.tr.manage.org.bo.Organization" %>
<%@ tag import="com.tmser.tr.manage.meta.service.MetaRelationshipService" %>
<%@ attribute name="id" type="java.lang.Integer" required="false" description="要显示元数据id" %>
<%@ attribute name="orgId" type="java.lang.Integer" required="false" description="所属机构id" %>
<%@ attribute name="type" type="java.lang.String" required="true" description="显示数据类型" %>
<%@ attribute name="var" type="java.lang.String" required="true" description="设置到变量名称" %>
<%!private MetaRelationshipService metaRelationshipService;
   private OrganizationService organizationService;
%>
<%
    if(metaRelationshipService == null) {
    	metaRelationshipService = SpringContextHolder.getBean(MetaRelationshipService.class);
    	organizationService = SpringContextHolder.getBean(OrganizationService.class);
    }
%>
<c:choose>
	 <c:when test="${not empty type && type == 'xdToNj'}">
	 	<%
	 			if(id != null && id != 0){
		 			request.setAttribute(var,MetaUtils.getPhaseGradeMetaProvider().listAllGradeByPhaseId(id));
	 			}
	 	%>
	 </c:when>
	 <c:when test="${not empty type && type == 'xdToXk'}">
	 	<%
			if(id != null && id != 0){
				Organization organization = organizationService.findOne(orgId);
				String areaIds = organization.getAreaIds();
				Integer[] areaIdArr = com.tmser.tr.utils.StringUtils.toIntegerArray(areaIds.substring(1, areaIds.length() - 1),
				        com.tmser.tr.utils.StringUtils.COMMA);
			 	request.setAttribute(var,MetaUtils.getPhaseSubjectMetaProvider().listAllSubject(orgId, id, areaIdArr));
			}
	 	%>
	 </c:when>
	 <c:when test="${not empty type && type == 'xzToXd'}">
	 	<%
	 	List<MetaRelationship> phase = new ArrayList<MetaRelationship>();
	 	Organization org = organizationService.findOne(orgId);
	 	if(org != null){
	 		phase = MetaUtils.getOrgTypeMetaProvider().listAllPhase(org.getSchoolings());
	 	}
	 	request.setAttribute(var,phase);
	 	%>
	 </c:when>
</c:choose>
<jsp:doBody/>
