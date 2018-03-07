define(["require","jquery"], function (require) {
	var $ = require("jquery");
	$(function(){
		init();
	}); 
	function init() {
		/*点击左侧菜单切换*/
		$("#history_menu").on("click","li a",function (){
			$(this).addClass("c_y_l_act").parent().siblings().children().removeClass("c_y_l_act");
			if($(this).attr("target") == "history_frame"){
				$("#history_index_content").hide();
				$("#history_frame").show();
			}
		});
		var baseurl =_WEB_CONTEXT_+"/jy/history/"+current_history_year;
		
	    $.getJSON(baseurl+"/data",function(data){
	    	var maxlenth = 0;
	    	var ele_len = 0;
	    	$.each(data.data, function(i,item){
	    	    $("<li/>").append($("<a/>").addClass(item.code == current_history_code ?"c_y_l_act":"").attr("href",baseurl+"/"+item.code+"/index").attr("target","history_frame").html(item.name)).appendTo("#history_menu");
	    	    if(item.countDesc.length > maxlenth )
	    	    	maxlenth = item.countDesc.length;
	    	    if(current_history_code != ''){
	    	    	$("#history_index_content").hide();
					$("#history_frame").show();
	    	    	$("#history_frame").attr("src",baseurl+"/"+current_history_code+"/index"+window.location.search);
	    	    }
	    	    var counthtml = "";
	   	    	for(var i=0;item.countDesc && i<item.countDesc.length;i++){
	    	    	counthtml+=item.countDesc[i]+"：&nbsp;"+item.count[i]+"&nbsp;&nbsp;";
	    	    	if(i%2!=0){
	    	    		counthtml += "</br>";
	    	    	}
	    	    }
	    	    if(counthtml){
	    	    	 ele_len++; 
	    	    	 $("<div/>").addClass("r_s_t_l_wrap")
	 	    	    .append($("<div/>").addClass("r_s_t_l_wrap_title").html(item.name))
	 	    	    .append($("<div/>").addClass("r_s_t_l_wrap_cont").html(counthtml))
	 	    	    .appendTo("#history_clumn_table");
	    	    }
	    	  });
	    	if(ele_len > 0 && ele_len % 2 == 1){
	    		var ht = $("#history_clumn_table > div:last").height();
	    		$("<div/>").addClass("r_s_t_l_wrap")
	    	    .append($("<div/>").addClass("r_s_t_l_wrap_title").height(ht).html(""))
	    	    .append($("<div/>").addClass("r_s_t_l_wrap_cont").height(ht).html(""))
	    	    .appendTo("#history_clumn_table");
	    	}
	    	$("#history_clumn_table").append("<div class='clear'></div>"); 
	    	 $(".r_s_t_l_wrap").each(function (){
	    		 $(this).find(".r_s_t_l_wrap_title").css("line-height",$(this).height()+'px');
    	    }); 
	    });
	}
	
});