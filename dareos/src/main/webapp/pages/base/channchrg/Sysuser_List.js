
/**
 * @module 系统管理
 * @func 渠道分管
 * @version 1.1
 * @author 张忠斌
 */
$(function () {
    parent.document.getElementById("selRowId").value = "";
	$("#allChecked").click(function(){
		var flag = document.getElementById("allChecked").checked;
		if(flag){
			$("#ordertb :checkbox").attr("checked","true");
		}else{
			$("#ordertb :checkbox").removeAttr("checked");
		}
		setSelRowId();
		 
	});
	
	$("#ordertb tr:gt(0)").find("td:gt(0)td:lt(7)").click(function(){
		checkedThis(this);
	});
	
});


function checkedThis(obj){
	var checkbox = $(obj).parent().find("input:checkbox");
	var flag = checkbox.attr("checked");
	if("checked" == flag){
		checkbox.removeAttr("checked");
	}else{
		checkbox.attr("checked","true");
	}
	setSelRowId();
}

function autoSubmit(){
	$("#queryForm").attr("action","childList");
	$("#queryForm").submit();
}

function setSelEid(obj){
//	var flag = $(obj).attr("checked");
//	alert(flag);
//	if(null == flag){
//		$(obj).attr("checked","true");
//	}else{
//		$(obj).removeAttr("checked");
//	}
//	setEidChecked(obj);
}

//明细表点击后设置选中
function setEidChecked(obj){
	var flag = $(obj).attr("checked");
	if(flag){
		$(obj).attr("checked","true");
	}else{
		$(obj).removeAttr("checked");
	}
	setSelRowId();
	 
}




function setSelRowId(){
    var ids = "";
    $("#ordertb input[name='eid']:checked").each(function(){
    	//只添加  分管表 没有的渠道
    	var USER_CHANN_CHRG_ID = $(this).parent().parent().find("input[name='USER_CHANN_CHRG_ID']").val();
    	if(null == USER_CHANN_CHRG_ID || "" == USER_CHANN_CHRG_ID){
    		ids = ids+$(this).val()+",";
    	} 
    });
   
    ids = ids.substr(0,ids.length-1);
    
    //设置选中的Id
    parent.document.getElementById("selRowId").value = ids;
    
    var oldIds = "";
    $("#ordertb input[name='eid']").not("input:checked").each(function(){
    	var USER_CHANN_CHRG_ID = $(this).parent().parent().find("input[name='USER_CHANN_CHRG_ID']").val();
    	if(null == USER_CHANN_CHRG_ID || "" == USER_CHANN_CHRG_ID){
    		 
    	}else{
    		oldIds = oldIds+"'"+$(this).val()+"',";
    	}
    });
    oldIds = oldIds.substr(0,oldIds.length-1);
    parent.document.getElementById("deleteIds").value = oldIds;
}

//取消分管
function listDelConfirm(obj,id){
	closeWindow();
	var XTYHIDS =  $("#"+id).val();
	var CHANN_ID = parent.topcontent.$("#CHANN_ID_1").val();
	if(null != XTYHIDS){
		XTYHIDS = "'"+XTYHIDS+"'";
	}
	 $.ajax({
	 	url: "deleteYhxx",
		type:"POST",
		data:{"XTYHIDS":XTYHIDS,"CHANN_ID":CHANN_ID},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("操作成功");
				$("#"+id).removeAttr("checked");
				$(obj).attr("disabled","disabled");
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}


 
   
