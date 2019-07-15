/**
 * @module 发货管理
 * @func 发货管理框架js
 * @version 1.0
 * @author 王朋涛
 */
$(function() {
	// 框架页面初始化
	var module = document.getElementById("module").value;
	framePageInit("toList");
	// 加载左帧页面
	$("#leftcontent").attr("src", "showTree");
	treeShowHide();
	var paramUrl = document.getElementById("paramUrl");
	if (paramUrl != null && paramUrl.value != "") {
		console.log(utf8((paramUrl.value.replaceAll("andflag", "&"))
				.replaceAll("equalsflag", "=")));
		framePageInit("toList"
				+ utf8((paramUrl.value.replaceAll("andflag", "&")).replaceAll(
						"equalsflag", "=")));
	} else {
		framePageInit("toList?module=" + module);
	}
});
 

// bottomcontent页面跳转。
function gotoBottomPage(action) {
	// 获取当前选中的记录
	var selRowId = $("#selRowId").val();
	console.log("selRowId:" + selRowId);
	// 初始化时下帧页面的action
	if (null == action || "label_1" == action) {// "label_1"的判断是为了与主从界面通用
		action = "toDetail";
	}
	var url = action + "?SEND_ORDER_ID=" + selRowId;
	// 下帧页面跳转
	myShowModalDialog(url,true);
}


