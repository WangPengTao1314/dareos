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
	<title>账户账单列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/base/channamount/ChannAmount_List.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellpadding="0" cellspacing="10" class="panel">
<tr>
	<td height="20">
		<div class="tablayer button_cls">
			<table id="qxBtnTb" cellSpacing=0 cellPadding=0 border=0 width="100%" style="border:0px">
				<tr>
				<td nowrap>
				    <c:if test="${pvg.PVG_BWS eq 1 || pvg.PVG_BWS_AUDIT eq 1}">
				    <button class="img_input" >
						<label for='query'>
						<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
						<input id="query" type="button" class="btn" value="查询" />	
						</label>
					</button>
				    </c:if>
				</td>
				</tr>
			</table>
		</div>
	</td>
</tr>
<tr>
	<td>
		<div class="lst_area">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" style="padding:0;" >
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="CHANN_NAME" width="15%">经销商</th>
					<th nowrap align="center" dbname="LEDGER_NAME_ABBR" width="15%">订单组织</th>
<!-- 					<th nowrap align="center" dbname="BILL_NO" width="15%">业务单号</th> -->
					<th nowrap align="center" dbname="YEAR" width="15%">年份</th>
					<th nowrap align="center" dbname="MONTH" width="15%">月份</th>
					<th nowrap align="center" dbname="BEG_AMOUNT"  width="10%">期初金额</th>
					<th nowrap align="center" dbname="RECHARGE_AMOUNT"  width="10%">充值金额</th>
					<th nowrap align="center" dbname="USE_AMOUNT"  width="10%">使用金额</th>
					<th nowrap align="center" dbname="END_AMOUNT" width="10%">期末金额</th>
					<th nowrap align="center" dbname="BEG_REBATE_AMOUNT"  width="10%">期初返利金额</th>
					<th nowrap align="center" dbname="USE_REBATE"  width="10%">使用返利</th>
					<th nowrap align="center" dbname="RECHARGE_REBATE"  width="10%">充值返利</th>
					<th nowrap align="center" dbname="END_REBATE_AMOUNT" width="15%">期末返利金额</th>
					<th nowrap align="center" dbname="BEG_DATE" width="10%">期初日期</th>
					<th nowrap align="center" dbname="END_DATE" width="10%">期末日期</th>
					<th nowrap align="center" dbname="UPD_TIME" width="10%">结算时间</th>
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()" onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%" align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.monthAcctNoteId}"/>
								<label for="radio_add"></label>
							</div>
						</td>
						<td align="center">${rst.channName}&nbsp;</td>
						<td align="center">${rst.ledgerNameAbbr}&nbsp;</td>
<%-- 						<td align="center">${rst.billNo}&nbsp;</td> --%>
						<td align="center">${rst.year}&nbsp;</td>
						<td align="center">${rst.month}&nbsp;</td>
						<td align="center">${rst.begAmount}&nbsp;</td>
						<td align="center">${rst.rechargeAmount}&nbsp;</td>
						<td align="center">${rst.useAmount}&nbsp;</td>
						<td align="center">${rst.endAmount }&nbsp;</td>
						<td align="center">${rst.begRebateAmount }&nbsp;</td>
						<td align="center">${rst.useRebate }&nbsp;</td>
						<td align="center">${rst.rechargeRebate }&nbsp;</td>
						<td align="center">${rst.endRebateAmount }&nbsp;</td>
						<td align="center">${rst.begDate }&nbsp;</td>
						<td align="center">${rst.endDate }&nbsp;</td>
						<td align="center">${rst.updTime }&nbsp;</td>
					</tr>
				</c:forEach>
				<c:if test="${empty page.result}">
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
<form id="queryForm" method="post" action="#" name="queryForm">
<input type="hidden" name="selectParams" id="selectParams" value="">
<input type="hidden" name="XTYHID" id="XTYHID" value="${XTYHID }">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo">
						<div>经销商名称：</div>
						 <input name="search" value="1" type="hidden" class="gdtd_select_input cx_demo"/>
								<input  type="hidden" value="${params.channId}" json="channId"
									name="channId" id="channId" inputtype="string" />
								<input name="channName"  type="text" readonly="readonly" autocheck="true" inputtype="string" value="${params.channName }" class="gdtd_select_input cx_demo"/>
								<c:if test="${IS_DRP_LEDGER eq 0 }">
									<img name="imgTab" align="absmiddle" class="magnifier select_gif"
									src="${ctx }/styles/newTheme/images/plus/select.gif" onClick="selChann();"/>
								</c:if>
					</td>
					<td class="gdtd_tb cx_demo">
					<div>业务单号：</div>
						<input name="billNo"  type="text" autocheck="true" inputtype="string" value="${params.billNo }" class="gdtd_select_input cx_demo"/>
					</td>
		      		<td class="gdtd_tb cx_demo">
		      			<div>帐套：</div>
						<select id="ledgerId" name="ledgerId"  class="gdtd_select_input cx_demo"></select>
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb cx_demo"><div>流水类型：</div>
						<select id="billType" name="billType" class="gdtd_select_input cx_demo"></select>
					</td>
					<td class="gdtd_tb cx_demo">
						<div>操作日期从：</div>
		      		
		            	<input type="text" json="optionTime1" id="optionTime1" name="optionTime1" autocheck="true" inputtype="string" value="${params.optionTime1}"
							label="日期"  onclick="SelectDate();" READONLY  class="gdtd_select_input cx_demo" />&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
							onclick="SelectDate(optionTime1);"/>
		      		</td>
		      		<td class="gdtd_tb cx_demo">
		      			<div>操作日期到：</div>
		      		
		            	<input type="text" json="optionTime2" id="optionTime2" name="optionTime2" autocheck="true" inputtype="string" value="${params.optionTime2}"
							label="日期" onclick="SelectDate();" READONLY class="gdtd_select_input cx_demo" />&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
							onclick="SelectDate(optionTime2);"/>
		      		</td>	
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn" value="确定" />&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" />&nbsp;&nbsp;
						<input  type="reset" class="btn cx_btn" value="重置" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
<script language="JavaScript">  
	SelDictShow("state","BS_211","${params.state }","");	
	SelDictShow("billType","BS_213","${params.billType }","");	
	if('${IS_DRP_LEDGER}' == "1" ){
		SelDictShow("ledgerId","BS_189","${params.ledgerId}"," CHANN_ID = '${CHANN_ID}' ");
	}else{
		SelDictShow("ledgerId","BS_212","${params.ledgerId }"," XTYHID = '${XTYHID}'");	
	}
	
</script>
</body>
</html>