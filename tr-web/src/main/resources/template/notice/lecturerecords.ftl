“${spaceName}_${tingkeren}”听了您的  
 &nbsp;<a onclick="joinIt('${id}');" href="javascript:void(0);"  style="color:#014efd;">${topic}</a>
  &nbsp;课题，您可以点击标题进行查看，也可以点击“我的备课本”进行查看和回复。
<script type="text/javascript">
    function joinIt(id){
       $.getJSON(_WEB_CONTEXT_+"/jy/lecturerecords/isDelete",{"id":id},function(data){
			if(data.code == 0){
				alert("该听课记录已被撰写人删除！");
			}else{
           	 window.location.href = '${ctx}/jy/lecturerecords/lecturereply?teachingpeopleId=${teachingpeopleId}&lecturepeopleId=${lecturepeopleId}&id=${id}';
            }
        });
   }
</script>