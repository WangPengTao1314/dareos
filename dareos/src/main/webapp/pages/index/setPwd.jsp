<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<%@ include file="/commons/jslibs.jsp"%>
		<script type=text/javascript src="${ctx}/scripts/core/md5.js"></script>
		<style type="text/css">
			.gdtd_select_input{width: 35%}
		</style>
		<title>密码修改</title>
	</head>
	<body class="bodycss1">
	<form action="">
	<table id="jsontb" width="100%" border="0" cellpadding="4" cellspacing="4" class="">
	<tr>
		<td class="detail2">
			<table width="100%" border="0" cellpadding="5" cellspacing="1"	class="detail_lst">
				<tr>
					<td width="35%" height="30" align="right" class="detail_label">用户编号：</td>
					<td width="65%" class="detail_content">&nbsp;
						&nbsp;<input type="text" readonly class="readonly gdtd_select_input" value="${YHBH}">	
					</td>
				</tr>
				<tr>
					<td width="25%" height="30" align="right" class="detail_label">用户姓名：</td>
					<td width="75%" class="detail_content">&nbsp;
						&nbsp;<input type="text" readonly class="readonly gdtd_select_input" value="${YHM}">	
					</td>
				</tr> 
				<tr>
					<td width="25%" height="30" align="right" class="detail_label">原&nbsp;&nbsp;密&nbsp;&nbsp;码：</td>
					<td width="75%" class="detail_content">&nbsp;
						&nbsp;<input id="oldpwd" name="oldpwd" type="password" inputtype="string" label="原密码" mustinput="true" minlength="6" maxlength="15" autocheck="true" class="gdtd_select_input" >
					</td>
				</tr> 
				<tr>
					<td width="25%" height="30" align="right" class="detail_label">新&nbsp;&nbsp;密&nbsp;&nbsp;码：</td>
					<td width="75%" class="detail_content">&nbsp;
						&nbsp;<input id="newpwd" name="newpwd" type="password" inputtype="string"
							label="新密码" mustinput="true" minlength="6" maxlength="15"
							autocheck="true" class="gdtd_select_input" ><br>
						<span class="FontBlue">&nbsp;&nbsp;(密码长度至少6位，且必须是数字、字母、特殊字符[!@#$%^&*]中的至少两类) </span>
					</td>
				</tr> 
				<tr>
					<td width="25%" height="30" align="right" class="detail_label">确认密码：</td>
					<td width="75%" class="detail_content">&nbsp;
						&nbsp;<input id="newpwd2" name="newpwd2" type="password" inputtype="string"
							label="确认密码" mustinput="true" minlength="6" maxlength="15"
							autocheck="true" class="gdtd_select_input" >
					</td>
				</tr> 
				<tr>
					<td height="32" colspan="2" align="center" class="gridToolbar">
						<input id="submit" type="button" class="btn"  value="确 定">
						&nbsp;&nbsp;
						<input type="reset" class="btn" value="重 置">
						&nbsp;&nbsp;
						<input id="close" type="button" class="btn" value="退 出" onclick="loginOutb();">
					</td>
				</tr>
			</table></td></tr></table>	
			</form>						
	</body>
	<script type="text/javascript">InitFormValidator(0);</script>
	<script type="text/javascript">
		$("#submit").click(function(){
			if(!FormValidate(0)){
				return;
			}
			var oldpwd = $("#oldpwd").val();
			var newpwd = $("#newpwd").val();
			var newpwd2 = $("#newpwd2").val();
			
			var re1 = new RegExp(/[0-9]/g);//只能是数字
	        var re2 = new RegExp(/[A-Za-z]/g);//只能是字母
	        var re3 = new RegExp(/[!@#$%^&*]/g);//只能是字符：[^%&',;=?$x22]+
	        var s1= re1.test(newpwd);
			var s2 = re2.test(newpwd);
			var s3 = re3.test(newpwd);

			if(oldpwd == ''){
		          showErrorMsg("请输入原密码");
		           return;
		        }  
		 	if(!((s1 && s2) || (s1 && s3)|| (s2 && s3)) ){
                 showErrorMsg("新密码必须是数字、字母、特殊字符[!@#$%^&*]中的至少两类");
                 return;
            }
	        if(newpwd != newpwd2){
	          showErrorMsg("两次输入密码不一致，请重新输入");
	           return;
	        } 
	        if(newpwd == oldpwd){
	          showErrorMsg("原始密码与新密码相同，请重新输入");
	           return;
	        }  
	        
	        $.ajax({
				url: "${ctx}/sys/login/setPwd",
				type:"POST",
				dataType:"text",
				data:{"oldpwd":hex_md5(oldpwd),"newpwd":hex_md5(newpwd)},
				complete: function(xhr){
					eval("jsonResult = "+xhr.responseText);
					if(jsonResult.success===true){
					     saveConfirm("密码修改成功,请使用新密码","loginOutb();");
					}else{
						showErrorMsg(utf8Decode(jsonResult.messages));
					}
				}
			});
		});

		function loginOutb(){
			parent.parent.window.location.href = "${ctx}/pages/index/logout.jsp";
		}
	</script>
</html>