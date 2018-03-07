var jq = $;
// 类别模板
var categoryTemplate;
define('planSummary',[ 'require', 'hogan', 'jquery' ],
		function(require) {
			$ = require('jquery');
			var hogan = require('hogan');
			$(function() {
				initEvent();
				categoryTemplate = hogan.compile($("#categoryTemplate").html());
				$('.close').click(function (){
					$.unblockUI();
					//location.reload(); 
				});
				initCategoryCount();
			});
			
			function initCategoryCount(){
				$(".PreCont_left").each(function(i){
					var obj = $(this).next(".PreCont_right");
					var children = obj.children(".Pre_cont_right_1_dl");
					$(this).find("span").html($(this).find("span").html()+children.length);
				});
			}

			function initEvent() {
				// 新增
				$('.addPlanSummary').click(function() {
					var sysRoleId = $(this).attr('data-sysRoleId');
					var userSpaceId = $(this).attr('data-userSpaceId');
					$('#title').val("");
					$('#id').val("");
					$("#uploadFile").val('');
					$("#hiddenFileId").val('');
					$(".mes_file_process").html("");
					$('#plainSummaryLabel_input').val('');
					$('#category').html(getCategoryHtml(sysRoleId));
					$('#userSpaceId').val(userSpaceId);
					$('#subject').val($(this).attr('data-subject'));
					$('#grade').val($(this).attr('data-grade'));
					$('#title').attr('data-default', 'true');
					$('#savePs').removeClass("saveEdit").addClass("savePs");
					$('#uploadUiId').attr("isedit","0");
					$('#cancelSave').hide();
					$("#planSummaryWin").dialog({width:450,height:400});
				});
				
				$("#category").delegate("input[name=category]","click", function(){
					setLabelExm($(this).val());
				});
				
				function setLabelExm(val){
					var tagStr="";
					if (val==1 || val==3){
						tagStr = '标签示例（教研计划、月度计划）'; 
					} else if (val==2 || val==4) {
						tagStr = '标签示例（备课总结、考试总结）';
					}
					$("#plainSummaryLabel").html(tagStr);
				}
				
				
				/** 编辑方法* */
				$('.menu_li_edit_0').click(function() {
					var category = $(this).attr('data-category');
					var sysRoleId = $(this).attr('data-sysRoleId');
					$('#savePs').removeClass("savePs").addClass("saveEdit");
					$('#cancelSave').show();
					$('#category').html(getCategoryHtml(sysRoleId, category));
					$('#id').val($(this).attr('data-id'));
					$('#title').val($(this).attr('data-title'));
					$('#userSpaceId').val($(this).attr('data-userSpaceId'));
					$('#title').attr('data-default', 'false');
					$("input[type='radio'][name='term'][value='"+$(this).attr('data-term')+"']").prop("checked",true);
					$('#uploadFile').val($(this).attr('data-fileName'));
					$('#form_contentFileKey').val($(this).attr('data-fileId'));
					$('#uploadUiId').attr("isedit","1");
					$('#plainSummaryLabel_input').val($(this).attr("data-label"));
					var categroy = $("input[name=category]:checked").val();
					if (category){
						setLabelExm(category);
					}
					$("#planSummaryWin").dialog({width:450,height:400});
				});

				// 删除
				$('.menu_li_del_0').click(
						function() {
							$('#id').val($(this).attr('data-id'));
							var r = confirm('您确定要删除“'
									+ $(this).attr('data-title') + '”吗？');
							if (r) {
								$.get('./jy/planSummary/'
										+ $(this).attr('data-id')
										+ '/delete.json', function(data) {
									if (data.result.code == 1) {
										reload();
									} else {
										alert('删除失败！');
									}
								}, 'json');
							}
						});

				// 提交
				$('.menu_li_submit_0').click(
						function() {
							$.get('./jy/planSummary/' + $(this).attr('data-id')
									+ '/submit.json', function(data) {
								if (data.result.code == 1) {
									alert('恭喜您提交成功，已提交到学校管理室！');
									reload();
								} else {
									alert('提交失败！');
								}
							}, 'json');
						});

				// 取消提交
				$('.menu_li_submit_1')
						.click(
								function() {
									var r = confirm('您确定要取消提交已选择的计划总结吗？取消提交后，学校管理者将看不到这些内容！');
									if (r) {
										$.get('./jy/planSummary/'
												+ $(this).attr('data-id')
												+ '/cancelSubmit.json',
												function(data) {
													if (data.result.code == 1) {
														reload();
													} else {
														alert('提交失败！');
													}
												}, 'json');
									}
								});

				// 分享
				$('.menu_li_share_0').click(
						function() {
							var r = confirm('您确定要分享“'
									+ $(this).attr('data-title')
									+ '”吗？分享成功后，您的小伙伴们就可以看到喽！');
							if (r) {
								$.get('./jy/planSummary/'
										+ $(this).attr('data-id')
										+ '/share.json', function(data) {
									if (data.result.code == 1) {
										reload();
									} else {
										alert('分享失败！');
									}
								}, 'json');
							}
						});

				// 取消分享
				$('.menu_li_share_1').click(
						function() {
							$.get('./jy/planSummary/' + $(this).attr('data-id')
									+ '/cancelShare.json', function(data) {
								if (data.result.code == 1) {
									alert('您已成功取消分享！');
									reload();
								} else {
									alert('取消分享失败！');
								}
							}, 'json');
						});

				// 发布
				$('.menu_li_punish_0').click(function() {
					$('#punsihSubmit').attr('data-id',$(this).attr('data-id'));
					$("#punishWin").dialog({width:450,height:300});
					 var config = {
							'.chosen-select'           : {},
							'.chosen-select-deselect'  : {allow_single_deselect: true},
							'.chosen-select-deselect' : {disable_search:true}
							};
					for (var selector in config) {
						jq(selector).chosen(config[selector]);
					}
//									var r = confirm('您确定要发布“'
//											+ $(this).attr('data-title')
//											+ '”吗？发布成功后，您的小伙伴们就可以看到喽！您也可以去“教研动态”中查看其他小伙伴发布的计划总结！');
//									if (r) {
//										$.get('./jy/planSummary/'
//												+ $(this).attr('data-id')
//												+ '/punish.json',
//												function(data) {
//													if (data.result.code == 1) {
//														reload();
//													} else {
//														alert('发布失败！');
//													}
//												}, 'json');
//									}
								});

				// 取消发布
				$('.menu_li_punish_1').click(
						function() {
							$.get('./jy/planSummary/' + $(this).attr('data-id')
									+ '/cancelPunsih.json', function(data) {
								if (data.result.code == 1) {
									alert('您已成功取消发布！');
									reload();
								} else {
									alert('取消发布失败！');
								}
							}, 'json');
						});
				$('#category').delegate(
						'input[name="category"]',
						'click',
						function() {
							if ($('#title').attr('data-default') == 'true') {
								var year = parseInt($('#schoolYear').val());
								var endYear = year + 1;
								var term = $('#schoolTerm').val() == 0 ? '上学期'
										: '下学期';
								$("input[name=term]:eq("+$('#schoolTerm').val()+")").attr("checked",'checked');
								var subject = $('#subject').val();
								var grade = $('#grade').val();
								var category = $(this).attr('data-category');
								$('#categoryFen').val(category);
								var html = year + '-' + endYear + '学年' + term
										+ grade + subject + category;
								$('#title').val(html);
							}
						});
                 $('input[name="term"]').click(function(){
                	 if ($('#title').attr('data-default') == 'true') {
                	    var year = parseInt($('#schoolYear').val());
						var endYear = year + 1;
						var term = $('input[name="term"]:checked').attr("data-term");
						$('#schoolTerm').val($('input[name="term"]:checked').val());
						var subject = $('#subject').val();
						var grade = $('#grade').val();
						var category =null;
						if($('#categoryFen').val()!=null||$('#categoryFen').val()!=""){
							category = $('#categoryFen').val();
						}else{
							category="";
						}
						var html = year + '-' + endYear + '学年' + term
								+ grade + subject + category;
						$('#title').val(html);
                	 }
                 })
				// 用户单独修改标题后，不在使用默认标题
				$('#title').change(function() {
					if ($(this).attr('data-default') == 'true') {
						$(this).attr('data-default', 'false');
					}
				});
				// 单击图标
				$('.fileIcon').click(
						function() {
							window.open(_WEB_CONTEXT_ + '/jy/planSummary/'+$(this).attr('data-id')+'/viewFile', '');
						});
				$('.event_check_1,.event_check_2').click(
						function() {
							var id = $(this).attr('data-id');
							//状态设置为存在已查阅记录
							$.ajax(_WEB_CONTEXT_+'/jy/planSummary/'+id+'/checkState?checkState=2',{
								type:'put',
								'dataType':'json'
							});
							$(this).find('.menu_li_check_1').removeClass('menu_li_check_1');
							//弹出审阅浏览框
							
							var resType = $(this).attr('data-resType');
							$('#checkedBox').attr(
									'src',
									'jy/check/infoIndex?flags=false&resType='
											+ resType + '&authorId='
											+ $('#currentUserId').val()
											+ '&resId=' + id);
							$("#checkWin").dialog({width:1125,height:540});
						});

				$('.event_review_1,.event_review_2').click(
						function() {
							var id = $(this).attr('data-id');
							//状态设置为存在已查阅记录
							$.ajax(_WEB_CONTEXT_+'/jy/planSummary/'+id+'/reviewState?reviewState=2',{
								type:'put',
								'dataType':'json'
							});
							
							var resType = $(this).attr('data-resType');
							$('#commentBox').attr(
									'src',
									'jy/comment/list?flags=true&resType='
											+ resType + '&authorId='
											+ $('#currentUserId').val()
											+ '&resId=' + id);
							$("#reviewWin").dialog({width:1125,height:540});
						});
				$('#cancelSave').click(function(){
					$(".dialog_close").click();
				});
				
				$.ajax('./jy/schoolactivity/joinSchoolCycles',{
					dataType:'json',
					type:'get',
					success:function(r){
						var circleHtml = '';
						var schoolHtml = '';
						if(r.vos.length>0){
							vos=r.vos;
							for(var index in vos){
								var vo = vos[index];
								schoolHtml='<div id="schoolNames_'+vo.id+'"><div class="schoolNames"><ul>';
								circleHtml+='<option value="'+vo.id+'">'+vo.name+'</option>';
								for(var j in vo.orgs){
									var org=vo.orgs[j];
									schoolHtml+='<li title="'+org.orgName+'">'+org.orgName+'</li>';
								}
								schoolHtml+='</ul></div></div>';
								$('#hidden_div_content').append(schoolHtml);
							}
							$('#circleIds').append(circleHtml);
						}
					}
				});
				$('input[name="punishRange"][value="0"]').attr('checked',true);
				$('#punsihSubmit').click(function(){
					if(checkPunishSubmit()){
						var data={};
						var range = $('input[name="punishRange"]:checked').val();
						data['punishRange']=range;
						if(range==1){
							var id = $('#circleIds').val();
							data['schoolCircleId']=id;
						}
						$.ajax('./jy/planSummary/'+ $(this).attr('data-id')+ '/punish?punishRange='+range+'&schoolCircleId='+$('#circleIds').val(),{
							'type': 'put',
							'dataType': 'json',
							'success': function(result){
								if (result.result.code == 1) {
									reload();
								} else {
									alert('发布失败！');
								}
							}
						});
					}
				});
				
				$('#cancelPunish').click(function(){
					$(".dialog_close").click();
				});
			}
			
			function checkPunishSubmit(){
				var range = $('input[name="punishRange"]:checked').val();
				if(range==null){
					alert('请选择发布范围！');
					return false;
				}
				if(range==1&&$('#circleIds').val()==null){
					alert('请选择发布的校际教研圈！');
					return false;
				}
				return true;
			}

			/** 获取类型的html* */
			function getCategoryHtml(sysRoleId, category) {
				var param = {};
				if (sysRoleId == 27) {
					param['TEACHER'] = true;
				} else if (sysRoleId == 1374) {
					param['NJZZ'] = true;
				} else if (sysRoleId == 1375) {
					param['XKZZ'] = true;
				} else if (sysRoleId == 1373) {
					param['BKZZ'] = true;
				} else if (sysRoleId == 1376 || sysRoleId == 1377 || sysRoleId == 2000) {
					param['DIRECTOR-HEADMASTER'] = true;
				}

				if (category == 1) {
					param['personPlan'] = true;
				} else if (category == 2) {
					param['personSummary'] = true;
				} else if (category == 3) {
					param['rolePlan'] = true;
				} else if (category == 4) {
					param['roleSummary'] = true;
				}

				return categoryTemplate.render(param);
			}

			/**
			 * 关闭回调
			 */
			function closeWinCallBack() {
				$('#file_process').empty();
			}
			
			function reload(){
				location.reload();
			}

		});

