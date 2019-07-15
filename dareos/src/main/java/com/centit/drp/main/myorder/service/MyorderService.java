
package com.centit.drp.main.myorder.service;

import java.util.List;
import java.util.Map;

import com.centit.base.product.model.ProductTree;
import com.centit.core.po.PageDesc;
import com.centit.drp.main.myorder.model.MyorderModel;
import com.centit.sys.model.UserBean;

/**
 * *@service
 * *@func
 * *@version 1.1
 * *@author lyg
 * *@createdate 2013-08-19 14:35:52
 */
public interface MyorderService  {
	/**
	 * 查询
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc);
	
	/**
	 * 查询所有品牌
	 * @return
	 */
	public List<String> findBRANDAll(Map<String,String> map);
	/**
	 * 查询当前时间的所有促销信息
	 * @return
	 */
	public List<String> findPRMT_PLANAll(Map<String,String> map);
    /**
     * 添加到购物车表
     */
    public void txEdit(MyorderModel model,UserBean userBean);
    public long getCount(Map<String,Object> map);
  //-- 0156143--Start--刘曰刚--2014-06-16//
	 
	/**
	 * 根据当前登录人所属渠道获取渠道折扣率
	 */
	public String getChannDiscount(String CHANN_ID);
	 //-- 0156143--End--刘曰刚--2014-06-16//
	/**
	 * 查询货品分类
	 */
	public List<String> findType();
	 /**
     * 显示树.
     * 
     * @return the tree
     * 
     * @throws Exception the exception
     */
    public List <ProductTree> getTree(String LEDGER_ID) throws Exception;
    
    public List<Map<String,String>> getLedgerByChannId(String channId);
    
    public List<Map<String,String>> getSeriesd(String LEDGER_ID);
}