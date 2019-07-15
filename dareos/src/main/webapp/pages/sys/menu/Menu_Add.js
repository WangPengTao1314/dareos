
/**
 *@module 菜单信息维护
 *@func 
 *@version 
 *@author 
 */
$(function(){
	if(!($("#tendering_no").val() == null && $("#tendering_no") == "")){
        $("#tendering_no").attr("readonly","readonly");
        $("#tendering_no").css("background-color","#cccccc");
     }
	//初始化校验
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	mtbSaveListener("add","menuId","toList","mainForm");
	
});



