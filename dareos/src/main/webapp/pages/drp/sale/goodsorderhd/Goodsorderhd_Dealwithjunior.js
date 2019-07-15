/**
 */
$(function(){
	closePage();
	checkReturnShow();
	// 根据是否可查看价格权限控制明细字段价格金额等列的显示/隐藏
	changeDtlPrice();
	//维护页面需要隐藏的按钮
	whBtnHidden(["audit"]);
	 
	//审核页面需要隐藏的按钮
	shBtnHidden(["add", "modify", "delete", "commit", "revoke"]);
	//主从及主从从列表页面通用加载方法
	listPageInit("toListJunior");
	//新增和修改按钮初始化
	//addModiToEditFrameInit("toEditFrame");
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	mtbDelListener("delete", "GOODS_ORDER_ID");
	
	// 退回
	/* 移到 转总部订货订单 的编辑页面退回
	$("#back").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if(null == selRowId || "" == selRowId){
			parent.showErrorMsg("请选择一条记录");
			return;
		}
		
		layer.prompt({title: '退回原因', formType: 2}, function(text, index){
			layer.confirm('是否确定退回？', {
				btn: ['确定','取消'] //按钮
			}, function(){
				var GOODS_ORDER_NO = $("#GOODS_ORDER_NO_"+selRowId).val();
				$.ajax({
					url: "back2c",
					type:"POST",
					dataType:"text",
					data:{"selRowId": selRowId, "GOODS_ORDER_NO": GOODS_ORDER_NO},
					complete: function (xhr){
						eval("jsonResult = "+xhr.responseText);
						if(jsonResult.success===true){
							//parent.showMsgPanel(utf8Decode(jsonResult.messages));
							//$("#pageForm").submit();
							showMsgPanelCloseWindow(utf8Decode(jsonResult.messages),'parent.window.frames["topcontent"].document.getElementById("queryForm").submit()');
						}else{
							parent.showErrorMsg(utf8Decode(jsonResult.messages));
						}
					}
				});
			});
		});
	});*/
	// 转总部订货订单
	$("#turnToHQ").click(function(){
		var selRowId = $("#selRowId").val();
		if(null == selRowId || "" == selRowId){
			parent.showErrorMsg("请选择一条记录");
		}else{
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("toEdit?option=turnToHQ&erjiOrderId="+selRowId);
		}
	});
	// 核价
	$("#pricing").click(function(){
		var selRowId = $("#selRowId").val();
		if(null == selRowId || "" == selRowId){
			parent.showErrorMsg("请选择一条记录");
		}else{
			setCommonPageInfo(true);
			parent.window.gotoBottomPage("toEdit?option=pricing");
		}
	});
	
	// 进度跟踪
	$("#flowTrack").click(function(){
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		var flowServiceId = $('#ORDER_TRACE_ID'+selRowId).val();
		myShowModalDialog(ctxPath+"/sys/flow/toList/"+flowServiceId);
	});
	
	$("#commit").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		var BILL_TYPE = $("#BILL_TYPE"+selRowId).val();
		var REBATE_CURRT = $("#REBATE_CURRT").val();
		var ORDER_AMOUNT = $("#ORDER_AMOUNT"+selRowId).val();
		REBATE_CURRT = parseFloat(REBATE_CURRT);
		ORDER_AMOUNT = parseFloat(ORDER_AMOUNT);
		if("返利订货" == BILL_TYPE && REBATE_CURRT<ORDER_AMOUNT){
			parent.showErrorMsg("返利金额不足，不能使用返利下单");
			return;
		}
		toCommit(selRowId);
		
	});
	
	$("#revoke").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if(null == selRowId || "" == selRowId){
			parent.showErrorMsg("请选择一条记录");
			return;
		}
//		toFlow("3");
		doRevoke(selRowId);
	});
	$("#audit").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		toFlow("2");
	});
	$("#flow").click(function () {
		var selRowId = parent.document.getElementById("selRowId").value;
		if (selRowId == "") {
			parent.showErrorMsg("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
			return;
		}
		toFlow("4");
	});
	
	secondPageCommit();
	
	$("#tempCredit").click(function(){
		tempCredit();
	});
	
	$("#rebackQuery").click(function(){
		 $("#flag").val("reback");
		 document.getElementById("q_search").click();
	});
	
	$("#sendQuery").click(function(){
		 $("#flag").val("isSend");
		 document.getElementById("q_search").click();
	});
	
	
});


//获取 是否使用返利
function getIS_USE_REBATE(){
	var selRowId = parent.document.getElementById("selRowId").value; 
	return $("#IS_USE_REBATE"+selRowId).val();
}

//获取返利折扣
function getREBATEDSCT(){
	var selRowId = parent.document.getElementById("selRowId").value; 
	return $("#REBATEDSCT"+selRowId).val();
}
//获取当前 返利额
function getREBATE_CURRT(){
	var selRowId = parent.document.getElementById("selRowId").value; 
	return $("#REBATE_CURRT"+selRowId).val();
}


//编辑页面 点击[保存并提交]，先跳转到一览页面 然后根据穿过来的参数GOODS_ORDER_ID 自动提交
function secondPageCommit(){
	var GOODS_ORDER_ID =  parent.document.getElementById("GOODS_ORDER_ID").value;
	var doCommitSave = parent.document.getElementById("doCommitSave").value;
	if("true" == doCommitSave){
		if(null != GOODS_ORDER_ID && "" != GOODS_ORDER_ID){
//		   toFlow("1",GOODS_ORDER_ID);
			toCommit(GOODS_ORDER_ID);
	    }
	}
	parent.document.getElementById("doCommitSave").value=false;
	parent.document.getElementById("doCommitSave").value="";
}

