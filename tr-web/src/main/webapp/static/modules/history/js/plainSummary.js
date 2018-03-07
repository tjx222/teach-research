define(["require","jquery"],function(require){
	var $ = require("jquery");
	$(document).ready(function(){
		calendar_year();
		initMenu();
		//下拉选项切换查找资源
		$(".full_year").change(function() {
			initCagetory($("#roleId").val(),"");
			var name =  $(this).attr("name");
			if("userSpaceId" == name ){
				$("#term").val("");
				$("#chosenType").val("");
			}
			submitForm();
		});
		//搜索按钮
		$(".ser_btn").click(function() {
			submitForm();
		});
		//点击名称查看
		$(".psTitle").click(function() {
			window.open(_WEB_CONTEXT_
						+ '/jy/scanResFile?resId='
						+ $(this).attr('data-resId'), '');
			});
		//查看查阅记录
		$('.event_check_1,.event_check_2').click(function() {
			var id = $(this).attr('data-id');
			// 状态设置为存在已查阅记录
			$.ajax(_WEB_CONTEXT_ + '/jy/planSummary/'
							+ id + '/checkState?checkState=2',
							{
								type : 'put',
								'dataType' : 'json'
							});
			$(this).find('.menu_li_check_1').removeClass('menu_li_check_1');
			// 弹出审阅浏览框
			var resType = $(this).attr('data-resType');
			var data = {
					url:'jy/check/infoIndex?flags=false&flago=false&resType='
						+ resType + '&authorId='
						+ $('#currentUserId').val()
						+ '&resId=' + id,
						width:954,
						height:514,
						title:"查阅查看"
			};
			window.parent.dialog(data);
			});

		//查看评论
		$('.event_review_1,.event_review_2').click(function() {
			var id = $(this).attr('data-id');
			// 状态设置为存在已查阅记录
			$.ajax(_WEB_CONTEXT_+ '/jy/planSummary/'+ id+ '/reviewState?reviewState=2',
					{type : 'put','dataType' : 'json'});
			var resType = $(this).attr('data-resType');
			var data = {
					url:'jy/comment/list?flags=true&flago=false&resType='+ resType
					+ '&authorId='+ $('#currentUserId').val()+ '&resId='+ id,
					width : 945,
					height : 514,
					title : "查看评论"
			};
			window.parent.dialog(data);
		});

		//查看简单信息
		$(".look_up").click(function() {
			$(".cont_list").html("");
			var look_up_isPunish = $("#look_up_isPunish");
			if (look_up_isPunish) {
				look_up_isPunish.remove();
			}
			var look_up_isSubmit = $("#look_up_isSubmit");
			if (look_up_isSubmit) {
				look_up_isSubmit.remove();
			}
			var id = $(this).attr("data-id");
			var category = $(this).parents("tr").children("td:eq(1)").text();
			$.ajax({
					url : "jy/history/${year}/jhzj/showInfo",
					data : {id : id},
					dataType : "json",
					success : function(data) {
						var ps = data.data.ps;
								$("#look_up_resName").html("资源名称:&nbsp;&nbsp;"+ps.title);
								$("#look_up_category").html("类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:&nbsp;&nbsp;"+ category);
								$("#look_up_date").html("撰写日期:&nbsp;&nbsp;"+ ps.lastupDttm);
									if((ps.roleId != 1376 && ps.roleId != 1377 && ps.roleId != 2000) || (ps.category !=3 && ps.category != 4) ){
										var isSubmit = ps.isPunish != 0 ? "已提交": "未提交";
										var l = '<div class="cont_list" id="look_up_isSubmit">提交状态:&nbsp;&nbsp;'+ isSubmit + '</div>';
										$("#look_up_date").after(l);
									}
								if (ps.category != 1 && ps.category != 2) {
									var isPunish = ps.isPunish != 0 ? "已发布": "未发布";
									var l = '<div class="cont_list" id="look_up_isPunish">发布状态:&nbsp;&nbsp;'+ isPunish + '</div>';
									$("#look_up_date").after(l);
								}
								var share = ps.isShare == 0 ? "未分享": "已分享";
								$("#look_up_share").html("分享状态:&nbsp;&nbsp;" + share);
								var size = data.data.rs ? (data.data.rs.size): "";
								size = bytesToSize(size);
								$("#look_up_resSize").html("文件大小:&nbsp;&nbsp;"+ size);
								$("#look_up_resType").html("文件格式:&nbsp;&nbsp;"+ ps.contentFileType);
								window.parent.dialog({
									content:$("#jhzj_dialog_content").html(),
									width : 420,
									height : 317,
									title: "详情"
								});
					},
					error : function() {
								alert("加载失败");
								}
			});
			
		});
		//全选复选框
		$(".all").click(function(){
			if($(this).prop("checked")){
				$(".check").prop("checked",true);
			} else{
				$(".check").removeProp("checked");
			}
		});
		//控制全选按钮取消选中
		$(".check").click(function(){
			if($(this).prop("checked") == false){
				$(".all").removeProp("checked");
			}
		});
		//批量下载
		$("#batchdownload").click(function(){
			var resIds = [];
			$(".check:checked").each(function(){
				var resId = $(this).next().attr("data-resId");
				resIds.push(resId);
			});
			if(resIds.length <= 0){
				alert("请选择要下载的资源");
				return false;
			}
			var filename = $(".check:checked:eq(0)").next().attr("title");
			window.open(_WEB_CONTEXT_
					+ "/jy/manage/res/batchDownload"
					+"?resids="+resIds+"&filename=" + filename+"等",
					"_self");
		});

	});
	
	function submitForm() {
	if ($('.ser_txt').attr("data-flag") == '0') {
		$('.ser_txt').val("");
	}
		$("#listForm").submit();
	}
	
	function calendar_year () {
		/* 下拉 */
		$(".class_teacher").chosen({
			disable_search : true
		});
		$(".full_year").chosen({
			disable_search : true
		});
	
		/* 文本框提示语 */
		$('.ser_txt').placeholder({
			word : '输入关键词进行搜索'
		});
	}

	function initMenu () {
		var roleId = $("#roleId").val();
		var selectSpace = $("#roleIds").val();
		var category = $("#category").val();
		if (roleId != '' && roleId != null) {
			selectSpace = roleId;
		}
		$("#roleIds").val(selectSpace);
		$("#roleIds").trigger("chosen:updated");
		var selectRoleId = $("#roleIds").find("option:selected").attr("data");
		$("#term").val($("#termId").val());
		$("#term").trigger("chosen:updated");
		initCagetory(selectRoleId,category);
	}
	function initCagetory(selectSpace,category) {
		var chosenType = $("#chosenType");
		var content = "";
		if(selectSpace == ''){
			$(".a2").hide();
		}
		switch (selectSpace) {
			case '27':
				content = "<option value='1'>个人计划</option><option value='2'>个人总结</option>";
				chosenType.append(content);
				chosenType.val(category);
				chosenType.trigger("chosen:updated");
				$(".a2").show();
			break;
			case '1377':
			case '1376':
			case '2000':
				content = "<option value='1'>个人计划</option><option value='2'>个人总结</option><option value='3'>学校计划</option><option value='4'>学校总结</option>";
				chosenType.append(content);
				chosenType.val(category);
				chosenType.trigger("chosen:updated");
				$(".a2").show();
			break;
			case '1375':
				content = "<option value='1'>个人计划</option><option value='2'>个人总结</option><option value='3'>学科计划</option><option value='4'>学科总结</option>";
				chosenType.append(content);
				chosenType.val(category);
				chosenType.trigger("chosen:updated");
				$(".a2").show();
			break;
			case '1374':
				content = "<option value='1'>个人计划</option><option value='2'>个人总结</option><option value='3'>年级计划</option><option value='4'>年级总结</option>";
				chosenType.append(content);
				chosenType.val(category);
				chosenType.trigger("chosen:updated");
				$(".a2").show();
			break;
			case '1373':
				content = "<option value='1'>个人计划</option><option value='2'>个人总结</option><option value='3'>备课组计划</option><option value='4'>备课组总结</option>";
				chosenType.append(content);
				chosenType.val(category);
				chosenType.trigger("chosen:updated");
				$(".a2").show();
			break;
			}
	
		}
	
	function bytesToSize(bytes) { 
	       if (bytes == 0 || bytes == "") return '0 B';  
	        var k = 1024;  
	        sizes = ['B','KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];  
	        i = Math.floor(Math.log(bytes) / Math.log(k));
	        var size = (bytes / Math.pow(k, i)).toFixed(2);
		    return size + ' ' + sizes[i];   
		}

	
});