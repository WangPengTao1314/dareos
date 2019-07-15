/**
 * @prjName:喜临门营销平台
 * @fileName:Saleorder_Apply
 */
$(function() {
	InitFormValidator(0);
	var APPLYSTATE = $('#APPLYSTATE').val();
	if ('提交' == APPLYSTATE) {
		$('#CHAN_REMARK').attr('readonly', 'readonly');
		
	}

	$("#save").click(function() {
		//
		allSave('apply');
	});
	$("#commit").click(function() {
		//
		allSave('applytocommit');
	});

});

function getSelRowId(){
	return parent.document.getElementById("selRowId").value;
}

//主子表保存
function allSave(func){
	var selRowId = getSelRowId();
	
	//获取json数据
	var parentData = siglePackJsonData("mainForm");
	
	$.ajax({
		url: func,
		type:"POST",
		dataType:"text",
		data:{"parentJsonData":parentData, "CHANGE_APPLY_ID":$('#CHANGE_APPLY_ID').val(), "SALE_ORDER_ID":$('#SALE_ORDER_ID').val() },
		complete: function (xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanelCloseWindow(utf8Decode(jsonResult.messages));
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}