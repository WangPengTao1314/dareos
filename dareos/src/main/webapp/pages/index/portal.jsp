<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>门户主页</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css"	href="${ctx}/styles/${theme}/css/style.css">
<link rel="stylesheet" type="text/css"	href="${ctx}/styles/drp/css/portal.css">
<script type="text/javascript" src="${ctx}/scripts/core/jscharts_mb.js"></script>
<script type=text/javascript src="${ctx}/pages/index/portal.js"></script>
<script type="text/javascript"	src="${ctx}/scripts/echarts/echarts.min.js"></script>

<style type="text/css">
#mycredit_show {
	margin: 0px auto;
	width: 400px;
	border: 1px;
	z-index: 1;
	position: absolute;
	background-color: white;
	border: 1px solid black;
	height: 100px;
	left: 680px;
	top: 160px;
	text-align: center;
	display: block;
}

.text_underline {
	border-bottom-width: 1px;
	border-bottom-style: double;
	border-top-width: 0px;
	border-left-width: 0px;
	border-right-width: 0px;
	outline: medium;
	width: 150px;
}

.wenben {
	width: 80px;
	text-align: right;
}

.neirong {
	width: 150px;
	text-align: left;
}

.notice_content {
	margin: 0px auto;
	width: 282px;
	height: 230px;
	left: 871px;
	top: 30px;
	border: 1px;
	z-index: 1;
	position: absolute;
	background-color: white;
	border: 1px solid black;
	text-align: center;
	display: none;
}
.info_img{
	position: absolute;
    bottom: 0%;
    right: 15%;
}
 .position_img{
 	position: relative;
 	width:55px;
 }
 .notics span：hover{
 	color:red
 }
 a:hover {
    text-decoration: underline;
    color: #cc0000 !important;
}
</style>
</head>
<body onLoad="loadChart()">
	<div id="x_contents" class="x_content" style="overflow: auto; width: 100%;">		
		<div class="left_c" >			
			<div class="left_c_top">
			   	
                 <div class="left_c_T_t">
					<div class="position_img">
						<img src="${ctx}/styles/drp/images/zhexiantu.png" alt="" class="info_img">
					</div>
					<div class="left_c_T_Msg">
						<p class="left_c_T_Title">销售趋势</p>
						<input type="hidden"  id="ztxxs"  value="${ztxxs}" />
						<c:forEach items="${sumlist.authList}" var="rst" varStatus="row">                                
							<div id="ledger${rst.LEDGER_ID}" onClick="ledger_Name(${rst.LEDGER_ID});" class="zt_title" style="">${rst.LEDGER_NAME_ABBR}</div>
						</c:forEach>
						<c:if test="${empty sumlist.authList}">
							<div class="zt_title" >请联系管理员，设置账套分管！</div>
						</c:if>
					</div>
				</div>
				<div id="maintwo" style="width: 92%; height: 80%; margin: 5 auto;"></div>
			</div>
			<div class="left_c_down" >
			    <div class="left_c_T_t">
					<div class="position_img">
						<img src="${ctx}/styles/drp/images/zhuzhuangtu.png" alt="" class="info_img">
					</div>
					<div class="left_c_T_Msg">
						<p class="left_c_T_Title">产品销售排名</p>
					</div>
				</div>
				<div id="main" style="width: 92%; height: 80%; margin: 5 auto;"></div>
			</div>
		</div>
		<div class="right_c">
			<div class="right_c_T " style="height: 28%">
			    <div class="right_c_T_t">
					<div class="position_img">
						<img src="${ctx}/styles/drp/images/u32.png" alt="" class="info_img">
					</div>
					<div class="right_c_T_Msg">
						<p class="right_c_T_Title">我的待办</p>
					</div>
				</div>
					<div class="right_c_T_d rigth_c_T_Do"  style="overflow-x: auto;overflow-y: hidden;" >
		     			<c:forEach items="${toDoList}" var="rst" varStatus="row">                                
							<div class="rignt_c_T_DoT">
				     			<!--上面的部分-->
				     			<div style="position: relative;width: 80px;">
				     			    <a class="right_c_t_a" onclick="toDoList('${rst.menuId}','${rst.menuName}','${rst.menuUrl}')" >
					     				<img src="${ctx}/styles/drp/images/bag.png" alt="">
					     				<span class="message_data">${rst.cont}</span>
					     				<p>${rst.menuName}</p>
					     			</a>  
				     			</div>			  			
					     	</div>     
						</c:forEach>
						<c:if test="${empty toDoList}">
							<li style="line-height: 38px;margin-left: 36px;">暂无待办信息！</li>
						</c:if>
					</div>
			</div>

			<!--常用功能  -->
			<div class="right_c_T " style="height: 28%; margin-top: 10px; " >
			    <div class="right_c_T_t">
					<div class="position_img">
						<img src="${ctx}/styles/drp/images/u23.png" alt="" class="info_img">
					</div>
					<div class="right_c_T_Msg">
						<p class="right_c_T_Title">常用功能</p>
					</div>
				</div>
					<div class="right_c_T_d rigth_c_T_Do"  style="overflow-x: auto;overflow-y: hidden;" >
		     			<c:forEach items="${CommonFunc}" var="rst" varStatus="row">                                
							<div class="rignt_c_T_DoT">
				     			<!--上面的部分-->
				     			<div style="position: relative;width: 80px;">
				     			    <a class="right_c_t_a" onclick="CommonFunc('${rst.MENU_ID}','${rst.MENU_NAME}','${rst.MENU_URL}')" >
					     				<img src="${ctx}/styles/drp/images/audit.png" alt="">
					     				<p>${rst.MENU_NAME}</p>					     			
					     			</a>  
				     			</div>			  			
					     	</div>     
						</c:forEach>
					</div>
			</div>
			<div class="right_c_T" style="height: 39%;margin-top: 10px;">
				<div class="right_c_T_t">
					<div class="position_img">
						<img src="${ctx}/styles/drp/images/u22.png" alt="" class="info_img">
					</div>
					<div class="right_c_T_Msg">
						<p class="right_c_T_Title">通知公告</p>
						<a id="more_notice" name="target" label="公告信息查看"
								style="font-size: 14px; text-decoration: none;position: absolute;bottom: 5px;right: 10px;"
								href="javascript:listNotice();" menu="more_notice" >更多>></a>
					</div>
				</div>
				
				<div class="right_c_T_d"
					style="width: 100%; height: 63%;box-sizing: border-box; display: flex">
					<div style="width: 36px; height: 100%;"></div>
					<div style="width: 98%; height: 100%;">
                         <c:forEach items="${noticeList}" var="rst" varStatus="row">                                
                                  <a href="javascript:queryNotice('${rst.NOTICE_ID}');"
                                     label="公告信息查看"
                                     name="target" menu = "notice_detail" class="notics"
                                     style="display:block;font-size:14px;color:#203c70;margin-top: 1%"><span>${row.index+1}、 ${rst.NOTICE_TITLE}</span><span style="float:right;margin-right: 10px;">${rst.CRETIME}</span></a>
						</c:forEach>

						<c:if test="${empty noticeList}">
							<li style="line-height: 38px">暂无公告信息！</li>
						</c:if>
					</div>
				</div>
			</div>

		</div>
		<!--right_c  end  -->
	</div>

