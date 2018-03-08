<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<ui:htmlHeader title="教研后台管理系统"></ui:htmlHeader>
<link href="${ctxStatic }/lib/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<link rel="stylesheet" href="${ctxStatic }/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css" media="all">
<link rel="stylesheet" href="${ctxStatic }/lib/ztree/css/zTreeStyle/jydemo.css" type="text/css" media="all">
<script type="text/javascript">
$(function(){
	DWZ.init("${ctxStatic }/modules/back/dwz.frag.xml", {
		loginUrl:"jy/uc/ajaxlogin", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"page.currentPage", numPerPage:"page.pageSize", orderFlag:"order", orderField:"flago", orderDirection:"flags"}, //【可选】
		keys: {statusCode:"statusCode", message:"message"}, //【可选】
		ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"${ctxStatic }/modules/back/css"}); // themeBase 相对于index页面的主题base路径
		}
	});
});
</script>
</head>
<body scroll="no">
	<div id="layout">
	
		<div id="header">
			<div class="headerNav">
				<a class="logo" href="${ctx }">标志</a>
				<div class="logoright">
					<ul class="nav">
					<c:if test="${not empty orgList }">
						<li id="switchEnvBox"><a href="javascript:">（<span>${sessionScope._CURRENT_ORG_.shortName }</span>）切换机构</a>
							<ul>
								<c:forEach items="${orgList }" var="org">
									<li><a href="${ctx }jy/back/select.json?orgId=${org.id }"  onclick="javascript:navTab.closeAllTab();" >${org.shortName }</a></li>
								</c:forEach>
							</ul>
						</li>
					</c:if>
						<li><a href="jy/uc/modifypsd" target="dialog" height="400" width="520" mask="true">${sessionScope._CURRENT_USER_.name }</a></li>
						<li><a href="jy/logout">退出</a></li>
					</ul>
				</div>
				
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>
					
				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2><span>Folder</span>系统功能</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
						<shiro:hasPermission name="zzjg">
							<li><a>组织机构管理</a>
								<ul>
									<shiro:hasPermission name="zzjg_xzqy">
										<li><a href="${ctx}jy/back/zzjg/goOrgTree" target="navTab" rel="orgTree">行政区域</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="zzjg_xxgl">
										<li><a href="${ctx}jy/back/zzjg/goSchTree" target="navTab" rel="orgNa">学校管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="zzjg_xzzzjg">
										<li><a href="${ctx}jy/back/zzjg/goxzjgTree" target="navTab" rel="org_unit">行政组织机构</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission name="jxtx">
							<li><a>教学体系设置</a>
								<ul>
									<shiro:hasPermission name="jxtx_xdgl">
										<li><a href="${ctx}jy/back/jxtx/phaseIndex?type=3" target="navTab" rel="phase">学段管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="jxtx_njgl">
										<li><a href="${ctx}jy/back/jxtx/gradeIndex" target="navTab" rel="grade">年级管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="jxtx_xkgl">	
										<li><a href="${ctx}jy/back/jxtx/subjectIndex" target="navTab" rel="subject">学科管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="jxtx_xxlxgl">
										<li><a href="${ctx}jy/back/jxtx/show_schoolType" target="navTab" rel="schType">学校类型管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="jxtx_cbsgl">
										<li><a href="${ctx}jy/back/jxtx/cbsIndex" target="navTab" rel="cbsgl">出版社管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="jxtx_jcgl">
										<li><a href="${ctx}jy/back/jxtx/jcgl_index" target="navTab" rel="jcgl">教材管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="jxtx_mlgl">
										<li><a href="${ctx}jy/back/jxtx/catalog_index" target="navTab" rel="catalog">目录管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="jxtx_xtjambgl">
										<li><a href="${ctx}jy/back/jxtx/jamb/index" target="navTab" rel="jambId">教案模板管理</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission name="yygl">
							<li><a>模块管理</a>
								<ul>
									<li><a href="${ctx}jy/back/yygl/index" target="navTab" rel="applicationManage">模块管理</a></li>
								</ul>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission name="jsgl">
							<li><a>角色管理</a>
								<ul>
									<shiro:hasPermission name="jsgl_roleType">
										<li><a href="${ctx}jy/back/jsgl/jslx/show_roleType" target="navTab" rel="roleType1">角色类型管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="jsgl_role">
										<li><a href="${ctx}jy/back/jsgl/show_role" target="navTab" rel="role1">基础角色管理</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission name="solution">
							<li><a>定制服务方案</a>
								<ul>
									<li><a href="${ctx}jy/back/solution/index" target="navTab" rel="solution1">定制服务方案</a></li>
								</ul>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission name="yhgl">
							<li><a>用户管理</a>
								<ul>
									<shiro:hasPermission name="yhgl_xt">
										<li><a href="${ctx}jy/back/yhgl/sys/index" target="navTab" rel="system_user">系统用户管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="yhgl_qu">
										<li><a href="${ctx}jy/back/yhgl/areaUserIndex" target="navTab" rel="area_user">区域用户管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="yhgl_xx">
										<li><a href="${ctx}jy/back/yhgl/schUserIndex" target="navTab" rel="school_user">学校用户管理</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="zygl">
							<li><a>资源管理</a>
								<ul>
									<shiro:hasPermission name="zygl_yzzy">
										<li><a href="${ctx}jy/back/zygl/yz/yzresourceIndex" target="navTab" rel="yzresource">预制资源管理</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission name="rzgl">
							<li><a>日志管理</a>
								<ul>
									<shiro:hasPermission name="rzgl_dlrz">
										<li><a href="${ctx}/jy/back/rzgl/logginLogIndex" target="navTab" rel="dlrz">登录日志</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="rzgl_czrz">
										<li><a href="${ctx}/jy/back/rzgl/operateLogIndex" target="navTab" rel="czrz">操作日志</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
							</shiro:hasPermission>
						<shiro:hasPermission name="ptgg">
							<li><a>平台公告</a>
								<ul>
									<shiro:hasPermission name="ptgg_tzgg">
										<li><a href="${ctx}/jy/back/ptgg/tzgg/ptgg_index" target="navTab" rel="tzgg">通知公告</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="ptgg_tpxw">
										<li><a href="${ctx}/jy/back/ptgg/tpxw/tpxw_index" target="navTab" rel="tpxw">图片新闻</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="ptgg_sygg">
										<li><a href="${ctx}/jy/back/ptgg/sygg/flatformAnnouncementList" target="navTab" rel="syggId">首页广告</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
						</shiro:hasPermission>
						<shiro:hasPermission name="operation">
							<li><a>运营管理</a>
								<ul>
									<shiro:hasPermission name="operation_yygl">
										<li><a href="${ctx}/jy/back/operation/index" target="navTab" rel="operationId">运营管理</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
						</shiro:hasPermission>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>机构配置管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<shiro:hasPermission name="xxsy">
							    <li><a>学校首页管理</a>
								<ul>
									<shiro:hasPermission name="xxsy_htgl">
										<li><a href="${ctx}/jy/back/xxsy/redhm/index" target="navTab" rel="redhead">红头管理</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="xxsy_xzfc">
										<li><a href="${ctx}jy/back/xxsy/show/list?type=master" target="navTab" rel="schoolShow">校长风采</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="xxsy_xxgk">
										<li><a href="${ctx}jy/back/xxsy/show/list?type=overview" target="navTab" rel="schoolShow">学校概况</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="xxsy_xxyw">
										<li><a href="${ctx}jy/back/xxsy/show/list?type=bignews" target="navTab" rel="schoolShow">学校要闻</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="xxsy_indexhfgg">
										<li><a href="${ctx}jy/back/xxsy/hfgg/schoolBannerList" target="navTab" rel="schoolBanner">横幅广告</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="xxsy_indextpxw">
										<li><a href="${ctx}jy/back/xxsy/tpxw/tpxw_index" target="navTab" rel="picNews">图片新闻</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="xxsy_indextzgg">
										<li><a href="${ctx}jy/back/xxsy/tzgg/ptgg_index" target="navTab" rel="indexnoticeAnnounce">通知公告</a></li>
									</shiro:hasPermission>
								</ul>
							</li>
						    </shiro:hasPermission>
						    <shiro:hasPermission name="xxconfig">
						     <li><a>学校配置管理</a>
						     <ul>
									<shiro:hasPermission name="xxconfig_class">
										<li><a href="${ctx}jy/back/schconfig/clss/index" target="navTab" rel="schClss">班级管理</a></li>
									</shiro:hasPermission>
							 </ul>
						     </li>
						    </shiro:hasPermission>
						    <!-- 新增菜单 -->
						    <shiro:hasPermission name="zdy_jxtx">
							    <li><a>教学信息管理</a>
							    	<ul>
							    		<shiro:hasPermission name="zdy_jxtx_subject">
											<li><a href="${ctx}jy/back/schconfig/subject/index" target="navTab" rel="schClss">学科管理</a></li>
										</shiro:hasPermission>
										<shiro:hasPermission name="zdy_jxtx_publisher">
											<li><a href="${ctx}jy/back/schconfig/publisher/index" target="navTab" rel="schPublichser">出版社管理</a></li>
										</shiro:hasPermission>
										<shiro:hasPermission name="zdy_jxtx_commdity">
											<li><a href="${ctx}jy/back/schconfig/commdity/index" target="navTab" rel="schCommdity">教材管理</a></li>
										</shiro:hasPermission>
										<shiro:hasPermission name="zdy_jxtx_catalog">
											<li><a href="${ctx}jy/back/schconfig/catalog/index" target="navTab" rel="schCatalog">目录管理</a></li>
										</shiro:hasPermission>
									</ul>
							    </li>
						    </shiro:hasPermission>
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>系统管理</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
							<shiro:hasPermission name="monitor">
							<li><a>系统监控</a>
								<ul>
									<shiro:hasPermission name="monitor_cache">
										<li><a href="${ctx}/jy/back/monitor/ehcache" target="navTab" rel="moniter-cache">缓存监控</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="monitor_jvm">
										<li><a href="${ctx}/jy/back/monitor/jvm" target="navTab" rel="moniter-jvm">虚拟机监控</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="monitor_sql">
										<li><a href="${ctx}/jy/back/monitor/sql" target="navTab" rel="moniter-sql">sql执行</a></li>
									</shiro:hasPermission>
									<%--<shiro:hasPermission name="monitor_app"> --%>
									<li><a href="${ctx}/jy/back/monitor/app/index" target="navTab" rel="moniter_app">应用对接</a></li>
