<!--  
/**
 * @module 系统管理
 * @func 供应商信息
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
	<title>供应商信息</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css" />
	<script type=text/javascript src="${ctx}/pages/base/provider/provider_Frame.js"></script>
</head>
<BODY>
    <table cellspacing="0" cellpadding="0" width="99.9%" height="98%">
		<tr>
			<td >
				<div id="topdiv" style="height: 95%; width: 100%">
				  <iframe id="topcontent" name="topcontent" src="#"  frameBorder=0 width="100%" height="100%" frameborder="0" scrolling="AUTO"></iframe>
				</div>
				<!-- <div class="tablayer tabBackground" style="height: 20px; width: 100%;">
					<table cellSpacing=0 cellPadding=0 border=0 width="100%">
						<tr>
						   这里加入需要显示的标签页, 注意序号和标签的宽度
							<td id="label" nowrap="nowrap">
								<span id="label_1" class="label_down" style="margin-top: 2px;font-size:13">&nbsp;详细信息&nbsp;</span>
								<input type="hidden" id="showLabel" value="label_1" />
							</td>
							标签页添加完毕
							<td class="label_line" align="right" width="150px" nowrap>
								<input type="button" id="butHidTop" class="button" value="↑" title="Alt+↑">
								<input type="button" id="butHidBottom" class="button" value="↓" title="Alt+↓">
							</td>
						</tr>
					</table>
				</div> -->
				<!-- <div id="bottomdiv" style="height: 42%; width: 100%">
					下帧
					<iframe id="bottomcontent" name="bottomcontent" src="#" frameBorder=0 width="100%" height="100%" frameborder="0" scrolling="AUTO"></iframe>
				</div> -->
			</td>
		</tr>
	</table>
<input type="hidden" id="module" name="module" value="${module}" />
<input type="hidden" id="pageNo" name="pageNo" value="${pageNo}" />
<input type="hidden" id="orderId" name="orderId"  value="${orderId}" />
<input type="hidden" id="orderType"  name="orderType" value="${orderType}" />
<input type="hidden" id="selRowId"  name="selRowId" value="${selRowId}" />
<input type="hidden" id="paramUrl" name="paramUrl" value="${paramUrl}"/>
</body>


