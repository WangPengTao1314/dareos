<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>浏览</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/erp/sale/saleorder/Saleorder_ChangeList.js"></script>
	<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
	<table width="99.5%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
		<tr>
			<td height="20">
				<div class="tablayer" >
					<table id="qxBtnTb" cellpadding="0" cellspacing="6" border="0" width="100%" >
						<tr>
							<td nowrap style=" word-spacing: 5px;">
								<c:if test="${pvg.PVG_EDIT_CHANGE eq 1}">
								<button class="img_input" id="applyauditBtn" >
									<label for='applyaudit'>
									<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
									<input id="applyaudit" type="button" class="btn" value="审核" />
									</label>
								</button>
								<button class="img_input" id="modifyBtn" style="display: none;">
									<label for='modify'>
									<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
									<input id="modify" type="button" class="btn" value="变更" />
									</label>
								</button>
								<%-- <button class="img_input" id="commitBtn" >
									<label for='commit'>
									<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
									<input id="commit" type="button" class="btn" value="提交" />
									</label>
								</button> --%>
								</c:if>
								<c:if test="${pvg.PVG_QUOTE_CONFIRM eq 1}">
								<button class="img_input" id="confirmquoteBtn" >
									<label for='confirmquote'>
									<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"  class="icon_font">
									<input id="confirmquote" type="button" class="btn" value="确认报价" />
									</label>
								</button>
								</c:if>
								<c:if test="${pvg.PVG_AUDIT_CHANGE eq 1}">
								<button class="img_input" id="auditBtn" >
									<label for='audit'>
									<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
									<input id="audit" type="button" class="btn" value="审图" />
									</label>
								</button>
								</c:if>
								<c:if test="${pvg.PVG_QUOTE_CHANGE eq 1}">
								<button class="img_input" id="quoteBtn" >
									<label for='quote'>
									<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
									<input id="quote" type="button" class="btn" value="报价" />
									</label>
								</button>
								</c:if>
								<c:if test="${pvg.PVG_CONFIRM_CHANGE eq 1}">
								<button class="img_input" id="confirmBtn" >
									<label for='confirm'>
									<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"  class="icon_font">
									<input id="confirm" type="button" class="btn" value="确认" />
									</label>
								</button>
								</c:if>
								<c:if test="${pvg.PVG_BWS eq 1 or pvg.PVG_BWS_CHANGE eq 1}">
								<button class="img_input" >
									<label for='flowTrack'>
									<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
									<input id="flowTrack" type="button" class="btn" value="进度跟踪" />
									</label>
								</button>
								<button class="img_input" >
									<label for='query'>
										<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
										<input id="query" type="button" class="btn" value="查询">
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
						<th width="3%"></th>
						<th  nowrap="nowrap" align="center" dbname="FACTORY_NO" width="13%">厂编</th>
						<th  nowrap="nowrap" align="center" dbname="FROM_BILL_NO" width="12%">订货单号</th>
						<th  nowrap="nowrap" align="center" dbname="BILL_TYPE" >销售类型</th>
						<th  nowrap="nowrap" align="center" dbname="PROESS_TYPE" >处理类型</th>
						<th  nowrap="nowrap" align="center" dbname="CHANN_NAME" width="12%">经销商</th>
						<th  nowrap="nowrap" align="center" dbname="CUST_NAME" >客户名称</th>
						<th  nowrap="nowrap" align="center" dbname="CUST_ADDR" style="width: 12%;">客户地址</th>
						<th  nowrap="nowrap" align="center" dbname="ORDER_DATE" >下单日期</th>
						<th  nowrap="nowrap" align="center" dbname="ORDER_DELIVERY_DATE" >交货日期</th>
						<th  nowrap="nowrap" align="center" dbname="TOTAL_AMOUNT" class="SHOWPRICE" >订单金额</th>
						<th  nowrap="nowrap" align="center" dbname="STATE" >订单状态</th>
						<th  nowrap="nowrap" align="center" dbname="APPLYSTATE" >申请单状态</th>
						<th  nowrap="nowrap" align="center" dbname="APPLY_AUDIT_NAME" width="5%">审核人</th>
					</tr>
					<c:forEach items="${page.result}" var="rst" varStatus="row">
						<c:set var="r" value="${row.count % 2}"></c:set>
						<c:set var="rr" value="${row.count}"></c:set>
						<%-- <tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"  onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));"> --%>
						<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));" ondblclick="parent.gotoBottomPage()" onmouseout="mout(this)"  id="tr${rr}" >
							<td width="1%" align='center' >
								<div class="radio_add"> 
									<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.CHANGE_APPLY_ID}"/>
									<label for="radio_add"></label>
								</div>
							</td>
							<td>
							</td>
							<td nowrap="nowrap" align="center" title="${rst.FACTORY_NO}">${rst.FACTORY_NO}</td>
							<td nowrap="nowrap" align="center" title="${rst.FROM_BILL_NO}">${rst.FROM_BILL_NO}</td>
							<td nowrap="nowrap" align="center" title="${rst.BILL_TYPE}">${rst.BILL_TYPE}</td>
							<td nowrap="nowrap" align="center">
								<c:choose>
								<c:when test="${IS_DRP_LEDGER eq '1' }">
									<c:if test="${fn:contains(rst.PROESS_TYPE,'现货')==true}">现货</c:if>
									<c:if test="${fn:contains(rst.PROESS_TYPE,'现货')==false}">非现货</c:if>
								</c:when>
								<c:otherwise>${rst.PROESS_TYPE}</c:otherwise>
								</c:choose>
							</td>
							<td nowrap="nowrap" align="center" title="${rst.CHANN_NAME}">${rst.CHANN_NAME}</td>
							<td nowrap="nowrap" align="center" title="${rst.CUST_NAME}">${rst.CUST_NAME}</td>
							<td nowrap="nowrap" align="center" title="${rst.CUST_ADDR}">${rst.CUST_ADDR}</td>
							<td nowrap="nowrap" align="center" title="${rst.ORDER_DATE}">${rst.ORDER_DATE }</td>
							<td nowrap="nowrap" align="center" title="${rst.ORDER_DELIVERY_DATE}">${rst.ORDER_DELIVERY_DATE }</td>
							<td nowrap="nowrap" align="center" title="${rst.TOTAL_AMOUNT}" class="SHOWPRICE" >${rst.TOTAL_AMOUNT}</td>
							<td nowrap="nowrap" align="center" title="${rst.STATE}">${rst.STATE}</td>
							<td nowrap="nowrap" align="center" title="${rst.APPLYSTATE}">${rst.APPLYSTATE}</td>
							<td nowrap="nowrap" align="center" title="${rst.APPLY_AUDIT_NAME}">${rst.APPLY_AUDIT_NAME}</td>
							<input type="hidden" id="state${rst.CHANGE_APPLY_ID}" value="${rst.STATE}"/>
							<input type="hidden" id="applystate${rst.CHANGE_APPLY_ID}" value="${rst.APPLYSTATE}"/>
							<input type="hidden" id="SALE_ORDER_ID${rst.CHANGE_APPLY_ID}" value="${rst.SALE_ORDER_ID}"/>
							<input type="hidden" id="SALE_ORDER_NO${rst.CHANGE_APPLY_ID}" value="${rst.SALE_ORDER_NO}"/>
							<input type="hidden" id="ORDER_TRACE_ID${rst.CHANGE_APPLY_ID}" value="${rst.ORDER_TRACE_ID}"/>
						</tr>
					</c:forEach>
					<c:if test="${empty page.result}">
						<tr>
							<td height="25" colspan="14" align="center" class="lst_empty">&nbsp;无相关记录&nbsp;</td>
							<td class="SHOWPRICE" ></td>
						</tr>
					</c:if>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td height="12px">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0" class="lst_toolbar">
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
		<input type="hidden" id="selAREAParam" name="selAREAParam" value=" STATE in('启用','停用')"/>
		<input type="hidden" id="selOrderChannParam" name="selOrderChannParam" value=" STATE in('启用','停用')  and CHANN_TYPE !='总部' "/>
		<input type="hidden" id="selGoodOrderParam" name="selGoodOrderParam" value=" STATE='已处理' and DEL_FLAG=0 and ORDER_CHANN_ID in ${params.CHANNS_CHARG} "/>
		<input type="hidden" id="selSaleOrderParam" name="selSaleOrderParam" value=" BILL_TYPE='标准' and DEL_FLAG=0 and ORDER_CHANN_ID in ${params.CHANNS_CHARG} "/>
		
		<table border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
			<tr>
				<td class="detail2">
					<table width="100%" border="0" cellpadding="5" cellspacing="0" <%-- class="detail3" --%> style="padding: 1%">
						<tr>
							<td class="gdtd_tb cx_demo">
								<div>厂编:</div>
								<input class="gdtd_select_input cx_demo" type="text" id="FACTORY_NO" name="FACTORY_NO" value="${params.FACTORY_NO}"/>
							</td>
							<td class="gdtd_tb cx_demo">
								<div>经销商:</div>
								<input class="gdtd_select_input cx_demo" type="text" id="CHANN_NAME" name="CHANN_NAME" value="${params.CHANN_NAME}"/>
							</td>
							<td class="gdtd_tb cx_demo">
								<div>客户名称:</div>
								<input class="gdtd_select_input cx_demo" type="text" id="CUST_NAME" name="CUST_NAME" value="${params.CUST_NAME}"/>
							</td>
							<td class="gdtd_tb cx_demo">
								<div>客户地址:</div>
								<input class="gdtd_select_input cx_demo" type="text" id="CUST_ADDR" name="CUST_ADDR" value="${params.CUST_ADDR}"/>
							</td>
						</tr>
						<tr>
							<td class="gdtd_tb cx_demo">
								<div>订货单号：</div>
								<input id="GOODS_ORDER_NO" name="GOODS_ORDER_NO"  value="${params.GOODS_ORDER_NO}" class="gdtd_select_input cx_demo" />
							</td>
							<td class="gdtd_tb cx_demo">
								<div>下单日期:</div>
								<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DATE_BEGIN" name="ORDER_DATE_BEGIN" onclick="SelectDate();" size="10" value="${params.ORDER_DATE_BEGIN}" style="width: 36.5%" >
								<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_BEGIN);">
								-&nbsp;<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DATE_END" name="ORDER_DATE_END" onclick="SelectDate();" size="10" value="${params.ORDER_DATE_END}" style="width: 36.5%" >
								<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_END);">
							</td>
							<td class="gdtd_tb cx_demo">
								<div>交货日期:</div>
								<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DELIVERY_DATE_BEGIN" name="ORDER_DELIVERY_DATE_BEGIN" onclick="SelectDate();" size="10" value="${params.ORDER_DELIVERY_DATE_BEGIN}" style="width: 36.5%" >
								<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DELIVERY_DATE_BEGIN);">
								-&nbsp;<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DELIVERY_DATE_END" name="ORDER_DELIVERY_DATE_END" onclick="SelectDate();" size="10" value="${params.ORDER_DELIVERY_DATE_END}" style="width: 36.5%" >
								<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DELIVERY_DATE_END);">
							</td>
							<td class="gdtd_tb cx_demo">
								<div>订单状态:</div>
								<select id="DDSTATE" name="DDSTATE" class="gdtd_select_input cx_demo"></select>
							</td>
						</tr>
						<tr>
							<td class="gdtd_tb cx_demo">
								<div>销售类型:</div>
								<select id="BILL_TYPE" name="BILL_TYPE" class="gdtd_select_input cx_demo"></select>
							</td>
							<c:choose>
							<c:when test="${IS_DRP_LEDGER eq '0' }">
								<td class="gdtd_tb cx_demo">
									<div>处理类型:</div>
									<select id="PROESS_TYPE" name="PROESS_TYPE" class="gdtd_select_input cx_demo"></select>
								</td>
							</c:when>
							<c:otherwise>
							</c:otherwise>
							</c:choose>
							<td class="gdtd_tb cx_demo" style="display: none;">
								<div>申请状态:</div>
								<select id="STATE" name="STATE" class="gdtd_select_input cx_demo"></select>
							</td>
						</tr>
						<tr>
							<td colspan="10" align="center" valign="middle">
								<input id="q_search" type="button" class="btn cx_btn" value="确定" >&nbsp;&nbsp;
								<input id="q_close" type="button" class="btn cx_btn" value="关闭"  >&nbsp;&nbsp;
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
    SelDictShow("DDSTATE","BS_186","${params.DDSTATE}","");
    SelDictShow("BILL_TYPE","BS_190","${params.BILL_TYPE}"," SJGL != 'SF'");
    SelDictShow("PROESS_TYPE","BS_191","${params.PROESS_TYPE}","");
</script>
</html>
