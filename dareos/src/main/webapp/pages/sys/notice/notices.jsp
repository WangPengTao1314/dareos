<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/drp/css/portal.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/drp/css/erpfirpage.css">
	<title>系统公告</title>
	<style type="text/css">
	</style>
</head>
<body class="bodycss1">
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<%--<table height="100%" width="98%" style="margin: 40px 0px 10px 10px;">
	<tr>
	 <td  align="center">
	    <div class="notice_dtl">
	     <div class="notice_dtl_h">
	    	<p>&nbsp;<img style="vertical-align: middle;"src="${ctx}/styles/drp/images/deng.png"/>&nbsp;置顶公告</p>
	     </div>
	    <div>
         <div class="ad_l" style="overflow-y: auto;">
          <h3 id="h3_notice_title">${notice.NOTICE_TITLE}</h3>
          <p id="p_notice" > ${notice.NOTICE} </p>
         </div>
       </div>
     
  </div>
  <input type='hidden' name='ATT_PATH' id='ATT_PATH' value='${notice.ATT_PATH}'/>
 </td>
</tr>
<tr>
 
</tr>

</table>--%>
<div style="width:95%">
	<div>
		<p>公告详情</p>
	</div>
	<div>
		<div class="ad_l">
			<p style="text-align: center;font-weight: bolder;font-size: 25px;">${notice.NOTICE_TITLE}</p>
			<p id="p_notice" > ${notice.NOTICE} </p>
		</div>
	</div>
</div>
  <%-- <div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
	<button class="img_input" >
              <label onclick="closeDialog()">
                  <img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
                  <input  type="button" class="btn" value="返回"  >
              </label>
       </button>
</div> 	 	 --%>
<div>附件信息：<button type="button" class="layui-btn uploadFile" id="uploadExcel" lay-data="{id:'uploadExcel'}" disabled>上传</button></div>
<input type="hidden" json="UPLOADEXCEL" name="uploadExcel" id="hid_uploadExcel" value="${notice.FILEPATH}" >
<table class="layui-table" style="width:85%" id="view_uploadExcel" ></table>
</body>
<script type="text/javascript">
$(function() {
	//上传文件
	displayDownFile('uploadExcel',true);
});
</script>
</html>
 