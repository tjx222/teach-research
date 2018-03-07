define(["require","zepto","echarts/echarts","iscroll"], function (require) {
	require("zepto");
	var echarts = require("echarts/echarts");
	var $ = Zepto;
	$(function(){ 
		echart();
		init(); 
		var totalList =new Array(); 
		var readList =new Array(); 
		var pingList =new Array();
		var joinList =new Array();
	});
    function init() { 
		list($('.managerecord_check_top ul').attr('data-term'));
    	$('.managerecord_check_top ul li a').click(function(){
    		$(this).addClass('com_header_act').parent().siblings().find('a').removeClass('com_header_act');
        	var term=$(this).attr("data-term");
        	list(term);
        });
        function destroy() { 
    		totalList = [];
    		readList = [];
    		pingList = [];
    		joinList = [];
    		chart = null;
    		$('#chart').html('') ;
    	} 
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
   		    		//successAlert(jsonList[index].name);
   		    		var manager = jsonList[index];
   		    		totalList.push(manager.submitNum);
   		    		readList.push(manager.checkNum);
   		    		pingList.push(manager.shareNum);
   		    		joinList.push(manager.commentNum);
   		    	}
   		    	echart(totalList,readList,pingList,joinList);
   		    } ,error:function(){
   		    	successAlert('error');
   		    },
   		    dataType: 'json'
   		});
   	 }
    }
    function echart(totalList,readList,pingList,joinList){
    	var barChart = echarts.init(document.getElementById('chart'));
    	var option = {
		
        legend: {
        	 y: 'top',
            data:['全校总数','发起总数','分享数','参与数']
        },
        calculable : true, 
        grid: { 
        	y: 70, 
        	y2:30, 
        	x2:20
        },
        xAxis : [
            {
                type : 'category', 
               /* splitLine: {show:false},*/
                data : ['集体备课','校际教研'], 
                axisLine:{ show:false },
                axisTick:{ show:false },
                axisLabel:{ 
                    textStyle:{
                       color:"#474747", //刻度颜色
                       fontSize:14    //刻度大小 
                  }
                }
                
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLine:{ show:false },
                /*splitLine: {show:false},*/
            }
        ],
       
        series : [
			{
			    name:'全校总数',
			    type:'bar',
			    barWidth:30,
			    itemStyle: {normal: { 
			    	color:'rgba(253,210,75,1)',
			    	label:{
			    		show:true,
			    		textStyle:{
			    			color:'#7d7d7d',
			    			fontSize:16
			    			}
			        }
			    }
			},
			    data:totalList
			},
            {
                name:'发起总数',
                type:'bar',
                barWidth:30,
                itemStyle: {normal: {color:'rgba(122,220,112,1)', label:{show:true,textStyle:{color:'#7d7d7d',fontSize:16}}}},
                data:readList
            },
            {
                name:'分享数',
                type:'bar',
                barWidth:30,
                itemStyle: {normal: {color:'rgba(76,200,231,1)', label:{show:true,textStyle:{color:'#7d7d7d',fontSize:16}}}},
                data:pingList
            },
            {
                name:'参与数',
                type:'bar',
                barWidth:30,
                itemStyle: {normal: {color:'rgba(237,145,33,1)', label:{show:true,textStyle:{color:'#7d7d7d',fontSize:16}}}},
                data:joinList
            }
        ]
    };
    	barChart.setOption(option);
    }
});