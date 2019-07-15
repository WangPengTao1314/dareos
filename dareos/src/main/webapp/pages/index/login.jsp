<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.centit.commons.model.Consts"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>圣象家居订单系统</TITLE>
<link rel="shortcut icon" href="${ctx}/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type=text/javascript src="${ctx}/scripts/core/md5.js"></script>
<script type=text/javascript src="${ctx}/scripts/core/jsLoader.js"></script>
<script type=text/javascript src="${ctx}/scripts/core/jquery-1.8.min.js"></script>
<script type=text/javascript src="${ctx}/scripts/layer/layer.js"></script>
<script type="text/javascript">

        $(document).ready(       		
          function () {
             $("#divBig").height($(window).height()); 
              var hg = $(window).height() / 8;
              $("#divInput").css("top", hg);
              var wg = ($(window).width()/20)
              var wgg  = ($(window).width()/4)
              console.log(111)
             $("#divInput").css("right", wg); 
              $("#tab_bg").css("width",wgg);
              $("#tab_bg").css("border-radius","10px");
				console.log(window.screen)
				if(window.screen.width == 1366){
					$(".intd").css("height","60px");
					$(".login").css("height","60px");
					$(".bottom").css("height","66px")
				}else{
						$(".intd").css("height","80px");
						$(".login").css("height","80px");
						$(".bottom").css("height","70px")
	
				}
          }
       );

          function resize() {  
        	    $("#divBig").height($(window).height()); 
                var hg = $(window).height() / 8;
                $("#divInput").css("top", hg);
                var wg = ($(window).width()/20)
                var wgg  = ($(window).width()/4)
                console.log(111)
               $("#divInput").css("right", wg); 
                $("#tab_bg").css("width",wgg);
                $("#tab_bg").css("border-radius","10px");
  				console.log(window.screen)
  				if(window.screen.width == 1366){
  					$(".intd").css("height","60px");
  					$(".login").css("height","60px");
  					$(".bottom").css("height","66px")
  				}else{
  						$(".intd").css("height","80px");
  						$(".login").css("height","80px");
  						$(".bottom").css("height","70px")
  	
            }
          }
    </script>
    <style type="text/css">
        a img{border:none;}	  
        a{text-decoration:none; font-family:Arial, "微软雅黑"; } /* 链接无下划线,有为underline */ 
        a:link {color:#595656; text-decoration:none; font-family:Arial, "微软雅黑"; }           /* 未访问的链接 */
        a:visited {color:#900; text-decoration: none;}
        a:hover{ text-decoration:none; font-family:Arial, "微软雅黑"; }                         /* 鼠标在链接上 */ 
        a:active {color: #fff; text-decoration: none; font-family:Arial, "微软雅黑"; }          /* 点击激活链接 */
        
	
	    .logBtn 
	    {
	        width:80%;
	        height:50px;
            background-color: #399bff;   
                border-radius: 4px
	    }

        .logButton
        {
            width:100%;
	        text-align:left;
	        line-height:30px;
	        color:#ffffff
	    }

        .logButton a
        {
            height:50px;
            width:100%;
           /*  background-image:url(${ctx}/pages/index/image/button01.png); */
		    margin:0;
		    padding:0;
		    color:#fff;
		    line-height:50px;
		    text-align:center;
		    display:block;
		    float:left;
        }		
        .logButton a:hover 
        {
	       /*  background-image:url(${ctx}/pages/index/image/button02.png); */
	        font-weight: bold;
	    }
	
        .expansion 
        {
             width:60px; 
             height:50px;
	         float:left;
        }
	 
    
    
    /***************/
     body{
     	margin:0px;
     	/*background-image:url(${ctx}/pages/index/image/dare_bgr.jpg);*/
     	/*background-repeat:repeat;*/
     /* 	background-image: url(${ctx}/pages/index/image/new_login.jpg);
    background-repeat: no-repeat;
    background-size: 100% */
     }
    .intd{
    	width:115px; 
    	height:50px; 
    	font-family:Arial, "微软雅黑"; 
    	COLOR: #fff; 
    	font-size:16px;
    }  
    .sp{
    	margin-right:10px;
    }
    .inpt{
      background: transparent;
    border: 0;
    border-bottom: 1px solid #cac6c6;
    box-sizing: border-box;
    display: block;
    font-size: 16px;
    outline: none !important;
    opacity: .9;
    padding: 5px 5px 8px 30px;
    width: 80%; 
    display: inline-block;
    margin-right: 20px
    }
   .login_img{
       display: inline-block;
       position: relative;
   	   left: 24px;
   	   top: 3px;
    }
    .tab_bg{
   /*  width:430px; */
  /*   background-color: rgba(0,0,0,0.6); */
  		padding: 60px 0px 0px !important
    }     
input:-webkit-autofill {
 -webkit-box-shadow: 0 0 0 1000px #399bff inset !important
  color:red
}
#divBig{
	width:100%;	
    background-color:#191e17;
    background-image: url(${ctx}/pages/index/image/new_login.jpg);
    background-repeat: no-repeat;
    background-size: 100% 100%;
}
</style>
</HEAD>
<body  scroll="no" onResize="resize();"  onkeydown="keyListenerEvent();">
<%
 String targetName=com.centit.commons.util.TimeComm.getPreTimeStamp("");
 String account = Consts.ACCOUNT_DISPLAY;
 
%>
  <div id="divBig" style="position: absolute;"></div>
	<div id="divInput" style=" float:right; z-index:100;position: relative">
	<div id="elephant" style="text-align: center"><img id="elephantImg" src="${ctx}/pages/index/image/new_logo.png"></img></div>
	<table cellpadding="0" cellspacing="0" border="0" class="tab_bg" id="tab_bg" >
		<FORM name=form onkeyup="keyListenerEvent();" id="loginform" action="${ctx}/sys/login/authLogin"     method=post onsubmit="return loginSubmit();" autocomplete="off" >
		<input id="invalidateFlag" name="invalidateFlag" type="hidden" value="">
		<input  id="userCss" name="userCss" type="hidden" value="<%=Consts.DEFAULT_CSS%>"/>
           <tr>
                <td class="login" colspan="2" style="padding: 10px 1px 1px 46px;font-size: 30px;color:rgb(76, 73, 73)">登录</td>
           </tr>
           <tr>
           <td align="center" valign="middle"  class="intd" colspan="2" >
           <img src="${ctx}/pages/index/image/new_loginUser.png" class="login_img" ></img>
           <input id="userId" name='S_NAME' placeholder='用户名' type="text" autocheck="true" mustinput="true" label="用户名" class="inpt" />
           </td>
           </tr>
           <tr>
           <td align="center" valign="middle"  class="intd" colspan="2">
           <img src="${ctx}/pages/index/image/new_loginPwd.png" class="login_img"></img>
           <input id="KL" name="KL" minlength="6" maxlength="15" placeholder='密码'   autocheck="true" mustinput="true" label="密码" size="15" type="password" class="inpt" />
           </td>
                <input id="S_PWD" name="S_PWD" type="hidden" value="">
                 <%
					if(!com.centit.commons.util.StringUtil.isEmpty(Consts.ACCOUNT_DISPLAY)&& "false".equals(Consts.ACCOUNT_DISPLAY)){
				%>
           </tr>
           	<%}else{ %>
             <tr>
             <td align="center" valign="middle"  class="intd" colspan="2">
             <img src="${ctx}/pages/index/image/new_loginIntd.png" class="login_img" ></img>
             <select  style="height:35px" name="S_ZTID" autocheck="true" mustinput="true" label="帐套"   id="S_ZTID" placeholder='用户名'  class="inpt" >
                 	</select>
                 	<input id="S_ZTMC" name="S_ZTMC" type="hidden"  value=""></td>
           </tr>
           <%}%>
           <tr>
                <td align="center" class="bottom" valign="bottom" colspan="2">
                  <div class="logBtn">
                    <div class="logButton"><a href='javascript:void(0);' onclick='loginSubmit()' >登录</a></div>
                  </div>
                </td>
           </tr>
           </FORM>
       </table>
    </div>
</body>
<script type="text/javascript">
var msg="${msg}";
if(msg!=null&&msg!=""){
	layer.alert(msg,{icon:2,closeBtn: 0},function(index){
		layer.close(index);
	}); 
}
//点击回车提交登录
function keyListenerEvent(){
	if(event.keyCode==13){
		if(checkMust()){
			loginSubmit();
		}
	}
}
function checkMust(){
	var userId=$("#userId").val();
	var KL=$("#KL").val();
	var SZTID=$("#S_ZTID").val();
	if(userId==""||userId==null){
		layer.alert('请输入用户名！');
		$("#userId").focus();
	}else if(KL==null||KL==""){
		layer.alert('请输入密码！');
		$("#KL").focus();
	}else if(SZTID==null||SZTID==""){
		layer.alert('未经授权账号，请联系管理员！');
	}else{
		return true;
	}
	return false;
}
function reset(){
	$("#loginform")[0].reset();
}
$(function(){
        browserinfo();
		$("#userId").focus();
		<%
			if(!(!com.centit.commons.util.StringUtil.isEmpty(Consts.ACCOUNT_DISPLAY)&& "false".equals(Consts.ACCOUNT_DISPLAY))){
		%>
		$("#userId").blur(function(){
			var userId = $(this).val();
			var select = document.getElementById("S_ZTID");
			$("#S_ZTID").empty();
			if(null != userId && "" != userId){
				$.ajax({
					url: "${ctx}/sys/login/getZtxx",
					type:"POST",
					dataType:"text",
					data:{"userId":userId},
					complete: function(xhr){
						eval("jsonResult = "+xhr.responseText);
						if(jsonResult.success===true){
							$("#S_ZTID").append(utf8Decode(jsonResult.messages));
						}else{
							parent.showErrorMsg(utf8Decode(jsonResult.messages));
						}
					}
				});
			}
		});
		<%
			}
		%>	
 //for set user css
   setTheCss();	
 });

 function loginSubmit(){
		if(checkMust()){
			var pwd = $("#KL").val();
			$("#S_PWD").val(hex_md5(pwd));
			var ztmc = $("#S_ZTID option:selected").text();
			$("#S_ZTMC").val(ztmc);
			$("#KL").val("");
			formSubmit();
            $("#KL").val(pwd);
            $("#S_ZTID").val("");
		}
		return false;
	}
  //for init user css
  function setTheCss(){	
  var theSytle=$.cookie('usersetcss');
  if(theSytle==null||theSytle=='')
  {
   theSytle='<%=Consts.DEFAULT_CSS%>';
   theme=theSytle;
  }
  include({
      cssFiles:["${ctx}/styles/"+theSytle+"/css/login.css"],       
      scripts:[]   
   }); 
  

  }
  //commit the page
  function formSubmit(){
        $("#invalidateFlag").val("true");
		<%if("true".equals(Consts.MAX_WINDOWS)){%>
		 $("#loginform").attr("target","<%=targetName%>")
		 var h = screen.height-50;
         var w = screen.width-10;
         var mainwin = window.open("about:blank", '<%=targetName%>', 'width='+w+',height='+h+',left=0,top=0,scrollbars=yes,resizable=no');
         mainwin.focus();
         form.submit(); 
		 if(navigator.Actual_Name!='Chrome')
	     {
	      window.opener=null;
	      window.close();
         }
        if(navigator.Actual_Name=='Chrome')
	     {
          top.window.opener = null;
          top.window.open('','_self',''); 
          top.window.close();
         }
	    <%}else{%>
	       form.submit(); 
	    <%}%>
       
  }
  function sleep(delay) {
	  var start = (new Date()).getTime();
	  while ((new Date()).getTime() - start < delay) {
	    continue;
	  }
	}
    </script>
</HTML>
    
