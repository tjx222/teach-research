<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div layoutH="6" class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;line-height: 35px;padding-left: 20px;">

	编号：&nbsp;&nbsp;${roleType.id }<br>
	
	角色类型名称：&nbsp;&nbsp;${roleType.roleTypeName }<br>
	
	应用于：&nbsp;&nbsp;		<c:if test="${roleType.usePosition==1}">
								<span>区域</span>
							</c:if>
							<c:if test="${roleType.usePosition==2}">
								<span>学校</span>
							</c:if>
							<c:if test="${roleType.usePosition==3}">
								<span>系统</span>
							</c:if>
			<br>
	角色类型入口地址 :&nbsp;&nbsp;${roleType.homeUrl }<br>
	角色类型说明 :&nbsp;&nbsp;${roleType.roleTypeDesc }<br>
	管辖部门:&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${roleType.isNoBmManager}">
				<span>显示</span>
			</c:if>
			<c:if test="${!roleType.isNoBmManager}">
				<span>不显示</span>
			</c:if><br>
	学段:&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${roleType.isNoXz}">
				<span>显示</span>
			</c:if>
			<c:if test="${!roleType.isNoXz}">
				<span>不显示</span>
			</c:if><br>
	学科:&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${roleType.isNoXk}">
				<span>显示</span>
			</c:if>
			<c:if test="${!roleType.isNoXk }">
				<span>不显示</span>
			</c:if><br>
	年级:&nbsp;&nbsp;&nbsp;&nbsp;
			<c:if test="${roleType.isNoNj }">
				<span>显示</span>
			</c:if>
			<c:if test="${!roleType.isNoNj}">
				<span>不显示</span>
			</c:if><br>
	显示顺序:&nbsp;&nbsp;${roleType.sort }<br>
	功能权限:
			<div style="margin-left: 60px;line-height: 20px">
				<c:forEach var="menu" items="${menuList }">
					<c:if test="${menu.parentid == 0 }">
						${menu.name }&nbsp;&nbsp;&nbsp;&nbsp;${menu.desc }<br>
						<c:forEach var="mn" items="${menuList }"> 
							<c:if test="${menu.id == mn.parentid }">
							&nbsp;&nbsp;&nbsp;&nbsp;${mn.name }&nbsp;&nbsp;&nbsp;&nbsp;${mn.desc }<br>
							</c:if>
						</c:forEach>
					</c:if>
				</c:forEach>
			</div>
	<br>
</div>