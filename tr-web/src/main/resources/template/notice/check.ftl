<input type="hidden" id="userId" value="${user.id}">

<#if resType==8||resType==9>
	<#assign scanUrl="./jy/planSummary/scanFiles/${resId}"/>
<#elseif resType==0||resType==1||resType==2>
	<#assign scanUrl="jy/check/lesson/${resType}/view?lesInfoId=${resId}"/>
<#elseif resType==3>
	<#assign scanUrl="jy/viewRes?resId=${resId}"/>
<#elseif resType==5>
	<#assign scanUrl="jy/activity/views?activityId=${resId}"/>
</#if>
<style type="text/css">
.Review_comments_1 {
	background: #fcfcfc none repeat scroll 0 0;
	border: 1px solid #d0d0d0;
	border-radius: 6px;
	height: auto;
	margin: 15px auto;
	width: 986px;
}

.Review_comments_1 dl {
	float: left;
	height: 100px;
	width: 100px;
}

.Review_comments_1 dl dd {
	height: 65px;
	width: 100px;
}

.Review_comments_1 dl dd img {
	display: block;
	height: 65px;
	margin: 5px auto;
	width: 60px;
}

.Review_comments_1 dl dt {
	font-size: 12px;
	height: 25px;
	line-height: 25px;
	text-align: center;
}

.Review_r {
	float: right;
	height: auto; 
	width: 884px;
}

.Review_r h4 {
	height: 30px;
	line-height: 30px;
}

.Review_r h5 {
	color: red;
	font-size: 14px;
	height: auto;
	line-height: 21px;
	word-break:break-all;
}

.Review_r h4 span {
	color:#014efd
}

.Review_r h6 {
	float: right;
	height: 25px;
	line-height: 25px;
}

.Review_r h6 span {
	color:#014efd
	font-weight: bold;
	margin-left: 10px;
}
</style>

<div class="Review_comments_1">
	<dl>
		<dd>
			<a href="./jy/companion/companions/${user.id!''}" target="_blank"> <img
				alt="" src="${user.photo!ctx+'/static/common/images/default.jpg'}">
			</a>
		</dd>
		<dt>${user.name!''}</dt>
	</dl>
	<div class="Review_r">
		<h4>
			查阅${typeName}：<a href="${scanUrl!''}" target="_blank">
			<span>
			<#if title?length gt 32>
			${title[0..32]!''}...
			<#else>
			${title!''}
			</#if> 
			</span>
			</a>
		</h4>
		<h5>${content!''}</h5>
	</div>
	<div class="clear"></div>
</div>




