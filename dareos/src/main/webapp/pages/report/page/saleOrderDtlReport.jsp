<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@taglib uri="/WEB-INF/tlds/runqianReport4.tld" prefix="report"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<title>
		</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/stylePrint.css">
		<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
		</style>
	</head>
	<body >
	<div style="position:relative;height:99%">
		<div style="position:absolute;left:1px;top: 1px;">
			<button class="img_input" id="queryBtn" >
				<label for='query'>
					<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
					<input id="query" type="button" class="btn" value="查询" style="border:0">
				</label>
			</button>
		</div>
		<div region="center" border="false"  class="main-table-div" style="overflow: auto;height:94%;margin-top:4px;">
		    	<report:html 
		    	    height="-1"
					name = "report1" 
					reportFileName = "${reportFileName}"
					printedRaq = "${reportFileName}"
					params = "${params}" 	
					funcBarLocation = "button" 
					exceptionPage = "/modules/report/reportcomm/reportError.jsp"
					needPageMark="yes"
					/>
		</div>
		<jsp:include page="../../../report/commons/toolbar.jsp" flush="false"/>
	</div>
		<div id="querydiv" class="query_div" style="top:0px;">
        <form action="${ctx}/sys/report/toSaleOrderDtlReport" method="post" id="queryForm">
	         <table border="0" cellpadding="0" cellspacing="0" class="detail cx_table" >
				<tr>
					<td class="detail2">
						<table width="100%" border="0" cellpadding="5" cellspacing="5" style="padding: 1%">
							<tr>
								<td class="gdtd_tb cx_demo">
									<div>厂编:</div>
									<input class="gdtd_select_input cx_demo" type="text" id="FACTORY_NO" name="FACTORY_NO" value="${QueryParams.FACTORY_NO}">
								</td>
								<td class="gdtd_tb cx_demo">
									<div>订单状态:</div>
									<select id="STATE" name="STATE"  class="gdtd_select_input cx_demo"></select>
								</td>
								<td class="gdtd_tb cx_demo">
									<div>订单类型:</div>
<%-- 									<input  name="BILL_TYPE" value="${QueryParams.BILL_TYPE}" class="gdtd_select_input cx_demo"> --%>
									<select id="BILL_TYPE" name="BILL_TYPE" class="gdtd_select_input cx_demo" ></select>
								</td>
								<td class="gdtd_tb cx_demo">
									<div>业务员:</div>
									<input  name="SALE_NAME" value="${QueryParams.SALE_NAME}" class="gdtd_select_input cx_demo">
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb cx_demo">
									<div>单据日期从:</div>
									<input class="gdtd_select_input cx_demo" type="text"  name="ORDER_DATE1" value="${QueryParams.ORDER_DATE1}" onclick="SelectDate();">
									<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE1);">
								</td>
								<td class="gdtd_tb cx_demo">
									<div>单据日期到:</div>
									<input id="ORDER_DATE2" name="ORDER_DATE2" value="${QueryParams.ORDER_DATE2}" class="gdtd_select_input cx_demo" onclick="SelectDate();">
									<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DATE2);">
								</td>
								<td class="gdtd_tb cx_demo">
									<div>订单交期从:</div>
									<input  name="ORDER_DELIVERY_DATE1" value="${QueryParams.ORDER_DELIVERY_DATE1}" class="gdtd_select_input cx_demo" onclick="SelectDate();">
									<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DELIVERY_DATE1);">
								</td>
								<td class="gdtd_tb cx_demo">
									<div>订单交期到:</div>
									<input  name="ORDER_DELIVERY_DATE2" value="${QueryParams.ORDER_DELIVERY_DATE2}" class="gdtd_select_input cx_demo" onclick="SelectDate();">
									<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(ORDER_DELIVERY_DATE2);">
								</td>
							</tr>
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
		
		<script type="text/javascript" src="${ctx}/report/commons/initReport.js"></script>
		<script type="text/javascript" src="${ctx}/report/commons/jquery.jqprint.js"></script>
		
		<script type="text/javascript">
		 SelDictShow("STATE","BS_215","${QueryParams.STATE}","");
		 SelDictShow("BILL_TYPE","BS_190","${QueryParams.BILL_TYPE}","");
		 $("#query").click(function(){
		 	var isShow = $("#querydiv").css("display");
		 	if("block"==isShow){
		 		$("#querydiv").css("display","none");
		 	}else{
		 		$("#querydiv").css("display","block");
		 	}
		 	//$("#querydiv").slideDown("normal");
		 }); 
		 $("#q_close").click(function(){
			 $("#querydiv").css("display","none");
		 });
// 		rq_init('${RptModel}');
		$("#report_1_scrollArea").css({
			width : '100%',
			height : '50%',
			overflow : 'auto'
		});
		$("#report_1_contentdiv").css({
			overflow : 'hidden'
		});
		 function doPrint()
		 {			 
			 $("#reportPrint2").jqprint();
		 }
		</script>
	</body>

</html>
