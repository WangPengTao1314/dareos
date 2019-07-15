<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
 <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
<!-- link rel="stylesheet" type="text/css" href="${ctx}/scripts/layui/css/layui.css"> -->
    <script type="text/javascript" src="${ctx}/scripts/layui/layui.js"></script>
	<style type="text/css">
		.layui-btn {
		    display: inline-block;
		    height: 30px;
		    line-height: 30px;
		    padding: 0 16px;
		    background-color: #1583e9;/*#009688;*/
		    color: #fff;
		    white-space: nowrap;
		    text-align: center;
		    font-size: 14px;
		    border: none;
		    border-radius: 2px;
		    cursor: pointer;
		}
		.layui-icon {
		    font-family: layui-icon!important;
		    margin-right: 3px;
		    font-size: 18px;
		    vertical-align: middle;
		    vertical-align: middle\9;
		    font-style: normal;
		    -webkit-font-smoothing: antialiased;
		    -moz-osx-font-smoothing: grayscale;
		}
		.uploadimg {
		    width: 16px;
		    height: 16px;
		}
		.layui-upload-file {
		    display: none!important;
		    opacity: .01;
		    filter: Alpha(opacity=1);
		}
		.layui-btn-xs {
		    height: 19px;
		    line-height: 22px;
		    padding: 0;
		    font-size: 12px;
		}		
		.layui-btn-danger {
		    background-color: #ffffff; 
		}
		.file-reload {
		    background-color: #ffffff;
		}
		.layui-table{
		    width: 100%;
		    border-spacing: 0;
		}
		.layui-table td {
		    position: relative;
		    padding:0;
		    min-height: 20px;
		    line-height: 20px;
		    font-size: 12px;		    
		}
		.layui-table .filename {
		    word-break:break-all;width:80%;line-height: 15px;border-bottom: 1px solid #D8DDE6;
		}
		.layui-table .option {
		    text-align:right;border-bottom: 1px solid #D8DDE6;	    
		}
		.layui-show{display:block!important}
		.layui-hide{display:none!important}
	</style>
</head>

