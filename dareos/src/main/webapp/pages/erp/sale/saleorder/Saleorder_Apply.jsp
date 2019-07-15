<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/erp/sale/saleorder/Saleorder_Apply.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
	<title>变更申请</title>
</head>
<body><!-- 
	<table id="qxBtnTb"  cellSpacing="0" cellPadding="0" border="0" >
		<tr style="height: 40px">
			<td nowrap>
				<button class="img_input">
					<label for="save">
						<img src="${ctx}/styles/${theme}/images/icon/baocun.png"
							class="icon_font">
						<input id="save" type="button" class="btn" value="保存">
					</label>
				</button>
				<button class="img_input">
					<label for="commit">
						<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"
							class="icon_font">
						<input id="commit" type="button" class="btn" value="提交">
					</label>
				</button>
				<button class="img_input">
					<label onclick="closeDialog()">
						<img src="${ctx}/styles/${theme}/images/icon/chexiao.png"
							class="icon_font">
						<input type="button" class="btn" value="返回"/>
					</label>
				</button> 
			</td>
		</tr>
	</table> -->
	<form name="mainForm" id="mainForm">
		<input type="hidden" json="CHANGE_APPLY_ID" id="CHANGE_APPLY_ID" name="CHANGE_APPLY_ID" value="${rst.CHANGE_APPLY_ID }" />
		<input type="hidden" json="SALE_ORDER_ID" id="SALE_ORDER_ID" name="SALE_ORDER_ID" value="${rst.SALE_ORDER_ID}"/>
		<input type="hidden" json="LEDGER_ID" id="LEDGER_ID" name="LEDGER_ID" value="${rst.LEDGER_ID}"/>
		<input type="hidden" json="LEDGER_NAME" id="LEDGER_NAME" name="LEDGER_NAME" value="${rst.LEDGER_NAME}"/>
		<table id="jsontbP" width="100%" border="0" cellpadding="4" cellspacing="4" class="detail">
			<tr>
				<td class="detail2">
					<table width="98%" border="0" cellpadding="0" cellspacing="0" class="detail3">
						<tr>
							<td class="gdtd">
								<div>厂编：</div>
								<input class="gdtd_select_input" type="text" value="${rst.FACTORY_NO}" json="FACTORY_NO"
									name="FACTORY_NO" id="FACTORY_NO" readonly/>
							</td>
							<td class="gdtd">
								<div>销售类型：</div>
								<input class="gdtd_select_input" json="BILL_TYPE" name="BILL_TYPE" id="BILL_TYPE" 
										value="${rst.BILL_TYPE }" readonly/>
							</td>
							<td class="gdtd">
								<div>订货单号：</div>
								<input class="gdtd_select_input" type="text" value="${rst.FROM_BILL_NO}" 
									json="FROM_BILL_NO" name="FROM_BILL_NO" id="FROM_BILL_NO" readonly />
								<input class="gdtd_select_input" type="hidden" value="${rst.SALE_ORDER_NO }"
									json="SALE_ORDER_NO" name="SALE_ORDER_NO" id="SALE_ORDER_NO">
								<input class="gdtd_select_input" type="hidden" value="${rst.ORDER_TRACE_ID }"
									json="ORDER_TRACE_ID" name="ORDER_TRACE_ID" id="ORDER_TRACE_ID">
								<input class="gdtd_select_input" type="hidden" value="${rst.APPLYSTATE }"
									json="APPLYSTATE" name="APPLYSTATE" id="APPLYSTATE">
							</td>
							<td class="gdtd">
								<div>处理类型：</div>
								<input class="gdtd_select_input" json="PROESS_TYPE" name="PROESS_TYPE" id="PROESS_TYPE" 
									<c:choose>
									<c:when test="${IS_DRP_LEDGER eq '1' }">
										<c:if test="${fn:contains(rst.PROESS_TYPE,'现货')==true}">value="现货" </c:if>
										<c:if test="${fn:contains(rst.PROESS_TYPE,'现货')==false}">value="非现货" </c:if>
									</c:when>
									<c:otherwise>value="${rst.PROESS_TYPE}"</c:otherwise>
									</c:choose>
									readonly />
							</td>
						</tr>
						<tr>
							<td  class="gdtd" nowrap>
								<div>预计到货日期：</div>
								<input class="gdtd_select_input edit" type="text" json="PRE_RECV_DATE" id="PRE_RECV_DATE"
									name="PRE_RECV_DATE" placeholder="订单交期后7天" value="${rst.PRE_RECV_DATE}"
									label="预计到货期" readonly />
							</td>
							<td  class="gdtd">
								<div>下单日期：</div>
								<input class="gdtd_select_input" type="text" json="ORDER_DATE" id="ORDER_DATE"
									name="ORDER_DATE" placeholder="提交自动生成" value="${rst.ORDER_DATE}"
									label="下单日期" readonly />
							</td>
							<td class="gdtd">
								<div>订单交期：</div>
								<input class="gdtd_select_input edit" type="text" json="ORDER_DELIVERY_DATE"
									id="ORDER_DELIVERY_DATE" name="ORDER_DELIVERY_DATE" placeholder="订单生效后25天" 
									value="${rst.ORDER_DELIVERY_DATE}" label="订单交期" readonly />
							</td>
						</tr>
						<tr>
							<td class="gdtd" colspan="4">
								<div>变更内容：</div>
								<textarea class="gdtd_textarea" style="width: 96.2%;" 
									json="CHAN_REMARK" name="CHAN_REMARK" id="CHAN_REMARK" inputtype="string"
									maxlength="200" rows="5">${rst.CHAN_REMARK}</textarea>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	
	<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
		<c:if test="${empty rst.APPLYSTATE || '草稿' eq rst.APPLYSTATE }">
		<button class="img_input">
			<label for="save">
				<img src="${ctx}/styles/${theme}/images/icon/baocun.png"
					class="icon_font">
				<input id="save" type="button" class="btn" value="保存">
			</label>
		</button>
		<button class="img_input">
			<label for="commit">
				<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"
					class="icon_font">
				<input id="commit" type="button" class="btn" value="提交">
			</label>
		</button>
		</c:if>
		<button class="img_input">
			<label onclick="closeDialog()">
				<img src="${ctx}/styles/${theme}/images/icon/chexiao.png"
					class="icon_font">
				<input type="button" class="btn" value="关闭"/>
			</label>
		</button> 
	</div>
</body>

