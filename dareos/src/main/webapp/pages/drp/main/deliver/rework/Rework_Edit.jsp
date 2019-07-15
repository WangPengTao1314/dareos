<!--  
 售后管理模块
 返修单编辑
-->
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
	<title>返修单编辑</title>
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/skin/jedate.css">
	<script type="text/javascript" src="${ctx}/pages/common/selproduct/selproduct.js"></script>
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/drp/main/deliver/rework/Rework_Edit.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		.child{overflow-x: auto}
	</style>
</head>
<body class="add_demoone" style=" overflow-y:auto; overflow-x:auto;" >
   <!--  <span class="add_demospan">当前位置：订单中心>返修管理</span> -->
	<form name="mainForm" id="mainForm">
		<input type="hidden" id="initSel" name="initSel" value=" CHANN_ID = '${entry.CHANN_ID }' ">
		<input type="hidden" id="XTYHID"  value="${ADMIN}">
		<table id="jsontbP" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
			<tr>
				<td class="detail2">
					<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable"  class="detail3"
						style="background-color: #fff; padding: 0 0 0 2%; border-collapse: separate; border-spacing: 10px;border-top:1px solid #e8edf2;;border-left:1px solid #e8edf2;;border-right:1px solid #e8edf2;;margin:0 auto">
						<tr>
							<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">基本信息</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>销售类型：</div>
								<input class="gdtd_select_input" json="bill_type" id="BILL_TYPE" name="BILL_TYPE"
									label="销售类型" autocheck="true" inputtype="string" mustinput="true" value="返修订单"  READONLY>
							</td>
							<td class="gdtd">
								<div> 返修单号：</div>
								<input class="gdtd_select_input" type="text"
									id="FACTORY_NO" value="${entry.FACTORY_NO}" json="factory_no" 
									 label="返修单号" >
								<input class="gdtd_select_input" type="hidden"  value="${entry.SALE_ORDER_ID}"
									json="sale_order_id" id="SALE_ORDER_IDS">
							</td>
							<td class="gdtd">
								<div>原订单编号：</div>
								<input class="gdtd_select_input"  id="FROM_BILL_NO" name="FROM_BILL_NO"    value="${entry.FROM_BILL_NO}" json="from_bill_no"  
								label="原订单编号"  >
								<img class="magnifier" align="absmiddle" id="imgID"   style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif"      onClick="selSaleOrder();">
								<input type="hidden" id="BMZT" value="state ='已完成'  AND USER_ID='${ADMIN}' AND  XTYHID='${ADMIN}'" style="width: 180px; height: 23px">
								<input class="gdtd_select_input" type="hidden"      value="${entry.SALE_ORDER_IDS }"  name="SALE_ORDER_IDS"  
									json="from_bill_id"  id="SALE_ORDER_ID">
								<input json="state" type="hidden"       id="STATE" value="${entry.STATE }" ><!--  -->
							</td>
							 <td class="gdtd">
								<div>问题反馈单号：</div>
								<input class="gdtd_select_input" type="text" value="${entry.PROBLEM_FEEDBACK_NO}" name="PROBLEM_FEEDBACK_NO"
									json="problem_feedback_no"  id="PROBLEM_FEEDBACK_NO" label="反馈单号"  autocheck="true" >
								<%-- <img class="select_gif" align="absmiddle" id="imgID"   style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif"      onClick="selCommon('BS_201', false, 'PROBLEM_FEEDBACK_ID', 'PROBLEM_FEEDBACK_ID', 'forms[0]','PROBLEM_FEEDBACK_ID,PROBLEM_FEEDBACK_NO','PROBLEM_FEEDBACK_ID,PROBLEM_FEEDBACK_NO')"> --%>
								<input class="gdtd_select_input" type="hidden" value="${entry.PROBLEM_FEEDBACK_ID}"
									json="problem_feedback_id"  id="PROBLEM_FEEDBACK_ID">
							</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>下单日期：</div>
								<input class="gdtd_select_input" type="text" json="order_date" id="ORDER_DATE"
									 placeholder="提交自动生成" value="${entry.ORDER_DATE}"
									label="下单日期" readonly="readonly">
							</td>
							<td class="gdtd">
								<div>订单交期：</div>
								<input class="gdtd_select_input" type="text" json="order_delivery_date"
									id="ORDER_DELIVERY_DATE"  placeholder="订单生效后25天" 
									value="${entry.ORDER_DELIVERY_DATE}" label="日期" onclick="SelectDate();">
									<img align="absmiddle" class="select_gif"
									src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"
									onclick="SelectDate(ORDER_DELIVERY_DATE);" />
							</td>
							<td class="gdtd">
								<div>预计到货期：</div>
								<input class="gdtd_select_input" type="text" json="pre_recv_date" id="PRE_RECV_DATE" name="PRE_RECV_DATE"
									 autocheck="true" value="${entry.PRE_RECV_DATE}" 
									label="预计到货期" onclick="SelectDate();" >
									
									<img align="absmiddle" class="select_gif"
									src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"
									onclick="SelectDate(PRE_RECV_DATE);" />
							</td>
							<td class="gdtd">
								<div>
									订单图纸&nbsp;：
									<button type="button" class="layui-btn uploadFile"
										id="ATT_PATH" lay-data="{id:'ATT_PATH'}">上传</button>
								</div> 
								<input type="hidden" json="ATT_PATH" name="ATT_PATH" id="hid_ATT_PATH" value="${entry.ATT_PATH}">
								<table class="layui-table" style="width: 85%"
									id="view_ATT_PATH"></table> 
							</td> 
						</tr>
						<tr>
							<td colspan="2">
								<div>备注：</div>
								<textarea class="gdtd_tb_textarta" json="remark2" name="REMARK2" id="REMARK2"
									label="经销商备注" autocheck="true" inputtype="string"
									maxlength="3000"  style="width:92.5%">${fn:replace(entry.REMARK2, "；", "&#13;")}</textarea>
							</td>
							<td class="gdtd"></td>
							<td class="gdtd"></td>
						</tr>
						<tr>
							<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">订货信息</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>订单组织：</div>
									<%-- <input class="gdtd_select_input" json="ledger_id" id="LEDGER_ID" name="LEDGER_ID"
									label="订单组织" type="hidden" value="${entry.LEDGER_ID}">
								   <input class="gdtd_select_input"  json="ledger_name"  id="LEDGER_NAME" value="${entry.LEDGER_NAME}" READONLY> --%>
								<select class="gdtd_select_input" json="ledger_id" id="LEDGER_ID" name="LEDGER_ID" 
									label="订单组织" autocheck="true" inputtype="string" mustinput="true" ></select>
								<input type="hidden" json="ledger_name" name="LEDGER_NAME"  id="LEDGER_NAME" value="${entry.LEDGER_NAME}">
							</td>
							<td class="gdtd">
								<div>经销商名称：</div>
								<input class="gdtd_select_input" type="text" value="${entry.CHANN_NAME}" 
									json="chann_name" name="CHANN_NAME" id="CHANN_NAME" readonly="readonly" 
									label="经销商名称" autocheck="true" inputtype="string" mustinput="true">
								<img name="imgTab" align="absmiddle" class="magnifier"
									src="${ctx }/styles/newTheme/images/plus/select.gif" onClick="selChann();"/>
								<input class="gdtd_select_input" type="hidden" value="${entry.CHANN_ID}" json="chann_id"
									id="CHANN_ID" inputtype="string" READONLY>
								<input class="gdtd_select_input" type="hidden" value="${entry.CHANN_NO}" json="chann_no"
									 id="CHANN_NO" inputtype="string" READONLY>
							</td>
							<td class="gdtd">
								<div>订货组织：</div>
								<input class="gdtd_select_input"  id="ORDER_CHANN_NAME" type="text"
									json="order_chann_name"  maxlength="100" value="${entry.ORDER_CHANN_NAME }">
								<input type="hidden" value="${entry.ORDER_CHANN_ID }"
									json="order_chann_id"    id="ORDER_CHANN_ID" READONLY>
								<input type="hidden" value="${entry.ORDER_CHANN_NO }"
									json="order_chann_no"    id="ORDER_CHANN_NO" READONLY>
							</td>
							<td class="gdtd">
								<div>收货组织：</div>
								<input class="gdtd_select_input" type="text"  json="recv_chann_name"    id="RECV_CHANN_NAME" 
									maxlength="100"  value="${entry.RECV_CHANN_NAME}">
								<input type="hidden" value="${entry.RECV_CHANN_ID }"
									json="recv_chann_id"    id="RECV_CHANN_ID" READONLY>
								<input type="hidden" value="${entry.RECV_CHANN_NO }"
									json="recv_chann_no"    id="RECV_CHANN_NO" READONLY>
							</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>标签打印名称：</div>
								<input class="gdtd_select_input" json="print_name"
									id="PRINT_NAME" maxlength="100" value="${entry.PRINT_NAME}">
							</td>
							<td class="gdtd">
								<div>业务员：</div>
								<input class="gdtd_select_input" type="text" 
									json="sale_name"    id="SALE_NAME"  value="${entry.SALE_NAME}" readonly="readonly">
								<input type="hidden" value="${entry.SALE_ID }"
									json="sale_id"    id="SALE_ID">
							</td>
							<td class="gdtd">
								<div>业务部门：</div>
								<input class="gdtd_select_input" type="text" 
									json="saledept_name" id="SALEDEPT_NAME"  maxlength="100"  value="${entry.SALEDEPT_NAME}" READONLY>
								<input type="hidden" value="${entry.SALEDEPT_ID }"
									json="saledept_id"  id="SALEDEPT_ID">
							</td>
							<td class="gdtd"></td>
						</tr>
						<tr>
							<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">收货信息</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>运输方式：</div>
								<select class="gdtd_select_input" json="transport" id="TRANSPORT" name="TRANSPORT"
									label="运输方式" autocheck="" inputtype="string" mustinput="true"  ></select>
							</td>
							<td class="gdtd">
								<div>运输结算方式：</div>
								<input class="gdtd_select_input" type="text" value="${entry.TRANSPORT_SETTLE}" json="transport_settle" 
									id="TRANSPORT_SETTLE"  label="运输结算方式" maxlength="100">
							</td>
							<td class="gdtd"></td>
							<td class="gdtd"></td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>收货人：</div>
								<input class="gdtd_select_input" type="text"  json="person_con"
									id="PERSON_CON"  label="收货人" maxlength="30" value="${entry.PERSON_CON}" >
							</td>
							<td class="gdtd">
								<div>联系电话：</div>
								<input class="gdtd_select_input" type="text" value="${entry.TEL}" json="tel" 
									id="TEL" label="联系电话"  maxlength="50" >
							</td>
							<td class="gdtd" colspan="2">
								<div>收货地址：</div>
								<input class="gdtd_select_input"   json="recv_addr"
								 id="RECV_ADDR"  maxlength="100"  value="${entry.RECV_ADDR}"> 
							</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>客户姓名：</div>
								<input class="gdtd_select_input" type="text" value="${entry.CUST_NAME}" json="cust_name"
									id="CUST_NAME"  label="客户姓名"  maxlength="50">
							</td>
							<td class="gdtd">
								<div>客户电话：</div>
								<input class="gdtd_select_input" type="text" value="${entry.CUST_TEL}" json="cust_tel"
									id="CUST_TEL"  label="客户电话"  maxlength="50">
							</td>
							<td class="gdtd" colspan="2">
								<div>客户住址：</div>
								<input class="gdtd_select_input" json="cust_addr"
									id="CUST_ADDR" label="客户住址"  maxlength="100" value="${entry.CUST_ADDR}">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	<!-- 模块，页码，排序Id，排序方式，选择记录主键Id -->
	<input type="hidden" id="actionType" value="list"/>
	<input type="hidden" id="module" value="${module}"/>
	<input type="hidden" id="pageNo" value="${pageNo}"/>
	<input type="hidden" id="orderId" value="${orderId}"/>
	<input type="hidden" id="orderType" value="${orderType}"/>
	<input type="hidden" id="selRowId" value="${selRowId}"/>
	<input type="hidden" id="paramUrl" value="${paramUrl}"/>
	<input type="hidden" id="doCommitSave" value="${doCommitSave}"/>
	<input type="hidden" id="flag" value="${flag}"/>  
	<input type="hidden" id="IS_DRP_LEDGER" value="${IS_DRP_LEDGER}"/>
	<input type="hidden" id="erjiOrderId" value="${erjiOrderId}"/>
	<input type="hidden" id="option" value="${option}"/>
	<input type="hidden" id="FACTORY_NOS" value="${entry.FACTORY_NO}"/>
	<div>
	<!-- 	<table id="qxBtnTb"  cellSpacing=0 cellPadding=0 border=0>
			<tr style="height: 40px">
				<td nowrap>
					
				</td>
			</tr>
		</table> -->
	</div>
	<table width="100%" border="0" cellpadding="5" cellspacing="0" class="lst">
		<tr>
			<td class="detail2">
					<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">物料明细</div>
				<div class="toggle">
						<button class="img_input addbtn">
					<label for="add">
						<img src="/dareos/styles/newTheme/images/icon/xinzeng.png"
							class="icon_font">
						<input id="add" type="button" class="btn add" value="新增">
					</label>
				</button>
				<button class="del_input">
					<label for="deleteDtl">
						<img src="/dareos/styles/newTheme/images/icon/shanchu.png"
							class="icon_font">
						<input id="deleteDtl" type="button" class="del_btn" value="删除">
					</label>
				</button>	
			</td>
		</tr>
	</table>
	<div class=" child">
	<form>
		<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
			<tr>
				<td class="detail2">
					<table id="jsontb" width="100%" border="0" cellpadding="5" cellspacing="0" class="lst" >
						<tr class="fixedRow">
							<th nowrap align="center" style="width: 2%"><input type="checkbox" style="height:12px;valign:middle" id="allChecked"></th>
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
							<th nowrap align="center" class=" ">工程位置</th>
							<th nowrap align="center" class="LMM">是否开锁孔</th>
							<th nowrap align="center">附图号</th>
							<th nowrap align="center">图纸</th> 
							<%-- <th nowrap align="center">处理类型</th> --%>
							<th nowrap align="center" class="SHOWPRICE">是否返利</th>
							<!-- <th nowrap align="center">可用库存</th> -->
							<th nowrap align="center">数量</th>
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
				</td>
			</tr>
		</table>
	</form>
		</div>
		<!-- 底部固定部分 -->
		<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
		<c:if test="${empty option }">
			<button class="img_input">
				<label for="save">
					<img src="/dareos/styles/newTheme/images/icon/baocun.png"
						class="icon_font">
					<input id="save" type="button" class="btn" value="保存">
				</label>
			</button>
			<button class="img_input">
				<label onclick="closeDialog()">
					<img src="/dareos/styles/newTheme/images/icon/chexiao.png"
						class="icon_font">
					<input type="button" class="btn" value="返回"/>
				</label>
			</button> 
			</c:if>
			<c:if test="${not empty option }">
			<button class="img_input">
				<label for="audit">
					<img src="/dareos/styles/newTheme/images/icon/baocun.png"
						class="icon_font">
					<input id="audit" type="button" class="btn" value="确定">
				</label>
			</button>
		</c:if>
	</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
	//订单组织
	//var initSel = "";
	//if('' == '${rst.FROM_BILL_NO}'){//总部
		SelDictShow("LEDGER_ID","BS_212","${entry.LEDGER_ID}"," XTYHID = '${ADMIN}' ");
		//initSel = " SJGL in ('S', 'SF')";
	//} else {
	//	SelDictShow("LEDGER_ID","BS_189","${rst.LEDGER_ID}"," CHANN_ID = '${rst.CHANN_ID}' ");
		//initSel = " SJGL in ('G', 'SF')";
	//}
	//
	SelDictShow("TRANSPORT","BS_185","${entry.TRANSPORT}","");
   var sale_id=$("#SALE_ORDER_IDS").val(); 
	if(sale_id==null||sale_id==""){//
		$("#FACTORY_NO").val("");
		$("#FROM_BILL_NO").val($("#FACTORY_NOS").val());
	}

	displayDownFile('ATT_PATH',true);
	
	var interval = setInterval(function(){
		if(uploadFile){
			clearInterval(interval);

			<c:forEach items="${entry.entrySun}" var="rst_child" varStatus="row">
			<c:set var="rr" value="${row.count}"></c:set>
			//countPrice('${rr}');
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
			        '${rst_child.ATT_PATH}',//14
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