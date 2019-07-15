/**
 * 发货管理模块
 * 返修发货编辑
 */
$(function() {
	//
	 if(!($("#SALE_ORDER_NOS").val() == null && $("#SALE_ORDER_NOS") == "")){
        //$("#SALE_ORDER_NOS").attr("readonly","readonly");
        //$("#FACTORY_NO").css("background-color","#cccccc");
     }
	
	if(!($("#SALE_ORDER_IDS").val() == "")){
		$("#imgID").attr("hidden","true");
	}else{
		//$("#bodycss1").attr("hidden","true");
	}
	var option = $('#option').val();
	if (option && 'audit' == option){
		$('#mainForm :input').not(':button, :submit, :reset, :hidden').attr('disabled', 'disabled');
		$('#mainForm img').hide();
		$('#mainForm #ATT_PATH').removeAttr('disabled');
		$('.child :input').not(':button, :submit, :reset, :hidden').attr('disabled', 'disabled');
		$('.child img').hide();
		$('.child input[json=sale_order_dtl_id]').removeAttr('disabled');
	} else if (option && 'quote' == option){
		$('#mainForm :input').not(':button, :submit, :reset, :hidden').attr('disabled', 'disabled');
		$('#mainForm img').hide();
	} else {
		// 初始化校验
		InitFormValidator(0);
	}
	$("#BILL_TYPE").click(function() {
		var IS_DRP_LEDGER = $("#IS_DRP_LEDGER").val(); // 1:经销商， 0：总部
		if (IS_DRP_LEDGER == "1") {
			$("#BILL_TYPE option[value='内部销售']").remove();
			$("#BILL_TYPE option[value='工程样品']").remove();
			$("#BILL_TYPE option[value='工程订单']").remove();
			$("#BILL_TYPE option[value='改补单']").remove();
			$("#BILL_TYPE option[value='清库销售']").remove();
			$("#BILL_TYPE option[value='网络销售']").remove();
		} else {
			$("#BILL_TYPE option[value='常规订单']").remove();
			$("#BILL_TYPE option[value='常规样品']").remove();
		}
	});
	// 订单组织下拉框事件
	$('#LEDGER_ID').change(function() {
		var lname = $("#LEDGER_ID option:selected").text();
		$("#LEDGER_NAME").val(lname);
	});

	$("#add").click(function() {
		
		addRow();
	});

	$("#deleteDtl").click(function() {
			// 查找当前是否有选中的记录
			var checkBox = $("#jsontb tr:gt(0) input[json=sale_order_dtl_id]:checked");
			if (checkBox.length < 1) {
				parent.showErrorMsg("请至少选择一条记录");
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
			} else {
				// parent.showConfirm("您确认要删除吗","bottomcontent.multiRecDeletes();");
				showConfirm("您确认要删除吗", "multiRecDeletes();");
			}
			// 删除后scroll可能消失，因此需要重新调整浮动按钮的位置
			setFloatPostion();
		});

	$("#save").click(	function() {
			// 查找当前是否有选中的记录
			var checkBox = $("#jsontb tr:gt(0) input[json=sale_order_dtl_id]:checked");
			if (checkBox.length < 1) {
				parent.showErrorMsg("请至少选择一条记录");
				return;
			}
			// 对于选中的明细校验
			if (!formMutiTrChecked()) {
				return;
			}
			//
			allSave();
		});
		
	$('#audit').click(function(){
		var selRowId = document.getElementById("selRowId").value;
		var parentData = "{'GOODS_ORDER_ID':'"+selRowId+"'}";
		//console.log('parentData', parentData);
		var childData = multiPackJsonData();
		//console.log('childData', childData);
	});

	  $("#allChecked").click(
			function() {
				var flag = document.getElementById("allChecked").checked;
				if (flag) {
					$("#jsontb input[json=sale_order_dtl_id]:checkbox").attr(
							"checked", "true");
				} else {
					$("#jsontb input[json=sale_order_dtl_id]:checkbox")
							.removeAttr("checked");
				}
			});
	  
	  var lid = $("#LEDGER_ID").val();
	  //console.log("lid:"+lid);
	  //根据订单组织控制明细字段列的显示/隐藏
	  changeDtlCol(lid);
	  //上传文件
	  displayDownFile('ATT_PATH',true);
});



