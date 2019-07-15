<!-- /**

 *@module 财务管理

 *@fuc 帐套信息维护

 *@version 1.1

 *@author 唐赟
*/ -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>帐套信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/sys/ztxxwh/ztxxwh_List.js"></script>
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
</head>
<body>
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20">
		<table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td>当前位置：系统管理&gt;&gt;基础信息&gt;&gt;帐套信息维护</td>
			<td width="50" align="right"><br></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20">
		<div class="tablayer" style="margin-left:3px; border-bottom:1px solid #d3d3d3; padding-bottom:3px; margin-bottom:3px;">
			<table cellSpacing=0 cellPadding=0 border=0 width="100%">
				<tr>
				   <td id="qxBtnTb" nowrap>
			      <c:if test="${qxcom.XT0011602 eq 1 }">
			      	  <button class="img_input addbtn" >
						  <label for='add'>
					      <img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
						  <input id="add" type="button" class="btn add" value="新增" />
						  </label>
					  </button>
			      	  <button class="img_input" >
						  <label for='modify'>
					      <img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
						  <input id="modify" type="button" class="btn" value="修改" />
						  </label>
					  </button>
			   	   </c:if>	
			   	   <c:if test="${qxcom.XT0011602 eq 1 }">
			   	   	   <button class="del_input" >
						  <label for='delete'>
					      <img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
						  <input id="delete" type="button" class="del_btn" value="删除" />
						  </label>
					   </button>
			   	   </c:if>	
			   	   
			   	   <c:if test="${qxcom.XT0011601 eq 1 }">
			   	   	   <button class="img_input" >
						  <label for='query'>
					      <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
						  <input id="query" type="button" class="btn" value="查询" />
						  </label>
					   </button>
			   	   </c:if>	
			   	   <c:if test="${qxcom.XT0011603 eq 1 }">
			   	       <button class="img_input" >
						  <label for='begin'>
					      <img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
						  <input id="begin" type="button" class="btn" value="启用" />
						  </label>
					   </button>
			   	       <button class="img_input" >
						  <label for='end'>
					      <img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font">
						  <input id="end" type="button" class="btn" value="停用" />
						  </label>
					   </button>
			   	   </c:if>
			   	   <%-- <button class="img_input" >
						  <label for='personal'>
					      <img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
						  <!-- <input id="personal" type="button" class="btn" value="个性化设置" > -->
						  </label>
				   </button> --%>
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
					<th nowrap align="center" dbname="ZTBH" >帐套编号</th>
					<th nowrap align="center" dbname="ZTMC" >帐套名称</th>
					<th nowrap align="center" dbname="YJZBJ" >月结帐标记</th>
					<th nowrap align="center" dbname="SJZT" >上级帐套</th>
					<th nowrap align="center" dbname="ZZSH" >增值税号</th>
					<th nowrap align="center" dbname="YYZZH" >营业执照号</th>
					<th nowrap align="center" dbname="ZTLB" >帐套类别</th>	
					<th nowrap align="center" dbname="CREATER" >创建人</th>
					<th nowrap align="center" dbname="CRETIME" >创建时间</th>	
					<th nowrap align="center" dbname="STATE" >状态</th>																														
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()" onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));changeButton('${rst.STATE}')">
						<td width="1%"align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.ZTXXID}"/>
								<label for="radio_add"></label>
							</div>
						</td>
						<td nowrap align="center">${rst.ZTBH }&nbsp;</td>
						<td nowrap align="center">${rst.ZTMC}&nbsp;</td>
						<td nowrap align="center">
							<c:if test="${rst.YJZBJ eq 0}">否</c:if>
							<c:if test="${rst.YJZBJ eq 1}">是</c:if>&nbsp;
						</td>
						<td nowrap align="center">${rst.SJZTMC}&nbsp;</td>
						<td nowrap align="center">${rst.ZZSH}&nbsp;</td>
						<td nowrap align="center">${rst.YYZZH}&nbsp;</td>
						<td nowrap align="center">${rst.ZTLB}&nbsp;</td>
						<td nowrap align="center">${rst.CRENAME }&nbsp;</td>
						<td nowrap align="center">${rst.CRETIME }&nbsp;</td>
						<td nowrap align="center">${rst.STATE }&nbsp;</td>
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
					${page.toolbarHtml}
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>

<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo"><div>帐套编号：</div>
				
						<input name="ZTBH" type="text" value="${params.ZTBH }" class="gdtd_select_input cx_demo">
					</td>
					<td class="gdtd_tb cx_demo"><div>帐套名称：</div>
					
						<input name="ZTMC" type="text" value="${params.ZTMC }" class="gdtd_select_input cx_demo">
					</td>
					<td class="gdtd_tb cx_demo"><div>上级帐套：</div>
					
						<input name="SJZT" type="text" seltarget="selZTXX" value="${params.SJZT }" class="gdtd_select_input cx_demo">
						<input json="ZTXXID" name="ZTXXID1" type="hidden" seltarget="selZTXX" value="${params.ZTXXID }" class="gdtd_select_input cx_demo">
		                <img align="absmiddle" name="selZTXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif"  onClick="selCommon('System_5', false, 'SJZT', 'ZTXXID', 'forms[1]','SJZT', 'ZTMC', '')">
					</td>
					
					<td class="gdtd_tb cx_demo"><div>增值税号：</div>
					
						<input name="ZZSH" type="text" value="${params.ZZSH }" class="gdtd_select_input cx_demo">
					</td>
				</tr>
				<tr>
				    <td class="gdtd_tb cx_demo"><div>帐套类别：</div>
				
						
						<select json="ZTLB" name="ZTLB" id="ZTLB" mustinput="true" class="gdtd_select_input cx_demo" >
						</select>
					</td>
					<td class="gdtd_tb cx_demo"><div>状态：</div>
					
						<select id="ZT" name="ZT" class="gdtd_select_input cx_demo" >
						</select>									
					</td>
					
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle" >
						<input id="q_search" type="button" class="btn cx_btn" value="确 定" />&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关 闭" />&nbsp;&nbsp;
						<input  type="reset" class="btn cx_btn" value="重置" />
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>

</body>
<script type="text/javascript">
	   SelDictShow("ZTLB","0","${params.ZTLB}","");	
	   SelDictShow("ZT","32","${params.ZT}","");	
	</script>
</html>
