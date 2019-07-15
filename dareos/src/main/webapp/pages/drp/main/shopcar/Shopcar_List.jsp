<!--
 * @prjName:喜临门营销平台
 * @fileName:Paymentup_Detial
 * @author lyg
 * @time   2013-08-23 10:25:58 
 * @version 1.1
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" import="com.centit.commons.util.StringUtil" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/drp/main/shopcar/Shopcar_List.js"></script>
	<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
	<style type="text/css">
		a {
			cursor: hand ;
		}
		tr.list_row1 td{
	background-color: #ffffff;	
}
table.lst th{
background-color:#d3d3d3;
color:#000;
font-weight:100;
}
tr.list_row0 td{	
	background-color: #f6f5eb;
}
	</style>
</head>
<body class="bodycss1">
 <input id="delFlag" type="hidden" value="false"/>
 <input id="REBATEDSCT" name="REBATEDSCT" label="返利折扣" type="hidden" value="${REBATEDSCT}">
 <input id="LARGESSDSCT" name="LARGESSDSCT" label="赠品折扣" type="hidden" value="${LARGESSDSCT}">
 <input id="REBATE_TOTAL" name="REBATE_TOTAL" label="返利总金额" type="hidden" value="${REBATE_TOTAL}">
 <input id="REBATE_CURRT" name="REBATE_CURRT" label="当前返利" type="hidden" value="${REBATE_CURRT-REBATE_FREEZE}">
 
 <input id="DECT_RATE" type="hidden" value="${params.DECT_RATE}">
	<div style=" width:100%">
	<table width="99.8%" height="100%" border="0" align="center" cellspacing="0" cellpadding="0" class="panel">
		<tr>
			<td height="20px" valign="top">
		      <table id="qxBtnTb"  cellSpacing=0 cellPadding=0 border=0 style="padding:1%" width="100%">
				<tr style="padding-bottom: 5px;">
				   <td nowrap style="padding-left: 3px; width: 10%">
				   	<c:if test="${pvg.PVG_DELETE eq 1 }">
					   <input id="delete" type="button" class="btn" value="删除" >
					 </c:if>
					</td>
					<td align="right" style="width: 70%">
					 <span style="font-weight: bolder;">总金额：</span><font id="total">0</font>
					 <span style="font-weight: bolder;">总数量：</span><font id="allNum">0</font>
					 
					 	<c:if test="${pvg.PVG_EDIT eq 1 }">
					 <input id="addPrd" style="margin-left: 5px" type="button" class="btn" value="生成订货订单"  >
					 </c:if>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td valign="top" colspan="2" >
				<div class="lst_area" style="margin-left:3px;">
					<form onsubmit="return false;">
					   <input type="hidden" id="CHANN_ID"  value="${CHANN_ID}"/>
					   <input type="hidden" id="picture_url" name="" value="${picture_url}" />
						<table width="100%" border="0" cellpadding="1" cellspacing="1" id="jsontb" class="lst">
							<tr class="fixedRow">
								<th nowrap="nowrap" align="center" ><input type="checkbox" style="height:12px;valign:middle" id="allChecked" /></th>
								<th nowrap="nowrap" align="center">图片</th>
								<th nowrap="nowrap" align="center">货品信息</th>
								<th nowrap="nowrap" align="center">规格属性</th>
								<th nowrap="nowrap" align="center">单位</th>
								<th nowrap="nowrap" align="center">参考单价</th>
								<th nowrap="nowrap" align="center">数量</th>
								<th nowrap="nowrap" align="center">金额</th>
								<th nowrap="nowrap" align="center" >操作</th>
							</tr>
							<c:forEach items="${list}" var="rst" varStatus="row">
								<c:set var="r" value="${row.count % 2}"></c:set>
								<c:set var="rr" value="${row.count}"></c:set>
								<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selectThis(this);setEidChecked(document.getElementById('eid${rr}'));">
								<td width="1%"align='center' >
									<input type="checkbox" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.SHOP_CART_ID}"  />
								 </td>
								  <td nowrap="nowrap" align="center">
				                  	${rst.ATT_PATH}
				                  </td>
				                  <td nowrap="nowrap" align="center">
				                  		${rst.PRD_NAME}
								  </td>
				                  <td nowrap="nowrap"  id="spec${rr}" align="center">
				                  		<span>
					                  		${rst.HOLE_SPEC}&nbsp;
					                  		${rst.PRD_SIZE}&nbsp;
											${rst.PRD_QUALITY}&nbsp;
											${rst.PRD_COLOR}&nbsp;
											${rst.PRD_TOWARD}&nbsp;
											${rst.PRD_GLASS}&nbsp;
											${rst.PRD_OTHER}&nbsp;
											${rst.PRD_SERIES}&nbsp;
											${PROJECTED_AREA}&nbsp;
											${EXPAND_AREA}&nbsp;
											${rst.HOLE_SPEC}
										</span>
				                  	<input type="button" value="..."  style="background-color:#199ed8; border:none; width:26px; height:26px;float:right" onclick="openSpec(this,'${rr}')"></input>
				                  </td>
				                  <td nowrap="nowrap" align="center" >
				                  	${rst.STD_UNIT}
				                  </td>
				                  <td nowrap="nowrap"  align="center" id="price${rr}">
				                  	${rst.PRICE}
				                  </td>
				                  <td nowrap="nowrap" align="center">
				                  	<input  type="text" id="ORDER_NUM${rr }" json="ORDER_NUM"  name="ORDER_NUM" value="1" onkeyup="countAmount('${rr }')"  style="text-align:center;width: 80px;" />
				                  </td>
				              		<td id="amount${rr}">
				              			${rst.ORDER_AMOUNT}
				              		</td>
						          <td>
						          	<a href="#" onclick="delShopCar('${rst.SHOP_CART_ID}',this)">删除</a>
						          	<input type="hidden" name="ORDER_AMOUNT" json="ORDER_AMOUNT" value="${rst.ORDER_AMOUNT}" />
						          	<input type="hidden" name="SHOP_CART_ID" json="SHOP_CART_ID" value="${rst.SHOP_CART_ID}" />
						          	<input type="hidden" name="PRD_ID" value="${rst.PRD_ID}"/>
							          <input type="hidden" name="PRICE" value="${rst.RET_PRICE_MIN}"/>
							          <input type="hidden" name="HOLE_SPEC" value="${rst.HOLE_SPEC}"/>
							          <input type="hidden" name="PRD_QUALITY" value="${rst.PRD_QUALITY}"/>
							          <input type="hidden" name="PRD_TOWARD" value="${rst.PRD_TOWARD}"/>
							          <input type="hidden" name="PRD_GLASS" value="${rst.PRD_GLASS}"/>
							          <input type="hidden" name="PRD_OTHER" value="${rst.PRD_OTHER}"/>
							          <input type="hidden" name="PRD_SERIES" value="${rst.PRD_SERIES}"/>
							          <input type="hidden" name="PRD_SIZE" value="${rst.PRD_SIZE}"/>
							          <input type="hidden" name="ATT_PATH" value="${rst.ATT_PATH}" />
							          <input type="hidden" name="REMARK" value="${rst.REMARK}" />
							          <input type="hidden" name="PRD_COLOR" value="${rst.PRD_COLOR}"/>
							          <input type="hidden" name="PROJECTED_AREA" value="${rst.PROJECTED_AREA}" />
							          <input type="hidden" name="EXPAND_AREA" value="${rst.EXPAND_AREA}"/>
							          <input type="hidden" name="LEDGER_ID" value="${rst.LEDGER_ID}"/>
						          </td>
							    </tr>
							</c:forEach>
							<c:if test="${empty list}">
								<tr>
									<td height="25" colspan="16" align="center" class="lst_empty">
						                无相关记录
									</td>
								</tr>
							</c:if>
						</table>
					</form>
				</div>
			</td>
		</tr>
		<tr>
			<td height="12px" align="center">
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="lst_toolbar">
					<tr>
						<td>
							<form id="listForm" action="#" method="post" name="listForm">
								<input id="REBATEFLAG" name="REBATEFLAG" value="${REBATEFLAG}" type="hidden"/>
								<input id="LARGESSFLAG" name="LARGESSFLAG" value="${LARGESSFLAG}" type="hidden"/>
								<input id="SHOP_CART_TYPE" name="SHOP_CART_TYPE" value="${params.SHOP_CART_TYPE}" type="hidden"/>
								<input type="hidden" id="pageSize" name="pageSize" value='${page.pageSize}'/>
								<input type="hidden" id="pageNo" name="pageNo" value='${page.pageNo}'/>
								<input type="hidden" id="orderType" name="orderType" value='${orderType}'/>
								<input type="hidden" id="orderId" name="orderId" value='${orderId}'/>
								<input type="hidden" id="selRowId" name="selRowId" value="${selRowId}">
								<input type="hidden" id="paramUrl" name="paramUrl" value="${paramCover.coveredUrl}">
								<span id="hidinpDiv" name="hidinpDiv"></span>
								${paramCover.unCoveredForbidInputs }
							</form>
						</td>
						<td align="left">
							 ${page.footerHtml}${page.toolbarHtml}
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</div>
</body>
<script type="text/javascript">
   SelDictShow("SHOP_CART_TYPE_","BS_110","${params.SHOP_CART_TYPE}","");
</script>
</html>