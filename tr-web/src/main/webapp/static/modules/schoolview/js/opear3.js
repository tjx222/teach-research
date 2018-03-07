/**
 * 含有dataurl属性的domclick事件处理
 * @param dom
 * @param target {"_self","_blank",null}
 */
function opearDom(dom,target,login){
	opearPath($(dom).attr("data-url"),target,login)
}
/**
 * 点击学校名重新载入资源页
 */
function clickSchool() {
	window.location.href = _WEB_CONTEXT_
			+ "/jy/schoolview/index?orgID=" + orgID;
}

/**
 * 马上登陆
 */
function login(){
	$('.login_wrap').show();
	$('.bg_box1').show();
	$('#l_username').focus();
	$('body').css("overflow", "hidden");
	$('.close1').click(function() {
		$('.login_wrap').hide();
		$('.bg_box1').hide();
		$('body').css("overflow-y", "scroll");
	});
	$('.login_wrap').bind('keypress',function(event){
        if(event.keyCode == "13")    
        {
        	openLogin(); 
        }
    });
}

/**
 * 退出登陆
 */
function logout() {
	$.post(_WEB_CONTEXT_ + "/jy/logout", function(data) {
		window.location.href = _WEB_CONTEXT_
				+ "/jy/schoolview/index?orgID=" + orgID;
	});

}

/**
 * 切换学段
 * @param id
 */
function switchxueduan(xdid) {
	window.location.href = _WEB_CONTEXT_
			+ "/jy/schoolview/index?orgID=" + orgID + "&xdid=" + xdid;
}

/**
 * 弹出登陆
 * @param id
 */
function openLogin() {
	$("#errmsg").html("");
	var username = $.trim($("input[name='username']").val());
	if (username != "" && username.length >= 4) {
		var password = $.trim($("input[name='password']").val());
		if (password != "" && password.length >= 6) {
			$.post(_WEB_CONTEXT_ + "/jy/uc/login", {
				"username" : username,
				"password" : password
			}, function(data) {
				if (data == "1") {
					$("#errmsg").html("");
					$('.login_wrap').hide();
					$('.box1').hide();
					$('body').css("overflow-y", "scroll");
					window.location.reload();
				} else if (data == "0") {
					$("#errmsg").html("用户不存在/密码错误");
				}
			});
		} else {
			$("#errmsg").html("请您输入至少6位有效字符的密码");
			$("input[name='password']").focus();
		}
	} else {
		$("#errmsg").html("请您输入至少4位有效字符的用户名");
		$("input[name='username']").focus();
	}
}
//绑定页面中按钮的click事件

function bindClickEvent(){
	$(".user_login").bind("click", function() {
		var pathUrl = $(this).attr("data-url");
		window.open(pathUrl);
	});
}

/**
 * 点击查看总体备课资源,教案、课件、反思
 * @param orgID
 * @param xdid
 */
function clickschoolres(orgID,xdid,restype){
	var pathUrl = "";
	if(restype=="all"){
		pathUrl = "jy/schoolview/res/lessonres/getPreparationResDetailed?subject="+subject;
	}else{//教案、课件、反思
		pathUrl = "jy/schoolview/res/lessonres/getSpecificResjiaoankejianfansi?restype="+restype+"&subject="+subject;
	}
	opearPath(pathUrl);
}

/**
 * 点击查看集体备课、区域教研、校际教研
 * @param orgID
 * @param xdid
 * @param restype
 */
function clickActive(orgID,xdid,restype){
	var pathUrl = "jy/schoolview/res/teachactive/getSpecificAvtive?orgID="+orgID+"&xdid="+xdid+"&restype="+restype+"&subject="+subject;
	opearPath(pathUrl);
}

/**
 * 集体备课、区域教研、校际教研的参与或查看入口
 */
