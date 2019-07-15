<!--
/*
  *@module 系统管理
  *@fuc 系统角色主表列表
  *@version 1.1
  *@author 唐赟
  */
 -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>系统角色信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/sys/xtjs/xtjs_List.js"></script>
</head>
<body>
<table width="99.9%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20px">
		<table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td>当前位置：系统管理&gt;&gt;权限管理&gt;&gt;角色信息维护</td>
			<td width="50" align="right"><input type="hidden" id="selRowIdxId" value="">&nbsp;</td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20">
		<div class="tablayer button_cls" style="padding: 10px 0px 10px 5px">
			<!-- <table cellSpacing=0 cellPadding=0 border=0 width="100%"> -->
			<table  cellSpacing=0 cellPadding=0 border=0 width="100%" style="border:0px">
				<tr>
				<td id="qxBtnTb" nowrap>
			
			   	    <c:if test="${qxcom.XT0010702 eq 1 }">
			   	       <button class="img_input addbtn">
							<label for='add'>
								<img class="icon_font" src="${ctx}/styles/${theme}/images/icon/xinzeng.png">
								<input type="button" id="add" class="btn add" value="新增" >
							</label>
						</button>
														
						
						<button class="img_input">
							<label for='modify'>
								<img class="icon_font" src="${ctx}/styles/${theme}/images/icon/xiugai.png">
								<input type="button" id="modify" class="btn" value="修改" >
							</label>
						</button>
			   	    </c:if>	
			   	    <c:if test="${qxcom.XT0010703 eq 1 }">
			   	        <button class="del_input">
										<label for='delete'>
											<img class="icon_font" src="${ctx}/styles/${theme}/images/icon/shanchu.png">
											<input type="button" id="delete" class="del_btn" value="删除" >
										</label>
									</button>	
			   	       
			   	    </c:if>		 
			   	    
			   	    <c:if test="${qxcom.XT0010701 eq 1 }">
			   	         <button class="img_input">
										<label for='query'>
											<img class="icon_font" src="${ctx}/styles/${theme}/images/icon/chaxun.png">
											<input type="button" id="query" class="btn" value="查询" >
										</label>
									</button>
			   	    </c:if>
			   	    
			   	    <c:if test="${qxcom.XT0010704 eq 1 }">
			   	       <button class="img_input">
										<label for='begin'>
											<img class="icon_font" src="${ctx}/styles/${theme}/images/icon/qiyong.png">
											<input type="button" id="begin" class="btn" value="启用" >
										</label>
									</button>		
									<button class="img_input">
										<label for='end'>
											<img class="icon_font" src="${ctx}/styles/${theme}/images/icon/tingyong.png">
											<input type="button" id="end" class="btn" value="停用" >
										</label>
									</button>
			   	    </c:if>
			   	    <c:if test="${qxcom.XT0010705 eq 1 }">
			   	       <input id="sysRight" type="button" class="btn" value="角色权限" >
			   	       <input id="jsyh" type="button" class="btn" value="角色用户">
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
					<th nowrap align="center" dbname="JSBH" >角色编号</th>
					<th nowrap align="center" dbname="JSMC" >角色名称</th>
					<th nowrap align="center" dbname="JSSM" >角色说明</th>
					<th nowrap align="center" dbname="STATE" >状态</th>																														
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)"  onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()" onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));changeButton('${rst.STATE}')">
						<td width="1%"align='center' >
						<div class="radio_add">
	<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.XTJSID}"/>
	<label for="radio_add"></label>
</div>
						
							
						</td>
						<td align="center">${rst.JSBH }&nbsp;</td>
						<td align="center">${rst.JSMC}&nbsp;</td>
						<td align="center">${rst.JSSM}&nbsp;</td>
						<td align="center" id="state${rst.XTJSID}" value="${rst.STATE}">${rst.STATE}&nbsp;</td>
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
<!-- 查询条件 -->
<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#">
<input id='selcondition' type='hidden' name='selcondition' value="(yhzt='启用' or yhzt='停用')" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo"><div>角色编号：</div>
					    <input type="hidden" id="JSBHZT" value="state='启用'" class="gdtd_select_input cx_demo">
						<input name="JSBH" type="text" seltarget="selJSBH" value="${params.JSBH }" class="gdtd_select_input cx_demo">
						<img align="absmiddle"  name="selJSBH" class="select_gif"
							src="${ctx}/styles/${theme}/images/plus/select.gif"
							onClick="selCommon('System_4', false, 'JSBH', 'XTJSID', 'forms[1]','JSBH,JSMC', 'JSBH,JSMC', '')">
					</td>
					<td class="gdtd_tb cx_demo"><div>角色名称：</div>
						<input name="JSMC" type="text" seltarget="selJSBH" value="${params.JSMC }" class="gdtd_select_input cx_demo">
					</td>
					<td class="gdtd_tb cx_demo"><div>状态：</div>
					
						<select id="YHZT" name="YHZT" class="gdtd_select_input cx_demo">
						</select>									
					</td>
					<td class="gdtd_tb cx_demo"><div>用户编号：</div>
					
						<input name="YHBH" type="text"  seltarget="selYH" value="${params.YHBH }" class="gdtd_select_input cx_demo">
						<img align="absmiddle"  name="selYH" class="select_gif"
							src="${ctx}/styles/${theme}/images/plus/select.gif"
							onClick="selCommon('4', false, 'YHBH', 'XTYHID', 'forms[1]','YHBH,YHM', 'YHBH,YHM', 'selcondition')">
					</td>
				</tr>
				<tr>
<!--					<td width="10%" align="right" class="detail_label">用户名称：</td>-->
<!--					<td width="15%" class="detail_content">-->
<!--						<input name="YHM" type="text" seltarget="selYH" value="${params.YHM }">-->
<!--					</td>-->
					<td class="gdtd_tb cx_demo"><div>人员姓名：</div>
					
						<input name="XM" type="text" seltarget="selXM" value="${params.XM }" class="gdtd_select_input cx_demo">
					</td>
					
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle" >
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
<script type="text/javascript">
	   SelDictShow("YHZT","32","${params.YHZT}","");	
</script>
</html>
