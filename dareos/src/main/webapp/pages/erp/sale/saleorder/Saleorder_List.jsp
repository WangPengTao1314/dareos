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
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/erp/sale/saleorder/Saleorder_List.js"></script>
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
							<c:if test="${pvg.PVG_EDIT eq 1}">
							<button class="img_input addbtn" id="addBtn" >
								<label for='add'>
								<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
								<input id="add" type="button" class="btn add" value="新增" />
								</label>
							</button>
							<button class="img_input" id="modifyBtn" >
								<label for='modify'>
								<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
								<input id="modify" type="button" class="btn" value="编辑" />
								</label>
							</button>
							<button class="img_input" id="copyBtn" >
								<label for='copy'>
								<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
								<input id="copy" type="button" class="btn" value="复制" />
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_DELETE eq 1}">
							<button class="del_input" id="deleteBtn" >
								<label for='delete'>
								<img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
								<input id="delete" type="button" class="del_btn" value="删除" />
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_EDIT eq 1}">
							<button class="img_input" id="commitBtn" >
								<label for='commit'>
								<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"  class="icon_font">
								<input id="commit" type="button" class="btn" value="提交" />
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_APPLY_FX eq 1 or pvg.PVG_APPLY eq 1}">
							<button class="img_input" id="apply4changeBtn"  >
								<label for='apply4change'>
								<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
								<input id="apply4change" type="button" class="btn" value="申请变更" />
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_TURNDESIGNER eq 1}">
							<button class="img_input" id="turnDesignerBtn" >
								<label for='turnDesigner'>
								<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
								<input id="turnDesigner" type="button" class="btn" value="转设计" />
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_ASSIGN eq 1}">
							<button class="img_input" id="assignDesignerBtn" >
								<label for='assignDesigner'>
								<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
								<input id="assignDesigner" type="button" class="btn" value="分派设计师" />
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_DESIGN eq 1}">
							<button class="img_input" id="designBtn" >
								<label for='design'>
								<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
								<input id="design" type="button" class="btn" value="拆件" />
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_TURNERP eq 1}">
							<button class="img_input" id="turnERPBtn" >
								<label for='turnERP'>
								<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
								<input id="turnERP" type="button" class="btn" value="转ERP处理" />
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.xtyhid eq 'admin'}">
							<button class="img_input" id="ERPbackBtn">
								<label for='ERPback'>
								<img src="${ctx}/styles/${theme}/images/icon/tuihui.png"  class="icon_font">
								<input id="ERPback" type="button" class="btn" value="ERP退回" />
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_CREFHZL eq 1}">
							<button class="img_input" id="scfhzlBtn">
								<label for='scfhzl'>
									<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
									<input id="scfhzl" type="button" class="btn" value="生成发货指令" >
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_CONFIRM_FX eq 1}">
							<button class="img_input" id="confirmBtn">
								<label for='confirm'>
									<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
									<input id="confirm" type="button" class="btn" value="收货确认" >
								</label>
							</button>
							</c:if>
							<c:if test="${pvg.PVG_BWS eq 1 or pvg.PVG_BWS_FX eq 1}">
							<button class="img_input" id="flowTrackBtn" >
								<label for='flowTrack'>
								<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
								<input id="flowTrack" type="button" class="btn" value="进度跟踪" />
								</label>
							</button>
							<button class="img_input" id="queryBtn" >
								<label for='query'>
									<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
									<input id="query" type="button" class="btn" value="查询">
								</label>
							</button>
							</c:if>
						</td>
						<td nowrap style=" word-spacing: 8px; " align="right">
							<c:if test="${pvg.PVG_IMPORT eq 1}">
							<button class="img_input" id="importBtn">
								<label for='import'>
									<img src="${ctx}/styles/${theme}/images/icon/daoru.png"  class="icon_font">
									<input id="import" type="button" class="btn" value="导入">
								</label>
							</button>
							</c:if>
							<%-- <c:if test="${pvg.PVG_BWS eq 1 or pvg.PVG_BWS_FX eq 1}">
							<button class="img_input" style="">
								<label for='export'>
									<img src="${ctx}/styles/${theme}/images/icon/daochu.png"  class="icon_font">
									<input id="export" type="button" class="btn" value="导出明细">
								</label>
							</button>
							</c:if> --%>
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
						<th width="22px"></th>
						<th width="22px"></th>
	                    <th  nowrap="nowrap" align="center" dbname="FACTORY_NO" style="width: 13%;">厂编</th>
	                    <th  nowrap="nowrap" align="center" dbname="FROM_BILL_NO" style="width: 10%;">订货单号</th>
	                    <th  nowrap="nowrap" align="center" dbname="BILL_TYPE" >销售类型</th>
	                    <th  nowrap="nowrap" align="center" dbname="PROESS_TYPE" >处理类型</th>
	                    <th  nowrap="nowrap" align="center" dbname="URGENCY_TYPE" >紧急程度</th>
	                    <th  nowrap="nowrap" align="center" dbname="CHANN_NAME" style="width: 12%;">经销商</th>
	                    <th  nowrap="nowrap" align="center" dbname="CUST_NAME" >客户名称</th>
	                    <th  nowrap="nowrap" align="center" dbname="CUST_ADDR" style="width: 12%;">客户地址</th>
	                    <th  nowrap="nowrap" align="center" dbname="ORDER_DATE" style="width: 160px;">下单日期</th>
	                    <%-- <th  nowrap="nowrap" align="center" dbname="ORDER_DELIVERY_DATE" style="width: 80px;">交货日期</th> --%>
	                    <th  nowrap="nowrap" align="center" dbname="TOTAL_AMOUNT" style="width: 60px;" class="SHOWPRICE" >订单金额</th>
	                    <th  nowrap="nowrap" align="center" dbname="STATE" style="width: 6%;">订单状态</th>
					</tr>
					<c:forEach items="${page.result}" var="rst" varStatus="row">
						<c:set var="r" value="${row.count % 2}"></c:set>
						<c:set var="rr" value="${row.count}"></c:set>
						<%-- <tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"  onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));"> --%>
						<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));" ondblclick="parent.gotoBottomPage()" onmouseout="mout(this)"  id="tr${rr}" >
							<td align='center' >
								<div class="radio_add"> 
									<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.SALE_ORDER_ID}"/>
									<label for="radio_add"></label>
								</div>
							</td>
							<td align='center'>
								<c:choose>
								<c:when test="${ not empty rst.APPLYSTATE }">审</c:when>
								<c:when test="${ '1' eq rst.CHANGE_FLAG }">更</c:when>
								<c:otherwise></c:otherwise>
								</c:choose>
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
							<td nowrap="nowrap" align="center" title="${rst.URGENCY_TYPE}">${rst.URGENCY_TYPE}</td>
							<td nowrap="nowrap" align="center" title="${rst.CHANN_NAME}">${rst.CHANN_NAME}</td>
							<td nowrap="nowrap" align="center" title="${rst.CUST_NAME}">${rst.CUST_NAME}</td>
							<td nowrap="nowrap" align="center" title="${rst.CUST_ADDR}">${rst.CUST_ADDR}</td>
							<td nowrap="nowrap" align="center" title="${rst.ORDER_DATE}"><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${rst.ORDER_DATE }" /></td>
							<%-- <td nowrap="nowrap" align="center" title="${rst.ORDER_DELIVERY_DATE}"><fmt:formatDate pattern="yyyy-MM-dd" value="${rst.ORDER_DELIVERY_DATE }" /></td> --%>
							<td nowrap="nowrap" align="center" title="${rst.TOTAL_AMOUNT}" class="SHOWPRICE" >${rst.TOTAL_AMOUNT}</td>
							<td nowrap="nowrap" align="center" title="${rst.STATE}">${rst.STATE}</td>
							<input type="hidden" name="RETURN_SHOW_FLAG" value="${rst.RETURN_SHOW_FLAG }">
							<input type="hidden" id="billType${rst.SALE_ORDER_ID}" value="${rst.BILL_TYPE}"/>
							<input type="hidden" id="state${rst.SALE_ORDER_ID}" value="${rst.STATE}"/>
							<input type="hidden" id="FACTORY_NO${rst.SALE_ORDER_ID}" value="${rst.FACTORY_NO}"/>
							<input type="hidden" id="LEDGER_ID${rst.SALE_ORDER_ID}" value="${rst.LEDGER_ID}"/>
							<input type="hidden" id="ORDER_TRACE_ID${rst.SALE_ORDER_ID}" value="${rst.ORDER_TRACE_ID}"/>
							<input type="hidden" id="FROM_BILL_NO${rst.SALE_ORDER_ID}" value="${rst.FROM_BILL_NO}"/>
							<input type="hidden" id="DESIGNER${rst.SALE_ORDER_ID}" value="${rst.DESIGNER}"/>
							<input type="hidden" id="CHANGE_FLAG${rst.SALE_ORDER_ID}" value="${rst.CHANGE_FLAG}"/>
							<input type="hidden" id="applystate${rst.SALE_ORDER_ID}" value="${rst.APPLYSTATE}"/>
						</tr>
					</c:forEach>
					<c:if test="${empty page.result}">
						<tr>
							<td height="25" colspan="12" align="center" class="lst_empty">&nbsp;无相关记录&nbsp;</td>
							<td class="SHOWPRICE" ></td>
						</tr>
					</c:if>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td height="12px" >
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="lst_toolbar">
				<tr>
					<td>
						<form id="pageForm" action="#" method="post" name="listForm">
						<input type="hidden" id="pageSize" name="pageSize" value='${page.pageSize}'/>
							<input type="hidden" id="pageNo" name="pageNo" value='${page.pageNo}'/>
							<input type="hidden" id="orderType" name="orderType" value='${orderType}'/>
							<input type="hidden" id="orderId" name="orderId" value='${orderId}'/>
							<input type="hidden" id="selRowId" name="selRowId" value="${selRowId}">
							<input type="hidden" id="xtyhid" name="xtyhid" value="${pvg.xtyhid}">
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
<form id="queryForm" method="post" action="#" autocomplete="off">
	<input type="hidden" name="search" value="search"/>
