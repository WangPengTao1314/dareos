<!-- 
 *@module 项目管理
 *@func 招投标编辑
 *@version 1.0
 *@author 王朋涛
 *  -->
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
		<script type="text/javascript" src="${ctx}/pages/drp/main/project/tendering/Tendering_Edit.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
		<title>招投标信息编辑</title>
		<style>
		
		table tr{
		height:50px;
		
		}
		.child{overflow-x: auto}
		.dashed{border-bottom: 1px solid #e8edf2;width: 98%;}	
		</style>
	</head>
	<body class="add_demoone" style=" overflow-y:auto; overflow-x:auto;" >
		<!-- <span class="add_demospan">当前位置:工程管理>项目管理</span> -->
		<form name="mainForm" id="mainForm" >
			<table id="jsontb" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
				<tr>
					<td>
						<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
							<%-- <tr>
								<td class="detail2">
								<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">招投标信息</div>
										<div class="toggle">
										<input type="button"  id="ipt1"  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;" onclick='changeinput(this,1)'>
										<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="vertical-align: middle;" id="imgsz1">
										</div>
								</td>
							</tr> --%>
							<tr id="myTable1">
								<td class="detail2" >
									<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable"  class="detail3"
										style="background-color: #fff; padding: 0 0 0 2%; border-collapse: separate; border-spacing: 10px;border-top:1px solid #e8edf2;;border-left:1px solid #e8edf2;;border-right:1px solid #e8edf2;;margin:0 auto">
										<tr>							
											<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
											<span style="float:left" class="dashed font-color">基本信息</span>
										</tr>
										<tr>
											<!-- <td align="right" class="gdtd"></td>  -->
											<td class="gdtd_tb">
												<div>投招标编号 &nbsp;:</div>
												<input  class="gdtd_select_input" name="tendering_id" hidden="true" json="tendering_id" id="tendering_id" type="text"  maxlength="32" value="${entry.TENDERING_ID}">
												<input  class="gdtd_select_input" name="tendering_no" json="tendering_no" placeholder="自动生成" id="tendering_no" type="text" inputtype="string"   label="招投标编号" 	autocheck="true" maxlength="30" value="${entry.TENDERING_NO}">
						 					</td> 
											<!-- <td align="right" class="gdtd"> </td>  -->
											<td class="gdtd_tb">
												<div>招标项目名称&nbsp;:</div>
												<textarea class="gdtd_select_input" name="PROJECT_NAME" json="project_name" id="PROJECT_NAME" type="text" 
												  autocheck="true" inputtype="string" label="招标项目名称" mustinput="true"   maxlength="200">${entry.PROJECT_NAME}</textarea>
											
											</td> 
											<!-- <td align="right" class="gdtd"></td>  -->
											<td class="gdtd_tb">
												<div>招标项目编号 &nbsp;:</div>
												<input  class="gdtd_select_input" name="project_no" json="project_no" id="project_no" type="text" 
												   autocheck="true" inputtype="text" label="招投标项目编号" maxlength="30" mustinput="true" value="${entry.PROJECT_NO}" >   
											</td> 
											<!-- <td align="right" class="gdtd"></td>  -->
											<td class="gdtd_tb">
												<div>项目类型&nbsp;:</div>
												<select name="project_type" class="gdtd_select_input" json="project_type"  label="项目类型" autocheck="true"
													id="PROJECT_TYPE" mustinput="true" inputtype="text" ></select>
											</td> 
										</tr>
										<tr>
											<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>招标单位 &nbsp;:</div>
												<textarea class="gdtd_select_input"  name="tendering_unit" json="tendering_unit"   
												type="text" inputtype="string"   label="招标单位 " maxlength="200">${entry.TENDERING_UNIT}</textarea>
								
											</td>
											<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>开标时间 &nbsp;:</div>
												<input  class="gdtd_select_input" name="open_time" type="text" json="open_time"  id="open_time" onclick="SelectDate();"
												class="jeinput" placeholder="年月日选择" 
												maxlength="20" value="${entry.OPEN_TIME}" READONLY>
												<!-- label="开标日期" inputtype="text" autocheck="true" mustinput="true" -->
												<img class="select_gif" align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
													onclick="SelectDate(open_time);"/>
											</td>
											<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>项目地点 &nbsp;:</div>
												<textarea class="gdtd_select_input"name="project_address" json="project_address" type="text" label="项目地点" 	
												autocheck="true" maxlength="500">${entry.PROJECT_ADDRESS}</textarea>
											</td>
											<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>工期(月) &nbsp;:</div>
												<input  class="gdtd_select_input" name="construc_period"  json="construc_period" type="text" maxlength="20" inputtype="int"   label="工期" 	autocheck="true" value="${entry.CONSTRUC_PERIOD}">
											</td>
										</tr>
										<tr>
											<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>负责人&nbsp;:</div>
												<input  class="gdtd_select_input" name="charge_person" json="charge_person"   type="text" inputtype="string"   label="负责人 " maxlength="50" value="${entry.CHARGE_PERSON}"	>
											</td>
											<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>预算金额 (元)&nbsp;:</div>
												<input  class="gdtd_select_input" name="budget_amount"  json="budget_amount" type="text" inputtype="int"   label="预算金额" 	autocheck="true" maxlength="10" value="${entry.BUDGET_AMOUNT}">
											</td>
										 	<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>投标保证金 (元)&nbsp;:</div>
												<input  class="gdtd_select_input" name="deposit"  json="deposit" type="text" inputtype="int"   label="投标保证金" 	autocheck="true" maxlength="10" value="${entry.DEPOSIT}">
											</td>
											<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>保证金回收时间 :</div>
												<input  class="gdtd_select_input" name="deposit_expire_date" type="text" json="deposit_expire_date"  id="deposit_expire_date" onclick="SelectDate();"
												class="jeinput" placeholder="年月日选择" 
												 value="${entry.DEPOSIT_EXPIRE_DATE}" READONLY>
												 <!-- label="保证金回收时间" inputtype="text" autocheck="true" mustinput="true" -->
												<img class="select_gif" align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" 
													onclick="SelectDate(deposit_expire_date);"/>
											</td>
										</tr>
										<tr class="ysfs">
											<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>甲方联系人 &nbsp;:</div>
												<input  class="gdtd_select_input" name="contact_a_person"  json="contact_a_person" type="text" maxlength="20" inputtype="String"   label="甲方联系人" 	autocheck="true" value="${entry.CONTACT_A_PERSON}">
											</td>
											<!-- <td align="right" class="gdtd"></td> -->
											<!-- <td align="right" class="gdtd"></td> -->
											<td class="gdtd_tb">
												<div>联系电话 &nbsp;:</div>
												<input  class="gdtd_select_input" name="tel"  json="tel" type="text" maxlength="20" inputtype="String"   label="联系电话" 	autocheck="true" value="${entry.TEL}">
											</td>
											<td>
												<div>采购内容:</div>
												<textarea style="width: 191.5%;" name="procure_content" json="procure_content"  type="text" inputtype="string"   label="采购内容"  maxlength="4000" rows="1" >${entry.PROCURE_CONTENT}</textarea> 
											</td>
										</tr>
										<tr>
											<td>
												<div>备注&nbsp;:</div>
												 <textarea style="width: 191.5%;" name="remark" json="remark"  type="text" inputtype="string"   label="备注"  maxlength="4000" rows="1" >${entry.REMARK}</textarea> 
											</td>
											<td class="gdtd_tb">									
											</td>
											<td class="gdtd_tb">									
											</td>
										</tr>
										<tr></tr>
									</table>
								</td>
							</tr>
						</table>
						<table width="100%" border="0" cellpadding="5" cellspacing="0" class="lst">
							<tr>
								<td class="detail2" style="border-bottom: none">
									<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px">附件信息
									<button type="button" class="layui-btn uploadFile" id="uploadFileid" lay-data="{id:'uploadFileid'}">上传</button></div>
									<input type="hidden" json="uploadExcel" name="uploadExcel" id="hid_uploadFileid">
									</div>
								</td>
							</tr>
					     </table>
						 <table   align="center" width="100%" border="0" cellpadding="0" cellspacing="0" class="lst"  id="view_uploadFileid">
							<tr class="fixedRow">
								<th align="center">序号</th>
								<th nowrap align="center" dbname="att_path" >附件名称</th>
								<th nowrap align="center" dbname="cre_name" >上传人</th>
								<th nowrap align="center" dbname="cre_time" >上传时间</th>
								<th nowrap align="center" dbname="" >操作</th>
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
		<div style="width:100%;height:52px"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
		<button class="img_input" >
                <label for='save'>
                    <img src="${ctx}/styles/${theme}/images/icon/baocun.png"  class="icon_font">
                    <input   id="save" type="button" class="btn" value="保存" >
                </label>
	    </button>
		<button class="img_input" >
	               <label onclick="closeDialog()">
	                   <img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
	                   <input  type="button" class="btn" value="返回"  >
	               </label>
	      </button>
	</div>
	<%@ include file="/pages/common/uploadfile/uploadfile_pro.jsp"%> 
	<script type="text/javascript">
	     //单一上传
	     $(function(rest){
	    	//console.log('rest_:'+rest);
			SelDictShow("PROJECT_TYPE","BS_183","${entry.PROJECT_TYPE}","");
			var url='toEdit';
			displayDownFile('uploadFileid','${entry.paths}',url); 
	     });
	</script>
	</body>
</html>
