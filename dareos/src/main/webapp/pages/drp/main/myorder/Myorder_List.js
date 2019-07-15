
/**
 *@module 分销业务
 *@func人员信息维护
 *@version 1.1
 *@author lyg
 */
$(function(){
	headColumnSort("jsontb");
	//hideDiv();
	$("#selShopCar").click(function (){
		window.open(ctxPath+'/drp/shopcar/toInfo','购物车','height=800, width=1200, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
	})
	
	//默认查询条件
	// 默认选中第一条数据
	$("#ledgerQuery").find("a[class='active']").each(function(){
		bgshow(this);
		refPrdTree(this);
		getSeriesQueryInfo($(this).attr("opt"));
		queryPrd();
		return false;
	})
});

function bgshow(e){
	 window.event.preventDefault()
	 $(e).addClass('bgshow')
	 if($(window.event.target).siblings().hasClass('bgshow')){
		 $(window.event.target).siblings().removeClass('bgshow')
	 }
}
function savePrd(row){
	var num = $("#ORDER_NUM"+row).val();
	var tab=true;
	if(num==0||num==null||num==""){
		alert("请输入订货数量");
		tab=false;
		return false;
	}
	if(isNaN(num)){
		alert("订货数量输入不合法,请重新输入");
		tab=false;
		return false;
	}
	if(num.indexOf(".")>0){
		alert("订货数量只允许输入正整数");
		tab=false;
		return false;
	}
    var re1 = new RegExp(/^\d{0,8}$/);
    if(!re1.test(num)){
       alert("订货数量最多输入八位正整数");
       tab=false;
		return false;
    }
    if(tab){
		childSave(row);
	}
}

//加入购物车
function childSave(row){
	var LEDGER_ID = $($("#ledgerQuery").find(".bgshow")[0]).attr("opt");
	var LEDGER_NAME = $($("#ledgerQuery").find(".bgshow")[0]).html();
	var tr=$("#tr"+row);
	var obj = {
			PRD_ID: tr.find("input[name='PRD_ID']").val(),
			ORDER_NUM: tr.find("input[name='ORDER_NUM']").val(),
			PRICE: tr.find("input[name='PRICE']").val(),
			ORDER_AMOUNT:tr.find("input[name='ORDER_AMOUNT']").val(),
			HOLE_SPEC: tr.find("input[name='HOLE_SPEC']").val(),
			PRD_QUALITY: tr.find("input[name='PRD_QUALITY']").val(),
			PRD_TOWARD: tr.find("input[name='PRD_TOWARD']").val(),
			PRD_GLASS: tr.find("input[name='PRD_GLASS']").val(),
			PRD_OTHER: tr.find("input[name='PRD_OTHER']").val(),
			PRD_SERIES: tr.find("input[name='PRD_SERIES']").val(),
			ATT_PATH: tr.find("input[name='ATT_PATH']").val(),
			PRD_SIZE: tr.find("input[name='PRD_SIZE']").val(),
			REMARK: tr.find("input[name='REMARK']").val(),
			PRD_COLOR: tr.find("input[name='PRD_COLOR']").val(),
			PROJECTED_AREA: tr.find("input[name='PROJECTED_AREA']").val(),
			EXPAND_AREA: tr.find("input[name='EXPAND_AREA']").val()
	}
	$.ajax({
		url: "edit",
		type:"POST",
		dataType:"text",
		data:{"jsonData": JSON.stringify(obj),"LEDGER_ID":LEDGER_ID,"LEDGER_NAME":LEDGER_NAME},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				showMsgPanel("添加成功",'$("#pageForm").submit();');
				// 添加购物车的物品数量返回默认值1，重新计算价格
//				$("#ORDER_NUM"+row).val(1);
//				countAmount(row)
//				parent.clickBut();
				
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
}
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


//图片加载不出来时，显示默认图片
function checkImag(obj){
	var picture_url = $("#picture_url").val();
	$(obj).attr("src",picture_url+"dafult.jpg");
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
	if(isNaN(price)||!price){
		price=0;
	}
	var amount= Math.round((price*num)*100)/100;
	$("#amount"+row).html(amount);
	$("#tr"+row).find("input[name='ORDER_AMOUNT']").val(amount);
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
	$("#jsontb").find("tr").eq(row+1).find(":input[name]").each(function(){
		obj[$(this).attr("name")]=$(this).val();
	})
	obj["LEDGER_ID"]=$($("#ledgerQuery").find(".bgshow")[0]).attr("opt");
	var url = ctxPath + "/drp/shopcar/toDiv?param="+encodeURIComponent(JSON.stringify(obj));
	url = url.replaceAll("\"","'");
	//下拉框单独写
	parent.layer.open({
	      type: 2,
	      title: '规格设置',
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
	    		  $("#jsontb").find("tr").eq(row+1).find(":input[name='"+i+"']").val(obj[i]);
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

function refPrdTree(e){
	//树
	 parent.$("#leftcontent").attr("src", "showTree?LEDGER_ID="+$(e).attr("opt"));
}

//根据帐套名称动态获取系列数据字段
function getSeriesQueryInfo(LEDGER_ID){
	$.ajax({
		url: "getSeriesList?LEDGER_ID="+LEDGER_ID,
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				var obj = JSON.parse(jsonResult.messages);
				var html="";
				for(var i =0;i<obj.length;i++){
					html+='<a href="" class="active"	onclick="bgshow(this);queryPrd()" opt="'+obj[i].SJXZ+'">'+obj[i].SJXMC+'</a> ';
				}
				$("#seriesQuery").html(html);
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	  });
}
/**
 * 动态获取货品列表
 */
var resObj;
function queryPrd(page){
	//获取选中树节点
	var tree=parent.window.leftcontent;
	var PAR_PRD_ID="";
	if(tree.getSelNodesId){
		PAR_PRD_ID=parent.window.leftcontent.getSelNodesId();
	}
	var prdInfo = $("#prdInfo").val();
	var ledger = $($("#ledgerQuery").find(".bgshow")[0]).attr("opt");
	var series = $($("#seriesQuery").find(".bgshow")[0]).attr("opt");
	if(!series){
		series="";
	}
	if(!page){
		page="";
	}
	$.ajax({
		url: "getPrdList?prdInfo="+prdInfo+"&ledger="+ledger+"&series="+series+"&parPrdId="+PAR_PRD_ID+page,
		type:"POST",
		dataType:"text",
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				resObj = JSON.parse(jsonResult.messages);
				console.log(resObj);
				//给from查询条件赋值
				$("#pageForm").find("#pageSize").val(resObj.pageSize);
				$("#pageForm").find("#pageNo").val(resObj.pageNo);
				$("#pageForm").find("#oldPageNo").val(resObj.pageNo);
				//生成分页
				var pageHtml=resObj.toolbarHtml;
				pageHtml = pageHtml.substring(0,pageHtml.indexOf('<script'));
				$("#pageHtml").html(pageHtml);
				var list = resObj.result;
				
				//删除多余行
				$('#jsontb tr:gt(0)').remove();
				//判断是否有数据
				if(list.length==0){
					$("#jsontb tr:gt(1)").append(
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
					html+='<tr id="tr'+i+'" class="list_row$'+(i%2)+'" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selectThis(this);setEidChecked(document.getElementById(\'eid'+i+'\'));">'+
								  '<td width="5%" nowrap="nowrap" align="center">'+
								  udfToEmpty(list[i].ATT_PATH)+
				                  '</td>'+
				                  '<td width="35%" nowrap="nowrap" align="center">'+
				                  udfToEmpty(list[i].PRD_NAME)+
								  '</td>'+
				                  '<td  width="35%" id="spec'+i+'" align="center" style="word-wrap:break-word;word-break:break-all;">'+
				                  		'<span>'+
				                  		udfToEmpty(list[i].HOLE_SPEC) + '&nbsp;'+
				                  		udfToEmpty(list[i].PRD_SIZE) + '&nbsp;'+
				                  		udfToEmpty(list[i].PRD_QUALITY) + '&nbsp;'+
				                  		udfToEmpty(list[i].PRD_COLOR) + '&nbsp;'+
				                  		udfToEmpty(list[i].PRD_TOWARD) + '&nbsp;'+
				                  		udfToEmpty(list[i].PRD_GLASS) + '&nbsp;'+
				                  		udfToEmpty(list[i].PRD_OTHER) + '&nbsp;'+
				                  		udfToEmpty(list[i].PRD_SERIES) + '&nbsp;'+
										udfToEmpty(list[i].HOLE_SPEC) + '&nbsp;'+
										udfToEmpty(list[i].PROJECTED_AREA) + '&nbsp;'+
										udfToEmpty(list[i].EXPAND_AREA) +
										'</span>'+
				                  	'<input type="button" value="..."  style="background-color:#199ed8; border:none; width:26px; height:26px;float:right" onclick="openSpec(this,'+i+')"></input>'+
				                  '</td>'+
				                  '<td width="5%" nowrap="nowrap" align="center" >'+
				                  udfToEmpty(list[i].STD_UNIT)+
				                  '</td>'+
//				                  '<td width="5%" nowrap="nowrap"  align="center" id="price'+i+'">'+
//				                  udfToEmpty(list[i].PRICE)+
//				                  '</td>'+
				                  '<td width="5%" nowrap="nowrap" align="center">'+
				                  	'<input  type="text" id="ORDER_NUM'+i+'" json="ORDER_NUM"  name="ORDER_NUM" value="1" '+
//				                  	' onkeyup="countAmount('+i+')" '+  
				                  	'	style="text-align:center;width: 80px;" />'+
				                  '</td>'+
//				              		'<td width="5%" id="amount'+i+'" align="center">'+
//				              		udfToEmpty(list[i].ORDER_AMOUNT)+
//				              		'</td>'+
						          '<td  width="5%" align="center">'+
						          		'<a href="#" onclick="countAmount('+i+ ');savePrd('+i+')">'+
						          		'<img src="'+ctxPath+'/styles/'+theme+'/images/main/shop.png"></a>'+
						          	'<input type="hidden" name="ORDER_AMOUNT" json="ORDER_AMOUNT" value="'+udfToEmpty(list[i].ORDER_AMOUNT)+'" />'+
						          	'<input type="hidden" name="SHOP_CART_ID" json="SHOP_CART_ID" value="'+udfToEmpty(list[i].SHOP_CART_ID)+'" />'+
						          	'<input type="hidden" name="PRD_ID" value="'+udfToEmpty(list[i].PRD_ID)+'"/>'+
//							          '<input type="hidden" name="PRICE" value="'+udfToEmpty(list[i].RET_PRICE_MIN)+'"/>'+
							          '<input type="hidden" name="HOLE_SPEC" value="'+udfToEmpty(list[i].HOLE_SPEC)+'"/>'+
							          '<input type="hidden" name="PRD_QUALITY" value="'+udfToEmpty(list[i].PRD_QUALITY)+'"/>'+
							          '<input type="hidden" name="PRD_TOWARD" value="'+udfToEmpty(list[i].PRD_TOWARD)+'"/>'+
							          '<input type="hidden" name="PRD_GLASS" value="'+udfToEmpty(list[i].PRD_GLASS)+'"/>'+
							          '<input type="hidden" name="PRD_OTHER" value="'+udfToEmpty(list[i].PRD_OTHER)+'"/>'+
							          '<input type="hidden" name="PRD_SERIES" value="'+udfToEmpty(list[i].PRD_SERIES)+'"/>'+
							          '<input type="hidden" name="PRD_SIZE" value="'+udfToEmpty(list[i].PRD_SIZE)+'"/>'+
							          '<input type="hidden" name="ATT_PATH" value="'+udfToEmpty(list[i].ATT_PATH)+'" />'+
							          '<input type="hidden" name="REMARK" value="'+udfToEmpty(list[i].REMARK)+'" />'+
							          '<input type="hidden" name="PRD_COLOR" value="'+udfToEmpty(list[i].PRD_COLOR)+'"/>'+
							          '<input type="hidden" name="PROJECTED_AREA" value="'+udfToEmpty(list[i].PROJECTED_AREA)+'" />'+
							          '<input type="hidden" name="EXPAND_AREA" value="'+udfToEmpty(list[i].EXPAND_AREA)+'" />'+
						          '</td>'+
							    '</tr>';
				}
				$("#jsontb").append(html);
			}else{
				showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	  });
}
//undefined转空字符串
function udfToEmpty(str){
	return str?str:"";
}
//重写分页方法
function _gotopagecho(page){
	listForm.pageNo.value = page;
	var t = $("#pageForm").serializeArray();
	//拼参数
	var obj="";
	for(var i =0;i<t.length;i++){
		obj+="&"+t[i].name+"="+t[i].value;
	}
	queryPrd(obj);
}
function _gotopagenext(){
	if(resObj.pageNo>=resObj.pages){ 
		if(parent.showErrorMsg){
			parent.showErrorMsg('已经是最后一页!');
			return;
		}else{
			showErrorMsg('已经是最后一页!');
			return;
		}
	}
	_gotopagecho(resObj.pageNo+1);
	
}
function _gotopageahead() {
	if (resObj.pageNo <= 1) {
		if (parent.showErrorMsg) {
			parent.showErrorMsg('已经是第一页!');
			return;
		} else {
			showErrorMsg('已经是第一页!');
			return;
		}
	}
	_gotopagecho(resObj.pageNo-1);
}
