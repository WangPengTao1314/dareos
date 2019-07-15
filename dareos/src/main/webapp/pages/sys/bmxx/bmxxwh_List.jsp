<!-- 
 *@module 系统管理
 *@func 部门信息
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
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/sys/bmxx/bmxxwh_List.js"></script>
</head>
<body >
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20">
		<table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td>当前位置：系统管理&gt;&gt;基础信息&gt;&gt;部门信息</td>
			<td width="50" align="right"></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20px" valign="top">
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
				<tr>
				   <td nowrap>
				   
				  <c:if test="${qxcom.XT0010402 eq 1}">
				  <button class="img_input addbtn" >
				   	<label for='add'>
					   	<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
					   <input id="add" type="button" class="btn add" value="新增">
				   	</label>
				  </button>
				  <!--  <input id="add" type="button" class="btn" value="新增(N)" title="Alt+N" accesskey="N">  -->
				  
				  <button class="img_input" >
				   	<label for='modify'>
					   	<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
					   <input id="modify" type="button" class="btn" value="修改">
				   	</label>
				  </button>
			   	  <!-- <input id="modify" type="button" class="btn" value="修改(U)" title="Alt+U" accesskey="U">  -->
				  </c:if>
				  <c:if test="${qxcom.XT0010403 eq 1}">
				    <!-- <input id="delete" type="button" class="btn" value="删除(R)"> -->
				    <button class="del_input" >
		                <label for='delete'>
		                    <img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
		                    <input id="delete" type="button" class="del_btn" value="删除" >
		                </label>
		           </button>
				  </c:if>
				  <c:if test="${qxcom.XT0010404 eq 1}">
				    <!-- <input id="start" type="button" class="btn" value="启用(Q)" title="Alt+Q" accesskey="Q">	
				   	<input id="stop" type="button" class="btn" value="停用(T)" title="Alt+T" accesskey="T">	 -->
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
				  <c:if test="${qxcom.XT0010401 eq 1}">
				    <!-- <input id="query" type="button" class="btn" value="查询(F)" title="Alt+F" accesskey="F"> -->
				    <button class="img_input" >
			                <label for='query'>
			                    <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
			                    <input id="query" type="button" class="btn" value="查询">	
			                </label>
			           </button>
				  </c:if>
				   	 <%--<input id="personal" type="button" class="btn" value="个性化设置" >--%>		
				</td>
				<td>
				</td>
				</tr>
			</table>
	</td>
</tr>
<tr>
	<td valign="top">
		<div class="lst_area">
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="U.BMBH" >部门编号</th>
					<th nowrap align="center" dbname="U.BMMC" >部门名称</th>
					<th nowrap align="center" dbname="U.BMJC" >部门简称</th>
					<!-- <th nowrap align="center" dbname="SJBM" >上级部门编号</th> -->
					<th nowrap align="center" dbname="K.BMMC" >上级部门名称</th>
					<!-- <th nowrap align="center" dbname="JGXXID" >所属机构编号</th> -->
					<th nowrap align="center" dbname="JGMC" >所属机构名称</th>
					<th nowrap align="center" dbname="U.DH" >电话</th>
					<th nowrap align="center" dbname="U.BMZT" >状态</th>
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
				
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" id="tr${rr}" ondblclick="parent.gotoBottomPage()"  onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%" align='center' >
							<div class="radio_add">
   								 <input type="radio" style="height:12px;valign:middle"  name="eid" id="eid${rr}" value="${rst.BMXXID}" />
   								 <label for="radio_add"></label>
							</div>
						</td>
						
						<td align="center">${rst.BMBH }&nbsp;</td>
						<td align="center">${rst.BMMC }&nbsp;</td>
						<td align="center">${rst.BMJC}&nbsp;</td>
						<!-- <td align="center">${rst.SJBM }&nbsp;</td> -->
						<td align="center">${rst.SJBM }&nbsp;</td>
						<!-- <td align="center">${rst.JGXXID }&nbsp;</td> -->
						<td align="center">${rst.JGMC }&nbsp;</td>
						<td align="center">${rst.DH}&nbsp;</td>
						<td nowrap align="center" id="state${rst.BMXXID}" value="${rst.BMZT}">${rst.BMZT}&nbsp;</td>
						
						<script type="text/javascript">
						   $("#tr${rr}").click(function(){
						      var val = $.trim($("#state${rst.BMXXID}").text());
						      selectThis(this);
						      setSelEid(document.getElementById('eid${rr}'));
						      changeButton(val);
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
<input name="conJGXX"  type="hidden" value=" JGZT in ('启用','停用')">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td  class="gdtd_tb cx_demo"><div>部门编号：</div>
				
						<input id="BMBH" name="BMBH" type="text" seltarget="selJGXX" value="${params.BMBH }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
						onClick="selCommon('System_9', false, 'BMXXID', 'BMXXID', 'forms[1]','BMXXID,BMBH,BMMC,BMJC', 'BMXXID,BMBH,BMMC,BMJC', '')">
					</td>
					<td class="gdtd_tb cx_demo"><div>部门名称：</div>
					
						<input id="BMXXID" name="BMXXID" type="hidden" seltarget="selJGXX" value="${params.BMXXID }" class="gdtd_select_input cx_demo">
						<input  id="BMMC" name="BMMC" type="text" seltarget="selJGXX" value="${params.BMMC }" class="gdtd_select_input cx_demo">
						
					</td>
					<td class="gdtd_tb cx_demo"><div>部门简称：</div>
						<input id="BMJC" name="BMJC" type="text" value="${params.BMJC }" class="gdtd_select_input cx_demo">
					</td>
					
					<td class="gdtd_tb cx_demo"><div>所属机构：</div>
					
					    <input name="JG" type="hidden" value=" jgzt='启用' ">
						<input id="JGXXID" name="JGXXID"  type="hidden" seltarget="selJG" value="${params.JGXXID }" class="gdtd_select_input cx_demo">
						<input id="JGMC" name="JGMC" type="text" seltarget="selJG" value="${params.JGMC }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selJG" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
						onClick="selCommon('System_2',false,'JGXXID','JGXXID','forms[0]','JGMC','JGMC')">
				   </td>                                                                                                                    
				</tr>
				<tr>
					
					<td class="gdtd_tb cx_demo"><div>上级部门</div>
					
						<input name="SJBMMC" type="text" seltarget="selJGXX" value="${params.SJBMMC }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" onClick="selCommon('System_9', true, 'SJBMMC', 'BMMC', 'forms[0]','SJBMMC', 'BMMC', '')">
					</td>
				
					<td class="gdtd_tb cx_demo"><div>状态：</div>
					
						<select id="BMZT" name="BMZT"  class="gdtd_select_input cx_demo">
					    </select>
					</td>
					
				</tr>
				<tr>
					<td colspan="8" align="center" valign="middle">
						<input id="q_search" type="button" class="btn cx_btn" value="确定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关闭">&nbsp;&nbsp;
						<input  type="reset" class="btn cx_btn" value="重置" >
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</div>
<script language="JavaScript">  
		SelDictShow("BMZT","32","${params.BMZT }","");
</script>
</body>
</html>
