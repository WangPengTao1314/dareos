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
	<title>账单信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/base/monthstatement/MonthStatement_List.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellpadding="0" cellspacing="10" class="panel">
<tr>
	<td height="20">
		<div class="tablayer button_cls">
			<form id="queryForm" method="post" action="#" name="queryForm">
			<table id="qxBtnTb" cellSpacing=0 cellPadding=0 border=0 width="100%" style="border:0px">
				<tr>
				<td nowrap width="15%">
					年份：<select name=year id="year" json="year" class="gdtd_select_input cx_demo" style="width: 50%"></select>
				</td>
				<td nowrap width="15%">
					月份：<select name="month" id="month" json="month" class="gdtd_select_input cx_demo" style="width: 50%"></select>
				</td>
				<td width="5%">
					<button class="img_input" type="button">
						<label for='queryTemp'>
						<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
						<input id="queryTemp" type="button" class="btn" value="查询" />	
						</label>
					</button>
				</td>
				<td width="50%">
					<button class="img_input" type="button">
						<label for='monthStat'>
						<img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
						<input id="monthStat" type="button" class="btn" value="生成月结日期" />	
						</label>
					</button>
				</td>
				</tr>
			</table>
			</form>
		</div>
	</td>
</tr>
<tr>
	<td>
		<div class="lst_area">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" style="padding:0;" >
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="YEAR" width="15%">年份</th>
					<th nowrap align="center" dbname="MONTH" width="15%">月份</th>
					<th nowrap align="center" dbname="BEG_DATE" width="15%">期初日期</th>
					<th nowrap align="center" dbname="END_DATE" width="15%">期末日期</th>
					<th nowrap align="center" dbname="IS_MONTH_ACCT" width="10%">是否月结</th>
					<th nowrap align="center"  width="10%">操作</th>
				</tr>
				<c:forEach items="${list}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"  onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%" align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.monthAcctId}"/>
								<label for="radio_add"></label>
							</div>
						</td>
						<td align="center">${rst.year}&nbsp;</td>
						<td align="center">${rst.month}&nbsp;</td>
						<td align="center">${rst.begDate}&nbsp;</td>
						<td align="center">${rst.endDate}&nbsp;</td>
						<td align="center">
							<c:if test="${rst.isMonthAcct eq 1}">
								是
							</c:if>
							<c:if test="${rst.isMonthAcct eq 0}">
								否
							</c:if>
							&nbsp;
						</td>
						<td align="center">
							<input type="button" value="月结" onclick="toAcct('${rst.year}','${rst.month}')"
								<c:if test="${rst.isMonthAcct eq 1}">
								disabled="disabled"
							</c:if>
							 />
						&nbsp;</td>
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
<tr>
	<td height="12px" align="center">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0" class="lst_toolbar">
			<tr>
				<td>
					<form id="pageForm" action="#" method="post" name="listForm">
					<input type="hidden" id="pageSize" name="pageSize" value='${page.pageSize}'/>
						<input type="hidden" id="pageNo" name="pageNo" value='${page.pageNo}'/>
						<input type="hidden" id="orderType" name="orderType" value='${orderType}'/>
						<input type="hidden" id="orderId" name="orderId" value='${orderId}'/>
						<input type="hidden" id="selRowId" name="selRowId" value="${selRowId}">&nbsp;
						<input type="hidden" id="paramUrl" name="paramUrl" value="${paramCover.coveredUrl}">
						<span id="hidinpDiv" name="hidinpDiv"></span>
						${paramCover.unCoveredForbidInputs } 
					</form>
				</td>
				<td align="left">
					 ${page.footerHtml}${page.toolbarHtml}
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
<div id="querydiv" class="query_div">
</div>
<script language="JavaScript">  
	SelDictShow("year","89","${params.year }","");
	SelDictShow("month","168","${params.month }","");
	
</script>
</body>
</html>