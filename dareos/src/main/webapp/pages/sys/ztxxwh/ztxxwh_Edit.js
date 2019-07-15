 /**

 *@module 财务管理

 *@fuc 帐套信息维护编缉页面

var status;
$(document).ready(function(){
    $("#ZTLB").attr("value",$("#ZTLB1").val());
    status = $("#ctrType").val();   
    if('modify' == status){    
       
       $("#ZTBH").attr("readonly","true");
      
    }
    mtbSaveListener("ztxx.shtml?action=toSave","ZTXXID","ztxx.shtml?action=toList","mainForm");
   
   
});*/
$(function(){
	//初始化校验
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
	
    $("#ZTLB").attr("value",$("#ZTLB1").val());
	
	var selId = parent.document.getElementById("selRowId").value;
	if(null == selId || "" == selId){
		var treeNodes = parent.leftcontent.getSelNodes();
		//modify by zhuxw
		//old version
		//if("" != treeNodes){
		if(treeNodes!=null&&treeNodes.id!=null){
			$("#SJZT").val(treeNodes.id);
			var jgbhname = treeNodes.name;
			var index = jgbhname.indexOf("(");
			//$("#SJJGBH").val(jgbhname.substring(0,index));
			$("#SJZTMC").val(jgbhname.substring(index+1,jgbhname.length-1));
		}
	}
	
	$("#save").click(function(){
		if(!formChecked('mainForm')){
			return;
		}
		var jsonData = siglePackJsonData();
		$.ajax({
			url: "toSave",
			type:"POST",
			dataType:"text",
			data:{"jsonData":jsonData,"ZTXXID":selId},
			complete: function(xhr){
				eval("jsonResult = "+xhr.responseText);
				if(jsonResult.success===true){
					var ZTXXID = utf8Decode(jsonResult.messages);
					//上帧页面跳转至编辑后的记录
//					window.parent.topcontent.location="toList?ZTXXID="+ZTXXID+parent.window.reqParamsEx();
					//左边树节点变更
					var sjzt = $("#SJZT").val();
					if(null == selId || "" == selId){//新增记录
						var newNode = {id:ZTXXID,
									   name:$("#ZTBH").val()+"("+$("#ZTMC").val()+")", 
									   pId:sjzt};
						parent.leftcontent.addTreeNodes(sjzt,newNode);
					}else{//修改记录
						parent.leftcontent.updateNodes(selId,$("#ZTBH").val()+"("+$("#ZTMC").val()+")");
					}
					showMsgPanelCloseWindow('保存成功','parent.window.frames["topcontent"].document.getElementById("queryForm").submit()');
				}else{
					showWarnMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	});
});
/*function formCheckedEx(){
          if($("#ZTBH").val().length < 4){
		      parent.showErrorMsg(utf8Decode("帐套编号请输入四位！"));
		      //flag = false;
	           return false;
		   } 
	
	       return true;
}*/
