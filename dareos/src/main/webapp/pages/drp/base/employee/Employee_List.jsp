<!-- 
 *@module 分销业务
 *@func 人员信息维护
 *@version 1.1
 *@author lyg
 *  -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>人员信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/drp/base/employee/Employee_List.js"></script>
	<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<tr>
	<td height="20">
		<div class="tablayer" >
			<table id="qxBtnTb" cellpadding="0"  cellspacing="6" border="0" width="100%" >
				<tr>
				   <td id="qxBtnTb" nowrap>
				   	<c:if test="${pvg.PVG_EDIT eq 1 }">
				   	   <button class="img_input addbtn" id="addBtn" >
							<label for='add'>
							<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
							<input id="add" type="button" class="btn add" value="新增" />
							</label>
						</button>
					   	<button class="img_input" id="modifyBtn" >
							<label for='modify'>
							<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
							<input id="modify" type="button" class="btn" value="编辑" />
							</label>
						</button>
				   	</c:if>
				   	<c:if test="${pvg.PVG_DELETE eq 1 }">
				   	   <button class="del_input" id="deleteBtn" >
									<label for='delete'>
									<img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
									<input id="delete" type="button" class="del_btn" value="删除" />
									</label>
								</button>
				   	</c:if>
				   	<c:if test="${pvg.PVG_START_STOP eq 1 }">
				   	   <button class="img_input" >
			                <label for='start'>
			                    <img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
			                    <input id="start" type="button" class="btn" value="启用" >
			                </label>
			           </button>
					   	<button class="img_input" >
			                <label for='stop'>
			                    <img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font">
			                    <input id="stop" type="button" class="btn" value="停用" >
			                </label>
			           </button>	
				   	</c:if>
				   	 <button class="img_input" >
			                <label for="defaultP">
			                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
			                    <input id="defaultP"  type="button" class="btn" value="设为默认密码"/>
			                </label>
			             </button>
			             <button class="img_input" >
			                <label for='qx'>
			                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
			                    <input id="qx" type="button" class="btn" value="用户角色" >
			                </label>
			           </button>	
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
	<td valign="top">
		<div class="lst_area" style="margin-left:3px;">
			<table id="ordertb" width="100%" border="0" cellpadding="3" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="RYBH" >人员编号${pvg.PVG_EDIT }</th>
					<th nowrap align="center" dbname="XM" >姓名</th>
					<th nowrap align="center" dbname="YHM" >用户名</th>
					<th nowrap align="center" dbname="XB" >性别</th>
					<th nowrap align="center" dbname="SJ" >手机</th>
					<th  nowrap="nowrap" align="center" dbname="CRENAME" >制单人</th>
                    <th  nowrap="nowrap" align="center" dbname="CRETIME" >制单时间</th>
					<th nowrap align="center" dbname="RYZT" >状态</th>		
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
				
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" id="tr${rr}" >
						<td width="1%" align='center' >
							<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.RYXXID}"/>
						</td>
						<input type="hidden" value="${rst.RYLB}" id="rylb${rst.RYXXID}"/>
						<td align="center">${rst.RYBH }&nbsp;</td>
						<td align="left">${rst.XM}&nbsp;</td>
						<td align="left">${rst.YHM}&nbsp;</td>
						<td align="center">${rst.XB}&nbsp;</td>
						<td align="center">${rst.SJ}&nbsp;</td>
						<td nowrap="nowrap" align="left">${rst.CRENAME}&nbsp;</td>
                    	 <td nowrap="nowrap" align="center">${rst.CRETIME}&nbsp;</td>
						<td nowrap align="center" json='STATE' id="state${rst.RYXXID}" value="${rst.RYZT}">${rst.RYZT}&nbsp;</td>
						
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
	<td height="12px" align="center">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0" class="lst_toolbar">
			<tr>
				<td>
					<form id="pageForm" action="#" method="post" name="listForm">
									<input type="hidden" id="pageSize" name="pageSize"
										value='${page.pageSize}' />
									<input type="hidden" id="pageNo" name="pageNo"
										value='${page.pageNo}' />
									<input type="hidden" id="orderType" name="orderType"
										value='${orderType}' />
									<input type="hidden" id="orderId" name="orderId"
										value='${orderId}' />
									<input type="hidden" id="selRowId" name="selRowId"
										value="${selRowId}">
									&nbsp;
									<input type="hidden" id="paramUrl" name="paramUrl"
										value="${paramCover.coveredUrl}">
									<span id="hidinpDiv" name="hidinpDiv"></span>
									${paramCover.unCoveredForbidInputs}
								</form>
				</td>
				<td align="right">
					 ${page.footerHtml}${page.toolbarHtml}
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>
<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#">
<table width="100%" border="0" cellpadding="4" cellspacing="4" class="detail">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="1" cellspacing="1" class="detail3">
			<input type="hidden" name="selectParams" value="BMZT in ('启用','停用') and DELFLAG=0 and JGXXID='${params.ZTXXID}' ">
			<input type="hidden" id="selectRYXX" name="selectRYXX" value=" ZTXXID='${params.ZTXXID}' ">
			<input type="hidden" name="selJs" value=" JSBH like '%DRP%' "/>
