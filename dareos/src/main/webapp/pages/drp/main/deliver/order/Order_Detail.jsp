<!-- 
 *@module 发货管理
 *@func 发货指令明细
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
<title>发货单明细</title>
</head>
<body>
	<!--浮动按钮层结束-->
	<form name="mainForm" id="mainForm">
		<table  id="jsontbP"  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
				<tr>
					<td class="detail2">
						<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable"  class="detail3"
						style="background-color: #fff; padding: 0 0 0 2%; border-collapse: separate; border-spacing: 10px;border-top:1px solid #e8edf2;;border-left:1px solid #e8edf2;;border-right:1px solid #e8edf2;;margin:0 auto">
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;overflow:hidden">基本信息</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>发货单号 &nbsp;：</div>
									${entry.SEND_ORDER_NO}
										<input class="gdtd_select_input" json="send_order_id"  name="send_order_id"  id="SEND_ORDER_ID" type="text" hidden="true"
										autocheck="true" inputtype="string" label="发货单id" value="${entry.SEND_ORDER_ID}"  maxlength="32">
								</td>
								<td class="gdtd_tb">
									<div>订单编号：</div>
								    ${entry.FACTORY_NO}
								</td>
								<td class="gdtd_tb">
									<div>当前状态&nbsp;：</div>
									${entry.STATE}
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>预计到货日期&nbsp;：</div>
									${entry.PRE_RECV_DATE}
								</td>
								<td class="gdtd_tb">
									<div>下单日期 &nbsp;：</div>
									${entry.ORDER_DATE}
								</td>
								<td class="gdtd_tb">
									<div>订单交期 &nbsp;：</div>
									${entry.ORDER_DELIVERY_DATE}
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>销售类型 &nbsp;：</div>
									${entry.BILL_TYPE}
								</td>
								<td class="gdtd_tb">
									<div>订单图纸：<button type="button" class="layui-btn uploadFile" id="ATT_PATH" lay-data="{id:'ATT_PATH'}" disabled>上传</button></div>
									<%-- <input json="attPath" name="attPath" id="attPath" value="${rst.attPath}"> --%>
									<input type="hidden" json="ATT_PATH" name="ATT_PATH" id="hid_ATT_PATH" value="${entry.ATT_PATH}">
									<table class="layui-table" style="width:85%" id="view_ATT_PATH"></table>
								</td>
								<td class="gdtd_tb">
									<div>经销商备注：</div>
									${fn:replace(entry.REMARK, "；", "&#13;")}
								</td>
							</tr>
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;overflow:hidden">订货信息</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>订单组织 &nbsp;：</div>
									${entry.LEDGER_NAME}
									<input json="ledger_id"id="LEDGER_ID"  value="${entry.LEDGER_ID}" hidden="true">
								</td>
								<td class="gdtd_tb">
									<div>订货组织&nbsp;：</div>
									${entry.ORDER_CHANN_NAME}
								</td>
								<td class="gdtd_tb">
									<div>收货组织&nbsp;：</div>
									${entry.RECV_CHANN_NAME}
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>经销商名称 ：</div>
									${entry.CHANN_NAME}
								</td>
								<td class="gdtd_tb">
									<div>业务部门：</div>
									${entry.SALEDEPT_NAME}
								</td>
								<td class="gdtd_tb">
									<div>业务员&nbsp;：</div>
									${entry.SALE_NAME}
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>标签打印名称：</div>
									${entry.PRINT_NAME}
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;overflow:hidden">收货信息</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>运输方式 &nbsp;：</div>
									${entry.TRANSPORT}
								</td>
								<td class="gdtd_tb">
									<div>运输结算方式：</div>
									${entry.TRANSPORT_SETTLE}
								</td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>收货人 ：</div>
									${entry.PERSON_CON}
								</td>
								<td class="gdtd_tb">
									<div>联系电话：</div>
									${entry.TEL}
								</td>
								<td class="gdtd_tb">
									<div>收货地址：</div>
									${entry.RECV_ADDR}
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>客户姓名 ：</div>
									${entry.CUST_NAME}
								</td>
								<td class="gdtd_tb">
									<div>客户电话：</div>
									${entry.CUST_TEL}
								</td>
								<td class="gdtd_tb">
									<div>客户地址：</div>
									${entry.CUST_ADDR}
								</td>
							</tr>
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;overflow:hidden">订单处理</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>厂编 ：</div>
									${entry.FACTORY_NO}
								</td>
								<td class="gdtd_tb"> 
									<div>处理类型：</div>
									${entry.PROESS_TYPE}
								</td>
								<td class="gdtd_tb">
									<div>备注2：</div>
									<%-- ${fn:replace(entry.REMARK2, "；", "&#13;")} --%>
									<c:set value="${fn:split(entry.REMARK2, '；') }" var="remark2" />
									<c:forEach items="${ remark2 }" var="s">${ s }<br/></c:forEach>
								</td>
							</tr>
							<tr>
								<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;overflow:hidden">审核信息</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>审核意见 ：</div>
									${entry.CHECK_ADVICE}
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</form>
	<div class="bodycss1 child">
		<table id="jsontb_div" width="98%" height="" border="0" cellSpacing=0
			cellPadding=0 style="margin-left: 1%">
			<tr>
				<td align="left" colspan="8"
					style="font-size: 16px; font-weight: 600; color: #a8afb3; overflow: hidden">
					<div
						style="height: 40px; line-height: 40px; color: #54698d; font-weight: 500; margin-left: 0.5%; font-size: 17px;">明细信息</div>
				</td>
			</tr>
			<tr>
				<td>
					<div  style="vertical-align: middle;">
						<form>
							<table width="99%" id="jsontb" border="0"
								style="border-collapse: collapse" class="lst">
								<tr>
									<!-- <th width="60"><input type="checkbox" name=""
										id="allChecked"></th> -->
									<th width="60" class="LMM">组号</th>
									<th width="120" class="">产品编码</th>
									<th width="120" class="">产品名称</th>
									<th width="120" class="LMM">门洞尺寸</th>
									<th width="120" class="">尺寸</th>
									<th width="120" class="LMM LYGCG">材质</th>
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
									<tr style="height: 42px;" class="list_row${r}"
										onmouseover="mover(this)" onmouseout="mout(this)" id="tr{rr}"
										onclick="selectThis(this);setEidChecked(document.getElementById('eid${rr}'));">
										<td width="120" align="center" class="LMM">${rst.GROUP_NO}&nbsp;</td>
										<td width="120" align="center" class="">${rst.PRD_NO}&nbsp;</td>
										<td width="120" align="center" class="">${rst.PRD_NAME}&nbsp;</td>
										<td width="120" align="center" class="LMM">${rst.HOLE_SPEC}&nbsp;</td>
										<td width="120" align="center" class="">${rst.PRD_SPEC}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG">${rst.PRD_QUALITY}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG">${rst.PRD_COLOR}&nbsp;</td>
										<td width="120" align="center" class="LMM">${rst.PRD_TOWARD}&nbsp;</td>
										<td width="120" align="center" class="LMM">${rst.PRD_GLASS}&nbsp;</td>
										<td width="120" align="center" class="LMM">${rst.PRD_OTHER}&nbsp;</td>
										<td width="120" align="center" class="LMM">${rst.PRD_SERIES}&nbsp;</td>
										<td width="120" align="center" class=" LYGCG">${rst.PROJECTED_AREA}&nbsp;</td>
										<td width="120" align="center" class=" LYGCG">${rst.EXPAND_AREA}&nbsp;</td>
										<td nowrap align="center" class=" " > ${rst.PRD_PLACE_TEXT} &nbsp; </td>
										<td nowrap align="center" class="LMM" > ${rst.IS_NO_LOCK_FLAG_TEXT} &nbsp; </td>
										<td width="120" align="center" class="">${rst.ORDER_NUM}&nbsp;</td>
										<td width="120" align="center" class="">${rst.SENDED_NUM}&nbsp;</td>
										<%-- <td width="120" align="center" >${rst.WAITNUM}</td> --%>
										<td width="120" align="center" class="">${rst.SEND_NUM}</td>
										<%-- <td width="120" align="center">${rst.ORDER_NUM}&nbsp;</td> --%>
										<td width="120" align="center" class="">${rst.STD_UNIT}&nbsp;</td>
										<td width="120" align="center" class="">${rst.PRICE}&nbsp;</td>
										<td width="120" align="center" class="">${rst.DECT_RATE}&nbsp;</td>
										<td width="120" align="center" class="">${rst.REBATE_AMOUNT}&nbsp;</td>
										<td width="120" align="center" class="">${rst.DECT_PRICE}</td>
										<td width="120" align="center" class="">${rst.SEND_AMOUNT}</td>
										<%-- <td width="120" align="center">${rst.ORDER_AMOUNT}&nbsp;</td> --%>
										<td width="120" align="center" class="">${rst.REMARK}&nbsp;</td>
									</tr>
								</c:forEach>
								<c:if test="${empty entry.entrySun}">
									<tr>
										<td style="height: 42px;" colspan="22" align="center"
											class="lst_empty">&nbsp;无相关记录&nbsp;</td>
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
	<div style="width: 100%; height: 52px"></div>
	<div
		style="width: 100%; padding: 0.5%; background: #F8F8F8; text-align: center; position: fixed; bottom: 0; word-spacing: 8px;">
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
	$(function (){
		//单一上传
		btnDisable([ "pass", "reject" ]);
		displayDownFile('ATT_PATH',true);
		var lid = '${entry.LEDGER_ID}';
		//根据订单组织控制明细字段列的显示/隐藏
		changeDtlCol(lid);
	});
</script>
</html>
