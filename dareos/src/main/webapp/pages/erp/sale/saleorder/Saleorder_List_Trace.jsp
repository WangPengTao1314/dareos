﻿<!--
 * @prjName:喜临门营销平台
 * @fileName:销售订单生命周期
 * @author zzb
 * @time   2013-09-15 10:35:10 
 * @version 1.1
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>销售订单生命周期</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/erp/sale/saleorder/Saleorder_List_Trace.js"></script>
	<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<tr>
	<td height="20">
		<div class="tablayer" >
		</div>
	</td>
</tr>
<tr>
	<td valign="top">
		<div class="lst_area">
			<table id="ordertb" width="100%" border="0" cellpadding="1" cellspacing="1" class="lst">
				<tr class="fixedRow">
                    <th  nowrap="nowrap" align="center" dbname="BILL_NO" >单据编号</th>
                    <th  nowrap="nowrap" align="center" dbname="BILL_TYPE" >单据类型</th>
                    <th  nowrap="nowrap" align="center" dbname="ACTION_NAME" >动作</th>
                    <th  nowrap="nowrap" align="center" dbname="DEAL_PSON_NAME" >处理人</th>
                    <th  nowrap="nowrap" align="center" dbname="DEAL_TIME" >处理时间</th>
                    <th  nowrap="nowrap" align="center" dbname="STATE" >状态</th> 
				</tr>
				<c:forEach items="${rst}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"  onclick="selectThis(this);setEidChecked(document.getElementById('eid${rr}'));">
                     <td nowrap="nowrap" align="center" >${rst.BILL_NO}&nbsp;</td>
                     <td nowrap="nowrap" align="center" >${rst.BILL_TYPE}&nbsp;</td>
                     <td nowrap="nowrap" align="left" >${rst.ACTION_NAME}&nbsp;</td>
                     <td nowrap="nowrap" align="left" >${rst.DEAL_PSON_NAME}&nbsp;</td>
                     <td nowrap="nowrap" align="center" >${rst.DEAL_TIME}&nbsp;</td>
                     <td nowrap="nowrap" align="center" >${rst.STATE}&nbsp;</td>
				    </tr>
				</c:forEach>
				<c:if test="${empty rst}">
					<tr>
						<td height="25" colspan="13" align="center" class="lst_empty">
			                &nbsp;无相关记录&nbsp;
						</td>
					</tr>
				</c:if>
			</table>
		</div>
	</td>
</tr>
</table>
<form id="form1" action="#" method="post" name="listForm">
	<input type="hidden" id="GOODS_ORDER_DTL_IDS" name="GOODS_ORDER_DTL_IDS" value=""/>
</form>
</body>
</html>