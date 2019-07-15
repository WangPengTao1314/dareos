<!--
 * @prjName:喜临门营销平台
 * @fileName:分销 -订货订单维护
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@page import="com.centit.commons.model.Consts"%>
 
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>浏览</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
</head>
<body class="bodycss1">
	<table width="99.5%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
		<tr>
			<td height="20"></td>
		</tr>
		<tr>
			<td valign="top">
				<div class="lst_area" >
					<table id="ordertb" width="100%" border="0" cellpadding="1" cellspacing="0" class="lst linebreak ellipsis">
						<tr class="fixedRow">
							<th nowrap="nowrap" align="center" dbname="BILL_NO" width="20%">业务单号</th>
							<th nowrap="nowrap" align="center" dbname="ACTION" width="15%">操作</th>
							<th nowrap="nowrap" align="center" dbname="ACTOR_NAME" width="20%">操作人</th>
							<th nowrap="nowrap" align="center" dbname="ACTOR_TIME" width="10%">操作时间</th>
							<th nowrap="nowrap" align="center" dbname="ACTOR_REMARKS" width="30%">审核意见</th>
						</tr>
						<c:forEach items="${list}" var="rst" varStatus="row">
							<c:set var="r" value="${row.count % 2}"></c:set>
							<c:set var="rr" value="${row.count}"></c:set>
							<tr  class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)"
								>
								<td nowrap="nowrap" align="center">${rst.billNo}</td>
								<td nowrap="nowrap" align="center">${rst.action}</td>
								<td nowrap="nowrap" align="center">${rst.actorName}</td>
								<td nowrap="nowrap" align="center">${rst.actorTime}</td>
								<td nowrap="nowrap" align="center" title="${rst.actorRemarks}">${rst.actorRemarks}</td>
							</tr>
						</c:forEach>
						<c:if test="${empty list}">
							<tr>
								<td height="25" colspan="15" align="center" class="lst_empty">无相关记录</td>
							</tr>
						</c:if>
					</table>
				</div>
			</td>
		</tr>
		<tr>
			<td height="20px" ></td>
		</tr>
	</table>
</body>
</html>