<!-- 			<input type="hidden" name="selJs" value=" "/> -->
				<tr>
					<td nowrap align="right" class="detail_label">人员编号：</td>
					<td class="detail_content">
						<input name="RYXXID" id="RYXXID"  type="hidden" seltarget="selRYXX" value="${params.RYXXID }">
						<input name="RYBH" id="RYBH"  type="text" seltarget="selRYXX" value="${params.RYBH }">
						<%--<img align="absmiddle" name="selRYXX" style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif" 
						onClick="selCommon('System_0', false, 'RYXXID', 'RYXXID', 'forms[1]','RYXXID,RYBH,XM', 'RYXXID,RYBH,XM', 'selectParam')">
						--%>
						
						<img align="absmiddle" name="selRYXX" style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif" 
						onClick="selCommon('BS_97', false, 'RYXXID', 'RYXXID', 'forms[1]','RYBH,XM', 'RYBH,XM', 'selectRYXX')">
						
						
					</td>
					<td nowrap align="right" class="detail_label">姓名：</td>
					<td class="detail_content">
						<input name="XM"  type="text" value="${params.XM }"/>
					</td>
					 <td nowrap align="right" class="detail_label">所属部门编号：</td>
		      		<td nowrap class="detail_content">
		                        <input type="text" json="BMBH" name="BMBH" seltarget="selJGXX" value="${params.BMBH }"/>
		                        <input id="BMXXID" name="BMXXID" type="hidden" seltarget="selJGXX" value="${params.BMXXID }"/>
								 <img align="absmiddle" name="selBmXX" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/select.gif" 
										   		onClick="selCommon('System_1', false, 'BMXXID', 'BMXXID', 'forms[1]','BMBH,BMMC', 'BMBH,BMMC', 'selectParams');">                
		      		</td>
		      		<td nowrap align="right" class="detail_label">所属部门名称：</td>
		      		<td nowrap class="detail_content">
		                <input type="text" name="BMMC" autocheck="true" seltarget="selBmXX" value="${params.BMMC }"/>
		      		</td>	
				</tr>
				<tr>
					<td nowrap align="right" class="detail_label">角色编号：</td>
		      		<td nowrap class="detail_content">
		                <input type="text" name="JSBH" autocheck="true"  value="${params.JSBH }"/>
		                <img align="absmiddle" name="selBmXX" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/select.gif" 
										   		onClick="selCommon('System_4', false, 'JSBH', 'XTJSID', 'forms[1]','JSBH,JSMC', 'JSBH,JSMC', 'selJs')">
		      		</td>	
		      		<td nowrap align="right" class="detail_label">角色名称：</td>
		      		<td nowrap class="detail_content">
		                <input type="text" name="JSMC" autocheck="true"  value="${params.JSMC}"/>
		      		</td>
		      		<%--
		      		<td nowrap align="right" class="detail_label">性别：</td>
					<td class="detail_content">
						<input id="XB" name="XB" json="XB" type="radio" value="男" <c:if test="${params.XB!='女' }">checked="checked"</c:if> />男
						<input id="XB" name="XB" json="XB" type="radio" value="女" <c:if test="${params.XB=='女' }">checked="checked"</c:if>/>女
					</td>
					--%>
					<td nowrap align="right" class="detail_label">状态：</td>
					<td class="detail_content">
						<select id="RYZT" name="RYZT" style="width:155" ></select>
					</td>
					<td nowrap align="right" class="detail_label"></td>
					<td class="detail_content"></td>
				</tr>
				<tr>
					
		      		<td width="8%" nowrap align="right" class="detail_label">制单时间从:</td>
					<td width="15%" class="detail_content">
	   					<input id="BEGIN_CRE_TIME" name="BEGIN_CRE_TIME" readonly="readonly" onclick="SelectTime();" style="width:155" value="${params.BEGIN_CRE_TIME}"    />
	   					<img align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectTime(BEGIN_CRE_TIME);" >
					</td>	
					<td width="8%" nowrap align="right" class="detail_label">制单时间到:</td>
					<td width="15%" class="detail_content">
	   					<input id="END_CRE_TIME" name="END_CRE_TIME" readonly="readonly" style="width:155" onclick="SelectTime();" value="${params.END_CRE_TIME}"/>
	   					<img align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectTime(END_CRE_TIME);" >
					</td>
					<td nowrap align="right" class="detail_label"></td>
					<td class="detail_content">
					</td>
					<td nowrap align="right" class="detail_label"></td>
					<td class="detail_content">
					</td>
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn" value="确定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn" value="关闭" >&nbsp;&nbsp;
						<input  type="reset" class="btn" value="重置" >
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
</body>
<script language="JavaScript">
		SelDictShow("RYZT","32","${params.RYZT}","");
</script>
</html>