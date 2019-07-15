<!-- /**

  *@module 系统管理

  *@fuc 系统用户编缉页面

  *@version 1.1

  *@author 唐赟
*/ -->
<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css"
			href="${ctx}/styles/${theme}/css/style.css">
		<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
		<script type=text/javascript src="${ctx}/scripts/core/md5.js"></script>
		<%@ include file="/commons/jslibs.jsp"%>
	<%@ include file="/commons/jslibs4tree.jsp"%>
	<script type="text/javascript" src="${ctx}/pages/sys/xtyh/xtyh_Edit.js"></script>
		<title>系统用户信息新增或修改</title>
	</head>
	<body>
		<input type="hidden" id="status" value="${status }" />
		<div class="buttonlayer" id="floatDiv">
			<table cellSpacing=0 cellPadding=0 border=0 width="100%">
				<tr>
					<td align="right" nowrap>
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
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="rowSplit"></td>
			</tr>
			<tr>
				<td>
					<form name="form" method="post" id='mainForm'>
						<!--  action="${ctx}/system/xtyh.shtml?action=toSave"	onsubmit="return doSave();">  -->

						<input type="hidden" name="SM" value="${param.SM}">
						<input type="hidden" name="REPMOD_NO" value="${param.REPMOD_NO}">
						${paramCover.unCovered_Inputs }
						<table id="jsontb" width="100%" border="0" cellspacing="0">
							<tr>
								<td>
									&nbsp;
									<input type="hidden" json="ctrType" name="ctrType" id="ctrType"
										value="${status }">
								</td>
							</tr>
							<tr>
								<td width="100%" align="center">
									<table width="100%" border="0" cellpadding="0" cellspacing="0"
										class="detail">
										<tr>
											<td class="detail2" style="border:none">
												<table width="100%" border="0" cellpadding="0"
													cellspacing="0" class="detail3">

													<tr>
														<td class="detail2" style="border:none">
															<table width="100%" id="detail" border="0"
																cellpadding="0" cellspacing="0" class="detail3">
																<tr>
																	<td width="15%" height="30" align="right"
																		class="detail_label">
																		用户编号：
																	</td>
																	<td width="35%" class="detail_content">
																		<input json="YHBH" id="YHBH" name="YHBH" type="text" maxlength="30" valueType="chinese:false" value="${rst.YHBH}" autocheck="true" label="用户编号" inputtype="String" mustinput="true" >
																	</td>
																	<td width="15%" height="30" align="right" class="detail_label">
																		用户名称：
																	</td>
																	<td width="35%" class="detail_content">
																		<input json="YHM" name="YHM" type="text" autocheck="true" maxlength="30" label="用户名" inputtype="string" value="${rst.YHM }">
																	</td>
																</tr>
																<tr>
																	<td width="15%" height="30" align="right" class="detail_label">
																		用户口令：
																	</td>
																	<td width="35%" class="detail_content">
																		<input json="YHKL" id="YHKL" name="YHKL"
																			type="password" autocheck="true" label="用户口令"
																			maxlength=30 inputtype="string" mustinput="true"
																			value="${rst.YHKL}">
																		<!-- <font color="red">(请输入数字，字母，特殊字符[!@#$%^&*]中的至少两类,并输入六位)</font> -->
																	</td>
																	<td width="15%" height="30" align="right"
																		class="detail_label">
																		确认用户口令：
																	</td>
																	<td width="35%" class="detail_content">
																		<input id="YHKL2" name="YHKL2" type="password"
																			autocheck="true" label="确认用户口令" maxlength=30
																			inputtype="string" mustinput="true" value="${rst.YHKL}">
																	</td>
																</tr>
																<tr>
																	<td></td>
																	<td colspan="3"><font color="red">(请输入数字，字母，特殊字符[!@#$%^&*]中的至少两类,并输入六位)</font></td>
																</tr>
																<tr>
																	<td width="15%" height="30" align="right"
																		class="detail_label">
																		人员编号：
																	</td>
																	<td width="35%" class="detail_content">
																	<input id="selcondition" type="hidden" name="selcondition" value=" IS_DRP_LEDGER=0" />
																		<input json="RYBH" name="RYBH" type="text" seltarget="selRYBH" autocheck="true" label="人员编号" inputtype="string" readonly mustinput="true" value="${rst.RYBH}">
																		<input json="RYXXID" name="RYXXID" type="hidden" value="${rst.RYXXID}" seltarget="selRYBH">
																		<img align="absmiddle" name="selRYBH"
																			style="cursor: hand"
																			src="${ctx}/styles/${theme}/images/plus/select.gif"
																			onClick="selCommon('System_10',false, 'RYXXID', 'RYXXID', 'forms[0]','RYXXID,RYBH,RYMC,BMMC,BMXXID,JGMC,JGXXID,ZTXXID,RYXXID', 'RYXXID,RYBH,XM,BMMC,BMXXID,JGMC,JGXXID,ZTXXID,RYXXID', 'selcondition')">
																	</td>
																	<td width="15%" height="30" align="right"
																		class="detail_label">
																		人员名称：
																	</td>
																	<td width="35%" class="detail_content">
																		<input json="RYMC" name="RYMC" id="RYMC" type="text" readonly  seltarget="selRYBH"
																			autocheck="true" label="人员名称" inputtype="string" mustinput="true"
																			value="${rst.XM }">
																	</td>
																</tr>

																<tr>
																	<td width="15%" height="30" align="right"
																		class="detail_label">
																		部门名称：
																	</td>
																	<td width="35%" class="detail_content">
																		<input json="BMMC" name="BMMC" id="BMMC" type="text" readonly
																			autocheck="true" label="部门名称" inputtype="string" seltarget="selRYBH"
																			mustinput="true" value="${rst.BMMC}">
																		<input type="hidden" json="BMXXID" id="BMXXID" seltarget="selRYBH"
																			name="BMXXID" value="${rst.BMXXID}">
																	</td>
																	<td width="15%" height="30" align="right"
																		class="detail_label">
																		机构名称：
																	</td>
																	<td width="35%" class="detail_content">
																		<input json="JGMC" name="JGMC" id="JGMC" type="text" readonly seltarget="selRYBH"
																			autocheck="true" label="机构名称" inputtype="string"
																			value="${rst.JGMC }">
																		<input type="hidden" json="JGXXID" id="JGXXID" seltarget="selRYBH" mustinput="true"
																			name="JGXXID" value="${rst.JGXXID}">
																		<input type="hidden" json="ZTXXID" id="ZTXXID" seltarget="selRYBH"
																			name="ZTXXID" value="${rst.ZTXXID}">
																	</td>
																</tr>
																<tr>
																	<td width="15%" height="30" align="right"
																		class="detail_label">
																			用户业务类型：
																	</td>
																	<td width="35%" class="detail_content">
																		<input json="YHYWLX" name="YHYWLX" id="BMMC" type="text" readonly
																			autocheck="true" label="用户业务类型" inputtype="string"  value="${rst.YHYWLX}">
																			<img  style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif"
																			onClick="selCommon('BS_206',true, 'YHYWLX', 'SJXMC', 'forms[0]','YHYWLX', 'SJXMC')">
																	</td>
																	<td width="15%" height="30" align="right"
																		class="detail_label">
																	</td>
																	<td width="35%" class="detail_content">
																	</td>
																</tr>

<!-- 																<tr> -->
																
<!-- 																	<td width="15%" height="30" align="right" -->
<!-- 																		class="detail_label"> -->
<!-- 																		用户类别： -->
<!-- 																	</td> -->
<!-- 																	<td width="35%" class="detail_content" > -->
<!-- 																		<select label="用户类别" name="YHLB" -->
<!-- 																			style="width: 153" id="YHLB" json="YHLB"  -->
<%-- 																			value="${rst.YHLB}"> --%>
<!-- 																		</select> -->
<!-- 																	</td> -->
<!-- 																	<td width="15%" height="30" align="right" -->
<!-- 																		class="detail_label"> -->
<!-- 																		首页URL： -->
<!-- 																	</td> -->
<!-- 																	<td width="35%" class="detail_content" colspan="3"> -->
<!-- 																		<select json="PORTAL_URL" style="width: 155px;" mustinput="true" autocheck="true" label="首页URL" inputtype="string" id="PORTAL_URL" name="PORTAL_URL"> -->
<!-- 																		</select> -->
<!-- 																	</td> -->
<!-- 																</tr> -->
															</table>
												</table>
												<br>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

						<br>

					</form>
				</td>
			</tr>
			</table>
			<table width="100%" height="25px">
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</html>
<script type="text/javascript">InitFormValidator(0);</script>
<script type="text/javascript">
// 	  SelDictShow("YHLB", "BS_182", '${rst.YHLB}', "");
	   var  PORTAL_URL="${rst.PORTAL_URL}";
	   if(""==PORTAL_URL||null==PORTAL_URL){
	   		PORTAL_URL="toDrpFirst";
	   }
//        SelDictShow("PORTAL_URL","BS_164",PORTAL_URL,"");   
</script>