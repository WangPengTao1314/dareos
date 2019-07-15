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
	<script type="text/javascript" charset="utf-8" src="${ctx}/pages/common/uEditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="${ctx}/pages/common/uEditor/ueditor.all.js"> </script>
	    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
	    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
	<script type="text/javascript" charset="utf-8" src="${ctx}/pages/common/uEditor/lang/zh-cn/zh-cn.js"></script>

	<script type="text/javascript" src="${ctx}/pages/common/select/selCommJs.js"></script>

	<script type="text/javascript" src="${ctx}/pages/sys/notice/Notice_Edit.js"></script>
	<script type=text/javascript src="${ctx}/scripts/util/My97DatePicker/WdatePicker.js"></script>

	<title>信息编辑</title>
</head>
<body onload="checkAuthFunc('${msg}');">
 <div class="buttonlayer" id="floatDiv">
      <table cellSpacing=0 cellPadding=0 border=0 width="100%">
		<tr>
		   <td align="center" nowrap>
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
		<table id="jsontb" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
		<%-- <input type="hidden" name="selectPYXXParams" value=" YHZT = '启用' and  ZTXXID = '${ZTXXID}' " /> --%>
		<tr>
			<td class="detail2">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" class="detail3">
				<tr height="50px">
					<td class="detail_label" width="10%">公告标题：</td>
					<td class="detail_content" width="40%">
                        <input type="text" json="NOTICE_TITLE"   id="NOTICE_TITLE" name="NOTICE_TITLE" maxlength="100" autocheck="true" inputtype="string" label="公告标题" value="${rst.NOTICE_TITLE}" mustinput="true"  class="gdtd_select_input"/>&nbsp;
					</td>
					<td class="detail_label" width="10%">公告类型：</td>
					<td  class="detail_content" width="40%">
                      <select id="NOTICE_TYPE" name="NOTICE_TYPE" label="公告类型"  json="NOTICE_TYPE" autocheck="true" inputtype="string" mustinput="true" class="gdtd_select_input"></select>
					</td>
				</tr>
				<tr height="50px">
					<td  class="detail_label">公告对象：</td>
						<%--<select id="NOTICE_OBJ" name="NOTICE_OBJ" label="公告对象"  json="NOTICE_OBJ" autocheck="true" inputtype="string" mustinput="true" style="width:200"></select>--%>
					<td class="detail_content" >
					<input type="hidden" class="gdtd_select_input" id="NOTICE_OBJ" name="NOTICE_OBJ" json="NOTICE_OBJ" value="${rst.NOTICE_OBJ}"  class="gdtd_select_input"/>
					<input type="checkbox" class="gdtd_select_input"  id="NOTICE_OBJ_CHCECK">经销商可见
					<%--<input type="checkbox" style="height:12px;valign:middle" id="NOTICE_OBJ" value="1">经销商可见--%>
					</td>


					<td class="detail_label">公告级别：</td>
					<td  class="detail_content">
						<select id="NOTICE_LVL" name="NOTICE_LVL" label="公告级别"  json="NOTICE_LVL" autocheck="true" inputtype="string" mustinput="true" class="gdtd_select_input"></select>
					</td>
				</tr>

				<tr height="50px">
					<%-- <td  class="detail_label">发布人名称：</td>
					<td  class="detail_content">
						<input type="hidden" id="XTYHID" name="ISSUER_ID" json="ISSUER_ID" value="${rst.ISSUER_ID}"  />
						<input type="text"  id="YHM" name="ISSUER_NAME" json="ISSUER_NAME" maxlength="30"    autocheck="true" inputtype="string" label="发布人名称" value="${rst.ISSUER_NAME}" mustinput="true"  class="gdtd_select_input"/>&nbsp;
						<img align="absmiddle" name="selJGXX" style="cursor: hand"
							 src="${ctx}/styles/${theme}/images/plus/select.gif"
							 onClick="selCommon('System_7', false, 'XTYHID', 'XTYHID', 'forms[0]','XTYHID,YHM', 'XTYHID,YHM')">
					</td> --%>
					<td  class="detail_label">公告范围：</td>
					<td  class="detail_content" colspan="3">
						<input type="hidden" id="NOTICE_DEPTS" name="NOTICE_DEPTS" json="NOTICE_DEPTS" value="${rst.NOTICE_DEPTS}"/>
						<c:forEach items="${zts}" var="rst" varStatus="row">
							<input type="checkbox" name="NOTICE_DEPT" value="${rst.LEDGER_ID}">${rst.LEDGER_NAME_ABBR}
						</c:forEach>
					</td>
				</tr>
				
				<tr height="50px">
					<td class="detail_label">开始时间：</td>
					<td class="detail_content">
                        <input type="text" json="STATIME"id="STATIME" name="STATIME" autocheck="true" inputtype="string" label="开始时间" value="${rst.STATIME}" mustinput="true" onclick="SelectTime();" readonly="readonly" class="gdtd_select_input"/>&nbsp;
						<img align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectTime(STATIME);">
					</td>
					<td  class="detail_label">结束时间：</td>
					<td  class="detail_content">
                        <input type="text" json="ENDTIME" id="ENDTIME"name="ENDTIME" autocheck="true" inputtype="string" label="结束时间"  value="${rst.ENDTIME}" mustinput="true" onclick="SelectTime();" readonly="readonly" class="gdtd_select_input"/>&nbsp;
						<img align="absmiddle" style="cursor:hand" src="${ctx}/styles/${theme}/images/plus/date_16x16.gif" onclick="SelectTime(ENDTIME);">
					</td>
				</tr>
				<tr height="50px">
					<td class="detail_label">附件上传&nbsp;：</td>
					<td class="detail_content">
						<div><button type="button" class="layui-btn uploadFile" id="uploadExcel" lay-data="{id:'uploadExcel'}">上传</button></div>
						<input type="hidden" json="UPLOADEXCEL" name="uploadExcel" id="hid_uploadExcel" value="${rst.FILEPATH}" >
						<table class="layui-table" style="width:85%" id="view_uploadExcel" ></table>
					</td>
					<td  class="detail_label"></td>
					<td  class="detail_label"></td>
				</tr>
				<tr height="150px">
                    <input type="hidden" json="NOTICE" id="NOTICE"  name="NOTICE" autocheck="true" pattern="string" maxlength="6000"label="公告信息" required="true"/>
					<td colspan="4" style="text-align: center;width: 90%">
						<textarea  id="editor"  type="text/plain" style="width: 100%">${rst.NOTICE}</textarea>
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
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
</html>
<script language="JavaScript">
//校验权限异常
	var ue = UE.getEditor('editor');
	SelDictShow("NOTICE_TYPE","192","${rst.NOTICE_TYPE}","");
	SelDictShow("NOTICE_OBJ","202","${rst.NOTICE_OBJ}","");
	SelDictShow("NOTICE_LVL","203","${rst.NOTICE_LVL}","");
	
	displayDownFile("uploadExcel",true,false);
</script>
