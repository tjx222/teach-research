<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div layoutH="6" class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid;line-height: 35px;padding-left: 20px;">
	编号：&nbsp;&nbsp;${role.id }<br>
	角色名称：&nbsp;&nbsp;${role.roleName }<br>
	角色类型：&nbsp;&nbsp;<jy:dic key="${role.sysRoleId }"/><br>
	角色说明 :&nbsp;&nbsp;${role.roleDesc }<br>
	功能权限:
			<div style="margin-left: 60px;line-height: 20px">
				<c:forEach var="menu" items="${menulist }">
					<c:if test="${menu.parentid == 0 }">
						${menu.name }&nbsp;&nbsp;&nbsp;&nbsp;${menu.desc }<br>
						<c:forEach var="mn" items="${menulist }"> 
							<c:if test="${menu.id == mn.parentid }">
							&nbsp;&nbsp;&nbsp;&nbsp;${mn.name }&nbsp;&nbsp;&nbsp;&nbsp;${mn.desc }<br>
							</c:if>
						</c:forEach>
					</c:if>
				</c:forEach>
			</div>
	<br>
</div>