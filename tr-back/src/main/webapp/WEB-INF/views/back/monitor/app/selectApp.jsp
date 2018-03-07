<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:if test="${app.type=='platform_callback'}">
	<div class="unit">		   
	    <label>应用Id：</label>
	    <input type="hidden" name="param[0].name" value="appid"/>
	    <input type="text" name="param[0].val" size="40"  value="${appParam['appid']}"/>
	</div>
	<div class="unit">		   
	    <label>应用key：</label>
	    <input type="hidden" name="param[1].name" value="appkey"/>
	    <input type="text" name="param[1].val" size="40"  value="${appParam['appkey']}"/>
	</div>
	<div class="unit">		   
	    <label>应用url：</label>
	    <input type="hidden" name="param[2].name" value="infoUrl"/>
	    <input type="text" name="param[2].val" size="40"  value="${appParam['infoUrl']}"/>
	</div>
	<div class="unit">		   
	    <label>学校信息url：</label>
	    <input type="hidden" name="param[3].name" value="schoolUrl"/>
	    <input type="text" name="param[3].val" size="40"  value="${appParam['schoolUrl']}"/>
	</div>
</c:if>
<c:if test="${app.type=='user_idcard'}">
</c:if>
