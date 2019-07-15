
<!--  
/**
 * @module 系统管理
 * @func 区域分管信息
 * @version 1.1
 * @author 张忠斌
 */
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>区域信息列表</title>
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/base/areachrg/areaChrg_List.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<tr>
	<td height="20px" valign="top">
	  <!--  <table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td height="20px">&nbsp;&nbsp;当前位置：系统管理&gt;&gt;基础信息&gt;&gt;区域分管维护</td>
			<td width="50" align="right"></td>
		</tr>
	  </table>   -->
	</td>
</tr>
<tr>
	<td height="20px" valign="top" >
	<div class="tablayer" style="margin-left:3px; border-bottom:1px solid #d3d3d3; padding-bottom:3px; margin-bottom:3px;">
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%" style="margin-left: 3px;">
				<tr>
				   <td nowrap>
			<!--     <c:if test="${qxcom.XT0010302 eq 1 }">
			   		<input id="add" type="button" class="btn" value="新增(N)" title="Alt+N" accesskey="N">
			   </c:if>
			   <c:if test="${qxcom.XT0010302 eq 1 }">
			   		<input id="modify" type="button" class="btn" value="修改(U)" title="Alt+U" accesskey="U">
			   </c:if>
			   <c:if test="${qxcom.XT0010303 eq 1 }">
			   		<input id="delete" type="button" class="btn" value="删除(R)" title="Alt+R" accesskey="R">
			   </c:if>-->
			   <c:if test="${qxcom.XT0010301 eq 1 }">
			   		
			   		
			   		<button class="img_input" >
						<label for='query'>
						<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
						<input type="button" id="query" class="btn" value="查询">
	 					</label>
					</button>
			   		
			   </c:if>
			   <!--  
			   <c:if test="${qxcom.XT0010304 eq 1 }">
			   		<input id="start" type="button" class="btn" value="启用(Q)" title="Alt+Q" accesskey="Q">		
			   </c:if>
			   <c:if test="${qxcom.XT0010304 eq 1 }">
			   		<input id="stop" type="button" class="btn" value="停用(T)" title="Alt+T" accesskey="T">
			   </c:if>	-->
			   <c:if test="${qxcom.XT0010305 eq 1 }">
				   <input id="personal" type="button" class="btn" value="个性化设置" >
			   </c:if>
				</td>
				</tr>
			</table>
			</div>
	</td>
</tr>
<tr>
	<td valign="top">
		<div class="lst_area" style="margin-left:3px;">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="AREA_NO">区域编号</th>
					<th nowrap align="center" dbname="AREA_NAME">区域名称</th>
					<th nowrap align="center" dbname="AREA_NO_P">上级区域编号</th>
					<th nowrap align="center" dbname="AREA_NAME_P">上级区域名称</th>
					<th nowrap align="center" dbname="CRE_NAME">制单人</th>
					<th nowrap align="center" dbname="CRE_TIME">制单时间</th>
					<th nowrap align="center" dbname="DEPT_NAME">制单部门</th>
					<th nowrap align="center" dbname="STATE" >状态</th>
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"  ondblclick="parent.gotoBottomPage()"  onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%"align='center' >
						<div class="radio_add">
	<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.AREA_ID}"/>
	<label for="radio_add"></label>
</div>
						
							
						</td>
                        <td nowrap align="center">${rst.AREA_NO}&nbsp;</td>
                        <td nowrap align="center">${rst.AREA_NAME}&nbsp;</td>
                        <td nowrap align="center">${rst.AREA_NO_P}&nbsp;</td>
                        <td nowrap align="center">${rst.AREA_NAME_P}&nbsp;</td>
                        <td nowrap align="center">${rst.CRE_NAME}&nbsp;</td>
                        <td nowrap align="center">${rst.CRE_TIME}&nbsp;</td>
                        <td nowrap align="center">${rst.DEPT_NAME}&nbsp;</td>
                        
                        <td nowrap json='STATE' align="left">${rst.STATE}&nbsp;</td>
                        <input type="hidden" id="state${rst.AREA_ID}" value="${rst.STATE}" /><!--  用于判断状态  看明细列表 是否可以编辑-->
				        
				
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
	<td height="12px" align="center">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0" class="lst_toolbar">
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
					${paramCover.unCoveredForbidInputs}
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


<div id="querydiv" class="query_div" >
<form id="queryForm" method="post" action="#" style="margin-top:2%">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
<input type="hidden" name="selectParams" value=" STATE in('启用','停用','制定') " />
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo"><div>区域编号：</div>
					
						<input type="text" name="AREA_NO" seltarget="selJGXX" value="${params.AREA_NO}" inputtype="string"  autocheck="true" class="gdtd_select_input cx_demo" />
                       
                        <input id="AREA_ID1" name="AREA_ID1" type="hidden" seltarget="selJGXX" value="${params.AREA_ID}" class="gdtd_select_input cx_demo">
                        
						 <img align="absmiddle" name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('BS_18', false, 'AREA_ID1', 'AREA_ID', 'forms[1]','AREA_NO,AREA_NAME', 'AREA_NO,AREA_NAME', 'selectParams')"> 								   		
					</td>
					<td class="gdtd_tb cx_demo"><div>区域名称：</div>
					
						<input type="text" json="AREA_NAME" name="AREA_NAME" seltarget="selJGXX" value="${params.AREA_NAME}" class="gdtd_select_input cx_demo"/>
					</td>
			 
					<td  class="gdtd_tb cx_demo"><div>上级区域编号：</div>
				
						<input type="text" name="AREA_NO_P" seltarget="selAREA_P" value="${params.AREA_NO_P}" class="gdtd_select_input cx_demo"/>
                        <input type="hidden" id="AREA_ID_P" name="AREA_ID_P"  seltarget="selAREA_P" value="${params.AREA_ID_P}" class="gdtd_select_input cx_demo">
                        
						 <img align="absmiddle" name="selSJJG" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
				onClick="selCommon('BS_18', false, 'AREA_NO_P', 'AREA_ID', 'forms[1]','AREA_NO_P,AREA_NAME_P', 'AREA_NO,AREA_NAME', 'selectParams')"> 								   										   		
					</td>
					
					
					<td class="gdtd_tb cx_demo"><div>状态：</div>
					
	   					<select id="STATE" name="STATE"  class="gdtd_select_input cx_demo"></select>
					</td>
					
				</tr>
				<tr>
					
					<td class="gdtd_tb cx_demo"><div>上级区域名称：</div>
					
						<input type="text" json="AREA_NAME_P" name="AREA_NAME_P" seltarget="selAREA_P" value="${params.AREA_NAME_P}" class="gdtd_select_input cx_demo"/>
					</td>
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle" >
						<input id="q_search" type="button" class="btn cx_btn" value="确定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" >&nbsp;&nbsp;
						<input  type="reset" class="btn cx_btn" value="重置">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>

</body>
<script language="JavaScript">
	//$(function(){
		SelDictShow("STATE","32","${params.STATE}","");
	//});
</script>
</html>
