<!-- 
 *@module 发货管理
 *@func 审核
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
<script type="text/javascript" src="${ctx}/pages/drp/main/deliver/repairShip/RepairShip_Check.js"></script>
	<style>
			.toggle{
		width: 40%;
    float: right;
    padding: 0.8% 0px 0.5% 0px;
    text-align: right;
	}
	.child{overflow-x: auto}
	</style>
<title>发货单编辑</title>
</head>
<body style="overflow-y: auto;overflow-x: auto;">
	<!--浮动按钮层结束-->
	<form name="mainForm" id="mainForm"  >
				<%-- <table   width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
				<tr>
					<td class="detail2" style="border-bottom:none">
										<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">订单信息</div>
										<div class="toggle">
											<input type="button"  id="ipt"  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;" onclick='changeinput(this)'>
											<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="vertical-align: middle;" id="imgsz">
										</div>
					</td>
				</tr>
				</table> --%>
				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="detail" style="    border: 1px solid #e8edf2" id="myTable">
				<tr>
					<td class="detail4">
						<div class="table_title dashed title_font">基本信息</div>
					</td>
				</tr>
				<tr>
					<td class="detail4">
						<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable1"  class=" detail3 table_detail">
							<tr>
								<td class="gdtd_tb">
									<div>发货单号：</div>
									<input class="gdtd_select_input" name="send_order_no"  id="SEND_ORDER_NO" type="text"
										autocheck="true" inputtype="string" label="发货单号" placeholder="自动生成"  maxlength="30"
										  value="${entry.SEND_ORDER_NO}">
										<input class="gdtd_select_input" name="send_order_id"  id="SEND_ORDER_ID" type="text" hidden="true"
										autocheck="true" inputtype="string" label="发货单id" value="${entry.SEND_ORDER_ID}"  maxlength="32">
								</td>
								<td class="gdtd_tb">
									<div>订单编号：</div>
								    <input class="gdtd_select_input" name="SALE_ORDER_NO" id="SALE_ORDER_NO" placeholder="请选择订单编号" type="text"  seltarget="selJGXX"  value="${entry.SALE_ORDER_NO}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>当前状态&nbsp;：</div>
									<input class="gdtd_select_input" name="STATE" id="STATE"   type="text"  seltarget="selJGXX"  value="${entry.STATE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>销售类型 &nbsp;：</div>
									<input class="gdtd_select_input" name="BILL_TYPE" id="BILL_TYPE"   type="text"  seltarget="selJGXX"  value="${entry.BILL_TYPE}"readonly="readonly">
								</td>
							<tr>
							<tr>
								<td class="gdtd_tb">
									<div>预计到货日期&nbsp;：</div>
									<input class="gdtd_select_input" name="PRE_RECV_DATE" id="PRE_RECV_DATE"  type="text"  seltarget="selJGXX"  value="${entry.PRE_RECV_DATE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>下单日期 &nbsp;：</div>
									<input class="gdtd_select_input" name="ORDER_DATE" id="ORDER_DATE"  type="text"  seltarget="selJGXX"  value="${entry.ORDER_DATE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>订单交期 &nbsp;：</div>
									<input class="gdtd_select_input" name="ORDER_DELIVERY_DATE" id="ORDER_DELIVERY_DATE"   type="text"  seltarget="selJGXX"  value="${entry.ORDER_DELIVERY_DATE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>
										订单图纸&nbsp;：
										<button type="button" class="layui-btn uploadFile"
											id="ATT_PATH" lay-data="{id:'ATT_PATH'}" disabled="true">上传</button>
									</div> 
									<input type="hidden" json="ATT_PATH" name="ATT_PATH" id="hid_ATT_PATH" value="${entry.ATT_PATH}">
									<table class="layui-table" style="width: 85%"
										id="view_ATT_PATH"></table> 
								</td> 
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>备注：</div>
									<input class="gdtd_select_input" name="REMARK" id="REMARK"   type="text"  seltarget="selJGXX"  value="${entry.REMARK}"readonly="readonly">
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="detail4">
								<div class="table_title dashed title_font">订货信息</div>
					</td>
				</tr>
				<tr>
					<td class="detail4">
						<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable2"  class="detail3 table_detail">
							<tr>
								<td class="gdtd_tb">
									<div>订单组织 &nbsp;：</div>
									<input class="gdtd_select_input" name="LEDGER_NAME" id="LEDGER_NAME"   type="text"  seltarget="selJGXX"  value="${entry.LEDGER_NAME}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>订货组织&nbsp;：</div>
									<input class="gdtd_select_input" name="ORDER_CHANN_NAME" id="ORDER_CHANN_NAME"   type="text"  seltarget="selJGXX"  value="${entry.ORDER_CHANN_NAME}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>收货组织&nbsp;：</div>
									<input class="gdtd_select_input"   name="RECV_CHANN_NAME" id="RECV_CHANN_NAME"  value="${entry.RECV_CHANN_NAME}">
								</td>
								<td class="gdtd_tb">
									<div>经销商名称 ：</div>
									<input class="gdtd_select_input" name="CHANN_NAME" id="CHANN_NAME"   type="text"  seltarget="selJGXX"  value="${entry.CHANN_NAME}"readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>标签打印名称：</div>
									<input class="gdtd_select_input" name="PRINT_NAME" id="PRINT_NAME"  type="text"  seltarget="selJGXX"  value="${entry.PRINT_NAME}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>业务部门：</div>
									<input class="gdtd_select_input" name="SALEDEPT_NAME" id="SALEDEPT_NAME"   type="text"  seltarget="selJGXX"  value="${entry.SALEDEPT_NAME}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>业务员&nbsp;：</div>
									<input class="gdtd_select_input" name="SALE_NAME" id="SALE_NAME"   type="text"  seltarget="selJGXX"  value="${entry.SALE_NAME}"readonly="readonly">
								</td>
								<td class="gdtd_tb"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="detail4">
					<div class="table_title dashed title_font">收货信息</div>
					</td>
				</tr>
				<tr>
					<td class="detail4">
						<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable3"  class="detail3 table_detail">
							<tr>
								<td class="gdtd_tb">
									<div>运输方式 &nbsp;：</div>
									<input class="gdtd_select_input" name="TRANSPORT" id="TRANSPORT"   type="text"  seltarget="selJGXX"  value="${entry.TRANSPORT}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>运输结算方式&nbsp;：</div>
									<input class="gdtd_select_input" name="TRANSPORT_SETTLE" id="TRANSPORT_SETTLE"  type="text"  seltarget="selJGXX"  value="${entry.TRANSPORT_SETTLE}"readonly="readonly">
								</td>
								<td class="gdtd_tb"></td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>收货人 ：</div>
									<input class="gdtd_select_input" name="PERSON_CON" id="PERSON_CON"   type="text"  seltarget="selJGXX"  value="${entry.PERSON_CON}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>联系电话：</div>
									<input class="gdtd_select_input" name="TEL" id="TEL"  type="text"  seltarget="selJGXX"  value="${entry.TEL}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>收货地址：</div>
									<input class="gdtd_select_input" name="RECV_ADDR" id="RECV_ADDR"   type="text"  seltarget="selJGXX"  value="${entry.RECV_ADDR}"readonly="readonly">
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>客户姓名 ：</div>
									<input class="gdtd_select_input" name="PERSON_CON" id="PERSON_CON"  type="text"  seltarget="selJGXX"  value="${entry.PERSON_CON}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>客户电话：</div>
									<input class="gdtd_select_input" name="CUST_TEL" id="CUST_TEL"   type="text"  seltarget="selJGXX"  value="${entry.CUST_TEL}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>客户地址：</div>
									<input class="gdtd_select_input" name="CUST_ADDR" id="CUST_ADDR"  type="text"  seltarget="selJGXX"  value="${entry.CUST_ADDR}"readonly="readonly">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="detail4">
					<div class="table_title dashed title_font">订单处理</div>
					</td>
				</tr>
				<tr>
					<td class="detail4">
						<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable4"  class="detail3  table_detail">
							<tr>
								<td class="gdtd_tb">
									<div>厂编 ：</div>
									<input class="gdtd_select_input" name="FACTORY_NO" id="FACTORY_NO"   type="text"  seltarget="selJGXX"  value="${entry.FACTORY_NO}"readonly="readonly">
								</td>
								<td class="gdtd_tb"> 
									<div>处理类型：</div>
									<input class="gdtd_select_input" name="PROESS_TYPE" id="PROESS_TYPE"  type="text"  seltarget="selJGXX"  value="${entry.PROESS_TYPE}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
									<div>备注2：</div>
									<input class="gdtd_select_input" name="REMARK2" id="REMARK2" type="text"  seltarget="selJGXX"  value="${entry.REMARK2}"readonly="readonly">
								</td>
								<td class="gdtd_tb">
								</td>
							</tr>
						</table>
					</td>
				</tr>
					<tr>
					<td class="detail4">
					<div class="table_title dashed title_font">订单审核</div>
					</td>
				</tr>
				<tr>
				<td class="detail4">
						<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable5"  class="detail3  table_detail">
							<tr>
								<td class="gdtd_tb">
									<div>审核意见:</div>
									<textarea style="width: 216.5%;" json="check_advice" name="check_advice" id="check_advice" inputtype="string" rows="3"
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
					                <th nowrap align="center"  class="LMM">组号</th>
					                <th nowrap align="center"  class="">产品编码</th>
					               <th nowrap align="center"  class="">产品名称</th>
					                <th nowrap align="center"  class="LMM">门洞尺寸</th>
					                <th nowrap align="center"  class="">尺寸</th>
					                <th nowrap align="center" class="LMM LYGCG" >材质</th>
					                <th nowrap align="center"  class="LMM LYGCG" >颜色</th>
					                <th nowrap align="center"  class="LMM">推向</th>
					                <th nowrap align="center"  class="LMM">玻璃</th>
					                <th nowrap align="center"  class="LMM">其他</th>
					                <th nowrap align="center"  class="LMM">系列</th>
					                <th nowrap align="center"  class=" LYGCG">投影面积</th>
					                <th nowrap align="center"  class=" LYGCG">展开面积</th>
					                <th nowrap align="center"  class="">订货数量</th>
					                <th nowrap align="center"  class="">实际发货数量</th>
					                <th nowrap align="center"  class="">可发货数量</th>
					                <th nowrap align="center"  class="">发货数量</th>
					                <!-- <th nowrap>可用库存</th> -->
					                <th nowrap align="center"  class="">单位</th>
					                <th nowrap align="center"  class="">计算报价</th>
					                <th nowrap align="center"  class="">折扣率(%)</th>
					                <th nowrap align="center"  class="">返利金额</th>
					                <th nowrap align="center"  class="">最终报价</th>
					                <th nowrap align="center"  class="">发货金额</th> 
					               <!--  <th nowrap>金额</th> --> 
					                <th nowrap align="center" >备注</th>
					            </tr>
					            <c:forEach items="${entry.entrySun}" var="rst" varStatus="row">
									<c:set var="r" value="${row.count % 2}"></c:set>
									<c:set var="rr" value="${row.count}"></c:set>
									<tr style="height: 42px;" class="list_row${r}"
										onmouseover="mover(this)" onmouseout="mout(this)" id="tr{rr}">
										<td width="120" align="center" class="LMM">${rst.GROUP_NO}&nbsp;</td>
										<td width="120" align="center">${rst.PRD_NO}&nbsp;</td>
										<td width="120" align="center">${rst.PRD_NAME}&nbsp;</td>
										<td width="120" align="center" class="LMM">${rst.HOLE_SPEC}&nbsp;</td>
										<td width="120" align="center">${rst.PRD_SPEC}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG">${rst.PRD_QUALITY}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG">${rst.PRD_COLOR}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG">${rst.PRD_TOWARD}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG">${rst.PRD_GLASS}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG">${rst.PRD_OTHER}&nbsp;</td>
										<td width="120" align="center" class="LMM LYGCG">${rst.PRD_SERIES}&nbsp;</td>
										<td width="120" align="center" class=" LYGCG">${rst.PROJECTED_AREA}&nbsp;</td>
										<td width="120" align="center" class=" LYGCG">${rst.EXPAND_AREA}&nbsp;</td>
										<td width="120" align="center" class="">${rst.ORDER_NUM}&nbsp;</td>
										<td width="120" align="center" class="">${rst.SENDED_NUM}&nbsp;</td>
										<td width="120" align="center" >${rst.WAITNUM}</td>
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
	//单一上传
   	uploadFile('ATT_PATH','',true,false,true,false,true);
	var ZTMC='${entry.LEDGER_ID}';
	changeDtlCol(ZTMC);
	
</script>
</html>
