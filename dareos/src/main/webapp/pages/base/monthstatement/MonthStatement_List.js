/**
 *@module 基础数据
 *@func品牌信息
 *@version 1.1
 *@author 郭利伟
 */
$(function(){
	//页面初始化
	listPageInit("toList");
	$("#queryTemp").click(function(){
		if($("#year").val()==""){
			showErrorMsg("请选择年份");
			return;
		}
		$("#queryForm").submit();
	})
	
	$("#monthStat").click(function(){
		var year = $("#year").val();
		if(year==""){
			showErrorMsg("请选择年份");
			return;
		}
		$.ajax({
		url: "edit?year="+year,
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("生成成功");
				$("#pageForm").submit();
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
 	});
	})
});	

function toAcct(year,month){
	showConfirm("是否结算"+year+"-"+month+"账单?","acct("+year+","+month+");");
}

function acct(year,month){
	$.ajax({
		url: "statement?year="+year+"&month="+month,
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("结算成功");
				$("#pageForm").submit();
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
 	});
}