<!-- 
 *@module  项目管理
 *@func 项目指令编辑
 *@version 1.0
 *@author 王朋涛
 *  -->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%> 
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/pages/common/uploadfile/uploadfile_order2.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<script language="JavaScript"src="${ctx}/pages/common/select/selCommJs.js"></script>
		<script type="text/javascript" src="${ctx}/pages/drp/main/project/order/Order_Edit.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>
		<title>项目指令编辑</title>
		<style>
		table tr{
			height:50px;
		}
		</style>
	</head>
	<body style="background-color: #fff;">
<%-- 		<div class="" id="floatDiv">
			<table cellspacing=0 cellpadding=0 border=0 width="100%">
				<tr>
					<td align="left" nowrap>
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
		</div>  --%>
		<!--浮动按钮层结束-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<form name="mainForm" id="mainForm">
						<div>
							<table id="jsontb" width="100%" border="0" cellpadding="0"
							cellspacing="0" class="detail">
								<tr>
									<td width="100%" align="left">
										 <table  cellSpacing="0"  cellPadding="0" border="0" width="100%">
									  		<tr>
									  			<td  class="gdtd_tb">
													<button type="button" class="layui-btn uploadFile" id="jsontF" lay-data="{id:'jsontF'}">上传</button></div>
													<input type="hidden" json="UPLOADFILE" name="hid_jsontF" id="hid_jsontF"  label="指令附件" autocheck="true" mustinput="true" inputtype="text" >
									  				<input name="PROJECT_ID" hidden="true" json="PROJECT_ID" seltarget="selJGXX" id="PROJECT_ID"   type="text" value="${params.project_id}">
									  			</td>
									  			<td   class="gdtd_tb">
									  				<div style="display: inline-block">指令类型：</div>
									  				<select name="DIRECTIVE_TYPE" id="DIRECTIVE_TYPE" class="gdtd_select_input" style="width: 60%" label="指令类型" autocheck="true" inputtype="string" mustinput="true" json="DIRECTIVE_TYPE" ></select>
									  				<input name="PROJECT_NAME" hidden="true" json="PROJECT_NAME" seltarget="selJGXX"  id="PROJECT_NAME" type="text" value="${params.project_name}">
									  			</td>
									  			<td id="imgID" class="gdtd_tb">
									  				项目编号&nbsp;：<input class="gdtd_select_input" style="width: 60%"  name="PROJECT_NO" label="项目编号" autocheck="true"   mustinput="true" inputtype="string"  json="PROJECT_NO" id="PROJECT_NO"  seltarget="selJGXX" type="text" value="${params.project_no}" >
													<img class="select_gif" align="absmiddle"   name="selJGXX" src="${ctx}/styles/${theme}/images/plus/select.gif" seltarget="selJGXX" onClick="selCommon('BS_200', false, 'PROJECT_ID', 'PROJECT_ID', 'forms[0]','PROJECT_ID,PROJECT_NO,PROJECT_NAME','PROJECT_ID,PROJECT_NO,PROJECT_NAME')">
									  				<input name="PROJECT_DIRECTIVE_ID" hidden="true" id="project_directive_id" json="PROJECT_DIRECTIVE_ID"   type="text" value="${params.project_directive_id}">
									  			</td>
									  		</tr>
										</table>
									</td>
								</tr>
							</table>
						</div>
						<div>
							<table  cellSpacing="0"  cellPadding="0" border="0" width="100%">
								<tr>
						  			<td>
						  				<table id="view_jsontF"   width="100%" border="0" cellpadding="0" cellspacing="0" class="lst" >
											<tr class="fixedRow">
												<th align="center">序号</th>
												<!-- <th nowrap align="center" dbname="project_no" >项目编号</th>
												<th nowrap align="center" dbname="project_name" >项目名称</th> -->
												<th nowrap align="center" dbname="DIRECTIVE_TYPE" >指令类型</th>
												<th nowrap align="center" dbname="att_path" >指令名称</th>
												<th nowrap align="center" dbname="cre_name" >上传人</th>
												<th nowrap align="center" dbname="cre_time" >上传时间</th>
												<th nowrap align="center" dbname="" >接受人</th>
												<th nowrap align="center" dbname="" >接受时间</th>
												<th nowrap align="center" dbname="" >状态</th>
												<!-- <th nowrap align="center" dbname="" >操作</th> -->
										   </tr>
											<c:forEach items="${page}" var="rst" varStatus="row">
												<c:set var="r" value="${row.count % 2}"></c:set>
												<c:set var="rr" value="${row.count}"></c:set>
												<tr class="list_row${r}" onmouseover="mover(this)"   onmouseout="mout(this)" id="tr${rr}">
													<td align='center' >${row.count}</td>
													<%-- <td align="center">${rst.PROJECT_NO}&nbsp;</td>
													<td align="center">${rst.PROJECT_NAME}&nbsp;</td> --%>
													<td align="center">${rst.DIRECTIVE_TYPE}&nbsp;</td>
													<td align="center">
														<a href="javascript:void(0)" onclick="downloadFile('${rst.ATT_PATH}','')">${rst.FILENAME}</a>
													</td>
													<td align="center">${rst.CRE_NAME}&nbsp;</td>
													<td align="center">${rst.CRE_TIME}&nbsp;</td>
													<td align="center">${rst.CRE_NAME}&nbsp;</td>
													<td align="center">${rst.CRE_TIME}&nbsp;</td>
													<td align="center">${rst.STATE}&nbsp;</td>
													<%-- <td align="center">
														-
														<a href="javascript:sendEntry('${rst.project_directive_id}')">&nbsp;下发&nbsp;</a>-
													</td> --%>
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
				<!-- 底部固定部分 -->
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
                    <input type="button" class="btn" value="返回"  >
                </label>
        </button>
	</div>
	</body>
	<script type="text/javascript">
		//通配框与下拉框
		 SelDictShow("DIRECTIVE_TYPE","BS_204","","");
		//多文件上传
	    //uploadFile('uploadFile',"SAMPLE_DIR",true,false,true,"");
		var url='toEdit';
		displayDownFile('jsontF','${page}',url);
	</script>
	
</html>
