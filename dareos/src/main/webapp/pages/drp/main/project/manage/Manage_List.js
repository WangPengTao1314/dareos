/**
  *@module 项目管理
  *@fuc  项目管理一览js
  *@version 1.1
  *@author 王朋涛
*/
	
$(function(){
	 //主从及主从从列表页面通用加载方法
	listPageInit("toList");
	InitFormValidator(0);
	//新增和修改按钮初始化
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	mtbDelListener("delete","project_id");
	var selRowId = $("#selRowId").val();
	   if(null == selRowId || "" == selRowId){
		  //按钮失效设置
	      btnDisable(["modify","delete","maintain","command"]);
	      return;
	   }
	   
	   $("#maintain").click(function(){
			 var selRowId = $("#selRowId").val();
		 	 if(null == selRowId || "" == selRowId){
		 		 parent.showErrorMsg("请选择一条记录");
		 	 }else{
		 	 	setCommonPageInfo(true);
				parent.window.gotoBottomPage("toMxEdit");
		 	 }
		});
	   $("#command").click(function(){
			 var selRowId = $("#selRowId").val();
		 	 if(null == selRowId || "" == selRowId){
		 		 parent.showErrorMsg("请选择一条记录");
		 	 }else{
		 	 	setCommonPageInfo(true);
				parent.window.gotoBottomPage("toComEdit");
		 	 }
		});  
	 
	
	 
})

 
 
 