<%-- 									</shiro:hasPermission> --%>
								</ul>
							</li>
						</shiro:hasPermission>
						
						<shiro:hasPermission name="task_manager">
							<li><a href="${ctx}/jy/back/task/index" target="navTab" rel="task_manager">定时任务</a>
							</li>
						</shiro:hasPermission>
						</ul>
					</div>
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox" >
					<div class="page unitBox" id="welcome">
					</div> 
				</div>
			
			</div>
		</div>

	</div>

	<div id="footer">....</div>

</body>
<script src="${ctxStatic }/lib/uploadify/scripts/jquery.uploadify.min.js" type="text/javascript"></script>
<script src="${ctxStatic }/lib/jquery/jquery.dragsort.min.js" type="text/javascript"></script>
<script src="${ctxStatic }/lib/kindeditor/kindeditor-min.js" type="text/javascript"></script>
<script src="${ctxStatic }/lib/kindeditor/editor.js" type="text/javascript"></script>
<script src="${ctxStatic }/lib/kindeditor/zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic }/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/chart/raphael-min.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/chart/g.raphael.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/chart/g.line.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/tablexport/base64.js"></script>
<script type="text/javascript" src="${ctxStatic }/lib/tablexport/tablexport.js"></script>
<script type="text/javascript">
var TREE_DATA = [];
function loadZzjgOrgTree(treeContainerId,treeSet,flag,nocheck){
	var orgZNode =[]; 
	if(!nocheck)
		orgZNode.push({id:0,pId:-1,name:"行政区域",title:"行政区域",open:true});
if(TREE_DATA.length == 0){
	$.ajax({
		type:"post",
		async : false,
		cache:false,
		dataType : "json",
		url : "${pageContext.request.contextPath}/jy/back/zzjg/orgFindTree", 
		success : function(data){ 
			TREE_DATA = data;
			init_tree(data,orgZNode,treeContainerId,treeSet,flag,nocheck);
		}
	});
}else{
	init_tree(TREE_DATA,orgZNode,treeContainerId,treeSet,flag,nocheck);
}

}
function init_tree(data,orgZNode,treeContainerId,treeSet,flag,nocheck){
	$.each(data,function(n,d){ 
		orgZNode.push({ 
			id:d.id, 
			name:d.name, 
			title:d.name,
			pId:d.parentId,
			code:d.code,
			flag:flag,
			nocheck:nocheck?true:false
		})
	});
	$.fn.zTree.init($("#"+treeContainerId), treeSet, orgZNode);
}

