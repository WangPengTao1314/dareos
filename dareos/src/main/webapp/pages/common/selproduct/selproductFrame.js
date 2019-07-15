
/**
 *@module 分销业务
 *@func人员信息维护
 *@version 1.1
 *@author lyg
 */
$(function(){
	var ledger_id = $('#ledger_id').val();
	$("#leftcontent").attr("src", "showTree?ledger_id="+ledger_id);
	$("#topcontent").attr("src","toList?ledger_id="+ledger_id);
	
});

	var parentObj = parent ;
	var obj = (parentObj.selProdArgs[13]==null||parentObj.selProdArgs[13]=="")?parentObj:parentObj.window.frames[parentObj.selProdArgs[13]];
	obj = obj || parentObj;
	obj.selProdArgs=parentObj.selProdArgs;
	var retVal;//console.log('obj:', obj);

	var keyValues = new Array();
	var frmName = obj.selProdArgs[5];
	var keyElems = obj.selProdArgs[3];
	var keyElemValues = new Array();
	var selCommElems = obj.selProdArgs[6];
	var selCommElemValues = new Array();

	var arrKeyElems = keyElems.split(",");
	var arrSelCommElems = selCommElems.split(",");
	for(var i=0;i<arrKeyElems.length;i++){
		keyElemValues[i] = new Array();
		var tmpobj = eval("obj.document."+frmName+"."+arrKeyElems[i]);
		var tmp = "";
		if(tmpobj.className==null||tmpobj.className.indexOf("selcomm")==-1){
			tmp = tmpobj.value;
		}else{
			tmp = tmpobj.oldValue;
		}
		tmp=tmp+"";
		if(tmp!=''){
			keyElemValues[i] = tmp.split(",");
		}
	}

	for(var i=0;i<arrSelCommElems.length;i++){
		selCommElemValues[i] = new Array();
		var tmpobj = eval("obj.document."+frmName+"."+arrSelCommElems[i]);
		var tmp = "";
		if(tmpobj.className==null||tmpobj.className.indexOf("selcomm")==-1){
			tmp = tmpobj.value;
		}else{
			tmp = tmpobj.oldValue;
		}
		tmp=tmp+"";
		if(tmp!=''){
			selCommElemValues[i] = tmp.split(",");
		}
	}

	function copyToParent(){
		var isChanged = false;
		for(var i=0;i<arrKeyElems.length;i++){
			var tmp = tranArrToStr(keyElemValues[i]);
			var tmpobj = eval("obj.document."+frmName+"."+arrKeyElems[i]);
			if(tmpobj.value != tmp) isChanged = true;
			tmpobj.value = tmp;
			if(tmpobj.seltarget!=""&&tmpobj.seltarget!=null)
			{
			  tmpobj.oldValue = tmp;
			}
		}
		for(var i=0;i<arrSelCommElems.length;i++){
			var tmp = tranArrToStr(selCommElemValues[i]);
			var tmpobj = eval("obj.document."+frmName+"."+arrSelCommElems[i]);
			tmpobj.value = tmp;
			if(tmpobj.seltarget!=""&&tmpobj.seltarget!=null)
			{
			  tmpobj.oldValue = tmp;
			}
			
		}
		retVal = new Array();
		retVal[0] = keyElemValues[0].length;
		retVal[1] = isChanged;
		return retVal;
	}
	
	function tranArrToStr(arr){
		if(arr==null||arr.length==0) return "";
		var tmpstr = arr[0];
		for(var i=1;i<arr.length;i++){
			tmpstr = tmpstr + "," + arr[i];
		}
		return tmpstr;
	}
