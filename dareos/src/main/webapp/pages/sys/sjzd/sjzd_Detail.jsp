<!-- 
/**
  *@module 系统管理
  *@fuc 数据字典详细信息 
  *@version 1.1
  *@author 张羽
*/

 -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<title>数据字典详情</title>
	</head>
	<body class="bodycss1">	
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
		<tr>
			<td class="detail2">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail_lst">
					<tr>
						<td width="15%" align="right" class="detail_label">数据字典编号：</td>
						<td width="35%" class="detail_content">${rst.SJZDBH}&nbsp;</td>
						<td width="15%" align="right" class="detail_label">数据字典名称：</td>
						<td width="35%" class="detail_content">${rst.SJZDMC}&nbsp;</td>
					</tr>
					<tr>
						<td width="15%" align="right"class="detail_label">创建人：</td>
						<td width="35%" class="detail_content">${rst.CRENAME}&nbsp;</td>
						<td width="15%" align="right" class="detail_label">创建时间：</td>
						<td width="35%" class="detail_content">${rst.CRETIME }&nbsp;</td>
					</tr>
					<tr>
						<td width="15%" align="right" class="detail_label" >其他说明：</td>
						<td width="35%" class="detail_content" >${rst.REMARK}&nbsp;</td>
						<td width="15%" align="right" class="detail_label" >状态：</td>
						<td colspan="4" width="35%" class="detail_content" >${rst.STATE}&nbsp;</td>						
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th nowrap align="center">数据项名称</th>
					<th nowrap align="center">数据项值</th>
					<th nowrap align="center">数据项代码</th>
					<th nowrap align="center">数据归类</th>
					<th nowrap align="center">其他说明</th>
				</tr>
				<c:forEach items="${list}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"  onclick="selectThis(this);setEidChecked(document.getElementById('eid${rr}'));setBtStates();">
						<td nowrap align="left">${rst.SJXMC}&nbsp;</td>
						<td nowrap align="left">${rst.SJXZ}&nbsp;</td>
						<td nowrap align="left">${rst.KEYCODE}&nbsp;</td>
						<td nowrap align="left">${rst.SJGL}&nbsp;</td>
						<td nowrap align="left">${rst.REMARK}&nbsp;</td>
					</tr>
				</c:forEach>
				<c:if test="${empty rst}">
					<tr>
						<td height="25" colspan="13" align="center" class="lst_empty">&nbsp;无相关信息&nbsp;</td>
					</tr>
				</c:if>
			</table>
	</body>
</html>
