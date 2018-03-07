<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="成长档案袋"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/record/css/record.css" media="screen">
	<ui:require module="../m/record/js"></ui:require>
</head>
<body>
<input type="hidden" id="authorId" value="${authorId}"/>
<div class="add_portfolio_wrap">
	<div class="edit_portfolio">
		<div class="edit_portfolio_title">
			<h3>添加档案袋</h3>
			<span class="close"></span>
		</div>
		<div class="edit_portfolio_content">
			<form id="formular" action="jy/record/addRecord">
				<ui:token></ui:token>
				<div class="form_input">
					<label><a>*</a>请输入档案袋名称</label>
					<p>
						<input type="text" name="name" maxlength="8">
						<a>注：最多可输入8个字</a>
					</p> 
				</div>
				<div class="form_input">
					<label><a></a>请输入档案袋简介</label>
					<p>
						<textarea cols="100" rows="3" name="desc" maxlength="50"></textarea>
						<a>注：最多可输入50个字</a>
					</p> 
				</div>
				<div class="border_bottom" style="margin: 3rem auto;"></div>
				<div>
					<input type="button" class="btn_preserve" value="保存">
				</div>
			</form>
		</div>
	</div>
</div>
<div class="edit_portfolio_wrap">
	<div class="edit_portfolio">
		<div class="edit_portfolio_title">
			<h3>修改档案袋</h3>
			<span class="close"></span>
		</div>
		<div class="edit_portfolio_content">
			<form id="formular2" action="jy/record/updateRecord" method="post">
			<ui:token></ui:token>
			<input type="hidden"  id="id"  name="id">
				<div class="form_input">
					<label><a>*</a>请输入档案袋名称:</label>
					<p>
						<input type="text" placeholder="请输入档案袋名称" id="name" name="name">
						<a>注：最多可输入8个字</a>
					</p> 
				</div>
				<div class="form_input">
					<label><a></a>请输入档案袋简介:</label>
					<p>
						<textarea cols="100" rows="3" class="desc" id="desc" name="desc"></textarea>
						<a>注：最多可输入50个字</a>
					</p> 
				</div>
				<div class="border_bottom" style="margin: 3rem auto;"></div>
				<div>
					<input type="button" class="btn_confirm" value="修改">
					<input type="button" class="btn_cencel" value="取消">
				</div>
			</form>
		</div>
	</div>
</div>
<div class="share_upload_wrap">
	<div class="share_upload">
		<div class="share_upload_title">
			<h3>分享</h3>
			<span class="close"></span>
		</div>
		<div class="share_upload_content">
			<div class="share_width">
				<q class="dlog_share" ></q>
				<span>您确定要分享吗？分享后，您的小伙伴就可以看到喽！</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定" id="share_confirm">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div>
	</div>
</div>
<div class="del_upload_wrap">
	<div class="del_upload">
		<div class="del_upload_title">
			<h3>删除档案袋</h3>
			<span class="close"></span>
		</div>
		<div class="del_upload_content">
			<div class="del_width">
				<q></q>
				<span>您确定要删除该档案袋吗？</span>
			</div>
			<div class="border_bottom"></div>
			<div>
				<input type="button" class="btn_confirm" value="确定" id="del_confirm">
				<input type="button" class="btn_cencel" value="取消">
			</div>
		</div> 
	</div>
