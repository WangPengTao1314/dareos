$(function(){
	closePage();
	checkReturnShow();
	// 根据是否可查看价格权限控制明细字段价格金额等列的显示/隐藏
	changeDtlPrice();
	//维护页面需要隐藏的按钮-总部
	whBtnHidden(["confirmBtn"]);
	//审核页面需要隐藏的按钮-经销商
	shBtnHidden(["addBtn", "importBtn", "modifyBtn", "deleteBtn", "commitBtn", "revokeBtn", "turnDesignerBtn","assignDesignerBtn","designBtn","turnERPBtn","scfhzlBtn"]);
//    var module = parent.window.document.getElementById("module").value;	
	//主从及主从从列表页面通用加载方法
	listPageInit("toList");
	//新增和修改按钮初始化
//	addModiToEditFrameInit("toEditFrame");
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	mtbDelListener("delete", "SALE_ORDER_ID");

	//复制
	$("#copy").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if(null == selRowId || "" == selRowId){
			parent.showErrorMsg("请选择一条记录");
		}else{
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("copyToEdit");
			setCommonPageInfo(false);
		}
	});
	// 申请变更
	$("#apply4change").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("toapply");
		}
	});
	// 变更订单
	$("#change").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("tochange");
		}
	});
	// 转设计师
	$("#turnDesigner").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			var state = document.getElementById("state" + selRowId).value;
			var FROM_BILL_NO = getFROM_BILL_NO();
			if('' == FROM_BILL_NO && '待处理' == state){//总部订单
				doBackConfirm(selRowId, '转设计师', 'turnDesigner');
			} else {
				doConfirm(selRowId, '转设计师', 'turnDesigner');
			}
		}
	});
	// 转ERP处理
	$("#turnERP").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			var state = document.getElementById("state" + selRowId).value;
			var FROM_BILL_NO = getFROM_BILL_NO();
			if('' == FROM_BILL_NO && '待处理' == state){//总部订单
				doBackConfirm(selRowId, '转ERP处理', 'turnERP');
			} else if('待生产' == state){//设计转
				setCommonPageInfo(true);
				parent.window.gotoBottomPage("toDesign?option=turnERP");
			} else {
				doConfirm(selRowId, '转ERP处理', 'turnERP');
			}
		}
	});
	// admin退单
	$("#ERPback").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			//doConfirm(selRowId, '强制退回', 'back2pre', '管理员协助退单');
			doConfirm(selRowId, '强制退回并删除NC订单', 'ERPback', '强制退回并删除NC订单');
		}
	});
	
	// 分派设计师
	$("#assignDesigner").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			setCommonPageInfo(true);/*//跳转设计师列表
			var FACTORY_NO = $('#FACTORY_NO'+selRowId).val();
			var LEDGER_ID = $('#LEDGER_ID'+selRowId).val();
			parent.window.gotoBottomPage("toAssign?LEDGER_ID="+LEDGER_ID+"&FACTORY_NO="+FACTORY_NO);*/
			parent.window.gotoBottomPage("toDesign?option=assign");
		}
	});
	// 设计师拆件
	$("#design").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("toDesign?option=design");
		}
	});
	$("#commit").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			doConfirm(selRowId, '提交', 'toCommit');
		}
	});
	$("#revoke").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (null == selRowId ||  selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		toFlow("3");
	});
	$("#audit").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (null == selRowId || selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		toFlow("2");
	});
	$("#flow").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		toFlow("4");
	});
	
	// 进度跟踪
	$("#flowTrack").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		var flowServiceId = $('#ORDER_TRACE_ID'+selRowId).val();
		myShowModalDialog(ctxPath+"/sys/flow/toList/"+flowServiceId);
	});
	
	$("#import").click(function(){
	});
	
	$("#export").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		window.location.href = "exportExcelDtl?SALE_ORDER_ID="+selRowId;
	});
	
	$("#query").click(function(){
//		$("#STATE option[text='未提交']").remove();
		//$("#BILL_TYPE option[text='非标']").remove();
	});
	
	//时间差变色 
	//dateDiffColor();
	$("#expdate").click(function(){
		$("#queryForm").attr("action","saleorder.shtml?action=toExpData&module=" + module);
		$("#queryForm").submit();
	});
	//生成发货指令
	$("#scfhzl").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			setCommonPageInfo(false);
			parent.window.gotoBottomPage("toSendOrder?SALE_ORDER_ID="+selRowId);
		}
	});
	// 确认收货
	$("#confirm").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		}else{
			doConfirm(selRowId, '收货', 'toConfirm');
		}
	});
});


