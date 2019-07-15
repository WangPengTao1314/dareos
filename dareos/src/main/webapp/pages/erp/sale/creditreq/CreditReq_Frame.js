/**
 * @module 基础数据
 * @func品牌信息
 * @version 1.1
 * @author 郭利伟
 */
$(function(){
	var paramUrl=document.getElementById("paramUrl");
	 if(paramUrl!=null&&paramUrl.value!=""){
	    framePageInit("toList" +utf8((paramUrl.value.replaceAll("andflag","&")).replaceAll("equalsflag","=")));
	 }else{
	    framePageInit("toList?module=" + module.value);
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
	var url = ""+action+"?creditReqId="+selRowId;
	//下帧页面跳转
	//$("#bottomcontent").attr("src",url);
	myShowModalDialog(url);
}
