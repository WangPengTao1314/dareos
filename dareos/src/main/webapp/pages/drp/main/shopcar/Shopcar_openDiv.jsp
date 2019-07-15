<!--
 * @prjName:喜临门营销平台
 * @fileName:Paymentup_Detial
 * @author lyg
 * @time   2013-08-23 10:25:58 
 * @version 1.1
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" import="com.centit.commons.util.StringUtil" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<style type="text/css">
		a {
			cursor: hand ;
		}
	</style>
</head>
<body>
	<div style="margin: 0 auto;width: 100%;height: 100%;" id="opendiv">
		<table cellspacing="6" style="padding:20px;width: 100%;">
				<tr class="LMM">
					<td  align="center">门洞尺寸</td>
					<td>
						<input name="HOLE_SPEC" value="${rst.HOLE_SPEC}" onblur="countNewSize(this)" class="gdtd_select_input">
					</td>
				</tr>
				<tr>
					<td align="center">尺寸</td>
					<td>
						<input name="PRD_SIZE" value="${rst.PRD_SIZE}" class="gdtd_select_input">
					</td>
				</tr>
				<tr class="LMM LYGCG">
					<td align="center">材质</td>
					<td>
						<select name="PRD_QUALITY" id="PRD_QUALITY"  class="gdtd_select_input"></select>
					</td>
				</tr>
				<tr class="LMM LYGCG">
					<td align="center">颜色</td>
					<td>
						<select name="PRD_COLOR" id="PRD_COLOR" class="gdtd_select_input"></select>
					</td>
				</tr>
				<tr class="LMM">
					<td align="center">推向</td>
					<td>
						<select name="PRD_TOWARD" id="PRD_TOWARD"  class="gdtd_select_input"></select>
					</td>
				</tr>
				<tr class="LMM">
					<td align="center">玻璃</td>
					<td>
						<select name="PRD_GLASS" id="PRD_GLASS"   class="gdtd_select_input"></select>
					</td>
				</tr>
				<tr class="LMM">
					<td align="center">其它</td>
					<td>
						<select name="PRD_OTHER" id="PRD_OTHER"  class="gdtd_select_input"></select>
					</td>
				</tr>
				<tr class="LMM">
					<td align="center">系列</td>
					<td>
						<select name="PRD_SERIES" id="PRD_SERIES" class="gdtd_select_input"></select>
					</td>
				</tr>
				<tr class=" LYGCG">
					<td align="center">投影面积</td>
					<td>
						<input name="PROJECTED_AREA" value="${rst.PROJECTED_AREA}" class="gdtd_select_input">
					</td>
				</tr>
				<tr class=" LYGCG">
					<td align="center">展开面积</td>
					<td>
						<input name="EXPAND_AREA" value="${rst.EXPAND_AREA}" class="gdtd_select_input">
					</td>
				</tr>
				<tr>
					<td align="center">要求</td>
					<td>
						<textarea name="REMARK"  style="width: 85%">${ rst.REMARK}</textarea>
					</td>
				</tr>
				<tr>
					<td align="center">图纸</td>
					<td>
						<button type="button" class="layui-btn uploadFile" id="ATT_PATH" lay-data="{id:'ATT_PATH'}">上传</button></div>
						<input type="hidden"  json="ATT_PATH" name="ATT_PATH" id="hid_ATT_PATH" value="${ rst.ATT_PATH}">
						<table class="layui-table" style="width:85%" id="view_ATT_PATH"></table>
					</td>
				</tr>
			</table>
		</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">
// uploadFile('ATT_PATH',"SAMPLE_DIR",true,true,true,"");
SelDictShow("PRD_QUALITY", "BS_192","${rst.PRD_QUALITY}");
SelDictShow("PRD_COLOR", "BS_193","${rst.PRD_COLOR}");
SelDictShow("PRD_TOWARD", "BS_196","${rst.PRD_TOWARD}");
SelDictShow("PRD_GLASS", "BS_194","${rst.PRD_GLASS}");
SelDictShow("PRD_OTHER", "BS_195","${rst.PRD_OTHER}");
SelDictShow("PRD_QUALITY", "BS_192", "${rst.PRD_QUALITY}");
SelDictShow("PRD_SERIES", "BS_206", "${rst.PRD_SERIES}");
changeDtlCol('${rst.LEDGER_ID}');
displayDownFile('ATT_PATH',true);
/**
 * 根据门洞尺寸获取标准尺寸
 * @param obj
 */
function countNewSize(obj){
	var newSize=toNewSize($(obj).val());
	$(obj).parent().parent().parent().find("input[name='PRD_SIZE']").val(newSize);
}
//把页面上的所有属性汇总成对象返回回去
function getOptPro(){
	var obj = {};
	$("#opendiv").find(":input").each(function(){
		var name = $(this).attr("name");
		obj[name] = $(this).val();
		$(this).val("");
  })
  return obj;
}
</script>
</html>