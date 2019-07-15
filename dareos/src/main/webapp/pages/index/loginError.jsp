<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.centit.commons.model.Consts"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<body >
	<script>
		layer.alert('${errorMsg}',{icon:2,closeBtn: 0},function(index){
			  layer.close(index);
			  top.location.href="../../pages/index/login.jsp";
// 			  console.log(window);
// 			  var win = parent.parent.window;
// 				if(win){
// 					win.location.href="../../pages/index/login.jsp";
// 				}else if(parent.window){
// 					parent.window.location.href="../../pages/index/login.jsp";
// 				}else{
// 					window.location.href="../../pages/index/login.jsp";
// 				}
		 });
	</script>
</body>    
