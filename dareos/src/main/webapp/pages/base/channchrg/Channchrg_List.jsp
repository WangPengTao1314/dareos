
<!--  
/**
 * @module 系统管理
 * @func 经销商分管
 * @version 1.1
 * @author 张忠斌
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
	<title>经销商分管</title>
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/base/channchrg/Channchrg_List.js"></script>
	<style type="text/css">
	  .query_div_1{
		display:none;
		width:100%;
		background-color:#e0edf6;
		filter:alpha(opacity=90);
		position:absolute;
		left:0;
		top:20;
		right:100%;
		bottom:100%;
	}
	
	</style>
</head>
<body >
<div id="querydiv" class="query_div_1" >
<form id="mainForm" method="post" >
 <input type="hidden" id="selectXTYH" name="selectXTYH" value=" YHZT='启用' and IS_DRP_LEDGER='0'" />
 <input type="hidden" name="selectParams" value=" STATE in('启用','停用') " />
 <input type="hidden" name="selectContion2" value=" DELFLAG = 0 and (bmzt = '启用' or bmzt = '停用' )" />
 <input type="hidden" name="selectContion3" value=" DELFLAG = 0 and (jgzt = '启用' or jgzt = '停用' )" />
 
 <input type="hidden" name="search" id="search" value="true" />
 <table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
	<tr>
		<td class="detail2" valign="top">
			<table id="querytb" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
			    <tr height="20px"> 
				    <td class="gdtd_tb cx_demo"><div>分配维度选择：</div>
					   <input type="radio" name="dimension" id="dimension_0"  checked="checked" value="0" class="gdtd_select_input cx_demo"/> 按人员维度
					   <input type="radio" name="dimension" id="dimension_1"  value="1"  class="gdtd_select_input cx_demo"  /> 按经销商维度
					</td>
					<td class="gdtd_tb cx_demo"><div>分管结果显示：</div>
					   <input type="radio" name="notcharg" id="notcharg_0"  checked="checked" /> 显示全部
					   <input type="radio" name="notcharg" id="notcharg_1"   /> 显示未分管
					   <input type="radio" name="notcharg" id="notcharg_2"   /> 显示已分管
					</td>
					<td class="gdtd_tb cx_demo"><div>分管帐套：</div>
						<select class="gdtd_select_input cx_demo" id="LEDGER_ID" name="LEDGER_ID" ></select>
					</td>
					<td class="gdtd_tb cx_demo">
					</td>
				</tr>
				<!-- 按照人员维度的查询条件   start -->
				<tr name="tr_ry" height="50px">
					<td class="gdtd_tb cx_demo" ><div>用户编号：</div>
						<input type="hidden" name="XTYHID" id="XTYHID" json="XTYHID"    value="${params.XTYHID}" class="gdtd_select_input cx_demo">
						<input type="text" name="YHBH" id="YHBH" label="用户编号" value="${params.YHBH}" inputtype="string"  autocheck="true" mustinput="true" readonly  class="gdtd_select_input cx_demo"/>
						<img align="absmiddle" name="selXTYH" class="magnifier" src="${ctx}/styles/${theme}/images/plus/select.gif" 
										onClick="selCommon('BS_7', false, 'XTYHID', 'XTYHID', 'forms[0]','YHBH,YHM', 'YHBH,YHM', 'selectXTYH')">
					</td>
					<td class="gdtd_tb cx_demo"><div>用户名称：</div>
						<input type="text" json="YHM" name="YHM" id="YHM" label="用户名称"   value="${params.YHM}" readonly class="gdtd_select_input cx_demo"/>
					</td>
					<td class="gdtd_tb cx_demo" ><div>经销商编号：</div>
						<input id="CHANN_ID" json="CHANN_ID" name="CHANN_ID" type="hidden"
							inputtype="string" seltarget="selJGXX" autocheck="true" 
							maxlength="100" value="${params.CHANN_ID}"class="gdtd_select_input cx_demo" />
														
						<input id="CHANN_NO" json="CHANN_NO"  name="CHANN_NO" type="text" inputtype="string"
							seltarget="selJGXX" autocheck="true" maxlength="32"
							value="${params.CHANN_NO}" class="gdtd_select_input cx_demo"/>
							
						<img align="absmiddle" name="selJGXX" class="select_gif"
							src="${ctx}/styles/${theme}/images/plus/select.gif"       
							onClick="selCommon('BS_19', false, 'CHANN_ID', 'CHANN_ID', 'forms[0]','CHANN_ID,CHANN_NO,CHANN_NAME', 'CHANN_ID,CHANN_NO,CHANN_NAME', 'selectParams')">
					</td>
					<td class="gdtd_tb cx_demo"><div>经销商名称：</div>
						<input type="text" id="CHANN_NAME" json="CHANN_NAME" name="CHANN_NAME" value="${params.CHANN_NAME }" class="gdtd_select_input cx_demo"/>
					</td>
				</tr>
				<!-- 按照人员维度的查询条件   end -->
				
				<!-- 按照经销商维度的查询条件  start -->
				<tr name="tr_chann">
					<td class="gdtd_tb cx_demo" ><div>经销商编号：</div>
						<input id="CHANN_ID_1" json="CHANN_ID_1" name="CHANN_ID_1" type="hidden" value="${params.CHANN_ID_1}">
						<input id="CHANN_NO_1" json="CHANN_NO_1"  name="CHANN_NO_1" type="text" inputtype="string" autocheck="true" mustinput="true"  maxlength="32" readonly
							class="gdtd_select_input cx_demo" value="${params.CHANN_NO_1}" >
						<img align="absmiddle" name="selJGXX" class="magnifier"
							src="${ctx}/styles/${theme}/images/plus/select.gif"       
							onClick="selCommon('BS_19', false, 'CHANN_ID_1', 'CHANN_ID', 'forms[0]','CHANN_ID_1,CHANN_NO_1,CHANN_NAME_1', 'CHANN_ID,CHANN_NO,CHANN_NAME', 'selectParams')">
					</td>
					<td class="gdtd_tb cx_demo" ><div>经销商名称：</div>
						<input type="text" id="CHANN_NAME_1" json="CHANN_NAME_1" name="CHANN_NAME_1" mustinput="true"  readonly value="${params.CHANN_NAME_1}" class="gdtd_select_input cx_demo" />
					</td>
					<td class="gdtd_tb cx_demo" ><div>用户编号：</div>
					    <input type="hidden" name="XTYHID_1" id="XTYHID_1" json="XTYHID_1"    value="${params.XTYHID_1}">
						<input type="text" name="YHBH_1" id="YHBH_1" label="用户编号" value="${params.YHBH_1}" class="gdtd_select_input cx_demo" />
						<img align="absmiddle" name="selXTYH" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('BS_7', false, 'XTYHID_1', 'XTYHID', 'forms[0]','YHBH_1,YHM_1', 'YHBH,YHM', 'selectXTYH')"> 								   		
					</td>
					<td class="gdtd_tb cx_demo" ><div>用户名称：</div>
						<input type="text" json="YHM_1" name="YHM_1" id="YHM_1" label="用户名称"   value="${params.YHM_1}" class="gdtd_select_input cx_demo" />
					</td>
					
				</tr><%-- 
				<tr name="tr_chann">
					
				    <td nowrap align="right" class="detail_label">机构名称：</td>
		      		<td nowrap class="detail_content">
                        <input type="text" json="JGMC" name="JGMC" seltarget="selJGXX" value="${params.JGMC }" />
                        <input id="JGXXID" name="JGXXID" type="hidden" seltarget="selJGXX" value="${params.JGXXID }" />
						 <img align="absmiddle" name="selJGXX" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('System_2', false, 'JGXXID', 'JGXXID', 'forms[0]','JGMC', 'JGMC', 'selectContion3');">                        
		      		</td>
		      		<td nowrap align="right" class="detail_label">部门名称：</td>
		      		<td nowrap class="detail_content">
                        <input type="text" id="BMMC" name="BMMC" seltarget="selBmXX" value="${params.BMMC }" />
                        <input id="BMXXID" name="BMXXID" type="hidden" seltarget="selBmXX" value="${params.BMXXID }" />
						 <img align="absmiddle" name="selBmXX" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/select.gif" 
								   		onClick="selCommon('System_1', false, 'BMXXID', 'BMXXID', 'forms[0]','BMMC', 'BMMC', 'selectContion2');">
		      		</td>	
		      		<td width="10%" align="right" class="detail_label"></td>
					<td width="15%" class="detail_content"> </td>
		      		<td width="10%" align="right" class="detail_label"></td>
					<td width="15%" class="detail_content"></td>				
				</tr> --%>
				<!-- 按照经销商维度的查询条件  end -->
				
			</table>
		</td>
	</tr>
</table>
</form>
<table style="width: 100%">
	<tr>
					<td colspan="10" align="center" valign="middle" style="background-color:rgb(255, 255, 255);">
					   <c:if test="${pvg.PVG_BWS eq 1 }">
						   <button class="img_input" >
				                <label for='query'>
				                    <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
				                    <input id="query" type="button" class="btn" value="查询">	
				                </label>
				           </button>
						</c:if>
						<c:if test="${pvg.PVG_EDIT eq 1 }">
							<button class="img_input" >
					                <label for='save'>
					                    <img src="${ctx}/styles/${theme}/images/icon/baocun.png"  class="icon_font">
					                    <input id="save" type="button" class="btn" value="保存" >
					                </label>
					           </button>
						</c:if>
					</td>
				</tr>
</table>
</div>
</body>
<script language="JavaScript">
	SelDictShow("LEDGER_ID","BS_212","${rst.LEDGER_ID}"," XTYHID = '${XTYHID}' ");
	SelDictShow("CHANN_TYPE", "171", '${params.CHANN_TYPE}', "");
    SelDictShow("CHAA_LVL", "169", '${params.CHAA_LVL}', "");
</script>
</html>
