<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//���÷�����ҳ��
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
poCtrl.setMenubar(false);//����ʾ�˵���
poCtrl.setOfficeToolbars(false);//����ʾoffice������
poCtrl.setTitlebar(false);//����ʾ������
poCtrl.setCustomToolbar(false);//����ʾ�Զ���Ĺ�����
poCtrl.setOfficeVendor(OfficeVendorType.AutoSelect);
//��Word�ĵ�
poCtrl.webCreateNew("test",DocumentVersion.Word2003);
poCtrl.setTagId("PageOfficeCtrl1");//���б���
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

