<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<ui:htmlHeader title="同伴资源"></ui:htmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/modules/comres/css/tbzy.css" media="screen">
	<link rel="stylesheet" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
	<style type="text/css">
		.chosen-container-single .chosen-single{ 
		border:1px #aeaeae solid;
		}
		.chosen-container{
		float:right;
		margin-top:5px;
		}
	</style>
</head>
<body>
	   <div class="jyyl_top">
		<ui:tchTop style="1" modelName="同伴资源"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>当前位置：
			    <jy:nav id="tbzy">
			    	<jy:param name="name" value="同伴资源"></jy:param>
			    </jy:nav></h3>
			<!-- <h4>当前学年：<select name="" id=""><option value="">2013-2014学年</option><option value="">2014-2015学年</option></select></h4> -->
		</div>
		<div class="clear"></div>
		<div class="collective_cont">
			<c:if test="${empty data.datalist }" >
				<form name="pageForm" method="post">
					<div >
							<h3>
								<ol id="UL">
									<li class="collective_cont_act">配套资源</li>
									<li class="collective_cont_act1"><a href="jy/comres/index_type">分类资源</a></li>
								</ol>
								<div class="p">
									<div class="ser">
										<input type="text" class="txt_seh" name="planName" value="${model.lessonName }">
										<input type="button" class="txt_btn" id="searchBtn">
									</div>
									<select name="bookId" class="chosen-select-deselect" id="bookList" style="width:120px;height:27px;float:right;margin-top:5px;">
									</select>
								</div>
							</h3>
							<div class="clear"></div> 
							<div class="empty_wrap">
								<div class="empty_img"></div>
						    	<div class="empty_info">
									${empty model.lessonName?'您的小伙伴还没有分享资源哟，稍后再来看看吧！':'找不到您查找的资源！'}							
								</div>
							</div>
						</div>
					</div>
				</form>
			</c:if>
			<c:if test="${not empty data.datalist }" >
				<form name="pageForm" method="post">
						<div>
							<h3>
								<ol id="UL">
									<li class="collective_cont_act">配套资源</li>
									<li class="collective_cont_act1"><a href="jy/comres/index_type">分类资源</a></li>
								</ol>
								<div class="p">
									<c:if test="${not empty model.lessonName }">
									<label for="" style="float:left;margin-top:-2px;" id="allResult"><a href="jy/comres/index" style="color:#51c7f8;">全部&lt;</a></label>
									</c:if>
									<div class="ser">
										<input type="text" class="txt_seh" name="planName" value="${model.lessonName }">
										<input type="button" class="txt_btn" id="searchBtn">
									</div>
									<select name="bookId" id="bookList"  class="chosen-select-deselect"  style="width:120px;">
									</select>
								</div>
							</h3>
							<div class="clear"></div>
							<div class="collective_cont_big">
								<div class="collective_cont_tab">
									<table>
									  <tr>
									    <th style="width:250px;">课题</th>
									    <th style="width:90px;">教案</th>
									    <th style="width:90px;">课件</th>
									    <th style="width:90px;">反思</th>
									    <th style="width:160px;">分享时间</th>
									    <th style="width:150px;">学校</th>
									    <th style="width:100px;">作者</th>
									  </tr>
									  <c:forEach var="kt" items="${data.datalist }">
									  <tr>
									    <td style="text-align:left;padding-left:5px;">
									   		 [${kt.bookShortname}][<jy:dic key="${kt.gradeId }"/>${kt.fasciculeId == 176?'上':kt.fasciculeId == 177?'下':'全' }]<a href="jy/comres/viewlesson?lessPlan=${kt.id}" target="_blank"><span title="${kt.lessonName }"><ui:sout value="${kt.lessonName }" length="20" needEllipsis="true"></ui:sout></span></a>
									    </td>
									    <td>${kt.jiaoanShareCount }</td>
									    <td>${kt.kejianShareCount }</td>
									    <td>${kt.fansiShareCount }</td>
									    <td><fmt:formatDate value="${kt.shareTime }" pattern="MM-dd"/></td>
									    <td><jy:di key="${kt.orgId }" className="com.tmser.tr.manage.org.service.OrganizationService" var="org">
									  				 <span title="<jy:di var="area" key="${org.areaId }" className="com.tmser.tr.manage.org.service.AreaService">${area.name }${org.shortName }</jy:di>"><ui:sout value="${org.shortName }" length="20" needEllipsis="true"></ui:sout></span>
									  	    </jy:di></td>
									    <td class="details">
										    <span>
										  		<jy:di key="${kt.userId }" className="com.tmser.tr.uc.service.UserService" var="u">
										  			${u.orgId  == sessionScope._CURRENT_SPACE_.orgId ? u.name : u.nickname}
										  		</jy:di> 
										  		<div class="school_1">
													<dl>
														<dd>
															<span ><a target="_blank" href="jy/companion/companions/${u.id }"><ui:photo src="${u.photo }" style="width:60px;height:60px;"></ui:photo></a></span>
														</dd>
														<dt>
														   <span style="color:#474747;">姓名：${u.orgId  == sessionScope._CURRENT_SPACE_.orgId ? u.name : u.nickname}
															</span>
															<span style="color:#474747;">学科：<jy:dic key="${kt.subjectId }"/></span>
															<span style="color:#474747;">年级：<jy:dic key="${kt.gradeId }"/></span>
															<span style="color:#474747;">教材：${kt.bookShortname}</span>
															<span style="color:#474747;">职称：${u.profession }</span>
															<span style="color:#474747;">学校：<b title="${org.shortName }"><ui:sout value="${org.shortName }" length="14" needEllipsis="true"></ui:sout></b>
															</span>
														</dt>
													</dl>
												</div>
										    </span>
										    
									    </td>
									  </tr>
									  </c:forEach>
									  </table>
									 </div>
									 <div class="clear"></div>
									 <ui:page url="jy/comres/index" data="${data}"/>
									 <input type="hidden" class="currentPage" name="page.currentPage" value="1">
							</div>
					</div>
				</form>
			</c:if>
		</div>
	<ui:htmlFooter style="1"></ui:htmlFooter>
<script type="text/javascript">
	$(document).ready(function(){
		$.getJSON("jy/comres/listBooks",function(data){
			var contianner = $('#bookList');
			for(var i=0;i<data.length;i++){
				var book = data[i];
				if(!book.bookShortname)
					continue;
				
				if("${model.bookShortname}" == book.bookShortname){
					$('<option value='+book.bookId+' selected="selected" >'+book.bookShortname+'</option>').appendTo(contianner);
				}else{
					$('<option value='+book.bookId+'>'+book.bookShortname+'</option>').appendTo(contianner);
				}
				$("#bookList").trigger("chosen:updated");
			}
		});
		
		$('#searchBtn').click(function(){
			$('input[name="page\\.currentPage"]').val("1");
			$('form[name="pageForm"]').submit();
		});
		//下拉列表
		  var config = {
		      '.chosen-select'           : {},
		      '.chosen-select-deselect'  : {allow_single_deselect: true},
		      '.chosen-select-deselect' : {disable_search:true}
		    };
		    for (var selector in config) {
		      $(selector).chosen(config[selector]);
		    }
	});
	</script>
</body>
<script>
</script>
</html>