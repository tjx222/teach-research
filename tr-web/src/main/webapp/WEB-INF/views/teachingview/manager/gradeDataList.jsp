<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="年级教研情况一览"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/teachingview/css/jyyl_css.css"media="screen">
	<ui:require module="teachingview/js"></ui:require>
</head>
<body> 
	<div class="jyyl_top">
		<ui:tchTop style="1" modelName="年级教研情况一览"></ui:tchTop>
	</div>
	<div class="jyyl_nav">
		当前位置：
		<jy:nav id="jyyl_njjy">
		</jy:nav>
	</div>
	<div class="teachingTesearch_class_content">
		<form id="form1" action="${pageContext.request.contextPath }/jy/teachingView/manager/teachingView_g" method="post">
		<div class="teachingTesearch_class_top">
			<p class="teachingTesearch_class_title">年级教研情况一览</p>
			<ul class="teachingTesearch_class_semester">
				<li>
					<input type="radio" name="termId" id="radio_shang" value="0" <c:if test="${search.termId==0 }">checked="checked"</c:if>/>
					<label for="radio_shang">上学期</label>
				</li>
				<li>
					<input type="radio" name="termId" id="radio_xia" value="1" <c:if test="${search.termId==1 }">checked="checked"</c:if>/>
					<label for="radio_xia">下学期</label>
				</li>
				<li>
					<input id="export" type="button" value="导出" tablename="年级教研情况一览"/>
				</li>
			</ul> 
		</div>
		<input id="orderFlag" type="hidden" name="orderFlag" value="${search.orderFlag }"/>
		<input id="orderMode" type="hidden" name="orderMode" value="${search.orderMode }"/>
		</form>
		<div id="list">
		<table cellpadding="0" cellspacing="0" class="teachingTesearch_class_table" id="header">
			<tr>
				<td>
					<b>
						年级/教师
					</b>
				</td>
				<td orderFlag="jiaoanWrite">
					<b>
						教案
						<c:if test="${search.orderFlag=='jiaoanWrite' }">
			    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
							<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
						</c:if>
						<c:if test="${search.orderFlag!='jiaoanWrite' }">
							<span class="up"></span>
							<span class="down"></span>
						</c:if>
					</b>
					<span class="num_tip">（篇数/课）</span>
				</td>
				<td orderFlag="kejianWrite">
					<b>
						课件
						<c:if test="${search.orderFlag=='kejianWrite' }">
			    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
							<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
						</c:if>
						<c:if test="${search.orderFlag!='kejianWrite' }">
							<span class="up"></span>
							<span class="down"></span>
						</c:if>
					</b>
					<span class="num_tip">（篇数/课）</span>
				</td>
				<td orderFlag="fansiWrite">
					<b>
						反思
						<c:if test="${search.orderFlag=='fansiWrite' }">
			    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
							<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
						</c:if>
						<c:if test="${search.orderFlag!='fansiWrite' }">
							<span class="up"></span>
							<span class="down"></span>
						</c:if>
					</b>
					<span class="num_tip">（篇数/课）</span>
				</td>
				<td orderFlag="listen">
					<b>
						听课记录
						<c:if test="${search.orderFlag=='listen' }">
			    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
							<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
						</c:if>
						<c:if test="${search.orderFlag!='listen' }">
							<span class="up"></span>
							<span class="down"></span>
						</c:if>
					</b>
					<span class="num_tip">（次数）</span>
				</td>
				<td orderFlag="activityJoin">
					<b>
						集体备课
						<c:if test="${search.orderFlag=='activityJoin' }">
			    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
							<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
						</c:if>
						<c:if test="${search.orderFlag!='activityJoin' }">
							<span class="up"></span>
							<span class="down"></span>
						</c:if>
					</b>
					<span class="num_tip">（参与数）</span>
				</td>
				<td orderFlag="summaryWrite">
					<b>
						计划总结
						<c:if test="${search.orderFlag=='summaryWrite' }">
			    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
							<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
						</c:if>
						<c:if test="${search.orderFlag!='summaryWrite' }">
							<span class="up"></span>
							<span class="down"></span>
						</c:if>
					</b>
					<span class="num_tip">（撰写数）</span>
				</td>
				<td orderFlag="share">
					<b>
						分享发表
						<c:if test="${search.orderFlag=='share' }">
			    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
							<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
						</c:if>
						<c:if test="${search.orderFlag!='share' }">
							<span class="up"></span>
							<span class="down"></span>
						</c:if>
					</b>
					<span class="num_tip">（总数）</span>
				</td>
				<td orderFlag="teacherRecordRes">
					<b>
						成长档案
						<c:if test="${search.orderFlag=='teacherRecordRes' }">
			    			<c:if test="${search.orderMode=='up'||empty search.orderMode }"> <span class="up"></span></c:if>
							<c:if test="${search.orderMode=='down'||empty search.orderMode }"> <span class="down"></span></c:if>
						</c:if>
						<c:if test="${search.orderFlag!='teacherRecordRes' }">
							<span class="up"></span>
							<span class="down"></span>
						</c:if>
					</b>
					<span class="num_tip">（精选数）</span>
				</td>
			</tr>
		</table>
		<div class="teachingTesearch_class_con">
			<table cellpadding="0" cellspacing="0" class="teachingTesearch_class_table2" id="expertTable">
				<c:forEach var="data" items="${dataList }">
					<tr class="change_bg">
						<td><a href="${data['url'] }">${data['gradeName'] } / ${data['teacherCount'] }</a></td>
						<td>${data['jiaoanTotal']}/${data['jiaoanWrite']}</td>
						<td>${data['kejianTotal']}/${data['kejianWrite']}</td>
						<td>${data['fansiTotal']}/${data['fansiWrite']}</td>
						<td>${data['listen']}</td>
						<td>${data['activityJoin']}</td>
						<td>${data['summaryWrite']}</td>
						<td>${data['share']}</td>
						<td class="no_border">${data['teacherRecordRes']}</td>
					</tr>
				</c:forEach>
				<c:if test="${!empty dataList }">
					<tr id="heji">
						<td>合计</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
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
						<td><button class="checkBtn"></button></td>
					</tr>
				</c:if>
			</table>
		</div>
		<p class="teachingTesearch_class_tip">注：表格为各项资源总数，点击各项名称即可按该项人均达标率进行排序显示。</p>
		</div>
		<div class="chartDiv"  style="display: none;width:986px;">
			<div class="radio_div">
				<div class="radioDiv"><input type="radio" name="kind" value="1"/>教案（撰写数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="2"/>课件（撰写数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="3"/>反思（撰写数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="4"/>听课记录（次数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="5"/>集体备课（参与数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="6"/>计划总结（撰写数）</div>
				<div class="radioDiv"><input type="radio" name="kind" value="7"/>分享发表（总数） </div>
				<div class="radioDiv"><input type="radio" name="kind" value="8"/>成长档案（精选数）</div>
			</div> 
			<div id="chartDiv" style="width: 900px;height:450px;margin:0 auto;"></div> 
			<div class="cencelDiv">
				<span></span>
				<strong>点此返回查看年级教研情况一览表</strong>
			</div>
		</div>
		<div class="clear"></div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
