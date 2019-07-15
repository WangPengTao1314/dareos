<!-- 
 *@module 菜单信息维护
 *@func 
 *@version 
 *@author 
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
	 	<script type="text/javascript" src="${ctx}/pages/sys/menu/Menu_Add.js"></script>
		<title>菜单信息</title>
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
					<td class="gdtd_tb cx_demo"><div>菜单编号：</div> 
						<input name="menuId" json="menuId" id="menuId" type="text" autocheck="true" mustinput="true" label="菜单编号" 
							size="30" valueType="chinese:false" inputtype="string" maxlength="6" value="${entry.MENU_ID}">
					</td>
					<td class="gdtd_tb cx_demo"><div>排序编号：</div>
						<input name="menuSort" json="menuSort" id="menuSort" type="text" inputtype="int"
							label="排序编号"   mustinput="true" autocheck="true" size="30"
							maxlength="2" value="${entry.MENU_SORT}">
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb cx_demo"><div>菜单名称：</div>
						<input name="menuName" json="menuName" id="menuName" type="text" inputtype="string" label="菜单名称"
							size="30" autocheck="true" maxlength="100" mustinput="true" 
							value="${entry.MENU_NAME}">
					</td>
					<td class="gdtd_tb cx_demo"><div>权限Code：</div>
						<input name="menuQxCode" json="menuQxCode" id="menuQxCode" type="text" inputtype="string"
							label="权限Code" mustinput="true" autocheck="true" size="30" placeholder="跟权限绑定，用来过滤菜单"
							maxlength="15" value="${entry.MENU_QXCODE}">&nbsp;
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb cx_demo"><div>上级菜单：</div> 
						<input name="menuParId" json="menuParId" id="menuParId" type="text" inputtype="string" mustinput="true" label="上级菜单"
							autocheck="true" size="30" value="${entry.MENU_PAR_ID}">
					</td>
					<td class="gdtd_tb cx_demo"><div>业务类型：</div>
						<input name="BUSINESSTYPE" size="30" placeholder="跟审批绑定，即审批流程的业务类型" json="BUSINESSTYPE" id="businiessType" type="text" value="${entry.BUSINESSTYPE}" inputtype="string" label="业务类型"  autocheck="true" maxlength="30" >
						
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb cx_demo"><div>菜单链接：</div>
						<input name="menuUrl" json="menuUrl" id="menuUrl" type="text" size="30" label="菜单链接"
							value="${entry.MENU_URL}" inputtype="string" size="30"
							maxlength="100">
					</td>
					<td class="gdtd_tb cx_demo"><div>菜单图片：</div>
						<input name="menuImg" json="menuImg" id="menuImg" type="text" size="30" label="菜单图片"
							value="${entry.MENU_IMG}" inputtype="string"
							maxlength="100">
					</td>
				</tr>
				<tr>
					<td class="gdtd_tb cx_demo"><div>菜单注释：</div>
						<input name="menuDesc" json="menuDesc" id="menuDesc" type="text" size="30" label="菜单注释"
							value="${entry.MENU_DESC}" inputtype="string"
							maxlength="100">
					</td>
				</tr>
			</table>
	    </form>
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
	</body>
</html>