//撤销 不走审批
function doRevoke(GOODS_ORDER_ID){
	 var GOODS_ORDER_NO = $("#GOODS_ORDER_NO_"+GOODS_ORDER_ID).val();
	$.ajax({
		url: "revoke",
		type:"POST",
		dataType:"text",
		data:{"GOODS_ORDER_ID":GOODS_ORDER_ID,"GOODS_ORDER_NO":GOODS_ORDER_NO},
		complete: function (xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel(utf8Decode(jsonResult.messages));
			    $("#pageForm").submit();
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	 });
}

 function toFlow(i,GOODS_ORDER_ID) {
	 var cutid = "";
	if(null != GOODS_ORDER_ID && ""!=GOODS_ORDER_ID){
		cutid = GOODS_ORDER_ID;
	}else{
		cutid = $("#selRowId").val();
	}
	
	document.affirm.id.value = cutid;
    document.affirm.sourceURI.value=ctxPath+"/toList"+document.getElementById("paramUrl").value ;
	switch (Number(i)) {
	  case 1:
		affirmEvent(0);
		break;//提交
	  case 2:
		affirmEvent(1);
		break;//审核
	  case 3:
		affirmEvent(2);
		break; //撤销
	  case 4:
		showHistory(cutid);
		break;//撤销
	}
}
 
 
 function toCommit(selRowId){
	 parent.showWaitPanel();
	 var ORDER_AMOUNT = "";
	 ORDER_AMOUNT = $("#ordertb input:checked").parent().parent().find("td[name='ORDER_AMOUNT']").text();
	 ORDER_AMOUNT = $.trim(ORDER_AMOUNT);
	 var GOODS_ORDER_NO = $("#GOODS_ORDER_NO_"+selRowId).val();
	 var BILL_TYPE = $("#type"+selRowId).val();
	 $.ajax({
		url: "toCommit",
		type:"POST",
		dataType:"text",
		data:{"GOODS_ORDER_ID":selRowId,"ORDER_AMOUNT":ORDER_AMOUNT,"GOODS_ORDER_NO":GOODS_ORDER_NO,"BILL_TYPE":BILL_TYPE},
		complete: function (xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel(utf8Decode(jsonResult.messages));
			    $("#pageForm").submit();
			}else{
				parent.closeWindow();
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	 });
 }
 
 function closePage(){
	 $("#mycredit_show").hide();
 }
  //设置 一览页面 ‘订货总额’
 function setTotalAmount(total){
	 var selRowId = parent.document.getElementById("selRowId").value;
	 var name="TOTL_AMOUNT_"+selRowId;
	 total =  total+"&nbsp;";
	 $("#ordertb").find("td[name='"+name+"']").html(total);
 }
 
 
 function getAREA_ID(){
	 var selRowId = parent.document.getElementById("selRowId").value; 
	 return $("#AREA_ID_"+selRowId).val();
 }
 
 //临时信用申请
 function tempCredit(){
	 var mainFrame = window.top.mainFrame;  
	  mainFrame.addTab("w-009","临时信用申请","../../temp_credit_req.shtml?action=toFrame");
 }
 
 
//点击选择某条记录后，需要根据状态判断是否可用
function setSelOperateEx(obj) {
	var selRowId = parent.document.getElementById("selRowId").value;
	var state = document.getElementById("state" + selRowId).value;
	//按钮状态重置
	btnReset();
	
	//当状态=待转总部
	if (state == "待转总部") {
		//btnDisable(["modify","delete","commit","revoke","confirmquote","shouhou","audit","quote","confirm","back", "crexsdd","modifyHandle"]);
	} else {
		btnDisable(["turnToHQ"]);
	}
	
	//当状态=已转总部
	if (state == "已转总部") {
		btnDisable(["turnToHQ"]);
	}
}
 

function getREBATEFLAG(){
	 var selRowId = parent.document.getElementById("selRowId").value;
	 var type = document.getElementById("type" + selRowId).value;
	 if("返利订货"==type){
		 return true;
	 }else{
		 return false;
	 }
 }



/**
  * 获取主表的单据类型
  */
 function getBILL_TYPE(){
	 var selRowId = parent.document.getElementById("selRowId").value;
	 return $("#BILL_TYPE"+selRowId).val();
 }
 
 function dealwithjunior(){
	 $.ajax({
			url: "toListJunior",
			type:"GET",
			dataType:"text",
			complete: function (xhr){
				eval("jsonResult = "+xhr.responseText);
				if(jsonResult.success===true){
					var data = jsonResult.data;
					$("#userCredit").val(data.canUserCredit);
					$("#midden").hide();
					 var v = $("#testDiv").html();
					 var html = '<div style="position:absolute;top:10%;left:10%;width:50%;height:50%;filter:alpha(opacity=100);">' 
						 +v+
					 '</div>';
					 parent.$("#midden").append(html);
					 parent.closeWindow();
				}else{
					parent.closeWindow();
					alert(utf8Decode(jsonResult.messages));
				}
			}
		});
 }
 
