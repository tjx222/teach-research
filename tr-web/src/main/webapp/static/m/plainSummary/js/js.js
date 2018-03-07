define(["require","zepto","iscroll"], function (require) {
	require("zepto"); 
	var $ = Zepto;
	$(function(){ 
		init();
	});
    function init() { 
    	//撰写计划总结点击事件
    	$('.add_cour').click(function (){
    		$('.plainSummary_wrap').show();
    		$('.mask').show();
    		var roleId=$(this).parent().attr('data-sysRoleId');
    		var keys=getKeyByRoleId(roleId);
    		var jihua=getDlogsTitle(keys[0],"计划");
			var zongjie=getDlogsTitle(keys[0],"总结");
    		if(keys.length==2){    			
    			var jihua1=getDlogsTitle(keys[1],"计划");
    			var zongjie1=getDlogsTitle(keys[1],"总结");
    			$('.plainSummary_mask_l').eq(0).show().find("span").html(jihua);
    			$('.plainSummary_mask_r').eq(0).show().find("span").html(zongjie);
    			$('.plainSummary_mask_l').eq(1).show().find("span").html(jihua1);
    			$('.plainSummary_mask_r').eq(1).show().find("span").html(zongjie1); 
    		}else{
    			if(roleId=='27'){//教师
    				$('.plainSummary_mask_l').eq(0).show().find("span").html(jihua);
        			$('.plainSummary_mask_r').eq(0).show().find("span").html(zongjie);
        			$('.plainSummary_mask_l').eq(1).hide();
        			$('.plainSummary_mask_r').eq(1).hide();
    			}else{
    				$('.plainSummary_mask_l').eq(0).hide();
        			$('.plainSummary_mask_r').eq(0).hide();
        			$('.plainSummary_mask_l').eq(1).show().find("span").html(jihua);
        			$('.plainSummary_mask_r').eq(1).show().find("span").html(zongjie);        			
    			}
    		}    		
			$('#sysRoleId').val(roleId);
			$('#userSpaceId').val($(this).parent().attr('data-userSpaceId'));
			$('#subject').val($(this).parent().attr('data-subject'));
			$('#grade').val($(this).parent().attr('data-grade'));
    	});
      
    	//选择撰写计划
    	$('.plainSummary_mask_l').click(function (e){
    		$('#ps_id').val("");
    		showUpload($(this).attr("data-category"),e,"");
    	});
    	//选择撰写总结
    	$('.plainSummary_mask_r').click(function (e){
    		$('#ps_id').val("");
    		showUpload($(this).attr("data-category"),e,"");
    	});
    	
    	//根据角色获取标识
    	function getKeyByRoleId(roleId){
    		var keys = new Array();
    		switch(roleId){
	    		case '1377'://校长
	    			keys[0]=1;
	    			keys[1]=5;
	    			break;
	    		case '1376'://主任
	    			keys[0]=1;
	    			keys[1]=5;
	    			break;
	    		case '1374'://年级
	    			keys[0]=4;
	    			break;
	    		case '1375'://学科
	    			keys[0]=3;
	    			break;
	    		case '1373'://备课
	    			keys[0]=2;
	    			break;
	    		case '27'://老师
	    			keys[0]=1;
	    			break;
	    		case '2000'://副校长
	    			keys[0]=1;
	    			keys[1]=5;
	    			break;
    			default:
    				break;	    			
    		}
    		return keys;
    	}
    	
    	//根据标识获取标头
    	function getDlogsTitle(key,type){
    		var title="计划总结";
    		switch(key){
    			case 1:
    				title = "个人"+type;
    				break;
    			case 2:
    				title = "备课"+type;
    				break;
    			case 3:
    				title = "学科"+type;
    				break;
    			case 4:
    				title = "年级"+type;
    				break;
    			case 5:
    				title = "学校"+type;
    				break;    			
				default:
					title="计划总结";
					break;
    		}  
    		return title;
    	}
    	
    	//显示上传图片窗口
    	function showUpload(category,e,fileName){
    		$('.mask').show();
    		$('.add_upload_wrap').show();
    		$('.plainSummary_wrap').hide();
    		e.stopPropagation();
    		var title=getTitle(category);
    		$(".add_upload_title h3").html("撰写"+title);
    		if($('#ps_id').val()=="" || $('#ps_id').val()==null){    			    			
				$('.form_tx').attr('data-default', 'true');
	    		initAddTitle(title);
	    		$("#fileToUpload").val("");	    		
	    		$('#category').val(category);
    		}else{
//    			$("#fileToUpload_1").attr("placeholder",fileName);    			
    		}
    		bindTitleCheck();
    	}
    	    	
    	function getTitle(category){
    		if(category==1){
    			return "个人计划";
    		}
			if(category==2){
			    return "个人总结";			
			}
			if(category==3){				
				var keys=getKeyByRoleId($("#sysRoleId").val());
				return getDlogsTitle(keys[keys.length-1],"计划");
			}
			if(category==4){
				var keys=getKeyByRoleId($("#sysRoleId").val());
				return getDlogsTitle(keys[keys.length-1],"总结");
			}
    	}
    	
    	//初始列表数据标题
    	setImgTitle(0);
    	
    	function setImgTitle(i){
    		$(".content_bottom_width").eq(i).find(".courseware_img_1").each(function(i){
        		$('#sysRoleId').val($(this).parent().parent().attr('data-sysRoleId'));
        		$(this).html(getTitle($(this).parent().attr("data-category")));
        	});
    	}
    	
    	//初始添加标题
    	function initAddTitle(title){
    		if ($('.form_tx').attr('data-default') == 'true') {
				var year = parseInt($('#schoolYear').val());
				var endYear = year + 1;
				var term = $('#schoolTerm').val() == 0 ? '上学期'
						: '下学期';
				var html = year + '-' + endYear + '学年' + term
						+ $('#grade').val() + $('#subject').val() + title;
				$('.form_tx').val(html);
			}
    	}
    	
    	//标题验证
    	function bindTitleCheck(){
    		$(".form_tx").blur(function(){
    			if($(this).val()==""){
    				successAlert("标题不能为空！");
    				return false;
    			}
    		});
    	}
    	
    	//关闭撰写面板
    	$('.close').click(function (){
    		var title=$(this).attr("data-title");
    		if(title==null || title==""){
    			$(".opinions_comment_wrap").hide();
    		}else{
    			$('.'+title+'_upload_wrap').hide();
    		}
    		$('.mask').hide();
    	});
    	
    	//点击取消按钮
    	$(".btn_cencel").click(function(){
    		$('.close').trigger("click");
    	});
    	
    	//点击遮罩层
    	 $('.plainSummary_wrap').click(function (){
     		$('.plainSummary_wrap').hide();
      		$('.mask').hide();
     	});
    	 
    	//小功能图标点击事件
     	$(".courseware_img_2").click(function () {
     		$('.cw_option_mask').hide();
     		$('.cw_option').hide();
     		var plan = $(this).parent();
     		plan.find('.cw_option_mask').show();
     		plan.find('.cw_option').show();
     		plan.siblings(".border").removeClass('border').addClass('border');
     		plan.find(".cw_option_edit").css({'-webkit-animation':'edit .5s',"-webkit-animation-fill-mode":"forwards"});
     		plan.find(".cw_option_del").css({'-webkit-animation':'del .5s',"-webkit-animation-fill-mode":"forwards"});
     		plan.find(".cw_option_submit").css({'-webkit-animation': 'submit .5s',"-webkit-animation-fill-mode":"forwards"});
     		plan.find(".cw_option_publish").css({'-webkit-animation': 'publish .5s',"-webkit-animation-fill-mode":"forwards"});
     		plan.find(".cw_option_share").css({'-webkit-animation': 'share .5s',"-webkit-animation-fill-mode":"forwards"});
     		plan.find(".cw_option_down").css({'-webkit-animation': 'down .5s',"-webkit-animation-fill-mode":"forwards"});
     	});
     	
     	//删除事件
     	$('.cw_option_close').click(function (){
     		$('.cw_option_mask').hide();
     		$('.cw_option').hide();
     	});
     	/*$(".plainSummary_content_c ul").each(function (){
     		$(this).css('width',$(this).width()*2);
     		$(this).parent().css('width',$(this).width()/2);
     	}); */
     	var plainSummary_content_c1 = new IScroll('.plainSummary_content_c1',{
      		scrollbars:true, 
      		fadeScrollbars:true,
      		click:true,
      		scrollX: true, 
      		scrollY: false, 
      		mouseWheel: true
      	});
        /* 点击切换 -start*/ 
    	var t_List=$(".plain_center_cont_wrap .plain_center_cont"),
			b_List=$(".plainSummary_content_c li");
		var len=t_List.length;
		var index=0;
		var tabIndex=$("#scrollLeft").val();
		if(tabIndex!=null && tabIndex!=""){
			index=parseInt(tabIndex);
		}
		move(index,"on");
		//左移
		$(".plainSummary_content_l").click(function(){
			if(index<=0){
				return ;
			}else{
				index--;
				move(index,"on");
			}
			var plain_center_cont1 = new IScroll('.plain_center_cont1',{
	      		scrollbars:true,
	      		mouseWheel:true,
	      		fadeScrollbars:true,
	      		click:true,
	      	});
		});
		//右移
		$(".plainSummary_content_r").click(function(){
			if(index>=len-1){
				return ;
			}else{
				index++;
				move(index,"on");
			}
			var plain_center_cont1 = new IScroll('.plain_center_cont1',{
	      		scrollbars:true,
	      		mouseWheel:true,
	      		fadeScrollbars:true,
	      		click:true,
	      	});
		});
		//标签点击切换移动
		b_List.click(function(){
			index=$(this).index();
			move(index,"on");
			var plain_center_cont1 = new IScroll('.plain_center_cont1',{
	      		scrollbars:true,
	      		mouseWheel:true,
	      		fadeScrollbars:true,
	      		click:true,
	      	});
		});
		//移动方法
		function move(i,cla){//样式切换
			t_List.eq(i).addClass(cla).siblings().removeClass(cla);
			t_List.eq(i).addClass('plain_center_cont1').siblings().removeClass('plain_center_cont1');
			b_List.eq(i).addClass(cla).siblings().removeClass(cla);
			setImgTitle(i);
			$("#scrollLeft").val(i);
			var plain_center_cont1 = new IScroll('.plain_center_cont1',{
	      		scrollbars:true,
	      		mouseWheel:true,
	      		fadeScrollbars:true,
	      		click:true,
	      	});
		}
		 /* 点击切换 -end*/ 
			/*$(".plainSummary_content_c").swipeLeft(function (){
				alert
			});	*/
		//编辑
		$('.cw_option_edit').click(function(e) {
			var editObj=$(this).closest(".courseware_ppt");
			var category = editObj.attr('data-category');
			$('#savePs').val("保存");
			$('#category').html(category);
			$('#ps_id').val(editObj.attr('data-id'));
			$('.form_tx').val(editObj.attr('data-title'));
			$('#userSpaceId').val(editObj.parent().attr('data-userSpaceId'));
			$('#sysRoleId').val(editObj.parent().attr('data-sysRoleId'));
			$('.form_tx').attr('data-default', 'false');
			$('#contentFileName').val(editObj.attr('data-fileName'));
			$('#contentFileKey').val(editObj.attr('data-fileId'));
			showUpload(category,e,editObj.attr('data-fileName'));
		});
				
		//功能操作弹出框
		function showDialogWin(fun,title,content){
			$("."+fun+"_upload_wrap h3").html(title);
	    	$("."+fun+"_upload_wrap ."+fun+"_width span").html(content);	    	
			$("."+fun+"_upload_wrap").show();
	      	$('.mask').show();	      	
		}
		
		// 删除
		$('.cw_option_del').click(function() {
			loaddel($(this));
		});
		// 删除1
		$(".cw_option_del1").click(function(){
			loaddel($(this));
		});
		// 删除2
		$(".cw_option_del2").click(function(){
			loaddel($(this));
		});	
		//加载删除
		function loaddel(valDel){
			var dlogTitle=getTitle(valDel.closest(".courseware_ppt").attr("data-category"));
			showDialogWin("del","删除"+dlogTitle,"您确定要删除该"+dlogTitle+"吗？删除后，将不可恢复！");
			var id=valDel.closest(".courseware_ppt").attr("data-id");
	      	$('.del_upload_wrap').find(".btn_confirm").on("click",function(){				
				$.getJSON(_WEB_CONTEXT_+'/jy/planSummary/'+ id+ '/delete.json', function(data) {
					if (data.result.code == 1) {
						freshLocation();//刷新页面
					} else {
						successAlert('删除失败！');
					}
				});
	      	});
		}
		
		// 提交
		$('.cw_option_submit').click(function() {
			loadSub($(this));
		});
		// 提交2
		$('.cw_option_submit2').click(function() {
			loadSub($(this));
		});		
		//加载提交
		function loadSub(valSub){
			var dlogTitle=getTitle(valSub.closest(".courseware_ppt").attr("data-category"));
			showDialogWin("submit","提交"+dlogTitle,"您确定要提交该"+dlogTitle+"吗？提交后，学校管理者将看到这些内容！");
			var id=valSub.closest(".courseware_ppt").attr("data-id");
	      	$('.submit_upload_wrap').find(".btn_confirm").on("click",function(){				
				$.getJSON(_WEB_CONTEXT_+'/jy/planSummary/' + id+ '/submit.json', function(data) {
					if (data.result.code == 1) {
						$('.submit_upload_wrap').hide();
						successAlert('恭喜您提交成功，已提交到学校管理室！',false,function(){
							freshLocation();//刷新页面
						});		
					} else {
						successAlert('提交失败！');
					}
				});
	      	});
		}
				
		// 取消提交
		$('.cw_option_qx_submit').click(function() {
			loadQxsub($(this));
		});
		// 取消提交2
		$('.cw_option_qx_submit2').click(function() {
			loadQxsub($(this));
		});
		//加载取消提交
		function loadQxsub(valQxsub){
			var dlogTitle=getTitle(valQxsub.closest(".courseware_ppt").attr("data-category"));
			showDialogWin("submit","取消提交"+dlogTitle,"您确定要取消提交该"+dlogTitle+"吗？取消提交后，学校管理者将看不到这些内容！");
			var id=valQxsub.closest(".courseware_ppt").attr("data-id");
	      	$('.submit_upload_wrap').find(".btn_confirm").on("click",function(){							
				$.getJSON(_WEB_CONTEXT_+'/jy/planSummary/'+ id+ '/cancelSubmit.json',function(data) {
					if (data.result.code == 1) {
						freshLocation();//刷新页面
					} else {
						successAlert('取消提交失败！');
					}
				});
	      	});
		}
		// 发布
		$('.cw_option_publish').click(function() {
			loadPub($(this));
		});
		//发布2
		$('.cw_option_publish2').click(function() {
			loadPub($(this));
		});
		//加载发布
		function loadPub(valPub){
			var dlogTitle=getTitle(valPub.closest(".courseware_ppt").attr("data-category"));					
			showDialogWin("publish","发布"+dlogTitle,"您确定要发布该"+dlogTitle+"吗？发布后，其他人将看到这些内容！");
			var id=valPub.closest(".courseware_ppt").attr("data-id");
			$('.publish_upload_wrap').find(".btn_confirm").on("click",function(){				
				$.post(_WEB_CONTEXT_+'/jy/planSummary/' + id+ '/punish',{"punishRange":0,"schoolCircleId":0}, function(data) {
					if (data.result.code == 1) {
						freshLocation();//刷新页面
					} else {
						successAlert('发布失败！');
					}
				},"json");
	      	});
		}
		// 取消发布
		$('.cw_option_qx_publish').click(function() {
			loadQxpub($(this));
		});
		// 取消发布2
		$('.cw_option_qx_publish2').click(function() {
			loadQxpub($(this));
		});
		//加载取消发布
		function loadQxpub(valQxpub){
			var dlogTitle=getTitle(valQxpub.closest(".courseware_ppt").attr("data-category"));				
			showDialogWin("publish","取消发布"+dlogTitle,"您确定要取消发布该"+dlogTitle+"吗？取消发布后，其他人将看不到这些内容！");
			var id=valQxpub.closest(".courseware_ppt").attr("data-id");
	      	$('.publish_upload_wrap').find(".btn_confirm").on("click",function(){							
				$.getJSON(_WEB_CONTEXT_+'/jy/planSummary/'+ id+ '/cancelPunsih.json',function(data) {
					if (data.result.code == 1) {
						freshLocation();//刷新页面
					} else {
						successAlert('取消发布失败！');
					}
				});
	      	});
		}
		// 分享
		$('.cw_option_share').click(function() {
			loadShare($(this));
		});
		// 分享1
		$(".cw_option_share1").click(function(){
			loadShare($(this));
		});
		// 分享2
		$(".cw_option_share2").click(function(){
			loadShare($(this));
		});
		//加载分享
		function loadShare(valShare){
			var dlogTitle=getTitle(valShare.closest(".courseware_ppt").attr("data-category"));
			showDialogWin("share","分享"+dlogTitle,"您确定要分享该"+dlogTitle+"吗？分享后，您的小伙伴就可以看到喽！");	
			var id=valShare.closest(".courseware_ppt").attr("data-id");
			$('.share_upload_wrap').find(".btn_confirm").on("click",function(){				
				$.getJSON(_WEB_CONTEXT_+'/jy/planSummary/'+ id+ '/share.json', function(data) {
					if (data.result.code == 1) {
						freshLocation();//刷新页面
					} else {
						successAlert('分享失败！');
					}
				});
			});
		}
		
		// 取消分享
		$('.cw_option_qx_share').click(function() {
			loadQxShare($(this));
		});		
		// 取消分享1
		$(".cw_option_qx_share1").click(function(){
			loadQxShare($(this));
		});
		// 取消分享2
		$(".cw_option_qx_share2").click(function(){
			loadQxShare($(this));
		});
		//加载取消分享
		function loadQxShare(valShare){
			var dlogTitle=getTitle(valShare.closest(".courseware_ppt").attr("data-category"));
			showDialogWin("share","取消分享"+dlogTitle,"您确定要取消分享该"+dlogTitle+"吗？取消分享后，您的小伙伴就不可以看到喽！");
			var id=valShare.closest(".courseware_ppt").attr("data-id");
	      	$('.share_upload_wrap').find(".btn_confirm").on("click",function(){				
				$.getJSON(_WEB_CONTEXT_+'/jy/planSummary/' + id+ '/cancelShare.json', function(data) {
					if (data.result.code == 1) {						
						freshLocation();//刷新页面							
					} else {
						successAlert('取消分享失败！');
					}
				});
	      	});
		}
		
		//查阅列表
		$('.courseware_img_3').click(function(){
			var ppt=$(this).closest(".courseware_ppt");
			var resId=ppt.attr("data-id");				
			var resType = $(this).attr('data-resType');	
			var term=ppt.attr("data-term");
			var gradeId=ppt.attr("data-gradeId");
			var subjectId=ppt.attr("data-subjectId");
			var title=ppt.attr("data-title");
			var authorId=ppt.attr("data-userId");
			$('.mask').show();
			$("#checkComment").css("display","block");
			$.ajax(_WEB_CONTEXT_+'/jy/planSummary/'+resId+'/checkState?checkState=2',{
				type:'put',
				'dataType':'json'
			});
			$("#iframe_scan").attr("src",_WEB_CONTEXT_+"/jy/check/infoIndex?flags=false&term="+term+"&gradeId="+gradeId+"&subjectId="+subjectId+"&title="+title+"&resType="+resType+"&authorId="+authorId+"&resId="+resId);				
		});
		
		//评论列表
		$('.courseware_img_4').click(function(){
			var ppt=$(this).closest(".courseware_ppt");
			var resId=ppt.attr("data-id");				
			var resType = $(this).attr('data-resType');	
			var term=ppt.attr("data-term");
			var gradeId=ppt.attr("data-gradeId");
			var subjectId=ppt.attr("data-subjectId");
			var title=ppt.attr("data-title");
			var authorId=ppt.attr("data-userId");
			$('.mask').show();
			$("#comment_div").css("display","block");
			$.ajax(_WEB_CONTEXT_+'/jy/planSummary/'+resId+'/reviewState?reviewState=2',{
				type:'put',
				'dataType':'json'
			});
	    	$("#iframe_comment").attr("src",_WEB_CONTEXT_+"/jy/comment/list?flags=false&resType="+resType+"&authorId="+authorId+"&resId="+resId);
	    });
				
		// 单击图标
		$('.onlineScan').click(function() {
			scanResFile_m($(this).closest(".courseware_ppt").attr("data-fileId"));
		});
    }
	
    //上传前回调
    window.startUpload = function(){
    	if($(".form_tx").val()==""){
    		successAlert("标题不能为空！");
			return false;
		}
    	return true;
    }
    
	 //上传后回调
    window.afterUpload = function(file){      	
    	if(file && file.data){
    		$('#contentFileKey').val(file.data);
    	}
    	console.log(file);
    	var contentFileName = file.filename;
    	var strArr = contentFileName.split('.');
    	var contentFileType = strArr[strArr.length - 1];
    	$('#contentFileName').val(contentFileName);
    	$('#contentFileType').val(contentFileType);    	
		if(file.code==0){
			savePs();
		}
    }
    //保存
    window.savePs = function(){   
    	var contentFileKey = $("#contentFileKey").val();
    	if(contentFileKey==null || contentFileKey ==""){
    		successAlert("请选择要上传的文件！");
    		return false;
    	}else{
    		var id=$("#ps_id").val();
    		var scrollLeft=$("#scrollLeft").val();
    		if(id == null || id == ''){
    			$.ajax({
    				type:"post",
    				dataType:"html",
    				url:_WEB_CONTEXT_+'/jy/planSummary/save?scrollLeft='+scrollLeft,
    				data:$("#ps_form").serializeArray(),
    				success:function(data){
    					successAlert("添加成功",false,function(){    						
    						freshLocation();//刷新页面
    					});
    				}
    			});
    		}else{
    			$.ajax({
    				type:"post",
    				dataType:"html",
    				url:_WEB_CONTEXT_+'/jy/planSummary/edit?scrollLeft='+scrollLeft,
    				data:$("#ps_form").serializeArray()+'&id='+id,
    				success:function(data){
    					successAlert("保存成功",false,function(){    						
    						freshLocation();//刷新页面
    					});
    				}
    			});    			
    		}    	    		
    	}
    }
    
    window.freshLocation = function(){
    	location.href = _WEB_CONTEXT_+'/jy/planSummary/index?scrollLeft='+$("#scrollLeft").val();
    }
});