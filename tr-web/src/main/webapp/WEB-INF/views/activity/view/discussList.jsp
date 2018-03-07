<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="参与讨论列表"></ui:htmlHeader>
	<script type="text/javascript" src="${ctxStatic }/modules/activity/js/discussReply.js"></script>
</head>
<body style="background: none;" >	
	<div class="word_tab_3" style="width:823px;height:auto;">
		<h3>讨论列表  &nbsp;&nbsp;
			<c:if test="${!empty activityDiscuss.userId}"><span style="cursor:pointer;color:#45a5fe;" onclick="discussAll('${activityDiscuss.activityId}')">全部留言</span></c:if>
	 	</h3>
	 	<c:if test="${not empty discussList.datalist }">
			<h4 id="shouqizhankai" style="cursor: default;padding-top:5px;height: 20px;width: 42px;">
				<span id="shouqi" style="display:block;width:42px;float:right;cursor:pointer;" onclick="shouzhan(0)">收起<img src="${ctxStatic }/common/images/sq.png" alt=""></span>
				<span id="zhankai" style="display:block;width:42px;float:right;cursor:pointer;display:none;" onclick="shouzhan(1)">展开<img src="${ctxStatic }/common/images/sqq.png" alt=""></span>
			</h4>
		</c:if>
		<c:if test="${empty discussList.datalist }">
		<h4></h4>
		</c:if>
		<div class="clear"></div>
		<div id="taolunliebiao" class="word_tab_3_cont" style="height:auto;">
		<c:if test="${!empty discussList.datalist  }">
			<c:forEach items="${discussList.datalist  }" var="data" varStatus="coStu">
				<div class="check-bottom_1">
						<jy:di key="${data.userId }" className="com.tmser.tr.uc.service.UserService" var="u"/>
					<div class="check-bottom_1_left">
						<ui:photo src="${u.photo }" width="60" height="65"></ui:photo>
						<div class="school_1">
									<dl>
										<dd>
											<span onclick="openTBHZ('${u.id }')"><ui:photo src="${u.photo }" width="80" height="85" ></ui:photo></span>
											<span>${u.name }</span>
										</dd>
										<dt>
											<jy:di key="${data.spaceId }" className="com.tmser.tr.uc.service.UserSpaceService" var="us"/>
											<c:if test="${!empty us.subjectId && us.subjectId!=0 }">
												<span>学科：
													<jy:dic key="${us.subjectId }"/>
												</span>
											</c:if>
											<c:if test="${!empty us.bookId }">
												<span>教材：
													<jy:ds key="${us.bookId }" className="com.tmser.tr.manage.meta.service.BookService" var="book"/>
													${book.formatName }
												</span>
											</c:if>
											<c:if test="${!empty us.gradeId && us.gradeId!=0 }">
												<span>年级：
													<jy:dic key="${us.gradeId }" />
												</span>
											</c:if>
											<span>职务：
												<jy:di key="${us.roleId }" className="com.tmser.tr.uc.service.RoleService" var="role"/>
												${role.roleName }
											</span>
											<c:if test="${!empty u.profession }">
												<span>职称：
													${u.profession }
												</span>
											</c:if>
										</dt>
									</dl>
								</div>
					</div>
					<div class="check-bottom_1_right" id="cont_${data.id}">
						<div class="check-bottom_1_right_top" style="word-break: break-all;">
							<span style="color:#45a5fe;" >${u.name }说：</span>
							<span style="color:#474747;">${data.content }</span>
						</div>
						<div class="check-bottom_1_right_botm">
							<strong style="cursor:pointer;margin-left: 13px;" data-id="${data.id }" data-opinionid="${data.id }" class="reply_rq" data-index="${data.id }" data-uname="${u.name}">回复</strong>
							<span><fmt:formatDate value="${data.createTime  }" pattern="yyyy-MM-dd HH:mm"/></span>
						</div>
					</div>
						
					<c:forEach items="${data.childList  }" var="dataTwo">
						<div class="check-bottom_02" id="cont_${dataTwo.id}">
							<jy:di key="${dataTwo.userId }" className="com.tmser.tr.uc.service.UserService" var="u2"/>
							<div class="check-bottom_02_left">
								<ui:photo src="${u2.photo }" width="60" height="65"></ui:photo>
								<div class="school_1">
									<dl>
										<dd>
											<span onclick="openTBHZ('${u2.id }')"><ui:photo src="${u2.photo }" width="80" height="85" ></ui:photo></span>
											<span>${u2.name }</span>
										</dd>
										<dt>
											<jy:di key="${dataTwo.spaceId }" className="com.tmser.tr.uc.service.UserSpaceService" var="us"/>
											<c:if test="${!empty us.subjectId && us.subjectId!=0 }">
												<span>学科：
													<jy:dic key="${us.subjectId }"/>
												</span>
											</c:if>
											<c:if test="${!empty us.bookId }">
												<span>教材：
													<jy:ds key="${us.bookId }" className="com.tmser.tr.manage.meta.service.BookService" var="book"/>
													${book.formatName }
												</span>
											</c:if>
											<c:if test="${!empty us.gradeId && us.gradeId!=0 }">
												<span>年级：
													<jy:dic key="${us.gradeId }"/>
												</span>
											</c:if>
											<span>职务：
												<jy:di key="${us.roleId }" className="com.tmser.tr.uc.service.RoleService" var="role"/>
												${role.roleName }
											</span>
											<c:if test="${!empty u2.profession }">
												<span>职称：
													${u2.profession }
												</span>
											</c:if>
										</dt>
									</dl>
								</div>
							</div>
							<div style="width:663px;margin-left:10px;float:left;min-height: 75px;font-size: 14px;">
						 		<div style="line-height: 20px;padding-bottom: 3px;word-break: break-all;" >
						 			<span style="color:#45a5fe;" >${u2.name }说：</span>
									<span>${dataTwo.content }</span>
						 		</div>
								<div style="width:100%;height:23px;padding-right: 2px;">
									<strong style="float:right;color:#838383;">
										<fmt:formatDate value="${dataTwo.createTime  }" pattern="yyyy-MM-dd HH:mm"/>&nbsp;
										<strong style="cursor:pointer;margin-left: 5px;color:#014efd;" data-id="${dataTwo.id }" data-opinionid="${data.id }" class="reply_rq" data-index="${dataTwo.id }" data-uname="${u2.name}" >回复</strong>
									</strong>
								</div>
							</div>
						</div>
						<div class="clear"></div>
					</c:forEach>
				</div>
				<div class="clear"></div>
				<div style="border-bottom:1px #bdbdbd solid;width:801px;margin-left:15px;margin-top:5px;margin-bottom:5px;"></div>
				<div class="clear"></div>
				<div id="replay_${data.id}" class="replay" style="display:none;padding-right: 20px;" data-opinionid="${data.id }" data-index="${coStu.index }">
					<form id="replayForm_${data.id}" action="" method="post">
						<input type="hidden" name="activityId" value="${activityDiscuss.activityId}"/>
						<input type="hidden" name="parentId" value="${data.id}"/>
						<input type="hidden" name="discussLevel" value="2"/>
						<input type="hidden" name="content" id="contHidden_${data.id}">
						<textarea rows="2" cols="86" name="content_reply" ></textarea>
						<input type="button" class="reply_btn" value="回复" style="margin:5px 0 5px 420px;" data-opinionid="${data.id}" data-index="${coStu.index }"/>
					</form>
				</div>
			</c:forEach>
			<form name="pageForm" method="post">
				<ui:page url="${ctx}jy/activity/discussIndex" data="${discussList}"  />
				<input type="hidden" class="currentPage" name="currentPage">
				<input type="hidden" name="typeId" value="5">
				<input type="hidden" name="userId" value="${activityDiscuss.userId}">
				<input type="hidden" name="activityId" value="${activityDiscuss.activityId}">
				<input type="hidden" name="canReply" value="${activityDiscuss.canReply}">
			</form>
			</c:if>
			<c:if test="${empty discussList.datalist  }">
				<div style="width:800px;text-align:center;line-height:30px;">
					<div class="check_k"></div>
					<div style="color:#ccc;font-size:15px;font-weight:bold;line-height:20px;height:30px;">暂无讨论,稍后再来看吧！</div>
				</div>
			</c:if>
		</div>
		
	</div>
	<form id="submitReply" method="post" action="${ctx}jy/activity/discussIndex">
		<input type="hidden" id="currentPage" name="currentPage" value="${page.currentPage}">
		<input type="hidden" name="typeId" value="5">
		<input type="hidden" name="userId" value="${activityDiscuss.userId}">
		<input type="hidden" name="activityId" value="${activityDiscuss.activityId}">
		<input type="hidden" name="canReply" value="${activityDiscuss.canReply}">
	</form>
</body>	
<script type="text/javascript">
	//收起展开
	function shouzhan(params){
		if(params==0){//收起
			if("${discussList.datalist  }"!="" && "${discussList.datalist  }"!="[]"){
				$("#taolunliebiao").css("display","none");
				$("#shouqi").css("display","none");
				$("#zhankai").css("display","");
				window.parent.setCwinHeight("discuss_iframe");
			}
		}else if(params==1){//展开
			$("#taolunliebiao").css("display","");
			$("#zhankai").css("display","none");
			$("#shouqi").css("display","");
			window.parent.setCwinHeight("discuss_iframe");
		}
	}
	
	//查看全部用户发起的讨论
	function discussAll(activityId){
		var canReply = "${activityDiscuss.canReply}";
		location.href = _WEB_CONTEXT_+"/jy/activity/discussIndex?activityId="+activityId+"&canReply="+canReply+"&typeId=5&"+ Math.random();
	}
	
	$("#taolunliebiao").eq(-3).remove();
	
	//打开同伴互助页面
	function openTBHZ(userId){
		window.parent.open(_WEB_CONTEXT_+"/jy/companion/companions/"+userId,"_blank");
	}
</script>
</html>
