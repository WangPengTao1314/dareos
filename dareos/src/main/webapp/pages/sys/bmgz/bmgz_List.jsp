<!-- 
 *@module 基础资料
 *@func 维护编码规则
 *@version 1.1
 *@author 孙炜
 *  -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>编码规则维护列表页面</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"/>
	<script type=text/javascript src="${ctx}/pages/sys/bmgz/bmgz_List.js"></script>
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
</head>

<body>
<table  width="99.8%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20px" valign="top">
	   <table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td><span id="pageTitle">当前位置：系统管理&gt;&gt;;基础信息 &gt;&gt;编码规则维护</span></td>
			<td width="50" align="right"></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20px" valign="top">
	<div class="tablayer" style="margin-left:3px; border-bottom:1px solid #d3d3d3; padding-bottom:3px; margin-bottom:3px;">
		<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%">
			<tr>
			   <td nowrap>
				  <c:if test="${qxcom.XT0010202 eq 1}"> 
				    
			   		
			   		<button class="img_input addbtn" >
						<label for='add'>
							<img src="${ctx}/styles/${theme}/images/icon/xinzeng.png"  class="icon_font">
							<input type="button" id="add" class="btn add" value="新增">
	 					</label>
					</button>
			   		<button class="img_input" >
						<label for='modify'>
							<img src="${ctx}/styles/${theme}/images/icon/xiugai.png"  class="icon_font">
							<input type="button" id="modify" class="btn" value="修改">
	 					</label>
					</button>
			   		
			   		
				  </c:if>
				  <c:if test="${qxcom.XT0010203 eq 1}"> 
				    
				    <button class="del_input" >
						<label for='delete'>
							<img src="${ctx}/styles/${theme}/images/icon/shanchu.png"  class="icon_font">
							<input type="button" id="delete" class="del_btn" value="删除">
	 					</label>
					</button>
				  </c:if>
				  <c:if test="${qxcom.XT0010201 eq 1}">
				    
				    <button class="img_input" >
						<label for='query'>
							<img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
							<input type="button" id="query" class="btn" value="查询">
	 					</label>
					</button>
				  </c:if>
				  <c:if test="${qxcom.XT0010204 eq 1}">
				    
				   	
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
				  <!-- <input id="personal" type="button" class="btn" value="个性化设置"> -->
				  
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
			<tr  class="fixedRow">
				<th nowrap align="center" width="1%" ></th>
				<th nowrap align="center" dbname="BMBH">编码编号</th>
				<th nowrap align="center" dbname="BMDX">编码对象</th>
				<th nowrap align="center" dbname="GZMC">规则名称</th>
				<th nowrap align="center" dbname="ZCD">总长度</th>
				<th nowrap align="center" dbname="STATE">状态</th>
				
			</tr>
			<c:forEach items="${page.result}" var="rst" varStatus="row">
			<c:set var="r" value="${row.count % 2}"></c:set>
			<c:set var="rr" value="${row.count}"></c:set>
			<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()" id="tr${rr}">
				<td  align='center' >
				<div class="radio_add">
	<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.BMGZID}">
	<label for="radio_add"></label>
</div>
				
					
				</td>
				<td  align="center">${rst.BMBH}&nbsp;</td>
				<td  align="center">${rst.BMDX}&nbsp;</td>
				<td  align="center">${rst.GZMC}&nbsp;</td>
				<td  align="center" id="zcd${rst.BMGZID}">${rst.ZCD}&nbsp;</td>
				<td  align="center" id="state${rst.BMGZID}" value="${rst.STATE}">${rst.STATE}&nbsp;</td>
				
				<script type="text/javascript">
				   $("#tr${rr}").click(function(){
				      var val = $.trim($("#state${rst.BMGZID}").text());
				      selectThis(this);
				      setSelEid(document.getElementById('eid${rr}'));
				      changeButton(val);
				   });
			    </script>
			</tr>
			</c:forEach>
			<c:if test="${empty page.result}">
			<tr>
				<td height="25" colspan="13" align="center" class="lst_empty">&nbsp;无相关信息&nbsp;</td>
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
<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#">
<input type="hidden" name="condition1" id="condition1" value=" RYZT = '启用' and delflag=0">
<input type="hidden" name="condition" id="condition" value=" delflag=0">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
				    <td class="gdtd_tb cx_demo"><div>编码编号：</div>
					
						<input name="BMBH" type="text" seltarget="selBM" value="${params.BMBH }" class="gdtd_select_input cx_demo">
						<input type="hidden" name="BMGZID" seltarget="selBM" value="${params.BMGZID }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selBM" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
						   onClick="selCommon('System_14', false, 'BMBH', 'BMBH', 'forms[1]','BMBH,BMDX,GZMC', 'BMBH,BMDX,GZMC', 'condition')">
					</td>
					<td class="gdtd_tb cx_demo"><div>编码对象：</div>
					
						<input name="BMDX" type="text" value="${params.BMDX }" class="gdtd_select_input cx_demo">
					</td>
					<td class="gdtd_tb cx_demo"><div>规则名称：</div>
				
						<input name="GZMC" type="text" value="${params.GZMC }" class="gdtd_select_input cx_demo">
					</td>	
					<td class="gdtd_tb cx_demo"><div>总长度：</div>
					
						<input name="ZCD" type="text" value="${params.ZCD }" class="gdtd_select_input cx_demo">
					</td>	
				</tr>
				<tr>
				    <td class="gdtd_tb cx_demo"><div>创建人：</div>
					
						<input name="CREATER" type="text" seltarget="selRYXX" value="${params.CREATER }" class="gdtd_select_input cx_demo">
						<input name="creId" id="creId" type="hidden" seltarget="selRYXX" value="${params.creId }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selRYXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
						   onClick="selCommon('System_3', false, 'creId', 'XTYHID', 'forms[1]','CREATER', 'YHBH', '')">
					</td>
					<td class="gdtd_tb cx_demo"><div>状态：</div>
				
						<select id="STATE" name="STATE" class="gdtd_select_input cx_demo" >
					    </select>
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
<script language="JavaScript">
  
		SelDictShow("STATE","32","${params.STATE }","");		

</script>

