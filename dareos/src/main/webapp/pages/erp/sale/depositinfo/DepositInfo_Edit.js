/**
 *@module 基础数据
 *@func品牌信息
 *@version 1.1
 *@author 郭利伟
 */
$(function(){
	//初始化校验
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	mtbSaveListener("edit","depositId");
	$('#ledgerId').change(function() {
		var lname = $("#ledgerId option:selected").text();
		$("#ledgerName").val(lname);
	});
});
//通用选取回调函数
function endSelCommBack(){
	var channId = $("#channId").val();
	SelDictShow("ledgerId","BS_189","${rst.ledgerId }"," chann_id = '"+channId+"'");
}

