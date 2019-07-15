<!-- 
 *@module  发货管理
 *@func  发货指令维护编辑
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
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
<script type="text/javascript" src="${ctx}/pages/drp/main/deliver/order/Order_Edit.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/skin/jedate.css">
<style>
    .child{overflow-x: auto; width: 100%; }
	.toggle{
		width: 40%;
    float: right;
    padding: 0.8% 0px 0.5% 0px;
    text-align: right;
	}
		table.lst {
    background-color: #fff;
    padding: 0 1%;
    }
</style>
<title>发货单编辑</title>
</head>
<body class="bodycss1 add_demoone" style=" overflow-y:auto; overflow-x:auto;">
	<form name="mainForm" id="mainForm" >
			<table  id="jsontbP"  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
				<tr>
					<td class="detail2">
						<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable"  class="detail3"
						style="background-color: #fff; padding: 0 0 0 2%; border-collapse: separate; border-spacing: 10px;border-top:1px solid #e8edf2;;border-left:1px solid #e8edf2;;border-right:1px solid #e8edf2;;margin:0 auto">
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">基本信息</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>发货单号 &nbsp;：</div>
									<input class="gdtd_select_input" json="send_order_no"  name="send_order_no"  id="send_order_no" type="text"
										autocheck="true" inputtype="string" label="发货单号" placeholder="自动生成"  maxlength="30"
										  value="${entry.SEND_ORDER_NO}" READONLY>
										<input class="gdtd_select_input" json="send_order_id"  name="send_order_id"  id="SEND_ORDER_ID" type="text" hidden="true"
										autocheck="true" inputtype="string" label="发货单id" value="${entry.SEND_ORDER_ID}"  maxlength="32">
								</td>
								<td class="gdtd_tb">
									<div>订单编号：</div>
								    <input class="gdtd_select_input" json="sale_order_no"  name="SALE_ORDER_NO" label="订单编号" id="FACTORY_NO" autocheck="true" inputtype="string"  mustinput="true"
								    type="text"       value="${entry.FACTORY_NO}" READONLY>
									<img align="absmiddle" id="imgID"  name="selJGXX" style="cursor: hand;position:relative;right:10%" src="${ctx}/styles/${theme}/images/plus/select.gif"     onClick="selCommon('BS_204', false, 'SALE_ORDER_ID', 'SALE_ORDER_ID', 'forms[0]','SALE_ORDER_ID','SALE_ORDER_ID','BMZT')">
									<input type="hidden" id="BMZT"  value=" STATE='生产中'  AND USER_ID='${ADMIN}' AND  XTYHID='${ADMIN}'" >
									<input name="SALE_ORDER_ID" json="sale_order_id" id="SALE_ORDER_ID"  hidden="true"  type="text"       value="${entry.SALE_ORDER_ID}">
								</td>
								<td class="gdtd_tb">
									<div>当前状态&nbsp;：</div>
									<input class="gdtd_select_input" json="state" name="STATE" id="STATE"   type="text"       value="${entry.STATE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>销售类型 &nbsp;：</div>
									<input class="gdtd_select_input" json="bill_type" name="BILL_TYPE" id="BILL_TYPE"   type="text"       value="${entry.BILL_TYPE}"readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>预计到货日期&nbsp;：</div>
									<input class="gdtd_select_input" json="pre_recv_date" name="PRE_RECV_DATE" id="PRE_RECV_DATE"  type="text"       value="${entry.PRE_RECV_DATE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>下单日期 &nbsp;：</div>
									<input class="gdtd_select_input" json="order_date" name="ORDER_DATE" id="ORDER_DATE"  type="text"       value="${entry.ORDER_DATE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>订单交期 &nbsp;：</div>
									<input class="gdtd_select_input" json="order_delivery_date" name="ORDER_DELIVERY_DATE" id="ORDER_DELIVERY_DATE"   type="text"       value="${entry.ORDER_DELIVERY_DATE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>订单图纸：<button type="button" class="layui-btn uploadFile" id="ATT_PATH" lay-data="{id:'ATT_PATH'}" disabled>上传</button></div>
									<%-- <input json="attPath" name="attPath" id="attPath" value="${rst.attPath}"> --%>
									<input type="hidden" json="ATT_PATH" name="ATT_PATH" id="hid_ATT_PATH" value="${entry.ATT_PATH}">
									<table class="layui-table" style="width:85%" id="view_ATT_PATH"></table>
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>经销商备注：</div>
									<textarea style="width: 189.5%;"  json="remark" name="REMARK" id="REMARK"   READONLY>${fn:replace(entry.REMARK, "；", "&#13;")}</textarea>
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">订货信息</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>订单组织 &nbsp;：</div>
									<textarea class="gdtd_select_input" json="ledger_name"  id="LEDGER_NAME" readonly="readonly">${entry.LEDGER_NAME}</textarea>
									<input json="ledger_id"id="LEDGER_ID"  value="${entry.LEDGER_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>订货组织&nbsp;：</div>
									<textarea class="gdtd_select_input" json="order_chann_name" id="ORDER_CHANN_NAME"  readonly="readonly">${fn:replace(entry.ORDER_CHANN_NAME, "；", "&#13;")}</textarea>
									<input json="order_chann_no"id="ORDER_CHANN_NO"  value="${entry.ORDER_CHANN_NO}"  hidden="true">
									<input json="order_chann_id"id="ORDER_CHANN_ID"  value="${entry.ORDER_CHANN_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>收货组织&nbsp;：</div>
									<textarea class="gdtd_select_input" json="recv_chann_name" id="RECV_CHANN_NAME"  READONLY>${fn:replace(entry.RECV_CHANN_NAME, "；", "&#13;")}</textarea>
									<input json="recv_chann_no"id="RECV_CHANN_NO"  value="${entry.RECV_CHANN_NO}"  hidden="true">
									<input json="recv_chann_id"id="RECV_CHANN_ID"  value="${entry.RECV_CHANN_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>经销商名称 ：</div>
									<textarea class="gdtd_select_input" json="chann_name" name="CHANN_NAME" id="CHANN_NAME"   READONLY>${fn:replace(entry.CHANN_NAME, "；", "&#13;")}</textarea>
									<input json="chann_no"	id="CHANN_NO"  value="${entry.CHANN_NO}"  hidden="true">
									<input json="chann_id"	id="CHANN_ID"  value="${entry.CHANN_ID}" hidden="true">
									<input json="area_id"	id="AREA_ID"  value="${entry.AREA_ID}" hidden="true">
									<input json="area_no"	id="AREA_NO"  value="${entry.AREA_NO}" hidden="true">
									<input json="area_name"	id="AREA_NAME"  value="${entry.AREA_NAME}" hidden="true">
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>业务部门：</div>
									<textarea class="gdtd_select_input"  json="saledept_name"  id="SALEDEPT_NAME"  readonly="readonly">${fn:replace(entry.SALEDEPT_NAME, "；", "&#13;")}</textarea>
									<input json="saledept_id"	id="SALEDEPT_ID"  value="${entry.SALEDEPT_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>业务员&nbsp;：</div>
									<textarea class="gdtd_select_input" json="sale_name" id="SALE_NAME" readonly="readonly">${fn:replace(entry.SALE_NAME, "；", "&#13;")}</textarea>
									<input json="sale_id"	id="SALE_ID"  value="${entry.SALE_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>标签打印名称：</div>
									<textarea style="width: 191.5%;" row="1" json="print_name" name="PRINT_NAME" id="PRINT_NAME"  READONLY>${fn:replace(entry.PRINT_NAME, "；", "&#13;")}</textarea>
								</td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">收货信息</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>运输方式 &nbsp;：</div>
									<select class="gdtd_select_input" json="transport" name="TRANSPORT" id="TRANSPORT"   type="text" ></select>
								</td>
								<td class="gdtd_tb">
									<div>运输结算方式：</div>
									<input class="gdtd_select_input" json="transport_settle" name="TRANSPORT_SETTLE" id="TRANSPORT_SETTLE"  type="text"       value="${entry.TRANSPORT_SETTLE}">
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>收货人 ：</div>
									<input class="gdtd_select_input" json="person_con" name="PERSON_CON" id="PERSON_CON"   type="text"       value="${entry.PERSON_CON}">
								</td>
								<td class="gdtd_tb">
									<div>联系电话：</div>
									<input class="gdtd_select_input" json="tel" name="TEL" id="TEL"  type="text"       value="${entry.TEL}">
								</td>
								<td class="gdtd_tb">
									<div>收货地址：</div>
									<textarea style="width: 188.5%;" rows="1"  json="recv_addr" name="RECV_ADDR" id="RECV_ADDR" >${fn:replace(entry.RECV_ADDR, "；", "&#13;")}</textarea>
								</td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>客户姓名 ：</div>
									<textarea class="gdtd_select_input" json="cust_name" name="CUST_NAME"  READONLY>${fn:replace(entry.CUST_NAME, "；", "&#13;")}</textarea>
								</td>
								<td class="gdtd_tb">
									<div>客户电话：</div>
									<input class="gdtd_select_input" json="cust_tel" name="CUST_TEL" id="CUST_TEL"   type="text"       value="${entry.CUST_TEL}"READONLY>
								</td>
								<td class="gdtd_tb">
									<div>客户地址：</div>
									<textarea style="width: 188.5%;" rows="1"  json="cust_addr" name="CUST_ADDR" id="CUST_ADDR" READONLY>${entry.CUST_ADDR}</textarea>
								</td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">订单处理</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>厂编 ：</div>
									<input class="gdtd_select_input" json="factory_no"  name="FACTORY_NO" id="FACTORY_NO"   type="text"       value="${entry.FACTORY_NO}"readonly="readonly">
								</td>
								<td class="gdtd_tb"> 
									<div>处理类型：</div>
									<input class="gdtd_select_input" json="proess_type" name="PROESS_TYPE" id="PROESS_TYPE"  type="text"       value="${entry.PROESS_TYPE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>备注2：</div>
									<textarea style="width: 188.5%;" rows="5" json="remark2"  name="REMARK2" id="REMARK2"   READONLY>${fn:replace(entry.REMARK2, "；", "&#13;")}</textarea>
								</td>
								<td class="gdtd_tb">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</form>
	<div class="detail" style="">
		<table style="float: left;padding: 9px 0% 0;">
			<tr>
				<td class="title">明细信息</td>
			</tr>
		</table>
	</div>
	<div class="bodycss1 child">
		<form  autocomplete="off">
			<table width="100%" id="jsontb"  border="0" cellpadding="5" cellspacing="0" style="border-collapse: collapse;" class="lst" >
	            <tr>
	                <th nowrap align="center" class="" ><input  type="checkbox" name="" id="allChecked"></th>
	                <th nowrap align="center"  class="LMM">组号</th>
	                <th nowrap align="center" class="">产品编码</th>
	               <th nowrap align="center" class="">产品名称</th>
	                <th nowrap align="center"  class="LMM">门洞尺寸</th>
	                <th nowrap align="center" class="">尺寸</th>
	                <th nowrap align="center"  class="LMM LYGCG" >材质</th>
	                <th nowrap align="center"  class="LMM LYGCG">颜色</th>
	                <th nowrap align="center"  class="LMM">推向</th>
	                <th nowrap align="center"  class="LMM">玻璃</th>
	                <th nowrap align="center"  class="LMM" >其他</th>
	                <th nowrap align="center"  class="LMM">系列</th>
	                <th nowrap align="center"  class=" LYGCG">投影面积</th>
	                <th nowrap align="center"  class=" LYGCG">展开面积</th>
	                <th nowrap align="center" class=" ">工程位置</th>
					<th  nowrap align="center" class="LMM">是否开锁孔</th>
	                <th nowrap align="center" class="">订货数量</th>
	                <th nowrap align="center" class="">实际发货数量</th>
	                <th nowrap align="center" class="">可发货数量</th>
	                <th nowrap align="center" class="">发货数量</th>
	                <!-- <th nowrap>可用库存</th> -->
	                <th nowrap align="center" >单位</th>
	                <th nowrap align="center" class="">计算报价</th>
	                <th nowrap align="center" class="">折扣率(%)</th>
	                <th nowrap align="center" class="">返利单价</th>
	                <th nowrap align="center" class="">返利金额</th>
	                <th nowrap align="center" class="">最终报价</th>
	                <th nowrap align="center" class="">发货金额</th> 
	               <!--  <th nowrap>金额</th> --> 
	                <th nowrap align="center" class="">备注</th>
	            </tr>
	            <c:forEach items="${entry.entrySun}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr style="height:42px;" class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" id="tr{rr}"><!-- onclick="selectThis(this);setEidChecked(document.getElementById('eid${rr}'));" -->
		                <td width="1%" align='center'  class="">
								<input  type="checkbox" json="send_order_dtl_id"  style="height:12px;valign:middle" name="send_order_dtl_id" id="eid${rr}" value="${rst.SEND_ORDER_DTL_ID}" checked>
								<input hidden="true"  json="sale_order_dtl_id"  value="${rst.SALE_ORDER_DTL_ID}"> 
						</td>
		                <td nowrap align="center"  class="LMM" >
		                	<input size="5"  style="text-align: center;"  json="group_no"  value="${rst.GROUP_NO}"READONLY> &nbsp;
		                	<input hidden="true"  json="prd_id"  value="${rst.PRD_ID}"> 
		                </td>
						<td nowrap align="center"  class="">
							<input size="10"  style="text-align: center;"  json="prd_no"  value="${rst.PRD_NO}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="">
							<input size="10"  style="text-align: center;"  json="prd_name"  value="${rst.PRD_NAME}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="LMM" >
							<input size="10"  style="text-align: center;"  json="hole_spec"  value="${rst.HOLE_SPEC}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="">
							<input size="10"  style="text-align: center;"  json="prd_spec"  value="${rst.PRD_SPEC}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="LMM LYGCG" >
							<input size="15"  style="text-align: center;"  json="prd_quality"  value="${rst.PRD_QUALITY}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="LMM LYGCG" >
							<input size="10"  style="text-align: center;"  json="prd_color"  value="${rst.PRD_COLOR}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="LMM" >
							<input size="10"  style="text-align: center;"  json="prd_toward"  value="${rst.PRD_TOWARD}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="LMM" >
							<input size="15"  style="text-align: center;"  json="prd_glass"  value="${rst.PRD_GLASS}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="LMM" >
							<input size="15"  style="text-align: center;"  json="prd_other"  value="${rst.PRD_OTHER}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="LMM" >
							<input size="15"  style="text-align: center;"  json="prd_series"  value="${rst.PRD_SERIES}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class=" LYGCG">
							<input size="10"  style="text-align: center;"  json="projected_area"  value="${rst.PROJECTED_AREA}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class=" LYGCG" >
							<input size="10" style="text-align: center;"  json="expand_area"  value="${rst.EXPAND_AREA}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class=" " >
							<input size="10" style="text-align: center;"  json="expand_area"  value="${rst.PRD_PLACE_TEXT}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="LMM" >
							<input size="10" style="text-align: center;"  json="expand_area"  value="${rst.IS_NO_LOCK_FLAG_TEXT}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" id="ORDER_NUM${rr}" class="">
							<input size="5"  style="text-align: center;"  json="order_num"  value="${rst.ORDER_NUM}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="">
							<input size="5"  style="text-align: center;"  json="sended_num"  value="${rst.SENDED_NUM}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="">
							<input size="5" id="WAITNUM${rr}"  style="text-align: center;"   value="${rst.WAITNUM}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="">
						  <input  type="text"  autocheck="true" inputtype="int"  label="发货数量"  json="send_num" mustinput="true" size="5" 
						  id="SEND_NUM${rr}" name="send_num"   value="${rst.SEND_NUM}" onkeyup="countPrice('${rr}')" style="text-align:center;">
						 </td>
						<%-- <td nowrap align="center">${rst.ORDER_NUM}&nbsp;</td> --%>
						<td nowrap align="center" class="">
							<input size="5"   style="text-align: center;"  json="std_unit"  value="${rst.STD_UNIT}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="">
							<input size="5"  style="text-align: center;"  json="price"  value="${rst.PRICE}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="">
							<input size="10"  style="text-align: center;"  json="dect_rate"  value="${rst.DECT_RATE}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="">
							<input type="text" id="REBATE_PRICE${rr}"  size="5"  json="rebate_price" name="dect_price" value="${rst.REBATE_PRICE}"  readonly="readonly">
						</td>
						<td nowrap align="center" class="">
							<input size="5"  id="REBATE_AMOUNT${rr}" style="text-align: center;"  json="rebate_amount"  value="${rst.REBATE_AMOUNT}"READONLY> &nbsp;
						</td>
						<td nowrap align="center" class="">
							<input type="text" id="DECT_PRICE${rr}"  size="5"  json="dect_price" name="DECT_PRICE" value="${rst.DECT_PRICE}"  readonly="readonly">
						</td>
						<td nowrap align="center"  class="">
							<input type="text" id="SEND_AMOUNT${rr}" json="send_amount" size="5" name="send_amount" value="${rst.SEND_AMOUNT}"  readonly="readonly">
						</td>
						<%-- <td nowrap align="center">${rst.ORDER_AMOUNT}&nbsp;</td> --%>
						<td nowrap align="center" class="">
							<input size="20"  style="text-align: center;"  json="remark"  value="${rst.REMARK}"READONLY> &nbsp;
							<input hidden="true"  json="prd_size"  value="${rst.PRD_SIZE}">
							<input hidden="true"  json="prd_place"  value="${rst.PRD_PLACE}">
							<input hidden="true"  json="is_no_rebate_flag"  value="${rst.IS_NO_REBATE_FLAG}">
							<input hidden="true"  json="is_no_lock_flag"  value="${rst.IS_NO_LOCK_FLAG}"> 
							<input hidden="true"  json="figure_no"  value="${rst.FIGURE_NO}"> 
							<input hidden="true"  json="row_no"  value="${rst.ROW_NO}"> 
						</td>
		            </tr>
	   			</c:forEach>
	   			<c:if test="${empty entry.entrySun}">
					<tr>
						<td style="height:42px;" colspan="22" align="center" class="lst_empty">
			                &nbsp;无相关记录&nbsp;
						</td>
					</tr>
				</c:if>
	        </table>
		</form>
    </div>
	<!--占位用table，以免显示数据被浮动层挡住-->
	<table width="100%" height="25px">
		<tr>  
			<td>&nbsp;</td>	
		</tr>
	</table>
	<form name="backForm" method="post">
		<input type="hidden" name="pageNo" value="${pageNo }">
		<input  type="hidden" id="selRowId" name="selRowId" value="${entry.SEND_ORDER_ID}">
	</form>
	<!-- 底部固定部分 -->
	<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
		<button class="img_input">
			<label for='save'>
				<img src="${ctx}/styles/${theme}/images/icon/baocun.png" class="icon_font"> 
				<input  id="save" type="button"class="btn" value="保存">
			</label>
		</button>
		<button class="img_input">
			<label onclick="closeDialog()">
				<img src="${ctx}/styles/${theme}/images/icon/chexiao.png" class="icon_font"> 
				<input  type="button" class="btn" value="返回">
			</label>
		</button>
	</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
	//185
	 $(function (){
	 	//运输方式
	 	SelDictShow("TRANSPORT","BS_185","${entry.TRANSPORT}","");
		//刷新发货金额
		<c:forEach items="${entry.entrySun}" var="rst" varStatus="row">
	 		<c:set var='rr' value='${row.count}'></c:set>
			 countPrice('${rr}');
		</c:forEach>
		//根据用户的账套信息显隐特定的字段
		 var lid = $("#LEDGER_ID").val();
 		//根据订单组织控制明细字段列的显示/隐藏
 		changeDtlCol(lid);
		displayDownFile('ATT_PATH',true);
			
	});
</script>
</html>