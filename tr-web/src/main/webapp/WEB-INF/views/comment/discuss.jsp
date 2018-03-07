<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="讨论列表"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/common/css/consult_opinion.css" media="screen">
	<ui:require module="comment/js"></ui:require>
</head>
<body style="background: none;" >	
	<div class='discuss_list_wrap'>
	<div class='discuss_list_wrap_cont clearfix'>
		<div class='discuss_list_title'>
			<h5 class='discuss_list_title_h5'><span></span>讨论列表</h5>
			<div class='search_criteria'>
				<div class='toggle'>
					<span></span>
					<strong>收起</strong>
				</div>
				<div class='search'>
					<input type='text' placeholder='输入教师姓名进行查找' class='search_txt' maxlength="20" value="${searchName }"/>
					<input type='button' class='search_btn' />
				</div>
				<c:if test="${not empty searchName or not empty activityDiscuss.crtId }">
					<span class='whole'>全部</span>
				</c:if>
			</div>
		</div>
		<div class='discuss_list_wrap_border'></div>
		
		<c:forEach items="${discussList.datalist  }" var="data" varStatus="coStu">
			<div class="discuss_list_cont clearfix"> 
				<jy:di key="${data.crtId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
				<div class="discuss_list_head">
					<div class="discuss_list_head_bg"></div> 
					<ui:photo src="${u.photo }" width="54" height="51" ></ui:photo>
				</div> 
				<div class="discuss_list_right">
					<div class="discuss_list_right_t">
						<span class="names">${u.name }：</span>
						<span class="names_date"><fmt:formatDate value="${data.crtDttm  }" pattern="yyyy-MM-dd HH:mm"/></span>
						<jy:di key="${u.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org"/>
						<span class="names_school">${org.name }</span>
					</div>
					<div class="discuss_list_right_c">
						${data.content }
					</div>
					<div class="discuss_list_right_b">
						<c:if test="${canReply}">
							<div class="reply" data-uname="${u.name }" data-pid="${data.id}" data-id="${data.id }" data-index="${coStu.index }">
								<span></span>回复
							</div>
						</c:if>
					</div>
					<c:forEach items="${data.childList  }" var="dataTwo">
						<div class='discuss_list_border'></div>
						<div class="discuss_list_cont1 clearfix">
							<jy:di key="${dataTwo.crtId }" className="com.tmser.tr.uc.service.UserService" var="u2"/> 
							<div class="discuss_list_head">
								<div class="discuss_list_head_bg"></div> 
								<ui:photo src="${u2.photo }" width="54" height="51" ></ui:photo>
							</div> 
							<div class="discuss_list_right1">
								<div class="discuss_list_right_t">
									<span class="names">${u2.name }：</span>
									<span class="names_date"><fmt:formatDate value="${dataTwo.crtDttm  }" pattern="yyyy-MM-dd HH:mm"/></span>
									<jy:di key="${u2.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org2"/>
									<span class="names_school">${org2.name }</span>
								</div>
								<div class="discuss_list_right_c">
									${dataTwo.content }
								</div>
								<div class="discuss_list_right_b">
									<c:if test="${canReply}">
										<div class="reply" data-uname="${u2.name }" data-pid="${dataTwo.parentId}" data-id="${dataTwo.id }" data-index="${coStu.index }">
											<span></span>
											回复
										</div>
									</c:if>
								</div>
							</div>
						</div>
					</c:forEach>
					<div class="rp_textarea" data-index="${data.id }" style="display:none;float:right;" id="rp_${data.id}" data-pid="${data.id}">
						<textarea name="replyContent" id="replyContent" style="margin-left:100px;display:none" class="hf"></textarea>
						<div class="clear"></div>
						<input type="button" class="reply_btn reply_textarea_btn" value="回复" style="margin:5px 0 5px 420px;"  data-pid="${data.id }" data-index="${coStu.index }" data-authorid=""/>
					</div>
				</div>
			</div>
			<div class='discuss_list_border'></div>
		</c:forEach>
		<c:if test="${empty discussList.datalist }">
			<div class="check_k_wrap">
				<div class="check_k"></div>
				<div class="check_k_info">暂无讨论,稍后再来看吧！</div>
			</div>
		</c:if>
		<div class='page'>
			<form name="pageForm" method="post" id="pageForm" action="${ctx}jy/comment/discussIndex">
				<ui:page url="${ctx}jy/comment/discussIndex" data="${discussList}" views="5"  />
				<input type="hidden" class="currentPage" name="currentPage">
				<input type="hidden" name="typeId" value="${activityDiscuss.typeId}">
				<input type="hidden" name="activityId" value="${activityDiscuss.activityId}">
				<input type="hidden" name="canReply" value="${canReply}">
				<input type="hidden" id="searchName" name="searchName" value="${searchName}">
			</form>
		</div>
	</div>
</div>
<form id="replayForm" method="post">
	<ui:token/>
	<input type="hidden" name="activityId" value="${activityDiscuss.activityId}"/>
	<input type="hidden" name="typeId" value="${activityDiscuss.typeId}" />
	<input type="hidden" id="parentId" name="parentId" />
	<input type="hidden" name="discussLevel" value="2"/>
	<input type="hidden" id="contHiddenId" name="content" />
</form>
</body>	
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','discuss','editor'],function(){
		<c:if test="${canReply}">
		var timestamp="";
		//错误时间戳
		var errortimeStamp=0;
		var discuss_num = 0;
		window.getDiscussNum = function(){
			$.ajax('./jy/comment/isupdate?r='+Math.random()+'&timestamp='+timestamp+'&typeId=${activityDiscuss.typeId}&activityId=${activityDiscuss.activityId}',{
				type:'get',
				dataType:'json',
				success:function(result){
					//result=result.result;
					if(typeof result=='undefined'){
						return;
					}else if(typeof result.code =='undefined'){
						window.top.location.reload();
					}
					if(result.code==1){
						if(discuss_num > 0 && result.data.noticeNum > discuss_num){
							if($("div.rp_textarea:visible").length>0){
								//TODO 正在回复
							}else{
								window.location.reload();
							}
						}
						timestamp=result.data.noticeTimeStamp;
						discuss_num = result.data.noticeNum;
					}
					//再次发起请求
					setTimeout("getDiscussNum()",2000);
				},
				error:function(){
					var now=new Date().getTime();
					//如果在10秒内连续出错，则等待两分钟后再执行
					if(now-errortimeStamp<10000){
						setTimeout("getDiscussNum()",120000);
					}else{
						setTimeout("getDiscussNum()",5000);
					}
					errortimeStamp=now;
				}
				
			});
			
		}
		setTimeout("getDiscussNum();",1000);
		</c:if>
	});
</script>
</html>
