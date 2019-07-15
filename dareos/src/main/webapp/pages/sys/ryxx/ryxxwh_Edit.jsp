<!-- 
 *@module 系统管理
 *@func 人员信息
 *@version 1.1
 *@author 吴亚林
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
		<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
		<script type="text/javascript" src="${ctx}/pages/sys/ryxx/ryxxwh_Edit.js"></script>
		<title>人员信息编辑</title>
	</head>
	<body>
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
		<!--浮动按钮层结束-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<form name="mainForm" id="mainForm">
						<table id="jsontb" width="100%" border="0" cellpadding="0"
							cellspacing="0" class="detail">
							<tr>
								<td class="detail2">
									<table width="100%" border="0" cellpadding="0" cellspacing="0"
										class="detail3">
										<tr height="50px">
											<td width="15%" align="right" class="detail_label">
												人员编号：
											</td>
											<td width="35%" class="detail_content">
												<input json="RYBH" name="RYBH" id="RYBH" type="text" autocheck="true" label="人员编号" valueType="chinese:false" inputtype="string" mustinput="true" maxlength="50" value="${rst.RYBH}" style="width:180px;height:23px">
												
											</td>
											<td width="15%" align="right" class="detail_label">
												姓名：
											</td>
											<td width="35%" class="detail_content">
												<input json="XM" name="XM" id="XM" type="text" autocheck="true" label="姓名" inputtype="string" mustinput="true"  maxlength="50" value="${rst.XM}" style="width:180px;height:23px">
										</tr>
										<tr height="50px">
											<td width="15%" align="right" class="detail_label">
												所属部门：
											</td>
											<td width="35%" class="detail_content">
												<input json="BMXXID" name="BMXXID" id="BMXXID" type="hidden" maxlength="32" autocheck="true" seltarget="selJGXX" label="所属部门" inputtype="string"   value="${rst.BMXXID }" READONLY style="width:180px;height:23px">
												<input type="hidden" id="BMZT" name="BMZT" value=" BMZT='启用' and JGZT='启用' AND (IS_DRP_LEDGER=0 or ZTXXID in (select a.CHANN_ID from BASE_CHANN a where a.CHANN_TYPE='直营办')) " style="width:180px;height:23px">
												<input json="BMMC"  name="BMMC" id="BMMC" type="text" autocheck="true" seltarget="selJGXX" label="所属部门名称" inputtype="string" mustinput="true"   value="${rst.BMMC }" readonly style="width:180px;height:23px">
												<img align="absmiddle" name="selJGXX" style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif" onClick="selCommon('System_9', false, 'BMXXID', 'BMXXID', 'forms[0]','BMXXID,BMMC,JGXXID,JGMC', 'BMXXID,BMMC,JGXXID,JGMC', 'BMZT')">
											</td>
											<td width="15%" align="right" class="detail_label">
												所属机构：
											</td>
											<td width="35%" class="detail_content">
												<!-- <input type="hidden" id="JGZT" name="JGZT" value=" JGZT='启用' and DELFLAG='0' "> -->
												<input json="JGXXID" name="JGXXID" id="JGXXID" type="hidden" inputtype="string" seltarget="selJGXX" autocheck="true"   value="${rst.JGXXID }" READONLY style="width:180px;height:23px">
												<input json="JGMC" name="JGMC" id="JGMC" type="text" inputtype="string" seltarget="selJGXX" autocheck="true" mustinput="true" label="所属机构"  value="${rst.JGMC }" READONLY style="width:180px;height:23px">
												<!-- <img align="absmiddle" name="selJGXX" style="cursor: hand" src="${ctx}/styles/${theme}/images/plus/select.gif" onClick="selCommon('2', false, 'JGXXID', 'JGXXID', 'forms[0]','JGXXID,JGMC', 'JGXXID,JGMC', 'JGZT')"> -->
											</td>
										</tr>
										
										<tr height="50px">
											<td width="15%" align="right" class="detail_label">
												性别：
											</td>
											<td width="35%" class="detail_content" >
												<div class="male">
													<input id="male" name="XB" json="XB" type="radio" value="男" <c:if test="${rst.XB!='女' }">checked="checked"</c:if> />
													 <label for="male">男</label>
												</div>
												<div class="female">
													<input id="female" name="XB" json="XB" type="radio" value="女" <c:if test="${rst.XB=='女' }">checked="checked"</c:if>/>
													 <label for="female">女</label>
												</div>
											</td>
											
											<td width="15%" align="right" class="detail_label">
												人员级别：
											</td>
											<td width="35%" class="detail_content">
												<select json="RYJB" id="RYJB" name="RYJB" style="width:180px;height:23px"></select>
											</td>
										</tr>
										
										<tr height="50px">
											<td width="15%" align="right" class="detail_label">
												身份证号：
											</td>
											<td width="35%" class="detail_content">
												<input json="SFZH" name="SFZH" id="SFZH" type="text" autocheck="true" label="身份证号" inputtype="string" maxlength="50" value="${rst.SFZH }" style="width:180px;height:23px">
											</td>
											<td width="15%" align="right" class="detail_label">
												职务：
											</td>
											<td width="35%" class="detail_content">
												<select json="ZW" id="ZW" name="ZW" autocheck="true" label="岗位" inputtype="string" style="width:180px;height:23px" ></select>
											</td>
											
										</tr>
										<tr height="50px">
											<td width="15%" align="right" class="detail_label">
												人员类别：
											</td>
											<td width="35%" class="detail_content">
												<select json="RYLB" id="RYLB" name="RYLB" autocheck="true" label="人员类别" inputtype="string" style="width:180px;height:23px" ></select>
											</td>
											<td width="15%" align="right" class="detail_label">
												社保基数：
											</td>
											<td width="35%" class="detail_content">
												<input json="SBJS" name="SBJS" id="SBJS" type="text" autocheck="true" label="社保基数" inputtype="string" maxlength="50" value="${rst.SBJS }" style="width:180px;height:23px">
											</td>
											
										</tr>										
										
										<tr height="50px">
										<td width="15%" align="right" class="detail_label">
												公司电话：
											</td>
											<td width="35%" class="detail_content">
												<input json="GSDH" name="GSDH"  id="GSDH" type="text" autocheck="true" label="公司电话" inputtype="string" maxlength="50" value="${rst.GSDH }" style="width:180px;height:23px">
											</td>
											<td width="15%" align="right" class="detail_label">
												手机：
											</td>
											<td width="35%" class="detail_content">
												<input json="SJ" name="SJ" id="SJ" type="text" autocheck="true" label="手机" inputtype="string" autocheck="true" mustinput="true" maxlength="11" value="${rst.SJ }" style="width:180px;height:23px">
											</td>
											
										</tr>
										
										<tr height="50px">
											<td width="15%" align="right" class="detail_label">
												电子邮件：
											</td>
											<td width="35%" class="detail_content">
												<input json="DZYJ" name="DZYJ" id="DZYJ"  type="text" autocheck="true" label="电子邮件" inputtype="string" maxlength="50" value="${rst.DZYJ }" style="width:180px;height:23px">
											</td>
											<td width="15%" align="right" class="detail_label">&nbsp;</td>
											<td width="35%" class="detail_content">
											    &nbsp;
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
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
	</body>
	<script language="javascript">
		$(function(){
			SelDictShow("RYJB","96","${rst.RYJB }","");
			SelDictShow("ZW","102","${rst.ZW }","");
			SelDictShow("RYLB","113","${rst.RYLB }","");
		});
	</script>
</html>
