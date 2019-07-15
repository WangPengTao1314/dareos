/**

  *@module 系统管理

  *@fuc 系统用户列表

  *@version 1.1

  *@author 朱晓文
  */
$(function(){
	//页面初始化
	listPageInit("toList");
	//新增和修改按钮初始化
	mtbAddModiInit();
	//删除监听.参数1：删除action，参数2：删除主键Id
	mtbDelListener("delete","XTYHID");
	 var selid =$.trim($("#selRowId").val());  
	 if(null == selid || "" == selid){
	      btnDisable(["begin","modify","delete","defaultP","end","userRight","systemAuthorize","AuthorizeList"]);
	      return;
	   }
	 $("#ledgerChrg").click(function(){
			//获取当前选中的记录
			   if(null == selRowId || "" == selRowId){
			      parent.showErrorMsg("请选择一条记录");
			      return;
			   }
			parent.window.gotoBottomPage("ledgerChrgList");
		})
	$("#userRight").click(function(){
	   var edID =$.trim($("#selRowId").val());  
			    if(!checkEid(edID))return;
                showDialog(ctxPath+"/sys/qxgl/toUserQXList?XTYHID="+edID);
	});
	
	$("#systemAuthorize").click(function(){
	   var edID =$.trim($("#selRowId").val());  
			    if(!checkEid(edID))return;
                showDialog("toUserQXList?XTYHID="+edID);
	});
	$("#AuthorizeList").click(function(){
		 var edID =$.trim($("#selRowId").val());  
			    if(!checkEid(edID))return;
                showDialog("childList?XTYHID="+edID);
	});
	
	
	
	$("#begin").click(function(){
	    var xtyhid = $("input:radio[name='eid']:checked").val();
	    
	    $.ajax({
	        url:"updateUserStatus",
            type:"POST",
			dataType:"text",
			data:{"xtyhid":xtyhid,"flag":1},
			complete: function(xhr){
			    eval("jsonResult = "+xhr.responseText);
			    if(jsonResult.success===true){
					parent.showMsgPanel("更新成功");
					$("#pageForm").submit();
				}else{
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}	
				
			}
	    });
	  });
	    
	  $("#end").click(function(){
	    var xtyhid = $("input:radio[name='eid']:checked").val();
	    $.ajax({
	        url:"updateUserStatus",
            type:"POST",
			dataType:"text",
			data:{"xtyhid":xtyhid,"flag":2},
			complete: function(xhr){
			    eval("jsonResult = "+xhr.responseText);
			    if(jsonResult.success===true){
					parent.showMsgPanel("更新成功");
					$("#pageForm").submit();
				}else{
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}	
			}	    
	    });
	  });
	  
	  $("#defaultP").click(function(){
	    var xtyhid = $("input:radio[name='eid']:checked").val();
	    var actionUrl = "updatePassword";
	    var goUrl="toList";
	    showConfirm("您确认要设为默认密码吗?","mtbDelConfirm('"+actionUrl+"','"+xtyhid+"','"+xtyhid+"','"+goUrl+"');");
	  });

});	
	
function mtbDelConfirm(actionUrl,pkId,selRowId,goUrl){
	closeWindow();
	
	$.ajax({
	        url:actionUrl+"?xtyhid="+selRowId,
            type:"POST",
			dataType:"text",
			complete: function(xhr){
				eval("jsonResult = "+xhr.responseText);
			    if(jsonResult.success===true){
					parent.showMsgPanel("更新成功");
					window.parent.topcontent.location="toList";
				}else{
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
	    
	    });
	    
	
}

function checkEid(edID){		
			if(edID==null || ""==edID.trim()){
				showErrorMsg("请选择机构对象！");
				return false;
			}
			return true;
}

function changeButton(value){
  
   value=value.substring(0,2);
   btnReset();
   if("启用" == value ){
        
		btnDisable(["begin","modify","delete"]);
	}else if( "停用" == value ){
	    
		btnDisable(["end","delete"]);
	}else if( "制定" == value ){
	    btnDisable(["end"]);
	}
	$("#selYhzt").val(value);
}

var obj_chrg;
function beforeChangeChrg(obj,IS_FG_ALL_CHANN){
	 obj_chrg = obj;
	 var v = $(obj).val();
	 var msg = "您确认要分管所有渠道吗?";
	 if(0 == v || "0" == v){
		 msg = "您确认要取消已经分管的所有渠道吗?";
	 }
	 showConfirm(msg,"changeChrg();");
	 $("#YT_MSG_BTN_CANCLE").click(function(){
//		 if("" == IS_FG_ALL_CHANN || 0 == IS_FG_ALL_CHANN || "0" == IS_FG_ALL_CHANN){
			 $(obj).parent().find("input[value='"+IS_FG_ALL_CHANN+"']").attr("checked","checked");
//		 }
	 });
}

function changeChrg(obj){
	closeWindow();
	obj = obj_chrg;
	var v = $(obj).val();
	var xtyhid = $(obj).parent().parent().find("input:radio[name='eid']").val();
	$.ajax({
        url:"updateUserChrgChann",
        type:"POST",
		dataType:"text",
		data:{"xtyhid":xtyhid,"CHRG":v},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
		    if(jsonResult.success===true){
				parent.showMsgPanel("操作成功");
//				window.parent.topcontent.location="xtyh.shtml?action=toList";
//				parent.topcontent.$("#queryForm").submit();
				}else{
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
	    
	    });
	
}

function openWindow(){
	var XTYHID = $("input:radio[name='eid']:checked").val();
	myShowModalDialog("toStepFrame?XTYHID="+XTYHID,false,900,500);
}