function canyu_chakan(activityId,typeId,isOver,orgID,xdid,restype){
	$.ajax({
		type:"post",
		dataType:"json",
		url:_WEB_CONTEXT_+"/jy/uc/isSessionOk.json",
		success:function(data){
			if(data.invalidated){//失效
				login();
			}else{//没有失效
				if(restype==1){//集体备课
					$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/schoolview/res/teachactive/havePowerOfActivity.json",
						data:{"id":activityId},
						success:function(data){
							if(data.havePower){//有参与的权限
								if(typeId=="1"){//同备教案
									if(isOver=="true"){//已结束，则查看
										window.open(_WEB_CONTEXT_+"/jy/activity/viewTbjaActivity?id="+activityId, "_blank");
									}else{//参与
										window.open(_WEB_CONTEXT_+"/jy/activity/joinTbjaActivity?id="+activityId, "_blank");
									}
								}else if(typeId=="2" || typeId=="3"){//主题研讨
									if(isOver=="true"){//已结束，则查看
										window.open(_WEB_CONTEXT_+"/jy/activity/viewZtytActivity?id="+activityId, "_blank");
									}else{//参与
										window.open(_WEB_CONTEXT_+"/jy/activity/joinZtytActivity?id="+activityId, "_blank");
									}
								}
							}else{//查看校际教研资源
								window.open(_WEB_CONTEXT_+"/jy/activity/onlyViewActivity?id="+activityId, "_blank");
							}
						}
					});
				}else if(restype==2){//校际教研
					var flag = "join";
					if(isOver){
						 flag = "view";
					}
					$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/schoolview/res/teachactive/havePowerOfSchoolActivity.json",
						data:{"id":activityId,"flag":flag},
						success:function(data){
							if(data.havePower){//有参与的权限
								if(typeId=="1"){//同备教案
									if(isOver=="true"){//已结束，则查看
										window.open(_WEB_CONTEXT_+"/jy/schoolactivity/viewTbjaSchoolActivity?id="+activityId, "_blank");
									}else{//参与
										window.open(_WEB_CONTEXT_+"/jy/schoolactivity/joinTbjaSchoolActivity?id="+activityId, "_blank");
									}
								}else if(typeId=="2"){//主题研讨
									if(isOver=="true"){//已结束，则查看
										window.open(_WEB_CONTEXT_+"/jy/schoolactivity/viewZtytSchoolActivity?id="+activityId, "_blank");
									}else{//参与
										window.open(_WEB_CONTEXT_+"/jy/schoolactivity/joinZtytSchoolActivity?id="+activityId, "_blank");
									}
								}
							}else{//查看校际教研资源
								window.open(_WEB_CONTEXT_+"/jy/schoolactivity/onlyViewSchoolActivity?id="+activityId, "_blank");
							}
						}
					});
				}else{
					alert("无效参数！"+"restype:"+restype);
				}
				
			}
		}
	});
	
}

/**
 * 点击查看专业成长
 * @param orgID
 * @param xdid
 * @param restype
 * 
 */
function clickProfession(orgID,xdid,restype){
	var pathUrl = "jy/schoolview/res/progrowth/getSpecificProfession?orgID="+orgID+"&xdid="+xdid+"&restype="+restype+"&subject="+subject;
	opearPath(pathUrl);
}

/**
 * 查看专业成长详细
 */
function viewprofress(id,orgID,xdid,restype){
	var pathUrl = "";
	if(restype==1){
		pathUrl = "jy/schoolview/res/progrowth/viewFile?id="+id+"&orgID="+orgID+"&xdid="+xdid;
	}else if(restype==2){
		pathUrl = "jy/schoolview/res/progrowth/thesisview?id="+id+"&orgID="+orgID+"&xdid="+xdid;
	}else if(restype==3){
		pathUrl = "jy/schoolview/res/progrowth/seetopic?id="+id+"&orgID="+orgID+"&xdid="+xdid;
	}
	opearPath(pathUrl,'_blank');
}

/**
 * 点击查看更多档案袋
 * @param orgID
 * @param xdid
 * @param restype
 */
function clickBags(orgID,xdid){
	var pathUrl = "jy/index/getSpecificRecordBag?orgID="+orgID+"&xdid="+xdid;
	opearPath(pathUrl);
}

/**
 * 删除（重构删除）
 * 通过用户ID查找该用户的档案袋
 * @param userID
 */
function findBagsByUserID(userID,orgID,xdid){
	var pathUrl = "jy/index/findList?orgID="+orgID+"&xdid="+xdid+"&id="+userID;
	opearPath(pathUrl);
}

/**
 * 备课资源不同科目进行查询
 * @param obj
 */
function changeSubject(obj,orgID,xdid,restype){
	subject=$(obj).val();
	clickschoolres(orgID, xdid, restype);
}

/**
 * 教研活动科目查询
 * @param obj
 * @param orgID
 * @param xdid
 * @param restype
 */
function activeChangeSubject(obj,orgID,xdid,restype){
	subject=$(obj).val();
	clickActive(orgID, xdid, restype);
}

/**
 * 专业成长科目查询
 * @param obj
 * @param orgID
 * @param xdid
 * @param restype
 */
function professionChangeSubject(obj,orgID,xdid,restype){
	subject=$(obj).val();
	clickProfession(orgID, xdid, restype);
}
/**
 *  点击链接处理
 * @param pathUrl 
 * @param target 打开窗口目标区
 * @param login 是否登录 
 */
function opearPath(pathUrl,target,login){
	//添加公用参数
	if(pathUrl.indexOf("?") != -1){
		pathUrl+="&"+postDataStr;
	}else{
		pathUrl+="?"+postDataStr;
	}
	if(login==false){
		if(target){
			window.open( _WEB_CONTEXT_+"/"+pathUrl,target);
		}else{
			window.location.href = _WEB_CONTEXT_+"/"+pathUrl;
		}
	}else{
		
		$.ajax({
			type:"post",
			dataType:"json",
			url:_WEB_CONTEXT_+"/jy/uc/isSessionOk.json",
			success:function(data){
				if(data.invalidated){//失效
					top.login();
				}else{//没有失效
					if(target){
						window.open( _WEB_CONTEXT_+"/"+pathUrl,target);
					}else{
						window.location.href = _WEB_CONTEXT_+"/"+pathUrl;
					}
				}
			}
		});
	}
	
}
