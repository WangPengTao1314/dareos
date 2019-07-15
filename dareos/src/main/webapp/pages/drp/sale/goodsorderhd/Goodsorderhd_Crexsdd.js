/**
 * @prjName:喜临门营销平台
 * @fileName:Goodsorderhd_Edit
 */
$(function() {
	for (var i=0;i<$('.xsddExt').length;i++) {
		// 初始化校验
		InitFormValidator('mainForm'+i);
	}

	$('#mainForm :input').not(':button, :submit, :reset, :hidden, .edit').attr('disabled', 'disabled');
	$('.child :input').not(':button, :submit, :reset, :hidden, .edit').attr('disabled', 'disabled');

	//上传文件
	displayDownFile('attPath',true);

	$("#create").click(function() {
		//
		allSave();
	});
	
	$("#back").click(function() {
		doBack();
	});

	$("#allChecked").click( function() {
		var flag = document.getElementById("allChecked").checked;
		if (flag) {
			$("#jsontb input[json=GOODS_ORDER_DTL_ID]:checkbox").attr(
					"checked", "true");
		} else {
			$("#jsontb input[json=GOODS_ORDER_DTL_ID]:checkbox")
					.removeAttr("checked");
		}
	});
});

//获取厂编
function getFactoryNo(){
	var parentData = siglePackJsonData('jsontbP');
	$.ajax({
		url: ctxPath+"/erp/saleorder/getFactoryNo",
		type : "GET",
		async : false,
		dataType : "json",
		data : {
			"parentJsonData" : parentData
		},
		complete : function(res) {
			console.log(res);
			//$("#ORDER_DELIVERY_DATE").val(res.data);
		}
	});

}

//修改厂编
function changeFactoryNo(obj){
	var dateStr = $(obj).val();//console.log(dateStr);
	var datePart = dateStr.split('-');
	var xtbs = $('#xtbs').val();
	var partNew = xtbs+datePart[1]+datePart[2];//console.log(partNew);
	for (var i=0;i<$('.xsddExt').length;i++) {
		var FACTORY_NO = $('#FACTORY_NO'+i).val();//console.log(FACTORY_NO);
		var part = FACTORY_NO.split('-')[0];//console.log(part);
		var no = FACTORY_NO.replace(part, partNew);//console.log(no);
		$('#FACTORY_NO'+i).val(no);
	}
	
	var PRE_RECV_DATE = $('#PRE_RECV_DATE').val();
	if('' == PRE_RECV_DATE){
		var date = new Date(dateStr.replace(/-/g,"\/"));console.log(date);
		date.setDate(date.getDate() + 7);
		var dateNew = dateFormat(date);
		$('#PRE_RECV_DATE').val(dateNew);
	}
	
}

