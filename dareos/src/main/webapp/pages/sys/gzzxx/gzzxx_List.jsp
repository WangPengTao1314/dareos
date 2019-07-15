<!--
/*
  *@module 系统管理
  *@func 工作组信息
  *@version 1.1
  *@author 吴亚林
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
	<title>工作组信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/sys/gzzxx/gzzxx_List.js"></script>
</head>
<body>
<table width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20px" valign="top">
	   <table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td>当前位置：系统管理&gt;&gt;权限管理&gt;&gt;工作组信息维护</td>
			<td width="50" align="right"><input type="hidden" id="selRowIdxId" value=""></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20px">
	  <div class="tablayer" style="margin-left:3px; border-bottom:1px solid #d3d3d3; padding-bottom:3px; margin-bottom:3px;">
			<table id="qxBtnTb"cellpadding="0" cellspacing="10" border=0 width="100%">
				<tr>
				   <td  nowrap> 
			   		
				   		<c:if test="${qxcom.XT0010802 eq 1 }">
					   		
					   		
					   		<button class="img_input addbtn" >
								<label for='add'>
									<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
									<input type="button" id="add" class="btn" value="新增">
	 							</label>
							</button>
					   		<button class="img_input" >
								<label for='modify'>
									<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
									<input type="button" id="modify" class="btn add" value="修改">
	 							</label>
							</button>
					   		
				   		</c:if>
				   		<c:if test="${qxcom.XT0010803 eq 1 }">
				 
				   			<button class="del_input" >
								<label for='delete'>
									<img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
									<input type="button" id="delete" class="del_btn" value="删除">
	 							</label>
							</button>
				   		</c:if>
				   		<c:if test="${qxcom.XT0010801 eq 1 }">
				   		
				   			<button class="img_input" >
								<label for='query'>
									<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
									<input type="button" id="query" class="btn" value="查询">
	 							</label>
							</button>
				   		</c:if>
				   		<c:if test="${qxcom.XT0010804 eq 1 }">
					   		
						   	<button class="img_input" >
								<label for='start'>
									<img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
									<input type="button" id="start" class="btn" value="启用">
	 							</label>
							</button>
							<button class="img_input" >
								<label for='stop'>
									<img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font">
									<input type="button" id="stop" class="btn" value="停用">
	 							</label>
							</button>
					   	</c:if>
				   		<c:if test="${qxcom.XT0010805 eq 1 }">
					   		<input id="gzzRight" type="button" class="btn" value="工作组权限" >	
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
					<th nowrap align="center" dbname="GZZBH" >工作组编号</th>
					<th nowrap align="center" dbname="GZZMC" >工作组名称</th>
					<th nowrap align="center" dbname="GZZSM" >工作组说明</th>
					<th nowrap align="center" dbname="GZZZT" >状态</th>
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
				
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					
					<tr class="list_row${r}" onmouseover="mover(this)" ondblclick="parent.gotoBottomPage()"  onmouseout="mout(this)" id="tr${rr}"  >
						<td width="1%" align='center' >
						<div class="radio_add">
	<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.GZZXXID}"/>
	<label for="radio_add"></label>
</div>
						
							
						</td>
						
						<td align="center">${rst.GZZBH }&nbsp;<input type="hidden" id="GZZXXID${rst.GZZXXID }" value="${rst.GZZXXID }"/></td>
						<td align="center">${rst.GZZMC}&nbsp;</td>
						<td align="center">${rst.GZZSM}&nbsp;</td>
						<!-- <td align="left">${rst.GZZZT}&nbsp;</td>-->
						<td nowrap align="center" id="state${rr}" value="${rst.GZZZT}">${rst.GZZZT}&nbsp;</td>
						
						<script type="text/javascript">
						   $("#tr${rr}").click(function(){
						      selectThis(this);
						      setSelEid(document.getElementById('eid${rr}'));
						      changeButton('${rst.GZZZT}');
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
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td width="10%" align="right" class="detail_label">工作组编号：</td>
					<td width="15%" class="detail_content">
						<input name="GZZBH" type="text" seltarget="GZZBH" value="${params.GZZBH }">
						<img align="absmiddle" id="selYH" name="GZZBH" style="cursor: hand"
							src="${ctx}/styles/${theme}/images/plus/select.gif"
							onClick="selCommon('System_13', false, 'GZZBH', 'GZZXXID', 'forms[1]','GZZBH,GZZMC', 'GZZBH,GZZMC', '')">
					</td>
					<td width="10%" align="right" class="detail_label">工作组名称：</td>
					<td width="15%" class="detail_content">
						<input name="GZZMC" type="text" seltarget="GZZBH" value="${params.GZZMC }">
					</td>
					<td width="10%" align="right" class="detail_label">状态：</td>
					<td width="15%" class="detail_content">
						<select id="GZZZT" name="GZZZT" style="width:173px" >
					    </select>
					</td>
					<td width="10%" align="right" class="detail_label">用户编号：</td>
					<td width="15%" class="detail_content">
						<input name="YHBH" type="text"  seltarget="selYH" value="${params.YHBH }">
						<img align="absmiddle" id="selYH" name="selYH" style="cursor: hand"
							src="${ctx}/styles/${theme}/images/plus/select.gif"
							onClick="selCommon('4', false, 'YHBH', 'XTYHID', 'forms[1]','YHBH,YHM', 'YHBH,YHM', 'selcondition')">
					</td>
				</tr>
				<tr>
					<td width="10%" align="right" class="detail_label">用户名称：</td>
					<td width="15%" class="detail_content">
						<input name="YHM" type="text" seltarget="selMC" value="${params.YHM }">
						<img align="absmiddle" id="selMC" name="selMC" style="cursor: hand"
							src="${ctx}/styles/${theme}/images/plus/select.gif"
							onClick="selCommon('4', false, 'YHM', 'XTYHID', 'forms[1]','YHBH,YHM', 'YHBH,YHM', 'selcondition')">
					</td>
					<td width="10%" align="right" class="detail_label"></td>
					<td width="15%" class="detail_content"></td>
					<td width="10%" align="right" class="detail_label"></td>
					<td width="15%" class="detail_content"></td>
					<td width="10%" align="right" class="detail_label"></td>
					<td width="15%" class="detail_content"></td>
				</tr>				
				<tr>
					<td colspan="10" align="center" valign="middle" >
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
<script language="JavaScript">  
		SelDictShow("GZZZT","32","${params.GZZZT }","");	
</script>
</body>
</html>