<script type="text/javascript">
//已完成销售订单月订货金额汇总
 var allInfo=[];
 var unfinished=[];
 var completed=[];
 var all=[];
 var dataT=[];
 
<c:forEach items="${sumlist.allInfo}" var="rst" varStatus="row">
    all.push('${rst}');
 	allInfo.push('${rst.TOTAL_AMOUNT}');
	unfinished.push('${rst.UNSENDED_AMOUNT}');
	completed.push('${rst.SENDED_AMOUNT}');
 	dataT.push('${rst.ORDER_DATE}');
</c:forEach> 

var dataNo=[];
var dataNum=[];
  <c:forEach items="${sumlist.orderRank}" var="rst" varStatus="row">
    dataNo.push('${rst.PRD_NAME}');
    dataNum.push('${rst.ORDER_NUM}');
</c:forEach> 
	dataNo.reverse();
	dataNum.reverse();
	//console.log('数量：'+dataNum);
 var myChart = echarts.init(document.getElementById('main'));
//款式订单明细下单排名
option = {
      textStyle:{
      	fontSize:14,
      	color:'#203c70',
      	fontWight:600,
      },
    
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
    	 top: '45',
    	 left: '15',
	     right: '45',
	     bottom: '10',
	     containLabel: true
    },
    xAxis : [
        {	name :'名称',
            type : 'category',
            data :dataNo,
            axisTick: {
                alignWithLabel: true
            },
            axisLabel : {
                interval : 0,
                formatter : function(params){
                	return params.replace(/.{6}(?!$)/g, (a) => a + '\n');                    
                }
            }
        }
    ],
    yAxis : [
        {	name :'数量',
            type : 'value'
        }
    ],
    series : [
        {
            name:'货品数量',
            type:'bar',
            barWidth: '30%',
            data:dataNum
        }
    ]
};

      // 使用刚指定的配置项和数据显示图表。
     myChart.setOption(option); 
/* ****************************************************************************************************************** */   
   var myChart = echarts.init(document.getElementById('maintwo'));
   
   var  option = {
            textStyle:{
            	fontSize:14,
            	fontWight:600,
            	color:'#203c70',
            },
      	    tooltip: {
      	        trigger: 'axis'
      	    },
      	    legend: {
      	        data:['全部','已发货','未发货'],
      	  		x: 'right'
      	    },
      	    grid: {
      	    	top: '45',
      	        left: '25',
      	        right: '45',
      	        bottom: '10',
      	        containLabel: true
      	    },
      	    toolbox: {
      	        feature: {
      	            //saveAsImage: {}
      	        }
      	    },
      	    xAxis: {
      	    	name:'日期',
      	        type: 'category',
      	        boundaryGap: false,
      	        data:dataT
      	        
      	    },
      	    yAxis: {
      	    	name:'金额',
      	        type: 'value'
      	    },
      	    series: [
      	        {
      	            name:'全部',
      	            type:'line',
      	            data:allInfo//[120, 132, 101, 134, 90, 230, 210,120, 132, 101, 134, 90]
      	        },
      	        {
      	            name:'已发货',
      	            type:'line',
      	            data:completed//[220, 182, 191, 234, 290, 330, 310,220, 182, 191, 234, 290]
      	        },
      	        {
      	            name:'未发货',
      	            type:'line',
      	            data:unfinished//[150, 232, 201, 154, 190, 330, 410,150, 232, 201, 154, 190]
      	        }
      	    ]
      	};
    
    myChart.setOption(option);
    

  </script>

</body>
</html>