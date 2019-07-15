<!--  
/**
  *@module 项目管理
  *@fuc 项目指令列表
  *@version 1.0
  *@author 王朋涛
*/
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<%@ include file="/pages/common/uploadfile/uploadfile_order.jsp"%> 
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>工程项目指令列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"/>
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script language="JavaScript" src="${ctx}/pages/drp/main/project/order/Order_List.js"></script>
	
</head>

<body>
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<tr>
	<td height="20px" valign="top">
	<div class="tablayer button_cls">
		<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
			<tr>
				   <td id="qxBtnTb" nowrap>
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
			                    <input id="modify" type="button" class="btn" value="编辑">
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
				   <c:if test="${pvg.PVG_EDIT eq 1 }"> 
				   	   <button class="img_input" >
			                <label for='release'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
			                    <input id="release" type="button" class="btn" value="指令下达" >
			                </label>
			           </button>
				   </c:if>
				   	<c:if test="${pvg.PVG_EDIT eq 1 }"> 
				   	   <button class="img_input" >
			                <label for='accept'>
			                    <img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
			                    <input id="accept" type="button" class="btn" value="指令接受" >
			                </label>
			           </button>
				   	</c:if> 
				   	  	<c:if test="${pvg.PVG_BWS eq 1 }">
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
	<td>
		<div class="lst_area">
		<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" >
			<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="project_no" >项目编号</th>
					<th nowrap align="center" dbname="project_name" > 项目名称</th>
					<th nowrap align="center" dbname="directive_type" >指令类型</th>
					<th nowrap align="center" dbname="" >指令名称</th>
					<th nowrap align="center" dbname="cre_name" >上传人</th>
					<th nowrap align="center" dbname="cre_time" >上传时间</th>
					<!-- <th nowrap align="center" dbname="" >接收人</th>
					<th nowrap align="center" dbname="" >接收时间</th>	 -->						
					<th nowrap align="center" dbname="" >状态</th>
				</tr>
			<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)"  onmouseout="mout(this)" id="tr${rr}">
						<td width="1%" align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.PROJECT_DIRECTIVE_ID}"/>
								<label for="radio_add"></label>
							</div>
							<input id="att_id${rr}"  type="hidden" value="${rst.ATT_ID}">
						</td>
						<td align="center">${rst.PROJECT_NO}&nbsp;</td>
						<td align="center">${rst.PROJECT_NAME}&nbsp;</td>
						<td align="center">${rst.DIRECTIVE_TYPE}&nbsp;</td>
						<td align="center">
							<a href="javascript:void(0)" onclick="downloadFile('${rst.ATT_PATH}','')">${rst.FILENAME}</a>
						</td>
						<td align="center">${rst.CRE_NAME}&nbsp;</td>
						 <td align="center">${rst.CRE_TIME}&nbsp;</td>  
						 <%-- <td align="center">${rst.USER_NAME}&nbsp;</td>
						 <td align="center">${rst.CRE_TIME}&nbsp;</td>  --%>
						 <td align="center"  id="state${rst.PROJECT_DIRECTIVE_ID}">${rst.STATE}</td> 
						<script type="text/javascript">
						   $("#tr${rr}").click(function(){
						      selectThis(this);
						      setSelEid(document.getElementById('eid${rr}'));
						      getATT_ID(document.getElementById('att_id${rr}'));
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
					<input type="hidden" id="att_id" name="att_id" value="${att_id}"/>
					<input type="hidden" id="selRowId" name="selRowId" value="${selRowId}">&nbsp;
					<input type="hidden" id="paramUrl" name="paramUrl" value="${paramCover.coveredUrl}">
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
					<td class="gdtd_tb cx_demo"><div>项目编号：</div>
				
						<input name="PROJECT_NO"  id="PROJECT_NO"  seltarget="selJGXX" type="text" value="${params.PROJECT_NO }" class="gdtd_select_input cx_demo">
						<%-- <img align="absmiddle"   name="selJGXX" style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif" seltarget="selJGXX" onClick="selCommon('BS_200', false, 'PROJECT_ID','PROJECT_ID','forms[0]','PROJECT_ID,PROJECT_NO,PROJECT_NAME','PROJECT_ID,PROJECT_NO,PROJECT_NAME')"> --%>
						<!-- <input  id="PROJECT_ID"  hidden="true"  seltarget="selJGXX" type="text"> -->
					</td>
					<td class="gdtd_tb cx_demo"><div>项目名称：</div>

						<input name="PROJECT_NAME" id="PROJECT_NAME" type="text" seltarget="selJGXX" value="${params.PROJECT_NAME }" class="gdtd_select_input cx_demo"/>
						<%-- <img align="absmiddle"   name="selJGXX" style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif" seltarget="selJGXX" onClick="selCommon('BS_200', false, 'PROJECT_ID','PROJECT_ID','forms[0]','PROJECT_ID,PROJECT_NAME','PROJECT_ID,PROJECT_NAME')"> --%>
					</td>
					 <td class="gdtd_tb cx_demo"><div>指令类型：</div>
		      		
		      			<select name="DIRECTIVE_TYPE"   id="DIRECTIVE_TYPE" class="gdtd_select_input cx_demo"  ></select>
		      		</td>
				
		      		 <td class="gdtd_tb cx_demo"><div>状态：</div>
					
						<select id="STATE" name="STATE" class="gdtd_select_input cx_demo"  ></select>
					</td>  
		      		
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
<script type=text/javascript>
	SelDictShow("STATE","BS_202","${params.STATE}","");
SelDictShow("DIRECTIVE_TYPE","BS_204","${params.DIRECTIVE_TYPE}","");
</script>

