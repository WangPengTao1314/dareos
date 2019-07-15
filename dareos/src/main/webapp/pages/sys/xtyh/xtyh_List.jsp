<!-- /**

  *@module 系统管理

  *@fuc 系统用户列表

  *@version 1.1

  *@author 朱晓文
*/ -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>系统用户信息列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/sys/xtyh/xtyh_List.js"></script>
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
</head>
<body>
<table width="99.9%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20">
		<table width="100%" cellspacing="0" cellpadding="0" >
		<tr>
			<td>当前位置：系统管理&gt;&gt;权限管理&gt;&gt;用户信息维护</td>
			<td width="50" align="right"><br></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20" valign="top">
		<div class="tablayer button_cls" style="padding:10px 0px 10px 5px">
			<table  cellSpacing=0 cellPadding=0 border=0 width="100%">
			<tr>
				<td id="qxBtnTb" nowrap>
			   	   <c:if test="${qxcom.XT0010602 eq 1 }">
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
			   	   	
			   	   <c:if test="${qxcom.XT0010601 eq 1 }">			   	       
				       <!--  <input id="query" type="button" class="btn" value="查询(F)" title="Alt+F" accesskey="F"> -->
			   	   </c:if>
			   	   <c:if test="${qxcom.XT0010604 eq 1 }">
			   	       <button class="img_input" >
			                <label for='begin'>
			                    <img src="${ctx}/styles/${theme}/images/icon/qiyong.png"  class="icon_font">
			                    <input id="begin" type="button" class="btn" value="启用" >
			                </label>
			           </button>
					   	<button class="img_input" >
			                <label for='end'>
			                    <img src="${ctx}/styles/${theme}/images/icon/tingyong.png"  class="icon_font">
			                    <input id="end" type="button" class="btn" value="停用" >
			                </label>
			           </button>	
			   	   </c:if>
			   	       
			   	  	<button class="img_input" >
			                <label for="defaultP">
			                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
			                     <input id="defaultP" type="button" class="btn" value="设为默认密码" >
			                </label>
			           </button>	
			   	   <c:if test="${qxcom.XT0010605 eq 1 }">
			   	        <button class="img_input" >
			                <label for="userRight">
			                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
			                     <input id="userRight" type="button" class="btn" value="用户权限" >
			                </label>
			           </button>	
			           <button class="img_input" >
			                <label for="AuthorizeList">
			                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
			                     <input id="AuthorizeList" type="button" class="btn" value="查看授权信息">
			                </label>
			           </button>	
			   	   </c:if>
			   	    <c:if test="${qxcom.XT0010606 eq 1 }">
			   	        <button class="img_input" >
			                <label for="systemAuthorize">
			                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
			                     <input id="systemAuthorize" type="button" class="btn" value="系统授权" >
			                </label>
			           </button>	
			   	   </c:if>
			   	   <c:if test="${qxcom.XT0010607 eq 1 }">
<!-- 			   	     <button class="img_input" > -->
<!-- 			                <label for="stepUser()"> -->
<%-- 			                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font"> --%>
<!-- 			                     <input id="stepUser" type="button" class="btn" value="分管用户关系" onclick="openWindow();"> -->
<!-- 			                </label> -->
<!-- 			           </button>	 -->
			           <button class="img_input" >
			                <label for="ledgerChrg">
			                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
			                    <input id="ledgerChrg"  type="button" class="btn" value="帐套分管"/>
			                </label>
			             </button>
			   	    </c:if>
			   	    <c:if test="${qxcom.XT0010603 eq 1 }">
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
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" style："padding:0px">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="YHBH" >用户编号</th>
					<th nowrap align="center" dbname="YHM" >用户名称</th>
					<th nowrap align="center" dbname="RYBH" >人员编号</th>
					<th nowrap align="center" dbname="XM" >人员名称</th>
					<th nowrap align="center" dbname="BMMC" >部门名称</th>
					<th nowrap align="center" dbname="JGMC" >机构名称</th>
					<th nowrap align="center" dbname="YHLB" >用户类别</th>
					<th nowrap align="center" dbname="YHZT" >状态</th>
					<th nowrap align="center" dbname="" >是否分管所有渠道</th>																																	
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()"  onclick="selectThis(this);selectThis(this);setSelEid(document.getElementById('eid${rr}'));changeButton('${rst.YHZT}')">
						<td width="1%"align='center' >
							<div class="radio_add">
								<input type="radio" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.XTYHID}"/>
								<label for="radio_add"></label>
							</div>
						</td>
						<td align="center">${rst.YHBH }&nbsp;</td>
						<td align="center">${rst.YHM}&nbsp;</td>
						<td align="center">${rst.RYBH}&nbsp;</td>
						<td align="center">${rst.XM}&nbsp;</td>
						<td align="center">${rst.BMMC}&nbsp;</td>
						<td align="center">${rst.JGMC}&nbsp;</td>						
						<td align="center">${rst.YHLB }&nbsp;</td>
						<td align="center">${rst.YHZT }&nbsp;</td>
						<td align="center">
                          <input type="radio" id="chrg0${rr}" name="chrg${rr}" value="1" onclick="beforeChangeChrg(this,'${rst.IS_FG_ALL_CHANN}');"
                           <c:if test="${1 eq rst.IS_FG_ALL_CHANN }">checked="checked" </c:if>
                          />分管所有
                          <input type="radio" id="chrg1${rr}" name="chrg${rr}" value="0" onclick="beforeChangeChrg(this,'${rst.IS_FG_ALL_CHANN}');"
                            <c:if test="${empty rst.IS_FG_ALL_CHANN or 0 eq rst.IS_FG_ALL_CHANN }"> checked="checked"  </c:if>
                          />否 
                        </td>
					</tr>
				</c:forEach>
				<c:if test="${empty page.result}">
					<tr>
						<td height="25" colspan="10" align="center" class="lst_empty">
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
						<input type="hidden" id="selYhzt" name="selYhzt" value="${rst.YHZT}">
						<input type="hidden" id="paramUrl" name="paramUrl" value="${paramCover.coveredUrl}">
						<span id="hidinpDiv" name="hidinpDiv"></span>
						${paramCover.unCoveredForbidInputs }
					</form>
				</td>
				<td align="left">
					${page.toolbarHtml}
				</td>
			</tr>
		</table>
	</td>
	
