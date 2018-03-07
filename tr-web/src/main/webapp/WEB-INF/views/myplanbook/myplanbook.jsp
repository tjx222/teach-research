<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta charset="UTF-8">
<ui:htmlHeader title="我的备课本"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic}/modules/myplanbook/css/index.css" media="screen">
<link rel="stylesheet" href="${ctxStatic}/modules/myplanbook/css/ztree/zTreeStyle.css" media="all">
<link rel="stylesheet" href="${ctxStatic}/modules/myplanbook/css/list.css" >
<script type="text/javascript" src="${ctxStatic}/lib/ztree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/lib/ztree/js/jquery.ztree.excheck-3.5.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/lib/ztree/js/jquery.ztree.exedit-3.5.min.js"></script>
<ui:require module="myplanbook/js"></ui:require>
<script type="text/javascript">
$(document).ready(function(){
	//默认展开上次操作的章节树
	if(${lessonPlan != null}){
		showTree('${lessonPlan.bookId}');
		$("#div_${lessonPlan.bookId}").attr("class","headers").find("span.arrow").attr("class","arrow down");
		$("#info_${lessonPlan.bookId}").find("ul.menu").fadeIn(100);
		var defaultNodeArray = ztreeObj.getNodesByParam("lessonId",'${lessonPlan.lessonId}');
		ztreeObj.selectNode(defaultNodeArray[0],false);
	}else if(${currentBook != null}){
		showTree('${currentBook.comId}');
	}
	//if(${!empty lessonPlan}){
		$("#iframe1").load("${pageContext.request.contextPath }/jy/myplanbook/tomyplandetail?lessonId=${lessonPlan.lessonId}&"+Math.random());
	//}
});
require(['jquery','jp/jquery-ui.min','jp/jquery.blockui.min','myplanbook'],function(){});
var treeNodes;
var ztreeObj;
var countData;
var setting = {  
        data:{
        	simpleData: {
    			enable: true,
    			idKey: "lessonId",
    			pIdKey: "parentId",
    			rootPId: 0
    		},
    		key:{
    			name: "lessonName"
    		}
        },
        callback:{
        	onClick: whenClickTitle,
        	beforeClick: beforeClickIt
        },
        view:{
        	selectedMulti:false
        }
    };  
  
//点击课题
function whenClickTitle(event, treeId, treeNode){
	$("#iframe1").load(_WEB_CONTEXT_+"/jy/myplanbook/tomyplandetail?lessonId="+treeNode.lessonId +"&"+Math.random());
}
//在点击节点前判断是否是父节点，父节点不作操作
function beforeClickIt(treeId, treeNode, clickFlag){
	if(treeNode.isParent){
		if(!treeNode.open){
			closeAll(treeId, treeNode);
		}
		ztreeObj.expandNode(treeNode);
		return false;
	}
	return true;
}
//关闭其他
function closeAll(treeId, treeNode){
	var nodesList = ztreeObj.getNodesByFilter(function filter(node){
		return node.open;
	}, true,treeNode.getParentNode());
	var nodes = ztreeObj.transformToArray(nodesList);
	if(nodes!=null){
		for(var i=0;i<nodes.length;i++){
			ztreeObj.expandNode(nodes[i],false,true,false);
		}
	}
}
function showTree(bookId){
	if($("#div_"+bookId).hasClass("headers")){
		$("#div_"+bookId).attr("class","header").find("span.arrow").attr("class","arrow up");
		$("#info_"+bookId).find("ul.menu").slideUp();
	}else if($("#div_"+bookId).hasClass("header")){
		$(".expmenu2").find("ul.menu").slideUp();
		$(".expmenu2").find(".headers").attr("class","header");
		$(".expmenu2").find("span.arrow").attr("class","arrow up");
		$("#div_"+bookId).attr("class","headers").find("span.arrow").attr("class","arrow down");
		//获取课题树
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{bookId:bookId},
	        url:  _WEB_CONTEXT_+"/jy/myplanbook/getplanbookdetail.json",
	        error: function () {
	            alert('请求失败');  
	        },  
	        success:function(data){
	            treeNodes = data.lessonList; 
	            countData = data.countData;
	        }  
	    });
	    ztreeObj = $.fn.zTree.init($("#lessonTree_"+bookId), setting, treeNodes);
	    var htmlStr = '<span>教案：'+countData.lessonCount_jiaoan+'课/'+countData.jiaoanCount+'篇</span><span>课件：'+countData.lessonCount_kejian+'课/'+countData.kejianCount+'篇</span><span>反思：'+countData.lessonCount_fansi+'课/'+countData.fansiCount+'篇</span><span>已提交：'+countData.submitCount+'课</span><span>已分享：<b id="span_'+bookId+'">'+countData.shareCount+'篇</b></span>';
	    $("#countData_"+bookId).html(htmlStr);
		$("#info_"+bookId).find("ul.menu").slideDown();
	}
	
}
function closeCallBack(lessonId){
	$("#iframe1").load(_WEB_CONTEXT_+"/jy/myplanbook/tomyplandetail?lessonId="+lessonId +"&"+Math.random());
}
function closeCallBack1(){
	window.location.reload();
}
//分享后分享数+1
function fenxiangUp(){
	$("#span_${currentBook.comId}").html(parseInt($("#span_${currentBook.comId}").html())+1);
}
//分享后分享数-1
function fenxiangDown(){
	$("#span_${currentBook.comId}").html(parseInt($("#span_${currentBook.comId}").html())-1);
}

