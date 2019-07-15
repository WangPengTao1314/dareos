<!-- 
 *@module 售后管理
 *@func 售后问题反馈单处理明细
 *@version 1.0
 *@author 王朋涛
 *  -->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<script language="JavaScript"
	src="${ctx}/pages/common/select/selCommJs.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/styles/${theme}/css/style.css">
<%-- <script type="text/javascript"
	src="${ctx}/pages/drp/main/deliver/after/After_Handle.js"></script> --%>
<title>问题反馈单明细</title>
</head>
<body style="overflow-y: auto; overflow-x: auto;">
	
	<!--浮动按钮层结束-->
	<form name="mainForm" id="mainForm"  >
		<table id="jsontb" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
		<tr>
			<td class="detail2">
				<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable"  class="detail3"
						style="background-color: #fff; padding: 0 0 0 2%; border-collapse: separate; border-spacing: 10px;border-top:1px solid #e8edf2;;border-left:1px solid #e8edf2;;border-right:1px solid #e8edf2;;margin:0 auto">
				<tr>
					<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">基本信息</td>
				</tr>
				<tr>
					<td class="gdtd_tb">
						<div>问题反馈单号 &nbsp;:</div>
						<input class="gdtd_select_input" name="problem_feedback_no" json="problem_feedback_no" id="problem_feedback_no" type="text" autocheck="true"
							 maxlength="30" value="${entry.PROBLEM_FEEDBACK_NO}"  READONLY> 
						<input  name="problem_feedback_id" json="problem_feedback_id" id="problem_feedback_id" type="text" hidden="true" label="问题单id"
						 value="${entry.PROBLEM_FEEDBACK_ID}" maxlength="32">
					</td>
					<td class="gdtd_tb">
						<div>订单编号:</div> <input class="gdtd_select_input" value="${entry.SALE_ORDER_NO}" maxlength="32" readonly="readonly" >
					</td>
					<td class="gdtd_tb">
						<div>经销商&nbsp;:</div> <input class="gdtd_select_input"  value="${entry.CHANN_NAME}" readonly="readonly" > 
						<input type="hidden" value="${entry.CHANN_ID}"  id="CHANN_ID"> 
					</td>
					<td class="gdtd_tb">
						<div>顾客名称 &nbsp;:</div> <input class="gdtd_select_input" value="${entry.CUST_NAME}" readonly="readonly" >
					</td>
				</tr>
				<tr>
				 	<td class="gdtd_tb">
				 	 	<div>订单组织&nbsp;:</div>
				 	 	<select class="gdtd_select_input"  id="LEDGER_ID" disabled= "true" ></select>
				  	</td>
					<td class="gdtd_tb">
						<div>收货人姓名 &nbsp;:</div>
						<input class="gdtd_select_input" value="${entry.PERSON_CON}" readonly="readonly" >
					</td>
					<td class="gdtd_tb">
						<div>收货电话</div> 
						<input class="gdtd_select_input"  value="${entry.TEL}" readonly="readonly" >
					</td>
					<td class="gdtd_tb">
						<div>收货地址&nbsp;:</div> <textarea class="gdtd_select_input"
						json="recv_addr" id="RECV_ADDR" readonly="readonly" >${entry.RECV_ADDR}</textarea>
						<input type="hidden" id="initSel" name="initSel" value=" CHANN_ID ='${entry.CHANN_ID }'"/>
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb">
						<div>交货日期 &nbsp;:</div> <input class="gdtd_select_input"  value="${entry.DELIVERY_TIME}" readonly="readonly">
					</td>
					<td class="gdtd_tb"></td>
					<td class="gdtd_tb"></td>
					<td class="gdtd_tb"></td>
				</tr>
				<tr>
					<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">问题反馈</td>
				</tr>
				<tr>
					<td class="gdtd_tb"> 
						<div>问题描述&nbsp;:</div>
						<textarea style="width: 186.5%;" rows="5" >${fn:replace(entry.PROBLEM_DETAILED, "；", "&#13;")}</textarea>
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb">
						<div>反馈附件&nbsp;：<button type="button" class="layui-btn uploadFile" id="ATT_PATH" lay-data="{id:'ATT_PATH'}" disabled>上传</button></div>
						<%-- <input json="attPath" name="attPath" id="attPath" value="${rst.attPath}"> --%>
						<input type="hidden"  name="ATT_PATH" id="hid_ATT_PATH" value="${entry.ATT_PATH}">
						<table class="layui-table" style="width:85%" id="view_ATT_PATH"></table>
					</td>
				</tr>	
				<tr>
					<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">问题处理</td>
				</tr>
				<tr>
					<td class="gdtd_tb">
						<div>处理结果 &nbsp;:</div>
						<input class="gdtd_select_input"  value="${entry.DISPOSE_TYPE}" readonly="readonly">
					</td>
					<td class="gdtd_tb"> 
						<div>责任所属&nbsp;:</div>
						<input class="gdtd_select_input"   value="${entry.BLAME}" readonly="readonly">
					</td>
					<td class="gdtd_tb"> 
						<div>改补单号&nbsp;:</div>
						<input class="gdtd_select_input"     value="${entry.AMENDMENT_NO}" readonly="readonly">
					</td>
					<td class="gdtd_tb">
						<div>厂编&nbsp;:</div>
						<input class="gdtd_select_input"   value="${entry.FACTORY_NO}" readonly="readonly">
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb">
						<div>收费&nbsp;:</div>
						<input class="gdtd_select_input"   value="${entry.SUPPLY_AMOUNT}" readonly="readonly">
					</td>
					<td class="gdtd_tb"> 
					   <div>问题分类&nbsp;:</div>
					   <select  class="gdtd_select_input" name="problem_type"  id="PROBLEM_TYPE" disabled="true" ></select>	
					</td>
					<td class="gdtd_tb"></td>
					<td class="gdtd_tb"></td>
			 	</tr>
			 	<tr>
					<td class="gdtd_tb">
						<div>分析原因&nbsp;:</div>
						<textarea style="width: 151%"  rows="3" maxlength="200"  id="reason_analysis" json="reason_analysis"   >${fn:replace(entry.REASON_ANALYSIS, "；", "&#13;")}</textarea>
					</td>
					<td class="gdtd_tb"></td>
					<td class="gdtd_tb"></td>
					<td class="gdtd_tb"></td>
			 	</tr>
				<tr>
					<td class="gdtd_tb">
						<div>
							处理附件&nbsp;：
							 <button type="button" class="layui-btn uploadFile"
								id="ATT_PATH2" lay-data="{id:'ATT_PATH2'}"  disabled>上传</button>
						</div> 
						<input type="hidden"  name="ATT_PATH2" id="hid_ATT_PATH2" value="${entry.ATT_PATH2}">
						<table class="layui-table" style="width: 85%"
							id="view_ATT_PATH2"></table> 
					</td> 
					<td class="gdtd_tb"></td>
					<td class="gdtd_tb"></td>
					<td class="gdtd_tb"></td>
				</tr>
			 </table>
			</td>
		</tr>
		</table>
	</form>
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
			<label onclick="closeDialog()"> <img
				src="${ctx}/styles/${theme}/images/icon/chexiao.png"
				class="icon_font"> <input type="button" class="btn"
				value="返回">
			</label>
		</button>
	</div>
	<form name="backForm" method="post">
		<input type="hidden" name="pageNo" value="${pageNo }">
	</form>
</body>
<script type="text/javascript">
		if('0' == '${IS_DRP_LEDGER}'){//总部
				SelDictShow("LEDGER_ID","BS_212","${entry.ORDER_ORG}"," XTYHID = '${XTYHID}'");
		} else {
			SelDictShow("LEDGER_ID","BS_189","${entry.ORDER_ORG}",$("#initSel").val());
		}
		//单一上传
    	displayDownFile('ATT_PATH',true);
		displayDownFile('ATT_PATH2',true);
		//SelDictShow("", "BS_183", "", "");
		
		var ORDER_ORG='${entry.ORDER_ORG}';
		if('10801' ==ORDER_ORG){//橱柜
			SelDictShow("PROBLEM_TYPE","BS_209","${entry.PROBLEM_TYPE}","");
		} else if('107' ==ORDER_ORG){//衣柜
			SelDictShow("PROBLEM_TYPE","BS_216","${entry.PROBLEM_TYPE}","");
		}else if('116'==ORDER_ORG){
			SelDictShow("PROBLEM_TYPE","BS_217","${entry.PROBLEM_TYPE}","");
		}else if('121'==ORDER_ORG){//圣世年轮
			
		}else if('113'==ORDER_ORG){//环美
			
		}
	
</script>
</html>