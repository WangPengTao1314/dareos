
/**
 *@module 销售管理
 *@func 编辑js
 *@version 1.1
 *@author 王朋涛
 */
$(function(){
	//初始化校验
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	$("#save").click(function() {
		allSave();
	});
	
	$("#allChecked").click(function() {
		var flag = document.getElementById("allChecked").checked;
		if (flag) {
			$("#jsontb input[json=order_degree_dtl_id]:checkbox").attr(
					"checked", "true");
		} else {
			$("#jsontb input[json=order_degree_dtl_id]:checkbox")
					.removeAttr("checked");
		}
	});
	
});

//主子表保存
function allSave() {
	// 主表form校验
	var parentCheckedRst = parent.topcontent.formChecked('mainForm');
	if (!parentCheckedRst) {
		btuMxRest();
		return;
	}
	// 明细校验
	if (!formMutiTrChecked()) {
		btuMxRest();
		return;
	}
	//var selRowId = document.getElementById("selRowId").value;
	// 获取主表json数据
	var parentData = siglePackJsonData('jsontbP');
	// console.log('parentData', parentData);

	var childData = multiPackJsonData();
	$.ajax({
		url : "feedback",
		type : "POST",
		async : false,
		dataType : "json",
		data : {
			"parentJsonData" : parentData,
			"childJsonData" : childData
			//"ORDER_DEGREE_ID" : selRowId
		},
		complete : function(xhr) {
			eval("jsonResult = " + xhr.responseText);
			if (jsonResult.success === true) {
				showMsgPanelCloseWindow('保存成功','parent.window.frames["topcontent"].document.getElementById("queryForm").submit()');
			} else {
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}