function allSave() {
	
	// 明细校验
	$("#SALE_ORDER_ID").val();	
	if (!formMutiTrChecked()) {
		btuMxRest();
		return;
	}
	var selRowId = document.getElementById("selRowId").value;
	var erjiOrderId = document.getElementById("erjiOrderId").value;
	// 获取主表json数据
	var parentData = siglePackJsonData('jsontbP');

	var childData;
	if ($("#jsontb tr:gt(0) input[json=sale_order_dtl_id]:checked").length > 0) {
		childData = multiPackJsonData();
	}
	$.ajax({
		url : "edit",
		type : "POST",
		async : false,
		dataType : "json",
		data : {
			"parentJsonData" : parentData,
			"childJsonData" : childData,
			"GOODS_ORDER_ID" : selRowId,
			"erjiOrderId" : erjiOrderId
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

// 删除操作
function multiRecDeletes() {
	// 查找当前是否有选中的记录
	var checkBox = $("#jsontb tr:gt(0) input:checked");
	// 获取所有选中的记录
	var ids = "";
	checkBox.each(function() {
		ids = ids + "'" + $(this).val() + "',";
	});
	ids = ids.substr(0, ids.length - 1);

	$.ajax({
		url : "deleteDtl",
		type : "POST",
		dataType : "text",
		data : {
			"SALE_ORDER_DTL_IDS" : ids
		},
		complete : function(xhr) {
			eval("jsonResult = " + xhr.responseText);
			if (jsonResult.success === true) {
				parent.showMsgPanel("删除成功");
				checkBox.parent().parent().remove();
				// 设置删除操作。有删除时返回后需要刷新页面。否则直接跳转上一页。
				$("#delFlag").val("true");
			} else {
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}
function selPrd(rownum) {
	selCommon("BS_21", false, "PRD_ID" + rownum, "PRD_ID", "forms[1]", "PRD_ID"
			+ rownum + ",PRD_NO" + rownum + ",PRD_NAME" + rownum + ",STD_UNIT"
			+ rownum + ",PRD_SPEC" + rownum + ",BRAND"
			+ rownum,
			"PRD_ID,PRD_NO,PRD_NAME,STD_UNIT,PRD_SPEC,BRAND");
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

function endSelCommBack(selId){
	if(selId=="BS_70"){
	   $("#mainForm").attr("action","toEdit");
	   mainForm.submit();
	}
	   
}
// 固定的一些共通的方法 end
// 下面这个方法要自己修改
// 表格增加一行
function addRow(arrs) {
	
	var soDtlEmpty = $("#soDtlEmpty").html();
	if (soDtlEmpty != null || soDtlEmpty != '') {
		$("#soDtlEmpty").remove();
	}
	if(null == arrs){
		arrs = [ '', '', '', '', '', '', '', '', '无', '无', '无', '无', '无', '无', '',
				'', '', '', '100', '', '1', '', '', '', '', 
				'0', '', '', '', '无', '1', '', '', '', '' 
				, '','', '', '', ''];
	}
	
	var lockChecked = '1' == arrs[30] ? "checked" : "";
	// 样式行
	var rownum = $("#jsontb tr").length;
	// console.log(rownum)
	var classrow = rownum % 2;
	rownum = _row_num;
	_row_num++;
	var techChecked = "";
	if (1 == arrs[15]) {
		techChecked = "checked";
	}
	$("#jsontb tr:last-child").after("<tr style='height:42px;' class='list_row"+classrow+"' onmouseover='mover(this)' onmouseout='mout(this)'></tr>");
	$("#jsontb tr:last-child").append("<td nowrap align='center'><input type='checkbox' style='height:12px;valign:middle' id='eid"+rownum+"' json='sale_order_dtl_id' name='SALE_ORDER_DTL_ID' value='"+arrs[0]+"' checked></td>")
	
	var soDtl = "";
	soDtl += '<td nowrap class="LMM"><input type="text" id="GROUP_NO' + rownum + '" json="group_no" size="3" name="GROUP_NO" value="' + arrs[1]	+ '"  /></td>';
	soDtl += '<td nowrap><input type="text" id="PRD_NO' + rownum + '" json="prd_no" size="10" name="PRD_NO" value="' + arrs[2] + '" label="产品编码"  autocheck="true" mustinput="true" inputtype="string" readonly style="padding-right: 20px; "/>'
						+ '<img name="imgTab" align="absmiddle" class="magnifier_mx edit" src="' + ctxPath + '/styles/newTheme/images/plus/select.gif" onClick=\'selPrd(' + rownum + ')\'/>'
						+ '<input type="hidden" id="PRD_ID' + rownum + '" json="prd_id" name="PRD_ID" value="' + arrs[3] + '"  />'
						+ '<input type="hidden" id="BRAND' + rownum + '" json="brand" name="BRAND" value="' + arrs[24] + '"  />'
						+ '<input type="hidden" id="ROW_NO' + rownum + '" json="row_no" name="ROW_NO" value="' + rownum + '"  />'
						+ '<input type="hidden" id="FROM_BILL_DTL_ID' + rownum + '" json="from_bill_dtl_id" name="FROM_BILL_DTL_ID" value="' + arrs[36] + '"  />'
				+ '</td>';
	soDtl += '<td nowrap><input type="text" id="PRD_NAME' + rownum + '" json="prd_name" size="15" name="PRD_NAME" value="' + arrs[4]	+ '" readonly /></td>';
	soDtl += '<td nowrap><input type="text" id="STD_UNIT' + rownum + '" json="std_unit" size="2" name="STD_UNIT" value="' + arrs[5]	+ '" readonly /></td> <!-- //单位 -->';
	soDtl += '<td nowrap class="LMM"><input type="text" id="HOLE_SPEC' + rownum + '" json="hole_spec" size="15" name="HOLE_SPEC" value="' + arrs[6] + '"  onblur="doorSize(' + rownum + ')"/></td>'; /* 门洞尺寸 */
	soDtl += '<td nowrap><input type="text" id="PRD_SPEC' + rownum + '" json="prd_spec" size="15" name="PRD_SPEC" value="' + arrs[23] + '" '
						+ 'label="规格尺寸" autocheck="" mustinput="" inputtype="string" format="数字*数字*数字" onfocus="tipSpec(this)" /></td>'; /* 规格型号/门洞转换的尺寸 */
	soDtl += '<td nowrap class="LMM LYGCG" ><select type="text" id="PRD_QUALITY' + rownum + '" json="prd_quality"  name="PRD_QUALITY" value="' + arrs[8] + '" style="width:100px;" '
				+ 'label="材质" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM LYGCG"><select type="text" id="PRD_COLOR' + rownum + '" json="prd_color"  name="PRD_COLOR" value="' + arrs[9] + '" style="width:90px;" '
				+ 'label="颜色" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select type="text" id="PRD_TOWARD' + rownum + '" json="prd_toward" name="PRD_TOWARD" value="' + arrs[10] + '" style="width:60px;" '
				+ 'label="推向" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select type="text" id="PRD_GLASS' + rownum + '" json="prd_glass"  name="PRD_GLASS" value="' + arrs[11] + '" style="width:90px;" '
				+ 'label="玻璃" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select type="text" id="PRD_OTHER' + rownum + '" json="prd_other"  name="PRD_OTHER" value="' + arrs[12] + '" style="width:90px;" '
				+ 'label="其他" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select type="text" id="PRD_SERIES' + rownum + '" json="prd_series" name="PRD_SERIES" value="' + arrs[13] + '" style="width:70px;" '
				+ 'label="系列" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class=" LYGCG"><input type="text" id="PROJECTED_AREA' + rownum + '" json="projected_area" size="10" name="PROJECTED_AREA" value="' + arrs[26] + '" /></td>'; /* 投影面积 */
	soDtl += '<td nowrap class=" LYGCG"><input type="text" id="EXPAND_AREA' + rownum + '" json="expand_area" size="10" name="EXPAND_AREA" value="' + arrs[27] + '" /></td>'; /* 展开面积 */
	soDtl += '<td nowrap class=""><select type="text" id="PRD_PLACE' + rownum + '" json="prd_place" name="PRD_PLACE" value="' + arrs[29] + '" ></select></td>';/* 工程位置 */
	soDtl += '<td nowrap class="LMM"><input type="checkbox" class="" id="IS_NO_LOCK_FLAG' + rownum + '" json="is_no_lock_flag" name="IS_NO_LOCK_FLAG" value="' + arrs[30] + '" ' + lockChecked + ' /></td>';
	
	soDtl += '<td nowrap><input type="text" id="FIGURE_NO' + rownum + '" json="figure_no" size="8" name="FIGURE_NO" value="' + arrs[31] + '" /></td>';
	soDtl += '<td nowrap><div style="float:left"><input type="hidden" id="hid_ATT_PATH' + rownum + '"  json="ATT_PATH" name="ATT_PATH" value="' + arrs[14] + '" /><button type="button" class="layui-dtlbtn " id="ATT_PATH' + rownum + '" lay-data="{fileid:\'/a/\'}" title="上传附件"><img src="'+ctxPath+'/styles/'+theme+'/images/icon/upload.png" class="audit"></button></div><div id="view_ATT_PATH' + rownum + '"></div></td>';
	//soDtl += '<td nowrap><select class="edit" id="PROESS_TYPE' + rownum + '" json="PROESS_TYPE"  name="PROESS_TYPE" value="' + arrs[37] + '" disabled></select></td>';

	soDtl += '<td nowrap><input type="checkbox" class="quote" id="IS_NO_REBATE_FLAG' + rownum + '" json="is_no_rebate_flag" name="IS_NO_REBATE_FLAG" value="' + arrs[15] + '" ' + techChecked + ' /></td>';
	//soDtl += '<td nowrap><input type="text" class="decimal" id="IS_BACKUP_FLAG' + rownum + '" json="is_backup_flag" size="3" name="IS_BACKUP_FLAG" value="' + arrs[16] + '" disabled /></td> <!-- //可用库存 -->';
	soDtl += '<td nowrap><input type="text" class="decimal" id="ORDER_NUM' + rownum + '" json="order_num" size="5" name="ORDER_NUM" value="' + arrs[20] + '" '
					+'label="数量" autocheck="true" mustinput="true" inputtype="int" '
					+'onkeyup="countAmount(' + rownum + ')"/></td>'; // 数量
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal quote" id="PRICE' + rownum + '" json="price" size="5" name="PRICE" value="' + arrs[17] + '" '
					+'label="计算报价" autocheck="true" mustinput="true" inputtype="float" '
					+'onkeyup="countPrice(' + rownum + ')" /></td>';
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal quote" id="DECT_RATE' + rownum + '" json="dect_rate" size="3" name="DECT_RATE" value="' + arrs[18] + '" '
					//+'label="折扣率" autocheck="true" mustinput="true" inputtype="float" valueType="10,2" '
					+'onkeyup="countPrice(' + rownum + ')" /></td>'; // 折扣率
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal quote" id="REBATE_AMOUNT' + rownum + '" json="rebate_amount" size="5" name="REBATE_AMOUNT" value="' + arrs[25] + '" '
					//+'label="返利" autocheck="true" mustinput="true" inputtype="float" valueType="10,2" '
					+'readonly /></td>'; // 返利
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal" id="DECT_PRICE' + rownum + '" json="dect_price" size="5" name="DECT_PRICE" value="'	+ arrs[19] + '" '
					//+'label="最终报价" autocheck="true" mustinput="true" inputtype="float" valueType="10,2" '
					+'readonly/></td>'; // 最终报价
	soDtl += '<td nowrap class="SHOWPRICE"><input type="text" class="decimal" id="ORDER_AMOUNT' + rownum + '" json="order_amount" size="6" name="ORDER_AMOUNT" value="'	+ arrs[21] + '" '
					//+'label="金额" autocheck="true" mustinput="true" inputtype="float" valueType="10,2" '
					+'readonly/></td>'; // 总金额
	soDtl += '<td nowrap><input class="edit" type="text" id="REMARK' + rownum	+ '" json="remark" size="15" name="REMARK" value="' + arrs[22]	+ '" '+'/></td>';
	soDtl += '</tr>';
	$("#jsontb tr:last-child").append(soDtl);
	
	//uploadFile('PATH' + rownum, "SAMPLE_DIR", true, false, true, "");
	uploadFile("ATT_PATH"+ rownum,'',true,false,true,false,true);
	
	SelDictShow("PRD_QUALITY" + rownum, "BS_192", arrs[8], "");
	SelDictShow("PRD_COLOR" + rownum, "BS_193", arrs[9], "");
	SelDictShow("PRD_TOWARD" + rownum, "BS_196", arrs[10], "");
	//SelDictShow("PRD_TOWARD" + rownum, "4", arrs[10], " 1=1 and sjzdid='PUSH_TOWARD'");
	SelDictShow("PRD_GLASS" + rownum, "BS_194", arrs[11], "");
	SelDictShow("PRD_OTHER" + rownum, "BS_195", arrs[12], "");
	SelDictShow("PRD_SERIES" + rownum, "BS_206", arrs[13], "");
	SelDictShow("PRD_PLACE" + rownum, "BS_214", arrs[29], "");
	

	var option = $('#option').val();
	if (option && 'audit' == option){
	} else {
		// form校验设置
		trCheckInit($("#jsontb tr:gt(" + (rownum - 1) + ") input"));
		trCheckInit($("#jsontb tr:gt(" + (rownum - 1) + ") select"));
		$("#jsontb tr:gt(" + (rownum - 1) + ") select").each(function() {
			$(this).css("width", "120px");
			$(this).css("text-align", "center");
			$(this).css("text-align-last", "center");
		})
	}
	var lid = $("#LEDGER_ID").val();
	//console.log("lid:"+lid);
	//根据订单组织控制明细字段列的显示/隐藏
	changeDtlCol(lid);
}

// 输入折扣率 算出 最终报价、总金额
function countPrice(rownum) {
	
	var danjia = $("#PRICE" + rownum).val();
	// 判断输入是否为数字，如果不是数字则默认为0
	
	var temp_ORDER_NUM = $("#ORDER_NUM" + rownum).val();// 数量
	var PRD_NO = $("#PRD_NO" + rownum).val();
	if (null == PRD_NO || "" == PRD_NO) {
		$("#DECT_PRICE" + rownum).attr("value", "");
		$("#ORDER_AMOUNT" + rownum).attr("value", "");
		parent.showErrorMsg("请先选择产品编码");
		return;
	}
	var price = isNaN(danjia) ? 0 : parseFloat(danjia); // 单价
	// 折后单价（最终报价 ），保留2位小数
	var DECT_PRICE = Math.round((isNaN(price) ? 0 : price) * 100) / 100;
	var ORDER_NUM = isNaN(temp_ORDER_NUM) ? 0 : parseFloat(temp_ORDER_NUM); // 数量
	// 总金额，保留2位小数
	var ORDER_AMOUNT = Math.round((isNaN(ORDER_NUM * DECT_PRICE) ? 0
			: ORDER_NUM * DECT_PRICE) * 100) / 100;
	$("#PRICE" + rownum).attr("value", DECT_PRICE);
	$("#DECT_PRICE" + rownum).attr("value",DECT_PRICE);
	$("#ORDER_AMOUNT" + rownum).attr("value", ORDER_AMOUNT);
	
	
	
	countTotalRebate();
	countTotalAmount();
}
// 输入数量 算出 总金额 
function countAmount(rownum) {
	var temp_ORDER_NUM = $("#ORDER_NUM" + rownum).val();// 数量
	var ORDER_NUM = isNaN(temp_ORDER_NUM) ? 0 : parseFloat(temp_ORDER_NUM); // 数量
	var DECT_PRICE = $("#PRICE" + rownum).val(); // 最终报价
	if (null != DECT_PRICE && "" != DECT_PRICE) {
		DECT_PRICE = isNaN(DECT_PRICE) ? 0 : parseFloat(DECT_PRICE);
	} else {
		$("#PRICE" + rownum).attr("value", "");
		parent.showErrorMsg("请输入单价");
		return;
	}
	// 总金额，保留2位小数
	var ORDER_AMOUNT = Math.round((isNaN(ORDER_NUM * DECT_PRICE) ? 0
			: ORDER_NUM * DECT_PRICE) * 100) / 100;
	$("#ORDER_AMOUNT" + rownum).attr("value", ORDER_AMOUNT);
	
	
	
	countTotalRebate();
	countTotalAmount();
}



//计算订单总金额
function countTotalRebate(){
	// 计算报价折后总金额
	var sum = 0;
	var ipt_price = $('#jsontb input[json=PRICE]');
	$.each(ipt_price, function(i, item){
		var price = $(item).val();
		var rownum = $(item).attr('id').replace('PRICE','');
		var num = $('#ORDER_NUM'+rownum).val();
		var dect_rate = $('#DECT_RATE'+rownum).val();
		sum += Number(num*$.trim(price)*$.trim(dect_rate));
	});
	
	//$('#total').val(Math.round(sum) /100);
}
// 修改最终报价 算出 计算报价和金额
function changeDectPrice(rownum) {
	var DECT_PRICE = $("#DECT_PRICE" + rownum).val(); // 最终报价
	if (DECT_PRICE && "" != DECT_PRICE) {
		DECT_PRICE = isNaN(DECT_PRICE) ? 0 : parseFloat(DECT_PRICE);
	}
	// 单价（计算报价 ），保留2位小数
	var PRICE = Math.round((isNaN(DECT_PRICE) ? DECT_PRICE: DECT_PRICE) * 100) / 100;
	$("#PRICE" + rownum).val(PRICE);
	var temp_ORDER_NUM = $("#ORDER_NUM" + rownum).val();// 数量
	var ORDER_NUM = isNaN(temp_ORDER_NUM) ? 0 : parseFloat(temp_ORDER_NUM); // 数量
	// 总金额，保留2位小数
	var ORDER_AMOUNT = Math.round((isNaN(ORDER_NUM * DECT_PRICE) ? 0
			: ORDER_NUM * DECT_PRICE) * 100) / 100;
	$("#ORDER_AMOUNT" + rownum).attr("value", ORDER_AMOUNT);
}
// 修改金额 算出 计算报价和单价
function changeAmount(rownum) {
	var ORDER_AMOUNT = $("#ORDER_AMOUNT" + rownum).val();// 总金额
	var temp_ORDER_NUM = $("#ORDER_NUM" + rownum).val();// 数量
	var ORDER_NUM = isNaN(temp_ORDER_NUM) ? 0 : parseFloat(temp_ORDER_NUM); // 数量
	// 折后单价（最终报价 ），保留2位小数
	var DECT_PRICE = Math.round((isNaN(ORDER_AMOUNT / ORDER_NUM) ? 0
			: ORDER_AMOUNT / ORDER_NUM) * 100) / 100;
	//$("#DECT_PRICE" + rownum).val(DECT_PRICE);
	
	
	// 单价（计算报价 ），保留2位小数
	var PRICE = Math.round((isNaN(DECT_PRICE) ? DECT_PRICE: DECT_PRICE) * 100) / 100;
	$("#PRICE" + rownum).val(PRICE);
}

// 门洞尺寸转换
function doorSize(rownum) {
	var HOLE_SPEC = $("#HOLE_SPEC" + rownum).val();
	var PRD_SIZE = toNewSize(HOLE_SPEC);
	$("#PRD_SIZE" + rownum).attr("value", PRD_SIZE);
}

//

function selSaleOrder(){
   selCommon('BS_70',false,'SALE_ORDER_ID','SALE_ORDER_ID','forms[0]','SALE_ORDER_ID','SALE_ORDER_ID','BMZT');
}


//选择经销商
//function selChann() {
//	var XTYHID = $('#SALE_ID').val();
//	var initSel = " STATE = '启用' and IS_BASE_FLAG = '0' and CHANN_ID in( select t.chann_id from BASE_USER_CHANN_CHRG t where t.user_id='" + XTYHID + "') ";
//	$('#initSel').val(initSel);
//	selCommon("BS_19", false, "CHANN_ID", "CHANN_ID", "forms[0]", 
//			"CHANN_ID,CHANN_NO,CHANN_NAME",
//			"CHANN_ID,CHANN_NO,CHANN_NAME", "initSel");
//	
//}


//选择经销商
function selChann() {
	var XTYHID =$("#XTYHID").val();
	var initSel = " IS_BASE_FLAG = '0' and CHANN_ID in( select t.chann_id from BASE_USER_CHANN_CHRG t where t.user_id='" + XTYHID + "') ";
	$('#initSel').val(initSel);
	/*selCommon("BS_205", false, "CHANN_ID", "CHANN_ID", "forms[0]", 
			"CHANN_NO,CHANN_NAME,CHANN_ID,PERSON_CON,TEL,RECV_ADDR,PRINT_NAME",
			"CHANN_NO,CHANN_NAME,CHANN_ID,PERSON_CON,TEL,DELIVER_DTL_ADDR,CHANN_ABBR", "initSel");*/
	selCommon("BS_205", false, "RECV_CHANN_ID", "CHANN_ID", "forms[0]",
			"ORDER_CHANN_ID,ORDER_CHANN_NO,ORDER_CHANN_NAME,RECV_CHANN_NO,RECV_CHANN_NAME,CHANN_ID,CHANN_NO,CHANN_NAME,PRINT_NAME"
			+ ",PERSON_CON,TEL,RECV_ADDR,TRANSPORT",
			"CHANN_ID,CHANN_NO,CHANN_ABBR,CHANN_NO,CHANN_ABBR,CHANN_ID,CHANN_NO,CHANN_NAME,CHANN_ABBR"
			+ ",PERSON_CON,TEL,DELIVER_DTL_ADDR,TRANSPORT", "initSel");
	
}

//function selChann() {
//	var LEDGER_ID = $('#LEDGER_ID').val();
//	if ('' == LEDGER_ID) {
//		parent.showWarnMsg("请先选择订单组织");
//		return;
//	}
//	var XTYHID = $('#SALE_ID').val();
//	var initSel = " STATE = '启用' and IS_BASE_FLAG = '0' and nvl(CHANN_ID_P, '1')='1' "
//					+ " and CHANN_ID in( select t.chann_id from BASE_USER_CHANN_CHRG t where t.user_id='" + XTYHID + "') "
//					+ " and CHANN_ID in( select t.chann_id from BASE_CHANN_LEDGER_CHRG t where t.ledger_id='" + LEDGER_ID + "') ";
//	$('#initSel').val(initSel);/*
//	selCommon("BS_19", false, "RECV_CHANN_ID", "CHANN_ID", "forms[0]",
//			"ORDER_CHANN_ID,ORDER_CHANN_NO,ORDER_CHANN_NAME,RECV_CHANN_NO,RECV_CHANN_NAME,CHANN_ID,CHANN_NO,CHANN_NAME,PRINT_NAME",
//			"CHANN_ID,CHANN_NO,CHANN_NAME,CHANN_NO,CHANN_NAME,CHANN_ID,CHANN_NO,CHANN_NAME,CHANN_ABBR", "initSel");*/
//	selCommon("BS_205", false, "RECV_CHANN_ID", "CHANN_ID", "forms[0]",
//			"ORDER_CHANN_ID,ORDER_CHANN_NO,ORDER_CHANN_NAME,RECV_CHANN_NO,RECV_CHANN_NAME,CHANN_ID,CHANN_NO,CHANN_NAME,PRINT_NAME"
//			+ ",PERSON_CON,TEL,RECV_ADDR,TRANSPORT",
//			"CHANN_ID,CHANN_NO,CHANN_ABBR,CHANN_NO,CHANN_ABBR,CHANN_ID,CHANN_NO,CHANN_NAME,CHANN_ABBR"
//			+ ",PERSON_CON,TEL,DELIVER_DTL_ADDR,TRANSPORT", "initSel");
//	
//	
//}
//
function changeinput(e){
	var newinput = document.getElementById('ipt')
	var newmytable =document.getElementById('jsontbP')
	var new_img = document.getElementById('imgsz')  
	if(newmytable.style.display == 'none'){
		newmytable.style.display = '';
		newinput.value="收起"
		new_img.style.transform="rotate(0deg)"
	/*	imgsz.style.transform="rotate(0deg)"*/
	}else{
		newmytable.style.display='none';
		newinput.value = "展开";
		/*transform:rotate(180deg);*/
		new_img.style.transform="rotate(180deg)"
		
	}	
 }
