define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init(); 
	});
    function init() { 
    	var content_bottom2 = new IScroll('.content_bottom2',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true, 
      	}); 
    	$('.courseware_img_2').click(function (){
    		var plan = $(this).parent();
    		plan.addClass('border').siblings().removeClass('border');
          	plan.find(".cw_option_mask").show();
          	plan.find('.cw_option').show();
          	plan.find(".cw_option_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_del").css({'-webkit-animation':'del .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_submit").css({'-webkit-animation': 'submit .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_share").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
          	plan.find(".cw_option_down").css({'-webkit-animation': 'down .5s',"-webkit-animation-fill-mode":"forwards"});
    	});
    	 $('.cw_option_close').click(function (){
    		 var plan = $(this).parent().parent();
    		 plan.find('.cw_option').hide();
    		 plan.find(".cw_option_mask").hide();
    		 plan.removeClass('border');
    	 });
    	 //关闭弹框
    	 $('.close1').click(function (){ 
    		 $('.partake_school_wrap1').hide();
    		 $('.mask1').hide();
    	 });
    	 $('#b_edit1').click(function(){
    		 $('.del_upload_wrap').hide();
    		 $('.add_upload_wrap').hide();
    		 $('.release_upload_wrap').hide();
    		 $('.mask').hide();
    	 });
    	 $('.close').click(function (){
    		 $('.del_upload_wrap').hide();
    		 $('.add_upload_wrap').hide();
    		 $('.release_upload_wrap').hide();
    		 $('.mask').hide();
    	 });
    	 //取消弹框
    	 $('.btn_cencel').click(function(){
    		 $('.del_upload_wrap').hide();
    		 $('.release_upload_wrap').hide();
    		 $('.mask').hide();
    	 })
    	 //删除
    	 $('.cw_option_del').click(function (){
    		 $('.del_upload_wrap').show();
    		 $('.mask').show();
    		 $('.btn_confirm').attr('data-id',$(this).parent().attr('data-id'));
    		 $('.btn_confirm').attr('data-resId',$(this).parent().attr('data-resId'));
    	 });
    	 $('.add_cour').click(function (){
    		 $('.add_upload_wrap').show();
    		 $('.mask').show();
    		 $('#uploadId').show();
    		 $('.enclosure_name').hide();
    		 $('.add_upload_title h3').html("上传教研进度表");
    		 $('#save').attr("class","btn_upload");
    		 $('#save').val("上传");
    		 $('#b_edit1').hide();
    		 $('.select1').html("请选择<q></q>")
    		 $('.select2').html("请选择<q></q>")
    		 $('.select3').html("请选择<q></q>")
    		 $('#qt_planName').val("");
    		 $('#id').val("");
    		 $('#schoolTeachCircleId').val("");
    		 $('#subjectId').val("");
    		 $('#gradeId').val("");
    		 $("#resId").val("");
    		//查看隐藏
    		 $('#viewOrg').hide();
    	 });
    	 $('.select1').click(function (){
    		 $('.menu_list1').toggle();
	       	  if($('.menu_list1').css("display") == 'block' ){
	       		  $('.add_upload_wrap1').show();   //如果元素为隐藏,则将它显现
	       		  var  menu_list_wrap1 = new IScroll('.menu_list_wrap1',{
	               		scrollbars:true,
	               		mouseWheel:true,
	               		fadeScrollbars:true,
	               		click:true,
	               	});
	       	  }else{
	       		  $('.add_upload_wrap1').hide();     //如果元素为显现,则将其隐藏
	       	  } 
	       	  var  menu_list_wrap1 = new IScroll('.menu_list_wrap1',{
	           		scrollbars:true,
	           		mouseWheel:true,
	           		fadeScrollbars:true,
	           		click:true,
	           	});
    	 });
    	 $('.select2').click(function (){
    		 $('.menu_list2').toggle();
	       	  if($('.menu_list2').css("display") == 'block' ){
	       		  $('.add_upload_wrap1').show();   //如果元素为隐藏,则将它显现
	       		  var  menu_list_wrap1 = new IScroll('.menu_list_wrap2',{
	               		scrollbars:true,
	               		mouseWheel:true,
	               		fadeScrollbars:true,
	               		click:true,
	               	});
	       	  }else{
	       		  $('.add_upload_wrap2').hide();     //如果元素为显现,则将其隐藏
	       	  } 
	       	  var  menu_list_wrap1 = new IScroll('.menu_list_wrap2',{
	           		scrollbars:true,
	           		mouseWheel:true,
	           		fadeScrollbars:true,
	           		click:true,
	           	});
    	 });
    	 $('.select3').click(function (){
    		 $('.menu_list3').toggle();
	       	  if($('.menu_list3').css("display") == 'block' ){
	       		  $('.add_upload_wrap1').show();   //如果元素为隐藏,则将它显现
	       		  var  menu_list_wrap1 = new IScroll('.menu_list_wrap3',{
	               		scrollbars:true,
	               		mouseWheel:true,
	               		fadeScrollbars:true,
	               		click:true,
	               	});
	       	  }else{
	       		  $('.add_upload_wrap3').hide();     //如果元素为显现,则将其隐藏
	       	  } 
	       	  var  menu_list_wrap1 = new IScroll('.menu_list_wrap3',{
	           		scrollbars:true,
	           		mouseWheel:true,
	           		fadeScrollbars:true,
	           		click:true,
	           	});
    	 });
    	 $('.add_upload_wrap1').click(function(){ 
       	     $('.menu_list1').hide();
       	     $('.menu_list2').hide();
       	     $('.menu_list3').hide();
         }); 
    	 //选择框
    	 $('.menu_list_wrap1 p').click(function(){
    		 $('.menu_list1').hide();
    		 $('#schoolTeachCircleId').val($(this).attr('data-value'));
    		 $('.select1').html($(this).text()+"<q></q>");
    		 //查看显示
    		 $('#viewOrg').show();
    		 $('#viewOrg').attr("data-circleId",$(this).attr('data-value'));
    		 $('#viewOrg').attr("data-name",$(this).text());
    	 });
    	 $('.menu_list_wrap2 p').click(function(){
    		 $('.menu_list2').hide();
    		 $('#subjectId').val($(this).attr('data-value'));
    		 $('.select2').html($(this).text()+"<q></q>");
    	 });
    	 $('.menu_list_wrap3 p').click(function(){
    		 $('.menu_list3').hide();
    		 $('#gradeId').val($(this).attr('data-value'));
    		 $('.select3').html($(this).text()+"<q></q>");
    	 });
    	//表单提交
	     	window.save=function () {
	     			$.ajax({
	     				type : "post",
	     				dataType : "json",
	     				url : _WEB_CONTEXT_ + "/jy/teachschedule/save.json",
	     				data : $("#jy_from").serialize(),
	     				success : function(data) {
	     					if(data.isOk==1){
	     						alert("保存成功")
	     						//刷新页面 site_preference=mobile
			     				location.href =  _WEB_CONTEXT_+"/jy/teachschedule/index";
	     					}
	     				}
	     			});
	     	}
	     	//上传教研进度表
	        window.afterUpload = function(data){
	    		if(data.code==0){
	    			$("#resId").val(data.data);
	    			save();
	    		}
	        }
	    	window.start=function () {
	    		var schoolTeachCircleId=$('#schoolTeachCircleId').val();
	    		var subjectId=$('#subjectId').val();
	    		var gradeId=$('#gradeId').val();
	    		var name=$('#qt_planName').val();
	    		var resId = $("#resId").val();
	    		if(schoolTeachCircleId==null||schoolTeachCircleId==""){
	    			successAlert('请选择校际教研圈');
	    			return false;
	    		}
	    		if(subjectId==null||subjectId==""){
	    			successAlert('请选择学科');
	    			return false;
	    		}
	    		if(gradeId==null||gradeId==""){
	    			successAlert('请选择年级');
	    			return false;
	    		}
	    		if(name==null||name==""){
	    			successAlert('标题不能为空,请输入');
	    			return false;
	    		}
	        	var file = document.getElementById("fileToUpload_1").files[0];
	        	//var vname=$('#fileToUpload').val();originFileName
	        	$('#originFileName').val($('#fileToUpload_1').val().substr(12,50));
	        	if(file){
	        		return true;
	        	}else if(!file && resId!=null && resId!=""){
	        		save();
	        		return false;
	        	}else{
	        		alert("请选择要上传的文件！");
	        		return false;
	        	}
	    	}
	    	//修改教研进度表
	    	$('.cw_option_edit').click(function(){
	    		var id=$(this).parent().attr('data-id');
	    		var title=$(this).parent().attr('data-name');
	    		var gradeName=$(this).parent().attr('data-gradeName');
	    		var subjectName=$(this).parent().attr('data-subjectName');
	    		var circleName=$(this).parent().attr('data-circleName');
	    		var resId=$(this).parent().attr('data-resId');
	    		var circleId=$(this).parent().attr('data-circleId');
	    		var subjectId=$(this).parent().attr('data-subjectId');
	    		var gradeId=$(this).parent().attr('data-gradeId');
    		    if (resId != "") {
  	  			  $.ajax({
  	  				  type : "post",
  	  				  dataType : "json",
  	  				  url : _WEB_CONTEXT_ + "/jy/teachschedule/getFileById.json",
  	  				  data : {
  	  					  "resId" : resId
  	  				  },
  	  				  success : function(data) {
  	  					 if (data.res != null) {
  	  						 var name=data.res.name.substring(0,13);
  	  						 $("#originFileName").val(name + "." + data.res.ext);
  	  						 $("#uploadFileName").html(name + "." + data.res.ext);
  	  						 $("#resId").val(resId);
  	  						//$(".mes_file_process").html("");
  	  					   }
  	  				    }
  	  			     });
  	  		      }
	    		 $('.add_upload_wrap').show();
	    		 $('.mask').show();
	    		 $('#uploadId').hide();
	    		 $('.enclosure_name').show();
	    		 $('#save').attr("class","edit1");
	    		 $('#save').val("修改");
	    		 $('#b_edit1').show();
	    		 $('.add_upload_title h3').html("修改教研进度表");
	    		 $('.select1').html(circleName+"<q></q>");
	    		 $('.select2').html(subjectName+"<q></q>");
	    		 $('.select3').html(gradeName+"<q></q>");
	    		 $('#id').val(id);
	    		 $('#qt_planName').val(title);
	    		 $('#schoolTeachCircleId').val(circleId);
	    		 $('#subjectId').val(subjectId);
	    		 $('#gradeId').val(gradeId);
	    		 //查看显示
	    		 $('#viewOrg').show();
	    		 $('#viewOrg').attr("data-circleId",circleId);
	    		 $('#viewOrg').attr("data-name",circleName);
	    		 
	    	});
	    	$('.enclosure_del').click(function(){
	    		 $('#uploadId').show();
	    		 $('.enclosure_name').hide();
	    	});
	    	//删除教学进度表
	    	$('.btn_confirm').click(function(){
	    		var id=$(this).attr('data-id');
	    		var resId=$(this).attr('data-resId');
	    		$.ajax({
					type : "post",
					dataType : "json",
					url : _WEB_CONTEXT_ + "/jy/teachschedule/delete.json",
					data : {
						"id" : id,
						"resId" : resId
					},
					success : function(data) {
						if (data.isOk) {
							location.href =  _WEB_CONTEXT_+"/jy/teachschedule/index";
						} else {
							successAlert("删除失败！");
						}
					}
				})
	    	});
	    	//发布教研进度表
	    	$('.cw_option_publish').click(function(){
	    		var id=$(this).parent().attr('data-id');
	    		var isRelease=$(this).attr('data-isRelease');
	    		$('.release_width span').text('您确定要发布该教研进度表吗？');
	    		$('.release_upload_wrap').show();
	    		$('.mask').show();
	    		$('.btn_confirm_rel').attr('data-id',id);
	    		$('.btn_confirm_rel').attr('data-isRelease',isRelease);
	    	});
	    	$('.cw_option_qx_publish').click(function(){
	    		var id=$(this).parent().attr('data-id');
	    		var isRelease=$(this).attr('data-isRelease');
	    		$('.release_width span').text('您确定要取消发布该教研进度表吗？');
	    		$('.release_upload_wrap').show();
	    		$('.mask').show();
	    		$('.btn_confirm_rel').attr('data-id',id);
	    		$('.btn_confirm_rel').attr('data-isRelease',isRelease);
	    	});
	    	//确定发布
	    	$('.btn_confirm_rel').click(function(){
	    		var id=$(this).attr('data-id');
	    		var isRelease=$(this).attr('data-isRelease');
	    		$.ajax({
	    			type: "post",
	    			dataType: "json",
	    			url: _WEB_CONTEXT_+"/jy/teachschedule/release.json",
	    			data:{
						"id" : id,
						"isRelease" : isRelease
					     },
					     success : function(data) {
								if (data.isRelease) {
									if (data.isOk) {
										successAlert("发布成功！",false,function(){
											location.href =  _WEB_CONTEXT_+"/jy/teachschedule/index";
										});
									} else {
										successAlert("发布失败！");
									}
								} else {
									if (data.isOk) {
										successAlert("取消发布成功！",false,function(){
											location.href =  _WEB_CONTEXT_+"/jy/teachschedule/index";
										});
									} else {
										successAlert("取消发布失败！");
									}
								}
							}
	    		  })
	    	});
	    	//教研进度表查看
	    	$('.courseware_ppt p').click(function(){
	    		var id=$(this).attr("data-id");
	    		location.href =  _WEB_CONTEXT_+"/jy/teachschedule/view?id="+id;
	    	})
    }
});