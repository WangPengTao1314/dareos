<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
        <title>门户主页</title>
        <meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/styles/drp/css/portal.css">
		<script type="text/javascript" src="${ctx}/scripts/core/jscharts_mb.js"></script>
		<script type=text/javascript src="${ctx}/pages/index/drpFirst.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/echarts/echarts.min.js"></script>
		
<style type="text/css">
 		#mycredit_show{
	margin: 0px auto; 
	width:400px;
	border: 1px;
	z-index:1;
	position:absolute;
	background-color: white;
	border:1px solid black;
	height:100px;
	left:680px;
	top:160px;
	text-align:center;
	display: block;
}
.text_underline{
	border-bottom-width:1px;
	border-bottom-style:double;
	border-top-width:0px;
	border-left-width:0px;
	border-right-width:0px;
	outline:medium;
	width:150px;
}
.wenben{
	width: 80px;
	text-align: right;
}
.neirong{
	width:150px;
	text-align:left;
}


.notice_content{
	margin: 0px auto;   
	width:282px;
	height:230px;
	left:871px;
	top:30px;
	border: 1px;
	z-index:1;
	position:absolute;
	background-color: white;
	border:1px solid black;
	text-align:center;
	display: none;
}
.news_time{
display: inline-block;
    font-size: 16px;
    position: relative;
    float: right;
    margin-top: 1%;
    margin-right: %8;
    margin-right: 2%;
}
	</style>
