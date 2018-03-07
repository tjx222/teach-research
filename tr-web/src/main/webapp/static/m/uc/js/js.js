define(["require","zepto","iscroll","datetime","placeholder"], function (require) {
	require("zepto");
	var $ = Zepto;
	$(function(){ 
		init();  
	}); 
    function init() {
    	var personal_info = new IScroll('.personal_info',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true,
      		click:true
      	});
    	$("#feedMsg").placeholder({
    		 word: '请输入反馈内容'
    	});
    	var strs = ",";
		$.each($(".formatName"),function(index,obj){
			if(strs.indexOf(","+obj.id+",") < 0 ){
				strs += obj.id+",";
			}
		});
		if(strs!=","){
			var bbspanhtml = "";
			$.each(strs.split(","),function(index,obj){
				if(obj!=null && obj!=""){
					bbspanhtml += "<span>"+obj+"</span>";
				}
			});
			$("#bbspan").html(bbspanhtml);
			$("#bbspan").css("display","");
		}
    	$('.info_r p').each(function (){
    		var that = $(this);
    		$(this).click(function (){
    			$(this).children('span').addClass('act').parent().siblings().children('span').removeClass('act');
    		});
    	});
    	$('.personal_center_l ul li').each(function(){
	   		 var that = this;
	   		 $(this).click(function (){ 
	   			 var index = $(this).index();
	   			 $(that).addClass('center_li_act').siblings().removeClass('center_li_act');
	   			 $('.personal_center_r .personal_center_r1').eq(index).show().siblings().hide();
	   			 var feedback = new IScroll('.feedback',{
	   	      		scrollbars:true,
	   	      		mouseWheel:true,
	   	      		fadeScrollbars:true, 
	   	      	 });
	   		 });
	 	 }); 
    	
    	//个人信息编辑和提交按钮
    	 var isEdit = false;
		 var isShowEditImg = false;
    	$('.personal_info_cont2 .edit').click(function(){
    	    	if(!isEdit){
    				$(this).css('background-image','url('+_STATIC_BASEPATH_+'/m/uc/images/sava.jpg)');
    				$('.info_r input').removeAttr('readOnly');
    				isEdit = true;
    				$('#picktime').click(function(){
    					var arr = new Array();
    					var date = new Date().getFullYear(); 
    					for (var i=0;i<65;i++){
    						var date_year = date-i;
    						arr[i] = date_year;
    					}
    					var year = arr.sort();
    					$('#picktime').mdatetimer({ 
    						mode : 1, //时间选择器模式：1：年月日，2：年月日时分（24小时），3：年月日时分（12小时），4：年月日时分秒。默认：1
    						format : 2, //时间格式化方式：1：2015年06月10日 17时30分46秒，2：2015-05-10  17:30:46。默认：2
    						years : year, //年份数组
    						nowbtn : true, //是否显示现在按钮
    						onOk : null, 	
    						onCancel:null 
    						});
    				});
    				if(!isShowEditImg){
    					$('.info_l span').show();
    					isShowEditImg = true;
    				}
    			}
    			else if(isEdit){
    				if(submitInfo()) {
    					if(isShowEditImg){
        					$('.info_l span').hide();
        					isShowEditImg = false;
        				}
        				$('.info_r input').attr('readOnly','readonly');
        				$(this).css('background-image','url('+_STATIC_BASEPATH_+'/m/uc/images/edut.jpg)');
        				isEdit = false;
    				}
    				
    			}
    	});
    	
    	//个人简介编辑和提交
    	var isEdit1 = false;
    	$('.personal_info_cont3 .edit').click(function(){
    		if(!isEdit1){
    			isEdit1 = true;
    			$(this).css('background-image','url('+_STATIC_BASEPATH_+'/m/uc/images/sava.jpg)');
    			$('.about textarea').removeAttr('readonly');
    		} else if(isEdit1){
    			var explain = $('#explains').val().trim();
    			if(explain.length>500){
    				successAlert('最多只能输入500个字符');
    				$('#explains').val('');
    				return false;
    			}
    			submitExplains();
    			isEdit1 = false;
    			$(this).css('background-image','url('+_STATIC_BASEPATH_+'/m/uc/images/edut.jpg)');
    			$('.about textarea').attr('readonly','readonly');
    		}
    	});
    	
    	
    	$('.save_edit').click(modifyPwd);
    	
    	//发送邮箱验证码
    	$('#btnSendCode').click(function(){sendMessage();});
    	//保存邮箱绑定
    	$('#saveverificationcode').click(function(){//点击按钮提交
    		//if($("#validationMailCode").validationEngine("validate"))
    		  var mail=$("#mails").val();
    		  var code=$("#verificationcode").val();
    		  if(mail==null||mail==""){
    			  successAlert("邮箱不能为空，请输入");
    		  }else if(code==null||code==""){
    			  successAlert("验证码不能为空，请输入");
    		  }else{
    			  $.ajax({
    	            	 type : "POST", // 用POST方式传输
    	            	 dataType : "json", // 数据格式:JSON
    	                 url: _WEB_CONTEXT_ +'/jy/uc/verificationMailCode.json', // 目标地址
    	                 data: {code:code, mails:mail},
    	                 success : function(data) {
    	         			if(data.code==1){
    	         				successAlert("验证通过，您可以使用该邮箱地址登录了。");
    	         			}else{
    	         				successAlert("验证码输入错误或验证码已过期");
    	         			}
    	         		}
    	            });     
    		   }
    	       
    	      });
    	
    	//提交反馈内容
    	$('#submitFeedback').click(function(){
    		var attachment = "";
    		$('.add_study_content').each(function(i){
    			//得到每个div上面绑定的编码过的filename
    			attachment += $(this).data('filepath')+',';
    		});
    		if(attachment != ""){
    			attachment = attachment.substring(0,attachment.length-1);
    		}
    		var message = $('#feedMsg').val().trim();
    		if(message == ''){
    			successAlert("请输入反馈内容");
    			return false;
    		} else if(message.length>100){
    			successAlert("输入限制：最多输入100个字符");
    			return false;
    		}
    		$.ajax({
    			type:"post",
    			dataType:"json",
    			url:_WEB_CONTEXT_+"/jy/feedback/saveFeedBack",
    			data:{message:message, attachment1:attachment},
    			success:function(data){
    				successAlert(data.msg);
    			},
    			error:function(data){
    				if(data.code==0){
    					successAlert(data.msg);
    				}
    			}
    		});
    	});
    }
    
    //教龄只能输入数字
    $('#schoolAge').keyup(function(){
    	checkNum("#schoolAge")
    });
    $('#schoolAge').bind("input",function(){
    	checkNum("#schoolAge")
    });
    
    //电话只能输入数字
    $('#cellphone').keyup(function(){
    	checkNum("#cellphone")
    });
    $('#cellphone').bind("input",function(){
    	checkNum("#cellphone")
    });
    
    //验证数字
    function checkNum(obj){
    	var value = $(obj).val();
    	$(obj).val(value.replace(/[^0-9]/g,""));
    }

    //提交修改用户信息请求
    function submitInfo(){
    	var nickname = $('#nickname').val().trim();
    	var schoolAge = $('#schoolAge').val().trim();
    	var profession = $('#profession').val().trim();
    	var honorary = $('#honorary').val().trim();
    	var sex = $('.info_r .act').attr("data");
    	var cellphone = $('#cellphone').val().trim();
    	var explains = $("#explains").val().trim();
    	var birthday = $('#picktime').val();
    	if(cellphone.length != 11){
    		successAlert("电话输入有误，请重新输入！");
    		$('#cellphone').focus();
    		return false;
    	}
    	var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
    	if(!reg.test(cellphone)){
    		successAlert("电话输入有误，请重新输入！");
    		$('#cellphone').focus();
    		return false;
    	}
    	if(!checkCellphone(cellphone)){
    		successAlert("该号码已被使用,请重新输入！");
    		$('#cellphone').focus();
    		return false;
    	}
    	$.ajax({
    		url:_WEB_CONTEXT_+"/jy/uc/saveuserinfomobile",
    		type: "POST",
    		data:{nickname:nickname,schoolAge:schoolAge,profession:profession,honorary:honorary,sex:sex,cellphone:cellphone,birthday:birthday},
    		success:function(data){
    			if(data.code == 1){
    				successAlert(data.msg);
    			} else{
    				successAlert("信息修改失败！");
    			}
    		},
    		error:function(){
    			successAlert("error occured");
    		}
    	});
    	return true;
    }
    
    //提交个人简介
    function submitExplains(){
    	var explains = $("#explains").val().trim();
    	$.ajax({
    		url:_WEB_CONTEXT_+"/jy/uc/saveuserinfomobile",
    		type: "POST",
    		data:{explains:explains},
    		success:function(data){
    			if(data.code == 1){
    				successAlert(data.msg);
    			}
    		},
    		error:function(){}
    	});
    }
    
    //通过后台检查电话唯一性
    function checkCellphone(cellphone){
    	var isCellphoneUsed = false;
    	$.ajax({
    		url:_WEB_CONTEXT_+"/jy/uc/verifyUseCell",
    		type:"POST",
    		async: false,
    		data:{"fieldId":"","fieldValue":cellphone},
    		success:function(data){
    			isCellphoneUsed = data[1];
    		},
    		error:function(){
    		}
    	});
    	return isCellphoneUsed;
    }
    
    //验证密码是否输入正确
    var isPwdRight = false;
    $('#password').blur(function(){
    	var nickname = $('.info_r2').text().trim();
    	var password = $(this).val().trim();
    	if("" == password){
    		successAlert("请输入密码!");
    		return false;
    	}
    	$.ajax({
    		url:_WEB_CONTEXT_+"/jy/uc/findps/verifyUsePassword",
    		type:"post",
    		data:{fieldId:nickname, fieldValue:password},
    		success:function(data){
    			isPwdRight = data[1];
    			if(isPwdRight){
    			} else{
    				successAlert("原密码输入错误，请重新输入!");
    			}
    		},
    		error:function(){
    			isPwdRight = false;
    			successAlert("出现异常，请重试!");
    		}
    	});
    });
    
    //提交修改密码请求
    function modifyPwd(){
    	if(!isPwdRight){
    		successAlert("原密码输入错误，请重新输入!");
    		return false;
    	}
    	var password = $('#password').val().trim();
    	var newpassword = $('#newpassword').val().trim();
    	var renewpassword = $('#renewpassword').val().trim();
    	var length = newpassword.length;
    	if(length<6 || length>16){
    		successAlert("请输入6~16个字符！");
    		return false;
    	}
    	if(newpassword != renewpassword){
    		successAlert("两次输入不一致");
    		return false;
    	}
    	if(newpassword == password){
    		successAlert("与原密码一致，请重新输入");
    		return false;
    	}
    	$.ajax({
    		url:_WEB_CONTEXT_+'/jy/uc/savemodifymobile',
        	type:'Post',
        	data:{password:password, newpassword:newpassword, renewpassword:renewpassword},
        	success:function(data){
        		if(data.code==1){
        			successAlert(data.msg + ",请重新登录！",true,logout);
        		}
        	},
        	error:function(){
        	}
    	});
    }
    //退出登录按钮点击
    $(".quit_login").click(logout);
    //退出登录
    function logout(){
    	window.location.href="jy/logout";
    }
    
    //发送邮箱验证码请求
    var InterValObj; // timer变量，控制时间
    var count = 60; // 间隔函数，1秒执行
    var curCount;// 当前剩余秒数
    var code = ""; // 验证码
    var codeLength = 6;// 验证码长度
    function sendMessage(){
    	$("#isCodeSend").html('');
    	curCount = count;
    	var mail=$("#mails").val();
    	var oldmail = $("#mails").attr("data-old");
    	if(oldmail != "" && oldmail == mail){
    		successAlert("该邮箱账号已经通过认证，无需再次认证！");
    		return false;
    	}
    	var m=mail.split("@")[1];
    	if(mail==null || mail==""){
    		successAlert("邮箱账号不能为空，请输入");
    		return false;
    	}else if((mail!=null || mail!="") && mail.indexOf("@")==-1 ||  m==""||m==null){
    		successAlert("邮箱账号不合法，请重新输入");
    		return false;
    	}
    	
    	if(!checkMail(mail)){
    		successAlert("该邮箱已被占用！");
    		return false;
    	}
    		// 向后台发送处理数据
    		$.ajax({
    			type : "POST", // 用POST方式传输
    			dataType : "json", // 数据格式:JSON
    			url : _WEB_CONTEXT_ +'/jy/uc/verificationCode', // 目标地址
    			data :"mail=" +mail,
    			success : function(data) {
    				if(data.code==1){
    					$("#btnSendCode").val("重新发送"+"("+curCount+")");
    					$("#isCodeSend").html('发送成功');
    					InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
    				}else{
    					$("#btnSendCode").val("重新发送"+"("+curCount+")");
    					$("#isCodeSend").html("发送失败,请稍后重试");
    					InterValObj = window.setInterval(SetRemainTime, 1000); 
    				}
    				
    			}
    		});
    	}
    
    //验证邮箱唯一性
    function checkMail(mail){
    	var isMailUsed = false;
    	$.ajax({
    		url:_WEB_CONTEXT_ +"/jy/uc/verifyUserMail",
    		type:"POST",
    		async: false,
    		data:{"fieldId":"","fieldValue":mail},
    		success:function(data){
    			isMailUsed = data[1];
    		},
    		error:function(){}
    	});
    	return isMailUsed;
    }
    
 // timer处理函数
    function SetRemainTime() {
    	if (curCount == 0) {
    		window.clearInterval(InterValObj);// 停止计时器
    		$("#btnSendCode").removeAttr("disabled");// 启用按钮
    		$("#btnSendCode").val("重新发送");
    		code = ""; // 清除验证码。
    	} else {
    		curCount--;
    		$("#btnSendCode").attr('disabled','true');
    		$("#btnSendCode").val("重新发送"+"("+curCount+")");
    	}
    }
    /*上传头像*/
    window.headerUpload = function(data){
		if (data.code == 0) {
			$.ajax({
				type : "post",
				dataType : "json",
				url : _WEB_CONTEXT_ + "/jy/uc/modifyPhoto",
				data : {photoPath:data.data},
				success : function(data) {
					//刷新页面
					window.location.reload();
				}
			});
			
		}
		
	}
     /*上传前回调*/ 
	 $('.submit_btn').hide();
     window.beforeUpload = function(){ 
    	 var feedImgs = $('.study_additional_content_r').children('.add_study_content');
  		 if(feedImgs.length>=4){
  			successAlert("您最多只能上传4张图片！");
  			return false;
  		 } 
  		 $('.submit_1').hide();
		 $('.submit_btn').show();
 	     return true;
	 } 
     window.feedbackUpload = function(data){
 		var content = '<div class="add_study_content">'
 			+'<div class="add_study_content_l">'
 			+'</div>'
 			+'<div class="add_study_content_c">'
 			+'<span>'+data.filename+'</span>'
 			+'<div class="complete">上传完成</div>'
 			+'</div>'
 			+'<input type="button" class="add_study_content_r" value="删除" />'
 			+'</div>';
 		var $content = $(content);	
 		$content.data("filepath",data.data);
 		$('.study_additional_content_r').append($content);                                      
 		$('.add_study_content_r').each(function (){
 			var that = this;
 			$(this).click(function (){
 				$(that).parent().remove();
 			});
     	});
 		 $('.submit_1').show();
 		 $('.submit_btn').hide(); 
 	}
    
});