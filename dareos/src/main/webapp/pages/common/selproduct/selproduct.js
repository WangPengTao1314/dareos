/*
 * @v3.1 edit by zhuw 2011-12-2
 */
var selProdArgs = new Array();//传递给通用选取的参数
var preSearch = true;//是否需要预先查询,默认是

/*
 * 通用选取调用函数
 * @param id      必选项 通用查询序号
 * @param type    必选项 选择类型
 * @param keyElem 必选项 存放主键的元素
 * @param key     必选项 主键对应的字段
 * @param frmName 必选项 表单名
 * @param elems   必选项 存放选取的值得元素名
 * @param fields  必选项 选取的字段'
 * @param cnName            可选项 存放查询条件的元素名
 * @param isShowSearchLayer 可选项 是否显示搜索层
 * @param specialTable      可选项 特殊情况下的表名,直接替换通用选取中的表名
 * @param specialAsTable    可选项 特殊情况下的表别名,和参数specialTable配套使用
 * @param initCondName      可选项 存放初始化的查询条件的元素
 * @return  返回一个数组 第一个元素存放的是选择的记录数，第二个元素存放的是记录是否改变 ??有问题，期待改进
 */
function selProduct(ledger_id, type, keyElem, key, frmName, elems, fields,cnName, showModel, specialTable, specialAsTable, initCondName){
	selProductOpen(ledger_id, type, keyElem, key, frmName, elems, fields, window.name,cnName, showModel, specialTable, specialAsTable, initCondName);
}

/*
 * 通用选取调用函数
 * @param id      必选项 通用查询序号
 * @param type    必选项 选择类型
 * @param keyElem 必选项 存放主键的元素
 * @param key     必选项 主键对应的字段
 * @param frmName 必选项 表单名
 * @param elems   必选项 存放选取的值得元素名
 * @param fields  必选项 选取的字段'
 * @param fields  所在iframe的ID 自动获取
 * @param cnName            可选项 存放查询条件的元素名
 * @param isShowSearchLayer 可选项 是否显示搜索层
 * @param specialTable      可选项 特殊情况下的表名,直接替换通用选取中的表名
 * @param specialAsTable    可选项 特殊情况下的表别名,和参数specialTable配套使用
 * @param initCondName      可选项 存放初始化的查询条件的元素
 * @return  返回一个数组 第一个元素存放的是选择的记录数，第二个元素存放的是记录是否改变 ??有问题，期待改进
 */
function selProductOpen(id, type, keyElem, key, frmName, elems, fields, iframeId,cnName, showModel, specialTable, specialAsTable, initCondName){
	id = id+"";
	selProdArgs[0] = id;
	selProdArgs[1] = id.substring(id.indexOf("_")+1);
	selProdArgs[2] = type;
	selProdArgs[3] = keyElem;
	selProdArgs[4] = key;
	selProdArgs[5] = frmName;
	selProdArgs[6] = elems;
	selProdArgs[7] = fields;
	selProdArgs[8] = (cnName==null||cnName=="")?"":(iframeId==null||iframeId=="")?eval("document."+frmName+"."+cnName+".value"):window.frames[iframeId].eval("document."+frmName+"."+cnName+".value");
	selProdArgs[9] = specialTable==null?"":specialTable;
	selProdArgs[10] = "";
	selProdArgs[11] = specialAsTable==null?"":specialAsTable;
	//初始化搜索条件
	selProdArgs[12] = (initCondName==null||initCondName=="")?"":window.frames[iframeId].eval("document."+frmName+"."+initCondName+".value");
	selProdArgs[13] = iframeId
	
	returnarr = openProdDialog(ctxPath+"/common/selproduct/toSelPrd?ledger_id="+selProdArgs[0]+"&isShowSearchLayer="+selProdArgs[10]+"&specialTable="+selProdArgs[9]+"&showQYTree="+showModel);
	return returnarr;
}
/*
 * 移动焦点
 * @param isNext true:移动到下一条元素 false:移动到第一条
 */
