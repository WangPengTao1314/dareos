<!-- 
 *@module 销售管理
 *@func 
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
<script type="text/javascript" src="${ctx}/pages/drp/main/order/progress/Progress_confirm_delivery.js"></script>
<script type=text/javascript src="${ctx}/scripts/core/jquery-1.6.3.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
<style>
    .child{overflow-x: auto; width: 100%; }
	
</style>
<title>订单进度确认</title>
</head>
<body style="overflow-y: auto; overflow-x: auto;">
	<!--浮动按钮层结束-->
	<form name="mainForm" id="mainForm">
		<table id="jsontbP" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
			<tr id="myTable1">
				<td class="detail2">
					<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable"  class="detail3"
										style="background-color: #fff; padding: 0 0 0 2%; border-collapse: separate; border-spacing: 10px;border-top:1px solid #e8edf2;;border-left:1px solid #e8edf2;;border-right:1px solid #e8edf2;;margin:0 auto">
						<tr>							
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
							<span style="float:left" class="dashed font-color">订单信息</span>
						</tr>
						<tr>
							<td class="gdtd_tb">
								<div>订单编号 &nbsp;:</div>
								<input class="gdtd_select_input"  value="${entry.ORDER_DEGREE_NO}" READONLY>
								<input hidden="true" json="ORDER_DEGREE_ID" value="${entry.ORDER_DEGREE_ID}">
							</td>
							<td class="gdtd_tb">
								<div>交货日期 &nbsp;:</div>
								<input class="gdtd_select_input"  value="${entry.DELIVERY_DATE}" READONLY>
							</td>
							<td  class="gdtd_tb">
								<div>订单备注&nbsp;:</div>
								<textarea style="width: 198.5%;" rows="1"
									maxlength="200" READONLY>${entry.REMARK}</textarea>
							</td>
							<td  class="gdtd_tb"></td>
						</tr>
						<tr>							
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
							<span style="float:left" class="dashed font-color">交期确认</span>
						</tr>
						<tr class="yhzz">
							<td class="gdtd_tb">
								<div>预计交货日期&nbsp;:</div>
								<input class="gdtd_select_input" type="text" json="estimate_delivery_date" id="ESTIMATE_DELIVERY_DATE"
									 autocheck="true" inputtype="string" value="${entry.ESTIMATE_DELIVERY_DATE}" placeholder="请选择时间"
									label="预计交货日期" mustinput="true" onclick="SelectDate();" READONLY>&nbsp;
								 <img align="absmiddle" style="cursor: hand"
									src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"
									onclick="SelectDate(ESTIMATE_DELIVERY_DATE);" />
							</td>
							<td class="gdtd_tb">
								<div>附件&nbsp;:<button type="button" class="layui-btn uploadFile" id="ATT_PATH" lay-data="{id:'ATT_PATH'}" disabled>上传</button></div>
								<input type="hidden" json="ATT_PATH" name="ATT_PATH" id="hid_ATT_PATH" value="${entry.ATT_PATH}">
								<table class="layui-table" style="width:85%" id="view_ATT_PATH"></table>
							</td>
							<td class="gdtd_tb" >
								<div>备注&nbsp;:</div>
								<textarea style="width: 198.5%;" rows="1"
									maxlength="200" json="remark" >${entry.REMARK}</textarea>
							</td>
							<td class="gdtd_tb"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="5" cellspacing="0" class="lst">
			<tr>
				<td class="detail2" style="border-bottom: none">
					<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">进度提报</div>
				</td>
			</tr>
	    </table>
	</form>
	<div class="bodycss1 child">
		<form  autocomplete="off">
			<table width="100%" id="jsontb"  border="0" cellpadding="5" cellspacing="0" style="border-collapse: collapse;" class="lst" >
				<tr class="fixedRow">
					<th width="60">组号</th>
					<th width="120">物料编码</th>
					<th width="120">物料名称</th>
					<th width="120">尺寸</th>
					<th width="120">材质</th>
					<th width="120">颜色</th>
					<th width="120">推向</th>
					<th width="120">玻璃</th>
					<th width="120">其它</th>							
					<th width="120">系列</th>
					<th width="120">数量</th>
					<th width="120">单位</th>
					<th width="120">完成数量</th>
					<th width="120">发货数量</th>
				</tr>
				<c:forEach items="${entry.entrySun}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" id="tr${rr}">
						<td width="1%" align='center' >
							${rst.GROUP_NO}
							<input type="text" value="${rst.ORDER_DEGREE_DTL_ID}"hidden=true"" json="order_degree_dtl_id">
						</td>
						<th width="120">${rst.PRD_NO}&nbsp;</td>
						<th width="120">${rst.PRD_NAME}&nbsp;</td>
						<th width="120">${rst.HOLE_SPEC}&nbsp;</td>
						<th width="120">${rst.PRD_QUALITY}&nbsp;</td>
						<th width="120">${rst.PRD_COLOR}&nbsp;</td>
						<th width="120">${rst.PRD_TOWARD}&nbsp;</td>
						<th width="120">${rst.PRD_GLASS}&nbsp;</td>  
						<th width="120">${rst.PRD_OTHER}&nbsp;</td>
						<th width="120">${rst.PRD_SERIES}&nbsp;</td> 
						<th width="120">${rst.ORDER_NUM}&nbsp;</td>
						<th width="120">${rst.STD_UNIT}&nbsp;</td>
						<th width="120">${rst.PRO_NUM}&nbsp;</td>
						<th width="120">${rst.SEND_NUM}&nbsp;</td>
					</tr>
				</c:forEach>
				<c:if test="${empty entry.entrySun}">
					<tr>
						<td height="25" colspan="13" align="center" class="lst_empty">
							&nbsp;无相关信息&nbsp;
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
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
		<button class="img_input">
			<label for='save'> <img
				src="${ctx}/styles/${theme}/images/icon/baocun.png"
				class="icon_font"> <input id="save" type="button"
				class="btn" value="保存">
			</label>
		</button>
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
		<input type="hidden" id="selRowId" name="selRowId" value="${entry.SEND_ORDER_ID}">
	</form>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
	//单一上传
   	displayDownFile('ATT_PATH',true);
	//SelDictShow("", "BS_183", "", "");
</script>
</html>