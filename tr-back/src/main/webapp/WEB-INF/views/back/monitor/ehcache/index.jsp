<%@ page import="org.springframework.cache.CacheManager" %>
<%@ page import="net.sf.ehcache.Statistics" %>
<%@ page import="net.sf.ehcache.Cache" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/include/taglib.jspf" %>
<div class="pageContent">
<div class="tabs" style="margin-top: 5px;" currentIndex="0" eventType="click">
	<div class="tabsHeader">
		<div class="tabsHeaderContent">
			<ul>
				<li><a href="javascript:;"  id="ehcache_local"><span>后台缓存监控</span></a></li>
				<li><a href="${ctx}/jy/back/monitor/ehcache/front" class="j-ajax" id="ehcache_front"><span>前台缓存监控</span></a></li>
			</ul>
		</div>
	</div>
	<div class="tabsContent" style="height:510px" class="layoutBox">
	<div>
    <table class="table">
   		 <thead>
			<th style="width:20%;">缓存名称</th>
			<th style="width:7%;"><a style="line-height:20px;" href="${ctx}/jy/back/monitor/ehcache?sort=hitPercent" navtitle="缓存监控" target="navTab" rel="moniter-cache">总命中率</a></th>
			<th style="width:7%;"><a style="line-height:20px;" href="${ctx}/jy/back/monitor/ehcache?sort=hitCount" navtitle="缓存监控" target="navTab" rel="moniter-cache">命中次数</a></th>
			<th style="width:7%;"><a style="line-height:20px;" href="${ctx}/jy/back/monitor/ehcache?sort=misses" navtitle="缓存监控" target="navTab" rel="moniter-cache">失效次数</a></th>
			<th style="width:10%;"><a style="line-height:20px;" href="${ctx}/jy/back/monitor/ehcache?sort=objectCount" target="navTab" navtitle="缓存监控" rel="moniter-cache">缓存总对象数</a></th>
			<th style="width:20%;">最后一秒查询完成的执行数</th>
			<th style="width:20%;">最后一次采样的平均执行时间</th>
			<th style="width:15%;">平均获取时间</th>
		</thead>
        <tbody>
        <%
            CacheManager cacheManager = (CacheManager) request.getAttribute("cacheManager");
        	Collection<String> cacheNames = cacheManager.getCacheNames();
            String[] names = sort(cacheNames, cacheManager, request.getParameter("sort"));
            for (String cacheName : names) {
                pageContext.setAttribute("cacheName", cacheName);
                pageContext.setAttribute("cache", cacheManager.getCache(cacheName).getNativeCache());
        %>
		
        <c:set var="totalCount"
               value="${cache.statistics.cacheHits + cache.statistics.cacheMisses}"/>
        <c:set var="totalCount" value="${totalCount > 0 ? totalCount : 1}"/>
        <c:set var="cacheHitPercent" value="${cache.statistics.cacheHits * 1.0 / (totalCount)}"/>
        <tr class="bold info">
            <td style="text-align:left;">
             <a href="${ctx}/jy/back/monitor/ehcache/${cacheName}/details" title="${cacheName}" target="navTab" navtitle="缓存详情" rel="moniter-cache-detail">${cacheName}</a>
            </td>
            <td><fmt:formatNumber type="percent" value="${cacheHitPercent}" /> </td>
            <td>${cache.statistics.cacheHits}</td>
            <td>${cache.statistics.cacheMisses}</td>
            <td>${cache.statistics.objectCount}</td>
            <td>${cache.statistics.searchesPerSecond}</td>
            <td><fmt:formatNumber type="number" value="${cache.statistics.averageSearchTime}" />毫秒</td>
            <td><fmt:formatNumber type="number" value="${cache.statistics.averageGetTime}" />毫秒</td>
        </tr>
        <%
            }
        %>

        </tbody>
    </table>
    <br/><br/>
</div>
		<div></div>
	</div>
</div>
</div>
<%!
        private String[] sort(final Collection<String> cacheNames, final CacheManager cacheManager, final String sort) {
        String[] a = new String[cacheNames.size()];    
		Arrays.sort(cacheNames.toArray(a), new Comparator<String>() {
                public int compare(String n1, String n2) {
                    Statistics s1 = ((Cache)cacheManager.getCache(n1).getNativeCache()).getStatistics();
                    Statistics s2 = ((Cache)cacheManager.getCache(n2).getNativeCache()).getStatistics();
                    if("hitPercent".equals(sort)) {
                        double n1HitPercent = 1.0 * s1.getCacheHits() / Math.max(s1.getCacheHits() + s1.getCacheMisses(), 1);
                        double n2HitPercent = 1.0 * s2.getCacheHits() / Math.max(s2.getCacheHits() + s2.getCacheMisses(), 1);
                        return -Double.compare(n1HitPercent, n2HitPercent);
                    }else if("hitCount".equals(sort)) {
                        return -Long.valueOf(s1.getCacheHits()).compareTo(Long.valueOf(s2.getCacheHits()));
                    }else if("misses".equals(sort)) {
                        return -Long.valueOf(s1.getCacheMisses()).compareTo(Long.valueOf(s2.getCacheMisses()));
                    } else if("objectCount".equals(sort)) {
                        return -Long.valueOf(s1.getObjectCount()).compareTo(Long.valueOf(s2.getObjectCount()));
                    }
                    return -n1.compareTo(n2);
                }

            });
		return a;
        }
%>