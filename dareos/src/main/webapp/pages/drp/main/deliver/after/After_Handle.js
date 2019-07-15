/**
 *@module 售后管理
 *@func 问题反馈单处理
 *@version 1.1
 *@author 王朋涛
 */
$(function(){
    
	//初始化校验
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	mtbSaveListener("handle","PROBLEM_FEEDBACK_ID","toList","mainForm");
	//退回
	 $("#returnback").click(function(){
		 doBack();
	});  
	 
	
});


function doBack(){
	var selRowId  =$("#PROBLEM_FEEDBACK_ID").val();
	 var order_org =$("#ORDER_ORG").val();
	//var selRowId = document.getElementById("selRowId").value;
	 var jsonData = siglePackJsonData("jsontb");
	parent.layer.prompt({title: '退回原因', formType: 2
		,btn: ['退回','取消'] //按钮
	}, function(text, index){
		$.ajax({
			url: "handle",
			type:"POST",
			dataType:"text",
			data:{"selRowId": selRowId,
				 "RETURN_RSON":text,
				 "ORDER_ORG":order_org,
				 "STATE":"已退回",
				 "jsonData":jsonData
				 },
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




