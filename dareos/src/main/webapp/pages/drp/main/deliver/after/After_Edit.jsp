<!-- 
 *@module 
 *@func 问题反馈单编辑
 *@version 1.0
 *@author 王朋涛
 *  -->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css"
	href="${ctx}/styles/${theme}/css/style.css">
<script language="JavaScript"
	src="${ctx}/pages/common/select/selCommJs.js"></script>
<script type="text/javascript"
	src="${ctx}/pages/drp/main/deliver/after/After_Edit.js"></script>
<script type=text/javascript
	src="${ctx}/scripts/core/jquery-1.6.3.min.js"></script>
<script type="text/javascript"
	src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
<title>问题反馈单编辑</title>
<style>
.toggle {
	width: 40%;
	float: right;
	padding: 0.8% 0px 0.5% 0px;
	text-align: right;
}
</style>
</head>
<body style="overflow-x: auto;">
	<!--浮动按钮层结束-->
<form name="mainForm" id="mainForm">
		<table id="jsontb"  width="100%" border="0" cellpadding="0" cellspacing="0"
			class="detail" style="border: 1px solid #e8edf2" id="jsontb">
			<tr>
				<td class="detail4">
					<table cellspacing="0" cellpadding="0" width="98%" height="98%"
						id="myTable1" class=" detail3 table_detail">
						<tr>
							<td class="gdtd_tb">
								<div>问题反馈单号 &nbsp;:</div>
								<input class="gdtd_select_input" name="problem_feedback_no" json="problem_feedback_no" id="problem_feedback_no" type="text" autocheck="true"
									placeholder="自动生成" maxlength="30" value="${entry.PROBLEM_FEEDBACK_NO}"  > 
								<input  name="problem_feedback_id" json="problem_feedback_id" id="problem_feedback_id" type="text" hidden="true" label="问题单id"
								 value="${entry.PROBLEM_FEEDBACK_ID}" maxlength="32">
								 <input type="hidden" id="XTYHID" name="XTYHID" value="${XTYHID}"/>
							</td>
							<td class="gdtd_tb">
								<div>订单编号:</div> 
								<input class="gdtd_select_input" name="SALE_ORDER_NO" json="sale_order_no"  id="FACTORY_NO" label="订单编号" type="text" value="${entry.SALE_ORDER_NO}" maxlength="32">
								<img class="select_gif" align="absmiddle" id="imgID"
								style="cursor: hand"
								src="${ctx}/styles/${theme}/images/plus/select.gif"
								onClick="selCommon('BS_70', false, 'SALE_ORDER_ID', 'SALE_ORDER_ID', 'forms[0]','SALE_ORDER_ID,FACTORY_NO,LEDGER_ID,CHANN_NAME,CHANN_ID,CHANN_NO,RECV_ADDR,TEL,PERSON_CON,PRE_RECV_DATE,CUST_NAME,ORDER_TRACE_ID','SALE_ORDER_ID,FACTORY_NO,LEDGER_ID,CHANN_NAME,CHANN_ID,CHANN_NO,RECV_ADDR,TEL,PERSON_CON,PRE_RECV_DATE,CUST_NAME,ORDER_TRACE_ID','BMZT')">
								<input class="gdtd_select_input" name="SALE_ORDER_ID"
								json="sale_order_id" id="SALE_ORDER_ID" hidden="true"
								type="text" maxlength="32" value="${entry.SALE_ORDER_ID}">
								<input type="hidden" id="BMZT"
								value="STATE ='已完成' and  XTYHID='${ADMIN}' AND USER_ID='${ADMIN}'"
								style="width: 180px; height: 23px">
								<input hidden="true" id="ORDER_TRACE_ID" value="${entry.ORDER_TRACE_ID}"  >
							</td>
							<td class="gdtd_tb">
								<div>顾客名称 &nbsp;:</div> <input class="gdtd_select_input"
								name="cust_name" json="cust_name" id="CUST_NAME" label="顾客名称"
								maxlength="30" type="text" value="${entry.CUST_NAME}" >
							</td>
						</tr>
						<tr>
						 	<td class="gdtd_tb">
						 	 	<div>订单组织&nbsp;:</div>
						 	 	<%-- <input json="order_org" id="LEDGER_ID" hidden="true" type="text" maxlength="32" value="${entry.ORDER_ORG}"> --%>
						 	 	<select class="gdtd_select_input" json="order_org" id="LEDGER_ID" name="LEDGER_ID" 
									label="订单组织" autocheck="true" inputtype="text" mustinput="true" type="text" ></select>
						  	</td>
							<td class="gdtd_tb">
								<div>经销商&nbsp;:</div> <input class="gdtd_select_input" name="chann_name"
								type="text" value="${entry.CHANN_NAME}" json="chann_name" label="经销商" 
								<c:if test="${IS_DRP_LEDGER eq 1 }">
									readonly="readonly"
								</c:if>
								autocheck="true" inputtype="text" mustinput="true" id="CHANN_NAME"> 
								<c:if test="${IS_DRP_LEDGER eq 0 }">
								<img name="imgTab" align="absmiddle" class="magnifier_2col"
									src="${ctx }/styles/newTheme/images/plus/select.gif" onClick="selChann();"/>
								</c:if>
								
								<input type="hidden"
								value="${entry.CHANN_ID}" json="chann_id" name="chann_id" autocheck="true" inputtype="text" label="选择经销商"
								id="CHANN_ID"> <input type="hidden"
								value="${entry.CHANN_NO}" json="chann_no" name="chann_no"
								id="CHANN_NO" READONLY>
							</td>
							<td class="gdtd_tb">
								<div>交货日期 &nbsp;:</div> <input class="gdtd_select_input"
								name="delivery_time" id="PRE_RECV_DATE" json="delivery_time"
								type="text" value="${entry.DELIVERY_TIME}" onclick="SelectDate();" readonly="readonly">
								<img class="select_gif" align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
													onclick="SelectDate(PRE_RECV_DATE);"/>
							</td>
						</tr>
						<tr>
							<td class="gdtd_tb">
								<div>收货人姓名 &nbsp;:</div> <input class="gdtd_select_input"
								name="person_con" id="PERSON_CON" json="person_con" type="text"
								value="${entry.PERSON_CON}">
							</td>
							<td class="gdtd_tb">
								<div>收货电话</div> <input class="gdtd_select_input" json="tel"
								id="TEL" type="text" value="${entry.TEL}">
								<input json="state" hidden="true" value="${entry.STATE}">
							</td>
							<td class="gdtd_tb">
								<div>收货地址&nbsp;:</div> <textarea class="gdtd_select_input"
								json="recv_addr" id="RECV_ADDR" >${entry.RECV_ADDR}</textarea> 
								<%-- <input class="gdtd_select_input" json="recv_addr"
								id="RECV_ADDR" type="text" value="${entry.RECV_ADDR}"> --%>
								<input type="hidden" id="initSel" name="initSel" value=" CHANN_ID ='${entry.CHANN_ID }'"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="detail4">
					<table cellspacing="0" cellpadding="0" width="98%" height="98%"
						id="myTable2" class="detail3 table_detail">
						<tr>
							<td class="gdtd_tb">
								<div>问题描述&nbsp;:</div> <textarea style="width: 61.5%;"
									label="问题描述" maxlength="500" rows="5" name="problem_detailed"
									json="problem_detailed" inputtype="string"
									id="problem_detailed">${fn:replace(entry.PROBLEM_DETAILED, "；", "&#13;")}</textarea>
							</td>
						</tr>
						<tr>
							 <td class="gdtd_tb">
								<div>
									反馈附件&nbsp;：
									<button type="button" class="layui-btn uploadFile"
										id="uploadExcel" lay-data="{id:'uploadExcel'}">上传</button>
								</div> 
								<input type="hidden" json="uploadExcel" name="uploadExcel" id="hid_uploadExcel" value="${entry.ATT_PATH}">
								<table class="layui-table" style="width: 85%"	id="view_uploadExcel"></table> 
							</td> 
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	<div class="bodycss1 child" hidden="true">
		<table id="jsontb_div" width="100%" border="0" cellpadding="0"
			cellspacing="0" class="detail">
			<tr>
				<td class="detail2" style="border-bottom: 0">
					<div
						style="width: 40%; float: left; height: 40px; font-size: 17px; font-weight: 500; line-height: 50px">附件信息</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="lst_area" style="vertical-align: middle;">
						<form>
							<table width="99%" id="jsontb1" border="0"
								style="border-collapse: collapse" class="lst">
								<tr class="fixedRow">
									<th align="center">序号</th>
									<th nowrap align="center" dbname="att_path">附件名称</th>
									<th nowrap align="center" dbname="cre_name">上传人</th>
									<th nowrap align="center" dbname="cre_time">上传时间</th>
									<th nowrap align="center" dbname="">操作</th>
								</tr>
								<c:forEach items="${entry.paths}" var="rst" varStatus="row">
									<c:set var="r" value="${row.count % 2}"></c:set>
									<c:set var="rr" value="${row.count}"></c:set>
									<tr class="list_row${r}" onmouseover="mover(this)"
										onmouseout="mout(this)" id="tr${rr}">
										<td align='center'>${row.count}</td>
										<td align="center"><input type="text" hidden="true"
											value="${rst.ATT_PATH}" id="uploadFile${rr}"
											readonly="readonly">&nbsp;</td>
										<td align="center">${rst.CRE_NAME}&nbsp;</td>
										<td align="center">${rst.CRE_TIME}&nbsp;</td>
										<td align="center">- <a
											href="javascript:deleteEntry('${rst.ATT_ID}')">&nbsp;删除&nbsp;</a>-
										</td>
									</tr>
								</c:forEach>
								<c:if test="${empty entry.paths}">
									<tr>
										<td height="25" colspan="13" align="center" class="lst_empty">
											&nbsp;无附件信息&nbsp;</td>
									</tr>
								</c:if>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<!--占位用table，以免显示数据被浮动层挡住-->
	<table width="100%" height="25px">
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<form name="backForm" method="post">
		<input type="hidden" name="pageNo" value="${pageNo }">
	</form>
	<!-- 底部固定部分 -->
	<div style="width: 100%; height: 52px"></div>
	<div
		style="width: 100%; padding: 0.5%; background: #F8F8F8; text-align: center; position: fixed; bottom: 0; word-spacing: 8px;">
		<button class="img_input">
			<label for='save'> <img
				src="${ctx}/styles/${theme}/images/icon/baocun.png"
				class="icon_font"> <input id="save" type="button" class="btn"
				value="保存">
			</label>
		</button>
		<button class="img_input">
			<label onclick="closeDialog()"> <img
				src="${ctx}/styles/${theme}/images/icon/chexiao.png"
				class="icon_font"> <input type="button" class="btn" value="返回">
			</label>
		</button>
	</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
	$(function() {
		//
		if('0' == '${IS_DRP_LEDGER}'){//总部
			SelDictShow("LEDGER_ID","BS_212","${entry.ORDER_ORG}"," XTYHID = '${XTYHID}'");
		} else {
			SelDictShow("LEDGER_ID","BS_189","${entry.ORDER_ORG}",$("#initSel").val());
		}
		//单一上传
		displayDownFile('uploadExcel',true);

	});
</script>
</html>