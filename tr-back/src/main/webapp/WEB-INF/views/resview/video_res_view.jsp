<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="${ctx}" />
<title>查看资源</title>
<style>
html,body{margin:0px; 
height:100%;
}
</style>
<script type="text/javascript" src="${ctxStatic }/lib/ckplayer/ckplayer.js" charset="utf-8"></script>

</head>
<body>
    	 <div style="height:100%;width:100%;overflow-y:hidden" id="play_container">
    	</div>
</body>
<script type="text/javascript">
    var flashvars={
        f:'${ctx}jy/manage/res/download/${resid }',
        c:0,
        loaded:'loadedHandler'
    };
    var video=['${ctx}jy/manage/res/download/${resid }->video/${ext}'];
    CKobject.embed('${ctxStatic }/lib/ckplayer/ckplayer.swf','play_container','ckplayer_a1','100%','100%',false,flashvars,video);
</script>
</html>