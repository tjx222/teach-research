var totalList =new Array(); 
var readList =new Array(); 
var pingList =new Array(); 
var nameList = new Array();
var joinList = new Array();
var chart;
$(document).ready(function () {
	list(term);
	});
	
	function list(term){
		 destroy() ;
		if(term==null||term=='')
			term=0;
		$.ajax({
		     type: 'get',
		     url: _WEB_CONTEXT_+'/jy/managerecord/jyhdata',
		     data:{'term':term,'t':Math.random()},
		    success: function(data){
		    	var jsonList = data;
		    	for(var index in jsonList){
		    		//alert(jsonList[index].name);
		    		var manager = jsonList[index];
		    		totalList.push(manager.submitNum);
		    		if(index==1)
		    			readList.push(null);
		    		else
		    			readList.push(manager.checkNum);
		    		if(index==1)
		    			pingList.push(null);
		    		else
		    			pingList.push(manager.shareNum);
		    		joinList.push(manager.commentNum);
		    		nameList.push(manager.name);
		    	}
		    	draw(totalList,readList,pingList,nameList,joinList,term);
		    	$('#bottom').focus;
		    } ,error:function(){
		    	alert('error');
		    },
		    dataType: 'json'
		});
	}
	
	function draw(totalList,readList,pingList,nameList,joinList,term){
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
	            text: '教研活动记录',
	            y:22,
	            style:{
	            	fontSize:'22px'
	            }
	        },
	        credits:{//右下角的文本  
	            enabled: true,  
	            position: {//位置设置  
	                align: 'right',  
	                x: -10,  
	                y: -10  
	            },  
	            href: "",//点击文本时的链接  
	            style: {  
	                color:'blue'  
	            },  
	            text: ""//显示的内容  
	        },  
	        xAxis: {
	            categories: nameList,
	        	labels:	{//生成可以点击的<a>横坐标	 
	        		formatter: function() {//this.value的值是：一月或其他的横坐标（当前点击的横坐标的值），showDetails为自己写的函数	
	        			
	        			if(this.value=='集体备课'){
	        				return  this.value +'<br /><a href="'+_WEB_CONTEXT_+'/jy/managerecord/activity?term='+term+'" style="color:#014efd;top:10;">查看详情</a>';	
	        			}else if(this.value=='区域教研')
	        				return  this.value +'<br /><a href="'+_WEB_CONTEXT_+'/jy/managerecord/region?term='+term+'" style="color:#014efd;">查看详情</a>';
	        			else
	        				return  this.value +'<br /><a href="'+_WEB_CONTEXT_+'/jy/managerecord/school?term='+term+'" style="color:#014efd;">查看详情</a>';
	        		} ,
	        		align:'center', 
	        		style: {
	        			fontSize:13, //刻度字体大小
	        		},
	        		y: 18
	        	},
	        gridLineColor: '#d0d0d0',//纵向网格线颜色
	        gridLineWidth: 1, //纵向网格线宽度   
	        },

	        yAxis: {
	            allowDecimals: false,
	            min: 0,
	            title: {
	                text: '数量'
	            },
	          minorTickInterval :  'auto' ,
	          gridLineColor: '#fbfbfb',//横向网格线颜色
	          gridLineDashStyle: 'longdash',//横向网格线样式
	          gridLineWidth: 1//横向网格线宽度
						
	        },

	        tooltip: {
	            headerFormat: '<b>{point.key}</b><br>',
	            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y}'
	        },

	        plotOptions: {
	            column: {
	             
								
	        dataLabels:{
	           enabled:true ,  inside:false
	       }
	            }
	        },

	        series: [{
	            name: '全校总数',
	            data: totalList
	        }, {
	            name: '发起总数',
	             data: readList
	        }, {
	            name: '分享数',
	             data: pingList
	        }, {
	            name: '参与数',
	             data: joinList
	        }]
	    });
		
	}
	
	//查看详情
	function showDetails(value){
		alert(value);
	}
	
	function showList(obj){
		var term = $(obj).val();
		list(term);
	}
	
	function destroy() { 
		totalList = [];
		readList = [];
		pingList = [];
		nameList = [];
		joinList=[];
		chart = null;
		$('#container').html('') ;
		} 