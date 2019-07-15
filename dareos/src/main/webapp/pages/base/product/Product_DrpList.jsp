
<!--  
/**
 * @module 系统管理
 * @func 货品信息
 * @version 1.1
 * @author 刘曰刚
 */
-->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>货品信息列表</title>
		<link rel="stylesheet" type="text/css"	href="${ctx}/styles/${theme}/css/style.css">
		<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>		
		<script type="text/javascript" src="${ctx}/pages/base/product/Product_DrpList.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
		#upFile {
			margin: 0px auto; 
			width:400px;
			border: 1px;
			z-index:1;
			position:absolute;
			background-color: white;
			border:1px solid black;
			height:100px;
			left:230px;
			top:110px;
			text-align:center;
			line-height: 100px;
			display: none;
		}
	</style>
	</head>
	<body>
		<table width="99.8%" height="100%" border="0" cellspacing="0"
			cellpadding="0" class="panel">
			<!-- <tr>
				<td height="20px" valign="top">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td height="20px">&nbsp;&nbsp;
								当前位置：系统管理&gt;&gt;基础信息&gt;&gt;货品信息
							</td>
							<td width="50" align="right"></td>
						</tr>
					</table>
				</td>
			</tr> -->
			<tr>
				<td height="30">
		<div class="tablayer" style="margin-left:3px; border-bottom:1px solid #d3d3d3; padding-bottom:3px; margin-bottom:3px;">
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%" style="border:0px">
						<tr>
							<td nowrap>
								<c:if test="${pvg.PVG_EDIT eq 1 or pvg.PVG_DRP_EDIT eq 1 }">
									<input id="add" type="button" class="btn" value="新增">								
									<input id="modify" type="button" class="btn" value="修改">
								</c:if>
								<c:if test="${pvg.PVG_DELETE eq 1 or pvg.PVG_DRP_DELETE eq 1 }">
									<input id="delete" type="button" class="btn" value="删除">
								</c:if>
								<c:if test="${pvg.PVG_START_STOP eq 1 or pvg.PVG_DRP_START_STOP eq 1 }">
									<input id="start" type="button" class="btn" value="启用">								
									<input id="stop" type="button" class="btn" value="停用">
								</c:if>
								<c:if test="${pvg.PVG_BWS eq 1 or pvg.PVG_DRP_BWS eq 1 }">
									<input id="query" type="button" class="btn" value="查询">
								</c:if>
								<c:if test="${pvg.PVG_DRP_IMPORT eq 1}">
									<input id="up" type="button" class="btn" value="导入" >
									<input id="export" type="button" class="btn" value="导出" >
								</c:if>
							    <div id="upFile">
							   		<input id="upInfo" value=""/>
							   		<p>
							   		<input value="关闭" type="button" class="btn" id="close" style="margin-right: 10px;">
							   		<input id="tempUp" type="button" class="btn"  value="模版下载" >
							   		<input id="type" value="" type="hidden">
							   </div>
							</td>
						</tr>
					</table>
					</div>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<div class="lst_area" style="margin-left:3px;">
						<table id="ordertb" width="100%" border="0" cellpadding="0"
							cellspacing="0" class="lst">
							<tr class="fixedRow">
								<th width="1%"></th>
								<th nowrap align="center" dbname="PRD_NO">货品编号</th>
								<th nowrap align="center" dbname="PRD_NAME">货品名称</th>
								<th nowrap align="center" dbname="PRD_SPEC">规格型号</th>								
								<th nowrap align="center" dbname="STD_UNIT">标准单位</th>								
								<th nowrap align="center" dbname="CRE_NAME">制单人</th>
								<th nowrap align="center" dbname="CRE_TIME">制单时间</th>
								<th nowrap align="center" dbname="DEPT_NAME">制单部门</th>
								<th nowrap align="center" dbname="STATE">状态</th>
							</tr>
							<c:forEach items="${page.result}" var="rst" varStatus="row">
								<c:set var="r" value="${row.count % 2}"></c:set>
								<c:set var="rr" value="${row.count}"></c:set>
								<tr class="list_row${r}" onmouseover="mover(this)"
									onmouseout="mout(this)"
									onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
									<td width="1%" align='center'>
										<input type="radio" style="height: 12px; valign: middle"
											name="eid" id="eid${rr}" value="${rst.PRD_ID}" />
									</td>
									<td nowrap align="left">${rst.PRD_NO}&nbsp;</td>
									<td nowrap align="left">${rst.PRD_NAME}&nbsp;</td>
									<td nowrap align="left">${rst.PRD_SPEC}&nbsp;</td>									
									<td nowrap align="center">${rst.STD_UNIT}&nbsp;</td>									
									<td nowrap align="left">${rst.CRE_NAME}&nbsp;</td>
									<td nowrap align="center">${rst.CRE_TIME}&nbsp;</td>
									<td nowrap align="left">${rst.DEPT_NAME}&nbsp;</td>									
									<td nowrap json='STATE' align="center">${rst.STATE}&nbsp;</td>																		
								</tr>
							</c:forEach>
							<c:if test="${empty page.result}">
								<tr>
									<td height="25" colspan="14" align="center" class="lst_empty">
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
									<input type="hidden" id="pageSize" name="pageSize" value='${page.pageSize}' />
									<input type="hidden" id="pageNo" name="pageNo" value='${page.pageNo}' />
									<input type="hidden" id="orderType" name="orderType" value='${orderType}' />
									<input type="hidden" id="orderId" name="orderId" value='${orderId}' />
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
		<div id="querydiv" class="query_div">
			<form id="queryForm" method="post" action="#">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="detail">
					<input type="hidden" name="selectParams" value=" STATE='启用' and FINAL_NODE_FLAG=0 ">
					<input type="hidden" name="selectParam" value=" STATE='启用' and FINAL_NODE_FLAG=1 and (COMM_FLAG=1 or (COMM_FLAG=0 and ${params.LEDGERSQL}))   and IS_NO_STAND_FLAG=0">
					<tr>
						<td class="detail2">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="detail3">
								<tr>
									<td width="8%" nowrap align="right" class="detail_label">货品编号：</td>
									<td width="15%" class="detail_content">
										<input id="PRD_ID" json="PRD_ID" name="PRD_ID" type="hidden" inputtype="string" value="${params.PRD_ID}">
										<input id="PRD_NO" json="PRD_NO"  name="PRD_NO" type="text" inputtype="string"   value="${params.PRD_NO}" >
										<img align="absmiddle" name="" style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif" 
											onClick="selCommon('BS_21', false, 'PRD_ID', 'PRD_ID', 'forms[1]','PRD_ID,PRD_NO,PRD_NAME', 'PRD_ID,PRD_NO,PRD_NAME', 'selectParam')">
									</td>
									<td width="8%" nowrap align="right" class="detail_label">货品名称：</td>
									<td width="15%" class="detail_content">
										<input name="PRD_NAME" type="text" value="${params.PRD_NAME}" id="PRD_NAME" json="PRD_NAME">
									</td>
									<td width="8%" nowrap align="right" class="detail_label">规格型号：</td>
									<td width="15%" class="detail_content">
										<input name=PRD_SPEC type="text" value="${params.PRD_SPEC}" id="PRD_SPEC" json="PRD_SPEC">
									</td>									
								</tr>								
								<tr>
									<td width="8%" nowrap align="right" class="detail_label">
										制单时间从：
									</td>
									<td width="15%" class="detail_content">
										<input name="BEGIN_CRE_TIME" type="text"	value="${params.BEGIN_CRE_TIME}" onclick="SelectTime();">
										<img align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
										onclick="SelectTime(BEGIN_CRE_TIME);"/>
									</td>
									<td width="8%" nowrap align="right" class="detail_label">
										制单时间到到：
									</td>
									<td width="15%" class="detail_content">
										<input name="END_CRE_TIME" type="text" value="${params.END_CRE_TIME }" onclick="SelectTime();">
										<img align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
										onclick="SelectTime(END_CRE_TIME);"/>
									</td>
									<td width="8%" nowrap align="right" class="detail_label">
										状态：
									</td>
									<td width="15%" class="detail_content">
										<select id="STATE" name="STATE" style="width: 155"></select>
									</td>									
								</tr>
								<tr>
									<td colspan="10" align="center" valign="middle"	>
										<input id="q_search" type="button" class="btn" value="确定">
										&nbsp;&nbsp;
										<input id="q_close" type="button" class="btn" value="关闭">
										&nbsp;&nbsp;
										<input type="reset" class="btn" value="重置" >
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
<jsp:include page="/pages/sys/spflow/publicFlow.jsp" />
<%@ include file="/pages/common/uploadfile/importfile.jsp"%>
<script language="JavaScript">  
	SelDictShow("STATE","32","${params.STATE}","");
	 uploadFile('upInfo', 'SAMPLE_DIR',true,function(){
	   	uploading();
	 });
	 
</script>
</html>
