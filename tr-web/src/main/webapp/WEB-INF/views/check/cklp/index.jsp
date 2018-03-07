<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅${type == 0 ? '教案': type == 1? '课件':'反思' }"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
</head>
<body>
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅${type == 0 ? '教案': type == 1? '课件':'反思' }"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：
		<jy:nav id="ckkt_index">
			<jy:param name="name" value="查阅${type == 0 ? '教案': type == 1? '课件':'反思' }"></jy:param>
		</jy:nav>
</div>

<div class="check_teacher_wrap">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_bottom clearfix" style="border-top:0;">
			<div class="out_reconsideration_see_title_box">
		        <span class="scroll_leftBtn"></span>
		        <div class="in_reconsideration_see_title_box" > 
					<ol class="ol_grade te_grade reconsideration_see_title">
						<c:choose>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fXZ') }">
								<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
								<c:forEach items="${grades }" var="g" varStatus="st">
									<li data="${g.id}" class="li_bg ${grade == g.id || (empty grade && st.index == 0) ? 'li_act':'' }">${g.name }</li>
								</c:forEach>
							</c:when>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
								  <li data="${_CURRENT_SPACE_.gradeId }" class="li_bg li_act"><jy:dic key="${_CURRENT_SPACE_.gradeId }"/></li>
							</c:when>
							<c:otherwise>
							
							</c:otherwise>
						</c:choose>
					</ol>
				</div>
				<span class="scroll_rightBtn"></span>
			</div>
			
			<div class="clear"></div>
			<div class="check_teacher_bottom_inside clearfix">
				<div class="check_teacher_bottom_inside_l">
					<ol class="ol_subject te_subject">
						<c:choose>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fXZ') }">
								<ui:relation var="subjects" type="xdToXk" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
								<c:forEach items="${subjects }" var="s" varStatus="st">
									<li data="${s.id }" class="ol_subject_li ${subject == s.id || (empty subject && st.index == 0) ? 'ol_subject_li_act' :'' }">${s.name }</li>
								</c:forEach>
							</c:when>
							<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
									<li class="ol_subject_li ol_subject_li_act" data="${_CURRENT_SPACE_.subjectId }"><jy:dic key="${_CURRENT_SPACE_.subjectId }"/></li>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>
					</ol>
				</div>
				<div class="check_teacher_bottom_inside_r">
					
				</div>
			</div>
		</div>
	</div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>
<script type="text/javascript">
    var HTMLDATA = {};
	function loadData(){
		var sub = $("li.ol_subject_li_act").attr("data");
		var grade = $("li.li_act").attr("data");
		var name = "s"+sub+"g"+grade;
		var html = HTMLDATA[name];
		if(!html){
			$.get("jy/check/lesson/${type}/tchlist?subject="+sub+"&grade="+grade,function(data){
				if(data.indexOf("check_info") != -1){
					HTMLDATA[name] = data;
					$(".check_teacher_bottom_inside_r").html(data);
				}else{
					window.location.href=_WEB_ROOT_;
				}
			});
		}else{
			$(".check_teacher_bottom_inside_r").html(html);
		}
		
	}
	$(document).ready(function(){
		loadData();
		$("ol.ol_grade li").click(function(){
			$this = $(this);
			if($this.attr("css") != "li_act"){
				$(".li_act").removeClass("li_act");
				$this.addClass("li_act");
				loadData();
			}
		});
		$("ol.ol_subject li").click(function(){
			$this = $(this);
			if($this.attr("css") != "ol_subject_li_act"){
				$(".ol_subject_li_act").removeClass("ol_subject_li_act");
				$this.addClass("ol_subject_li_act");
				loadData();
			}
		});
		var li1 = $(".in_reconsideration_see_title_box .li_bg ");
		var window1 = $(".out_reconsideration_see_title_box ol");
		var left1 = $(".out_reconsideration_see_title_box .scroll_leftBtn");
		var right1 = $(".out_reconsideration_see_title_box .scroll_rightBtn"); 
		window1.css("width", li1.length*95+"px");  
		if(li1.length >= 7){
			left1.show();
			right1.show();
		}else{
			left1.css({"visibility":"hidden"});
			right1.css({"visibility":"hidden"});
		} 
		var lc1 = 0;
		var rc1 = li1.length-7; 
		left1.click(function(){
			if (lc1 < 1) {
				return;
			}
			lc1--;
			rc1++;
			window1.animate({left:'+=90px'}, 500);  
		});

		right1.click(function(){
			if (rc1 < 1){
				return;
			}
			lc1++;
			rc1--;
			window1.animate({left:'-=90px'}, 500); 
		});
	});
</script>
</body>
</html>