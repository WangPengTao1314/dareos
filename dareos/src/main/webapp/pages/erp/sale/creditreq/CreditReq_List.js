/**
 *@module 基础数据
 *@func品牌信息
 *@version 1.1
 *@author 郭利伟
 */
$(function(){
	//页面初始化
	var module = parent.window.document.getElementById("module").value;
	listPageInit("toList");
	//新增和修改按钮初始化
	mtbAddModiInit();
	$("#query").click(function(){
		if("sh"==module){
			$("#state option[value='未提交']").remove();
		}
	});
	//删除监听.参数1：删除action，参数2：删除主键Id
	//mtbDelListener("delete","BRAND_ID");
	var selRowId = $("#selRowId").val();
	whBtnHidden(["audit_btn"]);
	shBtnHidden(["add_btn","modify_btn","delete_btn","commit_btn"]);
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
	 	 showConfirm("将删除该充值信息,您确认删除吗?","listDelConfirm();");
	 });
	
	// 启用
	$("#commit").click(function(){
	   //获取当前选中的记录
	   var selRowId = parent.document.getElementById("selRowId").value;
	   if(null == selRowId || "" == selRowId){
		   showWarnMsg("请至少选择一条记录");
	      return;
	   }
	   showConfirm("将提交该充值信息，是否继续?","listStartConfirm();");
	});
	// 审核
	$("#audit").click(function(){
		   //获取当前选中的记录
		   var selRowId = parent.document.getElementById("selRowId").value;
		   if(null == selRowId || "" == selRowId){
			   showWarnMsg("请至少选择一条记录");
		      return;
		   }
		   showAuditDiv();
		});
});	

function auditCommon(type,remark){
	var selRowId = $("#selRowId").val();
	var index = parent.layer.load(1);
	 $.ajax({
	 	url: "toAudit?creditReqId="+selRowId+"&remark="+remark+"&type="+type,
		type:"POST",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.layer.close(index);
				showMsgPanel("操作成功",'$("#pageForm").submit();');
			}else{
				parent.layer.close(index);
				showWarnMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}
//记录按钮根据状态控制
 function setSelOperateEx(obj){
	var selRowId = $("#selRowId").val();
	//var state1 =  $.trim($(obj).find("td[json='STATE']").text());
	var state = $("#state" + selRowId).val();
	//按钮状态重置
	btnReset();
	//$("#qxBtnTb input[name='brandList']").removeAttr("disabled");
	if(state == "提交"){
		btnDisable(["commit","delete","modify"]);
		
	}else if(state == "审核通过"){
		btnDisable(["audit","delete","commit","modify"]);
		
	}else if (state == "未提交"){
		btnDisable(["audit"]);
	}else if(state == "退回"){
		btnDisable(["audit"]);
	}
 }
 //删除记录
function listDelConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
	 $.ajax({
	 	url: "delete?creditReqId="+selRowId,
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
//审核记录
function listAuditConfirm(){
	closeWindow();
	var selRowId = $("#selRowId").val();
	 $.ajax({
		url: "toAudit?creditReqId="+utf8(selRowId),
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanel("状态修改成功",'$("#pageForm").submit();');
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
	 url: "toCommit?creditReqId="+utf8(selRowId),
	 type:"POST",
 	 dataType:"text",
	 complete: function(xhr){
		eval("jsonResult = "+xhr.responseText);
		if(jsonResult.success===true){
			showMsgPanel("状态修改成功",'$("#pageForm").submit();');
            //$("#pageForm").submit();
		}else{
			showWarnMsg(utf8Decode(jsonResult.messages));
		}
	  }
    });
}
