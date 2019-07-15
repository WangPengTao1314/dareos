<!--
 * @prjName:喜临门营销平台
 * @fileName:分销 -订货订单维护
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@page import="com.centit.commons.model.Consts"%>
 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>浏览</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/drp/sale/goodsorderhd/Goodsorderhd_Dealwithjunior.js"></script>
	<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
	  		#mycredit_show{
			margin: 0px auto; 
			width:500px;
			border: 1px;
			z-index:99;
			position:absolute;
			background-color: white;
			border:1px solid black;
			height:140px;
			left:230px;
			top:50px;
			text-align:center;
			display: block;
		}
		.text_underline{
			border-bottom-width:1px;
			border-bottom-style:double;
			border-top-width:0px;
			border-left-width:0px;
			border-right-width:0px;
			outline:medium;
			width:150px;
		}
		.wenben{
			width: 80px;
			text-align: right;
		}
		.neirong{
			width:150px;
			text-align:left;
		}
	</style>
</head>
<body >
	<table width="99.5%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
		<tr>
			<td height="20">
				<div class="tablayer">
					<table id="qxBtnTb" cellpadding="0"  cellspacing="6" border="0" width="100%" >
						<tr>
							<td nowrap style=" word-spacing: 5px;">
								<%-- 
								<c:if test="${pvg.PVG_BACK_SUB eq 1}">
								<button class="img_input" >
									<label for='back'>
									<img src="${ctx}/styles/${theme}/images/icon/tuihui.png"  class="icon_font">
									<input id="back" type="button" class="btn" value="退回" />
									</label>
								</button>
								</c:if>
								 --%>
								<c:if test="${pvg.PVG_QUOTE_SUB eq 1}">
								<button class="img_input" >
									<label for='pricing'>
									<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
									<input id="pricing" type="button" class="btn" value="报价" />
									</label>
								</button>
								</c:if>
								<c:if test="${pvg.PVG_EDIT_SUB eq 1}">
								<button class="img_input" >
									<label for='turnToHQ'>
									<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
									<input id="turnToHQ" type="button" class="btn" value="转总部订货" />
									</label>
								</button>
								</c:if>
								<c:if test="${pvg.PVG_BWS_SUB eq 1}">
								<button class="img_input" >
									<label for='flowTrack'>
									<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
									<input id="flowTrack" type="button" class="btn" value="进度跟踪" />
									</label>
								</button>
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
			<td valign="top">
				<div class="lst_area" >
					<table id="ordertb" width="100%" border="0" cellpadding="1" cellspacing="0" class="lst ellipsis">
						<tr class="fixedRow">
							<th width="3%"></th>
							<th nowrap="nowrap" align="center" dbname="GOODS_ORDER_NO" width="12%">订货编号</th>
							<th nowrap="nowrap" align="center" dbname="BILL_TYPE" >销售类型</th>
							<th nowrap="nowrap" align="center" dbname="CHANN_NAME" width="14%">经销商</th>
							<th nowrap="nowrap" align="center" dbname="CUST_NAME" >客户名称</th>
							<th nowrap="nowrap" align="center" dbname="CUST_ADDR" style="width: 14%;">客户地址</th>
							<th nowrap="nowrap" align="center" dbname="ORDER_DATE" >下单日期</th>
							<th nowrap="nowrap" align="center" dbname="ORDER_RECV_DATE" >交货日期</th>
							<th nowrap="nowrap" align="center" dbname="TOTAL_AMOUNT" class="SHOWPRICE" >订货总额</th>
							<th nowrap="nowrap" align="center" dbname="STATE" width="10%">订单状态</th>
							<th nowrap="nowrap" align="center" dbname="CRE_NAME" width="10%">负责人</th>
						</tr>
						<c:forEach items="${page.result}" var="rst" varStatus="row">
							<c:set var="r" value="${row.count % 2}"></c:set>
							<c:set var="rr" value="${row.count}"></c:set>
							<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"
								onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));"
								ondblclick="parent.gotoBottomPage()">
								<td align='center'>
								 <div class="radio_add">
										<input type="radio" style="valign: middle" name="eid" id="eid${rr}"
											value="${rst.GOODS_ORDER_ID}" />
										<label for="radio_add"></label>
									</div>
								</td>
								<td nowrap="nowrap" align="center">${rst.GOODS_ORDER_NO}</td>
								<td nowrap="nowrap" align="center">${rst.BILL_TYPE}</td>
								<td nowrap="nowrap" align="center" title="${rst.CHANN_NAME}">${rst.CHANN_NAME}</td>
								<td nowrap="nowrap" align="center">${rst.CUST_NAME}</td>
								<td nowrap="nowrap" align="center" title="${rst.CUST_ADDR}">${rst.CUST_ADDR}</td>
								<td nowrap="nowrap" align="center">${rst.ORDER_DATE}</td>
								<td nowrap="nowrap" align="center">${rst.ORDER_RECV_DATE}</td>
								<td nowrap="nowrap" align="center" class="SHOWPRICE" name="TOTL_AMOUNT_${rst.GOODS_ORDER_ID}" >
									${rst.TOTAL_AMOUNT}
								</td>
								<td nowrap="nowrap" align="center">
									${rst.STATE}
									<input id="state${rst.GOODS_ORDER_ID}" type="hidden" value="${rst.STATE}" />
									<input id="ORDER_TRACE_ID${rst.GOODS_ORDER_ID}" type="hidden" value="${rst.ORDER_TRACE_ID}" />
									<input id="type${rst.GOODS_ORDER_ID}" type="hidden" value="${rst.BILL_TYPE}" />
									<input type="hidden" id="AREA_ID_${rst.GOODS_ORDER_ID}" value="${rst.AREA_ID}" />
									<input type="hidden" id="GOODS_ORDER_NO_${rst.GOODS_ORDER_ID}"
										value="${rst.GOODS_ORDER_NO}" />
									<input type="hidden" id="BILL_TYPE${rst.GOODS_ORDER_ID}" label="单据类型"
										value="${rst.BILL_TYPE}" />
									<input type="hidden" id="TOTAL_AMOUNT${rst.GOODS_ORDER_ID}" label="订货总额"
										value="${rst.TOTAL_AMOUNT}" />
										<input type="hidden" name="RETURN_SHOW_FLAG" value="${rst.RETURN_SHOW_FLAG }">
								</td>
								<td nowrap="nowrap" align="center">${rst.CRE_NAME}</td>
							</tr>
						</c:forEach>
						<c:if test="${empty page.result}">
							<tr>
								<td height="25" colspan="10" align="center" class="lst_empty">&nbsp;无相关记录&nbsp;</td>
								<td class="SHOWPRICE" ></td>
							</tr>
						</c:if>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td height="12px">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="lst_toolbar">
					<tr>
						<td>
							<form id="pageForm" action="#" method="post" name="listForm">
							<input type="hidden" id="pageSize" name="pageSize" value='${page.pageSize}'/>
								<input type="hidden" id="pageNo" name="pageNo" value='${page.pageNo}'/>
								<input type="hidden" id="orderType" name="orderType" value='${orderType}'/>
								<input type="hidden" id="orderId" name="orderId" value='${orderId}'/>
								<input type="hidden" id="selRowId" name="selRowId" value="${selRowId}">
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
<form id="queryForm" method="post" action="#">
	<input type="hidden" name="search" value="search"/>
	<input type="hidden" id="selParam" name="selParam" value=" STATE in('启用','停用')"/>
	<input type="hidden" id="CHANN_ID" name="CHANN_ID" value="${CHANN_ID}"/>
