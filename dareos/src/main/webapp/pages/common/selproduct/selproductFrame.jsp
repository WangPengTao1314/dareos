<!-- 
 *@module 分销业务
 *@func 人员信息维护
 *@version 1.1
 *@author lyg
 *  -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title></title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/styles/${theme}/css/style.css">
<script language="JavaScript"
	src="${ctx}/pages/common/select/selCommJs.js"></script>
<script type="text/javascript"
	src="${ctx}/pages/common/selproduct/selproductFrame.js"></script>
<style type="text/css">
.tdTop {
	width: 80px;
}

.jsontb_tb  td {
	font-weight: 400;
	font-size: 16px;
}
.left_menu {
	width: 15%;
	height: 100%;
	border: 1px solid #ccc;
	display: inline-block;
	float: left;
}

input[type="radio"] {
	position: static !important;
	/* clip: rect(0, 0, 0, 0); */
}

.active {
	margin-right: 20px;
}

a:active {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
	color: #000
}

a:visited {
	text-decoration: none;
}

a:link {
	text-decoration: none;
}
</style>
</head>
<body class="bodycss1">
	<table cellspacing="0" cellpadding="0" width="99.9%" height="100%">
		<tr>
			<td id="leftiframe" width="200px" style="border-right: 1px solid #cccccc;">
				<iframe id="leftcontent" name="leftcontent"
						frameBorder="0" vspace="0px" hspace="0px" 
						width="100%" height="100%" scrolling="no"></iframe>
			</td>
			<td valign="top" height="95%">
				<iframe id="topcontent" name="topcontent" style="" 
						frameBorder="0" vspace="0px" hspace="0px" 
						width="100%" height="100%" scrolling="no"></iframe>
			</td>
		</tr>
	</table>
	<input type="hidden" value="${ledger_id }" id="ledger_id"/>
</body>
<script>
	var hg = $("#topcontent").height()
</script>
</html>