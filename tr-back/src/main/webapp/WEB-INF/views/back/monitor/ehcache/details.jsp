<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/taglib.jspf" %>
<div class="pageContent">
<div class="panelBar">
	<ul class="toolBar">
			<li><a class="edit" href="${ctx}/jy/back/monitor/ehcache/${cacheName}/clear" target="ajaxTodo" title="确定要清空缓存吗?" mask="true"><span>清空</span></a></li>
			<li><a class="delete" href="${ctx}/jy/back/monitor/ehcache/${cacheName}/{sid_obj}/delete" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
	</ul>
</div>
<table class="table" width="100%" layoutH="360" rel="cache-deatil-list">
	<thead>
			<tr>
				<th>键值</th>
				<th width="100">命中次数</th>
				<th width="80">大小</th>
				<th width="100">更新时间</th>
				<th width="100">访问时间</th>
				<th width="100">过期时间</th>
				<th width="80">timeToIdle</th>
				<th width="80">timeToLive</th>
				<th width="80">version</th>
			</tr>
		</thead>
        <tbody>
        <c:forEach items="${keys}" var="key" varStatus="stat">
            <tr target="sid_obj" rel="${key}">
                <td >${cacheName == 'sqlMappingCache'?jfn:decodeBase64(key) : key}</td>
                <td>${keydetails[key].hitCount}</td>
                <td>${keydetails[key].size}</td>
                <td>${keydetails[key].latestOfCreationAndUpdateTime}</td>
                <td>${keydetails[key].lastAccessTime}</td>
                <td>${keydetails[key].expirationTime}</td>
                <td>${keydetails[key].timeToIdle}</td>
                <td>${keydetails[key].timeToLive}</td>
                <td>${keydetails[key].version}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