</div>
<div class="mask"></div>
<div class="more_wrap_hide" onclick='moreHide()'></div>
<div id="wrapper">
	<header>
		<span onclick="javascript:window.history.go(-1);"></span>成长档案袋
		<div class="more" onclick="more()"></div>
	</header>
	<section>
		<div class="record_content">
			<div class="record_content_top">
				<div class="record_content_top_l">
				<c:choose>
			      <c:when test="${_CURRENT_SPACE_.subjectId==0}">
			                     学科：无
			      </c:when>
			      <c:otherwise>
				              学科：<jy:dic key="${_CURRENT_SPACE_.subjectId}"></jy:dic>
			      </c:otherwise>
			   </c:choose>
				</div>
				<div class="record_content_top_c">
				<c:choose>
			      <c:when test="${_CURRENT_SPACE_.gradeId==0}">
			                         年级：无
			      </c:when>
			      <c:otherwise>
				              年级：<jy:dic key="${_CURRENT_SPACE_.gradeId}"></jy:dic>
			      </c:otherwise>
			    </c:choose>
				</div>
				<div class="record_content_top_r">
				<c:choose>
			      <c:when test="${empty _CURRENT_SPACE_.bookId}">
			                         版本：无
			      </c:when>
			      <c:otherwise>
				              版本：<jy:ds key="${_CURRENT_SPACE_.bookId}" className="com.tmser.tr.manage.meta.service.BookService" var="book">
				              ${book.formatName }
				              </jy:ds>
			      </c:otherwise>
			    </c:choose>
				</div>
			</div>
			<div class="record_content_bottom" id="record_cont">
				<div id="scroller">
					<div class="record_content_bottom_list">
					<c:forEach  items="${recordBagList }" var="bag">
						<div class="record_list" type="${bag.type}"  id="${bag.id}">
							<div class="record_list_span">
								<span> ${bag.name } </span>
								<span> (${bag.resCount }) </span>
							</div>
							<c:if  test="${bag.type==0 }">
								<c:if  test="${bag.share==0 }">
									<div class="record_share" id="${bag.id }"></div> 
								</c:if>
								<c:if  test="${bag.share==1 && bag.isPinglun==0}">
									<div class="qx_record_share" id="${bag.id }"></div> 
								</c:if>
								<c:if  test="${bag.share==1 && bag.isPinglun==1}">
									<div class="jz_qx_record_share" id="${bag.id }"></div> 
								</c:if>
								<c:if  test="${bag.pinglun==1&&bag.isPinglun==1}">
								<div class="record_ping" bagId="${bag.id}" flag="true"><span></span></div>
								</c:if>
								<c:if  test="${bag.pinglun==1&&bag.isPinglun==0}">
								<div class="record_ping" bagId="${bag.id}" flag="false"></div>
								</c:if>
							</c:if>
							<c:if  test="${bag.type==1 }">
								<div class="record_more"></div> 
								<c:if  test="${bag.pinglun==1&&bag.isPinglun==1}">
								<div class="record_ping" bagId="${bag.id}" flag="true"><span></span></div>
								</c:if>
								<c:if  test="${bag.pinglun==1&&bag.isPinglun==0}">
								<div class="record_ping" bagId="${bag.id}" flag="false"></div>
								</c:if>
								<div class="record_option" style="display:none;">
									<div class="record_edit" title="编辑" id="${bag.id }"  name="${bag.name }"  desc="${bag.desc }"></div>
									<div class="record_del" title="删除" id="${bag.id }" name="${bag.name }" status="${bag.status }"></div>
									<c:if  test="${bag.share==0 }">
										<div class="record_share1" id="${bag.id }"></div>
									</c:if>
									<c:if  test="${bag.share==1 && bag.isPinglun==0}">
										<div class="qx_record_share1" id="${bag.id }"></div> 
									</c:if>
									<c:if  test="${bag.share==1 && bag.isPinglun==1}">
										<div class="jz_qx_record_share1" id="${bag.id }"></div> 
									</c:if>
									<div class="record_close" ></div>
								</div>
							</c:if>
						</div>
					</c:forEach>
						
						<div class="add_cour">
							<div class="add_cour_div">
								<div class="add_cour_div_top">
									<div class="add_cour_div_top_img"></div> 
								</div>
								<div class="add_cour_div_bottom">添加档案袋</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</div>
<!-- 评论意见 -->
<div class="opinions_comment_wrap" id="comment_div" style="display: none;" >
	<div class="opinions_comment">  
		<div class="opinions_comment_title">
			<h3>评论意见</h3>
			<span class="close"></span>
		</div>
		<iframe id="iframe_comment" style="border:none;overflow:hidden;width:100%;height:35rem;" src=""></iframe>
	</div>
</div>
</body>
<script type="text/javascript">
	require(["zepto",'js'],function($){	
	});
</script>
</html>