</tr>

</table>

<div id="querydiv" class="query_div">
<form id="queryForm" method="post" action="#">
<input type="hidden" name="selectContion2" value=" DELFLAG = 0 and (bmzt = '启用' or bmzt = '停用' )" />
<input type="hidden" name="selectContion3" value=" DELFLAG = 0 and (jgzt = '启用' or jgzt = '停用' )" />

<input type="hidden" name="selectContion4" value=" ryzt = '启用'" />
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo"><div>用户编号：</div>
					
						<input name="YHBH" type="text" seltarget="selYHBH" value="${params.YHBH }" class="gdtd_select_input cx_demo">
						<img align="absmiddle" name="selYHBH" class="select_gif"
						src="${ctx}/styles/${theme}/images/plus/select.gif"
						onClick="selCommon('System_3', false, 'YHBH', 'YHBH', 'forms[1]','YHBH,YHMC', 'YHBH,YHM', '')">
					</td>
					<td class="gdtd_tb cx_demo"><div>用户名称：</div>
					
						<input name="YHMC" type="text" seltarget="selYHBH" value="${params.YHMC }" class="gdtd_select_input cx_demo">
					</td>
					<td class="gdtd_tb cx_demo"><div>人员编号：</div>
					
						<input json="RYBH" name="RYBH" type="text" seltarget="selKFXX" value="${params.RYBH }" class="gdtd_select_input cx_demo">
                        <img align="absmiddle" name="selKFXX" class="select_gif"
						src="${ctx}/styles/${theme}/images/plus/select.gif"
						onClick="selCommon('System_0', false, 'RYBH', 'RYBH', 'forms[1]','RYBH,RYMC', 'RYBH,XM', '')">														
					</td>
					<td class="gdtd_tb cx_demo"><div>人员名称：</div>
					
						<input name="RYMC" type="text" seltarget="selKFXX" value="${params.RYMC }" class="gdtd_select_input cx_demo">
					</td>
					
				</tr>
				<tr>
					
				    <td class="gdtd_tb cx_demo"><div>机构名称：</div>
		    
		                        <input type="text" json="JGMC" name="JGMC" seltarget="selJGXX" value="${params.JGMC }" class="gdtd_select_input cx_demo" />
		                        <input id="JGXXID" name="JGXXID" type="hidden" seltarget="selJGXX" value="${params.JGXXID }" class="gdtd_select_input cx_demo" />
								 <img align="absmiddle" name="selJGXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
										   		onClick="selCommon('System_2', false, 'JGXXID', 'JGXXID', 'forms[1]','JGMC', 'JGMC', 'selectContion3');">                        
		      		</td>
		      		<td class="gdtd_tb cx_demo"><div>部门名称：</div>
		      	
		                        <input type="text" name="BMMC" seltarget="selBmXX" value="${params.BMMC }" class="gdtd_select_input cx_demo" />
		                        <input id="BMXXID" name="BMXXID" type="hidden" seltarget="selBmXX" value="${params.BMXXID }" class="gdtd_select_input cx_demo" />
								 <img align="absmiddle" name="selBmXX" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
										   		onClick="selCommon('System_1', false, 'BMXXID', 'BMXXID', 'forms[1]','BMMC', 'BMMC', 'selectContion2');">
		      		</td>	
		      		<td class="gdtd_tb cx_demo"><div>状态：</div>
					
						<select id="YHZT" name="YHZT"  class="gdtd_select_input cx_demo">
						</select>									
					</td>
		      				
				</tr>
				<tr>
					<td colspan="10" align="center" valign="middle" >
						<input id="q_search" type="button" class="btn cx_btn" value="确 定" >&nbsp;&nbsp;
						<input id="q_close" type="button" class="btn cx_btn" value="关 闭" >&nbsp;&nbsp;
						<input  type="reset" class="btn cx_btn" value="重置">
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
    //$(function(){
	   SelDictShow("YHZT","32","${params.YHZT}","");	
   // });
</script>
</html>
