<!--
 * @prjName:喜临门营销平台
 * @fileName:Goodsorderhd_Frame
 * @author zzb
 * @time   2013-09-15 10:35:10 
 * @version 1.1
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>预订单处理</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"/>
	<script type=text/javascript src="${ctx}/pages/drp/sale/goodsorderhd/Goodsorderhd_Frame.js"></script>
	<style type="text/css">
	  		#mycredit_show{
			margin: 0px auto; 
			width:500px;
			border: 1px;
			z-index:99;
			position:absolute;
			background-color: white;
			border:1px solid black;
			height:140px;
			left:230px;
			top:50px;
			text-align:center;
			display: block;
		}
		.text_underline{
			border-bottom-width:1px;
			border-bottom-style:double;
			border-top-width:0px;
			border-left-width:0px;
			border-right-width:0px;
			outline:medium;
			width:150px;
		}
		.wenben{
			width: 80px;
			text-align: right;
		}
		.neirong{
			width:150px;
			text-align:left;
		}
	</style>
</head>
<body>
<table cellspacing="0" cellpadding="0" width="99.9%" height="100%">
		<tr>
			<td >
				<div id="topdiv" style="height: 99%; width: 100%">
				  <iframe id="topcontent" name="topcontent" src=""  frameBorder=0 width="100%" height="100%" frameborder="0" scrolling="AUTO"></iframe>
				</div>
				<%-- <div class="tablayer tabBackground" style="height: 20px; width: 100%;">
					<table cellSpacing=0 cellPadding=0 border=0 width="100%">
						<tr>
						   <!--这里加入需要显示的标签页, 注意序号和标签的宽度-->
							<td id="label" nowrap="nowrap">
								<span id="label_1" class="label_down" style="margin-top:2px;">&nbsp;订货订单维护明细&nbsp;</span>
								<c:if test="${havaAreaSerId ne 0}">
<!--						        <span id="label_3" class="label_down1" style="margin-top:2px;">&nbsp;生命周期&nbsp;</span>-->
						        </c:if>
						        <span id="label_2" class="label_down1" style="margin-top:2px;">&nbsp;详细信息&nbsp;</span>
						        <input type="hidden" id="showLabel" value="label_1"/>
							</td>
							<!--标签页添加完毕-->
							<td class="label_line" align="right" width="150px" nowrap>
								<input type="button" id="butHidTop" class="button" value="↑" title="Alt+↑">
								<input type="button" id="butHidBottom" class="button" value="↓" title="Alt+↓">
							</td>
						</tr>
					</table>
				</div>
				<div id="midden"></div> --%>
				<!-- <div id="bottomdiv" style="height: 41%; width: 100%" >
					下帧
					<iframe id="bottomcontent" name="bottomcontent" src="" frameBorder=0 width="100%" height="100%" frameborder="0" scrolling="AUTO"></iframe>
				</div> -->
			</td>
		</tr>
	</table>
<!-- 模块，页码，排序Id，排序方式，选择记录主键Id -->
<input type="hidden" id="actionType" value="list"/>
<input type="hidden" id="CHANN_ID" value="${CHANN_ID}"/> 
<input type="hidden" id="channel" value="${channel}"/>
<input type="hidden" id="module" value="${module}"/>
<input type="hidden" id="pageNo" value="${pageNo}"/>
<input type="hidden" id="orderId" value="${orderId}"/>
<input type="hidden" id="orderType" value="${orderType}"/>
<input type="hidden" id="selRowId" value="${selRowId}"/>
<input type="hidden" id="paramUrl" value="${paramUrl}"/>
<input type="hidden" id="GOODS_ORDER_ID" value="${GOODS_ORDER_ID}"/>
<input type="hidden" id="doCommitSave" value="${doCommitSave}"/>
<input type="hidden" id="flag" value="${flag}"/>
<input type="hidden" id="HEAD" value="${HEAD}"/>
<input type="hidden" id="isShowPrice" value="${isShowPrice}"/>


</body>