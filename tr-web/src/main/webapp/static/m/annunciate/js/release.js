define(["require","zepto","iscroll","editor"], function (require) {
	require("zepto"); 
	require("editor");
	var $ = Zepto;
	$(function(){ 
		init();
		//网络编辑器
		webEditorOptions = {
			width:"100%",
			height:'475px',
			style:"2",
		};
		editor = $("#content").editor(webEditorOptions);
	});
    function init() { 
    	var editor1 = new IScroll('.editor',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true, 
      		click:true
      
      	});
    	var tz_content_bottom1 = new IScroll('.content_bottom1',{
      		scrollbars:true,
      		mouseWheel:true,
      		fadeScrollbars:true, 
      		/*click:true*/
      
      	});
    	$('.ht_select').click(function (){
    		$('.mask').show(); 
    		$('.ht_select_wrap').show(); 
    	});
    	$('.ht_btn').click(function(){
    		$('.ht_btn').hide();
    		$('.ht_from').hide();
    		$('.addRedHead').show();
    	});
    	$('.add_ht').click(function(event){
    		$('.ht_select_wrap').hide(); 
    		$('.ht_select').hide();
    		$('.mask').hide(); 
    		$('.ht_from').hide();
    		$('.addRedHead').show();
    		event.stopPropagation();
    	});
    	$('.ht_select_wrap1').click(function(event){
    		event.stopPropagation();
    	}); 
    	$('.ht_select_wrap').click(function (){
    		$('.ht_select_wrap').hide();
    		$('.mask').hide();
    		var type=$('.redtitle').attr("data-type");
    		location.href=_WEB_CONTEXT_ + "/jy/annunciate/release?type="+type;
    	});
    	$('.preview_dlog').click(function (){
    		$('.preview_dlog').hide();
    		$('.mask').hide();
    		$('.annun_fj').remove();
    	});
    	$(".ht_content_cont input[type=text]").focus(function () { 
    		$(this).addClass("myFocus"); 
    	}).blur(function () { 
    		$(this).removeClass("myFocus"); 
    	}); 
    	$('.ht_select_wrap #scroller p a').click(function(){
    		$('.mask').hide(); 
    		$('.ht_select_wrap').hide(); 
    		$('#ht_title').val($(this).text());
    		$('#redTitleId').val($(this).parent().attr("data-id")); 
    	});
    	//加载发布页面
    	var htitle=$('#ht_title').val();
    	if(htitle==null||htitle==""){
    		$('#ht_title').val($('.ht_select_wrap #scroller p').text());
    	}
    	$('.ht_from').val("XXX(20XX)XX号");
    	//successAlert($('.ht_select_wrap #scroller p').attr("data_id"));
    	//通知公告研讨附件上传回调
    	 $('.submit_btn').hide();
    	window.backUpload=function(obj){
    		var html =   '<div class="add_study_content" resId="'+obj.data+'" >'+
							'<div class="add_study_content_l"></div>'+
							'<div class="add_study_content_c">'+
							'<span>'+obj.filename+'</span>'+
							'<div class="complete">上传完成</div></div>'+
							'<input type="button" class="add_study_content_r" value="删除"></div>';
			var dom = $(html);
			$(".study_additional_content_r").append(dom);
			dom.find(".add_study_content_r").click(function(){
			 $(this).parent().remove();
			});
			//进度条样式
			$('#progressDiv').css("margin-top","-100px");
			 $('.release').show();
    		 $('.preview').show();
    		 $('.save_drafts').show(); 
    		 $('.submit_btn').hide();
    	}
    	//删除附件
    	$('.add_study_content_r').click(function(){
    		$(this).parent().remove();
    	})
    	//上传前验证
    	window.beforeUpload=function(){ 
    		 $('.release').hide();
    		 $('.preview').hide();
    		 $('.save_drafts').hide(); 
    		 $('.submit_btn').show();
    		var loadSum=$(".study_additional_content_r").find(".add_study_content").length;
    		if(loadSum==6){
    			alert("最多允许上传6个附件");
    			return false;
    		}
   			 return true;
    	 }
    	//更新资源resids
    	window.updateResIds=function(){
    		var attachs="";
    		$('.study_additional_content_r').find('.add_study_content').each(function(){
    			if(attachs==""){
    				attachs=$(this).attr("resId");
    			}else{
    				attachs=attachs+"#"+$(this).attr("resId");
    			}
    			$('#tzggRes').val(attachs);
    		})
    	}
    	//发布通知公告
    	$('.release').click(function(){
    		saveAnnunciate(1);
    	})
    	//存草稿箱
    	$('.save_drafts').click(function(){
    		saveAnnunciate(0);
    	})
    	//保存通知公告
    	window.saveAnnunciate=function(status){
    		var html = editor[0].html();//文本框的html代码
    		var text = editor[0].text();//文本框的文字
    		$('#content').text(html);//设置上html代码
    		var title=$('input[name=title]').val();
    		var redTitleId=$('#ht_title').val();
    		var type=$('input[name=type]').val();
    		if($('#redTitleId').val()==""||$('#redTitleId').val()==null){
    			$('#redTitleId').val($('.ht_select_wrap #scroller p').attr("data-id"));
    		}
    		var words="";
    		if(type==1){
    			if(redTitleId==null||redTitleId==""){
        			successAlert("请添加红头");
        			return false;
        		}
        		if(redTitleId.length>80){
        			successAlert("红头标题不能超过80字，请重新输入");
        			return false;
        		}
    		}
    		if(title.length==0){
    			successAlert("请输入标题");
    			return false;
    		}
    		if(title.length>200){
    			successAlert("标题不能超过200字，请重新输入");
    			return false;
    		}
    		if(text.length==0){
    			successAlert("请输入内容");
    			return false;
    		}
    		if(text.length>5000){
    			successAlert("内容不能超过5000字，请重新输入");
    			return false;
    		}
    		updateResIds();
    		if(status==1){
    			words="您确定要发布吗？";
    		}else{
    			words="您确定要存入草稿箱吗？";
    		}
			if(confirm(words)){
				$.ajax({
					type: "post",
					dataType: "json",
					url: _WEB_CONTEXT_+"/jy/annunciate/annunciates?status="+status,
					data: $("#annunciate_form").serialize(),
					success:function(data){
						if(data.result.code==1){
							if(status==1){
								successAlert("通知公告发布成功",false,function(){
									//刷新页面
									window.location.href = _WEB_CONTEXT_+"/jy/annunciate/punishs";
								});
							}else{
								//刷新页面
								window.location.href = _WEB_CONTEXT_+"/jy/annunciate/punishs";
							}
						}else{
							successAlert("通知公告发布失败");
						}
					}
				});
			}
    	}
    	//保存红头
    	$('.saveRed').click(function(){
    		var title=$('.redtitle').val();
    		if(title == "" ||title == null){
    			successAlert("请输入红头内容");
    			return false;
    		}
    		if(title.length>80){
    			successAlert("红头标题不能超过80字，请重新输入");
    			return false;
    		}
    		var type=$('.redtitle').attr("data-type");
    		$.ajax({
    			type: "post",
    			dataType: "json",
    			url: _WEB_CONTEXT_ + "/jy/annunciate/addredTitles",
    			data: "title="+title,
    			success:function(data){
    				location.href=_WEB_CONTEXT_ + "/jy/annunciate/release?type="+type;
    			}
    		});
    	})
    	//取消红头
    	$('.cencel').click(function (){
    		$(this).parent().hide(); 
    		var data = $(this).attr('data-list');
    		if(data.length==2){  
    			$('.ht_btn').show();
    		}else{ 
    			$('.ht_select').show();
        		$('.ht_from').css('display','block');
    		}
    	});
    	//删除红头
    	$('.ht_select_wrap #scroller p span').click(function(event){
    		var id=$(this).parent().attr("data-id");
    		$(this).parent().remove();
    		$.ajax({
    			type: "post",
    			dataType: "json",
    			url: _WEB_CONTEXT_ + "/jy/annunciate/deleteredTitles",
    			data: "id="+id,
    			success:function(data){
    				//location.href=_WEB_CONTEXT_ + "/jy/annunciate/release?type="+type;
    			}
    		});
    		event.stopPropagation();
    	})
    	//预览通知公告
    	$('.preview').click(function (){
    		var html = editor[0].html();//文本框的html代码
    		var text = editor[0].text();//文本框的文字
    		$('#content').text(html);//设置上html代码
    		var redTitleId=$('#ht_title').val();
    		var fromWhere=$('input[name=fromWhere]').val();
    		var title=$('input[name=title]').val();
    		var type=$('input[name=type]').val();
    		//验证
    		if(type==1){
    			if(redTitleId==""||redTitleId==null){
        			successAlert("请添加红头");
        			return false;
        		}
        		if(redTitleId.length>80){
        			successAlert("红头标题不能超过80字，请重新输入");
        			return false;
        		}
        		if(fromWhere.length==0){
        			successAlert("请输入红头内容");
        			return false;
        		}
        		if(fromWhere.length==30){
        			successAlert("红头内容不能超过30字");
        			return false;
        		}
    		}
    		$('.annunciate_content_width1 h3').text($('#ht_title').val());
        	$('.annunciate_content_width1 h4').text(fromWhere);
        	if(title.length==0){
        		successAlert("请输入标题");
    			return false;
        	}
        	if(title.length>200){
        		successAlert("标题不能超过200字，请重新输入");
    			return false;
        	}
        	$('.annun_cont h5').text(title);
        	if(text.length==0){
    			successAlert("请输入内容");
    			return false;
    		}
    		if(text.length>5000){
    			successAlert("内容不能超过5000字，请重新输入");
    			return false;
    		}
    		$('.mask').show(); 
    		$('.preview_dlog').show();
        	$('.annun_cont_c p').html($('#content').val());
        	//创建日期
        	var date=new Date();
        	var year=date.getYear()+1900;
        	var month=date.getMonth()+1;
        	var dat=date.getDate();
        	$('.annun_cont li').eq(0).text("发布时间："+year+"-"+month+"-"+dat);
        	//统计附件 
        	var html="";
        	var sum=$('.study_additional_content_r').find('.add_study_content').length;
        	$('.span3').text(sum);
        	$('.study_additional_content_r').find('.add_study_content_c span').each(function(){
        		var url="jy/manage/res/download/"+$(this).parent().parent().attr('resid')+"?filename="+encodeURI($(this).text());
        		html="<div class='annun_fj' style='margin:4rem 3.2rem 0 1rem;'>"+
					 "<a href='"+url+"'>"+
					 "<dl><dd></dd><dt>"+$(this).text()+
					 "</dt></dl></a></div>"
				$('.annun_cont_b h6').after($(html));
        	}) 
        	var annunciate_cont = new IScroll('#annunciate_cont',{
          		scrollbars:true,
          		mouseWheel:true,
          		fadeScrollbars:true, 
          	});
    	});
    	
    }
})