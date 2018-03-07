<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script src="${ctxStatic }/lib/requirejs/esl.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<script src="${ctxStatic }/common/js/comm.js"></script>
<link rel="stylesheet" href="${ctxStatic }/common/css/reset.css"
	media="all">
<link rel="stylesheet" href="${ctxStatic }/common/css/index.css"
	media="screen">
<link rel="stylesheet"
	href="${ctxStatic}/modules/companion/css/companion.css" media="screen" />
</head>
<body>
	<div class="all_message_cont_l" style="height: 578px;">
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
								data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion}">
								<dd>
									<ui:photo src="${item.photo }"></ui:photo>
									<div class="header1_bg"></div>
									<div class="Details">
										<div class="left_sj1"></div>
										<div class="Details_span">${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion}</div>
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
										<c:if test="${item.isSameOrg==0 }">
											<div class="Details_span"
												title="${item.schoolNameCompanion }">
												<ui:sout value="${item.schoolNameCompanion }" length="12"
													needEllipsis="true" />
											</div>
										</c:if>
									</div>
								</dd>
								<dt>
									${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }
									<c:if test="${item.isFriend==1 }">
										<b class="remo"
											data-userIdCompanion="${item.userIdCompanion }"
											data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }"></b>
									</c:if>
									<c:if test="${item.isFriend!=1 }">
										<b class="add menu_add"
											data-userIdCompanion="${item.userIdCompanion }"
											data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }"></b>
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
											data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion}">
											<dd>
												<ui:photo src="${item.photo }"></ui:photo>
												<div class="header1_bg"></div>
												<div class="Details">
													<div class="left_sj1"></div>
													<div class="Details_span">${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion}</div>
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
													<c:if test="${item.isSameOrg==0 }">
														<div class="Details_span"
															title="${item.schoolNameCompanion }">
															<ui:sout value="${item.schoolNameCompanion }" length="12"
																needEllipsis="true" />
														</div>
													</c:if>
												</div>
											</dd>
											<dt>
												${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }
												<b class="remo"
													data-userIdCompanion="${item.userIdCompanion }"
													data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }"></b>
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
											data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion}">
											<dd>
												<ui:photo src="${item.photo }"></ui:photo>
												<div class="header1_bg"></div>
												<div class="Details">
													<div class="left_sj1"></div>
													<div class="Details_span">${item.userNicknameCompanion }</div>
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
													<c:if test="${item.isSameOrg==0 }">
														<div class="Details_span"
															title="${item.schoolNameCompanion }">
															<ui:sout value="${item.schoolNameCompanion }" length="12"
																needEllipsis="true" />
														</div>
													</c:if>
												</div>
											</dd>
											<dt>
												${item.userNicknameCompanion }
												<c:if test="${item.isFriend==1 }">
													<b class="remo"
														data-userIdCompanion="${item.userIdCompanion }"
														data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }"></b>
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
								data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }"
								style="display: none;">
								<dd>
									<ui:photo src="${item.photo }"></ui:photo>
									<div class="header1_bg"></div>
									<div class="Details">
										<div class="left_sj1"></div>
										<div class="Details_span">${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }</div>
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
										<c:if test="${item.isSameOrg==0 }">
											<div class="Details_span"
												title="${item.schoolNameCompanion }">
												<ui:sout value="${item.schoolNameCompanion }" length="12"
													needEllipsis="true" />
											</div>
										</c:if>
									</div>
								</dd>
								<dt>
									${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }
									<c:if test="${item.isFriend==1 }">
										<b class="remo"
											data-userIdCompanion="${item.userIdCompanion }"
											data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }"></b>
									</c:if>
									<c:if test="${item.isFriend!=1 }">
										<b class="add" data-userIdCompanion="${item.userIdCompanion }"
											data-userNameCompanion="${item.isSameOrg==1?item.userNameCompanion:item.userNicknameCompanion }"></b>
									</c:if>
								</dt>
							</dl>
						</c:forEach></a></li>
			</ol>
			<div id="noneSearchResult" style="margin-left: 10px; display: none;">无查找结果…</div>
			<ol class='all_message_ol' style="border-top: 1px #cdd6de solid;">
				<li><a href="./jy/companion/companions/index" target="blank">同伴互助</a></li>
				<li><a href="./jy/companion/companions/messages/index"
					target="blank">全部留言</a></li>
			</ol>
		</div>
	</div>
	<ui:require module="companion/js"></ui:require>
	<script type="text/javascript">
		require.config({
			'packages' : [ {
				'name' : 'common',
				'location' : '${ctx}${ctxStatic}/common/js',
				'main' : 'global'
			}, {
				'name' : 'hogan',
				'location' : '${ctx}${ctxStatic}/lib/hogan',
				'main' : 'hogan'
			}, {
				'name' : 'zxui',
				'location' : '${ctx}${ctxStatic}/lib/zxui',
				'main' : 'Control'
			}, {
				'name' : 'editor',
				'location' : '${ctx}${ctxStatic}/lib/kindeditor',
				'main' : 'editor'
			}, {
				'name' : 'jp',
				'location' : '${ctx}${ctxStatic}/lib/jquery'
			} ],
			shim : {
				'kindeditor' : {
					exports : 'KindEditor'
				},
				'kindeditorLang' : [ 'kindeditor' ]
			},
			paths : {
				'kindeditor' : '${ctx}${ctxStatic}/lib/kindeditor/kindeditor-min',
				'kindeditorLang' : '${ctx}${ctxStatic}/lib/kindeditor/zh_CN'
			}
		});
		var _WEB_CONTEXT_ = "${ctx}";
		require(['companion_side_content', 'companion_side' ]);
	</script>
</body>
</html>