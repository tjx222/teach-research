<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="教学管理情况一览"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="教学管理情况一览"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_jxgl">
		</jy:nav>
	</div>
	<input type="hidden" id="termId" value="${searchVo.termId}">
	<input type="hidden" id="orgId" value="${searchVo.orgId}">
	<input type="hidden" id="userId" value="${searchVo.userId}">
	<div class="teachingManagement_content">
		<div class="teachingManagement_top">
			<p class="teachingManagement_title">教学管理情况一览</p>
			<ul class="teachingManagement_semester">
				<li>
					<input type="radio" name="radio" ${searchVo.termId==0?'checked':'' } id="radio_shang"/>
					<label for="radio_shang">上学期</label>
				</li>
				<li>
					<input type="radio" name="radio" ${searchVo.termId==1?'checked':'' } id="radio_xia"/>
					<label for="radio_xia">下学期</label>
				</li>
				<li>
					<input type="button" name="radio" id="exportExcelData" value="导出"/>
				</li>
			</ul>
		</div>
		<table cellpadding="0" cellspacing="0" class="teachingManagement_table" id="load_tr_table">
			<tr class="load_tr">
				<td rowspan="2" style="cursor:pointer;width:90px;">
					<b>
						姓名
						<span data-id="userName" data-value="1" ></span>
						<span data-id="userName" data-value="2" ></span>
					</b>
				</td>
				<td rowspan="2" class="sort_click" style="cursor:pointer;width:76px;text-align:left;padding-left:5px;"">
					<b>
						查阅教案
						<span class="up sort_" data-id="jiaoan_read" data-value="1" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
						<span class="down sort_" data-id="jiaoan_read" data-value="2" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
					</b>
					<span class="num_tip">（查阅数）</span>
				</td>
				<td rowspan="2" class="sort_click" style="cursor:pointer;width:76px;text-align:left;padding-left:5px;"">
					<b>
						查阅课件
						<span class="up sort_" data-id="kejian_read" data-value="1" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
						<span class="down sort_" data-id="kejian_read" data-value="2" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
					</b>
					<span class="num_tip">（查阅数）</span>
				</td>
				<td rowspan="2" class="sort_click" style="cursor:pointer;width:76px;text-align:left;padding-left:5px;">
					<b>
						查阅反思
						<span class="up sort_" data-id="fansi_read" data-value="1" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
						<span class="down sort_" data-id="fansi_read" data-value="2" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
					</b>
					<span class="num_tip">（查阅数）</span>
				</td>
				<td rowspan="2" class="sort_click" style="cursor:pointer;width:104px;text-align:left;padding-left:5px;">
					<b>
						查阅计划总结
						<span class="up sort_" data-id="plan_summary_read" data-value="1" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
						<span class="down sort_" data-id="plan_summary_read" data-value="2" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
					</b>
					<span class="num_tip">（查阅数）</span>
				</td>
				<td rowspan="2" class="sort_click" style="cursor:pointer;width:104px;text-align:left;padding-left:5px;">
					<b>
						查阅教学文章
						<span class="up sort_" data-id="thesis_read" data-value="1" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
						<span class="down sort_" data-id="thesis_read" data-value="2" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
					</b>
					<span class="num_tip">（查阅数）</span>
				</td>
				<td rowspan="2" class="sort_click" style="cursor:pointer;width:104px;text-align:left;padding-left:5px;">
					<b>
						查阅听课记录
						<span class="up sort_" data-id="lecture_read" data-value="1" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
						<span class="down sort_" data-id="lecture_read" data-value="2" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
					</b>
					<span class="num_tip">（查阅数）</span>
				</td>
				<td colspan="2" style="width:180px;" class="remove_td">
					集体备课
				</td>
			</tr>
			<tr class="load_tr2">
				<td class="sort_click" style="cursor:pointer;width:80px;">
					<b>
						查阅数
						<span class="up sort_" data-id="activity_read" data-value="1" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
						<span class="down sort_" data-id="activity_read" data-value="2" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
					</b>
				</td>
				<td class="sort_click" style="cursor:pointer;width:80px;">
					<b>
						发起数
						<span class="up sort_" data-id="activity_origination" data-value="1" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
						<span class="down sort_" data-id="activity_origination" data-value="2" data-phaseid="${_CURRENT_SPACE_.phaseId}"></span>
					</b>
				</td>
			</tr>
		</table>
		<div class="teachingManagement_con">
			<table cellpadding="0" cellspacing="0" class="teachingManagement_table2" id="exportTable">
				<c:if test="${dataSize != 0}">
					<c:forEach  items="${dataList}" var="data">
						<tr class="change_bg">
							<td style="width:88px;">
								<a href="${ctx}jy/teachingView/manager/m_details?userId=${data.userId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">
									${data.userName}
								</a>
							</td>
							<td style="width:79px;">
								<a href="${ctx}jy/teachingView/manager/m_lesson?userId=${data.userId}&flago=0&phaseId=${data.searchVo.phaseId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">
									<c:set var="cyja" value="${cyja+data.jiaoan_read}" ></c:set>
									${data.jiaoan_read}
								</a>
							</td>
							<td style="width:79px;">
								<a href="${ctx}jy/teachingView/manager/m_lesson?userId=${data.userId}&flago=1&phaseId=${data.searchVo.phaseId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">
									<c:set var="cykj" value="${cykj+data.kejian_read}" ></c:set>
									${data.kejian_read}
								</a>
							</td>
							<td style="width:79px;">
								<a href="${ctx}jy/teachingView/manager/m_lesson_fansi?userId=${data.userId}&phaseId=${data.searchVo.phaseId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">
									<c:set var="cyfs" value="${cyfs+data.fansi_read}" ></c:set>
									${data.fansi_read}
								</a>
							</td>
							<td style="width:107px;">
								<a href="${ctx}jy/teachingView/manager/m_checkPlanSummary?userId=${data.userId}&phaseId=${data.searchVo.phaseId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">
									<c:set var="cyjhzj" value="${cyjhzj+data.plan_summary_read}" ></c:set>
									${data.plan_summary_read}
								</a>
							</td>
							<td style="width:107px;">
								<a href="${ctx}jy/teachingView/manager/m_checkThesis?userId=${data.userId}&phaseId=${data.searchVo.phaseId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">
									<c:set var="cyjxwz" value="${cyjxwz+data.thesis_read}" ></c:set>
									${data.thesis_read}
								</a>
							</td>
							<td style="width:107px;">
								<a href="${ctx}jy/teachingView/manager/m_check_lecture?userId=${data.userId}&phaseId=${data.searchVo.phaseId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">
									<c:set var="cytkjl" value="${cytkjl+data.lecture_read}" ></c:set>
									${data.lecture_read}
								</a>
							</td>
							<td style="width:87px;">
								<a href="${ctx}jy/teachingView/manager/m_check_jitibeike?userId=${data.userId}&phaseId=${data.searchVo.phaseId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">
									<c:set var="cyjtbk" value="${cyjtbk+data.activity_read}" ></c:set>
									${data.activity_read}
								</a>
							</td>
							<td class="no_border" style="width:87px;">
								<a href="${ctx}jy/teachingView/manager/m_partLaunchActivity?userId=${data.userId}&phaseId=${data.searchVo.phaseId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">
									<c:set var="jhzjfq" value="${jhzjfq+data.activity_origination}" ></c:set>
									${data.activity_origination}
								</a>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<tr class="change_bg">
					<td style="width:88px;">
						合计
					</td>         
					<td style="width:88px;">
						${cyja}
					</td>
					<td style="width:88px;">
						${cykj}
					</td>
					<td style="width:88px;">
						${cyfs}
					</td>
					<td style="width:88px;">
						${cyjhzj}
					</td>
					<td style="width:88px;">
						${cyjxwz}
					</td>
					<td style="width:88px;">
						${cytkjl}
					</td>
					<td style="width:88px;">
						${cyjtbk}
					</td>
					<td class="no_border" style="width:87px;">
						${jhzjfq}
					</td>
				</tr>
				<c:if test="${dataSize == 0}">
	   				<div class="nofile">
						<div class="nofile1">
							暂时还没有数据，稍后再来查阅吧！
						</div>
					</div>
				</c:if>
			</table>
		</div>
		<p class="teachingManagement_tip">注：表格为各项资源数，点击各项名称即可按该项达标率进行排序显示。</p>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
	require(['jquery','tablexport','managerList'],function(){});
</script>
</html>