<script type="text/javascript">
layui.use('upload', function(){
	var upload = layui.upload;  
	var uploadList = upload.render({
        elem: '.uploadFile'
    	,url: ctxPath+'/sys/uploadFile/upload' //上传接口
  	    ,field: "Filedata" 
  	    ,accept: 'file' //允许上传的文件类型
  	    ,size:35000//单位KB
  	    ,auto: true
  	    ,multiple:false
  		,choose: function(obj){
  		      var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
  		      var id=this.id; 
  		      //读取本地文件
  		      obj.preview(function(index, file, result){
 		    	var tr = $(['<tr id="upload-'+ index +'">'
 		    	  	,'<td align="center"></td>'
  		          	,'<td align="center"></td>'
  		        	,'<td align="center"></td>'
  		      		,'<td align="center"></td>'
  		          	,'<td align="center"></td>'
  		    		,'<td align="center"></td>'
  		  			,'<td align="center"></td>'
  					,'<td align="center"></td>'
  		          	,'<td align="center"></td>'
  		           // ,'<td align="center"></td>'
  		          	,'<td align="center">'
  		            ,'<button class="layui-btn layui-btn-xs layui-btn-danger file-delete"><img src="'+ctxPath+'/styles/newTheme/images/main/chahao.png" ></button>'
  		          ,'</td>'
  		        ,'</tr>'].join(''));
  		        //单个重传
  		        tr.find('.file-reload').click(function(){
  		          obj.upload(index, file);
  		        });
  		        
  		        //删除
  		        tr.find('.file-delete').click(function(){
  		          delete files[index]; //删除对应的文件
  		          tr.remove();
  		          uploadList.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
  		        });	        
  		        $('#view_'+id).append(tr);
  		      });
  		    }
  		    ,done: function(res,index,upload){
  		    	var id=this.id;
  		    	 $('#view_'+this.id).find('.lst_empty').remove();
  		    	if(res.code == 0){ //上传成功
  		            var tr = $('#view_'+this.id).find('#upload-'+ index)
  		            ,tds = tr.children();
  		            tds.eq(1).html('<a href="javascript:void(0)" onclick=\"downloadFile(\''+res.filePath+'\')\">'+res.fileName+'</a>');
  		            tds.eq(4).find('.file-reload').addClass('layui-hide'); 
  		            //tds.eq(1).html(''); //清空操作
  		            addObj(id,res.filePath,false);

  		            tr.find('.file-delete').click(function(){tr.remove();deleteObjs(id,res.filePath);});	  
  		            return delete this.files[index]; //删除文件队列已经上传成功的文件
  		          }
  		          this.error(index, upload);
  		    }
  		    ,error: function(index, upload){
  		      var tr = $('#view_'+this.id).find('#upload-'+ index)
  		       ,tds = tr.children();
  		       //tds.eq(1).html('<span style="color: #FF5722;">上传失败</span>');
  		       tds.eq(1).find('.file-reload').removeClass('layui-hide'); //显示重传
  		      //请求异常回调
  		      //showErrorMsg('上传失败！');
  		    }
        });	  
});
function toJSONStr(str) {
	str=str.replaceAll('{','{"');
	str=str.replaceAll('}','"}');
	str=str.replaceAll('=','":"');
	str=str.replaceAll(', ','","');
	str=str.replaceAll('}","{','},{');
	return str;
}
//展示文件下载链接,多个文件请用逗号隔开
function displayDownFile(id,paths,url){
   var result = JSON.parse( toJSONStr(paths) );
   var fileListView = getObj('view_'+id);
   var num=0;
   for(var i in result){
	   var att_id=result[i].ATT_ID;
       var path=result[i].ATT_PATH;
       var name=result[i].CRE_NAME;
       var time=result[i].CRE_TIME;
       var count=Number(i)+1;
       
       //addObj(id,path,false);
       var _fileName=path.substring(path.lastIndexOf('/')+1,path.length);
       
       if(path.indexOf('_')>0&&(path.substring(path.lastIndexOf('_'),path.lastIndexOf('.'))).length==9){
          _fileName=path.substring(path.lastIndexOf('/')+1,path.lastIndexOf('_'));
       }else{
         _fileName=path.substring(path.lastIndexOf('/')+1,path.lastIndexOf('.'));
       }           
       var _fileType=path.substring(path.lastIndexOf('.'),path.length);
       var myDate = new Date();
       var _fileKey=myDate.getTime()+i;
       /*
       var tr = $(['<tr id="upload-'+ _fileKey +'">'
                  ,'<td align="center">'+ count +'</td>'
                  ,'<td hidden="true" id="att_ids-'+_fileKey+'">'+att_id+'</td>'
     	          ,'<td align="center"><a href="javascript:void(0)" onclick=\"downloadFile(\''+path+'\')\">'+_fileName+_fileType+'</a></td>'
     	          ,'<td align="center">'+ name +'</td>'
     	          ,'<td align="center">'+ time +'</td>'
     	          ,'<td align="center">'
     	            ,'<button class="layui-btn layui-btn-xs layui-btn-danger file-delete'+_fileKey+'" onclick="deleteObjs('+id+','+_fileName+','+att_id+')"><img src="'+ctxPath+'/styles/newTheme/images/main/chahao.png"></button>'
     	          ,'</td>'
     	        ,'</tr>'].join('')); */

       var filePath=this;
       //删除
	   
       //tr.find(".file-delete"+_fileKey).click(function(){
		   //alert('操作开始！！！');
	//	   tr.remove();
		   //att_id = tr[0].childNodes[1].innerHTML;
		   //deleteObjs(id,_fileName,att_id);
		//});
		 
       
       //fileListView.append(tr);
       num=num+1;
	}
	/* if(num==0){
		var tr = $(['<tr class="lst_empty"><td height="25" colspan="13" align="center" class="lst_empty">&nbsp;无附件信息&nbsp;</td></tr>'].join('')); 
		fileListView.append(tr);
	} */
}
//添加对象
function addObj(id,filePath,isSingle){
    var obj = $("#hid_"+id);
    var _url=obj.val();
    if(_url.indexOf(filePath)==-1)
    {
        if(isSingle){
              obj.attr("value",filePath);
          }else{
              _url=_url+","+filePath+",";
              _url=_url.replaceAll(",,",",");
              obj.attr("value",_url.replace(/^,*|,*$/g,'') );
          }
    }
    //
}
//删除对象
function deleteObjs(id,fileName,att_id){
    var obj = $("#hid_"+id);
    var _url=obj.val();
    //console.log('8888:'+'${pageContext.request.contextPath}');
    if(att_id !=null){
	    deleteSingleFile(att_id);
    }
    if(_url.indexOf(fileName)>=0)
    {
      _url=_url.replaceAll(fileName+",","");
      if(_url.indexOf(fileName)>=0)
      {
       _url=_url.replaceAll(fileName,"");
      }
      //alert(id+":"+fileName+":"+_url+"-----"+_url.replace(/^,*|,*$/g,''));
      obj.attr("value",_url.replace(/^,*|,*$/g,'') );
    }
}

