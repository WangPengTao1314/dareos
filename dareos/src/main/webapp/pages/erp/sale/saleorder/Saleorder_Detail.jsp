<!--
 * @prjName:喜临门营销平台
 * @fileName:Goodsorder_Detial
 * @author zzb
 * @time   2013-08-30 15:55:09 
 * @version 1.1
-->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/jslibs.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/${theme}/css/style.css">
	<title>信息详情</title>
	<style type="text/css">
		.child{overflow-x: auto; width: 100%; }
	</style>
</head>
<body class="bodycss1">
	<div class="">
		<table width="100%" width="100%" border="0" cellpadding="0" cellspacing="0" class="detail">
			<tr>
				<td class="detail2">
					<table style="width:100%;padding: 9px 0% 0;">
						<tr>
							<td class="title">订单信息</td>
							<td align="right">
								<%-- <a href="#" onclick="parent.gotoBottomPage('${ctx}/sys/report/toSaleOrderReport')"><img src="${ctx}/styles/${theme}/images/icon/print.png" class="icon_font"></a> &nbsp;&nbsp;&nbsp;
								<a href="#" onclick="parent.gotoBottomPage('${ctx}/sys/report/toSaleOrderReportNoPrice')"><img src="${ctx}/styles/${theme}/images/icon/print.png" class="icon_font"></a> &nbsp;&nbsp;&nbsp; --%>
								<input type="button"  id="ipt"  value="收起" style="border:0px solid #ddd;color:#bfbfbf;outline:none;background-color:#fff;height:22px;" onclick='changeinput(this)'>
								<img src="${ctx}/styles/${theme}/images/main/shouqi.png" alt="" style="vertical-align: middle;" id="imgsz">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td class="detail2" id="toggleTable">
					<table cellspacing="0" cellpadding="0" class="detail3 linebreak"
						style="border-left:1px solid #d8dde6;border-right:1px solid #d8dde6;">
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
								<div class="dashed font-color">基本信息</div>
							</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>销售类型：</div>
								${rst.BILL_TYPE }
							</td>
							<td class="gdtd">
								<div>厂编：</div>
								${rst.FACTORY_NO}
							</td>
							<td class="gdtd">
								<div>处理类型：</div>
								<c:choose>
								<c:when test="${IS_DRP_LEDGER eq '1' }">
									<c:if test="${fn:contains(rst.PROESS_TYPE,'现货')==true}">现货</c:if>
									<c:if test="${fn:contains(rst.PROESS_TYPE,'现货')==false}">非现货</c:if>
								</c:when>
								<c:otherwise>${rst.PROESS_TYPE}</c:otherwise>
								</c:choose>
							</td>
							<td class="gdtd">
								<div>订货单号：</div>
								${rst.FROM_BILL_NO}
							</td>
						</tr>
						<tr>
							<td class="gdtd">
								<div>预计到货期：</div>
								${rst.PRE_RECV_DATE}
							</td>
							<td class="gdtd">
								<div>下单日期：</div>
								${rst.ORDER_DATE}
							</td>
							<td class="gdtd">
								<div>订单交期：</div>
								${rst.ORDER_DELIVERY_DATE}
							</td>
							<td class="gdtd">
								<div>订单图纸：<button type="button" class="layui-btn uploadFile" id="attPath" lay-data="{id:'attPath'}" disabled>上传</button></div>
								<%-- <input json="attPath" name="attPath" id="attPath" value="${rst.attPath}"> --%>
								<input type="hidden" json="attPath" name="attPath" id="hid_attPath" value="${rst.attPath}">
								<table class="layui-table" style="width:85%" id="view_attPath"></table>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<div>经销商备注：</div>
								<c:set value="${fn:split(rst.REMARK, '；') }" var="remark" />
								<c:forEach items="${ remark }" var="s">${ s }<br/></c:forEach>
							</td>
							<td colspan="2">
								<div>备注：</div>
								<c:set value="${fn:split(rst.REMARK2, '；') }" var="remark2" />
								<c:forEach items="${ remark2 }" var="s">${ s } <br/></c:forEach>
							</td>
						</tr>
						<tr>
							<c:if test="${'工程订单' eq rst.BILL_TYPE or '工程样品' eq rst.BILL_TYPE }">
							<td colspan="2">
								<div>工程项目：</div>
								${rst.PROJECT_NAME}
							</td>
							</c:if>
							<td class="gdtd SHOWPRICE" nowrap>
								<div>订单总金额：</div>
								${rst.TOTAL_AMOUNT}
							</td>
							<td class="gdtd SHOWPRICE" nowrap>
								<div>订单总返利：</div>
								${rst.TOTAL_REBATE}
							</td>
						</tr>
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
								<div class="dashed font-color">订货信息</div>
							</td>
						</tr>
						<tr class="yhzz">
							<td class="gdtd">
								<div>订单组织：</div>
								${rst.LEDGER_NAME}
							</td>
							<td colspan="2">
								<div>经销商名称：</div>
								${rst.CHANN_NAME}
							</td>
							<td class="gdtd">
								<div>标签打印名称：</div>
								${rst.PRINT_NAME}
							</td>
						</tr>
						<tr class="yhzz">
							<td class="gdtd">
								<div>业务部门：</div>
								${rst.SALEDEPT_NAME}
							</td>
							<td class="gdtd">
								<div>业务员：</div>
								${rst.SALE_NAME}
							</td>
						</tr>
						<tr>
							<td align="left" colspan="4" style="font-size: 16px; font-weight: 600;color: #a8afb3;overflow:hidden">
							<div class="dashed font-color">收货信息</div>
							</td>
						</tr>
						<tr class="ysfs">
							<td class="gdtd">
								<div>收货人：</div>
								${rst.PERSON_CON}
							</td>
							<td class="gdtd">
								<div>联系电话：</div>
								${rst.TEL}
							</td>
							<td class="gdtd" colspan="2">
								<div>收货地址：</div>
								${rst.RECV_ADDR}
							</td>
						</tr>
						<tr class="ysfs">
							<td class="gdtd">
								<div>客户姓名：</div>
								${rst.CUST_NAME}
							</td>
							<td class="gdtd">
								<div>客户电话：</div>
								${rst.CUST_TEL}
							</td>
							<td class="gdtd" colspan="2">
								<div>客户住址：</div>
								${rst.CUST_ADDR}
							</td>
						</tr>
						<tr class="ysfs" style="display: none;">
							<td class="gdtd">
								<div>运输方式：</div>
								${rst.TRANSPORT }
							</td>
							<td class="gdtd">
								<div>运输结算方式：</div>
								${rst.TRANSPORT_SETTLE}
							</td>
							<td class="gdtd" nowrap>
								<div>订单总金额：</div>
								${rst.TOTAL_AMOUNT}
								<input class="gdtd_select_input" type="hidden" value="${rst.TOTAL_AMOUNT}"
									json="TOTAL_AMOUNT" name="TOTAL_AMOUNT" id="TOTAL_AMOUNT" readonly disabled/>
							</td>
							<td class="gdtd">
								<div>返利总金额：</div>
								${rst.TOTAL_REBATE}
								<input class="gdtd_select_input" type="hidden" value="${rst.TOTAL_REBATE}"
									json="TOTAL_REBATE" name="TOTAL_REBATE" id="TOTAL_REBATE" readonly disabled/>
							</td>
						</tr>
						<c:if test="${not empty rst.DESIGNER_NAME}">
						<tr>
							<td class="gdtd">
								<div>分派设计师：</div>
								${rst.DESIGNER_NAME}
							</td>
							<td class="gdtd">
								<div>拆件记料表：<button type="button" class="layui-btn uploadFile" id="attPath_cj" lay-data="{id:'attPath_cj'}" disabled>上传</button></div>
								<input type="hidden" json="attPath_cj" name="attPath_cj" id="hid_attPath_cj" value="${rst.attPath_cj}">
								<table class="layui-table" style="width:85%" id="view_attPath_cj"></table>
							</td>
							<td class="gdtd">
							</td>
							<td class="gdtd">
							</td>
						</tr>
						</c:if>
					</table>
				</td>
			</tr>
		</table>
	<input type="hidden" id="ztid" value="${rst.LEDGER_ID}"/>
	<div class="detail" style="">
		<table style="float: left;padding: 9px 0% 0;">
			<tr>
				<td class="title">明细信息</td>
			</tr>
		</table>
	</div>
	<div class="bodycss1 child">
		<table width="99.5%" height="" border="0" cellspacing="0" cellpadding="0" class="panel">
			<tr>
				<td>
					<div class="lst_area">
							<table id="ordertb" width="100%" border="0" cellpadding="5" cellspacing="0" class="lst">
								<tr class="fixedRow">
									<th nowrap align="center" class="LMM">组号</th>
									<th nowrap align="center" class="" style="min-width: 100px;">产品编码</th>
									<th nowrap align="center" class="">产品名称</th>
									<th nowrap align="center" class="LMM">门洞尺寸</th>
									<th nowrap align="center" class="">规格尺寸</th>
									<th nowrap align="center" class="LMM LYGCG">材质</th>
									<th nowrap align="center" class="LMM LYGCG">颜色</th>
									<th nowrap align="center" class="LMM">推向</th>
									<th nowrap align="center" class="LMM">玻璃</th>
									<th nowrap align="center" class="LMM">其他</th>
									<th nowrap align="center" class="LMM">系列</th>
									<th nowrap align="center" class=" LYGCG">投影面积</th>
									<th nowrap align="center" class=" LYGCG">展开面积</th>
									<th nowrap align="center" class=" ">工程位置</th>
									<th  nowrap align="center" class="LMM">是否开锁孔</th>
									<th  nowrap align="center">附图号</th>
									<th nowrap align="center">图纸</th>
									<%-- <th nowrap align="center">处理类型</th> --%>
									<th nowrap align="center">是否返利</th>
									<th nowrap align="center">可用库存</th>
									<th nowrap align="center">数量</th>
									<th nowrap align="center">单位</th>
									<th nowrap align="center">已实发数量</th>
									<th nowrap align="center" class="SHOWPRICE">计算报价</th>
									<th nowrap align="center" class="SHOWPRICE">折扣率(%)</th>
									<th nowrap align="center" class="SHOWPRICE">返利</th>
									<th nowrap align="center" class="SHOWPRICE">最终报价</th>
									<th nowrap align="center" class="SHOWPRICE">金额</th>
									<th nowrap align="center">备注</th>
								</tr>
								<c:forEach items="${rstChild}" var="rst_child" varStatus="row">
								<tr>
									<td nowrap align="center" class="LMM">${rst_child.GROUP_NO}</td>
									<td nowrap align="center" class="">${rst_child.PRD_NO}</td>
									<td nowrap align="center" class="">${rst_child.PRD_NAME}</td>
									<td nowrap align="center" class="LMM">${rst_child.HOLE_SPEC}</td>
									<td nowrap align="center" class="">${rst_child.PRD_SPEC}</td>
									<td nowrap align="center" class="LMM LYGCG">${rst_child.PRD_QUALITY}</td>
									<td nowrap align="center" class="LMM LYGCG">${rst_child.PRD_COLOR}</td>
									<td nowrap align="center" class="LMM">${rst_child.PRD_TOWARD}</td>
									<td nowrap align="center" class="LMM">${rst_child.PRD_GLASS}</td>
									<td nowrap align="center" class="LMM">${rst_child.PRD_OTHER}</td>
									<td nowrap align="center" class="LMM">${rst_child.PRD_SERIES}</td>
									<td nowrap align="center" class=" LYGCG">${rst_child.PROJECTED_AREA}</td>
									<td nowrap align="center" class=" LYGCG">${rst_child.EXPAND_AREA}</td>
									<td nowrap align="center" class="">${rst_child.PRD_PLACE_TEXT}</td>
									<td nowrap align="center" class="LMM">${rst_child.IS_NO_LOCK_FLAG eq '1' ? '是' : '否' }</td>
									<td nowrap align="center" class="">${rst_child.FIGURE_NO}</td>
									<td nowrap align="center">
										<div style="float:left">
											<input type="hidden" id="hid_attPath${row.index }"  json="attPath" name="attPath" value="${ rst_child.attPath}" />
											<button type="button" class="layui-dtlbtn " id="attPath${row.index }" lay-data="{fileid:\'/a/\'}" title="上传附件" disabled >
											<img src="${ctx}/styles/${theme}/images/icon/upload.png" ></button>
										</div>
										<div id="view_attPath${row.index }"></div>
									</td>
									<%-- <td nowrap align="center">${rst.PROESS_TYPE}</td> --%>
									<td nowrap align="center">${rst_child.IS_NO_REBATE_FLAG eq '1' ? '是' : '否'}</td>
									<td nowrap align="center"></td>
									<td nowrap align="center">${rst_child.ORDER_NUM}</td>
									<td nowrap align="center">${rst_child.STD_UNIT}</td>
									<td nowrap align="center">${rst_child.SENDED_NUM}</td>
									<td nowrap align="center" class="SHOWPRICE">${rst_child.PRICE}</td>
									<td nowrap align="center" class="SHOWPRICE">${rst_child.DECT_RATE}</td>
									<td nowrap align="center" class="SHOWPRICE">${rst_child.REBATE_AMOUNT}</td>
									<td nowrap align="center" class="SHOWPRICE">${rst_child.DECT_PRICE}</td>
									<td nowrap align="center" class="SHOWPRICE">${rst_child.ORDER_AMOUNT}</td>
									<td nowrap align="center">
										<c:set value="${fn:split(rst_child.REMARK, '；') }" var="remark" />
										<c:forEach items="${ remark }" var="s">${ s } <br/></c:forEach>
									</td>
								</tr>
								</c:forEach>
								<c:if test="${empty rstChild}">
									<tr id="soDtlEmpty">
										<td style="height:42px;" colspan="28" align="center" class="lst_empty">
											&nbsp;无相关记录&nbsp;
										</td>
									</tr>
								</c:if>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
				<!-- 底部固定部分 -->
		<div style="width:100%;height:52px"></div>
		<div style="width:100%;padding:0.5%;background:#F8F8F8;text-align:center;position:fixed;bottom:0;word-spacing: 8px;">
							<button class="img_input SHOWPRICE">
								<label for='import'>
									<%-- <a href="#" >
									<img src="${ctx}/styles/${theme}/images/icon/print.png" class="icon_font"></a> &nbsp;&nbsp;&nbsp; --%>
									<input id="printPrice" type="button" class="btn" value="打印(含价格)">
								</label>
							</button>
							<button class="img_input">
								<label for='import'>
									<%-- <a href="#" >
									<img src="${ctx}/styles/${theme}/images/icon/print.png" class="icon_font"></a> &nbsp;&nbsp;&nbsp; --%>
									<input id="printNoPrice" type="button" class="btn" value="打印">
								</label>
							</button>
							<button class="img_input" >
				                <label onclick="closeDialog()">
				                    <img src="${ctx}/styles/${theme}/images/icon/chexiao.png"  class="icon_font">
				                    <input  type="button" class="btn" value="返回"  />
				                </label>
				            </button>
		</div>
	</div>
