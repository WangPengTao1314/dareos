/**
  *@module 项目管理
  *@fuc 招投标管理列表js
  *@version 1.0
  *@author wang_pt
*/
	
$(function(){
	 //主从及主从从列表页面通用加载方法
	listPageInit("toList");
	//新增和修改按钮初始化
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	mtbDelListener("delete","tendering_id");
	var selRowId = $("#selRowId").val();
	   if(null == selRowId || "" == selRowId){
		  //按钮失效设置
	      btnDisable(["modify","delete"]);
	      return;
	   }
	  
	
	 
})

 
 
 