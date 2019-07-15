/**
 * @prjName:喜临门营销平台
 * @fileName:Prmtplan_Frame
 * @author zzb
 * @time   2013-08-23 16:04:28 
 * @version 1.1
 */
$(function(){
	//框架页面初始化
	var module = document.getElementById("module").value;
	var paramUrl = document.getElementById("paramUrl");
	var FLAG = $("#FLAG").val();
	 if(paramUrl!=null&&paramUrl.value!="")
	 {
	    framePageInit("toList" +utf8((paramUrl.value.replaceAll("andflag","&")).replaceAll("equalsflag","=")));
	 }	
	 else
     {	 
	    framePageInit("toList?module=" + module+"&FLAG="+FLAG);
	 }	
});

//bottomcontent页面跳转
function gotoBottomPage(url){
    //获取当前选中的记录
	var selRowId = $("#selRowId").val();
	if(null == url){
		url = $("#showLabel").attr("value");
	}else{
		//设置当前显示的标签页
		$("#showLabel").attr("value",url);
	}
	//下帧页面跳转
//	$("#bottomcontent").attr("src",url+"&PRMT_PLAN_ID="+selRowId);
	myShowModalDialog(url+"?PRMT_PLAN_ID="+selRowId)
}