
/**
 *@module 分销业务
 *@func人员信息维护
 *@version 1.1
 *@author lyg
 */
$(function(){
	
	$("#query").click(function(){
		var parId = '';
		var tree=parent.window.leftcontent;
		if(tree.getSelNodesId){
			parId=tree.getSelNodesId();
		}
		var prdInfo = $("#prdInfo").val();
		if('' == parId && '' == prdInfo){
			parent.showWarnMsg("请输入"+$("#prdInfo").attr('placeholder'));
		} else {
			queryPrd(parId);
		}
	});
	
	$("#allChecked").click(function(){
		allChecked()
	});

});

/**
 * 动态获取货品列表
 */
function queryPrd(parId){
	var prdInfo = $("#prdInfo").val();
	var ledger_id = getLedgerId();
	$.ajax({
		url: "getPrdList",
		type:"POST",
		dataType:"json",
		data: {prdInfo: prdInfo, ledger_id: ledger_id, parPrdId: parId},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				var list = JSON.parse(jsonResult.messages);
				
				//删除多余行
				$('#ordertb tr:gt(0)').remove();
				//判断是否有数据
				if(list.length==0){
					$("#ordertb").append(
								'<tr>'+
									'<td height="25" colspan="16" align="center" class="lst_empty" >'+
										'无相关记录'+
									'</td>'+
								'</tr>'
								);
					return;
				}
				
				// 遍历显示货品信息
				var html="";
				for(var i=0;i<list.length;i++){
					html+='<tr id="tr'+i+'" class="list_row'+(i%2)+'" onmouseover="mover(this)" onmouseout="mout(this)"'+
									' onclick="trClick(\'chk'+udfToEmpty(list[i].PRD_ID)+'\')"'+
							'>'+
								'<td  align="center" >'+
								'<input type="checkbox" attr-id="'+udfToEmpty(list[i].PRD_ID)+
									'" attr-no="'+udfToEmpty(list[i].PRD_NO)+
									'" attr-name="'+udfToEmpty(list[i].PRD_NAME)+
									'" attr-spec="'+udfToEmpty(list[i].PRD_SPEC)+
									'" attr-brand="'+udfToEmpty(list[i].BRAND)+
									'" attr-unit="'+udfToEmpty(list[i].STD_UNIT)+
									'" attr-price="'+udfToEmpty(list[i].PRVD_PRICE)+
									'" attr-parName="'+udfToEmpty(list[i].PAR_PRD_NAME)+
									'" class="'+udfToEmpty(list[i].PRD_ID)+
									'" name="chk'+udfToEmpty(list[i].PRD_ID)+
									'" id="chk'+udfToEmpty(list[i].PRD_ID)+
									'" onclick="checkBoxClick(\'chk'+udfToEmpty(list[i].PRD_ID)+'\')"/>'+
								'</td>'+
								'<td nowrap="nowrap" align="center">'+
								udfToEmpty(list[i].PRD_NO)+
								'</td>'+
								'<td nowrap="nowrap" align="center">'+
								udfToEmpty(list[i].PRD_NAME)+
								'</td>'+
								'<td nowrap="nowrap" align="center">'+
								udfToEmpty(list[i].PRD_SPEC)+
								'</td>'+/*
								'<td nowrap="nowrap" align="center">'+
								udfToEmpty(list[i].PRD_COLOR)+
								'</td>'+
								'<td nowrap="nowrap" align="center">'+
								udfToEmpty(list[i].BRAND)+
								'</td>'+*/
								'<td nowrap="nowrap" align="center">'+
								udfToEmpty(list[i].STD_UNIT)+
								'</td>'+/*
								'<td nowrap="nowrap" align="center">'+
								udfToEmpty(list[i].PAR_PRD_NO)+
								'</td>'+*/
								'<td nowrap="nowrap" align="center">'+
								udfToEmpty(list[i].PAR_PRD_NAME)+
								'</td>'+
							'</tr>';
				}
				$("#ordertb").append(html);
				// 勾选已选中货品
				for(var i =0;i<selectArr.length;i++){
					checkBoxClick('chk' + selectArr[i].PRD_ID);
				}
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

function checkBoxClick(id){
	var oObj = eval("document.all('"+id+"')");
	$(oObj).prop("checked", function(index, attr){
		return !attr;
	});
}
function trClick(id){
	var oObj = eval("document.all('"+id+"')");
	$(oObj).prop("checked", function(index, attr){
		return !attr;
	});
	selProd(id);
}
//定义所有选中的数据
var selectArr = []
function selProd(id){
	var oObj = eval("document.all('"+id+"')");

	var chk = $(oObj);
	var data = {
		PRD_ID:     chk.attr('attr-id')   
		,PRD_NO:     chk.attr('attr-no')   
		,PRD_NAME:   chk.attr('attr-name') 
		,PRD_SPEC:   chk.attr('attr-spec') 
		,BRAND:      chk.attr('attr-brand')
		,STD_UNIT:   chk.attr('attr-unit') 
		,PRVD_PRICE: chk.attr('attr-price')
		,PAR_PRD_NAME:chk.attr('attr-parName')
	};
	
	if(oObj.checked){
		addElement(data);
	}else{
		delElement(data);
	}
}

//添加已选操作
function addRow(data) {
	$('.no_data').remove();
	// 样式行
	var rownum = $("#jsontb tr").length;
	// console.log(rownum)
	var classrow = rownum % 2;
	rownum = _row_num;
	_row_num++;

	$("#jsontb tr:last-child").after('<tr id="tr'+rownum+'" class="invoice" onmouseover="mover(this)" onmouseout="mout(this)" >');
	$("#jsontb tr:last-child").append('');
	var html='<td style="display: none;">'+
				'<input type="checkbox" name=""  value="'+udfToEmpty(data.PRD_ID)+'"/>'+
				
				'<input type="checkbox" attr-id="'+udfToEmpty(data.PRD_ID)+
					'" attr-no="'+udfToEmpty(data.PRD_NO)+
					'" attr-name="'+udfToEmpty(data.PRD_NAME)+
					'" attr-spec="'+udfToEmpty(data.PRD_SPEC)+
					'" attr-brand="'+udfToEmpty(data.BRAND)+
					'" attr-unit="'+udfToEmpty(data.STD_UNIT)+
					'" attr-price="'+udfToEmpty(data.PRVD_PRICE)+
					'" attr-parName="'+udfToEmpty(data.PAR_PRD_NAME)+
					'" class="'+udfToEmpty(data.PRD_ID)+
					'" name="sel'+udfToEmpty(data.PRD_ID)+
					'" id="sel'+udfToEmpty(data.PRD_ID)+
					'" />'+
			'</td>'+
			'<td nowrap="nowrap" align="center">'+
			udfToEmpty(data.PRD_NO)+
			'</td>'+
			'<td nowrap="nowrap" align="center">'+
			udfToEmpty(data.PRD_NAME)+
			'</td>'+
			'<td nowrap="nowrap" align="center">'+
			udfToEmpty(data.PRD_SPEC)+
			'</td>'+
			'<td nowrap="nowrap" align="center">'+
			udfToEmpty(data.STD_UNIT)+
			'</td>'+
			'<td nowrap="nowrap" align="center">'+
			udfToEmpty(data.PAR_PRD_NAME)+
			'</td>'+
			'<td nowrap="nowrap" align="center">'+
				'<button class="del_input" id="del'+data.PRD_ID+'" onclick="removeRow(\'sel'+udfToEmpty(data.PRD_ID)+'\')">移除</button>'+
			'</td>';
	$("#jsontb tr:last-child").append(html);
}

//移除按钮
function removeRow(id){
	var oObj = eval("document.all('"+id+"')");

	var chk = $(oObj);
	var data = {
		PRD_ID:     chk.attr('attr-id')   
		,PRD_NO:     chk.attr('attr-no')   
		,PRD_NAME:   chk.attr('attr-name') 
		,PRD_SPEC:   chk.attr('attr-spec') 
		,BRAND:      chk.attr('attr-brand')
		,STD_UNIT:   chk.attr('attr-unit') 
		,PRVD_PRICE: chk.attr('attr-price')
		,PAR_PRD_NAME:chk.attr('attr-parName')
	};
	
	delElement(data);
}

//移除已选操作
function removeProd(e,id){
	console.log($('.'+id+''))
	$('.'+id+'').removeAttr("checked");
	$(e).parent().parent().remove()
	for(var i =0;i<selectArr.length;i++){
		if(selectArr[i].PRD_ID === id){
			selectArr.splice(i,1);
		}
	}
	if(selectArr.length==0){
		$("#jsontb").append(
					'<tr class="no_data">'+
						'<td height="25" colspan="16" align="center" class="lst_empty" >'+
							'暂无数据'+
						'</td>'+
					'</tr>'
					);
		return;
	}
}
//全选操作
function allChecked(){
	var flag = document.getElementById("allChecked").checked;
	if(flag){
		$("#jsontb :checkbox").attr("checked","true");
		var checkBox = $("#jsontb tr:gt(0) input:checked");
		checkBox.each(function(){
			var id=$(this).attr("attr-id")
			var type = $(this).attr("attr-name")
			var name = $(this).attr("attr-type")
			var obj = {id:id,type:type,name:name}
			selectArr.push(obj)	
			addRow()
		});
	}else{
		$("#jsontb :checkbox").removeAttr("checked");
	}
	
};

	function addElement(data){
		selectArr.push(data);
		addRow(data);

		var keyNames = parent.obj.selProdArgs[4].split(",");
		for(var i=0;i<keyNames.length;i++){
			if(parent.keyElemValues[i].length==0){
				parent.keyElemValues[i]=new Array();
			}
			if(!(parent.keyElemValues[i] instanceof Array)) {
				$("#chgArrToChar").val(parent.keyElemValues[i]);
				var tmp=$("#chgArrToChar").val();
				parent.keyElemValues[i]=tmp.split(",");
			}
			//console.log(eval("data."+keyNames[i]));
			parent.keyElemValues[i].push(eval("data."+keyNames[i]));
			console.log('keyElemValues['+i+']', parent.keyElemValues[i]);
		}
		var selCommFields = parent.obj.selProdArgs[7].split(",");
		for(var i=0;i<selCommFields.length;i++){
			if(parent.selCommElemValues[i].length==0){
				parent.selCommElemValues[i]=new Array();
			}
			if(!(parent.selCommElemValues[i] instanceof Array)) {
				$("#chgArrToChar").val(parent.selCommElemValues[i]);
				var tmp=$("#chgArrToChar").val();
				parent.selCommElemValues[i]=tmp.split(",");
			}
			parent.selCommElemValues[i].push(eval("data."+selCommFields[i]));
			//console.log('selCommElemValues['+i+']', parent.selCommElemValues[i]);
		}

	}

	function delElement(data){
		removeProd('#del'+data.PRD_ID, data.PRD_ID);

		var keyNames = parent.obj.selProdArgs[4].split(",");
		var selCommFields = parent.obj.selProdArgs[7].split(",");
		var j = 0;
		for(j=0;j<parent.keyElemValues[0].length;j++){
			var matchedFlag = false;
			for(var n=0;n<keyNames.length;n++){
				var continueFlag = false;
				var tmpValue = eval("data."+keyNames[n]);
				if(parent.keyElemValues[n][j]==tmpValue){
					continueFlag = true;
				}
				if(!continueFlag) break;
				if(n==keyNames.length-1) matchedFlag=true;
			}
			if(matchedFlag) break;
		}
		
		for(var i=0;i<keyNames.length;i++){
			var tmpArr = new Array();
			var n = 0;
			for(var k=0;k<parent.keyElemValues[i].length;k++){
				if(k!=j) {
					tmpArr[n++] = parent.keyElemValues[i][k];
				}
			}
			parent.keyElemValues[i] = tmpArr;
			console.log('keyElemValues['+i+']', parent.keyElemValues[i]);
		}
		
		for(var i=0;i<selCommFields.length;i++){
			var tmpArr = new Array();
			var n=0;
			for(var k=0;k<parent.selCommElemValues[i].length;k++){
				if(k!=j) {
					tmpArr[n++] = parent.selCommElemValues[i][k];
				}
			}
			parent.selCommElemValues[i] = tmpArr;
		
		}
	}
	function clickAll(){
		var tmpObject = targetTable.children[0].children;
		for(var i=0;i<tmpObject.length;i++)	{
			tmpObject[i].click();
		}
	}

//确定方法
function select_save(){
	 /*此数据为最终的数据list*/
	 var lg = selectArr.length
	 if(selectArr.length === 0){
		 parent.showErrorMsg("请选择数据");
		 return;
	 }else{
		 parent.showErrorMsg("选了"+selectArr.length+'条数据');
		 window.parent.data=selectArr
		 return lg
	 }
}
//undefined转空字符串
function udfToEmpty(str){
	return str?str:"";
}


function getLedgerId(){
	return parent.document.getElementById("ledger_id").value;
}