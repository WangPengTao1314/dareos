<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" import="java.awt.*"%>
<%@ taglib uri="/WEB-INF/tlds/runqianReport4.tld" prefix="report"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>
		</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/stylePrint.css">
	</head>
	<body >
    <div style="position:relative">
	<div class="layout" fit="true" id="report1" style="height:100%;width:100%;position:absolute;" align="center">
		<div region="center" border="false" id="reportPrint2"  class="main-table-div" style="overflow:auto;overflow-x:hidden;padding: 0 13px;">
	    	<report:html 
				name = "report1" 
				reportFileName = "${reportFileName}"
				printedRaq = "${reportFileName}"
				params = "${params}" 	
				funcBarLocation = "no" 
				exceptionPage = "/modules/report/reportcomm/reportError.jsp"
				needPageMark="yes"
				needPrint="yes"
				height="-1"
				generateParamForm="no"
				needSaveAsWord="yes" 
				displayNoLinkPageMark="no"
				/>
		</div>
	</div>
	<div style="position:absolute;right: 12px;top: 0px;">
		<a href="#" onclick="doPrint()"><img src="${ctx}/styles/${theme}/images/icon/print.png" class="icon_font"></a> 
	</div>
	</div>
		<script type="text/javascript" src="${ctx}/report/commons/initReport.js"></script>
		<script type="text/javascript" src="${ctx}/report/commons/jquery.jqprint.js"></script>
		<script type="text/javascript">
		 function doPrint()
		 {
			 $("#reportPrint2").jqprint();
		 }
		</script>
	</body>
</html>
