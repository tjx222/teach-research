<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
poCtrl.setMenubar(false);//不显示菜单栏
poCtrl.setOfficeToolbars(false);//不显示office工具栏
poCtrl.setTitlebar(false);//不显示标题栏
poCtrl.setCustomToolbar(false);//不显示自定义的工具栏
poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
//打开Word文档
poCtrl.webCreateNew("test",DocumentVersion.Word2003);
poCtrl.setTagId("PageOfficeCtrl1");//此行必需
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title>pageofficetest</title>
<script type="text/javascript">
document.createElement("section");
</script>
</head>
<body>
    <script type="text/javascript">
        function Save() {
            document.getElementById("PageOfficeCtrl1").WebSave();
        }
    </script>
 <div class="head_border"></div>
 <section class="main w12">
    <div class="fr tit2"><span class="arr"></span></div>
    <div style=" width:auto; height:700px;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1">
        </po:PageOfficeCtrl>
    </div>
 </section>
 </body>
</html>

