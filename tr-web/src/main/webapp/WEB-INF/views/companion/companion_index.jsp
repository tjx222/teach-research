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
<div class="advance_option_wrap">
	<div class="advance_option_wrap1"></div>
	<div class="advance_option_con" ${vo.isSameSchool ? "style='height: 40rem;'" :"style='height: 32rem;'" } >
		<div class="advance_option_title">
			<h3>高级搜索</h3>
			<span class="close"></span>
		</div> 
		<div class="advance_option_content">
			<form>
				<div class="form_input">
					<label>姓名</label>
					<input type="text" class="name_txt" id="Advanced_search_userName" value="${vo.userName }">
				</div>
				<c:if test="${!vo.isSameSchool }">
					<div class="form_input">
						<label>学校</label>
						<input type="text" class="name_txt" id="Advanced_search_schoolName" value="${vo.schoolName }">
					</div>
				</c:if>
				<c:if test="${vo.isSameSchool }">
					<div class="form_input">
						<label>职务</label>
						<strong class="select"><span id="role_span">请选择</span><q></q></strong>
						<input type="hidden" id="Advanced_search_roleId" value="${vo.roleId }"/>
						<div class="menu_list" >
							<span class="menu_list_top"></span>
							<div id="select_list" class="menu_list_wrap1">
								<div id="scroller">
									<p roleId="">全部</p>
									<p roleId="1377" ${vo.roleId=="1377" ? "class='act'" : "" }>校长</p>
									<p roleId="2000" ${vo.roleId=="2000" ? "class='act'" : "" }>副校长</p>
									<p roleId="1376" ${vo.roleId=="1376" ? "class='act'" : "" }>主任</p>
									<p roleId="1375" ${vo.roleId=="1375" ? "class='act'" : "" }>学科组长</p>
									<p roleId="1374" ${vo.roleId=="1374" ? "class='act'" : "" }>年级组长</p>
									<p roleId="1373" ${vo.roleId=="1373" ? "class='act'" : "" }>备课组长</p>
									<p roleId="27" ${vo.roleId=="27" ? "class='act'" : "" }>教师</p>
								</div>
							</div>
						</div>
					</div>
					<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
					<div class="form_input">
						<label>年级</label>
						<strong class="select1"><span id="grade_span">请选择</span><q></q></strong>
						<input type="hidden" id="Advanced_search_grade" value="${vo.gradeId }"/>
						<div class="menu_list1" >
							<span class="menu_list_top"></span>
							<div id="select_list1" class="menu_list_wrap1">
								<div id="scroller">
									<p gradeId="">全部</p>
									<c:forEach items="${grades }" var="s" varStatus="st">
										<p gradeId="${s.id }" ${vo.gradeId==s.id ? "class='act'" : "" }>${s.name }</p>
									</c:forEach>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${_CURRENT_SPACE_.sysRoleId!=271 }">
						<ui:relation var="subjects" type="xdToXk" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
						<div class="form_input">
							<label>学科</label>
							<strong class="select2"><span id="subject_span">请选择</span><q></q></strong>
							<input type="hidden" id="Advanced_search_subject" value="${vo.subjectId }"/>
							<div class="menu_list2" >
								<span class="menu_list_top"></span>
								<div id="select_list2" class="menu_list_wrap1">
									<div id="scroller">
										<p subjectId="">全部</p>
										<c:forEach items="${subjects }" var="s" varStatus="st">
											<p subjectId="${s.id }" ${vo.subjectId==s.id ? "class='act'" : "" }>${s.name }</p>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>	
					</c:if>
				</c:if>
				<div class="form_input">
					<label>职称</label>
					<input type="text" class="name_txt" id="Advanced_search_profession" value="${vo.profession }">
				</div>
				<div class="form_input">
					<label>教龄</label>
					<input type="text" class="name_txt" id="Advanced_search_schooleAge" value="${vo.schoolAge }">
				</div>
				<input type="button" class="btn_ser" value="查找">
			</form>
		</div>
	</div>
	
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-${empty param._HS_ ? 1 : param._HS_});"></span>同伴互助
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="companion_content">
			<div class="companion_content_l">
				<dl class="com_img">
					<dd class="img_1"></dd>
					<dt>同伴在线</dt>
				</dl>
				<dl>
					<dd class="img_2"></dd>
					<dt>我的消息</dt>
				</dl>
				<dl>
					<dd class="img_3"></dd>
					<dt>我的关注</dt>
				</dl>
			</div>
			<div class="companion_content_r">
				<header>
					<ul>
						<li data-isSameSchool="true"><a <c:if test="${vo.isSameSchool }">class="com_header_act"</c:if>>校内同伴</a></li>
						<li data-isSameSchool="false"><a <c:if test="${!vo.isSameSchool }">class="com_header_act"</c:if>>校外同伴</a></li>
					</ul>
				</header>
				<div class="com_option">
					<div class="com_option_l">
						<c:if test="${vo.isSameSchool }"> 您有<span>${page.totalCount }</span>位校内同伴，可以添加他们为好友哦！</c:if>
						<c:if test="${!vo.isSameSchool }"> 您有<span>${page.totalCount }</span>位校外同伴，可以添加他们为好友哦！</c:if>
					</div>
					<div class="com_option_r">
						<c:if test="${!empty vo.userName || !empty vo.roleId || !empty vo.gradeId ||
						!empty vo.subjectId || !empty vo.profession || !empty vo.schoolAge || !empty vo.schoolName}">
							<span class="show_all">显示全部</span>
						</c:if>
						<div class="serch">
							<input type="search" class="search"  placeholder="输入教师姓名进行查找" value="${vo.userName}">
							<input type="button" class="search_btn">
						</div>
						<div class="advance_option">高级</div>
					</div>
				</div> 
				<div class="clear"></div> 
				<div class="companion_strength" id="companion_strength">
					<div id="scroller">
						<c:if test="${empty  page.datalist }">
							<div class="content_k" style="margin-top: 5rem;">
								<dl>
									<dd></dd>
									<dt>很抱歉，没有找到此同伴，请重新查找！</dt>
								</dl>
							</div>
						</c:if>
						<c:if test="${not empty  page.datalist }">
						<div id="addmoredatas">
						<c:forEach items="${page.datalist }" var="item">
							<div class="com_str">
								<div class="companions_img" companionid="${item.userIdCompanion }">
									<ui:photo src="${item.photo }" width="96" height="96"></ui:photo>
								</div>
								<span>
									<c:if test="${vo.isSameSchool }">
										${item.userNameCompanion }
									</c:if>
									<c:if test="${!vo.isSameSchool}">
										${item.userNicknameCompanion } 
									</c:if>
								</span>
								<c:if test="${item.isFriend==1 }">
									<div class="btn_ygz"></div>
								</c:if>
								<c:if test="${item.isFriend==0 }">
									<div class="btn_gz"  data-userIdCompanion="${item.userIdCompanion }"></div>
								</c:if>
							</div>
						</c:forEach>
						</div>
						</c:if>
						<form id="pageForm" name="pageForm" method="post" action="${ctx}/jy/companion/companions/index?_HS_=${empty param._HS_ ? 2: param._HS_+1}">
							<ui:page url="${ctx}/jy/companion/companions/index?_HS_=${empty param._HS_ ? 2: param._HS_+1}" data="${page}" dataType="true" callback="addmoredatas"/>
							<input type="hidden" value="${vo.currentPage}" name="currentPage" class="currentPage" id="currentPage">
							<input type="hidden" value="${vo.isSameSchool}" name="isSameSchool" id="form_isSameSchool"> 
							<input type="hidden" value="${vo.roleId}" name="roleId" id="form_roleId"> 
							<input type="hidden" value="${vo.userName}" name="userName" id="form_userName">
						    <input type="hidden" value="${vo.subjectId}" name="subjectId" id="form_subjectId">
							<input type="hidden" value="${vo.gradeId}" name="gradeId" id="form_gradeId"> 
							<input type="hidden" value="${vo.profession}" name="profession" id="form_profession">
							<input type="hidden" value="${vo.schoolAge}" name="schoolAge" id="form_schoolAge">
							<input type="hidden" value="${vo.schoolName}" name="schoolName" id="form_schoolName">
							<input type="hidden" value="${isSearchPage}" name="isSearchPage" id="form_isSearchPage">
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto","js"],function(){	
	});
</script>
</html>