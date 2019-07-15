/**
 * @module 系统管理
 * @func 供应商信息
 */
$(function () {
	//页面初始化
	listPageInit("toList");
	//新增和修改按钮初始化
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	//mtbDelListener("jgxx.shtml?action=delete", "JGXXID");
	var selRowId = $("#selRowId").val();
    if(null == selRowId || "" == selRowId){
			  btnDisable(["start","modify","delete","stop"]);
	 	 	  return;
	} 
	$("#delete").click(function(){
		  if(null == selRowId || "" == selRowId){
			  showWarnMsg("请选择一条记录");
	 	 	  return;
	 	 } 
	 	 showConfirm("将删除该供应商信息,您确认删除吗?","listDelConfirm();");
	 });
	
	// 停用
	$("#stop").click(function(){
	   //获取当前选中的记录
	   if(null == selRowId || "" == selRowId){
		   showWarnMsg("请选择一条记录");
	      return;
	   }
	   showConfirm("将停用该供应商信息，是否继续?","listStopConfirm();");
	});
	
	// 启用
	$("#start").click(function(){
	   //获取当前选中的记录
	   if(null == selRowId || "" == selRowId){
		   showWarnMsg("请选择一条记录");
	      return;
	   }
	   showConfirm("将启用该供应商信息，是否继续?","listStartConfirm();");

	});
	
});

//记录按钮根据状态控制
 function setSelOperateEx(obj){
	//可根据id或name或所在列数获得值，具体方法开发人员自行选择。
	var selRowId = $("#selRowId").val();
	//var state =  $.trim($(obj).find("td[json='STATE']").text());
	var state = $.trim(document.getElementById("state" + selRowId).value);
	//按钮状态重置
	btnReset();
	if(state == "启用"){
		btnDisable(["start","modify","delete"]);
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
	 	url: "delete?PRVD_ID="+selRowId,
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
		url: "changeStateStop?PRVD_ID="+utf8(selRowId),
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanel("状态修改成功",'$("#pageForm").submit();');
				
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
	 url: "changeStateStart?PRVD_ID="+utf8(selRowId),
	 type:"POST",
 	 dataType:"text",
	 complete: function(xhr){
		eval("jsonResult = "+xhr.responseText);
		if(jsonResult.success===true){
			showMsgPanel("状态修改成功",'$("#pageForm").submit();');
            
		}else{
			showWarnMsg(utf8Decode(jsonResult.messages));
		}
	  }
    });
}

function setProv(){	
	//选择省的场合设定查询条件
	var tempCondition="";			
	var provValue = $("#PROV").val();
	
	if('' != provValue){
		tempCondition=tempCondition+"PROV= '"+provValue+"'";
	}
	
	$("#zoneConditionCity").val(tempCondition);
	
}