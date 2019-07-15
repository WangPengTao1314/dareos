
/**
 *@module 项目管理
 *@func 招投标编辑js
 *@version 1.0
 *@author 王朋涛
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
	mtbSaveListener("edit","tendering_id","toList","mainForm");
	
});

function changeinput(e,n){
	var newinput = document.getElementById('ipt'+n)
	var newmytable =document.getElementById('myTable1')
	var new_img = document.getElementById('imgsz'+n)
	if(newmytable.style.display == 'none'){
		newmytable.style.display = '';
		newinput.value="收起"
		new_img.style.transform="rotate(0deg)"
	/*	imgsz.style.transform="rotate(0deg)"*/
	}else{
		newmytable.style.display='none';
		newinput.value = "展开";
		/*transform:rotate(180deg);*/
		new_img.style.transform="rotate(180deg)"	
	}   
 }



