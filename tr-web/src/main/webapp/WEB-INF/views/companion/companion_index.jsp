<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="同伴互助"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen" />
<link rel="stylesheet"
	href="${ctxStatic }/modules/companion/css/companion.css" media="screen" />
<style type="text/css">
.chosen-container-single .chosen-single {
	border: 1px #c1c1c1 solid;
}
</style>
</head>
<body>
	<div id="add_sava" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">提示信息</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="table_cont">
					<div class="info">恭喜您，关注成功！</div>
				</div>
			</div>
		</div>
	</div>
	<div id="cancel_attention" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">取消关注</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="table_cont">
					<div class="info1">你确定要取消关注“”吗?</div>
					<input type='button' value="确定" class="ascertain" />
				</div>
			</div>
		</div>
	</div>
	<div id="ser_senior" class="dialog">
		<div class="dialog_wrap">
			<div class="dialog_head">
				<span class="dialog_title">提示信息</span> <span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="sel_wrap">
					<form action="" id="someSearch">
						<div class="sel_wrap_ser">
							<label for="">姓名：</label> <input type="text"
								id="Advanced_search_userName" value="${vo.userName }" />
						</div>
						<c:if test="${!vo.isSameSchool }">
							<div class="sel_wrap_ser">
								<label for="">单位：</label> <input type="text"
									id="Advanced_search_schoolName" value="${vo.schoolName }" />
							</div>
						</c:if>
						<c:if test="${vo.isSameSchool }">
							<div class="sel_wrap_ser">
								<label for="">职务：</label>
								<div>
									<select id="Advanced_search_roleId"
										class="chosen-select-deselect"
										style="width: 182px; height: 30px;">
										<option value="">全部</option>
										<option value="1377" ${vo.roleId==1377?"selected":"" }>校长</option>
										<option value="2000" ${vo.roleId==2000?"selected":"" }>副校长</option>
										<option value="1376" ${vo.roleId==1376?"selected":"" }>主任</option>
										<option value="1375" ${vo.roleId==1375?"selected":"" }>学科组长</option>
										<option value="1374" ${vo.roleId==1374?"selected":"" }>年级组长</option>
										<option value="1373" ${vo.roleId==1373?"selected":"" }>备课组长</option>
										<option value="27" ${vo.roleId==27?"selected":"" }>教师</option>
									</select>
								</div>
							</div>
						</c:if>
						<ui:relation var="grades" type="xdToNj"
							id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
						<div class="sel_wrap_ser">
							<label for="">年级：</label>
							<div>
								<select class="chosen-select-deselect"
									id="Advanced_search_grade" style="width: 182px; height: 30px;">
									<option value="" selected="selected">全部</option>
									<c:forEach items="${grades}" var="grade">
										<option value="${grade.id }"
											${vo.gradeId==grade.id ? "selected" : "" }>${grade.name }</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<c:if test="${vo.isSameSchool }">
							<ui:relation var="subjects" type="xdToXk"
								id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
							<div class="sel_wrap_ser">
								<label for="">学科：</label>
								<div>
									<select class="chosen-select-deselect"
										id="Advanced_search_subject"
										style="width: 182px; height: 30px;">
										<option value="" selected="selected">全部</option>
										<c:forEach items="${subjects }" var="subject">
											<option value="${subject.id }"
												${subject.id eq vo.subjectId ? 'selected' :'' }>${subject.name }</option>
										</c:forEach>
									</select>
								</div>
							</div>
						</c:if>
						<div class="sel_wrap_ser">
							<label for="">职称：</label> <input type="text"
								id="Advanced_search_profession" value="${vo.profession }" />
						</div>
						<div class="sel_wrap_ser">
							<label for="">教龄：</label> <input type="text"
								id="Advanced_search_schooleAge" value="${vo.schoolAge }" />
						</div>
						<input type="button" value="查找" class='lookup' id="advanceSearch" />
					</form>
				</div>
			</div>
		</div>
	</div>
	<div id="wrap" class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop style="1" modelName="同伴互助"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="copanions">
					<jy:param name="name" value="${vo.isSameSchool?'校内':'校外' }同伴" />
					<jy:param name="isSameSchool" value="${vo.isSameSchool }" />
				</jy:nav>
			</h3>
		</div>
		<div class='fq_activity_cont'>
			<div class='peer_coaching_wrap'>
				<div class='t_r_l_c_cont_tab'>
					<div class="t_r_l_c_select">
						<ol>
							<li
								class="t_r_l_c_li <c:if test="${vo.isSameSchool }">t_r_l_c_li_act</c:if>"
								data-isSameSchool="true">校内同伴</li>
							<li
								class="t_r_l_c_li <c:if test="${!vo.isSameSchool }">t_r_l_c_li_act</c:if>"
								data-isSameSchool="false">校外同伴</li>
						</ol>
						<div class="t_r_l_c_right">
							<div class='search_criteria' style="width: 295px;">
								<div class='sel_ser' id="advance_search_btn">高级</div>
								<div class='search'>
									<form id="oneSearch" action="">
										<input type="text" class="search_txt" name="userNameCompanion"
											placeholder="输入教师姓名进行查找" value="${vo.userName }"
											id="search_userName" /> <input type='button'
											class='search_btn' id="simpleSearch" />
									</form>
								</div>
								<span class='whole'
									style="float: left; ${isSearchPage==0?'visibility:hidden':'' }"
									id="reset">全部</span>
							</div>
						</div>
					</div>
					<div class='t_r_l_c_cont clearfix'
						style="padding: 10px 0px 50px 0px;">
						<div class='t_r_l_c_cont_tab'>
							<div class="unit_companion">
								<c:if test="${vo.isSameSchool }"> 您有<span>${page.totalCount }</span>位校内同伴，可以关注他们哦</c:if>
								<c:if test="${!vo.isSameSchool }"> 您有<span>${page.totalCount }</span>位校外同伴，可以关注他们哦</c:if>
							</div>
							<c:if test="${ empty page.datalist }">
								<div class="empty_wrap">
									<div class="empty_img"></div>
									<div class="empty_info" style="text-align: center;">当前列表为空，请稍后再来！</div>
								</div>
							</c:if>
							<c:if test="${not empty page.datalist }">
								<c:forEach items="${page.datalist }" var="item">
									<div class="companion_dl">
										<dl>
											<dd>
												<a href="./jy/companion/companions/${item.userIdCompanion }"
													target="_blank"> <ui:photo src="${item.photo }"
														width="80" height="80" />
													<div class='head_img_bg' data-id="${item.userIdCompanion }"></div>
													<div class="head_img_hover">
														<div class="left_sj"></div>
														<div class="companion_name">${vo.isSameSchool ? item.userNameCompanion : item.userNicknameCompanion }</div>
														<c:if test="${not empty  item.highestSubjectName}">
															<div class="companion_name"
																title="${item.highestSubjectName }">学科：${item.highestSubjectName }</div>
														</c:if>
														<c:if test="${not empty  item.highestFormatName}">
															<div class="companion_name"
																title="${item.highestFormatName }">教材：${item.highestFormatName }
															</div>
														</c:if>
														<c:if test="${not empty  item.highestGradeName}">
															<div class="companion_name"
																title="${item.highestGradeName }">年级：${item.highestGradeName }
															</div>
														</c:if>
														<div class="companion_name"
															title="${item.highestRoleName }">职务：${item.highestRoleName }</div>
														<c:if test="${not empty item.profession }">
															<div class="companion_name" title="${item.profession }">职称：${item.profession }</div>
														</c:if>
														<c:if test="${!vo.isSameSchool }">
															<div class="companion_name"
																title="${item.schoolNameCompanion }">${item.schoolNameCompanion }</div>
														</c:if>
													</div></a>
											</dd>
											<dt title="${vo.isSameSchool ? item.userNameCompanion : item.userNicknameCompanion }">${vo.isSameSchool ? item.userNameCompanion : item.userNicknameCompanion }</dt>
										</dl>
										<c:if test="${item.isFriend==1 }">
											<input type="button" class="ygz btn1"
												data-userIdCompanion="${item.userIdCompanion }"
												data-userNameCompanion="${vo.isSameSchool ? item.userNameCompanion : item.userNicknameCompanion }" />
										</c:if>
										<c:if test="${item.isFriend==0 }">
											<input type="button" class="add_gz btn2 addFriend"
												data-userIdCompanion="${item.userIdCompanion }"
												title="点击“+关注”按钮后，您就可以关注此好友哟！" />
										</c:if>
									</div>
								</c:forEach>
							</c:if>
						</div>
					</div>
					<div class="page"
						${voList.totalCount eq 0 ? 'style="display:none;"' : '' }>
						<div class="page_wrap">
							<form id="pageForm" name="pageForm" method="post"
								action="./jy/companion/companions/index">
								<ui:page url="./jy/companion/companions/index" data="${page}" />
								<input type="hidden" value="${vo.currentPage}"
									name="currentPage" class="currentPage" id="currentPage">
								<input type="hidden" value="${vo.pageSize}" name="pageSize"
									class="pageSize" id="page.pageSize"> <input
									type="hidden" value="12" name="pageSize"> <input
									type="hidden" value="${vo.isSameSchool}" name="isSameSchool"
									id="form_isSameSchool"> <input type="hidden"
									value="${vo.roleId}" name="roleId" id="form_roleId"> <input
									type="hidden" value="${vo.userName}" name="userName"
									id="form_userName"> <input type="hidden"
									value="${vo.subjectId}" name="subjectId" id="form_subjectId">
								<input type="hidden" value="${vo.gradeId}" name="gradeId"
									id="form_gradeId"> <input type="hidden"
									value="${vo.profession}" name="profession" id="form_profession">
								<input type="hidden" value="${vo.schoolAge}" name="schoolAge"
									id="form_schoolAge"> <input type="hidden"
									value="${vo.schoolName}" name="schoolName" id="form_schoolName">
								<input type="hidden" value="${isSearchPage}" name="isSearchPage"
									id="form_isSearchPage">
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<ui:htmlFooter style="1" needCompanionSide="false"></ui:htmlFooter>
	</div>
</body>
<script type="text/javascript"
	src="${ctxStatic }/common/js/placeholder.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script>
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<ui:require module="companion/js"></ui:require>
<script type="text/javascript">
	require([ 'companion' ]);
</script>
</html>
