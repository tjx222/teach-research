<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="同伴互助用户留言详情"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic}/modules/companion/css/companion.css" media="screen"></link>
<style type="text/css">
#uploadFile,#uploadFileFace input {
	width: 151px;
	height: 23px;
	border: 1px #c1c1c1 solid;
	float: left;
	margin-right: 10px;
	padding-left: 5px;
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
	<div id="wrap" class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop style="1" modelName="同伴互助用户留言详情"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="copanions_detail">
					<jy:param name="userIdCompanion" value="${vo.userIdCompanion }"></jy:param>
					<jy:param name="name" value="同伴互助" />
					<jy:param name="isSameSchool" value="${vo.isSameOrg }" />
					<jy:param name="detailName" value="${vo.isSameOrg==1?'校内':'校外' }同伴" />
				</jy:nav>
			</h3>
		</div>
		<div class='fq_activity_cont'>
			<div class="department_detail_wrap">
				<div class="detail_wrap_top">
					<div class="detail_wrap_top_l">
						<ui:photo src="${vo.photo }" width="58" height="58" />
						<div class="detail_head_bg"></div>
						<c:if test="${vo.isFriend==1 }">
							<input type="button" class="ygz detail_btn"
								data-userIdCompanion="${vo.userIdCompanion }"
								data-userNameCompanion="${vo.isSameOrg eq 1 ? vo.userNameCompanion : vo.userNicknameCompanion }" />
						</c:if>
						<c:if test="${vo.isFriend==0 }">
							<input type="button" class="add_gz detail_btn addFriend"
								data-userIdCompanion="${vo.userIdCompanion }" />
						</c:if>
					</div>
					<div class="detail_wrap_top_r">
						<div class="detail_name">
							<c:if test="${vo.isSameOrg==1 }">
								${vo.userNameCompanion }<span>(${vo.sex==0?"男":"女" })</span>
							</c:if>
							<c:if test="${vo.isSameOrg==0 }">
								${vo.userNicknameCompanion }<span>(${vo.sex==0?"男":"女" })</span>
							</c:if>
						</div>
						<c:if test="${not empty vo.profession || not empty vo.schoolAge}">
							<div class="detail_info">
								<c:if test="${not empty vo.profession }">
									<span>职称：${vo.profession }</span>
								</c:if>
								<c:if test="${not empty vo.schoolAge }">
									<span>教龄：${vo.schoolAge }年</span>
								</c:if>
							</div>
						</c:if>
						<c:if
							test="${not empty vo.subjectNames || not empty vo.formatNames || not empty vo.gradeNames}">
							<div class="detail_info">
								<c:if test="${not empty vo.subjectNames }">
									<span>学科：${vo.subjectNames }</span>
								</c:if>
								<c:if test="${not empty vo.formatNames }">
									<span>版本：${vo.formatNames}</span>
								</c:if>
								<c:if test="${not empty vo.gradeNames }">
									<span>年级：${vo.gradeNames }</span>
								</c:if>
							</div>
						</c:if>
						<div class="detail_info">职务：${vo.roleNames }</div>
						<c:if test="${vo.isSameOrg==0 }">
							<div class="detail_info">学校名称：${vo.schoolNameCompanion }</div>
						</c:if>
					</div>
					<div class="clear"></div>
				</div>
				<div class="detail_wrap_center">
					<form id="messageForm" method="post">
						<input type="hidden" name="userIdReceiver"
							value="${vo.userIdCompanion }">
						<h3>
							<span></span> 和他聊一聊：<b
								style="float: right; font-size: 16px; color: #a9a9a9; font-weight: normal;"><b
								id="w_count">0</b>/500</b>
						</h3>
						<span id="messageInput_count" style="display: none;"></span>
						<div class="detail_wrap_center_cont">
							<textarea name="message" id="messageInput"></textarea>
							<div class="detail_wrap_center_cont_b">
								<div class="detail_w_c_b_l" style="width: 328px;"
									id="uploadUiId">
									<ui:upload name="attachment" originFileName="uploadFile"
										fileType="doc,docx,xls,xlsx,ppt,pptx,pdf,txt,zip,rar,jpg,jpeg,gif,png,mp3,mp4,wma,rm,rmvb,flV,swf,avi"
										fileSize="50"
										relativePath="companion/o_${_CURRENT_USER_.orgId }/u_${_CURRENT_USER_.id }"
										startElementId="btn" beforeupload="beforeupload"
										containerID="uploadUiId" isWebRes="false"
										callback="savePsCallBack" />
									<input type="button" value="上传文件" class="file_btn" id="btn" />
								</div>
								<div class="detail_w_c_b_r" id="attachViews"></div>
							</div>
						</div>
						<div class="clear"></div>
						<input type="button" class="submit_disscuss" value='说完了' id="confirm" />
					</form>
				</div>
				<iframe src="./jy/companion/messages/${vo.userIdCompanion }/page"
					onload="setCwinHeight(this,false,100)" scrolling="no"
					frameborder="0" style="width: 100%;" id="frameDetail"> </iframe>
			</div>
		</div>
		<ui:htmlFooter style="1" needCompanionSide="false"></ui:htmlFooter>
	</div>
</body>
<ui:require module="companion/js"></ui:require>
<script type="text/javascript">
	require([ 'companion_detail' ]);
</script>
</html>