// 主子表保存
function allSave() {
	var selRowId = document.getElementById("selRowId").value;
	//console.log('selRowId:', selRowId);
	// 获取订货订单主表json数据
	var parentData = siglePackJsonData('jsontbP');
	//console.log('parentData', parentData);
	// 封装成json,备用
	var parentJson = eval ("(" + parentData + ")");
	parentJson.attPath = '';

	var len = $('.xsddExt').length;
	var jsonArr = new Array(len)
	for (var i=0;i<len;i++) {
		// 主表table校验
		var parentCheckedRst = formChecked('jsontbP'+i);
		if (!parentCheckedRst) {
			return;
		}
		// 明细校验
		if (!formMutiTrChecked('jsontb'+i)) {
			return;
		}
		// 拆分后的销售订单的主表信息
		var xsddData = siglePackJsonData('jsontbP'+i); //console.log('xsddData:', xsddData);
		// 拆分后的销售订单的明细信息
		var childData = multiPackJsonData('jsontb'+i); //console.log('childData:', childData);
		
		// 先封装成json，再转json字符串
		var xsddJson = eval ("(" + xsddData + ")"); //console.log('xsddJson:', xsddJson);
		var childJson = eval ("(" + childData + ")"); //console.log('childJson:', childJson);
		var xsddJsonNew = $.extend({}, parentJson, xsddJson);
		xsddJsonNew.childList = childJson;
		jsonArr[i] = xsddJsonNew;
	}
	//console.log('jsonArr:', jsonArr);
	var res = JSON.stringify( jsonArr ); //console.log(res);

	$.ajax({
		url : "crexsdd",
		type : "POST",
		async : false,
		dataType : "json",
		data : {
			"parentJsonData" : parentData,
			"childJsonData" : res,
			"GOODS_ORDER_ID" : selRowId
		},
		complete : function(xhr) {
			eval("jsonResult = " + xhr.responseText);
			if (jsonResult.success === true) {
				showMsgPanelCloseWindow('保存成功','parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
			} else {
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

function doBack(){
	var selRowId = document.getElementById("selRowId").value;
	parent.layer.prompt({title: '退回原因', formType: 2
		,btn: ['退回','取消'] //按钮
	}, function(text, index){
		$.ajax({
			url: "back",
			type:"POST",
			dataType:"text",
			data:{"selRowId": selRowId, "RETURN_RSON":text},
			complete: function (xhr){
				eval("jsonResult = "+xhr.responseText);
				if(jsonResult.success===true){
					parent.layer.close(index);
					//parent.showMsgPanel(utf8Decode(jsonResult.messages));
					//$("#pageForm").submit();
					showMsgPanelCloseWindow(utf8Decode(jsonResult.messages),'parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
				}else{
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	});
}
function selPRD_QUALITY(rownum, i){
	var initSel = " SJZDID='PROD_MATER'";
	$('#initParams'+i).val(initSel);
	selCommon("System_6", false, "PRD_QUALITY"+ rownum + "", "SJXZ", "childFrm"+i, 
			"PRD_QUALITY"+ rownum + "",
			"SJXZ", "initParams"+i);
}

// 额外校验
function formCheckedEx(){
	
	return true;
}

// 固定的一些共通的方法 end
// 下面这个方法要自己修改
// 表格增加一行
function addRow(arrs, i) {
	var soDtlEmpty = $("#soDtlEmpty").html();
	if (soDtlEmpty != null || soDtlEmpty != '') {
		$("#soDtlEmpty").remove();
	}
	if (null == arrs) {
		arrs = [ '', '', '', '', '', '', '', '', '', '', '', '', '', '', '',
				'', '', '', '100', '', '', '', '', '', '', 
				'', '', '', '', '1', '', '', '', '', '' 
				, '', '', '', '', ''];
	}
	var techChecked = "";
	if ('1' == arrs[15]) {
		techChecked = "checked";
	}
	var lockChecked = '1' == arrs[30] ? "checked" : "";
	// 样式行
	var rownum = $("#jsontb"+i+" tr").length;
	// console.log(rownum)
	var classrow = rownum % 2;
	rownum = i +''+ _row_num;
	_row_num++;//alert("rownum："+rownum);
	$("#jsontb"+i+" tr:last-child")
			.after("<tr style='height:42px;' class='list_row" + classrow + "' ></tr>");
	$("#jsontb"+i+" tr:last-child")
			.append("<td nowrap align='center'><input type='checkbox' style='height:12px;valign:middle' id='eid" + rownum+ "' json='SALE_ORDER_DTL_ID' name='SALE_ORDER_DTL_ID' value='"+ arrs[0] + "' checked/></td>")
	var soDtl = "";
	soDtl += '<td nowrap class="LMM"><input type="text" id="GROUP_NO' + rownum + '" json="GROUP_NO" size="3" name="GROUP_NO" value="' + arrs[1] + '"  /></td>';
	soDtl += '<td nowrap><input type="text" id="PRD_NO' + rownum + '" json="PRD_NO" size="10" name="PRD_NO" value="' + arrs[2] + '" title="' + arrs[2] + '" label="产品编码"  readonly />'
						+ '<input type="hidden" id="PRD_ID' + rownum + '" json="PRD_ID" name="PRD_ID" value="' + arrs[3] + '"  />'
						+ '<input type="hidden" id="BRAND' + rownum + '" json="BRAND" name="BRAND" value="' + arrs[24] + '"  />'
						+ '<input type="hidden" id="ROW_NO' + rownum + '" json="ROW_NO" name="ROW_NO" value="' + rownum + '"  />'
						+ '<input type="hidden" id="FROM_BILL_DTL_ID' + rownum + '" json="FROM_BILL_DTL_ID" name="FROM_BILL_DTL_ID" value="' + arrs[36] + '"  />'
				+ '</td>';
	soDtl += '<td nowrap><input type="text" id="PRD_NAME' + rownum + '" json="PRD_NAME" size="15" name="PRD_NAME" value="' + arrs[4] + '" title="' + arrs[4] + '" disabled="disabled"/></td>';
	
	soDtl += '<td nowrap class="LMM"><input type="text" id="HOLE_SPEC' + rownum + '" json="HOLE_SPEC" size="15" name="HOLE_SPEC" value="' + arrs[6] + '" title="' + arrs[6] + '" /></td>'; /* 门洞尺寸 */
	soDtl += '<td nowrap><input type="text" id="PRD_SPEC' + rownum + '" json="PRD_SPEC" size="15" name="PRD_SPEC" value="' + arrs[23] + '" title="' + arrs[23] + '" /></td>'; /* 规格型号/门洞转换的尺寸 */

	soDtl += '<td nowrap class="LMM LYGCG">'/*
			+ '<select type="text" id="PRD_QUALITY' + rownum + '" json="PRD_QUALITY"  name="PRD_QUALITY" value="' + arrs[8] + '" title="' + arrs[8] + '" style="width:100px;" '
			+ 'label="材质" autocheck="true" mustinput="true" inputtype="string" ></select>'*/
			+'<input type="text" id="PRD_QUALITY' + rownum + '" json="PRD_QUALITY" size="10" name="PRD_QUALITY" value="' + arrs[8] + '" title="' + arrs[8] + '" '
			+'label="材质" autocheck="true" mustinput="true" inputtype="string" readonly style="padding-right: 20px;" />'
			+'<img name="imgTab" align="absmiddle" class="magnifier_mx quote" src="' + ctxPath + '/styles/newTheme/images/plus/select.gif" onClick=\'selPRD_QUALITY(' + rownum + ','+i+')\'/>'
			+'</td>';
	soDtl += '<td nowrap class="LMM LYGCG"><select class="edit"  id="PRD_COLOR' + rownum + '" json="PRD_COLOR"  name="PRD_COLOR" value="' + arrs[9] + '" title="' + arrs[9] + '" style="width:90px;" '
			+ 'label="颜色" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select class="edit" id="PRD_TOWARD' + rownum + '" json="PRD_TOWARD" name="PRD_TOWARD" value="' + arrs[10] + '" title="' + arrs[10] + '" style="width:60px;" '
			+ 'label="推向" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select class="edit" id="PRD_GLASS' + rownum + '" json="PRD_GLASS"  name="PRD_GLASS" value="' + arrs[11] + '" title="' + arrs[11] + '" style="width:90px;" '
			+ 'label="玻璃" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select class="edit" id="PRD_OTHER' + rownum + '" json="PRD_OTHER"  name="PRD_OTHER" value="' + arrs[12] + '" title="' + arrs[12] + '" style="width:90px;" '
			+ 'label="其他" autocheck="true" mustinput="true" inputtype="string" ></select></td>';
	soDtl += '<td nowrap class="LMM"><select class="edit" id="PRD_SERIES' + rownum + '" json="PRD_SERIES" name="PRD_SERIES" value="' + arrs[13] + '" title="' + arrs[13] + '" style="width:70px;" '
			+ 'label="系列" autocheck="true" mustinput="true" inputtype="string" ></select></td>';

	soDtl += '<td nowrap class=" LYGCG"><input type="text" id="PROJECTED_AREA' + rownum + '" json="PROJECTED_AREA" size="10" name="PROJECTED_AREA" value="' + arrs[26] + '" title="' + arrs[26] + '" /></td>'; /* 投影面积 */
	soDtl += '<td nowrap class=" LYGCG"><input type="text" id="EXPAND_AREA' + rownum + '" json="EXPAND_AREA" size="10" name="EXPAND_AREA" value="' + arrs[27] + '" title="' + arrs[27] + '" /></td>'; /* 展开面积 */
	soDtl += '<td nowrap class="LMM"><input type="checkbox" class="" id="IS_NO_LOCK_FLAG' + rownum + '" json="IS_NO_LOCK_FLAG" name="IS_NO_LOCK_FLAG" value="' + arrs[30] + '" ' + lockChecked + ' /></td>';

	soDtl += '<td nowrap><div style="float:left"><input type="hidden" id="hid_attPath' + rownum + '"  json="attPath" name="attPath" value="' + arrs[14] + '" /><button type="button" class="layui-dtlbtn " id="attPath' + rownum + '" lay-data="{fileid:\'/a/\'}" title="上传附件"><img src="'+ctxPath+'/styles/'+theme+'/images/icon/upload.png" ></button></div><div id="view_attPath' + rownum + '"></div></td>';
	soDtl += '<td nowrap><select class="edit" id="PROESS_TYPE' + rownum + '" json="PROESS_TYPE"  name="PROESS_TYPE" value="' + arrs[37] + '" label="处理类型" autocheck="true" mustinput="true" inputtype="string" style="width: 95%"></select></td>';

	soDtl += '<td nowrap><input type="checkbox" id="IS_NO_REBATE_FLAG' + rownum + '" json="IS_NO_REBATE_FLAG" name="IS_NO_REBATE_FLAG" value="' + arrs[15] + '" ' + techChecked + ' /></td>';
	soDtl += '<td nowrap><input type="text" class="decimal" id="IS_BACKUP_FLAG' + rownum + '" json="IS_BACKUP_FLAG" size="3" name="IS_BACKUP_FLAG" value="' + arrs[16] + '" disabled="disabled"/></td> <!-- //可用库存 -->';
	soDtl += '<td nowrap><input type="text" class="decimal" id="ORDER_NUM' + rownum + '" json="ORDER_NUM" size="5" name="ORDER_NUM" value="' + arrs[20] + '"  /></td>'; // 数量
	soDtl += '<td nowrap><input type="text" id="STD_UNIT' + rownum + '" json="STD_UNIT" size="2" name="STD_UNIT" value="' + arrs[5] + '" title="' + arrs[5] + '" disabled="disabled"/></td> <!-- //单位 -->';
	soDtl += '<td nowrap><input type="text" class="decimal" id="PRICE' + rownum + '" json="PRICE" size="5" name="PRICE" value="' + arrs[17] + '"  /></td>';
	soDtl += '<td nowrap><input type="text" class="decimal" id="DECT_RATE' + rownum + '" json="DECT_RATE" size="3" name="DECT_RATE" value="' + arrs[18]	+ '"  /></td>'; // 折扣率
	soDtl += '<td nowrap><input type="text" class="decimal" id="REBATE_AMOUNT' + rownum + '" json="REBATE_AMOUNT" size="5" name="REBATE_AMOUNT" value="' + arrs[25] + '"  /></td>'; // 返利
	soDtl += '<td nowrap><input type="text" class="decimal" id="DECT_PRICE' + rownum + '" json="DECT_PRICE" size="5" name="DECT_PRICE" value="' + arrs[19] + '"  /></td>'; // 最终报价
	soDtl += '<td nowrap><input type="text" class="decimal" id="ORDER_AMOUNT' + rownum + '" json="ORDER_AMOUNT" size="6" name="ORDER_AMOUNT" value="' + arrs[21] + '"  /></td>'; // 总金额
	soDtl += '<td nowrap><textarea class="edit" id="REMARK' + rownum + '" json="REMARK" name="REMARK" inputtype="string" title="' + arrs[22] + '" >' + arrs[22].replaceAll('；', '&#13;') + '</textarea></td>';
	soDtl += '</tr>';
	$("#jsontb"+i+" tr:last-child").append(soDtl);

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
	SelDictShow("PRD_GLASS" + rownum, "BS_194", arrs[11], "");
	SelDictShow("PRD_OTHER" + rownum, "BS_195", arrs[12], "");
	SelDictShow("PRD_SERIES" + rownum, "BS_206", arrs[13], "");
	SelDictShow("PROESS_TYPE" + rownum, "BS_191", arrs[37], "");
	/*uploadFile('attPath' + rownum, "SAMPLE_DIR", true, false, true, "");*/

	var lid = $("#ztid").val();//console.log('lid=',lid);
	// 根据订单组织控制明细字段列的显示/隐藏
	changeDtlCol(lid);
	// 根据是否可查看价格权限控制明细字段价格金额等列的显示/隐藏
	changeDtlPrice();
	
	$("#jsontb"+i+" tr:last-child :input").not('.edit').attr('disabled', 'disabled');
	// form校验设置
	trCheckInit($("#jsontb"+i+" tr:last-child input"));
	trCheckInit($("#jsontb"+i+" tr:last-child select"));
	$("#jsontb"+i+" tr:last-child select.edit").each(function() {
		//$(this).css("width", "80px");
		$(this).css("text-align", "center");
		$(this).css("text-align-last", "center");
	})
	
	//id,folder,isdown,ismulupl是否可传多个,autoCommit是否自动提交,isMain是否主表,isSingle是否单个覆盖
	uploadFile("attPath"+ rownum,'',true,false,true,false,true);
	
	// 修改处理方式
	$('#PROESS_TYPE' + rownum).change(function(){
		var proessType = $(this).val();// 明细处理类型
		changeProessType(proessType, rownum, i);
		// 计算订单总金额
		countTotalRebate(i);
		countTotalAmount(i);
	});
}

// 计算订单总金额
function countTotalRebate(i){
	// 计算报价折后总金额
	var sum = 0;
	var ipt_rebate = $('#jsontb'+i+' input[json=REBATE_AMOUNT]');
	$.each(ipt_rebate, function(idx, item){
		var rebate = $(item).val();
		sum += Number($.trim(rebate));
	});
	
	$('#TOTAL_REBATE'+i).val(sum);
}

// 计算订单总金额
function countTotalAmount(i){
	// 最终报价总金额
	var sum = 0;
	var ipt_amount = $('#jsontb'+i+' input[json=ORDER_AMOUNT]');
	$.each(ipt_amount, function(idx, item){
		var amount = $(item).val();
		sum += Number($.trim(amount));
	});
	$('#TOTAL_AMOUNT'+i).val(sum);
}

// 
function changeProessType(proessType, rownum, i){
	var index = 0;
	for (var j=0;j<$('.xsddExt').length;j++) {
		var ptype = $('#PROESS_TYPE' + j).val();
		if (ptype.indexOf('现货') > -1 && proessType.indexOf('现货') > -1) {
			index = j;
			break;
		} else if (ptype == proessType) {
			index = j;
			break;
		}
	}
	if(index == i){
		return ;
	}
	var arrs = [
		$('#eid' + rownum).val(),//0
		$('#GROUP_NO' + rownum).val(),//1
		$('#PRD_NO' + rownum).val(),//2
		$('#PRD_ID' + rownum).val(),//3
		$('#PRD_NAME' + rownum).val(),//4
		$('#STD_UNIT' + rownum).val(),//5
		$('#HOLE_SPEC' + rownum).val(),//6
		$('#PRD_SIZE' + rownum).val(),//7
		$('#PRD_QUALITY' + rownum).val(),//8
		$('#PRD_COLOR' + rownum).val(),//9
		$('#PRD_TOWARD' + rownum).val(),//10
		$('#PRD_GLASS' + rownum).val(),//11
		$('#PRD_OTHER' + rownum).val(),//12
		$('#PRD_SERIES' + rownum).val(),//13
		$('#hid_attPath' + rownum).val(),//14
		$('#IS_NO_REBATE_FLAG' + rownum).val(),//15
		$('#IS_BACKUP_FLAG' + rownum).val(),//16
		$('#PRICE' + rownum).val(),//17
		$('#DECT_RATE' + rownum).val(),//18
		$('#DECT_PRICE' + rownum).val(),//19
		$('#ORDER_NUM' + rownum).val(),//20
		$('#ORDER_AMOUNT' + rownum).val(),//21
		$('#REMARK' + rownum).val(),//22
		$('#PRD_SPEC' + rownum).val(),//23
		$('#BRAND' + rownum).val(),//24
		$('#REBATE_AMOUNT' + rownum).val(),//25
		$('#PROJECTED_AREA' + rownum).val(),//26
		$('#EXPAND_AREA' + rownum).val(),//27
		$('#ROW_NO' + rownum).val(),//28
		$('#PRD_PLACE' + rownum).val(),//29
		$('#IS_NO_LOCK_FLAG' + rownum).val(),//30
		'' ,//31
		'',//32
		'',//33
		'',//34
		'',//35
		$('#FROM_BILL_DTL_ID' + rownum).val(), //36
		proessType, //37
		''
	];//console.log('arrs', arrs);
	addRow(arrs, index);
	
	$('#eid' + rownum).parent().parent().remove();
	
	// 计算订单总金额
	countTotalRebate(j);
	countTotalAmount(j);
}

function changeinput(){
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