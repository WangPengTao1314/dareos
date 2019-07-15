<!--  
/**
  *@module 项目管理
  *@fuc 项目节点维护
  *@version 1.1
  *@author 王朋涛
*/
-->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%> 
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<%@ include file="/pages/common/uploadfile/uploadfile_pro.jsp"%> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/pross_style.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<%-- <link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/tabs.css"> --%>
		<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
		<script language="JavaScript" src="${ctx}/pages/drp/main/project/manage/Manage_Detail_Servicing.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/core/lib.js"></script>
		<style>
			.btn:hover, .btn:focus, .btn.focus {
		 	     color: white;
		    text-decoration: none;
			}
		</style>
		<title>项目节点维护</title>
	</head>
	<body style="overflow-y:scroll;background-color: #fff;" >
		<form name="mainForm" id="mainForm">
			<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
				<tr>
					<td class="detail2">
						<%-- <table style="width:100%;padding: 9px 1% 0;">
							<tr>
								<td colspan="4" class="title">项目信息</td>
								<td colspan="4" align="right">
								    <img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="position:relative;left:58px;vertical-align: middle;" id="imgsz1">
									<input type="button"  id="ipt1"  value="收起" style="border:1px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;width:9%;height:22px;text-align:left;" onclick='changeinput(this,1)'>
								</td>
							</tr>					
						</table> --%>
					<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">项目信息</div>
					<div class="toggle">
							<input type="button"  id="ipt"  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;padding: 10px 0 0 0;line-height: 0px;" onclick='changeinput(this)'>
							<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="vertical-align: middle;" id="imgsz">
					</div>
					</td>
				</tr>
				<tr>
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
									<input  class="gdtd_select_input"   name="project_id" hidden="true"value="${entry.PROJECT_ID}">
									<input  class="gdtd_select_input"  name="project_no"  value="" READONLY>
								</td> 
								<td class="gdtd_tb">
									<div>项目名称 &nbsp;:</div>
									<textarea   class="gdtd_select_input"READONLY>${entry.PROJECT_NAME}</textarea>
								</td>
								</td>
								<td class="gdtd_tb">
								 	<div>招投标编号 :</div>
									<input  class="gdtd_select_input"  value="${entry.TENDERING_NO }" READONLY>
								</td>  
								<td class="gdtd_tb">
									<div>项目类型:</div>
									<input  class="gdtd_select_input"  value="${entry.PROJECT_TYPE }" READONLY>
								</td> 
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>收货地址 &nbsp;:</div>
									<textarea   style="width: 189.5%;" rows="1"  READONLY>${entry.ADDRESS}</textarea>
								</td>
								<td class="gdtd_tb"></td>
								 <td class="gdtd_tb">
									<div>合同签订单位 &nbsp;:</div>
									<textarea   style="width: 189.5%;" rows="1"  READONLY>${entry.CONTRACT_UNIT}</textarea>
								</td>
								<td class="gdtd_tb"></td>
							</tr>
							
							<tr>
								<td class="gdtd_tb">
									<div>单位地址:</div>
									<textarea   style="width: 189.5%;" rows="1"  READONLY>${entry.UNIT_ADDRESS}</textarea>
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb">
									<div>甲方联系人 &nbsp;:</div>
									<input  class="gdtd_select_input" value="${entry.CONTACT_A_PERSON}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>税务登记号:</div>
									<input  class="gdtd_select_input"  value="${entry.AX_BURDEN}" READONLY>
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>银行账号:</div>
									<input  class="gdtd_select_input"   value="${entry.BANK_ACCT}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>开户银行:</div>
									<input  class="gdtd_select_input" value="${entry.BANK_NAME}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>质保期 &nbsp;:</div>
									<input  class="gdtd_select_input"  value="${entry.WARRANTY_PERIOD}" READONLY>
								</td>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>开工时间 &nbsp;:</div>
									 <input  class="gdtd_select_input"  value="${entry.START_DATE}" READONLY>
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>竣工验收时间:</div>
									<input  class="gdtd_select_input" value="${entry.CHECK_ACCEPT}" READONLY>
								</td>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>质保金到期时间:</div>
									<input  class="gdtd_select_input"    value="${entry.WARRANTY_AMOUNT_EXPIRE_DATE}" READONLY>
								</td>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>结算时间:</div>
									<input  class="gdtd_select_input"  value="${entry.SETTLE_DATE}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>结算金额:</div>
									<input  class="gdtd_select_input" value="${entry.SETTLE_AMOUNT}" READONLY>
								</td> 
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>供货单位:</div>
									<textarea   style="width: 189.5%;" rows="1"  name="supply_unit" 
												json="supply_unit"  maxlength="50" READONLY>${entry.SUPPLY_UNIT}</textarea>
								</td>
								<td class="gdtd_tb"></td>
								<td class="gdtd_tb">
									<div>业务员:</div>
									<input  class="gdtd_select_input"   value="${entry.SALESMAN}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>项目经理:</div>
									<input  class="gdtd_select_input"    value="${entry.PROJECT_MANAGER}" READONLY>
								</td>
							</tr>
							<tr>
								<td>
									<div>颜色及材质:</div>
									<textarea  style="width: 189.5%;" rows="3"READONLY>${entry.COLOUR_AND_MATERIAL}</textarea>
								</td>
								<td align="right" class="gdtd"></td>
								<td class="gdtd_tb">
									<div>合同数量:</div>
									<textarea  style="width:83.5%;" rows="3" name="contracts_number" json="contracts_number"  type="text" inputtype="int"   
									label="合同数量" 	autocheck="true" maxlength="500" READONLY>${entry.CONTRACTS_NUMBER}</textarea>
								</td>
								<td class="gdtd_tb"> 
									<div>工艺图纸:</div>
									<textarea  style="width: 83.5%;" rows="3"  name="process_drawing" json="process_drawing"  type="text" inputtype="string"   
									label="工艺图纸" 	autocheck="true" maxlength="500" READONLY>${entry.PROCESS_DRAWING}</textarea>
								</td>
							</tr>
							<tr>
								<td>
									<div>五金配置:</div>
									<textarea  style="width:189.5%;" rows="3" name="hardware_config" json="hardware_config"  
									maxlength="200" READONLY>${entry.HARDWARE_CONFIG}</textarea>
								</td>
								<td  class="gdtd"></td>
								<td class="gdtd_tb">
									<div>涂装或表面效果要求:</div>
									<textarea  style="width: 189.5%;" rows="3"  name="appearance_requirement" 
									json="appearance_requirement"   maxlength="200" READONLY>${entry.APPEARANCE_REQUIREMENT}</textarea>
								</td>
								<td  class="gdtd"></td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>产品包装及标识要求:</div>
									<textarea   style="width: 189.5%;" rows="3"  name="packaging_requirements" 
									json="packaging_requirements"  maxlength="200" READONLY>${entry.PACKAGING_REQUIREMENTS}</textarea>
								</td>
								<td  class="gdtd"></td>
								<td>
									<div>用材要求:</div>
									<textarea    style="width: 189.5%;" rows="3" name="material_requirement" 
									json="material_requirement"  maxlength="200" READONLY>${entry.MATERIAL_REQUIREMENT}</textarea>
								</td>
								<td  class="gdtd"></td>
							</tr>
							<tr>
								<td>
									<div>备注:</div>
									 <textarea  style="width: 189.5%;" rows="3" name="remark" json="remark"  
									 maxlength="500" READONLY>${entry.REMARK}</textarea>
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
		</div>	
	<table width="100%" border="0" cellpadding="5" cellspacing="0" class="detail">
			<tr>
				<td class="detail2" style="border-bottom:0">
					<div style="width:40%;float: left;height:8px;font-size:16px;font-weight:500;line-height:60px">付款阶段信息</div>
				<div style="width:40%;float: left;height:8px;font-size:16px;font-weight:500;line-height:60px">实际出货总金额:${entry.OUT_PRD_AMOUNT}</div>
				<div class="toggle" style='padding: 0.5% 0px 0px 0px;'>
				   	  <button class="img_input addbtn">
						<label for="add" style="margin-bottom: 0px">
							<img src="/dareos/styles/newTheme/images/icon/xinzeng.png"
								class="icon_font">
							<input id="add" type="button" class="btn add" value="新增" style="line-height: 18px;">
						</label>
					</button>
				   <button class="del_input">
						<label for="delete"  style="margin-bottom: 0px">
							<img src="/dareos/styles/newTheme/images/icon/shanchu.png"
								class="icon_font">
							<input id="delete" type="button" class="del_btn" value="删除" style="line-height: 18px;">
						</label>
					</button>
				</div>
			</td>
		</tr>
	</table>
	<div class="bodycss1 child" style="padding-top:0.5%">
		<form>
			<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
				<tr>
					<td class="detail2">
						<table id="jsontb" width="100%"  border="0" cellpadding="0" cellspacing="0" class="lst" >
				            <tr class="fixedRow">
								<th width="60" align="center"><input type="checkbox" style="height:12px;valign:middle" id="allChecked"></th>
								<th width="80"	align="center" >收款阶段</th>
								<th width="80" align="center">收款条件</th>
								
								<th width="70" align="center">请款时间</th>
								<th width="70" align="center">开票时间</th>
								<th width="70" align="center">开票金额(元)</th>
								<th width="70" align="center">回款时间</th>
								<th width="70" align="center">回款金额(元)</th>
								<th width="70" align="center">出货金额(元)</th>
								
								<th width="70" align="center">收款日期</th>
								<th width="70" align="center">收款比例(%)</th>
								<th width="70" align="center">收款金额(元)</th>
								<th width="70" align="center">实收金额(元)</th>
							</tr>
						</table>
					</td>
				</tr>
				<c:if test="${empty entry.entrySun}">
				<tr id="soDtlEmpty">
					<td style="height:42px;" colspan="13" align="center" class="lst_empty">
						&nbsp;无相关记录&nbsp;
					</td>
				</tr>
			</c:if>
			</table>
			<table width="100%" border="0" cellpadding="5" cellspacing="0" class="detail">
			<tr>
					<td class="detail2">
						<div style="width:100%;float: left;height:50px;font-size:16px;font-weight:500;line-height:50px">项目阶段信息</div>
				</td>
			</tr>
			</table>
			<div class="bodycss1 child" >
				<form>
					<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
						<tr>
							<td class="detail2">
								<table id="jsontbP" cellSpacing=0 cellPadding=0 border=0 width="100%" style="padding-top:2%" >
									<tr>
										<td>
											 <input   type="text" hidden="true" json="project_id" name="project_id" value="${entry.PROJECT_ID}" >
											 <input   type="text" hidden="true" json="project_no" name="project_id" value="${entry.PROJECT_NO}" >
											 <input   type="text" hidden="true" json="project_name" name="project_name" id="STATE" value="${entry.PROJECT_NAME}" >
											 <input   type="text" hidden="true"  name="state" id="STATE" value="${entry.STATE}" >
										</td>
									</tr>
									<tr>
										<td>
											<ul id="tabs" class="nav nav-pills nav-justified step step-arrow" style="margin:15px 0">
											  <li><a href="#" title="tab1">样品信息</a></li>
											  <li><a href="#" title="tab2">样板房</a></li>
											  <li ><a href="#" title="tab3">合同签订</a></li>
											  <li><a href="#" title="tab4">项目交接</a></li>
											  <li><a href="#" title="tab5">材料申购</a></li>
											  <li><a href="#" title="tab6">  安装合同签订</a></li>
											  <li><a href="#" title="tab7"> 大货下单</a></li>
											  <li><a href="#" title="tab8">发货</a></li>
											  <li><a href="#" title="tab9">签证</a></li>
											  <li><a href="#" title="tab10">补货</a></li>
											  <li><a href="#" title="tab11">维修</a></li>
											</ul>
											<div id="content">
											  <div id="tab1">
											  	<table style="width:100%">
											  		<tr>
											  			<td>
												  			<button type="button" class="layui-btn uploadFile" id="sample_att_id" lay-data="{id:'sample_att_id'}">上传</button></div>
															<input type="hidden" json="sample_att_id" name="sample_att_id" id="hid_sample_att_id" value="${rst.sample_att_id}">
											  			</td> 
											  		</tr>
											  		<tr>
											  			<td>
											  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" id="view_sample_att_id">
																<tr class="fixedRow">
																	<th align="center">序号</th>
																	<th nowrap align="center" dbname="att_path" >附件名称</th>
																	<th nowrap align="center" dbname="cre_name" >上传人</th>
																	<th nowrap align="center" dbname="cre_time" >上传时间</th>
																	<th nowrap align="center" >操作</th>
															   </tr>
															</table>
											  			</td>
											  		</tr>
											  	</table>
											  </div>
											  <div id="tab2">
											  	<table style="width:100%">
												  	<tr>
												  		<td><!-- 请选择板样房附件： -->
													   	  <button type="button" class="layui-btn uploadFile" id="sample_houses_att_id" lay-data="{id:'sample_houses_att_id'}">上传</button></div>
														  <input type="hidden" json="sample_houses_att_id" name="sample_houses_att_id" id="hid_sample_houses_att_id" value="${rst.sample_houses_att_id}">
												  		</td>
												  	</tr>
												  	<tr>
											  			<td>
											  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_sample_houses_att_id">
																<tr class="fixedRow">
																	<th align="center">序号</th>
																	<th nowrap align="center" dbname="att_path" >附件名称</th>
																	<th nowrap align="center" dbname="cre_name" >上传人</th>
																	<th nowrap align="center" dbname="cre_time" >上传时间</th>
																	<th nowrap align="center" >操作</th>
															   </tr>
															</table>
											  			</td>
										  			</tr>
											  	</table>
											  </div>
											  <div id="tab3">
											  	<table style="width:100%">
											  		<tr>
											  			<td><!--  请选择合同签订附件： -->
											  	 		  <button type="button" class="layui-btn uploadFile" id="contract_sing_att_id" lay-data="{id:'contract_sing_att_id'}">上传</button></div>
														  <input type="hidden" json="contract_sing_att_id" name="contract_sing_att_id" id="hid_contract_sing_att_id" value="${rst.contract_sing_att_id}">
											  			</td>
											  		</tr>
											  		<tr>
											  			<td>
											  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_contract_sing_att_id">
																<tr class="fixedRow">
																	<th align="center">序号</th>
																	<th nowrap align="center" dbname="att_path" >附件名称</th>
																	<th nowrap align="center" dbname="cre_name" >上传人</th>
																	<th nowrap align="center" dbname="cre_time" >上传时间</th>
																	<th nowrap align="center" >操作</th>
															   </tr>
															</table>
											  			</td>
											  		</tr>
											  	</table>
											  </div>
											  <div id="tab4">
											  	<table style="width:100%">
											  		<tr>
											  			<td>
											   				<!--  请选择项目交接附件： -->
											   				<button type="button" class="layui-btn uploadFile" id="project_handover_att_id" lay-data="{id:'project_handover_att_id'}">上传</button></div>
														  <input type="hidden" json="project_handover_att_id" name="project_handover_att_id" id="hid_project_handover_att_id" value="${rst.project_handover_att_id}">
											  			</td>
											  		</tr>
											  		<tr>
											  			<td>
											  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_project_handover_att_id">
																<tr class="fixedRow">
																	<th align="center">序号</th>
																	<th nowrap align="center" dbname="att_path" >附件名称</th>
																	<th nowrap align="center" dbname="cre_name" >上传人</th>
																	<th nowrap align="center" dbname="cre_time" >上传时间</th>
																	<th nowrap align="center" >操作</th>
															   </tr>
															</table>
											  			</td>
											  		</tr>
											  	</table>
											  </div>
											  <div id="tab5">
											  		<table style="width:100%">
											  			<tr>
												  			<td><!-- 请选择材料申购附件： -->
													           <button type="button" class="layui-btn uploadFile" id="material_apply_att_id" lay-data="{id:'material_apply_att_id'}">上传</button></div>
															   <input type="hidden" json="material_apply_att_id" name="material_apply_att_id" id="hid_material_apply_att_id" value="${rst.material_apply_att_id}">
												  			</td>
												  		</tr>
												  		<tr>
												  			<td>
												  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_material_apply_att_id">
																	<tr class="fixedRow">
																		<th align="center">序号</th>
																		<th nowrap align="center" dbname="att_path" >附件名称</th>
																		<th nowrap align="center" dbname="cre_name" >上传人</th>
																		<th nowrap align="center" dbname="cre_time" >上传时间</th>
																		<th nowrap align="center" >操作</th>
																   </tr>
																</table>
												  			</td>
												  		</tr>
											  		</table>
											  </div>
											  <div id="tab6">
											  		<table style="width:100%">
											  			<tr>
													  		<td><!--  请选择安装合同签订附件： -->
													   			<button type="button" class="layui-btn uploadFile" id="install_contract_sing_att_id" lay-data="{id:'install_contract_sing_att_id'}">上传</button></div>
														        <input type="hidden" json="install_contract_sing_att_id" name="install_contract_sing_att_id" id="hid_install_contract_sing_att_id" value="${rst.install_contract_sing_att_id}">
													  		</td>
													  	</tr>
													  	<tr>
													  			<td>
													  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_install_contract_sing_att_id">
																		<tr class="fixedRow">
																			<th align="center">序号</th>
																			<th nowrap align="center" dbname="att_path" >附件名称</th>
																			<th nowrap align="center" dbname="cre_name" >上传人</th>
																			<th nowrap align="center" dbname="cre_time" >上传时间</th>
																			<th nowrap align="center" >操作</th>
																	   </tr>
																	</table>
													  			</td>
													  		</tr>
											  		</table>
											  </div>
											  <div id="tab7">
											  	<table style="width:100%">
											  		<tr>
												  		<td>
												           <!-- 请选择大货下单附件： -->
												           <button type="button" class="layui-btn uploadFile" id="goods_order_att_id" lay-data="{id:'goods_order_att_id'}">上传</button></div>
														  <input type="hidden" json="goods_order_att_id" name="goods_order_att_id" id="hid_goods_order_att_id" value="${rst.goods_order_att_id}">
												  		</td>
												  	</tr>
												  	<tr>
											  			<td>
											  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_goods_order_att_id">
																<tr class="fixedRow">
																	<th align="center">序号</th>
																	<th nowrap align="center" dbname="att_path" >附件名称</th>
																	<th nowrap align="center" dbname="cre_name" >上传人</th>
																	<th nowrap align="center" dbname="cre_time" >上传时间</th>
																	<th nowrap align="center" >操作</th>
															   </tr>
															</table>
											  			</td>
												  	</tr>
											  	</table>
											  </div>
											  <div id="tab8">
											  	<table style="width:100%">
											  		<tr>
												  		<td><!--  请选择发货附件： -->
												  	 	<button type="button" class="layui-btn uploadFile" id="visa_att_id" lay-data="{id:'visa_att_id'}">上传</button></div>
														  <input type="hidden" json="visa_att_id" name="visa_att_id" id="hid_visa_att_id" value="${rst.visa_att_id}">
												  		</td>
												  	</tr>
												  	<tr>
												  			<td>
												  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_visa_att_id">
																	<tr class="fixedRow">
																		<th align="center">序号</th>
																		<th nowrap align="center" dbname="att_path" >附件名称</th>
																		<th nowrap align="center" dbname="cre_name" >上传人</th>
																		<th nowrap align="center" dbname="cre_time" >上传时间</th>
																		<th nowrap align="center" >操作</th>
																   </tr>
																</table>
												  			</td>
												  		</tr>
											  		</table>
											  </div>
											  <div id="tab9">
											  		<table style="width:100%">
												  		<tr>
													  		<td><!-- 请选择签证附件： -->
													  	  		<button type="button" class="layui-btn uploadFile" id="send_pro_att_id" lay-data="{id:'send_pro_att_id'}">上传</button></div>
														        <input type="hidden" json="send_pro_att_id" name="send_pro_att_id" id="hid_send_pro_att_id" value="${rst.send_pro_att_id}">
													  		</td>
												  		</tr>
												  		<tr>
												  			<td>
												  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_send_pro_att_id">
																	<tr class="fixedRow">
																		<th align="center">序号</th>
																		<th nowrap align="center" dbname="att_path" >附件名称</th>
																		<th nowrap align="center" dbname="cre_name" >上传人</th>
																		<th nowrap align="center" dbname="cre_time" >上传时间</th>
																		<th nowrap align="center" >操作</th>
																   </tr>
																</table>
												  			</td>
												  		</tr>
											  		</table>
											  </div>
											  <div id="tab10">
												  	<table style="width:100%">
												  		<tr>
												  		<td>
												          <!--  请选择补货附件： -->
												          <button type="button" class="layui-btn uploadFile" id="replenishment_att_id" lay-data="{id:'replenishment_att_id'}">上传</button></div>
														  <input type="hidden" json="replenishment_att_id" name="replenishment_att_id" id="hid_replenishment_att_id" value="${rst.replenishment_att_id}">
												  		</td>
												  	</tr>
												  	<tr>
											  			<td>
											  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_replenishment_att_id">
																<tr class="fixedRow">
																	<th align="center">序号</th>
																	<th nowrap align="center" dbname="att_path" >附件名称</th>
																	<th nowrap align="center" dbname="cre_name" >上传人</th>
																	<th nowrap align="center" dbname="cre_time" >上传时间</th>
																	<th nowrap align="center" >操作</th>
															   </tr>
															</table>
											  			</td>
											  		</tr>
											  	</table>
											  </div>
											  <div id="tab11">
												  <table style="width:100%">
												  	<tr>
												  		<td>
												          <!-- 请选择维修附件： -->
												          <button type="button" class="layui-btn uploadFile" id="service_att_id" lay-data="{id:'service_att_id'}">上传</button></div>
														  <input type="hidden" json="service_att_id" name="service_att_id" id="hid_service_att_id" value="${rst.service_att_id}">
												  		</td>
												  	</tr>
												  	<tr>
											  			<td>
											  				<table   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_service_att_id">
																<tr class="fixedRow">
																	<th align="center">序号</th>
																	<th nowrap align="center" dbname="att_path" >附件名称</th>
																	<th nowrap align="center" dbname="cre_name" >上传人</th>
																	<th nowrap align="center" dbname="cre_time" >上传时间</th>
																	<th nowrap align="center" >操作</th>
															   </tr>
															</table>
											  			</td>
											  		</tr>
												  </table>
											  </div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</form>
	<form name="backForm" method="post">
		<input type="hidden" name="pageNo" value="${pageNo }">
		<input type="hidden" id="selRowId" name="selRowId" value="${entry.PROJECT_ID}">
	</form>
	<div style="width:100%;height:52px;z-index: 1001;"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;z-index: 1002;">
		  <button class="img_input">
			<label for="save" style="margin-bottom:0px">
				<img src="/dareos/styles/newTheme/images/icon/baocun.png"
					class="icon_font">
				<input id="save" type="button" class="btn" value="保存" style="line-height: 18px;">
			</label>
		</button>
		<button class="img_input" >
               <label onclick="closeDialog()" style="margin-bottom:0px">
                   <img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
                   <input type="button" class="btn" value="返回" style="line-height: 18px;" >
               </label>
       	</button>
	</div>
	</body>
	<script type="text/javascript">
		<c:forEach items="${entry.entrySun}" var="rst" varStatus="row">
			var arrs = [
				'${rst.PROJECT_PAYABLE_ID}',//0
				'${rst.PAYABLE_STAGE}',//1
				'${rst.PAYABLE_CONDITION}',//2
				'${rst.PAYABLE_DATE}',//3
				'${rst.PAYABLE_RATE}',//4
				'${rst.PAYABLE_AMOUNT}',//5
				'${rst.REALITY_AMOUNT}',//6
				'${rst.PROJECT_ID}',//7
				'${rst.REQUEST_AMOUNT_DATE}',//8
				'${rst.INVOICE_DATE}',//9
				'${rst.INVOICE_AMOUNT}',//10
				'${rst.BACK_AMOUNT_DATE}',//11
				'${rst.BACK_AMOUNT}',//12
				'${rst.OUT_PRD_AMOUNT}'//13
				];
		    addRow(arrs);
		</c:forEach>
	</script>
	<script type="text/javascript">
		//通配框与下拉框
		SelDictShow("project_type","BS_183","${entry.PROJECT_TYPE}","");
		//多文件上传
		var url='toMxEdit';
		displayDownFile('sample_att_id','${entry.listPath1}',url);
		displayDownFile('sample_houses_att_id','${entry.listPath2}',url);
		displayDownFile('contract_sing_att_id','${entry.listPath3}',url);
		displayDownFile('project_handover_att_id','${entry.listPath4}',url);
		displayDownFile('material_apply_att_id','${entry.listPath5}',url);
		displayDownFile('install_contract_sing_att_id','${entry.listPath6}',url);
		displayDownFile('goods_order_att_id','${entry.listPath7}',url);
		displayDownFile('send_pro_att_id','${entry.listPath8}',url);
		displayDownFile('visa_att_id','${entry.listPath9}',url);
		displayDownFile('replenishment_att_id','${entry.listPath10}',url);
		displayDownFile('service_att_id','${entry.listPath11}',url);
	</script>
</html>