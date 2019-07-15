
<!--  
/**
 * @module 系统管理
 * @func 货品信息
 * @version 1.1
 * @author 刘曰刚
 */ 
-->

<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>货品信息</title>
	<link rel="stylesheet" type="text/css"
		href="${ctx}/styles/${theme}/css/style.css" />
	<script type=text/javascript src="${ctx}/pages/base/pdtmenu/Pdtmenu_Frame.js">
</script>
</head>
<BODY>
	<table cellspacing="0" cellpadding="0" width="99.9%" height="100%">
		<tr>
			<td id="leftiframe" width="260px">
				<iframe id="leftcontent" name="leftcontent" style="margin-left: 0px"
					src="#" frameBorder=0 width="100%" height="100%" vspace="0px"
					hspace="0px" scrolling="AUTO"></iframe>
			</td>
			<td width="7px">
				<table id="colResize" width="7" height="100%"
					style="cursor: col-resize" border="0" cellpadding="0"
					cellspacing="0"
					background="${ctx}/styles/${theme}/images/left/sep3pt.gif">
					<tr>
						<td>
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
			<td valign="top" height="95%">
				<div id="topdiv" style="height: 100%; width: 100%">
					<iframe id="topcontent" name="topcontent" style="margin-left: 0px"
						src="#" frameBorder=0 width="100%" height="100%" vspace="0px"
						hspace="0px" scrolling="no"></iframe>
				</div>
				
			</td>
		</tr>
	</table>
	<!-- 模块，页码，排序Id，排序方式，选择记录主键Id -->
	<input type="hidden" id="module" value="${module}" />
	<input type="hidden" id="pageNo" value="${pageNo}" />
	<input type="hidden" id="orderId" value="${orderId}" />
	<input type="hidden" id="orderType" value="${orderType}" />
	<input type="hidden" id="selRowId" value="${selRowId}" />
	<input type="hidden" id="paramUrl" value="${paramUrl}" />
	<input type="hidden" id="selDtlId" value=""/>
</body>


