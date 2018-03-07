<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="成长档案袋"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/modules/record/css/recordCss.css" media="screen">
<style type="text/css">
.td1 {
	width: 220px;
}
</style>
<ui:require module="record/js"></ui:require>
<script type="text/javascript">
	var we = '${ctx}';
	var id = '${id}';
	var type = '${type}';
	var page = '${page.currentPage}';
</script>
</head>
<div id="_jx"  class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">精选</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<form id="formular">
				<div class="Growth_Portfolio_bottom">
					<input type="hidden" id="one">
					<p>
						<label for="">名称：</label> <span id="name"></span><strong></strong>
					</p>
					<div class="clear"></div>
					<p>
						<label for="" id="lab">微评：</label>
						<textarea style="float: left;padding: 5px;font-size: 12px;" name="desc" id="desc" cols="38" rows="4" placeholder="您可以给精选文件添加微评，如不想添加，请直接点击“保存”" 
							class="validate[optional,maxSize[50]] text-input"></textarea>
						<strong class="strong_sty" >注：最多可输入50个字</strong>
					</p>
					<div class="clear"></div>
					<p style="margin-top: 20px;text-align:center;">
						<input type="button" id="button" class="Growth_Portfolio_btn btn_bottom_1" value="保存">
					</p>
				</div>
			</form>
		</div>
	</div>
</div>
<body>
	<div class="wrapper">
		<div class="jyyl_top">
			<ui:tchTop style="1" modelName="成长档案袋" ></ui:tchTop>
		</div> 
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="czdad"></jy:nav>
				&nbsp;&gt;&nbsp;<a href="${ctx }jy/record/sysList?id=${id}&type=${type}">${name1 }</a>&nbsp;&gt;&nbsp;精选
			</h3>
		</div>
		<div class="clear"></div>
		<div class="jx_list_content">
			<div class="Growth_Portfolio_jx">
				<h3 class="Growth_Portfolio_content1_h3">${name1 }</h3>
				<form id="searchForm" name="searchForm" method="post">
					<p>
						<input type="text" name="planName" id="planName" value="${name}" placeholder="输入课题进行查找"class="txt_seh">
						<input type="hidden" name="id" value="${id}">
						<input type="hidden" name="type"value="${type}"> 
						<input type="button" class="txt_btn">
					</p>
				</form>
				<h4 id="all" style="cursor: pointer;">
					全部<span> < </span>
				</h4>
			</div>
			<div class="Growth_Portfolio_tab">
				<div class="Growth_Portfolio_table">
					<c:if test="${not empty data.datalist }">
						<table>
							<tr>
								<th style="width: 350px;">课题</th>
								<th style="width: 100px;">年级学科</th>
								<th style="width: 100px;">授课人</th>
								<th style="width: 100px;">听课节数</th>
								<th style="width: 100px;">听课时间</th>
								<th style="width: 60px;">操作</th>
							</tr>
							<c:forEach items="${data.datalist  }" var="record">
								<!-- 有精选记录 -->
								<tr>
									<td style="text-align: left;" id="${record.resId }" class="td1"
										resId="${record.path }">${record.flago }<span
										style="cursor: pointer;color:#2890fb;"><ui:sout
												value="${record.recordName }" length="23" needEllipsis="true"></ui:sout></span></td>
									<td>${record.ext.grade }</td>
									<td>${record.ext.teachPeople }</td>
									<td>${record.ext.num }</td>
									<td>${record.time }</td>
									<td>
										<c:choose>
											<c:when test="${record.status==0 }"> 
												<b style="color:#FF8400;cursor: pointer;" id="${record.resId }" class="img1" name="${record.flago }${record.recordName }">精选</b>
											</c:when>
											<c:otherwise> 
												<b style="color:#FF8400;cursor: pointer;"  class="img2">已精选</b>
											</c:otherwise>
										</c:choose></td>
								</tr>
							</c:forEach>
						</table>
					</c:if>
					<c:if test="${empty data.datalist&&empty name }">
						<div class="empty_wrap"> 
							<div class="empty_info">
								您还没有可精选的听课记录，赶紧去“<a href="./jy/lecturerecords/list" target="_blank"  style="color:#014efd;">撰写听课记录</a>”吧！
							</div>
						</div>
					</c:if>
					<c:if test="${empty data.datalist&&not empty name }">
						<div class="empty_wrap"> 
							<div class="empty_info">
								未找到可精选的听课记录！
							</div>
						</div>
					</c:if>
				</div>
				<div class="pages">
					<form name="pageForm" method="post">
						<ui:page url="${ctx}jy/record/findSysList?id=${id}&type=${type}"
							data="${data }" />
						<input type="hidden" class="currentPage" name="page.currentPage">
						<input type="hidden" name="id" value="${id}"> <input
							type="hidden" name="type" value="${type}"> <input
							type="hidden" name="planName" value="${word}">
					</form>
				</div>
			</div>
		</div>
		
		<div class="clear"></div>
		<ui:htmlFooter></ui:htmlFooter>
	</div>
	<script type="text/javascript">
		require([ 'jquery', 'jp/jquery-ui.min', 'jp/jquery.blockui.min',
				'jp/jquery.form.min', 'jp/jquery.validationEngine-zh_CN',
				'jp/jquery.validationEngine.min', 'jx', 'common/comm' ],
				function() {
				});
	</script>

</body>
</html>