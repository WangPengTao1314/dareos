<!--
 * @prjName:喜临门营销平台
 * @fileName:Prmtplan_List
 * @author zzb
 * @time   2013-08-23 16:04:28 
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
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/erp/sale/prmtplan/Prmtplan_List.js"></script>
	<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<tr>
	<td height="20px" valign="top">
	<div class="tablayer button_cls">
		<!-- 	<table id="qxBtnTb" cellSpacing=0 cellPadding=0 border=0 width="100%" style="border:0px"> -->
				<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
				<tr>
                  <td nowrap>
				   <c:if test="${pvg.PVG_EDIT eq 1 }">
			   		<button class="img_input addbtn" >
			                <label for='add'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
			                    <input type="button" id="add" class="btn add" value="新增">
			                </label>
			           </button>
				   	   <button class="img_input" >
			                <label for='modify'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
			                    <input id="modify" type="button" class="btn" value="修改">
			                </label>
			           </button>
				   </c:if>
				   <c:if test="${pvg.PVG_DELETE eq 1 }">
			   		<button class="del_input" >
			                <label for='delete'>
			                    <img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
			                    <input id="delete" type="button" class="del_btn" value="删除" >
			                </label>
			           </button>
				   </c:if>
				   <c:if test="${pvg.PVG_START_STOP eq 1 }">
					<button class="img_input" >
			                <label for='start'>
			                    <img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
			                    <input id="start" type="button" class="btn" value="启用" >
			                </label>
			           </button>
					   	<button class="img_input" >
			                <label for='stop'>
			                    <img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font">
			                    <input id="stop" type="button" class="btn" value="停用" >
			                </label>
			           </button>	
				   </c:if>
				   <c:if test="${pvg.PVG_BWS eq 1 or pvg.PVG_BWS_AUDIT eq 1 }">
			   		 <button class="img_input" >
			                <label for='query'>
			                    <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
			                    <input id="query" type="button" class="btn" value="查询">	
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
	<td valign="top">
		<div class="lst_area">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
                   
                    <th  nowrap="nowrap" align="center" dbname="PRMT_PLAN_NO" >促销方案编号</th>
                    <th  nowrap="nowrap" align="center" dbname="PRMT_PLAN_NAME" >促销方案名称</th>
                    <th  nowrap="nowrap" align="center" dbname="PRMT_TYPE" >促销类型</th>
                     <th  nowrap="nowrap" align="center" dbname="EFFCT_DATE_BEG" >生效日期从</th>
                     <th  nowrap="nowrap" align="center" dbname="EFFCT_DATE_END" >生效日期到</th>
                    <th  nowrap="nowrap" align="center" dbname="CRE_NAME" >制单人</th>
                    <th  nowrap="nowrap" align="center" dbname="CRE_TIME" >制单时间</th>
                    <th  nowrap="nowrap" align="center" dbname="DEPT_NAME" >制单部门</th>
                    <th  nowrap="nowrap" align="center" dbname="STATE" >状态</th>
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"  onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
					 <td width="1%"align='center' >
						<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.PRMT_PLAN_ID}"/>
								<label for="radio_add"></label>
							</div>
					 </td>
                    
                     <td nowrap="nowrap" align="center">${rst.PRMT_PLAN_NO}</td>
                     <td nowrap="nowrap" align="left">${rst.PRMT_PLAN_NAME}</td>
                     <td nowrap="nowrap" align="left">${rst.PRMT_TYPE}</td>
                     <td nowrap="nowrap" align="center">${rst.EFFCT_DATE_BEG}</td>
                      <td nowrap="nowrap" align="center">${rst.EFFCT_DATE_END}</td>
                     <td nowrap="nowrap" align="left">${rst.CRE_NAME}</td>
                     <td nowrap="nowrap" align="center">${rst.CRE_TIME}</td>
                     <td nowrap="nowrap" align="left">${rst.DEPT_NAME}</td>
                     <td nowrap="nowrap" align="center" id="state${rst.PRMT_PLAN_ID}" >${rst.STATE}</td>
                    <input id="state${rst.PRMT_PLAN_ID}" type="hidden"  value="${rst.STATE}" />
				    </tr>
				</c:forEach>
				<c:if test="${empty page.result}">
					<tr>
						<td height="25" colspan="10" align="center" class="lst_empty">
			                无相关记录
						</td>
					</tr>
				</c:if>
			</table>
		</div>
	</td>