function moveSelCommFocus(isNext, elem){
	var arrSelCommElems = selProdArgs[6].split(",");
	var arrKeyElems = selProdArgs[3].split(",");
	var frmName = selProdArgs[5];
	//移动焦点到下一条元素
	try{
		if(isNext){
			//按tabIndex移动
			moveFocus(eval("document." + frmName + "."+arrSelCommElems[arrSelCommElems.length-1]), eval("document." + frmName + ".elements"), true, true);
		}else{//移动焦点到第一条元素
		
			var arrObj1 = new Array();
			for(i=0;i<arrKeyElems.length;i++){
				arrObj1[i] = eval("document." + frmName + "."+ arrKeyElems[i]);
			}
			if(!moveFocus(null, arrObj1, true, true)){
				var arrObj2 = new Array();
				for(var i=0;i<arrSelCommElems.length;i++){
						arrObj2[i] = eval("document." + frmName + "."+ arrSelCommElems[i]);
				}
			  moveFocus(null, arrObj2, true, true);
			}
		}
	}catch(e){
		//
	}
}

/*
 * 检查通用选取元素数据是否正确
 * @param formName 表单名,默认为forms[0]
 * @param seltarget 通用选取组名(触发通用选取的元素的名字),默认即为所有组
 * @return 如果通用选取数据不正确的元素数组
 */
function doSelCommValidate(formName, seltarget){
	if(formName==null) formName = "forms[0]";//默认的表单
	try{
		if(seltarget!=null&&eval("document."+formName+".all('"+seltarget+"')")==null&&eval("document."+formName+"."+seltarget)==null){
			
			return new Array();
		}
	}catch(e){
	  

		return new Array();
	}
	var elems = eval("document."+formName+".elements");
	var elemArr = new Array();
	var j = 0;
	if(seltarget==null) seltarget = "";
	//查询出通用选取框数据不正确的元素
	for(var i=0;i<elems.length;i++){
		if(elems[i]!=null&&elems[i].seltarget!=""&&elems[i].seltarget!=null){
			if(elems[i].oldValue!=elems[i].value){
				elemArr[j++] = elems[i];
				break;
			}
		}
	}
	//如果没有不正确的则返回空数组
	if(elemArr==null||elemArr.length==0) return elemArr;
	//将数据不正确的元素同一组的通用选取的元素放到对象数组中
	for(var i=0;i<elems.length;i++){
		if(elems[i]!=null&&elems[i].seltarget!=""&&elems[i].seltarget!=null){
			if(elemArr[0].seltarget == elems[i].seltarget&&elemArr[0] != elems[i]){
			    
				elemArr[j++] = elems[i];
			}
		}
	}
	//返回对象数组
	return elemArr;
}

/*
 * 对通用选取数据不符的元素进行处理
 * @param elems 一组通用选取数组
 * @param operType 处理类型 0:弹出通用选取重新选取 1:恢复上次选择的值 2:清空
 */
function doSelComm(elems, operType){
	if(elems==null||elems.length==0) return;
	switch(operType){
		case 0:
		  //触发事件弹出通用选取框
			eval("elems[0].form."+elems[0].seltarget+".click()");
			break;
		case 1:
		  //将原值赋回给元素
			for(var i=0;i<elems.length;i++){
				elems[i].value=elems[i].oldValue;
			}
			break;
		case 2:
		  //将元素的值均置空
			for(var i=0;i<elems.length;i++){
				elems[i].value="";
				elems[i].oldValue="";
			}
			break;
			//默认弹出通用选取框
		default:
			eval("elems[0].form."+elems[0].seltarget+".click()");
	}
}
/**
 * 移动焦点
 * @param elem 焦点移动的起点,不包括本元素,默认从表单第一个元素开始
 * @param elems 焦点移动的范围,默认为表单中的所有元素
 * @param direction 方向 true:正向 false:反向
 * @param isMoveByTabIndex 是否按照tabIndex移动
 * @return 焦点移动到的元素，如果没有移动到某个元素则返回null
 */
