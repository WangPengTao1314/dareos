<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- 
 *@module 系统管理
 *@func 文件上传下载 
 *@version 1.0
 *@author 乔建胜
 -->
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">

function closeExeclDiv(){
	$("#importExeclDiv").css("display","none");
	$("#_Import_Execl").remove();
}
var index;
function importExecl(id){
	if(null==id){
		id="import";
	}
	var obj = $("#"+id);
	obj.click(function(){/* 
		if("block"!=$("#importExeclDiv").css("display")){
			$("#execlChoose").append("<input type='text' id='_Import_Execl'/> ");
			//导入初始化
			uploadFile('_Import_Execl','EXECL_PATH');
			$("#importExeclDiv").css("display","block");
			
		}else{
			closeExeclDiv();
		} */
		$("#_Import_Execl").click();
	});
}
importExecl("import");

//③创建fileLoad方法用来上传文件
function fileLoad(ele){
    //④创建一个formData对象
    var formData = new FormData();
    //⑤获取传入元素的val
    var name = $(ele).val();
    //⑥获取files
    var files = $(ele)[0].files[0];
    //⑦将name 和 files 添加到formData中，键值对形式
    formData.append("Filedata", files);
    formData.append("name", name);
    console.log('$(ele)', $(ele));
    var Filedata = $(ele)[0].files[0];
    $.ajax({
        url: ctxPath+'/sys/uploadFile/upload', //上传接口
        type: 'POST',
        data: formData,
        //data: {Filedata: Filedata},
        processData: false,// ⑧告诉jQuery不要去处理发送的数据
        contentType: false, // ⑨告诉jQuery不要去设置Content-Type请求头
        beforeSend: function () {
            //⑩发送之前的动作
            //alert("我还没开始发送呢");
        },
        success: function (responseStr) {
           //11成功后的动作
           var res = eval ("(" + responseStr + ")");
           console.log('res:',res);
           if(res.code == 0){ //上传成功
               parseExcel(res.filePath);
               $("#_Import_Execl").val('');
           }
        },
        error : function (responseStr) {
            //12出错后的动作
            //alert("出错啦");
            console.log();
        }
    });
}
$(function () {
    var $input =  $("#_Import_Execl");
    // ①为input设定change事件
    $input.change(function () {
        // ②如果value不为空，调用文件加载方法
        if($(this).val() != ""){
            index = layer.load(1);
            fileLoad(this);
        }
    })
})
</script>
<div id='importExeclDiv' style='display:none'>
<iframe id='_hiddenIframe' name='_hiddenIframe' style='display:none'></iframe>
<form name="_downloadFileForm" method="post" target="_hiddenIframe" action="">
<input  type="text" name="secPath" style="display:none" value=""/>&nbsp;
<input  type="text" name="fileName"  style="display:none" value=""/>&nbsp;
<input  type="text" name="params"  style="display:none" value=""/>&nbsp;
</form>
<form id="importExcel" method="post" target="_hiddenIframe" enctype="multipart/form-data">
<input type="file" name="up" id="_Import_Execl" value=""accept=".xls,.xlsx" />
<input type="submit" value="导入">
</form>
</div>
