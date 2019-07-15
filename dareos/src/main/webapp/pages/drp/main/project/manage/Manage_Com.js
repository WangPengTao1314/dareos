 
/**
 * @module 系统管理
 * @func 货品套
 * @version 1.1
 * @author 刘曰刚
 */

 //固定的一些共通的方法 begin
$(function(){
     
    //初始化校验
	InitFormValidator(0);
	//主从及主从从列表页面通用加载方法
	 listPageInit("toComEdit");
	//添加浮动按钮层的监听
	addFloatDivListener();
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	 mtbSaveListener("comEdit","project_id","toList","mainForm"); 
	 
	 $("#allChecked").click(function(){
			var flag = document.getElementById("allChecked").checked;
			if(flag){
				$("#view_jsontF :checkbox").attr("checked","true");
			}else{
				$("#view_jsontF :checkbox").removeAttr("checked");
			}
		});
	 $("#delete").click(function(){
		 //查找当前是否有选中的记录
		 var checkBox = $("#view_jsontF tr:gt(0) input:checked");
		 if(checkBox.length<1){
			 parent.showErrorMsg("请至少选择一条记录");
			 return false;
		 }
		 mtbDelConfirmS("deleteOrder","toComEdit");
	 });
	 
	 $("#send").click(function(){
		 //查找当前是否有选中的记录
		 var checkBox = $("#view_jsontF tr:gt(0) input:checked");
		 if(checkBox.length<1){
			 parent.showErrorMsg("请至少选择一条记录");
			 return false;
		 }
		 mtbDelConfirmS("sendOrder","toComEdit");
	 });
	
});


//删除操作
function mtbDelConfirmS(url,goUrl){
   //查找当前是否有选中的记录
	var checkBox = $("#view_jsontF tr:gt(0) input:checked");
	//获取所有选中的记录
	var ids = "";
	checkBox.each(function(){
		if(url=="sendOrder"){
			ids = ids+"'"+$.trim($("#order_id" + $(this).val()).html())+"',";
		}else{
			ids = ids+"'"+$(this).val()+"',";
		}
		
	});
	ids = ids.substr(0,ids.length-1);
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
}


 