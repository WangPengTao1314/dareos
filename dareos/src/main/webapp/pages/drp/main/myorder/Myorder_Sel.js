
/**
 *@module 分销业务
 *@func人员信息维护
 *@version 1.1
 *@author lyg
 */
$(function(){
//	$("#leftcontent").attr("src", "showTree");
	$("#topcontent").attr("src","toList")
	gotoBottomPage();
});
function clickBut(){
	var rebateQuery=$("#rebateQuery").prop("checked");//返利商品
	var lassQuery=$("#lassQuery").prop("checked");//赠品
	var type=$("#PRD_TYPE").val();//选中的分类
	var brand="";//选中的品牌
	var spec=$("#PRD_SPEC").val();//选中的规格型号
	var prd_Info="";//文本框输入的货品名称
	var prmt="";//促销信息
	$("input[name='brand']").each(function(){
		if($(this).attr("checked")){
			brand=brand+"'"+$(this).val()+"',";
		}
	})
	brand = brand.substr(0,brand.length-1);
//	$("input[name='spec']").each(function(){
//		if($(this).attr("checked")){
//			spec=spec+"'"+$(this).val()+"',";
//		}
//	})
//	spec = spec.substr(0,spec.length-1);
//	$("input[name='type']").each(function(){
//		if($(this).attr("checked")){
//			type=type+"'"+$(this).val()+"',";
//		}
//	})
//	type = type.substr(0,type.length-1);
	
	$("input[name='prmt']").each(function(){
		if($(this).attr("checked")){
			prmt=prmt+"'"+$(this).val()+"',";
		}
	})
	prmt = prmt.substr(0,prmt.length-1);
	if($("#prd_Info").val()!=null&&$("#prd_Info").val()!=""){
		prd_Info=$("#prd_Info").val();
	}
	
	var action="toList?brand="+utf8(brand)+"&spec="+utf8(spec)+"&type="+utf8(type)+"&prd_Info="+utf8(prd_Info)+"&prmt="+utf8(prmt)+"&rebateQuery="+utf8(rebateQuery)+"&lassQuery="+utf8(lassQuery);
	gotoBottomPage(action);
}
//下帧初始化
function gotoBottomPage(action){
	//初始化时下帧页面的action
	if(null == action){
	  action = "toList";
	}
	var url = action;
	//下帧页面跳转
	$("#bottomcontent").attr("src",url);
}

/*function bgshow(e){
	 console.log(e);
	 window.event.preventDefault()
	 $(e).css('background','#0083c6');
	 $(e).css('color','#fff');
	 $(e).css('border-radius','3px');
	 let subArr=$(window.event.target).siblings();
	 var i=0;
	 for(i=0;i<subArr.length;i++){
		 subArr[i].style.background="#fff";
		 subArr[i].style.color="#000";
		 }
	 
}
*/
