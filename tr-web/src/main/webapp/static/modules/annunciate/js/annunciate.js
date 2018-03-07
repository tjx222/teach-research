require(['jquery','editor','jp/jquery-ui.min','jp/jquery.dragsort.min','jp/jquery.blockui.min'],function(){
	var $ = require("jquery");
	$(function(){
		//网络编辑器
		webEditorOptions = {
			width:"100%",
			height:'375px',  
			style:"2",
		};
		editor = $("#web_editor").editor(webEditorOptions);
	});
	require("jp/jquery.dragsort.min")
	$(function(){
		init();
	});
	function init() {
		//预览通知公告
		$('.preview').click(function (){
			var html = editor[0].html();//文本框的html代码
			var text = editor[0].text();//文本框的文字
			$('#web_editor').text(html);//设置上html代码
			var title=$("#title").val();
			var type=$('input:radio:checked').val();
			var from=$('#fromWhere').val();
			if(!$("#annunciate_form").validationEngine('validate')) {
				return false;
			}
			if($(":checked").val()==1){
				if(redTitle==1){
					alert("请添加红头！");
					return;
				}
			}
			var n=(text.split('>')).length-1;
			if(n>400){
				alert("最多可输入400个表情！");
				return;
			}
			if(text.length==0){
				alert("通知公告内容不能为空！");
				return;
			}else if(text.length-n*113>5000){
				alert("通知公告内容不能超过5000！");
				return;
			}
			$("#preview_box").dialog({width:1000,height:550});
			if(type==1){
				$('.file_wrap_top').remove();
				$('.file_wrap').prepend('<div class="file_wrap_top">'
						+'<h3 class="file_wrap_h3"><span>'+$("#titleId").text()+'</span></h3>'
						+'<h4 class="file_wrap_h4">'+$("#fromWhere").val()+'</h4></div>');
				
			}
			var date=new Date();
			var time=date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
			var name=$('#name').val();
			$('.file_wrap_bottom_h4').html("发布时间："+time+"&nbsp;&nbsp;|&nbsp;&nbsp;作者："+name);
			$('.pt_file_wrap_h3 span').html(title);
			$('.file_wrap_bottom p').html(html);
		});
		$('.select_div').click(function (){
			$('.select_div1').fadeToggle();
		});
		$(document).bind("click",function(e){
		    var target = $(e.target);
		    if(target.closest("#test").length == 0){
		   		 $(".select_div1").hide();
		   	 }
		 })
		$('.add_ht_btn').click(function (){
			$('#newRedHead').show();
		});
		//添加红头 
		window.addRedHead=function (){
		 	jq("#redHead").attr("src",_WEB_CONTEXT_ + "/jy/annunciate/addRedHead?"+ Math.random());
			jq(document.body).css({"overflow-x":"hidden","overflow-y":"hidden"});
		 	jq.blockUI({ message: jq('#redHead_iframe'),css:{width: '400px',height: '210px',top:'30%',left:'50%',marginLeft:'-200px'}});
		 }
		$('.red_head_title h3').click(function (){
		    $('.red_head_title_name').slideToggle()
		 });
		//切换普通文件与红头文件
		$(":radio[name='type']").click(function(){
			var index = $(":radio[name='type']").index($(this));
			if(index == 0){
			    $('#red_header').hide(); 
			 } else{
	 			$('#red_header').show(); 
	 			$('.red_head_title_name').hide();
	 			var redTitle=$('#titleId').text();
	 			if(redTitle==null||redTitle==""){
	 				var $this=$('.red_head_cont .red_head_name:first');
	 				$('#titleId').html($this.text());
	 				$('#redTitleId').val($this.attr("data-id"));
	 				
	 			}
			 }
		});
		var index=$('input:radio:checked').val();
		if(index == 0){
		    $('#red_header').hide(); 
		 } else{
 			$('#red_header').show(); 
 			$('.red_head_title_name').hide();
 			var redTitle=$('#titleId').text();
 			if(redTitle==null||redTitle==""){
 				var $this=$('.red_head_cont .red_head_name:first');
 				$('#titleId').html($this.text());
 				$('#redTitleId').val($this.attr("data-id"));
 				
 			}
		 }
		//添加红头
		$('.add_red_btn').click(function(){
			$('#red_header').show(); 
 			$('.red_head_title_name').hide();
 			$('.red_head_title h3').hide();
 			$('#fromWhere').hide();
 			$('.red_head_title_save').show();
		});
		$('.ht_btn').click(function(){
			$('#red_header').show(); 
			$('.ht_btn').hide();
			$('#fromWhere').hide();
 			$('.red_head_title_name').hide();
 			$('.red_head_title h3').hide();
 			$('.red_head_title_save').show();
		});
		//取消添加红头
		$('.cancel').click(function(){
			$('#red_header').show(); 
 			$('.red_head_title_name').hide();
 			$('.red_head_title h3').show();
 			$('.red_head_title_save').hide();
		});
	
		//保存红头
		$('.saveRed').click(function(){
			var title=$('#redTitle').val();
			if(title==null||title==""){
				alert("红头标题不能为空");
				return false;
			}
			if(title.length>80){
				alert("红头标题字数不能超过80");
				return false;
			}
			$.ajax({
				type :  "post",
				dataType : "json",
				url : _WEB_CONTEXT_+"/jy/annunciate/addredTitles",
				data : {"title":title},
				success:function(data){
					//alert("通知公告发布成功");
					if(data.result.code==1){
						window.parent.location.href = _WEB_CONTEXT_+"/jy/annunciate/release?type=1";
					}else{
						alert("红头保存失败");
					}
				}
			});
		});
		//选中红头
		$('.red_head_name span').click(function(){
			$('#redTitleId').val($(this).parent().attr("data-id"));
			$('#titleId').text($(this).text());
			$('.red_head_title_name').hide();
		});
		//删除红头
		$('.red_head_name b').click(function(){
			var id=$(this).parent().attr("data-id");
			if(id!=null){
				$.ajax({
					type:"post",
					dataType:"json",
					url:_WEB_CONTEXT_+"/jy/annunciate/deleteredTitles",
					data: {"id": id},
					success:function(data){
						if(data.result.code==1){
							$('#ht_'+id).parent().remove();
							window.parent.location.href = _WEB_CONTEXT_+"/jy/annunciate/release?type=1";
					    }else{
					    	alert("删除失败");
					    	}
					    }
				});
				//location.href = _WEB_CONTEXT_+"/jy/annunciate/deleteredTitles?id="+id;
			}
		})
		//上一篇 
		$('.last_one').click(function(){
			var id=$(this).parent().attr("data-id");
			var status=$(this).parent().attr("data-status");
			var type=$(this).parent().attr("data-type");
			if(id!=null){
				location.href=_WEB_CONTEXT_+"/jy/annunciate/previousAnnunciates?id="+id+"&&status="+status+"&&type="+type;
			}
		});
		//下一篇 
		$('.next_article').click(function(){
			var id=$(this).parent().attr("data-id");
			var status=$(this).parent().attr("data-status");
			var type=$(this).parent().attr("data-type");
			if(id!=null){
				location.href=_WEB_CONTEXT_+"/jy/annunciate/nextAnnunciates?id="+id+"&&status="+status+"&&type="+type;
			}
		});
		//发布
		$('.publish').click(function(){
			saveAnnunciate(true);
		});
		//存草稿箱
		$('.save_drafts').click(function(){
			saveAnnunciate(false);
		});
		window.saveAnnunciate=function(state){
			var html = editor[0].html();//文本框的html代码
			var text = editor[0].text();//文本框的文字
			$('#web_editor').text(html);//设置上html代码
			var title=$("#title").val();
			var fromWhere=$("#fromWhere").val();
			var redTitle=$("#changRedHead").val();
			var tlength=title.trim().length;
			if(!$("#annunciate_form").validationEngine('validate')) {
				return false;
			}
			if($(":checked").val()==1){
				if(redTitle==1){
					alert("请添加红头！");
					return;
				}
			}
			var n=(text.split('>')).length-1;
			if(n>400){
				alert("最多可输入400个表情！");
				return;
			}
			if(text.length==0){
				alert("通知公告内容不能为空！");
				return;
			}else if(text.length-n*113>5000){
				alert("通知公告内容不能超过5000！");
				return;
			}
			var roleId=$("#roleId").val();
			if(roleId==1378||roleId==1380){
				var orgCount=$('#ul3 li').size();
				if(orgCount!=null&&orgCount<=0){
					alert("请选择机构");
					return;
				}
				var orgsjoinIds=",";
				$('#ul3 li').each(function(){
					orgsjoinIds+=$(this).prop("id")+",";
				});
				$('#orgsjoinIds').val(orgsjoinIds);
				$('#orgsJoinCount').val($("#ul2 li").size());
			}
			updateResIds();
			if(state){
				if(confirm("您确定要发布吗？"))
					$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/annunciate/annunciates?status=1",
						data:$("#annunciate_form").serialize(),
						success:function(data){
							if(data.result.code==1){
								alert("通知公告发布成功");
								window.location.href = _WEB_CONTEXT_+"/jy/annunciate/punishs";
							}else{
								alert("通知公告发布失败");
							}
							
						}
					});
			}else{
				if(confirm("您确定要存入草稿箱吗？"))
					$.ajax({
						type:"post",
						dataType:"json",
						url:_WEB_CONTEXT_+"/jy/annunciate/annunciates?status=0",
						data:$("#annunciate_form").serialize(),
						success:function(data){
							if(data.result.code==1){
								window.location.href = _WEB_CONTEXT_+"/jy/annunciate/punishs";
							}else{
								alert("通知公告存草稿失败");
							}
							
						}
					});
			}
		}
		//显示机构集合
		$('.please_select').click(function (){
			 $("#assessment_committee_wrap").dialog({width:560,height:480});
			 var searchStr = $("#search").val();
			 var orgList=null;
				$.ajax({  
			        async : false,  
			        cache:true,  
			        type: 'POST',  
			        dataType : "json",  
			        data:{"search":searchStr},
			        url: _WEB_CONTEXT_+"/jy/annunciate/getOrgListOfArea.json",
			        error: function () {
			        	$("#ul1").html("机构加载失败");
			        },  
			        success:function(data){
			        	orgList = data.orgList;
			        }  
			    });
			var htmlStr="<div id='quanxuan'><input type='checkbox' name='checkbox3'  onclick='quanxuan(this);'>&nbsp;&nbsp;&nbsp;全选<div class='clear'></div></div>";
			for(var i=0;i<orgList.length;i++){
				htmlStr += "<li id='s_"+orgList[i].id+"' value='"+orgList[i].id+"' title='"+orgList[i].name+"'><input type='checkbox' name='checkbox1'><a>"+cutStr(orgList[i].name,19,true)+"</a></li>";
			}
			$("#ul1").html(htmlStr);
			$("#ul2 li").each(function(){
				$('#ul1 #s_'+$(this).prop("value")).remove();
			});
			if($("#ul1 li").size()>0)
				$('#quanxuan').show();	
			else
				$('#quanxuan').hide();
			if($("#ul2 li").size()>0)
				$('#quanxuan2').show();
			else
				$('#quanxuan2').hide();
			
			$('#selectOrg').text("已选学校("+$("#ul2 li").size()+")");
	    });
		$("#ul1").on("change","input[name='checkbox1']",function(){
			if($("#ul1 input[name='checkbox1']:not(:checked):visible").length>0){
				$("#quanxuan").find("input").prop("checked",false);
			}else{
				$("#quanxuan").find("input").prop("checked",true);
			}
		});
		$("#ul2").on("change","input[name='checkbox1']",function(){
			if($("#ul2 input[name='checkbox1']:not(:checked)").length>0){
				$("#quanxuan2").find("input").prop("checked",false);
			}else{
				$("#quanxuan2").find("input").prop("checked",true);
			}
		});
		//添加已选择的学校
		$('.add_1').click(function(){
			//获取选中的选项，删除并追加给对方
	    	$("#ul1 input[name='checkbox1']:checked").each(function(){
	  			$(this).parent().find("a").append('<b class="remo" id="remove"></b>');
	  			$(this).prop("checked",false);
	  			$(this).parent().appendTo('#ul2');
	    	});
	       	//如果右侧有内容，则显示全选复选框
	       	if($("#ul2 li").length>0){
	        	$("#quanxuan2").show();
	        	$("#quanxuan2").find("input[name='checkbox2']").prop("checked",false);
	        }
	       	//如果左边无内容则隐藏全选框
	       	if($("#ul1 li:visible").length<=0){
	       		$("#quanxuan").hide();
	       	} 
	       	$('#selectOrg').text("已选学校("+$("#ul2 li").size()+")");
		});
		 //移到左边
	    $('#add_2').on('click',function() {
	     	$("#ul2 input[name='checkbox1']:checked").each(function(){
	   			$(this).prop("checked",false);
	   			$(this).parent().appendTo('#ul1');
	     	});
	     	if($("#ul2 input[name='checkbox1']").length<=0){
	     		$("#quanxuan2").hide();
	        }
	     	//如果左边有内容则显示全选框
	       	if($("#ul1 input[name='checkbox1']:visible").length>0){
	       		$("#quanxuan").find("input").attr("checked",false);
	       		$("#quanxuan").show();
	       	}
	       	$('#selectOrg').text("已选学校("+$("#ul2 li").size()+")");
	     });
	  //确认所选择的学校
	    $('.btn_sub').click(function(){
	    	var orgsHtml = "";
	    	$("#ul2 li").each(function(){
	    		orgsHtml +="<li id='"+$(this).prop("value")+"'><span></span><strong title='"+$(this).text()+"'>"+cutStr($(this).text(),60,true)+"</strong><b id='"+$(this).prop("value")+"'></b></li>"
	    	});
	    	$("#ul3").html(orgsHtml);
	    	$("#orgCount").text("参与学校（"+$("#ul2 li").size()+"个）");
	    	$('.mask').hide();
	    	$("#assessment_committee_wrap").hide();
			$('.mask').hide();
			$(document.body).css({"overflow-x":"auto","overflow-y":"auto"});
	    });
	    $('.btn').click(function(){
	    	var searchStr = $("#search").val();
			var orgList=null;
				$.ajax({  
			        async : false,  
			        cache:true,  
			        type: 'POST',  
			        dataType : "json",  
			        data:{"search":searchStr},
			        url: _WEB_CONTEXT_+"/jy/annunciate/getOrgListOfArea.json",
			        error: function () {
			        	$("#ul1").html("机构加载失败");
			        },  
			        success:function(data){
			        	orgList = data.orgList;
			        }  
			    });
			var htmlStr="<div id='quanxuan'><input type='checkbox' name='checkbox3'  onclick='quanxuan(this);'>&nbsp;&nbsp;&nbsp;全选<div class='clear'></div></div>";
			for(var i=0;i<orgList.length;i++){
				htmlStr += "<li id='s_"+orgList[i].id+"' value='"+orgList[i].id+"' title='"+orgList[i].name+"'><input type='checkbox' name='checkbox1'><a>"+cutStr(orgList[i].name,19,true)+"</a></li>";
			}
			$("#ul1").html(htmlStr);
			$("#ul2 li").each(function(){
				$('#ul1 #s_'+$(this).prop("value")).remove();
			});
	    });
	    //移除学校
	    $('#ul3').on('click','li b',function(){
	    	$(this).parent().remove();
	    	var id=$(this).prop("id");
	    	$("#ul2 #s_"+id).remove();
	    	$("#orgCount").text("参与学校（"+$("#ul2 li").size()+"个）");
	    });
	    //移除附件
	    $('.files_wrap_right').click(function(){
	    	var resId=$(this).parent().attr("data-resId");
	    	removeRes(resId,$(this));
	    })
	 }
});
/**
 * 通知公告 ------start-----
 */
