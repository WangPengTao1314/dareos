
<!--  
/**
 * @module 系统管理
 * @func 机构信息
 * @version 1.1
 * @author 蔡瑾钊
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
	<title>机构信息列表</title>
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/sys/jgxx/jgxx_List.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20px" valign="top">
	   <table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td>当前位置：系统管理&gt;&gt;基础信息&gt;&gt;机构信息</td>
			<td width="50" align="right"></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20px" valign="top">
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
				<tr>
				   <td nowrap>
			   <c:if test="${qxcom.XT0010302 eq 1 }">
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
			   <c:if test="${qxcom.XT0010303 eq 1 }">
				   <button class="del_input" >
			                <label for='delete'>
			                    <img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
			                    <input id="delete" type="button" class="del_btn" value="删除" >
			                </label>
			           </button>
			   </c:if>
			   <c:if test="${qxcom.XT0010304 eq 1 }">
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
			   <c:if test="${qxcom.XT0010301 eq 1 }">
			   		<button class="img_input" >
			                <label for='query'>
			                    <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
			                    <input id="query" type="button" class="btn" value="查询">	
			                </label>
			           </button>
			   </c:if>
			   <%--	
			   <c:if test="${qxcom.XT0010305 eq 1 }">
				   <input id="personal" type="button" class="btn" value="个性化设置" >
			   </c:if>
				--%></td>
				</tr>
			</table>
	</td>
</tr>
<tr>
	<td valign="top">
		<div class="lst_area">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="JGBH">机构编号</th>
					<th nowrap align="center" dbname="JGMC">机构名称</th>
					<th nowrap align="center" dbname="JGJC">机构简称</th>
					<th nowrap align="center" dbname="SJJGBH">上级机构编号</th>
					<th nowrap align="center" dbname="SJJGMC">上级机构名称</th>
					<th nowrap align="center" dbname="GJ">国家</th>
					<th nowrap align="center" dbname="SF">省份</th>
					<th nowrap align="center" dbname="CS">城市</th>
					<th nowrap align="center" dbname="CRENAME">创建人</th>
					<th nowrap align="center" dbname="CRETIME">创建时间</th>
					<th nowrap align="center" dbname="JGZT" >状态</th>
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()"  onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%"align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.JGXXID}"/>
								<label for="radio_add"></label>
							</div>
						</td>
                        <td nowrap align="center">${rst.JGBH}&nbsp;</td>
                        <td nowrap align="center">${rst.JGMC}&nbsp;</td>
                        <td nowrap align="center">${rst.JGJC}&nbsp;</td>
                        <td nowrap align="center">${rst.SJJGBH}&nbsp;</td>
                        <td nowrap align="center">${rst.SJJGMC}&nbsp;</td>
                        <td nowrap align="center">${rst.GJ}&nbsp;</td>
                        <td nowrap align="center">${rst.SF}&nbsp;</td>
                        <td nowrap align="center">${rst.CS}&nbsp;</td>
                        <td nowrap align="center">${rst.CRENAME}&nbsp;</td>
                        <td nowrap align="center">${rst.CRETIME}&nbsp;</td>
                        <td nowrap json='STATE' align="center">${rst.JGZT}&nbsp;</td>
                        <input type="hidden" id="state${rst.JGXXID}" value="${rst.JGZT}"/>
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
<input type="hidden" name="selectContion" value=" jgzt in('启用','停用','制定') " />
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr height="50px">
					<td class="gdtd_tb cx_demo" ><div>机构编号：</div>
					
						<input type="text" name="JGBH" seltarget="selJGXX" value="${params.JGBH }" inputtype="string"  autocheck="true" class="gdtd_select_input cx_demo"/>
                        <input id="JGXXID1" name="JGXXID1" type="hidden" seltarget="selJGXX" value="${params.JGXXID }" class="gdtd_select_input cx_demo">
						 <img align="absmiddle" name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('System_2', false, 'JGXXID1', 'JGXXID', 'forms[1]','JGBH,JGMC', 'JGBH,JGMC', 'selectContion');">  								   		
					</td>
					<td class="gdtd_tb cx_demo" ><div>机构名称：</div>
					
						<input type="text" json="JGMC" name="JGMC" seltarget="selJGXX" value="${params.JGMC }" class="gdtd_select_input cx_demo"/>
					</td>
					<td class="gdtd_tb cx_demo"><div>机构简称：</div>
					
						<input name="JGJC" type="text" value="${params.JGJC }" class="gdtd_select_input cx_demo">
					</td>
					<td  class="gdtd_tb cx_demo"><div>上级机构编号：</div>
					
						<input type="text" name="SJJG" seltarget="selSJJG" value="${params.SJJG }" class="gdtd_select_input cx_demo"/>
                        <input id="JGXXID2" name="JGXXID2" type="hidden" seltarget="selSJJG" value="${params.JGXXID2 }" class="gdtd_select_input cx_demo">
						 <img align="absmiddle" name="selSJJG" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('System_2', false, 'JGXXID2', 'JGXXID', 'forms[1]','SJJG', 'JGBH', 'selectContion');">  								   		
					</td>
				</tr>
				<tr>
					
					<td class="gdtd_tb cx_demo"><div>状态：</div>
					
	   					<select id="JGZT" name="JGZT" class="gdtd_select_input cx_demo" ></select>
					</td>
					
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle" >
						<input id="q_search" type="button" class="btn cx_btn" value="确定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" >&nbsp;&nbsp;
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
<script language="JavaScript">
	//$(function(){
		SelDictShow("JGZT","32","${params.JGZT}","");
	//});
</script>
</html>
