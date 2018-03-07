var totalList =new Array(); 
var readList =new Array(); 
var pingList =new Array(); 
var nameList = new Array();
var uriList = {};
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
		     url: _WEB_CONTEXT_+'/jy/managerecord/tchdata',
		     data:{'term':term,'t':Math.random()},
		    success: function(data){
		    	var jsonList = data;
		    	for(var index in jsonList){
		    		//alert(jsonList[index].name);
		    		var manager = jsonList[index];
		    		totalList.push(manager.submitNum);
		    		readList.push(manager.checkNum);
		    		pingList.push(manager.shareNum);
		    		nameList.push(manager.name);
		    		uriList[manager.name]=manager.uri;
		    	}
		    	draw(totalList,readList,pingList,nameList,uriList,term);
		    	$('#bottom').focus;
		    } ,error:function(){
		    	alert('error');
		    },
		    dataType: 'json'
		});
	}
	
	function draw(totalList,readList,pingList,nameList,uriList,term){
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
	            animation:true, 
	        },

	        title: {
	        	useHTML:true,
	            text: '教学管理记录',
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
	        			return  this.value +'<br /><a target="_blank" href="'+_WEB_CONTEXT_+'/jy/managerecord/' + uriList[this.value] +'&term='+term+'" style="color:#014efd;">查看详情</a>';
	        		},
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
	            headerFormat: '<b>{point.key}</b><br>',
	            pointFormat: '<span style="color:{series.color}">\u25CF</span> {series.name}: {point.y}'
	        },
	        plotOptions: {
	            column: {
	        dataLabels:{
	           enabled:true ,
						  inside:false
	       }
	            }
	        },

	        series: [{
	            name: '全校总数',
	            data: totalList
	        }, {
	            name: '撰写数',
	             data: readList
	        }, {
	            name: '分享数',
	             data: pingList
	        }]
	    });
		
	}
	
	//查看详情
	function showDetails(value){
		//alert(value);
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
		chart = null;
		$('#container').html('') ;
		} 