/**
 *@module 基础数据
 *@func 部门信息维护
 *@version 1.1
 *@author 吴亚林
 */

$(function(){
	//页面初始化
	listPageInit("toList");
	//新增和修改按钮初始化
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	//mtbDelListener("delete","BMXXID");
	
	var selRowId = $("#selRowId").val();
    if(selRowId == null || '' == selRowId){
        btnDisable(["start","modify","delete","stop"]);
	      return;
    }
    
    $("#delete").click(function(){
		  if(null == selRowId || "" == selRowId){
			  showWarnMsg("请选择一条记录");
	 	 	  return;
	 	 } 
	 	 showConfirm("将删除该机构及其下级机构,您确认删除吗?","listDelConfirm();","N");
	 });
    
	// 停用
    $("#stop").click(function(){
 	   //获取当前选中的记录
 	   if(null == selRowId || "" == selRowId){
 		   showWarnMsg("请选择一条记录");
 	      return;
 	   }
 	   showConfirm("将停用该机构及其下级机构，是否继续?","listStopConfirm();");
 	});
//	$("#stop").click(function(){
//	   //获取当前选中的记录
//       var selRowId = parent.document.getElementById("selRowId").value;
//	   if(null == selRowId || "" == selRowId){
//	      parent.showErrorMsg("请至少选择一条记录");
//	      return;
//	   }
//
//	   $.ajax({
//		url: "changeState&BMXXID="+selRowId+"&ckstate=0",
//		type:"POST",
//		dataType:"text",
//		complete: function(xhr){
//			eval("jsonResult = "+xhr.responseText);
//			if(jsonResult.success===true){
//				parent.showMsgPanel("状态修改成功");
//				$("#state"+selRowId).html(utf8Decode(jsonResult.messages));
//				$("#state"+selRowId).val(utf8Decode(jsonResult.messages));
//				changeButton(utf8Decode(jsonResult.messages));
//				$("#pageForm").submit();
//			}else{
//				if(jsonResult.messages=="stateError1"){
//					parent.showConfirm("该部门有未停用的下级部门和人员，您要全部停用吗？","topcontent.stopAll();");
//				}else{
//					parent.showErrorMsg(utf8Decode(jsonResult.messages));
//				}
//			}
//		}
//	  });
//	});
	
	// 启用
    $("#start").click(function(){
 	   //获取当前选中的记录
 	   if(null == selRowId || "" == selRowId){
 		   showWarnMsg("请选择一条记录");
 	      return;
 	   }
 	   showConfirm("将启用该机构及其下级机构，是否继续?","listStartConfirm();");

 	});
//	$("#start").click(function(){
//	   //获取当前选中的记录
//	   var selRowId = parent.document.getElementById("selRowId").value;
//	   
//	   if(null == selRowId || "" == selRowId){
//	      parent.showErrorMsg("请至少选择一条记录");
//	      return;
//	   }
//	   
//	   $.ajax({
//		url: "changeState&BMXXID="+utf8(selRowId),
//		type:"POST",
//		dataType:"text",
//		complete: function(xhr){
//			eval("jsonResult = "+xhr.responseText);
//			if(jsonResult.success===true){
//				parent.showMsgPanel("状态修改成功");
//				changeButton(utf8Decode(jsonResult.messages));
//				$("#pageForm").submit();
//			}else{
//				parent.showErrorMsg(utf8Decode(jsonResult.messages));
//			}
//		}
//	  });
//	});
	
});	

function stopAll(){
	 var selRowId = parent.document.getElementById("selRowId").value;
	 $.ajax({
		url: "changeState?BMXXID="+selRowId+"&ckstate=1",
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("状态修改成功");
				//parent.window.gotoBottomPage("label_1");
				//$("#state"+selRowId).html(utf8Decode(jsonResult.messages));
				//$("#state"+selRowId).val(utf8Decode(jsonResult.messages));
				//changeButton(utf8Decode(jsonResult.messages));
				//parent.window.gotoBottomPage("label_1");
				//saveSuccess("状态修改成功","cmxx.shtml?action=toFrame");
				$("#pageForm").submit();
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	  });
}
function changeButton(value){
	 btnReset();
     if ('启用' == value ){
     btnDisable(["start","delete","modify"]);
   }else if ('制定' == value){
      btnDisable(["stop"]);
   } else if('停用' == value ){
      btnDisable(["stop","delete"]);
   }else {
      btnDisable(["start","stop"]);
   }
}
function load(){
	//下帧页面跳转
	$("#bottomcontent").attr("src","toDetail?BMXXID=XCVSA");
}

function listStartConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
	   //获取当前选中的记录
//	   var selRowId = parent.document.getElementById("selRowId").value;
	   
	   if(null == selRowId || "" == selRowId){
	      parent.showErrorMsg("请至少选择一条记录");
	      return;
	   }
	   
	   $.ajax({
		url: "changeState?BMXXID="+utf8(selRowId),
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("状态修改成功");
				//changeButton(utf8Decode(jsonResult.messages));
				$("#pageForm").submit();
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	  });
}

function listStopConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
	   //获取当前选中的记录
//	   var selRowId = parent.document.getElementById("selRowId").value;
	   
	   if(null == selRowId || "" == selRowId){
	      parent.showErrorMsg("请至少选择一条记录");
	      return;
	   }
	$.ajax({
	url: "changeState?BMXXID="+selRowId+"&ckstate=0",
	type:"POST",
	dataType:"text",
	complete: function(xhr){
		eval("jsonResult = "+xhr.responseText);
		if(jsonResult.success===true){
			parent.showMsgPanel("状态修改成功");
			//$("#state"+selRowId).html(utf8Decode(jsonResult.messages));
			//$("#state"+selRowId).val(utf8Decode(jsonResult.messages));
			//changeButton(utf8Decode(jsonResult.messages));
			$("#pageForm").submit();
		}else{
			if(jsonResult.messages=="stateError1"){
				parent.showConfirm("该部门有未停用的下级部门和人员，您要全部停用吗？","topcontent.stopAll();");
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	}
  });
}

function listDelConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
	 $.ajax({
	 	url: "delete?BMXXID="+selRowId,
		type:"POST",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				//删除树节点
				parent.leftcontent.deleteNodes(selRowId);
				//当前树选中节点
				var selNodesId = parent.leftcontent.getSelNodesId();
				showMsgPanel("删除成功",'window.location="toList?BMXXID='+selNodesId+'"');
				
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}