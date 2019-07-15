<!--
 * @prjName:喜临门营销平台
 * @fileName:Goodsorderhd_List
 * @author zzb
 * @time   2013-09-15 10:35:10 
 * @version 1.1
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>浏览</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/drp/sale/goodsorderhd/Goodsorderhd_List_Chld.js"></script>
			<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<tr>
	<td height="20">
		<div class="tablayer" >
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
				<tr>
                	<td nowrap>
					   <c:if test="${pvg.PVG_EDIT eq 1 }">
				   		<input id="edit" type="button" class="btn" value="编辑" >
				   	   </c:if>
				   	   <c:if test="${pvg.PVG_DELETE eq 1 }">
				   		<input id="delete" type="button" class="btn" value="删除" >
				   	   </c:if>
					   &nbsp;
					</td>
					<td align="right">
						总金额:<span style="margin-right: 20px;" id="totalAmount">${totalInfo.TOTALAMOUNT}</span>
						总数量:<span style="margin-right: 20px;" id="totalNum">${totalInfo.TOTALNUM}</span>
						总体积:<span style="margin-right: 20px;" id="totalNum">${totalInfo.TOTALVOLUME}</span>
					</td>
				</tr>
			</table>
		</div>
	</td>
</tr>
<tr>
	<td valign="top">
		<div class="lst_area">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow" >
					<th nowrap align="center" style="width:2%"><input type="checkbox" style="valign:middle" id="allChecked"></th>
					<th  nowrap="nowrap" align="center" dbname="ROW_NO" >行号</th>
                    <th  nowrap="nowrap" align="center" dbname="PRD_NAME" >货品名称</th>
                    <th  nowrap="nowrap" align="center" dbname="PRD_SPEC" >规格型号</th>
<!--                    <th  nowrap="nowrap" align="center" dbname="PRD_COLOR" >花号</th>-->
                    <th  nowrap="nowrap" align="center" dbname="BRAND" >品牌</th>
                    <th  nowrap="nowrap" align="center" dbname="STD_UNIT" >标准单位</th>
                    <th  nowrap="nowrap" align="center" dbname="" name="hideTdByBillType" >特殊规格说明</th>
                    <th  nowrap="nowrap" align="center" dbname="PRICE" >单价</th>
                    <th  nowrap="nowrap" align="center" dbname="DECT_RATE" >折扣率</th>
                    <th  nowrap="nowrap" align="center" dbname="DECT_PRICE" >折后单价</th>
                    <th  nowrap="nowrap" align="center" dbname="ORDER_NUM" >订货数量</th>
                    <th  nowrap="nowrap" align="center" dbname="ORDER_RECV_DATE" >要求到货日期</th>
                    <th  nowrap="nowrap" align="center" dbname="VOLUME" >货品体积</th>
                    <th  nowrap="nowrap" align="center" dbname="TOTAL_VOLUME" >总体积</th>
                    <th  nowrap="nowrap" align="center" dbname="ORDER_AMOUNT" >总金额</th>
                    <th  nowrap="nowrap" align="center" dbname="OLD_PRICE" >原价格</th>
                    <th  nowrap="nowrap" align="center" dbname="REMARK" >备注</th>
                    <th  nowrap="nowrap" align="center" dbname="CANCEL_FLAG" >状态</th>
				</tr>
				<c:forEach items="${rst}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" id="tr{rr}" onclick="selectThis(this);setEidChecked(document.getElementById('eid${rr}'));">
					 <td style="width:2%"align='center' >
						<input type="checkbox"  style="valign:middle"  id="eid${rr}" value="${rst.GOODS_ORDER_DTL_ID}" onclick="setEidChecked(this);"/>
					 </td>
					 <td nowrap="nowrap" align="center" >${rst.ROW_NO}&nbsp;</td>
                     <td nowrap="nowrap" align="center" >
                      <a href="javascript:void(0);" onclick="oppenAdvcDtlPage(this);" url="toLookAdvc?GOODS_ORDER_DTL_ID=${rst.GOODS_ORDER_DTL_ID}">
                      ${rst.PRD_NO}
                     <c:if test="${rst.DM_SPCL_TECH_NO ne null}">-${rst.DM_SPCL_TECH_NO}</c:if>
                     </a>
                     </td>
                     <td nowrap="nowrap" align="left" >${rst.PRD_NAME}&nbsp;</td>
                     <td nowrap="nowrap" align="center" >${rst.PRD_SPEC}&nbsp;</td>