function getObj(id){
   var obj = $("#"+id);
   return obj; 
}

function getFileImg(_fileType){
   if(_fileType.toUpperCase()=='.DOC')
   {
      return 'word.png';
   }
    if(_fileType.toUpperCase()=='.DOCX')
   {
      return 'word.png';
   }
   if(_fileType.toUpperCase()=='.XLS'||_fileType.toUpperCase()=='.XLSX')
   {
      return 'excel.png';
   }
   if(_fileType.toUpperCase()=='.PDF')
   {
      return 'pdf.png';
   }
   if(_fileType.toUpperCase()=='.TXT'||_fileType.toUpperCase()=='.SQL')
   {
      return 'text.png';
   }
   if(_fileType.toUpperCase()=='.RAR'||_fileType.toUpperCase()=='.ZIP')
   {
      return 'rar.png';
   }
   if(_fileType.toUpperCase()=='.PPT'||_fileType.toUpperCase()=='.PPTX')
   {
      return 'ppt.png';
   }
   if(_fileType.toUpperCase()=='.AVI'||_fileType.toUpperCase()=='.RMVB'||_fileType.toUpperCase()=='.3GP')
   {
      return 'film.png';
   }
   if(_fileType.toUpperCase()=='.BMP'||_fileType.toUpperCase()=='.JPG'||_fileType.toUpperCase()=='.JPEG'||_fileType.toUpperCase()=='.PNG'||_fileType.toUpperCase()=='.GIF')
   {
      return 'picture.png';
   }
   
   return 'other.png';
   
}


/**
function deleteFile(folder,fileName){
   if(folder==null||folder==''||fileName==null||fileName=='')
   {
     showErrorMsg("没有文件需要删除!");
     return false;
   }
   document._downloadFileForm.secPath.value=folder;
   document._downloadFileForm.fileName.value=fileName;
   document._downloadFileForm.action="${ctx}/fileuploadhelper.shtml?action=deleteFile";
   document._downloadFileForm.submit();
}
**/

//文件下载
function downloadFile(folder,id){
   var fileName=path.substring(folder.lastIndexOf('/')+1,folder.length);
   fileName=fileName+new Date().getTime();
   id.attr("value",fileName);
   if(folder==null||folder==''||fileName==null||fileName=='')
   {
     showErrorMsg("没有文件需要下载!");
     return false;
   }
   document._downloadFileForm.secPath.value=folder;
   document._downloadFileForm.fileName.value=fileName;
   document._downloadFileForm.action=ctxPath+'/sys/uploadFile/download';
   document._downloadFileForm.submit();
}
//文件下载
function downloadFile(filePath){
   if(filePath==null||filePath=='')
   {
     showErrorMsg("没有文件需要下载!");
     return false;
   }
   document._downloadFileForm.fileName.value=filePath;
   document._downloadFileForm.action=ctxPath+'/sys/uploadFile/download';
   document._downloadFileForm.submit();
}
//多文件压缩下载
function mulFileDown(jsonParams){
   if(jsonParams==null||jsonParams=='[]')
   {
     showErrorMsg("没有文件需要下载!");
     return false;
   }
   document._downloadFileForm.action="${ctx}/fileuploadhelper.shtml?action=mulFileDown";
   document._downloadFileForm.params.value=jsonParams;
   document._downloadFileForm.submit();
}

//通用单附件删除函数
function deleteSingleFile(ids){
	$.ajax({
		url: "${pageContext.request.contextPath}/project/tendering/deleteFile",
		type:"POST",
		dataType:"text",
		data:{"att_ids":ids},
		complete: function(xhr){
			eval("jsonResult = "+xhr.responseText);
			if(jsonResult.success===true){
				parent.showMsgPanel("删除成功");
				//checkBox.parent().parent().remove();
				//设置删除操作。有删除时返回后需要刷新页面。否则直接跳转上一页。
				//$("#delFlag").val("true");
			}else{
				parent.showErrorMsg(utf8Decode(jsonResult.messages));
			}
		}
	});
	
}



//function uploadFile(id,folder,isdown,ismulupl,autoCommit,callback){}

</script>
<iframe id='_hiddenIframe' name='_hiddenIframe'  style='display:none'></iframe>
<form name="_downloadFileForm" method="post" target="_hiddenIframe" >
<input  type="text" name="fileName"  style="display:none" value=""/>&nbsp;
<input  type="text" name="params"  style="display:none" value=""/>&nbsp;
</form>
</html>