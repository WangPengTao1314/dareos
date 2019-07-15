/**
 * @module 发货管理
 * @func js
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
		var status ="已通过";
		changeState("check", "send_order_id", "toList","mainForm",status);
	});
	$("#reject").click(function() {
		var status ="已拒绝";
		changeState("check", "send_order_id", "toList","mainForm",status);
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
		var jsonData = siglePackJsonData();
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
					showMsgPanelCloseWindow('保存成功','parent.window.frames["topcontent"].document.getElementById("queryForm").submit()');
				} else {
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	}

});

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
	var new_img = document.getElementById('imgsz')
	if(newmytable.style.display == 'none'){
		newmytable.style.display = '';
		newinput.value="收起"
			new_img.style.transform="rotate(0deg)"
	    
	}else{
		newmytable.style.display='none';
		newinput.value = "展开";
		/*transform:rotate(180deg);*/
		new_img.style.transform="rotate(180deg)"
		
	}
        
 }

