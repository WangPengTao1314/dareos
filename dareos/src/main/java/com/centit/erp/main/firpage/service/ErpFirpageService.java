package com.centit.erp.main.firpage.service;

import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.sys.model.UserBean;

public interface ErpFirpageService{
	
	/**
	 * 查询推广促销
	 * @param size 记录数
	 * @return
	 */
	public List<PrmtplanModel> queryPrmtpList(UserBean userBean,int size);
	
	
	/**
	 * 加载公告
	 * @param NOTICE_ID
	 * @return
	 */
	public Map<String,Object> loadNoticeModel(String NOTICE_ID);
	
	/**
	 * 公告加载更多
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public void pageQueryNotice(Map<String,Object> params, PageDesc pageDesc);
	
	
	/**
	 * 加载促销
	 * @param PRMT_PLAN_ID
	 * @return
	 */
	public Map<String,String> loadPrmtModel(String PRMT_PLAN_ID);
	
	/**
	 * 公告促销更多
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public void pageQueryPrmt(Map<String,Object> params, PageDesc pageDesc);
	
	/**
	 * 查询  待发货预订单   待入库  待退货 
	 */
    public List queryCount();
    
	/**
	 *查询 待退货 
	 */
    public int queryPrdRetuenReqCount(Map<String,String> params);
    
    /**
	 *查询  待入库  状态 已提交
	 */
    public int queryStoreIn(Map<String,String> params);
    /**
	 *查询  待发货  状态 待发货
	 */
    public int queryOrderCount(Map<String,String> params);
    
    //查询订货订单处理未处理状态条数
    public int queryGoodsCount(String CHANNS_CHARG);
    //查询制定交付计划条数
    public int queryTurnoverplanCount(UserBean userBean);
    //查询交付计划维护
    public int queryTurnoverhdCount(UserBean userBean);
    //货品发运
    public int queryPdtdeliverCount(UserBean userBean);
    
    //发运确认
    public int querydeliverconfmCount(UserBean userBean);
    /**
     * 查询待审核的工艺单
     * @param userBean
     * @return
     */
    public int queryTechorderAudit(UserBean userBean);
    /**
     * 查询核价的工艺单
     * @param userBean
     * @return
     */
    public int queryTechorderPrice(UserBean userBean);
   

}
