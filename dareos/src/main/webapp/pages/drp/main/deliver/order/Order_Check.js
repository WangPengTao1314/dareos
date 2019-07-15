/**
 * @module 发货管理
 * @func 发货审核js
 * @version 1.0
 * @author 王朋涛
 */
$(function() {

	// 初始化校验
	InitFormValidator(0);
	// 添加浮动按钮层的监听
	// addFloatDivListener();
	// 保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	// mtbSaveListener("edit","send_order_id","toList","mainForm");
	$("#pass").click(function() {
		var send_id=$("#SEND_ORDER_ID").val();
		checkOrder(send_id);
	});
	$("#reject").click(function() {
		var status ="已拒绝";
		showConfirm("您确认要退回吗?","changeState('check', 'SEND_ORDER_ID', 'toList','mainForm','"+status+"',null);");
	});
	
	//

});
function changeState(actionUrl, pkId, successUrl, formId,status,mssage) {
	if (formId == null || formId == '') {
		formId = "mainForm";
	}
	if(!formChecked(formId)){
		return;
	}
	var selId = parent.document.getElementById("selRowId").value;
	var jsonData = siglePackJsonData();
	var index = parent.layer.load(1);
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
				parent.layer.close(index);
				parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭弹出框
				parent.window.frames["topcontent"].document.getElementById("queryForm").submit();
//				showMsgPanelCloseWindow(utf8Decode(jsonResult.messages),'parent.window.frames["topcontent"].document.getElementById("queryForm").submit();');
				//showMsgPanelCloseWindow(mssage,'');
			} else {
				parent.layer.close(index);
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}
/**
 * 审核校验
 * @param 
 * @returns
 */
function checkOrder(send_id){
	var index = parent.layer.load(1);
	$.ajax({
		url :'checkOrder',
		type : "POST",
		dataType : "text",
		data : {
			"SEND_ORDER_ID" : send_id
		},
		complete : function(xhr) {
			eval("jsonResult = " + xhr.responseText);
			if (jsonResult.success === true) {
				parent.layer.close(index);
				if("金额不足"==jsonResult.messages){
					showConfirm("账户实际金额不足，确认强制发货吗?","changeState('check', 'SEND_ORDER_ID', 'toList','mainForm','已通过',jsonResult.messages);");	
				}else{
					changeState('check', 'SEND_ORDER_ID', 'toList','mainForm','已通过',jsonResult.messages);
				}
			} else {
				parent.layer.close(index);
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
		
	});
	
}

function changeinput(e){
	var newinput = document.getElementById('ipt')
	var newmytable =document.getElementById('myTable')
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
function changeinput2(e){
	var newinput = document.getElementById('ipt')
	var newmytable =document.getElementById('jsontb')
	if(newmytable.style.display == 'none'){
		newmytable.style.display = '';
		newinput.value="收起"
		(imgsz).style.transform="rotate(0deg)"
	    
	}else{
		newmytable.style.display='none';
		newinput.value = "展开";
		/*transform:rotate(180deg);*/
		(imgsz).style.transform="rotate(180deg)"
		
	}
        
 }
