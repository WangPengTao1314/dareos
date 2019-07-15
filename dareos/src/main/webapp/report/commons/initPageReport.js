/**
* autoBig 大报表查询分页时 用！！ 
* xingkf 2012-04-12
**/
	$(function(){
		//报表展现时，自动填满当前窗口！！！
//		$("#report1").css({ color: "#ff0011",width: "100%" }); 
		
		$("#report1_scrollArea table").each(function(i){
			$(this).css({ color: "#ff0011",width: "100%" });
		}); 		
		$("table[id^='report1']").css({ color: "#ff0011",width: "100%" });
//		$("#report1_topdiv").children("div").css({width:"100%"});
		//把“仅可供测试使用，请勿用于生产环境“ 去掉，呵呵....
//		$(".report1").remove();
		
 		/**** xingkefa 2011-11-07 start ****/
		$('#t_page_span').text(report1_getTotalPage());
		$('#c_page_span').text(report1_getCurrPage());
		//给翻页form设置路径
		$("form[name='report1_turnPageForm']").attr("action","report.shtml?action=toPageResult");
		function report1_toPage(pageNo) {
			if( pageNo < 1 || pageNo > report1_getTotalPage() ) return;
			$("form[name='report1_turnPageForm']").attr("action","report.shtml?action=toPageResult");

			$("form[name='report1_turnPageForm']").find("input[name='report1_currPage']").val(pageNo);

			showWaitPanel('数据查询');			
			setTimeout("_frm.submit()",5);
		}
		$("#newtPage").click(function(){
			report1_toPage(report1_getCurrPage()+1);
		});
		$("#firstPage").click(function(){
			report1_toPage(1);
		});
		$("#prevPage").click(function(){
			report1_toPage(report1_getCurrPage()-1);
		});
		$("#lastPage").click(function(){
			report1_toPage(report1_getTotalPage());
		});
 		/**** end ****/		
	});