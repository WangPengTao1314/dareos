
<!-- 
 *@module 系统管理
 *@func 品牌信息
 *@version 1.1
 *@author  郭利伟
 *  -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>资金信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/base/channcapital/ChannCapital_List.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellpadding="0" cellspacing="10" class="panel">
<tr>
	<td>
		<div class="lst_area">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" style="padding:0;" >
				<tr class="fixedRow">
					<th nowrap align="center" dbname="BILL_NO" width="10%">业务单号</th>
					<th nowrap align="center" dbname="BILL_TYPE" width="5%">业务类型</th>
					<th nowrap align="center" dbname="CHANN_NAME" width="15%">经销商</th>
					<th nowrap align="center" dbname="LEDGER_NAME" width="5%">账套</th>
					<th nowrap align="center" dbname="AMOUNT_TOTAL" width="8%">金额</th>
					<th nowrap align="center" dbname="REBATE_TOTAL" width="8%">返利</th>
					<th nowrap align="center" dbname="CREDIT_TOTAL" width="8%">信用</th>
				</tr>
				<c:forEach items="${list}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" >
						<td align="center">${rst.billNo}&nbsp;</td>
						<td align="center">${rst.billType}&nbsp;</td>
						<td align="center">${rst.channName}&nbsp;</td>
						<td align="center">${rst.ledgerName}&nbsp;</td>
						<td align="center">${rst.amountTotal}&nbsp;</td>
						<td align="center">${rst.rebateTotal}&nbsp;</td>
						<td align="center">${rst.creditTotal}&nbsp;</td>
					</tr>
				</c:forEach>
				<c:if test="${empty list}">
					<tr>
						<td height="25" colspan="13" align="center" class="lst_empty">
							&nbsp;无相关信息&nbsp;
						</td>
					</tr>
				</c:if>
			</table>
		</div>
	</td>
</tr>
</table>
</body>
</html>