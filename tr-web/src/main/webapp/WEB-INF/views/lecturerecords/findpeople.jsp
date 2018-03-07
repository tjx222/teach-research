<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<ui:htmlHeader title="听课记录"></ui:htmlHeader>
<link rel="stylesheet" type="text/css" href="${ctxStatic }/modules/check/check_thesis/css/check_thesis.css" media="screen">
<link rel="stylesheet" href="${ctxStatic }/modules/lecturerecords/css/lecturerecords.css" media="screen" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/lib/AmazeUI/css/amazeui.chosen.css" media="screen">
</head>
<body>
	<div class="jyyl_top">
	<ui:tchTop style="1" modelName="听课记录"></ui:tchTop>
</div>
<div class="jyyl_nav">
	当前位置：
		<jy:nav id="ckkt_index">
			<jy:param name="name" value="听课记录"></jy:param>
		</jy:nav>
</div>

<div class="check_teacher_wrap">
	<div class="check_teacher_wrap1"> 
		<div class="check_teacher_bottom clearfix" style="border-top:0;">
				<ui:relation var="phases" type="xzToXd"></ui:relation>
				<c:if test="${fn:length(phases) > 1 }">
				<div style="float:left;width:106px;">
					<select id="phaseSelect" style="width:auto;padding: 0 57%; border: 1px solid #ccc; height: 28px;">
						<c:forEach var="phase" items="${phases }">
							<option value="${phase.id }" ${phase.id == phaseId ? 'selected':'' }>${phase.name}
							</option>
						</c:forEach>
					</select>
					</div>
				</c:if>
			<div class="out_reconsideration_see_title_box" style="padding-left:58px" >
				<span class="scroll_leftBtn"></span>
		        <div class="in_reconsideration_see_title_box" > 
					<ol class="ol_grade te_grade reconsideration_see_title">
						<ui:relation var="grades" type="xdToNj" id="${phaseId }"></ui:relation>
						<c:forEach items="${grades }" var="g" varStatus="st">
							<li data="${g.id}" class="li_bg" onclick="findGrade(this);">${g.name }</li>
						</c:forEach>
					</ol>
				</div>
				<span class="scroll_rightBtn"></span>
			</div>
			<div class="clear"></div>
			<div class="check_teacher_bottom_inside clearfix">
				<div class="check_teacher_bottom_inside_l">
					<ol class="ol_subject te_subject">
								<ui:relation var="subjects" type="xdToXk" id="${phaseId }"></ui:relation>
								<c:forEach items="${subjects }" var="s" varStatus="st">
									<li data="${s.id }" class="ol_subject_li" onclick="findSubject(this);">${s.name }</li>
								</c:forEach>
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
$().ready(function(){
	$('.te_subject').find("li").first().addClass("ol_subject_li_act");
	$('.te_grade').find("li").first().addClass("li_act");
	tab();
	reflushPeople();//按照条件找人
	$("#phaseSelect").change(function(){
		window.location.href=_WEB_CONTEXT_+"/jy/lecturerecords/findpeople?phaseId="+$(this).val();
	});
});

//点击的时候按照科目找人
function findSubject(obj){
	$('.ol_subject_li_act').removeClass("ol_subject_li_act");
	$(obj).addClass("ol_subject_li_act");
	reflushPeople();
}

//点击的时候按照年级找人
function findGrade(obj){
	$('.li_act').removeClass("li_act");
	$(obj).addClass("li_act");
	reflushPeople();
}

//调到撰写校内听课记录页面
function writeLectureRecordsInnerInput(id){
	window.location.href=_WEB_CONTEXT_+"/jy/lecturerecords/writeLectureRecordsInnerInput?"
		+"addflag=true&userSpanceId="+id;
}

/**
 * 校内听课，按照条件刷新找人
 */
function reflushPeople(){
	var subjectID=$('.ol_subject_li_act').attr("data");
	var gradeID=$('.li_act').attr("data");
	$.ajax( {    
	    url:'jy/lecturerecords/reflushPeople',// 跳转到 action    
	    data:{    
	    	"subjectId" : subjectID,
	    	"gradeId":gradeID
	    },    
	    type:'post',    
	    cache:false,    
	    dataType:'html',    
	    success:function(data) {
	    	$('.check_teacher_bottom_inside_r').html(data);
	    },
	     error : function() {    
	          alert("查询找人异常！");    
	     }    
	});
}
function tab(){
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
}
</script>
</body>
</html>