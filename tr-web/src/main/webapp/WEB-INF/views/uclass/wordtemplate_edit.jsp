<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<html>
<head>
<ui:htmlHeader title="教案撰写"></ui:htmlHeader>
<link rel="stylesheet" href="${ctxStatic}/modules/writelessonplan/css/edit.css">
<script type="text/javascript" src="${ctxStatic}/modules/writelessonplan/js/js.js"></script>
<script type="text/javascript">
var wordObj; //word控件实体
var range;  //区域
var bookmarkname = "BM_title";//书签
var ww = $(parent.window).width(),offsetWith = ww - 500 > 0?ww - 500:0;
var ops="height=600,width=500,scrollbars=yes,titlebar=no,left="+offsetWith+",top=10,toolbar=no,resizable=yes,location=no";
function AfterDocumentOpened() {
    wordObj = document.getElementById("PageOfficeCtrl1");
    wordObj.SetEnableFileCommand(3, false); // 禁用office自带的保存
    wordObj.CustomToolbar = false;//不显示自定义工具栏
    wordObj.OfficeToolbars = true;//默认显示工具栏
    wordObj.Menubar = false;//默认不显示菜单栏
  //将光标定位到书签所在的位置
    var mac = "Function myfunc()" + " \r\n"
            + "  ActiveDocument.Bookmarks(\""+ bookmarkname +"\").Select " + " \r\n"
            + "End Function " + " \r\n";
    wordObj.RunMacro("myfunc", mac);
    range = wordObj.Document.Application.Selection.Range;
    range.Text = "${lessonName}";
	range.Select();
}

//保存到服务器
function saveToLocal(){
	var fileName = $("#lesson_name").val();
	alert(window.parent._SAVE_PATH);
	wordObj.SaveAs(window.parent._SAVE_PATH,true);
	parent.ucbook.postWebPageMessage( JSON.stringify({ action: "save", arguments: []}));
	alert("保存成功！");
}
//工具栏显示隐藏
function fadeInOrOut(){
	if(wordObj.OfficeToolbars){
		wordObj.OfficeToolbars = false;
		$('#fade').html('<img style="float:left;margin-right:5px;" src="${ctxStatic }/modules/writelessonplan/images/sou1.png" title="隐藏工具栏">');
	}else{
		wordObj.OfficeToolbars = true;
		$('#fade').html('<img style="float:left;margin-right:5px;" src="${ctxStatic }/modules/writelessonplan/images/sou.png" title="显示工具栏">');
	}
}


function scanFile(resid){
	$('.add_push_wrap').hide();
	$('.add_peer_wrap').hide();
	scanResFile(resid,ops);
}
//获取右侧推送资源
//var commend_data = [null,null,null,[null,null,null,null]];
function showCommendRes(){
	$("#li_0").trigger("click");
}
function getCommendRes(resType,currentPage){
	var lessonId = $("#lesson_id").val();
	var bookId = $("#book_id").val();
	if(lessonId!=""){
		//请求后台数据
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        data:{resType:resType,lessonId:lessonId,bookId:bookId,'page.currentPage':currentPage},
	        url: _WEB_CONTEXT_+"/jy/getAllCommendResource",
	        error: function () {
	            alert('请求推送资源失败');  
	        },  
	        success:function(data){
	        	$("#commendDiv_"+resType).html(data);
	        }  
	    });
	}else{
		$("#commendDiv_"+resType).html("请先点选课题");
	}
		
}
//获取右侧教案模板
var templateListData = null;
function getTemplates(){
	if(templateListData != null){
		//showTemplateData();
		return;
	}else{
		//请求后台数据
		$.ajax({  
	        async : false,  
	        cache:true,  
	        type: 'POST',  
	        dataType : "json",  
	        url: _WEB_CONTEXT_+"/jy/ws/uclass/getLessonPlanTemplate.json",
	        error: function () {
	            alert('获取教案模板失败');  
	        },  
	        success:function(data){
        		templateListData = data.templateList;
        		//展示数据
        		showTemplateData();
	        }  
	    });
	}
}
//展示教案模板
function showTemplateData(){
	var htmlStr = '';
	for(var i=0;i<templateListData.length;i++){
		var icopath = '${ctxStatic }/common/icon/base/word.png';
		if(templateListData[i].ico && templateListData[i].ico != ''){
			icopath = '${ctx}/'+templateListData[i].ico;
		}
		if(templateListData[i].tpId=='${template.tpId }'){
			htmlStr = htmlStr + '<dl id="'+templateListData[i].tpId+'" onclick="parent.showNewTemplate(this,${template.tpId })"><dd><img src="'+icopath+'"><span></span><strong></strong><b></b></dd>'+
			'<dt>'+templateListData[i].tpName+'</dt></dl>';
			/* <input type="radio" value="'+templateListData[i].tpId+'" onclick="parent.showNewTemplate(this,${template.tpId })" checked="checked" name="template">' */
		}else{
			htmlStr = htmlStr + '<dl id="'+templateListData[i].tpId+'" onclick="parent.showNewTemplate(this,${template.tpId })"><dd><img src="'+icopath+'"></dd>'+
			'<dt>'+templateListData[i].tpName+'</dt></dl>';
			/* <input type="radio" value="'+templateListData[i].tpId+'" onclick="parent.showNewTemplate(this,${template.tpId })" name="template"> */
		}
	}
	$("#template").html(htmlStr);
}
</script>
</head>
<body style="background:#f2f2f2;">
<div id="qwe" style="height: 32px;overflow:hidden;">
	<h3 style="width: 80px;margin-right:10px;float: left;cursor: pointer;line-height:30px;" onclick="fadeInOrOut();"id="fade">
	<img style="float:left;display:block;" src="${ctxStatic }/modules/writelessonplan/images/sou.png" title="工具栏隐藏/显示">
	</h3>
	<input type="button" onclick="saveToLocal();" style="cursor: pointer;width: 108px;height: 32px;background: url('${ctxStatic }/modules/writelessonplan/images/preserve.png') no-repeat;float:right;border:none;margin-right:4px;">
	<input type="hidden" id="lesson_id" name="lessonId" value="${lessonId }"/>
  		<input type="hidden" id="lesson_name" name="lessonName" value="${lessonName }"/>
  		<!-- 模板id -->
  		<input type="hidden" id="tp_id" name="tpId" <c:if test='${template!=null }'>value="${template.tpId }"</c:if> />
