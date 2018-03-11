<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<html>
<head>
<ui:htmlHeader title="教案撰写"></ui:htmlHeader>
<link rel="stylesheet"
	href="${ctxStatic}/modules/writelessonplan/css/edit.css">
<link rel="stylesheet"
	href="${ctxStatic}/modules/writelessonplan/css/index.css">
<script type="text/javascript"
	src="${ctxStatic}/modules/writelessonplan/js/js.js"></script>
<script type="text/javascript">
	var wordObj; //word控件实体
	var range; //区域
	var bookmarkname = "PO_title";//书签
	var chaptername = "";
	var folder = "C:/";
	var ww = $(parent.window).width(), offsetWith = ww - 500 > 0 ? ww - 500 : 0;
	var ops = "height=600,width=500,scrollbars=yes,titlebar=no,left="
			+ offsetWith + ",top=10,toolbar=no,resizable=yes,location=no";
	function AfterDocumentOpened() {
		wordObj = document.getElementById("PageOfficeCtrl1");
		wordObj.SetEnableFileCommand(3, false); // 禁用office自带的保存
		wordObj.CustomToolbar = false;//不显示自定义工具栏
		wordObj.OfficeToolbars = true;//默认显示工具栏
		wordObj.Menubar = false;//默认不显示菜单栏
		try {
			var bookmarklist = wordObj.DataRegionList;
			bookmarklist.Refresh();
			if (bookmarklist.Count > 0) {
				for (var i = 0; i < bookmarklist.Count; i++) {
					bmName = bookmarklist.Item(i).Name;
					if (bmName == bookmarkname) {
						locateCursor();
						break;
					}
				}
			}
		} catch (e) {

		}
		//定时保存到本地备份（每10分钟）
		saveToLocal();
	}
	//将光标定位到书签所在的位置
	function locateCursor() {
		var mac = "Function myfunc()" + " \r\n  On Error Resume Next \r\n"
				+ "  ActiveDocument.Bookmarks(\"" + bookmarkname
				+ "\").Select " + " \r\n" + "End Function " + " \r\n";
		wordObj.RunMacro("myfunc", mac);
		range = wordObj.Document.Application.Selection.Range;
	}
	//在标题位置添加课题名称
	function setLessonName(lessonName) {
		if (lessonName != chaptername && range != null) {
			range.Text = lessonName;
			range.Select();
		}
		chaptername = lessonName;
	}
	//在标题位置添加课题名称
	function updateLessonName(lessonTimes) {
		if (range != null) {
			if (lessonTimes == '') {
				range.Text = chaptername;
			} else {
				var name = " 第" + lessonTimes + "课时"
				if (lessonTimes == 'all') {
					name = "不分课时";
				}
				range.Text = chaptername + name;
			}
		}
	}
	//保存到服务器
	function saveToServer() {
		if ($("#lesson_id").val() == "" || $("#lesson_hours").val() == "") {
			alert("请先选择课题和课时！");
		} else {
			window.parent.checked_zhihui($("#lesson_hours").val());
			wordObj.WebSave();
			//将返回的教案id赋值
			if (wordObj.CustomSaveResult == 'success') {
				//window.location.reload();
				window.parent.showSuccessBox();
				$("#box2").show();

			} else if (wordObj.CustomSaveResult == 'fail1') {
				alert("保存失败，选择的课时下已存在教案");
				window.parent.location.reload();
			} else if (wordObj.CustomSaveResult == 'fail2') {
				alert("只有在教师身份下才可以撰写教案，请切换身份");
				window.parent.location.reload();
			} else if (wordObj.CustomSaveResult == 'fail3') {
				alert("空间容量不足，保存失败");
			} else {
				alert("保存失败，请重试");
			}
		}
	}
	
	//工具栏显示隐藏
	function fadeInOrOut() {
		if (wordObj.OfficeToolbars) {
			wordObj.OfficeToolbars = false;
			$('#fade')
					.html(
							'<img style="float:left;margin-right:5px;" src="${ctxStatic }/modules/writelessonplan/images/sou1.png" title="隐藏工具栏">');
		} else {
			wordObj.OfficeToolbars = true;
			$('#fade')
					.html(
							'<img style="float:left;margin-right:5px;" src="${ctxStatic }/modules/writelessonplan/images/sou.png" title="显示工具栏">');
		}
	}

	//将当前word文件保存到本地
	function saveToLocal() {
		var dingshi = window.setInterval(function() {
			var fileName = $("#lesson_name").val();
			var hoursName = $("#lesson_hours").val();
			if (hoursName != "") {
				wordObj.SaveAs(folder + fileName + "第" + hoursName
						+ "课时(备份).doc", true);
			}
		}, 600000);

	}
	//获取右侧的同伴资源
	var peer_data = [ null, null, null ];
	function showPeerRes() {
		$("#li_1").trigger("click");
	}
	function getPeerRes(planType, currentPage) {
		var lessonId = $("#lesson_id").val();
		if (peer_data[planType] != null) {
			//展示从缓存中获得的数据
			showPeerData(planType);
		} else {
			//请求后台数据
			if (lessonId != "") {
				$.ajax({
					async : false,
					cache : true,
					type : 'POST',
					dataType : "json",
					data : {
						lessonId : lessonId,
						planType : planType,
						'page.currentPage' : currentPage
					},
					url : _WEB_CONTEXT_ + "/jy/getPeerResource.json",
					error : function() {
						alert('请求同伴资源失败');
					},
					success : function(data) {
						if (data.result == "success") {
							peer_data[planType] = data.lessonPlanPageList;
							//展示数据
							showPeerData(planType);
						} else {
							alert('请求同伴资源失败');
						}
					}
				});
			} else {
				$("#peer_" + planType).html("请先点选课题");
			}
		}
	}

	function scanFile(resid) {
		$('.add_push_wrap').hide();
		$('.add_peer_wrap').hide();
		scanResFile(resid, ops);
	}
	//展示同伴资源数据
	function showPeerData(planType) {
		var htmlStr = '<div style="display:block;height:455px;">';
		var peerDataList = peer_data[planType].datalist;
		for (var i = 0; i < peerDataList.length; i++) {
			var fileName = cutStr(peerDataList[i].planName, 16, true);
			var orgName = cutStr(peerDataList[i].orgName + ' '
					+ peerDataList[i].userName, 15, true);
			htmlStr = htmlStr
					+ '<div class="Pre_cont_right_1_dl"><dl><dd><span></span>';
			if (planType == 1) {
				htmlStr = htmlStr
						+ '<img src="${ctxStatic }/common/icon/base/ppt.png" onclick="scanFile(\''
						+ peerDataList[i].resId + '\');">';
			} else {
				htmlStr = htmlStr
						+ '<img src="${ctxStatic }/common/icon/base/doc.png" onclick="scanFile(\''
						+ peerDataList[i].resId + '\');">';
			}
			var icopath;
			$.ajax({
				url : _WEB_CONTEXT_ + "/jy/manage/res/getResPath",
				async : false,
				data : {
					resId : peerDataList[i].resId,
					fileName : peerDataList[i].planName
				},
				success : function(data) {
					icopath = data.data;
				}
			});
			htmlStr = htmlStr
					+ '</dd><dt><span title="'+peerDataList[i].planName+'">'
					+ fileName
					+ '</span><span title="'+peerDataList[i].orgName+' '+peerDataList[i].userName+'">'
					+ orgName
					+ '</span></dt></dl>'
					+ '<div class="show_p"><ol><li class="show_p_1" title="查看" onclick="scanFile(\''
					+ peerDataList[i].resId
					+ '\');"></li>'
					+ '<li><a class="show_p_2" title="下载" href="'+icopath+'"></a></li></ol></div></div>';
		}
		htmlStr = htmlStr + '</div>';
		if (peer_data[planType].totalCount > 1) {
			if (peer_data[planType].currentPage > 1) {
				htmlStr = htmlStr
						+ '<div style="height:50px;line-height:50px;" class="page"><ol><li class="one_page"><a style="cursor: pointer;" onclick="peer_data['
						+ planType + ']=null;getPeerRes(' + planType + ','
						+ (peer_data[planType].currentPage - 1)
						+ ');">上一页</a></li>';
			} else {
				htmlStr = htmlStr
						+ '<div style="height:50px;line-height:50px;" class="page"><ol><li class="on_one_page"><a disabled="disabled">上一页</a></li>';
			}
			if (peer_data[planType].currentPage < peer_data[planType].totalPages) {
				htmlStr = htmlStr
						+ '<li class="next_page"><a style="cursor: pointer;" onclick="peer_data['
						+ planType + ']=null;getPeerRes(' + planType + ','
						+ (peer_data[planType].currentPage + 1)
						+ ');">下一页</a></li></ol></div>';
			} else {
				htmlStr = htmlStr
						+ '<li class="the_next_page"><a disabled="disabled">下一页</a></li></ol></div>';
			}
		}
		$("#peer_" + planType).html(htmlStr);
	}
	//获取右侧推送资源
	//var commend_data = [null,null,null,[null,null,null,null]];
	function showCommendRes() {
		$("#li_0").trigger("click");
	}
	function getCommendRes(resType, currentPage) {
		var lessonId = $("#lesson_id").val();
		var bookId = $("#book_id").val();
		if (lessonId != "") {
			//请求后台数据
			$.ajax({
				async : false,
				cache : true,
				type : 'POST',
				data : {
					resType : resType,
					lessonId : lessonId,
					bookId : bookId,
					'page.currentPage' : currentPage
				},
				url : _WEB_CONTEXT_ + "/jy/getAllCommendResource",
				error : function() {
					alert('请求推送资源失败');
				},
				success : function(data) {
					$("#commendDiv_" + resType).html(data);
				}
			});
		} else {
			$("#commendDiv_" + resType).html("请先点选课题");
		}

	}
	//获取右侧教案模板
	var templateListData = null;
	function getTemplates() {
		if (templateListData != null) {
			//showTemplateData();
			return;
		} else {
			//请求后台数据
			$.ajax({
				async : false,
				cache : true,
				type : 'POST',
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/getLessonPlanTemplate.json",
				error : function() {
					alert('获取教案模板失败');
				},
				success : function(data) {
					templateListData = data.templateList;
					//展示数据
					showTemplateData();
				}
			});
		}
	}
	//展示教案模板
	function showTemplateData() {
		var htmlStr = '';
		for (var i = 0; i < templateListData.length; i++) {
			var icopath = '${ctxStatic }/common/icon/base/word.png';
			if (templateListData[i].ico && templateListData[i].ico != '') {
				$.ajax({
					url : _WEB_CONTEXT_ + "/jy/manage/res/getResPath",
					async : false,
					data : {
						resId : templateListData[i].ico
					},
					success : function(data) {
						icopath = data.data;
					}
				});
			}
			if (templateListData[i].tpId == '${template.tpId }') {
				htmlStr = htmlStr
						+ '<dl id="'
						+ templateListData[i].tpId
						+ '" onclick="parent.showNewTemplate(this,${template.tpId })"><dd><img src="'+icopath+'"><span></span><strong></strong><b></b></dd>'
						+ '<dt>' + templateListData[i].tpName + '</dt></dl>';
				/* <input type="radio" value="'+templateListData[i].tpId+'" onclick="parent.showNewTemplate(this,${template.tpId })" checked="checked" name="template">' */
			} else {
				htmlStr = htmlStr
						+ '<dl id="'
						+ templateListData[i].tpId
						+ '" onclick="parent.showNewTemplate(this,${template.tpId })"><dd><img src="'+icopath+'"></dd>'
						+ '<dt>' + templateListData[i].tpName + '</dt></dl>';
				/* <input type="radio" value="'+templateListData[i].tpId+'" onclick="parent.showNewTemplate(this,${template.tpId })" name="template"> */
			}
		}
		$("#template").html(htmlStr);
	}
	require([ 'jquery', 'jp/jquery.cookie.min' ], function() {
		if ($.cookie('planPath_cookie') != null
				&& $.cookie('planPath_cookie') != "") {
			folder = $.cookie('planPath_cookie');
		}
		//选择文件夹
		window.saveFolder = function() {
			folder = $("#planFolder").val().replace(/\\/g, "/");
			$.cookie('planPath_cookie', $("#planFolder").val(), {
				expires : 365
			});
			$("#folderbox").hide();
		}
	});
	function close1() {
		$("#folderbox").hide();
	}
	function setPlanFoder() {
		if ($.cookie('planPath_cookie') != null) {
			$("#planFolder").val($.cookie('planPath_cookie'));
		}
		$("#folderbox").show();
	}
	
	//验证
	function checksaveLesson() {
		if ($("#lesson_id").val() == "" || $("#lesson_hours").val() == "") {
			alert("请先选择课题和课时！");
			return false;
		}else{
		return true;
		}
	}
	//getcheckhtml
	function getCheckHtml(topNum, content) {
		var str = '<div class="kjmsformError parentFormkj_form formError" style="opacity:0.87; position:absolute; top:'+topNum+'px; left:95px;">'
				+ '<div class="formErrorContent" style="padding:8px 10px;">* '
				+ content
				+ '<br></div>'
				+ '<div class="formErrorArrow"><div class="line10"></div><div class="line9"></div><div class="line8"></div>'
				+ '<div class="line7"></div><div class="line6"></div><div class="line5"></div>'
				+ '<div class="line4"></div><div class="line3"></div><div class="line2"></div><div class="line1"></div>'
				+ '</div></div>';
		return str;
	}
