define(['require','jquery','jp/jquery.placeholder.min'],function(require){
	var jq=require('jquery');
	require('jp/jquery.placeholder.min');
	jq(function(){
		init();
	});
	//初始化
	function init(){
		jq('input, textarea').placeholder();
		jq('.t_r_l_c_li').click(function(){
			jq('.t_r_l_c_li').removeClass('t_r_l_c_li_act');
			jq(this).addClass('t_r_l_c_li_act');
			doSearch({'currentPage':1,'isSearchPage':0});
		});
		jq('.addFriend').click(function(){
			var userIdCompanion=jq(this).attr('data-userIdCompanion');
			var url='./jy/companion/friends/'+userIdCompanion;
			jq.post(url,{},function(result){
				if(result.result.code==1){
					jq('#add_sava').find(".info").html('恭喜您，关注成功！');
					jq(".dialog_close").click(function(){
						location.reload();
					});
				}else{
					jq('#add_sava').find(".info").html('添加好友失败：'+result.result.errorMsg);
				}
				jq("#add_sava").dialog({
					width : 400,
					height : 200
				});
			},'json');
		});
		jq('.ygz').click(function(event) {
			var userIdCompanion = jq(this).attr('data-userIdCompanion');
			var userNameCompanion = jq(this).attr('data-userNameCompanion');
			var url = "./jy/companion/friends/" + userIdCompanion + ".json";
			jq("#cancel_attention").find(".info1").html("你确定要取消关注“" + userNameCompanion + "”吗?");
			jq("#cancel_attention").dialog({
				width : 350,
				height : 200
			});
			jq(".ascertain").click(function() {
				jq(".dialog_close").trigger("click");
				jq.ajax(url, {
					dataType : 'json',
					type : 'delete',
					success : function(result) {						
						if (result.result.code == 1) {
							jq('#add_sava').find(".info").html("取消关注成功！");
							jq(".dialog_close").click(function(){
								location.reload();
							});
						} else {
							jq('#add_sava').find(".info").html(result.result.msg);
						}
						jq("#add_sava").dialog({
							width : 400,
							height : 200
						});
					}
				});
			});
			event.stopPropagation();// 阻止事件冒泡
		});
		jq('#simpleSearch').click(function(){
			doSearch({userName:jq('#search_userName').val(),isSearchPage:1,currentPage:1});
		});
		jq('#advance_search_btn').click(function() {
			jq("#ser_senior").dialog({
				width : 400,
				height : 350
			});
			jq(".chosen-select-deselect").chosen({
				disable_search : true
			});	
		});
		jq('#advanceSearch').click(function(){
			if(isNaN(jq('#Advanced_search_schooleAge').val())){
				jq('#add_sava').find(".info").html("教龄必须是数字！");
				jq("#add_sava").dialog({
					width : 400,
					height : 200
				});
				return;
			}
			var param={
					userName:jq('#Advanced_search_userName').val(),
					roleId:jq('#Advanced_search_roleId').val(),
					subjectId:jq('#Advanced_search_subject').val(),
					gradeId:jq('#Advanced_search_grade').val(),
					profession:jq('#Advanced_search_profession').val(),
					schoolAge:jq('#Advanced_search_schooleAge').val(),
					schoolName:jq('#Advanced_search_schoolName').val(),
					isSearchPage:1,
					currentPage:1
			};
			doSearch(param);
		});
		jq('#reset').click(function(event){
			doSearch({isSearchPage:0});
			event.stopPropagation();
		});
	}
	
	function reset(){
		jq('form_roleId').val('');
		jq('form_userName').val('');
		jq('form_subjectId').val('');
		jq('form_gradeId').val('');
		jq('form_profession').val('');
		jq('form_schoolAge').val('');
	}
	
	function doSearch(param){
		reset();
		var isSameSchool=jq('.t_r_l_c_li.t_r_l_c_li_act').attr('data-isSameSchool');
		jq('#form_isSameSchool').val(isSameSchool);
		jq('#form_roleId').val(param.roleId);
		jq('#form_userName').val(param.userName);
		jq('#form_subjectId').val(param.subjectId);
		jq('#form_gradeId').val(param.gradeId);
		jq('#form_profession').val(param.profession);
		jq('#form_schoolAge').val(param.schoolAge);
		jq('#form_schoolName').val(param.schoolName);
		if(param['currentPage']!=null){
			jq('#currentPage').val(param['currentPage']);
		}
		if(param['isSearchPage']!=null){
			jq('#form_isSearchPage').val(param.isSearchPage);
		}
		jq('#pageForm').submit();
	}
});
