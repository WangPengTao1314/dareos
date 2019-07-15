<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>返修单明细</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/drp/main/deliver/rework/Rework_Check.js"></script>
	<style type="text/css">
		.child{overflow-x: auto}
	</style>
</head>
<body class="add_demoone" style=" overflow-y:auto; overflow-x:auto;" >
    <!-- <span class="add_demospan">当前位置：订单中心>返修管理</span> -->
	<form name="mainForm" id="mainForm">
		<table id="jsontbP" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
				<%-- <tr>
					<td class="detail2">
					<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">订单信息</div>
					<div class="toggle">
						<input type="button"  id="ipt"  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;" onclick='changeinput(this)'>
						<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="vertical-align: middle;" id="imgsz">
					</div>
					</td>
			</tr> --%>
			<tr>
				<td class="detail2">
					<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable"  class="detail3"
						style="background-color: #fff; padding: 0 0 0 2%; border-collapse: separate; border-spacing: 10px;border-top:1px solid #e8edf2;;border-left:1px solid #e8edf2;;border-right:1px solid #e8edf2;;margin:0 auto">
							<tr>
							<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">基本信息</td>
						</tr>
						<tr>
							<td class="gdtd_tb">
								<div>销售类型：</div>
								<input class="gdtd_select_input"  value="返修订单"  READONLY>
							</td>
							<td class="gdtd_tb">
								<div>返修单号：</div>
								<input class="gdtd_select_input"  value="${entry.FACTORY_NO}" label="返修单号"  READONLY>
							</td>
							<td class="gdtd_tb">
								<div>原订单编号：</div>
								<input class="gdtd_select_input"       value="${entry.FROM_BILL_NO}" label="原订单编号"  READONLY>
							</td>
							 <td class="gdtd_tb">
								<div>问题反馈单号：</div>
								<input class="gdtd_select_input" value="${entry.PROBLEM_FEEDBACK_NO}"  label="反馈单号"  READONLY>
							</td>
						</tr>
						<tr>
							<td class="gdtd_tb">
								<div>下单日期：</div>
								<input class="gdtd_select_input" value="${entry.ORDER_DATE}"READONLY>
							</td>
							<td class="gdtd_tb">
								<div>订单交期：</div>
								<input class="gdtd_select_input" value="${entry.ORDER_DELIVERY_DATE}" READONLY>
							</td>
							<%-- <td class="gdtd_tb">
								<div>订单图纸：</div>
								<input class="gdtd_select_input" name="ATT_PATH" json="ATT_PATH" id="ATT_PATH" value="${entry.ATT_PATH}">
							</td> --%>
							<td class="gdtd_tb">
								<div>预计到货期：</div>
								<input class="gdtd_select_input"  value="${entry.PRE_RECV_DATE}" label="预计到货期"  READONLY>
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
							<td colspan="8">
								<div>备注：</div>
								<textarea style="width: 44.5%;resize:none" 
								rows="5"	maxlength="1000" READONLY>${entry.REMARK}</textarea>
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
								<div>订单组织：</div>
								<textarea class="gdtd_select_input"  READONLY>${entry.LEDGER_NAME}</textarea>
								<input id="LEDGER_ID"  value="${entry.LEDGER_ID}" hidden="true">
							</td>
							<td class="gdtd_tb">
								<div>订货组织：</div>
								<textarea class="gdtd_select_input"  READONLY>${entry.ORDER_CHANN_NAME }</textarea>
							</td>
							<td class="gdtd_tb">
								<div>收货组织：</div>
								<textarea class="gdtd_select_input" 
								 maxlength="100"  READONLY>${entry.RECV_CHANN_NAME}</textarea>
							</td>
							<td class="gdtd_tb">
								<div>经销商名称：</div>
								<textarea class="gdtd_select_input"  READONLY>${entry.CHANN_NAME}</textarea>
							</td>
						</tr>
						<tr>
							<td class="gdtd_tb">
								<div>业务员：</div>
								<textarea class="gdtd_select_input"   READONLY>${entry.SALE_NAME}</textarea>
							</td>
							<td class="gdtd_tb">
								<div>业务部门：</div>
								<textarea class="gdtd_select_input"  READONLY>${entry.SALEDEPT_NAME}</textarea>
							</td>
							<td class="gdtd_tb">
								<div>标签打印名称：</div>
								<textarea style="width: 188.5%;" rows="1" readonly="readonly">${entry.PRINT_NAME}</textarea>
							</td>
							<td class="gdtd_tb"></td>
						</tr>
						<tr>
							<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">收货信息</td>
						</tr>
						<tr>
							<td class="gdtd_tb">
								<div>运输方式：</div>
								<input class="gdtd_select_input"  value="${entry.TRANSPORT}" READONLY>
							</td>
							<td class="gdtd_tb">
								<div>运输结算方式：</div>
								<input class="gdtd_select_input"  value="${entry.TRANSPORT_SETTLE}" READONLY>
							</td>
							<td class="gdtd_tb"></td>
							<td class="gdtd_tb"></td>
						</tr>
						<tr>
							<td class="gdtd_tb">
								<div>收货人：</div>
								<textarea class="gdtd_select_input"   READONLY>${entry.PERSON_CON}</textarea>
							</td>
							<td class="gdtd_tb">
								<div>联系电话：</div>
								<input class="gdtd_select_input"  value="${entry.TEL}" READONLY>
							</td>
							<td class="gdtd_tb">
								<div>收货地址：</div>
								<textarea  style="width: 188.5%;height:30px;" rows="1" READONLY>${entry.RECV_ADDR}</textarea>
							</td>
							<td class="gdtd_tb"></td>
						</tr>
						<tr>
							<td class="gdtd_tb">
								<div>客户姓名：</div>
								<textarea class="gdtd_select_input"  READONLY>${entry.CUST_NAME}</textarea>
							</td>
							<td class="gdtd_tb">
								<div>客户电话：</div>
								<input class="gdtd_select_input"  value="${entry.CUST_TEL}" READONLY>
							</td>
							<td class="gdtd_tb">
								<div>客户住址：</div>
								<textarea style="width: 188.5%;height:30px;" rows="1"  READONLY>${entry.CUST_ADDR}</textarea>
							</td>
							<td class="gdtd_tb"></td>
						</tr>
						<tr>
							<td colspan="6" align="left"  style="font-size: 16px; font-weight: 600;border-bottom:1px solid #e8edf2;border-top: 0px;">审核信息</td>
						</tr>
						<tr>
							<td>
								<div>备注：</div>
								<textarea style="width: 188.5%;" rows="5" placeholder="请填写审核意见" maxlength="200" json="remark2"  id="REMARK2" inputtype="string"
									maxlength="2000">${entry.REMARK2}</textarea>
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
	<!-- 模块，页码，排序Id，排序方式，选择记录主键Id -->
	<div>
		<table style="display:none" id="qxBtnTb"  cellSpacing=0 cellPadding=0 border=0>
			<tr style="height: 40px">
				<td nowrap>
					<c:if test="${empty option }">
					</c:if>
					<c:if test="${not empty option }">
					</c:if>
				</td>
			</tr>
		</table>
	</div>
	<div class="bodycss1 child">
		<table  id="jsontb_div"  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
			<tr>
				<td class="detail2">
				    <div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">物料明细</div>
				</td>
			</tr>
			<tr>
				<td class="detail2">
					<table id="jsontb" width="100%" border="0" cellpadding="5" cellspacing="0" class="lst" >
						<tr class="fixedRow">
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
							<!-- <th nowrap align="center">图纸</th> -->
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
						<c:forEach items="${entry.entrySun}" var="rst" varStatus="row">
							<c:set var="r" value="${row.count % 2}"></c:set>
							<c:set var="rr" value="${row.count}"></c:set>
							<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"  id="tr${rr}">
								<td nowrap class="LMM">${rst.GROUP_NO}</td>
								<td nowrap>${rst.PRD_NO}</td>
								<td nowrap>${rst.PRD_NAME}</td>
								<td nowrap>${rst.PRD_NO}</td>
								<td nowrap class="LMM">${rst.HOLE_SPEC}</td>
								<td nowrap>${rst.PRD_SPEC}</td>
								<td nowrap class="LMM LYGCG" >${rst.PRD_QUALITY}</td>
								<td nowrap class="LMM LYGCG">${rst.PRD_COLOR}</td>
								<td nowrap class="LMM">${rst.PRD_TOWARD}</td>
								<td nowrap class="LMM">${rst.PRD_GLASS}</td>
								<td nowrap class="LMM">${rst.PRD_OTHER}</td>
								<td nowrap class="LMM">${rst.PRD_SERIES}</td>
								<td nowrap class=" LYGCG">${rst.PROJECTED_AREA}</td>
								<td nowrap class=" LYGCG">${rst.EXPAND_AREA}</td>
								<td nowrap class="">${rst.PRD_PLACE}</td>
								<td nowrap class="LMM">
									<c:if test="${rst.IS_NO_LOCK_FLAG eq 0}">
										否
									</c:if>
									<c:if test="${rst.IS_NO_LOCK_FLAG eq 1}">
										是
									</c:if>
								</td>
								<td nowrap>${rst.FIGURE_NO}</td>
								<!-- <td nowrap><div style="float:left"><input type="hidden" id="hid_attPath' + rownum + '"   name="attPath" value="' + arrs[14] + '" /><button type="button" class="layui-dtlbtn " id="attPath' + rownum + '" lay-data="{fileid:\'/a/\'}" title="上传附件"><img src="'+ctxPath+'/styles/'+theme+'/images/icon/upload.png" class="audit"></button></div><div id="view_attPath' + rownum + '"></div></td> -->
								<td nowrap>
									<c:if test="${rst.IS_NO_REBATE_FLAG eq 0}">
										否
									</c:if>
									<c:if test="${rst.IS_NO_REBATE_FLAG eq 1}">
										是
									</c:if>
								</td>
								<td nowrap>${rst.IS_BACKUP_FLAG}</td>
								<td nowrap>${rst.ORDER_NUM}</td>
								<td nowrap class="SHOWPRICE">${rst.PRICE}</td>
								<td nowrap class="SHOWPRICE">${rst.DECT_RATE}</td>
								<td nowrap class="SHOWPRICE">${rst.REBATE_AMOUNT}</td>
								<td nowrap class="SHOWPRICE">${rst.DECT_PRICE}</td>
								<td nowrap class="SHOWPRICE">${rst.ORDER_AMOUNT}</td>
								<td nowrap>${rst.REMARK}</td>
							</tr>
						</c:forEach>
						<c:if test="${empty entry.entrySun}">
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
    </div>
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
	</button> 
	</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">

displayDownFile('ATT_PATH',true);
var lid = $("#LEDGER_ID").val();
//根据订单组织控制明细字段列的显示/隐藏
changeDtlCol(lid);
</script>
</html>