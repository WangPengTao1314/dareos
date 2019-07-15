<!--
 * @prjName:喜临门营销平台
 * @fileName:Paymentup_Detial
 * @author lyg
 * @time   2013-08-23 10:25:58 
 * @version 1.1
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" import="com.centit.commons.util.StringUtil" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/drp/main/myorder/Myorder_List.js"></script>
	<style type="text/css">
		div.lst_area { overflow-y: auto; }
		a {
			cursor: hand ;
		}
tr.list_row1 td{
	background-color: #ffffff;	
}
table.lst th{
    background-color: lightslategray;
    color: #fff;
    font-weight: 100;
}
tr.list_row0 td{	
	background-color: #f6f5eb;
}
.tdTop {
	width: 80px;
}

.jsontb_tb  td {
	font-weight: 400;
	font-size: .875rem;
}

.chakan {
	width: 70px;
    height: 30px;
    outline: none;
    border: none;
    color: #fff;
    padding-left: 27px;
    background: #3c93dd;
}

.chankan_img {
	position: relative;
	left: 28px;
	vertical-align: middle;
}
/* .myorder_radio{
			 position: static!important;
			 clip: rect(1, 1, 1, 1)!important;
			 
		 
		 } */
.left_menu {
	width: 15%;
	height: 100%;
	border: 1px solid #ccc;
	display: inline-block;
	float: left;
}

input[type="radio"] {
	position: static !important;
	/* clip: rect(0, 0, 0, 0); */
}

.active {
	margin-right: 20px;
	width: 100px;
}

a:active {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
	color: #000
}

a:visited {
	text-decoration: none;
}

a:link {
	text-decoration: none;
}
.bgshow{
	background:#0083c6; 
	color:#fff !important;
	border-radius:3px;
	padding:4px 10px;
}
#jsontb tr td span
{
    width: 100% !important;
    float: left !important;
    overflow: hidden !important;
    text-overflow: ellipsis !important;
    white-space: normal !important;
}
	</style>
</head>
<body>
	<div style="width:100%;overflow-x: auto; overflow-y: auto;">
	<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
		<div id="innerdiv" style="position:absolute;">
			<img id="bigimg" style="border:5px solid #fff;" src="" />
		</div>
	</div>
	<table width="99.8%" height="100%" border="0" align="center" cellspacing="0" cellpadding="0" class="panel">
		<tr>
			<td height="20px" valign="top">
			<table  id="" class="jsontb_tb" cellpadding="0" cellspacing="6" border=0 width="100%" >
				<tr height="30px">
				    <td width="6%" align="right">接单组织：</td>
					<td  class="showtd" id="ledgerQuery">
							<c:forEach items="${ledgerList}" var="rst" varStatus="row">
								<a href="" class="active"	onclick="bgshow(this);queryPrd();refPrdTree(this);" opt="${rst.LEDGER_ID}">${rst.LEDGER_NAME_ABBR }</a> 
							</c:forEach>
					</td>
					<td width="40%"><input type="text" id="prdInfo" style="width:70%;height:29px;border:1px solid #D8DDE6;"><img src="${ctx}/styles/${theme}/images/main/chaxun.png"
							alt="" class="chankan_img"><input type="button" value="查询" onclick="queryPrd()"
							class="chakan"></td>
				</tr>
				<tr height="30px">
				    <td  width="6%" align="right">系列：</td>
					<td class="showtd" id="seriesQuery"></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td valign="top" colspan="2" >
				<div class="lst_area" >
					<form onsubmit="return false;">
					   <input type="hidden" id="CHANN_ID"  value="${CHANN_ID}"/>
					   <input type="hidden" id="picture_url" name="" value="${picture_url}" />
						<table width="100%" border="0" cellpadding="1" cellspacing="1" id="jsontb" class="lst" style="table-layout:fixed">
							<tr class="fixedRow">
								<th nowrap="nowrap" width="5%"  align="center">图片</th>
								<th nowrap="nowrap" width="25%"  align="center">货品信息</th>
								<th nowrap="nowrap" width="35%"  align="center">规格属性</th>
								<th nowrap="nowrap" width="6%"  align="center">单位</th>
<!-- 								<th nowrap="nowrap" width="8%"  align="center">参考单价</th> -->
								<th nowrap="nowrap" width="8%"  align="center">数量</th>
<!-- 								<th nowrap="nowrap" width="8%"  align="center">金额</th> -->
								<th nowrap="nowrap" width="5%"  align="center" >操作</th>
							</tr>
						</table>
					</form>
				</div>
			</td>
		</tr>
		<tr>
			<td height="12px" align="center">
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="lst_toolbar">
					<tr>
						<td>
							<form id="pageForm"  action="#" method="post" name="listForm">
							<input type="hidden" id="pageSize" name="pageSize" value='${page.pageSize}'/>
								<input type="hidden" id="pageNo" name="pageNo" value='${page.pageNo}'/>
								<input type="hidden" id="orderType" name="orderType" value='${orderType}'/>
								<input type="hidden" id="orderId" name="orderId" value='${orderId}'/>
								<input type="hidden" id="selRowId" name="selRowId" value="${selRowId}">
								<input type="hidden" id="paramUrl" name="paramUrl" value="${paramCover.coveredUrl}">
								<input type="hidden" id="oldPageNo" name="oldPageNo" value="${page.pageNo}" >
								<input type="hidden" id="PRDIDS" name="PRDIDS"> 
								<span id="hidinpDiv" name="hidinpDiv"></span>
								${paramCover.unCoveredForbidInputs }
							</form>
						</td>
						<td align="left" id="pageHtml">
							 ${page.footerHtml}${page.toolbarHtml}
						</td>
						<td align="right" style="padding-right: 10px;">
							<input id="selShopCar" type="button" value="购物车下单" class="btn"/>
						</td> 
					</tr>
				</table>
			</td>
		</tr>
	</table>
	</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
     displayDownFile('ATT_PATH',true);
</script>
</html>