//上传回调
function backUpload(data) {
	var rid = "'" +$("input[name=backresId]").val()+ "'";
	var trhtml = '<div class="files_wrap" data-resId="'+$("input[name=backresId]").val() +'"><div class="files_wrap_left"></div>'+
	'<div title="'+$("#originFileName").val()+'" class="files_wrap_center">  '+ cutStr($("#originFileName").val(),29,true) +'</div>'+
	'<div class="files_wrap_right" onclick="removeRes('+ rid +',$(this));"></div>'+
	'</div>';
	$("#resdownload").prepend(trhtml);
	updateResIds();
}
//上传之前调用
function beforeUpload(){
	if($("#resTable tr").length<6){
		return true;
	}else{
		alert("最多允许上传6个参考附件");
		return false;
	}
}
//删除附件
function removeRes(resId,thi) {
	thi.parent().remove();
	updateResIds();
	
}
//更新资源resids
function updateResIds(){
	var attachs="";
	$('#resdownload').find('.files_wrap').each(function(){
		if(attachs!="")
			attachs+="#";
		attachs+=$(this).attr("data-resId");
	});
	$('#tzggRes').val(attachs);
}
//全选
function quanxuan(obj){
	$(obj).parent().parent().find("input[name='checkbox1']").each(function(){
		if(!$(this).is(":hidden")){
			$(this).prop("checked",$(obj).is(":checked"));
		}
	});   
}
//进入转发区域通知
function forwardAnnunciate(id){
	window.parent.location.href = _WEB_CONTEXT_+"/jy/annunciate/toForwardAnnunciate?id="+id;
}

