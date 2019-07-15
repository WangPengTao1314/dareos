

<!--  
/**
 * @module 系统管理
 * @func 经销商信息
 * @version 1.1
 * @author  刘曰刚
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
	<title>经销商列表</title>
	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script type="text/javascript" src="${ctx}/pages/base/chann/Chann_List.js"></script>
	<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
</head>
<body >
<table width="99.5%" height="100%" border="0" cellspacing="0" cellpadding="0" class="panel">
<!-- <tr>
	<td height="20px" valign="top">
	   <table width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td height="20px">&nbsp;&nbsp;当前位置：系统管理&gt;&gt;基础信息&gt;&gt;经销商信息</td>
			<td width="50" align="right"></td>
		</tr>
	  </table>  
	</td>
</tr> -->
<tr>
	<td height="20px" valign="top" >
	   <div class="tablayer" >
			<table id="qxBtnTb" cellpadding="0" cellspacing="10" border=0 width="100%" >
				<tr >
				   <td nowrap >
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
						                    <input id="modify" type="button" class="btn" value="修改">
						                </label>
						           </button>
						           <button class="img_input" >
						                <label for="deliverAddr">
						                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
						                    <input id="deliverAddr"  type="button" class="btn" value="收货地址"/>
						                </label>
						             </button>
						           <button class="img_input" >
						                <label for="channChrg">
						                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
						                    <input id="channChrg"  type="button" class="btn" value="经销商分管"/>
						                </label>
						             </button>
						             <button class="img_input" >
						                <label for="ledgerChrg">
						                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
						                    <input id="ledgerChrg"  type="button" class="btn" value="帐套分管"/>
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
								<c:if test="${pvg.PVG_BWS eq 1 }">
									<button class="img_input" >
						                <label for='query'>
						                    <img src="${ctx}/styles/${theme}/images/icon/chaxun.png"  class="icon_font">
						                    <input id="query" type="button" class="btn" value="查询">	
						                </label>
						           </button>
						           <button class="img_input" >
						                <label for='expdata'>
						                    <img src="${ctx}/styles/${theme}/images/icon/daochu.png"  class="icon_font">
						                    <input id="expdata" type="button" class="btn" value="导出" >
						                </label>
						           </button>	
									
								</c:if>
								<c:if test="${pvg.PVG_BATCH eq 1}">
										<button class="img_input" >
						                <label onclick="orpenWindow()">
						                    <img src="${ctx}/styles/${theme}/images/icon/caozuo.png"  class="icon_font">
						                    <input  type="button" class="btn" value="批量维护区域经理"/>
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
			<table id="ordertb" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst">
				<tr class="fixedRow">
					<th width="1%"></th>
					<th nowrap align="center" dbname="CHANN_NO">经销商编号</th>
					<th nowrap align="center" dbname="CHANN_NAME">经销商名称</th>
					<!-- <th nowrap align="center" dbname="CHANN_TYPE">经销商类型</th> -->
					<th nowrap align="center" dbname="CHAA_LVL">经销商级别</th>
					<th nowrap align="center" dbname="CHANN_NAME_P">上级经销商</th>
					<th nowrap align="center" dbname="AREA_NO">区域编号</th>
					<th nowrap align="center" dbname="AREA_NAME">区域名称</th>
					<th nowrap align="center" dbname="CRE_NAME">制单人</th>
					<th nowrap align="center" dbname="CRE_TIME">制单时间</th>
					<th nowrap align="center" dbname="DEPT_NAME">制单部门</th>
					<th nowrap align="center" dbname="STATE">状态</th>
				</tr>
				<c:forEach items="${page.result}" var="rst" varStatus="row">
					<c:set var="r" value="${row.count % 2}"></c:set>
					<c:set var="rr" value="${row.count}"></c:set>
					<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" ondblclick="parent.gotoBottomPage()"  onclick="selectThis(this);setSelEid(document.getElementById('eid${rr}'));">
						<td width="1%"align='center' >
							<div class="radio_add">
								<input type="radio" style="valign:middle" name="eid" id="eid${rr}" value="${rst.CHANN_ID}"/>
								<label for="radio_add"></label>
							</div>
						</td>
                        <td nowrap align="center">${rst.CHANN_NO}&nbsp;</td>
                        <td nowrap align="center">${rst.CHANN_NAME}&nbsp;</td>
                        <%-- <td nowrap align="center" >${rst.CHANN_TYPE}&nbsp;</td> --%>
                        <td nowrap align="center" >${rst.CHAA_LVL}&nbsp;</td>
                        <td nowrap align="center">${rst.CHANN_NAME_P}&nbsp;</td>
                        <td nowrap align="center">${rst.AREA_NO}&nbsp;</td>
                        <td nowrap align="center">${rst.AREA_NAME}&nbsp;</td>
                        <td nowrap align="center">${rst.CRE_NAME}&nbsp;</td>
                        <td nowrap align="center">${rst.CRE_TIME}&nbsp;</td>
                        <td nowrap align="center">${rst.DEPT_NAME}&nbsp;</td>
                        <td nowrap json='STATE' align="center">${rst.STATE}&nbsp;</td>
                        <input type="hidden" id="state${rst.CHANN_ID}" value="${rst.STATE}"/>
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
					${paramCover.unCoveredForbidInputs}
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
<div id="querydiv" class="query_div" >
<form id="queryForm" method="post" action="#">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail cx_table">
	<input type="hidden" name="selectParams" value=" STATE in( '启用')  and DEL_FLAG='0'">
	<input id="selcondition" type="hidden" name="selcondition" value=" 1=1 " />
	<input type="hidden" name="selectParamsArea" value="STATE in( '启用','停用') and DEL_FLAG='0' and AREA_NAME_P is null ">
	<input type="hidden" id="selectAreaManager" name="selectAreaManager" value=""/>
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr>
					<td class="gdtd_tb cx_demo"><div>经销商编号：</div>
						<input id="CHANN_ID" json="CHANN_ID" name="CHANN_ID" type="hidden"
							inputtype="string" seltarget="selJGXX" autocheck="true"
							 value="${params.CHANN_ID}" class="gdtd_select_input cx_demo" >
														
						<input id="CHANN_NO" json="CHANN_NO"  name="CHANN_NO" type="text" inputtype="string"
							seltarget="selJGXX" autocheck="true" 
							value="${params.CHANN_NO}"  class="gdtd_select_input cx_demo" >
							
						<img align="absmiddle" name="selJGXX" class="select_gif"
							src="${ctx}/styles/${theme}/images/plus/select.gif"       
							onClick="selCommon('BS_19', true, 'CHANN_ID', 'CHANN_ID', 'forms[1]','CHANN_ID,CHANN_NO,CHANN_NAME', 'CHANN_ID,CHANN_NO,CHANN_NAME', 'selectParams')">
					</td>
					<td class="gdtd_tb cx_demo"><div>经销商名称：</div>
					
						<input type="text" json="CHANN_NAME" name="CHANN_NAME" id="CHANN_NAME" value="${params.CHANN_NAME }" class="gdtd_select_input cx_demo"/>
					</td>
					<td class="gdtd_tb cx_demo"><div>经销商类型：</div>
					
						<select name="CHANN_TYPE" id="CHANN_TYPE" json="CHANN_TYPE" class="gdtd_select_input cx_demo"></select>
					</td>
					<td class="gdtd_tb cx_demo"><div>经销商级别：</div>
					
						<select label="经销商级别" name="CHAA_LVL"
							valueType="chinese:false" inputtype="string"
							id="CHAA_LVL" json="CHAA_LVL"
							value="${params.CHAA_LVL}" class="gdtd_select_input cx_demo">
						</select>
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb cx_demo"><div>上级经销商编号：</div>
				
						<input id="CHANN_ID_P" json="CHANN_ID_P" name="CHANN_ID_P" type="hidden"
														inputtype="string"  autocheck="true"
														maxlength="100" value="${params.CHANN_ID_P}" class="gdtd_select_input cx_demo">
														
													<input id="CHANN_NO_P" json="CHANN_NO_P"  name="CHANN_NO_P" type="text" inputtype="string"
														seltarget="selJGXX" autocheck="true" maxlength="30"
														value="${params.CHANN_NO_P}" class="gdtd_select_input cx_demo">
														
													<img align="absmiddle" name="selJGXX" class="select_gif"
														src="${ctx}/styles/${theme}/images/plus/select.gif"       
														onClick="selCommon('BS_19', true, 'CHANN_ID_P', 'CHANN_ID', 'forms[1]','CHANN_ID_P,CHANN_NO_P,CHANN_NAME_P', 'CHANN_ID,CHANN_NO,CHANN_NAME', 'selectParams')">
					</td>
					<td class="gdtd_tb cx_demo"><div>上级经销商名称：</div>
					
						<input id="CHANN_NAME_P" name="CHANN_NAME_P" type="text" inputtype="string"
														 label="上级经销商名称"  json="CHANN_NAME_P" value="${params.CHANN_NAME_P }" class="gdtd_select_input cx_demo">
					</td>
					<td class="gdtd_tb cx_demo"><div>区域编号：</div>
					
						<input id="AREA_ID" json="AREA_ID" name="AREA_ID" type="hidden"
							inputtype="string" autocheck="true" value="${params.AREA_ID}" class="gdtd_select_input cx_demo">
							
						<input id="AREA_NO" json="AREA_NO"  name="AREA_NO" type="text" inputtype="string"
							  value="${params.AREA_NO}"  lable="区域编号" class="gdtd_select_input cx_demo">
							
						<img align="absmiddle" class="select_gif"
							src="${ctx}/styles/${theme}/images/plus/select.gif" maxlength="30"
							onClick="selCommon('BS_18', true, 'AREA_ID', 'AREA_ID', 'forms[1]','AREA_ID,AREA_NO,AREA_NAME', 'AREA_ID,AREA_NO,AREA_NAME', 'selectParams')">
					</td>
					<td class="gdtd_tb cx_demo"><div>区域名称：</div>
					
						<input id="AREA_NAME" name="AREA_NAME" type="text" inputtype="string" maxlength="50"
							 label="区域名称"  json="AREA_NAME" value="${params.AREA_NAME }" class="gdtd_select_input cx_demo">
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb cx_demo"><div>省份：</div>
					 
	   					<input type="text" id="PROV" name="PROV"    value="${params.PROV}" class="gdtd_select_input cx_demo"/>
					    <img align="absmiddle" name="selSHIP" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif"       
							onClick="selCommon('BS_113', true, 'PROV', 'PROV', 'forms[1]','PROV,CITY,COUNTY', 'PROV,CITY,COUNTY', '')">
					 </td>
					<td class="gdtd_tb cx_demo"><div>城市：</div>
					
						<input name="CITY" id="CITY" json="CITY" type="text" value="${params.CITY}" class="gdtd_select_input cx_demo">
					</td>
					
					<td class="gdtd_tb cx_demo"><div>区县：</div>
					
						<input name="COUNTY" id="COUNTY" json="COUNTY" type="text" value="${params.COUNTY}" class="gdtd_select_input cx_demo">
					</td>
				</tr>
				<tr>
				    
				    <td class="gdtd_tb cx_demo"><div>制单时间从：</div>
					
						<input type="text" class="gdtd_select_input cx_demo" json="BEGIN_CRE_TIME" id="BEGIN_CRE_TIME"name="BEGIN_CRE_TIME" value="${params.BEGIN_CRE_TIME}" autocheck="true" inputtype="string" label="制单时间从"  mustinput="true" onclick="SelectTime();" readonly />&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectTime(BEGIN_CRE_TIME);"/>
					</td>
					<td class="gdtd_tb cx_demo"><div>制单时间到：</div>
					
						<input type="text" class="gdtd_select_input cx_demo" json="END_CRE_TIME" id="END_CRE_TIME" value="${params.END_CRE_TIME }" name="END_CRE_TIME" autocheck="true" inputtype="string" label="制单时间到"  mustinput="true" onclick="SelectTime();" readonly />&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectTime(END_CRE_TIME);"/>
					</td>
					<td class="gdtd_tb cx_demo"><div>区域经理：</div>
					
					    <input name="AREA_MANAGE_NAME" id="AREA_MANAGE_NAME" json="AREA_MANAGE_NAME" type="text"  value="${params.AREA_MANAGE_NAME}" class="gdtd_select_input cx_demo">
					    <img align="absmiddle" name="selSHIP" class="select_gif" src="${ctx}/styles/${theme}/images/plus/select.gif"       
									onClick="selCommon('BS_167', true, 'AREA_MANAGE_NAME', 'AREA_MANAGE_NAME', 'forms[1]','AREA_MANAGE_NAME', 'AREA_MANAGE_NAME', 'selectAreaManager')">						   
					</td>
				    <td class="gdtd_tb cx_demo"><div>状态：</div>
					
						<select name=STATE id="STATE" json="STATE" class="gdtd_select_input cx_demo"></select>
					</td>
				</tr>
				
				<tr>
				    <td class="gdtd_tb cx_demo"><div>加盟日期从：</div>
					
						<input type="text" json="BEGIN_JOIN_DATE" class="gdtd_select_input cx_demo" id="BEGIN_JOIN_DATE" name="BEGIN_JOIN_DATE" value="${params.BEGIN_JOIN_DATE}" autocheck="true" inputtype="string" label="加盟日期从"  mustinput="true" onclick="SelectDate();" readonly />&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(BEGIN_JOIN_DATE);"/>
					</td>
					<td class="gdtd_tb cx_demo"><div>加盟日期到：</div>
					
						<input type="text" json="END_JOIN_DATE" id="END_JOIN_DATE" value="${params.END_JOIN_DATE }" name="END_JOIN_DATE" autocheck="true" inputtype="string" label="加盟日期到"  mustinput="true" onclick="SelectDate();" readonly  class="gdtd_select_input cx_demo"/>&nbsp;
						<img align="absmiddle" class="select_gif" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(END_JOIN_DATE);"/>
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

<script language="JavaScript">
	//$(function(){
		SelDictShow("STATE","32","${params.STATE}","");
		SelDictShow("CHANN_TYPE", "171", '${params.CHANN_TYPE}', "");
		//SelDictShow("CHAA_LVL", "169", '${params.CHAA_LVL}', "");
		SelDictShow("CHAA_LVL", "BS_100", '${params.CHAA_LVL}', "");
	//});
</script>
</html>