</head>
<body>
 <div class="x_content" style="overflow: auto;width:100%;" >
  	<div class="left_c">
  		<div class="left_c_top"  style="width:98%;height:48%;border:1px solid #d3e5f3;margin:1%;display:flex;justify-content:center;align-items:center;">
  			<div id="main" style="width:92%;height:86%;margin:0 auto;"></div>
  		</div>
  		<div class="left_c_down"  style="width:98%;height:48%;border:1px solid #d3e5f3;margin:1%;display:flex;justify-content:center;align-items:center;">
  			<div id="maintwo" style="width:92%;height:86%;margin:0 auto;"></div>
  		</div>
  	</div>
  	
   <%-- <div class="report_div" >
         
	     <div id="report_div2" class="report_tital">
	     <p id='report_menu'>
	    	   <c:if test="${qxcom.XT0013301 eq 1 }">
	    	   <span id='sale_report' align='center' style="cursor:pointer"><B>线下加盟商出库</B></span>
	    	   </c:if>
	    	   <c:if test="${qxcom.XT0013302 eq 1 }">
	    	   <span id='advc_report' align='center' style="cursor:pointer"><B>线下加盟商订货</B></span>
	    	   </c:if>
	    	   <c:if test="${qxcom.XT0013303 eq 1 }">
	    	   <span id='dstr_report' align='center' style="cursor:pointer"><B>线下直营办销售</B></span>
	    	   </c:if>
	    	   <c:if test="${qxcom.XT0013304 eq 1 }">
	    	   <span id='dstr_report_main' align='center' style="cursor:pointer"><B>直营办销售</B></span>
	    	   </c:if>
	    	   <c:if test="${qxcom.XT0013305 eq 1 }">
	    	   <span id='chann_report' align='center' style="cursor:pointer"><B>渠道相关</B></span>
	    	    </c:if>
	    	</p>
	    	<iframe id='report_frame' src=""   frameBorder=0 width="100%" height="95%" frameborder="0" marginheight="0" marginwidth="0"  scrolling="auto"></iframe>
	     </div> 
  </div> --%>

 
    <div class="right_c">
     	<div class="right_c_T" style="width:94%;height:30%;border:1px solid #d3e5f3;margin-top:2%;margin-left:3%;box-sizing: border-box;">
     		<div class="right_c_T_t" style="width:100%;height:19%;display:flex;">
     			<div style="width:12%;height:100%">
     				<img src="${ctx}/styles/drp/images/wodedaiban.png" alt="" style="margin-top:16%;margin-left:16%">
     			</div>
     			<div style="width:85%;height:140%;border-bottom: 1px solid #b3b6b7;">
     				<p style="color:#515568;font-size:14px;font-weight:900;margin-top:5%">我的待办</p>
     			</div>
     		</div>
	     	<div class="right_c_T_d" style="width:100%;height:70%;box-sizing: border-box;display:flex">
	     		
	     		<div style="width:33%;height:100%;">
	     			<img src="${ctx}/styles/drp/images/bag.png" alt="" style="position: absolute;top:12%; left:70.5%;" >
	     			<a href="" color="red" style="position: absolute;top: 19%; left: 72%;">123</a>
	     			<input type="button" value="待处理订单" style="position: absolute;top: 23%;left: 69.4%;width:7%;background-color:#fef1d5;border:1px solid #d3e5f3">
	     		</div>
	     		<div style="width:33%;height:100%;">
	     			<img src="${ctx}/styles/drp/images/audit.png" alt="" style="position: absolute;top:12%; left:79.6%;">
	     			<a href="" color="red" style="position: absolute;top: 19%; left: 81.5%;">123</a>
	     			<input type="button" value="待处理问题" style="position: absolute;top: 23%;left: 79%;width:7%;background-color:#fef1d5;border:1px solid #d3e5f3">
	     		</div>
	     		<div style="width:33%;height:100%;">
	     			<img src="${ctx}/styles/drp/images/send.png" alt="" style="position: absolute;top:12%; left:89%;">
	     			<a href="" color="red" style="position: absolute;top: 19%; left: 90.3%;">123</a>
	     			<input type="button" value="待发货" style="position: absolute;top: 23%;left: 88%;width:7%;background-color:#fef1d5;border:1px solid #d3e5f3">
	     		</div>
	     </div>
     	</div>
     
     <!--常用功能  -->
     
     	<div class="right_c_M" style="width:94%;height:40%;border:1px solid #d3e5f3;margin:3%;box-sizing: border-box;">
     		<div class="right_c_M_t" style="width:100%;height:12.5%;display:flex;">
     	  		<div style="width:12%;height:100%">
     				<img src="${ctx}/styles/drp/images/changyonggongneng.png" alt="" style="margin-top:14%;margin-left:16%">
     	  		</div>
     			<div style="width:85%;height:150%;border-bottom: 1px solid #b3b6b7;">
     				<p style="color:#515568;font-size:14px;font-weight:900;margin-top: 5%;">常用功能</p>
     			</div>
     		</div>
     	 <div class="right_c_M_d" style="width:100%;height:77%;box-sizing: border-box;display:flex;flex-wrap:wrap">
     		<div style="width:12%;height:50%;"></div>
     		<div style="width:29%;height:50%;">
     			<img src="${ctx}/styles/drp/images/send.png" alt="" style="position: absolute;top:43%; left:70.5%;">
     			<input type="button" value="项目管理" style="position: absolute;top: 51%;left: 70.4%;background-color:#fff;border:none">
     		</div>
     		<div style="width:29%;height:50%;">
     			<img src="${ctx}/styles/drp/images/send.png" alt="" style="position: absolute;top:43%; left:79.6%;">
     			<input type="button" value="项目管理" style="position: absolute;top: 51%;left: 79.5%;background-color:#fff;border:none">
     		</div>
     		<div style="width:29%;height:50%;">
     			<img src="${ctx}/styles/drp/images/send.png" alt="" style="position: absolute;top:43%; left:89%;">
     			<input type="button" value="项目管理" style="position: absolute;top: 51%;left: 88.9%;background-color:#fff;border:none">
     		</div>
     		<div style="width:10%;height:50%;"></div>
     		<div style="width:29%;height:50%;">
     			<img src="${ctx}/styles/drp/images/send.png" alt="" style="position: absolute;top:57%; left:70.5%;">
     			<input type="button" value="项目管理" style="position: absolute;top: 65%;left: 70.4%;background-color:#fff;border:none">
     		</div>
     		<div style="width:29%;height:50%;">
     			<img src="${ctx}/styles/drp/images/send.png" alt="" style="position: absolute;top:57%; left:79.6%;">
     			<input type="button" value="项目管理" style="position: absolute;top: 65%;left: 79.5%;background-color:#fff;border:none">
     		</div>
     		
     		
     </div> 
    
     
     </div> 
     
     
    <div class="right_c_B" style="width:94%;height:23.5%;border:1px solid #d3e5f3;margin-left:3%">
     	<div class="right_c_T_t" style="width:100%;height:28%;;display:flex;">
          	<div style="width:12%;height:100%">
     			<img src="${ctx}/styles/drp/images/tongzhi.png" alt="" style="margin-top:12%;margin-left:12%">
     		</div>
     		<div style="width:85%;border-bottom: 1px solid #b3b6b7;">
     			<p style="color:#515568;font-size:14px;font-weight:900;margin-top: 4%;">
                    通知公告
					<a id="more_notice" name="target"
					   label="公告信息查看" style="font-size:16px;text-decoration:none;float: right;"
					   href="javascript:void(0);"
					   menu = "more_notice"
					   url="/sys/drpFirst/listNotice">更多>></a>
                </p>

     		</div>
     	</div>
     	<div class="right_c_T_d" style="width:100%;height:72%;;box-sizing: border-box;display:flex">
     		<div style="width:13%;height:100%;"></div>
     		<div style="width:85%;height:100%;">
     			<%--<a href="" style="display:block;font-size:16px;color:#203c70;margin-top: 6%;"></a>
     			<a href="" style="display:block;font-size:16px;color:#203c70">关于调整内设机构及职能的通知</a>--%>
			<%-- 		<c:forEach items="${noticeList}" var="rst" varStatus="row">
						<c:choose>
							<c:when test="${row.index eq 0}">
								<a href="${ctx}/sys/first/queryNoticeById?NOTICE_ID=${rst.NOTICE_ID}" style="display:block;font-size:16px;color:#203c70;margin-top: 6%;">${row.index+1}、 ${rst.NOTICE_TITLE}</a>
							</c:when>
							<c:otherwise>
                                <a href="${ctx}/sys/first/queryNoticeById?NOTICE_ID=${rst.NOTICE_ID}" style="display:block;font-size:16px;color:#203c70">${row.index+1}、 ${rst.NOTICE_TITLE}</a>
							</c:otherwise>
						</c:choose>


					</c:forEach> --%>
					
					 <c:forEach items="${noticeList}" var="notice" varStatus="row">
			           <c:set var="rr" value="${row.count}"></c:set>
			             <input type="hidden" id="" name="NOTICE_ID" value="${notice.NOTICE_ID}"/>
			             <span style="display: block;padding: 0px 0px 2px 0px;">
			              <a href="${ctx}/sys/first/queryNoticeById?NOTICE_ID=${rst.NOTICE_ID}" id="a_title_${rr}" name="a_title_"  
			              style="display:inline-block;font-size:16px;color:#203c70;margin-top: 1%;">${row.index+1}、${notice.NOTICE_TITLE}</a> 
			              <p	 style="display:inline-block" class="news_time"  > ${notice.STATIME}</p>
			             </span><%--
			             <span style="display:inline-block;position:absolute;width: 400px; overflow: hidden; text-overflow:clip; white-space:nowrap;">${notice.NOTICE_TITLE}</span>
			              <span style="float: right;margin-left: 100px;"><a href="javascript:void(0);" onclick="showFile('${notice.NOTICE_ID}','${notice.ATT_PATH}');">附件</a></span>--%>
			             
			         </c:forEach>
								
					
					
					
					<c:if test="${empty noticeList}">
						<li>暂无公告信息！</li>
					</c:if>
     		</div>
     	</div>
     </div>
     
 </div>   <!--right_c  end  -->
