<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<%-- <link rel="stylesheet" href="${ctx}/styles/${theme}/css/new_demo.css"> --%>
	<title>
		<c:if test="${not empty rst.STATE }">${rst.STATE }</c:if>
		<c:if test="${empty rst.STATE }">编辑</c:if>
	</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/erp/sale/saleorder/Saleorder_Edit.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/pages/common/selproduct/selproduct.js"></script>
	<style type="text/css">
		.child{overflow-x: auto; width: 100%; }
		.dashed{border-bottom: 1px solid #e8edf2;width: 98%;}

	.toggle{
		width: 40%;
    float: right;
    text-align: right;
    }
	</style>
</head>
<body class="bodycss1 add_demoone" style=" overflow-y:auto; overflow-x:auto;">
<!-- <span class="add_demospan">当前位置：销售管理>>订单中心>>订单管理</span> -->
<div class="">
	<form name="mainForm" id="mainForm" autocomplete="off">
		<input type="hidden" id="DELIVER_ADDR_ID" />
		<input type="hidden" id="initSel" name="initSel" value=" CHANN_ID = '${rst.CHANN_ID }' "/>
		<input type="hidden" json="SALE_ORDER_ID" id="SALE_ORDER_ID" name="SALE_ORDER_ID" value="${rst.SALE_ORDER_ID }"/>
		<input type="hidden" json="CHANGE_APPLY_ID" id="CHANGE_APPLY_ID" name="CHANGE_APPLY_ID" value="${rst.CHANGE_APPLY_ID }"/>
		<input type="hidden" id="state" value="${rst.STATE }"/>
		<table id="jsontbP" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
			<tr>
				<td class="detail2">
					<table style="width:100%;padding: 9px 0% 0;">
						<tr>
							<td class="title">订单信息</td>	
							<td align="right">
								<input type="button"  id="ipt"  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;" onclick='changeinput(this)'>
								<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="vertical-align: middle;" id="imgsz">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="detail2" id="toggleTable">
					<table cellspacing="0" cellpadding="0" class="detail3"
						style="border-left:1px solid #d8dde6;border-right:1px solid #d8dde6;">
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
								<div class="dashed font-color">基本信息</div>
							</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>销售类型：</div>
								<select class="gdtd_select_input" json="BILL_TYPE" name="BILL_TYPE" id="BILL_TYPE" 
									label="销售类型" autocheck="true" inputtype="string" mustinput="true"></select>
							</td>
							<td class="gdtd">
								<div>订货单号：</div>
								<input class="gdtd_select_input" type="text" readonly="readonly"
									value="${rst.FROM_BILL_NO}" json="FROM_BILL_NO" name="FROM_BILL_NO"
									id="FROM_BILL_NO" placeholder="订货单号" />
								<input class="gdtd_select_input" type="hidden" value="${rst.SALE_ORDER_NO }"
									json="SALE_ORDER_NO" name="SALE_ORDER_NO" id="SALE_ORDER_NO">
								<input class="gdtd_select_input" type="hidden" value="${rst.ORDER_TRACE_ID }"
									json="ORDER_TRACE_ID" name="ORDER_TRACE_ID" id="ORDER_TRACE_ID">
								<input class="gdtd_select_input" type="hidden" value="${rst.TOTL_AMOUNT}"
									json="TOTL_AMOUNT" name="TOTL_AMOUNT" id="TOTL_AMOUNT"/>
								<input class="gdtd_select_input" type="hidden" value="${rst.NC_ID}"
									json="NC_ID" name="NC_ID" id="NC_ID"/>
							</td>
							<td class="gdtd">
								<div>预计到货日期：</div>
								<input class="gdtd_select_input applyaudit" type="text" json="PRE_RECV_DATE" id="PRE_RECV_DATE"
									name="PRE_RECV_DATE" placeholder="订单交期后7天" value="${rst.PRE_RECV_DATE}"
									label="预计到货期" autocheck="true" inputtype="string" onclick="SelectDate();" readonly />
								<img align="absmiddle" class="select_gif"
									src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"
									onclick="SelectDate(PRE_RECV_DATE);" />
							</td>
							<td class="gdtd">
								<div>下单日期：</div>
								<input class="gdtd_select_input" type="text" json="ORDER_DATE" id="ORDER_DATE"
									name="ORDER_DATE" placeholder="提交自动生成" value="${rst.ORDER_DATE}"
									label="下单日期" readonly />
							</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>订单交期：</div>
								<input class="gdtd_select_input" type="text" json="ORDER_DELIVERY_DATE"
									id="ORDER_DELIVERY_DATE" name="ORDER_DELIVERY_DATE" placeholder="订单生效后25天" 
									value="${rst.ORDER_DELIVERY_DATE}" label="订单交期" onclick="SelectDate();" readonly
									onchange="changeFactoryNo(this);" />
								<img align="absmiddle" class="select_gif"
									src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"
									onclick="SelectDate(ORDER_DELIVERY_DATE);" />
								<input type="hidden" id="xtbs" value="${xtbs }" />
							</td>
							<td colspan="2">
								<div>经销商备注：</div>
								<textarea class="gdtd_tb_textarta" json="REMARK" name="REMARK" id="REMARK"
									label="经销商备注" autocheck="true" inputtype="string"
									maxlength="3000"  style="width:92.5%">${fn:replace(rst.REMARK, "；", "&#13;")}</textarea>
							</td>
							<td class="gdtd">
								<div>订单图纸：<button type="button" class="layui-btn uploadFile" id="attPath" lay-data="{id:'attPath'}">上传</button></div>
								<%-- <input json="attPath" name="attPath" id="attPath" value="${rst.attPath}"> --%>
								<input type="hidden" json="attPath" name="attPath" id="hid_attPath" value="${rst.attPath}">
								<table class="layui-table" style="width:85%" id="view_attPath"></table>
							</td>
						</tr>
						<tr class='proj' style='display: none;'>
							<td class="gdtd" colspan="2">
								<div>工程项目：</div>
								<input class="gdtd_select_input" type="text" value="${rst.PROJECT_NAME}" maxlength="200"
									json="PROJECT_NAME" name="PROJECT_NAME" id="PROJECT_NAME" readonly="readonly" style="width:92.5%"
									label="工程项目" autocheck="true" inputtype="string" mustinput="true" />
								<img name="imgTab" align="absmiddle" class="magnifier_2col"
									src="${ctx }/styles/newTheme/images/plus/select.gif" onClick="selProj();"/>
								<input class="gdtd_select_input" type="hidden" value="${rst.PROJECT_ID}" json="PROJECT_ID"
									name="PROJECT_ID" id="PROJECT_ID" inputtype="string" />
								<input class="gdtd_select_input" type="hidden" value="${rst.PROJECT_NO}" json="PROJECT_NO"
									name="PROJECT_NO" id="PROJECT_NO" inputtype="string" />
							</td>
						</tr>
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
								<div class="dashed font-color">订货信息</div>
							</td>
						</tr>
						<tr class="yhzz">
							<td class="gdtd">
								<div>订单组织：</div>
								<select class="gdtd_select_input" json="LEDGER_ID" id="LEDGER_ID" name="LEDGER_ID" 
									label="订单组织" autocheck="true" inputtype="string" mustinput="true" ></select>
								<input class="gdtd_select_input" type="hidden" json="LEDGER_NAME" name="LEDGER_NAME" 
									id="LEDGER_NAME" value="${rst.LEDGER_NAME}">
							</td>
							<td class="gdtd" colspan="2">
								<div>经销商名称：</div>
								<input class="gdtd_select_input" type="text" value="${rst.CHANN_NAME}" maxlength="200"
									json="CHANN_NAME" name="CHANN_NAME" id="CHANN_NAME" readonly="readonly" style="width:92.5%"
									label="经销商名称" autocheck="true" inputtype="string" mustinput="true" />
								<img name="imgTab" align="absmiddle" class="magnifier_2col"
									src="${ctx }/styles/newTheme/images/plus/select.gif" onClick="selChann();"/>
								<input class="gdtd_select_input" type="hidden" value="${rst.CHANN_ID}" json="CHANN_ID"
									name="CHANN_ID" id="CHANN_ID" inputtype="string" />
								<input class="gdtd_select_input" type="hidden" value="${rst.CHANN_NO}" json="CHANN_NO"
									name="CHANN_NO" id="CHANN_NO" inputtype="string" />
									
								<input type="hidden" value="${rst.ORDER_CHANN_ID }"
									json="ORDER_CHANN_ID" name="ORDER_CHANN_ID" id="ORDER_CHANN_ID">
								<input type="hidden" value="${rst.ORDER_CHANN_NO }"
									json="ORDER_CHANN_NO" name="ORDER_CHANN_NO" id="ORDER_CHANN_NO">
								<input type="hidden" value="${rst.ORDER_CHANN_NAME }"
									json="ORDER_CHANN_NAME" name="ORDER_CHANN_NAME" id="ORDER_CHANN_NAME">
								<input type="hidden" value="${rst.RECV_CHANN_ID }"
									json="RECV_CHANN_ID" name="RECV_CHANN_ID" id="RECV_CHANN_ID">
								<input type="hidden" value="${rst.RECV_CHANN_NO }"
									json="RECV_CHANN_NO" name="RECV_CHANN_NO" id="RECV_CHANN_NO">
								<input type="hidden" value="${rst.RECV_CHANN_NAME }"
									json="RECV_CHANN_NAME" name="RECV_CHANN_NAME" id="RECV_CHANN_NAME">
							</td>
							<td class="gdtd">
								<div>标签打印名称：</div>
								<input class="gdtd_select_input applyaudit" type="text" value="${rst.PRINT_NAME}" json="PRINT_NAME"
									name="PRINT_NAME" id="PRINT_NAME" maxlength="200" 
									label="标签打印名称" autocheck="true" inputtype="string" mustinput="true" />
							</td>
						</tr>
						<tr class="yhzz">
							<td class="gdtd">
								<div>业务部门：</div>
								<input class="gdtd_select_input" type="text" value="${rst.SALEDEPT_NAME}"
									json="SALEDEPT_NAME" name="SALEDEPT_NAME" id="SALEDEPT_NAME" 
									readonly="readonly" />
								<input type="hidden" value="${rst.SALEDEPT_ID }"
									json="SALEDEPT_ID" name="SALEDEPT_ID" id="SALEDEPT_ID">
							</td>
							<td class="gdtd">
								<div>业务员：</div>
								<input class="gdtd_select_input" type="text" value="${rst.SALE_NAME}"
									json="SALE_NAME" name="SALE_NAME" id="SALE_NAME" 
									readonly="readonly" />
								<input type="hidden" value="${rst.SALE_ID }"
									json="SALE_ID" name="SALE_ID" id="SALE_ID">
							</td>
						</tr>
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
								<div class="dashed font-color">收货信息</div>
							</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>收货人：</div>
								<input class="gdtd_select_input" type="text" value="${rst.PERSON_CON}" json="PERSON_CON"
									name="PERSON_CON" id="PERSON_CON" 
									label="收货人" autocheck="true" inputtype="string" mustinput="true" maxlength="30" />
									<img name="imgTab" align="absmiddle" class="magnifier"
									src="${ctx }/styles/${theme}/images/plus/select.gif" onClick="selPerson();"/> 
							</td>
							<td class="gdtd">
								<div>联系电话：</div>
								<input class="gdtd_select_input" type="text" value="${rst.TEL}" json="TEL" name="TEL"
									id="TEL" label="联系电话" autocheck="true" inputtype="string" mustinput="true" maxlength="50" />
							</td>
							<td class="gdtd" colspan="2">
								<div>收货地址：</div>
								<input class="gdtd_select_input" type="text" value="${rst.RECV_ADDR}" json="RECV_ADDR"
									name="RECV_ADDR" id="RECV_ADDR"  style="width:92.5%"
									label="收货地址" autocheck="true" inputtype="string" mustinput="true" maxlength="250" />
							</td>
						</tr>
						<tr class="ysfs">
							<td class="gdtd">
								<div>客户姓名：</div>
								<input class="gdtd_select_input" type="text" value="${rst.CUST_NAME}" json="CUST_NAME"
									name="CUST_NAME" id="CUST_NAME" 
									label="客户姓名" autocheck="true" inputtype="string" mustinput="" maxlength="200" />
							</td>
							<td class="gdtd">
								<div>客户电话：</div>
								<input class="gdtd_select_input" type="text" value="${rst.CUST_TEL}" json="CUST_TEL"
									name="CUST_TEL" id="CUST_TEL" 
									label="客户电话" autocheck="true" inputtype="string" mustinput="" maxlength="50" />
							</td>
							<td class="gdtd" colspan="2">
								<div>客户住址：</div>
								<input class="gdtd_select_input" type="text" value="${rst.CUST_ADDR}" json="CUST_ADDR"
									name="CUST_ADDR" id="CUST_ADDR"  style="width:92.5%"
									label="客户住址" autocheck="true" inputtype="string" mustinput="" maxlength="250" />
							</td>
						</tr>
						<tr class="ysfs" style="">
							<td class="gdtd">
								<div>运输方式：</div>
								<select class="gdtd_select_input applyaudit" json="TRANSPORT" id="TRANSPORT" name="TRANSPORT"
									label="运输方式" autocheck="" inputtype="string" mustinput="true"  ></select>
							</td>
							<td class="gdtd">
								<div>运输结算方式：</div>
								<input class="gdtd_select_input applyaudit" type="text" value="${rst.TRANSPORT_SETTLE}"
									json="TRANSPORT_SETTLE" name="TRANSPORT_SETTLE" id="TRANSPORT_SETTLE" 
									label="运输结算方式" autocheck="" inputtype="string" mustinput="" />
							</td>
						</tr>
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
								<div class="dashed font-color">订单处理</div>
							</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>厂编：</div>
								<input class="gdtd_select_input applyaudit" type="text" value="${rst.FACTORY_NO}" json="FACTORY_NO"
									name="FACTORY_NO" id="FACTORY_NO" 
									label="厂编" autocheck="true" inputtype="string" mustinput="true" />
							</td>
							<td class="gdtd">
								<div>处理类型：</div>
								<select class="gdtd_select_input" json="PROESS_TYPE" name="PROESS_TYPE" id="PROESS_TYPE" 
									label="处理类型" autocheck="true" inputtype="string" mustinput="true"></select>
							</td>
							<td class="gdtd">
								<div>紧急程度：</div>
								<select class="gdtd_select_input" json="URGENCY_TYPE" name="URGENCY_TYPE" id="URGENCY_TYPE" 
									label="紧急程度" autocheck="true" inputtype="string" mustinput="true" ></select>
							</td>
							<c:if test="${not empty option && (option eq 'design' or option eq 'assign' or option eq 'turnERP') }">
							<td class="gdtd">
								<div>拆件记料表：<button type="button" class="layui-btn uploadFile" id="attPath_cj" lay-data="{id:'attPath_cj'}">上传</button>	</div>
								<input type="hidden" json="attPath_cj" name="attPath_cj" id="hid_attPath_cj" value="${rst.attPath_cj}" class="design">
								<table class="layui-table" style="width:85%" id="view_attPath_cj"></table>
							</td>
							<td style="display: none;">
								<input type="hidden" json="DESIGNER_ID" id="DESIGNER_ID" name="DESIGNER_ID" value="${rst.DESIGNER_ID }" />
							</td>
							</c:if>
						</tr>
						<tr>
							<td colspan="2">
								<div>备注：</div>
								<textarea class="gdtd_tb_textarta applyaudit" json="REMARK2" name="REMARK2" id="REMARK2"
									label="备注" autocheck="true" inputtype="string"
									maxlength="3000"  style="width:92.5%">${fn:replace(rst.REMARK2, "；", "&#13;")}</textarea>
							</td>
							<td class="gdtd SHOWPRICE" >
								<div>订单总金额：</div>
								<input class="gdtd_select_input" type="text" value="${rst.TOTAL_AMOUNT}"
									json="TOTAL_AMOUNT" name="TOTAL_AMOUNT" id="TOTAL_AMOUNT" readonly disabled/>
							</td>
							<td class="gdtd SHOWPRICE">
								<div>返利总金额：</div>
								<input class="gdtd_select_input" type="text" value="${rst.TOTAL_REBATE}"
									json="TOTAL_REBATE" name="TOTAL_REBATE" id="TOTAL_REBATE" readonly disabled/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<c:if test="${not empty option and option ne 'design' and option ne 'assign' and option ne 'turnERP'}">
			<tr>
				<td class="detail2">
					<table cellspacing="0" cellpadding="0" class="detail3" style="">
						<tr>
							<td nowrap class="gdtd" colspan="2">
								<div>申请变更内容：</div>
								<textarea class="" style="width: 92.5%;"
									json="CHAN_REMARK" name="CHAN_REMARK" id="CHAN_REMARK" inputtype="string"
									maxlength="200" rows="5">${rst.CHAN_REMARK}</textarea>
							</td>
							<td nowrap class="gdtd" colspan="2">
								<div>实际变更内容：</div>
								<textarea class=" applyaudit" style="width: 92.5%;"
									json="ORDER_REMARK" name="ORDER_REMARK" id="ORDER_REMARK" inputtype="string"
									label="实际变更内容" autocheck="true" inputtype="string" mustinput="true"
									maxlength="200" rows="5" >${rst.ORDER_REMARK}</textarea>
							</td>
						</tr>
						<tr>
							<td nowrap class="gdtd" colspan="2">
								<div>审核意见：</div>
								<textarea style="width: 92.5%;" class=" applyaudit audit" json="auditContents" name="auditContents" id="auditContents" inputtype="string"
									label="审核意见" autocheck="" inputtype="string" mustinput=""
									maxlength="500" rows="5" ${rst.RETURN_RSON }></textarea>
							</td>
							<td nowrap class="gdtd" colspan="2">
								<div>&nbsp;</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			</c:if>
			<input type="hidden" json="auditStatus" name="auditStatus" id="auditStatus" value>
		</table>
		<input type="hidden" id="option" value="${option}"/>
		<input type="hidden" id="ztid" value="${rst.LEDGER_ID}"/>
		<input type="hidden" id="xslx" value="${rst.BILL_TYPE}"/>
	</form>
    <!-- <div class="audit">流程图占位区
    </div> -->
	<div class="detail" style="">
		<table style="float: left;padding: 9px 0% 0;">
			<tr>
				<td class="title">明细信息</td>
			</tr>
		</table>
		<table id="qxBtnTb" cellpadding="0" cellspacing="0" border="0" style=" float: right; ">
			<tr style="height: 40px">
				<td align="right" style="display: none;" id="quoteTd">
						数量合计:<span class="validate"></span>
						<input class="gdtd_select_input decimal quote" type="text" value="${rst.SUM}"
							id="sum" readonly style="width: 80px; height:30px; color: red;" />&nbsp;
					<!-- <td nowrap align="right" style="width: 100%"> -->
						报价总金额:<span class="validate"></span>
						<input class="gdtd_select_input decimal quote" type="text" value="${rst.AMOUNT}"
							id="total" readonly style="width: 110px; height:30px; color: red;" 
							onchange="countRebateDsct('#totalRebate')" />&nbsp;元&nbsp;
					<!-- </td>
					<td align="left" style="width: 160px"> -->
						账户返利余额:<%-- <span class="validate">${rst.REBATE_CURRT}&nbsp;元&nbsp;</span> --%>
						<input class="gdtd_select_input decimal quote" type="text" value="${rst.REBATE_CURRT}" json="REBATE_CURRT"
							name="REBATE_CURRT" id="REBATE_CURRT" readonly style="width: 80px; height:30px; color: red;"/>&nbsp;元&nbsp;
					<!-- </td>
					<td align="left" class="gdtd_tb"> -->
						本单使用返利:
						<input class="gdtd_select_input decimal quote" type="text" value="${rst.REBATEDSCT}" json=REBATEDSCT
							name="REBATEDSCT" id="REBATEDSCT" style="width: 50px; height:30px; "
							onkeyup="countRebateAmount(this)" onchange="countRebateAmount(this)" />&nbsp;%&nbsp;
						<input class="gdtd_select_input decimal quote" type="text" value="${rst.TOTAL_REBATE}"
							name="totalRebate" id="totalRebate" style="width: 80px; height:30px; "
							onkeyup="countRebateDsct(this)" onchange="countRebateDsct(this)" />&nbsp;元&nbsp;
					<!-- </td> -->
				</td>
				<td>
					&nbsp;&nbsp;
				</td>
				<td nowrap style="word-spacing: 6px;">
							<c:if test="${empty option || option eq 'applyaudit'}">
							<button class="img_input addbtn">
								<label for="add">
									<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"
										class="icon_font">
									<input id="add" type="button" class="btn add" value="新增">
								</label>
							</button>
							<button class="img_input">
								<label for="reverse">
									<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"
										class="icon_font">
									<input id="reverse" type="button" class="btn" value="反选">
								</label>
							</button>
							<button class="img_input">
								<label for="copy">
									<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"
										class="icon_font">
									<input id="copy" type="button" class="btn" value="复制">
								</label>
							</button>
							<button class="img_input" style="display: none;">
								<label for="batch">
									<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"
										class="icon_font">
									<input id="batch" type="button" class="btn" value="批量修改">
								</label>
							</button>
							<button class="img_input deletebtn">
								<label for="delete">
									<img src="${ctx}/styles/${theme}/images/icon/shanchu.png"
										class="icon_font">
									<input id="delete" type="button" class="btn deletebtn" value="删除">
								</label>
							</button>
							</c:if>
				</td>
			</tr>
		</table>
	</div>
	<!-- 表格 -->
	<div class="bodycss1 child">
		<form autocomplete="off">
		<input type="hidden" id="selectParams" name="selectParams" value="STATE='启用' and DEL_FLAG='0' and FINAL_NODE_FLAG='1' ">
		<input type="hidden" id="initParams" value="">
		<table id="jsontb" width="100%" border="0" cellpadding="5" cellspacing="0" class="lst" >
			<tr class="fixedRow">
				<th nowrap align="center" style="width: 2%"><input type="checkbox" style="height:12px;valign:middle" id="allChecked"></th>
				<th nowrap align="center" class="LMM">组号</th>
				<th nowrap align="center" class="" style="min-width: 100px;">产品编码</th>
				<th nowrap align="center" class="">产品名称</th>
				<th nowrap align="center" class="LMM">门洞尺寸</th>
				<th nowrap align="center" class="">规格尺寸</th>
				<th nowrap align="center" class="LMM LYGCG">材质</th>
				<th nowrap align="center" class="LMM LYGCG">颜色</th>
				<th nowrap align="center" class="LMM">推向</th>
				<th nowrap align="center" class="LMM">玻璃</th>
				<th nowrap align="center" class="LMM">其他</th>
				<th nowrap align="center" class="LMM">系列</th>
				<th nowrap align="center" class=" LYGCG">投影面积</th>
				<th nowrap align="center" class=" LYGCG">展开面积</th>
				<th nowrap align="center" class=" ">工程位置</th>
				<th nowrap align="center" class="LMM">是否开锁孔</th>
				<th nowrap align="center">附图号</th>
				<th nowrap align="center">图纸</th>
				<%-- <th nowrap align="center">处理类型</th> --%>
				<th nowrap align="center" class="SHOWPRICE">是否返利</th>
				<th nowrap align="center">可用库存</th>
				<th nowrap align="center">数量</th>
				<th nowrap align="center">单位</th>
				<th nowrap align="center" class="SHOWPRICE">计算报价</th>
				<th nowrap align="center" class="SHOWPRICE">折扣率(%)</th>
				<th nowrap align="center" class="SHOWPRICE">返利</th>
				<th nowrap align="center" class="SHOWPRICE">最终报价</th>
				<th nowrap align="center" class="SHOWPRICE">金额</th>
				<th nowrap align="center">备注</th>
			</tr>

			<c:if test="${empty rstChild}">
				<tr id="soDtlEmpty">
					<td style="height:42px;" colspan="23" align="center" class="lst_empty">
						&nbsp;无相关记录&nbsp;
					</td>
				</tr>
			</c:if>
		</table>
		</form>
	</div>
	<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;left: 0;word-spacing: 8px;">
		<c:if test="${empty option }">
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
					<input id="commit" type="button" class="btn" value="保存并提交">
				</label>
			</button>
		</c:if>
		<c:if test="${not empty option }">
			<c:if test="${option eq 'assign'}">
			<button class="img_input">
				<label for="back2pre">
					<img src="${ctx}/styles/${theme}/images/icon/tuihui.png" class="icon_font">
					<input id="back2pre" type="button" class="btn" value="退回" status="F">
				</label>
			</button>
			<button class="img_input">
				<label for='assign'>
					<img src="${ctx}/styles/${theme}/images/icon/shenhe.png" class="icon_font">
					<input id="assign" type="button" class="btn" value="分派" />
				</label>
			</button>
			</c:if>
			<c:if test="${option eq 'design'}">
			<button class="img_input">
				<label for="tmpdesign">
					<img src="${ctx}/styles/${theme}/images/icon/baocun.png"
						class="icon_font">
					<input id="tmpdesign" type="button" class="btn" value="暂存" status="Z">
				</label>
			</button>
			<button class="img_input">
				<label for="design">
					<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"
						class="icon_font">
					<input id="design" type="button" class="btn" value="确定" status="T">
				</label>
			</button>
			<button class="img_input">
				<label for="undesign">
					<img src="${ctx}/styles/${theme}/images/icon/tuihui.png"
						class="icon_font">
					<input id="undesign" type="button" class="btn" value="退回" status="F">
				</label>
			</button>
			</c:if>
			<c:if test="${option eq 'turnERP'}">
			<button class="img_input">
				<label for="turnERP">
					<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"
						class="icon_font">
					<input id="turnERP" type="button" class="btn" value="转ERP" status="T">
				</label>
			</button>
			<button class="img_input">
				<label for="back2pre">
					<img src="${ctx}/styles/${theme}/images/icon/tuihui.png"
						class="icon_font">
					<input id="back2pre" type="button" class="btn" value="退回" status="F">
				</label>
			</button>
			</c:if>
			<c:if test="${option eq 'applyaudit'}">
			<button class="img_input">
				<label for="agree">
					<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"
						class="icon_font">
					<input id="agree" type="button" class="btn" value="变更" status="T">
				</label>
			</button>
			<button class="img_input">
				<label for="disagree">
					<img src="${ctx}/styles/${theme}/images/icon/tuihui.png"
						class="icon_font">
					<input id="disagree" type="button" class="btn" value="退回" status="F">
				</label>
			</button>
			</c:if>
			<c:if test="${'audit' eq option || option eq 'quote'}">
			<button class="img_input">
				<label for="audit">
					<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"
						class="icon_font">
					<input id="audit" type="button" class="btn" value="通过" status="T">
				</label>
			</button>
			</c:if>
			<c:if test="${'confirmquote' eq option || option eq 'confirm'}">
			<button class="img_input">
				<label for="confirm">
					<img src="${ctx}/styles/${theme}/images/icon/tijiao.png"
						class="icon_font">
					<input id="confirm" type="button" class="btn" value="确认" status="T">
				</label>
			</button>
			</c:if>
			<c:if test="${'audit' eq option || option eq 'quote' || 'confirmquote' eq option || option eq 'confirm'}">
			<button class="img_input">
				<label for="back">
					<img src="${ctx}/styles/${theme}/images/icon/tuihui.png"
						class="icon_font">
					<input id="back" type="button" class="btn" value="退回" status="F">
				</label>
			</button>
			</c:if>
		</c:if>
			<button class="img_input">
				<label onclick="closeDialog()">
					<img src="${ctx}/styles/${theme}/images/icon/chexiao.png"
						class="icon_font">
					<input type="button" class="btn" value="关闭"/>
				</label>
			</button>
	</div>
</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile_mul.jsp"%>
<script type="text/javascript">
	var initSel = "";
	if('' == '${rst.FROM_BILL_NO}'){//总部
		SelDictShow("LEDGER_ID","BS_212","${rst.LEDGER_ID}"," XTYHID = '${XTYHID}' ");
		//initSel = " SJGL in ('S', 'SF')";
	} else {
		SelDictShow("LEDGER_ID","BS_189","${rst.LEDGER_ID}"," CHANN_ID = '${rst.CHANN_ID}' ");
		//initSel = " SJGL in ('G', 'SF')";
	}
	SelDictShow("BILL_TYPE","BS_190","${rst.BILL_TYPE}",initSel);
	SelDictShow("TRANSPORT","BS_185","${rst.TRANSPORT}","");
	SelDictShow("PROESS_TYPE", "BS_191", "${rst.PROESS_TYPE}", "");
	SelDictShow("URGENCY_TYPE","BS_218","${rst.URGENCY_TYPE}","");
</script>
<script type="text/javascript">
	var interval = setInterval(function(){
		if(uploadFile){
			clearInterval(interval);

			<c:forEach items="${rstChild}" var="rst_child" varStatus="row">
			var arrs = [
			          '${rst_child.SALE_ORDER_DTL_ID}',//0
		              '${rst_child.GROUP_NO}',//1
		              '${rst_child.PRD_NO}',//2
		              '${rst_child.PRD_ID}',//3
		              '${rst_child.PRD_NAME}',//4
		              '${rst_child.STD_UNIT}',//5
		              '${rst_child.HOLE_SPEC}',//6
		              '${rst_child.PRD_SIZE}',//7
		              '${rst_child.PRD_QUALITY}',//8
		              '${rst_child.PRD_COLOR}',//9
		              '${rst_child.PRD_TOWARD}',//10
		              '${rst_child.PRD_GLASS}',//11
		              '${rst_child.PRD_OTHER}',//12
		              '${rst_child.PRD_SERIES}',//13
		              '${rst_child.attPath}',//14
		              '${rst_child.IS_NO_REBATE_FLAG}',//15
		              '${rst_child.IS_BACKUP_FLAG}',//16
		              '${rst_child.PRICE}',//17
		              '${rst_child.DECT_RATE}',//18
		              '${rst_child.DECT_PRICE}',//19
		              '${rst_child.ORDER_NUM}',//20
		              '${rst_child.ORDER_AMOUNT}',//21
		              '${rst_child.REMARK}',//22
		              '${rst_child.PRD_SPEC}',//23
		              '${rst_child.BRAND}',//24
		              '${rst_child.REBATE_AMOUNT}',//25
		              '${rst_child.PROJECTED_AREA}',//26
		              '${rst_child.EXPAND_AREA}',//27
		              '${rst_child.ROW_NO}',//28
		              '${rst_child.PRD_PLACE}',//29
		              '${rst_child.IS_NO_LOCK_FLAG}',//30
		              '${rst_child.FIGURE_NO}',//31
		              'bak',//32
		              'bak',//33
		              'bak',//34
		              'bak',//35
		              '${rst_child.FROM_BILL_DTL_ID}',//36
		              '${rst.PROESS_TYPE}',//37
		            ];//console.log('arrs', arrs);
			addRow(arrs);
			</c:forEach>

		}
	},500);
</script>
</html>