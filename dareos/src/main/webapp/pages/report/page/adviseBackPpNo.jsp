<!-- 
 * @module 报表管理
 * @fuc 报表打印
 * @version 1.0
 * @author 邢克罚
 *  -->
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
	            <form action="${ctx}/system/report/toAdviseBackPpNo" method="get">
	            	<input type="hidden" name="reportUnit" id="reportUnit_AdviseBackPpNoQuery"
						value="${reportUnit}">

					<div class="search-item">
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
	                </div>
	            </form>
	        </div>
	    </div>
	    
		<div region="center" border="false"  class="main-table-div" style="overflow: auto;">
				<report:html 
					width="-1" 
					name="report_${RptModel}"
					printedRaq = "${reportFileName}"
					functionBarColor="#fff5ee"
					funcBarFontColor="blue"
					funcBarLocation="button"
					needSaveAsExcel="yes"
					needSaveAsPdf="yes"
					needPrint="yes"
					saveAsName="${printedRaq}"
					needPageMark="yes"
					needScroll="no"
					displayNoLinkPageMark="no"
					reportFileName="${reportFileName}"
					useCache="yes"
					separator="&nbsp;&nbsp;"
					params="${params}" 	
				    exceptionPage="/modules/report/reportcomm/reportError.jsp"
				    scrollWidth="100%"
				    generateParamForm="no"
				    savePrintSetup="yes" 
				  />
		</div>
		
		<jsp:include page="toolbarNew.jsp" flush="true">
			<jsp:param name="reportId" value="${RptModel}" />
			<jsp:param name="pageFlag" value="true" />
		</jsp:include>
		
	</div>
		<script type="text/javascript" src="${ctx}/report/commons/reportPageInit.js"></script>
		<script type="text/javascript">
		//设置滚动条的宽度
// 		var width = window.screen.width * 0.65 - 10;//弹出窗口的宽度
// 		var height = window.screen.height * 0.65 - 4;//弹出窗口的高度
		$("#report_${RptModel}_scrollArea").css({
			width : '100%',
			height : '50%',
			overflow : 'auto'
		});
		$("#report_${RptModel}_contentdiv").css({
			overflow : 'hidden'
		});
		
		rq_init('${RptModel}');
		var Core = require('core/core');
		var Config = require('config');
		
		// 通用选取选择单位 
		var ops = {
			prompt : '',
			iconWidth : 18,
			icons : [ {
				iconCls : 'icon-search',
				handler : function(e) {
					var reportUnit_ = "${currentReportUnit}";
					/* console.log(reportUnit_); */
					var conditions = " and UNITCODE like '"+reportUnit_+"%' ";
					Core.commonSelect(this, 'middle', '选择单位', 'BASE_0001',
							'reportUnit_AdviseBackPpNoQuery,reportUnitName_AdviseBackPpNoQuery',
							'UNITCODE,UNITNAME', conditions);
				}
			} ]
		};
		$("#${RptModel}").find('#reportUnitName_AdviseBackPpNoQuery').textbox(ops);
		
		//限制查询范围
		$("#${RptModel}").find("input[name='reportDateBeg']").datebox({
			onChange: function (n, o) {
// 				//如果结束时间不为空，并且结束时间在开始时间之前，清空结束时间
				var beg=$("#${RptModel}").find("#reportDateBeg").datebox("getValue");
				var end=$("#${RptModel}").find("#reportDateEnd").datebox("getValue");
				if(end&&new Date(beg.replace("-", "/").replace("-", "/"))>new Date(end.replace("-", "/").replace("-", "/"))){
					$("#${RptModel}").find("#reportDateEnd").datebox("setValue","");
					$("#${RptModel}").find("#reportDateEnd").datebox("setText","");
				}
			}
		})
		//限制结束时间不能大于开始时间
		$("#${RptModel}").find("#reportDateEnd").datebox().datebox('calendar').calendar({
			validator: function(date){
				var dt=new Date($("#${RptModel}").find("#reportDateBeg").datebox("getValue").replace("-", "/").replace("-", "/"));
				return dt<=date;
			}
		});
		
		// 页面加载完成的响应函数 
		$.parser.onComplete = function() {
			$("#${RptModel}").find('.textbox-addon .textbox-icon').removeClass('textbox-icon-disabled');
		}
		
		</script>
	</body>

</html>
