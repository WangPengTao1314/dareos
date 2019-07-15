
/**
 *@module 发货管理
 *@func 发货指令维护编辑
 *@version 1.0
 *@author 王朋涛
 */
$(function(){
	//发货编号
	 if(!($("#send_order_no").val() == null && $("#send_order_no") == "")){
        $("#send_order_no").attr("readonly","readonly");
        $("#send_order_no").css("background-color","#cccccc");
     } 
	 //初始化校验
	 InitFormValidator(0);
	 if(!($("#SEND_ORDER_ID").val()=="")){
		 $("#imgID").attr("hidden","true"); 
	 }else{
		 
		// $("#jsontb_div").attr("hidden","true"); 
	 }
	//添加浮动按钮层的监听
	addFloatDivListener();
	//保存监听。参数1：请求Url，参数2：主键Id，参数3：上帧url.保存成功后需要刷新上帧，
	//mtbSaveListener("edit","sale_order_id","toList","mainForm");
		 $("#save").click(function(){
			  $("#save").attr("disabled");
			  //form表单数据校验
			   var parentCheckedRst = formChecked('mainForm');
				if (!parentCheckedRst) {
					return;
				}
				// 明细校验
				if (!formMutiTrChecked()) {
					return;
				}
				 var checkBox = $("#jsontb tr:gt(0) input[json=send_order_dtl_id]:checked");
				 if (checkBox.length < 1) {
					 parent.showErrorMsg("请至少选择一条记录");
					 return;
				 }
			 if(dataColumnsValidation("jsontb",['send_order_dtl_id'],"red")){
				 
			 		childSave();
			  }
		 });
		 
	 
	 
	$("#allChecked").click(function(){
		var flag = document.getElementById("allChecked").checked;
		if(flag){
			$("#jsontb :checkbox").attr("checked","true");
		}else{
			$("#jsontb :checkbox").removeAttr("checked");
		}
	});
	
	//
	/*$("#toAdd").click(function(){
		var selRowId2 = $("#SALE_ORDER_ID").val();
		setCommonPageInfo(false);
		parent.window.gotoBottomPage2("toEdit","SALE_ORDER_ID",selRowId2);
		 
	});*/
	
});
//通用选取回调函数
function endSelCommBack(){
	goEdit();
}
//子表保存
function childSave(){
	
	// 获取主表json数据
	var parentData = siglePackJsonData('jsontbP');
	var jsonDataMulti = multiPackJsonData("jsontb");
	$.ajax({
		url: "edit",
		type:"POST",
		dataType:"text",
		data:{"parentJsonData":parentData,"jsonDataMulti":jsonDataMulti,"SEND_ORDER_ID":$("#SEND_ORDER_ID").val(),"SALE_ORDER_ID":$("#SALE_ORDER_ID").val()},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanelCloseWindow('保存成功','parent.window.frames["topcontent"].document.getElementById("queryForm").submit()');
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

function getSelRowId(){
	return parent.document.getElementById("selRowId").value;
}

//保存之后跳转到编辑页面

function  saveChangeEdit(orderId){
	var send_id=$("#SEND_ORDER_ID").val();
	if((send_id=="")){
		//showMsgPanelCloseWindow('请编辑信息！！！','goEdit(send_id);');
		goEdit(orderId);
	}else{
		showMsgPanelCloseWindow('保存成功','parent.window.frames["topcontent"].document.getElementById("queryForm").submit();');
	}
	
	
}

function goEdit(){
	$("#mainForm").attr("action","toEdit");
	mainForm.submit();
}



function changeinput(e){
	var newinput = document.getElementById('ipt')
	var newmytable =document.getElementById('myTable')
	var new_img = document.getElementById('imgsz')  
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
function changeinput2(e){
	var newinput = document.getElementById('ipt')
	var newmytable =document.getElementById('jsontb')
	if(newmytable.style.display == 'none'){
		newmytable.style.display = '';
		newinput.value="收起"
		(imgsz).style.transform="rotate(0deg)"
	    
	}else{
		newmytable.style.display='none';
		newinput.value = "展开";
		/*transform:rotate(180deg);*/
		(imgsz).style.transform="rotate(180deg)"
		
	}
        
 }


function countPrice(rownum) {
	//可发货数量，发货数量不能大于可发货数量
	var WAITNUM = $("#WAITNUM" + rownum).val();//可发货数量
	
	var temp_ORDER_NUM = $("#SEND_NUM" + rownum).val();// 数量
	 
	var flag=parseFloat(temp_ORDER_NUM)>parseFloat(WAITNUM);
	if(flag){
		parent.showWarnMsg("发货数量不能大于可发货数量!!!");
		$("#SEND_NUM" + rownum).attr("value","");
		return;
	}
	
	var jsbj = isNaN($("#PRICE" + rownum).val()) ? 0 : parseFloat($("#PRICE" + rownum).val()); //计算报价
	var zkl = isNaN($("#DECT_RATE" + rownum).val()) ? 0 : parseFloat($("#DECT_RATE" + rownum).val());//折扣
	//var danjia = isNaN($("#DECT_PRICE" + rownum).val()) ? 0 : parseFloat($("#DECT_PRICE" + rownum).val());//最总报价
	// 判断输入是否为数字，如果不是数字则默认为0
	//var PRICE = isNaN(danjia) ? 0 : parseFloat(danjia); 
	var PRICE = Math.round((isNaN(jsbj * zkl) ? 0 : jsbj * zkl)) / 100;// 单价
	var ORDER_NUM = isNaN(temp_ORDER_NUM) ? 0 : parseFloat(temp_ORDER_NUM); // 数量
	// 总金额，保留2位小数
	var ORDER_AMOUNT = Math.round((isNaN(ORDER_NUM * PRICE) ? 0
			: ORDER_NUM * PRICE));
	$("#DECT_PRICE" + rownum).attr("value", PRICE);
	$("#SEND_AMOUNT" + rownum).attr("value", ORDER_AMOUNT);
}









