/**
 *@module 基础资料
 *@func 维护编码规则
 *@version 1.1
 *@author 孙炜
 */
$(function(){
	//框架页面初始化
	framePageInit("toList");

     var paramUrl=document.getElementById("paramUrl");
	 if(paramUrl!=null&&paramUrl.value!="")
	    framePageInit("toList" +utf8((paramUrl.value.replaceAll("andflag","&")).replaceAll("equalsflag","=")));
	 else	
	    framePageInit("toList?module=" + module);
});

//bottomcontent页面跳转.点击标签，根据选中的记录查询子表信息。
/*function gotoBottomPage(showLabelId){
	//获取当前选中的记录
	var selRowId = $("#selRowId").val();
	if(null == showLabelId){
		showLabelId = $("#showLabel").attr("value");
	}else{
		//设置当前显示的标签页
		$("#showLabel").attr("value",showLabelId);
	}
	var url;
	if("label_1" == showLabelId){
		url = "childList"
	}else{
		url = "toDetail"
	}
	//下帧页面跳转
	$("#bottomcontent").attr("src",url+"?BMGZID="+utf8(selRowId));
	
}*/
//bottomcontent页面跳转。
function gotoBottomPage(action) {
	//获取当前选中的记录
	var selRowId = $("#selRowId").val();
	//初始化时下帧页面的action
	if (null == action || "label_1" == action) {//"label_1"的判断是为了与主从界面通用
		action = "toDetail";
	}
	var url = action + "?BMGZID=" + selRowId;
	//下帧页面跳转
	myShowModalDialog(url)
}
//是否刷新标记
var refresh = true;
function setRefreshFlag(isRefresh){
	refresh = isRefresh;
}