</tr>
<tr>
	<td height="12px">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0" class="lst_toolbar">
			<tr>
				<td>
					<form id="pageForm" action="#" method="post" name="listForm">
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
 <tr>
	<td height="12px" align="center">		
	</td>
</tr> 
</table>
<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#">
<input type="hidden" name="FLAG" id="FLAG" value="${FLAG}"/>
<table width="100%" border="0" cellpadding="4" cellspacing="4" class="detail cx_table">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="1" cellspacing="1" class="detail3">
               <tr>
                    <td class="gdtd_tb cx_demo"><div>促销方案编号:</div>
					    <input type="hidden" id="selectParams"  value="" class="gdtd_select_input cx_demo">
					    <input type="hidden" name="search" value="1" class="gdtd_select_input cx_demo"/>
					    <input type="hidden"  id="PRMT_PLAN_ID" name="PRMT_PLAN_ID"  value="${params.PRMT_PLAN_ID}" class="gdtd_select_input cx_demo"/>
	   					<input type="text" id="PRMT_PLAN_NO" name="PRMT_PLAN_NO"   value="${params.PRMT_PLAN_NO}" class="gdtd_select_input cx_demo"/>
	   					 <img align="absmiddle" name="selJGXX" class="select_gif"
							src="${ctx}/styles/${theme}/images/plus/select.gif"       
							onClick="selCommon('BS_47', false, 'PRMT_PLAN_ID', 'PRMT_PLAN_ID', 'forms[1]','PRMT_PLAN_NO,PRMT_PLAN_NAME','PRMT_PLAN_NO,PRMT_PLAN_NAME', '')">
					</td>					
                    <td class="gdtd_tb cx_demo"><div>促销方案名称:</div>
					
					  <input type="text" id="PRMT_PLAN_NAME" name="PRMT_PLAN_NAME"  value="${params.PRMT_PLAN_NAME}" class="gdtd_select_input cx_demo"/>
					</td>
                    <td class="gdtd_tb cx_demo"><div>促销类型:</div>
					
					 <select name="PRMT_TYPE" id="PRMT_TYPE" class="gdtd_select_input cx_demo"></select>
					</td>
                    <td class="gdtd_tb cx_demo"><div>状态:</div>
					
					  <select name="STATE" id="STATE" class="gdtd_select_input cx_demo"></select>
					</td>
               </tr>
               <tr>
                    <td class="gdtd_tb cx_demo"><div>生效日期从:</div>
					
	   					<input  type="text"  id="EFFCT_DATE_BEG" name="EFFCT_DATE_BEG" readonly="readonly"   onclick="SelectDate();" class="gdtd_select_input cx_demo" value="${params.EFFCT_DATE_BEG}">
	   					<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"  onclick="SelectDate(EFFCT_DATE_BEG);" >
					</td>					
                    <td class="gdtd_tb cx_demo"><div>生效日期到:</div>
					
					 <input  type="text"  id="EFFCT_DATE_END" name="EFFCT_DATE_END" readonly="readonly"   onclick="SelectDate();" class="gdtd_select_input cx_demo" value="${params.EFFCT_DATE_END}">
	   					<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"  onclick="SelectDate(EFFCT_DATE_END);" >
					</td>
                    <td  class="gdtd_tb cx_demo"><div>制单时间从:</div>
					
					 <input  type="text"  id="CRE_TIME_BEGIN" name="CRE_TIME_BEGIN" readonly="readonly"   onclick="SelectTime();" class="gdtd_select_input cx_demo"  value="${params.CRE_TIME_BEGIN}">
	   					<img align="absmiddle" class="select_gif"src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"  onclick="SelectTime(CRE_TIME_BEGIN);" >
					</td>
                    <td class="gdtd_tb cx_demo"><div>制单时间到:</div>
					
					  <input  type="text"  id="CRE_TIME_END" name="CRE_TIME_END" readonly="readonly"   onclick="SelectTime();" class="gdtd_select_input cx_demo" value="${params.CRE_TIME_END}">
	   					<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"  onclick="SelectTime(CRE_TIME_END);" >
					</td>
               </tr>
               <tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn" value="确定" >
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" >
						<input  type="reset" class="btn cx_btn" value="重置" >
					</td>
			   </tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
</body>
</html>
<script type="text/javascript">
    SelDictShow("PRMT_TYPE","BS_23","${params.PRMT_TYPE}","");
   	SelDictShow("STATE", "32", "${params.STATE}", "");
</script>