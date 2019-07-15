<!--  
/**
  *@module 售后管理
  *@fuc 售后问题单处理
  *@version 1.0
  *@author 王朋涛
*/
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>工程项目列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"/>
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script language="JavaScript" src="${ctx}/pages/drp/main/deliver/after/After_List.js"></script>
	
</head>

<body>
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20px" valign="top">
	   <table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td>当前位置：销售管理 &gt;&gt; 订单中心 &gt;&gt; 售后反馈</td> 
			<td width="50" align="right"></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20px" valign="top">
	<div class="tablayer button_cls">
		<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
			<tr>
				   <td id="qxBtnTb" nowrap>
				   <c:if test="${pvg.PVG_EDIT eq 1 or pvg.PVG_EDIT_FX eq 1}">
				   		<button class="img_input addbtn" id="add_btn">
			                <label for='add'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
			                    <input type="button" id="add" class="btn add" value="新增">
			                </label>
			           </button>
				   	   <button class="img_input" id="modify_btn">
			                <label for='modify'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
			                    <input id="modify" type="button" class="btn" value="编辑">
			                </label>
			           </button>
			           <button class="img_input" id="submit_btn">
			                <label for='submit'>
			                    <img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
			                    <input id="submit" type="button" class="btn" value="提交">
			                </label>
			           </button>
				   	</c:if>
				   	<c:if test="${pvg.PVG_DELETE eq 1 or pvg.PVG_DELETE_FX eq 1 }">
				   	   <button class="del_input" id="delete_btn">
			                <label for='delete'>
			                    <img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
			                    <input id="delete" type="button" class="del_btn" value="删除" >
			                </label>
			           </button>
				   </c:if> 
				   	<c:if test="${pvg.PVG_BWS_AUDIT eq 1 }">
				   	   <button class="img_input" id="handle_btn">
			                <label for='handle'>
			                    <img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
			                    <input id="handle" type="button" class="btn" value="处理" >
			                </label>
			           </button>
				   	</c:if>
				   	<c:if test="${pvg.PVG_BWS eq 1 or  pvg.PVG_BWS_FX eq 1 }">
				   	 <button class="img_input" id="flowTrackBtn" >
						<label for='flowTrack'>
						<img src="${ctx}/styles/${theme}/images/icon/shenhe.png"  class="icon_font">
						<input id="flowTrack" type="button" class="btn" value="处理跟踪" />
						</label>
					  </button>
				   	  <button class="img_input" >
			                <label for='query'>
			                    <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
			                    <input id="query" type="button" class="btn" value="查询">	
			                </label>
			           </button>
			           <%-- <button class="img_input">
							<label for='import2'>
								<a href="#" >
								<img src="${ctx}/styles/${theme}/images/icon/print.png" class="icon_font"></a> &nbsp;&nbsp;&nbsp;
								<input id="import2" onclick="parent.gotoBottomPage('${ctx}/sys/report/toDepositInfoDtlReport')" type="button" class="btn" value="明细">
							</label>
						</button> --%>
				   	</c:if>
				</td>
				</tr>
		</table>
		</div>
	</td>
</tr>
<tr>
	<td>
		<div class="lst_area">
		<table id="ordertb" width="100%" border="0" cellpadding="1" cellspacing="0" class="lst ellipsis" >
			<tr class="fixedRow">
					<th width="22px"></th>
					<th nowrap align="center" dbname="problem_feedback_no" style="width: 10%;">问题反馈单号</th>
					<th nowrap align="center" dbname="sale_order_no" style="width: 15%;">订单号</th>
					<th nowrap align="center" dbname="cust_name" style="width: 5%;">客户</th>   
					<th nowrap align="center" dbname="chann_name" style="width: 18%;">经销商</th>
					<th nowrap align="center" dbname="problem_sketch" style="width: 18%;">问题描述</th> 
					<th nowrap align="center" dbname="problem_type" style="width: 8%;"> 问题分类</th>
					<th nowrap align="center" dbname="state" style="width: 5%;">状态</th>
					<th nowrap align="center" dbname="upd_name" style="width: 5%;">处理人</th>							
					<th nowrap align="center" dbname="state" style="width: 5%;">分析原因</th>
					<th nowrap align="center" dbname="cre_time" >提出时间</th>
				</tr>
			<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" ondblclick="parent.gotoBottomPage()" onmouseout="mout(this)" id="tr${rr}">
						<td width="1%" align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.PROBLEM_FEEDBACK_ID}"/>
								<label for="radio_add"></label>
							</div>
						</td>
						<td align="center" id="PROBLEM_FEEDBACK_NO${rst.PROBLEM_FEEDBACK_ID}" >${rst.PROBLEM_FEEDBACK_NO}</td>
						<td align="center">${rst.SALE_ORDER_NO}&nbsp;</td>
						<td align="center">${rst.CUST_NAME}&nbsp;</td>
						<td align="center" title="${rst.CHANN_NAME}">${rst.CHANN_NAME}&nbsp;</td>
						<td align="center" title="${rst.PROBLEM_DETAILED}">${rst.PROBLEM_DETAILED}&nbsp;</td>
						<td align="center" title="${rst.LEDGER_NAME_ABBR}">${rst.LEDGER_NAME_ABBR}&nbsp;</td> 
						<td align="center" id="STATE${rst.PROBLEM_FEEDBACK_ID}">${rst.STATE}</td>
						 <td align="center">${rst.USER_NAME}&nbsp;</td> 
						<td align="center" title="${rst.REASON_ANALYSIS}">${rst.REASON_ANALYSIS}</td>
						<td align="center">${rst.CRE_TIME}&nbsp;</td>
						<input type="hidden" id="CHANN_ID${rst.PROBLEM_FEEDBACK_ID}" value="${rst.CHANN_ID}"/>
						<input type="hidden" id="CREATOR${rst.PROBLEM_FEEDBACK_ID}" value="${rst.CREATOR}"/>
						<input type="hidden" id="UPDATOR${rst.PROBLEM_FEEDBACK_ID}" value="${rst.UPDATOR}"/>
						<input type="hidden" id="ORDER_ORG${rst.PROBLEM_FEEDBACK_ID}" value="${rst.ORDER_ORG}"/>
						<input type="hidden" id="ORDER_TRACE_ID${rst.PROBLEM_FEEDBACK_ID}" value="${rst.ORDER_TRACE_ID}"/>
						<script type="text/javascript">
						   $("#tr${rr}").click(function(){
						      selectThis(this);
						      setSelEid(document.getElementById('eid${rr}'));
						   });
					    </script>
						
					</tr>
				</c:forEach>
				<c:if test="${empty page.result}">
					<tr>
						<td height="25" colspan="13" align="center" class="lst_empty">
							&nbsp;无相关信息&nbsp;
						</td>
					</tr>
				</c:if>
		</table>
		</div>
	</td>
