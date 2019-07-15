
$(function(){
	
	init();
	
	// 销售类型下拉框事件
	$('#BILL_TYPE').change(function() {
		showProj(this);
		changeBillType(this);
	});
	// 订单组织下拉框事件
	$('#LEDGER_ID').change(function() {
		// 存在货品信息，提示
		var prods = $("#jsontb tr:gt(0) input[json=SALE_ORDER_DTL_ID]");
		if (prods.length < 1) {
			changeLedger(this);
		} else {
			showConfirm("修改订单组织将清空明细信息，是否确认修改？","delProdAll();changeLedger('#LEDGER_ID');", "restore('#LEDGER_ID');");
		}

	});

	$("#reverse").click(function() {
		// 查找当前是否有选中的记录
		var checkBox = $("#jsontb tr:gt(0) input[json=SALE_ORDER_DTL_ID]");
		checkBox.prop("checked", function(index, attr){
			return !attr;
		});
	});

	$("#copy").click(function() {
		// 查找当前是否有选中的记录
		var checkBox = $("#jsontb tr:gt(0) input[json=SALE_ORDER_DTL_ID]:checked");
		if (checkBox.length < 1) {
			parent.showWarnMsg("请至少选择一条记录");
			return;
		}
		dtl_copy();
	});

	$("#add").click(function() {
		addRow();
	});

	$("#delete").click(function() {
		// 查找当前是否有选中的记录
		var checkBox = $("#jsontb tr:gt(0) input[json=SALE_ORDER_DTL_ID]:checked");
		if (checkBox.length < 1) {
			parent.showWarnMsg("请至少选择一条记录");
			return;
		}
		// 获取所有选中的记录
		var ids = "";
		checkBox.each(function() {
			if ("" != $(this).val()) {
				ids = ids + "'" + $(this).val() + "',";
			}
		});
		ids = ids.substr(0, ids.length - 1);
		// 没有ids说明都是页面新增的，数据库中无记录，可以直接remove
		if ("" == ids) {
			checkBox.parent().parent().remove();
			// 计算订单总金额
			countTotalRebate();
			countTotalAmount();
		} else {
			// 变更审核时不可以删除原有数据{
			var option = $('#option').val();
			if (option && 'applyaudit' == option){// 变更审核
				parent.showWarnMsg("不能删除原有数据！仅可修改数量为零！");
				return;
			}
			
			// parent.showConfirm("您确认要删除吗","bottomcontent.multiRecDeletes();");
			showConfirm("您确认要删除吗?", "multiRecDeletes();");
		}
		// 删除后scroll可能消失，因此需要重新调整浮动按钮的位置
		setFloatPostion();
	});
	
	//新增修改(明细表)
	$("#save").click(function(){
		allSave('edit');
	});
	$("#commit").click(function() {
		//
		allSave('edittocommit');
	});
	
	// 分派设计师
	$("#assign").click(function(){
		var selRowId = getSelRowId();//销售订单ID
		var FACTORY_NO = $('#FACTORY_NO').val();
		var LEDGER_ID = $('#LEDGER_ID').val();
		parent.window.gotoBottomPage("toAssign?LEDGER_ID="+LEDGER_ID+"&FACTORY_NO="+FACTORY_NO);
	});
	
	// 拆件页面
	$('#design, #tmpdesign, #undesign').click(function(){
		var selRowId = getSelRowId();//销售订单ID
		var option = $('#option').val();
		var status = $(this).attr('status');
		$('#auditStatus').val(status);
		if('T' == status){
			// 拆件校验
		}
		// 获取主表json数据
		var parentData = siglePackJsonData('jsontbP');
		console.log('parentData', parentData);
		$.ajax({
			url : "design",
			type : "POST",
			async : false,
			dataType : "json",
			data : {
				"parentJsonData" : parentData,
				"SALE_ORDER_ID" : selRowId,
				"option" : option
			},
			complete : function(xhr) {
				eval("jsonResult = " + xhr.responseText);
				if (jsonResult.success === true) {
					showMsgPanelCloseWindow(utf8Decode(jsonResult.messages),'parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
				} else {
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	});
	
	// 转ERP
	$('#turnERP').click(function(){
		var loading = layer.load(1);
		var selRowId = getSelRowId();//销售订单ID
		$.ajax({
			url: "turnERP",
			type:"POST",
			dataType:"text",
			data:{"SALE_ORDER_ID": selRowId},
			complete: function (xhr){
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
	
	// 变更申请审核
	$('#agree,#disagree').click(function(){
		var selRowId = getSelRowId();//变更申请单ID
		var option = $('#option').val();
		var status = $(this).attr('status');
		$('#auditStatus').val(status);

		if ('T' == status) {
			// 主表form校验
			var parentCheckedRst = formChecked('mainForm');
			if (!parentCheckedRst) {
				return;
			}
			// 明细校验
			if (!formMutiTrChecked()) {
				return;
			}
		}
		var auditContents = $('#auditContents').val();
		if ('F' == status && '' == auditContents) {
			var tempLabel=$('#auditContents').attr("label");
			var msg = GetSpanMessage(MsgMustInput, tempLabel);
			showWarnMsg(msg);
			return;
		}

		//获取json数据
		var parentData = siglePackJsonData('jsontbP');
		var childData;
		if($("#jsontb tr:gt(0) input[json='SALE_ORDER_DTL_ID']:checked").length>0){
			childData = multiPackJsonData();
		}
		
		$.ajax({
			url : "change",
			type : "POST",
			async : false,
			dataType : "json",
			data : {
				"parentJsonData" : parentData,
				"childJsonData" : childData,
				"CHANGE_APPLY_ID" : selRowId,
				"SALE_ORDER_ID":$('#SALE_ORDER_ID').val(),
				"option" : option
			},
			complete : function(xhr) {
				eval("jsonResult = " + xhr.responseText);
				if (jsonResult.success === true) {
					showMsgPanelCloseWindow('操作成功','parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
				} else {
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	});
	
	// 审图、报价
	$('#audit').click(function(){
		var selRowId = getSelRowId();//变更申请单ID
		var option = $('#option').val();
		var status = $(this).attr('status');
		$('#auditStatus').val(status);
		// 获取主表json数据
		var parentData = siglePackJsonData('jsontbP');
		//console.log('parentData', parentData);
		var childData = multiPackJsonData();
		//console.log('childData', childData);
		$.ajax({
			url : "audit",
			type : "POST",
			async : false,
			dataType : "json",
			data : {
				"parentJsonData" : parentData,
				"childJsonData" : childData,
				"SALE_ORDER_ID":$('#SALE_ORDER_ID').val(),
				"option" : option
			},
			complete : function(xhr) {
				eval("jsonResult = " + xhr.responseText);
				if (jsonResult.success === true) {
					showMsgPanelCloseWindow('保存成功','parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
				} else {
					parenshowWarnMsgsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	});

	// 经销商确认报价，总部领导确认
	$('#confirm').click(function(){
		var selRowId = getSelRowId();//变更申请单ID
		var option = $('#option').val();
		var status = $(this).attr('status');
		$('#auditStatus').val(status);
		// 获取主表json数据
		var parentData = siglePackJsonData('jsontbP');
		//console.log('parentData', parentData);
		var childData = multiPackJsonData();
		//console.log('childData', childData);
		$.ajax({
			url : "confirm",
			type : "POST",
			async : false,
			dataType : "json",
			data : {
				"parentJsonData" : parentData,
				"childJsonData" : childData,
				"SALE_ORDER_ID":$('#SALE_ORDER_ID').val(),
				"option" : option
			},
			complete : function(xhr) {
				eval("jsonResult = " + xhr.responseText);
				if (jsonResult.success === true) {
					showMsgPanelCloseWindow('操作成功','parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
				} else {
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	});
	
	// 退回上一节点
	$('#back2pre').click(function(){
		var selRowId = getSelRowId();
		var option = $('#option').val();
		var status = $(this).attr('status');
		$('#auditStatus').val(status);
		// 获取主表json数据
		var parentData = siglePackJsonData('jsontbP');
		$.ajax({
			url : "back2pre",
			type : "POST",
			async : false,
			dataType : "json",
			data : {
				"parentJsonData" : parentData,
				"selRowId":selRowId,
				"option" : option
			},
			complete : function(xhr) {
				eval("jsonResult = " + xhr.responseText);
				if (jsonResult.success === true) {
					showMsgPanelCloseWindow('操作成功','parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
				} else {
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	});
	
	// 退回指定节点
	$('#back').click(function(){
		var selRowId = getSelRowId();//变更申请单ID
		var option = $('#option').val();
		var status = $(this).attr('status');
		$('#auditStatus').val(status);
		// 获取主表json数据
		var parentData = siglePackJsonData('jsontbP');
	});
	
	$("#allChecked").click(function(){
		var flag = document.getElementById("allChecked").checked;
		if(flag){
			$("#jsontb input[json='SALE_ORDER_DTL_ID']").attr("checked","true");
		}else{
			$("#jsontb input[json='SALE_ORDER_DTL_ID']").removeAttr("checked");
		}
//		var BILL_TYPE=parent.topcontent.getBILL_TYPE();
//		if("赠品订货" == BILL_TYPE){
//			$("#jsontb tr th[name='hideTdByBillType']").css("display","none"); 
//			$("#jsontb tr td[name='hideTdByBillType']").css("display","none");
//		}
	});

});

function showProj(obj){
	var bill_type = $(obj).val();
	if('工程订单'==bill_type){
		$('tr.proj').find("input[json=PROJECT_NAME]").attr("autocheck","true");
		$('tr.proj').find("input[json=PROJECT_NAME]").attr("mustinput","true");
		$('tr.proj').show();
	} else if('工程样品'==bill_type){
		$('tr.proj').find("input[json=PROJECT_NAME]").removeAttr("autocheck","true");
		$('tr.proj').find("input[json=PROJECT_NAME]").attr("mustinput","true");
		$('tr.proj').show();
	} else {
		$('tr.proj').find("input[json=PROJECT_NAME]").removeAttr("autocheck","true");
		$('tr.proj').find("input[json=PROJECT_NAME]").removeAttr("mustinput","true");
		$('tr.proj').hide();
	}
}

function changeBillType(obj){
	var lid = $('#ztid').val();
	if(lid != ''){
		// 生成新厂编
		getFactoryNo();
	}

}

function changeLedger(obj){
	// 生成新厂编
	getFactoryNo();
	//正常修改订单组织
	var lname = $("#LEDGER_ID option:selected").text();
	$("#LEDGER_NAME").val(lname);
	// 根据订单组织控制明细字段列的显示/隐藏
	var lid = $(obj).val();
	$("#ztid").val(lid);
	changeDtlCol(lid);

}

//获取厂编
function getFactoryNo(){
	var state = $('#state').val();
	if(state && '草稿' != state){
		return ;
	}
	var parentData = siglePackJsonData('jsontbP');
	$.ajax({
		url: "getFactoryNo",
		type : "GET",
		async : false,
		dataType : "json",
		data : {
			"parentJsonData" : parentData
		},
		complete : function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				var obj = jsonResult.data;
				console.log(obj);
				$('#FACTORY_NO').val(obj.FACTORY_NO);
				$('#SALE_ORDER_NO').val(obj.FACTORY_NO);
				$('#xtbs').val(obj.xtbs);
				var ORDER_DELIVERY_DATE = $('#ORDER_DELIVERY_DATE').val();
				if('' == ORDER_DELIVERY_DATE){
					$('#ORDER_DELIVERY_DATE').val(obj.ORDER_DELIVERY_DATE);
				}
				var PRE_RECV_DATE = $('#PRE_RECV_DATE').val();
				if('' == PRE_RECV_DATE){
					$('#PRE_RECV_DATE').val(obj.PRE_RECV_DATE);
				}
				
			} else {
				showWarnMsg(utf8Decode(jsonResult.messages));
				restore('#LEDGER_ID');
			}
		}
	});

}

//修改厂编
function changeFactoryNo(obj){
	var state = $('#state').val();
	if('草稿' != state){
		return ;
	}
	var dateStr = $(obj).val();//console.log(dateStr);
	var datePart = dateStr.split('-');
	var xtbs = $('#xtbs').val();
	var partNew = xtbs+datePart[1]+datePart[2];//console.log(partNew);
	
	var FACTORY_NO = $('#FACTORY_NO').val();//console.log(FACTORY_NO);
	var part = FACTORY_NO.split('-')[0];//console.log(part);
	var no = FACTORY_NO.replace(part, partNew);//console.log(no);
	$('#FACTORY_NO').val(no);
	
	var PRE_RECV_DATE = $('#PRE_RECV_DATE').val();
	if('' == PRE_RECV_DATE){
		var date = new Date(dateStr.replace(/-/g,"\/"));console.log(date);
		date.setDate(date.getDate() + 7);
		var dateNew = dateFormat(date);
		$('#PRE_RECV_DATE').val(dateNew);
	}
	
}

function restore(obj){
	//订单组织改为更换前的值
	var lid = $("#ztid").val();
	$(obj).val(lid);
}

function delProdAll(){
	// 清空明细信息
	$("#jsontb tr:gt(0) input[json=SALE_ORDER_DTL_ID]").prop("checked", true);
	multiRecDeletes();
}

//删除操作
function multiRecDeletes(){
	//查找当前是否有选中的记录
	var checkBox = $("#jsontb tr:gt(0)  input[name='SALE_ORDER_DTL_ID']:checked");
	//获取所有选中的记录
	var ids = "";
	checkBox.each(function(){
		ids = ids+"'"+$(this).val()+"',";
	});
	ids = ids.substr(0,ids.length-1);
	
	$.ajax({
		url: "childDelete",
		type:"POST",
		dataType:"text",
		data:{"SALE_ORDER_DTL_IDS":ids},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("删除成功");
				checkBox.parent().parent().remove();
				//设置删除操作。有删除时返回后需要刷新页面。否则直接跳转上一页。
//				$("#delFlag").val("true");
				// 计算订单总金额
				countTotalRebate();
				countTotalAmount();
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

//主子表保存
function allSave(func){
	// 主表form校验
	//var parentCheckedRst = parent.topcontent.formChecked('mainForm');
	var parentCheckedRst = formChecked('mainForm');
	if (!parentCheckedRst) {
		return;
	}
	// 明细校验
	if (!formMutiTrChecked()) {
		return;
	}

	// 计算订单总金额
	//countTotalRebate();
	countTotalAmount();

	var selRowId = getSelRowId();
	// 获取主表json数据
	var parentData = siglePackJsonData('jsontbP');

	$("#jsontb tr:gt(0) input[json=SALE_ORDER_DTL_ID]").attr('checked', 'checked');
	var childData;
	if($("#jsontb tr:gt(0) input[json='SALE_ORDER_DTL_ID']:checked").length>0){
		childData = multiPackJsonData();
	}

	$.ajax({
		url: func,
		type:"POST",
		async : false,
		dataType:"json",
		data:{"parentJsonData":parentData,"childJsonData":childData,"SALE_ORDER_ID":$('#SALE_ORDER_ID').val() },
		complete: function (xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanelCloseWindow(utf8Decode(jsonResult.messages),'parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

function init(){
	var option = $('#option').val();
	if (option && ('design' == option || 'assign' == option || ('待生产' == state && 'turnERP' == option))){//设计师拆件和设计部转erp
		$('#mainForm img').remove();
		$('#mainForm :input').not(':button, :submit, :reset, :hidden, .design').attr('disabled', 'disabled');
		// 附件添加只读属性
		$('#mainForm #attPath').attr('disabled', 'disabled');
		$('.child img').remove();
		$('.child :input').not(':button, :submit, :reset, :hidden').attr('disabled', 'disabled');
	} else if (option && 'applyaudit' == option){// 变更审核
		$('#mainForm :input').not(':button, :submit, :reset, :hidden, .applyaudit').attr('disabled', 'disabled');
	} else if (option && 'audit' == option){//审图
		$('#mainForm img').remove();
		$('#mainForm :input').not(':button, :submit, :reset, :hidden, .audit').attr('disabled', 'disabled');
		$('.child img').remove();
		$('.child :input').not(':button, :submit, :reset, :hidden').attr('disabled', 'disabled');
	} else if (option && 'quote' == option){//报价
		$('#mainForm img').remove();
		$('#mainForm :input').not(':button, :submit, :reset, :hidden, .audit').attr('disabled', 'disabled');
		// 附件添加只读属性
		$('#mainForm #attPath').attr('disabled', 'disabled');
		//$('#quoteTd').show();
		$('.child img').remove();
		$('.child input[json=attPath]').attr('disabled', 'disabled');
	} else if (option && 'confirmquote' == option || 'confirm' == option){//经销商确认报价和总部领导审核，所有元素只读
		$('#mainForm img').remove();
		$('#mainForm :input').not(':button, :submit, :reset, :hidden, .audit').attr('disabled', 'disabled');
		// 附件添加只读属性
		$('#mainForm #attPath').attr('disabled', 'disabled');
		//$('#quoteTd').show();
		//$('#quoteTd :input').attr('disabled', 'disabled');
		$('.child img').remove();
		$('.child :input').not(':button, :submit, :reset, :hidden').attr('disabled', 'disabled');
		// 附件添加只读属性
		$('.child input[json=attPath]').attr('disabled', 'disabled');
	} else {
<<<<<<< .mine
		
=======
		$('#quoteTd').show();
>>>>>>> .r2222
	}

	//上传文件
	displayDownFile('attPath',true);
	
	//上传文件-拆件计料表
	displayDownFile('attPath_cj',true);
	
	//根据订单类型控制工程项目的显示/隐藏
	showProj('#xslx');

	var lid = $("#ztid").val();//console.log('lid=',lid);
	// 根据订单组织控制明细字段列的显示/隐藏
	changeDtlCol(lid);
	// 根据是否可查看价格权限控制明细字段价格金额等列的显示/隐藏
	changeDtlPrice();

	$("#jsontb tr").each(function(){
		$(this).find("td:gt(0)").click(function(){
			$(this).parent().find("input[json='SALE_ORDER_DTL_ID']").attr("checked","checked");
		});
	});

	$("#jsontb tr:gt(0) input[json='SALE_ORDER_DTL_ID']").attr("checked","true");
	
	//长字段鼠标指向时显示
	showTips();

	// 初始化校验
	InitFormValidator(0);

	// 计算本单返利折扣
	var total = Number($('#total').val());
	var flje = Number($('#totalRebate').val());
	if (0 == flje || 0 == total) {
		return ;
	} else {
		var rebatedsct = Math.round((isNaN(flje / total) ? 0.00 : flje / total) * 10000) /100;
		$('#REBATEDSCT').val(rebatedsct);
	}
}

// 选择工程项目
function selProj() {
	var XTYHID = $('#SALE_ID').val();
	var initSel = " IS_BASE_FLAG = '0' and CHANN_ID in( select t.chann_id from BASE_USER_CHANN_CHRG t where t.user_id='" + XTYHID + "') ";
	$('#initSel').val(initSel);
	selCommon("BS_200", false, "PROJECT_ID", "PROJECT_ID", "forms[0]", 
			"PROJECT_NO,PROJECT_NAME",
			"PROJECT_NO,PROJECT_NAME", "");
}

// 选择经销商
function selChann() {
	var LEDGER_ID = $('#LEDGER_ID').val();
	if ('' == LEDGER_ID) {
		parent.showWarnMsg("请先选择订单组织");
		return;
	}
	var XTYHID = $('#SALE_ID').val();
	var initSel = " STATE = '启用' and IS_BASE_FLAG = '0' and nvl(CHANN_ID_P, '1')='1' "
					+ " and CHANN_ID in( select t.chann_id from BASE_USER_CHANN_CHRG t where t.user_id='" + XTYHID + "') "
					+ " and CHANN_ID in( select t.chann_id from BASE_CHANN_LEDGER_CHRG t where t.ledger_id='" + LEDGER_ID + "') ";
	$('#initSel').val(initSel);
	selCommon("BS_205", false, "PERSON_CON", "PERSON_CON", "forms[0]", 
			"ORDER_CHANN_ID,ORDER_CHANN_NO,ORDER_CHANN_NAME,RECV_CHANN_ID,RECV_CHANN_NO,RECV_CHANN_NAME,CHANN_ID,CHANN_NO,CHANN_NAME,PRINT_NAME"
			+ ",TEL,RECV_ADDR,TRANSPORT",
			"CHANN_ID,CHANN_NO,CHANN_ABBR,CHANN_ID,CHANN_NO,CHANN_ABBR,CHANN_ID,CHANN_NO,CHANN_NAME,CHANN_ABBR"
			+ ",TEL,DELIVER_DTL_ADDR,TRANSPORT", "initSel");
	
}

function selPerson() {
	var CHANN_ID = $('#CHANN_ID').val();
	if ('' == CHANN_ID) {
		parent.showWarnMsg("请先选择经销商");
		return;
	}
	var initSel = " CHANN_ID = '" + CHANN_ID + "'";
	$('#initSel').val(initSel);
	selCommon("BS_76", false, "DELIVER_ADDR_ID", "DELIVER_ADDR_ID", "forms[0]", 
			"PERSON_CON,TEL,RECV_ADDR,TRANSPORT",
			"PERSON_CON,TEL,DELIVER_DTL_ADDR,TRANSPORT", "initSel");
}

function selPrd(rownum) {
	var LEDGER_ID = $('#LEDGER_ID').val();
	if ('' == LEDGER_ID) {
		parent.showWarnMsg("请先选择订单组织");
		return;
	}
	selProduct(LEDGER_ID, true, "PRD_ID" + rownum, "PRD_ID", "forms[1]", 
			"PRD_ID"+ rownum + ",PRD_NO" + rownum + ",PRD_NAME" + rownum 
			+ ",STD_UNIT"+ rownum + ",PRICE" + rownum + ",PRD_SPEC" + rownum 
			+ ",BRAND"+ rownum,
			"PRD_ID,PRD_NO,PRD_NAME,STD_UNIT,PRVD_PRICE,PRD_SPEC,BRAND");
			
}
function selPRD_QUALITY(rownum){
	var initSel = " SJZDID='PROD_MATER'";
	$('#initParams').val(initSel);
	selCommon("System_6", false, "PRD_QUALITY"+ rownum + "", "SJXZ", "forms[1]", 
			"PRD_QUALITY"+ rownum + "",
			"SJXZ", "initParams");
}

// 额外校验
function formCheckedEx(){
	
	return true;
}

//固定的一些共通的方法 end
//下面这个方法要自己修改
//表格增加一行
function addRow(arrs){
	var soDtlEmpty = $("#soDtlEmpty").html();
	if (soDtlEmpty!=null || soDtlEmpty !='') {
		$("#soDtlEmpty").remove();
	}
	if(null == arrs){
		arrs = [ '', '', '', '', '', '', '', '', '无', '无', '无', '无', '无', '无', '',
				'', '', '', '100', '', '1', '', '', '', '', 
				'0', '', '', '', '无', '1', '', '', '', '' 
				, '', $('#PROESS_TYPE').val(), '', '', ''];
	}
	var techChecked = "";
	if ('1' == arrs[15]) {
		techChecked = "checked";
	}
	var lockChecked = '1' == arrs[30] ? "checked" : "";
	//样式行
	var rownum = $("#jsontb tr").length;
//	var SPCL_TECH_FLAG;
//	if(arrs[18]==null||arrs[18]==""||arrs[18]=="0"){
//		SPECIAL_FLAG_TEXT='无';
//	}else{
//		SPECIAL_FLAG_TEXT="<span style='color: red'>有</span>"
//	}
	var classrow = rownum % 2;
	rownum = _row_num;
	_row_num++;
	$("#jsontb tr:last-child").after("<tr style='height:42px;' class='list_row"+classrow+"' onmouseover='mover(this)' onmouseout='mout(this)'></tr>");
	$("#jsontb tr:last-child").append("<td nowrap align='center'><input type='checkbox' style='height:12px;valign:middle' id='eid"+rownum+"' json='SALE_ORDER_DTL_ID' name='SALE_ORDER_DTL_ID' value='"+arrs[0]+"' /></td>")
	var soDtl = "";
	soDtl += '<td nowrap class="LMM"><input type="text" id="GROUP_NO' + rownum + '" json="GROUP_NO" size="3" name="GROUP_NO" value="' + arrs[1]	+ '"  /></td>';
	soDtl += '<td nowrap><input type="text" id="PRD_NO' + rownum + '" json="PRD_NO" size="10" name="PRD_NO" value="' + arrs[2] + '" title="' + arrs[2] + '" label="产品编码"  autocheck="true" mustinput="true" inputtype="string" readonly style="padding-right: 20px; "/>'
						+ '<img name="imgTab" align="absmiddle" class="magnifier_mx edit" src="' + ctxPath + '/styles/newTheme/images/plus/select.gif" onClick=\'selPrd(' + rownum + ')\'/>'
						+ '<input type="hidden" id="PRD_ID' + rownum + '" json="PRD_ID" name="PRD_ID" value="' + arrs[3] + '"  />'
						+ '<input type="hidden" id="BRAND' + rownum + '" json="BRAND" name="BRAND" value="' + arrs[24] + '"  />'
						+ '<input type="hidden" id="ROW_NO' + rownum + '" json="ROW_NO" name="ROW_NO" value="' + rownum + '"  />'
						+ '<input type="hidden" id="FROM_BILL_DTL_ID' + rownum + '" json="FROM_BILL_DTL_ID" name="FROM_BILL_DTL_ID" value="' + arrs[36] + '"  />'
				+ '</td>';
	soDtl += '<td nowrap><input type="text" id="PRD_NAME' + rownum + '" json="PRD_NAME" size="15" name="PRD_NAME" value="' + arrs[4] + '" title="' + arrs[4] + '" readonly /></td>';
	
	soDtl += '<td nowrap class="LMM"><input type="text" id="HOLE_SPEC' + rownum + '" json="HOLE_SPEC" size="15" name="HOLE_SPEC" value="' + arrs[6] + '" title="' + arrs[6] + '" '
						+' format="数字*数字*数字" onfocus="tipSpec(this)" onblur="doorSize(' + rownum + ')"/></td>'; /* 门洞尺寸 */
	soDtl += '<td nowrap><input type="text" id="PRD_SPEC' + rownum + '" json="PRD_SPEC" size="15" name="PRD_SPEC" value="' + arrs[23] + '" title="' + arrs[23] + '" '
						+ 'label="规格尺寸" autocheck="" mustinput="" inputtype="string" /></td>'; /* 规格型号/门洞转换的尺寸 */

	soDtl += '<td nowrap class="LMM LYGCG" >'/*
				+'<select type="text" id="PRD_QUALITY' + rownum + '" json="PRD_QUALITY"  name="PRD_QUALITY" value="' + arrs[8] + '" title="' + arrs[8] + '" style="width:100px;" '
				+ 'label="材质" autocheck="true" mustinput="true" inputtype="string" ></select>'*/
				+'<input type="text" id="PRD_QUALITY' + rownum + '" json="PRD_QUALITY" size="10" name="PRD_QUALITY" value="' + arrs[8] + '" title="' + arrs[8] + '" '
				+'label="材质" autocheck="true" mustinput="true" inputtype="string" readonly style="padding-right: 20px;" />'
				+'<img name="imgTab" align="absmiddle" class="magnifier_mx quote" src="' + ctxPath + '/styles/newTheme/images/plus/select.gif" onClick=\'selPRD_QUALITY(' + rownum + ')\'/>'
				+'</td>';
	soDtl += '<td nowrap class="LMM LYGCG"><select type="text" id="PRD_COLOR' + rownum + '" json="PRD_COLOR"  name="PRD_COLOR" value="' + arrs[9] + '" title="' + arrs[9] + '" style="width:90px;" '
				+ 'label="颜色" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select type="text" id="PRD_TOWARD' + rownum + '" json="PRD_TOWARD" name="PRD_TOWARD" value="' + arrs[10] + '" title="' + arrs[10] + '" style="width:60px;" '
				+ 'label="推向" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select type="text" id="PRD_GLASS' + rownum + '" json="PRD_GLASS"  name="PRD_GLASS" value="' + arrs[11] + '" title="' + arrs[11] + '" style="width:90px;" '
				+ 'label="玻璃" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select type="text" id="PRD_OTHER' + rownum + '" json="PRD_OTHER"  name="PRD_OTHER" value="' + arrs[12] + '" title="' + arrs[12] + '" style="width:90px;" '
				+ 'label="其他" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select type="text" id="PRD_SERIES' + rownum + '" json="PRD_SERIES" name="PRD_SERIES" value="' + arrs[13] + '" title="' + arrs[13] + '" style="width:70px;" '
				+ 'label="系列" autocheck="true" mustinput="true" inputtype="string" ></select></td>';

	soDtl += '<td nowrap class=" LYGCG"><input type="text" id="PROJECTED_AREA' + rownum + '" json="PROJECTED_AREA" size="10" name="PROJECTED_AREA" value="' + arrs[26] + '" title="' + arrs[26] + '" /></td>'; /* 投影面积 */
	soDtl += '<td nowrap class=" LYGCG"><input type="text" id="EXPAND_AREA' + rownum + '" json="EXPAND_AREA" size="10" name="EXPAND_AREA" value="' + arrs[27] + '" title="' + arrs[27] + '" /></td>'; /* 展开面积 */
	soDtl += '<td nowrap class=""><select type="text" id="PRD_PLACE' + rownum + '" json="PRD_PLACE" name="PRD_PLACE" value="' + arrs[29] + '" ></select></td>';/* 工程位置 */
	soDtl += '<td nowrap class="LMM"><input type="checkbox" class="" id="IS_NO_LOCK_FLAG' + rownum + '" json="IS_NO_LOCK_FLAG" name="IS_NO_LOCK_FLAG" value="' + arrs[30] + '" ' + lockChecked + ' /></td>';
	
	soDtl += '<td nowrap><input type="text" id="FIGURE_NO' + rownum + '" json="FIGURE_NO" size="8" name="FIGURE_NO" value="' + arrs[31] + '" /></td>';
	soDtl += '<td nowrap><div style="float:left"><input type="hidden" id="hid_attPath' + rownum + '"  json="attPath" name="attPath" value="' + arrs[14] + '" /><button type="button" class="layui-dtlbtn " id="attPath' + rownum + '" lay-data="{fileid:\'/a/\'}" title="上传附件"><img src="'+ctxPath+'/styles/'+theme+'/images/icon/upload.png" class="audit"></button></div><div id="view_attPath' + rownum + '"></div></td>';
	//soDtl += '<td nowrap><select class="edit" id="PROESS_TYPE' + rownum + '" json="PROESS_TYPE"  name="PROESS_TYPE" value="' + arrs[37] + '" disabled></select></td>';

	soDtl += '<td nowrap class="SHOWPRICE"><input type="checkbox" class="quote" id="IS_NO_REBATE_FLAG' + rownum + '" json="IS_NO_REBATE_FLAG" name="IS_NO_REBATE_FLAG" value="' + arrs[15] + '" ' + techChecked + ' /></td>';
	soDtl += '<td nowrap><input type="text" class="decimal" id="IS_BACKUP_FLAG' + rownum + '" json="IS_BACKUP_FLAG" size="3" name="IS_BACKUP_FLAG" value="' + arrs[16] + '" disabled /></td> <!-- //可用库存 -->';
	soDtl += '<td nowrap><input type="text" class="decimal" id="ORDER_NUM' + rownum + '" json="ORDER_NUM" size="5" name="ORDER_NUM" value="' + arrs[20] + '" '
					+'label="数量" autocheck="true" mustinput="true" inputtype="float" '
					+'onkeyup="countPrice(' + rownum + ')"/></td>'; // 数量
	soDtl += '<td nowrap><input type="text" id="STD_UNIT' + rownum + '" json="STD_UNIT" size="2" name="STD_UNIT" value="' + arrs[5] + '" title="' + arrs[5] + '" readonly /></td> <!-- //单位 -->';
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal quote" id="PRICE' + rownum + '" json="PRICE" size="5" name="PRICE" value="' + arrs[17] + '" '
					+'label="计算报价" autocheck="true" mustinput="true" inputtype="float" '
					+'onkeyup="countPrice(' + rownum + ')" /></td>';
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal quote" id="DECT_RATE' + rownum + '" json="DECT_RATE" size="3" name="DECT_RATE" value="' + arrs[18] + '" '
					//+'label="折扣率" autocheck="true" mustinput="true" inputtype="float" valueType="10,2" '
					+'onkeyup="countPrice(' + rownum + ')" /></td>'; // 折扣率
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal quote" id="REBATE_AMOUNT' + rownum + '" json="REBATE_AMOUNT" size="5" name="REBATE_AMOUNT" value="' + arrs[25] + '" '
					//+'label="返利" autocheck="true" mustinput="true" inputtype="float" valueType="10,2" '
					+'readonly /></td>'; // 返利
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal" id="DECT_PRICE' + rownum + '" json="DECT_PRICE" size="5" name="DECT_PRICE" value="'	+ arrs[19] + '" '
					//+'label="最终报价" autocheck="true" mustinput="true" inputtype="float" valueType="10,2" '
					+'readonly/></td>'; // 最终报价
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal" id="ORDER_AMOUNT' + rownum + '" json="ORDER_AMOUNT" size="6" name="ORDER_AMOUNT" value="'	+ arrs[21] + '" '
					//+'label="金额" autocheck="true" mustinput="true" inputtype="float" valueType="10,2" '
					+'readonly/></td>'; // 总金额
	soDtl += '<td nowrap><textarea class="edit" id="REMARK' + rownum + '" json="REMARK" name="REMARK" inputtype="string" title="' + arrs[22] + '" >' + arrs[22].replaceAll('；', '&#13;') + '</textarea></td>';
	soDtl += '</tr>';
	$("#jsontb tr:last-child").append(soDtl);
	$("#jsontb tr:last-child").find("td:gt(0)").click(function(){
		$(this).parent().find("input[json=SALE_ORDER_DTL_ID]").attr("checked","checked");
	});

	changeTile("PRD_QUALITY",rownum);
	changeTile("PRD_COLOR",rownum);
	changeTile("PRD_TOWARD",rownum);
	changeTile("PRD_GLASS",rownum);
	changeTile("PRD_OTHER",rownum);
	changeTile("PRD_SERIES",rownum);
	changeTile("PRD_PLACE",rownum);

	//SelDictShow("PRD_QUALITY" + rownum, "BS_192", arrs[8], "");
	SelDictShow("PRD_COLOR" + rownum, "BS_193", arrs[9], "");
	SelDictShow("PRD_TOWARD" + rownum, "BS_196", arrs[10], "");
	//SelDictShow("PRD_TOWARD" + rownum, "4", arrs[10], " 1=1 and sjzdid='PUSH_TOWARD'");
	SelDictShow("PRD_GLASS" + rownum, "BS_194", arrs[11], "");
	SelDictShow("PRD_OTHER" + rownum, "BS_195", arrs[12], "");
	SelDictShow("PRD_SERIES" + rownum, "BS_206", arrs[13], "");
	SelDictShow("PRD_PLACE" + rownum, "BS_214", arrs[29], "");
	//SelDictShow("PROESS_TYPE" + rownum, "BS_191", arrs[37], "");
	/*uploadFile('attPath' + rownum, "SAMPLE_DIR", true, false, true, "");*/

	var lid = $("#ztid").val();//console.log('lid=',lid);
	// 根据订单组织控制明细字段列的显示/隐藏
	changeDtlCol(lid);
	// 根据是否可查看价格权限控制明细字段价格金额等列的显示/隐藏
	changeDtlPrice();

	var state = $('#state').val();
	var option = $('#option').val();
	if (option && ('design' == option || 'assign' == option || ('待生产' == state && 'turnERP' == option))){
		$('#jsontb tr:last-child :input').not(':button, :submit, :reset, :hidden').attr('disabled', 'disabled');
			$("#jsontb tr:last-child").find('#attPath'+ rownum).attr('disabled', 'disabled');
			$('#jsontb tr:last-child img').remove();
	} else if (option && 'applyaudit' == option){
		/*if(arrs[26] && arrs[26] != ''){
			$("#jsontb tr:last-child input[json='SALE_ORDER_DTL_ID']").attr("checked","checked");
			$("#jsontb tr:last-child input[json='SALE_ORDER_DTL_ID']").attr("disabled","disabled");
		}*/
	} else if (option && ('audit' == option || 'quote' == option || 'confirmquote' == option || 'confirm' == option)){
	} else {
		//form校验设置
		trCheckInit($("#jsontb tr:last-child input"));
		trCheckInit($("#jsontb tr:last-child select"));
		$("#jsontb tr:last-child select").each(function() {
			//$(this).css("width", "120px");
			$(this).css("text-align", "center");
			$(this).css("text-align-last", "center");
		})
	}
	
	//id,folder,isdown,ismulupl是否可传多个,autoCommit是否自动提交,isMain是否主表,isSingle是否单个覆盖
	uploadFile("attPath"+ rownum,'',true,false,true,false,true);

}

// 输入折扣率和计算报价 算出 最终报价、总金额
function countPrice(rownum) {
	var zhekoulv = $("#DECT_RATE" + rownum).val();
	var danjia = $("#PRICE" + rownum).val();
	// 判断输入是否为数字，如果不是数字则默认为100
	var dect_rate = isNaN(parseFloat(zhekoulv)) ? 100 : parseFloat(zhekoulv); // 折扣率
	dect_rate = zhekoulv == 0 ? 100 : zhekoulv; // 折扣率
	$("#DECT_RATE" + rownum).val(dect_rate);
	var temp_ORDER_NUM = $("#ORDER_NUM" + rownum).val();// 数量
	var PRD_NO = $("#PRD_NO" + rownum).val();
	if (null == PRD_NO || "" == PRD_NO) {
		$("#PRICE" + rownum).val("0");
		$("#DECT_RATE" + rownum).val("100");
		$("#DECT_PRICE" + rownum).attr("value", "");
		$("#ORDER_AMOUNT" + rownum).attr("value", "");
		parent.showWarnMsg("请先选择产品编码");
		return;
	}
	var ORDER_NUM = isNaN(parseFloat(temp_ORDER_NUM)) ? 0 : parseFloat(temp_ORDER_NUM); // 数量
	var price = isNaN(parseFloat(danjia)) ? 0 : parseFloat(danjia); // 单价
	var rebate = Number($.trim($('#REBATE_AMOUNT' + rownum).val()));// 返利
	// 总金额，保留2位小数
	var ORDER_AMOUNT = Math.round((isNaN(ORDER_NUM * price * dect_rate) ? 0 : ORDER_NUM * price * dect_rate) ) / 100 - rebate;
	// 折后单价（最终报价 ），保留2位小数
	var DECT_PRICE = Math.round(isNaN(ORDER_AMOUNT / ORDER_NUM) ? 0 : Number(ORDER_AMOUNT / ORDER_NUM) * 100) / 100;
	$("#ORDER_AMOUNT" + rownum).attr("value", ORDER_AMOUNT);
	$("#DECT_PRICE" + rownum).attr("value", DECT_PRICE);
	
	// 计算订单总数量
	countTotalNum();
	// 计算订单总金额
	countTotalRebate();
	countTotalAmount();
}
// 输入数量 算出 总金额
function countAmount(rownum) {
	var temp_ORDER_NUM = $("#ORDER_NUM" + rownum).val();// 数量
	var ORDER_NUM = isNaN(temp_ORDER_NUM) ? 0 : parseFloat(temp_ORDER_NUM); // 数量
	var DECT_PRICE = $("#DECT_PRICE" + rownum).val(); // 最终报价
	if (null != DECT_PRICE && "" != DECT_PRICE) {
		DECT_PRICE = isNaN(DECT_PRICE) ? 0 : parseFloat(DECT_PRICE);
	} else {
		$("#ORDER_AMOUNT" + rownum).attr("value", "");
		//parent.showWarnMsg("请输入最终报价");
		//return;
	}
	// 总金额，保留2位小数
	var ORDER_AMOUNT = Math.round((isNaN(ORDER_NUM * DECT_PRICE) ? 0
			: ORDER_NUM * DECT_PRICE) * 100) / 100;
	$("#ORDER_AMOUNT" + rownum).attr("value", ORDER_AMOUNT);
	
	// 计算订单总金额
	countTotalRebate();
	countTotalAmount();
}

// 门洞尺寸转换
function doorSize(rownum) {
	var HOLE_SPEC = $("#HOLE_SPEC" + rownum).val();
	if('' != HOLE_SPEC){
		var PRD_SPEC = toNewSize(HOLE_SPEC);
		$("#PRD_SPEC" + rownum).attr("value", PRD_SPEC);
	}
}

// 计算订单总数量
function countTotalNum(){
	var sum = 0;
	var ipt_num = $('#jsontb input[json=ORDER_NUM]');
	$.each(ipt_num, function(i, item){
		var num = $(item).val();
		sum += Number(num);
	});
	
	$('#sum').val(sum);
}

// 计算订单总金额
function countTotalRebate(){
	// 计算报价总金额
	var sum = 0;
	var ipt_price = $('#jsontb input[json=PRICE]');
	$.each(ipt_price, function(i, item){
		var price = $(item).val();
		var rownum = $(item).attr('id').replace('PRICE','');
		var num = $('#ORDER_NUM'+rownum).val();
		sum += Number(num*$.trim(price));
	});
	
	$('#total').val(Math.round(sum * 100) /100);
	$('#total').change();
}

// 计算订单总金额
function countTotalAmount(){
	// 最终报价总金额
	var sum = 0;
	var ipt_amount = $('#jsontb input[json=ORDER_AMOUNT]');
	$.each(ipt_amount, function(i, item){
		var amount = $(item).val();
		sum += Number($.trim(amount));
	});
	$('#TOTAL_AMOUNT').val(Math.round(sum * 100) /100);
	// 返利总金额
	sum = 0;
	var ipt_rebate = $('#jsontb input[json=REBATE_AMOUNT]');
	$.each(ipt_rebate, function(i, item){
		var amount = $(item).val();
		sum += Number($.trim(amount));
	});
	$('#TOTAL_REBATE').val(Math.round(sum * 100) /100);
}

// 计算本单返利金额
function countRebateAmount(obj){
	var total = $('#total').val();
	var rebatedsct = $(obj).val();
	if (0 == total) {
		return ;
	}
	if (30 < rebatedsct) {
		showWarnMsg("返利使用上限为30%！");
		$(obj).val(30);
		rebatedsct = 30;
	}
	
	//计算返利总额
	var flje = Math.round(Number(total) * Number(rebatedsct)) /100;
	var curr = Number($('#REBATE_CURRT').val());
	if (flje > curr) {
		showWarnMsg("账户返利余额不足！");
		flje = curr;
		$('#totalRebate').val(flje);
		countRebateDsct('#totalRebate');
		return ;
	}
	$('#totalRebate').val(flje);
	
	//计算明细最终折扣率
	countRebateDsctDtl(rebatedsct);
}

// 计算本单返利
function countRebateDsct(obj){
	var total = Number($('#total').val());
	var flje = Number($(obj).val());
	if (0 == flje) {
		return ;
	}
	if (0 == total) {
		showWarnMsg("请先输入计算报价！");
		$(obj).val('');
		return ;
	}
	var curr = Number($('#REBATE_CURRT').val());
	if (flje > curr) {
		showWarnMsg("账户返利余额不足！");
		$(obj).val(curr);
		flje = curr;
	}
	
	//计算返利 保留2位小数
	var rebatedsct = Math.round((isNaN(flje / total) ? 0.00 : flje / total) * 10000) /100;
	if (30 < rebatedsct) {
		showWarnMsg("返利使用上限为30%！");
		rebatedsct = 30;
		$('#REBATEDSCT').val(rebatedsct);
		countRebateAmount('#REBATEDSCT');
		return ;
	}
	$('#REBATEDSCT').val(rebatedsct);
	
	
	//计算明细最终折扣率
	countRebateDsctDtl(rebatedsct);
}

// 计算明细最终折扣率
function countRebateDsctDtl(rebatedsct){
	var sum_rebate = 0;
	var ipt_rebate = $('input[json=REBATE_AMOUNT]');
	$.each(ipt_rebate, function(i, item){
		var rownum = $(item).attr('id').replace('REBATE_AMOUNT','');
		var num = $('#ORDER_NUM'+rownum).val();
		var price = Number($('#PRICE'+rownum).val());
		var dect_rate = Number($('#DECT_RATE'+rownum).val());
		var rebate = Math.floor(Number(num) * Number(price * dect_rate / 100) * rebatedsct/100);
		$(item).val(rebate);
		var ORDER_AMOUNT = Math.round(Number(num) * Number(price * dect_rate)) / 100 - rebate;
		$("#ORDER_AMOUNT" + rownum).val(ORDER_AMOUNT);
		
		var DECT_PRICE = Math.round(Number(ORDER_AMOUNT / num) * 100) / 100;
		$('#DECT_PRICE'+rownum).val(DECT_PRICE);
		
		sum_rebate += rebate;// 明细返利合计
	});
	
	if(0 == sum_rebate){
		
	} else {
	
		// 修改最后一行的返利、最终报价、金额
		var totalRebate = $('#totalRebate').val();
		var diff_rebate = Math.round(totalRebate*100 - sum_rebate*100) /100;
		var ipt_rebate_lst = $('input[json=REBATE_AMOUNT]:last');
		var last_rebate = ipt_rebate_lst.val();
		var last_rebate_new = Number(last_rebate) + Number(diff_rebate);
		ipt_rebate_lst.val(last_rebate_new);
		var rownum_lst = ipt_rebate_lst.attr('id').replace('REBATE_AMOUNT','');
		
		var num_lst = $('#ORDER_NUM'+rownum_lst).val(); // 数量
		var price_lst = $('#PRICE'+rownum_lst).val();
		var dect_rate_lst = $('#DECT_RATE'+rownum_lst).val();
		// 总金额，保留2位小数
		var amount_lst = Math.round(Number(num_lst) * Number(price_lst * dect_rate_lst)) / 100 - last_rebate_new;
		// 折后单价（最终报价 ），保留2位小数
		var dect_price_lst = Math.round(Number(amount_lst / num_lst) * 100) / 100;
		$("#ORDER_AMOUNT" + rownum_lst).attr("value", amount_lst);
		$("#DECT_PRICE" + rownum_lst).attr("value", dect_price_lst);
	}
	// 计算订单总金额
	countTotalAmount();
}

function dtl_copy() {
	var index = layer.load(1);
	var childData = multiPackJsonData();
	var childJson = eval ("(" + childData + ")");
	for (var i = 0; i < childJson.length; i++) {
		//console.log('childJson['+i+']', childJson[i]);
		var rst_child = childJson[i]
		var arrs = [
			'',//rst_child.SALE_ORDER_DTL_ID,//0
			rst_child.GROUP_NO,//1
			rst_child.PRD_NO,//2
			rst_child.PRD_ID,//3
			rst_child.PRD_NAME,//4
			rst_child.STD_UNIT,//5
			rst_child.HOLE_SPEC,//6
			rst_child.PRD_SIZE,//7
			rst_child.PRD_QUALITY,//8
			rst_child.PRD_COLOR,//9
			rst_child.PRD_TOWARD,//10
			rst_child.PRD_GLASS,//11
			rst_child.PRD_OTHER,//12
			rst_child.PRD_SERIES,//13
			rst_child.attPath,//14
			rst_child.IS_NO_REBATE_FLAG,//15
			rst_child.IS_BACKUP_FLAG,//16
			rst_child.PRICE,//17
			rst_child.DECT_RATE,//18
			rst_child.DECT_PRICE,//19
			rst_child.ORDER_NUM,//20
			rst_child.ORDER_AMOUNT,//21
			rst_child.REMARK,//22
			rst_child.PRD_SPEC,//23
			rst_child.BRAND,//24
			rst_child.REBATE_AMOUNT,//25
			rst_child.PROJECTED_AREA,//26
			rst_child.EXPAND_AREA,//27
			rst_child.ROW_NO,//28
			rst_child.PRD_PLACE ,//29
			rst_child.IS_NO_LOCK_FLAG ,//30
			rst_child.FIGURE_NO ,//31
			'',//32
			'',//33
			'',//34
			'',//35
			rst_child.FROM_BILL_DTL_ID,//36
			rst_child.PROESS_TYPE,//37
			''
		];
		addRow(arrs);
	}
	layer.close(index);
}

function getSelRowId(){
	return parent.document.getElementById("selRowId").value;
}


function selAddrInfo(){
	var chann=$("#RECV_CHANN_ID").val();
	if(""==chann||null==""){
		parent.showWarnMsg(utf8Decode("请选择收货方！"));
		return;
	}
	$("#selectAddrParams").val("DEL_FLAG=0 and STATE='启用' and CHANN_ID='"+chann+"'");
	selCommon('BS_76', false, 'RECV_ADDR_NO', 'DELIVER_ADDR_NO', 'forms[0]','RECV_ADDR_NO,RECV_ADDR', 'DELIVER_ADDR_NO,DELIVER_DTL_ADDR', 'selectAddrParams');
}
function selRecvChann(){
	var RECV_CHANN_ID=$("#RECV_CHANN_ID").val();
	selCommon('BS_19', false, 'RECV_CHANN_ID', 'CHANN_ID', 'forms[0]','RECV_CHANN_ID,RECV_CHANN_NO,RECV_CHANN_NAME', 'CHANN_ID,CHANN_NO,CHANN_NAME', 'selOrderChannParam');
	var newRECV_CHANN_ID=$("#RECV_CHANN_ID").val();
	if(RECV_CHANN_ID!=newRECV_CHANN_ID){
		$("RECV_ADDR_NO").val("");
		$("RECV_ADDR").val("");
	}
}
function selOrderChann(){
	selCommon('BS_19', false, 'ORDER_CHANN_ID', 'CHANN_ID', 'forms[0]','ORDER_CHANN_ID,ORDER_CHANN_NO,ORDER_CHANN_NAME,PERSON_CON,TEL,SHIP_POINT_ID,SHIP_POINT_NAME,AREA_ID,AREA_NO,AREA_NAME', 'CHANN_ID,CHANN_NO,CHANN_NAME,PERSON_CON,TEL,SHIP_POINT_ID,SHIP_POINT_NAME,AREA_ID,AREA_NO,AREA_NAME', 'selOrderChannParam');
}
 function selStore(){
	 var CHANN_ID = $("#RECV_CHANN_ID").val();
	if(null == CHANN_ID || "" == CHANN_ID){
		parent.showWarnMsg("请先选择收货方信息")
		return;
	}
	 var v = "STATE ='启用' and DEL_FLAG='0' and BEL_CHANN_ID='"+CHANN_ID+"' "
	 $("#selectStore").val(v);
	 selCommon('BS_35', false, 'STORE_ID', 'STORE_ID', 'forms[0]','STORE_NO,STORE_NAME', 'STORE_NO,STORE_NAME', 'selectStore')
 }
 function getCHANN_ID(){
	 return $("#ORDER_CHANN_ID").val();
 }
 function getBILL_TYPE(){
	 return $("#BILL_TYPE").val();
 }

//通用选取回调函数
function endSelCommBack(selId){
	if ("BS_202" == selId) {console.log('selId', selId);
		var selRowId = getSelRowId();
		var option = $('#option').val();
		var status = $(this).attr('status');
		$('#auditStatus').val(status);
		// 获取主表json数据
		var parentData = siglePackJsonData('jsontbP');
		//console.log('parentData', parentData);
		var childData = multiPackJsonData();
		//console.log('childData', childData);
		
	}
}

function changeinput(e){
	var newinput = document.getElementById('ipt')
	var newmytable =document.getElementById('toggleTable')
	if(newmytable.style.display == 'none'){
		newmytable.style.display = '';
		newinput.value="收起"
		imgsz.style.transform="rotate(0deg)"

	}else{
		newmytable.style.display='none';
		newinput.value = "展开";
		/*transform:rotate(180deg);*/
		imgsz.style.transform="rotate(180deg)"

	}
}

function selFlow(){
	var flowNo = $("#flowNo").val();
	var ORDER_TRACE_ID = $("#ORDER_TRACE_ID").val();
	$("#flowSel").val(" FLOW_NO = '"+flowNo+"' and TABLE_NAME = 'DRP_GOODS_ORDER' and FLOW_SERVICE_ID='"+ORDER_TRACE_ID+"' ");
	selCommon("BS_202", false, "stateBack", "STATE_VAL", "forms[0]", "stateBack", "STATE_VAL", "flowSel");

}

function showTips() {
}

