
/**
 *@module 分销业务
 *@func购物车
 *@version 1.1
 *@author lyg
 */
$(function(){
	headColumnSort("jsontb","listForm");
	//添加浮动按钮层的监听
	addFloatDivListener();
	var REBATEFLAG=$("#REBATEFLAG").val();
	if("1"==REBATEFLAG){
		$("#rebate").prop("checked",true);
	}else{
		$("#rebate").prop("checked",false);
	}
	$("#allChecked").click(function(){
		var flag = document.getElementById("allChecked").checked;
		if(flag){
			$("#jsontb :checkbox").attr("checked","true");
		}else{
			$("#jsontb :checkbox").removeAttr("checked");
		}
		
	});
	//下单
	$("#addPrd").click(function(){
		if(check() && judgeRebate()){
			childSave("add");
		}
	})
	//下单并提交
 	$("#commit").click(function(){
 		if(check() && judgeRebate()){
	    	childSave("commit");
		}
 	})
	$("#delete").click(function(){
		//查找当前是否有选中的记录
		var checkBox = $("#jsontb tr:gt(0) input:checked");
		if(checkBox.length<1){
			parent.showErrorMsg("请至少选择一条记录");
			return;
		}
		var tab=false;
		//获取所有选中的记录
		var ids = "";
		checkBox.each(function(){
			if("" != $(this).val()){
				ids = ids+"'"+$(this).val()+"',";
			}
			var type=$(this).parent().parent().find("td[name='type']").html();
			if("预订单转订货"==type){
				tab=true;
				parent.showErrorMsg("非手工新增货品不可删除");
				return;
			}
		});
		if(tab){
			return;
		}
		ids = ids.substr(0,ids.length-1);
		//没有ids说明都是页面新增的，数据库中无记录，可以直接remove
		if("" == ids){
			checkBox.parent().parent().remove();
		}else{
			showConfirm("您确认要删除吗","multiRecDeletes();");
		}
		//删除后scroll可能消失，因此需要重新调整浮动按钮的位置
		setFloatPostion();
	});
	
 
 
});
//验证
function check(){
	var checkBox = $("#jsontb tr:gt(0) input:checked");
		if(checkBox.length<1){
			parent.showErrorMsg("请至少选择一条记录");
			return ;
		}
		var tab = true;
		checkBox.each(function(){
			var input = $(this).parent().parent().find("input[name='ORDER_NUM']").eq(0);
			var num = input.val();
			var label = input.attr("label");
			if(num==0||num==null||num==""){
				parent.showErrorMsg("请输入'"+label+"'");
				tab=false;
				return false;
			}
			if(isNaN(num)){
				parent.showErrorMsg("'"+label+"'输入不合法,请重新输入");
				tab=false;
				return false;
			}
			if(num.indexOf(".")>0){
				parent.showErrorMsg("'"+label+"'只允许输入正整数");
				tab=false;
				return false;
			}
			var re1 = new RegExp(/^\d{0,8}$/);
	        if(!re1.test(num)){
	            parent.showErrorMsg("'"+label+"'最多可输入8位正整数");
	            tab=false;
				return false;
	        }
		});
		return tab;
}