/**
 * 教研
 * 
 * @returns
 */
function valid() {
	var category = $(':radio[name="category"]:checked').val();
	if (category == null || category == '') {
		alert('分类必选！');
		return false;
	}
	var title = $('#title').val();
	if (title == null || title == '') {
		alert('标题必填！');
		return false;
	}
	if (title.length > 30) {
		alert('最多可输入30个字，请重新输入');
		return false;
	}
	if ($("#uploadFile").val().length==0) {
		alert('必须上传文件！');
		return false;
	}
	return true;
}

/**
 * 保存计划总结回调
 */
function savePs(res) {
	var id = $('#id').val();
	if(res && res.data){
		$('#form_contentFileKey').val(res.data);
	}else{
		$('#uploadFile').val($("li.menu_li_edit_0[data-id='"+id+"']").attr('data-fileName'));
	}
	
	var contentFileName = $('#uploadFile').val();
	var strArr = contentFileName.split('.');
	var contentFileType = strArr[strArr.length - 1];
	$('#form_contentFileName').val(contentFileName);
	$('#form_contentFileType').val(contentFileType);
	
	if (id != null && id != '') {
		$('#planSummaryForm').attr('action','./jy/planSummary/edit');
		$('#planSummaryForm').submit();
	} else {
		$('#planSummaryForm').attr('action','./jy/planSummary/save');
		$('#planSummaryForm').submit();
	}
}

//查看教研圈
function lookTeachCircle(){
	var id=$("#circleIds").val();
	if(id!=""){
		$('#circleSchoolNames').html($('#schoolNames_'+id).html());
		$('#seeShow').show();
	}else{
		$('#circleSchoolNames').html("");
		$('#seeShow').hide();
	}
}
