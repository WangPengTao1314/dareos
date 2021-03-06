<!-- 
 *@module 发货管理
 *@func 发货指令审核
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
<script type="text/javascript"
	src="${ctx}/pages/drp/main/deliver/order/Order_Check.js"></script>
	<style>
		.child{overflow-x: auto; width: 100%; }
		.toggle{
		width: 40%;
    float: right;
    padding: 0.8% 0px 0.5% 0px;
    text-align: right;
	}
	</style>
<title>发货单审核</title>
</head>
<body style="overflow-y: auto;overflow-x: auto;">
	<!--浮动按钮层结束-->
	<form name="mainForm" id="mainForm"  >
			<table  id="jsontb"  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
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
									<input class="gdtd_select_input" json="ledger_name" name="LEDGER_NAME" id="LEDGER_NAME"   type="text"       value="${entry.LEDGER_NAME}"readonly="readonly">
									<input json="ledger_id"id="LEDGER_ID"  value="${entry.LEDGER_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>订货组织&nbsp;：</div>
									<input class="gdtd_select_input" json="order_chann_name" name="ORDER_CHANN_NAME" id="ORDER_CHANN_NAME"   type="text"       value="${entry.ORDER_CHANN_NAME}"readonly="readonly">
									<input json="order_chann_no"id="ORDER_CHANN_NO"  value="${entry.ORDER_CHANN_NO}"  hidden="true">
									<input json="order_chann_id"id="ORDER_CHANN_ID"  value="${entry.ORDER_CHANN_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>收货组织&nbsp;：</div>
									<input class="gdtd_select_input" json="recv_chann_name"  name="RECV_CHANN_NAME" id="RECV_CHANN_NAME"  value="${entry.RECV_CHANN_NAME}" READONLY>
									<input json="recv_chann_no"id="RECV_CHANN_NO"  value="${entry.RECV_CHANN_NO}"  hidden="true">
									<input json="recv_chann_id"id="RECV_CHANN_ID"  value="${entry.RECV_CHANN_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>经销商名称 ：</div>
									<textarea class="gdtd_select_input" json="chann_name" name="CHANN_NAME" id="CHANN_NAME"   READONLY>${entry.CHANN_NAME}</textarea>
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
									<input class="gdtd_select_input"  json="saledept_name"  name="saledept_name" id="SALEDEPT_NAME"   type="text"       value="${entry.SALEDEPT_NAME}"readonly="readonly">
									<input json="saledept_id"	id="SALEDEPT_ID"  value="${entry.SALEDEPT_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>业务员&nbsp;：</div>
									<input class="gdtd_select_input" json="sale_name" name="SALE_NAME" id="SALE_NAME"   type="text"       value="${entry.SALE_NAME}"readonly="readonly">
									<input json="sale_id"	id="SALE_ID"  value="${entry.SALE_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>标签打印名称：</div>
									<textarea style="width: 190.5%;" row="1" json="print_name" name="PRINT_NAME" id="PRINT_NAME"  READONLY>${entry.PRINT_NAME}</textarea>
								</td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">收货信息</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>运输方式 &nbsp;：</div>
									<input class="gdtd_select_input" json="transport" name="TRANSPORT" id="TRANSPORT"   type="text"       value="${entry.TRANSPORT}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>运输结算方式：</div>
									<input class="gdtd_select_input" json="transport_settle" name="TRANSPORT_SETTLE" id="TRANSPORT_SETTLE"  type="text"       value="${entry.TRANSPORT_SETTLE}"readonly="readonly">
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>收货人 ：</div>
									<input class="gdtd_select_input" json="person_con" name="PERSON_CON" id="PERSON_CON"   type="text"       value="${entry.PERSON_CON}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>联系电话：</div>
									<input class="gdtd_select_input" json="tel" name="TEL" id="TEL"  type="text"       value="${entry.TEL}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>收货地址：</div>
									<textarea style="width: 190.5%;" rows="1"  json="recv_addr" name="RECV_ADDR" id="RECV_ADDR" READONLY>${entry.RECV_ADDR}</textarea>
								</td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>客户姓名 ：</div>
									<input class="gdtd_select_input" json="cust_name" name="CUST_NAME"   type="text"       value="${entry.CUST_NAME}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>客户电话：</div>
									<input class="gdtd_select_input" json="cust_tel" name="CUST_TEL" id="CUST_TEL"   type="text"       value="${entry.CUST_TEL}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>客户地址：</div>
									<textarea style="width: 190.5%;" rows="1"  json="cust_addr" name="CUST_ADDR" id="CUST_ADDR" READONLY>${entry.CUST_ADDR}</textarea>
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
									<textarea style="width: 190.5%;" rows="1" json="remark2"  name="REMARK2" id="REMARK2"   READONLY>${fn:replace(entry.REMARK2, "；", "&#13;")}</textarea>
								</td>
								<td class="gdtd_tb">
								</td>
							</tr>
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">审核信息</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>审核意见:</div>
									<textarea style="width: 190.5%;" json="check_advice" name="check_advice" id="check_advice" inputtype="string" rows="5"
									maxlength="200"  placeholder="请填写审核意见" ></textarea>
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</form>
	</div>
	<div class="bodycss1 child">
		<table  id="jsontb_div"  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
			<tr>
				<td class="detail2" style="border-bottom:none">
					<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">订单明细</div>
				</td>
			</tr>
			<tr>
				<td>
					<div style="vertical-align: middle;">
						<form>
							<table width="99%" id="jsontb"  border="0" style="border-collapse: collapse" class="lst">
					           <tr>
									<!-- <th width="60"><input type="checkbox" name=""
										id="allChecked"></th> -->
									<th width="60"  class="LMM">组号</th>
									<th width="120" class="">产品编码</th>
									<th width="120" class="">产品名称</th>
									<th width="120" class="LMM">门洞尺寸</th>
									<th width="120" class="">尺寸</th>
									<th width="120" class="LMM">材质</th>
									<th width="120" class="LMM LYGCG">颜色</th>
									<th width="120" class="LMM">推向</th>
									<th width="120" class="LMM">玻璃</th>
									<th width="120" class="LMM">其他</th>
									<th width="120" class="LMM">系列</th>
									<th width="120" class=" LYGCG">投影面积</th>
									<th width="120" class=" LYGCG">展开面积</th>
									<th nowrap align="center" class=" ">工程位置</th>
									<th  nowrap align="center" class="LMM">是否开锁孔</th>
									<th width="120" class="">订货数量</th>
									<th width="120" class="">实际发货数量</th>
									<!-- <th width="120">可发货数量</th> -->
									<th width="120" class="">发货数量</th>
									<!-- <th width="120">可用库存</th> -->
									<th width="120" class="">单位</th>
									<th width="120" class="">计算报价</th>
									<th width="120" class="">折扣率(%)</th>
									<th width="120" class="">返利金额</th>
									<th width="120" class="">最终报价</th>
									<th width="120" class="">发货金额</th>
									<!--  <th width="120">金额</th> -->
									<th width="120" class="">备注</th>
								</tr>
					            <c:forEach items="${entry.entrySun}" var="rst" varStatus="row">
									<c:set var="r" value="${row.count % 2}"></c:set>
									<c:set var="rr" value="${row.count}"></c:set>
									<tr style="height:42px;" class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" id="tr{rr}">
						                <td width="120" align="center" class="LMM" >${rst.GROUP_NO}&nbsp;</td>
										<td width="120" align="center" >${rst.PRD_NO}&nbsp;</td>
										<td width="120" align="center">${rst.PRD_NAME}&nbsp;</td>
										<td width="120" align="center" class="LMM" >${rst.HOLE_SPEC}&nbsp;</td>
										<td width="120" align="center">${rst.PRD_SPEC}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG" >${rst.PRD_QUALITY}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG" >${rst.PRD_COLOR}&nbsp;</td>
										<td width="120" align="center" class="LMM" >${rst.PRD_TOWARD}&nbsp;</td>
										<td width="120" align="center" class="LMM" >${rst.PRD_GLASS}&nbsp;</td>
										<td width="120" align="center" class="LMM" >${rst.PRD_OTHER}&nbsp;</td>
										<td width="120" align="center" class="LMM" >${rst.PRD_SERIES}&nbsp;</td>
										<td width="120" align="center" class=" LYGCG" >${rst.PROJECTED_AREA}&nbsp;</td>
										<td width="120" align="center" class=" LYGCG" >${rst.EXPAND_AREA}&nbsp;</td>
										<td nowrap align="center" class=" " >
											${rst.PRD_PLACE_TEXT} &nbsp;
										</td>
										<td nowrap align="center" class="LMM" >
											${rst.IS_NO_LOCK_FLAG_TEXT} &nbsp;
										</td>
										<td width="120" align="center" class="">${rst.ORDER_NUM}&nbsp;</td>
										<td width="120" align="center" class="">${rst.SENDED_NUM}&nbsp;</td>
										<td width="120" align="center" class="">${rst.SEND_NUM}</td>
										<td width="120" align="center" class="">${rst.STD_UNIT}&nbsp;</td>
										<td width="120" align="center" class="">${rst.PRICE}&nbsp;</td>
										<td width="120" align="center" class="">${rst.DECT_RATE}&nbsp;</td>
										<td width="120" align="center" class="">${rst.DECT_PRICE}&nbsp;</td>
										<td width="120" align="center" class="">${rst.SEND_AMOUNT}&nbsp;</td>
										<td width="120" align="center" class="">${rst.REMARK}&nbsp;</td>
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
	<!-- 底部固定部分 -->
		<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
			<button class="img_input">
				<label for='reject'>  
				   <img src="${ctx}/styles/${theme}/images/icon/tuihui.png"  class="icon_font">
				 	<input  id="reject" type="button" class="btn" value="退回">
				</label>
			</button>
			<button class="img_input">
				<label for='pass'>  
					<img src="${ctx}/styles/${theme}/images/icon/pass.png"  class="icon_font">
				 	<input id="pass" type="button" class="btn" value="审核通过">
				</label>
			</button>
			<button class="img_input">
				<label onclick="closeDialog()"> 
					<img src="${ctx}/styles/${theme}/images/icon/chexiao.png" class="icon_font"> 
					<input type="button" class="btn" value="返回">
				</label>
			</button>
	</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
	$(function(){
		//单一上传
  		 displayDownFile('ATT_PATH',true);
		//SelDictShow("", "BS_183", "", "");
		var lid = '${entry.LEDGER_ID}';
	 	//根据订单组织控制明细字段列的显示/隐藏
	 	changeDtlCol(lid);
	});
</script>
</html>
