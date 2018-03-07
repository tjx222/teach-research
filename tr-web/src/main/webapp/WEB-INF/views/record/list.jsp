<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<ui:htmlHeader title="成长档案袋"></ui:htmlHeader>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="${ctxStatic }/common/css/reset.css">
	<link rel="stylesheet" href="${ctxStatic }/modules/record/css/record.css">
	<ui:require module="record/js"></ui:require> 
</head>
<div id="_add"  class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">添加档案袋</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<form id="formular" action="jy/record/addRecord" method="post">
				<div class="Growth_Portfolio_bottom">
					<p>
						<label for="" style="width: 85px;"><b style="left:-7px;">*</b>档案袋名称：</label>
						<input style="border:1px #9f9f9f solid;height:22px;margin-left: 4px;" type="text" name="name" class="input_txt validate[required,minSize[0]] text-input validate[optional,maxSize[8]] text-input" ><strong>注：最多可输入8个字</strong>
					</p>
					<div class="clear"></div>
					<p style="margin-top:30px;">
						<label for="" id="lab" style="width: 85px;">档案袋简介：</label>
						<textarea style="width:271px;float:left;margin-left: 5px;" name="desc" cols="30" rows="4" class="validate[optional,maxSize[50]] text-input"></textarea><br />
						<strong class="strong_sty" style="margin-left:80px;" >注：最多可输入50个字</strong>
					</p>
					<div class="clear"></div>
					<p style="margin-top:20px;">
						<input type="button"  id="button" class="Growth_Portfolio_btn" value="保存">
					</p>
				</div>
			</form>
		</div>
	</div>
</div>

<div id="_modify"  class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">修改档案袋</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<form id="formular2" action="jy/record/updateRecord" method="post">
				<div class="Growth_Portfolio_bottom">
					<p>
						<label for="" style="width: 85px;"><b style="left:-7px;">*</b>档案袋名称：</label>
						<input style="border:1px #9f9f9f solid;height:22px;margin-left: 4px;" type="text" name="name" class="input_txt validate[required,minSize[0]] text-input validate[optional,maxSize[8]] text-input" ><strong>注：最多可输入8个字</strong>
					</p>
					<div class="clear"></div>
					<p style="margin-top:30px;">
						<label for="" id="lab" style="width: 85px;">档案袋简介：</label>
						<textarea style="width:271px;float:left;margin-left: 5px;" name="desc" cols="30" rows="4" class="validate[optional,maxSize[50]] text-input"></textarea><br />
						<strong class="strong_sty" style="margin-left:80px;" >注：最多可输入50个字</strong>
					</p>
					<div class="clear"></div>
					<div class="btn_bottom" style="margin-top:20px;text-align:center;">
						<input id="button2" type="button" class="btn_bottom_3" value="修改"> 
						<input type="button" class="btn_bottom_4" value="不改了" onclick="$('.dialog_close').click();">
					</div>
					<input type="hidden"  id="id"  name="id">
				</div>
			</form>
		</div>
	</div>
</div>

<input type="hidden" value="${authorId}" id="authorId"/>
<div id="box_comment"  class="dialog">
	<div class="dialog_wrap">
		<div class="dialog_head">
			<span class="dialog_title">评论意见</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<iframe name="iframe_scan" id="iframe_comment" style="width:940px;height:600px;" frameborder="0"></iframe>
		</div>
	</div>
</div>
<body>
<div class="wrapper">
	<div class="jyyl_top"><ui:tchTop style="1" modelName="成长档案袋" hideMenuList="false"></ui:tchTop></div>
	<div class="jyyl_nav">
		<h3>当前位置：<jy:nav id="czdad"></jy:nav></h3>
	</div>
	<div class='record_cont'>
		<div class='record_cont1'>
			<h3 class="record_cont1_h3">
				<c:choose>
				      <c:when test="${_CURRENT_SPACE_.subjectId==0}">
				          <span>学科：无</span>
				      </c:when>
				      <c:otherwise>
					      <span>学科：<jy:dic key="${_CURRENT_SPACE_.subjectId}"></jy:dic></span>
				      </c:otherwise>
				   </c:choose>
				   <c:choose>
					      <c:when test="${_CURRENT_SPACE_.gradeId==0}">
					          <span>年级：无</span>
					      </c:when>
					      <c:otherwise>
						     <span>年级：<jy:dic key="${_CURRENT_SPACE_.gradeId}"></jy:dic></span>
					      </c:otherwise>
				   </c:choose>
				<c:choose>
				      <c:when test="${empty _CURRENT_SPACE_.bookId}">
				           <span>版本：无</span>
				      </c:when>
				      <c:otherwise>
					       <span>版本：<jy:ds key="${_CURRENT_SPACE_.bookId}" className="com.tmser.tr.manage.meta.service.BookService" var="book">
					              ${book.formatName }
					        	</jy:ds>
					        </span>
				      </c:otherwise>
				   </c:choose>
			</h3>
			<div class="Growth_Portfolio_cont">
				<c:forEach items="${recordBagList }" var="bag">
					<div class="Growth_Portfolio_model">
						<div class="Growth_Portfolio_model_bg main" type="${bag.type}"  id="${bag.id}">
							<span title="${bag.desc }"  >
								${bag.name }<br />
								<b>(${bag.resCount })</b>
							</span>
						</div>
						<div class="show_p">
							<ol>
								<c:if  test="${bag.type==1 }"><!-- 系统档案袋不能修改和删除 -->
									<li class="li_edit"  title="修改" id="${bag.id }"  name="${bag.name }"  desc="${bag.desc }"></li>
									<li class="li_del"  title="删除" id="${bag.id }"  name="${bag.name }"  status="${bag.status }"></li>
								</c:if>
								<c:if  test="${bag.share==0 }"><!-- 未分享状态 -->
									<li class="li_share"  title="分享" id="${bag.id }"  share="${bag.share }" ></li>
								</c:if>
								<c:if  test="${bag.share==1 && bag.isPinglun==0}"><!-- 分享状态 -->
									<li class="li_share_cancle"  title="取消分享" id="${bag.id }"  share="${bag.share }" ></li>
								</c:if>
								
								<c:if  test="${(bag.pinglun==0||bag.pinglun==null)&&bag.isPinglun==1}"><!-- 评论状态 -->
									<li class="li_ping"  onclick="showCommentListBox('7','${bag.id}','false')"></li>
								</c:if>
								<c:if  test="${bag.pinglun==1}"><!-- 评论状态 -->
									<li class="li_ping"  onclick="showCommentListBox('7','${bag.id}','true')">
										<span class="spot"></span>
									</li>
								</c:if>
							</ol>
						</div>
					</div>
				</c:forEach>
				
				<div class="Growth_Portfolio_model_1">
					<div class="add"></div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	<div class="clear"></div><br/><br/><br/>
	<ui:htmlFooter></ui:htmlFooter>
</div> 
</body>
<script type="text/javascript">
	require(['jquery','jp/jquery-ui.min','jp/jquery.form.min','jp/jquery.validationEngine-zh_CN','jp/jquery.validationEngine.min','list'],function(){});
</script>
</html>