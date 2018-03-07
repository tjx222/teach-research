<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="同伴互助"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/lib/calendar/skin/WdatePicker.css" media="screen" />
<link rel="stylesheet"
	href="${ctxStatic}/modules/companion/css/companion.css" media="screen" />
</head>
<body>
	<input type="hidden" id="currentUserId" value="${_CURRENT_USER_.id }">
	<div id="wrap" class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop style="1" modelName="同伴互助"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="copanions_messages">
					<jy:param name="name" value="同伴互助" />
					<jy:param name="isSameSchool" value="1" />
				</jy:nav>
			</h3>
		</div>
		<div class='all_message_cont'>
			<div class="all_message_cont_l">
				<div class="all_message_cont_l_t">
					<div class="serch_wrap">
						<input type="text" class="serch" id="userName"
							placeholder="输入姓名进行查找" /> <input type="button" class="serch_btn"
							id="userNameSearch" data-type="search_compane" />
					</div>
				</div>
				<ol class='all_message_ol'>
					<li class="Recently companions" data-type="recently_compane">最近联系人</li>
					<li class="Friends companions all_message_ol_act"
						data-type="friends_compane">我的关注</li>
				</ol>
				<div class="all_message_cont_l_b">
					<ol class="expmenu" style="display: none;" id="recently_compane">
						<li class="expmenu_li"><a class="header1"><c:forEach
									items="${companions}" var="item" varStatus="status">
									<dl
										class="recentlyCompanions <c:if test="${status.index==0 }">header1_dl_act</c:if>"
										data-userIdCompanion="${item.userIdCompanion }"
										data-userNameCompanion="${item.userNameCompanion}">
										<dd>
											<ui:photo src="${item.photo }"></ui:photo>
											<div class="header1_bg"></div>
											<div class="Details">
												<div class="left_sj1"></div>
												<div class="Details_span">${item.userNameCompanion }</div>
												<div class="Details_span" title="${item.highestRoleName }">
													职务：
													<ui:sout value="${item.highestRoleName }" length="9"
														needEllipsis="true" />
												</div>
												<div class="Details_span" title="${item.profession }">
													职称：
													<ui:sout value="${item.profession }" length="9"
														needEllipsis="true" />
												</div>
											</div>
										</dd>
										<dt>${item.userNameCompanion }
											<c:if test="${item.isFriend==1 }">
												<b class="remo"
													data-userIdCompanion="${item.userIdCompanion }"
													data-userNameCompanion="${item.userNameCompanion }"></b>
											</c:if>
										</dt>
									</dl>
								</c:forEach></a></li>
					</ol>
					<ol class="expmenu" id="friends_compane">
						<li><a class="header"> <span class="arrow up"></span> <span
								class="label">单位同伴<b>&nbsp;(${friendsInnerNum }个)&nbsp;</b></span>
						</a> <span class="no">
								<ol class="menu" style="display: none;">
									<li class="checkbox_1"><a class="header1"><c:forEach
												items="${friendsInner}" var="item" varStatus="status">
												<dl
													class="recentlyCompanions <c:if test="${status.index==0 }">header1_dl_act</c:if>"
													data-userIdCompanion="${item.userIdCompanion }"
													data-userNameCompanion="${item.userNameCompanion}">
													<dd>
														<ui:photo src="${item.photo }"></ui:photo>
														<div class="header1_bg"></div>
														<div class="Details">
															<div class="left_sj1"></div>
															<div class="Details_span">${item.userNameCompanion }</div>
															<div class="Details_span"
																title="${item.highestRoleName }">
																职务：
																<ui:sout value="${item.highestRoleName }" length="9"
																	needEllipsis="true" />
															</div>
															<div class="Details_span" title="${item.profession }">
																职称：
																<ui:sout value="${item.profession }" length="9"
																	needEllipsis="true" />
															</div>
														</div>
													</dd>
													<dt>${item.userNameCompanion }
														<b class="remo"
															data-userIdCompanion="${item.userIdCompanion }"
															data-userNameCompanion="${item.userNameCompanion }"></b>
													</dt>
												</dl>
											</c:forEach></a></li>
								</ol>
						</span></li>
						<li><a class="header"> <span class="arrow up"></span> <span
								class="label">其他同伴<b>&nbsp;(${friendsOuterNum }个)&nbsp;</b></span>
						</a> <span class="no">
								<ol class="menu" style="display: none;">
									<li class="checkbox_1"><a class="header1"><c:forEach
												items="${friendsOuter}" var="item" varStatus="status">
												<dl
													class="recentlyCompanions <c:if test="${status.index==0 }">header1_dl_act</c:if>"
													data-userIdCompanion="${item.userIdCompanion }"
													data-userNameCompanion="${item.userNameCompanion}">
													<dd>
														<ui:photo src="${item.photo }"></ui:photo>
														<div class="header1_bg"></div>
														<div class="Details">
															<div class="left_sj1"></div>
															<div class="Details_span">${item.userNameCompanion }</div>
															<div class="Details_span"
																title="${item.highestRoleName }">
																职务：
																<ui:sout value="${item.highestRoleName }" length="9"
																	needEllipsis="true" />
															</div>
															<div class="Details_span" title="${item.profession }">
																职称：
																<ui:sout value="${item.profession }" length="9"
																	needEllipsis="true" />
															</div>
														</div>
													</dd>
													<dt>${item.userNameCompanion }
														<c:if test="${item.isFriend==1 }">
															<b class="remo"
																data-userIdCompanion="${item.userIdCompanion }"
																data-userNameCompanion="${item.userNameCompanion }"></b>
														</c:if>
													</dt>
												</dl>
											</c:forEach> </a></li>
								</ol>
						</span></li>
					</ol>
					<!-- 查询面板 -->
					<ol class="expmenu" style="display: none;" id="search_compane">
						<li class="expmenu_li"><a class="header1"><c:forEach
									items="${allCompanions}" var="item" varStatus="status">
									<dl
										class="recentlyCompanions searchItem <c:if test="${status.index==0 }">header1_dl_act</c:if>"
										data-userIdCompanion="${item.userIdCompanion }"
										data-userNameCompanion="${item.userNameCompanion}"
										style="display: none;">
										<dd>
											<ui:photo src="${item.photo }"></ui:photo>
											<div class="header1_bg"></div>
											<div class="Details">
												<div class="left_sj1"></div>
												<div class="Details_span">${item.userNameCompanion }</div>
												<div class="Details_span" title="${item.highestRoleName }">
													职务：
													<ui:sout value="${item.highestRoleName }" length="9"
														needEllipsis="true" />
												</div>
												<div class="Details_span" title="${item.profession }">
													职称：
													<ui:sout value="${item.profession }" length="9"
														needEllipsis="true" />
												</div>
											</div>
										</dd>
										<dt>${item.userNameCompanion }
											<c:if test="${item.isFriend==1 }">
												<b class="remo"
													data-userIdCompanion="${item.userIdCompanion }"
													data-userNameCompanion="${item.userNameCompanion }"></b>
											</c:if>
										</dt>
									</dl>
								</c:forEach></a></li>
					</ol>
					<div id="noneSearchResult"
						style="margin-left: 10px; display: none;">无查找结果…</div>
				</div>
			</div>
			<div class="all_message_cont_r">
				<div class='all_message_cont_r1'>
					<h3 id="messagesTitle"></h3>
					<div class="all_message_conter">
						<div class="all_message_c clearfix" id="content"></div>
					</div>
					<div class="all_message_b">
						<div class="all_message_b_l">
							<input type="text" style="height: 22px;" name="startTime"
								id="startTime"
								class="validate[required,custom[dateTimeFormat]] Wdate"
								value="<fmt:formatDate value="${act.startTime}" pattern="yyyy-MM-dd"/>"
								onclick="onSelectDate()" />
						</div>
						<div class="all_message_b_r">
							<div class="" id="first_page"></div>
							<div class="" id="previous_page"></div>
							<div class="" id="next_page"></div>
							<div class="" id="last_page"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<ui:htmlFooter style="1" needCompanionSide="false"></ui:htmlFooter>
	</div>
