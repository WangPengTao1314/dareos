﻿﻿<!--  
/**
 * @module 系统管理
 * @func 货品计量单位
 * @version 1.1
 * @author 刘曰刚
 */
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/base/product/Prd_Unit_Mx_Edit.js"></script>
	<title>货品计量单位编辑</title>
</head>
<body class="bodycss1">
<!-- 删除标记:在该页面有删除操作后，返回时刷新页面。否则直接返回上一级，不刷新 -->
<input id="delFlag" type="hidden" value="false"/>
<table width="99.8%" height="95%" border="0" cellSpacing=0 cellPadding=0>
<tr>
	<td height="20px" valign="top">
      <table id="qxBtnTb"  cellpadding="0" cellspacing="10" border=0>
		<tr>
		   <td nowrap>
			   <input id="add" type="button" class="btn" value="新增"  >
			   <input id="delete" type="button" class="btn" value="删除"  >
			   <input id="save" type="button" class="btn" value="保存"  >
			   <input type="button" class="btn" value="返回"  onclick="gobacknew();">
		</td>
		</tr>
	</table>
	</td>
</tr>
<tr>
<td>
	<div class="lst_area">
	    <form>
		<input type="hidden" id="PRD_ID" name="PRD_ID">
		<input type="hidden" name=selectParams value="STATE='启用'">
		<table id="jsontb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
			<tr class="fixedRow">
              <th nowrap align="center"><input type="checkbox" style="height:12px;valign:middle" id="allChecked"></th>
              <th nowrap align="center">计量单位编号</th>
              <th nowrap align="center">计量单位名称</th>
              <th nowrap align="center">单位类型</th>
              <th nowrap align="center">换算关系</th>
            </tr>
		</table>
		<input type="hidden" id="chistate" value="${chistate}" />
		</form>
	</div>
</td>
</tr>
</table>
</body>
<script type="text/javascript">
	<c:forEach items="${rst}" var="rst" varStatus="row">
	var arrs = [
				'${rst.PRD_UNIT_ID}',
				'${rst.PRD_ID}',
				'${rst.MEAS_UNIT_ID}',
              	'${rst.MEAS_UNIT_NO}',
              	'${rst.MEAS_UNIT_NAME}',
             	'${rst.MEAS_UNIT_TYPE}',
            	'${rst.RATIO}'
           		];
    addRow(arrs);
	</c:forEach>
</script>
</html>
