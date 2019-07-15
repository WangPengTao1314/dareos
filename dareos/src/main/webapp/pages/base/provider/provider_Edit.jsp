<!--  
/**
 * @module 系统管理
 * @func 供应商信息
 */
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/${theme}/css/style.css">
		<script language="JavaScript"
			src="${ctx}/pages/common/select/selCommJs.js"></script>
		<script type="text/javascript"
			src="${ctx}/pages/base/provider/provider_Edit.js"></script>
		<title>供应商编辑</title>
	</head>
	<body class="bodycss1">
		<div style="height: 100">
			<div class="buttonlayer" id="floatDiv">
				<table cellSpacing=0 cellPadding=0 border=0 width="100%">
					<tr>
						<td align="right" nowrap>
							<button class="img_input" >
								<label for='save'>
								<img src="${ctx}/styles/${theme}/images/icon/baocun.png"  class="icon_font">
								<input id="save" type="button" class="btn" value="保存" />
								</label>
							</button>
							<button class="img_input" >
								<label onclick="closeDialog()">
								<img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
								<input type="button" class="btn" value="返回"/>
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
							<input type="hidden" name="selectContion" value="STATE='启用'">
							<input type="hidden" name="ryxxSelectContion" value='DELFLAG = 0'>
							<input type="hidden" name="selectContion1"	value="DELFLAG = 0 and JGZT = '启用'">
							<table id="jsontb" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
								<tr>								
									<td class="detail2">
										<table width="100%" border="0" cellpadding="0" cellspacing="0"
											class="detail3">
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													供应商编号：
												</td>
												<td width="35%" class="detail_content">
													<c:if test="${empty rst.PRVD_NO}">
														<input id="PRVD_NO" json="PRVD_NO" name="PRVD_NO" type="text" autocheck="true"
														label="供应商编号" inputtype="string" mustinput="true" maxlength="30" value="${rst.PRVD_NO}">
													</c:if>
													<c:if test="${not empty rst.PRVD_NO}">
														<input id="PRVD_NO" json="PRVD_NO" name="PRVD_NO" type="text" autocheck="true"
														label="供应商编号" inputtype="string" mustinput="true" maxlength="30" value="${rst.PRVD_NO}" readonly>
													</c:if>
												</td>
												<td width="15%" align="right" class="detail_label">
													供应商名称：
												</td>
												<td width="35%" class="detail_content">
													<input id="PRVD_NAME" json="PRVD_NAME" name="PRVD_NAME" type="text" autocheck="true"
														label="供应商名称" inputtype="string" mustinput="true" maxlength="100" value="${rst.PRVD_NAME}">
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													供应商类别：
												</td>
												<td width="35%" class="detail_content">												
													<select json="PRVD_TYPE" id="PRVD_TYPE" name="PRVD_TYPE" style="width:173" autocheck="true" mustinput="true" inputtype="string"></select>
													</select>													
												</td>
												<td width="15%" align="right" class="detail_label">
													供应商级别：
												</td>
												<td width="35%" class="detail_content">
													<select json="PRVD_LVL" id="PRVD_LVL" name="PRVD_LVL" style="width:173" autocheck="true" mustinput="true" inputtype="string"></select>												
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label">
													国家：
												</td>
												<td width="35%" class="detail_content">
													<input  json="NATION" name="NATION" type="text" inputtype="string"
														seltarget="selDZXX" autocheck="true" maxlength="32"
														value="${rst.NATION }" readonly>
													<input json="PRVD_ID" name="PRVD_ID" type="hidden"
														seltarget="selDZXX" autocheck="true" label="国家"
														inputtype="string" maxlength="50" value="${rst.PRVD_ID}">
													<img align="absmiddle" name="selDZXX" style="cursor: hand"
														src="${ctx}/styles/${theme}/images/plus/select.gif"
														onClick="selCommon('BS_20', false, 'PRVD_ID', 'ZONE_ID', 'forms[0]','NATION,PROV,CITY,COUNTY', 'NATION,PROV,CITY,COUNTY', 'selectContion')">
												</td>
												<td width="15%" align="right" class="detail_label" nowrap>
												</td>
												<td width="35%" class="detail_content">
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													省份：
												</td>
												<td width="35%" class="detail_content">
													<input json="PROV" name="PROV" type="text" autocheck="true"
														label="省份" inputtype="string" maxlength="50"
														value="${rst.PROV }" readonly>
												</td>
												<td width="15%" align="right" class="detail_label">
													城市：
												</td>
												<td width="35%" class="detail_content">
													<input json="CITY" name="CITY" type="text" autocheck="true"
														label="城市" inputtype="string" maxlength="50"
														value="${rst.CITY }" readonly>
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													区县：
												</td>
												<td width="35%" class="detail_content">
													<input json="COUNTY" name="COUNTY" type="text" autocheck="true"
														label="区县" inputtype="string" maxlength="50"
														value="${rst.COUNTY }" readonly>
												</td>
												<td width="15%" align="right" class="detail_label">

												</td>
												<td width="35%" class="detail_content">
										
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label">
													联系人：
												</td>
												<td width="35%" class="detail_content">
													<input json="PERSON_CON" name="PERSON_CON" type="text" autocheck="true"
														label="联系人" inputtype="string" maxlength="30" mustinput="true"  value="${rst.PERSON_CON }">
												</td>
												<td width="15%" align="right" class="detail_label">
													电话：
												</td>
												<td width="35%" class="detail_content">
													<input json="TEL" name="TEL" type="text" autocheck="true"
														label="电话" inputtype="string" maxlength="30"
														value="${rst.TEL }">
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label">
													手机：
												</td>
												<td width="35%" class="detail_content">
													<input json="MOBILE_PHONE" name="MOBILE_PHONE" type="text" autocheck="true"
														label="手机" inputtype="string" maxlength="30"
														value="${rst.MOBILE_PHONE }">
												</td>
												<td width="15%" align="right" class="detail_label">
													传真：
												</td>
												<td width="35%" class="detail_content">
													<input json="TAX" name="TAX" type="text" autocheck="true"
														label="传真" inputtype="string" maxlength="30"
														value="${rst.TAX }">
												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label">
													邮编：
												</td>
												<td width="35%" class="detail_content">
													<input json="POST" name="POST" type="text" autocheck="true"
														label="邮编" inputtype="string" maxlength="30"
														value="${rst.POST }">
												</td>
												<td width="15%" align="right" class="detail_label">
													Email：
												</td>
												<td width="35%" class="detail_content">
													<input json="EMAIL" name="EMAIL" type="text" autocheck="true"
														label="Email" inputtype="string" maxlength="30"
														value="${rst.EMAIL }">
												</td>
											</tr>

											<tr>
												<td width="15%" align="right" class="detail_label">
													纳税人识别号：
												</td>
												<td width="35%" class="detail_content">
													<input json="BUSS_LIC" name="BUSS_LIC" type="text" autocheck="true"
														label="纳税人识别号" inputtype="string" maxlength="30"
														value="${rst.BUSS_LIC }">
												</td>
												<td width="15%" align="right" class="detail_label">
												
												</td>
												<td width="35%" class="detail_content">

												</td>
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label">
													开户行：
												</td>
												<td width="35%" class="detail_content">
													<input json="OPENING_BANK" name="OPENING_BANK" type="text" autocheck="true"
														label="开户行" inputtype="string" maxlength="100"
														value="${rst.OPENING_BANK }">
												</td>
												<td width="15%" align="right" class="detail_label">
													银行账号：
												</td>
												<td width="35%" class="detail_content">
													<input json="BANK_ACCT" name="BANK_ACCT" type="text" autocheck="true"
														label="银行账号" inputtype="string" maxlength="30"
														value="${rst.BANK_ACCT }">
												</td>
											</tr>											
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													详细地址：
												</td>
												<td width="35%" class="detail_content" colspan="3">		
												    <input json="ADDRESS" name="ADDRESS" type="text" 
														label="详细地址" inputtype="string" maxlength="250" style="width:84%"
														value="${rst.ADDRESS }">											
												</td>												
											</tr>
											<tr>
												<td width="15%" align="right" class="detail_label" nowrap>
													备注：
												</td>
												<td width="35%" class="detail_content" colspan="3">													
													<textarea json="REMARK" name="REMARK" inputtype="string"
													maxlength="250" rows="2" cols="79%">${rst.REMARK }</textarea>
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
			        <td height="20px">&nbsp;</td>
			    </tr>
			</table>
		</div>
	</body>
	<script language="JavaScript">  
			SelDictShow("PRVD_TYPE", "173", '${rst.PRVD_TYPE}',"");			
			SelDictShow("PRVD_LVL", "181", '${rst.PRVD_LVL}',"");
			//SelDictShow("PRVD_NATRUE", "182", '${rst.PRVD_NATRUE}',"");
			//SelDictShow("PRVD_CY", "183", '${rst.PRVD_CY}',"");
			//SelDictShow("PRVD_CAP", "184", '${rst.PRVD_CAP}',"");	
	</script>
</html>
