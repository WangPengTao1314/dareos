<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8" import="java.awt.*"%>
<%@ taglib uri="/WEB-INF/tlds/runqianReport4.tld" prefix="report"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>
		</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/stylePrint.css">
	</head>
	<body >
	<form action="${ctx}/sys/report/toSaleOrderReport" method="get">
            	<input type="hidden" name="reportUnit" id=""
					value="${reportUnit}">
					
				<!-- div class="search-item">
					<label>数据统计范围</label> <input readonly="true"
						class="easyui-textbox" type="text" name="unitName"
						value="${unitName}" id="reportUnitName_AdviseBackPpNoQuery" >
				</div>
            
                <div class="search-item">
                    <label>统计日期范围</label>
                    <input type="text" class="easyui-datebox" id="reportDateBeg" name="reportDateBeg" value="${reportDateBeg}"  />
                    	至
                    <input type="text" class="easyui-datebox" id="reportDateEnd"   name="reportDateEnd" value="${reportDateEnd}"  />
                </div>
                <input type="hidden" name="isQueryByDate" value="true">
                <div class="search-actions">
                    <a class="easyui-linkbutton btn-search">搜索</a>
                </div-->
			    <div style="float: right;">
					<a href="#" onclick="doPrint()"><img src="${ctx}/styles/${theme}/images/icon/print.png" class="icon_font"></a> &nbsp;&nbsp;&nbsp;
					<%-- <input type="button"  id=""  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;" onclick='changeinput(this)'>
					<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="vertical-align: middle;" id=""> --%>
				</div> 
            </form>
	<div class="layout" fit="true" id="report1" style="height:100%;width: 100%">
		<div region="center" border="false" id="reportPrint2"  class="main-table-div" style="overflow:auto;overflow-x:hidden;">
	    	<report:html 
				name = "report1" 
				reportFileName = "${reportFileName}"
				printedRaq = "${reportFileName}"
				params = "${params}" 	
				funcBarLocation = "no" 
				exceptionPage = "/modules/report/reportcomm/reportError.jsp"
				needPageMark="yes"
				needPrint="yes"
				height="-1"
				generateParamForm="no"
				needSaveAsWord="yes" 
				wordLabel="<input  type='button' class='btn' value='导出Word'>"
				displayNoLinkPageMark="no"
				/>
		</div>
		<div style="display:none">
		<jsp:include page="../../../report/commons/toolbar.jsp" flush="false"/>
		</div>
	</div>
	<div id="querydiv" class="query_div" style="top:0px;">
        <form action="${ctx}/sys/report/toDepositInfoDtlReport" method="post" id="queryForm">
	         <table border="0" cellpadding="0" cellspacing="0" class="detail cx_table" >
				<tr>
					<td class="detail2">
						<table width="100%" border="0" cellpadding="5" cellspacing="5" style="padding: 1%">
							<tr>
								<td class="gdtd_tb cx_demo">
									<div>客户名称:</div>
									<input id="CHANN_NAME"  name="CHANN_NAME" value="${QueryParams.CHANN_NAME}" class="gdtd_select_input cx_demo">
								</td>
								<td class="gdtd_tb cx_demo">
									<div>订单状态:</div>
									<input id="STATE" name="STATE" value="${QueryParams.STATE}" class="gdtd_select_input cx_demo">
								</td>
								<td class="gdtd_tb cx_demo">
									<div>充值日期从:</div>
									<input class="gdtd_select_input cx_demo" type="text" id="CRE_TIME2" name="CRE_TIME2" value="${QueryParams.CRE_TIME2}" onclick="SelectDate();">
								</td>
								<td class="gdtd_tb cx_demo">
									<div>~充值日期到:</div>
									<input id="CRE_TIME1" name="CRE_TIME1" value="${QueryParams.CRE_TIME1}" class="gdtd_select_input cx_demo" onclick="SelectDate();">
								</td>
							</tr>
							 <%-- <tr>
								<td class="gdtd_tb cx_demo">
									<div>订货单号：</div>
									<input id="GOODS_ORDER_NO" name="GOODS_ORDER_NO"  value="${QueryParams.GOODS_ORDER_NO}" class="gdtd_select_input cx_demo" />
								</td>
								<td class="gdtd_tb cx_demo">
									<div>下单日期:</div>
									<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DATE_BEGIN" name="ORDER_DATE_BEGIN" onclick="SelectDate();" size="10" value="${QueryParams.ORDER_DATE_BEGIN}" style="width: 36.5%">
									<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_beg);">
									-&nbsp;<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DATE_END" name="ORDER_DATE_END" onclick="SelectDate();" size="10" value="${QueryParams.ORDER_DATE_END}" style="width: 36.5%" >
									<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_END);">
								</td>
								<td class="gdtd_tb cx_demo">
									<div>交货日期:</div>
									<input class="gdtd_select_input cx_demo"  type="text" id="ORDER_DELIVERY_DATE_BEGIN" name="ORDER_DELIVERY_DATE_BEGIN" onclick="SelectDate();" size="10" value="${QueryParams.ORDER_DELIVERY_DATE_BEGIN}" style="width: 36.5%" >
									<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE_beg);">
									-&nbsp;<input class="gdtd_select_input cx_demo" type="text" id="ORDER_DELIVERY_DATE_END" name="ORDER_DELIVERY_DATE_END" onclick="SelectDate();" size="10" value="${QueryParams.ORDER_DELIVERY_DATE_END}" style="width: 36.5%" >
									<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DELIVERY_DATE_END);">
								</td>
								<td class="gdtd_tb cx_demo">
									<div>订单状态:</div>
									<select id="STATE" name="STATE" class="gdtd_select_input cx_demo"></select>
								</td>
							</tr>  --%>
							<tr>
								<td colspan="10" align="center" valign="middle">
									<input id="q_search" type="submit" class="btn cx_btn" value="确定" >&nbsp;&nbsp;
									<input id="q_close" type="button" class="btn cx_btn" value="关闭" >&nbsp;&nbsp;
									<input id="q_reset" type="button" class="btn cx_btn" value="重置"
									onclick="javascript:$('#queryForm :input').not(':button, :submit, :reset, :hidden').val('');">&nbsp;&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		</div>
	<div style="position:absolute;right: 12px;top: 0px;">
		<a href="#" onclick="doPrint()"><img src="${ctx}/styles/${theme}/images/icon/print.png" class="icon_font"></a> 
	</div>
<%-- 	<script type="text/javascript" src="${ctx}/report/commons/reportPageInit.js"></script> --%>
		<script type="text/javascript" src="${ctx}/report/commons/initReport.js"></script>
		<script type="text/javascript" src="${ctx}/report/commons/jquery.jqprint.js"></script>
		
		<script type="text/javascript">
// 		rq_init('${RptModel}');
		
		 function doPrint()
		 {
			 
			 $("#reportPrint2").jqprint();

		 }
		</script>
	</body>

</html>
