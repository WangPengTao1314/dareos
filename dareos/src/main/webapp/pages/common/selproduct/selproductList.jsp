<!--
 * @prjName:喜临门营销平台
 * @fileName:Paymentup_Detial
 * @author lyg
 * @time   2013-08-23 10:25:58 
 * @version 1.1
-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/common/selproduct/selproductList.js"></script>
	<style type="text/css">

	input[type="radio"] {
		position: static !important;
		/* clip: rect(0, 0, 0, 0); */
	}
	box{
	  display: -webkit-flex; /* Safari */
	  display: flex;
	}
	div.lst_area{
		width: 100%;
		overflow-x: auto;
		overflow-y: auto;
		min-height: 40%;
	}
	</style>
	<script>
            $(document).ready(       		
                    function () {
                    	var centerHg = (parent.hg - 70)*0.55+"px"
                		var bottomHg = (parent.hg - 70)*0.45+"px"
                		$("#centerHg").css("height",centerHg);
                		$("#bottomHg").css("height",bottomHg);
                    }
                 );
	</script>
</head>
<body>
<div class="box" style="padding: 5px;" id="topHg">
	<input type="text" id="prdInfo" style="width:70%;height:30px;" placeholder="编号、名称、规格型号或者分类名称" />
				<button class="img_input">
					<label for='query'>
						<img src="${ctx}/styles/${theme}/images/main/chaxun.png" class="icon_font">
						<input id="query" type="button" class="btn" value="查询">
					</label>
				</button>
</div>
<div class="box" style="overflow: auto;" id="centerHg">
	<table width="99.8%" height="" border="0" align="center" cellspacing="0" cellpadding="0" class="panel">
		<tr>
			<td>
				<div class="lst_area">
					<!-- 上面表格部分 -->
					<table id="ordertb" width="100%" border="0" cellpadding="1" cellspacing="0" class="lst">
						<tr class="fixedRow">
							<th nowrap align="center" style="width: 40px"></th>
								<input type="checkbox" style="height:12px;valign:middle;display:none;" id="allChecked" />
							</th>
							<th nowrap align="center" dbname="PRD_NO">货品编号</th>
							<th nowrap align="center" dbname="PRD_NAME">货品名称</th>
							<th nowrap align="center" dbname="PRD_SPEC">规格型号</th>
							<%-- <th nowrap align="center" dbname="PRD_COLOR">花号</th> --%>
							<%-- <th nowrap align="center" dbname="BRAND">品牌</th> --%>
							<th nowrap align="center" dbname="STD_UNIT">标准单位</th>
							<%-- <th nowrap align="center" dbname="PAR_PRD_NO">货品分类编号</th> --%>
							<th nowrap align="center" dbname="PAR_PRD_NAME">货品分类名称</th>
						</tr>
						<c:if test="${empty rstChild}">
							<tr id="soDtlEmpty">
								<td style="height:42px;" colspan="23" align="center" class="lst_empty">
									&nbsp;无相关记录&nbsp;
								</td>
							</tr>
						</c:if>
					</table>
				</div>
			</td>
		</tr>
	</table>
</div>
<div class="box" style="padding: 10px 5px 5px;">
	已选货品
</div>
<div class="box" style="overflow: auto;" id="bottomHg">
	<table width="99.8%" height="" border="0" align="center" cellspacing="0" cellpadding="0" class="panel">
		<tr>
			<td valign="top">
				<div class="lst_area">
					<!-- 下面表格部分 -->
					<table width="100%" border="0" cellpadding="1" cellspacing="0" id="jsontb" class="lst">
						<tr class="fixedRow">
							<th style="display: none;"></th>
							<th nowrap align="center" >货品编号</th>
							<th nowrap align="center" >货品名称</th>
							<th nowrap align="center" >规格型号</th>
							<th nowrap align="center" >标准单位</th>
							<th nowrap align="center" >货品分类名称</th>
							<th nowrap align="center" >操作</th>
						</tr>
						<tr class="no_data">
							<td height="25" colspan="10" align="center" class="lst_empty" >
								暂无数据 
							</td>
						</tr>
					</table>
				</div>
			</td>
		</tr>
	</table>
	<input type="hidden" id="chgArrToChar" >
</div>
</body>
</html>