</body>
<%@ include file="/pages/common/uploadfile/uploadfile.jsp"%>
<script type="text/javascript">

var interval=setInterval(function(){
	if(uploadFile){
		clearInterval(interval);
		<c:forEach items="${rstChild}" var="rst_child" varStatus="row">
			//上传文件
			uploadFile('attPath${row.index }','',true,false,true,false,true);
		</c:forEach>
	}
},500);

$(function() {
	//上传文件
	displayDownFile('attPath',true);
	
	//上传文件-拆件计料表
	displayDownFile('attPath_cj',true);

	var lid = $("#ztid").val();//console.log('lid=',lid);
	// 根据订单组织控制明细字段列的显示/隐藏
	changeDtlCol(lid);
	// 根据是否可查看价格权限控制明细字段价格金额等列的显示/隐藏
	changeDtlPrice();

	$('.child :input').attr('disabled', 'disabled');

	$('#printPrice').click(function(){
		parent.gotoBottomPage('${ctx}/sys/report/toSaleOrderReport');
	});
	$('#printNoPrice').click(function(){
		parent.gotoBottomPage('${ctx}/sys/report/toSaleOrderReportNoPrice');
	});
});


function changeinput(e){
	 console.log(e)
		var newinput = document.getElementById('ipt')
		var newmytable =document.getElementById('toggleTable')
		if(newmytable.style.display == 'none'){
			newmytable.style.display = '';
			newinput.value="收起"
			imgsz.style.transform="rotate(0deg)"
		}else{
			newmytable.style.display='none';
			newinput.value = "展开";
			/*transform:rotate(180deg);*/
			imgsz.style.transform="rotate(180deg)"
	        
	    }

}


</script>
</html>