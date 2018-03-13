<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<meta charset="UTF-8">
	<ui:mHtmlHeader title="查阅意见"></ui:mHtmlHeader>
	<link rel="stylesheet" href="${ctxStatic }/m/comment/css/comment.css" media="screen">
	<ui:require module="../m/comment/js"></ui:require>
</head>
<body> 
<div class="opinions_comment_title">
	<h3>查阅意见</h3>
	<span class="close"></span>
</div>
<div class="opinions_comment_content" id="wrap3">
	<div id="scroller">
		<div class="consult_opinion">
			<div class="consult_opinion_left">
				<img src="${ctxStatic }/m/comment/images/img.png" alt="">
			</div>
			<div class="consult_opinion_right">
				<span>杨志刚：</span>
				<strong>2015-07-02</strong>
				<div class="comment_content1">
				课件的交互性很强，整个画面的设计比较合里理，内容也符合学生的接受程个个度。
				课件的交互性很强，整个画面的设计比较合里理内容也符合学生的接受程度。
				</div>
				<div class="reply">回复</div>
				<div class="clear"></div>
				<div class="reply_opinion">
					<div class="reply_opinion_left">
						<img src="${ctxStatic }/m/comment/images/img1.png" alt="">
					</div>
					<div class="reply_opinion_right">
						<span>李宏毅：</span>
						<strong>2015-07-02</strong>
						<div class="reply_content">
						课件的交互性很强，整个画面的设计比较合里理，内容也符合学生的接受程个个度。
						课件的交互性很强，整个画面的设计比较合里理内容也符合学生的接受程度。
						</div>
						<div class="reply1">回复</div>
					</div>
				</div>
			</div> 
		</div>
		<div class="consult_opinion">
			<div class="consult_opinion_left">
				<img src="${ctxStatic }/m/comment/images/img.png" alt="">
			</div>
			<div class="consult_opinion_right">
				<span>杨志刚：</span>
				<strong>2015-07-02</strong>
				<div class="comment_content">
				课件的交互性很强，整个画面的设计比较合里理，内容也符合学生的接受程个个度。
				课件的交互性很强，整个画面的设计比较合里理内容也符合学生的接受程度。
				</div>
				<div class="reply">回复</div>
			</div>
		</div>
		<div class="consult_opinion">
			<div class="consult_opinion_left">
				<img src="${ctxStatic }/m/comment/images/img.png" alt="">
			</div>
			<div class="consult_opinion_right">
				<span>杨志刚：</span>
				<strong>2015-07-02</strong>
				<div class="comment_content">
				课件的交互性很强，整个画面的设计比较合里理，内容也符合学生的接受程个个度。
				课件的交互性很强，整个画面的设计比较合里理内容也符合学生的接受程度。
				</div>
				<div class="reply">回复</div>
				<div class="clear"></div>
				<div class="reply_opinion">
					<div class="reply_opinion_left">
						<img src="${ctxStatic }/m/comment/images/img1.png" alt="">
					</div>
					<div class="reply_opinion_right">
						<span>李宏毅：</span>
						<strong>2015-07-02</strong>
						<div class="reply_content">
						课件的交互性很强，整个画面的设计比较合里理，内容也符合学生的接受程个个度。
						课件的交互性很强，整个画面的设计比较合里理内容也符合学生的接受程度。
						</div>
						<div class="reply1">回复</div>
					</div>
				</div>
			</div>
		</div>
		<div style="width:100%;height:1.5rem;"></div>
	</div>
</div> 
</body>
<script type="text/javascript"> 
require(['jquery','jp/jquery-ui.min','js'],function($){	
}); 
</script>
</html>