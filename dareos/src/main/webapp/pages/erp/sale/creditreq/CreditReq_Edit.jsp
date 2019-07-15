<!-- 
 /**
 * @module 系统管理
 * @func 信用额度申请
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
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/pages/erp/sale/creditreq/CreditReq_Edit.js"></script>
		<title>信用额度申请编辑</title>
	<body>
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
								<input type="button" class="btn" value="返回" />
								</label>
							</button>
						</td>
					</tr>
				</table>
			</div>

			<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
				
				<tr>
					<td>
						<form name="main" id="mainForm">
							<input type="hidden" name="selectParams" value=" STATE =  '启用' and DEL_FLAG='0' and IS_BASE_FLAG=0 and CHANN_ID in( select t.chann_id from BASE_USER_CHANN_CHRG t where t.user_id='${xtyhId }')">
							<table id="jsontb" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
								<tr>
									<td class="detail2">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
											<tr>
											    <td width="16%" align="right" class="detail_label">
													业务单号：
												</td>
												<td width="35%" class="detail_content">
													<input json="creditReqNo" name="creditReqNo" id="creditReqNo" 
													autocheck="true"  inputtype="string" mustinput="true"  maxlength="30" readonly="readonly"
													 <c:if test="${not empty rst.creditReqNo}">value="${rst.creditReqNo}"</c:if>
													 <c:if test="${!not empty rst.creditReqNo}">value="自动生成"</c:if>
													 type="text" label="业务单号"  >
												</td>
												<td width="16%" align="right" class="detail_label">
													经销商：
												</td>
												<td width="35%" class="detail_content">
													<input id="channId" json="channId" name="channId" id="channId"
															type="hidden" inputtype="string" autocheck="true" 
															value="${rst.channId}">

													<input id="channNo" json="channNo" name="channNo"
														type="hidden" inputtype="string" autocheck="true" 
														value="${rst.channNo}"  >
														
														<input id="channName" name="channName" type="text" mustinput="true" 
														inputtype="string" label="经销商" json="channName" autocheck="true"
														value="${rst.channName }"  READONLY>
														
													<img align="absmiddle" name="selJGXX" style="cursor: hand"
														src="${ctx}/styles/${theme}/images/plus/select.gif"
														onClick="selCommon('BS_19', false, 'channId', 'CHANN_ID', 'forms[0]','channId,channNo,channName', 'CHANN_ID,CHANN_NO,CHANN_NAME','selectParams')">
												</td>
											</tr>
											<tr>
												<td width="16%" align="right" class="detail_label">
													订单组织：
												</td>
												<td width="35%" class="detail_content">
													<input json="ledgerName"  name="ledgerName" id="ledgerName" type="hidden"  label="订单组织"  value="${rst.ledgerName}">
													<select json="ledgerId"  name="ledgerId" id="ledgerId"  autocheck="true" label="订单组织" inputtype="string" mustinput="true"   value="${rst.ledgerId}"></select>
												</td>
												<td width="16%" align="right" class="detail_label">
													申请额度：
												</td>
												<td width="35%" class="detail_content">
													<input id="creditTotal" name="creditTotal" type="text" valueType="8,2"
														inputtype="zffloat" label="申请额度" json="creditTotal" autocheck="true" mustinput="true"
														value="${rst.creditTotal }"  >
												</td>
											</tr>
											<tr>
												<td width="16%" align="right" class="detail_label">
													生效日期：
												</td>
												<td width="35%" class="detail_content">
													<input type="text" json="effectiveDate" id="effectiveDate" name="effectiveDate" autocheck="true" inputtype="string" value="${rst.effectiveDate}" mustinput="true" 
															label="日期"  onclick="SelectDate();" READONLY />&nbsp;
														<img align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
															onclick="SelectDate(effectiveDate);"/>
												</td>
												<td width="16%" align="right" class="detail_label">
													失效日期：
												</td>
												<td width="35%" class="detail_content">
													<input type="text" json="expirationDate" id="expirationDate" name="expirationDate" autocheck="true" inputtype="string" value="${rst.expirationDate}" mustinput="true" 
															label="日期"  onclick="SelectDate();" READONLY />&nbsp;
														<img align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
															onclick="SelectDate(expirationDate);"/>
												</td>
											</tr>
											<tr>
												<td width="16%" align="right" class="detail_label">
													附件
												</td>
												<td colspan="3" class="detail_content">
													<button type="button" class="layui-btn uploadFile" id="attPath" lay-data="{id:'attPath'}">上传</button>
													<input type="hidden"  json="attPath" name="attPath" id="hid_attPath" value="${ rst.attPath}">				
													<table class="layui-table" style="width:85%" id="view_attPath"></table>
												</td>
											</tr>
											<tr>
							               		<td width="15%" align="right" class="detail_label">备注：</td>
											   <td width="35%" class="detail_content"  colspan="3">
							                     <textarea  json="remark" name="remark" id="remark" autocheck="true" inputtype="string"   maxlength="250"   label="备注"    rows="4" cols="80%">${rst.remark}</textarea>
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
			        <td height="25px">&nbsp;</td>
			    </tr>
			</table>
		</div>
	</body>
</html>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script language="JavaScript"> 
	var channId = $("#channId").val();
	if(channId){
		SelDictShow("ledgerId","BS_189","${rst.ledgerId }"," chann_id = '"+channId+"'");
	}
	displayDownFile('attPath',true,false,true);
</script>