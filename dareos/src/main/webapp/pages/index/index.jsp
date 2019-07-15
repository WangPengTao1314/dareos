<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<html>
    <head>
        <title>圣象家居订单系统</title>
        <link rel="shortcut icon" href="${ctx}/favicon.ico">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
        <script type="text/javascript" src="${ctx}/scripts/core/jquery-1.8.min.js"></script>
<script language="JavaScript">
    
	var treeData = eval("(${treeData})");
	var rootMenu ='010000';
	IndexPage=function(){};
	IndexPage.prototype = {
	    showLeft : function() {
	        midFrame.cols="217,7,*";
	        sepFrame.imgSep.src="${ctx}/styles/${theme}/images/left/bs_left.jpg";
	    },
	    hideLeft : function() {
	        midFrame.cols="0,7,*";
	        sepFrame.imgSep.src="${ctx}/styles/${theme}/images/left/bs_right.jpg";
	    }
	};
	var indexPage=new IndexPage();
	function indexUrl(){
	    return '${ctx}/${indexUrl}';
	}
	function writeTopMenus() {
	    if(treeData.size > 0){
	        rootMenu=treeData.children[0].id;
	    }
	    var sb = [];
	    for (var i = 0, j = treeData.size; i < j; i++) {
	        var menu = treeData.children[i];
	        
	        sb[sb.length] = '<td id="td'+menu.id+'" align="center" valign="middle" style="width:8%;cursor:pointer;" class="menuactive" onclick="changeSelectMenu(\'' + menu.id + '\',\''+ menu.name +'\')">';
	        sb[sb.length] = '&nbsp;&nbsp;<b><a style ="color:#000;font-size:14px;font-weight:200;text-decoration:none;" href="#"';
	        sb[sb.length] = ' title="' + menu.name + '" ';
	        sb[sb.length] = '>' + menu.name + '</a></b>&nbsp;&nbsp;';
	        sb[sb.length] = '</td>';
	       
	  
	
	    }
	    
	    return sb.join("");
	};

	
	
	function wirteDate(){
		var weekDayLabels = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
		var now = new Date();
	    var year=now.getFullYear();
		var month=now.getMonth()+1;
		var day=now.getDate();
	    var currentime = year+"年"+month+"月"+day+"日 "+weekDayLabels[now.getDay()];
	    return currentime;
	};
	
	TreeCtl = function(menuId) {
	    this.menuId = menuId;
	};
	TreeCtl.prototype = {
	    showMenu : function() {
	    }
	};
  var treeCtl=new TreeCtl(rootMenu);
	
	
   window.onunload = onunload_handler;   
    function onunload_handler(){ 
        $.ajax({
				url: "login.shtml?action=destroyedSession",
				type:"POST",
				dataType:"text", 
				complete: function(xhr){
				 //alert("谢谢使用！");
				}
			}); 
    }   
	
     //分销门户 标记  add by zzb 2014-2-10
   function indexTop(){
	   return "${indexTop}";
   }
     
      //取当前用户消息数 add by zzb 2014-5-8
   function messageSize(){
      return  "${messageSize}";
   }
	
</script>
    </head>    
<frameset <c:if test="${indexTop eq 'drp' }">rows="50px,*" </c:if> <c:if test="${indexTop ne 'drp' }">rows="50px,*" </c:if>  cols="*" frameborder="no" border="0" framespacing="0">
     <frame src="${ctx}/pages/index/top.jsp"  name="topFrame" noresize scrolling="no" marginwidth="0" marginheight="0" id="topFrame"/>
 <%--  <frame <c:if test="${indexTop eq 'drp' }">src="${ctx}/pages/index/topdrp.jsp" </c:if> <c:if test="${indexTop ne 'drp' }">src="${ctx}/pages/index/top.jsp" </c:if>   name="topFrame" noresize scrolling="no" marginwidth="0" marginheight="0" id="topFrame"/>  --%>
     <frameset rows="*"  framespacing="0" frameborder="no" border="0" id="midFrame">
        <frame src="${ctx}/${indexUrl}" name="mainFrame" scrolling="No" noresize style="margin: 0px;" id="mainFrame"/>
     </frameset>
 </frameset>
<noframes><body></body></noframes>
</html>
