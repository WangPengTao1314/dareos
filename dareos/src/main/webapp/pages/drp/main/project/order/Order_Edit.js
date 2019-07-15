
/**
 *@module 项目指令
 *@func 编辑js
 *@version 1.1
 *@author 王朋涛
 */
$(function(){
	//初始化校验
	//if(!($("#project_directive_id").val()=="")){
	//	 $("#imgID").attr("hidden","true"); 
	//	 $("#imgID2").attr("hidden","true"); 
	// }
	
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	mtbSaveListener("edit","project_directive_id","toList","mainForm");
	
	/*
	 * 数据非空校验时的属性 :
	 * name="" 
	 * label="" 
	 * autocheck=""   
	 * mustinput="" 
	 * inputtype=""  
	 */
	
	
	
	
});

/*function sendEntry(){
	$.ajax({
		url: url,
		type:"POST",
		dataType:"text",
		data:{"project_directive_ids":ids},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				//parent.showMsgPanel("操作成功");
				//checkBox.parent().parent().remove();
				//设置删除操作。有删除时返回后需要刷新页面。否则直接跳转上一页。
				//$("#delFlag").val("true");
				$("#mainForm").attr('action',goUrl);
				mainForm.submit();
				
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
	
	
}*/