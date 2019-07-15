<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<style>
	input.btn {
	border: 1px solid #a9c2c9;
/* 	background-image: url(../images/btn_bg.jpg); */
	height: 22px;
	cursor: pointer;
	background-repeat: repeat-x;
}
</style>
<div class="btnBar" style="width:100%;height:30px;" >
  <ul class="left" >
     <li class="toggleBg borderRight" style="width: 20%">
      <ul class="fileOper" >
        <!--<li><a class="ICOhover" href="#" onClick="report1_print();return false;"><span title="打印" class="print"></span></a></li>-->
        <li><a class="ICOhover" href="#" onClick="report1_saveAsExcel();return false;"><span title="导出excel" class="excel"></span></a></li>
        <li><a class="ICOhover" href="#" onClick="report1_saveAsPdf();return false;"><span title="导出pdf" class="pdf"></span></a></li>
       <!--<li><a class="ICOhover" href="#" onClick="report1_saveAsWord();return false;"><span title="导出word" class="word"></span></a></li>-->
       <!--<li><a class="ICOhover" href="#" onClick="report1_pivot();return false;"><span title="数据透视" class="operator"></span></a></li> -->
       </ul>
    </li>
    <li class="floatRight borderLeft">
      <ul class="fileOper">
         <Li><a class="ICOhover" href="#" onClick="try{report1_toPage( 1 );}catch(e){}return false;"><span title="首页" class="begin"></span></a></li>
        <li><a class="ICOhover" href="#" onClick="try{report1_toPage(report1_getCurrPage()-1);}catch(e){}return false;"><span title="上一页" class="pre"></span></a></li>
        <Li><a class="ICOhover" href="#" onClick="try{report1_toPage(report1_getCurrPage()+1);}catch(e){}return false;"><span title="下一页" class="next"></span></a></li>
        <li><a class="ICOhover" href="#" onClick="try{report1_toPage(report1_getTotalPage());}catch(e){}return false;"><span title="尾页" class="end"></span></a></li>    
      </ul>
    </li>
    <li class="floatRight">  <div style="display:inline-block; margin:9px 4px 3px 4px; float:left; ">共<span id="t_page_span"></span>页/第<span id="c_page_span"></span>页&nbsp;&nbsp;</div></li>
  </ul>
  <!-- 报表路径 -->
  <input type="hidden" id="rptModel" value="${param.rptModel}">
  <!-- sql参数 -->
  <input type="hidden" id="params" value="${param.params}">
  <!-- 报表名称 -->
  <input type="hidden" id="RPT_NAME" value="${param.RPT_NAME}">
  <!-- 判断通用选取是否删除过 -->
  <input type="hidden" id="flag" value="0"/>
<script type="text/javascript">
	$("#goback").click(function(){
		history.go(-1);
	});
</script>
</div>
