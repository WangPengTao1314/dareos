<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title id="title_text">
		<c:if test="${not empty rst.STATE }">${rst.STATE }</c:if>
		<c:if test="${empty rst.STATE }">编辑</c:if>
	</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/drp/sale/goodsorderhd/Goodsorderhd_HandleEdit.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/pages/common/selproduct/selproduct.js"></script>
	<style type="text/css">
		.child{overflow-x: auto; width: 100%; }
		.dashed{border-bottom: 1px solid #e8edf2;width: 98%;}
		.frozen{position: fixed; left:0;}
		
	</style>
</head>
<body class="bodycss1 add_demoone" style=" overflow-y:auto; overflow-x:auto;" >
<div class="">
	<!--  <span class="add_demospan">当前位置：经销商>订货单管理</span> -->
		<table id="jsontbP" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
			<tr>
				<td class="detail2">
				<table style="width:100%;padding: 9px 0% 0;">
				<tr>
				<td class="title">订单信息</td>	
				<td align="right" onclick='changeinput(this)'>
					<input type="button"  id="ipt"  value="收起" style="border:none;color:#bfbfbf;outline:none;background-color:#fff;height:22px;">
					<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="vertical-align: middle;" id="imgsz">
				</td>
				</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td class="detail2" id="toggleTable">
				<form name="mainForm" id="mainForm" autocomplete="off">
					<input type="hidden" id="DELIVER_ADDR_ID" />
					<input type="hidden" id="initSel" value=" CHANN_ID in ${rst.CHANN_IDS } and IS_BASE_FLAG = '0' "/>
					<input type="hidden" id="flowSel" value="" />
					<input type="hidden" id="flowNo" value="${flowNo }" />
					<input type="hidden" id="stateBack" value="" />
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
									value="${rst.GOODS_ORDER_NO}" json="GOODS_ORDER_NO" name="GOODS_ORDER_NO"
									id="GOODS_ORDER_NO" placeholder="编号自动生成" />
								<input class="gdtd_select_input" type="hidden" value="${rst.GOODS_ORDER_ID}"
									json="GOODS_ORDER_ID" name="GOODS_ORDER_ID" id="GOODS_ORDER_ID">
								<input class="gdtd_select_input" type="hidden" value="${rst.ORDER_TRACE_ID }"
									json="ORDER_TRACE_ID" name="ORDER_TRACE_ID" id="ORDER_TRACE_ID">
								<input class="gdtd_select_input" type="hidden" value="${rst.FROM_BILL_ID }"
									json="FROM_BILL_ID" name="FROM_BILL_ID" id="FROM_BILL_ID">
								<input class="gdtd_select_input" type="hidden" value="${rst.TOTL_AMOUNT}"
									json="TOTL_AMOUNT" name="TOTL_AMOUNT" id="TOTL_AMOUNT"/>
							</td><%-- 
							<td class="gdtd">
								<div>预计到货日期：</div>
								<input class="gdtd_select_input" type="text" json="PRE_RECV_DATE" id="PRE_RECV_DATE"
									name="PRE_RECV_DATE" placeholder="订单交期后7天" value="${rst.PRE_RECV_DATE}"
									label="预计到货日期" autocheck="true" inputtype="string" onclick="SelectDate();" readonly />
							</td> --%>
							<td class="gdtd">
								<div>下单日期：</div>
								<input class="gdtd_select_input" type="text" json="ORDER_DATE" id="ORDER_DATE"
									name="ORDER_DATE" placeholder="提交自动生成" value="${rst.ORDER_DATE}"
									label="下单日期" readonly />
							</td>
							<td class="gdtd">
							<div>订单交期：</div>
								<input class="gdtd_select_input" type="text" json="ORDER_DELIVERY_DATE"
									id="ORDER_DELIVERY_DATE" name="ORDER_DELIVERY_DATE" <%-- placeholder="订单生效后25天"  --%>
									value="${rst.ORDER_DELIVERY_DATE}" label="订单交期" readonly />
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div>经销商备注：</div>
								<textarea class="gdtd_tb_textarta" json="REMARK" name="REMARK" id="REMARK"
									label="经销商备注" autocheck="true" inputtype="string"
									maxlength="3000">${fn:replace(rst.REMARK, "；", "&#13;")}</textarea>
							</td>
							<td class="gdtd">
								<div>订单图纸：<button type="button" class="layui-btn uploadFile " id="attPath" lay-data="{id:'attPath'}">上传</button></div>
								<input type="hidden"  json="attPath" name="attPath" id="hid_attPath" value="${ rst.attPath}">				
								<table class="layui-table" style="width:85%" id="view_attPath"></table>
							</td>
						</tr>
						<!-- <tr>
							<td align="left">备注：</td>
						</tr> -->
						<%-- <tr>
							<td colspan="8">
								<textarea style="width: 58.5%;" json="REMARK" name="REMARK" id="REMARK" inputtype="string"
									maxlength="200"  rows="5">${rst.REMARK}</textarea>
							</td>
						</tr> --%>
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
							<div class="dashed font-color">订货信息</div>
							</td>
						</tr>
						<tr class="yhzz">
							<td  class="gdtd">
								<div>订单组织：</div>
								<select class="gdtd_select_input" json="LEDGER_ID" id="LEDGER_ID" name="LEDGER_ID"
									label="订单组织" autocheck="true" inputtype="string" mustinput="true" ></select>
								<input class="gdtd_select_input" type="hidden" json="LEDGER_NAME" name="LEDGER_NAME" 
									id="LEDGER_NAME" value="${rst.LEDGER_NAME}">
							</td>
							<td  class="gdtd">
								<div>订货组织：</div>
								<input class="gdtd_select_input" name="ORDER_CHANN_NAME" id="ORDER_CHANN_NAME" type="text"
									json="ORDER_CHANN_NAME" value="${rst.ORDER_CHANN_NAME }" 
									maxlength="100" readonly="readonly" />
								<input type="hidden" value="${rst.ORDER_CHANN_ID }"
									json="ORDER_CHANN_ID" name="ORDER_CHANN_ID" id="ORDER_CHANN_ID">
								<input type="hidden" value="${rst.ORDER_CHANN_NO }"
									json="ORDER_CHANN_NO" name="ORDER_CHANN_NO" id="ORDER_CHANN_NO">
							</td>
							<td  class="gdtd">
								<div>收货组织：</div>
								<input class="gdtd_select_input" type="text" value="${rst.RECV_CHANN_NAME}"
									json="RECV_CHANN_NAME" name="RECV_CHANN_NAME" id="RECV_CHANN_NAME" 
									label="收货组织" autocheck="true" inputtype="string" mustinput="true"
									maxlength="100" readonly="readonly" />
								<c:if test="${IS_DRP_LEDGER ne '2' and empty erjiOrderId}">
								<img name="imgTab" align="absmiddle" class="magnifier"
									src="${ctx }/styles/newTheme/images/plus/select.gif" onClick="selChann();"/>
								</c:if>
								<input type="hidden" value="${rst.RECV_CHANN_ID }"
									json="RECV_CHANN_ID" name="RECV_CHANN_ID" id="RECV_CHANN_ID">
								<input type="hidden" value="${rst.RECV_CHANN_NO }"
									json="RECV_CHANN_NO" name="RECV_CHANN_NO" id="RECV_CHANN_NO">
							</td>
							<td  class="gdtd">
								<div>经销商名称：</div>
								<input class="gdtd_select_input" type="text" value="${rst.CHANN_NAME}" maxlength="200"
									json="CHANN_NAME" name="CHANN_NAME" id="CHANN_NAME" readonly="readonly" />
								<input class="gdtd_select_input" type="hidden" value="${rst.CHANN_ID}" json="CHANN_ID"
									name="CHANN_ID" id="CHANN_ID" inputtype="string" />
								<input class="gdtd_select_input" type="hidden" value="${rst.CHANN_NO}" json="CHANN_NO"
									name="CHANN_NO" id="CHANN_NO" inputtype="string" />
							</td>
						</tr>
						<tr class="yhzz">
							<td class="gdtd">
								<div>标签打印名称：</div>
								<input class="gdtd_select_input" type="text" value="${rst.PRINT_NAME}" json="PRINT_NAME"
									name="PRINT_NAME" id="PRINT_NAME" maxlength="100" 
									label="标签打印名称" autocheck="true" inputtype="string" mustinput="true" />
							
							</td>
							<td class="gdtd">
								<div>业务部门：</div>
								<input class="gdtd_select_input" type="text" value="${rst.SALEDEPT_NAME}"
									json="SALEDEPT_NAME" name="SALEDEPT_NAME" id="SALEDEPT_NAME" 
									maxlength="100" readonly="readonly" />
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
						<tr >
							<td  class="gdtd">
								<div>收货人：</div>
								<input class="gdtd_select_input" type="text" value="${rst.PERSON_CON}" json="PERSON_CON"
									name="PERSON_CON" id="PERSON_CON" 
									label="收货人" autocheck="true" inputtype="string" mustinput="true" maxlength="30" />
									<img name="imgTab" align="absmiddle" class="magnifier"
									src="${ctx }/styles/${theme}/images/plus/select.gif" onClick="selPerson();"/>
							</td>
							<td  class="gdtd">
								<div>联系电话：</div>
								<input class="gdtd_select_input" type="text" value="${rst.TEL}" json="TEL" name="TEL"
									id="TEL" label="联系电话" autocheck="true" inputtype="string" mustinput="true" maxlength="50" />
								</td>
							<td  class="gdtd" colspan="2">
								<div>收货地址：</div>
								<input class="gdtd_select_input" type="text" value="${rst.RECV_ADDR}" json="RECV_ADDR"
									name="RECV_ADDR" id="RECV_ADDR" style="width:92.5%"
									label="收货地址" autocheck="true" inputtype="string" mustinput="true" maxlength="100" />
							</td>
						</tr>
						<tr class="ysfs">
							<td  class="gdtd">
								<div>客户姓名：</div>
								<input class="gdtd_select_input" type="text" value="${rst.CUST_NAME}" json="CUST_NAME"
									name="CUST_NAME" id="CUST_NAME" 
									label="客户姓名" autocheck="true" inputtype="string" mustinput="true" maxlength="50" />
							</td>
							<td class="gdtd">
								<div>客户电话：</div>
								<input class="gdtd_select_input" type="text" value="${rst.CUST_TEL}" json="CUST_TEL"
									name="CUST_TEL" id="CUST_TEL" 
									label="客户电话" autocheck="true" inputtype="string" mustinput="true" maxlength="50" />
							</td>
							<td class="gdtd" colspan="2">
								<div>客户住址：</div>
								<input class="gdtd_select_input" type="text" value="${rst.CUST_ADDR}" json="CUST_ADDR"
									name="CUST_ADDR" id="CUST_ADDR" style="width:92.5%"
									label="客户住址" autocheck="true" inputtype="string" mustinput="true" maxlength="100" />
							</td>
						</tr>
						<tr class="ysfs" style="display: none;">
							<td class="gdtd">
								<div>运输方式：</div>
								<select class="gdtd_select_input" json="TRANSPORT" id="TRANSPORT" name="TRANSPORT"
									label="运输方式" autocheck="" inputtype="string" mustinput="true" ></select>
							</td>
							<td class="gdtd">
								<div>运输结算方式：</div>
								<input class="gdtd_select_input" type="text" value="${rst.TRANSPORT_SETTLE}"
									json="TRANSPORT_SETTLE" name="TRANSPORT_SETTLE" id="TRANSPORT_SETTLE" 
									label="运输结算方式" autocheck="" inputtype="string" mustinput="" maxlength="100" />
							</td>
							<td class="gdtd" nowrap>
								<div>订单总金额：</div>
								<input class="gdtd_select_input" type="text" value="${rst.TOTAL_AMOUNT}"
									json="TOTAL_AMOUNT" name="TOTAL_AMOUNT" id="TOTAL_AMOUNT" readonly disabled/>
							</td>
							<td class="gdtd">
								<div>返利总金额：</div>
								<input class="gdtd_select_input" type="text" value="${rst.TOTAL_REBATE}"
									json="TOTAL_REBATE" name="TOTAL_REBATE" id="TOTAL_REBATE" readonly disabled/>
							</td>
						</tr>
						<c:if test="${not empty option and 'pricing' ne option}">
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
							<div class="dashed font-color">审核信息</div>
							</td>
						</tr>
						<tr>
							<td nowrap class="gdtd" colspan="2">
								<div>
									<c:choose>
									<c:when test="${'turnToHQ' eq option }">退回意见</c:when>
									<c:otherwise>审核意见：</c:otherwise>
									</c:choose>
								</div>
								<textarea style="width:95.5%; " class="audit" json="auditContents" name="auditContents" id="auditContents" inputtype="string"
									<c:if test="${not empty option and 'turnToHQ' ne option }">
									label="审核意见" autocheck="true" inputtype="string" mustinput="true"
									</c:if>
									maxlength="500" rows="5"></textarea>
								<input type="hidden" json="auditStatus" name="auditStatus" id="auditStatus" value>
							</td>
							<td colspan="2">
								<div>备注：</div>
								<textarea class=" quote" json="REMARK2" name="REMARK2" id="REMARK2"
									label="备注" autocheck="true" inputtype="string"
									maxlength="3000" rows="5" style="width:92.5%">${fn:replace(rst.REMARK2, "；", "&#13;")}</textarea>
							</td>
						</tr>
						</c:if>
					</table>
					</form>
				</td>
			</tr>
		</table>

	<!-- <tr>
        <td>操作</td>
    </tr>
    <div class="audit">流程图占位区

    </div> -->

	<!-- 模块，页码，排序Id，排序方式，选择记录主键Id -->
	<input type="hidden" id="flag" value="${flag}"/>  
	<input type="hidden" id="IS_DRP_LEDGER" value="${IS_DRP_LEDGER}"/>
	<input type="hidden" id="erjiOrderId" value="${erjiOrderId}"/>
	<input type="hidden" id="option" value="${option}"/>
	<input type="hidden" id="ztid" value="${rst.LEDGER_ID}"/>
	<div class="detail" style="">
		<table style="float: left;padding: 9px 0% 0;">
			<tr>
				<td class="title">明细信息</td>
			</tr>
		</table>
		<table id="qxBtnTb" cellpadding="0" cellspacing="0" border="0" style=" float: right; ">
			<tr style="height: 40px">
				<td align="right" style="display: none;" id="quoteTd">
					<!-- <td nowrap align="right" style="width: 100%"> -->
						报价总金额：<span class="validate"></span>
						<input class="gdtd_select_input decimal quote" type="text" value="${rst.AMOUNT}"
							id="total" readonly style="width: 150px; height:30px; color: red;" 
							onchange="countRebateDsct('#totalRebate')" />&nbsp;元&nbsp;
					<!-- </td>
					<td align="left" style="width: 160px"> -->
						账户返利余额：<%-- <span class="validate">${rst.REBATE_CURRT}&nbsp;元&nbsp;</span> --%>
						<input class="gdtd_select_input decimal quote" type="text" value="${rst.REBATE_CURRT}" json="REBATE_CURRT"
							name="REBATE_CURRT" id="REBATE_CURRT" readonly style="width: 100px; height:30px; color: red;"/>&nbsp;元&nbsp;
					<!-- </td>
					<td align="left" class="gdtd_tb"> -->
						本单使用返利：
						<input class="gdtd_select_input decimal quote" type="text" value="${rst.REBATEDSCT}" json=REBATEDSCT
							name="REBATEDSCT" id="REBATEDSCT" style="width: 50px; height:30px; "
							onkeyup="countRebateAmount(this)" onchange="countRebateAmount(this)" />&nbsp;%&nbsp;
						<input class="gdtd_select_input decimal quote" type="text" value="${rst.TOTAL_REBATE}"
							name="totalRebate" id="totalRebate" style="width: 80px; height:30px; "
							onkeyup="countRebateDsct(this)" onchange="countRebateDsct(this)" />&nbsp;元&nbsp;
					<!-- </td> -->
				</td>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td nowrap style="word-spacing: 8px;">
					<c:if test="${empty option or 'turnToHQ' eq option}">
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
	<div class="bodycss1 child">
		<!-- 删除标记:在该页面有删除操作后，返回时刷新页面。否则直接返回上一级，不刷新 -->
		<input id="delFlag" type="hidden" value="false"/>
		<table width="99.5%" height="" border="0" cellspacing="0" cellpadding="0" class="panel">
			<tr>
				<td>
					<div class="lst_area">
						<form autocomplete="off">
							<input type="hidden" id="PRDNOS" name="PRDNOS" value="${PRDNOS}">
							<input type="hidden" id="selectParams" name="selectParams" value="STATE='启用' and DEL_FLAG='0' and FINAL_NODE_FLAG='1' ">
							<input type="hidden" id="initParams" value="">
							<input type="hidden" id="DECT_RATE" value="${DECT_RATE}">
							<input type="hidden" id="rate" name="rate">
							<table id="jsontb" width="100%" border="0" cellpadding="5" cellspacing="0" class="lst" >
								<tr class="fixedRow">
									<th nowrap align="center" style="width: 2%"><input type="checkbox" style="height: 12px; valign: middle" id="allChecked"></th>
									<th nowrap align="center" class="LMM">组号</th>
									<th nowrap align="center" class="" style="min-width: 100px;">产品编码</th>
									<th nowrap align="center" class="">产品名称</th>
									<th nowrap align="center" class="">单位</th>
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
									<th nowrap align="center" class="LMM">是否开锁孔</th>
									<th nowrap align="center">图纸</th>
									<th nowrap align="center">是否返利</th>
									<th nowrap align="center">数量</th>
									<th nowrap align="center" class="SHOWPRICE" >计算报价</th>
									<th nowrap align="center" class="SHOWPRICE" >折扣率(%)</th>
									<th nowrap align="center" class="SHOWPRICE" >返利</th>
									<th nowrap align="center" class="SHOWPRICE" >最终报价</th>
									<th nowrap align="center" class="SHOWPRICE" >金额</th>
									<th nowrap align="center">备注</th>
								</tr>

								<c:if test="${empty rstChild}">
									<tr id="soDtlEmpty">
										<td style="height:42px;" colspan="25" align="center" class="lst_empty">
											&nbsp;无相关记录&nbsp;
										</td>
									</tr>
								</c:if>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
		<input id="flag" value="1" type="hidden">
	</div>
	<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;left: 0;word-spacing: 8px;">
		<c:if test="${empty option and 'turnToHQ' ne option}">
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
		<button class="img_input">
			<label onclick="closeDialog()">
				<img src="${ctx}/styles/${theme}/images/icon/chexiao.png" class="icon_font"> 
				<input type="button" class="btn" value="关闭"/>
			</label>
		</button> 
	</div>
</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
	var initSel = " SJGL in ('G', 'SF')";
	SelDictShow("BILL_TYPE","BS_190","${rst.BILL_TYPE}",initSel);
	SelDictShow("LEDGER_ID","BS_212","${rst.LEDGER_ID}"," XTYHID = '${XTYHID}' ");
	SelDictShow("TRANSPORT","BS_185","${rst.TRANSPORT}","");

</script>

<script type="text/javascript">
	var erjiOrderId = '${erjiOrderId}';//alert(erjiOrderId);
    var interval=setInterval(function(){
     	if(uploadFile){
     		clearInterval(interval);
			<c:forEach items="${rstChild}" var="rst_child" varStatus="row">
			var GOODS_ORDER_DTL_ID = '${rst_child.GOODS_ORDER_DTL_ID}';
			if(erjiOrderId && ''!=erjiOrderId){
				GOODS_ORDER_DTL_ID = '';
			}
			var arrs = [
						GOODS_ORDER_DTL_ID,//0
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
			            'bak',//31
			            'bak',//32
			            'bak',//33
			            'bak',//34
			            'bak',//35
		            ];//console.log('arrs', arrs);
		         addRow(arrs);
			</c:forEach>

     	}
    },500);
</script>
</html>