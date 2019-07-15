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
<%@ include file="/commons/qxcommon.jsp"%>
<%@ include file="/pages/common/uploadfile/uploadfile_order.jsp"%>
<%-- <%@ include file="/pages/common/uploadfile/uploadfilenew.jsp"%> --%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<script language="JavaScript"src="${ctx}/pages/common/select/selCommJs.js"></script>
		<script type="text/javascript" src="${ctx}/pages/drp/main/project/manage/Manage_Com.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
		<title>项目指令信息编辑</title>
		<style>
		table tr{
		height:50px;
		}
		</style>
	</head>
	<body style="background-color: #fff;">

		<!--浮动按钮层结束-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<form name="mainForm" id="mainForm">
						<div>
							<table id="jsontb" width="100%" border="0" cellpadding="0"
										cellspacing="0" class="detail">
								<tr>
									<td>
										<button class="img_input">
											<label for="delete">
												<img src="/dareos/styles/newTheme/images/icon/shanchu.png"
													class="icon_font">
												<input id="delete" type="button" class="btn" value="删除">
											</label>
										</button>
										<button class="img_input" >
							                <label for='send'>
							                    <img src="${ctx}/styles/${theme}/images/icon/baocun.png"  class="icon_font">
							                    <input id="send" type="button" class="btn" value="下发" >
							                </label>
										</button>
									</td>
						  			<td>
						  				指令类型&nbsp;：<select name="DIRECTIVE_TYPE" id="DIRECTIVE_TYPE" style="width:173px;height:21px;" 
						  				label="指令类型" autocheck="true"  mustinput="true" inputtype="text" json="DIRECTIVE_TYPE" ></select>
						  				<input name="project_id" hidden="true" json="PROJECT_ID" id="PROJECT_ID"  type="text" value="${params.PROJECT_ID}">
						  			</td>
						  			<td>
						  				<input name="PROJECT_NAME" hidden="true" json="PROJECT_NAME"   type="text" value="${params.PROJECT_NAME}">
						  			</td>
						  			<td>
						  				<input name="PROJECT_NO" hidden="true" json="PROJECT_NO"   type="text" value="${params.PROJECT_NO}">
						  			</td>
									<td>
										<button type="button" class="layui-btn uploadFile" id="jsontF" lay-data="{id:'jsontF'}">上传</button></div>
										<input type="hidden" json="UPLOADFILE" name="hid_jsontF" id="hid_jsontF"  label="指令附件" autocheck="true" mustinput="true" inputtype="text" >
						  			</td>
								</tr>
							</table>
						</div>
						<div>
							<table   cellSpacing="0"  cellPadding="0" border="0" width="100%">
								<tr>
						  			<td><!-- view_ -->
						  				<table id="view_jsontF"  width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" >
											<tr class="fixedRow">
												<th align="center"><input type="checkbox" style="height:12px;valign:middle" id="allChecked"></th>
												<th nowrap align="center">项目编号</th>
												<th nowrap align="center">项目名称</th>
												<th nowrap align="center">指令类型</th>
												<th nowrap align="center">指令名称</th>
												<th nowrap align="center">上传人</th>
												<th nowrap align="center">上传时间</th>
												<th nowrap align="center">接受人</th>
												<th nowrap align="center">接受时间</th>
												<th nowrap align="center">状态</th>
												<th nowrap align="center" hidden="true">操作</th>
										   </tr>
											<c:forEach items="${page}" var="rst" varStatus="row">
													<c:set var="r" value="${row.count % 2}"></c:set>
													<c:set var="rr" value="${row.count}"></c:set>
													<tr class="list_row${r}" onmouseover="mover(this)"   onmouseout="mout(this)" id="tr${rr}">
														<td align='center' >
															<input  type='checkbox' id='att_id${rr}'  value="${rst.ATT_ID}" />
														</td>
														<td align="center">${rst.PROJECT_NO}&nbsp;</td>
														<td align="center">${rst.PROJECT_NAME}&nbsp;</td>
														<td align="center">${rst.DIRECTIVE_TYPE}&nbsp;</td>
														<td align="center">
																<%-- <input type="hidden" id="hid_attPath${rr}"  onclick="downloadFile('${rst.ATT_PATH}','hid_attPath${rr}')" name="UPLOADFILE" value="${rst.ATT_PATH}" /> --%>
															<%-- <div style="float:left">
																<button type="button" class="layui-dtlbtn attPathUploader" id="attPath${rr}" lay-data="{fileid:\'/a/\'}" title="上传附件">
																	<img src="${pageContext.request.contextPath}/styles/newTheme/images/icon/upload.png" >
																</button>
															</div>
															<div id="view_attPath${rr}"></div> --%>
															<a href="javascript:void(0)" onclick="downloadFile('${rst.ATT_PATH}','')">${rst.FILENAME}</a>
														</td>
														<td align="center">${rst.CRE_NAME}&nbsp;</td>
														<td align="center">${rst.CRE_TIME}&nbsp;</td>
														<td align="center">${rst.CRE_NAME}&nbsp;</td>
														<td align="center">${rst.CRE_TIME}&nbsp;</td>
														<td align="center">${rst.STATE}&nbsp;</td>
														<td id="order_id${rst.ATT_ID}"  hidden="true">${rst.PROJECT_DIRECTIVE_ID}</td>
													</tr>
												</c:forEach>
												<c:if test="${empty page}">
													<tr>
														<td height="25" colspan="13" align="center" class="lst_empty">
															&nbsp;无附件信息&nbsp;
														</td>
													</tr>
												</c:if>
										</table>
						  			</td>
						  			<td>&nbsp;</td>
						  		</tr>
							</table>
						</div>
					</form>
				</td>
			</tr>
		</table>
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
	                   <input id="save" type="button" class="btn" value="保存" >
	               </label>
		    </button>
			<button class="img_input" >
	                <label onclick="closeDialog()">
	                    <img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
	                    <input  type="button" class="btn" value="返回"  >
	                </label>
	        </button>
		</div>
	</body>
	<script type="text/javascript">
	//通配框与下拉框
	 SelDictShow("DIRECTIVE_TYPE","BS_204","","");
	//多文件上传 
	var url='toComEdit';
    displayDownFile('jsontF','${page}',url);
	</script>
</html>
