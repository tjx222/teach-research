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
	});
    function init() { 
    	var data=$('.managerecord_check_top ul');
		list(data.attr('data-term'),data.attr('data-grade'),data.attr('data-subject'));
    	$('.managerecord_check_top ul li a').click(function(){
    		$(this).addClass('com_header_act').parent().siblings().find('a').removeClass('com_header_act');
        	var term=$(this).attr("data-term");
        	var grade=$(this).parent().parent().attr("data-grade");
        	var subject=$(this).parent().parent().attr("data-subject"); 
        	list(term,grade,subject);
        });
        function destroy() { 
    		totalList = [];
    		readList = [];
    		pingList = [];
    		chart = null;
    		$('#chart').html('') ;
    	} 
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
    		    		//successAlert(jsonList[index].name);
    		    		var manager = jsonList[index];
    		    		if(manager.type==6 || manager.type==10){//排除听课记录和教学文章
    		    		}else{
    		    			totalList.push(manager.submitNum);
    		    			readList.push(manager.checkNum);
    		    			pingList.push(manager.commentNum);
    		    		}
    		    	}
    		    	echart(totalList,readList,pingList);
    		    	//$('#bottom').focus;
    		    } ,error:function(){
    		    	successAlert('error');
    		    },
    		    dataType: 'json'
    		});
    	}
    }
    function echart(totalList,readList,pingList){
    	var barChart = echarts.init(document.getElementById('chart'));
    	var option = {
		
        legend: {
        	 y: 'top',
            data:['各学科提交总数','查阅数','查阅意见']
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
                data : ['查阅教案','查阅课件','查阅反思','查阅集体备课','查阅计划总结'], 
                /*label : {
		            formatter: function() { 
		            	
		    			if(Number==0){
		    				return  this.value +'<br /> <a style="color:#014efd;">查阅详情</a></br>';	
		    			}else
		    				return  this.value +'<br /> <a href="style="color:#014efd;">查阅详情</a></br>';
		    			var res = 'Function formatter : <br/>' + params[0].name;
		                for (var i = 0, l = params.length; i < l; i++) {
		                    res += '<br/>' + params[i].seriesName + ' : ' + params[i].value;
		                }
		                return 'loading';
					}
                },*/
                axisLine:{ show:false },
                axisTick:{ show:false },
                axisLabel:{ 
                    textStyle:{
                       color:"#474747", //刻度颜色
                       fontSize:16    //刻度大小 
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
                name:'各学科提交总数',
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
                name:'查阅数',
                type:'bar',
                barWidth:30,
                itemStyle: {normal: {color:'rgba(122,220,112,1)', label:{show:true,textStyle:{color:'#7d7d7d',fontSize:16}}}},
                data:readList
            },
            {
                name:'查阅意见',
                type:'bar',
                barWidth:30,
                itemStyle: {normal: {color:'rgba(76,200,231,1)', label:{show:true,textStyle:{color:'#7d7d7d',fontSize:16}}}},
                data:pingList
            }
        ]
    };
    	barChart.setOption(option);
    }
});