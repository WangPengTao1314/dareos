$(function(){
	var HEAD = $("#HEAD").val();
	var module = document.getElementById("module").value;
	//框架页面初始化
	framePageInit("toList");
	var paramUrl=document.getElementById("paramUrl");
	if(paramUrl!=null&&paramUrl.value!=""){
		framePageInit("toList" +utf8((paramUrl.value.replaceAll("andflag","&")).replaceAll("equalsflag","=")));
	}else{
		if(HEAD=='H'){
			framePageInit("toList?STATE2=已完成&module=" + module);
		}else{
			framePageInit("toList?module=" + module);
		}
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