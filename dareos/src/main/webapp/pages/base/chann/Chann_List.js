
/**
 * @module 系统管理
 * @func 机构信息
 * @version 1.1
 * @author 刘曰刚
 */
$(function () {
	//页面初始化
	listPageInit("toList");
	//新增和修改按钮初始化
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	
    var selRowId = $("#selRowId").val();
    if(null == selRowId || "" == selRowId){
			  btnDisable(["start","modify","delete","stop","deliverAddr","channChrg","ledgerChrg","expdata"]);
	 	 	  return;
	} 
	$("#delete").click(function(){
		  if(null == selRowId || "" == selRowId){
			  parent.showErrorMsg("请选择一条记录");
	 	 	  return;
	 	 } 
	 	 parent.showConfirm("将删除该渠道及其下级渠道,您确认删除吗?","topcontent.listDelConfirm();");
	 });
	$("#expdata").click(function(){
		 $("#queryForm").attr("action","toExpData");
		 $("#queryForm").submit();
	 });
	// 停用
	$("#stop").click(function(){
	   //获取当前选中的记录
	   if(null == selRowId || "" == selRowId){
	      parent.showErrorMsg("请选择一条记录");
	      return;
	   }
	   parent.showConfirm("将停用该渠道及其下级渠道，是否继续?","topcontent.listStopConfirm();");
	});
	
	$("#deliverAddr").click(function(){
		//获取当前选中的记录
	   if(null == selRowId || "" == selRowId){
		   parent.showErrorMsg("请选择一条记录");
	      return;
	   }
	   parent.window.gotoBottomPage("childList");
	})
	
	// 启用
	$("#start").click(function(){
	   //获取当前选中的记录
	   if(null == selRowId || "" == selRowId){
	      parent.showErrorMsg("请选择一条记录");
	      return;
	   }
	   parent.showConfirm("将启用该渠道及其下级渠道，是否继续?","topcontent.listStartConfirm();");

	});
	/**
	 * 渠道分管
	 */
	$("#channChrg").click(function(){
		//获取当前选中的记录
		   if(null == selRowId || "" == selRowId){
		      showErrorMsg("请选择一条记录");
		      return;
		   }
		parent.window.gotoBottomPage("chargList");
	})
	
	$("#ledgerChrg").click(function(){
		//获取当前选中的记录
		   if(null == selRowId || "" == selRowId){
		      parent.showErrorMsg("请选择一条记录");
		      return;
		   }
		parent.window.gotoBottomPage("ledgerChrgList");
	})
});

 function setSelOperateEx(obj){
	//可根据id或name或所在列数获得值，具体方法开发人员自行选择。
	 var selRowId = $("#selRowId").val();
		var state =  document.getElementById("state" + selRowId).value;
	//按钮状态重置
	btnReset();
    if(state == "启用"){
		btnDisable(["start","delete"]);
	}else if(state == "停用"){
		btnDisable(["stop","delete"]);
	}else if (state == "制定"){
		btnDisable(["stop"]);
	}
 }
 
 
function orpenWindow(){

     var CHANN_NO = $("#CHANN_NO").val();
     var CHANN_NAME = $("#CHANN_NAME").val();
     var CHANN_TYPE = $("#CHANN_TYPE").val();
     var CHAA_LVL   = $("#CHAA_LVL").val();
     var CHANN_NO_P = $("#CHANN_NO_P").val();
     var CHANN_NAME_P = $("#CHANN_NAME_P").val();
     var AREA_NO    = $("#AREA_NO").val();
     var AREA_NAME  = $("#AREA_NAME").val();
     var PROV       = $("#PROV").val();
     var CITY       = $("#CITY").val();
     var BEGIN_CRE_TIME = $("#BEGIN_CRE_TIME").val();
     var END_CRE_TIME   = $("#END_CRE_TIME").val();
     var STATE      = $("#STATE").val();
     var AREA_NAME_P = $("#AREA_NAME_P").val();
     myShowModalDialog("toBatchFrame?CHANN_NO="+CHANN_NO+"&CHANN_NAME="+utf8(CHANN_NAME)+"&CHANN_TYPE="+utf8(CHANN_TYPE)+"&CHAA_LVL="+utf8(CHAA_LVL)+"&CHANN_NO_P="+CHANN_NO_P+"&CHANN_NAME_P="+utf8(CHANN_NAME_P)+"&AREA_NO="+AREA_NO+"&AREA_NAME="+utf8(AREA_NAME)+"&PROV="+utf8(PROV)+"&CITY="+utf8(CITY)+"&BEGIN_CRE_TIME="+BEGIN_CRE_TIME+"&END_CRE_TIME="+END_CRE_TIME+"&STATE="+utf8(STATE)+"&AREA_NAME_P="+utf8(AREA_NAME_P),false,900,500);
	 //var url="saleplan.shtml?action=toBatch";
	 //$("#topcontent").attr("src",url);
}
 
function listDelConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
	 $.ajax({
	 	url: "delete?CHANN_ID="+selRowId,
		type:"POST",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("删除成功");
				window.location="toList?CHANN_ID="+selRowId;
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

//停用按钮的url
function listStopConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
	 $.ajax({
		url: "changeStateTy?CHANN_ID="+utf8(selRowId),
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("状态修改成功");
				$("#pageForm").submit();
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
 	});
}
//启用按钮的
function listStartConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
    $.ajax({
	 url: "changeStateQy?CHANN_ID="+utf8(selRowId),
	 type:"POST",
 	 dataType:"text",
	 complete: function(xhr){
		eval("jsonResult = "+xhr.responseText);
		if(jsonResult.success===true){
			parent.showMsgPanel("状态修改成功");
            $("#pageForm").submit();
		}else{
			parent.showErrorMsg(utf8Decode(jsonResult.messages));
		}
	  }
    });
}
