<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${ctx}" />
<meta charset="UTF-8">
<title>生成教案</title>
<link rel="stylesheet" href="${ctxStatic }/modules/writelessonplan/css/style.css" media="screen"> 
<link rel="stylesheet" href="${ctxStatic }/lib/kindeditor/themes/default/default.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/lib/jquery/css/jquery.dad.css" media="all">
</head>
<body>
<div class="wrap">
	<input type="text" value="${userId }" id="userid" style="display:none"/>
	<div class="lessonPlan_content">
		<div class="lessonPlan_content_header">
			<p>《${chapterName }》简案 </p>
			<c:if test="${empty chapterTree.child }">
				<span class="checkCon_button">${chapterTree.name }<img src="${ctxStatic }/modules/writelessonplan/images/down.png"/></span><span id="lessonid" style="display:none">${chapterTree.id }</span>	
			</c:if>
			<c:if test="${not empty chapterTree.child }">
				<span class="checkCon_button">选择章节<img src="${ctxStatic }/modules/writelessonplan/images/down.png"/></span><span id="lessonid" style="display:none"></span>
				<ul class="checkCon_ul">
					<c:forEach items="${chapterTree.child }" var="tree">
						<li title="${tree.name }"><span class='chaptername'>${tree.name }</span></li><span style='display:none'>${tree }</span>
					</c:forEach>
				</ul>
			</c:if>
			<span class="addCon_button">添加内容<img src="${ctxStatic }/modules/writelessonplan/images/down.png"/></span>
			<ul class="addCon_ul">
				<li>教学目标</li>
				<li>学情分析</li>
				<li>重点难点</li>
				<li>教具媒体</li>
				<li>教学过程</li>
				<li class="no">布置作业</li>
			</ul>
		</div>
		<form>
		<div class="lessonPlan_content_content">
			<div class="lessonPlan_content_type">
				<p class="type_title1">
					<strong><span>教学目标</span></strong><b class="del"></b><img src="${ctxStatic }/modules/writelessonplan/images/edit1.png" class="edit"/>
				</p>
				<textarea class="type_con1" placeholder="请输入文字"></textarea>
				<div class="type_con2" style="display:none;">
					<div class="conList2 content">
						<span></span>
						<p></p>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			<div class="lessonPlan_content_type">
				<p class="type_title2" isprocess="1">
					<strong><span>教学过程</span></strong><b class="del"></b>
				</p>
				<div class="type_con2">
					<div class="textarea_box">
						<textarea id="process" class="type_con1" placeholder="请输入文字"></textarea>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		</form>
	</div>
	<footer>
		<input type="button" value="保存至备课本" class="lesson_saveButton"/>
	</footer>
</div>
<!--提示弹框-->
<div class="mask"></div>
<div class="dialog">
	<div class="dialog_header">
		<span>提示</span>
		<span class="dialog_close"></span>
	</div>
	<div class="dialog_content">
		<div>
			<img src="${ctxStatic }/modules/writelessonplan/images/sign.png"/>
			<p>保存后的备课本将无法修改</p>
		</div>
		<button>确定</button>
	</div>
</div>

<div class="dialog1">
	<div class="dialog_header1">
		<span>提示</span>
		<span class="dialog_close"></span>
	</div>
	<div class="dialog_content1">
		<div>
			<img src="${ctxStatic }/modules/writelessonplan/images/sign.png"/>
			<p>保存后的备课本将无法修改</p>
		</div>
	</div>
</div>