// 选择经销商
function selChann() {
	var xtyhid = $('#xtyhid').val();
	var initSel = " STATE in ('启用','停用') and IS_BASE_FLAG = '0' and CHANN_ID in( select t.chann_id from BASE_USER_CHANN_CHRG t where t.user_id='" + xtyhid + "') ";
	$('#initSel').val(initSel);
	selCommon("BS_19", false, "CHANN_NAME", "CHANN_NAME", "forms[1]", 
			"CHANN_NAME",
			"CHANN_NAME", "initSel");
	
}


//提交
function commit(selRowId){
	var SALE_ORDER_NO = $("#SALE_ORDER_NO"+selRowId).val();
	$.ajax({
		url: "saleorder.shtml?action=toCommit",
		type:"POST",
		dataType:"text",
		data:{"SALE_ORDER_ID":selRowId,"SALE_ORDER_NO":SALE_ORDER_NO},
		complete: function (xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel(utf8Decode(jsonResult.messages));
				$("#pageForm").submit();
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

// 确认、取消
function doConfirm(selRowId, title, func, reason){
	layer.confirm('是否确认'+title+'？', {
		icon:3
	}, function(index){
		var loading = layer.load(1);
		$.ajax({
			url: func,
			type:"POST",
			dataType:"text",
			data:{"SALE_ORDER_ID": selRowId, selRowId: selRowId, reason: reason},
			complete: function (xhr){
				layer.close(index);
				eval("jsonResult = "+xhr.responseText);
				if(jsonResult.success===true){
					showMsgPanelCloseWindow(utf8Decode(jsonResult.messages),'parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
				}else{
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
				layer.close(loading);
			}
		});
		
	});
}

// 待退回按钮的确认、取消
function doBackConfirm(selRowId, title, func){
	var html ='<div style="width: 100%;height: 100%;" id="auditDiv">'+
				'<div>填写意见：</div>'+
				'<textarea style="width: 99%; resize:none; " id="auditContents" maxlength="500" rows="8"></textarea>'+
			'</div>';
	layer.open({
		title: '是否确认'+title+'？'
		,area : ['500px' , '300px']
		,content: html
		,closeBtn: 0
		,btn: ['确定', '退回', '取消']
		,yes:function(index, layero){//console.log('1');
			var loading = layer.load(1);
			$.ajax({
				url: func,
				type:"POST",
				dataType:"text",
				data:{"SALE_ORDER_ID": selRowId},
				complete: function (xhr){
					layer.close(index);
					eval("jsonResult = "+xhr.responseText);
					if(jsonResult.success===true){
						showMsgPanelCloseWindow(utf8Decode(jsonResult.messages),'parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
					}else{
						parent.showErrorMsg(utf8Decode(jsonResult.messages));
					}
					layer.close(loading);
				}
			});
			return false;// 开启该代码可禁止点击该按钮关闭
		}
		,btn2:function(index, layero){
			var auditContents = $('#auditContents').val();
			if (auditContents.length == 0) {
				parent.showWarnMsg("请输入退回原因！");
				return false;
			}
			var loading = layer.load(1);
			$.ajax({
				url: 'back?auditContents='+auditContents,
				type:"POST",
				dataType:"text",
				data:{"SALE_ORDER_ID": selRowId},
				complete: function (xhr){
					layer.close(index);
					eval("jsonResult = "+xhr.responseText);
					if(jsonResult.success===true){
						showMsgPanelCloseWindow(utf8Decode(jsonResult.messages),'parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
					}else{
						parent.showErrorMsg(utf8Decode(jsonResult.messages));
					}
					layer.close(loading);
				}
			});
			return false;// 开启该代码可禁止点击该按钮关闭
		}
		,cancel: function(index, layero){}
	});
}


function openReportUrl(obj,SALE_ORDER_NO){
	//showWarnMsg("该报表开发还未完成，敬请期待！");
	var title = $(obj).attr("title");
	var url = "../../drp/report/saleProStatus.shtml?action=toFrame&SALE_ORDER_NO="+SALE_ORDER_NO;
	toShowPage(title,url);
}


function toShowPage(lable,urlAll){
	var mainFrame = window.top.mainFrame;
	mainFrame.addTab(lable,lable,urlAll);
}


function orpenWindow(SALE_ORDER_ID){
	window.showModalDialog("saleorderview.shtml?action=toGoodsFrame&SALE_ORDER_ID="+SALE_ORDER_ID,window,"dialogwidth=900px; dialogheight=700px; status=no");
}



//时间差变色  
function dateDiffColor(){
	var inputs = $("#ordertb tr:gt(0)").find("input[name='DATE_DIFF']");
	inputs.each(function(){
		var date = $(this).val();
		date = parseInt(date);
		if(date >=1 ){
			$(this).parent().parent().find("td").css("background-color", "#E693A2");//花号还原
			  
		}
	});
}


function toFlow(i) {
	var cutid = $("#selRowId").val();
	document.affirm.id.value = cutid;
	document.affirm.sourceURI.value=ctxPath+"/saleorder.shtml?action=toList"+document.getElementById("paramUrl").value ;
	switch (Number(i)) {
	  case 1:
		affirmEvent(0);
		break;//提交
	  case 2:
		affirmEvent(1);
		break;//审核
	  case 3:
		affirmEvent(2);
		break; //撤销
	  case 4:
		showHistory(cutid);
		break;//撤销
	}
}

function closePage(){
	$("#mycredit_show").hide();
}

//设置 一览页面 ‘订货总额’
function setTotalAmount(total){
	var selRowId = parent.document.getElementById("selRowId").value;
	var name="TOTL_AMOUNT_"+selRowId
	$("#ordertb").find("td[name='"+name+"']").text(total);
}

//点击选择某条记录后，需要根据状态判断是否可用
function setSelOperateEx(obj, data) {
	var selRowId = parent.document.getElementById("selRowId").value;
	var state = document.getElementById("state" + selRowId).value;
	//设置按钮的状态
	setBtnState(state);
}
//点击选择某条记录后，需要根据状态判断是否可用
function setBtnState(state) {
	var billType = getBILL_TYPE();
	//按钮状态重置
	btnReset();
	if (state == "草稿") {
		btnDisable(["change","turnDesigner","assignDesigner","design","turnERP","apply4change","scfhzl"]);
	}
	//当状态=退回
	if (state == "退回") {
		btnDisable(["change","turnDesigner","assignDesigner","design","turnERP","apply4change","scfhzl"]);
	}
	if (state == "待处理") {
		btnDisable(["modify","delete","commit","change","assignDesigner","design","scfhzl"]);
	}
	if (state == "分派设计师") {
		btnDisable(["modify","delete","commit","change","turnDesigner","design","turnERP","scfhzl"]);
	}
	if (state == "设计师拆件") {
		btnDisable(["modify","delete","commit","change","turnDesigner","assignDesigner","turnERP","scfhzl"]);
	}
	if (state == "待生产") {
		btnDisable(["modify","delete","commit","change","turnDesigner","assignDesigner","design"]);
	}
	if (state == "生产中") {
		btnDisable(["modify","delete","commit","change","turnDesigner","assignDesigner","design","turnERP"]);
	} else {
		btnDisable(["scfhzl","apply4change","ERPback"]);
	}
	if (state == "已发货") {
		btnDisable(["modify","delete","commit","change","turnDesigner","assignDesigner","design","turnERP","apply4change","scfhzl"]);
	} else {
		btnDisable(["confirm"]);
	}
	if (state == "已完成") {
		btnDisable(["modify","delete","commit","change","turnDesigner","assignDesigner","design","turnERP","apply4change","confirm","scfhzl"]);
	} else {
	}
	if (billType == "常规订单" || billType == "常规样品") {
		btnDisable(["modify","delete","commit"]);
	}
	
	var xtyhid = $('#xtyhid').val();
	var DESIGNER = getDESIGNER();
	if (xtyhid != DESIGNER) {
		btnDisable(["design"]);
	}
	
	var CHANGE_FLAG = getCHANGE_FLAG();
	if ('1' == CHANGE_FLAG) {
		btnDisable(["modify","delete","commit","apply4change","turnDesigner","assignDesigner","design","turnERP","scfhzl"]);
	}

}
function getCHANN_ID(){
	var selRowId = parent.document.getElementById("selRowId").value;
	return $("#RECV_CHANN_ID"+selRowId).val();
}
function getBILL_TYPE(){
	var selRowId = parent.document.getElementById("selRowId").value;
	return $("#BILL_TYPE"+selRowId).val();
}
function getFROM_BILL_NO(){
	var selRowId = parent.document.getElementById("selRowId").value;
	return $("#FROM_BILL_NO"+selRowId).val();
}
function getDESIGNER(){
	var selRowId = parent.document.getElementById("selRowId").value;
	return $("#DESIGNER"+selRowId).val();
}
function getCHANGE_FLAG(){
	var selRowId = parent.document.getElementById("selRowId").value;
	return $("#CHANGE_FLAG"+selRowId).val();
}

//EXECL导入解析
function parseExcel(filePath){
	$.ajax({
		url: "parseExcel",
		type:"POST",
		dataType:"text",
		data:{"filePath":filePath },
		complete: function(xhr){
			parent.closeWindow();
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("成功导入!");
				window.location.reload();
			}else{
				parent.showWarnMsg(utf8Decode(jsonResult.messages));
			}
			layer.closeAll('loading'); //关闭加载层
		}
	});
}
