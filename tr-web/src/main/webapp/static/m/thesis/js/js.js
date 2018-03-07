define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){
		init();
	}); 
	function init() {
		var  myScroll = new IScroll('#wrap',{
	    	 scrollbars: true, 
	    	 mouseWheel:true,
	    	 fadeScrollbars:true,
	    	 click:true,
	    });
		myScroll.on('scrollEnd',demo); 
	    function demo(){ 
	    	 if ((this.y - this.maxScrollY) <= -(500+this.maxScrollY)) {  
	   	     	  $(".return_1").show(); 
	   	     	  $(".return_1").click(function (){
	   	     		myScroll.scrollTo(0, 0, 1000, IScroll.utils.ease.circular);
	 	          });
	   	     }else{
	   	     	 $(".return_1").hide();      
	   	     }
	    };
		$('.thesis_word').on('click','.thesis_img_2',function(){
			$(this).parent().addClass('border').siblings().removeClass('border');
			var id=$(this).attr("data_id");
	    	$("#cw_option_mask_"+id).show();
	      	$('.cw_option_'+id).show();
	          $(".cw_option_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
	          $(".cw_option_jz_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
	          $(".cw_option_del").css({'-webkit-animation':'del .5s',"-webkit-animation-fill-mode":"forwards"});
	          $(".cw_option_share").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
	          $(".cw_option_qx_share").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
	          $(".cw_option_down").css({'-webkit-animation': 'down .5s',"-webkit-animation-fill-mode":"forwards"});
		})
		$('.cw_option_close').click(function(){
			 var id=$(this).parent().attr("data_id");
			 $(this).parent().parent().removeClass('border');
    		 $("#cw_option_mask_"+id).hide("slow");
    		 $('.cw_option_'+id).hide("slow");
    		 $(".cw_option_"+id+".cw_option_edit").css('-webkit-animation', 'edit .5s');
    		 $(".cw_option_"+id+".cw_option_del").css('-webkit-animation', 'del .5s');
    		 $(".cw_option_"+id+".cw_option_share").css('-webkit-animation', 'share .5s');
    		 $(".cw_option_"+id+".cw_option_qx_share").css('-webkit-animation', 'share .5s');
    		 $(".cw_option_"+id+".cw_option_down").css('-webkit-animation', 'down .5s');
		})
	      $('.subject').click(function (){
	    	  var subjectId=$('#subjectId').val();
	          $('.menu_list').toggle();
	          $('.menu_list1').hide();
	          $('#wrap1 p').attr('class','');
	          $('#'+subjectId).attr('class','act');
	          if($('.menu_list').css("display") == 'block' ){
        		  $('.add_thesis_wrap1').show();   //如果元素为隐藏,则将它显现
        	  }else{
        		  $('.add_thesis_wrap1').hide();     //如果元素为显现,则将其隐藏
        	  };
	      });
		  $('.add_thesis_wrap1').click(function(){ 
			  $('.menu_list').hide();
			  $('.menu_list1').hide();
		  }); 
	      $('.classifying').click(function (){
	    	  var thesisType=$('#thesisType').val();
	          $('.menu_list1').toggle();
	          $('.menu_list').hide();
	          $('#wrap2 p').attr('class','');
	          $('#'+thesisType).attr('class','act');
	          if($('.menu_list1').css("display") == 'block' ){
        		  $('.add_thesis_wrap1').show();   //如果元素为隐藏,则将它显现
        	  }else{
        		  $('.add_thesis_wrap1').hide();     //如果元素为显现,则将其隐藏
        	  };
	      });
	      $('.add_cour').click(function (){
	      	$('.add_thesis_wrap').show();
	      	$('.add_thesis_wrap1').show();
	      	$('#addorUpdate').html("上传教学文章");
	      	$('#save').attr('class','btn_thesis');
    		$('#save').val("上传");
    		$('#editId').val('');//id置空
    		//$('#originFileName').css({'z-index':'0','text-align':'center','background-color':'#f1f1f1'});
    		$('#fileToUpload').css("","");
    		$("#fileuploadContainer").find(".enclosure_name").hide();
	    	$("#fileuploadContainer").find("strong").show();
	      	$('.mask').show();
	      	 var  myScroll1 = new IScroll('#wrap1',{
		    	 scrollbars: true, 
		    	 mouseWheel:true,
		    	 fadeScrollbars:true,
		    	 click:true,
		    });
	      	var  myScroll2 = new IScroll('#wrap2',{
		    	 scrollbars: true, 
		    	 mouseWheel:true,
		    	 fadeScrollbars:true,
		    	 click:true,
		    });
	      	$('.menu_list').hide();
	      	$('.menu_list1').hide();
	      });
	      $('.close').click(function (){
	      	$('.add_thesis_wrap').hide();
	      	$('.edit_thesis_wrap').hide();
	      	$('.del_thesis_wrap').hide();
	      	$('.share_thesis_wrap').hide()
	      	$('.opinions_thesis_wrap').hide();
	      	$('.mask').hide();
	      });
	      $('.enclosure_del').click(function (){
	    	  $("#fileuploadContainer").find(".enclosure_name").hide();
	    	  $("#fileuploadContainer").find("strong").show();
		      $('#originFileName').val("请选择文件");
		      $('#resId').val("");
		  });
	      //选择框
	      $('#wrap1 p').click(function(){
	    	  $( this ).toggleClass("act").siblings().removeClass("act"); 
	    	  $('.menu_list').hide();
	    	  $("#subjectId").val($(this).attr("id"));
		      $("#selectSubject").html($(this).text()+'<q></q>'); 
	    	  
	      })
	      $('#wrap2 p').click(function(){
	    	  $( this ).toggleClass("act").siblings().removeClass("act"); 
	    	  $('.menu_list1').hide();
	    	  $("#thesisType").val($(this).text());
		      $("#selectType").html($(this).text()+'<q></q>');
	      })
	      //修改教学文章
	      $('.cw_option_edit').click(function(){
	    	  var edit=$(this).parent();
	    	  var id=edit.attr("data_id");
	    	  var thesisTitle=edit.attr("data_title");
	    	  var thesisType=edit.attr("data_thesisType");
	    	  var resId=edit.attr("data_resId");
	    	  var name=edit.attr("data_name");
	    	  var subjectId=edit.attr("data_subjectId");
	    	  if (resId != "") {
	  			$.ajax({
	  				type : "post",
	  				dataType : "json",
	  				url : _WEB_CONTEXT_ + "/jy/thesis/getFileById.json",
	  				data : {
	  					"resId" : resId
	  				},
	  				success : function(data) {
	  					if (data.res != null) {
	  						var name=data.res.name.substring(0,10);
	  						$("#originFileName").val(name + "..." + data.res.ext);
	  						$("#uploadFileName").html(name + "..." + data.res.ext);
	  						$("#resId").val(resId);
	  						//$(".mes_file_process").html("");
	  					 }
	  				  }
	  			   });
	  		    }
	    	    //调整修改样式
	    	    $('#fileuploadContainer').find('.enclosure_name').show();
	    	    $('#fileuploadContainer').find('strong').hide();
	    	    $(".formError").remove();
	    		$('.add_thesis_wrap').show();
	    		$('#addorUpdate').html("修改教学文章")
		       	$('.mask').show();
	    		$('#save').attr('class','btn_edit');
	    		$('#save').val("修改");
	    		$('#cancel').attr('style','');
		    	 var  myScroll1 = new IScroll('#wrap1',{
			    	 scrollbars: true, 
			    	 mouseWheel:true,
			    	 fadeScrollbars:true,
			    	 click:true,
			    });
		      	var  myScroll2 = new IScroll('#wrap2',{
			    	 scrollbars: true, 
			    	 mouseWheel:true,
			    	 fadeScrollbars:true,
			    	 click:true,
			    });
		    	 $('.menu_list').hide();
		 	 	 $('.menu_list1').hide(); 
		 	 	 $('#editId').val(id);  
		 	 	 $('#thesisTitle').val(thesisTitle);
		 	 	 $('#subjectId').val(subjectId);
		 	 	 $('#thesisType').val(thesisType);
		 	 	 $("#selectType").html(thesisType+'<q></q>');
		 	 	 $("#selectSubject").html(name+'<q></q>');
	      })
	      //关闭修改窗口
	      $('.btn_cencel').click(function(){
	    	  $('.add_thesis_wrap').hide();
		      $('.edit_thesis_wrap').hide();
		      $('.del_thesis_wrap').hide();
		      $('.share_thesis_wrap').hide()
		      $('.opinions_thesis_wrap').hide();
		      $('.mask').hide();
	      });
	      //删除教学文章
	      $('.cw_option_del').click(function() {
	    	  var edit=$(this).parent();
	    	  var id=edit.attr("data_id");
	    	  var resId=edit.attr("data_resId");
	    	  $('.del_thesis_wrap').show();
	          $('.mask').show();
	    	  $('#deleteThesis').click(function (){
	    		  $.ajax({
		  				type : "post",
		  				dataType : "json",
		  				url : _WEB_CONTEXT_ + "/jy/thesis/delete.json",
		  				data : {
		  					"id" : id,
		  					"resId" : resId
		  				},
		  				success : function(data) {
		  					if (data.isOk) {
		  						//alert("删除成功！");
		  						location.href =  _WEB_CONTEXT_+"/jy/thesis/index";
		  					} else {
		  						successAlert("删除失败！");
		  					}
		  				}
		  			})
		          
		        });
	      })
	      //分享教学文章
	      $('.cw_option_share').click(function(){
	    	  var edit=$(this).parent();
	    	  var id=edit.attr("data_id");
	    	  var resId=edit.attr("data_resId");
	    	  $('.share_thesis_wrap').show();
	          $('.mask').show();
	          tapId="shareThesis";
	          $('#qxshareThesis').attr("style","display:none;");
	          $('#shareThesis').attr("style","");
	          $('#isOrNotShare').html("您确定要分享该教学文章吗？分享后，您的小伙伴就可以看到喽！您也可以去“学校首页”中查看其他小伙伴的课件哦！");
	    	  $('#'+tapId).click(function (){
	    		  $.ajax({
		  				type : "post",
		  				dataType : "json",
		  				url : _WEB_CONTEXT_ + "/jy/thesis/share.json",
		  				data : {
		  					"id" : id,
		  					"isShare" : 1
		  				},
		  				success : function(data) {
		  						if (data.isOk) {
		  							//alert("分享成功！");
		  							location.href =  _WEB_CONTEXT_+"/jy/thesis/index";
		  						} else {
		  							successAlert("分享失败！");
		  						}
		  				}
		  			})
		       });
	  	  })
	  	  //取消分享
	  	  $('.cw_option_qx_share').click(function(){
	  		  var edit=$(this).parent();
	    	  var id=edit.attr("data_id");
	    	  var resId=edit.attr("data_resId");
	    	  $('.share_thesis_wrap').show();
	          $('.mask').show();
	          tapId="qxshareThesis";
        	  $('#qxshareThesis').attr("style","");
        	  $('#shareThesis').attr("style","display:none;");
        	  $('#isOrNotShare').html("您确定要取消分享该教学文章吗？取消分享后，您的小伙伴就看不到喽！");
        	  $('#'+tapId).click(function (){
	    		  $.ajax({
		  				type : "post",
		  				dataType : "json",
		  				url : _WEB_CONTEXT_ + "/jy/thesis/share.json",
		  				data : {
		  					"id" : id,
		  					"isShare" : 0
		  				},
		  				success : function(data) {
		  						if (data.isOk) {
		  							//alert("取消分享成功！");
		  							location.href =  _WEB_CONTEXT_+"/jy/thesis/index";
		  						} else {
		  							successAlert("取消分享失败！");
		  						}
		  				}
		  			})
		       });
	  	  })
	  	  //教学文章评论弹出框
	      $('.thesis_img_4').click(function (){
	          	$('.mask').show();
	          	$('.opinions_comment_wrap').show();
	          	$("#iframe_comment").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=0&resType=10&resId="+$(this).attr('data-resId'));
	       }); 
	      $('.opinions_comment span').click(function(){
	    	  $(".opinions_comment_wrap").hide();
	    	  $('.mask').hide();
	      });
	       //表单提交
	     	window.save=function () {
	     			$.ajax({
	     				type : "post",
	     				dataType : "json",
	     				url : _WEB_CONTEXT_ + "/jy/thesis/save.json",
	     				data : $("#th_form").serialize(),
	     				success : function(data) {
	     					successAlert("保存成功",false,function(){
	     						//刷新页面 
	     						location.href =  _WEB_CONTEXT_+"/jy/thesis/index?pageSize=11&site_preference=mobile";
	     					});
	     				}
	     			});
	     	}
	     	//上传教学文章
	        window.backSave = function(data){
	    		if(data.code==0){
	    			$("#resId").val(data.data);
	    			save();
	    		}
	        }
	    	window.start=function () {
	    		var thesisTitle=$('#thesisTitle').val();
	    		if(thesisTitle==null||thesisTitle==""){
	    			successAlert("请输入标题");
	    			return false;
	    		}
	    		if(thesisTitle.length>30){
	    			successAlert("标题不能超过30个字");
	    			return false;
	    		}
	    		$('#uploadSave').hide();
	    		$('.btn_sc').show();
	    		var resId = $("#resId").val();
	        	var file = document.getElementById("fileToUpload_1").files[0];
	        	//var vname=$('#fileToUpload').val();originFileName
	        	$('#originFileName').val($('#fileToUpload_1').val().substr(12,50));
	        	if(file){
	        		return true;
	        	}else if(!file && resId!=null && resId!=""){
	        		save();
	        		return false;
	        	}else{
	        		alert("请选择要上传的文件！",true,null);
	        		return false;
	        	}
	    	}
	    	$('.search').click(function() {
	    		var search = $("#search").val();
	    		//if (search != null && search != "") {
	    			$("#search_form").submit();
	    			//$("#whole	").attr("style", "margin-top:-2px;");
	    		//}

	    	});
	    	//查看教学文章
	        $('.thesis_word p').click(function(){
	        	var resId=$(this).attr("data-resId");
	        	scanResFile_m(resId);
	        })
	};
});