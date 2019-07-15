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
	<script type="text/javascript" src="${ctx}/pages/erp/sale/saleorder/Saleorder_Assign.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
	<title>设计师分派</title>
</head>
<body><%-- 
	<table id="qxBtnTb"  cellSpacing=0 cellPadding=0 border=0>
		<tr style="height: 40px">
			<td nowrap>
				<button class="img_input">
					<label for="save">
						<img src="${ctx}/styles/${theme}/images/icon/baocun.png"
							class="icon_font">
						<input id="save" type="button" class="btn" value="确定">
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
	</table> --%>
	<form id="mainDiv">
		<input type="hidden" json="DESIGNER_ID" id="DESIGNER_ID" name="DESIGNER_ID" value="${entry.DESIGNER_ID }" />
		<input type="hidden" json="SALE_ORDER_ID" id="SALE_ORDER_ID" name="SALE_ORDER_ID" value="${entry.SALE_ORDER_ID}"/>
		<input type="hidden" json="SALE_ORDER_NO" id="SALE_ORDER_NO" name="SALE_ORDER_NO" value="${entry.SALE_ORDER_NO}"/>
		<input type="hidden" json="DESIGNER" id="DESIGNER" name="DESIGNER" value="${entry.DESIGNER}"/>
		<input type="hidden" json="DESIGNER_NAME" id="DESIGNER_NAME" name="DESIGNER_NAME" value="${entry.DESIGNER_NAME}"/>
		<input type="hidden" json="auditStatus" name="auditStatus" id="auditStatus" value>
		<c:set var="assignList" value="${entry.assignList}"></c:set>
		<c:set var="dateList" value="${entry.dateList}"></c:set>
			<table id="jsontb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th  nowrap="nowrap" align="center"  >分派</th>
					<th  nowrap="nowrap" align="center"  >设计员</th>
					<th  nowrap="nowrap" align="center"  >已分配未完成订单</th>
					<c:forEach items="${dateList }" var="date" varStatus="row">
					<th  nowrap="nowrap" align="center"  >${date }</th>
					</c:forEach>
				</tr>
				<c:forEach items="${assignList }" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onclick="selectThis(this);setSelOperateEx($('#yhid${rr}'));" id="tr${rr}" >
						<td width="1%"></td>
						<td width="1%" align='center' >
							<div class="radio_add"> 
								<input type="radio" style="height:12px;valign:middle" name="yhid" id="yhid${rr}" value="${rst.XTYHID}" 
									designerName = "${rst.XM }"/>
								<label for="radio_add"></label>
							</div>
						</td>
						<td nowrap="nowrap" align="center">${rst.XM}&nbsp;</td>
						<td nowrap="nowrap" align="center">${rst.TOTAL_UNFIN}&nbsp;/&nbsp;${rst.TOTAL}</td>
						<td nowrap="nowrap" align="center"  >${rst.DAY0UNFIN}&nbsp;/&nbsp;${rst.DAY0TTL}</td>
						<td nowrap="nowrap" align="center"  >${rst.DAY1UNFIN}&nbsp;/&nbsp;${rst.DAY1TTL}</td>
						<td nowrap="nowrap" align="center"  >${rst.DAY2UNFIN}&nbsp;/&nbsp;${rst.DAY2TTL}</td>
					</tr>
				</c:forEach>
			</table>
	</form>
	
	<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;left: 0;word-spacing: 8px;">
			<button class="img_input">
				<label for="tmpsave">
					<img src="${ctx}/styles/${theme}/images/icon/baocun.png"
						class="icon_font">
					<input id="tmpsave" type="button" class="btn" value="暂存" status="Z">
				</label>
			</button>
			<button class="img_input">
				<label for="save">
					<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"
						class="icon_font">
					<input id="save" type="button" class="btn" value="确定" status="T">
				</label>
			</button>
			<button class="img_input">
				<label onclick="closeDialog()">
					<img src="${ctx}/styles/${theme}/images/icon/chexiao.png"
						class="icon_font">
					<input type="button" class="btn" value="关闭"/>
				</label>
			</button> 
	</div>
</body>

