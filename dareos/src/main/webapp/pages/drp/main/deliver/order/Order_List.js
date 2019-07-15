/**
  *@module 发货管理
  *@fuc 发货管理列表js
  *@version 1.1
  *@author 王朋涛
*/
	
$(function(){
	 //主从及主从从列表页面通用加载方法
	listPageInit("toList");
    //
	//新增和修改按钮初始化
	mtbAddModiInit();
	whBtnHidden(["check_btn"]);
	shBtnHidden(["add_btn","modify_btn","delete_btn","submit_btn"]);
	//删除监听.参数1：删除action，参数2：删除主键Id
	mtbDelListener("delete","SEND_ORDER_ID");
	   var selRowId = $("#selRowId").val();
	   if(null == selRowId || "" == selRowId){
		  //按钮失效设置
	      btnDisable(["modify","delete","check","submit","flowTrack"]);
	      return;
	   }
	   
	   $("#check").click(function(){
			 var selRowId = $("#selRowId").val();
		 	 if(null == selRowId || "" == selRowId){
		 		 parent.showErrorMsg("请选择一条记录");
		 	 }else{
		 	 	setCommonPageInfo(true);
				parent.window.gotoBottomPage("toCheck");
		 	 }
		});
	   $("#submit").click(function(){
			 var selRowId = $("#selRowId").val();
			 var LEDGER_ID = $("#LEDGER_ID"+selRowId).val();
			 var FACTORY_NO = $("#FACTORY_NO"+selRowId).val();
		 	 if(null == selRowId || "" == selRowId){
		 		 parent.showErrorMsg("请选择一条记录");
		 	 }else{
		 		showConfirm("您确认要提交吗?","mtbSubmit('submit?LEDGER_ID="+LEDGER_ID+"&FACTORY_NO="+FACTORY_NO+"','SEND_ORDER_ID','"+selRowId+"');");
		 		
		 	 }
		});  
	   
	// 进度跟踪
		$("#flowTrack").click(function(){
			var selRowId = parent.document.getElementById("selRowId").value;
			if (selRowId == "") {
				parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
				return;
			}
			//var flowServiceId =$('#ORDER_TRACE_ID'+selRowId).val();
			myShowModalDialog(ctxPath+"/sys/flow/toList/"+selRowId);
		});
	 
});

function setSelOperateEx(obj){
	//可根据id或name或所在列数获得值，具体方法开发人员自行选择。
	var selRowId = $("#selRowId").val();
	var state =  $.trim($("#STATE" + selRowId).html());
	//按钮状态重置
	btnReset();
	if(state == "已通过"){
		btnDisable(["modify","delete","check","submit"]);
	}else if(state=="待审核"){
		btnDisable(["modify","delete","submit"]);
	}else if(state=="草稿"){
		//btnDisable(["submit"]);
	}else if(state=="已完成"){
		btnDisable(["modify","delete","check","submit"]);
		
	}
}

function mtbSubmit(actionUrl,pkId,selRowId,goUrl){
	closeWindow();
	 $.ajax({
	 	url: actionUrl+"&"+pkId+"="+selRowId,
		type:"POST",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanel("提交成功");
				$("#queryForm").attr("action","toList?module=wh");
				queryForm.submit();
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}


 
 