<input type="hidden" id="initSel" name="initSel" value=" STATE in('启用','停用')"/>
<input type="hidden" id="selOrderChannParam" name="selOrderChannParam" value=" STATE in('启用','停用')  and CHANN_TYPE !='总部' "/>
<input type="hidden" id="selGoodOrderParam" name="selGoodOrderParam" value=" STATE='已处理' and DEL_FLAG=0 and ORDER_CHANN_ID in ${params.CHANNS_CHARG} "/>
<input type="hidden" id="selSaleOrderParam" name="selSaleOrderParam" value=" BILL_TYPE='标准' and DEL_FLAG=0 and ORDER_CHANN_ID in ${params.CHANNS_CHARG} "/>

<table border="0" cellpadding="0" cellspacing="0" class="detail cx_table" >
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="5" cellspacing="0" style="padding: 1%">
				<tr>
					<td class="gdtd_tb cx_demo">
						<div>厂编:</div>
						<input class="gdtd_select_input cx_demo" type="text" id="FACTORY_NO" name="FACTORY_NO" value="${params.FACTORY_NO}"/>
					</td>
					<td class="gdtd_tb cx_demo">
						<div>经销商:</div>
						<input class="gdtd_select_input cx_demo" type="text" id="CHANN_NAME" name="CHANN_NAME" value="${params.CHANN_NAME}"
						<c:if test="${IS_DRP_LEDGER eq '0' }">
						 seltarget="imgSelChann" 
						</c:if>
						/>
						<c:if test="${IS_DRP_LEDGER eq '0' }">
						<img name="imgSelChann" align="absmiddle" class="select_gif"
							src="${ctx }/styles/newTheme/images/plus/select.gif" onClick="selChann();"/>
						</c:if>
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
						<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DATE_BEGIN" name="ORDER_DATE_BEGIN" onclick="SelectDate();" size="10" value="${params.ORDER_DATE_BEGIN}" style="width: 36.5%">
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_BEGIN);">
						-&nbsp;<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DATE_END" name="ORDER_DATE_END" onclick="SelectDate();" size="10" value="${params.ORDER_DATE_END}" style="width: 36.5%" >
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_END);">
					</td>
					<td class="gdtd_tb cx_demo">
						<div>交货日期:</div>
						<input class="gdtd_select_input cx_demo"  type="text" id="ORDER_DELIVERY_DATE_BEGIN" name="ORDER_DELIVERY_DATE_BEGIN" onclick="SelectDate();" size="10" value="${params.ORDER_DELIVERY_DATE_BEGIN}" style="width: 36.5%" >
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_BEGIN);">
						-&nbsp;<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DELIVERY_DATE_END" name="ORDER_DELIVERY_DATE_END" onclick="SelectDate();" size="10" value="${params.ORDER_DELIVERY_DATE_END}" style="width: 36.5%" >
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DELIVERY_DATE_END);">
					</td>
					<td class="gdtd_tb cx_demo">
						<div>订单状态:</div>
						<select id="STATE" name="STATE" class="gdtd_select_input cx_demo"></select>
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
					<td class="gdtd_tb cx_demo">
						<div>紧急程度:</div>
						<select id="URGENCY_TYPE" name="URGENCY_TYPE" class="gdtd_select_input cx_demo"></select>
					</td>
					<td class="gdtd_tb cx_demo">
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
<%@ include file="/pages/common/uploadfile/importExecl.jsp"%>
<script type="text/javascript">
    SelDictShow("STATE","BS_215","${params.STATE}","");
	var initSel = "";
	/* 
	if('0' == '${IS_DRP_LEDGER }'){//总部
	} else {
		initSel = " SJGL in ('G', 'SF')";
	} */
	SelDictShow("BILL_TYPE","BS_190","${params.BILL_TYPE}",initSel);
    SelDictShow("PROESS_TYPE","BS_191","${params.PROESS_TYPE}","");
    SelDictShow("URGENCY_TYPE","BS_218","${params.URGENCY_TYPE}","");
</script>
</html>
