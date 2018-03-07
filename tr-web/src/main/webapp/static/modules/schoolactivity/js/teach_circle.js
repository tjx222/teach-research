define(['require','jquery','jp/jquery.placeholder.min'],function(require){
	var jq=require('jquery');
	require('jp/jquery.placeholder.min');
	jq(function(){
		jq('input, textarea').placeholder();
		
		//区域省下拉变更事件
		jq("#province").change(function(){
			findAreaListByParentId(jq(this).val(),2);
		});
		
		//区域市下拉变更事件
		jq("#city").change(function(){
			findAreaListByParentId(jq(this).val(),3);
		});
		
		//区域县下拉变更事件
		jq("#county").change(function(){
			setAreaIds();
		});
		
		/**
		 * 获取下级区域列表
		 */
		function findAreaListByParentId(parentId,level){
			var url = _WEB_CONTEXT_+"/jy/schoolactivity/circle/findAreaListByParentId";
			jq.getJSON(url,{"parentId":parentId,"level":level},function(data){
				if(data){
					var options = "";
					for (var index in data) {
						options += '<option value="'+data[index].id+'" >'+data[index].name+'</option>';
					}
					if(options != ""){
						if(level==2){	
							jq("#city").prev("span").show();
							jq("#city_chosen").show();
							jq("#city").html(options).val(data[0].id).trigger('chosen:updated').trigger('change');
						}else if(level==3){
							jq("#county").prev("span").show();
							jq("#county_chosen").show();
							jq("#county").html(options).val(data[0].id).trigger('chosen:updated').trigger('change');
						}
					}else{
						if(level==2 || level==3){
							if(level==2){
								jq("#city").prev("span").hide();
								jq("#city_chosen").hide();
								jq("#city").val("").trigger('change');
							}
							jq("#county").prev("span").hide();
							jq("#county_chosen").hide();
							jq("#county").val("").trigger('change');
						}
					}					
				}
			});
		}
		
		/**
		 * 设置选择区域的Ids
		 */
		function setAreaIds(){
			var ids = "";
			var provinceId = $("#province").val();
			if(provinceId != null && provinceId != ""){
				ids = "," + provinceId + ",";
			}
			var cityId = $("#city").val();
			if(cityId != null && cityId != ""){
				ids += cityId + ",";
			}
			var countyId = $("#county").val();
			if(countyId != null && countyId != ""){
				ids += countyId + ",";
			}
			jq("#areaIds").val(ids);
		}
		
		jq("#province").trigger('change');//激发省下拉框
		
	});
});

//搜索学校
window.searchSchool = function (){
	  var schoolName =  $.trim($("#searchContent").val());
	  var areaIds = $.trim($("#areaIds").val());
	  if(schoolName!=""){
		  $.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/searchSchool.json",
				data:{"schoolName":schoolName,"areaIds":areaIds},
				success:function(data){
					if(data.orgList!=null && data.orgList.length>0){
						var schStr = '';
						$(data.orgList).each(function(index,obj) {
							schStr += '<li isone="'+obj.id+'" title="'+obj.areaName+"，"+obj.shortName+'" id="'+obj.id+'@'+obj.shortName+'@1" ><a class="w168">'+obj.shortName+'</a></li>';
					  	});
						$('#ul1').html(schStr);
					}else{
						$('#ul1').html("");
						alert("没有找到匹配的学校，请您换个关键字再试试！");
					}
				}
			});
	  }else{
		  alert("请输入学校名称！");
	  }
};
//保存校际教研圈
window.submitCircle = function (){
	  if($("#teach_circle").validationEngine('validate')){
		  var schoolOrgs = "";
		  $('#ul2 li').each(function(index,obj) {
			  schoolOrgs += obj.id+",";
		  });
		  if(schoolOrgs!=""){
			  schoolOrgs = schoolOrgs.substring(0, schoolOrgs.length-1);
			  $("#createCircleOrgs").val(schoolOrgs);
			  //判断教研圈是否重复
			  $.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/checkName.json",
					data:{"id":$("#circleId").val(),"name":$("#circleName").val()},
					success:function(data){
						if(data.isOk){
							  $.ajax({
									type:"post",
									dataType:"json",
									url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/save.json",
									data:$("#teach_circle").serialize(),
									success:function(data){
										if(data.isOk){
											alert("校际教研圈保存成功！");
											//刷新页面
											$("#teach_circle")[0].reset();
											window.location.reload();
										}else{
											alert("校际教研圈保存出错！");
										}
									}
								});
						}else{
							alert("该校际教研圈名称已存在，请重新输入！");
							$("#circleName").focus();
						}
					}
				});
		  }else{
			  alert("请选择学校！");
		  }
	  }
};


//删除教研圈
window.deleteCircle = function (circleId){
	  if(confirm("您确定要删除此教研圈吗？")){
		  $.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/delete.json",
				data:{"id":circleId},
				success:function(data){
					if(data.isOk){
						alert("删除教研圈成功！");
						//刷新页面
						$("#teach_circle")[0].reset();
						window.location.reload();
					}else{
						alert("删除教研圈出错！");
					}
				}
			});
	  }
};

//退出教研圈
window.tuichu = function (circleId){
	  if(confirm("您确定要退出此教研圈吗？")){
		  $.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/tuichu.json",
				data:{"id":circleId},
				success:function(data){
					if(data.isOk){
						alert("退出教研圈成功！");
						//刷新页面
						$("#teach_circle")[0].reset();
						window.location.reload();
					}else{
						alert("退出教研圈出错！");
					}
				}
			});
	  }
};

//恢复教研圈
window.huifu = function huifu(circleId){
	  if(confirm("您确定要恢复此教研圈吗？")){
		  $.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/huifu.json",
				data:{"id":circleId},
				success:function(data){
					if(data.isOk){
						alert("恭喜您，恢复成功！");
						//刷新页面
						$("#teach_circle")[0].reset();
						window.location.reload();
					}else{
						alert("恢复教研圈出错！");
					}
				}
			});
	  }
};
//不修改内容
window.notupCircle = function (){
	  $("#teach_circle")[0].reset();
	  $("#ul1").html("");
	  $("#ul2").html("");
	  $("#circleId").val("");
	  $("#btn_xg").css("display","none");
	  $("#btn_bgl").css("display","none");
	  $("#btn_sub").css("display","");
};
//显示置空
window.setNull = function (){
	$("#placeholder_search").html("");
	$("#searchContent").focus();
};
//显示置空
window.setInit = function (){
	var content = $.trim($("#searchContent").val());
	if(content==null || content==""){
  		$("#placeholder_search").html("输入学校名称进行查找");
	}
};
