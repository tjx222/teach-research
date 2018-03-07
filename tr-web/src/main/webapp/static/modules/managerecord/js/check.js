var totalList =new Array(); 
var readList =new Array(); 
var pingList =new Array(); 
var nameList = new Array();
var uriList = {};
var checkCount = {};
var checkYijian = {};
var titleList  = new Array();
var chart;
$(document).ready(function () {
	checkMainUserList();
	});
	
function list(term,grade,subject){
		 destroy() ;
		if(term==null||term=='')
			term=0;
		if(grade==null||grade=='')
			grade=0;
		if(subject==null||subject=='')
			subject=0;
		$.ajax({
		     type: 'get',
		     url: _WEB_CONTEXT_+'/jy/managerecord/checkdata',
		    data:{'term':term,'grade':grade,'subject':subject,'t':Math.random()},
		    success: function(data){
		    	var jsonList = data;
		    	for(var index in jsonList){
		    		//alert(jsonList[index].name);
		    		var manager = jsonList[index];
		    		totalList.push(manager.submitNum);
		    		readList.push(manager.checkNum);
		    		pingList.push(manager.commentNum);
		    		nameList.push(manager.name);
		    		uriList[manager.name]=manager.uri;
		    		checkCount[manager.name]=manager.checkNum;
		    		checkYijian[manager.name]=manager.commentNum;
		    		titleList.push(manager.title);
		    	}
		    	draw(totalList,readList,pingList,nameList,uriList,titleList,term);
		    	$('#bottom').focus;
		    } ,error:function(){
		    	alert('error');
		    },
		    dataType: 'json'
		});
	}
	
	function draw(totalList,readList,pingList,nameList,uriList,titleList,term){
		 chart = $('#container').highcharts({
	        chart: {
	            type: 'column',
	            options3d: {
	                enabled: true,
	                alpha: 15,
	                beta: 15,
	                viewDistance: 25,
	                depth: 40
	            },
	            marginTop: 80,
	            marginRight: 40,
	            backgroundColor:"#FFF",
	            animation:true
	        },

	        title: {
	        	 useHTML:true,
	            text: '查阅记录',
	            y:22,
	            style:{
	            	fontSize:'22px', 
	            }
	        },

	        xAxis: {
	            categories: nameList,
	        	labels:	{//生成可以点击的<a>横坐标	 
	        		formatter: function() {//this.value的值是：一月或其他的横坐标（当前点击的横坐标的值），showDetails为自己写的函数
	        			if(Number(checkCount[this.value])==0 && Number(checkYijian[this.value])==0){
	        				return  this.value +'<br /><a style="color:#014efd;">查阅详情</a>';	
	        			}else
	        				return  this.value +'<br /><a href="'+_WEB_CONTEXT_+'/jy/managerecord/check/' + uriList[this.value] +'?term='+term+'" style="color:#014efd;">查阅详情</a>';	
        			},
        			align:'center', 
	        		style: {
	        			fontSize:13, //刻度字体大小
	        		},
	        		y: 18
	        	},
	        	gridLineColor: '#d0d0d0',//纵向网格线颜色
	        	gridLineWidth: 1, //纵向网格线宽度
	        	y:10
	        },

	        yAxis: {
	            allowDecimals: false,
	            min: 0,
	            title: {
	                text: '数量'
	            },
//						   labels: {
//	                formatter: function() { //格式化标签名称
//	                    return this.value + '个';
//	                }
//	            }, 
	          minorTickInterval :  'auto' ,
	          gridLineColor: '#fbfbfb',//横向网格线颜色
	          gridLineDashStyle: 'longdash',//横向网格线样式
	          gridLineWidth: 1//横向网格线宽度
						
	        },

	        tooltip: {
	            headerFormat: '<b>{point.key}</b><br />',
	            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y}'
	        },

	        plotOptions: {
	            column: {
	            	dataLabels:{
	            		enabled:true ,
	            		inside:false
	            	}
	            },
	            series: {
	                cursor: 'pointer',
	                events: {
	                    click: function(event) {
	                    	if(this.name=="查阅数")
	                    		window.open(_WEB_CONTEXT_+"/jy/managerecord/check/"+ uriList[event.point.category]+"?listType=0&term="+term );
	                    	if(this.name=="查阅意见")
	                    		window.open(_WEB_CONTEXT_+"/jy/managerecord/check/"+ uriList[event.point.category]+"?listType=1&term="+term );
	                    }
	                }
	            }
	        },  
	        
	        series: [{
	            name: titleList[0],
	            data: totalList
	        }, {
	            name: '查阅数',
	             data: readList
	        }, {
	            name: '查阅意见',
	             data: pingList
	        }]
	    });
		
	}
	
	function checkMainUserList(){
		var term = $('input:radio[name="term"]:checked').val();
		var grade = $("#grade").val();
		var subject = $("#subject").val();
		list(term,grade,subject);
	}
	
	function destroy() { 
		totalList = [];
		readList = [];
		pingList = [];
		nameList = [];
		chart = null;
		$('#container').html('') ;
	} 
	