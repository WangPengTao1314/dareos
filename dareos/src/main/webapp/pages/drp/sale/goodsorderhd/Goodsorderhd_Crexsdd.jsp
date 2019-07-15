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
	<%-- <link rel="stylesheet" href="${ctx}/styles/${theme}/css/new_demo.css"> --%>
	<title>转销售订单</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/drp/sale/goodsorderhd/Goodsorderhd_Crexsdd.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		.child{overflow-x: auto; width: 100%; }
		.dashed{border-bottom: 1px solid #e8edf2;width: 98%;}
		table.xsddExt tr td{ line-height: 30px; }
	</style>
</head>
<body class="bodycss1 add_demoone" style=" overflow-y:auto; overflow-x:auto;" >
	<form name="mainForm" id="mainForm">
	
		<table id="jsontbP" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
			<tr>
				<td class="detail2">
					<table style="width:100%;padding: 9px 0% 0;">
						<tr>
							<td class="title">订单信息</td>
							<td align="right">
								<input type="button"  id="ipt"  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;" onclick='changeinput()'>
								<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="" id="imgsz" onclick='changeinput()'>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="detail2" id="toggleTable">
					<table cellspacing="0" cellpadding="0"  class="detail3 linebreak"
						style="border-left:1px solid #d8dde6;border-right:1px solid #d8dde6;">
						<tr >
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
							<div class="dashed font-color">基本信息</div>
							</td>
						</tr>
						<tr class="baseinfo">
							<td class="gdtd">
							<div>销售类型：</div>
								${rst.BILL_TYPE }
								<select class="gdtd_select_input" json="BILL_TYPE" id="BILL_TYPE" name="BILL_TYPE" style="display: none;"></select>
							</td>
							<td class="gdtd">
								<div>订货单号：</div>
								${rst.GOODS_ORDER_NO}
								<input class="gdtd_select_input" type="hidden" readonly="readonly"
									value="${rst.GOODS_ORDER_NO}" json="FROM_BILL_NO" name="FROM_BILL_NO"
									id="FROM_BILL_NO" />
								<input class="gdtd_select_input" type="hidden" value="${rst.GOODS_ORDER_ID}"
									json="FROM_BILL_ID" name="FROM_BILL_ID" id="FROM_BILL_ID">
								<input class="gdtd_select_input" type="hidden" value="${rst.BILL_TYPE}"
									json="BILL_TYPE2" name="BILL_TYPE2" id="BILL_TYPE2">
								<input class="gdtd_select_input" type="hidden" value="${rst.ORDER_TRACE_ID }"
									json="ORDER_TRACE_ID" name="ORDER_TRACE_ID" id="ORDER_TRACE_ID">
							</td><%-- 
							<td  class="gdtd" nowrap>
								<div>预计到货日期：</div>
								<input class="gdtd_select_input edit" type="text" json="PRE_RECV_DATE" id="PRE_RECV_DATE"
									name="PRE_RECV_DATE" placeholder="订单交期后7天" value="${rst.PRE_RECV_DATE}"
									label="预计到货期" onclick="SelectDate();" READONLY />
								<img align="absmiddle" class="calendar" style="cursor: hand"
									src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"
									onclick="SelectDate(PRE_RECV_DATE);" />
							</td> --%>
							<td  class="gdtd">
								<div>下单日期：</div>
								${rst.ORDER_DATE}
								<input class="gdtd_select_input" type="hidden" json="ORDER_DATE" id="ORDER_DATE"
									name="ORDER_DATE" placeholder="提交自动生成" value="${rst.ORDER_DATE}"
									label="下单日期" READONLY />
							</td>
							<td class="gdtd">
								<div>订单交期：</div>
								<input class="gdtd_select_input edit" type="text" json="ORDER_DELIVERY_DATE"
									id="ORDER_DELIVERY_DATE" name="ORDER_DELIVERY_DATE" placeholder="订单生效后25天" 
									value="${rst.ORDER_DELIVERY_DATE}" label="日期" onclick="SelectDate();" READONLY
									onchange="changeFactoryNo(this);" />
								<img align="absmiddle" class="calendar" style="cursor: hand"
									src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"
									onclick="SelectDate(ORDER_DELIVERY_DATE);" />
								<input type="hidden" id="xtbs" value="${xtbs }" />
							</td>
						</tr>
						<tr class="baseinfo">
							<td colspan="2">
								<div>经销商备注：</div>
								<c:set value="${fn:split(rst.REMARK, '；') }" var="str1" />
								<c:forEach items="${ str1 }" var="s">${ s }<br/></c:forEach>
								<textarea style="width: 92%; display: none;" json="REMARK" name="REMARK" id="REMARK" inputtype="string"
									maxlength="200">${fn:replace(rst.REMARK, "；", "&#13;")}</textarea>
							</td>
							<td class="gdtd">
								<div>订单图纸：<button type="button" class="layui-btn uploadFile " id="attPath" lay-data="{id:'attPath'}" disabled>上传</button></div>
								<input type="hidden"  json="attPath" name="attPath" id="hid_attPath" value="${ rst.attPath}">
								<table class="layui-table" style="width:85%" id="view_attPath"></table>
							</td>
						</tr>
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
							<div class="dashed font-color">订货信息</div>
							</td>
						</tr>
						<tr class="orderinfo">
							<td class="gdtd">
								<div>订单组织：</div>
								${rst.LEDGER_NAME}
								<select class="gdtd_select_input" json="LEDGER_ID" id="LEDGER_ID" name="LEDGER_ID"
									style="display: none;"></select>
								<input class="gdtd_select_input" type="hidden" json="LEDGER_NAME" name="LEDGER_NAME" 
									id="LEDGER_NAME" value="${rst.LEDGER_NAME}">
							</td>
							<td class="gdtd">
								<div>订货组织：</div>
								${rst.ORDER_CHANN_NAME }
								<input class="gdtd_select_input" name="ORDER_CHANN_NAME" id="ORDER_CHANN_NAME" type="hidden"
									json="ORDER_CHANN_NAME" value="${rst.ORDER_CHANN_NAME }" 
									maxlength="100" readonly="readonly" />
								<input type="hidden" value="${rst.ORDER_CHANN_ID }"
									json="ORDER_CHANN_ID" name="ORDER_CHANN_ID" id="ORDER_CHANN_ID">
								<input type="hidden" value="${rst.ORDER_CHANN_NO }"
									json="ORDER_CHANN_NO" name="ORDER_CHANN_NO" id="ORDER_CHANN_NO">
							</td>
							<td class="gdtd">
								<div>订货组织：</div>
								${rst.RECV_CHANN_NAME}
								<input class="gdtd_select_input" type="hidden" value="${rst.RECV_CHANN_NAME}"
									json="RECV_CHANN_NAME" name="RECV_CHANN_NAME" id="RECV_CHANN_NAME" 
									maxlength="100" readonly="readonly" />
								<input type="hidden" value="${rst.RECV_CHANN_ID }"
									json="RECV_CHANN_ID" name="RECV_CHANN_ID" id="RECV_CHANN_ID">
								<input type="hidden" value="${rst.RECV_CHANN_NO }"
									json="RECV_CHANN_NO" name="RECV_CHANN_NO" id="RECV_CHANN_NO">
							</td>
							<td class="gdtd">
								<div>经销商名称：</div>
								${rst.CHANN_NAME}
								<input class="gdtd_select_input" type="hidden" value="${rst.CHANN_NAME}" maxlength="100"
									json="CHANN_NAME" name="CHANN_NAME" id="CHANN_NAME" readonly="readonly" />
								<input class="gdtd_select_input" type="hidden" value="${rst.CHANN_ID}" json="CHANN_ID"
									name="CHANN_ID" id="CHANN_ID" inputtype="string" />
								<input class="gdtd_select_input" type="hidden" value="${rst.CHANN_NO}" json="CHANN_NO"
									name="CHANN_NO" id="CHANN_NO" inputtype="string" />
							</td>
						</tr>
						<tr class="orderinfo">
							<td class="gdtd">
								<div>标签打印名称：</div>
								${rst.PRINT_NAME}
								<input class="gdtd_select_input" type="hidden" value="${rst.PRINT_NAME}" json="PRINT_NAME"
									name="PRINT_NAME" id="PRINT_NAME" maxlength="100" />
							</td>
							<td class="gdtd">
								<div>业务部门：</div>
								${rst.SALEDEPT_NAME}
								<input class="gdtd_select_input" type="hidden" value="${rst.SALEDEPT_NAME}"
									json="SALEDEPT_NAME" name="SALEDEPT_NAME" id="SALEDEPT_NAME" 
									maxlength="100" readonly="readonly" />
								<input type="hidden" value="${rst.SALEDEPT_ID }"
									json="SALEDEPT_ID" name="SALEDEPT_ID" id="SALEDEPT_ID">
							</td>
							<td class="gdtd">
								<div>业务员：</div>
								${rst.SALE_NAME}
								<input class="gdtd_select_input" type="hidden" value="${rst.SALE_NAME}" 
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
						<tr class="receiveinfo">
							<td class="gdtd">
								<div>收货人：</div>
								${rst.PERSON_CON}
								<input class="gdtd_select_input" type="hidden" value="${rst.PERSON_CON}" json="PERSON_CON"
									name="PERSON_CON" id="PERSON_CON"  />
							</td>
							<td class="gdtd">
								<div>联系电话：</div>
								${rst.TEL}
								<input class="gdtd_select_input" type="hidden" value="${rst.TEL}" json="TEL" name="TEL"
									id="TEL"  />
							</td>
							<td class="gdtd" colspan="2">
								<div>收货地址：</div>
								${rst.RECV_ADDR}
								<input class="gdtd_select_input" type="hidden" value="${rst.RECV_ADDR}" json="RECV_ADDR"
									name="RECV_ADDR" id="RECV_ADDR"  />
							</td>
						</tr>
						<tr class="receiveinfo">
							<td class="gdtd">
								<div>客户姓名：</div>
								${rst.CUST_NAME}
								<input class="gdtd_select_input" type="hidden" value="${rst.CUST_NAME}" json="CUST_NAME"
									name="CUST_NAME" id="CUST_NAME"  />
							</td>
							<td class="gdtd">
								<div>客户电话：</div>
								${rst.CUST_TEL}
								<input class="gdtd_select_input" type="hidden" value="${rst.CUST_TEL}" json="CUST_TEL"
									name="CUST_TEL" id="CUST_TEL"  />
							</td>
							<td class="gdtd" colspan="2">
								<div>客户住址：</div>
								${rst.CUST_ADDR}
								<input class="gdtd_select_input" type="hidden" value="${rst.CUST_ADDR}" json="CUST_ADDR"
									name="CUST_ADDR" id="CUST_ADDR"  />
							</td>
						</tr>
						<tr class="ysfs" style="display: none;">
							<td class="gdtd">
								<div>运输方式：</div>
								${rst.TRANSPORT }
								<select class="gdtd_select_input" json="TRANSPORT" id="TRANSPORT" name="TRANSPORT"
									style="display: none;"></select>
							</td>
							<td class="gdtd">
								<div>运输结算方式：</div>
								${rst.TRANSPORT_SETTLE}
								<input class="gdtd_select_input" type="hidden" value="${rst.TRANSPORT_SETTLE}"
									json="TRANSPORT_SETTLE" name="TRANSPORT_SETTLE" id="TRANSPORT_SETTLE"  />
							</td>
							<td class="gdtd" nowrap>
								<div>订单总金额：</div>
								${rst.TOTAL_AMOUNT}
								<input class="gdtd_select_input" type="hidden" value="${rst.TOTAL_AMOUNT}"
									json="TOTAL_AMOUNT" name="TOTAL_AMOUNT" id="TOTAL_AMOUNT" readonly disabled/>
							</td>
							<td class="gdtd">
								<div>返利总金额：</div>
								${rst.TOTAL_REBATE}
								<input class="gdtd_select_input" type="hidden" value="${rst.TOTAL_REBATE}"
									json="TOTAL_REBATE" name="TOTAL_REBATE" id="TOTAL_REBATE" readonly disabled/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		
	</form>
	
	<!-- <tr>
        <td>操作</td>
    </tr>
    <div class="audit">流程图占位区

    </div> -->
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
	<input type="hidden" id="ztid" value="${rst.LEDGER_ID}"/>
	
	<!-- 删除标记:在该页面有删除操作后，返回时刷新页面。否则直接返回上一级，不刷新 -->
	<input id="delFlag" type="hidden" value="false"/>
	<input type="hidden" id="PRDNOS" name="PRDNOS" value="${PRDNOS}">
	<input type="hidden" id="selectParams" name="selectParams" value="STATE='启用' and DEL_FLAG=0 and FINAL_NODE_FLAG=1 ">
	<input type="hidden" id="DECT_RATE" value="${DECT_RATE}">
	<input type="hidden" id="rate" name="rate">
	<!-- <div class="detail">
		<table id="qxBtnTb" cellpadding="0" cellspacing="5" border="0">
			<tr style="height: 40px">
				<td nowrap style="word-spacing: 8px;">
				</td>
			</tr>
		</table>
	</div> -->
	<c:forEach items="${rstChild}" var="entry" varStatus="list">
	<form name="mainForm" id="mainForm${list.index }">
		<table id="jsontbP${list.index }" width="100%" border="0" cellpadding="5" cellspacing="0" class="detail xsddExt">
			<tr>
				<td align="left" class="gdtd" colspan="4">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td class="gdtd" colspan="4">
					<div class="fontBold dashed">销售订单&nbsp;${list.count }/${fn:length(rstChild) }</div>
				</td>
			</tr>
			<tr>
				<td class="gdtd">
					<div>厂编：</div>
					<input class="gdtd_select_input" type="text" value="${entry.FACTORY_NO}" json="FACTORY_NO"
						name="FACTORY_NO" id="FACTORY_NO${list.index }" 
						label="厂编" autocheck="true" inputtype="string" mustinput="true" />
					<input type="hidden" value="${entry.SALE_ORDER_NO }" json="SALE_ORDER_NO" name="SALE_ORDER_NO" />
				</td>
				<td class="gdtd">
					<div>处理类型：</div>
					<input class="gdtd_select_input" type="text" value="${entry.PROESS_TYPE}" json="PROESS_TYPE"
						name="PROESS_TYPE" id="PROESS_TYPE${list.index }" disabled />
				</td>
				<td class="gdtd">
					<div>紧急程度：</div>
					<select class="gdtd_select_input" json="URGENCY_TYPE" name="URGENCY_TYPE" id="URGENCY_TYPE${list.index }" 
						label="紧急程度" autocheck="true" inputtype="string" mustinput="true" ></select>
				</td><%-- 
				<td class="gdtd">
					<div>订单图纸：</div>
					<input json="attPath" name="attPath" id="attPath${list.index }" value="${ rst.attPath}">
				</td> --%>
				<td class="gdtd">
					<div>订单图纸：<button type="button" class="layui-btn uploadFile" id="attPath${list.index }" lay-data="{id:'attPath${list.index }'}">上传</button></div>
					<input type="hidden"  json="attPath" name="attPath" id="hid_attPath${list.index }" value="${entry.attPath}" />
					<table class="layui-table" id="view_attPath${list.index }"></table>
				</td>
			</tr>
			<tr>
				<td class="gdtd" colspan="2">
					<div>备注：</div>
					<textarea class="gdtd_tb_textarta" json="REMARK2" name="REMARK2" id="REMARK2"
						label="备注" autocheck="true" inputtype="string"
						maxlength="3000" style="width:92.5%">${fn:replace(rst.REMARK2, "；", "&#13;")}</textarea>
				</td>
				<td class="gdtd">
					<div>订单总金额：</div>
					<input type="text" class="gdtd_select_input" value="" json="TOTAL_AMOUNT" name="TOTAL_AMOUNT" id="TOTAL_AMOUNT${list.index }" readonly/>
				</td>
				<td class="gdtd">
					<div>订单总返利：</div>
					<input type="text" class="gdtd_select_input" value="" json="TOTAL_REBATE" name="TOTAL_REBATE" id="TOTAL_REBATE${list.index }" readonly/>
				</td>
			</tr>
		</table>
	</form>
	<div class="bodycss1 child">
		<table width="99.5%" height="" border="0" cellspacing="0" cellpadding="0" class="panel">
			<tr>
				<td>
					<div class="lst_area">
						<form name="childFrm${list.index }">
							<input type="hidden" id="initParams${list.index }" value="">
							<table id="jsontb${list.index }" width="100%" border="0" cellpadding="5" cellspacing="0" class="lst" >
								<tr class="fixedRow">
									<th nowrap align="center" style="width: 2%"><input type="checkbox" style="height: 12px; valign: middle" id="allChecked"></th>
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
									<th nowrap align="center" class="LMM">是否开锁孔</th>
									<th nowrap align="center">图纸</th>
									<th nowrap align="center">处理类型</th>
									<th nowrap align="center">是否返利</th>
									<th nowrap align="center">可用库存</th>
									<th nowrap align="center">数量</th>
									<th nowrap align="center">单位</th>
									<th nowrap align="center">计算报价</th>
									<th nowrap align="center">折扣率(%)</th>
									<th nowrap align="center">返利</th>
									<th nowrap align="center">最终报价</th>
									<th nowrap align="center">金额</th>
									<th nowrap align="center">备注</th>
								</tr>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</c:forEach>
	<input id="flag" value="1" type="hidden">
	
	<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;left: 0;word-spacing: 8px;">
		<button class="img_input">
			<label for="create">
				<img src="${ctx}/styles/${theme}/images/icon/baocun.png"
					class="icon_font">
				<input id="create" type="button" class="btn" value="确定">
			</label>
		</button>
		<button class="img_input">
			<label for="back">
				<img src="${ctx}/styles/${theme}/images/icon/tuihui.png"
					class="icon_font">
				<input id="back" type="button" class="btn" value="退回" status="F">
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
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
	SelDictShow("STATE","BS_186","${rst.STATE}","");
	SelDictShow("LEDGER_ID","BS_189","${rst.LEDGER_ID}"," CHANN_ID = '${rst.CHANN_ID}' ");
	SelDictShow("TRANSPORT","BS_185","${rst.TRANSPORT}","");
	SelDictShow("BILL_TYPE","BS_190","${rst.BILL_TYPE}","");
</script>

<script type="text/javascript">
	var interval = setInterval(function(){
		if(uploadFile){
			clearInterval(interval);

			<c:forEach items="${rstChild}" var="entry" varStatus="list">
			SelDictShow("URGENCY_TYPE${list.index }","BS_218","${entry.URGENCY_TYPE}","");
			//上传文件
			displayDownFile('attPath${list.index }',true);
			//uploadFile('attPath${list.index }','',true,false,true,false,true);
			<c:set var="rst_list" value="${entry.childList}"></c:set>
			<c:forEach items="${rst_list}" var="rst_child" varStatus="row">
			var arrs = [
				'',//0
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
		        '${rst_child.GOODS_ORDER_DTL_ID}',//36
		        '${entry.PROESS_TYPE}',//37
		            ];//console.log('arrs', arrs);
			addRow(arrs, ${list.index });
			</c:forEach>

			// 计算订单总金额
			countTotalRebate(${list.index });
			countTotalAmount(${list.index });
			</c:forEach>

		}
	},500);
</script>
</html>