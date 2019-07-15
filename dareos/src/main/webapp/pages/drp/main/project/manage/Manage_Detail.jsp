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
		<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>
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
		<div id="mainDiv">
		<form name="mainForm" id="mainForm">
			<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
				<tr>
					<td class="detail2">
					<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">项目信息</div>
							<div class="toggle">
							<input type="button"  id="ipt"  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;" onclick='changeinput(this)'>
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
								<span style="float:left" class="font-color">基本信息</span>
								<div class="dashed">&nbsp</div>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>项目编号 &nbsp;:</div>
									<input  class="gdtd_select_input"   value="${entry.PROJECT_NO}" READONLY>
								</td> 
								<td class="gdtd_tb">
									<div>项目名称 &nbsp;:</div>
									<input  class="gdtd_select_input"  value="${entry.PROJECT_NAME}"  READONLY>
								</td> 
								<td class="gdtd_tb">
									<div>项目类型:</div>
									<input  class="gdtd_select_input"  value="${entry.PROJECT_TYPE}"  READONLY>
								</td> 
								<td class="gdtd_tb">
									<div>合同签订单位 &nbsp;:</div>
									<input  class="gdtd_select_input"   value="${entry.CONTRACT_UNIT}"	READONLY>
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>收货地址 &nbsp;:</div>
									<input  class="gdtd_select_input"  value="${entry.ADDRESS}" READONLY>
								</td>
								 <td class="gdtd_tb">
								 	<div>招投标编号 :</div>
									<input  class="gdtd_select_input" value="${entry.TENDERING_NO }" READONLY >
								</td> 
								<td class="gdtd_tb">
									<div>单位地址:</div>
									<input  class="gdtd_select_input"   value="${entry.UNIT_ADDRESS}"  READONLY>
								</td>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>开工时间 &nbsp;:</div>
									 <input  class="gdtd_select_input"  value="${entry.START_DATE}" READONLY>
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>质保期 &nbsp;:</div>
									<input  class="gdtd_select_input"  value="${entry.WARRANTY_PERIOD}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>银行账号:</div>
									<input  class="gdtd_select_input"  value="${entry.BANK_ACCT}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>开户银行:</div>
									<input  class="gdtd_select_input"  value="${entry.BANK_NAME}" READONLY>
								</td>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>竣工验收时间:</div>
									<input  class="gdtd_select_input" value="${entry.CHECK_ACCEPT}" READONLY>
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>甲方联系人 &nbsp;:</div>
									<input  class="gdtd_select_input"   value="${entry.CONTACT_A_PERSON}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>税务登记号:</div>
									<input  class="gdtd_select_input" value="${entry.AX_BURDEN}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>供货单位:</div>
									<input  class="gdtd_select_input" value="${entry.SUPPLY_UNIT}" READONLY>
								</td>
								<td class="gdtd_tb" style="white-space: nowrap;">
									<div>质保金到期时间:</div>
									<input  class="gdtd_select_input" value="${entry.WARRANTY_AMOUNT_EXPIRE_DATE}" READONLY>
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>结算金额:</div>
									<input  class="gdtd_select_input"  value="${entry.SETTLE_AMOUNT}"  READONLY>
								</td> 
								<td class="gdtd_tb">
									<div>业务员:</div>
									<input  class="gdtd_select_input"  value="${entry.SALESMAN}"  READONLY>
								</td>
								<td class="gdtd_tb">
									<div>项目经理:</div>
									<input  class="gdtd_select_input"  value="${entry.PROJECT_MANAGER}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>结算时间:</div>
									<input  class="gdtd_select_input"  value="${entry.SETTLE_DATE}" READONLY>
								</td>
							</tr>
							<tr>
								<td>
									<div>颜色及材质:</div>
									<textarea  style="width: 189.5%;" rows="3" READONLY>${entry.COLOUR_AND_MATERIAL}</textarea>
								</td>
								<td align="right" class="gdtd"></td>
								<td class="gdtd_tb">
									<div>合同数量:</div>
									<input  class="gdtd_select_input"  value="${entry.CONTRACTS_NUMBER}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>实际出货总金额:</div>
									 <input  class="gdtd_select_input"  value="${entry.OUT_PRD_AMOUNT}" READONLY>
								</td>
							</tr>
							<tr>
								<td>
									<div>五金配置:</div>
									<textarea  style="width:189.5%;" rows="3"  READONLY>${entry.HARDWARE_CONFIG}</textarea>
								</td>
								<td class="gdtd"></td>
								<td>
									<div>用材要求:</div>
									<textarea    style="width: 189.5%;" rows="3" READONLY>${entry.MATERIAL_REQUIREMENT}</textarea>
								</td>
								<td class="gdtd_tb"> 
									<%-- <div>工艺图纸:</div>
									<input    class="gdtd_select_input"  value="${entry.PROCESS_DRAWING}" > --%>
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>涂装或表面效果要求:</div>
									<textarea  style="width: 189.5%;" rows="3" READONLY>${entry.APPEARANCE_REQUIREMENT}</textarea>
								</td>
								<td  class="gdtd"></td>
								<td class="gdtd_tb">
									<div>产品包装及标识要求:</div>
									<textarea   style="width: 189.5%;" rows="3" READONLY>${entry.PACKAGING_REQUIREMENTS}</textarea>
								</td>
								<td  class="gdtd"></td>
							</tr>
							<tr>
								
								<td>
									<div>备注:</div>
									 <textarea  style="width: 189.5%;" rows="3" READONLY>${entry.REMARK}</textarea>
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
					<td class="detail2">
						<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:60px">付款阶段信息</div>
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
									<th width="3%" align="center">序号</th>
									<th width="120"	align="center" >收款阶段</th>
									<th width=120" align="center">收款条件</th>
									
									<th width=120" align="center">请款时间</th>
									<th width=120" align="center">开票时间</th>
									<th width=120" align="center">开票金额(元)</th>
									<th width=120" align="center">汇款时间</th>
									<th width=120" align="center">回款金额(元)</th>
									<th width=120" align="center">出货金额(元)</th>
									
									<th width="100" align="center">收款日期</th>
									<th width="100" align="center">收款比例(%)</th>
									<th width="100" align="center">收款金额(元)</th>
									<th width="100" align="center">实收金额(元)</th>
								</tr>
								<c:forEach items="${entry.entrySun}" var="rst" varStatus="row">
									<tr>
										<td align="center">
											${row.count}
										</td>
										<td align="center">
											${rst.PAYABLE_STAGE}
										</td>
										<td align="center">
											${rst.PAYABLE_CONDITION}
										</td>
										<td align="center">
											${rst.REQUEST_AMOUNT_DATE}
										</td>
										<td align="center">
											${rst.INVOICE_DATE}
										</td>
										<td align="center">
											${rst.INVOICE_AMOUNT}
										</td>
										<td align="center">
											${rst.BACK_AMOUNT_DATE}
										</td>
										<td align="center">
											${rst.BACK_AMOUNT}
										</td>
										<td align="center">
											${rst.OUT_PRD_AMOUNT}
										</td>
										<td align="center">
											${rst.PAYABLE_DATE}
										</td>
										<td align="center">
											${rst.PAYABLE_RATE}
										</td>
										<td align="center">
											${rst.PAYABLE_AMOUNT}
										</td>
										<td align="center">
											${rst.REALITY_AMOUNT}
										</td>  
									</tr>
								</c:forEach>
								<c:if test="${empty entry.entrySun}">
									<tr>
										<td height="25" colspan="13" align="center" class="lst_empty">
											&nbsp;无相关信息&nbsp;
										</td>
									</tr>
								</c:if> 
							</table>
					</td>
				</tr>
			</table>
		</form>
	</div>
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
									<!--  <input   type="submit" class="btn" value="保存(S)" title="Alt+S" accesskey="S" > -->
									 <input   type="text" hidden="true" name="project_id" value="${entry.PROJECT_ID}" >
									 <input   type="text" hidden="true" name="state" id="STATE" value="${entry.STATE}" >
								</td>
							</tr>
							<tr>
								<td>
									<ul id="tabs" class="nav nav-pills nav-justified step step-arrow">
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
										  			<!-- <button type="button" class="layui-btn uploadFile" id="sample_att_id" lay-data="{id:'sample_att_id'}">上传</button></div> -->
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
											   	  <!-- <button type="button" class="layui-btn uploadFile" id="sample_houses_att_id" lay-data="{id:'sample_houses_att_id'}">上传</button> -->
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
									  	 		  <!-- <button type="button" class="layui-btn uploadFile" id="contract_sing_att_id" lay-data="{id:'contract_sing_att_id'}">上传</button></div> -->
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
									   				<!-- <button type="button" class="layui-btn uploadFile" id="project_handover_att_id" lay-data="{id:'project_handover_att_id'}">上传</button></div> -->
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
											           <!-- <button type="button" class="layui-btn uploadFile" id="material_apply_att_id" lay-data="{id:'material_apply_att_id'}">上传</button></div> -->
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
											   			<!-- <button type="button" class="layui-btn uploadFile" id="install_contract_sing_att_id" lay-data="{id:'install_contract_sing_att_id'}">上传</button></div> -->
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
										           <!-- <button type="button" class="layui-btn uploadFile" id="goods_order_att_id" lay-data="{id:'goods_order_att_id'}">上传</button></div> -->
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
										  	 		<!-- <button type="button" class="layui-btn uploadFile" id="visa_att_id" lay-data="{id:'visa_att_id'}">上传</button></div> -->
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
											  	  		<!-- <button type="button" class="layui-btn uploadFile" id="send_pro_att_id" lay-data="{id:'send_pro_att_id'}">上传</button></div> -->
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
										          <!-- <button type="button" class="layui-btn uploadFile" id="replenishment_att_id" lay-data="{id:'replenishment_att_id'}">上传</button></div> -->
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
										          <!-- <button type="button" class="layui-btn uploadFile" id="service_att_id" lay-data="{id:'service_att_id'}">上传</button></div> -->
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
	<form name="backForm" method="post">
		<input type="hidden" name="pageNo" value="${pageNo }">
		<input type="hidden" id="selRowId" name="selRowId" value="${entry.PROJECT_ID}">
	</form>
	<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
		  <button class="img_input">
			<label for="save">
				<img src="/dareos/styles/newTheme/images/icon/baocun.png"
					class="icon_font">
				<input id="save" type="button" class="btn" value="保存">
			</label>
		</button>
		<button class="img_input" >
               <label onclick="closeDialog()">
                   <img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
                   <input type="button" class="btn" value="返回"  >
               </label>
       	</button>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	$("#content div").hide();  
	$("#tabs li:eq(0)").addClass("active");
	$("#content div:eq(0)").fadeIn();  
	var state=$("#STATE").val();
	 $('#tabs a').click(function(e) {
		e.preventDefault();        
		$("#content div").hide();  
		$("#tabs li").removeClass("active");
		$(this).parent().addClass("active");
			$('#' + $(this).attr('title')).fadeIn();  
	}); 
});
function changeinput(e){
	var newinput = document.getElementById('ipt')
	var newmytable =document.getElementById('myTable')
	if(newmytable.style.display == 'none'){
		newmytable.style.display = '';
		newinput.value="收起"
		(imgsz).style.transform="rotate(0deg)"
	    
	}else{
		newmytable.style.display='none';
		newinput.value = "展开";
		/*transform:rotate(180deg);*/
		(imgsz).style.transform="rotate(180deg)"
		
	}
        
 }

	//多文件上传
	displayDownFile('sample_att_id','${entry.listPath1}');
	displayDownFile('sample_houses_att_id','${entry.listPath2}');
	displayDownFile('contract_sing_att_id','${entry.listPath3}');
	displayDownFile('project_handover_att_id','${entry.listPath4}');
	displayDownFile('material_apply_att_id','${entry.listPath5}');
	displayDownFile('install_contract_sing_att_id','${entry.listPath6}');
	displayDownFile('goods_order_att_id','${entry.listPath7}');
	displayDownFile('send_pro_att_id','${entry.listPath8}');
	displayDownFile('visa_att_id','${entry.listPath9}');
	displayDownFile('replenishment_att_id','${entry.listPath10}');
	displayDownFile('service_att_id','${entry.listPath11}');
</script>
</html>