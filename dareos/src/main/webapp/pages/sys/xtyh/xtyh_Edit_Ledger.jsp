<!--  
/**
  *@module 系统管理
  *@fuc 送货地址信息
  *@version 1.1
  *@author 张忠斌
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
    <script type="text/javascript" src="${ctx}/pages/sys/xtyh/xtyh_Edit_Ledger.js"></script>
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<title>帐套分管信息</title>
</head>
<body>
<table width="99.8%" height="95%" border="0" cellSpacing=0 cellPadding=0>
	<tr id="btntr">
		<td height="20px" valign="top">
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0>
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
		<table id="jsontb" width="100%"border="0" cellpadding="0" cellspacing="0" class="lst">
			<input type="hidden" name="con" id = "con" value="  sjzt = '2' and STATE='启用' "/>
			<tr class="fixedRow">
				<th nowrap align="center" style="width:5%">
				<input type="checkbox"  style="height:12px;valign:middle;" id="allChecked"></th>
				<th nowrap align="center" style="width:10%">分管帐套</th>
				<th nowrap align="center" style="width:10%">简称</th>
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
	var arrs = ['${rst.XTYH_LEDGER_ID}',
	            '${rst.LEDGER_ID}',
		        '${rst.LEDGER_NAME}',
		        '${rst.LEDGER_NAME_ABBR}'
				];
	addRow(arrs);
	</c:forEach>
</script>
 