</body>
<script type="text/template" id="messageTemplate">
				{{#messages}}
					<div class="all_message_c">
					{{#currentUser}}
						<h4>
							{{userNameSender}}<span>{{senderTime}}</span>
						</h4>
					{{/currentUser}}
                    {{#notCurrentUser}}
						<h4>
							{{userNameSender}}<span>{{senderTime}}</span>
						</h4>
					{{/notCurrentUser}}
					<p>{{message}}</p>
					<div class="files_wrap1">
					{{#attachment1}}
						<div class='files_wrap'>
							<div class='files_wrap_left'></div>
							<div class='files_wrap_center' title="{{attachment1Name}}">
								<a href="jy/companion/attachments/{{attachment1}}" target="_blank">{{attachment1Name}}</a></div>
						</div>
					{{/attachment1}}
					{{#attachment2}}
						<div class='files_wrap'>
							<div class='files_wrap_left'></div>
							<div class='files_wrap_center' title="{{attachment2Name}}">
								<a href="jy/companion/attachments/{{attachment2}}" target="_blank">{{attachment2Name}}</a></div>
						</div>
					{{/attachment2}}
					{{#attachment3}}
						<div class='files_wrap'>
							<div class='files_wrap_left'></div>
							<div class='files_wrap_center' title="{{attachment3Name}}">
								<a href="jy/companion/attachments/{{attachment3}}" target="_blank">{{attachment3Name}}</a></div>
						</div>
					{{/attachment3}}
					</div>
					</div>
				{{/messages}}
				{{#noMessages}}
					<div class="empty_wrap">
						<div class="empty_img"></div>
						<div class="empty_info" style="text-align: center;">您还没有留言喔～</div>
					</div>
				{{/noMessages}}
	</script>
<script type="text/javascript"
	src="${ctxStatic }/lib/calendar/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/common/js/placeholder.js"></script>
<ui:require module="companion/js"></ui:require>
<script type="text/javascript">
	require([ 'companion_messages' ]);
</script>
</html>