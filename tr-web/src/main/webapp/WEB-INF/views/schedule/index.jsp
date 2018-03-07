<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${ctx}" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>日程表</title>
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<script src="${ctxStatic }/lib/requirejs/esl.js"></script>
 <link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/schedule/css/normalize.css">
 <link href="${ctxStatic }/modules/schedule/css/font-awesome.css" rel="stylesheet" />
    <!--[if IE 7]>
    <link href="${ctxStatic }/modules/schedule/css/font-awesome-ie7.css" rel="stylesheet" />
    <![endif]-->
    <!--[if lt IE 9]>
        <script src="${ctxStatic }/modules/schedule/js/html5shiv.min.js"></script>
    <![endif]-->
	<link href="${ctxStatic }/modules/schedule/css/cal.css" rel="stylesheet" />
    <link href="${ctxStatic }/modules/schedule/css/main.css" rel="stylesheet" />
    <script src="${ctxStatic }/lib/respond/respond.min.js"></script>
<script type="text/javascript">
var _WEB_CONTEXT_ = '${pageContext.request.contextPath }';
require.config({
	'baseUrl': '${ctx}${ctxStatic}/modules/schedule',
    'packages': [
       {
        'name': 'jquery',
        'location': './js',
        'main': 'jquery.min.js'
    	},
		{
		    'name': 'common',
		    'location': '${ctx}${ctxStatic}/common/js',
		    'main': 'global'
		},
        {
            'name': 'hogan',
            'location': '${ctx}${ctxStatic}/lib/hogan',
            'main': 'hogan'
        },
        {
            'name': 'zxui',
            'location': '${ctx}${ctxStatic}/lib/zxui',
            'main': 'Control'
        },
        {
            'name': 'cal',
            'location': './js'
        },
        {
            'name': 'saber-emitter',
            'location': 'js',
            'main': 'emitter'
        }
    ],
    urlArgs: {
        'common': '20140303',
        'cal': '20131022'
    }
});
    require(['cal/main']);
	</script>
</head>
<body class="result-op">
<div id="main-wraper">
<div id="main">
<div id="ibd-cal-wraper" data-click='{"mod":"ical"}'>
<div class="ecl-ui-lunar c-clearfix" id="lunar"></div>
</div>
</div>
</div>
</body>
</html>