//********右边区域*************
function deleteClose(planId,cancel){
	if(cancel){
		$("#book_del").dialog("close");
	}else{
		$.ajax({  
	        async : false,  
	        type: 'POST',  
	        dataType : "json",  
	        data:{'planId':planId},
	        url:  _WEB_CONTEXT_+"/jy/myplanbook/deleteLessonPlan.json",
	        success:function(data){
	            if(data.result=='success'){
	            	$("#book_del").dialog("close");
	            	window.location.reload();
	            }else{
	            	$("#book_del").find(".del_info").html("删除失败，该资源可能已提交或分享");
					$("#bt_delete").attr("onclick","deleteClose("+planId+",'cancel')");
	            }
	        }  
	    });
	}
}
 /**
 * 删除备课资源
 */
function deleteIt(planId){
	$("#bt_delete").attr("onclick","deleteClose("+planId+")");
	$("#bt_cancel_delete").attr("onclick","deleteClose("+planId+",'cancel')");
	$("#book_del").dialog({width:450,height:250});
}
/*
 * 分享备课资源
 */
function shareIt(planId){
	$("#book_share").find(".del_info").html("您确定分享吗？");
	$("#querenbut").attr("onclick","sharingOk("+planId+")");
	$("#book_share").dialog({width:450,height:250});		
}
function sharingOk(planId){
	$.ajax({  
        async : false,  
        type: 'POST',  
        dataType : "json",  
        data:{'planId':planId},
        url:  _WEB_CONTEXT_+"/jy/myplanbook/shareLessonPlan.json",
        success:function(data){
            if(data.result=='success'){
            	parent.fenxiangUp();//分享数+1
            	window.location.reload();
            }else{
            	$("#book_share").find(".del_info").html("系统出错，分享失败！");
            	$("#querenbut").attr("onclick","sharingClose()");
            }
        }  
    });
}
function cancelShare(planId){
	$.ajax({  
        async : false,  
        type: 'POST',  
        dataType : "json",  
        data:{'planId':planId},
        url:  _WEB_CONTEXT_+"/jy/myplanbook/unShareLessonPlan.json",
        success:function(data){
            if(data.result== 0){
            	parent.fenxiangDown();//分享数-1
            	window.location.reload();
            }else if(data.result==1){
            	$("#book_share").find(".del_info").html("取消分享失败，该资源已被评论");
            	$("#querenbut").attr("onclick","sharingClose()");
            }else if(data.result==2){
            	$("#book_share").find(".del_info").html("系统出错");
            	$("#querenbut").attr("onclick","sharingClose()");
            }
        }  
    });
}
function sharingClose(){
	$("#book_share").dialog("close");
}
/**
 * 取消分享
 */
function unShareIt(planId){
	$("#book_share").find(".del_info").html("您确定要取消分享吗？");
	$("#querenbut").attr("onclick","cancelShare("+planId+")");
	$("#book_share").dialog({width:450,height:250});
}
/**
 * 修改教案
 */
