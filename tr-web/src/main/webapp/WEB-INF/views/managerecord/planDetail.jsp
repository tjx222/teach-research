<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<ui:htmlHeader title="计划总结"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/managerecord/css/check_detail.css" media="screen">
	<style type="text/css">
	.Pre_cont_right_1_dl{
		width:1170px;
		height:auto;
		min-height:500px;
		margin:0 auto;
	}
	.record_reco_cont{
		width:582px;
		float:left;
		min-height:490px;
		height:auto;
		margin-bottom:0;
	}
	.record_reco_3{
		width:572px;
		height:30px;
		line-height:30px;
		font-size:16px;
		font-weight:bold;
		border:0;
		border-bottom:1px #51c7f8 dashed;
		padding-left:10px;
	}
	.record_dl{
		margin:6px;
	}
	</style>
</head>
<body>
	<div class="wrapper"> 
		<div class='jyyl_top'>
		<ui:tchTop style="1" modelName="计划总结"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			当前位置：<jy:nav id="jxgljl_jhzj"></jy:nav>
		</div>
		<div class="clear"></div>
		<div class="record_reco">
				<h3>
					<ul id="UL">
						<c:if test='${empty listType || listType==0 }'>
							<li class="record_act1" value="0" onclick="toCheckInfo(this.value);">撰写（${count}）</li>
							<li class="record_act" value="1" onclick="toCheckInfo(this.value);">分享（${shareCount }）</li>
						</c:if>
						<c:if test='${listType==1 }'>
							<li class="record_act" value="0" onclick="toCheckInfo(this.value);">撰写（${count}）</li>
							<li class="record_act1" value="1" onclick="toCheckInfo(this.value);">分享（${shareCount }）</li>
						</c:if>
					</ul>
					<label style="float: right;margin-right:10px;margin-top:15px;"> 
						学期：
						<input name="term"  style="vertical-align:middle;margin-top:-3px;" type="radio" value="0"  <c:if test="${term==0}">checked="checked" </c:if> onclick="toCheckInfo1(this);"  >
					    上学期
					    <input name="term"  style="vertical-align: middle;margin-top:-3px;" type="radio" value="1"  <c:if test="${term==1}">checked="checked" </c:if> onclick="toCheckInfo1(this);"  >
					    下学期
					</label>
				</h3>
				<div class="clear"></div>
				<div class="Pre_cont_right_1_dl">
					<div class="record_reco_cont"  style="border-right:1px #51c7f8 dashed;">
						<div class="record_reco_3">计划（${listCount }）</div>
						<div class="clear"></div>
						<div style="width:572px;height:480px;overflow:auto;">
							<c:forEach var="ps" items="${list }">
								<div class="record_dl" onclick="viewCheckInfo('${ps.id}');">
								<dl class="fileIcon" data-id="${ps.id }">
									<dd>
										<ui:icon ext="${ps.contentFileType }"></ui:icon>
									</dd>
									<dt>
										
										<span>
											<a href="javascript:void(0);" title='<ui:sout value="${ps.title}" escapeXml="true"/>'>
												<ui:sout value="${ps.title}" escapeXml="true" length="20" needEllipsis="true"/>
											 </a>
											 <br>
											 <fmt:formatDate value="${ps.lastupDttm}" pattern="yyyy-MM-dd"/>
										</span>
									</dt>
								</dl>
									<c:if test='${empty listType || listType==0 }'>
											<c:if test="${ps.isCheck!=0 }">
												<strong class="trans"></strong>
											</c:if>
								   </c:if>
								   		<c:if test='${ listType==1 &&ps.isReview!=0}'>
										      <strong class="trans1"><b>${ ps.reviewNum}</b></strong>
										</c:if>
								</div>
							</c:forEach>
						</div>
					</div>
					<div class="record_reco_cont" >
						<div class="record_reco_3">总结（${zCount }）</div>
						<div class="clear"></div>
						<div style="width:465px;height:480px;overflow:auto;">
							<c:forEach var="ps" items="${zlist }">
								<div class="record_dl" onclick="viewCheckInfo('${ps.id}');">
										<dl class="fileIcon" data-id="${ps.id }">
											<dd>
												<ui:icon ext="${ps.contentFileType }"></ui:icon>
											</dd>
											<dt>
												 <span>
													<a href="javascript:void(0);" title='<ui:sout value="${ps.title}" escapeXml="true"/>'>
														<ui:sout value="${ps.title}" escapeXml="true" length="20" needEllipsis="true"/>
													 </a>
													 <br>
													 <fmt:formatDate value="${ps.lastupDttm}" pattern="yyyy-MM-dd"/>
												</span>
											</dt>
										</dl>
										<c:if test='${empty listType || listType==0 }'>
											<c:if test="${ps.isCheck!=0 }">
												<strong class="trans"></strong>
											</c:if>
										</c:if>
										<c:if test='${ listType==1 &&ps.isReview!=0}'>
											<strong class="trans1"><b>${ ps.reviewNum}</b></strong>
										</c:if>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		<div class="clear"></div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<script type="text/javascript">
var currentTerm = '${term}';
var listType = '${listType}';
function toCheckInfo(listType){
	location.href = _WEB_CONTEXT_ + "/jy/managerecord/planDetail?listType="+listType+"&term="+currentTerm
}
function toCheckInfo1(obj){
	location.href = _WEB_CONTEXT_ + "/jy/managerecord/planDetail?listType="+listType+"&term="+$(obj).val();
}
function viewCheckInfo(planInfoId){
	window
	.open(_WEB_CONTEXT_ + '/jy/planSummary/'
			+ planInfoId
			+ '/viewFile', '');
}
</script>
</html>