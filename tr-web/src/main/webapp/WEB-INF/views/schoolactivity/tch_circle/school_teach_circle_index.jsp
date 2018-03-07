<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<ui:htmlHeader title="校际教研教研圈首页"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic }/modules/schoolactivity/css/teach_circle.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/validationEngine.jquery.css" media="screen">
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine-zh_CN.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/jquery/jquery.validationEngine.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/modules/schoolactivity/js/teach_circle.js"></script>
<style type="text/css">
.z_bn {
	width: 110px;
	display: block;
	padding: 0px 2px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	float: left;
}

.z_ty {
	font-weight: 200;
	color: green;
}

.z_jj {
	font-weight: 200;
	color: red;
}

.z_tc {
	font-weight: 200;
	color: #bbb;
}

.z_zc {
	font-weight: 200;
	color: #474747;
}

.w120 {
	display: block;
	width: 120px;
	padding: 0px 2px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.w90 {
	display: block;
	width: 90px;
	padding: 0px 2px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.w168 {
	display: block;
	max-width: 168px;
	padding: 0px 2px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.w140 {
	display: block;
	width: 140px;
	padding: 0px 2px;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
	float: left;
}
</style>
</head>

<body>
	<div class="wrapper">
		<div class='jyyl_top'>
			<ui:tchTop style="1" modelName="校际教研参与"></ui:tchTop>
		</div>
		<div class="jyyl_nav">
			<h3>
				当前位置：
				<jy:nav id="cjjyq"></jy:nav>
			</h3>
		</div>
		<div class="clear"></div>
		<div class="research_circle">
			<div class="research_circle_l">
				<h3>
					<strong class="research_circle_l_h3_strong"></strong>
					<span>创建校际教研圈</span>
				</h3>
				<form id="teach_circle" action="${ctx}jy/schoolactivity/circle/save" method="post">
					<ui:token></ui:token>
					<input type="hidden" id="createCircleOrgs" name="circleOrgs">
					<input type="hidden" id="circleId" name="id">
					<p>
						<span class="research_circle_p_span">*</span>
						<label for="">教研圈名称：</label> 
						<input id="circleName" type="text" name="name" value="校际教研圈" class="txt validate[required,maxSize[25]]" maxlength="26">
					</p>
					<p>
						<span class="research_circle_p_span">*</span>
						<input type="hidden" id="areaIds" name="areaIds" value=",${not empty provinceList ? provinceList[0].id :'' }," />
						<span>省</span> 
						<select style="width: 105px; height: 25px; border: 1px #999 solid;" class="chosen-select-deselect validate[required]" id="province" name="province">
							<c:forEach items="${provinceList }" var="province">
								<option value="${province.id }">${province.name }</option>
							</c:forEach>
						</select> 
						<span>市</span> 
						<select	style="width: 105px; height: 25px; border: 1px #999 solid;" class="chosen-select-deselect validate[required]" id="city" name="city">
						</select>
						<span>区</span>
						<select style="width: 105px; height: 25px; border: 1px #999 solid;" class="chosen-select-deselect validate[required]" id="county" name="county">
						</select>
					</p>
					<p style="margin-bottom: 10px;">
						<span class="research_circle_p_span">*</span>
						<label for="">选择学校：</label>
					</p>
					<div class="centent1">
						<label class="centent1_lable">
							<input id="searchContent" type="search" placeholder="输入学校名称进行查找" value="" maxlength="12" class="search" style="display: inline;">
							<input type="button" class="btn" onclick="searchSchool()">
						</label>
						<!-- 						<b id="placeholder_search" class="placeholder_search" onclick="setNull()">输入学校名称进行查找</b> onclick="setNull()" onkeydown="setNull()" onblur="setInit()" -->
						<span class="centent1_span">已选学校:</span>
						<div class="clear"></div>
						<div class="centent_l">
							<ul id="ul1">

							</ul>
						</div>
						<div class="centent_c">
							<input type="button" value="添加" class="add_1" id="add_1">
						</div>
						<div class="centent_r">
							<ul id="ul2">

							</ul>
						</div>
						<div class="clear"></div>
						<div class="button_wrap"> 
							<input id="btn_sub" type="button" class="btn_sub1" onclick="submitCircle()" value="确定"> 
							<input id="btn_xg" type="button" class="btn_xg1" onclick="submitCircle()" style="display: none;" value="修改"> 
							<input id="btn_bgl" type="button" class="btn_bgl1" onclick="notupCircle()" style="display: none;" value="不改了">
						</div>
					</div>
				</form>
			</div>
			<div class="research_circle_r">
				<h3>校际教研圈列表</h3>

				<c:choose>
					<c:when test="${!empty stcList }">
						<c:forEach items="${stcList }" var="stc" varStatus="status">
							<div class="Reflect_cont_right_1_dl">
								<dl>
									<dd>
										<c:forEach items="${stc.stcoList }" var="stco">
											<c:if test="${_CURRENT_USER_.orgId == stco.orgId }">
												<c:if test="${stco.state==4 }">
													<img src="${ctxStatic }/modules/schoolactivity/images/qxj.png" alt="">
												</c:if>
												<c:if test="${stco.state!=4 }">
													<img src="${ctxStatic }/modules/schoolactivity/images/xj.png" alt="">
													<div class="school"
														<c:if test="${(status.index+1) mod 3 == 0 }">style="left:-120px;"</c:if>>
														<ol id="cirid_${stc.id }" cirname="${stc.name }">
															<c:forEach items="${stc.stcoList }" var="stco">
																<jy:ds var="org" key="${stco.orgId }"
																	className="com.tmser.tr.manage.org.service.OrganizationService"></jy:ds>
																<li stitle="${org.areaName }，${org.shortName }" sname="${org.shortName }" state="${stco.state }" orgid="${org.id }">
																	<a title="${org.areaName }，${org.shortName }" class="w90">${org.shortName }</a>
																	<span> <c:choose>
																			<c:when test="${stco.state==1}">
																				<label class="z_zc">待接受</label>
																			</c:when>
																			<c:when test="${stco.state==2}">
																				<label class="z_ty">已接受</label>
																			</c:when>
																			<c:when test="${stco.state==3}">
																				<label class="z_jj">已拒绝</label>
																			</c:when>
																			<c:when test="${stco.state==4}">
																				<label class="z_tc">已退出</label>
																			</c:when>
																			<c:when test="${stco.state==5}">
																				<label class="z_ty">已恢复</label>
																			</c:when>
																		</c:choose>
																</span></li>
															</c:forEach>
														</ol>
													</div>
												</c:if>
											</c:if>
										</c:forEach>
									</dd>
									<dt>
										<span title="${stc.name }" class="w120">${stc.name }</span> <span><fmt:formatDate
												value="${stc.crtDttm }" pattern="yyyy-MM-dd" /></span>
									</dt>
								</dl>
								<div class="show_p">
									<ol>
										<c:forEach items="${stc.stcoList }" var="stco" varStatus="st">
											<c:if test="${_CURRENT_USER_.orgId == stco.orgId }">
												<c:if test="${stco.state==4 }">
													<c:set var="isTuiChu" value="true"></c:set>
												</c:if>
												<c:if test="${stco.state!=4 }">
													<c:set var="isTuiChu" value="false"></c:set>
												</c:if>
											</c:if>
											<c:if test="${st.index > 0 }">
												<c:if test="${stco.state>=2 &&  stco.state!=3 }">
													<c:set var="isJSState" value="true"></c:set>
												</c:if>
											</c:if>
										</c:forEach>
										<c:if test="${_CURRENT_USER_.id==stc.crtId}">
											<c:if test="${isTuiChu }">
												<li class="jz_li_edit" title="禁止修改"></li>
												<li title="禁止删除" class="jz_li_del"></li>
											</c:if>
											<c:if test="${!isTuiChu }">
												<li title="修改" class="li_edit" onclick="upCircle('${stc.id }')"></li>
												<c:if test="${stc.isDelete }">
													<li title="禁止删除" class="jz_li_del"></li>
												</c:if>
												<c:if test="${!stc.isDelete }">
													<li title="删除" class="li_del" onclick="deleteCircle('${stc.id}')"></li>
												</c:if>
											</c:if>
										</c:if>
										<c:if test="${isTuiChu }">
											<li title="恢复" class="li_recovery" onclick="huifu('${stc.id}')"></li>
										</c:if>
										<c:if test="${!isTuiChu }">
											<c:if test="${isJSState }">
												<c:set var="isJSState" value="false"></c:set>
												<li title="退出" class="li_quit"
													onclick="tuichu('${stc.id}')"></li>
											</c:if>
										</c:if>
									</ol>
								</div>
							</div>
						</c:forEach>
						<div class="clear"></div>
						<div style="height: 30px;">&nbsp;</div>
					</c:when>
					<c:otherwise>
						<div class="check_lesson_c1"
							style="height: 250px; margin: 165px auto;">
							<div class="check_lesson_c2">您还没有创建校际教研圈哟，赶紧去左边“创建校际教研圈”吧！
							</div>
						</div>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="clear"></div>
		<ui:htmlFooter style="1"></ui:htmlFooter>
	</div>
</body>
<ui:require module="schoolactivity/js"></ui:require>
<link rel="stylesheet"
	href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
<script type="text/javascript"
	src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script>
<script type="text/javascript">
	var config = {
		'.chosen-select' : {},
		'.chosen-select-deselect' : {
			allow_single_deselect : true
		},
		'.chosen-select-deselect' : {
			disable_search : true
		},
	};
	for ( var selector in config) {
		$(selector).chosen(config[selector]);
	}
</script>
<script type="text/javascript">
	$(function() {
		var content = $.trim($("#searchContent").val());
		if (content != "") {
			$("#placeholder_search").html("");
		}
		$('.centent_l ul').on('click', 'li', function() {
			$(this).addClass("centent_l_act").siblings().removeClass("centent_l_act");
		});

		//移到右边
		$('#add_1').on('click',function() {
					//获取选中的选项，删除并追加给对方
					var isone = $('#ul1 li.centent_l_act').attr("isone");
					var isobj = document.getElementById("r_" + isone);
					if (isobj != null) {
						alert("选中列表中已经存在了此机构，请重新选择！");
					} else {
						var id = isone;
						var name = $('#ul1 li.centent_l_act').attr("title");
						var names = name.split("，");
						if (id != undefined && names != undefined) {
							var str = '<li isone="'+id+'" title="'+names[0]+'，'+names[1]+'" id="'+id+'@'+names[1]+'@1" ><a><label class="w140">' + names[1]
									+ '</label><b id="r_'+id+'"" class="remove"></b></a></li>';
							$('#ul1 li.centent_l_act').remove();
							$(str).appendTo('#ul2');
							$('#ul2 li').removeClass("centent_l_act");
						} else {
							alert("请您先选择一项！");
						}
					}
				});

		$('.centent_r ul').on('click', 'b.remove', function() {
			var name = $(this).parents('li').attr("title");
			var names = name.split("，");
			var isone = $(this).parents('li').attr("isone");
			var leftStr = '<li isone="'+isone+'" title="'+names[0]+'，'+names[1]+'"><a class="w168">' + names[1] + '</a></li>';
			$(this).parents('li').remove();
			$(leftStr).appendTo('.centent_l ul');
		});
	});
	//修改教研圈
	window.upCircle = function(circleId) {
		var orgStr = "";
		var circleName = $("#cirid_" + circleId).attr("cirname");
		$.each($("#cirid_" + circleId + " li"), function(index, obj) {
			var name = $(obj).attr("sname");
			var title = $(obj).attr("stitle");
			var orgId = $(obj).attr("orgid");
			var state = $(obj).attr("state");
			var id = orgId + "@" + name + "@" + state;
			if (state == 1) {
				orgStr += '<li isone="'+orgId+'" title="'+title+'" id="'+id+'"><a><b class="z_bn">' + name + '</b><label class="z_zc">待接受</label><b class="remove" id="r_'+orgId+'"></b></a></li>';
			} else if (state == 2) {
				orgStr += '<li isone="'+orgId+'" title="'+title+'" id="'+id+'"><a><b class="z_bn">' + name + '</b><label class="z_ty">已接受</label><b class="isok" id="r_'+orgId+'"></b></a></li>';
			} else if (state == 3) {
				orgStr += '<li isone="'+orgId+'" title="'+title+'" id="'+id+'"><a><b class="z_bn">' + name + '</b><label class="z_jj">已拒绝</label><b class="remove" id="r_'+orgId+'"></b></a></li>';
			} else if (state == 4) {
				orgStr += '<li isone="'+orgId+'" title="'+title+'" id="'+id+'"><a><b class="z_bn">' + name + '</b><label class="z_tc">已退出</label><b class="remove" id="r_'+orgId+'"></b></a></li>';
			} else if (state == 5) {
				orgStr += '<li isone="'+orgId+'" title="'+title+'" id="'+id+'"><a><b class="z_bn">' + name + '</b><label class="z_ty">已恢复</label><b class="isok" id="r_'+orgId+'"></b></a></li>';
			}
		});
		$('#ul2').html(orgStr);
		$("#circleId").val(circleId);
		$("#circleName").val(circleName);
		$("#btn_sub").css("display", "none");
		$("#btn_xg").css("display", "");
		$("#btn_bgl").css("display", "");
	};

	/***/
	require([ 'teach_circle' ]);
</script>
</html>