<%--
/**
 * @author zhuxw
 * @function 
 * @version 2011年11月16日 11时23分15秒
 */
--%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/sys/notice/Notice_List.js"></script>
	<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20px" valign="top">
		<table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td>当前位置：系统管理&gt;&gt;消息管理&gt;&gt;公告信息维护
			</td>
			<td width="50" align="right"></td>
		</tr>
	  </table>
	</td>
</tr> -->
<tr>
	<td height="20">
		<div class="tablayer" style="margin-left:3px; border-bottom:0px solid #d3d3d3; padding-bottom:3px; margin-bottom:3px;">
			<table cellSpacing=0 cellPadding=0 border=0 width="100%" id="qxBtnTb">
				<tr>
				   <td nowrap>
				   <c:if test="${pvg.PVG_EDIT eq 1 }">
			   		<button class="img_input addbtn" >
		                <label for='add'>
		                    <img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
		                    <input type="button" id="add" class="btn add" value="新增">
		                </label>
		           </button>
			   		<button class="img_input" >
		                <label for='modify'>
		                    <img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
		                    <input id="modify" type="button" class="btn" value="修改">
		                </label>
		           </button>
				   </c:if>
				   <c:if test="${pvg.PVG_DELETE eq 1 }">
			   		<button class="del_input" >
		                <label for='delete'>
		                    <img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
		                    <input id="delete" type="button" class="del_btn" value="删除" >
		                </label>
		           </button>
				   </c:if>
				  <%-- <c:if test="${pvg.PVG_CLOSE eq 1 }">
				    <input id="close" type="button" class="btn" value="关闭(L)">
				   </c:if>--%>
				   <c:if test="${pvg.PVG_BWS eq 1 or pvg.PVG_BWS_AUDIT eq 1 }">
			   		<button class="img_input" >
			                <label for='query'>
			                    <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
			                    <input id="query" type="button" class="btn" value="查询">	
			                </label>
			           </button>
				   </c:if>
				</td>
				</tr>
			</table>
		</div>
	</td>
</tr>
<tr>
	<td valign="top">
		<div class="lst_area">
			<table id="ordertb" width="100%" border="0" cellpadding="1" cellspacing="1" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th  nowrap="nowrap" align="center" dbname="NOTICE_TITLE">公告标题</th>
					<th nowrap="nowrap" align="center"  dbname="NOTICE_TYPE">公告类型</th>
					<th nowrap="nowrap" align="center"  dbname="NOTICE_OBJ">公告对象</th>
					<th nowrap="nowrap" align="center"  dbname="NOTICE_LVL">公告级别</th>
					<th nowrap="nowrap" align="center"  dbname="CRETIME">制单时间</th>
					<th nowrap="nowrap" align="center"  dbname="STATIME ">开始时间</th>
					<th nowrap="nowrap" align="center"  dbname="ENDTIME">结束时间</th>
					<%--<th nowrap="nowrap" align="center"  dbname="STATE">状态</th>--%>
					
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="goQueryNotice()" onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%"align='center' >
							<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.NOTICE_ID}"/>
						</td>
						<td nowrap="nowrap" align="left">${rst.NOTICE_TITLE}&nbsp;
						</td>
						<td nowrap="nowrap" align="left">
                            <c:if test="${rst.NOTICE_TYPE eq 1}">
                                通知&nbsp;
                            </c:if>
                            <c:if test="${rst.NOTICE_TYPE eq 2}">
                                促销&nbsp;
                            </c:if>
                            <c:if test="${rst.NOTICE_TYPE eq 3}">
                                资料&nbsp;
                            </c:if>
                        </td>
						<td nowrap="nowrap" align="center">
                            <c:if test="${rst.NOTICE_OBJ eq 1}">
                                全部&nbsp;
                            </c:if>
                            <c:if test="${rst.NOTICE_OBJ eq 2}">
                                总部&nbsp;
                            </c:if>
                            <c:if test="${rst.NOTICE_OBJ eq 3}">
                                经销商&nbsp;
                            </c:if>
                        </td>
						<td nowrap="nowrap" align="center">
							<c:if test="${rst.NOTICE_LVL eq 1}">
								置顶&nbsp;
							</c:if>
							<c:if test="${rst.NOTICE_LVL eq 2}">
								一般&nbsp;
							</c:if>
						</td>
						<td nowrap="nowrap" align="center">${rst.CRETIME}&nbsp;</td>
						<td nowrap="nowrap" align="center">${rst.STATIME }&nbsp;</td>
						<td nowrap="nowrap" align="center">${rst.ENDTIME}&nbsp;</td>
						<%--<td nowrap="nowrap" align="center">${rst.STATE}&nbsp;</td>--%>
						 <input id="state${rst.NOTICE_ID}" type="hidden"/>
						
					</tr>
				</c:forEach>
				<c:if test="${empty page.result}">
					<tr>
						<td height="25" colspan="12" align="center" class="lst_empty">
							&nbsp;无相关信息&nbsp;
						</td>
					</tr>
				</c:if>
			</table>
		</div>
	</td>
