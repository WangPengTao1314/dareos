
/**
 *@module 项目管理
 *@func 编辑js
 *@version 1.1
 *@author 王朋涛
 */
$(function(){
    
	if(!($("#project_no").val() == null && $("#project_no") == "")){
        $("#project_no").attr("readonly","readonly");
        $("#project_no").css("background-color","#cccccc");
     }
	 
	 
	//初始化校验
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	mtbSaveListener("edit","project_id","toList","mainForm");
	 
	
});
function changeinput(e){
	var newinput = document.getElementById('ipt')
	var newmytable =document.getElementById('myTable1')
	var new_img =document.getElementById('imgsz')
	if(newmytable.style.display == 'none'){
		newmytable.style.display = '';
		newinput.value="收起"
			new_img.style.transform="rotate(0deg)"
	    
	}else{
		newmytable.style.display='none';
		newinput.value = "展开";
		/*transform:rotate(180deg);*/
		imgsz.style.transform="rotate(180deg)"
		
	}
        
 }
