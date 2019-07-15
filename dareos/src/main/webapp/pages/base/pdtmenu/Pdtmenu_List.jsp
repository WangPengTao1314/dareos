
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
		<script type="text/javascript" src="${ctx}/pages/base/pdtmenu/Pdtmenu_List.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
	</head>
	<body>
		<table width="99.8%" height="100%" border="0" cellspacing="0"
			cellpadding="0" class="panel">
			<!-- <tr>
				<td height="20px" valign="top">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td height="20px">&nbsp;&nbsp;
								当前位置：系统管理&gt;&gt;基础信息&gt;&gt;货品分类信息
							</td>
							<td width="50" align="right"></td>
						</tr>
					</table>
				</td>
			</tr> -->
			<tr>
				<td height="20px" valign="top">
		
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%" style="border:0px">
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
											<input type="button" id="modify" class="btn" value="修改">
	 									</label>
									</button>
									
									
									
								</c:if>
								<c:if test="${pvg.PVG_DELETE eq 1 }">
									
										
										
									<button class="del_input" >
										<label for='delete'>
											<img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
											<input type="button" id="delete" class="del_btn" value="删除">
	 									</label>
									</button>
								</c:if>
								<c:if test="${pvg.PVG_START_STOP eq 1 }">
									
										
										
								<button class="img_input" >
										<label for='start'>
											<img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
											<input type="button" id="start" class="btn" value="启用">
	 									</label>
									</button>
									
									<button class="img_input" >
										<label for='stop'>
											<img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font">
											<input type="button" id="stop" class="btn" value="停用">
	 									</label>
									</button>
								</c:if>
								<c:if test="${pvg.PVG_BWS eq 1 }">
									
							
									<button class="img_input" >
										<label for='query'>
											<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
											<input type="button" id="query" class="btn" value="查询">
	 									</label>
									</button>
								</c:if>
							</td>
						</tr>
					</table>
					
				</td>
			</tr>
			<tr>
				<td valign="top">
					<div class="lst_area">
						<table id="ordertb" width="100%" border="0" cellpadding="0"
							cellspacing="0" class="lst">
							<tr class="fixedRow">
								<th width="1%"></th>
								<th nowrap align="center" dbname="PRD_NO">货品分类编号</th>
								<th nowrap align="center" dbname="PRD_NAME">货品分类名称</th>
								<th nowrap align="center" dbname="PAR_PRD_NO">上级货品分类编号</th>
								<th nowrap align="center" dbname="PAR_PRD_NAME">上级货品分类名称</th>
								<th nowrap align="center" dbname="BRAND">品牌</th>
								<th nowrap align="center" dbname="DEAFULT_ADVC_SEND_DATE">默认交期</th>
								<th nowrap align="center" dbname="CRE_NAME">制单人</th>
								<th nowrap align="center" dbname="CRE_TIME">制单时间</th>
								<th nowrap align="center" dbname="DEPT_NAME">制单部门</th>
								<th nowrap align="center" dbname="STATE">状态</th>
								
							</tr>
							<c:forEach items="${page.result}" var="rst" varStatus="row">
								<c:set var="r" value="${row.count % 2}"></c:set>
								<c:set var="rr" value="${row.count}"></c:set>
								<tr class="list_row${r}" onmouseover="mover(this)" ondblclick="parent.gotoBottomPage()"
									onmouseout="mout(this)"
									onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
									<td width="1%" align='center'>
										
											
										<div class="radio_add">
											<input type="radio" style="height: 12px; valign: middle"
											name="eid" id="eid${rr}" value="${rst.PRD_ID}" />
											<label for="radio_add"></label>
										</div>
										
									</td>									
									<td nowrap align="center">${rst.PRD_NO}&nbsp;</td>
									<td nowrap align="center">${rst.PRD_NAME}&nbsp;</td>
									<td nowrap align="center">${rst.PAR_PRD_NO}&nbsp;</td>
									<td nowrap align="center">${rst.PAR_PRD_NAME}&nbsp;</td>
									<td nowrap align="center">${rst.BRAND}&nbsp;</td>
									<td nowrap align="center">${rst.DEAFULT_ADVC_SEND_DATE}&nbsp;</td>
									<td nowrap align="center">${rst.CRE_NAME}&nbsp;</td>
									<td nowrap align="center">${rst.CRE_TIME}&nbsp;</td>
									<td nowrap align="center">${rst.DEPT_NAME}&nbsp;</td>
									<input type="hidden" id="PRD_TYPE" name="PRD_TYPE" json="PRD_TYPE" 
										value="${rst.PRD_TYPE}">
									<td nowrap json='STATE' align="center">${rst.STATE}&nbsp;</td>
									<input id="state${rst.PRD_ID}" type="hidden"  value="${rst.STATE}" />
									
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
									<input type="hidden" id="pageSize" name="pageSize"
										value='${page.pageSize}' />
									<input type="hidden" id="pageNo" name="pageNo"
										value='${page.pageNo}' />
									<input type="hidden" id="orderType" name="orderType"
										value='${orderType}' />
									<input type="hidden" id="orderId" name="orderId"
										value='${orderId}' />
									<input type="hidden" id="selRowId" name="selRowId"
										value="${selRowId}">
									&nbsp;
									<input type="hidden" id="paramUrl" name="paramUrl"
										value="${paramCover.coveredUrl}">
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
					class="detail cx_table">
					<input type="hidden" name="selectParams" value=" STATE='启用' and FINAL_NODE_FLAG=0 ">
					<tr>
						<td class="detail2">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								class="detail3">
								<tr>
									<td class="gdtd_tb cx_demo"><div>货品分类编号：</div>
										<input id="PRD_ID" json="PRD_ID" name="PRD_ID" type="hidden" inputtype="string"  value="${params.PRD_ID}" class="gdtd_select_input cx_demo">
										<input id="PRD_NO" json="PRD_NO"  name="PRD_NO" type="text" inputtype="string"   value="${params.PRD_NO}"  class="gdtd_select_input cx_demo">
										<img align="absmiddle" name="" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
											onClick="selCommon('BS_21', false, 'PRD_ID', 'PRD_ID', 'forms[1]','PRD_ID,PRD_NO,PRD_NAME', 'PRD_ID,PRD_NO,PRD_NAME', 'selectParams')">
									</td>
									<td class="gdtd_tb cx_demo"><div>货品分类名称：</div>
								
										<input name="PRD_NAME" type="text" value="${params.PRD_NAME}" id="PRD_NAME" json="PRD_NAME" class="gdtd_select_input cx_demo">
									</td>
									<td class="gdtd_tb cx_demo"><div>上级货品分类编号：</div>
										<input id="PAR_PRD_ID" json="PAR_PRD_ID" name="PAR_PRD_ID" type="hidden" inputtype="string" value="${params.PAR_PRD_ID}" class="gdtd_select_input cx_demo">
										<input id="PAR_PRD_NO" json="PAR_PRD_NO"  name="PAR_PRD_NO" type="text" inputtype="string"  value="${params.PAR_PRD_NO}" class="gdtd_select_input cx_demo">
										<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif"       
											onClick="selCommon('BS_21', false, 'PAR_PRD_ID', 'PRD_ID', 'forms[1]','PAR_PRD_ID,PAR_PRD_NO,PAR_PRD_NAME', 'PRD_ID,PRD_NO,PRD_NAME', 'selectParams')">
									</td>
									<td class="gdtd_tb cx_demo">
										<div>制单时间从：</div>
										<input name="BEGIN_CRE_TIME" type="text"	value="${params.BEGIN_CRE_TIME}" onclick="SelectTime();" class="gdtd_select_input cx_demo" >
										<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
										onclick="SelectTime(BEGIN_CRE_TIME);"/>
									</td>
								</tr>
								<tr>
									
									<td class="gdtd_tb cx_demo">
										<div>制单时间到到：</div>
										<input name="END_CRE_TIME" type="text" value="${params.END_CRE_TIME }" onclick="SelectTime();" class="gdtd_select_input cx_demo">
										<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
										onclick="SelectTime(END_CRE_TIME);"/>
									</td>
									<td class="gdtd_tb cx_demo"><div>上级货品分类名称：</div>
									
										<input name="PAR_PRD_NAME" type="text" value="${params.PAR_PRD_NAME}" id="PAR_PRD_NAME" json="PAR_PRD_NAME" class="gdtd_select_input cx_demo">
									</td>
									
									<td class="gdtd_tb cx_demo">
										<div>状态：</div>
									
									
										<select id="STATE" name="STATE" class="gdtd_select_input cx_demo"></select>
									</td>
									
								</tr>
								<tr>
									<td colspan="10" align="center" valign="middle">
										<input id="q_search" type="button" class="btn cx_btn" value="确定">&nbsp;&nbsp;
										<input id="q_close" type="button" class="btn cx_btn" value="关闭">&nbsp;&nbsp;
										<input type="reset" class="btn cx_btn" value="重置">
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
		SelDictShow("STATE","32","","");
</script>
</html>