</tr>
<tr>
	<td height="8px">
		<table width="100%" border="0" cellpadding="0"cellspacing="0" class="lst_toolbar">
		<tr>
			<td>
				<form id="pageForm" action="#" method="post" name="listForm">
				<input type="hidden" id="pageSize" name="pageSize" value='${page.pageSize}'/>
					<input type="hidden" id="pageNo" name="pageNo" value='${page.pageNo}'/>
					<input type="hidden" id="orderType" name="orderType" value='${orderType}'/>
					<input type="hidden" id="orderId" name="orderId" value='${orderId}'/>
					<input type="hidden" id="selRowId" name="selRowId" value="${selRowId}">&nbsp;
					<input type="hidden" id="paramUrl" name="paramUrl" value="${paramCover.coveredUrl}">
					<input type="hidden" id="is_drp_ledger" name="is_drp_ledger" value='${IS_DRP_LEDGER}'/>
					<input type="hidden" id="chann_id" name="chann_id" value='${CHANN_ID}'/>
					<span id="hidinpDiv" name="hidinpDiv"></span>
					${paramCover.unCoveredForbidInputs } 
				</form>
			</td>
			<td align="left">${page.footerHtml}${page.toolbarHtml}</td>
		</tr>
		</table>
	</td>
</tr>	
</table>
<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
<input type="hidden" name="selectContion2" value=" DELFLAG = 0 and bmzt = '启用'" />
<input type="hidden" name="selectContion3" value=" DELFLAG = 0 and jgzt = '启用'" />

	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo">
						<div>问题反馈单号：</div>
						<input type="text" name="PROBLEM_FEEDBACK_NO" class="gdtd_select_input cx_demo"  seltarget="selRYXX" value="${param.PROBLEM_FEEDBACK_NO}">
					</td>
					<td class="gdtd_tb cx_demo">
					 	<div>订单编号：</div>
						<input name="SALE_ORDER_NO" id="SALE_ORDER_NO"  type="text" class="gdtd_select_input cx_demo" seltarget="selJGXX"  value="${param.SALE_ORDER_NO}"/>
						<%-- <img align="absmiddle"    name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" seltarget="selJGXX" onClick="selCommon('BS_70', false, 'SALE_ORDER_ID', 'SALE_ORDER_ID', 'forms[1]','SALE_ORDER_ID,SALE_ORDER_NO','SALE_ORDER_ID,SALE_ORDER_NO')">
						<input name="SALE_ORDER_ID" id="SALE_ORDER_ID" hidden="true" type="text" class="gdtd_select_input cx_demo" seltarget="selJGXX"  /> --%>
					</td>
					<!-- <td class="gdtd_tb cx_demo">
						<div>问题类型：</div>
						<select name="PROBLEM_TYPE"   id="PROBLEM_TYPE" type="text" inputtype="strimg" label="问题类型"  class="gdtd_select_input cx_demo"  autocheck="true"></select>	
					</td> -->
					 <td class="gdtd_tb cx_demo">
					 	<div>客户：</div>
		                 <input type="text"  name="CUST_NAME"  value="${params.CUST_NAME}" class="gdtd_select_input cx_demo"/>
		      		</td>
					<td class="gdtd_tb cx_demo">
					<div>经销商：</div>
		               <input type="text" name="CHANN_NAME" autocheck="true" seltarget="selBmXX" value="${params.CHANN_NAME}" class="gdtd_select_input cx_demo"/>
		      	    </td>	
				</tr>
				<tr>
		      	    <td class="gdtd_tb cx_demo">
		      	    	<div>状态：</div>
		      	    	<select name="STATE" class="gdtd_select_input cx_demo"  id="STATE" type="text" inputtype="strimg" label="状态"    autocheck="true" ></select>	
		      	    </td>	
		      		<td class="gdtd_tb cx_demo"></td>
		      		<td class="gdtd_tb cx_demo"></td>
		      		<td class="gdtd_tb cx_demo"></td>
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn"  value="确定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn"  value="关闭" >&nbsp;&nbsp;
						<input  type="reset" class="btn cx_btn" value="重置"  >
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
</body>
<script type=text/javascript>
	SelDictShow("STATE","BS_197","${params.STATE }","");
	SelDictShow("PROBLEM_TYPE","BS_198","${params.PROBLEM_TYPE }","");
</script>



