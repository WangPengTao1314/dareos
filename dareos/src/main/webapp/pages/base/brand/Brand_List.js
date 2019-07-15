/**
 *@module 基础数据
 *@func品牌信息
 *@version 1.1
 *@author 郭利伟
 */
$(function(){
	//页面初始化
	listPageInit("toList");
	//新增和修改按钮初始化
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	//mtbDelListener("delete","BRAND_ID");
	var selRowId = $("#selRowId").val();
	if(null == selRowId || "" == selRowId){
	   btnDisable(["start","modify","delete","stop"]);
	   
	   return;
    }
	$("#delete").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		  if(null == selRowId || "" == selRowId){
			  showErrorMsg("请至少选择一条记录");
	 	 	  return;
	 	 } 
	 	 showConfirm("将删除该品牌信息,您确认删除吗?","listDelConfirm();");
	 });
	// 停用
	$("#stop").click(function(){
	   //获取当前选中的记录
       var selRowId = parent.document.getElementById("selRowId").value;
	   if(null == selRowId || "" == selRowId){
	      showErrorMsg("请至少选择一条记录");
	      return;
	   }
	   showConfirm("将停用该品牌信息，是否继续?","listStopConfirm();");
	});
	
	// 启用
	$("#start").click(function(){
	   //获取当前选中的记录
	   var selRowId = parent.document.getElementById("selRowId").value;
	   if(null == selRowId || "" == selRowId){
		   showWarnMsg("请至少选择一条记录");
	      return;
	   }
	   showConfirm("将启用该品牌信息，是否继续?","listStartConfirm();");
	});
});	


//记录按钮根据状态控制
 function setSelOperateEx(obj){
	var selRowId = $("#selRowId").val();
	//var state1 =  $.trim($(obj).find("td[json='STATE']").text());
	var state = $.trim(document.getElementById("state" + selRowId).value);
	//按钮状态重置
	btnReset();
	//$("#qxBtnTb input[name='brandList']").removeAttr("disabled");
	if(state == "启用"){
		btnDisable(["start","delete"]);
		
	}else if(state == "停用"){
		btnDisable(["stop","delete"]);
		
	}else if (state == "制定"){
		btnDisable(["stop"]);
		
	}
 }
 //删除记录
function listDelConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
	 $.ajax({
	 	url: "delete?BRAND_ID="+selRowId,
		type:"POST",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanel("删除成功",'$("#pageForm").submit();');
				
			}else{
				showWarnMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}
//停用记录
function listStopConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
	 $.ajax({
		url: "changeStateStop?BRAND_ID="+utf8(selRowId),
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanel("状态修改成功",'$("pageForm").submit();');
				//$("#pageForm").submit();
			}else{
				showWarnMsg(utf8Decode(jsonResult.messages));
			}
		}
 	});
}
//启用记录
function listStartConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
    $.ajax({
	 url: "changeStateStart?BRAND_ID="+utf8(selRowId),
	 type:"POST",
 	 dataType:"text",
	 complete: function(xhr){
		eval("jsonResult = "+xhr.responseText);
		if(jsonResult.success===true){
			showMsgPanel("状态修改成功",'$("pageForm").submit();');
            //$("#pageForm").submit();
		}else{
			showWarnMsg(utf8Decode(jsonResult.messages));
		}
	  }
    });
}
