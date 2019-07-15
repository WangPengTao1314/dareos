<%@ page language="java" pageEncoding="UTF-8"%> 
<%@page import="com.centit.sys.model.UserBean,java.util.Map"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
 UserBean userBean = (UserBean) session.getAttribute("UserBean");
 String userName=userBean.getXM();
 String ztInfo=userBean.getLoginZTMC();
 %>
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/top.css" />
 <link rel="stylesheet" href="${ctx}/scripts/layui/css/layui.css"  media="all">
<script type="text/javascript" src="${ctx}/scripts/core/jquery-1.8.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/layer/layer.js"></script>
<style type="text/css">
 
.bs_right_low {
    background-repeat: no-repeat;
    position: absolute;
    right: 26px;
    top: 28px;
}
img {
	border: 0px currentColor;
} 
.thStyle{
    display: inline-block;
    position: relative;
    right:3px;
    font-size: 20px;
    font-weight: 600;
    letter-spacing: 2px;
    margin-left: 5px;
}
.img_sty{
	    width: 1px;
    position: relative;
    right: 2px;
    height: 12px;
}
#setPwd{
	cursor: pointer
}
#loginOut{
	cursor: pointer
}
.left_Info:hover {
    background: #0073c5;
    /* border: 1px solid #9e1212; */
    border-radius: 2px;
    box-shadow: 0 0 10px #b3a9a9;
}
</style>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class ="navbar">
	   <tr> 
	   		<!-- <td align="left" >
	   			<span style="padding-left:5%;color:#fff;font-size:12px"><script language="javascript">
	   				var weekDayLabels = new Array("星期日","星期一","星期二","星期三","星期四","星期五","星期六");
					var now = new Date();
				    var year=now.getFullYear();
					var month=now.getMonth()+1;
					var day=now.getDate();
				    var currentime = year+"年"+month+"月"+day+"日 "+weekDayLabels[now.getDay()];
	   				document.write(currentime);
	   			</script></span>
	   		</td> -->
	   		
	   	<td align="left"  id='erp_logo' nowrap="nowrap" style="width:3%">
		     <img src="${ctx}/pages/index/image/elephant_white.png" style="margin-left:12px;height: 40px;"></img>${indexTop}
		</td>
		<td><h1 class="thStyle">圣象家居订单管理系统</h1></td>
		<td  align="right">
		<div style="position: relative;right:3%;" >		
			<img align="top" src="${ctx}/styles/${theme}/images/icon/user.png"></img>			
			<span id="user" style="position: absolute;" ><%=userName %>&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- <a href="#" id="message"><img id="pic3" style="margin-bottom:5px;"></img></a> -->
			</span>
			<span style="position: relative;top: 11px;"><%=ztInfo %>&nbsp;&nbsp;</span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<img align="top" id="setPwd" src="${ctx}/styles/${theme}/images/icon/password.png" class="left_Info" title="修改密码"></img>
				<img align="top" id="loginOut" src="${ctx}/styles/${theme}/images/icon/exit.png" class="left_Info" onclick="loginOuta()" title="注销" ></img>	
	            </div>
		</td>
	   </tr>
