package com.centit.drp.main.firpage.service;

import java.util.List;
import java.util.Map;

import com.centit.core.po.PageDesc;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.sys.model.NoticeModel;
import com.centit.sys.model.UserBean;

public interface DrpFirpageService{
	
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
	 * @param pageQureyMap
	 * @param pageDesc
	 */
	public List  pageQueryNotice(Map<String, Object> pageQureyMap,PageDesc pageDesc);
	
	/**
	 * 加载促销
	 * @param PRMT_PLAN_ID
	 * @return
	 */
//	public Map<String,String> loadPrmtModel(String PRMT_PLAN_ID);
	
	/**
	 * 公告促销更多
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public void pageQueryPrmt(Map<String,Object> params,PageDesc pageDesc );
	
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
    
    /**
     * 首页柱状图
     * @return
     */
    public List<Map<String,String>> queryBar(UserBean userBean);
    
    /**
     * 查找附件
     * @param NOTICEID
     * @return
     */
//    public Map<String,String> queryFilePath(String NOTICEID);
    /**
     * 查询发运单
     * @param userBean
     * @return
     */
    public List<Map<String,String>> queryDeliver(UserBean userBean);
	
}