</div>     <!--x_content  end  -->
  
      <%-- <div class="news" id="news">
       <div class="news_h agency_h" ><p>公告信息</p>
        <span><a id="more_notice" name="target"  menu="gg-0000" label="公告信息查看" 
        style="font-size:16px;text-decoration:none" href="drpFirpage.shtml?action=toMoreNotice">more...</a></span> 
        </div>
        
      <div class="news_c" id="news_c">
        <ul id="ul_notice">
         <c:forEach items="${noticeList}" var="notice" varStatus="row">
           <c:set var="rr" value="${row.count}"></c:set>
            <li>
             <input type="hidden" id="" name="NOTICE_ID" value="${notice.NOTICE_ID}"/>
             <p><a href="javascript:void(0);" id="a_title_${rr}" name="a_title_">${notice.NOTICE_TITLE}</a></p>
             <span style="float: right;margin-left: 100px;">
             </span>
             <span  class="news_time" >${notice.STATIME}</span>
            </li>
         </c:forEach>
         <c:if test="${empty noticeList}">
           <li>暂无公告信息！</li>
         </c:if>
        </ul>
      </div>   --%>
  
    <%-- <div class="agency">
      <div class="agency_h">
        <p>待办工作</p>
      </div>
      <table id="first" width="100%" border="0" cellpadding="1" cellspacing="1" class="lst">
		<tr class="fixedRow_" height="25px" >
			<th style="text-align:center;" >待办事项</th>
			<th style="text-align:center;" >待办事项数</th>
			<th style="text-align:center;" >状态</th>
		</tr>
		<c:forEach items="${list}" var="bean" varStatus="row">
			<c:set var="r" value="${row.count % 2}"></c:set>
			<c:set var="rr" value="${row.count}"></c:set>
				<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)">
					<td>
						&nbsp;<a href="javascript:void(0)" onclick="parent.addTab('${ bean.MENU_ID}','${ bean.MENU_NAME}','${ctx}/${bean.MENU_URL }')">${bean.MENU_NAME}</a>&nbsp;
					</td>
					<td align="center"> ${bean.NUM}&nbsp; </td>
					<td align="center"> 待审批&nbsp; </td>
				</tr>
		</c:forEach>
		<c:if test="${empty list}">
			<tr height="25px"> 
			  <td colspan="4">
					暂无待办事项
			 </td>
			</tr>
		</c:if>
	</table>
    </div> --%>
  
  
   
   <!-- <div class="notice_content" id="notice_content">
	    <div title="关闭" class="closer" onclick="closeNoticeContent();"></div>
	    <div>
	         <div class="ncon_l" style="overflow-y:auto; ">
	          <h3 id="h3_notice_title"></h3>
	          <p id="p_notice" > </p>
	        </div>
        </div>
  </div> -->
  
