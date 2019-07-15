/**
 * @prjName:喜临门营销平台
 * @fileName:Saleorder_Assign
 */
$(function() {
	InitFormValidator(0);

	$("#tmpsave,#save").click(function() {
		var status = $(this).attr('status');
		allSave(status);
	});

	var designerid= $('#DESIGNER').val();
	//$("input[name='yhid'][value="+xtyhid+"]").attr('checked','true');
	setSelOperateEx("input[name='yhid'][value="+designerid+"]");
});

function getSelRowId(){
	return document.getElementById("SALE_ORDER_ID").value;
}

function getSelDtlId(){
	return document.getElementById("DESIGNER").value;
}

//主子表保存
function allSave(status){
	var selRowId = getSelRowId();
	var selDtlId = getSelDtlId();
	if('F' != status && selDtlId == '') {
		parent.showErrorMsg(utf8Decode("请至少选中一位设计师！"));
		return;
	}
	$('#auditStatus').val(status);	
	//获取json数据
	var parentData = siglePackJsonData("mainDiv");
	
	$.ajax({
		url: "assign",
		type:"POST",
		dataType:"text",
		data:{"parentJsonData":parentData, "DESIGNER_ID":$('#DESIGNER_ID').val(), "SALE_ORDER_ID":selRowId },
		complete: function (xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				if('Z' == status){
					showMsgPanelCloseWindow('保存成功');
				} else {
					showMsgPanelCloseWindow(utf8Decode(jsonResult.messages),'parent.window.frames["topcontent"].document.getElementById("pageForm").submit()');
				}
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

function setSelOperateEx(obj){
	$(obj).attr('checked','true');
	var val = $(obj).val();
	$('#DESIGNER').val(val);
	$('#DESIGNER_NAME').val($(obj).attr('designerName'));
	
	// 添加背景颜色显示
	$("#jsontb tr:gt(0)").each(function(){
		$(this).removeClass('mover');
		if($(this).find("input[name='yhid']").prop('checked')){
			$(this).addClass('mover');
		}
	})
}