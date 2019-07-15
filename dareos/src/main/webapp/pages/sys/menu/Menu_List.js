/**
  *@module 菜单信息维护
  *@fuc 
  *@version 
  *@author 
*/
	
$(function(){
	 //主从及主从从列表页面通用加载方法
	listPageInit("toList");
 
	//新增和修改按钮初始化
	//mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	mtbDelListener("delete","menuId");
	$("#add").click(function(){
		setCommonPageInfo(false);
		parent.window.gotoBottomPage("toAdd");
	});
	
	$("#modify").click(function(){
		 var selRowId = $("#selRowId").val();
	 	 if(null == selRowId || "" == selRowId){
	 		 parent.showErrorMsg("请选择一条记录");
	 	 }else{
	 	 	setCommonPageInfo(true);
			parent.window.gotoBottomPage("toEdit");
	 	 }
	});
	$("#reset").click(function(){
	    $(":text").val("");
	 }); 
	
})

 
 
 