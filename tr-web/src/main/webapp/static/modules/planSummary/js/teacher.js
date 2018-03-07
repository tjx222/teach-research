define('teacher',[ 'require', 'hogan', 'jquery' ],function(require) {
			var $ = require('jquery'), hogan = require('hogan');
			var contentTemplate = null;
			
			var doSearch = function() {
				var gradeId = $('.grade.nj_act').attr('data-gradeId');
				var subjectId = $('.subject.primary_act').attr('data-subjectId');
				var gradeName = $('.grade.nj_act').attr('data-gradeName');
				var subjectName = $('.subject.primary_act').attr('data-subjectName');
				$.get('./jy/planSummaryCheck/teacher/list.json?gradeId='
						+ gradeId + '&subjectId=' + subjectId,
						function(result) {
							if (result && result.result.code) {
								var data = result.result.data;
								var total = data.total;
								data['gradeSubjectName']=gradeName+subjectName;
								total['plainSummaryNum'] = total.plainNum
								+ total.summaryNum;
								total['plainSummarySubmitNum'] = total.plainSubmitNum
								+ total.summarySubmitNum;
								total['plainSummaryCheckedNum'] = (total.plainCheckedNum + total.summaryCheckedNum) > 0 ? (total.plainCheckedNum + total.summaryCheckedNum)
										: 0;
								if(data.checkStatisticses.length==0){
									data['noTeacher']=true;
								}
								for (var i = 0; i < data.checkStatisticses.length; i++) {
									var item = data.checkStatisticses[i];
									item['plainSummaryNum'] = item.plainNum
									+ item.summaryNum;
									item['plainSummarySubmitNum'] = item.plainSubmitNum
									+ item.summarySubmitNum;
									item['plainSummaryCheckedNum'] = (item.plainCheckedNum + item.summaryCheckedNum) > 0 ? (item.plainCheckedNum + item.summaryCheckedNum)
											: 0;
									if(item.user['photo']==null||item.user['photo']==''){
										item.user.photo="static/common/images/default.jpg";
									}
								}
								$('#content').html(contentTemplate.render(data));
							} else {
								alert(result.errorMsg);
							}
						}, 'json')
			}
			
			$(function() {
				contentTemplate = hogan.compile($("#contentTemplate")
						.html());
				$('.grade').click(function() {
					$('.grade').removeClass('nj_act');
					$(this).addClass('nj_act');
					doSearch();
				});
				$('.subject').click(function() {
					$('.subject').removeClass('primary_act');
					$(this).addClass('primary_act');
					doSearch();
				});
				// 查询信息
				doSearch();
				
				var li1 = $(".in_reconsideration_see_title_box .grade ");
				var window1 = $(".out_reconsideration_see_title_box ol");
				var left1 = $(".out_reconsideration_see_title_box .scroll_leftBtn");
				var right1 = $(".out_reconsideration_see_title_box .scroll_rightBtn"); 
				window1.css("width", li1.length*95+"px");  
				if(li1.length >= 7){
					left1.show();
					right1.show();
				}else{
					left1.css({"visibility":"hidden"});
					right1.css({"visibility":"hidden"});
				} 
				var lc1 = 0;
				var rc1 = li1.length-7; 
				left1.click(function(){
					if (lc1 < 1) {
						return;
					}
					lc1--;
					rc1++;
					window1.animate({left:'+=90px'}, 500);  
				});

				right1.click(function(){
					if (rc1 < 1){
						return;
					}
					lc1++;
					rc1--;
					window1.animate({left:'-=90px'}, 500); 
				});
			});

		});
