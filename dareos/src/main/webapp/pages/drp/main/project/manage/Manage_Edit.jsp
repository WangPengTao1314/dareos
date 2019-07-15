<!-- 
 *@module  项目管理
 *@func 编辑
 *@version 1.1
 *@author 王朋涛
 *  -->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<script language="JavaScript"src="${ctx}/pages/common/select/selCommJs.js"></script>
		<script type="text/javascript" src="${ctx}/pages/drp/main/project/manage/Manage_Edit.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
		<title>项目信息编辑</title>
		<style>
		table tr{
		height:50px;
		}
		.dashed{border-bottom: 1px solid #e8edf2;width: 98%;}	
		</style>
	</head>
	<body style="background-color: #fff;overflow-y: auto; overflow-x: auto;">
		<div class="buttonlayer" id="floatDiv">
			<table cellspacing=0 cellpadding=0 border=0 width="100%">
				<tr align="center">
					<td align="center" class="gdtd" nowrap>
						<button class="img_input" >
			                <label for='save'>
			                    <img src="${ctx}/styles/${theme}/images/icon/baocun.png"  class="icon_font">
			                    <input     id="save" type="button" class="btn" value="保存" >
			                </label>
					    </button>
						<button class="img_input" >
			                <label onclick="closeDialog()">
			                    <img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
			                    <input   type="button" class="btn" value="返回"  >
			                </label>
				        </button>
					</td>
				</tr>
			</table>
		</div>
		<!--浮动按钮层结束-->
		<form name="mainForm" id="mainForm">
			<table id="jsontb" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
				<%-- <tr>
					<td class="detail2">
					<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">项目信息</div>
							<div class="toggle">
							<input type="button"  id="ipt"  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;" onclick='changeinput(this)'>
							<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="vertical-align: middle;" id="imgsz">
					</div>
					</td>
				</tr> --%>
				<tr id="myTable1">
					<td class="detail2">
						<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable"  class="detail3"
							style="background-color: #fff; padding: 0 0 0 2%; border-collapse: separate; border-spacing: 10px;border-top:1px solid #e8edf2;;border-left:1px solid #e8edf2;;border-right:1px solid #e8edf2;;margin:0 auto">
							<tr>							
								<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
								<span style="float:left"  class="dashed font-color">基本信息</span>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>项目编号 &nbsp;:</div>
									<input  class="gdtd_select_input"   name="project_id" hidden="true" json="project_id" id="project_id" type="text"   inputtype="string"   label="项目id" 	autocheck="true" value="${entry.PROJECT_ID}">
									<input  class="gdtd_select_input"  name="project_no" json="project_no" id="PROJECT_NO" type="text" placeholder="自动生成"  inputtype="string"   label="项目编号" 	autocheck="true"  value="${entry.PROJECT_NO}">
								</td> 
								<td class="gdtd_tb">
									<div>项目名称 &nbsp;:</div>
									<textarea  class="gdtd_select_input"   name="PROJECT_NAME" json="project_name" id="PROJECT_NAME" type="text" 
									   autocheck="true" inputtype="string" label="项目名称" mustinput="true" maxlength="200" >${entry.PROJECT_NAME}</textarea>
								</td>
								<td class="gdtd_tb">
								 	<div>招投标编号 :</div>
									<input  class="gdtd_select_input"  json="tendering_no" name="TENDERING_NO" id="TENDERING_NO" type="text"  autocheck="true"  
										  value="${entry.TENDERING_NO }" >
									<img class="select_gif" align="absmiddle" name="selJGXX" style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif" 
										onClick="selCommon('BS_199', false, 'TENDERING_ID', 'TENDERING_ID', 'forms[0]','TENDERING_ID,TENDERING_NO,PROJECT_NAME,PROJECT_TYPE,PROJECT_NO,TENDERING_UNIT,OPEN_TIME','TENDERING_ID,TENDERING_NO,PROJECT_NAME,PROJECT_TYPE,PROJECT_NO,TENDERING_UNIT,OPEN_TIME')">
									<input  class="gdtd_select_input"  json="tendering_id" name="TENDERING_ID" id="TENDERING_ID" hidden="true" seltarget="selJGXX" type="text"  autocheck="true"  
										label="招投标id" inputtype="string"   value="${entry.TENDERING_ID }" READONLY >
								</td>  
								<td class="gdtd_tb">
									<div>项目类型:</div>
									<select class="gdtd_select_input" name="PROJECT_TYPE" json="project_type" id="PROJECT_TYPE" inputtype="string"
										mustinput="true" autocheck="true" label="项目类型" > </select>
								</td> 
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>收货地址 &nbsp;:</div>
									<textarea   style="width: 189.5%;" rows="1"  name="address" 
												json="address"  maxlength="200" >${entry.ADDRESS}</textarea>
								</td>
								<td class="gdtd_tb"></td>
								 <td class="gdtd_tb">
									<div>合同签订单位 &nbsp;:</div>
									<textarea   style="width: 189.5%;" rows="1"  name="contract_unit" 
												json="contract_unit"  maxlength="200" >${entry.CONTRACT_UNIT}</textarea>
								</td>
								<td class="gdtd_tb"></td>
							</tr>
							
							<tr>
								<td class="gdtd_tb">
									<div>单位地址:</div>
									<textarea   style="width: 189.5%;" rows="1"  name="unit_address" id="TENDERING_UNIT"
												json="unit_address"  maxlength="200" >${entry.UNIT_ADDRESS}</textarea>
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb">
									<div>甲方联系人 &nbsp;:</div>
									<input  class="gdtd_select_input"   name="contact_a_person" json="contact_a_person" type="text" inputtype="string"  label="质保期" 	autocheck="true" maxlength="30" value="${entry.CONTACT_A_PERSON}" >
								</td>
								<td class="gdtd_tb">
									<div>税务登记号:</div>
									<input  class="gdtd_select_input"   name="ax_burden" json="ax_burden" type="text" inputtype="string"   label="税务登记号" 	autocheck="true" maxlength="30" value="${entry.AX_BURDEN}">
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>银行账号:</div>
									<input  class="gdtd_select_input"   name="bank_acct"  json="bank_acct" type="text" inputtype="string"   label="银行账号" 	autocheck="true" maxlength="30" value="${entry.BANK_ACCT}">
								</td>
								<td class="gdtd_tb">
									<div>开户银行:</div>
									<input  class="gdtd_select_input"   name="bank_name" json="bank_name" type="text" inputtype="string"   label="开户银行" 	autocheck="true" maxlength="50" value="${entry.BANK_NAME}">
								</td>
								<td class="gdtd_tb">
									<div>质保期 &nbsp;:</div>
									<input  class="gdtd_select_input"   name="warranty_period" json="warranty_period" type="text" inputtype="string"  label="质保期" 	autocheck="true" maxlength="20" value="${entry.WARRANTY_PERIOD}" >
								</td>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>开工时间 &nbsp;:</div>
									 <input  class="gdtd_select_input"  name="start_date" type="text" json="start_date" id="OPEN_TIME" onclick="SelectDate();"
									class="jeinput" placeholder="年月日选择"
									value="${entry.START_DATE}" READONLY>
									 <!-- label="开工时间" autocheck="true"  mustinput="true"  inputtype="text" -->
									<img class="select_gif" align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
										onclick="SelectDate(start_date);"/> 
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>竣工验收时间:</div>
									<input  class="gdtd_select_input"  name="check_accept" type="text" json="check_accept" id="check_accept" onclick="SelectDate();"
									class="jeinput" placeholder="年月日选择" 
									 value="${entry.CHECK_ACCEPT}" READONLY>
									<!--  label="竣工验收时间' " autocheck="true"  mustinput="true" inputtype="text" -->
									<img class="select_gif" align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
										onclick="SelectDate(check_accept);"/> 
								</td>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>质保金到期时间:</div>
									<input  class="gdtd_select_input"   name="warranty_amount_expire_date" type="text" json="warranty_amount_expire_date" id="warranty_amount_expire_date" onclick="SelectDate();"
									class="jeinput" placeholder="年月日选择" 
									 value="${entry.WARRANTY_AMOUNT_EXPIRE_DATE}" READONLY>
									<!--  label="质保金到期时间" autocheck="true"  mustinput="true" inputtype="text" -->
									<img class="select_gif" align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
										onclick="SelectDate(warranty_amount_expire_date);"/> 
								</td>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>结算时间:</div>
									<input  class="gdtd_select_input"   name="settle_date" type="text" json="settle_date" id="settle_date" onclick="SelectDate();"
									class="jeinput" placeholder="年月日选择" 
									 value="${entry.SETTLE_DATE}" READONLY>
									<!--  label="结算时间" autocheck="true"  mustinput="true"   inputtype="text" -->
									<img class="select_gif" align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
										onclick="SelectDate(settle_date);"/> 
								</td>
								<td class="gdtd_tb">
									<div>结算金额:</div>
									<input  class="gdtd_select_input"  name="settle_amount" json="settle_amount"  type="text" inputtype="int"   label="结算金额" 	autocheck="true" maxlength="10" value="${entry.SETTLE_AMOUNT}" >
								</td> 
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>供货单位:</div>
									<textarea   style="width: 189.5%;" rows="1"  name="supply_unit" 
												json="supply_unit"  maxlength="50" >${entry.SUPPLY_UNIT}</textarea>
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb">
									<div>业务员:</div>
									<input  class="gdtd_select_input"  name="salesman" json="salesman"  type="text" inputtype="string"   
									label="业务员" 	autocheck="true" maxlength="50" value="${entry.SALESMAN}" >
								</td>
								<td class="gdtd_tb">
									<div>项目经理:</div>
									<input  class="gdtd_select_input"   name="project_manager" json="project_manager"  type="text" inputtype="string"   
									label="项目经理" 	autocheck="true" maxlength="50" value="${entry.PROJECT_MANAGER}" >
								</td>
							</tr>
							<tr>
								<td>
									<div>颜色及材质:</div>
									<textarea  style="width: 189.5%;" rows="3" name="colour_and_material" 
									json="colour_and_material"   maxlength="4000" >${entry.COLOUR_AND_MATERIAL}</textarea>
								</td>
								<td align="right" class="gdtd"></td>
								<td class="gdtd_tb">
									<div>合同数量:</div>
									<textarea  style="width:83.5%;" rows="3" name="contracts_number" json="contracts_number"  type="text" inputtype="int"   
									label="合同数量" 	autocheck="true" maxlength="500">${entry.CONTRACTS_NUMBER}</textarea>
								</td>
								<td class="gdtd_tb"> 
									<div>工艺图纸:</div>
									<textarea  style="width: 83.5%;" rows="3"  name="process_drawing" json="process_drawing"  type="text" inputtype="string"   
									label="工艺图纸" 	autocheck="true" maxlength="500" >${entry.PROCESS_DRAWING}</textarea>
								</td>
							</tr>
							<tr>
								<td>
									<div>五金配置:</div>
									<textarea  style="width:189.5%;" rows="3" name="hardware_config" json="hardware_config"  
									maxlength="4000" >${entry.HARDWARE_CONFIG}</textarea>
								</td>
								<td  class="gdtd"></td>
								<td class="gdtd_tb">
									<div>涂装或表面效果要求:</div>
									<textarea  style="width: 189.5%;" rows="3"  name="appearance_requirement" 
									json="appearance_requirement"   maxlength="4000">${entry.APPEARANCE_REQUIREMENT}</textarea>
								</td>
								<td  class="gdtd"></td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>产品包装及标识要求:</div>
									<textarea   style="width: 189.5%;" rows="3"  name="packaging_requirements" 
									json="packaging_requirements"  maxlength="4000" >${entry.PACKAGING_REQUIREMENTS}</textarea>
								</td>
								<td  class="gdtd"></td>
								<td>
									<div>用材要求:</div>
									<textarea    style="width: 189.5%;" rows="3" name="material_requirement" 
									json="material_requirement"  maxlength="4000" >${entry.MATERIAL_REQUIREMENT}</textarea>
								</td>
								<td  class="gdtd"></td>
							</tr>
							<tr>
								<td>
									<div>备注:</div>
									 <textarea  style="width: 189.5%;" rows="3" name="remark" json="remark"  
									 maxlength="4000">${entry.REMARK}</textarea>
								</td>
								<td  class="gdtd"></td>
								<td  class="gdtd"></td>
								<td  class="gdtd"></td>
							</tr>
							<tr>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
		<!--占位用table，以免显示数据被浮动层挡住-->
		<table width="100%" height="25px">
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
		
		SelDictShow("PROJECT_TYPE","BS_183","${entry.PROJECT_TYPE}","");
	</script>
</html>
