//设置滚动条的宽度
		var width=window.screen.width*0.65-10;//弹出窗口的宽度
		var height=window.screen.height*0.65-4;//弹出窗口的高度
		$("#report_1").css({width: width,height: height});
		$(function(){
		     $("div.report_1").remove();
		});
	
	  function _editorKeyPress(val){
//		  console.log(val.srcElement.id)
		  var editId = val.srcElement.id;
		  //验证正整数
		  var reg = /^[1-9]\d*$/; 
		  var numReg = /\d+/g;//过滤数字
		  var letReg = /^[A-Z]+/g;//过滤英文
		  var reporIndex = editId.indexOf("editBox");
		  var reportId=editId.substring(0,reporIndex);
		  //编辑框ID
		  var editInp=editId;
		
		  if($("#"+editInp).val()!=""){
			  var editInpVal = $("#"+editInp).val();
			  if (!reg.test(editInpVal)) {  
				  $("#"+editInp).val("0")
              }  
		  }
		 
		  //编辑单元格ID
		  var editCellId=val.path[0].editingCell.id;
		  //单元格坐标
		  var cell=editCellId.substr(reportId.length,editCellId.length);
		  //横向单元格号
		  var cellLet=cell.match(letReg).join(',');
		  //竖向单元格号
		  var cellNum=cell.match(numReg).join(',');
		  
		  //获取累计当前数量
		  //序号+1为显示的累计数量，序号+2为页面刚加载时初始化的数量
		  var totalNum=$("#"+reportId+cellLet+(parseInt(cellNum)+2)).text();
		  if(totalNum==null || totalNum=="" || isNaN(totalNum)){
			  totalNum = 0;
		  }
		  totalNum=parseInt(totalNum)
		  //获取输入框填报数字
		  var input=$("#"+editInp).val();
		  input=input==""?0:isNaN(input)?0:parseInt(input);
		  $("#"+reportId+cellLet+(parseInt(cellNum)+1)).text(input+totalNum);
		
	  }	
	  
	  function _setEditorStyle( editor, cell ) {
		if( editor == null ) return;
		var x = cell.offsetLeft, y = cell.offsetTop;
		var obj = cell.offsetParent;
		var offsetP;
		if( editor.style ) offsetP = editor.offsetParent;
		else offsetP = editor.Table.offsetParent;
		while( obj != null && obj != offsetP ) {
			x += obj.offsetLeft + obj.clientLeft;
			y += obj.offsetTop + obj.clientTop;
			obj = obj.offsetParent;
		}
		var dx, dy;
		var isScroll = document.getElementById( cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) + "_contentdiv" ) != null;
		if( isScroll ) {
			var div = cell.parentNode;
			while( div.tagName != "DIV" || div.id == "div_" + cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) ) div = div.parentNode;
			x = x - div.scrollLeft;
			y = y - div.scrollTop;
			dx = div.offsetLeft;
			dy = div.offsetTop;
			obj = div.offsetParent;
			while( obj != null && obj != offsetP ) {
				dx += obj.offsetLeft;// + obj.clientLeft;
				dy += obj.offsetTop;// + obj.clientTop;
				obj = obj.offsetParent;
			}
		}
		var currentStyle = window.getComputedStyle(cell, null);
		switch( cell.getAttribute( "editStyle" ) ) {
			case "2":
			case "3":
				if( currentStyle.backgroundColor != "transparent" ) editor.setBackColor( currentStyle.backgroundColor );
				else editor.setBackColor( "white" );
				editor.setFontWeight( currentStyle.fontWeight );
				editor.setFontColor( currentStyle.color );
				editor.setFontFace( currentStyle.fontFamily );
				editor.setFontItalic( currentStyle.fontStyle );
				editor.setFontSize( currentStyle.fontSize );
				editor.setLeft( x - 2 );
				editor.setTop( y - 2 );
				editor.setWidth( cell.offsetWidth + 2 );
				editor.setHeight( cell.offsetHeight );
				editor.toggleOptions();
				break;
			default:
				editor.style.textAlign = currentStyle.textAlign;
				if( currentStyle.backgroundColor != "transparent" ) editor.style.backgroundColor = currentStyle.backgroundColor;
				else editor.style.backgroundColor = "white";
				editor.style.paddingLeft = currentStyle.paddingLeft;
				editor.style.paddingRight = currentStyle.paddingRight;
				editor.style.fontWeight = currentStyle.fontWeight;
				editor.style.color = currentStyle.color;
				editor.style.fontFamily = currentStyle.fontFamily;
				editor.style.fontStyle = currentStyle.fontStyle;
				editor.style.fontSize = currentStyle.fontSize;
				editor.style.textDecoration = currentStyle.textDecoration;
				editor.style.verticalAlign = currentStyle.verticalAlign;
				editor.style.left = x - 2+'px';
				editor.style.top = y - 2+'px';
				editor.style.width = cell.offsetWidth + 1+'px';
				editor.style.height = cell.offsetHeight + 1+'px';
				editor.focus();
		}
		if( isScroll ) {
			if( y < dy || y + cell.offsetHeight > dy + div.offsetHeight - 16 ) {
				cell.scrollIntoView( false );
				_reportScroll( cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) );
			}
			if( x < dx ) {
				div.scrollLeft -= dx - x + 1;
				_reportScroll( cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) );
			}
			if( x + cell.offsetWidth > dx + div.offsetWidth - 16 ) {
				div.scrollLeft += x + cell.offsetWidth - dx - div.offsetWidth + 17;
				_reportScroll( cell.id.substring( 0, cell.id.lastIndexOf( "_" ) ) );
			}
		}
	}