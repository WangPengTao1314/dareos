/**
  *@module 售后管理
  *@fuc  问题反馈单列表js
  *@version 1.1
  *@author 王朋涛
*/
$(function(){
	 //主从及主从从列表页面通用加载方法
	listPageInit("toList");
 
	//新增和修改按钮初始化
	mtbAddModiInit();
	whBtnHidden(["handle_btn"]);
	//shBtnHidden(["add_btn","modify_btn","delete_btn","submit_btn"]);
	//删除监听.参数1：删除action，参数2：删除主键Id 
	mtbDelListener("delete","PROBLEM_FEEDBACK_ID");
	//submit
	var selRowId = $("#selRowId").val();
	   if(null == selRowId || "" == selRowId){
		  //按钮失效设置
	      btnDisable(["modify","delete","handle","submit","flowTrack"]);
	      return;
	   }
	   
	   
	   $("#handle").click(function(){
			 var selRowId = $("#selRowId").val();
		 	 if(null == selRowId || "" == selRowId){
		 		 parent.showErrorMsg("请选择一条记录");
		 	 }else{
		 	 	setCommonPageInfo(true);
				parent.window.gotoBottomPage("toHandle");
		 	 }
		}); 
	   $("#submit").click(function(){//
			 var selRowId = $("#selRowId").val();
			 var order_org = $("#ORDER_ORG" + selRowId).val();
			 var PROBLEM_FEEDBACK_NO =  $.trim($("#PROBLEM_FEEDBACK_NO" + selRowId).html());
		 	 if(null == selRowId || "" == selRowId){
		 		 parent.showErrorMsg("请选择一条记录");
		 	 }else{
		 		showConfirm("您确认要提交吗?","mtbSubmit('submit?PROBLEM_FEEDBACK_NO="+PROBLEM_FEEDBACK_NO+"&ORDER_ORG="+order_org+"','PROBLEM_FEEDBACK_ID','"+selRowId+"');");
		 		
		 	 }
		});  
	   
	   $("#flowTrack").click(function(){
			var selRowId = $("#selRowId").val();
			if (selRowId == "") {
				parent.showWarnMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
				return;
			}
			//var flowServiceId = selRowId;//$('#ORDER_TRACE_ID'+selRowId).val();
			myShowModalDialog(ctxPath+"/sys/flow/toList/"+selRowId);
		});
	 
	
	 
})

function mtbSubmit(actionUrl,pkId,selRowId,goUrl){
	closeWindow();
	 $.ajax({
	 	url: actionUrl+"&"+pkId+"="+selRowId,
		type:"POST",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanel("提交成功");
				$("#queryForm").attr("action","toList");
				queryForm.submit();
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

function setSelOperateEx(obj){ 
	//可根据id或name或所在列数获得值，具体方法开发人员自行选择。
	var selRowId = $("#selRowId").val();
	var chann_id = $("#chann_id").val();
	//var is_drp_ledger = $("#is_drp_ledger").val();
	var state =  $.trim($("#STATE" + selRowId).html());
	//
	var creator =  $("#CREATOR"+ selRowId).val();
	var updator =  $("#UPDATOR"+ selRowId).val();
	var chann_ids =  $("#CHANN_ID"+ selRowId).val();
	
	//按钮状态重置
	btnReset();
	if(state == "已处理"){
		btnDisable(["modify","submit"]);
		if(creator!=updator){
			if(chann_id!=chann_ids){
				btnDisable(["delete"]);
			}
		}
	}else if(state == "草稿"){
		btnDisable(["handle"]);
	}else if (state == "处理中"){
		btnDisable(["modify","delete","submit"]);
		
	}else if(state == "已退回"){
		btnDisable(["handle"]);
		if(creator!=updator){
			if(chann_id!=chann_ids){
				btnDisable(["modify","delete","submit"]);
			}
		}
	}
	//if(creator!="admin"){
	//	btnDisable(["modify","delete","submit"]);
	//}
	
}


 
 
 