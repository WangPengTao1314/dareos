 /**
  *@module 系统管理
  *@fuc 送货地址信息js
  *@version 1.1
  *@author zzb
*/

$(function(){
	
 	init();//页面初始化
 	
	$("#add").click(function(){
	    addRow(); 
	});
	
	$("#clickAddRow").dblclick(function(){
	    addRow(); 
	});
	
	$("#delete").click(function(){
		//查找当前是否有选中的记录
		var checkBox = $("#jsontb tr:gt(0) input:checked");
		if(checkBox.length<1){
			parent.showErrorMsg("请至少选择一条记录");
			return;
		}
		//获取所有选中的记录
		var ids = "";
		checkBox.each(function(){
			if("" != $(this).val()){
				ids = ids+"'"+$(this).val()+"',";
			}
		});
		ids = ids.substr(0,ids.length-1);
		//没有ids说明都是页面新增的，数据库中无记录，可以直接remove
		if("" == ids){
			checkBox.parent().parent().remove();
		}else{
			parent.showConfirm("您确认要删除吗","bottomcontent.multiRecDeletes();");
		}
		//删除后scroll可能消失，因此需要重新调整浮动按钮的位置
		setFloatPostion();
	});
	
	//保存按钮
	$("#save").click(function(){
		childSave();
	});
	
	$("#allChecked").click(function(){
		var flag = document.getElementById("allChecked").checked;
		if(flag){
			$("#jsontb :checkbox").attr("checked","true");
		}else{
			$("#jsontb :checkbox").removeAttr("checked");
		}
	});
});

 
//子表保存
function childSave(){
    //查找当前是否有选中的记录
	var checkBox = $("#jsontb tr:gt(0) input:checked");
	//获取所有选中的记录
	var selRowId = getSelRowId();
	var jsonData = multiPackJsonData();
	$.ajax({
		url: "insertLegerChrg",
		type:"POST",
		dataType:"text",
		data:{"childJsonData":jsonData,"XTYHID":selRowId},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanel('保存成功','window.location="ledgerChrgList?XTYHID='+selRowId+'"');
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

//删除操作
function multiRecDeletes(){
//查找当前是否有选中的记录
	var checkBox = $("#jsontb tr:gt(0) input:checked");
	//获取所有选中的记录
	var ids = "";
	checkBox.each(function(){
		ids = ids+"'"+$(this).val()+"',";
	});
	ids = ids.substr(0,ids.length-1);
	var selRowId = getSelRowId();
	$.ajax({
		url: "delLedChrgByLedIds",
		type:"POST",
		dataType:"text",
		data:{"XTYH_LEDGER_IDS":ids,"XTYHID":selRowId},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("删除成功");
				checkBox.parent().parent().remove();
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}
	
				
//table增加一行
 
function addRow(arrs){
	if(null == arrs){
		arrs = ['','','','','',''];//添加字段的时候必须添加
	}
	var selRowId = parent.document.getElementById("selRowId").value;
	//上级数据项选取规则，新增时，选取该字典下任意节点，如果该字典下无明细，则不选
	//修改时，选取该字典下非自己的任一节点，不可与已有的数据项值重复。
	//样式行   
	var rownum = $("#jsontb tr").length;
	var classrow = rownum% 2;
	rownum=_row_num;_row_num++;
	
	var html="";
	$("#jsontb tr:last-child").after("<tr class='list_row"+classrow+"' onmouseover='mover(this)' onmouseout='mout(this)'></tr>");
	
		    html+="<td nowrap align='center'><input type='checkbox' style='height:12px;valign:middle' style='width:100%' json='XTYH_LEDGER_ID' name='XTYH_LEDGER_ID"+rownum+"' value='"+arrs[0]+"'/></td>"+
		    			'<td nowrap align="left">'+
		    			'<input  json="LEDGER_ID" id="LEDGER_ID'+rownum+'"    name="LEDGER_ID"     type="hidden"   inputtype="string"   maxlength="30"  value="'+arrs[1]+'"/>&nbsp;'+
		    			'<input  json="LEDGER_NAME" id="LEDGER_NAME'+rownum+'"  style="width:85%" readonly="readonly" name="LEDGER_NAME"   autocheck="true" mustinput="true" label="帐套名称"  type="text"   inputtype="string"   maxlength="30"  value="'+arrs[2]+'"/>&nbsp;';
		    if(arrs[0]==""){
		    	html+='<img name="imgTab" align="absmiddle" style="cursor: hand" src="' + ctxPath + '/styles/newTheme/images/plus/select.gif" onClick=\'selLedger(' + rownum + ')\'/>'
		    }
		    html+=	'</td>'+
			'<td nowrap align="left">'+
			'<input  json="LEDGER_NAME_ABBR" id="LEDGER_NAME_ABBR'+rownum+'"  style="width:85%" readonly="readonly" name="LEDGER_NAME_ABBR"   autocheck="true" mustinput="true" label="简称"  type="text"   inputtype="string"   maxlength="30"  value="'+arrs[3]+'"/>&nbsp;';
	    	html+=	'</td>';
    $("#jsontb tr:last-child").append(html);
	$("#jsontb tr:last-child").find("td:gt(0)").click(function(){
		$(this).parent().find("input[type='checkbox']").attr("checked","checked");
	});
	//form校验设置
	InitFormValidator(0);
}

function selLedger(rownum){
	selCommon("BS_28", true, "LEDGER_ID" + rownum, "ZTBH", "forms[0]", "LEDGER_ID"
			+ rownum + ",LEDGER_NAME" + rownum+",LEDGER_NAME_ABBR"+rownum ,
			"ZTBH,ZTMC,ZTJC", "con");
}
 


function getSelRowId(){
	return parent.document.getElementById("selRowId").value;
}
function init(){
	$("#jsontb tr").each(function(){
		$(this).find("td:gt(0)").click(function(){
			$(this).parent().find("input[type='checkbox']").attr("checked","checked");
		});
	});
	$("#jsontb tr:gt(0) :checkbox").attr("checked","true");
	
	
	//form校验设置
	InitFormValidator(0);
	//添加浮动按钮层的监听
	addFloatDivListener();
}

function gobacknew(){
	var channId = getSelRowId();
	 window.location="ledgerChrgList?XTYHID="+channId; 
}