function moveFocus(elem, elems, direction, isMoveByTabIndex){
	//try{
		if(elems==null){
			if(elem==null){
				elems = document.forms[0].elements;
			}else{
				elems = elem.form.elements;
			}
		}
		for(var i=0;i<elems.length;i++){
			if(elem!=null&&elems[i]!=elem){
				continue;
			}else{
				if(direction==false){
					//按tabIndex的顺序反向移动焦点
					if(isMoveByTabIndex==true&&elem.tabIndex>1){
						//当前的tabIndex
						var curTabIndex = 2;
						if(elem!=null){
							curTabIndex = elem.tabIndex;
						}
						for(var j=0;j<elems.length;j++){
							//找到当前tabIndex后的元素,置焦点并返回该元素
							if(elems[j].tabIndex==curTabIndex-1){
								//判断目标元素是否能够获得焦点
								if(elems[j].style!=null&&elems[j].style.display!='none'&&elems[j].disabled==false&&elems[j].readOnly!=true){
									if((elems[j].tagName=='INPUT'&&(elems[j].type=='text'||elems[j].type=='checkbox'||elems[j].type=='radio'))||elems[j].tagName=='TEXTAREA'||elems[j].tagName=='SELECT'){
										document.selection.empty();
										elems[j].focus();
										elems[j].focus();
										if(elems[j].tagName=='INPUT'&&elems[j].type=='text'){
												elems[j].createTextRange().select();
										}
										//返回焦点成功移动后的元素
										return elems[j];
									}
								}
								//如果不能获得焦点继续移到下一个tabIndex
								while(--curTabIndex<elems.length&&curTabIndex>=0){
									for(var n=0;n<elems.length;n++){
										if(elems[n].tabIndex==curTabIndex){
											//判断目标元素是否能够获得焦点
											if(elems[n].style!=null&&elems[n].style.display!='none'&&elems[n].disabled==false&&elems[n].readOnly!=true){
												if((elems[n].tagName=='INPUT'&&(elems[n].type=='text'||elems[n].type=='checkbox'||elems[n].type=='radio'))||elems[n].tagName=='TEXTAREA'||elems[n].tagName=='SELECT'){
													document.selection.empty();
													elems[n].focus();
													elems[n].focus();
													if(elems[n].tagName=='INPUT'&&elems[n].type=='text'){
															elems[n].createTextRange().select();
													}
													//返回焦点成功移动后的元素
													return elems[n];
												}
											}
										}
									}
								}
								break;
							}
						}
					}

					//按页面元素出现的顺序反向移动焦点
					if(elem==null) i=elems.length;
					while(--i>=0){
						try{
							if(elems[i].style!=null&&elems[i].style.display!='none'&&elems[i].disabled==false&&elems[i].readOnly!=true){
								if((elems[i].tagName=='INPUT'&&(elems[i].type=='text'||elems[i].type=='checkbox'||elems[i].type=='radio'))||elems[i].tagName=='TEXTAREA'||elems[i].tagName=='SELECT'){
									document.selection.empty();
									elems[i].focus();//在有的地方用一次focus移动焦点不成功，所以用了两次
									elems[i].focus();
									if(elems[i].tagName=='INPUT'&&elems[i].type=='text'){
											elems[i].createTextRange().select();
									}
									return elems[i];
								}
							}
						}catch(e){
							continue;
						}
					}
				}else{
					//按tabIndex的顺序正向移动焦点
					if(isMoveByTabIndex==true&&(elem==null||elem.tabIndex>0)){
						//当前的tabIndex
						var curTabIndex = 0;
						if(elem!=null){
							curTabIndex = elem.tabIndex;
						}
						for(var j=0;j<elems.length;j++){
							//找到当前tabIndex后的元素,置焦点并返回该元素
							if(elems[j].tabIndex==curTabIndex+1){
								//判断目标元素是否能够获得焦点
								if(elems[j].style!=null&&elems[j].style.display!='none'&&elems[j].disabled==false&&elems[j].readOnly!=true){
									if((elems[j].tagName=='INPUT'&&(elems[j].type=='text'||elems[j].type=='checkbox'||elems[j].type=='radio'))||elems[j].tagName=='TEXTAREA'||elems[j].tagName=='SELECT'){
										document.selection.empty();
										elems[j].focus();
										elems[j].focus();
										if(elems[j].tagName=='INPUT'&&elems[j].type=='text'){
												elems[j].createTextRange().select();
										}
										return elems[j];
									}
								}
								//如果不能获得焦点继续移到下一个tabIndex
								while(++curTabIndex<elems.length){
									for(var n=0;n<elems.length;n++){
										if(elems[n].tabIndex==curTabIndex){
											//判断目标元素是否能够获得焦点
											if(elems[n].style!=null&&elems[n].style.display!='none'&&elems[n].disabled==false&&elems[n].readOnly!=true){
												if((elems[n].tagName=='INPUT'&&(elems[n].type=='text'||elems[n].type=='checkbox'||elems[n].type=='radio'))||elems[n].tagName=='TEXTAREA'||elems[n].tagName=='SELECT'){
													document.selection.empty();
													elems[n].focus();
													elems[n].focus();
													if(elems[n].tagName=='INPUT'&&elems[n].type=='text'){
															elems[n].createTextRange().select();
													}
													return elems[n];
												}
											}
										}
									}
								}
								break;
							}
						}
					}

					//按页面元素出现的顺序正向移动焦点
					if(elem==null) i=-1;
					while(++i<elems.length){
						try{
							if(elems[i].style!=null&&elems[i].style.display!='none'&&elems[i].disabled==false&&elems[i].readOnly!=true){
								if((elems[i].tagName=='INPUT'&&(elems[i].type=='text'||elems[i].type=='checkbox'||elems[i].type=='radio'))||elems[i].tagName=='TEXTAREA'||elems[i].tagName=='SELECT'){
									document.selection.empty();
									elems[i].focus();
									if(elems[i].tagName=='INPUT'&&elems[i].type=='text'){
											elems[i].createTextRange().select();
									}
									return elems[i];
								}
							}
						}catch(e){
							continue;
						}
					}
				}
			}
			break;
		}
	//}catch(e){
		//
	//}
	return false;
}

