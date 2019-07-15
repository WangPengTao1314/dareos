

/**
 *@module 分销业务
 *@func人员信息维护
 *@version 1.1
 *@author lyg
 */
$(function(){
	showBtnLook(null);
	InitFormValidator("mainForm");
	
	$("#bottomdiv").hover(
		function() {
		    $("#carType option[text='-请选择-']").remove();
	    }, 
	   function() {
		  $("#carType option[text='-请选择-']").remove();
	    }
	);
	
	$("#topDiv").hover(
		function() {
		    $("#carType option[text='-请选择-']").remove();
	    }, 
	   function() {
		  $("#carType option[text='-请选择-']").remove();
	    }
	);
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
		if(check()){
			childSave("add");
		}
	})
	//下单并提交
 	$("#commit").click(function(){
 		if(check() ){
	    	childSave("commit");
		}
 	})
	$("#delete").click(function(){
		//查找当前是否有选中的记录
		var checkBox = $("#jsontb tr:gt(0) input:checked");
		if(checkBox.length<1){
			showErrorMsg("请至少选择一条记录");
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
				showErrorMsg("非手工新增货品不可删除");
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
function getJson(){
	var CUST_NAME=$("#CUST_NAME").val();
	var CUST_TEL=$("#CUST_TEL").val();
	var CUST_ADDR=$("#CUST_ADDR").val();
	if(""==CUST_NAME||null==CUST_NAME){
		showErrorMsg("请填写客户姓名");
		return null;
	}
	if(""==CUST_TEL||null==CUST_TEL){
		showErrorMsg("请填写联系电话");
		return null;
	}
	if(""==CUST_ADDR||null==CUST_ADDR){
		showErrorMsg("请输入详细地址");
		return null;
	}
	return siglePackJsonData("channInfo");
}


function showBtnLook(obj){
	var checked = $(obj).attr("checked");
	if(checked){
		rmoveBtnDis(["look"]);
	}else{
		btnDisable(["look"]);
	}
}

function showPage(){
	window.open("carcalculate.shtml?action=listGoodsOrder&page_plag=1&notSend="+utf8("总部未发"),'总部未发货品查看','height=500, width=800, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
}
function selRevcAddr(){
	var CHANN_ID=$("#CHANN_ID").val();
	if(""==CHANN_ID||null==CHANN_ID){
		showErrorMsg("请先选择收货方信息")
		return;
	}
	$("#selectAddrParams").val(" DEL_FLAG=0 and STATE='启用' and CHANN_ID='"+CHANN_ID+"' ");
	selCommon('BS_76', false, 'RECV_ADDR_NO', 'DELIVER_ADDR_NO', 'forms[0]','RECV_ADDR_NO,RECV_ADDR', 'DELIVER_ADDR_NO,DELIVER_DTL_ADDR', '');
																																			//selectAddrParams
}


//下拉框选择的车型 从后台获取该车型的容积
//选择车型的时候 计算需要多少车   调用下帧的 方法
function changeVolumn(){
   $("#old_VOLUME").val("");//清空 然后在下帧设置值
   $("#MIN_VOLUME").val("");
   $("#MAX_VOLUME").val("");
   window.bottomcontent.getVolumFrom();
}

 
 function rmoveBtnDis(arrys){
	if(null==arrys || arrys.length<1){
		return;
	}
	for(var i=0; i<arrys.length; i++){
		$("#"+arrys[i]).removeAttr("disabled");
	}
 }
 function selStore(){
	var CHANN_ID=$("#CHANN_ID").val();
	if(""==CHANN_ID||null==CHANN_ID){
		showErrorMsg("请先选择收货方信息")
		return;
	}
	var html="STATE ='启用' and DEL_FLAG='0' and BEL_CHANN_ID='"+CHANN_ID+"' "
	$("#selectStore").val(html);
	selCommon('BS_35', false, 'STORE_ID', 'STORE_ID', 'forms[0]','STORE_ID,STORE_NO,STORE_NAME', 'STORE_ID,STORE_NO,STORE_NAME', 'selectStore');
 }
 function rebOp(){
	 window.bottomcontent.rebOp();
 }
  function larOp(){
	 window.bottomcontent.larOp();
 }
 function selRECV(){
	 var chann_id_old=$("#CHANN_ID").val();
	 selCommon('BS_19', false, 'CHANN_ID', 'CHANN_ID', 'forms[0]','CHANN_ID,CHANN_NO,CHANN_NAME,PERSON_CON,TEL,AREA_ID,AREA_NO,AREA_NAME', 'CHANN_ID,CHANN_NO,CHANN_NAME,PERSON_CON,TEL,AREA_ID,AREA_NO,AREA_NAME','selectParams');
	 var chann_id=$("#CHANN_ID").val();
	 if(chann_id_old!=chann_id){
		 $("#RECV_ADDR_NO").val("");
		 $("#RECV_ADDR").val("");
		 $("#STORE_ID").val("");
		 $("#STORE_NO").val("");
		 $("#STORE_NAME").val("");
	 }
 }
 

	 function changeinput(e){
			var newinput = document.getElementById('ipt')
			var newmydiv =document.getElementById('topDiv')
			if(newmydiv.style.display == 'none'){
				newmydiv.style.display = '';
				newinput.value="收起"
				imgsz.style.transform="rotate(0deg)"
			    
			}else{
				newmydiv.style.display='none';
				newinput.value = "展开";
				/*transform:rotate(180deg);*/
				imgsz.style.transform="rotate(180deg)"
		    }
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
	 				showMsgPanel("删除成功");
	 				$(obj).parent().parent().remove()
	 			}else{
	 				showErrorMsg(utf8Decode(jsonResult.messages));
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
	 	width = 550,
		height = 530;
	 	// 初始化默认值
	 	$("#jsontb").find("tr").eq(row).find(":input[name]").each(function(){
	 		console.log($(this).attr("name")+":" + $(this).val())
	 		obj[$(this).attr("name")]=$(this).val();
	 	})
	 	var url = "toDiv?param="+encodeURIComponent(JSON.stringify(obj));
	 	url = url.replaceAll("\"","'");
	 	//下拉框单独写
	 	layer.open({
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
	 	    	  var obj=window["layui-layer-iframe" + index].getOptPro();
	 	    	  for(var i in obj){
	 	    		  $("#jsontb").find(":input[name='"+i+"']").val(obj[i]);
	 	    		  if(i=="ATT_PATH"){
	 		    			break;
	 		    		}
	 		    		spec+=obj[i]+"  ";
	 	    	  }
	 	    	  $("#spec"+row).find("span").html(spec)
	 	    	  layer.close(index);
	 	      },
	 	      btn2:function(index,layero){
	 	      }
	 		});
	 }
	//验证
	 function check(){
	 	var checkBox = $("#jsontb tr:gt(0) input:checked");
	 		if(checkBox.length<1){
	 			showErrorMsg("请至少选择一条记录");
	 			return ;
	 		}
	 		var tab = true;
	 		checkBox.each(function(){
	 			var input = $(this).parent().parent().find("input[name='ORDER_NUM']").eq(0);
	 			var num = input.val();
	 			var label = input.attr("label");
	 			if(num==0||num==null||num==""){
	 				showErrorMsg("请输入'"+label+"'");
	 				tab=false;
	 				return false;
	 			}
	 			if(isNaN(num)){
	 				showErrorMsg("'"+label+"'输入不合法,请重新输入");
	 				tab=false;
	 				return false;
	 			}
	 			if(num.indexOf(".")>0){
	 				showErrorMsg("'"+label+"'只允许输入正整数");
	 				tab=false;
	 				return false;
	 			}
	 			var re1 = new RegExp(/^\d{0,8}$/);
	 	        if(!re1.test(num)){
	 	            showErrorMsg("'"+label+"'最多可输入8位正整数");
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
	 	if($("#rebate").prop("checked")){
	 		rebate = 1;
	 		REBATEDSCT = $("#REBATEDSCT").val();
	 	}
	 	if($("#larRebate").prop("checked")){
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
	 	var tabData = getJson();
	 	if(null == tabData || "" == tabData){
	 		return;
	 	}
	 	console.log(tabData)
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
	 				showMsgPanel("操作成功","window.location.reload()");
	 				//gotoBottomPage();
	 				$("#REBATEFLAG").val("");
	 			}else{
	 				showErrorMsg(utf8Decode(jsonResult.messages));
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
	 	$.ajax({
	 		url: "delete",
	 		type:"POST",
	 		dataType:"text",
	 		data:{"SHOP_CART_IDS":ids},
	 		complete: function(xhr){
	 			eval("jsonResult = "+xhr.responseText);
	 			if(jsonResult.success===true){
	 				showMsgPanel("删除成功");
	 				checkBox.parent().parent().remove();
	 			}else{
	 				showErrorMsg(utf8Decode(jsonResult.messages));
	 			}
	 		}
	 	});
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