function childSave(type){
	var checkBox = $("#jsontb tr:gt(0) input:checked");
	
	//返利
	var rebate = 0;
	var REBATEDSCT = "";//返利折扣
	//赠品
	var LARGESSFLAG = 0;
	var LARGESSDSCT = "";//赠品折扣
	if(parent.$("#rebate").prop("checked")){
		rebate = 1;
		REBATEDSCT = $("#REBATEDSCT").val();
	}
	if(parent.$("#larRebate").prop("checked")){
		LARGESSFLAG = 1;
		LARGESSDSCT = $("#LARGESSDSCT").val();
	}
	var ids = "";
	checkBox.each(function(){
		if("" != $(this).val()){
			ids = ids+"'"+$(this).val()+"',";
		}
	});
	ids = ids.substr(0,ids.length-1);
	var tabData = parent.getJson();
	if(null == tabData || "" == tabData){
		return;
	}
 	//主表的 交期 取 明细最近的交期  如：2014-7-8和 2014-7-9，取2014-7-8的插到表里
    var ORDER_RECV_DATE =  getORDER_RECV_DATE();
    var jsonData = multiPackJsonData();
    $.ajax({
		url: "edit",
		type:"POST",
		dataType:"text",
		data:{"jsonData":jsonData,"type":type,"tabData":tabData,/*"rebate":rebate,*/
    	"SHOP_CART_IDS":ids,"ORDER_RECV_DATE":ORDER_RECV_DATE,"REBATEDSCT":REBATEDSCT,"LARGESSFLAG":LARGESSFLAG,"LARGESSDSCT":LARGESSDSCT},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("操作成功");
				setTimeout('listForm.submit()',100);
				//parent.gotoBottomPage();
				parent.$("#rebate").prop("checked",false);
				$("#REBATEFLAG").val("");
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

 function toFlow(i,GOODS_ORDER_ID) {
	 var cutid = GOODS_ORDER_ID;
	document.affirm.id.value = cutid;
    document.affirm.sourceURI.value=ctxPath+"/toList"+document.getElementById("paramUrl").value ;
	switch (Number(i)) {
	  case 1:
		affirmEvent(0);
		break;//提交
	  case 2:
		affirmEvent(1);
		break;//审核
	  case 3:
		affirmEvent(2);
		break; //撤销
	  case 4:
		showHistory(cutid);
		break;//撤销
	}
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
	$.ajax({
		url: "delete",
		type:"POST",
		dataType:"text",
		data:{"SHOP_CART_IDS":ids},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("删除成功");
				checkBox.parent().parent().remove();
				//设置删除操作。有删除时返回后需要刷新页面。否则直接跳转上一页。
				$("#delFlag").val("true");
				$("#listForm").submit();
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

/**
 * 数量值改变时计算价格
 */
function countAmount(row){
	var num =$("#ORDER_NUM"+row).val();
	if(isNaN(num)||num==""){
		$("#ORDER_NUM"+row).val(1);
		num=1
	}
	num = parseFloat(num)
	var price = parseFloat($("#price"+row).html());
	var amount= Math.round((price*num)*100)/100;
	$("#amount"+row).html(amount);
	$("#tr"+row).find("input[name='ORDER_AMOUNT']").val(amount);
}

//鼠标按下订货数量时,计算金额,总体积
function showImg(obj,id){
	var fileName = $(obj).attr("src");
	
	/**
	 * 获取图片大小
	 * var pic=$("#"+id);
	 * var theImage = new Image();
	 * theImage.src = pic.attr("src");
	 * var imageWidth = theImage.width;
	 * var imageHeight = theImage.height;
	 */
	window.open("toPicture?fileName="+fileName,'货品信息图片','height=500, width=800, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
}





function urlshow(SPCL_TECH_ID,PRICE){
	window.open('techorderManager.shtml?action=viewTechWithPrice&acqModel=1&SPCL_TECH_ID='+SPCL_TECH_ID+'&PRICE='+PRICE,'特殊工艺单维护','height=600, width=800, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
}
function hide(){
	if($("#showOrHide").val()=="↑"){
		parent.$("#topDiv").hide();
		$("#showOrHide").val("↓");
		parent.$("#bottomdiv").css({height:'100%'});
	}else{
		parent.$("#topDiv").show();
		$("#showOrHide").val("↑");
		parent.$("#bottomdiv").css({height:'73%'});
	}
}



//主表的 交期 取 明细最近的交期  如：2014-7-8和 2014-7-9，取2014-7-8的插到表里
function getORDER_RECV_DATE(){
	var checkBox = $("#jsontb tr:gt(0) input:checked");
	var inputs = $("#jsontb").find("input[json='ORDER_RECV_DATE']");
	var bigTime = "";
	var resultDate = "";
	checkBox.each(function(){
		var date = $(this).parent().parent().find("input[json='ORDER_RECV_DATE']").val();
		date = $.trim(date);
		if(null != date && "" !=date){
			var arr = date.split("-");
	        var starttime = new Date(arr[0], arr[1], arr[2]);
	        var starttimes = starttime.getTime();
	        if(null == bigTime || "" == bigTime){
	        	 bigTime = starttimes;
	        	 resultDate = date;
	        }else if(bigTime>starttimes){
	        	bigTime = starttimes;
	        	resultDate = date;
	        }
		}
        
	});
	return resultDate;
}


function pitchChecked(id){
	$("#eid"+id).prop("checked",true);
}


//点中返利按钮时，判断是否有返利金额，如果没有，给予提示，不让选中
function rebOp(){
	var rebate = parent.$("#rebate").prop("checked");
	var larRebate = parent.$("#larRebate").prop("checked");
	if(larRebate&&rebate){
		parent.showErrorMsg(utf8Decode("使用赠品折扣时不能使用返利折扣！"));
		parent.$("#rebate").removeAttr("checked");
		return;
	}
	var REBATEDSCT=$("#REBATEDSCT").val();
	if(rebate&&(""==REBATEDSCT||null==REBATEDSCT||0==REBATEDSCT)){
		parent.showErrorMsg(utf8Decode("总部未设置您的返利折扣，不能使用返利金额，请联系总部！"));
		parent.$("#rebate").removeAttr("checked");
		return;
	}
	if(rebate){
		$("#REBATEFLAG").val("1");
		$("#listForm").submit();
	}else{
		$("#REBATEFLAG").val("0");
		$("#listForm").submit();
	}
}

//点击使用赠品折扣判断是否已选中返利折扣和是否有赠品折扣
function larOp(){
	var rebate = parent.$("#rebate").prop("checked");
	var larRebate = parent.$("#larRebate").prop("checked");
	if(larRebate&&rebate){
		parent.showErrorMsg(utf8Decode("使用返利折扣时不能使用赠品折扣！"));
		parent.$("#larRebate").removeAttr("checked");
		return;
	}
	var LARGESSDSCT=$("#LARGESSDSCT").val();
	if(larRebate&&(""==LARGESSDSCT||null==LARGESSDSCT||0==LARGESSDSCT)){
		parent.showErrorMsg(utf8Decode("总部未设置您的赠品折扣，不能使用赠品订货，请联系总部！"));
		parent.$("#larRebate").removeAttr("checked");
		return;
	}
	if(larRebate){
		$("#LARGESSFLAG").val("1");
		$("#listForm").submit();
	}else{
		$("#LARGESSFLAG").val("0");
		$("#listForm").submit();
	}
}


//判断返利金额是否足够
function judgeRebate(){
	var checked = $("#rebate").attr("checked");
	if("checked" == checked){
		var total = 0;
		var REBATEDSCT = $("#REBATEDSCT").val();
		var REBATE_CURRT = $("#REBATE_CURRT").val();
		var checkBox = $("#jsontb tr:gt(0) input:checked");
		checkBox.each(function(){
			var PRICE = $(this).parent().parent().find("td[name='BASE_PRICE']").text();
			var ORDER_NUM = $(this).parent().parent().find("input[json='ORDER_NUM']").val();
			PRICE = parseFloat($.trim(PRICE));
			ORDER_NUM = parseInt($.trim(ORDER_NUM));
			if(!isNaN(PRICE) && !isNaN(ORDER_NUM)){
				total = parseFloat(total) + PRICE*REBATEDSCT*ORDER_NUM;
			}
		}); 
		
		if(total>REBATE_CURRT){
			parent.showErrorMsg("返利金额不足，不能使用返利下单");
			return false;
		} 
	}
	return true;
}

//购物车类型查询
function changeListPage(obj){
	var SHOP_CART_TYPE_ = $("#SHOP_CART_TYPE_").val();
	if(null == SHOP_CART_TYPE_){
		SHOP_CART_TYPE_ = "";
	}
	$("#SHOP_CART_TYPE").val(SHOP_CART_TYPE_);
	$("#listForm").submit();
}
 

 

//图片加载不出来时，显示默认图片
function checkImag(obj){
	var picture_url = $("#picture_url").val();
	$(obj).attr("src",picture_url+"dafult.jpg");
	 
}


function changeTextArea(obj) {
	var upper = obj.className;
	if("uppercase"==upper){
		obj.value=obj.value.toUpperCase();
	}
	var maxL = obj.maxlength || obj.maxLength;
	var L = stringLength(obj.value);
	if (L > maxL) {
		obj.value = obj.oVal;
	} else {
		obj.oVal = obj.value;
	}
}

function delShopCar(id,obj){
	$.ajax({
		url: "delete",
		type:"POST",
		dataType:"text",
		data:{"SHOP_CART_IDS":"'"+id+"'"},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("删除成功");
				$(obj).parent().parent().remove()
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}

/**
 * 弹出规格信息
 */
function openSpec(ele,row){
	var obj = {};
	var
	width = 350;
	height = 350;
	// 初始化默认值
	$("#jsontb").find("tr").eq(row).find(":input[name]").each(function(){
		console.log($(this).attr("name")+":" + $(this).val())
		obj[$(this).attr("name")]=$(this).val();
	})
	
	var url = "toDiv?param="+JSON.stringify(obj);
	url = url.replaceAll("\"","'");
	obj["LEDGER_ID"]=$($("#ledgerQuery").find(".bgshow")[0]).attr("opt");
	//下拉框单独写
	parent.layer.open({
	      type: 2,
	      title: false,
	      maxmin: false,// 最大最小化
	      shadeClose: false, //点击遮罩关闭层
	      resize: false,//是否可拉伸
	      content: url,
	      offset: 'auto',
	      area : [width+'px' , height+'px'],
	      btn:["确定","取消"],
	      yes:function(index,layero){
	    	  var spec="";
	    	  var obj=parent.window["layui-layer-iframe" + index].getOptPro();
	    	  for(var i in obj){
	    		  $("#jsontb").find(":input[name='"+i+"']").val(obj[i]);
	    		  if(i=="ATT_PATH"){
		    			break;
		    		}
		    		spec+=obj[i]+"  ";
	    	  }
	    	  $("#spec"+row).find("span").html(spec)
	    	  parent.layer.close(index);
	      },
	      btn2:function(index,layero){
	      }
		});
}