</div>

	<div class="com_cont_right1" style="z-index: 1000">
		<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		<div class="com_cont_right1_left">
		</div>
		<div class="com_cont_right1_right" align="center">
			<ul>
				<li id="jiaoan" class="li_1" onclick="getTemplates();" style="margin-top:24px;height:50px;">教案模板</li>
				<li id="push" class="li_1" onclick="showCommendRes();" style="margin-top:24px;height:50px;">推送资源</li>
			</ul>
		</div>
	</div>
	<div id="box2" style="display:none; width: 100%;height: 100%; z-index: 2000;overflow:hidden;" >
		<div class="box1" style="z-index: 2000;background: none;">
		<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		</div>
	</div>
	<div id="box3" style="right:20px;top:-22px;display:none; width: 200px;height: 180px; position: absolute; z-index: 2000;overflow:hidden;" >
		<div class="box3" style="z-index: 2000;  height: 180px; right: 20px;overflow-y: hidden; position: fixed; top: -22px; width: 200px;"></div>
		<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
	</div>
		<div class="add_lesson_wrap" style="height: 568px; position: absolute; z-index: 1000;" align="center">
		   	<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		   	<div class="dlog-top">
				<span>教案模板</span>
				<b class="close"></b>
			</div>
			<div class="dlog-bottom" id="template">
				<c:forEach var="template" items="${parent.templateList }">
					<dl>
						<dd><img src="${ctxStatic }/common/icon/base/word.png" alt=""></dd>
						<dt><%-- <input type="radio" onclick="showNewTemplate('${template.tpId }')" name="template"> --%>${template.tpName }</dt>
					</dl>
				</c:forEach>
			</div>
		</div>
		<div class="add_push_wrap" style="height: 568px; position: absolute; z-index: 1000;" align="center">
		   	<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		   	<div class="dlog-top">
				<span>推送资源</span>
				<b class="close"></b>
			</div>
			<div class="dlog-bottom">
				<div class="menu_list"> <!-- id="commendDiv" -->
					<ul>
						<li class="menu_list_act" onclick="getCommendRes(0,1);" id="li_0">教案</li>
						<li onclick="getCommendRes(1,1);">课件</li>
						<li onclick="getCommendRes(2,1);">习题</li>
						<li style="border:none;" onclick="getCommendRes(3,1);">素材</li>
					</ul>
					<div class="clear"></div>
					<div class="menu_list_big">
						<div class="menu_list_tab" id="commendDiv_0">
							
						</div>
						<div class="menu_list_tab" id="commendDiv_1">
							
						</div>
						<div class="menu_list_tab" id="commendDiv_2">
							
						</div>
						<div class="menu_list_tab" id="commendDiv_3">
							
						</div>
					</div>
				</div>
			</div>
		</div>
		<div style="height:695px;margin-top:2px;z-index: -5;">
	        <po:PageOfficeCtrl id="PageOfficeCtrl1">
	        </po:PageOfficeCtrl>
	   </div>
</body>
</html>