require(['jquery','teacherList','tablexport','viewChart'],function(){
	$("#heji").find("td").each(function(index){
		if(index!=0){
			$(this).text(col_sum(index));
		}
	});
	function col_sum(col){
		var sum_ = 0;
		$(".change_bg").each(function(){
			if (col <= 3) {
				var num = $(this).find("td").eq(col).text().split("/");
				if (sum_.length >0 ) {
				var sumArr = sum_.split("/");
				sum_ = (parseInt(sumArr[0]) + parseInt(num[0])) + "/" + (parseInt(sumArr[1]) + parseInt(num[1]));
					
				} else {
					sum_ = num[0] + "/" + num[1];
				}
			} else {
				sum_ = parseInt(sum_) + parseInt($(this).find("td").eq(col).text());
			}
		});
		return sum_
	}
	$("#viewChart").find("td").each(function(index,obj){
		$(obj).find("button").click(function(){
			//更改标题
			$(".teachingTesearch_class_title").text("年级教研分项情况图表");
			//选中查看的类别
			$(".radio_div").find(".radioDiv").eq(index-1).find("input").prop("checked",true);
			//生成图表
			$("#list").hide();
			var trArray = $(".teachingTesearch_class_table2").find(".change_bg");
			viewChart(trArray,index);
		});
	});
	$(".cencelDiv").click(function(){
		$(".chartDiv").hide();
		$("#list").show();
		//更改标题
		$(".teachingTesearch_class_title").text("年级教研情况一览");
	});
	$(".radio_div").find("input[type='radio']").click(function(){
		var trArray = $(".teachingTesearch_class_table2").find(".change_bg");
		viewChart(trArray,$(this).val());
	});
});
</script>
</html>