function searchAreaC(treeContainerId,treeSet,treeLook,zNodeData,flag){
	$("#"+treeContainerId).empty();
	$.fn.zTree.destroy(treeContainerId);
	if($.trim($("#"+treeLook).val())!=""){
		$.ajax({ 
			type : "post",
			dataType : "json",
			cache:false,
			data : {"name":$.trim($("#"+treeLook).val())},
			url : "${pageContext.request.contextPath}/jy/back/zzjg/searCodition", 
			success : function(data){ 
				var zNodeData = [];
				zNodeData.push({id:0,pId:-1,name:"行政区域",title:"行政区域",flag:"org",open:true});
				$.each(data,function(n,obj){ 
					zNodeData.push({ 
						id:obj.id, 
						name:obj.name, 
						pId:obj.parentId,
						flag:flag
					}); 
				}); 
				$("#"+treeContainerId).addClass("ztree");
				$.fn.zTree.init($("#"+treeContainerId), treeSet,zNodeData);
				var treeObj = $.fn.zTree.getZTreeObj(treeContainerId);
				treeObj.expandAll(true);
			}
		});
	}else{
		loadZzjgOrgTree(treeContainerId,treeSet,flag);
	}
}

function toggleOptionShow(obj,arrShow,arrHide){
	function arrHandle(arr,type){
		if($.isArray(arr)){
			var len=arr.length;
			for(i=0;i<len;i++){
				var optionNow=obj.find("option#"+ arr[i]);
				var optionP=optionNow.parent("span");
				if(type=="show"){					
					if(optionP.size()){
						optionP.children().clone().replaceAll(optionP); 
					}				
				}else{
					if(!optionP.size()){
						optionNow.wrap("<span style='display:none'></span>");
					}
				}
			}
		}
	}
	arrHandle(arrShow,"show");
	arrHandle(arrHide,"hide");
}
</script>
</html>