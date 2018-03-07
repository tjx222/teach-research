<%--
	上传文件组件
--%>
<%@ tag pageEncoding="UTF-8" description="文件上传组件" trimDirectiveWhitespaces="true"  %>
<%@ include file="/WEB-INF/include/taglib.jspf"%>
<%-- <%@ attribute name="name" type="java.lang.String" required="true" description="文件上传完成后存储隐藏域名称" %> --%>
<%@ attribute name="fileType" type="java.lang.String" required="false" description="文件类型" %>
<%@ attribute name="relativePath" type="java.lang.String" required="false" description="文件存储相对路径" %>
<%@ attribute name="startElementId" type="java.lang.String" required="false" description="启动文件html元素id,不设置则在选择文件后自动上传" %>
<%@ attribute name="callback" type="java.lang.String" required="false" description="文件上传成功后回调" %>
<%@ attribute name="beforeupload" type="java.lang.String" required="false" description="文件上传前回调" %>
<%@ attribute name="fileSize" type="java.lang.Integer" required="false" description="单个文件大小，默认10M" %>
<%@ attribute name="isWebRes" type="java.lang.Boolean" required="false" description="是否web资源文件上传" %>
<%@ attribute name="progressBar" type="java.lang.Boolean" required="false" description="是否加入进度条" %>
<%@ attribute name="num" type="java.lang.Integer" required="false" description="多个附件时为了区别所用的编号" %>
<style>
.progressDiv{
	width: 10rem;
    height: 3rem;
}
.process{
	width: 10rem;
    height: 1.1rem;
    margin-top: 0.4rem;
    border:0.083rem #1aa97b solid;
}
.process span{
   background-color: #1aa97b; 
   display: block;
  height: 1.1rem;
}
.processPercent{
	width: 10rem;
	height:1.9rem;
	line-height:1.9rem;
	text-align:center;
	font-size:1.167rem;
	color: #1aa97b; 
}
</style>
<c:if test="${empty isWebRes}">
    <c:set var="isWebRes" value="false"/>
</c:if>
<c:if test="${empty fileSize}">
    <c:set var="fileSize" value="10"/>
</c:if>
<c:if test="${empty num}">
    <c:set var="num" value="1"/>