<!--                     <td nowrap="nowrap" align="left" >${rst.PRD_COLOR}&nbsp;</td>-->
                     <td nowrap="nowrap" align="center" >${rst.BRAND}&nbsp;</td>
                     <td nowrap="nowrap" align="center" >${rst.STD_UNIT}&nbsp;</td>
                      <td nowrap="nowrap" align="center" name="hideTdByBillType" >
                     	 <c:if test="${rst.SPCL_TECH_FLAG ge 1}">
                     	 	<c:if test="${!empty rst.SPCL_SPEC_REMARK }">
	                     		<label class="hideNoticeText" title="${rst.SPCL_SPEC_REMARK}">${rst.SPCL_SPEC_REMARK}</label>
	                     	</c:if>
                     	 	<input type="button" class="btn" value="查看" onclick="url('${rst.SPCL_TECH_ID}','${rst.PRICE}')" />
                     	 </c:if>
                     	 <c:if test="${rst.SPCL_TECH_FLAG eq '0' || rst.SPCL_TECH_FLAG eq null}">
                     	 	无
                     	 </c:if>
                     	&nbsp;
                     </td>
                     <td nowrap="nowrap" align="right" >${rst.PRICE}&nbsp;</td>
                     <td nowrap="nowrap" align="right" >${rst.DECT_RATE}&nbsp;</td>
                     <td nowrap="nowrap" align="right" >${rst.DECT_PRICE}&nbsp;</td>
                     <td nowrap="nowrap" align="right" >${rst.ORDER_NUM}&nbsp;</td>
                     <td nowrap="nowrap" align="center" >${rst.ORDER_RECV_DATE}</td>
                     <td nowrap="nowrap" align="right" >${rst.VOLUME}&nbsp;</td>
                     <td nowrap="nowrap" align="right" >${rst.TOTAL_VOLUME}&nbsp;</td>
                     <td nowrap="nowrap" align="right">
                       <input type="hidden" name="HID_ORDER_AMOUNT" value="${rst.ORDER_AMOUNT}" />
                      ${rst.ORDER_AMOUNT}&nbsp;
                     </td>
                     <td nowrap="nowrap" align="right" >${rst.OLD_PRICE}&nbsp;</td>
                     <td nowrap="nowrap" align="right" >${rst.REMARK}&nbsp;</td>
                     
                     <td nowrap="nowrap" align="center" >
                       <c:if test="${rst.CANCEL_FLAG eq 1}">总部取消</c:if>
                        <c:if test="${empty rst.CANCEL_FLAG || rst.CANCEL_FLAG eq 0}">正常</c:if>
                     </td>
                     <input type="hidden" id="CANCEL_FLAG${rr}" name="CANCEL_FLAG" value="${rst.CANCEL_FLAG}"/>
				    </tr>
				</c:forEach>
				<c:if test="${empty rst}">
					<tr>
						<td height="25" colspan="20" align="center" class="lst_empty">
			                &nbsp;无相关记录&nbsp;
						</td>
					</tr>
				</c:if>
			</table>
		</div>
	</td>
</tr>
</table>
<form id="form1" action="#" method="post" name="listForm">
	<input type="hidden" id="GOODS_ORDER_DTL_IDS" name="GOODS_ORDER_DTL_IDS" value=""/>
</form>
</body>
</html>