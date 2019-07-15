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
	<title>品牌信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/base/brand/Brand_List.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellpadding="0" cellspacing="10" class="panel">
<tr>
	<td height="20">
		<!-- <table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td height="20px">&nbsp;&nbsp;当前位置：系统管理&gt;&gt;基础信息&gt;&gt;品牌信息</td>
			<td width="50" align="right"></td>
		</tr>
	  </table>   -->
	</td>
</tr>
<tr>
	<td height="20">
		<div class="tablayer" style="margin-left:3px; border-bottom:1px solid #d3d3d3; padding-bottom:3px; margin-bottom:3px;">
			<table id="qxBtnTb" cellSpacing=0 cellPadding=0 border=0 width="100%" style="border:0px">
				<tr>
				<td nowrap>
				    <c:if test="${pvg.PVG_EDIT eq 1 }">
				    	<button class="img_input addbtn" >
							<label for='add'>
							<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
							<input id="add" name="brandList" type="button" class="btn add" value="新增" />
							</label>
						</button>
				   		<button class="img_input" >
							<label for='modify'>
							<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
							<input id="modify" name="brandList" type="button" class="btn" value="修改" />
							</label>
						</button>
				    </c:if>
				    <c:if test="${pvg.PVG_DELETE eq 1 }">
				    	<button class="del_input" >
							<label for='delete'>
							<img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
							<input id="delete" name="brandList" type="button" class="del_btn" value="删除" />
							</label>
						</button>
				    </c:if>
				    <c:if test="${pvg.PVG_START_STOP eq 1 }">
				    	<button class="img_input" >
							<label for='start'>
							<img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
							<input id="start" name="brandList" type="button" class="btn" value="启用" />	
							</label>
						</button>
				   		<button class="img_input" >
							<label for='stop'>
							<img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font">
							<input id="stop" name="brandList" type="button" class="btn" value="停用" />
							</label>
						</button>
				    </c:if>	
				    <c:if test="${pvg.PVG_BWS eq 1 }">
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
	<td valign="top">
		<div class="lst_area" style="margin-left:3px;">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="BRAND_ID" width="20%">品牌ID</th>
					<th nowrap align="center" dbname="BRAND" width="15%">品牌名称</th>
					<th nowrap align="center" dbname="BRAND_EN" width="15%">英文名称</th>
					<th nowrap align="center" dbname="CRE_NAME" width="10%">制单人</th>
					<th nowrap align="center" dbname="CRE_TIME" width="15%">制单时间</th>
					<th nowrap align="center" dbname="DEPT_NAME" width="15%">制单部门</th>
					<th nowrap align="center" dbname="STATE" width="10%">状态</th>		
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()" onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%" align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.BRAND_ID}"/>
								<label for="radio_add"></label>
							</div>
						</td>
						<td align="center">${rst.BRAND_ID}&nbsp;</td>
						<td align="center">${rst.BRAND }&nbsp;</td>
						<td align="center">${rst.BRAND_EN }&nbsp;</td>
						<td align="center">${rst.CRE_NAME}&nbsp;</td>
						<td align="center">${rst.CRE_TIME}&nbsp;</td>
						<td align="center">${rst.DEPT_NAME}&nbsp;</td>
						<!-- <td align="left">${rst.BMXXID}&nbsp;</td> -->
						<td json='STATE' align="center">${rst.STATE}&nbsp;</td>
						<input type="hidden" id="state${rst.BRAND_ID}" value="${rst.STATE}"/>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo"><div>品牌名称：</div>
					
						<input name="BRAND"  type="text" autocheck="true" inputtype="string" value="${params.BRAND }" class="gdtd_select_input cx_demo"/>
					</td>
					<td class="gdtd_tb cx_demo"><div>英文名称：</div>
					
						<input name="BRAND_EN"  type="text" autocheck="true" inputtype="string" value="${params.BRAND_EN }" class="gdtd_select_input cx_demo"/>
					</td>
		      		<td class="gdtd_tb cx_demo"> <div>状态：</div>
					
						<select id="STATE" name="STATE" class="gdtd_select_input cx_demo"></select>
					</td>
					<td class="gdtd_tb cx_demo"><div>制单时间从：</div>
		      		
		            	<input type="text" json="CRE_TIME_FROM" id="CRE_TIME_FROM" name="CRE_TIME_FROM" autocheck="true" inputtype="string" value="${params.CRE_TIME_FROM}"
							label="日期"  onclick="SelectTime();" READONLY class="gdtd_select_input cx_demo"/>&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
							onclick="SelectTime(CRE_TIME_FROM);"/>
		      		</td>
				</tr>
				<tr>
					
		      		<td class="gdtd_tb cx_demo"><div>制单时间到：</div>
		      		
		            	<input type="text" json="CRE_TIME_TO" id="CRE_TIME_TO" name="CRE_TIME_TO" autocheck="true" inputtype="string" value="${params.CRE_TIME_TO}"
							label="日期" onclick="SelectTime();" READONLY  class="gdtd_select_input cx_demo"/>&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
							onclick="SelectTime(CRE_TIME_TO);"/>
		      		</td>	
		      		
					<!-- <td width="8%" nowrap align="right" class="detail_label"></td>
					<td width="15%" class="detail_content">
					</td> -->
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
	SelDictShow("STATE","32","${params.STATE }","");	
</script>
</body>
</html>