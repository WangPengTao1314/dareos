<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%> 
<%@ include file="/commons/jslibs.jsp"%>
<%@ page import="com.centit.sys.model.UserBean,java.util.Map"%>
<%
try { 
   String KeyName = request.getAttribute("KeyName")==null?"":(String)request.getAttribute("KeyName"); 
   if("".equals(KeyName))
    KeyName = request.getParameter("KeyName")==null?"":(String)request.getParameter("KeyName"); 
   String KeyID = request.getAttribute("KeyID")==null?"":(String)request.getAttribute("KeyID");
   if("".equals(KeyID))
    KeyID = request.getParameter("KeyID")==null?"":(String)request.getParameter("KeyID"); 
   String MKLB = request.getAttribute("MKLB")==null?"":(String)request.getAttribute("MKLB");
   if("".equals(MKLB))
    MKLB = request.getParameter("MKLB")==null?"":(String)request.getParameter("MKLB"); 
%>
<html>
<head>
<TITLE>OAMKXXTree.jsp</TITLE>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
</HEAD>

<script language="JavaScript" src="${ctx}/pages/sys/qxcomm/admin_checkForm.js"></script>
<script language="JavaScript" src="${ctx}/pages/sys/qxcomm/admin_left.js"></script>
<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>


<script language="Javascript">
  function waitShow() {
    document.insertForm.style.display='inline';
    document.all('waitbar').style.display = "none";
  }
</script>
<BODY  onload="setTimeout('waitShow()', 1200);" class="bodycss1">
<%
   String action = "";
   String cmdFlag = "";
   if (KeyName.equals("XTYHID")) {
     //action = "../../../servlet/butone.common.privilege.ui.XTYHServlet";
     action="insertXTYHQXSelf";
     cmdFlag = "insertXTYHQXSelf";
   } 
   if (KeyName.equals("XTJSID")) {
     //action = "../../../servlet/butone.common.privilege.ui.XTJSServlet";
     action="insertxtjsQX";
     cmdFlag = "insertxtjsQX";
   }
   if (KeyName.equals("GZZXXID")){
     //action = "../../../servlet/butone.common.privilege.ui.GZZXXServlet";
      action="insertGZZQX";
     cmdFlag = "insertGZZQX";
   }
   
   if (KeyName.equals("SYSTEMADM")){
     //action = "../../../servlet/butone.common.privilege.ui.SYSTEMADMServlet";
     action="insertxtjsQX";
     cmdFlag = "InsertMKQX";

   }
   
   String MKQXJB = "";
   MKQXJB = request.getParameter("MKQXJB")==null?"":request.getParameter("MKQXJB");
   if (MKQXJB.equals("")) MKQXJB = "Default";
%>


<div id="waitbar">
<br>
<br>
<br>
<br>
<br>
<table border="0" cellpadding="0" cellspacing="0" width="50%" align=center>
  <tr>
    <td width="51%" noWrap>
      <p align="right"><FONT face=宋体 color=navy 
      size=2>正在加载系统模块信息：</FONT></p>   </td>
    <td width="4%" bordercolor="#000000">
      <marquee align="middle" direction="right" scrolldelay="35" bgcolor="gainsboro" scrollamount="4" style="BORDER-RIGHT: black 1px outset; BORDER-TOP: black 1px outset; FONT-SIZE: xx-small; BORDER-LEFT: black 1px outset; WIDTH: 133px; COLOR: #000080; BORDER-BOTTOM: black 1px outset; HEIGHT: 13px" 
      behavior="slide" 
     >███████████████████████████████████████████████████████████████████████████████</marquee>
    </td><td width="45%" align=left><div id=per></div></td>
  </tr>
</table>
</div>

<form name="insertForm"  method="post" action="<%=action%>" style="display:none">
<input type="hidden" name="XTYHID" value="<%=KeyID%>">
<input type="hidden" name="cmdFlag" value="<%=cmdFlag%>">
<input type="hidden" name="<%=KeyName%>" value="<%=KeyID%>">
<input type="hidden" name="MKSM" value="<%=MKLB%>">

<%
   String[] MKAry = (String[])request.getAttribute("jspMkary");
   if (MKAry != null && MKAry.length >=1) {
%>

<br>


 
 <%  
   if (!KeyName.equalsIgnoreCase("SYSTEMADM")) {
%>
默认权限级别
<%= request.getAttribute("jspReturnQXJBRadioList")%>


      <%}} else {%>
      <hr>
        <div align="center"><font color="red">该系统模块信息为空!</font></div>
      <%}%>
<hr>
<%
    String condition = " where "+KeyName + "='"+ KeyID+"'";
    String yesOrNo = "";
    String tableName = "";
    boolean  AllFlag = false;
    if (KeyName.equalsIgnoreCase("XTJSID")) {
      UserBean aUserBean = (UserBean)session.getAttribute("UserBean");
      tableName = "T_SYS_JSQX";
        yesOrNo =  (String)request.getAttribute("jspYesOrNo");
        if (yesOrNo.equals("")) {
          AllFlag = true;
        }
    }
    if (KeyName.equalsIgnoreCase("XTYHID")) {
      UserBean aUserBean = (UserBean)session.getAttribute("UserBean");
        tableName = "T_SYS_YHQX";
        yesOrNo =  (String)request.getAttribute("jspYesOrNo");
         if (yesOrNo.equals("")) {
          AllFlag = true;
        }
    }
    if (KeyName.equalsIgnoreCase("GZZXXID")) {
      UserBean aUserBean = (UserBean)session.getAttribute("UserBean");
       yesOrNo =  (String)request.getAttribute("jspYesOrNo");
        tableName = "T_SYS_GZZQX";
         if (yesOrNo.equals("")) {
          AllFlag = true;
        }
    }
 
    String strMK = "";
     if (KeyName.equals("SYSTEMADM")){
        
          AllFlag = true;
        
      strMK = (String)request.getAttribute("jspStrMK");
   } else {
      
      strMK =  (String)request.getAttribute("jspStrMK");
   }

  %>

<% for (int i=0; i< MKAry.length; i=i+3) {
	int tempMKAryI = i+1;
	int tempMKAryI2 = i+2;
    if ( yesOrNo.indexOf(MKAry[tempMKAryI])!=-1 || AllFlag) {
%>
<div  title="<%=MKAry[tempMKAryI2]%>"><img  style="vertical-align: top;" id="img<%=MKAry[tempMKAryI]%>" src="${ctx}/styles/${theme}/images/plus/closediv.gif" onclick="changeImg('<%=MKAry[tempMKAryI]%>');switchMenu(sub<%=MKAry[tempMKAryI]%>); ">
<input name="checkSelA" <%if (strMK.indexOf(MKAry[tempMKAryI])!=-1) {%>checked<%}%> value="<%=MKAry[tempMKAryI]%>" type="checkBox" id="input<%=MKAry[tempMKAryI]%>" style="" onclick="changeImg('<%=MKAry[tempMKAryI]%>');switchMenu(sub<%=MKAry[tempMKAryI]%>); callSel('<%=MKAry[tempMKAryI]%>');"><%=MKAry[i]+"["+MKAry[tempMKAryI]+"]"%></div>
<%
  //String[] SubMKAry = aMKQXUtil.getMKAry(MKLB, 7, " SJMK='"+MKAry[tempMKAryI]+"' AND (MKZT<>'停用' or MKZT is null or MKZT='')");
    Map jspSubMkaryMap = (Map)request.getAttribute("jspSubMkaryMap");
    String[] SubMKAry = (String[])jspSubMkaryMap.get(tempMKAryI+"");
%>
<div id="sub<%=MKAry[tempMKAryI]%>" style="display:none">
<% for (int j=0; j<SubMKAry.length; j=j+3) {
		int tempMKAryJ = j+1;
		int tempMKAryJ2 = j+2;
   if ( yesOrNo.indexOf(SubMKAry[tempMKAryJ])!=-1 || AllFlag) {
%>
&nbsp;&nbsp;&nbsp;&nbsp;
<img style="vertical-align: top;" title="<%=SubMKAry[tempMKAryJ2]%>" id="img<%=SubMKAry[tempMKAryJ]%>" src="${ctx}/styles/${theme}/images/plus/closediv.gif" onclick="changeImg('<%=SubMKAry[tempMKAryJ]%>');switchSubmenu(sub<%=SubMKAry[tempMKAryJ]%>);">
<input  title="<%=SubMKAry[tempMKAryJ2]%>" id="input<%=SubMKAry[tempMKAryJ]%>"  <%if (strMK.indexOf(SubMKAry[tempMKAryJ])!=-1) {%>checked<%}%>  type="checkBox" name="checkSelB<%=MKAry[tempMKAryI]%>"  value="<%=SubMKAry[tempMKAryJ]%>" style="" onclick="changeImg('<%=SubMKAry[tempMKAryJ]%>');switchSubmenu(sub<%=SubMKAry[tempMKAryJ]%>); callSel('<%=SubMKAry[tempMKAryJ]%>');"><font title="<%=SubMKAry[tempMKAryJ2]%>"><%=SubMKAry[j]+"["+SubMKAry[tempMKAryJ]+"]"%></font>

<%
  //String[] FinalMKAry = aMKQXUtil.getMKAry(MKLB, 9, "SJMK='"+SubMKAry[tempMKAryJ]+"' AND (MKZT<>'停用' or MKZT is null or MKZT='') ");
   Map jspFinalMkaryMap = (Map)request.getAttribute("jspFinalMkaryMap");
   
   String[] FinalMKAry = (String[])jspFinalMkaryMap.get(SubMKAry[tempMKAryJ]+tempMKAryI+tempMKAryJ);

   
%>
<div  id="sub<%=SubMKAry[tempMKAryJ]%>" style="display:none">
<br>
<% 
 for (int k=0; k<FinalMKAry.length; k=k+3) { 
		int tempMKAryK = k+1;
		int tempMKAryK2 = k+2;
	
		
   if ( yesOrNo.indexOf(FinalMKAry[tempMKAryK])!=-1 || AllFlag) {
  
%>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input title="<%=FinalMKAry[tempMKAryK2]%>" id="input<%=FinalMKAry[tempMKAryK]%>"   <%   if (strMK.indexOf(FinalMKAry[tempMKAryK])!=-1) { %>checked<%}%> type="checkBox" name="checkSel" value="<%=FinalMKAry[tempMKAryK]%>" onclick="callSel('<%=FinalMKAry[tempMKAryK]%>')" style=""><font  title="<%=FinalMKAry[tempMKAryK2]%>"><%=FinalMKAry[k]+"["+FinalMKAry[tempMKAryK]+"]"%></font>
<div title="<%=FinalMKAry[tempMKAryK2]%>" id ="final<%=FinalMKAry[tempMKAryK]%>" >
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <% int flag=0;
       if (MKQXJB.equals("Default")) {	
        
    %>
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_0"%>" <%if (strMK.indexOf(FinalMKAry[tempMKAryK]+"_0")!=-1) { flag=1;%>checked<%}%>>机构集中
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_1"%>" <%if (strMK.indexOf(FinalMKAry[tempMKAryK]+"_1")!=-1) { flag=1;%>checked<%}%>>多机构
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_2"%>" <%if (strMK.indexOf(FinalMKAry[tempMKAryK]+"_2")!=-1) { flag=1;%>checked<%}%>>本机构
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_3"%>" <%if (strMK.indexOf(FinalMKAry[tempMKAryK]+"_3")!=-1) { flag=1;%>checked<%}%>>部门集中
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_4"%>" <%if (strMK.indexOf(FinalMKAry[tempMKAryK]+"_4")!=-1) { flag=1;%>checked<%}%>>多部门
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_5"%>" <%if (strMK.indexOf(FinalMKAry[tempMKAryK]+"_5")!=-1) { flag=1;%>checked<%}%>>本部门
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_6"%>" <%if (strMK.indexOf(FinalMKAry[tempMKAryK]+"_6")!=-1||flag==0) { flag=1;%>checked<%}%>>本人员
    <%
      }
      else {
    %>
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_0"%>" <%if (MKQXJB.equals("0")) { %>checked<%}%>>机构集中
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_1"%>" <%if (MKQXJB.equals("1")) { %>checked<%}%>>多机构
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_2"%>" <%if (MKQXJB.equals("2")) { %>checked<%}%>>本机构
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_3"%>" <%if (MKQXJB.equals("3")) { %>checked<%}%>>部门集中
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_4"%>" <%if (MKQXJB.equals("4")) { %>checked<%}%>>多部门
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_5"%>" <%if (MKQXJB.equals("5")) { %>checked<%}%>>本部门
    <input type="radio" name="radioSel<%=FinalMKAry[tempMKAryK]%>" style="" value="<%=FinalMKAry[tempMKAryK]+"_6"%>" <%if (MKQXJB.equals("6")) { %>checked<%}%>>本人员 
    <%
       }
    %>
</div>
<%}%>
<%}%>
</div>
<br>
<%}%>
<%}%>
</div>
<%}%>
<%}%>
<div style="width:100%;height:32px"></div>
<div style="width:100%;padding-top:1%;background:white;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
	<table border="0" cellspacing="0" cellpadding="0" width="100%">
	  <tr>
	    <td align="center"> 
	      <input class="button" type="button" name="Submit1" value="提交" onclick="sendForm()">
	      <input class="button" type="reset" name="Submit2" value="重置">
	    </td>
	  </tr>
	</table>
</div>
</form>

<script language="JavaScript">


function changeImg(value) {
  try {
  var length;  
  if (eval("document.all('sub"+value+"')"+".style.display")=='none') {
    eval("document.all"+"('img"+value+"').src='${ctx}/styles/${theme}/images/plus/opendiv.gif'");  
    
  
  } else {
    eval("document.all('img"+value+"').src='${ctx}/styles/${theme}/images/plus/closediv.gif'");  
  }
  } catch(e) {
    return;
  }
  

}



function refreshOpener(){
     alert('设置成功！');
     try{
       window.opener.refreshSelf("<%=KeyID%>"); 
     }catch(e){
       //
     }
     window.opener=null; 
     window.close();
	 
  }
  
function sendForm() {
    document.insertForm.Submit1.disabled = true;
	document.insertForm.Submit2.disabled = true;
	document.insertForm.submit();
	
}



function callDefaultRadio(Type) {
    if (!confirm("是否需要重新设定默认的所有权限级别？如果确定，则当前所有模块的权限级别都会被置为默认的级别同时目前没有提交的新增权限将会丢失（但如果不提交,原有权限不会更新)！")) return false;
	 window.location="toMkxxTree?MKLB=<%=MKLB%>&KeyName=<%=KeyName%>&KeyID=<%=KeyID%>&MKQXJB="+Type;
	
  }
  
  
 function callSel(MKBH) {
    try {
     var a;   
	 a= $("#input"+MKBH)[0].checked;
     if (a == false) {
        if (MKBH.length == 5) {
           var tmp = $("#sub"+MKBH).find("input[name='checkSelB"+MKBH+"']").length;
              
           for (i=0; i<tmp; i++) {  
	         if ($("#sub"+MKBH).find("input[name='checkSelB"+MKBH+"']")[i].value.indexOf(MKBH) != -1) {
	           $("#sub"+MKBH).find("input[name='checkSelB"+MKBH+"']")[i].checked = false;          
	         }
	       }          	       	     
        }
        
        if (MKBH.length == 7) {
           var tmp = $("#sub"+MKBH).find("input[name='checkSel']").length;
         
           if (tmp == null)   {
             tmp = 0;
             $("#sub"+MKBH).find("input[name='checkSel']").checked = false;  
           } 
           
           for (i=0; i<tmp; i++) {  
	           $("#sub"+MKBH).find("input[name='checkSel']")[i].checked = false;
	       }    
        }               
            var tmpMKBH;
	          
	           var decide; 
	           eval("decide=MKBH.substring(2,5)");
	           
	          var tmp2=$("#sub"+MKBH).find("input[name='checkSel']").length;
               for (j=0; j<tmp2; j++) {  
                 	tmpvalue = $("#sub"+MKBH).find("input[name='checkSel']")[j].value;
	             if (eval("tmpvalue.indexOf(MKBH)") != -1) {
	                $("#sub"+MKBH).find("input[name='checkSel']")[j].checked = false;
	             }
	           }  
     } else { 
	      if (MKBH.length == 5) {
           var tmp = $("#sub"+MKBH).find("input[name='checkSelB"+MKBH+"']").length;
           for (i=0; i<tmp; i++) {  
	         if ($("#sub"+MKBH).find("input[name='checkSelB"+MKBH+"']")[i].value.indexOf(MKBH) != -1) {
	           $("#sub"+MKBH).find("input[name='checkSelB"+MKBH+"']")[i].checked = true;     
	         }
	       }          
        }
        if (MKBH.length == 7) {
           var tmp = $("#sub"+MKBH).find("inut[name='checkSel']").length;
         
           if (tmp == null)   {
            
             tmp = 0;
             $("#sub"+MKBH).find("input[name='checkSel']").checked = true;  
           }
           
           for (i=0; i<tmp; i++) {  
	           $("#sub"+MKBH).find("input[name='checkSel']")[i].checked = true;  
	       }    
        }
	   
	   
	   //tmp
	   
	     var tmpMKBH;
	           var tmp2;
	   //      eval("tmpMKBH=document.insertForm.checkSelB"+MKBH+"[i].value");
	   //      alert(tmpMKBH);
	          
	           var decide; 
	           eval("decide=MKBH.substring(2,5)");
	           
	           tmp2=$("#sub"+MKBH).find("input[name='checkSel']").length;
	           
               for (j=0; j<tmp2; j++) {  
                  tmpvalue = $("#sub"+MKBH).find("input[name='checkSel']")[j].value;
                 
	             if (eval("tmpvalue.indexOf(MKBH)") != -1) {
	                $("#sub"+MKBH).find("input[name='checkSel']")[j].checked = true;    
	             }
	            // if (tmpvalue.substring(2,5) > decide) {
	            //   break;
	            // }
	           }  
	           
	   
	   //tewf
	   
	   
     }
     	
     } catch(e) {
    	 console.log(e)
     }
  }		
 
  	var strMK = '<%=strMK%>';
  	  	
  	
  function readAMKQX() {     
    try {
    
    var strTemp = "";	
    var MKNo = 0;

	// alert(strMK);
     // alert(document.insertForm.checkSelA.length);
      for (i=0; i<document.insertForm.checkSelA.length; i++) {
                     strTemp2 = document.insertForm.checkSelA[i].value;
               
                        if (strMK.indexOf(strTemp2)!= -1) {
                        
                          document.insertForm.checkSelA[i].checked = true;
                          
                            var length;
                            eval("length=document.insertForm.checkSelB"+strTemp2+".length");

                            for (j=0; j<length; j++) {
						        strValue =  eval("document.insertForm.checkSelB"+strTemp2+"["+j+"].value");

     
					        if (strMK.indexOf(strValue)!= -1) {
            				    eval("document.insertForm.checkSelB"+strTemp2+"["+j+"].checked = true");
                           }
        
                         }
                          
                      }
	 
	 } 
	 
	 } catch(e) {
	 }	 
	 
  }
  
  function readAllSubMKQX() {
     try {
       var strTemp = "";	
       var MKNo = 0;
                    
       for (i=0; i<document.insertForm.checkSel.length; i++) {
          strTemp2 = document.insertForm.checkSel[i].value;
          if (strMK.indexOf(strTemp2)!= -1) {
             document.insertForm.checkSel[i].checked = true;
             
             for (j=0; j<7;j++) {
              if (eval("strMK.indexOf(document.insertForm.radioSel"+strTemp2+"[j].value)!=-1")) {
                 eval("document.insertForm.radioSel"+strTemp2+"[j].checked = true");
                 break;
               }
             }
          } 
 	   } 
	 
	 } catch(e) {
	 }  	 	 
  }    
</script>
<%
} catch (Exception ex) {
  ex.printStackTrace();
}
%>
</BODY>
</html>