</table>
</body>
<script type="text/javascript">
	/* layui.use('element', function(){
	  var element = layui.element; 
	  
	  //监听导航点击
	  element.on('nav(demo)', function(elem){
	    //console.log(elem)
	    layer.msg(elem.text());
	  });
	}); */

   $(function(){
      //$("#pic1").attr("background","${ctx}/styles/${theme}/images/top/logo_all.gif");
      //$("#pic2").attr("background","${ctx}/styles/${theme}/images/top/bs_bannercenter_new.jpg");
      $("#pic3").attr("src","${ctx}/styles/${theme}/images/top/nonews.gif"); 
      $("#pic4").attr("src","${ctx}/styles/${theme}/images/top/zx.gif");
      $("#pic5").attr("src","${ctx}/styles/${theme}/images/top/gb.gif");
      $("#pic6").attr("background","${ctx}/styles/${theme}/images/top/top5.png");
      $("#pic7").attr("src","${ctx}/styles/${theme}/images/top/modpwd.gif");
      var messageSize = parent.messageSize();
      if(messageSize>=1){
    	 // $("#messageSize").text("("+messageSize+")");
      }
      
    });
	
	 function loginOuta(){
		 parent.window.location.href = "${ctx}/pages/index/logout.jsp";
	 }
   
    var preMenuObj = null;
    var old = "";
    function changeSelectMenu(menuid,menuname){
    	
    	
    
    	/*if(preMenuObj != null){
    		preMenuObj.css("color","#fff");
    	}
    	
		$("#"+menuid).css("color","#000");
		preMenuObj = $("#"+menuid);*/
		
		
		if(preMenuObj != null){
    		preMenuObj.html(old);
    	}
    	
		preMenuObj = $("#td"+menuid);		
		old = "&nbsp;&nbsp;<b><a style ='color:#000;font-size:14px;font-weight:200;text-decoration:none;' href='#' title='" + menuname + "  ' onclick='changeSelectMenu(\"" + menuid + "\",\"" + menuname+ "\");' >"+menuname+"</a><b>&nbsp;&nbsp;";
		
		
		
		/* $("#td"+menuid).html("<div style='float:left' style='text-align:center' ><img src='${ctx}/styles/${theme}/images/top/left.gif' alt='' style='vertical-align:middle;'/></div>");
		$("#td"+menuid).html($("#td"+menuid).html()+"<div style='text-align:center' class='menubackground'>&nbsp;&nbsp;<b><a style ='color:#835d2c;font-size:12px;font-weight:bold;text-decoration:none;' href='#' title='" + menuname + "' onclick='changeSelectMenu(\"" + menuid + "\",\"" + menuname+ "\");' >"+menuname+"</a><b>&nbsp;&nbsp;</div>");
		$("#td"+menuid).html($("#td"+menuid).html()+"<div style='float:left;text-align:center'><img src='${ctx}/styles/${theme}/images/top/right.gif' alt='' style='vertical-align:middle;'/></div>"); */
		
		parent.treeCtl.showMenu(menuid);
		preMenuObj.siblings().removeClass("menuchoose");//删除td同辈元素的选中样式
		preMenuObj.addClass("menuchoose");//给选中的菜单添加选中样式
	};
	

	/* document.getElementById("message").onclick=function closeWindow(){
		window.showModalDialog('${ctx}/sys/first/getNewShortInfo&sid='+new Date().valueOf(),'','dialogwidth=950px; dialogheight=600px; status=no');
		//fresh();
	} */
	//fresh(); 
		
    //var int=self.setInterval("fresh();",120000);
	function fresh()
	{
		$.ajax({
			url: "${ctx}/sys/first/queryNotLookMess",
			type:"POST",
			dataType:"text",
			complete: function(xhr){
			    eval("jsonResult="+xhr.responseText);
			    if(jsonResult.success==true){
			    	var haveSize = jsonResult.messages;
			    	if(haveSize>0){
			    		$("#messageSize").text("("+haveSize+")");
			    	}
			    	document.getElementById("message").innerHTML="<img align='top' src='${ctx}/styles/${theme}/images/top/havenews.gif'>";
			    }else{
			    	$("#messageSize").text("");
			    	document.getElementById("message").innerHTML="<img align='top' src='${ctx}/styles/${theme}/images/top/nonews.gif'>";
			   	}
			}
		});
	}
	
	var mainFrame = window.top.mainFrame;  
	function addTab(id,title,url,QX){
		if(null == QX){
			QX = "";
		}
		if(null != url && "" != url){
			 $.ajax({
				url: "../../drpFirpage.shtml?action=checkHaveQx?QX="+QX,
				type:"POST",
				dataType:"json",
				data:{},
				complete: function(xhr){
					eval("jsonResult = "+xhr.responseText);
					if(jsonResult.success===true){
						alert(jsonResult.messages);
					}else{
					    mainFrame.addTab(id,title,"../../"+url);
					}
				}
			});
			
		}
		
	}
	
	function openDialog(url, width, height){
		if(!width){
			width = document.body.clientWidth * 0.9;
		}
		if(!height){
			height = document.body.clientHeight * 0.9;//高占80%高度
		}
		var retVal;
		layer.open({
		      type: 2,
		      title: '通用选取',
		      maxmin: false,// 最大最小化
		      shadeClose: false, //点击遮罩关闭层
		      resize: false,//是否可拉伸
		      area : [width+'px' , height+'px'],
		      content: url,
		      offset: 'auto',
		      btn:["确定","取消"],
		      yes:function(index,layero){

		      }
		    });
	}
	
	
 	document.getElementById("setPwd").onclick=function closeWindow(){
 		mainFrame.addTab("setpwd001","修改密码","${ctx}/sys/login/setPwdLogin?sid="+new Date().valueOf());
 		//openDialog('${ctx}/sys/login/setPwdLogin?sid='+new Date().valueOf());
		//window.showModalDialog('${ctx}/login.shtml?action=setPwdLogin&sid='+new Date().valueOf(),'','dialogwidth=620px; dialogheight=280px; status=no;scroll=yes');
	}  
</script>

</html>
