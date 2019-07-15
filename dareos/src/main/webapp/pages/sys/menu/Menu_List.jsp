<!--  
/**
  *@module 菜单信息维护
  *@fuc 
  *@version
  *@author 
*/
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>菜单列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"/>
	<script language="JavaScript" src="${ctx}/pages/sys/menu/Menu_List.js"></script>
	
</head>

<body>
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">

<tr>
	<td height="20px" valign="top">
	<div class="tablayer" style="margin-left:3px;padding-bottom:3px; margin-bottom:3px;">
		<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
			<tr>
				   <td id="qxBtnTb" nowrap>
				  <%--  <c:if test="${pvg.PVG_EDIT eq 1 }"> --%>
				   		<button class="img_input addbtn" >
			                <label for='add'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
			                    <input type="button" id="add" class="btn add" value="新增">
			                </label>
			           </button>
				   	   <button class="img_input" >
			                <label for='modify'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
			                    <input id="modify" type="button" class="btn" value="编辑">
			                </label>
			           </button>
				   <%-- 	</c:if>
				   	<c:if test="${pvg.PVG_BWS eq 1 }">  --%>
				   	  <button class="img_input" >
			                <label for='query'>
			                    <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
			                    <input id="query" type="button" class="btn" value="查询">	
			                </label>
			           </button>
				   <%-- 	</c:if>
				   <c:if test="${pvg.PVG_DELETE eq 1 }">  --%>
				   	   <button class="del_input" >
			                <label for='delete'>
			                    <img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
			                    <input id="delete" type="button" class="del_btn" value="删除" >
			                </label>
			           </button>
				  <%--  </c:if> --%>
				</td>
				</tr>
		</table>
		</div>
	</td>
</tr>
<tr>
	<td>
		<div class="lst_area">
		<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" >
		   <tr class="fixedRow">
				<th width="20" height="25"> </th>
				<th width="120" height="25">
					菜单编号
				</th>
				<th width="318" align="center">
					菜单名称
				</th>
				<th width="120" align="center">
					上级菜单
				</th>
				<th align="center">
					权限Code
				</th>
				<th align="center">
					业务类型
				</th>
				<th width="100" align="center">
					排序
				</th>
			</tr>
			<c:forEach items="${page.result}" var="rst" varStatus="row">
			<c:set var="r" value="${row.count % 2}"></c:set>
			<c:set var="rr" value="${row.count}"></c:set>
			<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()" id="tr${rr}" >
				<td width="1%" align='center'>
					<div class="radio_add">
						<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.MENU_ID}"/>
						<label for="radio_add"></label>
					</div>
				</td>
				<td align="center">
					${rst.MENU_ID }
				</td>
				<td align="center">
					&nbsp;${rst.MENU_NAME}
				</td>
				<td align="center">
					${rst.MENU_PAR_ID}
				</td>
				<td align="center">
					&nbsp;${rst.MENU_QXCODE}
				</td>
				<td align="center">
					&nbsp;${rst.BUSINESSTYPE}
				</td>
				<td align="center">
					${rst.MENU_SORT}
				</td> 
				<script type="text/javascript">
				   $("#tr${rr}").click(function(){
				      selectThis(this);
				      setSelEid(document.getElementById('eid${rr}'));
				   });
			    </script>
			</tr>
		</c:forEach>
		<c:if test="${empty page.result}">
			<tr>
				<td height="25" colspan="8" align="center" class="lst_empty">
					&nbsp;无相关信息&nbsp;
				</td>
			</tr>
		</c:if> 
		</table>
	 	</div>
	</td>
</tr>
<tr>
	<td height="8px">
		<table width="100%" border="0" cellpadding="0"cellspacing="0" class="lst_toolbar">
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
			<td align="left">${page.footerHtml}${page.toolbarHtml}</td>
		</tr>
		</table>
	</td>
</tr>	
</table>
<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
<input type="hidden" name="selectContion2" value=" DELFLAG = 0 and bmzt = '启用'" />
<input type="hidden" name="selectContion3" value=" DELFLAG = 0 and jgzt = '启用'" />
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo"><div>菜单编号：</div>
						<input type="text" name="menuId" id="menuId"    seltarget="selRYXX" value="${param.menuId}" class="gdtd_select_input cx_demo">
					</td>
					<td class="gdtd_tb cx_demo"><div>菜单名称：</div>
						<input name="menuName" id="menuName"  seltarget="selJGXX" type="text" value="${param.menuName}" class="gdtd_select_input cx_demo">
					</td>
					 <td class="gdtd_tb cx_demo"><div>上级菜单：</div>
		                 <input type="text"  name="menuParId" id="menuParId"  seltarget="selJGXX" value="${param.menuParId}" class="gdtd_select_input cx_demo">
		      		</td>
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn" value="确定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" >&nbsp;&nbsp;
						<input  id="reset" type="button" class="btn cx_btn" value="重置" >
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
</body>



