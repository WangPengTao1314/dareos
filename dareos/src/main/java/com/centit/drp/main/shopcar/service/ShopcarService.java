
package com.centit.drp.main.shopcar.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.drp.main.shopcar.model.ShopcarChannInfoModel;
import com.centit.drp.main.shopcar.model.ShopcarModel;
import com.centit.sys.model.UserBean;


/**
 * *@service
 * *@func
 */
public interface ShopcarService {
	/**
	 * 查询
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public List<Map<String,Object>> pageQuery(Map<String, Object> params);
	
	/**
	 * @主表删除
	 * @param Map
	 * 
	 * @return true, if tx delete
	 */
	public void txDelete(Map <String, Object> params);
	//修改购物车
	public void update(List<ShopcarModel> ModelList,UserBean userBean)throws ParseException;
     
	/**
	 * 查询当前渠道
	 * @param CHANN_ID 渠道ID
	 * @return
	 */
    public Map<String, Object> getChannInfo(String CHANN_ID);
    
    
    /**
     * 修改特殊工艺后修改该条的价格
     * @param map
     */
    public void upPrice(Map<String,Object> map);
    
    public long getCount(Map<String,Object> map);
    public Map<String,Object> getCHANN_TYPE(String CHANN_ID);
    public String getLargessDect(String CHANN_ID);
    //购物车保存提交
    public boolean txSaveCommit(Map<String,String> shopParams,List<ShopcarModel> ModelList,UserBean userBean,ShopcarChannInfoModel channModel) throws Exception;
    
}