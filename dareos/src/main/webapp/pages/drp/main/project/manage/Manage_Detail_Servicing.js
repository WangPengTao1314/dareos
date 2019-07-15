 
/**
 * @module 系统管理
 * @func 货品套
 * @version 1.1
 * @author 刘曰刚
 */

 //固定的一些共通的方法 begin
$(function(){
    //页面初始化
    init();
	$("#add").click(function(){
		 
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
			//parent.showConfirm("您确认要删除吗","");
			multiRecDeletes2();
		}
		//删除后scroll可能消失，因此需要重新调整浮动按钮的位置
		setFloatPostion();
	});
	
	
	$("#save").click(	function() {
		// 查找当前是否有选中的记录
		var checkBox = $("#jsontb tr:gt(0) input[json=project_payable_id]:checked");
		//if (checkBox.length < 1) {
		//	parent.showErrorMsg("请至少选择一条记录");
		//	return;
		//}
		// 对于选中的明细校验
		if (!formMutiTrChecked()) {
			return;
		}
		//
		allSave();
	});
	function allSave() {
		// 主表form校验
		var parentCheckedRst = parent.topcontent.formChecked('mainForm');
		if (!parentCheckedRst) {
			btuMxRest();
			return;
		}
		// 明细校验
		if (!formMutiTrChecked()) {
			btuMxRest();
			return;
		}
		var selRowId = document.getElementById("selRowId").value;
		//console.log('selRowId:', selRowId);
		
		// 获取主表json数据
		var parentData = siglePackJsonData('jsontbP');
		// console.log('parentData', parentData);

		var childData;
		if ($("#jsontb tr:gt(0) input[json=project_payable_id]:checked").length > 0) {
			childData = multiPackJsonData();
		}
		$.ajax({
			url : "mxEdit",
			type : "POST",
			async : false,
			dataType : "json",
			data : {
				"parentJsonData" : parentData,
				"childJsonData" : childData,
				"project_id" : selRowId
			},
			complete : function(xhr) {
				eval("jsonResult = " + xhr.responseText);
				if (jsonResult.success === true) {
					showMsgPanelCloseWindow('保存成功','parent.window.frames["topcontent"].document.getElementById("queryForm").submit()');
				} else {
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
	}
	
	$("#allChecked").click(function(){
		var flag = document.getElementById("allChecked").checked;
		if(flag){
			$("#jsontb :checkbox").attr("checked","true");
		}else{
			$("#jsontb :checkbox").removeAttr("checked");
		}
	});
	
	 
});


//删除操作
function multiRecDeletes2(){
   //查找当前是否有选中的记录
	var checkBox = $("#jsontb tr:gt(0) input:checked");
	//获取所有选中的记录
	var ids = "";
	checkBox.each(function(){
		ids = ids+"'"+$(this).val()+"',";
	});
	ids = ids.substr(0,ids.length-1);
	
	$.ajax({
		url: "mxDelete",
		type:"POST",
		dataType:"text",
		data:{"PROJECT_PAYABLE_ID":ids},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("删除成功");
				checkBox.parent().parent().remove();
				//设置删除操作。有删除时返回后需要刷新页面。否则直接跳转上一页。
				$("#delFlag").val("true");
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

function init(){
	$("#jsontb tr").each(function(){
		$(this).find("td:gt(0)").click(function(){
			$(this).parent().find("input[type='checkbox']").attr("checked","checked");
		});
	});
	//var actionType = parent.document.getElementById("actionType").value;
    $("#jsontb tr:gt(0) :checkbox").attr("checked","true");
	//form校验设置
	InitFormValidator(0);
}

function getActionType(){
	return parent.document.getElementById("actionType").value;
}

function getSelRowId(){
	return parent.document.getElementById("selRowId").value;
}
//table增加一行
 
// 固定的一些共通的方法 end
// 下面这个方法要自己修改
//表格增加一行
function addRow(arrs){
	var soDtlEmpty = $("#soDtlEmpty").html();
	if (soDtlEmpty!=null || soDtlEmpty !='') {
		$("#soDtlEmpty").remove();
	}
	if(null == arrs){
     arrs = [ '',
              '',
              '',
              '',
              '',
              '',
              '',
              $("#selRowId").val(),
              '',
              '',
              '',
              '',
              '',
              ''
	        ];
		}
	//样式行
	var rownum =  $("#jsontb tr").length;
	var classrow = rownum% 2;
	 rownum=_row_num;_row_num++;
	$("#jsontb tr:last-child").after("<tr class='list_row"+classrow+"' onmouseover='mover(this)' onmouseout='mout(this)'></tr>");
	$("#jsontb tr:last-child")
	    .append("<td nowrap align='center'><input  type='checkbox' style='height:12px;valign:middle' id='PROJECT_PAYABLE_ID"+rownum+"' json='project_payable_id' name='PROJECT_PAYABLE_ID' value='"+arrs[0]+"' checked></td>")
	    .append('<td nowrap align="center"><input  size="6" style="text-align: center;width:100%;"   json="payable_stage" id="PAYABLE_STAGE'+rownum+'" name="PAYABLE_STAGE"  inputtype="string"  autocheck="true" label="收款阶段"   type="text"  mustinput=""      value="'+arrs[1]+'"/>&nbsp;' +"</td>")
	    .append('<td nowrap align="center"><input size="6"  style="text-align: center;width:100%;"  json="payable_condition" id="PAYABLE_CONDITION'+rownum+'" name="PAYABLE_CONDITION'+rownum+'"  inputtype=""   autocheck="true"   label="收款条件"    type="text"  mustinput=""       value="'+arrs[2]+'"/>&nbsp;</td>')
	   
	    .append('<td nowrap align="center"><input size="10"  style="text-align: center;width:100%;"  json="request_amount_date" id="REQUEST_AMOUNT_DATE'+rownum+'" name="REQUEST_AMOUNT_DATE'+rownum+'" onclick="SelectDate();"  inputtype="string"   autocheck="true"   label="请款时间" placeholder="请选择时间"     type="text"  mustinput=""       value="'+arrs[8]+'"/>&nbsp;</td>')
	    .append('<td nowrap align="center"><input size="10"  style="text-align: center;width:100%;"  json="invoice_date" id="INVOICE_DATE'+rownum+'" name="INVOICE_DATE'+rownum+'" onclick="SelectDate();" inputtype="string"   autocheck="true"   label="开票时间"  placeholder="请选择时间"   type="text"  mustinput=""       value="'+arrs[9]+'"/>&nbsp;</td>')
	    .append('<td nowrap align="center"><input size="10"  style="text-align: center;width:100%;"  json="invoice_amount" id="INVOICE_AMOUNT'+rownum+'" name="INVOICE_AMOUNT'+rownum+'"  inputtype="int"   autocheck="true"   label="开票金额"    type="text"  mustinput=""       value="'+arrs[10]+'"/>&nbsp;</td>')
	    .append('<td nowrap align="center"><input size="10"  style="text-align: center;width:100%;"  json="back_amount_date" id="BACK_AMOUNT_DATE'+rownum+'" name="BACK_AMOUNT_DATE'+rownum+'" onclick="SelectDate();"  inputtype="string"   autocheck="true"   label="汇款时间" placeholder="请选择时间"     type="text"  mustinput=""       value="'+arrs[11]+'"/>&nbsp;</td>')
	    .append('<td nowrap align="center"><input size="8"  style="text-align: center;width:100%;"  json="back_amount" id="BACK_AMOUNT'+rownum+'" name="BACK_AMOUNT'+rownum+'"  inputtype="int"   autocheck="true"   label="回款金额"    type="text"  mustinput=""       value="'+arrs[12]+'"/>&nbsp;</td>')
	    .append('<td nowrap align="center"><input size="8"  style="text-align: center;width:100%;"  json="out_prd_amount" id="OUT_PRD_AMOUNT'+rownum+'" name="OUT_PRD_AMOUNT'+rownum+'"  inputtype="int"   autocheck="true"   label="出货金额"    type="text"  mustinput=""       value="'+arrs[13]+'"/>&nbsp;</td>')
	    
	    .append('<td nowrap align="center"><input size="10"  style="text-align: center;width:100%;"  json="payable_date" id="PAYABLE_DATE'+rownum+'" name="PAYABLE_DATE'+rownum+'" onclick="SelectDate();"  inputtype="string"   autocheck="true"   label="收款日期"   placeholder="请选择时间"   type="text"  mustinput=""       value="'+arrs[3]+'"/>&nbsp;</td>')
	    .append('<td nowrap align="center"><input size="8"  style="text-align: center;width:100%;"  json="payable_rate" id="PAYABLE_RATE'+rownum+'" name="PAYABLE_RATE'+rownum+'"  inputtype="string"   autocheck="true"   label="收款比例"    type="int"  mustinput=""       value="'+arrs[4]+'"/>&nbsp;</td>')
	    .append('<td nowrap align="center"><input size="8"  style="text-align: center;width:100%;"  json="payable_amount" id="PAYABLE_AMOUNT'+rownum+'" name="PAYABLE_AMOUNT'+rownum+'"  inputtype="int"   autocheck="true"   label="收款金额"    type="text"  mustinput=""       value="'+arrs[5]+'"/>&nbsp;</td>')
	    .append('<td nowrap align="center"><input size="8"  style="text-align: center;width:100%;"  json="reality_amount" id="REALITY_AMOUNT'+rownum+'" name="REALITY_AMOUNT'+rownum+'"  inputtype="int"   autocheck="true"   label="实收金额"    type="text"  mustinput=""       value="'+arrs[6]+'"/>&nbsp;</td>')
	    .append('<input  json="project_id" hidden="true" id="PROJECT_ID'+rownum+'" name="PROJECT_ID'+rownum+'"  inputtype="string"   autocheck="true"   label="项目id"    type="text"  mustinput=""       value="'+arrs[7]+'"/>')
	      ;
	$("#jsontb tr:last-child").find("td:gt(0)").click(function(){
		$(this).parent().find("input[name='PROJECT_PAYABLE_ID']").attr("checked","checked");
	});
	 
}
	
	//tab标签页
/*	$(document).ready(function() {
		$("#content div").hide();  
		$("#tabs li:eq(0)").attr("id","current");  
		$("#tabs li:eq(0)").addClass("active");
		$("#content div:eq(0)").fadeIn();  
		//
		 var state=$("#STATE").val();
		 $('#tabs a').click(function(e) {
			e.preventDefault();        
			$("#content div").hide();  
			$("#tabs li").attr("id",""); 
			$("#tabs li").removeClass("active"); 
			$(this).parent().attr("id","current");
			$(this).parent().addClass("active");
			//if($(this).text()==state){
				$('#' + $(this).attr('title')).fadeIn();  
			//}
		}); 
	});
 */
	$(document).ready(function() {
		
		$("#content div").hide();  
		$("#tabs li:eq(0)").addClass("active");
		$("#content div:eq(0)").fadeIn();  
		var state=$("#STATE").val();
		 $('#tabs a').click(function(e) {
			e.preventDefault();        
			$("#content div").hide();  
			$("#tabs li").removeClass("active");
			$(this).parent().addClass("active");
				$('#' + $(this).attr('title')).fadeIn();  
		}); 
	});
	
	function changeinput(e){
		var newinput = document.getElementById('ipt')
		var newmytable =document.getElementById('myTable')
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

/*$(function() {
	debugger
		bsStep(5);
	})*/
	