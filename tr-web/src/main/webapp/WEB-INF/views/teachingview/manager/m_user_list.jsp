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
		<div id="list">
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
						<tr class="change_bg replace_tr">
							<td style="width:88px;"><a href="${ctx}jy/teachingView/manager/m_details?userId=${data.userId}&termId=${data.searchVo.termId}&orgId=${data.searchVo.orgId}">${data.userName}</a></td>
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
				<tr class="replace_tr">
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
				<tr id="viewChart">
					<td>查看图表</td>
					<td><button class="checkBtn"></button></td>
					<td><button class="checkBtn"></button></td>
					<td><button class="checkBtn"></button></td>
					<td><button class="checkBtn"></button></td>
					<td><button class="checkBtn"></button></td>
					<td><button class="checkBtn"></button></td>
					<td><button class="checkBtn"></button></td>
					<td class="no_border"><button class="checkBtn"></button></td>
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
		<p class="teachingManagement_tip">注：表格为各项资源数，点击各项名称即对该项进行排序显示。</p> 
		</div>
		<div class="chartDiv"  style="display: none;width:986px;">
			<div class="radio_div">
				<div class="radioDiv"><input type="radio" name="kind" value="1"/>查阅教案（查阅数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="2"/>查阅课件（查阅数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="3"/>查阅反思（查阅数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="4"/>查阅计划总结（查阅数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="5"/>查阅教学文章（查阅数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="6"/>查阅听课记录（查阅数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="7"/>集体备课查阅数 </div>
				<div class="radioDiv"><input type="radio" name="kind" value="8"/>集体备课发起数</div>
			</div> 
			<div id="chartDiv" style="width: 900px;height:450px;margin:0 auto;"></div> 
			<div class="cencelDiv">
				<span></span>
				<strong>点此返回查看教学管理情况一览表</strong>
			</div> 
		</div>
		<div class="clear"></div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
	require(['jquery','tablexport','managerList','viewChart'],function(){
		$("#viewChart").find("td").each(function(index,obj){
			$(obj).find("button").click(function(){
				//更改标题
				$(".teachingManagement_title").text("教学管理分项情况图表");
				//选中查看的类别
				$(".radio_div").find(".radioDiv").eq(index-1).find("input").prop("checked",true);
				//生成图表
				$("#list").hide();
				var trArray = $(".teachingManagement_table2").find(".change_bg");
				viewChart(trArray,index);
			});
		});
		$(".cencelDiv").click(function(){
			$(".chartDiv").hide();
			$("#list").show();
			//更改标题
			$(".teachingManagement_title").text("教学管理情况一览");
		});
		$(".radio_div").find("input[type='radio']").click(function(){
			var trArray = $(".teachingManagement_table2").find(".change_bg");
			viewChart(trArray,$(this).val());
		});
	});
</script>
</html>