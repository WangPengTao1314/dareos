<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/jslibs4tree.jsp"%>
<html>
<head>
<title>系统用户授权</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"/>
</head>
<body bgcolor="#FFFFFF" text="#000000" style="padding:2%">
<input type="hidden" name="cmdFlag" value="insertXTYHQXSelf">
<input type="hidden" name="isSubmit" value="F">
<input type="hidden" name="XTYHID" value="${XTYHID}"><%--
<br>
  <table  width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
      <th colspan="4">系统用户授权</th>
    </tr>
  </table>


<br>
--%>

<table width="100%" height="10%" border="0" cellspacing="0" cellpadding="0">
  <tr>
  	${qxtab}
  </tr>
</table>

<iframe src="${ctx}/pages/sys/xtyh/message.jsp?value=1" name="bottomcontent" id="bottomcontent" width="100%" height="83%" frameborder="0"></iframe>

</body>

<script type="text/javascript">
var oldLableObject = null;
function showit(myLable,file,lmax) {
	var obj = document.all[myLable];
	try{
		if(oldLableObject==null){
			oldLableObject = document.all("BlueLabel0");
		}
		oldLableObject.className="label_down label_line";
	}catch(e){
		//
	}
	if (file != document.all.bottomcontent.src) {
		document.all.bottomcontent.src = file;
	}
	obj.className="label_up";
	oldLableObject = obj;
}
</script>


</html>
