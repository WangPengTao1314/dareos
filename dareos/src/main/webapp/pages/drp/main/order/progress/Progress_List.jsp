<!--  
/**
  *@module 销售管理
  *@fuc 订单进度列表
  *@version 1.0
  *@author 王朋涛
*/
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>订单进度列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"/>
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/drp/main/order/progress/Progress_List.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">

<tr>
	<td height="20px" valign="top">
	<div class="tablayer" style="margin-left:3px;padding-bottom:3px; margin-bottom:3px;">
		<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
			<tr>
				   <td id="qxBtnTb" nowrap>
				   <c:if test="${pvg.PVG_CONFIRM eq 1 }">
				   		<button class="img_input" >
			                <label for='confirm_delivery'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
			                    <input type="button" id="confirm_delivery" class="btn" value="交期确认">
			                </label>
			           </button>
				    </c:if>
				    <c:if test="${pvg.PVG_FEEDBACK eq 1 }">
				   	   <button class="img_input" >
			                <label for='progress_feedback'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
			                    <input id="progress_feedback" type="button" class="btn" value="进度反馈">
			                </label>
			           </button>
				    </c:if>
				   <c:if test="${pvg.PVG_QUERY eq 1 }">
				   	  <button class="img_input" >
			                <label for='query'>
			                    <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
			                    <input id="query" type="button" class="btn" value="查看">	
			                </label>
			           </button>
				   	</c:if>
				</td>
				</tr>
		</table>
		</div>
	</td>
</tr>
<tr>
	<td>
		<div class="lst_area">
		<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" >
			<tr class="fixedRow">
				<th width="1%"></th>
				<th nowrap align="center" dbname="sale_order_no" >订单编号</th>
				<th nowrap align="center" dbname="order_from" >订单来源</th>
				<th nowrap align="center" dbname="delivery_date" >交货日期</th>
				<th nowrap align="center" dbname="order_num" >订单数量</th>
				<th nowrap align="center" dbname="complete_num" >完成数量</th>
				<th nowrap align="center" dbname="send_num" >发货数量</th>
				<th nowrap align="center" dbname="confirm_peo" >确认人</th>
				<th nowrap align="center" dbname="confirm_date" >确认时间</th>							
				<th nowrap align="center" dbname="state" >状态</th>
			</tr>
			<c:forEach items="${page.result}" var="rst" varStatus="row">
				<c:set var="r" value="${row.count % 2}"></c:set>
				<c:set var="rr" value="${row.count}"></c:set>
				<tr class="list_row${r}" onmouseover="mover(this)" ondblclick="parent.gotoBottomPage()" onmouseout="mout(this)" id="tr${rr}">
					<td width="1%" align='center' >
						<div class="radio_add">
							<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.ORDER_DEGREE_ID}"/>
							<label for="radio_add"></label>
						</div>
					</td>
					<td align="center">${rst.SALE_ORDER_NO}&nbsp;</td>
					<td align="center">${rst.ORDER_FROM}&nbsp;</td>
					<td align="center">${rst.DELIVERY_DATE}&nbsp;</td>
					<td align="center">${rst.ORDER_NUM}&nbsp;</td>
					<td align="center">${rst.COMPLETE_NUM}&nbsp;</td>
					<td align="center">${rst.SEND_NUM}&nbsp;</td>
					<td align="center">${rst.CONFIRM_PEO}&nbsp;</td>  
					<td align="center">${rst.CONFIRM_DATE}&nbsp;</td>
					<td align="center" id="state${rst.ORDER_DEGREE_ID}">${rst.STATE}</td> 
					<script type="text/javascript">
					   $("#tr${rr}").click(function(){
					      selectThis(this);
					      setSelEid(document.getElementById('eid${rr}'));
					   });
				    </script>
				</tr>
			</c:forEach>
			<c:if test="${empty page.result}">
				<tr>
					<td height="25" colspan="13" align="center" class="lst_empty">
						&nbsp;无相关信息&nbsp;
					</td>
				</tr>
			</c:if>
		</table>
		</div>
	</td>
</tr>
<tr>
	<td height="8px">
		<table width="100%" border="0" cellpadding="0"cellspacing="0" class="lst_toolbar">
		<tr>
			<td>
				<form id="pageForm" action="#" method="post" name="listForm">
				<input type="hidden" id="pageSize" name="pageSize" value='${page.pageSize}'/>
					<input type="hidden" id="pageNo" name="pageNo" value='${page.pageNo}'/>
					<input type="hidden" id="orderType" name="orderType" value='${orderType}'/>
					<input type="hidden" id="orderId" name="orderId" value='${orderId}'/>
					<input type="hidden" id="selRowId" name="selRowId" value="${selRowId}">&nbsp;
					<input type="hidden" id="paramUrl" name="paramUrl" value="${paramCover.coveredUrl}">
					<span id="hidinpDiv" name="hidinpDiv"></span>
					${paramCover.unCoveredForbidInputs } 
				</form>
			</td>
			<td align="left">${page.footerHtml}${page.toolbarHtml}</td>
		</tr>
		</table>
	</td>
</tr>	
</table>
<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#" >
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
<input type="hidden" name="selectContion2" value=" DELFLAG = 0 and bmzt = '启用'" />
<input type="hidden" name="selectContion3" value=" DELFLAG = 0 and jgzt = '启用'" />

	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo">
						<div>订单编号：</div>
						<input class="gdtd_select_input cx_demo"  type="text" name="SALE_ORDER_NO" id="SALE_ORDER_NO"  seltarget="selRYXX" value="${params.SALE_ORDER_NO }">
					</td>
					<td class="gdtd_tb cx_demo">
						<div>交货日期从：</div>
						<input name="DELIVERY1" id="DELIVERY1" type="text" seltarget="selJGXX"    value="${params.DELIVERY1 }" class="gdtd_select_input cx_demo" onclick="SelectDate();">
					</td>
					 <td class="gdtd_tb cx_demo">
					 	<div>交货日期到：</div>
		                 <input type="text" class="gdtd_select_input cx_demo" name="DELIVERY2" id="DELIVERY2" seltarget="selJGXX" value="${params.DELIVERY2 }" onclick="SelectDate();">
		      		</td>
					<td class="gdtd_tb cx_demo">
					 	<div>状态：</div>
			      		<select name="STATE" id="STATE" type="text" inputtype="strimg" label="项目类型" class="gdtd_select_input cx_demo"   autocheck="true"></select>	
			      	</td>	
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn" value="确定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" >&nbsp;&nbsp;
						<input id="q_reset" type="button" class="btn cx_btn" value="重置"
						onclick="javascript:$('#queryForm :input').not(':button, :submit, :reset, :hidden').val('');">&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
</body>
<script type=text/javascript>
	SelDictShow("STATE","BS_208","","");
</script>



