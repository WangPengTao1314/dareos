
/**
 *@module 销售管理
 *@func 编辑js
 *@version 1.1
 *@author 王朋涛
 */
$(function(){
    
	if(!($("#project_no").val() == null && $("#project_no") == "")){
        $("#project_no").attr("readonly","readonly");
        $("#project_no").css("background-color","#cccccc");
     }
	//初始化校验
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
	
	if($("#state").val()=="已确认"||$("#state").val()=="已完成"){
		btnDisable(["confirm_delivery"]);
	}
	
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	$("#save").click(	function() {
		allSave();
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

	//var childData = multiPackJsonData("jsontbS");
	$.ajax({
		url : "confirm",
		type : "POST",
		async : false,
		dataType : "json",
		data : {
			"parentJsonData" : parentData
			//"childJsonData" : childData,
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
