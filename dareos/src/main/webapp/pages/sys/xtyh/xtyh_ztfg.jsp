<!-- 
 *@module 库存管理
 *@fuc 成品出库处理
 *@version 1.1
 *@author 孙炜
 *  -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
    <script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/sys/xtyh/xtyh_ztfg.js"></script>
	<title>帐套分管明细</title>
</head>
<body>
<form action="">
<input id="hasMx" type="hidden" value="${hasMx}"/>
<input id="ztFilter" value="" type="hidden">
<input id="ZTXXID" value="" type="hidden"><input id="ZTBH" value="" type="hidden">
<table width="100%" height="100%" border="0" cellSpacing=0 cellPadding=0>
<tr>
	<td height="20">
		<div class="tablayer">
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
				<tr>
				   <td nowrap>
			   		<input id="add" type="button" class="btn" value="新增" >
					<input id="delete" type="button" class="btn" value="删除">
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
					<th nowrap align="center"><input type="checkbox" style="height:12px;valign:middle" id="allChecked"></th>
					<th nowrap align="center">用户名</th>
					<th nowrap align="center">帐套编号</th>
					<th nowrap align="center">帐套名称</th>
				</tr>
				<c:forEach items="${rst}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)">
						<td nowrap align='center' >
							<input type="checkbox" style="height:12px;valign:middle" id="eid${rr}" value="${rst.XTYHZTDZID}">
						</td>
						<td nowrap align='left' >${rst.YHM}</td>
						<td nowrap align="left">${rst.ZTBH}&nbsp;</td>
						<td nowrap align="left">${rst.ZTMC}&nbsp;</td>
					</tr>
				</c:forEach>
				<c:if test="${empty rst}">
					<tr>
						<td height="25" colspan="13" align="center" class="lst_empty">&nbsp;无相关信息&nbsp;</td>
					</tr>
				</c:if>
			</table>
		</div>
	</td>
</tr>
</table>
</form>
</body>
</html>