function editIt(planId,planType,lessonId){
	if(planType=='0'){
		window.open(_WEB_CONTEXT_+"/jy/myplanbook/toEditLessonPlan?planId="+planId,"_blank");
	}else if(planType=='1'){
		window.open(_WEB_CONTEXT_+"/jy/courseware/index?planId="+planId+"&lessonId="+lessonId,"_blank");
	}else if(planType=='2'){
		window.open(_WEB_CONTEXT_+"/jy/rethink/index?planId="+planId+"&lessonId="+lessonId,"_blank");
	}
	
}
function scanLessonPlan(planId){
	window.open(_WEB_CONTEXT_+"/jy/myplanbook/scanLessonPlan?planId="+planId,"_blank");
}
function updateIcon_scan(obj){
	$(obj).attr("class","menu_li_5");
}
function updateIcon_comment(obj){
	$(obj).attr("class","menu_li_6");
}
</script>
</head>

	<div id="book_share" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">分享</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
				</div>
				<div class="BtnWrap">
					<input type="button" id="querenbut" value="确定" class="confirm">
				    <input type="button" value="取消" class="cancel" data="" onclick="sharingClose()">  
				</div> 
			</div>
		</div>
	</div>
	<div id="book_del" class="dialog"> 
		<div class="dialog_wrap"> 
			<div class="dialog_head">
				<span class="dialog_title">删除</span>
				<span class="dialog_close"></span>
			</div>
			<div class="dialog_content">
				<div class="del_info">
					删除后不可恢复，您确定删除吗？		
				</div>
				<div class="BtnWrap">
					<input type="button" value="确定" class="confirm" id="bt_delete">
				    <input type="button" value="取消" class="cancel" id="bt_cancel_delete"> 
				</div>
			</div>
		</div>
	</div>

<div id="submit_bkb" class="dialog"> 
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">提交上级</span>
			<span class="dialog_close" onclick="location.reload()"></span>
		</div>
		<div class="dialog_content">
			<div class="upload-bottom">
				<div class="upload-bottom_tab">
					<ul>
						<li class="upload-bottom_tab_blue" id="not_submit">未提交</li><li id="is_submit">已提交</li>
					</ul>
				</div>
				<div class="clear"></div>
			<iframe name="iframe2" id="iframe2" frameBorder="0" scrolling="no" style="width:800px;height:420px;border:0;"></iframe>
			</div>
		</div>
	</div>
</div>
<div id="book_option_show" class="dialog"> 
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">查阅意见列表</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<iframe name="checkedBox" id="checkedBox" onload="setCwinHeight(this,false,100)" style="width:100%;height:100%;border:none;" frameborder="0"></iframe>
		</div>
	</div>
</div>
<div id="book_review_show" class="dialog"> 
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">评论意见列表</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<iframe name="comment" id="commentBox" onload="setCwinHeight(this,false,100)" style="width:100%;height:100%;border:none;" frameborder="0"></iframe>
		</div>
	</div>
</div>
<div id="book_listen_show" class="dialog"> 
	<div class="dialog_wrap"> 
		<div class="dialog_head">
			<span class="dialog_title">听课意见列表</span>
			<span class="dialog_close"></span>
		</div>
		<div class="dialog_content">
			<iframe name="iframe_visit" id="iframe_visit" style="width:860px;height:400px;border:none;"></iframe>
		</div>
	</div>
</div>
	<div class="jyyl_top"> 
		<ui:tchTop style="1" modelName="我的备课本"></ui:tchTop>
	</div> 
	<div class="jyyl_nav">
		<h3>
			当前位置：
			<jy:nav id="wdbkb">
				<jy:param name="name" value="我的备课本"></jy:param>
			</jy:nav>
		</h3>
	</div>
	
	<div class="home_cont">
		<div class="home_cont_l">
			<ul class="expmenu2" >
				<c:forEach var="book" items="${bookList }">
					<li>
						<div id="div_${book.comId }" class="header" title="${book.comName }" onclick="showTree('${book.comId }');">
							<ui:sout value="${book.comName }" length="30" needEllipsis="true"></ui:sout>
							<span class="arrow up"></span>
						</div>
						<span class="no" id="info_${book.comId }">
							<ul class="menu" style="display:none;">
								<li>
									<div class="Pre_cont_left_1">
										<div class="file_info" id="countData_${book.comId }">
										</div>
										<input type="button" class="subBtn" value="提交上级" onclick="showSubmitBox('${book.comId }',0);"/> 
									</div>
									<div  class="clear"></div>
									<div class="Pre_cont_left_2">
										<ul id="lessonTree_${book.comId }" class="ztree"></ul>
									</div> 
								</li>
							</ul>
						</span>
					</li>
				</c:forEach>
			</ul>
		</div>
		<div class="home_cont_r">
			<div id="iframe1" style="width:925px;border:none;height:655px;"></div>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
</body>
</html>