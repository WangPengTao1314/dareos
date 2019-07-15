
<!--  
/**
 * @module 系统管理
 * @func 经销商信息
 * @version 1.1
 * @author 刘曰刚
 */
-->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@page import="com.centit.commons.model.Consts"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
		<script type="text/javascript" src="${ctx}/pages/base/chann/Chann_Edit.js"></script>
		<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
		<title>经销商信息编辑</title>
	</head>
	<body class="bodycss1">
		<div style="height:100">
			<div class="buttonlayer" id="floatDiv">
				<table cellSpacing=0 cellPadding=0 border=0 width="100%">
					<tr>
						<td align="right" nowrap>
							<button class="img_input" >
					                <label for='save'>
					                    <img src="${ctx}/styles/${theme}/images/icon/baocun.png"  class="icon_font">
					                    <input id="save" type="button" class="btn" value="保存" >
					                </label>
					           </button>
							<button class="img_input" >
					                <label onclick="closeDialog()">
					                    <img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
					                    <input type="button" class="btn" value="返回"  >
					                </label>
					           </button>
						</td>
					</tr>
				</table>
			</div>

			<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<form name="form" id="mainForm">
							<input type="hidden" name="selectParams"  value=" STATE in( '启用') and DEL_FLAG='0' ">
							<input type="hidden" name="selectParamsT" value=" STATE in( '启用') and DEL_FLAG='0' AND AREA_NO_P IS NOT NULL ">
							<input type="hidden" name="selectArea" value=" RYZT='启用' and DELFLAG=0 and JGXXID='${JGXXID}' ">
							<input type="hidden" name="selectParamsC" value=" CHAA_LVL='分公司' and DEL_FLAG=0 ">
							<input name="STATE" type="hidden" value="制定" />
							<table id="jsontb" width="100%" border="0" cellpadding="0"	cellspacing="0" class="detail">
								<tr>
									<td class="detail2">
										<table width="100%"  border="0" cellpadding="0" cellspacing="0"
											class="detail3">
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													经销商编号：
												</td>
												<td width="35%" class="detail_content">
													<input id="CHANN_NO" json="CHANN_NO" name="CHANN_NO"
														type="text" autocheck="true" label="经销商编号"
														inputtype="string" mustinput="true"
														valueType="chinese:false" maxlength="30"
														value="${rst.CHANN_NO}"
														<c:if test="${rst.CHANN_NO!=NULL}">READONLY</c:if>>
												</td>
												<td width="15%" align="right" class="detail_label">
													经销商名称：
												</td>
												<td width="35%" class="detail_content">
													<input id="CHANN_NAME" json="CHANN_NAME" name="CHANN_NAME"
														type="text" autocheck="true" label="经销商名称"
														inputtype="string" mustinput="true" maxlength="100"
														value="${rst.CHANN_NAME}"  <c:if test="${rst.CHANN_NO!=NULL}">READONLY</c:if>>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													上级经销商编号：
												</td>
												<td width="35%" class="detail_content">
													<input type="hidden" id="CHANN_ID_P" name="CHANN_ID_P" inputtype="string" autocheck="true" label="上级经销商ID" json="CHANN_ID_P"  value="${rst.CHANN_ID_P}"   readonly/>
													<input type="text" id="CHANN_NO_P" name="CHANN_NO_P" inputtype="string" autocheck="true" label="上级经销商编号"   json="CHANN_NO_P"  value="${rst.CHANN_NO_P}"   readonly/>
													<img align="absmiddle" name="selCHANN" style="cursor: hand"	src="${ctx}/styles/${theme}/images/plus/select.gif"
														onClick="selCommon('BS_19', false, 'CHANN_ID_P', 'CHANN_ID', 'forms[0]', 'CHANN_ID_P,CHANN_NO_P,CHANN_NAME_P','CHANN_ID,CHANN_NO,CHANN_NAME', 'selectParamsC')">
												</td>
												<td width="15%" align="right" class="detail_label" nowrap>
													上级经销商名称：
												</td>
												<td width="35%" class="detail_content">
													<input type="text" id="CHANN_NAME_P" name="CHANN_NAME_P" inputtype="string" autocheck="true" label="上级经销商名称"   json="CHANN_NAME_P"  value="${rst.CHANN_NAME_P}"  readonly/>
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													经销商简称：
												</td>
												<td width="35%" class="detail_content">
													<input json="CHANN_ABBR" name="CHANN_ABBR" type="text"
														autocheck="true" label="经销商简称" inputtype="string"
														maxlength="100" value="${rst.CHANN_ABBR }">
												</td>
												<td width="15%" align="right" class="detail_label">
													经销商类型：
												</td>
												<td width="35%" class="detail_content">
													<select label="经销商类型" name="CHANN_TYPE"
														valueType="chinese:false" inputtype="string"
														style="width: 173" id="CHANN_TYPE" json="CHANN_TYPE" 
														value="${rst.CHANN_TYPE}" mustinput="true">
													</select>
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label">
													经销商级别：
												</td>
												<td width="35%" class="detail_content">
													<select label="经销商级别" name="CHAA_LVL"
														inputtype="string"  autocheck="true"
														style="width: 173" id="CHAA_LVL" json="CHAA_LVL"
														value="${rst.CHAA_LVL}" mustinput="true">
													</select>
												</td>
												<td width="15%" align="right" class="detail_label">
													加盟日期：
												</td>
												<td width="35%" class="detail_content">
													<input type="text" json="JOIN_DATE" id="JOIN_DATE" name="JOIN_DATE" autocheck="true" inputtype="string" label="加盟日期"  value="${rst.JOIN_DATE}"   onclick="SelectDate();" readonly />
													<img align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectDate(JOIN_DATE);">
												</td>
											</tr>

											<!-- <tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													区域经理名称：
												</td>
												<td width="35%" class="detail_content">
													<input type="hidden" id="AREA_MANAGE_ID" name="AREA_MANAGE_ID" inputtype="string" autocheck="true" label="区域经理ID" json="AREA_MANAGE_ID"  value="${rst.AREA_MANAGE_ID}"   readonly/>
													<input type="text" id="AREA_MANAGE_NAME" name="AREA_MANAGE_NAME" inputtype="string" autocheck="true" label="区域经理名称" mustinput="true"  json="AREA_MANAGE_NAME"  value="${rst.AREA_MANAGE_NAME}"   readonly/>
													<img align="absmiddle" name="selJGXX" style="cursor: hand"
														src="${ctx}/styles/${theme}/images/plus/select.gif"
														onClick="selCommon('System_0', false, 'AREA_MANAGE_ID', 'RYXXID', 'forms[0]','AREA_MANAGE_ID,AREA_MANAGE_NAME,AREA_MANAGE_TEL', 'RYXXID,XM,SJ', 'selectArea')">
												</td>
												<td width="15%" align="right" class="detail_label" nowrap>
													区域经理电话：
												</td>
												<td width="35%" class="detail_content">
													<input type="text" id="AREA_MANAGE_TEL" name="AREA_MANAGE_TEL" inputtype="string" autocheck="true" label="区域经理电话" mustinput="true"  json="AREA_MANAGE_TEL"  value="${rst.AREA_MANAGE_TEL}"  readonly/>
												</td>
											</tr> -->
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													区域编号：
												</td>
												<td width="35%" class="detail_content">
													<input id="AREA_ID" json="AREA_ID" name="AREA_ID"
														type="hidden" inputtype="string"
														value="${rst.AREA_ID}">
														
													<input id="AREA_NO" json="AREA_NO" name="AREA_NO" type="text" 
													 inputtype="string" value="${rst.AREA_NO}" label="区域编号" autocheck="true"
													 READONLY >
													 
													<img align="absmiddle" style="cursor: hand"
														src="${ctx}/styles/${theme}/images/plus/select.gif"
														onClick="selCommon('BS_18', false, 'AREA_ID', 'AREA_ID', 'forms[0]','AREA_ID,AREA_NO,AREA_NAME', 'AREA_ID,AREA_NO,AREA_NAME', 'selectParamsT')">
												</td>
												<td width="15%" align="right" class="detail_label">
													区域名称：
												</td>
												<td width="35%" class="detail_content">
													<input id="AREA_NAME" name="AREA_NAME" type="text"  autocheck="true" 
														inputtype="string" label="区域名称" json="AREA_NAME" 
														value="${rst.AREA_NAME }" READONLY>
												</td>
											</tr>
											<tr>												
												<td width="15%" align="right" class="detail_label" nowrap>
													国家：
												</td>
												<td width="35%" class="detail_content">
													<input id="ZONE_ID" json="ZONE_ID" name="ZONE_ID"
														type="hidden" inputtype="string"
														value="${rst.ZONE_ID}">
													<input id="NATION" json="NATION" name="NATION" type="text" 
														inputtype="string"  value="${rst.NATION}" label="国家"  autocheck="true"
														READONLY>

													<img align="absmiddle" name="selZONE" style="cursor: hand"
														src="${ctx}/styles/${theme}/images/plus/select.gif" 
														onClick="selCommon('BS_20', false, 'ZONE_ID','ZONE_ID', 'forms[0]','ZONE_ID,NATION,PROV,CITY,COUNTY', 'ZONE_ID,NATION,PROV,CITY,COUNTY', 'selectParams')">
												</td>
												<td width="15%" align="right" class="detail_label">
													省份：
												</td>
												<td width="35%" class="detail_content">
													<input id="PROV" json="PROV" name="PROV" type="text" autocheck="true"
														 label="省份" inputtype="string"
														value="${rst.PROV }" READONLY>
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													城市：
												</td>
												<td width="35%" class="detail_content">
													<input id="CITY" json="CITY" name="CITY" type="text"   autocheck="true"
														 label="城市" inputtype="string"
														value="${rst.CITY }" READONLY>
												</td>
												<td width="15%" align="right" class="detail_label">
													区县：
												</td>
												<td width="35%" class="detail_content">
													<input id="COUNTY" json="COUNTY" name="COUNTY" type="text" 
														 label="区县"  autocheck="true"
														inputtype="string" value="${rst.COUNTY }" READONLY>
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													联系人：
												</td>
												<td width="35%" class="detail_content">
													<input json="PERSON_CON" name="PERSON_CON" type="text" mustinput="true"
														autocheck="true" label="联系人" inputtype="String"
														maxlength="30" value="${rst.PERSON_CON }">
												</td>
												<td width="15%" align="right" class="detail_label">
													电话：
												</td>
												<td width="35%" class="detail_content">
													<input name="TEL" type="text" json="TEL" id="TEL" label="电话"
														autocheck="true" inputtype="string" maxlength="30"
														value="${rst.TEL}">
												</td>
											</tr>
											<tr>
												
												<td width="15%" align="right" class="detail_label" nowrap>
													手机：
												</td>
												<td width="35%" class="detail_content">
													<input name="MOBILE" type="text" mustinput="true" autocheck="true" label="手机"
														id="MOBILE"  maxlength="30" json="MOBILE" inputtype="string" 
														value="${rst.MOBILE}">
												</td>
												<td width="15%" align="right" class="detail_label" nowrap>
													传真：
												</td>
												<td width="35%" class="detail_content">
													<input name="TAX" type="text" autocheck="true" label="传真"
														id="TAX" inputtype="string" maxlength="30" json="TAX"
														value="${rst.TAX}">
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label">
													电子邮件：
												</td>
												<td width="35%" class="detail_content">
													<input id="EMAIL" json="EMAIL" name="EMAIL" type="text"
														autocheck="true" label="电子邮件" inputtype="string"
														maxlength="30" value="${rst.EMAIL }">
												</td>
												
												<td width="15%" align="right" class="detail_label" nowrap>
													邮政编码：
												</td>
												<td width="35%" class="detail_content">
													<input name="POST" json="POST" type="text"
														inputtype="string" id="POST" autocheck="true" label="邮政编码"
														maxlength="30" value="${rst.POST }">
												</td>
											</tr>
											<tr>
												
												<td width="15%" align="right" class="detail_label" nowrap>
													纳税人识别号：
												</td>
												<td width="35%" class="detail_content">
													<input json="BUSS_LIC" name="BUSS_LIC" type="text"
														inputtype="string" autocheck="true" maxlength="30"
														value="${rst.BUSS_LIC }" label="纳税人识别号">
												</td>
												<td width="15%" align="right" class="detail_label" nowrap>
												</td>
												<td width="35%" class="detail_content">
												</td>
											</tr>

											<tr>	
												<td width="15%" align="right" class="detail_label" nowrap>
													开户银行：
												</td>
												<td width="35%" class="detail_content">
													<input json="BANK_NAME" name="BANK_NAME" type="text"
														autocheck="true" label="开户银行" inputtype="string"
														maxlength="50" value="${rst.BANK_NAME }">
												</td>											
												<td width="15%" align="right" class="detail_label">
													银行账号：
												</td>
												<td width="35%" class="detail_content">
													<input json="BANK_ACCT" name="BANK_ACCT" type="text"
														autocheck="true" label="银行账号" inputtype="string"
														maxlength="30" value="${rst.BANK_ACCT }">
												</td>
											</tr>						

											<tr>
												<td width="15%" align="right" class="detail_label">
													详细地址：
												</td>
												<td width="35%" class="detail_content"  colspan="3">
													<input json="ADDRESS" name="ADDRESS" type="text" autocheck="true"
														label="详细地址" inputtype="string" maxlength="250" style="width:84%"
														value="${rst.ADDRESS }">
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label">
													备注：
												</td>
												<td width="35%" class="detail_content" colspan="3">
												    <textarea json="REMARK" name="REMARK" inputtype="string" maxlength="250" rows="2" cols="79%"><c:out value="${rst.REMARK}"></c:out></textarea>
												</td>
											</tr>

										</table>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<!--占位用行，以免显示数据被浮动层挡住-->
					<td height="20px">
						&nbsp;
					</td>
			    </tr>
			</table>
		</div>
	</body>
	<script language="JavaScript">
		SelDictShow("CHANN_TYPE", "171", '${rst.CHANN_TYPE}', "");
		//SelDictShow("INIT_YEAR", "89", "${rst.INIT_YEAR}", "");
		//SelDictShow("INIT_MONTH", "168", "${rst.INIT_MONTH}", "");
		//修改经销商销售级别和经销商级别的下拉值
		SelDictShow("CHAA_LVL", "BS_100", '${rst.CHAA_LVL}', "");
		//SelDictShow("CHAA_SALE_LVL", "169", '${rst.CHAA_SALE_LVL}', "");
		//SelDictShow("BUSS_NATRUE", "177", '${rst.BUSS_NATRUE}', "");
		//SelDictShow("CITY_TYPE", "BS_101", '${rst.CITY_TYPE}', "");
		//SelDictShow("COST_TYPE", "BS_131", '${rst.COST_TYPE}', "");
	</script>
</html>
