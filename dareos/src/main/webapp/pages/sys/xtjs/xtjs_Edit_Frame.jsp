<!-- 
/**
  *@module 系统管理 
  *@fuc 系统角色编辑框架
  *@version 1.1 
  *@author 唐赟
*/
 -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"/>
	<script type=text/javascript src="${ctx}/pages/sys/xtjs/xtjs_Edit_Frame.js"></script>
	<title>系统角色信息</title>
</head>
<body>
<table width="99.9%" height="98%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<div id="topdiv" style="height:50%;width:100%">
			<!-- 上帧 -->
			<iframe id="topcontent" name="topcontent" src="#"  frameBorder=0 width="100%" height="100%" frameborder="0" scrolling="AUTO"></iframe>
			</div>
			<div class="tablayer tabBackground">
			<table cellSpacing=0 cellPadding=0 border=0 width="100%">
				<tr>
					<td class="label_line" width="8px">&nbsp;</td>
					<!--这里加入需要显示的标签页, 注意序号和标签的宽度-->
					<td id="label" nowrap="nowrap">
						<span id="label_1" class="label_down" style="margin-top:2px;">&nbsp;系统角色明细&nbsp;</span>
						<input type="hidden" id="showLabel" value="label_1"/>
					</td>
					<!--标签页添加完毕-->
					<td class="label_line" align="right" width="150px">
						<input type="button" id="butHidTop" class="button" value="↑" >
						<input type="button" id="butHidBottom" class="button" value="↓">
					</td>
				</tr>
			</table>
			</div>
			<div id="bottomdiv" style="height:45%;width:100%">
			<!-- 下帧 -->
			<iframe id="bottomcontent" name="bottomcontent" src="#"  frameBorder=0 width="100%" height="100%" frameborder="0" scrolling="AUTO"></iframe>
			</div>
		</td>
	</tr>
</table>

<input type="hidden" id="actionType" value="edit"/>
<!-- 模块，页码，排序Id，排序方式，选择记录主键Id -->
<input type="hidden" id="module" value="${module}"/>
<input type="hidden" id="pageNo" value="${pageNo}"/>
<input type="hidden" id="orderId" value="${orderId}"/>
<input type="hidden" id="orderType" value="${orderType}"/>
<input type="hidden" id="selRowId" value="${selRowId}"/>
</body>
 

