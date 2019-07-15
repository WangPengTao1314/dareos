<!-- 
 *@module 系统管理
 *@func 人员信息
 *@version 1.1
 *@author 吴亚林
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
	<title>机构信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/sys/ryxx/ryxxwh_List.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20">
		<table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td>当前位置：系统管理&gt;&gt;基础信息&gt;&gt;人员信息</td>
			<td width="50" align="right"></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20">
		<div class="tablayer" >
			<table cellSpacing=0 cellPadding=0 border=0 width="100%">
				<tr>
				   <td id="qxBtnTb" nowrap>
				   	<c:if test="${qxcom.XT0010502 eq 1 }">
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
				   	<c:if test="${qxcom.XT0010503 eq 1 }">
				   	   <button class="del_input" >
			                <label for='delete'>
			                    <img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
			                    <input id="delete" type="button" class="del_btn" value="删除" >
			                </label>
			           </button>
				   	</c:if>
				   	<c:if test="${qxcom.XT0010504 eq 1 }">
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
				   	<c:if test="${qxcom.XT0010501 eq 1 }">
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
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="RYBH" >人员编号</th>
					<th nowrap align="center" dbname="XM" >姓名</th>
					<th nowrap align="center" dbname="XB" >性别</th>
					<th nowrap align="center" dbname="SJ" >手机</th>
					<th nowrap align="center" dbname="SFZH" >身份证号</th>
					<th nowrap align="center" dbname="BMMC" >所属部门</th>
					<th nowrap align="center" dbname="JGMC" >所属机构</th>
					<th nowrap align="center" dbname="ZW" >职务</th>							
					<th nowrap align="center" dbname="RYZT" >状态</th>		
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
				
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					
					<tr class="list_row${r}" onmouseover="mover(this)" ondblclick="parent.gotoBottomPage()" onmouseout="mout(this)" id="tr${rr}">
						<td width="1%" align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.RYXXID}"/>
								<label for="radio_add"></label>
							</div>
						</td>
						<td align="center">${rst.RYBH }</td>
						<td align="center">${rst.XM}</td>
						<td align="center">${rst.XB}</td>
						<td align="center">${rst.SJ}</td>
						<td align="center">${rst.SFZH}</td>
						<!-- <td align="left">${rst.BMXXID}</td> -->
						<td align="center">${rst.BMMC}</td>
						<!-- <td align="left">${rst.JGXXID}</td> -->
						<td align="center">${rst.JGMC}</td>
						<td align="center">${rst.ZW }</td>						
						<td align="center"  id="state${rst.RYXXID}">${rst.RYZT}</td>
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
							无相关信息
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
						<input type="hidden" id="selRowId" name="selRowId" value="${selRowId}">
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
<input type="hidden" name="selectContion2" value=" DELFLAG = 0 and bmzt = '启用'" />
<input type="hidden" name="selectContion3" value=" DELFLAG = 0 and jgzt = '启用'" />

	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo"><div>人员编号：</div>
						<input name="RYXXID" type="hidden" seltarget="selRYXX" value="${params.RYXXID }" class="gdtd_select_input cx_demo">
						<input name="RYBH"  type="text" seltarget="selRYXX" value="${params.RYBH }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selRYXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" onClick="selCommon('System_0', true, 'RYXXID', 'RYXXID', 'forms[1]','RYXXID,RYBH,XM', 'RYXXID,RYBH,XM', '')">
					</td>
					<td class="gdtd_tb cx_demo"><div>姓名：</div>
				
						<input name="XM"  type="text" value="${params.XM }" class="gdtd_select_input cx_demo"/>
					</td>
					 <td class="gdtd_tb cx_demo"><div>所属机构：</div>
		                        <input type="text" json="JGMC" name="JGMC" seltarget="selJGXX" value="${params.JGMC }" class="gdtd_select_input cx_demo"/>
		                        <input id="JGXXID" name="JGXXID1" type="hidden" seltarget="selJGXX" value="${params.JGXXID }" class="gdtd_select_input cx_demo"/>
								 <img align="absmiddle" class="select_gif" name="selJGXX" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/select.gif" 
										   		onClick="selCommon('System_2', true, 'JGXXID', 'JGXXID', 'forms[1]','JGMC', 'JGMC', '');">                        
		      		</td>
		      		<td class="gdtd_tb cx_demo"><div>所属部门：</div>
			                        <input type="text" name="BMMC" autocheck="true" seltarget="selBmXX" value="${params.BMMC }" class="gdtd_select_input cx_demo"/>
			                        <input id="BMXXID" name="BMXXID1" type="hidden" seltarget="selBmXX" value="${params.BMXXID }" class="gdtd_select_input cx_demo"/>
									 <img align="absmiddle" name="selBmXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
											   		onClick="selCommon('System_1', true, 'BMXXID', 'BMXXID', 'forms[1]','BMMC', 'BMMC', '');">
			      		</td>	
				</tr>
				<tr>
					
		      		<td class="gdtd_tb cx_demo"><div>状态：</div>
					
						<select id="RYZT" name="RYZT" class="gdtd_select_input cx_demo" ></select>
					</td>
		      		
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn" value="确定" >
						<input id="q_close" type="button" class="btn cx_btn" value="关闭" >
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
<script language="JavaScript">
		SelDictShow("RYZT","32","${params.RYZT}","");
</script>
</html>