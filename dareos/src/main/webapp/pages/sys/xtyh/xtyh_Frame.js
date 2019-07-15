/**

  *@module 系统管理

  *@fuc 系统用户框架页面

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

//bottomcontent页面跳转。
function gotoBottomPage(url){
	//获取当前选中的记录
	var selRowId = $("#selRowId").val();
	//初始化时下帧页面的action
	if("" == url || null==url){
		url = "toDetail";
	}
	url = ""+url+"?XTYHID="+selRowId;
	//下帧页面跳转
//	$("#bottomcontent").attr("src",url);
	myShowModalDialog(url)
}