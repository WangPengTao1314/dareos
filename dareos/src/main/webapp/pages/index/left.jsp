<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%> 
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta name="description" content="Xenon Boostrap Admin Panel" />
<meta name="author" content="" />
<title>Xenon - Dashboard</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/xenon-core.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/xenon-components.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/xenon-skins.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/custom.css" />
<script type="text/javascript" src="${ctx}/scripts/core/jquer-1.10.2.min.js"></script>
</head>
<body class="page-body">
<div class="page-container">
<div class="sidebar-menu toggle-others fixed">
<div class="sidebar-menu-inner">
<div><a href="#" data-toggle="sidebar">
<i class="fa-bars"></i>
</a></div>
<ul id="main-menu" class="main-menu">
              <!--       <li class="active opened active">
                    <a href="#">
                    <i class="linecons-cog"></i>
                    <span class="title">demo</span>
                    </a>
				                    <ul>
				                    <li class="active">
				                    <a href="dashboard-1.html">
				                    <span class="title">Dashboard 1</span>
				                    </a>
				                    </li>
				                    <li>
				                    <a href="dashboard-2.html">
				                    <span class="title">Dashboard 2</span>
				                    </a>
				                    </li>
				                    <li>
				                    <a href="dashboard-3.html">
				                    <span class="title">Dashboard 3</span>
				                    </a>
				                    </li>
				                    <li>
				                    <a href="dashboard-4.html">
				                    <span class="title">Dashboard 4</span>
				                    </a>
				                    </li>
				                    <li>
				                    <a href="skin-generator.html">
				                    <span class="title">Skin Generator</span>
				                    </a>
				                    </li>
				                    </ul>
                    </li>      -->                               
</ul>
</div>
</div>
</div>
<script type="text/javascript" src="${ctx}/scripts/core/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/TweenMax.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/resizeable.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/xenon-api.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/xenon-toggles.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/xenon-custom.js"></script>
<script>
 console.log(parent.treeData)
var treeData = []
for(var i=0;i<parent.treeData.children.length;i++){
	for(var n =0;n<parent.treeData.children[i].children.length;n++){
		treeData.push(parent.treeData.children[i].children[n])
	}
}
console.log(treeData)
var mainFrame=window.top.mainFrame;  
    	function renderTree(arr, leval) {
        	var nodes = ""
    			if (typeof arr != 'object') return ''
    			for (var i = 0; i < arr.length; i++) { 
    				var url = arr[i].img
    			  	if(leval == 0){
    			  		if(!url){
        					nodes += '<li class="' + leval + '"><a href="#" onclick="test(\'' + arr[i].id + '\',\'' + arr[i].name + '\',\'' + arr[i].url + '\')" ><img src="${ctx}/styles/${theme}/images/left/demo.png"></img><span style=" position: relative;left: 9px; bottom: 3px">' + arr[i].name + '</span></a>';
            				if (arr[i].children) {
            					nodes += '<ul>'
            					nodes +=  renderTree(arr[i].children, leval + 1)
            					nodes += '</ul>'
            				}
            				nodes += '</li>'
        				}else{
        					nodes += '<li class="' + leval + '"><a href="#" onclick="test(\'' + arr[i].id + '\',\'' + arr[i].name + '\',\'' + arr[i].url + '\')" ><img src="${ctx}/styles/${theme}/images/left/'+url+'""></img><span style=" position: relative;left: 9px; bottom: 3px">' + arr[i].name + '</span></a>';
            				if (arr[i].children) {
            					nodes += '<ul>'
            					nodes +=  renderTree(arr[i].children, leval + 1)
            					nodes += '</ul>'
            				}
            				nodes += '</li>'
        				}
    			  	}else{
    			  		nodes += '<li class="' + leval + '"><a href="#" onclick="test(\'' + arr[i].id + '\',\'' + arr[i].name + '\',\'' + arr[i].url + '\')" ><span>' + arr[i].name + '</span></a>';
        				if (arr[i].children) {
        					nodes += '<ul>'
        					nodes +=  renderTree(arr[i].children, leval + 1)
        					nodes += '</ul>'
        				}
        				nodes += '</li>'	
    			  	}
    					
    			}
    			return nodes
		}
		var html = renderTree(treeData, 0)
		$(".main-menu").html(html)
	/* 	$.sidebarMenu($('.sidebar-menu'))/*  */ 
	function test(id,name,url){
		console.log(id)
		console.log(name)
		console.log(url)
		if(url == "null"){
			return false
		}else if(mainFrame.g && mainFrame.g('YT_LOAD_MSG')){
			return false;
		}	else{
			 mainFrame.addTab(id,name,"../../"+url);
		}		
	}
</script>
</body>
</html>

