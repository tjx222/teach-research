<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 <tr id="${kt.id}">
	<td style="text-align:left;">
	<strong>${kt.type==0?'【校内】':'【校外】'}</strong>
	<span onclick="seetopic(this);" title="${kt.topic}"><ui:sout value="${kt.topic}" length="26" needEllipsis="true"/></span>
	   <c:if test="${kt.isReply=='1'}"><!-- 判断是否有回复 -->
	   	<a class="a1" onclick="replylist(${kt.lecturepeopleId},${kt.id},${kt.teachingpeopleId},this);"><c:if test="${kt.replyUp=='1'}"><b></b></c:if></a>
	   </c:if>
		<!-- <a href="" class="a2"><b></b></a> -->
	 	<c:if test="${kt.isComment=='1'}"><!-- 判断是否有评论 -->
	    	<a class="a3" onclick="commentlist(${kt.lecturepeopleId},${kt.resType},${kt.id});"><c:if test="${kt.commentUp=='1'}"><b></b></c:if></a>
	    </c:if>
   </td>
   <td>
	 <c:if test="${kt.type=='0'}">
		  ${kt.grade}${kt.subject}
	 </c:if>
	 <c:if test="${kt.type=='1'}">
		  ${empty kt.gradeSubject?'-':kt.gradeSubject}
     </c:if>
   </td>
   <td>${empty kt.teachingPeople?'-':kt.teachingPeople}</td>
   <td>${kt.numberLectures}</td>
   <td>
   	<fmt:setLocale value="zh"/>
		<fmt:formatDate value="${kt.lectureTime}" pattern="MM-dd"/>
   </td>
   <td style="position: relative;">
	    <c:if test="${kt.isSubmit==0}"><!-- 未提交 -->
	    	<c:if test="${kt.isShare==0}">
	    		<img src="${ctxStatic }/modules/lecturerecords/images/fx.png" alt="" onclick="changeShare(this);" style="width:18px;position: absolute;left:32px;top:3px;height:20px;margin-right:10px;" title="分享" topic="${kt.type==0?'【校内】':'【校外】'}${kt.topic}">
    			<img src="${ctxStatic }/modules/lecturerecords/images/de2.png" alt="" onclick="deletelecture(this);" style="position: absolute;left:68px;top:5px;width:14px;height:15px;" title="删除" topic="${kt.type==0?'【校内】':'【校外】'}${kt.topic}">
	    	</c:if>
	    	<c:if test="${kt.isShare==1}">
	    		<c:if test="${kt.isComment==0}">
	    			<img src="${ctxStatic }/modules/lecturerecords/images/fx2.png" alt="" onclick="changeShare(this);" style="width:18px;height:20px;margin-right:10px;position: absolute;left:32px;top:3px;"title="取消分享" topic="${kt.type==0?'【校内】':'【校外】'}${kt.topic}">
    				<img src="${ctxStatic }/modules/lecturerecords/images/jzde.png" style="position: absolute;left:68px;top:5px;width:14px;height:15px;cursor: not-allowed;"  alt="" title="禁止删除">
	    		</c:if>
	    		<c:if test="${kt.isComment==1}">
	    			<img src="${ctxStatic }/modules/lecturerecords/images/fx2.png" alt="" style="width:18px;height:20px;margin-right:10px;position: absolute;left:32px;top:3px;" title="禁止分享">
    				<img src="${ctxStatic }/modules/lecturerecords/images/jzde.png" alt="" style="width:14px;height:15px;position: absolute;left:68px;top:5px;cursor: not-allowed;"  title="禁止删除">
	    		</c:if>
	    	</c:if>
	    </c:if>
    </td>
   <td style="display: none;">
		<input type="text" value="${kt.isDelete}" id="isdelete"/><!-- 是否删除标记 -->
		<input type="text" value="${quxiaofenxiang}" id="isshare"/><!-- 是否取消分享 -->
	</td>
 </tr>