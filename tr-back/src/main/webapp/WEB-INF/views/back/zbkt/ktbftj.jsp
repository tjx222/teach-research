<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<h2 class="contentTitle">课堂总数按天统计</h2>
	<div class="ktbftj pageContent">
	<div style="width: 430px;">
		<label>日期：</label> <input id="startTime" type="text" name="startTime" 
			style="width: 120px; background-color: #fff; height: 19px;"
			value="<fmt:formatDate value="${model.startTime }" pattern="yyyy-MM-dd"/>" datefmt="yyyy-MM-dd" maxDate="%y-%M-%d"
			readonly="readonly" class="date" /> 到 
			<input id="endTime" type="text" name="endTime"
			style="width: 120px; background-color: #fff; height: 19px;" 
			value="<fmt:formatDate value="${model.endTime }" pattern="yyyy-MM-dd"/>" datefmt="yyyy-MM-dd" maxDate="%y-%M-%d"
			readonly="readonly" class="date" />
		<button type="submit" onclick="ktbftj.loadchart(1)" style="width: 55px;">统计</button>
	</div>
	<div id="ktbftj_chartHolder" style="width: 1600px; height: 300px"></div>
	<h2 class="contentTitle">课堂开始时间实时统计</h2>
	<div style="width: 430px;">
		<label>日期：</label> <input id="startTime2" type="text" name="startTime" 
			style="width: 120px; background-color: #fff; height: 19px;"
			value="<fmt:formatDate value="${model.endTime}" pattern="yyyy-MM-dd"/>" datefmt="yyyy-MM-dd" maxDate="%y-%M-%d"
			readonly="readonly" class="date" />
		<button type="submit" onclick="ktbftj.loadchart(2)" style="width: 55px;">统计</button>
	</div>
	<div id="ktbftj_chartHolder2" style="width: 1600px; height: 300px"></div>
</div>
<div class="right" id="yh_unit_l" style="display: none">
			<div class="prompt">
				<p>
					<span>
						无数据
					</span>
				</p>
			</div>
		</div>
<script type="text/javascript">
var ktbftj = {
	_weight:document.documentElement.clientWidth*0.8,
	//课堂总数按天统计
	joinclassByDay:function(result){
		if(result.linesX.length==0){
			//无数据
			$("#ktbftj_chartHolder").html($("#yh_unit_l").html());
			return false;
		};
		$("#ktbftj_chartHolder").html("");
		var axisxstepLength = result.optionsX.length-1;
		var options = {
				axis: "0 0 1 1", // Where to put the labels (trbl)
				axisxstep: axisxstepLength==0?1:axisxstepLength, // x轴间隔数
				axisystep: 1, // y轴间隔数
				shade:false, // 阴影
				smooth:false, //直线
				symbol:"circle",
				colors:["#66ccFF"],
				axisxlables:result.optionsX
			};
		var r = Raphael("ktbftj_chartHolder");
		var lines = r.linechart(
				30, // X 左边距：像素
				10, // Y 下边距：像素
				ktbftj._weight, // X 宽度
				260, // Y 高度
				result.linesX, // x轴占位
				result.linesY, // y轴对应数据
				options // opts object
			).hover(function() {
		        this.flag = r.popup(this.x, this.y, this.value, "right").insertBefore(this);
		    }, function() {
		        this.flag.animate({opacity: 0}, 500, ">", function () {this.remove();});
		    });
	},
	//课堂开始时间实时统计
	joinclassTime:function(result){
		if(result.linesX.length==0){
			//无数据
			$("#ktbftj_chartHolder2").html($("#yh_unit_l").html());
			return false;
		};
		$("#ktbftj_chartHolder2").html("")
		var axisxstepLength = result.optionsX.length-1;
		var options = {
				axis: "0 0 1 1", // Where to put the labels (trbl)
				axisxstep: axisxstepLength==0?1:axisxstepLength, // x轴间隔数
				axisystep: 1, // y轴间隔数
				shade:false, // true, false
				smooth:false, //曲线
				symbol:"circle",
				colors:["#66ccFF"],
				axisxlables: result.optionsX
			};
		var r = Raphael("ktbftj_chartHolder2");
		var lines = r.linechart(
				30, // X start in pixels
				10, // Y start in pixels
				ktbftj._weight, // Width of chart in pixels
				260, // Height of chart in pixels
				result.linesX, // Array of x coordinates equal in length to ycoords
				result.linesY, // Array of y coordinates equal in length to xcoords
				options // opts object
			).hover(function() {
		        this.flag = r.popup(this.x, this.y, this.value, "right").insertBefore(this);
		    }, function() {
		        this.flag.animate({opacity: 0}, 500, ">", function () {this.remove();});
		    });
	},
	//根据日期查询
	loadchart:function(type){
		var url = "${ctx}/jy/back/zbkt/ktbftjResult";
		if(type==1){
			var startTime = $(".ktbftj #startTime").val();
			var endTime = $(".ktbftj #endTime").val();
			startTime = new Date(startTime).Format("yyyy-MM-dd HH:mm:ss");
			endTime = new Date(endTime).Format("yyyy-MM-dd HH:mm:ss");
			var data = {
					'startTime':startTime,
					'endTime':endTime,
					'flago':type
			}
			var callback = ktbftj.joinclassByDay;
			ktbftj.requestAjax(url,data,callback);
		}else{
			var startTime = $(".ktbftj #startTime2").val();
			startTime = new Date(startTime).Format("yyyy-MM-dd HH:mm:ss");
			var data = {
					'startTime':startTime,
					'flago':type
			}
			var callback = ktbftj.joinclassTime;
			ktbftj.requestAjax(url,data,callback);
		}
	},
	requestAjax:function(url,data,callback){
		$.ajax({
			type:"post",
			dataType:"json",
			url:url,
			data:data,
			success:function(result){
				if(callback){
					callback(result);
				}
			}
		});	
	},
}
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "H+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
} //var time = new Date().Format("yyyy-MM-dd HH:mm:ss");
$(function () {
	ktbftj.loadchart(1);
	ktbftj.loadchart(2);
})
</script>
