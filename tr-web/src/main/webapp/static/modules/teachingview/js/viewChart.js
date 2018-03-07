define(["require","jquery",'echarts/echarts'], function (require) {
	var echarts = require("echarts/echarts");
	window.viewChart = function(trArray,colNum){
		
		//在页面获取并构造初始化数据
		var objectList = new Array();
		function Person(name,num){
			this.name = name;
			this.num = num;
		}
		trArray.each(function(index,obj){
			var text = $(obj).find("td").eq(colNum).text();
			if (text.indexOf("/") != -1) {
				var textArr = text.split("/");
				text = textArr[1];
			}
			objectList.push(new Person($(obj).find("td").eq(0).text(),text));
		});
		objectList.sort(function(a,b){
			return b.num-a.num;
		});
		var headerData = new Array();
		var numData = new Array();
		for(var obj in objectList){
			headerData.push(objectList[obj].name);
			numData.push(objectList[obj].num);
		}
		var radioText = new Array();
		radioText.push($(".radio_div").find("input[type='radio']:checked").parent().text());
		$(".chartDiv").show();
		//生成chart图
		initChart(headerData,numData,radioText);
	}
	function initChart(headerData,numData,radioText){
		// 基于准备好的dom，初始化echarts实例
	    var myChart = echarts.init(document.getElementById('chartDiv'));
	    // 指定图表的配置项和数据
	    var option = {
	        title: {
	            text: ''
	        },
	        tooltip: {},
	        legend: {
	            data:radioText
	        },
	        xAxis: {
	            data: headerData
	        },
	        yAxis: {},
	        color:['#27bb8b'],
	        series: [{
	            name: radioText[0],
	            type: 'bar',
	            data: numData
	        }]
	    };
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);

	}


});