<%-- <input type="hidden" id="selOrderChannParam" name="selOrderChannParam" value=" STATE in('启用','停用') and CHANN_ID='${ZTXXID}' or AREA_SER_ID='${ZTXXID}' "/> --%>
<%-- <input type="hidden" id="selSendChannParam" name="selSendChannParam" value=" STATE in('启用','停用') and CHANN_ID in('${BASE_CHANN_ID}','${AREA_SER_ID}') "/> --%>
 <input type="hidden" name=selectPrd value="STATE in ('启用','停用') and DEL_FLAG='0' and FINAL_NODE_FLAG=1 ">
 <input type="hidden" id="flag" name="flag" value=""/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table" >
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="5" cellspacing="0" style="padding:1%">
				<tr>
					<td class="gdtd_tb cx_demo">
						<div>订货单号：</div>
						<input id="GOODS_ORDER_NO" name="GOODS_ORDER_NO"  value="${params.GOODS_ORDER_NO}" class="gdtd_select_input cx_demo" />
					</td>
					<td class="gdtd_tb cx_demo">
						<div>销售类型:</div>
						<select id="BILL_TYPE" name="BILL_TYPE" class="gdtd_select_input cx_demo"></select>
					</td>
					<td class="gdtd_tb cx_demo">
						<div>经销商:</div>
						<input type="hidden" name="ORDER_CHANN_ID" id="ORDER_CHANN_ID" value="" class="gdtd_select_input cx_demo"/>
						<input type="text" id="ORDER_CHANN_NAME" name="ORDER_CHANN_NAME"   value="${params.ORDER_CHANN_NAME}" class="gdtd_select_input cx_demo"/>
					</td>
					<td class="gdtd_tb cx_demo">
						<div>状态:</div>
						<select id="STATE" name="STATE" class="gdtd_select_input cx_demo"></select>
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb cx_demo">
						<div>下单日期:</div>
						<input type="text" id="ORDER_DATE_beg" name="ORDER_DATE_beg" onclick="SelectDate();" size="10" value="${params.ORDER_DATE_beg}" class="gdtd_select_input cx_demo" style="width: 36.5%" >
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_beg);">
						-&nbsp;<input type="text" id="ORDER_DATE_end" name="ORDER_DATE_end" onclick="SelectDate();" size="10" value="${params.ORDER_DATE_end}"  class="gdtd_select_input cx_demo" style="width: 36.5%" >
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_end);">
					</td>
					<td>
						<div>交货日期:</div>
						<input type="text" id="ORDER_RECV_DATE_beg" name="ORDER_RECV_DATE_beg" onclick="SelectDate();" size="10" value="${params.ORDER_RECV_DATE_beg}" class="gdtd_select_input cx_demo" style="width: 36.5%" >
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_RECV_DATE_beg);">
						-&nbsp;<input type="text" id="ORDER_RECV_DATE_end" name="ORDER_RECV_DATE_end" onclick="SelectDate();" size="10" value="${params.ORDER_RECV_DATE_end}" class="gdtd_select_input cx_demo" style="width: 36.5%" >
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_RECV_DATE_end);">
					</td>
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn" value="确定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" >&nbsp;&nbsp;
						<input id="q_reset" type="button" class="btn cx_btn" value="重置" 
							onclick="javascript:$('#queryForm :input').not(':button, :submit, :reset, :hidden').val('');">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
</body>
<script type="text/javascript">
	SelDictShow("STATE", "BS_188", "${params.STATE}", "");
	SelDictShow("BILL_TYPE","BS_93","${params.BILL_TYPE}","");
</script>
</html>
