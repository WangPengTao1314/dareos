<!-- 
 *@module 项目管理
 *@func 招投标明细
 *@version 1.0
 *@author 王朋涛
 *  -->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%> 
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/pages/common/uploadfile/uploadfile_pro.jsp"%> 
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/skin/jedate.css">
		<script type=text/javascript src="${ctx}/scripts/core/jquery-1.6.3.min.js"></script>
		<title>查询菜单</title>
	</head>
	<body>
		<form name="mainForm" id="mainForm" >
			<table  width="100%" border="0" cellpadding="0"
				cellspacing="0" class="detail">
				<tr>
					<td class="detail2" style="border-bottom: none;">
						<table id="jsontb" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail" style="border-bottom: 1px solid #e8edf2;">
							<tr>
								<td class="detail2">
									<table style="width:100%;padding: 5px 0px;;">
										<tr>
											<td colspan="4" class="title">订单信息</td>
											<td colspan="4" align="right">
											    <img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="position:relative;left:58px;vertical-align: middle;" id="imgsz">
												<input type="button"  id="ipt"  value="收起" style="border: none;color:#bfbfbf;outline:none;background-color:#fff;width:9%;height:22px;text-align:left;" onclick='changeinput(this)'>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr id="myTable">
								<td class="detail2">
									<table cellspacing="0" cellpadding="0" width="98%" height="98%" id="myTable"  class="detail3"
										style="background-color: #fff; padding: 0 0 0 2%; border-collapse: separate; border-spacing: 10px;border-top:1px solid #e8edf2;;border-left:1px solid #e8edf2;;border-right:1px solid #e8edf2;;margin:0 auto">
										<tr>							
								<td align="left" colspan="4" style="font-size: 14px; font-weight: 600;color: #a8afb3;overflow:hidden">
								<span style="float:left;font-size: 16px;font-weight: 600;color: #54698d;">基本信息</span>
								<div class="dashed">&nbsp</div>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>投招标编号 &nbsp;:</div>
									<input  class="readonly gdtd_select_input" value="${entry.TENDERING_NO}" READONLY>
			 					</td> 
								<td class="gdtd_tb">
									<div>招标项目名称&nbsp;:</div>
									<input  class="readonly gdtd_select_input" value="${entry.PROJECT_NAME}"  READONLY>
								</td> 
								<td class="gdtd_tb">
									<div>招标项目编号 &nbsp;:</div>
									<input  class="readonly gdtd_select_input" value="${entry.PROJECT_NO}" READONLY>   
								</td> 
								<td class="gdtd_tb">
									<div>项目类型&nbsp;:</div>
									<input  class="readonly gdtd_select_input" value="${entry.PROJECT_TYPE}" READONLY> 
								</td> 
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>招标单位 &nbsp;:</div>
									<input  class=" readonly gdtd_select_input"  value="${entry.TENDERING_UNIT}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>开标时间 &nbsp;:</div>
									<input  class="readonly gdtd_select_input" value="${entry.OPEN_TIME}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>项目地点 &nbsp;:</div>
									<input  class="gdtd_select_input"  value="${entry.PROJECT_ADDRESS}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>工期(月) &nbsp;:</div>
									<input  class="readonly gdtd_select_input"  value="${entry.CONSTRUC_PERIOD}" READONLY>
								</td>
							</tr>
							<tr>
								<td class="gdtd_tb">
									<div>负责人&nbsp;:</div>
									<input  class="readonly gdtd_select_input" value="${entry.CHARGE_PERSON}"	READONLY>
								</td>
								<td class="gdtd_tb">
									<div>预算金额 (元)&nbsp;:</div>
									<input  class="readonly gdtd_select_input"  value="${entry.BUDGET_AMOUNT}" READONLY>
								</td>
							 	<!-- <td align="right" class="gdtd"></td> -->
								<td class="gdtd_tb">
									<div>投标保证金 (元)&nbsp;:</div>
									<input  class="readonly gdtd_select_input"  value="${entry.DEPOSIT}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>保证金回收时间 :</div>
									<input  class=" readonly gdtd_select_input" value="${entry.DEPOSIT_EXPIRE_DATE}" READONLY>
								</td>
							</tr>
							<tr class="ysfs">
								<td class="gdtd_tb">
									<div>甲方联系人 &nbsp;:</div>
									<input  class="readonly gdtd_select_input"  value="${entry.CONTACT_A_PERSON}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>采购内容:</div>
									<input  class="readonly gdtd_select_input"  value="${entry.PROCURE_CONTENT}" READONLY>
								</td>
								<td class="gdtd_tb">
									<div>联系电话 &nbsp;:</div>
									<input  class="readonly gdtd_select_input" value="${entry.TEL}" READONLY>
								</td>
							</tr>
							<tr>
								<td>
									<div>备注&nbsp;:</div>
									 <textarea style="width: 187.5%;"  rows="5"  READONLY>${entry.REMARK}</textarea> 
								</td>
							</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="detail2" style="border:none">
						<div style="width:40%;float: left;height:40px;font-size:16px;font-weight:500;line-height:50px;border-bottom: none;padding-left: 1%">明细信息</div>
						<input type="hidden" json="uploadExcel" name="uploadExcel" id="hid_uploadExcel" value="">
					</td>
				</tr>
				<tr>
					<td>
						<table id="view_uploadExcel"  width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" >
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
		<!-- 底部固定部分 -->
		<div style="width:100%;height:52px"></div>
		<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
			<button class="img_input" >
	               <label onclick="closeDialog()">
	                   <img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
	                   <input  type="button" class="btn" value="返回"  >
	               </label>
	        </button>
		</div>
		</form>
		<!--占位用table，以免显示数据被浮动层挡住-->
		<table width="100%" height="25px">
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
	<script>
	displayDownFile('uploadExcel','${entry.paths}'); 
	//
	function changeinput(e){
		var newinput = document.getElementById('ipt')
		var newmytable =document.getElementById('myTable')
		var new_img = document.getElementById('imgsz')  
		if(newmytable.style.display == 'none'){
			newmytable.style.display = '';
			newinput.value="收起"
			new_img.style.transform="rotate(0deg)"
		/*	imgsz.style.transform="rotate(0deg)"*/
		}else{
			newmytable.style.display='none';
			newinput.value = "展开";
			/*transform:rotate(180deg);*/
			new_img.style.transform="rotate(180deg)";
			
		}
	 }
</script>
	</body>
</html>
