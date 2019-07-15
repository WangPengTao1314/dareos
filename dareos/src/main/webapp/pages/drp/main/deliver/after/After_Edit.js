/**
 *@module 售后管理
 *@func 编辑js
 *@version 1.0
 *@author 王朋涛
 */
$(function(){
    
	if(!($("#problem_feedback_no").val() == null && $("#problem_feedback_no") == "")){
      //  $("#problem_feedback_no").attr("readonly","readonly");
       //$("#problem_feedback_no").css("background-color","#cccccc");
     }
	
	 
	//初始化校验
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	mtbSaveListener("edit","PROBLEM_FEEDBACK_ID","toList","mainForm");
	 
	
});

//选择经销商
function selChann() {
	var XTYHID =$("#XTYHID").val();
	var initSel = " IS_BASE_FLAG = '0' and CHANN_ID in( select t.chann_id from BASE_USER_CHANN_CHRG t where t.user_id='" + XTYHID + "') ";
	$('#initSel').val(initSel);
	selCommon("BS_205", false, "CHANN_ID", "CHANN_ID", "forms[0]", 
			"CHANN_NO,CHANN_NAME,CHANN_ID,PERSON_CON,TEL,RECV_ADDR",
			"CHANN_NO,CHANN_NAME,CHANN_ID,PERSON_CON,TEL,DELIVER_DTL_ADDR", "initSel");
	
}



function  deleteEntry(ATT_ID){
 	parent.showConfirm("您确认要删除吗?","mtbDelConfirm22('deleteFile','att_id','"+ATT_ID+"');");

}

function changeinput(e,n){
	var newinput = document.getElementById('ipt'+n)
	var newmytable =document.getElementById('myTable'+n)
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


