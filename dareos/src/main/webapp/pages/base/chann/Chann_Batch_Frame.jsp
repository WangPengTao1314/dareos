
<!--  
/**
 * @module 系统管理
 * @func 批量维护区域经理
 * @version 1.1
 * @author zcx
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
	<title>批量维护区域经理</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css" />
	<script type=text/javascript src="${ctx}/pages/base/chann/Chann_Batch_Frame.js"></script>
</head>
<BODY>
	<table cellspacing="0" cellpadding="0" width="99.9%" height="100%">
		<tr>
		    <td width="7px">
			 <table id="colResize" width="7" height="100%" style="cursor: col-resize" border="0" cellpadding="0" cellspacing="0"  background="${ctx}/styles/${theme}/images/left/sep3pt.gif">
			  <tr>
			    <td>&nbsp;</td>
			  </tr>
			</table>
		   </td>
			<td valign="top" height="95%">
				<div id="topdiv" style="height: 40%; width: 100%">
					<iframe id="topcontent" name="topcontent" style="margin-left: 0px" src="#" frameBorder=0 width="100%" height="100%" vspace="0px" hspace="0px" scrolling="AUTO"></iframe>
				</div>
				<div class="tablayer tabBackground" style="height: 20px; width: 100%;">
					<table cellSpacing=0 cellPadding=0 border=0 width="100%">
						<tr>
							<td class="label_line" width="8px">
								&nbsp;
							</td>
							<!--这里加入需要显示的标签页, 注意序号和标签的宽度-->
							<td id="label" nowrap="nowrap">
								<span id="label_1" class="label_down" style="margin-top: 2px;">&nbsp;渠道信息&nbsp;</span>
								<input type="hidden" id="showLabel" value="label_1" />
							</td>
							<!--标签页添加完毕-->
							<td class="label_line" align="right" width="55px">
								<input type="button" id="butHidTop" class="button" value="↑" title="Alt+↑">
								<input type="button" id="butHidBottom" class="button" value="↓" title="Alt+↓">
							</td>
						</tr>
					</table>
				</div>
				<div id="bottomdiv" style="height: 55%; width: 100%">
					<!-- 下帧 -->
					<iframe id="bottomcontent" name="bottomcontent" src="#"
						frameBorder=0 width="100%" height="100%" frameborder="0"
						scrolling="AUTO"></iframe>
				</div>
			</td>
		</tr>
	</table>
	<input type="hidden" id="actionType" value="list"/>
	<!-- 模块，页码，排序Id，排序方式，选择记录主键Id -->
	<input type="hidden" id="module" value="${module}" />
	<input type="hidden" id="pageNo" value="${pageNo}" />
	<input type="hidden" id="orderId" value="${orderId}" />
	<input type="hidden" id="orderType" value="${orderType}" />
	<input type="hidden" id="selRowId" value="${selRowId}" />
	<input type="hidden" id="paramUrl" value="${paramUrl}"/>
	<input type="hidden" id="deleteIds" value=""/>
	
</body>


