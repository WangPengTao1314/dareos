/**
 *@module 售后管理
 *@func 返修单框架js
 *@version 1.0
 *@author 王朋涛
 */
$(function(){
	//框架页面初始化
	var HEAD = $("#HEAD").val();
	framePageInit("toList");
	//var module = $("#module").val();
	var module = document.getElementById("module").value;
	//加载左帧页面
    $("#leftcontent").attr("src","showTree");
    	treeShowHide();
    	var paramUrl=document.getElementById("paramUrl");
	 if(paramUrl!=null&&paramUrl.value!=""){
		 console.log(utf8((paramUrl.value.replaceAll("andflag","&")).replaceAll("equalsflag","=")));
	    framePageInit("toList" +utf8((paramUrl.value.replaceAll("andflag","&")).replaceAll("equalsflag","=")));
	 }else{	
		if(HEAD=='H'){
			framePageInit("toList?STATE2=审核通过&module=" + module);
		}else{
			framePageInit("toList?module=" + module);
		}
	 }
});

//bottomcontent页面跳转。
function gotoBottomPage(action){
	//获取当前选中的记录
	var selRowId = $("#selRowId").val();
	console.log("selRowId:"+selRowId);
	//初始化时下帧页面的action
	if(null == action || "label_1" == action){//"label_1"的判断是为了与主从界面通用
		action = "toDetail";
	}
	var url = action+"?SALE_ORDER_ID="+selRowId;
	//下帧页面跳转
	myShowModalDialog(url,true);
}
