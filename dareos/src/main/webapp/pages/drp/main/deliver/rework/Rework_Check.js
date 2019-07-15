/**
 * @module 售后管理
 * @func 返修单审核js
 * @version 1.0
 * @author 王朋涛
 */
$(function() {

	// 初始化校验
	InitFormValidator(0);
	// 添加浮动按钮层的监听
	// addFloatDivListener();
	// 保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	// mtbSaveListener("edit","sale_order_id","toList","mainForm");
	$("#pass").click(function() {
		var status ="生产中";
		showConfirm("您确认要通过审核吗?","changeState('check', 'SALE_ORDER_ID','toList','mainForm',"+status+");");
	});
	$("#reject").click(function() {
		var status ="已退回";
		showConfirm("您确认要退回吗?","changeState('check','SALE_ORDER_ID','toList','mainForm',"+status+");");
	});
	
	//
	function changeState(actionUrl, pkId, successUrl, formId,status) {
		if (formId == null || formId == '') {
			formId = "mainForm";
		}
		if(!formChecked(formId)){
			return;
		}
		var selId = parent.document.getElementById("selRowId").value;
		var jsonData = siglePackJsonData("jsontbP");
		$.ajax({
			url : actionUrl + "?" + pkId + "=" + selId+"&STATUS="+status,
			type : "POST",
			dataType : "text",
			data : {
				"jsonData" : jsonData
			},
			complete : function(xhr) {
				//$("#save").removeAttr("disabled");
				eval("jsonResult = " + xhr.responseText);
				if (jsonResult.success === true) {
					showMsgPanelCloseWindow('操作成功','parent.window.frames["topcontent"].document.getElementById("queryForm").submit()');
				} else {
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	}

});
