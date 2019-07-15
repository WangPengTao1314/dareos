/**
  *@module 销售管理
  *@fuc   
  *@version 1.1
  *@author 王朋涛
*/
	
$(function(){
	 //主从及主从从列表页面通用加载方法
	listPageInit("toList");
	
	//交期确认
	$("#confirm_delivery").click(function(){
		setCommonPageInfo(true);
		parent.window.gotoBottomPage("toConfirm");
	});
	//进度反馈
	$("#progress_feedback").click(function(){
		 var selRowId = $("#selRowId").val();
	 	 if(null == selRowId || "" == selRowId){
	 		 parent.showErrorMsg("请选择一条记录");
	 	 }else{
	 	 	setCommonPageInfo(true);
			parent.window.gotoBottomPage("toFeedback");
	 	 }
	});
	
	var selRowId = $("#selRowId").val();
   if(null == selRowId || "" == selRowId){
	  //按钮失效设置
      btnDisable(["confirm_delivery","progress_feedback"]);
      return;
   }
	   
	 
});

function setSelOperateEx(obj){
	//可根据id或name或所在列数获得值，具体方法开发人员自行选择。
	var selRowId = $("#selRowId").val();
	var state =  $.trim($("#state" + selRowId).html());
	//按钮状态重置
	btnReset();
	if(state == "已确认"){
		btnDisable(["confirm_delivery"]);
	}else if(state == "已完成"){
		btnDisable(["confirm_delivery","progress_feedback"]);
	}else if (state == "待确认"){
		btnDisable(["progress_feedback"]);
	}
}
 
 
 