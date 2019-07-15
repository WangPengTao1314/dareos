<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String reportId = request.getParameter("reportId");
	String reportFlag = request.getParameter("reportFlag");
	String pageFlag  = request.getParameter("pageFlag");
%>

		<div class="datagrid-pager pagination rqpage" id="boolbarDiv<%=reportId %>" style="width:100%;height:30px;">
		<div id="pageDiv<%=reportId %>" >
			<table cellspacing="0" cellpadding="0" border="0" style="float:right;">
				<tbody>
					<tr>
						<td>
							<div class="pagination-btn-separator"></div>
						</td>
						<td>
							<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled" title="第一页">
								<span class="l-btn-left l-btn-icon-left">
									<span class="l-btn-text l-btn-empty">&nbsp;</span>
									<span class="l-btn-icon pagination-first">&nbsp;</span>
								</span>
							</a>
						</td>
						<td>
							<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled" title="上一页">
								<span class="l-btn-left l-btn-icon-left">
									<span class="l-btn-text l-btn-empty">&nbsp;</span>
									<span class="l-btn-icon pagination-prev">&nbsp;</span>
								</span>
							</a>
						</td>
						<td class="pagination-links"></td>
						<td>
							<a href="javascript:void(0)"  title="下一页">
							<input type="button" value="aa">
								<span class="l-btn-left l-btn-icon-left">
									<span class="l-btn-text l-btn-empty">&nbsp;</span>
									<span class="l-btn-icon pagination-next">&nbsp;</span>
								</span>
							</a>
						</td>
						<td>
							<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain l-btn-disabled l-btn-plain-disabled" title="最后一页">
								<span class="l-btn-left l-btn-icon-left">
									<span class="l-btn-text l-btn-empty">&nbsp;</span>
									<span class="l-btn-icon pagination-last">&nbsp;</span>
								</span>
							</a>
						</td>
						<td>
							<div class="pagination-btn-separator"></div>
						</td>
						<td>
							<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" title="刷新">
								<span class="l-btn-left l-btn-icon-left">
									<span class="l-btn-text l-btn-empty">&nbsp;</span>
									<span class="l-btn-icon pagination-load">&nbsp;</span>
								</span>
							</a>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="pagination-info" style="float:right">第 <span id="currPage">0</span> 页，总共 <span id="totalPage">0</span> 页</div>
			</div>
			<table cellspacing="0" cellpadding="0" border="0" style="float:left;margin-left:5px;">
				<tbody>
					<tr>
<!-- 						<td> -->
<!-- 							<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" id="exportWord"> -->
<!-- 								<span class="l-btn-left l-btn-icon-left"> -->
<!-- 									<i style="font-size: 12px;font-family: initial;color: #666;padding:0 5px;"> -->
<!-- 									<span class="fa fa-file-word-o" style="font-size: 150%;color: #58b5eb;">&nbsp;</span> -->
<!-- 									</i> -->
<!-- 								</span> -->
<!-- 							</a> -->
<!-- 						</td> -->
						<td>
							<div class="pagination-btn-separator"></div>
						</td>
						<td>
							<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" id="exportExcel">
								<span class="l-btn-left l-btn-icon-left">
									<i style="    font-size: 12px;font-family: initial;color: #666;padding:0 5px;">
									<span class="fa fa-file-excel-o" style="font-size: 150%;color: #4ab042;">&nbsp;</span>
									</i>
								</span>
							</a>
						</td>
						<td>
							<div class="pagination-btn-separator"></div>
						</td>
						
