$(function(){
	checkReturnShow();
	// 根据是否可查看价格权限控制明细字段价格金额等列的显示/隐藏
	changeDtlPrice();
	//维护页面需要隐藏的按钮
	whBtnHidden(["applyauditBtn", "modifyBtn","auditBtn","quoteBtn", "confirmBtn"]);
	//审核页面需要隐藏的按钮
	shBtnHidden(["confirmquoteBtn"]);
	//主从及主从从列表页面通用加载方法
	listPageInit("toList");
	
	// 变更申请单审核
	$("#applyaudit").click(function() {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			var SALE_ORDER_ID = $('#SALE_ORDER_ID'+selRowId).val();
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("tochange?option=applyaudit&SALE_ORDER_ID="+SALE_ORDER_ID);
		}
	});
	
	// 审图
	$("#audit").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			var SALE_ORDER_ID = $('#SALE_ORDER_ID'+selRowId).val();
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("toAudit?option=audit&SALE_ORDER_ID="+SALE_ORDER_ID);
		}
	});
	// 报价
	$("#quote").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			var SALE_ORDER_ID = $('#SALE_ORDER_ID'+selRowId).val();
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("toAudit?option=quote&SALE_ORDER_ID="+SALE_ORDER_ID);
		}
	});
	// 确认报价
	$("#confirmquote").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			var SALE_ORDER_ID = $('#SALE_ORDER_ID'+selRowId).val();
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("toAudit?option=confirmquote&SALE_ORDER_ID="+SALE_ORDER_ID);
		}
	});
	// 确认
	$("#confirm").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			var SALE_ORDER_ID = $('#SALE_ORDER_ID'+selRowId).val();
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("toAudit?option=confirm&SALE_ORDER_ID="+SALE_ORDER_ID);
		}
	});
	
	// 进度跟踪
	$("#flowTrack").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		var flowServiceId = $('#ORDER_TRACE_ID'+selRowId).val();
		myShowModalDialog(ctxPath+"/sys/flow/toList/"+flowServiceId);
	});
});

function doBack(selRowId){
	
}

//点击选择某条记录后，需要根据状态判断是否可用
function setSelOperateEx(obj) {
	//可根据id或name或所在列数获得值，具体方法开发人员自行选择。
	var selRowId = $("#selRowId").val();
	var state =  document.getElementById("state" + selRowId).value;
	var applystate =  document.getElementById("applystate" + selRowId).value;
	//设置按钮的状态
	setBtnState(state, applystate);
}
//点击选择某条记录后，需要根据状态判断是否可用
function setBtnState(state, applystate) {
	//按钮状态重置
	btnReset();

	//当申请单状态=草稿
	if (applystate != "提交") {
		btnDisable(["applyaudit"]);
	}
	
	//当状态=审图中
	if (state == "审图中") {
		btnDisable(["modify","delete","commit","revoke","confirmquote","shouhou","quote","confirm", "crexsdd"]);
	}
	if (state != "审图中") {
		btnDisable(["audit"]);
	}
	
	//当状态=报价中
	if (state == "报价中") {
		btnDisable(["modify","delete","commit","revoke","confirmquote","shouhou","audit","confirm", "crexsdd"]);
	}
	if (state != "报价中") {
		btnDisable(["quote"]);
	}
	
	//当状态=待经销商确认
	if (state == "待经销商确认") {
		btnDisable(["modify","delete","commit","revoke","shouhou","audit","quote","confirm", "crexsdd"]);
	}
	if (state != "待经销商确认") {
		btnDisable(["confirmquote"]);
	}
	
	//当状态=订单审核
	if (state == "订单审核") {
		btnDisable(["modify","delete","commit","revoke","confirmquote","shouhou","audit","quote", "crexsdd"]);
	}
	if (state != "订单审核") {
		btnDisable(["confirm"]);
	}

	//当状态=审核通过
	if (state == "已审核") {
		btnDisable(["modify","delete","commit","revoke","confirmquote","shouhou","audit","quote", "confirm"]);
	}

	//当状态=待处理
	if (state == "待处理") {
		btnDisable(["modify","delete","commit","revoke","confirmquote","shouhou","audit","quote", "confirm"]);
	}
}