/**
 * @fileName:Goodsorderhd_Frame
 */
$(function(){
	var module = document.getElementById("module").value;
	 //框架页面初始化
	 var CHANN_ID = document.getElementById("CHANN_ID").value;
	 var channel = document.getElementById("channel").value;
	 var flag = $("#flag").val();
	 var HEAD=$("#HEAD").val();
	 var paramUrl=document.getElementById("paramUrl");
	 if(channel != "junior"){
		 if(HEAD=='H'){
			 framePageInit("toList?STATE2=H_H&CHANN_ID=" + CHANN_ID+"&channel="+channel+"&flag="+flag);
		 }else{
			 framePageInit("toList?CHANN_ID=" + CHANN_ID+"&channel="+channel+"&flag="+flag);
		 }
	 } else {
		    framePageInit("toListJunior?CHANN_ID=" + CHANN_ID+"&channel="+channel+"&flag="+flag);
	}
	 
});

//bottomcontent页面跳转
function gotoBottomPage(action){
    //获取当前选中的记录
	var selRowId = $("#selRowId").val();

	if(null == action){
		action = "toDetail";
	}

	var andstr = "?";
	if(action.indexOf(andstr) != -1 ){
		andstr = "&";
	}
	
	var module = document.getElementById("module").value;
	var url = action + andstr + "module=" + module + "&selRowId="+selRowId;
	//下帧页面跳转
	myShowModalDialog(url, true, 1000);
}

 function closePage(){
	 $("#midden").html("");
 }
