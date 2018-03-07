<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<html>
<head>
<link rel="stylesheet" href="../${ctxStatic}/lib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="../${ctxStatic}/lib/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="../${ctxStatic}/lib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="../${ctxStatic}/lib/ztree/js/jquery.ztree.exhide-3.5.min.js"></script>
<title>模板列表</title>

<script type="text/javascript">
var treeNodes;
var ztreeObj;
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
        	beforeExpand:closeAll,
        	onClick: addTitle,
        	beforeClick: isParentOrNot
        },
        view:{
 //       	addDiyDom:addSomeButton
        }
    };  
  
$(document).ready(function(){
	
});
//将节点的名称添加到word文档的标题处
function addTitle(event, treeId, treeNode){
	//ajax请求后台获取已写过的课时
	var hoursStr;
	$.ajax({  
        async : false,  
        cache:true,  
        type: 'POST',  
        dataType : "json",  
        data:{lessonId:treeNode.lessonId},
        url: "getHoursStrOfWritedLesson.json",
        error: function () {
            alert('请求失败');  
        },  
        success:function(data){
        	window.parent.clearAllcheckbox();
        	hoursStr = data.hoursStr;
       		//调用父页面js,将已写的课时置灰
           	window.parent.checked_zhihui(hoursStr)
        }  
    });
	//调用父页面的设置标题方法
	window.parent.document.getElementById("iframe2").contentWindow.setLessonName(treeNode.lessonName);
	$('#lesson_id',window.parent.document.getElementById("iframe2").contentWindow.document).val(treeNode.lessonId);
	$('#lesson_name',window.parent.document.getElementById("iframe2").contentWindow.document).val(treeNode.lessonName);
	$('#lesson_time',window.parent.document).fadeIn(300);
}
//在点击节点前判断是否是父节点，父节点不作操作
function isParentOrNot(treeId, treeNode, clickFlag){
	if(treeNode.isParent){
		return false;
	}
	return true;
}
//关闭其他
function closeAll(event, treeId, treeNode){
	var nodes = ztreeObj.transformToArray(ztreeObj.getNodes());
	for(var i=0;i<nodes.length;i++){
		ztreeObj.expandNode(nodes[i],false,true,false);
	}
}
function showTree(num,bookId){
	//获取课题树
	$.ajax({  
        async : false,  
        cache:true,  
        type: 'POST',  
        dataType : "json",  
        data:{bookId:bookId},
        url: "getLessonList.json",
        error: function () {
            alert('请求失败');  
        },  
        success:function(data){
            treeNodes = data.lessonList; 
        }  
    });
	$(".ztree").each(function(){
	    $(this).fadeOut(200);
	  });
	$("#lessonTree_"+num).fadeIn(500);
    ztreeObj = $.fn.zTree.init($("#lessonTree_"+num), setting, treeNodes);
}
</script>
</head>

<body>
    	<c:forEach var="book" items="${bookList }" varStatus="num">
			<li id="bookId_${num.count}" style="width: 150px;height: 20px; background: green;" onclick="showTree('${num.count}','${book.comId }');"><span>${book.comName }</span> </li>
			<ul id="lessonTree_${num.count}" class="ztree" >
    		
    		</ul>
		</c:forEach>
</body>
</html>