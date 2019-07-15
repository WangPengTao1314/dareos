<!--  
/**
  *@module 系统管理
  *@fuc 收货地址信息
*/
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>收货地址信息</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"/>
    <script type="text/javascript" src="${ctx}/pages/base/chann/Chann_List_Addr.js"></script>
</head>
<BODY>
<table width="99.9%" height="100%" border="0" cellSpacing=0 cellPadding=0>
<tr id="btntr">
	<td height="20px" valign="top">
		<div class="tablayer" >
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%" style="border:0px">
				<tr>
				   <td nowrap>
				    <c:if test="${pvg.PVG_EDIT eq 1 }">
			   		<input id="edit" type="button" class="btn" value="编辑" >
			   		</c:if>
			   		<c:if test="${pvg.PVG_DELETE eq 1 }">
			   		<input id="delete" type="button" class="btn" value="删除">
			   		 <c:if test="${pvg.PVG_EDIT eq 1 }">
			   		<input id="start" type="button" class="btn" value="启用" >								
					<input id="stop" type="button" class="btn" value="停用" >
			   		</c:if>
			   		</c:if>
				</td>
				</tr>
			</table>
		</div>
	</td>
</tr>
<tr>
	<td>
		<div class="lst_area" style="margin-left:3px;">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th nowrap align="center"><input type="checkbox" style="height:12px;valign:middle" id="allChecked"></th>
					<th nowrap align="center">收货地址编号</th>
					<th nowrap align="center">详细地址</th>
					<th nowrap align="center">联系人</th>
					<th nowrap align="center">联系电话</th>
					<th nowrap align="center">运输方式</th>
					<th nowrap align="center">状态</th>
				</tr>
				<c:forEach items="${rst}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"  onclick="selectThis(this);setEidChecked(document.getElementById('eid${rr}'));setBtStates();">
						<td nowrap align='center' >
							<input type="checkbox" name="eid" style="height:12px;valign:middle" id="eid${rr}" value="${rst.DELIVER_ADDR_ID}" onclick="setEidChecked(this);">
						</td>
						<td nowrap align="left">${rst.DELIVER_ADDR_NO}&nbsp;</td>
						<td nowrap align="left">${rst.DELIVER_DTL_ADDR}&nbsp;</td>
						<td nowrap align="left">${rst.PERSON_CON}&nbsp;</td>
						<td nowrap align="left">${rst.TEL}&nbsp;</td>
						<td nowrap align="left">${rst.TRANSPORT}&nbsp;</td>
						<td nowrap align="center" id="state${rst.DELIVER_ADDR_ID}">${rst.STATE}</td>
					</tr>
				</c:forEach>
				<c:if test="${empty rst}">
					<tr>
						<td height="25" colspan="13" align="center" class="lst_empty">&nbsp;无相关信息&nbsp;</td>
					</tr>
				</c:if>
			</table>
		</div>
	</td>
</tr>
</table>
<form id="form1" action="#" method="post" name="listForm">
	<input type="hidden" id="DELIVER_ADDR_IDS" name="DELIVER_ADDR_IDS" value=""/>
</form>
</body>
 

