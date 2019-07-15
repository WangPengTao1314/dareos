
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
	</head>
	<body >
	
	
	
	<div class="layout" fit="true" id="${RptModel}" style="height:100%">
		<div region="north" border="false">
	        <div class="easyui-panel search-body" cls="search" id="search">
	            <form action="${ctx}/system/report/toSafeSector" method="get">
	            	<div class="search-item">
	                    <label>数据统计范围</label>
	                    <input class="easyui-textbox"  readonly="readonly" value="${unitName}"  />
	                </div>
	            </form>
	        </div>
	    </div>
	    
		<div region="center" border="false"  class="main-table-div" style="overflow: auto;">
	    	
<!-- 				funcBarLocation = "no"  -->
					 <report:html 
				width="-1"
				name = "report_${RptModel}" 
				reportFileName = "${reportFileName}"
				printedRaq = "${reportFileName}"
				params = "${params}" 	
				funcBarLocation = "no" 
				exceptionPage = "/modules/report/reportcomm/reportError.jsp"
				needPageMark="yes"
				needScroll="no"
				displayNoLinkPageMark="no"
				needPrint="yes"
				height="-1"
				scrollWidth="100%"
				/>
		</div>
		
		
	</div>
		<script type="text/javascript" src="${ctx}/report/commons/reportPageInit.js"></script>
		<script type="text/javascript">
		//设置滚动条的宽度
		var width = window.screen.width * 0.65 - 10;//弹出窗口的宽度
		var height = window.screen.height * 0.65 - 4;//弹出窗口的高度
		$("#report1_scrollArea").css({
			width : '100%',
			height : 'auto',
			overflow : 'auto'
		});
		$("#report1_contentdiv").css({
			overflow : 'hidden'
		});
		

		
		rq_init('${RptModel}');
		</script>
	</body>

</html>
