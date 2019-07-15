


/**
 * @module 系统管理
 * @func 货品信息
 * @version 1.1
 * @author 刘曰刚
 */
$(function(){
	//框架页面初始化
//	framePageInit("toList");
	$("#leftcontent").attr("src", "showTree");
	treeShowHide();
	var paramUrl=document.getElementById("paramUrl");
	 if(paramUrl!=null&&paramUrl.value!=""){
	    framePageInit("toList" +utf8((paramUrl.value.replaceAll("andflag","&")).replaceAll("equalsflag","=")));
	 }else{
	    framePageInit("toList?module=" + module);
	 }
});
function gotoBottomPage(showLabelId) {
	//获取当前选中的记录
	var selRowId = $("#selRowId").val();
	var url;
	if(null == showLabelId){
		showLabelId = $("#showLabel").attr("value");
		if("toEdit"==showLabelId){
			showLabelId="label_1";
		}
	}else{
		//设置当前显示的标签页
		$("#showLabel").attr("value",showLabelId);
	}
	//初始化时下帧页面的action
	if ("label_4" == showLabelId) {//label_4跳转货品套明细
		url = "toSuitList";
	}else if("label_3" == showLabelId){//label_3跳转计量单位明细
		url="toUnitList";
	}else if("label_2" == showLabelId){
		url="toPrdTech";//跳转货品特殊工艺维护
	}else if("toEdit"==showLabelId){
		url="toEdit";//新增
		setLabelSelected("label_1");
	}else if("label_1"==showLabelId){//详细信息页面
		url="toDetail";
		setLabelSelected("label_1");
	}else{
		url=showLabelId;
	}
	$("#flag").val("0");
	//下帧页面跳转
	if("label_4" == showLabelId){
		
		myShowModalDialog(url+"?MAIN_PRD_ID="+utf8(selRowId));
		//$("#bottomcontent").attr("src", url+"?MAIN_PRD_ID="+utf8(selRowId));
	}else{
		myShowModalDialog(url+"?PRD_ID="+utf8(selRowId));
		//$("#bottomcontent").attr("src", url+"?PRD_ID="+utf8(selRowId));
	}
}
	//bottomcontent页面跳转。
	function gotoBottomPage(action) {
		//获取当前选中的记录
		var selRowId = $("#selRowId").val();
		//初始化时下帧页面的action
		if (null == action || "label_1" == action) {//"label_1"的判断是为了与主从界面通用
			action = "toDetail";
		}
		var url = action + "?PRD_ID=" + selRowId;
		//下帧页面跳转
		myShowModalDialog(url)
	
}
