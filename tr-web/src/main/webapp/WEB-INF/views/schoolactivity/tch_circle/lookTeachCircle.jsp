<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="查看校际教研圈"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/modules/schoolactivity/css/school_teaching.css"
	media="screen" />
<script type="text/javascript">
	$(function() {
		$(".teaching_circle_dl dl").hover(function() {
			$(this).next("div").show();
		}, function() {
			$(this).next("div").hide();
		});
	});
</script>
</head>
<body>
	<div class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop style="1" modelName="校际教研"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="xjjy_ckjyq"></jy:nav>
			</h3>
		</div>
		<div class='teaching_table_cont'>
			<div class='teaching_circle_table_c'>
				<h3>
					<span></span><strong>校际教研圈列表</strong>
				</h3>
				<c:choose>
					<c:when test="${!empty stcList }">
						<div class="teaching_circle_table_b">
							<c:forEach items="${stcList }" var="stc">
								<div class="teaching_circle_dl">
									<dl>
										<dd>
											<div class="school">
												<ol>
													<c:forEach items="${stc.stcoList }" var="stco">
														<li>
															<a title="${stco.orgName }" class="w90">${stco.orgName }</a>
															<span>
																 <c:choose>
																	<c:when test="${stco.state==1}">
																		<label class="z_zc">待接受</label>
																	</c:when>
																	<c:when test="${stco.state==2}">
																		<label class="z_ty">已接受</label>
																	</c:when>
																	<c:when test="${stco.state==3}">
																		<label class="z_jj">已拒绝</label>
																	</c:when>
																	<c:when test="${stco.state==4}">
																		<label class="z_tc">已退出</label>
																	</c:when>
																	<c:when test="${stco.state==5}">
																		<label class="z_ty">已恢复</label>
																	</c:when>
																</c:choose>
															</span>
														</li>
													</c:forEach>
												</ol>
											</div>
										</dd>
										<dt>
											<span title='${stc.name }'>${stc.name }</span> <span><fmt:formatDate
													value="${stc.crtDttm }" pattern="yyyy-MM-dd" /></span>
										</dt>
									</dl> 
								</div>
							</c:forEach>
						</div>
					</c:when>
					<c:otherwise>
						<div class="empty_wrap">
							<div class="empty_img"></div>
							<div class="empty_info" style="text-align: center;">校长还没有创建校际教研圈哟，稍后再来查看吧！</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
</html>