<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="同伴互助"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/companion/css/companion.css" media="screen">
	<ui:require module="../m/companion/js"></ui:require>
</head>
<body>
<jy:di key="${companionId }" className="com.tmser.tr.uc.service.UserService" var="u"></jy:di>
<div class="com_personal_center" style="width:100%;">
	<div class="right_option" style="width:9.5%;">
		<div>
			<!-- right_option_act1  选中的的样式 -->
			<c:if test="${not empty datas.jiaoanlist }">
				<a href="javascript:void(0);">
					<span class="right_option_act">教案</span>
					<span class="border1"></span>
				</a>
			</c:if>
			<c:if test="${not empty datas.kejianlist }">
				<a href="javascript:void(0);">
					<span class="right_option_act">课件</span>
					<span class="border1"></span>
				</a>
			</c:if>
			<c:if test="${not empty datas.fansilist }">
				<a href="javascript:void(0);">
					<span class="right_option_act">反思</span>
					<span class="border1"></span>
				</a>
			</c:if>
			<c:if test="${not empty datas.lecturelist }">
				<a href="javascript:void(0);">
					<span class="right_option_act" style="line-height:1.4rem;height:3.8rem;padding-top:1.2rem;" >听课<br />记录</span>
					<span class="border1"></span>
				</a>
			</c:if>
			<c:if test="${not empty datas.plainlist }">
				<a href="javascript:void(0);">
					<span class="right_option_act" style="line-height:1.4rem;height:3.8rem;padding-top:1.2rem;">计划<br />总结</span>
					<span class="border1"></span>
				</a>
			</c:if>
			<c:if test="${not empty datas.thesislist }">
				<a href="javascript:void(0);">
					<span class="right_option_act" style="line-height:1.4rem;height:3.8rem;padding-top:1.2rem;">教学<br />文章</span>
					<span class="border1"></span>
				</a>
			</c:if>
		</div>
	</div>
	<div class="com_personal_center1" style="margin:0 0 0 4rem;">
		<h3><a href="${ctx }/jy/companion/companions/compSendMsg/${companionId}">返回</a>${u.name }</h3>
		<div class="com_personal_cont" id="com_personal_cont">
			<div id="scroller">
				<div class="com_personal_cont_head" style="height:6rem;">
					<ui:photo src="${u.photo }" width="69" height="69"></ui:photo>
					<h4>${u.name }(${u.sex==0?'男':'女' })</h4>
				</div> 
				<div class="com_personal_content">
					<div class="com_personal_cont_head_cont">
						 <div class="com_per_img4">
						 </div>
						  <div class="com_per_cont">
						  	学校名称：${datas.orgName }
						 </div>
					</div>
					<div class="com_personal_cont_head_cont">
						 <div class="com_per_img">
						 </div>
						 <div class="com_per_cont">
						  	<span>教龄：${u.schoolAge }${empty u.schoolAge ? "暂无" : "年"}</span>    
					 		<c:if test="${not empty u.profession }">
					 			<span>职称：${u.profession }</span>
					 		</c:if>
						 </div>
					</div>
					<div class="com_personal_cont_head_cont">
						 <div class="com_per_img1">
						 </div>
						  <div class="com_per_cont">
						  	<span>学科：${datas.subject }</span>    
						  	<span>版本：${datas.formatName }</span>
						 </div>
					</div>
					<div class="com_personal_cont_head_cont">
						 <div class="com_per_img2">
						 </div>
						  <div class="com_per_cont">
						  	年级：${datas.grade }
						 </div>
					</div>
					<div class="com_personal_cont_head_cont">
						 <div class="com_per_img3">
						 </div>
						  <div class="com_per_cont" style="border-bottom:0;">
						  	职务：${datas.role }
						 </div>
					</div>
					<div class="com_personal_cont_head_btn">
						<a href="${ctx }/jy/companion/companions/compSendMsg/${companionId}" class="message_btn"></a>
						<c:if test="${datas.isCare }">
							<span class="gz_btn" act="revoke" userIdCompanion="${companionId}" userNameCompanion="${u.name }">取消关注</span>
						</c:if>
						<c:if test="${!datas.isCare }">
							<span class="gz_btn" act="add" userIdCompanion="${companionId}" >+ 加关注</span>
						</c:if>
					</div>
					<div class="border" style="width:85%;"></div>
					 <div class="com_per_cont_share">
						<div id="scroll">
							<c:if test="${not empty datas.jiaoanlist }">
								<div class="ja_wrap">
									<div class="ja">教案</div>
									<c:forEach items="${datas.jiaoanlist }" var="ja">
										<div class="com_personal_cont_head_cont1" style="width:100%";>
											<div class="com_personal_cont_head_cont1_l" style="width:10%;">
												<ui:photo src="${u.photo }" width="57" height="57"></ui:photo>
											</div>
											<div class="com_personal_cont_head_cont1_r" style="width:88%;">
												<h4>${u.name }</h4>
												<div class="com_per_c_h_fj" style="width:100%";>
													<jy:ds key="${ja.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"></jy:ds>
													<div class="com_per_c_h_fj_l">
													</div>
													<div class="com_per_c_h_fj_r">
														<h4>分享了教案：${ja.planName }</h4>
														<h5>${ja.planName }.${res.ext }</h5>
													</div>
													<a href="<ui:download resid="${res.id}" filename="${ja.planName }"></ui:download>" class="down_btn"></a> 
												</div>
												<span><fmt:formatDate value="${ja.shareTime}" pattern="yyyy-MM-dd HH:mm"/></span>
											</div>
										</div> 
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${not empty datas.kejianlist }">
								<div class="ja_wrap">
									<div class="ja">课件</div>
									<c:forEach items="${datas.kejianlist }" var="kj">
										<div class="com_personal_cont_head_cont1" style="width:100%";>
											<div class="com_personal_cont_head_cont1_l" style="width:10%;">
												<ui:photo src="${u.photo }" width="57" height="57"></ui:photo>
											</div>
											<div class="com_personal_cont_head_cont1_r" style="width:88%;">
												<h4>${u.name }</h4>
												<div class="com_per_c_h_fj" style="width:100%";>
													<jy:ds key="${kj.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"></jy:ds>
													<div class="com_per_c_h_fj_l">
													</div>
													<div class="com_per_c_h_fj_r">
														<h4>分享了课件：${kj.planName }</h4>
														<h5>${res.name }.${res.ext }</h5>
													</div> 
													<a href="<ui:download resid="${res.id}" filename="${res.name }"></ui:download>" class="down_btn"></a>
												</div>
												<span><fmt:formatDate value="${kj.shareTime}" pattern="yyyy-MM-dd HH:mm"/></span>
											</div>
										</div> 
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${not empty datas.fansilist }">
								<div class="ja_wrap">
									<div class="ja">反思</div>
									<c:forEach items="${datas.fansilist }" var="fs">
										<div class="com_personal_cont_head_cont1" style="width:100%";>
											<div class="com_personal_cont_head_cont1_l" style="width:10%;">
												<ui:photo src="${u.photo }" width="57" height="57"></ui:photo>
											</div>
											<div class="com_personal_cont_head_cont1_r" style="width:88%;">
												<h4>${u.name }</h4>
												<div class="com_per_c_h_fj" style="width:100%";>
													<jy:ds key="${fs.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"></jy:ds>
													<div class="com_per_c_h_fj_l">
													</div>
													<div class="com_per_c_h_fj_r">
														<h4>分享了反思：${fs.planName }</h4>
														<h5>${res.name }.${res.ext }</h5>
													</div> 
													<a href="<ui:download resid="${res.id}" filename="${res.name }"></ui:download>" class="down_btn"></a>
												</div>
												<span><fmt:formatDate value="${fs.shareTime}" pattern="yyyy-MM-dd HH:mm"/></span>
											</div>
										</div> 
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${not empty datas.lecturelist }">
								<div class="ja_wrap">
									<div class="ja">听课记录</div>
									<c:forEach items="${datas.lecturelist }" var="lecture">
										<div class="com_personal_cont_head_cont1" style="width:100%";>
											<div class="com_personal_cont_head_cont1_l" style="width:10%;">
												<ui:photo src="${u.photo }" width="57" height="57"></ui:photo>
											</div>
											<div class="com_personal_cont_head_cont1_r" style="width:88%;">
												<h4>${u.name }</h4>
												<div class="com_per_c_h_fj" style="width:100%";>
													<div class="com_per_c_h_fj_l">
													</div>
													<div class="com_per_c_h_fj_r">
														<h4>分享了听课记录：${lecture.topic }</h4>
				<!-- 										<h5>${res.name }.${res.ext }</h5> -->
													</div> 
				<!-- 									<a href="<ui:download resid="${res.id}" filename="${res.name }"></ui:download>" class="down_btn"></a> -->
												</div>
												<span><fmt:formatDate value="${lecture.shareTime}" pattern="yyyy-MM-dd HH:mm"/></span>
											</div>
										</div> 
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${not empty datas.plainlist }">
								<div class="ja_wrap">
									<div class="ja">计划总结</div>
									<c:forEach items="${datas.plainlist }" var="plain">
										<div class="com_personal_cont_head_cont1" style="width:100%";>
											<div class="com_personal_cont_head_cont1_l" style="width:10%;">
												<ui:photo src="${u.photo }" width="57" height="57"></ui:photo>
											</div>
											<div class="com_personal_cont_head_cont1_r" style="width:88%;">
												<h4>${u.name }</h4>
												<div class="com_per_c_h_fj" style="width:100%";>
													<jy:ds key="${plain.contentFileKey }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"></jy:ds>
													<div class="com_per_c_h_fj_l">
													</div>
													<div class="com_per_c_h_fj_r">
														<h4>分享了计划总结：${plain.title }</h4>
														<h5>${res.name }.${res.ext }</h5>
													</div> 
													<a href="<ui:download resid="${res.id}" filename="${res.name }"></ui:download>" class="down_btn"></a>
												</div>
												<span><fmt:formatDate value="${plain.shareTime}" pattern="yyyy-MM-dd HH:mm"/></span>
											</div>
										</div> 
									</c:forEach>
								</div>
							</c:if>
							<c:if test="${not empty datas.thesislist }">
								<div class="ja_wrap">
									<div class="ja">教学文章</div>
									<c:forEach items="${datas.thesislist }" var="thesis">
										<div class="com_personal_cont_head_cont1" style="width:100%";>
											<div class="com_personal_cont_head_cont1_l" style="width:10%;">
												<ui:photo src="${u.photo }" width="57" height="57"></ui:photo>
											</div>
											<div class="com_personal_cont_head_cont1_r" style="width:88%;">
												<h4>${u.name }</h4>
												<div class="com_per_c_h_fj" style="width:100%";>
													<jy:ds key="${thesis.resId }" className="com.tmser.tr.manage.resources.service.ResourcesService" var="res"></jy:ds>
													<div class="com_per_c_h_fj_l">
													</div>
													<div class="com_per_c_h_fj_r">
														<h4>分享了教学文章：${thesis.thesisTitle }</h4>
														<h5>${res.name }.${res.ext }</h5>
													</div> 
													<a href="<ui:download resid="${res.id}" filename="${res.name }"></ui:download>" class="down_btn"></a>
												</div>
												<span><fmt:formatDate value="${thesis.shareTime}" pattern="yyyy-MM-dd HH:mm"/></span>
											</div>
										</div> 
									</c:forEach>
								</div>
							</c:if>
							<div style="clear:both;height:2rem;"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
	require(["zepto","detail"],function(){
	});
</script>
</html>