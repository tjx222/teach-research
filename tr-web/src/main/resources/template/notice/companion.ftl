<#if type==1><#--添加好友-->
	<style type="text/css">
		.Details11{
			width: 190px;
			height: 106px;
			border: 1px #c1c1c1 solid;
			background: #f1f1f1;
			float: left;
		}
		.Details11 .Details11_img{
			width: 80px;
		  	height: 95px;
		  	float: left;
		}
		.Details11 .Details11_img img{
			width: 60px;
			height: 60px;
			display: block;
			margin: 14px 8px;
			cursor: pointer;
		}
		.Details11 .Details11_span{
			  width: 110px;
		  	  height: 90px;
		 	 float: left;
		  	 margin: 5px 0 0 0;
		}
		.Details11 .Details11_span span{
			display: inline-block;
			width: 110px;
			line-height: 17px;
			font-size: 12px;
			white-space: nowrap;
			overflow: hidden;
			text-overflow:ellipsis;
		}
		.Details2{
			float: left;
			line-height: 95px;
			margin-left: 10px;
		}
	</style>
		<div class="Teaching_schedule_cont">
				<p>
					<a href="./jy/companion/companions/${companion.userIdCompanion }" target="_blank">
					<div class="Details11">
						<div class="Details11_img">
	                        <img src="${companion.photo!ctx+'/static/common/images/default.jpg'}" height="" width="" alt="">
						</div>
						<div class="Details11_span">
							<span title="${userName}">
								${userName}
							</span>
							<span title="${companion.highestRoleName!''}">职务:${highestRoleNameEllipsis!''}</span>
							<#if companion.profession?exists && companion.profession!=''>
								<span title="${companion.profession}">职称:${professionEllipsis!''}</span>
							</#if>
							<#if companion.isSameOrg==0>
								<span title="${companion.schoolNameCompanion!''}">${schollNameEllipsis!''}</span>
							</#if>
						</div>
					</div>
					</a>
					<div class="Details2">
						"<strong>${userName}</strong>"
						已添加您为好友，点击好友头像就可以给好友发送消息啦！
					</div>
				</p>
			</div>
<#elseif type==2> <#--移除好友-->
	<#if isSameOrg>
		您已被“${userName!''}”移除好友列表！
	<#else>
		您已被“${schooleName!''}-${userName!''}”移除好友列表！
	</#if>
	
<#elseif type==3> <#--发送留言-->
	<div>
		<iframe src="./jy/companion/messages/${userId }/page" onload="setCwinHeight(this,false,100)"frameborder="0"  style="border:none;width:100%;height:50rem;">
		</iframe>
	</div>
	
</#if>
