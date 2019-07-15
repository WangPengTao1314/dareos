/**
 *@module 售后管理
 *@func 框架
 *@version 1.1
 *@author 王朋涛
 */
$(function(){
	//框架页面初始化
	framePageInit("toList");
	//加载左帧页面
	var module = document.getElementById("module").value;
    $("#leftcontent").attr("src","showTree");
    	treeShowHide();
    	var paramUrl=document.getElementById("paramUrl");
	 if(paramUrl!=null&&paramUrl.value!=""){
		 console.log(utf8((paramUrl.value.replaceAll("andflag","&")).replaceAll("equalsflag","=")));
	    framePageInit("toList" +utf8((paramUrl.value.replaceAll("andflag","&")).replaceAll("equalsflag","=")));
	 }else{	
	    framePageInit("toList?module=" + module);
	 }
});

//bottomcontent页面跳转。
function gotoBottomPage(action){
	//获取当前选中的记录
	var selRowId = $("#selRowId").val();
	//初始化时下帧页面的action
	if(null == action || "label_1" == action){//"label_1"的判断是为了与主从界面通用
		action = "toDetail";
	}
	var url = action+"?PROBLEM_FEEDBACK_ID="+selRowId;
	//下帧页面跳转
	myShowModalDialog(url,true);
}
function mtbDelConfirm22(actionUrl,pkId,selRowId){
	closeWindow();
	$.ajax({
		url: actionUrl+"?"+pkId+"="+selRowId,
		type:"POST",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				
				showMsgPanel("操作成功!");
				//gotoBottomPage2("toEdit",selRowId);
				//topcontent.pageForm.mainForm.submit();
				//$("#mainForm").submit();
				// window.location="#";
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
	
}

