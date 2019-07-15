<!-- 
 *@module 分销业务
 *@func 人员信息维护
 *@version 1.1
 *@author lyg
 *  -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<%@ include file="/commons/qxcommon.jsp"%>
<%@page import="com.centit.commons.model.Consts"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<title>购物车</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<script language="JavaScript" src="${ctx}/pages/common/select/selCommJs.js"></script>
	<script type="text/javascript" src="${ctx}/pages/drp/main/shopcar/Shopcar_OrderInfo.js"></script>
	<style type="text/css">
	
		.span_num{
			color: red;
			font-size:12px;
		}
		.text_underline{
			border-bottom-width:0px;
			border-bottom-style:double;
			border-top-width:0px;
			border-left-width:0px;
			border-right-width:0px;
		}
		.spanTitle{
			font-weight: bold;
			font-size: 15px;
			display:block;
			margin-left:30px;
		}
		table{
			/* border: 1px solid #a9a9a9; */
			width: 100%;
			
		}
		input,select,textarea{
			width:95%;
		}
		input[disabled] {
    		background-color: #ebebe4;
}

input{
	height:30px;
}
select{
	height:30px;
}
.dashed{border-bottom: 1px solid #e8edf2;width: 98%; margin-top: 4px;}	
.title_Style{
	float:left;
	font-size:16;
	font-weight: 600;
	border-top: 0px;
	color: #54698d;
}
#jsontb tr td span
{
    width: 100% !important;
    float: left !important;
    overflow: hidden !important;
    text-overflow: ellipsis !important;
    white-space: normal !important;
}
	</style>
</head>
<body  class="bodycss1">
	<div style="width:97%;padding:2% 0 0 1%;margin:0 auto;border-bottom: 1px solid #e8edf2">订货信息
		<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="position:relative;right:2%;vertical-align: middle;float:right" id="imgsz">
		<input type="button"  id="ipt"  value="收起" style="border:0px;color:#bfbfbf;outline:none;background-color:#fff;width:5%;height:22px;text-align:left;float:right;margin-top: -4px;" onclick='changeinput(this)'>
	</div>
		
	<div id="topDiv" style="border: 1px solid #e8edf2;width:98%;margin:0 auto;position:relative">
	<form method="POST" action="#" id="mainForm" >
		<table class="detail3" id="channInfo" style="padding:0 2%;background:#fff">
			<tr>							
							<td align="left" colspan="4" style="font-size: 14px; font-weight: 600;color: #a8afb3;overflow:hidden">
							<span class="title_Style">订货单位信息</span>
							<div class="dashed">&nbsp</div>
						</tr>		
			<tr>
				<td>
					<table height="46px">
						<tr>
							<td >
									<span style="margin-left:10px" > 订货单位信息：${rst.CHANN_NAME} </span>
							</td> 
							<c:forEach items="${moneyList}" var="rst" varStatus="row">
								<td>
										${rst.LEDGER_NAME_ABBR }：<a href="#" style="color: red;">￥${rst.AMOUNT_MONEY + rst.AMOUNT_REBATE + rst.AMUNT_CREDIT -rst.FREEZ_MONEY - rst.FREEZ_REBATE-rst.FREEZ_CREDIT-rst.OWE_CREDIT } </a>
								</td> 
							</c:forEach>
							
							<td  >
									  <img id="shopcar_img" src="${ctx}/styles/${theme}/images/main/scxiangxiajiantou.png" alt="" onclick="opentable(this)">
							</td> 
							
						</tr>
						<tr class="shopcar_opentable" style="display:none">
						<td></td>
						<c:forEach items="${moneyList}" var="rst" varStatus="row">
					<td>
						资金金额：<a href="#" style="color: red;">${rst. AMOUNT_MONEY-rst.FREEZ_MONEY}</a>
					</td> 
				</c:forEach>
						
						
						
						</tr>
						<tr class="shopcar_opentable"  style="display:none">
						<td></td>
							<c:forEach items="${moneyList}" var="rst" varStatus="row">
					<td>
						信用余额：<a href="#" style="color: red;">${rst. AMUNT_CREDIT -rst.FREEZ_CREDIT-rst.OWE_CREDIT}</a>
					</td> 
				</c:forEach>
						
						</tr>
						<tr class="shopcar_opentable" style="display:none">
						<td></td>
							<c:forEach items="${moneyList}" var="rst" varStatus="row">
					<td>
						返利余额：<a href="#" style="color: red;">${rst. AMOUNT_REBATE -rst.FREEZ_REBATE}</a>
					</td> 
				</c:forEach>
						
						
						</tr>
					</table>
				</td>
			</tr>
			<tr>							
							<td align="left" colspan="4" style="font-size: 14px; font-weight: 600;color: #a8afb3;overflow:hidden">
							<span class="title_Style">订货收货方信息</span>
							<div class="dashed">&nbsp</div>
						</tr>	
			<tr>
			
				<td>
						<table  height="95px">
							<tr>
								<td class="gdtd detail_content">
									<div>收货方名称：</div>
									<input class="gdtd_select_input" type="hidden" id="CHANN_ID" json="CHANN_ID" name="CHANN_ID" value="${params.CHANN_ID }" />
									<input class="gdtd_select_input" type="hidden" id="CHANN_NO" json="CHANN_NO" name="CHANN_NO" value="${params.CHANN_NO }" />
									<input class="gdtd_select_input" type="text"    style=""  json="CHANN_NAME" name="CHANN_NAME" id="CHANN_NAME"  autocheck="true" label="收货方名称" disabled="disabled" inputtype="string"  value="${params.CHANN_NAME }" READONLY/>
								</td>
								<td  class="gdtd">
									<div>运输方式：</div>
									<select class="gdtd_select_input" json="TRANSPORT" id="TRANSPORT" name="TRANSPORT" style=''border:1px solid #a9a9a9;height:25px">
									</select> 
								</td>
								<td  class="gdtd">
									<div>运输结算方式：</div>
									<input  class="gdtd_select_input" name="TRANSPORT_SETTLE" type="text" json="TRANSPORT_SETTLE" autocheck="true" id="TRANSPORT_SETTLE" inputtype="string" maxlength="30" value="${rst.TRANSPORT_SETTLE}">
								</td>
								<td  class="gdtd"></td>	
							</tr>
							<tr>
							<td class="gdtd" >
									<div>联系人：</div>
									<input class="gdtd_select_input" json="PERSON_CON"   name="PERSON_CON" autocheck="true" id="PERSON_CON" type="text"  inputtype="String"  maxlength="30" value="${params.PERSON_CON }">
								</td>
								
								<td  class="gdtd">
									<div>联系电话：</div>
									<input class="gdtd_select_input" name="TEL" type="text"  json="TEL" autocheck="true" id="TEL" inputtype="string"  maxlength="30" value="${params.TEL}">
								</td>
								
								<td class="gdtd" colspan="2">
									<div>详细地址：</div>
									<input class="gdtd_select_input" type="text"  id="RECV_ADDR" json="RECV_ADDR" disabled="disabled"  name="RECV_ADDR"  autocheck="true" inputtype="string"  size="250"   maxlength="250"   label="详细地址"  value="${params.ADDRESS}" style="width:96%"/>
								</td>
							</tr>
						</table>
				</td>
			</tr>
			<tr>							
				<td align="left" colspan="4" style="font-size: 14px; font-weight: 600;color: #a8afb3;overflow:hidden">
				<span style="float:left">客户信息</span>
				<div class="dashed">&nbsp</div>
			</tr>	
			<tr>
				<td>
					<table height="95px">
						<tr>
							<td class="gdtd">
								<div>客户姓名：</div>
									<input  class="gdtd_select_input" json="CUST_NAME" name="CUST_NAME"  autocheck="true" id="CUST_NAME" type="text" mustinput="true" inputtype="String" maxlength="30"      label="客户姓名"     />
							</td>
								<td class="gdtd">
									<div>联系电话：</div>
									<input class="gdtd_select_input" json="CUST_TEL" name="CUST_TEL" autocheck="true" id="CUST_TEL" type="text" mustinput="true" inputtype="String"  maxlength="30"  label="联系电话" />
								</td>
							    <td class="gdtd" colspan="2">
									<div>客户详细地址：</div>
									<input class="gdtd_select_input" type="text" style="width:96%"  id="CUST_ADDR" json="CUST_ADDR"  name="CUST_ADDR"  autocheck="true" inputtype="string"  size="250" mustinput="true"  maxlength="250" label="客户详细地址"   />
								</td> 
							</tr>
						 <tr>
							<td class="gdtd">
								<div>附件上传：</div>
									<button type="button" class="layui-btn uploadFile attPathUploader" id="ATT_PATH" lay-data="{id:'ATT_PATH'}">上传</button>
													<input type="hidden"  json="ATT_PATH" name="ATT_PATH" id="hid_ATT_PATH" value="${ rst.ATT_PATH}">				
													<table class="layui-table" style="width:85%" id="view_ATT_PATH"></table>
								</td>
							<td class="gdtd" colspan="2">
									<div>备注：</div>
									<textarea class="gdtd_tb_textarta" id="REMARK" name="REMARK" json="REMARK" style="width:96%"></textarea>
								</td>
								<td class="gdtd" ></td>
							</tr>
						</table>
				</td>
			</tr>	
		</table>
		<%-- <table <!-- id="shopcar_opentable" --> style="border:1px solid #D8DDE6;width:70%;position:absolute;top:100px;left:23%;z-index:999;padding: 1% 0 1% 3%;display:none;background-color:#fff">
			<tr>
				<c:forEach items="${moneyList}" var="rst" varStatus="row">
					<td>
						资金金额：<a href="#" style="color: red;">${rst. AMOUNT_MONEY-rst.FREEZ_MONEY}</a>
					</td> 
				</c:forEach>
			</tr>
			<tr>
				<c:forEach items="${moneyList}" var="rst" varStatus="row">
					<td>
						信用余额：<a href="#" style="color: red;">${rst. AMUNT_CREDIT -rst.FREEZ_CREDIT-rst.OWE_CREDIT}</a>
					</td> 
				</c:forEach>
			</tr>
			<tr>
				<c:forEach items="${moneyList}" var="rst" varStatus="row">
					<td>
						返利余额：<a href="#" style="color: red;">${rst. AMOUNT_REBATE -rst.FREEZ_REBATE}</a>
					</td> 
				</c:forEach>
			</tr>
		
		
		</table> --%>
		</form>
	</div>
	<div id="bottomdiv" style="width: 100%;z-index:10;position:absolute;height: 50%">
		<!-- 下帧 -->
<!-- 		<iframe id="bottomcontent" name="bottomcontent" src="#" frameBorder=0  width="100%" height="100%" style="z-index:-1;position:absolute;" frameborder="0" scrolling="AUTO"></iframe> -->
<input id="delFlag" type="hidden" value="false"/>
 <input id="REBATEDSCT" name="REBATEDSCT" label="返利折扣" type="hidden" value="${REBATEDSCT}">
 <input id="LARGESSDSCT" name="LARGESSDSCT" label="赠品折扣" type="hidden" value="${LARGESSDSCT}">
 <input id="REBATE_TOTAL" name="REBATE_TOTAL" label="返利总金额" type="hidden" value="${REBATE_TOTAL}">
 <input id="REBATE_CURRT" name="REBATE_CURRT" label="当前返利" type="hidden" value="${REBATE_CURRT-REBATE_FREEZE}">
 
 <input id="DECT_RATE" type="hidden" value="${params.DECT_RATE}">
	<div style="/* overflow-x: auto; overflow-y: auto; height: 95%;  */width:100%">
	<table width="99.8%" height="100%" border="0" align="center" cellspacing="0" cellpadding="0" class="panel">
		<tr>
			<td height="20px" valign="top">
		      <table id="qxBtnTb"  cellSpacing=0 cellPadding=0 border=0 style="padding:1%" width="100%">
				<tr style="padding-bottom: 5px;">
				  
					<td align="right" style="width: 70%">
<!-- 					 <span style="font-weight: bolder;">总金额：</span><font id="total">0</font> -->
<!-- 					 <span style="font-weight: bolder;">总数量：</span><font id="allNum">0</font> -->
					 	<%-- <c:if test="${pvg.PVG_EDIT eq 1 }">
					 <input id="addPrd" style="margin-left: 5px;width: 100px;" type="button" class="btn" value="生成订货订单"  >
					 </c:if> --%>
					</td>
					
					 <td nowrap style="padding-left: 3px; width: 5%">
				   	<c:if test="${pvg.PVG_DELETE eq 1 }">
					   <input id="delete" type="button" class="btn" value="删除" >
					 </c:if>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td valign="top" colspan="2" >
				<div class="lst_area" style="margin-left:3px;">
					<form onsubmit="return false;">
					   <input type="hidden" id="CHANN_ID"  value="${CHANN_ID}"/>
					   <input type="hidden" id="picture_url" name="" value="${picture_url}" />
						<table width="100%" border="0" cellpadding="1" cellspacing="1" id="jsontb" class="lst">
							<tr class="fixedRow">
								<th nowrap="nowrap" align="center" ><input type="checkbox" style="height:12px;valign:middle" id="allChecked" /></th>
								<th nowrap="nowrap" align="center">图片</th>
								<th nowrap="nowrap" align="center">货品信息</th>
								<th nowrap="nowrap" align="center">规格属性</th>
								<th nowrap="nowrap" align="center">单位</th>
<!-- 								<th nowrap="nowrap" align="center">参考单价</th> -->
								<th nowrap="nowrap" align="center">数量</th>
<!-- 								<th nowrap="nowrap" align="center">金额</th> -->
								<th nowrap="nowrap" align="center" >操作</th>
							</tr>
							<c:forEach items="${list}" var="rst" varStatus="row">
								<c:set var="r" value="${row.count % 2}"></c:set>
								<c:set var="rr" value="${row.count}"></c:set>
								<tr class="list_row${r}" onmouseover="mover(this)" onmouseout="mout(this)" onclick="selectThis(this);setEidChecked(document.getElementById('eid${rr}'));">
								<td width="1%"align='center' >
									<input type="checkbox" style="height:12px;valign:middle" name="eid" id="eid${rr}" value="${rst.SHOP_CART_ID}"  />
								 </td>
								  <td nowrap="nowrap" align="center">
				                  	${rst.PIC_PATH}&nbsp;
				                  </td>
				                  <td nowrap="nowrap" align="center">
				                  		${rst.PRD_NAME}
								  </td>
				                  <td nowrap="nowrap"  id="spec${rr}" align="center">
				                  		<span>
					                  		${rst.HOLE_SPEC}&nbsp;
					                  		${rst.PRD_SIZE}&nbsp;
											${rst.PRD_QUALITY}&nbsp;
											${rst.PRD_COLOR}&nbsp;
											${rst.PRD_TOWARD}&nbsp;
											${rst.PRD_GLASS}&nbsp;
											${rst.PRD_OTHER}&nbsp;
											${rst.PRD_SERIES}&nbsp;
											-----${rst.HOLE_SPEC}
										</span>
				                  	<input type="button" value="..."  style="background-color:#199ed8; border:none; width:26px; height:26px;float:right" onclick="openSpec(this,'${rr}')"></input>
				                  </td>
				                  <td nowrap="nowrap" align="center" >
				                  	${rst.STD_UNIT}
				                  </td>
<%-- 				                  <td nowrap="nowrap"  align="center" id="price${rr}"> --%>
<%-- 				                  	${rst.PRICE} --%>
<!-- 				                  </td> -->
				                  <td nowrap="nowrap" align="center">
				                  	<input  type="text" id="ORDER_NUM${rr }" json="ORDER_NUM"  name="ORDER_NUM" value="${rst. ORDER_NUM}"
<%-- 				                  	 onkeyup="countAmount('${rr }')"   --%>
				                  	style="text-align:center;width: 80px;" />&nbsp;
				                  </td>
<%-- 				              		<td id="amount${rr}"> --%>
<%-- 				              			${rst.ORDER_AMOUNT} --%>
<!-- 				              		</td> -->
						          <td>
						          	<a href="#" onclick="delShopCar('${rst.SHOP_CART_ID}',this)">删除</a>
<%-- 						          	<input type="hidden" name="ORDER_AMOUNT" json="ORDER_AMOUNT" value="${rst.ORDER_AMOUNT}" /> --%>
						          	<input type="hidden" name="SHOP_CART_ID" json="SHOP_CART_ID" value="${rst.SHOP_CART_ID}" />
						          	<input type="hidden" name="PRD_ID" value="${rst.PRD_ID}"/>
<%-- 							          <input type="hidden" name="PRICE" value="${rst.RET_PRICE_MIN}"/> --%>
							          <input type="hidden" name="HOLE_SPEC" value="${rst.HOLE_SPEC}"/>
							          <input type="hidden" name="PRD_QUALITY" value="${rst.PRD_QUALITY}"/>
							          <input type="hidden" name="PRD_TOWARD" value="${rst.PRD_TOWARD}"/>
							          <input type="hidden" name="PRD_GLASS" value="${rst.PRD_GLASS}"/>
							          <input type="hidden" name="PRD_OTHER" value="${rst.PRD_OTHER}"/>
							          <input type="hidden" name="PRD_SERIES" value="${rst.PRD_SERIES}"/>
							          <input type="hidden" name="PRD_SIZE" value="${rst.PRD_SIZE}"/>
							          <input type="hidden" name="ATT_PATH" value="${rst.ATT_PATH}" />
							          <input type="hidden" name="REMARK" value="${rst.REMARK}" />
							          <input type="hidden" name="PRD_COLOR" value="${rst.PRD_COLOR}"/>
							          <input type="hidden" name="LEDGER_ID" value="${rst.LEDGER_ID}" />
						          </td>
							    </tr>
							</c:forEach>
							<c:if test="${empty list}">
								<tr>
									<td height="25" colspan="16" align="center" class="lst_empty">
						                &nbsp;无相关记录&nbsp;
									</td>
								</tr>
							</c:if>
						</table>
					</form>
				</div>
			</td>
		</tr>
	</table>
	</div>
	<div style="width:100%;height:52px;"></div>
	<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
		<c:if test="${pvg.PVG_EDIT eq 1 }">
					 <input id="addPrd" style="margin-left: 5px;width: 100px;" type="button" class="btn" value="生成订货订单"  >
					 </c:if>
			
	</div>
	</div>
	
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script language="JavaScript">
    function divShow(id){
    	document.getElementById(id).style.display="";
    }
    SelDictShow("TRANSPORT","BS_185","","");
    displayDownFile('ATT_PATH',true,false,true);

  
   function opentable(e){
	   var newimgid =  document.getElementById('shopcar_img')
	   var newtableid =  $('.shopcar_opentable')
	   for(var i=0;i<newtableid.length;i++){
		   if(newtableid[i].style.display == 'none'){
			   newtableid[i].style.display = '';
		   }else{
			   newtableid[i].style.display="none";
			   
		   } 
	   }
	
   } 
   
   $('#main').click(function(){
	   
   })
   
    
</script>
</html>