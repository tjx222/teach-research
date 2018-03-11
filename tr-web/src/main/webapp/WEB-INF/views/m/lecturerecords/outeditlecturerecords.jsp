<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="听课记录"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/lecturerecords/css/lecturerecords.css" media="screen">
	<ui:require module="../m/lecturerecords/js"></ui:require>
</head>
<body>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		撰写校外听课记录
		<div class="more" onclick="more()"></div>
	</header>
	<section> 
		<div class="inneredit_content"> 
			<div>
				<div class="inneredit_content_c"> 
					<h3>听课记录<a>查看教案</a></h3>
					<table border="1">
						<tr>
							<td>课题</td>
							<td colspan="3">
								<input type="text" />
							</td>
							<td>听课地点</td>
							<td>
								<input type="text" />
							</td>
						</tr>
						<tr>
							<td>授课教师</td>
							<td>
								<input type="text" />
							</td>
							<td>单位</td>
							<td>
								<input type="text" />
							</td>
							<td>年级学科</td>
							<td>
								<input type="text" />
							</td>
						</tr>
						<tr>
							<td>听课人</td>
							<td>胡艳芳</td>
							<td>听课时间</td>
							<td>2015-02-14</td>
							<td>听课杰数</td>
							<td>
								<select>
									<option>请选择听课节数</option>
									<option>1</option>
									<option>2</option>
									<option>3</option>
									<option>4</option>
									<option>5</option>
								</select>
							</td>
						</tr>
						<tr> 
							<td colspan="6">
								<textarea style="width:100%;height:40rem">编辑器插件</textarea>
							</td>
						</tr>
					</table>
					<ul>
						<li></li>
						<li></li>
					</ul>
				</div>
			</div>
		</div> 
	</section>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'outedit'],function(){	
	}); 
</script>
</html>