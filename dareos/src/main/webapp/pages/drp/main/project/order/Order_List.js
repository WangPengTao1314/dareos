/**
  *@module 项目指令
  *@fuc  项目指令一览js
  *@version 1.0
  *@author 王朋涛
*/
	
$(function(){
	 //主从及主从从列表页面通用加载方法
	listPageInit("toList");
 
	//新增和修改按钮初始化
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	mtbDelListener2("delete","project_directive_id");
	//release:指令下达
	$("#release").click(function(){
		orderOperation("release","project_directive_id");
	});
	//accept:指令接受
	$("#accept").click(function(){
		orderOperation("accept","project_directive_id");
	});
	//
	var selRowId = $("#selRowId").val();
   if(null == selRowId || "" == selRowId){
	  //按钮失效设置
      btnDisable(["modify","delete","release","accept"]);
      return;
   }
   
   $("#maintain").click(function(){
		 var selRowId = $("#selRowId").val();
	 	 if(null == selRowId || "" == selRowId){
	 		 parent.showErrorMsg("请选择一条记录");
	 	 }else{
	 	 	setCommonPageInfo(true);
			parent.window.gotoBottomPage("toMxEdit");
	 	 }
	});  
	 
	
	 
});



//主表删除
function orderOperation(actionUrl,pkId){
	 var selRowId = $("#selRowId").val();
	  if(null == selRowId || "" == selRowId){
		  parent.showErrorMsg("请选择一条记录");
 	 	  return;
 	 } 
 	 var goUrl = $("#pageForm").attr("action"); 
 	parent.showConfirm("您确认要对改指令操作吗?","orderDelConfirm('"+actionUrl+"','"+pkId+"','"+selRowId+"','"+goUrl+"');");
}


function    getATT_ID(obj){
	document.getElementById("att_id").value = $(obj).val();
}


//主表删除监听
function mtbDelListener2(actionUrl,pkId){
	
	$("#delete").click(function(){
	 	 mtbDelete2(actionUrl,pkId);
	 });
}
//主表删除
function mtbDelete2(actionUrl,pkId){
	 var selRowId = $("#att_id").val();
	  if(null == selRowId || "" == selRowId){
		  parent.showErrorMsg("请选择一条记录");
 	 	  return;
 	 } 
	  pkId="att_id";
 	 var goUrl = $("#pageForm").attr("action"); 
 	 parent.showConfirm("您确认要删除吗?","mtbDelConfirm('"+actionUrl+"','"+pkId+"','"+selRowId+"','"+goUrl+"');");
}


function setSelOperateEx(obj){
	//可根据id或name或所在列数获得值，具体方法开发人员自行选择。
	var selRowId = $("#selRowId").val();
	var state =  $.trim($("#state" + selRowId).html());
	//按钮状态重置
	btnReset();
	if(state == "待下发"){
		btnDisable(["accept"]);
	}else if(state == "待接收"){
		btnDisable(["release","delete","modify"]);
	}else if (state == "已接受"){
		btnDisable(["accept","release","delete","modify"]);
	}
}

 
 
 