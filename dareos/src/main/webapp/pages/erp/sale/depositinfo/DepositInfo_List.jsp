<!-- 
 *@module 系统管理
 *@func 品牌信息
 *@version 1.1
 *@author  郭利伟
 *  -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>充值信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/erp/sale/depositinfo/DepositInfo_List.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellpadding="0" cellspacing="10" class="panel">
<tr>
	<td height="20">
		<div class="tablayer button_cls">
			<table id="qxBtnTb" cellSpacing=0 cellPadding=0 border=0 width="100%" style="border:0px">
				<tr>
				<td nowrap>
				    <c:if test="${pvg.PVG_EDIT eq 1 }">
				    	<button class="img_input addbtn"  id="add_btn">
							<label for='add'>
							<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
							<input id="add" name="brandList" type="button" class="btn add" value="新增" />
							</label>
						</button>
				   		<button class="img_input"  id="modify_btn">
							<label for='modify'>
							<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
							<input id="modify" name="brandList" type="button" class="btn" value="修改" />
							</label>
						</button>
				    </c:if>
				    <c:if test="${pvg.PVG_DELETE eq 1 }">
				    	<button class="del_input" id="delete_btn">
							<label for='delete'>
							<img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
							<input id="delete" name="brandList" type="button" class="del_btn" value="删除" />
							</label>
						</button>
				    </c:if>
				    <c:if test="${pvg.PVG_COMMIT eq 1 }">
				    	<button class="img_input" id="commit_btn" >
							<label for='commit'>
							<img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
							<input id="commit" name="brandList" type="button" class="btn" value="提交" />	
							</label>
						</button>
				    </c:if>	
				    <c:if test="${pvg.PVG_AUDIT eq 1 }">
					    <button class="img_input" id="audit_btn" >
							<label for='audit'>
							<img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font">
							<input id="audit" name="brandList" type="button" class="btn" value="审核" />
							</label>
						</button>
						<button class="img_input" id="audit_btn" >
							<label for='audit2'>
							<%-- <img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font"> --%>
							<input id="audit2"  onclick="parent.gotoBottomPage('${ctx}/sys/report/toDepositInfoDtlReport')" name="brandList" type="button" class="btn" value="充值审核明细" />
							</label>
						</button>
					</c:if>	
				    <c:if test="${pvg.PVG_BWS eq 1 || pvg.PVG_BWS_AUDIT eq 1}">
				    <button class="img_input" >
						<label for='query'>
						<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
						<input id="query" type="button" class="btn" value="查询" />	
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
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" style="padding:0;" >
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="DEPOSIT_NO" width="15%">业务单号</th>
					<th nowrap align="center" dbname="CHANN_NAME" width="15%">经销商</th>
					<th nowrap align="center" dbname="LEDGER_NAME" width="15%">账套</th>
					<th nowrap align="center" dbname="DEPOSIT_TYPE" width="10%">类型</th>
					<th nowrap align="center" dbname="DEPOSIT_AMOUNT" width="10%">金额</th>
					<th nowrap align="center" dbname="CRE_NAME" width="10%">制单人</th>
					<th nowrap align="center" dbname="CRE_TIME" width="15%">制单时间</th>
					<th nowrap align="center" dbname="AUDIT_NAME" width="10%">审核人</th>
					<th nowrap align="center" dbname="STATE" width="10%">状态</th>		
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()" onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%" align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.depositId}"/>
								<label for="radio_add"></label>
							</div>
						</td>
						<td align="center">${rst.depositNo}&nbsp;</td>
						<td align="center">${rst.channName }&nbsp;</td>
						<td align="center">${rst.ledgerName }&nbsp;</td>
						<td align="center">${rst.depositType}&nbsp;</td>
						<td align="center">${rst.depositAmount}&nbsp;</td>
						<td align="center">${rst.creName}&nbsp;</td>
						<td align="center">${rst.creTime}&nbsp;</td>
						<td align="center">${rst.auditName}&nbsp;</td>
						<td json='STATE' align="center">${rst.state}&nbsp;</td>
						<input type="hidden" id="state${rst.depositId}" value="${rst.state}"/>
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
<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#" name="queryForm">
<input type="hidden" name="selectParams" value=" STATE in( '启用')">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo">
						<div>经销商名称：</div>
						 <input name="search" value="1" type="hidden" class="gdtd_select_input cx_demo"/>
						<input name="channName"  type="text" autocheck="true" inputtype="string" value="${params.channName }" class="gdtd_select_input cx_demo"/>
					</td>
					<td class="gdtd_tb cx_demo">
					<div>业务单号：</div>
					
						<input name="depositNo"  type="text" autocheck="true" inputtype="string" value="${params.depositNo }" class="gdtd_select_input cx_demo"/>
					</td>
		      		<td class="gdtd_tb cx_demo">
		      			<div>帐套：</div>
						<select id="ledgerId" name="ledgerId"  class="gdtd_select_input cx_demo"></select>
					</td>
					<td class="gdtd_tb cx_demo"><div>类型：</div>
						<select id="depositType" name="depositType" class="gdtd_select_input cx_demo"></select>
					</td>
				</tr>
				<tr>
				<td class="gdtd_tb cx_demo">
					<div>状态：</div>
						<select id="state" name="state"  class="gdtd_select_input cx_demo"></select>
				</td>
				<td class="gdtd_tb cx_demo">
				<div>审核人：</div>
						<input name="auditName"  type="text" autocheck="true" inputtype="string" value="${params.auditName }" class="gdtd_select_input cx_demo"/>
				</td>
					<td class="gdtd_tb cx_demo">
						<div>制单时间从：</div>
		      		
		            	<input type="text" json="creTime1" id="creTime1" name="creTime1" autocheck="true" inputtype="string" value="${params.creTime1}"
							label="日期"  onclick="SelectTime();" READONLY  class="gdtd_select_input cx_demo" />&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
							onclick="SelectTime(creTime1);"/>
		      		</td>
		      		<td class="gdtd_tb cx_demo">
		      			<div>制单时间到：</div>
		      		
		            	<input type="text" json="creTime2" id="creTime2" name="creTime2" autocheck="true" inputtype="string" value="${params.creTime2}"
							label="日期" onclick="SelectTime();" READONLY class="gdtd_select_input cx_demo" />&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
							onclick="SelectTime(creTime2);"/>
		      		</td>	
		      		
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn" value="确定" />&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" />&nbsp;&nbsp;
						<input  type="reset" class="btn cx_btn" value="重置" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
<script language="JavaScript">  
	SelDictShow("state","BS_211","${params.state }","");	
	SelDictShow("ledgerId","BS_212","${params.ledgerId }"," XTYHID = '${xtyhId}'");	
	SelDictShow("depositType","BS_210","${params.depositType }","");	
</script>
</body>
</html>