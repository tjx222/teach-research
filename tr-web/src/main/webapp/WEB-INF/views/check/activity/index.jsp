<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="查阅集体备课列表页"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_lecturerecords/css/check_lecturerecords.css">  
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/lib/AmazeUI/css/amazeui.chosen.css">
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.chosen.min.js"></script> 
	<script type="text/javascript" src="${ctxStatic }/lib/AmazeUI/js/amazeui.min.js"></script> 
</head>
<body>
<div class="jyyl_top">
	<ui:tchTop style="1" modelName="查阅集体备课"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：
	<jy:nav id="ckkt_index">
		<jy:param name="name" value="查阅集体备课"></jy:param>
	</jy:nav>
</div>
<div class="check_teacher_wrap">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_bottom clearfix" style="border-top:0;">
			<div class="out_reconsideration_see_title_box">
		        <span class="scroll_leftBtn"></span>
		        <div class="in_reconsideration_see_title_box" > 
					<ol class="ol_grade reconsideration_see_title">
						<c:choose>
						<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fXZ') }">
						<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
						<c:forEach items="${grades }" var="g" varStatus="st">
							<li class="li_bg <c:if test='${grade==g.id||(empty grade&&st.index==0)}'>li_act</c:if>" data="${g.id }">${g.name }</li>
						</c:forEach>
						</c:when>
						<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
							<li class="li_bg li_act" data="${_CURRENT_SPACE_.gradeId }"><jy:dic key="${_CURRENT_SPACE_.gradeId }"/></li>
						</c:when>
						<c:otherwise></c:otherwise>
						</c:choose>
				</ol> 
				</div>
				<span class="scroll_rightBtn"></span>
			</div>
			
			<div class="check_teacher_bottom_inside clearfix">
				<div class="check_teacher_bottom_inside_l">
					<ol class="ol_subject">
						<c:choose>
						<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fXZ') }">
							<ui:relation var="subjects" type="xdToXk" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
							<c:forEach items="${subjects }" var="s" varStatus="st">
								<li data="${s.id }" class="ol_subject_li <c:if test='${subject==s.id||(empty subject&&st.index==0)}'>ol_subject_li_act</c:if>" >${s.name }</li>
							</c:forEach>
						</c:when>
						<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
						<li data="${_CURRENT_SPACE_.subjectId }" class="ol_subject_li ol_subject_li_act" ><jy:dic key="${_CURRENT_SPACE_.subjectId }"/></li>
						</c:when>
						<c:otherwise></c:otherwise>
						</c:choose>
					</ol>
				</div>
				<div class="check_teacher_bottom_inside_r">
					<iframe id="activitylist"  width="100%" height="585px" scrolling="no" style="border:none;"  frameborder="no" border="0" ></iframe>
				</div>
			</div>
		</div>
	</div>
</div>
<ui:htmlFooter style="1"></ui:htmlFooter>










<%-- <div class="wrap">
	<ui:tchTop ></ui:tchTop>
	<div class="wrap_top_2">
		<h3>
			当前位置：
			<jy:nav id="ckkt_index">
				<jy:param name="name" value="查阅集体备课"></jy:param>
			</jy:nav>
		</h3>
	</div>
	<div class="clear"></div>
	<div class="check_lesson_primary">
		<div class="check_lesson_primary_l">
			<h3>集体备课</h3>
			<c:choose>
			<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fXZ') }">
				<ui:relation var="subjects" type="xdToXk" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
				<c:forEach items="${subjects }" var="s" varStatus="st">
					<h4 class="_subOrGrade">
						<span data="${s.id }" class="${subject == s.id || (empty subject && st.index == 0) ? 'primary_act' :'' }">${s.name }</span>
					</h4>
				</c:forEach>
			</c:when>
			<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
				<h4 class="_subOrGrade"> 
					<span class="primary_act" data="${_CURRENT_SPACE_.subjectId }"><jy:dic key="${_CURRENT_SPACE_.subjectId }"/></span>
				</h4>
			</c:when>
			<c:otherwise></c:otherwise>
			</c:choose>
		</div>
		<div class="check_lesson_primary_r">
			<h3>
				<ul class="_subOrGrade">
			<c:choose>
				<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XKZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'ZR')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'XZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'fXZ') }">
					<ui:relation var="grades" type="xdToNj" id="${_CURRENT_SPACE_.phaseId }"></ui:relation>
					<c:forEach items="${grades }" var="g" varStatus="st">
						<li data="${g.id }" class="grade ${grade == g.id || (empty grade && st.index == 0) ? 'nj_act':'' }">${g.name }</li>
					</c:forEach>
				</c:when>
				<c:when test="${jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'NJZZ')||jfn:checkSysRole(_CURRENT_SPACE_.sysRoleId,'BKZZ') }">
					  <li data="${_CURRENT_SPACE_.gradeId }" class="grade nj_act"><jy:dic key="${_CURRENT_SPACE_.gradeId }"/></li>
				</c:when>
				<c:otherwise>
				
				</c:otherwise>
			</c:choose>
				</ul>
			</h3>
			<iframe id="activitylist"  width="100%" height="585px" scrolling="no" style="border:none;"  frameborder="no" border="0" ></iframe>
		</div>
	</div>
	<div class="clear"></div>
	<ui:htmlFooter></ui:htmlFooter>
</div> --%>

<script type="text/javascript">
	function loadData(){
		var sub = $("li.ol_subject_li_act").attr("data");
		var grade = $("li.li_act").attr("data");
		if(sub!=null || grade!=null){
			$("#activitylist").attr("src","jy/check/activity/activitylist?subject="+sub+"&grade="+grade+"&"+ Math.random());
		}
	}
	$(document).ready(function(){
		loadData();
		$("ol.ol_subject li").click(function(){
			$this = $(this);
			if($this.attr("css") != "ol_subject_li_act"){
				$("li.ol_subject_li_act").removeClass("ol_subject_li_act");
				$this.addClass("ol_subject_li_act");
				loadData();
			}
		});
		$("ol.ol_grade li").click(function(){
			$this = $(this);
			if($this.attr("css") != "li_act"){
				$("li.li_act").removeClass("li_act");
				$this.addClass("li_act");
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
	//查阅活动
	function chayueActivity(activityId,typeId){
		window.open(_WEB_CONTEXT_+"/jy/check/activity/chayueActivity?activityId="+activityId+"&typeId="+typeId,"_blank");
	}
</script>
</body>
</html>