<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="教研平台"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/school/tch/css/index.css" media="screen"> 
<ui:require module="school/tch/js"></ui:require>
</head> 
<div id="curriculum_pop" class="dialog">
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">课程表</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<iframe id="curriculum" width='702' height='480'  frameborder="0" scrolling="no" style="border: none; overflow: hidden;"></iframe>
		</div>
	</div>
</div>
<body>
<div id="app_pop" class="dialog"> 
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">添加工作项</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<div class="add_work_item menu_box">
				<ul>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="guide2">
	这里是您的常态工作项。
	<span></span>
</div>
<div class="guide1"></div>
<div class="guide"></div>
<div class="wrapper"> 
	<div class="jyyl_top"><ui:tchTop style="1" modelName="通知公告"></ui:tchTop></div>
	<div class="jyyl_nav">
		当前位置：<jy:nav id="tch_index" ></jy:nav>
	</div>
	<div class='home_cont'>
		<div class="home_cont_l">
			<div class="home_cont_l_top">
				<h3 class="home_cont_l_top_h3">
					<span></span>
					<strong>最新动态</strong>
				</h3>
				<div class="home_cont_l_top_div">
					<ul id="ul">
					</ul>
				</div>
			</div> 
			<div class="home_cont_l_bottom">
				<h4 class="home_cont_l_top_h4">
					<span></span>
					<strong>课程表</strong>
					<b class="curriculum"></b>
				</h4>
					
						<div class="home_cont_l_bottom_div">
						<div class="table_curriculum1" id="curriculum_table">
						 <div class="cont_left_2_1">
						<div class="table_curriculum1" id="curriculum_table">
							<table cellspacing="0" cellpadding="0" >
								<tbody>
									<tr>
										<th></th>
										<th>一</th>
										<th>二</th>
										<th>三</th>
										<th>四</th>
										<th>五</th>
										<th class="six">六</th>
										<th class="seven">日</th>
									</tr>
									<c:forEach begin="1" end="10" step="1" var="status">
										<tr class="${status>8?'night':'' }">
											<td  style="border-top: 0px; border-bottom: 0px;"></td>
											<c:forEach begin="1" end="7" step="1" var="status2">
												<td class="${status2>5?'day':''}" id="c${status2}-${status}"></td>
											</c:forEach>
										</tr>
									</c:forEach>
								</tbody>

							</table>
						</div>
						<div class="mo">
								上<br />午
						</div>
						<div class="aft">
							下<br />午
						</div>
						<div class="ev">
							晚<br />上
						</div>
					</div>
					</div>
					</div> 
			</div>
			<div class="home_cont_l_bottom1" style="display:none;"> 
				<div class="home_cont_l_bottom1_title">历年资源</div>
				<ul class="show_history_li">
				</ul> 
			</div>
		</div>
		<div class="home_cont_r">
			<h4 class="home_cont_r_h4">
				<span></span>
				<strong>我的工作项</strong> 
			    <div style="font-size: 14px;height: 40px;line-height: 40px; float: right;margin-right:20px;"><a style="color:#aa1111;" target="_blank" href="p.jsp">如撰写教案等功能无法正常使用，请点击此处下载pageOffice插件</a></div>
			</h4>
			<div class="cont_right_0">
				<div class="cont_right_1">
					<div id="menu_list_box" class="menu_box">
						<ul id="tuozhuai" class="dragsort-ver" > 
							<c:forEach items="${sessionScope._CURRENT_MENU_LIST_ }" var="m">
								<li>
									<dl data-mid="${m.id }" data-id="${m.menu.id }">
										<dd>
											<a href="${m.menu.url }" target="${m.menu.target }"><img
												src="${empty m.ico ? m.menu.icon.imgSrc : m.ico }"
												alt="${empty m.name ? m.menu.name : m.name }" title="${m.menu.desc}"></a>
										</dd>
										<dt>
											<a href="${m.menu.url }" target="${m.menu.target }">${empty m.name ? m.menu.name : m.name }</a>
										</dt>
										<c:if test="${m.menu.fixed == 0 }">
											<span mid="${m.id }" class="del"></span>
										</c:if>
									</dl>
								</li>
							</c:forEach>
						</ul>
					</div>
					<p class="add" id="add" style="width:${fn:length(sessionScope._CURRENT_MENU_LIST_)%4 eq 0 ? '620px;':'145px;'}">
						<span></span> 
						<strong class="guide3"></strong>
						<strong class="guide4">
							点击这里可以添加已隐藏的工作项！
							<b></b>
						</strong>
					</p>
					<div class="clear"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div><br/><br/><br/>
	<ui:htmlFooter style="1"></ui:htmlFooter>
</div> 
</body>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','js'],function($){	}); 
		<c:if test="${empty sessionScope._CURRENT_USER_.lastLogin}">
		require(['guide']); 
		</c:if> 
	</script>
	
</html>