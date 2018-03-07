	function successAlert(save,isConfirm,callback){
		 var obj=document.getElementById("btn"); 
		addbefore();
		sAlert(save);
        if(isConfirm){
            var preserve = document.getElementById("msgBtn1");
//            var cancel = document.getElementById("msgBtn2");
            preserve.onclick = function(){
                presAlert();
                eval("var callbackfunc = "+callback);
                if(typeof(callbackfunc) == 'function'){
                	callbackfunc();
                }
            };
//            cancel.onclick = function(){
//                cansAlert();
//            };
        }else{
           hidesAlert(callback); 
       }
	};
	function addbefore() {  
//	    var newNode = document.createElement("div"); 
//	        newNode.innerHTML = "<div id='dlog'><div id='dlog_top'></div><div class='dlog_bottom'></div></div>";
//        var oldNode = document.getElementsByClassName("mask"); //目标标签 
//            oldNode[0].parentNode.insertBefore(newNode,oldNode[0]);//在目标标签前面添加元素 
	}  
    function presAlert(){  
        var bgObj=document.getElementById("bgDiv");
        var msgObj=document.getElementById("msgDiv");
        document.body.removeChild(bgObj); 
        document.body.removeChild(msgObj);
    }
     function cansAlert(){  
        var bgObj=document.getElementById("bgDiv");
        var msgObj=document.getElementById("msgDiv");
        document.body.removeChild(bgObj); 
        document.body.removeChild(msgObj);
    }
    function hidesAlert(callback){  
        document.getElementById("msgBtn1").style.display="none";
//        document.getElementById("msgBtn2").style.display="none"; 
        setTimeout(function(){
            var bgObj=document.getElementById("bgDiv");
            var msgObj=document.getElementById("msgDiv");
            document.body.removeChild(bgObj); 
            document.body.removeChild(msgObj);
            eval("var callbackfunc = "+callback);
            if(typeof(callbackfunc) == 'function'){
            	callbackfunc();
            }
        },2000);
    }
    function sAlert(str){  
        var msgw,msgh,bordercolor; 
        msgw=400;   //提示窗口的宽度  
        msgh=200;   //提示窗口的高度   
        titleheight=25 //提示窗口标题高度   
        //bordercolor="#336699";//提示窗口的边框颜色  
        titlecolor="#99CCFF";//提示窗口的标题颜色  
        var sWidth,sHeight;   
        sWidth=document.body.offsetWidth;
        //浏览器工作区域内页面宽度 或使用 screen.width//屏幕的宽度   
        sHeight=screen.height;//屏幕高度（垂直分辨率） 
        //背景层（大小与窗口有效区域相同，即当弹出对话框时，背景显示为放射状透明灰色） 
        var bgObj=document.createElement("div");//创建一个div对象（背景层） 
        //动态创建元素，这里创建的是 div 
        bgObj.setAttribute('id','bgDiv'); 
        bgObj.style.position="absolute";  
        bgObj.style.top="0";   
        bgObj.style.background="#777";   
        bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";  
        bgObj.style.opacity="0.6";  
        bgObj.style.left="0";   
        bgObj.style.width="100%";  
        bgObj.style.height="100%";  
        bgObj.style.zIndex = "10000";  
        document.body.appendChild(bgObj);
        //在body内添加该div对象  
        //创建一个div对象（提示框层）   
        var msgObj=document.createElement("div");
        //定义div属性，即相当于   
        msgObj.setAttribute("id","msgDiv");  
        msgObj.setAttribute("align","center"); 
        msgObj.style.background="white";   
        msgObj.style.position = "absolute";  
        msgObj.style.left = "50%";  
        msgObj.style.top = "50%";   
        msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";  
        msgObj.style.marginLeft = "-200px"    
        msgObj.style.marginTop = -100+document.documentElement.scrollTop+"px";  
        msgObj.style.width = msgw + "px";  
        msgObj.style.height =msgh + "px";  
        msgObj.style.textAlign = "center";  
        msgObj.style.lineHeight ="25px";  
        msgObj.style.zIndex = "10001";   
        var title=document.createElement("h4");
        //创建一个h4对象（提示框标题栏）  
        title.setAttribute("id","msgTitle");  
        title.setAttribute("align","center");  
        title.style.margin="0";  
        title.style.padding="10px 3px 3px 3px";   
        title.style.background="#f4f4f4";   
        title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100,  finishY=100,style=1,opacity=75,finishOpacity=100);";    
        title.style.borderBottom="1px #dbdbdb solid"  
        title.style.height="30px";     
        title.style.font="16px Microsoft YaHei, Geneva, Arial, Helvetica, sans-serif";  
        title.style.cursor="pointer";    
        title.innerHTML="操作提示";
        document.body.appendChild(msgObj);
        //在body内添加提示框div对象msgObj  
        document.getElementById("msgDiv").appendChild(title);
        //在提示框div中添加标题栏对象title   
        var txt=document.createElement("p");
        //创建一个p对象（提示框提示信息）  
        //定义p的属性，即相当于   
        //<p style="margin:1em 0;" id="msgTxt">测试</p>  
        var btn1=document.createElement("input");
//        var btn2=document.createElement("input");
        btn1.setAttribute("value","确定");  
        btn1.setAttribute("type","button");
        btn1.setAttribute("id","msgBtn1"); 
        btn1.style.width="75px"; 
        btn1.style.height="32px";   
        btn1.style.color="#fff";   
        btn1.style.textAlign="center";  
        btn1.style.font="14px Microsoft YaHei, Geneva, Arial, Helvetica, sans-serif"; 
        btn1.style.borderRadius="5px";
        btn1.style.border="none";  
        btn1.style.background="#fe7e37";   
        btn1.style.marginRight="15px"; 
        btn1.style.lineHeight="28px";  
//        btn2.setAttribute("value","取消");  
//        btn2.setAttribute("type","button"); 
//        btn2.setAttribute("id","msgBtn2");
//        btn2.style.width="75px"; 
//        btn2.style.height="32px";   
//        btn2.style.color="#fff";   
//        btn2.style.textAlign="center";  
//        btn2.style.font="14px Microsoft YaHei, Geneva, Arial, Helvetica, sans-serif"; 
//        btn2.style.borderRadius="5px";
//        btn2.style.border="none";
//        btn2.style.background="#999";   
//        btn2.style.lineHeight="28px"; 

        txt.style.margin="2.3em 0"; 
        txt.style.font="16px Microsoft YaHei, Geneva, Arial, Helvetica, sans-serif";  
        txt.setAttribute("id","msgTxt");  
        txt.innerHTML=str;
        //来源于函数调用时的参数值   
        document.getElementById("msgDiv").appendChild(txt);
        //在提示框div中添加提示信息对象txt 
        document.getElementById("msgDiv").appendChild(btn1);
//        document.getElementById("msgDiv").appendChild(btn2);
        //在提示框div中添加按钮

    }  
