<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
#ktFileInput{float:left;}
#ktFileInput-queue{position: absolute;  left: 339px; top: -8px;}
</style>
<div class="pageContent" id="dao_ru"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
	<form id="form" method="post" action="${ctx }jy/back/schconfig/clss/batchsave" class="pageForm required-validate"
		onsubmit="return validateCallback(this,clsdialogAjax);">
		
		<div class="pageFormContent" layoutH="66" >
					<table class="list nowrap itemDetail" addButton="添加班级" id="class_batchadd_tb" width="100%">
						<thead>
							<tr align="center">
								<th align="center" type="hidden" name="classes[#index#].orgId" defaultVal="${orgId }"></th>
								<th align="center" type="text" name="classes[#index#].code" defaultVal="${grade }0#index#" size="8">编号</th>
								<th align="center" type="text" name="classes[#index#].name" defaultVal="<jy:dic key="${grade }"></jy:dic>#index#班" size="30">名称</th>
								<th align="center" type="enum" name="classes[#index#].gradeId" enumUrl="${ctx }jy/back/schconfig/clss/lookSelect?grade=${grade}&orgId=${orgId }&phase=${phase}" size="9">年级</th>
								<th align="center" type="text" name="classes[#index#].sort" defaultVal="#index#" size="4" fieldClass="digits">输入显示顺序</th>
								<th align="center" type="del" width="60">操作</th>
							</tr>
						</thead>
						<tbody></tbody>
					</table>
		</div>
		
		<div class="formBar" style="margin-top: 10px">
						<ul>
							<li>
								<div class="button">
									<div class="buttonContent" align="center" style="margin-right: 338px" >
										<button type="submit" >保存</button>
									</div>
								</div>
							</li>
						</ul>
		</div>

	</form>

</div>