</c:if>
<%-- <c:if test="${not empty name }" > --%>
<!-- production -->
<input type="file" name="file" placeholder="请选择文件" id="fileToUpload_${num }" onchange="fileSelected_${num }();"/>
<div class="progressDiv" id="progressDiv_${num }" style="display: none;">
<div class="process" id="process"><span id="progressBar_${num }" style="width:0%;"></span></div>
<div class="processPercent" id="processPercent_${num }">0%</div>
</div>
<script type="text/javascript">
//var flag = true;
window.onload=function(){
	if(document.getElementById('${startElementId}')!=null){
		document.getElementById('${startElementId}').onclick = function(){
			uploadFile_${num}();
		}
	}
}
if('${empty callback}'=='false'){
	document.getElementById("fileToUpload_${num }").setAttribute("callback","${callback}");
}
if('${empty beforeupload}'=='false'){
	document.getElementById("fileToUpload_${num }").setAttribute("beforeupload","${beforeupload}");
}
function fileSelected_${num}() {
    var file = document.getElementById('fileToUpload_${num}').files[0];
    if (file) {
      <c:if test="${not empty fileType}">
          var ext = getExt(file.name);
          if(ext==null || (",${fileType},").indexOf(","+ext+",")<0){
        	  //flag = false;
        	  clearFileText_${num}();
        	  alert("只支持格式为：${fileType}的文件");
        	  return false;
          }
      </c:if>
      <c:if test="${not empty fileSize}">
	      var fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;
	      var size = ${fileSize};
	      if(fileSize>size){
	    	  //flag = false;
	    	  clearFileText_${num}();
	    	  alert("上传的文件过大，请上传小于${fileSize}M的文件");
	    	  return false;
	      }
	  </c:if>
	  //document.getElementById('progressDiv').style.display = "none";
	  document.getElementById('processPercent_${num}').innerHTML = '0%';
      document.getElementById('progressBar_${num}').style.width = '0%';
	  <c:if test="${empty startElementId}">
	      uploadFile_${num}();
	  </c:if>
      //if (file.size > 1024 * 1024)
      //fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
      //else
      //  fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

      //document.getElementById('fileName').innerHTML = 'Name: ' + file.name;
     // document.getElementById('fileSize').innerHTML = 'Size: ' + fileSize;
      //document.getElementById('fileType').innerHTML = 'Type: ' + file.type;
    }
  }

  function uploadFile_${num}() {
   eval("var beforeupload = "+document.getElementById("fileToUpload_${num }").getAttribute("beforeupload"));
   if(document.getElementById("fileToUpload_${num }").getAttribute("beforeupload")!=null){
	   if(typeof(beforeupload) == 'function'){
		  	if(!beforeupload()){
		  		clearFileText_${num}();
		  		return false;		 
		  	}
	 	 }
   }
	var file = document.getElementById('fileToUpload_${num}').files[0];
	if (file) {
//		if(flag){
			var fd = new FormData();
		    fd.append("file", document.getElementById('fileToUpload_${num}').files[0]);
		    fd.append("relativePath", "${relativePath }");
		    fd.append("isWebRes", ${isWebRes });
		    fd.append("file", document.getElementById('fileToUpload_${num}').files[0]);
		    var xhr = new XMLHttpRequest();
		    <c:if test="${not empty progressBar && progressBar=='true'}">
		    xhr.upload.addEventListener("progress", uploadProgress_${num}, false);
		    </c:if>
		    xhr.addEventListener("load", uploadComplete_${num}, false);
		    xhr.addEventListener("error", uploadFailed, false);
		    xhr.addEventListener("abort", uploadCanceled, false);
		    xhr.open("POST", _WEB_CONTEXT_+"/jy/manage/res/upload");
		    xhr.send(fd);
		    <c:if test="${not empty progressBar && progressBar=='true'}">
		    //开始显示进度条
		    document.getElementById('progressDiv_${num}').style.display = "block";
		    </c:if>
//		}
	}
  }

  function uploadProgress_${num}(evt) {
    if (evt.lengthComputable) {
      var percentComplete = Math.round(evt.loaded * 100 / evt.total);
      document.getElementById('processPercent_${num}').innerHTML = percentComplete.toString() + '%';
      document.getElementById('progressBar_${num}').style.width = percentComplete.toString() + '%';
    }
    else {
     // document.getElementById('progressNumber').innerHTML = 'unable to compute';
    }
  }

  function uploadComplete_${num}(evt) {
    clearFileText_${num}();
    eval("var callback = "+document.getElementById("fileToUpload_${num }").getAttribute("callback"));
    if(document.getElementById("fileToUpload_${num }").getAttribute("callback")!=null){
    	if(typeof(callback) == 'function'){
	 		var obj = eval("("+evt.target.responseText+")");
	 	    callback(obj);
	 	}
    }
	 <c:if test="${not empty progressBar && progressBar=='true'}">
	 //进度条逐渐消失
     var a = setTimeout(function(){ 
		 document.getElementById("progressDiv_${num}").style.display="none";
	 },1500);
	 </c:if>
  }

  function uploadFailed(evt) {
    alert("上传失败");
  }

  function uploadCanceled(evt) {
  }

  //获取文件后缀
  function getExt(fileName){
	  var index = null;
	  while(fileName.indexOf(".")>0){
		  index = fileName.indexOf(".");
		  fileName = fileName.substring(fileName.indexOf(".")+1,fileName.length);
	  }
	  if(index==null){
		  alert("上传的文件格式未知！");
		  //flag = false;
		  return null;
	  }
	  return fileName;
  }
  
  //重置file文本域
  function clearFileText_${num}(){
	  var fileInput = document.getElementById('fileToUpload_${num}');
	  if(fileInput.outerHTML) { //IE,chrome
		  fileInput.outerHTML = fileInput.outerHTML;
	  }else{ // FF
		  fileInput.value = "";
	  }
  }
</script>
<%-- </c:if> --%>