</body>
<script src="${ctxStatic }/lib/jquery/jquery-1.11.2.min.js"></script>
<script src="${ctxStatic }/lib/jquery/jquery.dad.min.js"></script>
<script src="${ctxStatic }/lib/kindeditor/editor.js"></script> 
<script src="${ctxStatic }/lib/kindeditor/kindeditor-min.js"></script> 
<script src="${ctxStatic }/lib/kindeditor/zh_CN.js"></script>
<script>
	var bodyHeight=$(window).height();
	$(".lessonPlan_content").css("height",bodyHeight-66+'px');
	var processEditor;
    function text2(){
			//点击编辑
			$(".edit").each(function(){
				var that=this;
				$(this).click(function(){
					var content = $(that).parents(".lessonPlan_content_type").find(".type_con2 p");
					var constr = "";
					for(i=0;i<content.length;i++){
						constr += $(content[i]).text()+"\n";
					}
					constr = constr.substring(0,constr.lastIndexOf("\n"));
					$(that).parents(".lessonPlan_content_type").find("textarea").show().parents(".lessonPlan_content_type").find(".type_con2").hide();
					$(that).parents(".lessonPlan_content_type").find("textarea").val(constr);
				})
			});
	};
    function text(){
		//编辑框输入内容保存
		$(".type_con1").each(function(){
			var that=this;
			$(this).blur(function(){
				var index = $(that).parents(".lessonPlan_content_type").index();
				if($(that).val()==""){
					return;
				}else{
					var arr = $(that).val().split("\n");
					var list = "";
					if(index%2==0){
						for(i=0;i<arr.length;i++){
							if(arr[i] != ""){
							list += "<div class='conList2 content'>"
									+"<span></span>"
									+"<p>"+arr[i]+"</p>"
									+"<div class='clear'></div></div>";
							}
						}
					} else{
						for(i=0;i<arr.length;i++){
							if(arr[i] != ""){
								list += "<div class='conList content'>"
										+"<span></span>"
										+"<p>"+arr[i]+"</p>"
										+"<div class='clear'></div></div>";
							}
						}
					}
					
					var content = $(that).parents(".lessonPlan_content_type").find(".type_con2");
					content.empty();
					content.append(list);
					content.show();
					$(that).hide();
				}
			})
		})
	};
	function del(){
		$(".del").each(function(){
			var that=this;
			$(this).click(function(){
				$(that).parent().parent().remove();
			})
		})
	};
	function save(){
		var arr = "";
		var lessonid = "";
		$(".lesson_saveButton").click(function(){
			arr = "";
			lessonid = $("#lessonid").html().trim();
			var p_html=$(".lessonPlan_content_content .lessonPlan_content_type");
			for(i=0;i<p_html.length;i++){
				var title = p_html.eq(i).children("p").text().trim();
				arr += title+"<br/>";
				if(p_html.eq(i).children("p").attr("isprocess")){
					var conarr = processEditor[0].text().split("\n");
					for(j=0;j<conarr.length;j++){
						if("" != conarr[j]){
							arr +=conarr[j]+"<br/>"
						}
					}
				} else{
					var conList = p_html.eq(i).children(".type_con2").children(".content");
					var para = "";
					if(conList.length>0){
						for(j=0;j<conList.length;j++){
							var cont = conList.eq(j).children("p").html().trim();
							if(cont && cont!=""){
								para += cont+"<br/>";
							}
						}
					}
					arr += para;
				}
				
			}
			if(arr==""){
				$(".mask").show();
				$(".dialog1").show();
				$(".dialog_content1 p").html("请填写内容");
				return false;
			}else if (!lessonid) {
				$(".mask").show();
				$(".dialog1").show();
				$(".dialog_content1 p").html("请选择章节");
				return false;
			}  else {
				$(".mask").show();
				$(".dialog").show();
			}
		});
		$(".close").click(function(){
			$(".mask").hide();
			$(".dialog").hide();
			$(".dialog1").hide();
			$(".dialog_content1 p").html("");
		});
		$("button").click(function(){
			var userid = $("#userid").val().trim();
		    $.ajax({
           	type: "POST",
           	url: "${ctx}jy/ws/saveLessonPlanRemote",
           	data: {lessonid:lessonid, userid:userid,content:arr},//优课章节id,当前用的登录名,批注内容
           	dataType: "json",
           	success: function (data) {
           		$(".mask").show();
				$(".dialog1").show();
				$(".dialog_content1 p").html(data.msg);
           	},
           	error: function (msg) {
           	}
			});
			$(".mask").hide();
			$(".dialog").hide();
			$(".edit").hide();
		})	
	};
	
	$(document).ready(function(){
		webEditorOptions = {
				width:"100%",
				height:'200px',  
				style:"0",
				htmlTags:{'br,tbody,tr,strong,b,sub,sup,em,i,u,strike,s,del' : []}
			};
			processEditor = $("#process").editor(webEditorOptions);
			processEditor[0].html('${contents }');
		$(".checkCon_ul li").last().addClass("no");
		$(".checkCon_ul").mouseleave(function(){
			$(this).hide();
		});
		$(".addCon_ul").mouseleave(function(){
			$(this).hide();
		});
	    text();
		text2();
		save();
		del();
		$('.lessonPlan_content_content').dad({
			draggable: 'strong',
			callback:function(){
 				if(processEditor){
 					processEditor[0].sync();
 					processEditor[0].remove();
 					processEditor = $("#process").editor(webEditorOptions);
 				}
			}
		});
		//左上角下拉菜单
		$(".checkCon_button").click(function(){
			$(".checkCon_ul").show();
		})
		
		$(".checkCon_ul").delegate("li","click",function(){
			var obj = ($(this).next().text());
				var jsonObj = eval("(" + obj + ")");
				var data = jsonObj.child;
				if(data == null || data.length == 0){
					$(".checkCon_ul").hide();
					$(".checkCon_button").text($(this).text());
					$("#lessonid").text(jsonObj.id);
				} else{
					$(this).siblings().children("ul").remove();
					$(this).children("ul").remove();
					var item = "<ul class='two_ul'>";
	 				for(i=0;i<data.length;i++){
	 					var tree = JSON.stringify(data[i]);
	 					var li = "<li><span class='chaptername'>"+data[i].name+"</span></li><span style='display:none'>"+tree+"</span>"
	 					item += li;
	 				}
	 				item += "</ul>"
	 				$(this).append(item);
	 			}
		});
		//右上角下拉菜单
		$(".addCon_button").click(function(){
			$(".addCon_ul").show();
		})
		$(".addCon_ul li").each(function(){
			var that=this;
			$(this).click(function(){
				$(".addCon_ul").hide();
				var html=$(that).html();
				var len=$(".lessonPlan_content_type").length;
				if(len%2==0){
					var addHtml='<div class="lessonPlan_content_type"><p class="type_title1"><strong><span>'+html+'</span></strong><b class="del"></b><img src="${ctxStatic}/modules/writelessonplan/images/edit1.png" class="edit"/></p><textarea class="type_con1" placeholder="请输入文字"></textarea><div class="type_con2" style="display:none;"></div><div class="clear"></div></div>';
				}else{
					var addHtml='<div class="lessonPlan_content_type"><p class="type_title2"><strong><span>'+html+'</span></strong><b class="del"></b><img src="${ctxStatic}/modules/writelessonplan/images/edit2.png" class="edit"/></p><textarea class="type_con1" placeholder="请输入文字"></textarea><div class="type_con2" style="display:none;"></div><div class="clear"></div></div>';
				}
				$('.lessonPlan_content_content').append(addHtml);
				var top=$(".lessonPlan_content_type").last().offset().top;
				$(".lessonPlan_content").animate({scrollTop:top},500);
				$('.lessonPlan_content_content').dad({draggable: 'strong',});
				text();
				text2();
// 				save();
				del();
			})
		});
	})
</script>
</html>