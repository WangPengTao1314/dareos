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
	mtbSaveListener("edit","creditReqId");
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

function formCheckedEx(){
	
	var d = new Date();
	var strDate = getDateStr(d);

	var d2 = new Date($("#expirationDate").val());
	var strDate2 = getDateStr(d2);
	console.log(new Date(strDate));
	console.log(new Date(strDate2));
	if ( new Date(strDate) >  new Date(strDate2)) {
	  parent.showErrorMsg(utf8Decode("失效日期必须大于当前日期！"));
	  return false;
	}
	var d2 = new Date($("#effectiveDate").val());
	var d3 = new Date($("#expirationDate").val());
	if (d3< d2) {
	  parent.showErrorMsg(utf8Decode("失效日期不能小于生效日期！"));
	  return false;
	}
	return true;
}


function getDateStr(date) {
var month = date.getMonth() + 1;
var strDate = date.getFullYear() + '-' + month + '-' + date.getDate();
return strDate;
}




