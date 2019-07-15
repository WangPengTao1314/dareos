<!-- 
/**
 * @module 系统管理
 * @func 充值信息
 * @version 1.1
 * @author 郭利伟
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
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<title>账单信息详情</title>
</head>
<body class="bodycss1">	

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail_lst">
				<tr>
					<td width="15%" align="right"class="detail_label">业务单号：</td>
					<td width="35%" class="detail_content">${rst.billNo }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">业务类型：</td>
					<td width="35%" class="detail_content">${rst.billType}&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">金额：</td>
					<td width="35%" class="detail_content">${rst.amountTotal }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">返利：</td>
					<td width="35%" class="detail_content">${rst.rebateTotal}&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">信用：</td>
					<td width="35%" class="detail_content">${rst.creditTotal }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">可用金额：</td>
					<td width="35%" class="detail_content">${rst.amountMoney - rst.freezMoney }&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">可用信用：</td>
					<td width="35%" class="detail_content">${rst.amuntCredit -rst.freezCredit -rst.oweCredit }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">可用返利：</td>
					<td width="35%" class="detail_content">${rst.amountRebate - rst.freezRebate }&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">经销商：</td>
					<td width="35%" class="detail_content">${rst.channName }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">订单组织：</td>
					<td width="35%" class="detail_content">${rst.ledgerName}&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">操作时间：</td>
					<td width="35%" class="detail_content">${rst.optionTime}&nbsp;</td>
					<td width="15%" align="right"class="detail_label"></td>
					<td width="35%" class="detail_content"></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>
