<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
	.unit label{text-align: right;}
	.unit span{position: relative;top:5px;}
</style>
<div class="pageContent">
	<div  id="" class="pageFormContent" layoutH="56">
		<div class="unit">
			<label>用户姓名：</label>
			<span>${data.userName }</span>
		</div>
		<div class="unit">
			<label>用户身份名称：</label>
			<span>${data.spaceName }</span>
		</div>
		<div class="unit">
			<label>所属部门：</label>
			<span>${data.department }</span>
		</div>
		<div class="unit">
			<label>管辖部门：</label>
			<span>${data.departments }</span>
		</div>
		<div class="unit">
			<label>角色：</label>
			<span>${data.role }</span>
		</div>
		<div class="unit">
			<label>学段：</label>
			<span>${data.phase }</span>
		</div>
		<div class="unit">
			<label>学科：</label>
			<span>${data.subject }</span>
		</div>
		<div class="unit">
			<label>年级：</label>
			<span>${data.grade }</span>
		</div>
		<div class="unit">
			<label>教材：</label>
			<span>${data.book }</span>
		</div>
		<div class="unit">
			<label>学年：</label>
			<span>${data.schoolYear }</span>
		</div>
	</div>	
	<div class="formBar">
		<ul>
			<li>
				<div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div>
			</li>
		</ul>
	</div>
</div>
<script type="text/javascript">
	$.pdialog.resizeDialog({style: {height: 320,width:580}}, $.pdialog.getCurrent(), "");
</script>