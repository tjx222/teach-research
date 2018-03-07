define(["require","jquery",'tablexport'], function (require) {
var $=require("jquery");
require("tablexport/base64");
$(document).ready(function(){
	$("#exportExcelData").click(function(e){
		var html = $("#load_tr_table .load_tr").html();
		var tdObj = "<td>集体备课查阅数</td><td>集体备课发起数</td>";
		var trObj = "<tr class='load_export_tr'>"+html+tdObj+"</tr>";
		$("#exportTable").prepend(trObj);
		$("#exportTable .remove_td").remove();
		$("#exportTable").find("tr").last().remove();
		try{
			$('#exportTable').tableExport({type:'excel',escape:'false',fileName:"教学管理情况一览.xls",htmlContent:'false'},e);
		}catch(e){}
		//$("#exportTable .load_export_tr").remove();
		window.location.reload();
	})
	$('.managers_details_con_box li').each(function (){
		var dd_len = $(this).find("dd span");
		if(dd_len.length <= 4){
			$(".managers_details_con_type dd span").css({"padding-top":"0","line-height":"27px"})
		}
	}); 
	var termId = $("#termId").val();
	var orgId = $("#orgId").val();
	var userId = $("#userId").val();
	$(".sort_click").click(function(){
		var index = $("td.sort_click").index($(this));
		$(".sort_click").each(function(i,objSort){
			if(i!=index){
				$(objSort).find(".up").show().css("top","10px");
				$(objSort).find(".down").show().css("top","19px");
			}
		})
		var size = $(this).find("span.sort_:visible").size();
		if(size==2){
			var objSort = $(this).find("span.sort_").eq(0).show().css("top","14px");
			upSort(objSort);
			$(this).find("span.up").show();
			$(this).find("span.down").hide();
		}else{
			var sort = $(this).find("span.sort_:visible").eq(0).attr("data-value");
			var sortObj = $(this).find("span.sort_:visible").eq(0);
			if(sort == 1){
				downSort(sortObj);
				$(this).find("span.down").show().css("top","15px");
				$(this).find("span.up").hide();
			}else{
				upSort(sortObj);
				$(this).find("span.up").show().css("top","14px");
				$(this).find("span.down").hide();
			}
		}
	})
	function upSort(obj){
		$("b").css("color","");
		var flago = $(obj).attr("data-id");
		var phaseId = $(obj).attr("data-phaseid");
		$.ajax({  
			async : false,  
			cache:true,  
			type: 'POST',  
			dataType : "json",  
			data:{'flago':flago,"flags":"asc","termId":termId,"orgId":orgId,"userId":userId,phaseId:phaseId},
			url:   _WEB_CONTEXT_+"/jy/teachingView/manager/m_user_list_sort",
			error: function () {
				alert('操作失败，请稍后重试');  
			},  
			success:function(data){
				if(data){
					var htmlStr = loadManagerData(data);
					$(".teachingManagement_table2").find(".replace_tr").remove();
					$("#viewChart").before(htmlStr);
				}
			}  
		});	
	}
	function downSort(obj){
		//降序
		$("b").css("color","");
		var flago = $(obj).attr("data-id");
		var phaseId = $(obj).attr("data-phaseid");
		$.ajax({  
			async : false,  
			cache:true,  
			type: 'POST',  
			dataType : "json",  
			data:{'flago':flago,"flags":"desc","termId":termId,"orgId":orgId,"userId":userId,phaseId:phaseId},
			url:   _WEB_CONTEXT_+"/jy/teachingView/manager/m_user_list_sort",
			error: function () {
				alert('操作失败，请稍后重试');  
			},  
			success:function(data){
				if(data){
					var htmlStr = loadManagerData(data);
					$(".teachingManagement_table2").find(".replace_tr").remove();
					$("#viewChart").before(htmlStr);
				}
			}  
		});	
	}
	//升序
	var loadManagerData = function(data){
		var htmlStr = "";
		var count = {'cyja':0,'cykj':0,'cyfs':0,'cyjhzj':0,'cyjxwz':0,'cytkjl':0,'cyjhzj':0,'fqjhzj':0};
		for(var i in data){
			count['cyja']+=data[i].jiaoan_read;
			count['cykj']+=data[i].kejian_read;
			count['cyfs']+=data[i].fansi_read;
			count['cyjhzj']+=data[i].plan_summary_read;
			count['cyjxwz']+=data[i].thesis_read;
			count['cytkjl']+=data[i].lecture_read;
			count['cyjhzj']+=data[i].activity_read;
			count['fqjhzj']+=data[i].activity_origination;
			htmlStr += '<tr class="change_bg replace_tr">';
			htmlStr += '<td style="width:88px;">';
			htmlStr += '<a href="jy/teachingView/manager/m_details?userId='+data[i].userId+'&termId='+data[i].searchVo.termId+'&orgId='+data[i].searchVo.orgId+'">';
			htmlStr += ''+data[i].userName+'</a></td>';
			htmlStr += '<td style="width:79px;">';
			htmlStr += '<a href="jy/teachingView/manager/m_lesson?userId='+data[i].userId+'&flago=0&phaseId='+data[i].searchVo.phaseId+'&termId='+data[i].searchVo.termId+'&orgId='+data[i].searchVo.orgId+'">';
			htmlStr +=''+data[i].jiaoan_read+'</a></td>';
			htmlStr += '<td style="width:79px;">';
			htmlStr += '<a href="jy/teachingView/manager/m_lesson?userId='+data[i].userId+'&flago=1&phaseId='+data[i].searchVo.phaseId+'&termId='+data[i].searchVo.termId+'&orgId='+data[i].searchVo.orgId+'">';
			htmlStr +=''+data[i].kejian_read+'</a></td>';
			htmlStr += '<td style="width:79px;">';
			htmlStr += '<a href="jy/teachingView/manager/m_lesson_fansi?userId='+data[i].userId+'&phaseId='+data[i].searchVo.phaseId+'&termId='+data[i].searchVo.termId+'&orgId='+data[i].searchVo.orgId+'">';
			htmlStr +=''+data[i].fansi_read+'</a></td>';
			htmlStr += '<td style="width:107px;">';
			htmlStr += '<a href="jy/teachingView/manager/m_checkPlanSummary?userId='+data[i].userId+'&phaseId='+data[i].searchVo.phaseId+'&termId='+data[i].searchVo.termId+'&orgId='+data[i].searchVo.orgId+'">';
			htmlStr +=''+data[i].plan_summary_read+'</a></td>';
			htmlStr += '<td style="width:107px;">';
			htmlStr += '<a href="jy/teachingView/manager/m_checkThesis?userId='+data[i].userId+'&phaseId='+data[i].searchVo.phaseId+'&termId='+data[i].searchVo.termId+'&orgId='+data[i].searchVo.orgId+'">';
			htmlStr +=''+data[i].thesis_read+'</a></td>';
			htmlStr += '<td style="width:107px;">';
			htmlStr += '<a href="jy/teachingView/manager/m_check_lecture?userId='+data[i].userId+'&phaseId='+data[i].searchVo.phaseId+'&termId='+data[i].searchVo.termId+'&orgId='+data[i].searchVo.orgId+'">';
			htmlStr +=''+data[i].lecture_read+'</a></td>';
			htmlStr += '<td style="width:87px;">';
			htmlStr += '<a href="jy/teachingView/manager/m_check_jitibeike?userId='+data[i].userId+'&phaseId='+data[i].searchVo.phaseId+'&termId='+data[i].searchVo.termId+'&orgId='+data[i].searchVo.orgId+'">';
			htmlStr +=''+data[i].activity_read+'</a></td>';
			htmlStr += '<td class="no_border" style="width:87px;">';
			htmlStr += '<a href="jy/teachingView/manager/m_partLaunchActivity?userId='+data[i].userId+'&phaseId='+data[i].searchVo.phaseId+'&termId='+data[i].searchVo.termId+'&orgId='+data[i].searchVo.orgId+'">';
			htmlStr +=''+data[i].activity_origination+'</a></td>';
			htmlStr+="</tr>";
		}
		htmlStr += '<tr class="replace_tr">';
		htmlStr += '<td style="width:88px;">合计</td>';
		htmlStr += '<td style="width:88px;">'+count['cyja']+'</td>';
		htmlStr += '<td style="width:88px;">'+count['cykj']+'</td>';
		htmlStr += '<td style="width:88px;">'+count['cyfs']+'</td>';
		htmlStr += '<td style="width:88px;">'+count['cyjhzj']+'</td>';
		htmlStr += '<td style="width:88px;">'+count['cyjxwz']+'</td>';
		htmlStr += '<td style="width:88px;">'+count['cytkjl']+'</td>';
		htmlStr += '<td style="width:88px;">'+count['cyjhzj']+'</td>';
		htmlStr += '<td class="no_border" style="width:87px;">'+count['fqjhzj']+'</td>';
		htmlStr += '</tr>';
		return htmlStr;
	}
	
	/**
	 * 集体备课
	 */
	$(".teachingTesearch_jitibeike_table").each(function(){
		$(this).find("tr").last().find("td").css("border-bottom","none");
	})
	//tab切换
	$(".managers_rethink_con_bigType li").each(function(){
		var self=this;
		$(this).click(function(){
			var index=$(self).index();
			$(self).addClass("li_active3").siblings().removeClass("li_active3");
			$(".managers_rethink_outBox_type").eq(index).addClass("show").siblings().removeClass("show");
			$(".teachingTesearch_jitibeike_outBox_type").eq(index).addClass("show").siblings().removeClass("show");
		})
	});
	$(".managers_rethink_con_smallType li").each(function(){
		var self=this;
		$(this).click(function(){
			var index=$(self).index();
			$(self).addClass("li_active4").siblings().removeClass("li_active4");
			$(self).parent().next().find(".managers_rethink_intBox_type").eq(index).addClass("show").siblings().removeClass("show");
		})
	});
	$(".regoin_sch").on("mousemove",function(){
		var activtyId = $(this).attr("data-id");
		showOrgList(activtyId);
	})
	var showOrgList = function(activityId){
		var orgList;
		$.ajax({  
			async : false,  
			cache:true,  
			type: 'POST',  
			dataType : "json",  
			data:{'activityId':activityId},
			url:   _WEB_CONTEXT_+"/jy/region_activity/getJoinOrgsOfActivity.json",
			error: function () {
				//alert('操作失败，请稍后重试');  
			},  
			success:function(data){
				orgList = data.orgList;
			}  
		});	
		var htmlStr = "";
		for(var i=0;i<orgList.length;i++){
			htmlStr += "<li title='"+orgList[i].name+"'>"+cutStr(orgList[i].name,24,true)+"</li>";
		}
		$("#orgUl_"+activityId).html(htmlStr);
	}
	$("#radio_shang").click(function(){
		window.location.href=_WEB_CONTEXT_+"/jy/teachingView/manager/m_user_list?termId=0";
	})
	$("#radio_xia").click(function(){
		window.location.href=_WEB_CONTEXT_+"/jy/teachingView/manager/m_user_list?termId=1";
	})
	$(".teachingManagement_table").find("tr").each(function(){
		$(this).find("td").last().css("border-right","none");
	})
	$(".change_bg:even").css("background","#f9f9f9");
	$(".teachingManagement_table2").find("tr").last().find("td").css("border-bottom","none");
});
$(".avtivi_check_option").click(function(){
	var id = $(this).attr("data-id");
	showScanListBox(id,false);
})
//显示查阅意见box(支持集体备课，教案、课件、反思，其他反思除外 isUpdate:是否更新有新查阅意见状态)
window.showScanListBox = function(activityId,isUpdate){
	$("#checkedBox").attr("src",_WEB_CONTEXT_+"/jy/teachingView/view/infoIndex?flags=true&resType=5&resId="+activityId);
	$("#check_box").dialog({width:945,height:560});
}

});