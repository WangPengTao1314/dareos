package com.centit.core.po;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: PageDesc.java
 * @Description: 分页对象
 * @author: zhu_hj
 * @date: 2018年5月2日 下午3:02:37
 */
public class PageDesc implements Serializable{ 
	private static final long serialVersionUID = 1L;
	
	/** The DEFAUL t_ form. */
	private static String DEFAULT_FORM = "listForm";
	
	/** The form. */
	private String form = DEFAULT_FORM;
	
	
	
	public PageDesc() {
        pageSize = 20;
        pageNo = 1;
    }
	
	public PageDesc(int pn, int ps) {
        pageSize = ps;
        pageNo = pn;
    }
	
	public PageDesc(int pn, int ps, int tr) {
		total = tr;
        pageSize = ps;
        pageNo = pn;
    }

	// 页码
    private int pageNo;
    
    // 每页记录数（小于等于0表示不分页）
    private int pageSize;
    
    // 记录总数
    private int total = 0;
    
    // 总页数
    private int pages = 0;
    
    // 开始记录
    private int startRow = 0;
    
    // 结束记录
    private int endRow = 0;
    
    
    // 结果集
    private List<?> result;
    
    

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public String getFooterHtml(){
		StringBuffer html = new StringBuffer();
		return html.toString();
	}
	
	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}
	
	
	/**
	 * 翻页控制.
	 * 
	 * @return the toolbar html
	 */
	public String getToolbarHtml() {
		StringBuffer html = new StringBuffer();
		long curPageNo = getPageNo();
		long totalPage = getPages();
		long total = getTotal();
		html.append("<span class='curPage_FN'  onclick='javascript:_gotopagecho(1)' >首页</span>");
		//总页数大于1才显示上一页
		if(totalPage>1){
			html.append("<span class='curPage_FN' onclick='_gotopageahead()'>上一页</span>");
		}
		if(curPageNo < 6){
			for(int i=0 ;i<totalPage; i++){
				if(i>5){
					if(totalPage > 6){
						if(totalPage > 7){
							html.append("<b>...</b>");
						}
						html.append("<span class='otherPage' onclick='javascript:_gotopagecho("+totalPage+")' >"+totalPage+"</span>");
					}
					break;
				}else{
					int j = i+1;
					if(curPageNo == j){
						html.append("<span class='curPage' >"+j+"</span>");
					}else{
						html.append("<span class='otherPage' onclick='javascript:_gotopagecho("+j+")' >"+j+"</span>");
					}
				}
			}
		}else{
			html.append("<span class='otherPage' onclick='javascript:_gotopagecho("+1+")' >"+1+"</span>");
			if(totalPage <= curPageNo+3){
				if(totalPage!=6&& totalPage!=7){
					html.append("<b>...</b>");
				}
				for(long i=(totalPage-6==0?1:totalPage-6) ;i<totalPage; i++){
					long j = i+1;
					if(curPageNo == j){
						html.append("<span aligh='center' class='curPage' >"+j+"</span>");
					}else{
						html.append("<span class='otherPage' onclick='javascript:_gotopagecho("+j+")' >"+j+"</span>");
					}
				}
			}else{
				html.append("<b>...</b>");
				for(long i=(curPageNo-3),t=curPageNo+2;i<t ; i++){
					long j = i+1;
					if(curPageNo == j){
						html.append("<span aligh='center' class='curPage' >"+j+"</span>");
					}else{
						html.append("<span class='otherPage' onclick='javascript:_gotopagecho("+j+")' >"+j+"</span>");
					}
				}
				html.append("<b>...</b>");
				html.append("<span class='otherPage' onclick='javascript:_gotopagecho("+totalPage+")' >"+totalPage+"</span>");
			}
		}
		
		long tempNextPage = curPageNo+1;
		if(totalPage>1){
			html.append("<span class='curPage_FN' onclick='_gotopagenext()'>下一页</span>");
		}
		html.append("<span class='curPage_FN' onclick='javascript:_gotopagecho("+totalPage+")'>末页</span>");
//		html.append("每页<select onChange='_gotoPage3()' style='font-size:12px;' id='_gotoPageSize' class='page_no' maxlength='5'>");
		//xingkefa 2012-01-19 每页显示条数列表！  start
//		String[] pagesizes = pageSizeList.split(",");
		String pagesize = pageSize + "";
//		for(int i=0;i<pagesizes.length;i++){
//			if(pagesize.equals(pagesizes[i])){
//				html.append("<option selected='selected' value='"+pagesizes[i]+"'>").append(pagesizes[i]).append("</option>");
//			}else{
//				html.append("<option value='"+pagesizes[i]+"'>").append(pagesizes[i]).append("</option>");
//			}
//		}
//		html.append("</select>条");
		//end 
//		html.append("到第<input id='_gotoPageNo' class='page_no' maxlength='5' value="+curPageNo+">页");
//		html.append("<input type='button' id='_gotoPageNobt'  class='btn'  onclick='_gotoPage2()' value='确定'>");
		html.append("<td align='right'><span style='margin-right:50px'>共").append(total).append("条记录</span></td>");
		html.append("<script type='text/javascript'>");
		
        //选择确定的页面后的跳转
		html.append("function _gotopagecho(page){");
		html.append(getForm()).append(".pageNo.value = page;");
		html.append("showWaitPanel('');");
		html.append("setTimeout('").append(getForm()).append(".submit()',100);");
		html.append("}");
		
		//点击下一页后的页面跳转
		//modify by zhuxw parent 有可能为null
		html.append("function _gotopagenext(){if("+tempNextPage+">"+totalPage+"){ if(parent.showErrorMsg){parent.showErrorMsg('已经是最后一页!');return;}else{showErrorMsg('已经是最后一页!');return;}}");
		html.append(getForm()).append(".pageNo.value = "+tempNextPage+";");
		html.append("showWaitPanel('');");
		html.append("setTimeout('").append(getForm()).append(".submit()',100);");
		html.append("}");
		
		long tempAheadPage = curPageNo-1;
		//点击上一页后的页面跳转
		html.append("function _gotopageahead(){if("+tempAheadPage+"<"+1+"){if(parent.showErrorMsg){parent.showErrorMsg('已经是第一页!');return;}else{showErrorMsg('已经是第一页!');return;}}");
		html.append(getForm()).append(".pageNo.value = "+tempAheadPage+";");
		html.append("showWaitPanel('');");
		html.append("setTimeout('").append(getForm()).append(".submit()',100);");
		html.append("}");
		
		//点击确定后的跳转
		html.append("function _gotoPage2(){");
		html.append("var inpt = g('_gotoPageNo');");
		html.append("var pageNo = inpt.value*1;");
		// 頁碼等於當前頁碼時，不飜頁
		html.append("if(").append(curPageNo).append(" == pageNo ){return;");
		// 頁碼是否超出範圍
		html.append("}else if (").append(totalPage).append(" < pageNo){");
		html.append("parent.showErrorMsg('页码超出范围!');return;");
		html.append("}else if ( 1 > pageNo){");
		html.append("parent.showErrorMsg('页码超出范围!');return;");
		html.append("}");
		// 頁碼跳轉
		html.append(getForm()).append(".pageNo.value = pageNo;");
		html.append("showWaitPanel('');");
		html.append("setTimeout('").append(getForm()).append(".submit()',100);");
		html.append("}");
		
		//pagesizelist 改变时跳转！
		html.append("function _gotoPage3(){");
		html.append("var inpt = g('_gotoPageSize');");
		html.append("var pagesize = inpt.value;");
		// pagesize跳轉
		html.append(getForm()).append(".pageSize.value = pagesize;");
		html.append("showWaitPanel('');");
		html.append("setTimeout('").append(getForm()).append(".submit()',100);");
		html.append("}");
		
		html.append("</script>");
		return html.toString();
	}

    
}