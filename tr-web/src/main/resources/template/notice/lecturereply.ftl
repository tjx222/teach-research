“${tname}”回复了您 
<a onclick="joinIt('${id}');" href="javascript:void(0);"  style="color:#014efd;">${topic}</a>
  课题的听课记录，您可以点击标题进行查看，也可以点击“听课记录”进行查看和回复。
 <script type="text/javascript">
    function joinIt(id){
       $.getJSON(_WEB_CONTEXT_+"/jy/lecturerecords/isDelete",{"id":id},function(data){
			if(data.code == 0){
				alert("该听课记录已被撰写人删除！");
			}else{
           	 window.location.href = '${ctx}/jy/lecturerecords/list';
            }
        });
   }
</script>