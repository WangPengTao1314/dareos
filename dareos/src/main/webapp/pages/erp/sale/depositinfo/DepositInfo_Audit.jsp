<!-- 
/**
 * @module 系统管理
 * @func 充值信息
 * @version 1.1
 * @author 郭利伟
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
	<title>充值审核</title>
</head>
<body class="bodycss1">	
<div style="height: 100%">
			<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;left: 0;word-spacing: 8px;">
							<button class="img_input" >
								<label for='audit'>
								<img src="${ctx}/styles/${theme}/images/icon/baocun.png"  class="icon_font">
								<input id="audit" type="button" class="btn" value="审核通过" />
								</label>
							</button>
							<button class="img_input" >
								<label for='back'>
								<img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
								<input id="back" type="button" class="btn" value="退回" />
								</label>
							</button>
							<button class="img_input" >
								<label onclick="closeDialog()">
								<img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
								<input type="button" class="btn" value="返回" />
								</label>
							</button>
			</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail_lst"  style="word-break:break-all; word-wrap:break-all;">
				<tr>
					<td width="15%" align="right"class="detail_label">业务单号：</td>
					<td width="35%" class="detail_content">${rst.depositNo }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">充值类型：</td>
					<td width="35%" class="detail_content">${rst.depositType}&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">经销商：</td>
					<td width="35%" class="detail_content">${rst.channName }&nbsp;</td>
					<td width="15%" align="right"class="detail_label">订单组织：</td>
					<td width="35%" class="detail_content">${rst.ledgerName}&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">充值金额：</td>
					<td width="35%" class="detail_content">${rst.depositAmount }&nbsp;</td>
					<td width="15%" align="right"class="detail_label"></td>
					<td width="35%" class="detail_content"></td>
				</tr>
				<tr>
					<td width="15%" align="right"class="detail_label">附件：</td>
					<td width="35%" class="detail_content" colspan="3">
						<button disabled="disabled" type="button" class="layui-btn uploadFile attPathUploader" id="attPath" lay-data="{id:'attPath'}">上传</button>
						<input type="hidden"  json="attPath" name="attPath" id="hid_attPath" value="${ rst.attPath}">				
						<table class="layui-table" style="width:85%" id="view_attPath"></table>
					</td>
				</tr>
				<tr>
					<td width="15%" align="right" class="detail_label">制单人：</td>
					<td width="35%" class="detail_content">${rst.creName }&nbsp;</td>
					<td width="15%" align="right" class="detail_label">制单时间：</td>
					<td width="35%" class="detail_content">${rst.creTime}&nbsp;</td>
				</tr>
				<tr>
					
					<td width="15%" align="right" class="detail_label">制单部门：</td>
					<td width="35%" class="detail_content">${rst.deptName }&nbsp;</td>
					<td width="15%" align="right" class="detail_label">制单机构：</td>
					<td width="35%" class="detail_content">${rst.orgName }&nbsp;</td>
				</tr>				
				<tr>
					
					<td width="15%" align="right"class="detail_label">更新人：</td>
					<td width="35%" class="detail_content">${rst.updName }</td> 
					<td width="15%" align="right" class="detail_label" >更新时间：</td>
					<td width="35%" class="detail_content">${rst.updTime }&nbsp;</td>
				</tr>
				<tr>
					<td width="15%" align="right" class="detail_label">备注：</td>
					<td width="35%" class="detail_content" colspan="3" style="white-space:normal"> ${rst.remark} </td>
				</tr>
				<tr>
					<td width="15%" align="right" class="detail_label">审核意见：</td>
					<td width="35%" class="detail_content"  colspan="3">
	                     <textarea   id="auditRemark" autocheck="true" inputtype="string"   maxlength="250"   label="审核意见"    rows="4" cols="80%"></textarea>
	                     <input id="selRowId" value="${rst. depositId}" type="hidden">
					   </td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
					 <!--占位用行，以免显示数据被浮动层挡住-->
			        <td height="40px">&nbsp;</td>
			    </tr>
</table>
</div>
</body>
</html>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
 	displayDownFile('attPath','SAMPLE_DIR',false,false);
 	$("#audit").click(function(){
 		auditCommit("审核通过");
 	})
 	$("#back").click(function(){
 		auditCommit("退回");
 	})
 	function auditCommit(type){
 	var remark =$("#auditRemark").val()
	var selRowId = $("#selRowId").val();
	var index = parent.layer.load(1);
	 $.ajax({
	 	url: "audit?depositId="+selRowId+"&remark="+remark+"&type="+type,
		type:"POST",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.layer.close(index);
				showMsgPanelCloseWindow('操作成功','parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
			}else{
				parent.layer.close(index);
				showWarnMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}
</script>