</tr>
<tr>
	<td height="12px" align="center">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0" class="lst_toolbar">
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
				<td align="left">
					 ${page.footerHtml}${page.toolbarHtml}
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#">
<table width="100%" border="0" cellpadding="4" cellspacing="0" class="detail cx_table ">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="1" cellspacing="1" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo"><div>开始时间：</div>
					
	   					<input type="text" id="STATIME" name="STATIME" readonly="readonly"  onclick="SelectTime();"  value="${params.STATIME }" class="gdtd_select_input cx_demo">
	   					<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectTime(STATIME);">
					</td>
					<td class="gdtd_tb cx_demo"><div>结束时间：</div>
					
	   					<input id="ENDTIME"  name="ENDTIME"  onclick="SelectTime();" value="${params.ENDTIME }" class="gdtd_select_input cx_demo">
	   					<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectTime(ENDTIME);">
					</td>
					
					 <td  class="gdtd_tb cx_demo"><div>制单时间从:</div>
					
	   					<input  type="text"  id="CRETIME_START" name="CRETIME_START" readonly="readonly"   onclick="SelectTime();" class="gdtd_select_input cx_demo" value="${params.CRETIME_START}">
	   					<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"  onclick="SelectTime(CRETIME_START);" >
					</td>
					<td class="gdtd_tb cx_demo"><div>制单时间到:</div>
					
	   					<input  type="text" id="CRETIME_END" name="CRETIME_END" readonly="readonly"   onclick="SelectTime();"  class="gdtd_select_input cx_demo" value="${params.CRETIME_END}">
	   					<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif"  onclick="SelectTime(CRETIME_END);">
					</td>
					
				</tr>
				<tr>
				    <td class="gdtd_tb cx_demo"><div>公告类型：</div>
					
	   					 <select name="NOTICE_TYPE" id="NOTICE_TYPE" json="NOTICE_TYPE" class="gdtd_select_input cx_demo"> </select>
					</td>
					<td class="gdtd_tb cx_demo"><div>公告对象：</div>
					
						<select name="NOTICE_OBJ" id="NOTICE_OBJ" json="NOTICE_OBJ" class="gdtd_select_input cx_demo"> </select>
					</td>
					<td class="gdtd_tb cx_demo"><div>公告级别：</div>
					
						<select name="NOTICE_LVL" id="NOTICE_LVL" json="NOTICE_LVL" class="gdtd_select_input cx_demo"> </select>
					</td>
					<%--<td width="15%" align="right" class="detail_label">公告对象：</td>
					<td width="35%" class="detail_content">
                    <input type="text"  id="NOTICE_OBJ" name="NOTICE_OBJ" json="NOTICE_OBJ" maxlength="30"    autocheck="true" inputtype="string" label="公告对象" value="${params.NOTICE_OBJ}" mustinput="true"  />&nbsp;
                     <img align="absmiddle" name="selJGXX" style="cursor: hand"
						src="${ctx}/styles/${theme}/images/plus/select.gif"       
						onClick="selCommon('BS_128', true, 'NOTICE_OBJ', 'SJXMC', 'forms[1]','NOTICE_OBJ', 'SJXMC', '')">
					</td>--%>
				</tr>
				
				<tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn" value="确定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" >&nbsp;&nbsp;
						<input  type="reset" class="btn cx_btn" value="重置" >
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
</body>
<jsp:include page="/pages/sys/spflow/publicFlow.jsp" />
</html>

<script type="text/javascript">
	SelDictShow("NOTICE_TYPE","192","${params.NOTICE_TYPE}","");
	SelDictShow("NOTICE_OBJ","202","${params.NOTICE_OBJ}","");
	SelDictShow("NOTICE_LVL","203","${params.NOTICE_LVL}","");
	SelDictShow("STATE", "BS_138", "${params.STATE}", "");
	function goQueryNotice(){
		 var selRowId = $("#selRowId").val();
		var url ='${ctx}/sys/first/queryNoticeById?NOTICE_ID='+selRowId;
		myShowModalDialog(url,true);
		
	}
	//初始化 审批流程
	/* spflowInit("${pvg.AUD_BUSS_TYPE}", "${pvg.AUD_TAB_NAME}", "${pvg.AUD_TAB_KEY}", "", "${pvg.AUD_FLOW_INS}", "UPDTIME"); */	   
</script>