function openProdDialog(url, width, height){
	if(!width){
		width = document.body.clientHeight * 1.8;
	}
	if(!height){
		height = document.body.clientHeight * 0.98;//高占90%高度
	}
	var retVal;
	layer.open({
	      type: 2,
	      title: '通用选取',
	      maxmin: false,// 最大最小化
	      shadeClose: false, //点击遮罩关闭层
	      resize: false,//是否可拉伸
	      area : [width+'px' , height+'px'],
	      scrollbar: false,
	      content: url,
	      offset: '5px',
	      btn:["确定","取消"],
	      yes:function(index,layero){
	    	  var retVal=window["layui-layer-iframe" + index].copyToParent();
	    	  layer.close(index);
	    	  // 判断单选多选,true是多选
	    	  if(selProdArgs[2]){
	    		  var field = selProdArgs[6].split(",");// 获取元素ID
	    		  var rownum;//行号
	    		  for(var i =0;i<field.length;i++){
	    			  rownum = parseInt(field[i].replace(/[^0-9]/ig,""));//截取数字位
	    			  // 循环判断截取的数字位是否在所有字段中都包含，如果是，则确认为ROWNUM
	    			  for(var j=0;j<field.length;j++){
	    				  if(field[j].indexOf(rownum)<0){
	    					  break;
	    				  }
	    			  }
	    		  }
	    		  if(!isNaN(rownum)){//如果类型是NAN 说明是多选但不需要分割，跳出方法
	    			// 确定完rownum后，截取出来获取元素ID前缀
		    		  var ids="";//元素ID前缀
		    		  for(var i =0;i<field.length;i++){
		    			  ids += field[i].replace(rownum,"")+",";
		    		  }
		    		  ids = ids.substring(0,ids.length-1);
		    		  /*if(selProdArgs[13]==null||selProdArgs[13]==""){
		    			  multiSelProductSet(retVal,ids,rownum);
			    	  }else{
			    		  var obj = window.frames[selProdArgs[13]];
			    		  obj.multiSelProductSet(retVal,ids,rownum);
			    	  }*/
		    		  multiSelCommonSet(retVal,ids,rownum);
	    		  }
	    	  }/*
	    	  if(selProdArgs[13]==null||selProdArgs[13]==""){
	    		  endSelCommBack();
	    	  }else{
	    		  window.frames[selProdArgs[13]].endSelCommBack();
	    	  }*/
	    	  endSelProductBack();
	      }
	    });
}
function endSelProductBack(){}
