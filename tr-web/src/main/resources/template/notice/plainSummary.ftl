<#if type==1><#--提交计划总结-->
	<iframe id="fileView" src="jy/scanResFile?resId=${contentFileKey}" style="border:none;width:100%;height:850px;">
	</iframe>
<#elseif type==2> <#--计划总结评论-->
	<iframe id="commentBox" src="jy/comment/list?flags=false&authorId=${userId}&resType=${resType}&resId=${resId }"
					width="100%" height="850px;" style="border:none; overflow:hidden" scrolling="no"  ></iframe>
<#elseif type==3> <#--计划总结审阅-->
	<iframe id="checkView" style="border:none;width:100%;height:850px;" scrolling="no" src="jy/check/infoIndex?flags=false&resType=${resType }&authorId=${userId}&resId=${resId}"></iframe>
</#if>