<!-- 						<td> -->
<!-- 							<div class="pagination-btn-separator"></div> -->
<!-- 						</td> -->
<!-- 						<td> -->
<!-- 							<a href="javascript:void(0)" class="l-btn l-btn-small l-btn-plain" id="reportPrint"> -->
<!-- 								<span class="l-btn-left l-btn-icon-left"> -->
<!-- 									<i style="    font-size: 12px;font-family: initial;color: #666;padding:0 5px;"> -->
<!-- 									<span class="fa fa-print" style="font-size: 150%;color: #c09cf;">&nbsp;</span> -->
<!-- 									</i> -->
<!-- 								</span> -->
<!-- 							</a> -->
<!-- 						</td> -->
					</tr>
				</tbody>
			</table>
			<div style="clear:both;"></div>
		</div>
		
		<script type="text/javascript">
			/* var Consts = require('Consts'); */
			var reportFlag='<%=reportFlag %>'
			var reportId='<%=reportId %>'
			var pageFlag='<%=pageFlag %>'
// 			initPopUp()
			//是否显示打印和分页
			
			//首页
			$('#<%=reportId%>').find('.rqpage a').eq(0).on('click',function(){
				rq_toPage(1, '<%=reportId%>');	
			})
			
			//上一页
			$('#<%=reportId%>').find('.rqpage a').eq(1).on('click',function(){
				rq_toPage(rq_getCurrPage('<%=reportId%>')-1, '<%=reportId%>');			
			})
			
			//下一页
			$('#<%=reportId%>').find('.rqpage a').eq(2).on('click',function(){
				alert(1);
				rq_toPage(rq_getCurrPage('<%=reportId%>')+1, '<%=reportId%>');			
			})
			
			//末页
			$('#<%=reportId%>').find('.rqpage a').eq(3).on('click',function(){
				rq_toPage(rq_getTotalPage('<%=reportId%>'), '<%=reportId%>');			
			})
			
			//刷新
			$('#<%=reportId%>').find('.rqpage a').eq(4).on('click',function(){
				rq_toPage(rq_getCurrPage('<%=reportId%>'), '<%=reportId%>');			
			})
			
			$('#<%=reportId%>').find('#currPage').html(rq_getCurrPage('<%=reportId%>'));
			$('#<%=reportId%>').find('#totalPage').html(rq_getTotalPage('<%=reportId%>'));
			
			//导出word
			$('#<%=reportId%>').find('#exportWord').on('click',function(){
				eval('report_<%=reportId%>_saveAsWord()');		
			})
			
			//导出Excel
			$('#<%=reportId%>').find('#exportExcel').on('click',function(){
				eval('report_<%=reportId%>_saveAsExcel()');		
			})
			
			//导出pdf
			$('#<%=reportId%>').find('#exportPdf').on('click',function(){
				eval('report_<%=reportId%>_saveAsPdf2("")');		
			})
			
			//打印
			$('#<%=reportId%>').find('#reportPrint').on('click',function(){
				eval('report_<%=reportId%>_print()');
			})
			
			//获取当前页
			function rq_getCurrPage(reportId){
				return eval('report_'+reportId+'_getCurrPage()');
			}
			
			//获取总页数
			function rq_getTotalPage(reportId){
				return eval('report_'+reportId+'_getTotalPage()');
			}
			
			function showRap(url){
				var reportUnit = $("#"+reportId).find("input[name='reportUnit']").val();
				/* console.log(reportUnit);
				console.log(url); */
				url = url+"&reportUnit="+reportUnit;
		      	window.open("/wzdu/system/report/"+url,'报表','height=600, width=800, top=100, left=100, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
		      }
			
			//跳转至指定页
			function rq_toPage(pageNo, reportId){
				if( pageNo < 1 || pageNo > rq_getTotalPage(reportId) ) return false;
				$(rq_getPageForm(reportId)).find('[name=report_'+reportId+'_currPage]').val(pageNo);
				rq_onPageFormSubmit(reportId);
			}
			
			//获取润乾报表自动生成的form
			function rq_getPageForm(reportId){
				return document.getElementsByName('report_'+reportId+'_turnPageForm')[0];
			}
			
			//点击分页条时提交润乾form
			function rq_onPageFormSubmit(reportId){
				var pageForm = rq_getPageForm(reportId);
				var searchForm = $('#'+reportId).find("form")[0];
// 				var FormData = $(searchForm).form('value');
				
				//处理多选的combobox
// 				$(searchForm).find('input[isMultiple=true]').each(function(){
// 					var val = $(this).combobox('getValues');
// 					FormData[this.id] = val;
// 				})

// 				var data = eval(FormData);
				var params = '';
// 				for(var key in data){
// 					params+=((params==''?'?':'&')+key+'='+data[key]);
// 					$(pageForm).find('[name='+key+']').remove();
// 				}
				$(pageForm).find('[name=_csrf]').remove();
				$(pageForm).find('[name=_]').remove();
				params+='&'+$(pageForm).serialize();
				$('#'+reportId).find("form").submit();
				/* Consts.reloadTabs(rq_getOptId(reportId), searchForm.action+params); */
			}
			
			function rq_getOptId(reportId){
				var searchForm = $('#'+reportId).find("form")[0];
				var optId = $(searchForm).attr('optId');
				if(optId!=undefined && optId!=''){
					reportId = optId;
				}
				return reportId;
			}
			
			//初始化多选Combobox  obj-元素ID  data-多选值
			function rq_initMultipleCombobox(reportId,obj,data){
				$('#'+reportId).find('#'+obj).combobox({
				    onLoadSuccess : function() {
				    	if(data!=''){
				    		var codes = data.split(',');
				    		for(var i=0;i<codes.length;i++){
				    			$('#'+reportId).find('#'+obj).combobox('select', codes[i]);
				    		}
				    	}
				    } 
				})
			}
			
			function rq_init(reportId){
				var searchForm = $('#'+reportId).find("form")[0];
				//查询
// 				$('#'+reportId).find('.btn-search').on('click',function(){
// 					//验证必填项
// 					var isValid = $(searchForm).form('validate');
// 					if(!isValid){
// 						return;
// 					}
// 					var FormData = $(searchForm).form('value');
					
// 					var reportDateStart = FormData.reportDateStart;
// 					var reportDateBeg = FormData.reportDateBeg;
// 					var reportDateEnd = FormData.reportDateEnd;
// 					var isQueryByDate = FormData.isQueryByDate;
// 					if(isQueryByDate!=undefined && isQueryByDate=="true"){
// 						if(reportDateBeg==''){
// 							jQuery.messager.alert("警告","请选择统计日期范围！","warning");
// 							return;
// 						}
// 					}
					
// 					if(reportDateStart!=undefined && reportDateStart!=''){
// 						if(reportDateEnd==''){
// 							jQuery.messager.alert("警告","统计日期范围请选择完整！","warning");
// 							return;
// 						}
// 					}
// 					if(reportDateBeg!=undefined && reportDateBeg!=''){
// 						if(reportDateEnd==''){
// 							jQuery.messager.alert("警告","统计日期范围请选择完整！","warning");
// 							return;
// 						}
// 					}
// 					if(reportDateEnd!=undefined && reportDateEnd!=''){
// 						if(reportDateStart=='' || reportDateBeg==''){
// 							jQuery.messager.alert("警告","统计日期范围请选择完整！","warning");
// 							return;
// 						}
// 					}
					
// 					$(searchForm).find('input[isMultiple=true]').each(function(){
// 						var val = $(this).combobox('getValues');
// 						FormData[this.id] = val;
// 					})
// 					var data = eval(FormData);
// 					var params = '';
// 					for(var key in data){
// 						params+=((params==''?'?':'&')+key+'='+data[key]);
// 					}
// 					/* Consts.reloadTabs(rq_getOptId(reportId), searchForm.action+params); */
// 				});
				
// 				//重置
// 				$('#'+reportId).find('.btn-reset').on('click',function(){
// 					$('#'+reportId).find('form')[0].reset();
// 					$(searchForm).find('input[isMultiple=true]').each(function(){
// 						$(this).combobox('setValue','');
// 					})
// 				});
				
				//报表区域高度调整
				setTimeout(function(){
					var h = $('#'+reportId).height();//页面总高度
					var ht = $('#'+reportId).children('div').eq(0).height();//查询Div高度
					var hb = $('#'+reportId).children('div').eq(2).height();//工具条高度
					$('#'+reportId).children('div').eq(1).css('height',h-ht-hb);
					$('#report_'+reportId).css('width',$('#'+reportId).width()-2).css('margin','auto');
				}, 200);
				
				//setTimeout(function(){
					/* var $main = $('#'+reportId).children().eq(1);
					//var $reportDiv = $('#report_'+reportId+'_reportDiv');
					//var $scrollArea = $('#report_'+reportId+'_scrollArea');
					var $funcBar = $('#report_'+reportId+'_funcbar');
					var $contentdiv = $('#report_'+reportId+'_contentdiv');
			
					$funcBar.css('margin-left','20px');
					$contentdiv.css('width',$main.width()-40);
					$contentdiv.css('height',$main.height()-$funcBar.height()-2);
					$contentdiv.css('padding',"0 20px");
					$contentdiv.children().eq(0).css("margin","auto"); */
				//}, 500); 
			}
			
			
			function initPopUp() {
				if( document.getElementById("popupContainer") != null ){
					document.getElementById("popupContainer").remove();
				};
				theBody = document.getElementsByTagName('BODY')[0];
				popmask = document.createElement('div');
				popmask.id = 'popupMask';
				popmask.style.position = "absolute";
				popmask.style.zindex = "900";
				popmask.style.top = "0px";
				popmask.style.left = "0px";
				popmask.style.width = "100%";
				popmask.style.height = "100%";
				popmask.style.opacity = ".4";
				popmask.style.filter = "alpha(opacity=40)";
				popmask.style.backgroundColor = "#333333";
				popmask.style.display = "none";
				popcont = document.createElement('div');
				popcont.id = 'popupContainer';
				popcont.style.position = "absolute";
				popcont.style.zIndex = "901";
				popcont.style.display = "none";
				popcont.innerHTML = '' +
					'<DIV id=popupInner style="border: 2px solid #000000;ackground-color: #ffffff;">' +
						'<DIV style="background-color:#486CAE;color:#ffffff;font-weight:bold;height:17px;padding: 4px;border-bottom: 2px solid #000000;border-top: 1px solid #78A3F2;border-left: 1px solid #78A3F2;border-right: 1px solid #204095;position: relative;z-index: 903;" id=popupTitleBar>' +
							'<DIV id=popupTitle style="float:left;font-size:14px"></DIV>' +
							'<DIV id=popupControls style="float: right;cursor: pointer;cursor: hand;"><SPAN style="text-align:center;font-size:14px;BACKGROUND-COLOR: gray; WIDTH: 16px; HEIGHT: 16px; COLOR: #000000;border-left:1px solid #cccccc;border-top:1px solid #cccccc;border-right:1px solid #555555;border-bottom:1px solid #555555" id=popCloseBox onclick=rq_hidePopWin();>X</SPAN></DIV>' +
						'</DIV>' +
						'<IFRAME style="margin: 0px;position: relative;z-index: 902;BACKGROUND-COLOR: transparent;" id=popupFrame frameBorder=0 allowTransparency name=popupFrame scrolling=auto tabEnabled="true"></IFRAME>' +
					'</DIV>';
				theBody.appendChild(popmask);
				theBody.appendChild(popcont);
				
				rq_gPopupMask = document.getElementById("popupMask");
				rq_gPopupContainer = document.getElementById("popupContainer");
				rq_gPopFrame = document.getElementById("popupFrame");	
				// check to see if this is IE version 6 or lower. hide select boxes if so
				var brsVersion = parseInt(window.navigator.appVersion.charAt(0), 10);
				if (brsVersion <= 6 && window.navigator.userAgent.indexOf("MSIE") > -1) {
					rq_gHideSelects = true;
				}
				rq_addEvent(window, "resize", centerPopWin);
				rq_addEvent(window, "scroll", centerPopWin);
			}
		</script>