<%-- <%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<div id="mycredit_show" style="background-color: #F1F4FB;display: none;word-wrap: break-word; ">
    	<h4 align="center" style="margin-top: 5px" id="fileDown_title">附件下载</h4>
    	<table>
    	 <tr>
    	  <td>
    	  <input type="hidden" name="ATT_PATH" id="ATT_PATH" value=""/>
    	  </td>
    	 </tr>
    	</table>
    	
	    <input style="margin-top: 10px" id="close" class="btn" type="button" value="关闭" onclick="closePage();"/>
</div> --%>





<script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
/*         app.title = '堆叠柱状图';
 */ 
var option = {
		 title: {
             text: '堆叠柱状图'
         },
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    legend: {
        data:['搜索引擎','百度','谷歌','必应','其他']
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis : [
        {
            type : 'category',
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'直接访问',
            type:'bar',
            data:[320, 332, 301, 334, 390, 330, 320]
        },
        {
            name:'邮件营销',
            type:'bar',
            stack: '广告',
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'联盟广告',
            type:'bar',
            stack: '广告',
            data:[220, 182, 191, 234, 290, 330, 310]
        },
        {
            name:'视频广告',
            type:'bar',
            stack: '广告',
            data:[150, 232, 201, 154, 190, 330, 410]
        },
        {
            name:'搜索引擎',
            type:'bar',
            data:[862, 1018, 964, 1026, 1679, 1600, 1570],
            markLine : {
                lineStyle: {
                    normal: {
                        type: 'dashed'
                    }
                },
                data : [
                    [{type : 'min'}, {type : 'max'}]
                ]
            }
        },
        {
            name:'百度',
            type:'bar',
            barWidth : 5,
            stack: '搜索引擎',
            data:[620, 732, 701, 734, 1090, 1130, 1120]
        },
        {
            name:'谷歌',
            type:'bar',
            stack: '搜索引擎',
            data:[120, 132, 101, 134, 290, 230, 220]
        },
        {
            name:'必应',
            type:'bar',
            stack: '搜索引擎',
            data:[60, 72, 71, 74, 190, 130, 110]
        },
        {
            name:'其他',
            type:'bar',
            stack: '搜索引擎',
            data:[62, 82, 91, 84, 109, 110, 120]
        }
    ]
};

        // 使用刚指定的配置项和数据显示图表。
       myChart.setOption(option); 
  /* ****************************************************************************************************************** */   
  var myChart = echarts.init(document.getElementById('maintwo'));
      var  option = {
    		  title: {
                  text: '堆叠曲线图'
              },
        	    tooltip: {
        	        trigger: 'axis'
        	    },
        	    legend: {
        	        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
        	    },
        	    grid: {
        	        left: '3%',
        	        right: '4%',
        	        bottom: '3%',
        	        containLabel: true
        	    },
        	    toolbox: {
        	        feature: {
        	            saveAsImage: {}
        	        }
        	    },
        	    xAxis: {
        	        type: 'category',
        	        boundaryGap: false,
        	        data: ['周一','周二','周三','周四','周五','周六','周日']
        	    },
        	    yAxis: {
        	        type: 'value'
        	    },
        	    series: [
        	        {
        	            name:'邮件营销',
        	            type:'line',
        	            stack: '总量',
        	            data:[120, 132, 101, 134, 90, 230, 210]
        	        },
        	        {
        	            name:'联盟广告',
        	            type:'line',
        	            stack: '总量',
        	            data:[220, 182, 191, 234, 290, 330, 310]
        	        },
        	        {
        	            name:'视频广告',
        	            type:'line',
        	            stack: '总量',
        	            data:[150, 232, 201, 154, 190, 330, 410]
        	        },
        	        {
        	            name:'直接访问',
        	            type:'line',
        	            stack: '总量',
        	            data:[320, 332, 301, 334, 390, 330, 320]
        	        },
        	        {
        	            name:'搜索引擎',
        	            type:'line',
        	            stack: '总量',
        	            data:[820, 932, 901, 934, 1290, 1330, 1320]
        	        }
        	    ]
        	};
      
 
      myChart.setOption(option);
        
    </script>

</body>
</html>