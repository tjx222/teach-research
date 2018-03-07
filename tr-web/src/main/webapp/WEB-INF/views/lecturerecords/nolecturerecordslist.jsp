<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="听课记录"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/lecturerecords.css">
<style type="text/css">
.school{
 	position: absolute; 
 	width: 130px;
 	height: 30px;
 	z-index: 1001;
 	padding-top: 5px; 
 	display: none;
 	margin-left:-5px;
 	left:35px;
 	top:138px;
}

.school .in_school{
	width:50px;
	height: 23px;
	line-height: 23px;
	text-align: center;
	border-radius: 5px;
	border:1px #8f8f8f solid;
	background: #fff;
	font-size: 14px;
	display: inline-block;
	margin-right: 5px;
	cursor: pointer;
}
.school .in_school:hover{
	color: #fff;
	background: #51c7f8;
}
.school .outside_school:hover{
	color: #fff;
	background: #51c7f8;
}
.school .outside_school{
	width: 50px;
	cursor: pointer;
	height: 23px;
	line-height: 23px;
	text-align: center;
	border-radius: 5px;
	border:1px #8f8f8f solid;
	background: #fff;
	font-size: 14px;
	display: inline-block;
}
</style>
</head>

<body>
	<div class="wrap">
	<ui:tchTop hideMenuList="false"></ui:tchTop>
		<div class="wrap_top_2">
			<h3>当前位置：<jy:nav id="tkjl"></jy:nav></h3>
		</div>
		<div class="clear"></div>
		<div class="lecture_record">
			<div class="lecture_record_bg">
				您还没有撰写“听课记录”<br />哟，赶紧去赶紧点击<br/>
					<input type="button" class="zx">吧！
					<div class="school">
						<span class="in_school" onclick="findPeople();">校内</span>
						<span class="outside_school" onclick="writeLectureRecordsOuteInput();">校外</span>
					</div>
					
			</div>
		</div>
		<div class="clear"></div>
		<ui:htmlFooter></ui:htmlFooter>
	</div>
<script type="text/javascript" src="${ctxStatic}/modules/lecturerecords/js/lecturerecords.js"></script>
</body>
</html>