<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<ui:htmlHeader title="历年资源"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/history.css" media="screen"> 
	<link rel="stylesheet" href="${ctxStatic }/modules/history/css/common.css" media="screen"> 
	<script type="text/javascript">
		var current_history_year = '${year}';
		var current_history_code = '${code}';
		function changeNav(){
			var navs = $("#history_nav a")
			if(arguments.length == 1){
				if(navs.length ==2){
					$(navs).last().attr("href","jy/history/"+current_history_year+"/");
					$("#history_nav").append("&gt;&nbsp;").append($("<a/>").attr("href","javascript:void(0)").addClass("a_blank").html(arguments[0]));
				}else if(navs.length > 2){
					var content= "当前位置：";
					content+=navs[0].outerHTML+"&nbsp;&gt;&nbsp;";
					content+=navs[1].outerHTML+"&nbsp;&gt;&nbsp;";
					content+='<a href="javascript:void(0)" class="a_blank" >'+arguments[0]+'</a>';
					$("#history_nav").html(content);
				}
			}else if(arguments.length == 2){
				$("#history_nav a:last").attr("href",arguments[0]).attr("target","history_frame").removeClass("a_blank");
				$("#history_nav").append("&nbsp;>&nbsp;").append($("<a/>").attr("href","javascript:void(0)").addClass("a_blank").html(arguments[1]));
			}
		}
		function dialog(ops){
			$("#dialogWin").dialog(ops);
		}
	</script>
	<ui:require module="history/js"></ui:require>
</head>
<body> 

	<div id="dialogWin" class="dialog">
		<div class="dialog_wrap"> 
			<div class="dialog_content">
			</div>
		</div>
	</div>

	<div class="jyyl_top">
		<ui:tchTop style="1"></ui:tchTop>
	</div>
	<div class="history_nav" id="history_nav">
		当前位置：<jy:nav id="history"></jy:nav>
	</div>
	<div class="calendar_year">
		<div class="calendar_year_left">
			<ul class="calendar_year_left_ul" id="history_menu">
				<li>
					<a href="jy/history/${year }/" class="${empty code?'c_y_l_act':'' }" >
						<span style="font-weight:bold;">历年资源</span> 
						<span>${year }~${year+1}学年</span>
					</a>
				</li>
			</ul> 
		</div>  
		<div class="calendar_year_right">
		<!-- onload="setCwinHeight(this,false,100)" -->
		    <iframe frameborder="0" scrolling="no" id="history_frame" name="history_frame" style="border: none;height:800px;width:100%;display:none" src="" ></iframe>
			<div class="calendar_year_center" id="history_index_content">
				<h3 class="calendar_year_right_h3">
					<span>担任职务：</span>
					<strong>
						<c:forEach items="${userSpaces }" var="space">${space.spaceName }&nbsp;&nbsp;</c:forEach>
					</strong> 
				<div class="clear"></div>
				</h3>
<!-- 				<div class="echart">
					2014～2015学年资源统计图
				</div> -->
				<div class="resource_statistics">
					<h3>
						${year }~${year+1}学年资源统计表
					</h3>
					<div class="resource_statistics_table" id="history_clumn_table">					
					</div> 
				</div>
			</div> 
			<div class="clear"></div>
		</div>
	</div>
	<!--footer-->
	<ui:htmlFooter style="1"></ui:htmlFooter>
</body>
<script type="text/javascript">
	require(['jquery','common/dialog','homepage'],function(){});
</script>
</html>