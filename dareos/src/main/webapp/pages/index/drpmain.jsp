<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<%-- <link href="${ctx}/styles/${theme}/css/TabPanel.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${ctx}/scripts/core/jquery-1.8.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/TabPanel.js"></script>
<script type="text/javascript" src="${ctx}/scripts/util/Fader.js"></script> --%>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css"> --%>
<link href="${ctx}/styles/${theme}/css/TabPanel.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/font-awesome.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/xenon-core.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/xenon-components.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/xenon-skins.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/custom.css" />
<script type="text/javascript" src="${ctx}/scripts/core/jquer-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/jquery-1.8.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/TabPanel.js"></script>
<script type="text/javascript" src="${ctx}/scripts/util/Fader.js"></script>
<style>
.grid {
          display: -webkit-flex;
          display: -moz-flex;
          display: -o-flex;
          display: -ms-flex;
          display: flex;
          height: 100%;
        }
.leftSty{
	float: left;
    height: 100%;
    width:180px
}
.rightSty{
    overflow: hidden;
    height: 100%;
    float: left;
    display: inline-block
}  
.iconDiv{
    height: 20px;
    width: 20px;
    text-align: cente
}
 .iconDiv:hover{
	    background: #0073c5;
    /* border: 1px solid #9e1212; */
    border-radius: 2px;
    box-shadow: 0 0 10px #b3a9a9
} 
.overflowSty{
	overflow:scroll
}
.toggle{
	padding: 10px 10px 10px 10px
}
.toggle_open{
	padding: 10px 10px 10px 10px
}
</style>
<script type="text/javascript">
		//tab
		var tabpanel;
		$(document).ready(function(){
			  tabpanel = new TabPanel({
				renderTo:'tab',
				autoResizable:true,
				border:'none',
				active : 0,
				maxLength : 10,
				items : [{
					id:'1',
					title:'门户页面', 
					closable:false,
					html:'<iframe src="${ctx}/sys/first/toFirst" id="fir_page" width="100%" height="100%" frameborder="0" ></iframe>'
				}]
			 });
				//saleMssage();
				//setInterval("saleMssage()",1000*60*10);
			queryPrmt();
			
			$("#more_prmt").click(function(){    
				 fir_page.doMore_Prmt();
				 return false;
				//document.getElementById("to_more").click();
				//$("#queryMoreForm").attr("action","${ctx}/drpFirpage.shtml?action=toMorePrmt");
				//$("#queryMoreForm").submit();
			});
		});
		
		function addTab(id,name,url){		
		  tabpanel.addTab({id:id,title:name, html:'<iframe name="'+id+'IFrame" src="'+url+'" id="'+id+'IFrame" width="100%" height="100%" frameborder="0" ></iframe>'});
		}
		
		//促销信息
		function queryPrmt(){
			$.ajax({
				url: "${ctx}/sys/drpFirst/queryPrmt",
				type:"POST",
				dataType:"json",
				data:{},
				complete: function(xhr){
					eval("jsonResult = "+xhr.responseText);
					if(jsonResult.success===true){
						var data = jsonResult.data;
						 if(null ==data || ""==data){
							 $("#marqueeId").append("<span>暂无促销信息</span>");
						 }else{
							$.each(data,function(i,item){
							   $("#marqueeId").append("<span>"+item.PRMT_PLAN_NAME+"</span>").append("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
						    });
						 }
						 
					}
				}
			});
		}
		
		
		
</script>
		<title></title> 
	</head>
<!-- 	<body  style="margin:0px;border:0px;overflow:auto" > 
		<div class="grid">
			<div class="leftSty" id="leftSty">
				<div class="page-container">
					<div class="sidebar-menu toggle-others fixed">
					<ul id="main-menu" class="main-menu">                           
					</ul>
					</div>
				</div>
			</div>
			<div id="tab" class="rightSty"></div>
		</div>
	</body> -->
	
		<body  style="margin:0px;border:0px;overflow:auto" > 
	<div class="grid" id="grid">
	<div class="leftSty" id="leftSty">
		<div class="page-container">
			<div class="sidebar-menu toggle-others fixed">
			<div id="toggle" class="toggle">
			<div class="iconDiv" style="text-align: center">
			<a style="position: relative; top: 3px" href="#" data-toggle="sidebar">
				<i style="color: #ffffff" class="fa-bars"></i>
			</a>
			</div>
			</div> 
			<ul id="main-menu" class="main-menu overflowSty">                           
			</ul>
			</div>
		</div>
	</div>
	<div id="tab" class="rightSty"></div>
	</div>
	</body>
	
	<script type="text/javascript" src="${ctx}/scripts/core/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/TweenMax.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/resizeable.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/xenon-api.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/xenon-toggles.js"></script>
<script type="text/javascript" src="${ctx}/scripts/core/xenon-custom.js"></script>
<!-- <title></title> 
	</head>
	<body  style="margin:0px;border:0px;overflow:auto" >
	<div id="tab" style="width:100%;"></div>
	</body> -->
	<script>
// console.log(parent.treeData)
var treeData = []
for(var i=0;i<parent.treeData.children.length;i++){
	for(var n =0;n<parent.treeData.children[i].children.length;n++){
		treeData.push(parent.treeData.children[i].children[n])
	}
}
//console.log(treeData)
var mainFrame=window.top.mainFrame;  
    	function renderTree(arr, leval) {
        	var nodes = ""
    			if (typeof arr != 'object') return ''
    			for (var i = 0; i < arr.length; i++) { 
    				var url = arr[i].img
    			  	if(leval == 0){
    			  		if(!url){
        					nodes += '<li class="' + leval + '"><a href="#" onclick="test(\'' + arr[i].id + '\',\'' + arr[i].name + '\',\'' + arr[i].url + '\')" ><img style="position: relative;bottom: 3px;" src="${ctx}/styles/${theme}/images/left/demo.png"></img><span style=" position: relative;left: 9px; top: 0px">' + arr[i].name + '</span></a>';
            				if (arr[i].children) {
            					nodes += '<ul>'
            					nodes +=  renderTree(arr[i].children, leval + 1)
            					nodes += '</ul>'
            				}
            				nodes += '</li>'
        				}else{
        					nodes += '<li class="' + leval + '"><a href="#" onclick="test(\'' + arr[i].id + '\',\'' + arr[i].name + '\',\'' + arr[i].url + '\')" ><img style="position: relative;bottom: 3px;" src="${ctx}/styles/${theme}/images/left/'+url+'""></img><span style=" position: relative;left: 9px; top: 0px">' + arr[i].name + '</span></a>';
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
		//console.log(id)
		//console.log(name)
		//console.log(url)
		if(url == "null"){
			return false
		}else if(mainFrame.g && mainFrame.g('YT_LOAD_MSG')){
			return false;
		}	else{
			 mainFrame.addTab(id,name,"../../"+url);
		}		
	}
</script>
	<script type="text/javascript" language="javascript"> 

function getSystemTime()
{
	   $.ajax({
			url: "${ctx}/login.shtml?action=getSysTime",
			type:"POST",
			dataType:"text",
			complete: function(xhr){
				eval("jsonResult = " + xhr.responseText);
				if (jsonResult.success === true) {
					
var strTitle="";
var strContent=jsonResult.messages;
var msgw,msgh,bordercolor; 
    msgw=400;//提示窗口的宽度 
    msgh=100;//提示窗口的高度 
    titleheight=25 //提示窗口标题高度 
    bordercolor="#336699";//提示窗口的边框颜色 
    titlecolor="#99CCFF";//提示窗口的标题颜色
    var sWidth,sHeight; 
    sWidth=document.body.offsetWidth; 
    sHeight=screen.height; 
    var bgObj=document.createElement("div"); 
    bgObj.setAttribute('id','bgDiv'); 
    bgObj.style.position="absolute"; 
    bgObj.style.top="0"; 
    bgObj.style.background="#777"; 
    bgObj.style.filter="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75"; 
    bgObj.style.opacity="0.6"; 
    bgObj.style.left="0"; 
    bgObj.style.width=sWidth + "px"; 
    bgObj.style.height=sHeight + "px"; 
    bgObj.style.zIndex = "10000"; 
    document.body.appendChild(bgObj);
    var msgObj=document.createElement("div") 
    msgObj.setAttribute("id","msgDiv"); 
    msgObj.setAttribute("align","center"); 
    msgObj.style.background="white"; 
    msgObj.style.border="1px solid " + bordercolor; 
    msgObj.style.position = "absolute"; 
    msgObj.style.left = "50%"; 
    msgObj.style.top = "50%"; 
    msgObj.style.font="12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif"; 
    msgObj.style.marginLeft = "-225px" ; 
    msgObj.style.marginTop = -75+document.documentElement.scrollTop+"px"; 
    msgObj.style.width = msgw + "px"; 
    msgObj.style.height =msgh + "px"; 
    msgObj.style.textAlign = "center"; 
    msgObj.style.lineHeight ="25px"; 
    msgObj.style.zIndex = "10001";
    var title=document.createElement("h4"); 
    title.setAttribute("id","msgTitle"); 
    title.setAttribute("align","right"); 
    title.style.margin="0"; 
    title.style.padding="3px"; 
    title.style.background=bordercolor; 
    title.style.filter="progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);"; 
    title.style.opacity="0.75"; 
    title.style.border="1px solid " + bordercolor; 
    title.style.height="18px"; 
    title.style.font="12px Verdana, Geneva, Arial, Helvetica, sans-serif"; 
    title.style.color="white"; 
    title.style.cursor="pointer"; 
    title.title = "点击关闭"; 
    title.innerHTML="<table border='0′ width='100%'><tr><td align='left'><b>"+ strTitle +"</b></td><td>关闭</td></tr></table></div>"; 
    title.onclick=function(){ 
    document.body.removeChild(bgObj); 
    document.getElementById("msgDiv").removeChild(title); 
    document.body.removeChild(msgObj); 
    } 
    document.body.appendChild(msgObj); 
    document.getElementById("msgDiv").appendChild(title); 
    var txt=document.createElement("p"); 
    txt.style.margin="1em 0" 
    txt.setAttribute("id","msgTxt"); 
    txt.innerHTML=strContent; 
    document.getElementById("msgDiv").appendChild(txt); 

} else {
					parent.showErrorMsg(utf8Decode(jsonResult.messages));
				}
			}
		});
}

    function saleMssage(){
   	$.ajax({
			url: "",
			type:"POST",
			dataType:"text",
			complete: function(xhr){
				$("#saleMsg").text(xhr.responseText);
			}
		});
    }
    
  
</script>
</html>
