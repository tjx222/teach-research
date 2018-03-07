define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init(); 
	});
    function init() {
    	
    	//区域省下拉变更事件
		$("#province").change(function(){
			findAreaListByParentId($(this).val(),2);
		});
		
		//区域市下拉变更事件
		$("#city").change(function(){
			findAreaListByParentId($(this).val(),3);
		});
		
		//区域县下拉变更事件
		$("#county").change(function(){
			setAreaIds();
		});
		
		/**
		 * 获取下级区域列表
		 */
		function findAreaListByParentId(parentId,level){
			var url = _WEB_CONTEXT_+"/jy/schoolactivity/circle/findAreaListByParentId";
			$.getJSON(url,{"parentId":parentId,"level":level},function(data){
				if(data){
					var options = "";
					for (var index in data) {
						options += '<option value="'+data[index].id+'" >'+data[index].name+'</option>';
					}
					if(options != ""){
						if(level==2){		
							$("#city").html(options).val(data[0].id).trigger('change');
						}else if(level==3){
							$("#county").prev("span").show();
							$("#county").show();
							$("#county").html(options).val(data[0].id).trigger('change');
						}
					}else{
						if(level==3){						
							$("#county").prev("span").hide();
							$("#county").hide();
							$("#county").val("").trigger('change');
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
			$("#areaIds").val(ids);
		}
		
		$("#province").trigger('change');//激发省下拉框
    	
    	var content_bottom2 = new IScroll('.content_bottom2',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true, 
      		click:true
      	});
    	$('.add_cour').click(function (){
    		$('.option_school_title h3').text("创建校际教研圈");
    		$('.mask').show();
    		$('#saveStc').show();
    		$('#edit').hide();
    		$('#b_edit').hide();
    		$('.option_school_wrap').show();
    		$('#circleName').val("校际教研圈");
    		$('#searchContent').val("");
    		$('#createCircleOrgs').val("");
    		$('#ul2').html("");
    		$('.y_option-school span').html("0");
    		var school_cont1 = new IScroll('.option_school_cont1',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true,
          	});
    	});
    	//关闭窗口
    	$('.close').click(function (){
    		$('.mask').hide();
    		$('.option_school_wrap').hide();  
    		$('.del_upload_wrap1').hide(); 
    		$('.del_upload_wrap2').hide(); 
    		$('.del_upload_wrap3').hide(); 
    		$('.partake_school_wrap1').hide();
    		
    	});
    	$('#b_edit').click(function (){
    		$('.mask').hide();
    		$('.option_school_wrap').hide();  
    		$('.del_upload_wrap1').hide(); 
    		$('.del_upload_wrap2').hide(); 
    		$('.partake_school_wrap1').hide();
    	});
    	$('.btn_cencel').click(function (){
    		$('.mask').hide();
    		$('.option_school_wrap').hide();
    		$('.del_upload_wrap').hide(); 
    		$('.del_upload_wrap1').hide(); 
    		$('.del_upload_wrap2').hide();  
    		$('.del_upload_wrap3').hide(); 
    		$('.partake_school_wrap1').hide();
    	});
    	$('.btn_cencel1').click(function (){
    		$('.mask1').hide();
    		$('.del_upload_wrap').hide();  
    	});
    	$('.close2').click(function (){ 
    		$('.del_upload_wrap').hide(); 
    		$('.mask1').hide();
    	});
    	$('.option_school_cont1 ul li span').click(function (){ 
    		$(this).addClass('span_act');
    	});
    	
    	$('.close1').click(function (){
    		$('.yxz_school').hide(); 
    	});
    	$('.y_option-school').click(function (){
    		$('.yxz_school').show();
    		var yxz_school_cont1 = new IScroll('.yxz_school_cont1',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true,
          		click:true,
          	}); 
    	});
    	//查询学校机构
    	$('.search_btn').click(function(){
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
    								schStr += '<li  id="'+obj.id+'@'+obj.shortName+'@1" title="'+obj.shortName+'">'+obj.shortName+'<span></span></li>';
    						  	});
    							$('#ul').html(schStr);
    							$('.option_school_cont2').hide();
    						}else{
    							$('#ul').html("");
    							successAlert("没有找到匹配的学校，请您换个关键字再试试！");
    						}
    					}
    				});
    		  }else{
    			  successAlert("请输入学校名称！");
    		  }
    	});
    	//添加学校至已选择学校
    	$('#ul').on('click','li span',function(){
    		$(this).addClass('span_act');
    		var id=$(this).parent().attr('id');
    		var name=$(this).parent().text();
    		var istrue=true;
    		$('.yxz_school_cont ul').find('li').each(function(){
    			if(id==$(this).attr('id')){
        			successAlert("已选择学校列表中已经存在了此机构，请重新选择！");
        			istrue=false;
        			return;
        		}
    		})
    		if(!istrue){
    			return false;
    		}
    		var num=$('.y_option-school span').text();
    		$('.y_option-school span').text(++num);
    		$('#ul2').append('<li  title="'+name+'" id="'+id+'">'+name+'<span></span></li>');
    	});
    	//删除已选择学校
    	$('.yxz_school_cont ul').on('click','li span',function (){ 
    		$('.del_upload_wrap').show();
    		$('.mask1').show();
    		$(this).parent().addClass('deleteOrg');
    	});
    	$('#deleteOrg').click(function(){
    		$('.del_upload_wrap').hide();
    		$('.mask1').hide();
    		$('.yxz_school_cont ul').find('.deleteOrg').remove();
    		var num=$('.y_option-school span').text();
    		$('.y_option-school span').text(--num);
    	});
    	//保存校际教研圈
    	$('#saveStc').click(function(){
    	   save();
    	});
    	//修改校际教研圈
    	$('#edit').click(function(){
    	   save();
    	});
    	window.save=function(){
    		var schoolOrgs = "";
     	    $('#ul2').find('li').each(function(index,obj) {
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
 			 								successAlert("校际教研圈保存成功！",false,function(){
 			 									//刷新页面
 			 	 								$("#teach_circle")[0].reset();
 			 	 								window.location.reload();
 			 								});
 			 							}else{
		 									successAlert("校际教研圈保存出错！");
 			 							}
 			 						}
 			 					});
 							}else{
 								successAlert("该校际教研圈名称已存在，请重新输入！");
 								$("#circleName").focus();
 							}
 						}
 					});
 			  }else{
 				  successAlert("请选择学校！");
 			  }
 			  
    	}
    	//删除教研圈
    	$('.courseware_img_del').click(function (){
    		var id=$(this).parent().attr("data-id");
    		$('.del_upload_wrap1').show();
    		$('.mask').show();
    		$('#deleteActivity').attr("data-id",id);
    	});
    	$('#deleteActivity').click(function(){
    		var circleId=$(this).attr('data-id');
    		$.ajax({
				type:"post",
				dataType:"json",
				url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/delete.json",
				data:{"id":circleId},
				success:function(data){
					if(data.isOk){
						successAlert("删除教研圈成功！",false,function(){
							//刷新页面
							$("#teach_circle")[0].reset();
							window.location.reload();
						});
					}else{
						successAlert("删除教研圈出错！");
					}
				}
			});
    	});
    	//退出校际教研圈
    	$('.courseware_img_quit').click(function(){
    		var id=$(this).parent().attr("data-id");
    		$('.mask').show();
    		$('.del_upload_wrap2').show();
    		$('#quitSchoolActivity').attr("data-id",id);
    	});
    	$('#quitSchoolActivity').click(function(){
    		var circleId=$(this).attr('data-id');
    		$('.mask').hide();
    		$('.del_upload_wrap2').hide();
    		$.ajax({
  				type:"post",
  				dataType:"json",
  				url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/tuichu.json",
  				data:{"id":circleId},
  				success:function(data){
  					if(data.isOk){
  						successAlert("退出教研圈成功！",false,function(){
  						//刷新页面
  	  						$("#teach_circle")[0].reset();
  	  						window.location.reload();
  						});
  					}else{
  						successAlert("退出教研圈出错！");
  					}
  				}
  			});
    	});
    	//恢复校际教研圈
    	$('.courseware_img_recovery').click(function(){
    		var id=$(this).parent().attr("data-id");
    		$('.mask').show();
    		$('.del_upload_wrap3').show();
    		$('#recoverySchoolActivity').attr("data-id",id);
    	});
    	$('#recoverySchoolActivity').click(function(){
    		var circleId=$(this).attr('data-id');
    		$('.mask').hide();
    		$('.del_upload_wrap3').hide();
    		 $.ajax({
 				type:"post",
 				dataType:"json",
 				url:_WEB_CONTEXT_+"/jy/schoolactivity/circle/huifu.json",
 				data:{"id":circleId},
 				success:function(data){
 					if(data.isOk){
 						successAlert("恭喜您，恢复成功！",false,function(){
 							//刷新页面
 	 						$("#teach_circle")[0].reset();
 	 						window.location.reload();
  						});
 					}else{
 						alert("恢复教研圈出错！");
 					}
 				}
 			});
    	});
    	
    }
});