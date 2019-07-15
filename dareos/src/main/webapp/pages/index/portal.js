/**
 * @prjName:
 * @fileName:分销首页
 * @author 
 * @time  
 * @version 
 */

$(function(){	
    
});

function showNoticeContent(obj){
	var NOTICE_ID = $(obj).parent().parent().find("input[name='NOTICE_ID']").val();
	window.showModalDialog(ctxPath + '/sys/erpFirst/toNotice&NOTICE_ID='+NOTICE_ID,'','dialogwidth=950px; dialogheight=600px; status=no');
	//window.open('erpFirpage.shtml?action=toNotice&NOTICE_ID='+NOTICE_ID,'','height=950,width=600');
}

function closeNoticeContent(){
	$("#notice_content").hide();
}
//清空背景色
function clearColor(){
	$("#ul_notice li").each(function(){
		var color = $(this).css("background-color");
		if("#ffefd3" == color ){
			$(this).css("background-color","#F8F7F2")
		}
	});
	
}

/**
 * 待处理订单\待入库\待退货
 */
function queryCount(){
	$.ajax({
	url: ctxPath + "/sys/erpFirst/queryErpCount",
	type:"POST",
	dataType:"json",
	data:{},
	complete: function(xhr){
		eval("jsonResult = "+xhr.responseText);
		if(jsonResult.success===true){
			var data = jsonResult.data;
			 if(null !=data && ""!=data){
				$("#good_count").text(data.Goods);//未处理的订货订单
			    $("#turnoverplan_count").text(data.turnoverplan);//制定交付计划
			    $("#turnoverhd_count").text(data.turnoverhd);
			    $("#pdtdeliver_count").text(data.pdtdeliver);
			    $("#deliverconfm_count").text(data.deliverconfm);
			    $("#techAudit_count").text(data.techAudit);
			    $("#techPrice_count").text(data.techPrice);
			    
			 }
			 
		}
	}
});
	 
}

//公告信息列表
function listNotice(){
	var mainFrame = window.top.mainFrame; 
	var url ='../../sys/notice/listNotice';
	 mainFrame.addTab('XT0304','通知公告',url);
}
function queryNotice(NOTICE_ID){
	var mainFrame = window.top.mainFrame;  
	var url ='../../sys/notice/queryNoticeById?NOTICE_ID='+NOTICE_ID;
	/**
	 * @param menuID:菜单标识ID
	 * @param menuName:菜单名称
	 * @param menuUrl：菜单页面路径
	 * @returns
	 */
    mainFrame.addTab('XT030402','通知公告',url);
}
//公共的代办列表
function toDoList(menuID,menuName,menuUrl,state){
	if(!(menuUrl.indexOf("?module")>=0)){
		menuUrl ='../../'+menuUrl+'paramUrl=&HEAD=H';
	}else{
		menuUrl ='../../'+menuUrl+'&paramUrl=&HEAD=H';
	}
    var mainFrame = window.top.mainFrame;  
    mainFrame.addTab(menuID,menuName,menuUrl);
}
function CommonFunc(menuID,menuName,menuUrl){
	var mainFrame = window.top.mainFrame;  
    mainFrame.addTab(menuID,menuName,'../../'+menuUrl);
}
function loadChart(){
	var ztxxs=$("#ztxxs").val();
    var ledgerid="";
    if(ztxxs.indexOf("116") != -1){
    	ledgerid="116";
    }else{
	    ledgerids=ztxxs.split();
	    if(ledgerids.length>0){
	    	ledgerid=ledgerids[0];
	    }
    }
	ledger_Name(ledgerid);  	
}
function ledger_Name(ledger_id){
	if(ledger_id ==null){
		ledger_id ="";
	}
	//var mainFrame = window.top.mainFrame; 
	$("#ledger113").css("background","#1583e9");
	$("#ledger116").css("background","#1583e9");
	$("#ledger10801").css("background","#1583e9");
	$("#ledger107").css("background","#1583e9");
	$("#ledger121").css("background","#1583e9");
	$("#ledger"+ledger_id).css("background","#f75c12");
	
	$.ajax({
		url: "sys/first/goFirst?LEDGER_ID="+ledger_id,
		type:"GET",
		success: function(xhr){
			   $('#maintwo').html(xhr);
			   $('#main').html(xhr);
		}
	});	 
}






