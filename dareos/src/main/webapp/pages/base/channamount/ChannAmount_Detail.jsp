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
					<td width="15%" align="right"class="detail_label">经销商：</td>
					<td width="35%" class="detail_content">${rst.channName }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">订单组织：</td>
					<td width="35%" class="detail_content">${rst.ledgerNameAbbr }&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">期初金额：</td>
					<td width="35%" class="detail_content">${rst.begAmount }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">期末金额：</td>
					<td width="35%" class="detail_content">${rst.endAmount}&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">期初返利金额：</td>
					<td width="35%" class="detail_content">${rst.begRebateAmount}&nbsp;</td>
					<td width="15%" align="right"class="detail_label">期末返利金额：</td>
					<td width="35%" class="detail_content">${rst.endRebateAmount }</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">期初冻结金额：</td>
					<td width="35%" class="detail_content">${rst.begFreezAmount}&nbsp;</td>
					<td width="15%" align="right"class="detail_label">期末冻结金额：</td>
					<td width="35%" class="detail_content">${rst.endFreezAmount }</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">期初冻结信用：</td>
					<td width="35%" class="detail_content">${rst.begFreezCredit}&nbsp;</td>
					<td width="15%" align="right"class="detail_label">期末冻结信用：</td>
					<td width="35%" class="detail_content">${rst.endFreezCredit }</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">扣减金额：</td>
					<td width="35%" class="detail_content">${rst.useAmount}&nbsp;</td>
					<td width="15%" align="right"class="detail_label">冻结金额：</td>
					<td width="35%" class="detail_content">${rst.freezAmount }</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">冻结信用：</td>
					<td width="35%" class="detail_content">${rst.freezCredit}&nbsp;</td>
					<td width="15%" align="right"class="detail_label">冻结返利：</td>
					<td width="35%" class="detail_content">${rst.freezRebate }</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">充值金额：</td>
					<td width="35%" class="detail_content">${rst.rechargeAmount}&nbsp;</td>
					<td width="15%" align="right"class="detail_label">充值返利：</td>
					<td width="35%" class="detail_content">${rst.rechargeRebate }</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">年份：</td>
					<td width="35%" class="detail_content">${rst.year }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">月份：</td>
					<td width="35%" class="detail_content">${rst.month}&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">期初日期：</td>
					<td width="35%" class="detail_content">${rst.begDate }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">期末日期：</td>
					<td width="35%" class="detail_content">${rst.endDate}&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">结算日：</td>
					<td width="35%" class="detail_content">${rst.updName }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">结算时间：</td>
					<td width="35%" class="detail_content">${rst.updTime }&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</body>
</html>