</script>
</head>


<body style="background: #f2f2f2;">

	<div id="folderbox" class="saved_successfully1"
		style="z-index: 2000; display: none;">
		<div class="saved_successfully_wrap1" style="z-index: 2000">
			<iframe
				style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
				frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
			<div class="dlog-top">
				<span>设置保存位置</span> <b class="close" onclick="close1();"></b>
			</div>
			<div class="saved_bottom" style="background: #fff;">
				<p id="save_b_p">注：为避免断网、断电情况给您造成内容丢失，平台可提供10分钟自动将您的内容保存到本地电脑的功能。如需此功能，请设置以下保存位置！格式例如（文件夹必须是已存在的）：D:/文件夹名称/</p>
				<div class="route">
					<h5>保存位置：</h5>
					<input type="text" class="save_location" id="planFolder">
					<p id="save_b_btn">
						<input type="button" class="save_btn" onclick="saveFolder();"
							value="保存">
					</p>
				</div>
			</div>
		</div>
	</div>

	<div id="qwe" style="overflow: hidden;">
		<!-- <h3
			style="width: 13px; margin-right: 10px; float: left; cursor: pointer; line-height: 30px;"
			onclick="fadeInOrOut();" id="fade">
			<span class="qwe_h3_span" title="工具栏隐藏/显示"></span>点击这里，展开/收起工具栏
		</h3>
		<input type="button" onclick="uploadLocalLesson();" class="saveJA" value="上传本地教案"> 
		<input type="button" onclick="saveToServer();" class="saveJA" value="保存教案">  -->
		<input type="hidden" id="lesson_id" name="lessonId" value="" />
		<input type="hidden" id="book_id" name="bookId" value="" /> 
		<input type="hidden" id="lesson_name" name="lessonName" value="" />
		<input type="hidden" id="gradeId" name="gradeId" value="" />
		<input type="hidden" id="subjectId" name="subjectId" value="" />
		<!-- 已选课时 -->
		<input type="hidden" id="lesson_hours" name="hoursIdStr" value="" />
		<!-- 教案id -->
		<input type="hidden" id="plan_id" name="planId" value="" />
		<!-- 模板id -->
		<input type="hidden" id="tp_id" name="tpId"
			<c:if test='${template!=null }'>value="${template.tpId }"</c:if> />
	</div>

	<c:choose>
		<c:when test="${jfn:cfgv('writePlan.showPeerRes','no')=='no'}">
			<div class="com_cont_right1" style="z-index: 1000; ">
				<iframe
					style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
					frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
				<div class="com_cont_right1_left" ></div>
				<div class="com_cont_right1_right" align="center">
					<ul>
						<li id="jiaoan" class="li_1" onclick="getTemplates();"	style="margin-top: 14px;">教案模板</li>
						<li id="push" class="li_1" onclick="showCommendRes();" style="margin-top:14px;">推送资源</li>
						<li id="peer" style="border:none;padding-top:7px;" class="li_1" onclick="showPeerRes();" style="margin-top:14px;">同伴资源</li>
						<!-- 				<li style="border:none;" id="electronics" class="li_1">电子教材</li>  -->
					</ul>
				</div>
			</div>
		</c:when>
		<c:otherwise>
			<div class="com_cont_right1" style="z-index: 1000; height: 130px;">
				<iframe
					style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
					frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
				<div class="com_cont_right1_left" style="height: 130px;"></div>
				<div class="com_cont_right1_right" align="center"
					style="height: 130px; margin-top: -130px;">
					<ul>
						<li id="jiaoan" class="li_1" onclick="getTemplates();"
							style="margin-top: 14px;">教案模板</li>
						<<li id="push" class="li_1" onclick="showCommendRes();" style="margin-top:14px;">推送资源</li>
						<li id="peer" style="border: none; padding-top: 7px;" class="li_1"
							onclick="showPeerRes();" style="margin-top:14px;">同伴资源</li>
						<!-- 				<li style="border:none;" id="electronics" class="li_1">电子教材</li>  -->
					</ul>
				</div>
			</div>
		</c:otherwise>
	</c:choose>

	<div id="box2"
		style="display: none; width: 100%; height: 100%; z-index: 2000; overflow: hidden;">
		<div class="box1" style="z-index: 2000; background: none;">
			<iframe
				style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
				frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		</div>
	</div>
	<div id="box3"
		style="right: 20px; top: -22px; display: none; width: 200px; height: 180px; position: absolute; z-index: 2000; overflow: hidden;">
		<div class="box3"
			style="z-index: 2000; height: 180px; right: 20px; overflow-y: hidden; position: fixed; top: -22px; width: 200px;"></div>
		<iframe
			style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
			frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
	</div>
	<div class="add_lesson_wrap"
		style="height: 568px; position: absolute; z-index: 1000;"
		align="center">
		<iframe
			style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
			frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		<div class="dlog-top">
			<span>教案模板</span> <b class="close"></b>
		</div>
		<div class="dlog-bottom" id="template">
			<c:forEach var="template" items="${parent.templateList }">
				<dl>
					<dd>
						<img src="${ctxStatic }/common/icon/base/word.png" alt="">
					</dd>
					<dt>
						<%-- <input type="radio" onclick="showNewTemplate('${template.tpId }')" name="template"> --%>${template.tpName }</dt>
				</dl>
			</c:forEach>
		</div>
	</div>
	<div class="add_push_wrap"
		style="height: 568px; position: absolute; z-index: 1000;"
		align="center">
		<iframe
			style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
			frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		<div class="dlog-top">
			<span>推送资源</span> <b class="close"></b>
		</div>
		<div class="dlog-bottom">
			<div class="menu_list">
				<!-- id="commendDiv" -->
				<ul>
					<li class="menu_list_act" onclick="getCommendRes(0,1);" id="li_0">教案</li>
					<li onclick="getCommendRes(1,1);">课件</li>
					<li onclick="getCommendRes(2,1);">习题</li>
					<li style="border: none;" onclick="getCommendRes(3,1);">素材</li>
				</ul>
				<div class="clear"></div>
				<div class="menu_list_big">
					<div class="menu_list_tab" id="commendDiv_0"></div>
					<div class="menu_list_tab" id="commendDiv_1"></div>
					<div class="menu_list_tab" id="commendDiv_2"></div>
					<div class="menu_list_tab" id="commendDiv_3"></div>
				</div>
			</div>
		</div>
	</div>
	<div class="add_peer_wrap"
		style="height: 568px; position: absolute; z-index: 1000;"
		align="center">
		<iframe style="position: absolute; visibility: inherit; top: 0px; left: 0px; height: 100%; width: 100%; margin: 0px; padding: 0px; z-index: -1; border-width: 0px; opacity: 0.00;"
			frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		<div class="dlog-top">
			<span>同伴资源</span> <b class="close"></b>
		</div>
		<div class="dlog-bottom">
			<div id="firstpane" class="menu_list">
				<!-- id="commendDiv" -->
				<ul>
					<li style="width: 73px;" class="menu_list_act"
						onclick="getPeerRes(0,1)" id="li_1">教案</li>
					<li style="width: 73px;" onclick="getPeerRes(1,1)">课件</li>
					<li style="width: 73px; border: none;" onclick="getPeerRes(2,1)">反思</li>
				</ul>
				<div class="clear"></div>
				<div class="menu_list_big">
					<div class="menu_list_tab" id="peer_0"></div>
					<div class="menu_list_tab" id="peer_1"></div>
					<div class="menu_list_tab" id="peer_2"></div>
				</div>
			</div>
			<!-- <div id="firstpane" class="menu_list">
				    <p class="menu_head current" onclick="getPeerRes(0,1)">教案</p>
				    <div style="display:block;height:433px;" class='menu_body' id="peer_0">
				    
				    </div>
				    <p class="menu_head" onclick="getPeerRes(1,1)">课件</p>
				   <div style="display:none;height:433px;" class='menu_body' id="peer_1">
					 	

				    </div>
				    <p class="menu_head" onclick="getPeerRes(2,1)">反思</p>
				    <div style="display:none;height:433px;" class='menu_body' id="peer_2">
					 	
				    </div>
				</div> -->
		</div>
	</div>
	<%-- 		<div class="add_electronics_wrap" style="height: 568px; position: absolute; z-index: 1000;" align="center">
		   	<iframe style="position:absolute; visibility:inherit; top:0px; left:0px; height:100%; width:100%; margin:0px; padding:0px; z-index:-1; border-width:0px;opacity: 0.00;" frameborder="0" scrolling="no" width="100%" height="2000px;"></iframe>
		   	<div class="dlog-top">
				<span>电子教材</span>
				<b class="close"></b>
			</div>
			<div class="dlog-bottom">
				<dl>
					<dd><img src="${ctxStatic }/modules/writelessonplan/images/book1.png" alt=""></dd>
					<dt>人教四年级上</dt>
				</dl>
				<dl>
					<dd><img src="${ctxStatic }/modules/writelessonplan/images/book1.png" alt=""></dd>
					<dt>人教四年级下</dt>
				</dl>
			</div>
		</div> --%>
	<div style="width: 700px; height: 695px; margin-top: 2px; z-index: -5;">
		<po:PageOfficeCtrl id="PageOfficeCtrl1">
		</po:PageOfficeCtrl>
	</div>
</body>
</html>