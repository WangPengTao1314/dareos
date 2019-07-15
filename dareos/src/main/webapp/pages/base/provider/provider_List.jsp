<!--  
/**
 * @module 系统管理
 * @func 供应商信息
 */
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>供应商信息列表</title>
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/base/provider/provider_List.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.5%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20">
		<table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td height="20px">&nbsp;&nbsp;当前位置：系统管理&gt;&gt;基础信息&gt;&gt;供应商信息</td>
			<td width="50" align="right"></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20">
		<div class="tablayer" style="margin-left:3px; border-bottom:1px solid #d3d3d3; padding-bottom:3px; margin-bottom:3px;">
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%" style="border:0px">
							<tr>
								<td nowrap>
									<c:if test="${pvg.PVG_EDIT eq 1 }">
										<button class="img_input addbtn" >
											<label for='add'>
											<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
											<input id="add" type="button" class="btn add" value="新增"/>
											</label>
										</button>
										<button class="img_input" >
											<label for='modify'>
											<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
											<input id="modify" type="button" class="btn" value="修改"/>
											</label>
										</button>
									</c:if>
									<c:if test="${pvg.PVG_DELETE eq 1 }">
										<button class="del_input" >
											<label for='delete'>
											<img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
											<input id="delete" type="button" class="del_btn" value="删除"/>
											</label>
										</button>
									</c:if>
									<c:if test="${pvg.PVG_START_STOP eq 1 }">
										<button class="img_input" >
											<label for='start'>
											<img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
											<input id="start" type="button" class="btn" value="启用"/>
											</label>
										</button>
										<button class="img_input" >
											<label for='stop'>
											<img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font">
											<input id="stop" type="button" class="btn" value="停用"/>
											</label>
										</button>
									</c:if>
									<c:if test="${pvg.PVG_BWS eq 1 }">
										<button class="img_input" >
											<label for='query'>
											<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
											<input id="query" type="button" class="btn" value="查询"/>
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
		<div class="lst_area" style="margin-left:3px;">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="PRVD_NO" width="15%">供应商编号</th>
					<th nowrap align="center" dbname="PRVD_NAME" width="15%">供应商名称</th>
					<th nowrap align="center" dbname="PRVD_TYPE" width="10%">供应商类别</th>
					<th nowrap align="center" dbname="PROV" width="6%">省份</th>
					<th nowrap align="center" dbname="CITY" width="6%">城市</th>
					<th nowrap align="center" dbname="PERSON_BUSS" width="16%">业务员</th>
					<th nowrap align="center" dbname="CRE_NAME" width="10%">制单人</th>
					<th nowrap align="center" dbname="CRE_TIME" width="10%">制单时间</th>
					<th nowrap align="center" dbname="DEPT_NAME" width="10%">制单部门</th>
					<th nowrap align="center" dbname="STATE" width="6%">状态</th>
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()" onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%"align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.PRVD_ID}"/>
								<label for="radio_add"></label>
							</div>
						</td>
                        <td nowrap align="center">${rst.PRVD_NO}&nbsp;</td>
                        <td nowrap align="center">${rst.PRVD_NAME}&nbsp;</td>
                        <td nowrap align="center">${rst.PRVD_TYPE}&nbsp;</td>
                        <td nowrap align="center">${rst.PROV}&nbsp;</td>
                        <td nowrap align="center">${rst.CITY}&nbsp;</td>
                        <td nowrap align="center">${rst.PERSON_BUSS}&nbsp;</td>
                        <td nowrap align="center">${rst.CRE_NAME}&nbsp;</td>
                        <td nowrap align="center">${rst.CRE_TIME}&nbsp;</td>
                        <td nowrap align="center">${rst.DEPT_NAME}&nbsp;</td>
                        <td nowrap  json='STATE' align="center">${rst.STATE}&nbsp;</td>  
                        <input type="hidden" id="state${rst.PRVD_ID}" value="${rst.STATE}"/>                      
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
<form id="queryForm" method="post" action="#">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
<input type="hidden" id="selectContion" name="selectContion" value=" STATE in('启用') " />
<input type="hidden" id="selectContionYY" name="selectContionYY" value=" RYZT in('启用') " />
<input type="hidden" id="zoneConditionProv" name="zoneConditionProv" value="" />
<input type="hidden" id="zoneConditionCity" name="zoneConditionCity" value="" />
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo" ><div>供应商编号：</div>
				
						<input type="text" name="PRVD_NO" seltarget="selJGXX" value="${params.PRVD_NO }" inputtype="string"  autocheck="true" class="gdtd_select_input cx_demo"/>
                        <input id="PRVD_ID" name="PRVD_ID" type="hidden" seltarget="selJGXX" value="${params.PRVD_ID }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('BS_25', false, 'PRVD_ID', 'PRVD_ID', 'forms[1]','PRVD_NO,PRVD_NAME', 'PRVD_NO,PRVD_NAME', 'selectContion');">  								   		
					</td>
					<td class="gdtd_tb cx_demo" ><div>供应商名称：</div>
					
						<input type="text" json="PRVD_NAME" name="PRVD_NAME" seltarget="selJGXX" value="${params.PRVD_NAME }" class="gdtd_select_input cx_demo"/>
					</td>
					<td class="gdtd_tb cx_demo"><div>供应商类别：</div>
					
						<select id="PRVD_TYPE" name="PRVD_TYPE" class="gdtd_select_input cx_demo" value="${rst.PRVD_TYPE}" ></select>
					</td>
					
					<td class="gdtd_tb cx_demo"><div>省份：</div>
					
						<input type="text" id="PROV" name="PROV" seltarget="selJGXX" value="${params.PROV }" inputtype="string"  autocheck="true" onChange="setProv()" class="gdtd_select_input cx_demo"/>
                        <input id="PRVD_ID" name="PRVD_ID" type="hidden" seltarget="selJGXX" value="${params.PRVD_ID }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('BS_31', false, 'PRVD_ID', 'PROV', 'forms[1]','PROV', 'PROV', 'zoneConditionProv');setProv();">						
					</td>					
				</tr>
				<tr>
					
					<td class="gdtd_tb cx_demo"><div>城市：</div>
					
						<input type="text" id="CITY" name="CITY" seltarget="selJGXX" value="${params.CITY}" inputtype="string"  autocheck="true"" class="gdtd_select_input cx_demo"/>
                        <input id="PRVD_ID" name="PRVD_ID" type="hidden" seltarget="selJGXX" value="${params.PRVD_ID }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('BS_32', false, 'PRVD_ID', 'CITY', 'forms[1]','CITY', 'CITY', 'zoneConditionCity');">						
					</td>
					<td class="gdtd_tb cx_demo"><div>业务员：</div>
					
						<input type="text" name="PERSON_BUSS" seltarget="selJGXX" value="${params.PERSON_BUSS }" inputtype="string"  autocheck="true" class="gdtd_select_input cx_demo"/>
                        <input id="PRVD_ID" name="PRVD_ID" type="hidden" seltarget="selJGXX" value="${params.PRVD_ID }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('BS_0', false, 'PRVD_ID', 'RYXXID', 'forms[1]','PERSON_BUSS', 'XM', 'selectContionYY');">
					</td>
					<td class="gdtd_tb cx_demo">
						<div>制单时间从：
					</div>
					
						<input name="CRE_TIME_FROM" type="text"	value="${params.CRE_TIME_FROM}" onclick="SelectTime();" class="gdtd_select_input cx_demo">
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
						onclick="SelectTime(CRE_TIME_FROM);"/>
					</td>
					<td class="gdtd_tb cx_demo">
						<div>制单时间到：
					</div>
					
						<input name="CRE_TIME_TO" type="text" value="${params.CRE_TIME_TO}" onclick="SelectTime();" class="gdtd_select_input cx_demo">
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
						onclick="SelectTime(CRE_TIME_TO);"/>
					</td>					
				</tr>
				<tr>					
					
					<td class="gdtd_tb cx_demo">
						<div>状态：</div>
				
					
						<select id="STATE" name="STATE" class="gdtd_select_input cx_demo" value="${rst.STATE}"></select>
					</td>
				</tr>				
				<tr>
					<td colspan="10" align="center" valign="middle" >
						<input id="q_search" type="button" class="btn cx_btn" value="确定" />&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" />&nbsp;&nbsp;
						<input type="reset" class="btn cx_btn" value="重置" />
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
		SelDictShow("PRVD_TYPE","173","${params.PRVD_TYPE}","");
		SelDictShow("STATE","32","${params.STATE}","");	
</script>
</html>
