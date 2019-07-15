/*
  *@module 系统管理
  *@fuc 系统角色框架页面
  *@version 1.1
  *@author 唐赟
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
		url = "childList";
	}else{
		url = "toDetail";
	}
	
	//下帧页面跳转
	$("#bottomcontent").attr("src",url+"?XTJSID="+selRowId);
	
}*/
//bottomcontent页面跳转。
function gotoBottomPage(action) {
	//获取当前选中的记录
	var selRowId = $("#selRowId").val();
	//初始化时下帧页面的action
	if (null == action || "label_1" == action) {//"label_1"的判断是为了与主从界面通用
		action = "toDetail";
	}
	var url = action + "?XTJSID=" + selRowId;
	//下帧页面跳转
	myShowModalDialog(url)
}
  
  
 