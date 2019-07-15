/**
 *@module 基础数据
 *@func品牌信息
 *@version 1.1
 *@author 郭利伟
 */
$(function(){
	//页面初始化
	listPageInit("toList");
	$("#query").click(function(){
		$("#billType option[value='冻结']").remove();
		$("#billType option[value='释放冻结']").remove();
	})
});	
function selChann(){
	var XTYHID = $('#XTYHID').val();
	var initSel = " CHANN_ID in( select t.chann_id from BASE_USER_CHANN_CHRG t where t.user_id='" + XTYHID + "') ";
	$('#selectParams').val(initSel);
	selCommon("BS_19", false, "channId", "CHANN_ID", "forms[1]", 
			"channId,channName", "CHANN_ID,CHANN_NAME","selectParams");
}
