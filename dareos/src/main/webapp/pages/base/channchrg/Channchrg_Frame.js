
/**
 * @module 系统管理
 * @func 渠道分管
 * @version 1.1
 * @author 张忠斌
 */
$(function () {
	//框架页面初始化
	framePageInit("","42","51");
	//框架页面初始化
	$("#topcontent").attr("src","toTopPage");
	gotoBottomPage();
});

//bottomcontent页面跳转。
function gotoBottomPage() {
	var url = "childList";
	//下帧页面跳转
	$("#bottomcontent").attr("src", url);
}




