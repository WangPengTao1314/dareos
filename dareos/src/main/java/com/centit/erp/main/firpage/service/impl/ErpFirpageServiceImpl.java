package com.centit.erp.main.firpage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.commons.model.BusinessConsts;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.firpage.mapper.DrpFirpageMapper;
import com.centit.erp.main.firpage.service.ErpFirpageService;
import com.centit.erp.sale.prmtplan.mapper.PrmtplanMapper;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.sys.mapper.NoticeMapper;
import com.centit.sys.model.NoticeModel;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class ErpFirpageServiceImpl  implements ErpFirpageService {
	
	@Autowired
	DrpFirpageMapper drpFirpageMapper;
	
	@Autowired
	NoticeMapper noticeMapper;
	
	@Autowired
	PrmtplanMapper prmtplanMapper;

	/**
	 * 查询推广促销
	 * 
	 * @param userBean
	 *            userBean
	 * @param size
	 *            记录数
	 * @return
	 */
	public List<PrmtplanModel> queryPrmtpList(UserBean userBean, int size) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("XTYHID", userBean.getXTYHID());
		paramMap.put("CHANN_ID", userBean.getCHANN_ID());
		paramMap.put("STATE", BusinessConsts.ISSUANCE);
		paramMap.put("size", size);
		return drpFirpageMapper.queryPrmt(paramMap);
	}

	/**
	 * 加载公告
	 * 
	 * @param NOTICE_ID
	 * @return
	 */
	public Map<String, Object> loadNoticeModel(String NOTICE_ID) {
		Map<String,Object> map=noticeMapper.loadById(NOTICE_ID);
		String attPath = LogicUtil.getAttPath(NOTICE_ID);
		map.put("FILEPATH",attPath);

		return map;
	}

	/**
	 * 公告加载更多
	 * 
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public void pageQueryNotice(Map<String, Object> params, PageDesc pageDesc) {
		Page<NoticeModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	drpFirpageMapper.pageQueryNotice(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	/**
	 * 加载促销
	 * 
	 * @param PRMT_PLAN_ID
	 * @return
	 */
	public Map<String, String> loadPrmtModel(String PRMT_PLAN_ID) {
		return prmtplanMapper.loadById(PRMT_PLAN_ID);
	}

	/**
	 * 公告促销更多
	 * 
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public void pageQueryPrmt(Map<String, Object> params, PageDesc pageDesc) {
		Page<PrmtplanModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	drpFirpageMapper.pageQueryPrmt(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	/**
	 * 查询 待发货预订单 待入库 待退货
	 */
	public List<Map<String,String>> queryCount() {
		return drpFirpageMapper.queryCount();
	}

	
	/**
	 *查询  待退货  状态 已提交
	 */
    public int queryPrdRetuenReqCount(Map<String,String> params){
    	return drpFirpageMapper.queryPrdRetuenReqCount(params);
    }
    
    /**
	 *查询  待入库  状态 已提交
	 */
    public int queryStoreIn(Map<String,String> params){
    	return drpFirpageMapper.queryStoreIn( params);
    }
    
    /**
	 *查询  待发货  状态 待发货
	 */
    public int queryOrderCount(Map<String,String> params){
    	return drpFirpageMapper.queryOrderCount(params);
    }
    
	/**
	 * 首页柱状图
	 * 
	 * @return
	 */
	public List<Map<String,String>> queryBar(UserBean userBean){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("STATE",BusinessConsts.HAVE_SEND_GOODS);
	    return drpFirpageMapper.queryBar(paramMap);
	}

	 
	
	/**
	 * 查询订货订单处理未处理状态条数
	 * @param CHANNS_CHARG
	 * @return
	 */
    public int queryGoodsCount(String CHANNS_CHARG){
    	Map<String,String> map=new HashMap<String, String>();
    	map.put("CHANNS_CHARG", CHANNS_CHARG);
    	map.put("STATE", BusinessConsts.UNDONE);
    	return drpFirpageMapper.queryGoodsCount( map);
    }
    /**
     * 查询制定交付计划条数
     * @param userBean
     * @return
     */
    public int queryTurnoverplanCount(UserBean userBean){
    	Map<String,String> map=new HashMap<String, String>();
    	StringBuffer sql=new StringBuffer();
    	sql.append("'").append(BusinessConsts.PASS).append("',");//'审核通过'
//    	sql.append("'").append(BusinessConsts.HAVE_CHECK_PRICE).append("',"); //'已核价'
    	//sql.append("'").append(BusinessConsts.UN_HAVE_CHECK_PRICE).append("',"); //'未核价'
    	sql.append("'").append(BusinessConsts._BACK).append("'"); //'退回'
    	map.put("CHANNS_CHARG", userBean.getCHANNS_CHARG());
    	map.put("STATE", sql.toString());
    	map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
//    	map.put("CREATOR", userBean.getXTYHID());
    	return drpFirpageMapper.queryTurnoverplanCount(map);
    }
    
    /**
     * 查询交付计划维护条数
     * @param userBean
     * @return
     */
    public int queryTurnoverhdCount(UserBean userBean){
    	Map<String,String> map=new HashMap<String, String>();
    	StringBuffer sql=new StringBuffer();
    	sql.append("'").append(BusinessConsts.UNCOMMIT).append("'");//'审核通过'
    	map.put("CHANNS_CHARG", userBean.getCHANNS_CHARG());
    	map.put("STATE", sql.toString());
    	map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
    	return drpFirpageMapper.queryTurnoverhdCount(map);
    }
    
    /**
     * 查询货品发运维护条数
     * @param userBean
     * @return
     */
    public int queryPdtdeliverCount(UserBean userBean){
    	Map<String,String> map=new HashMap<String, String>();
    	StringBuffer sql=new StringBuffer();
    	sql.append("'").append("已提交生产").append("'");//'审核通过'
    	map.put("CHANNS_CHARG", userBean.getCHANNS_CHARG());
    	map.put("STATE", sql.toString());
    	map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
    	return drpFirpageMapper.queryTurnoverhdCount( map);
    }
    
    /**
     * 查询发运确认条数
     * @param userBean
     * @return
     */
    public int querydeliverconfmCount(UserBean userBean){
    	Map<String,String> map=new HashMap<String, String>();
    	StringBuffer sql=new StringBuffer();
    	sql.append("'").append("已发货").append("'");//'审核通过'
    	map.put("CHANNS_CHARG", userBean.getCHANNS_CHARG());
    	map.put("STATE", sql.toString());
    	map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
    	return drpFirpageMapper.queryTurnoverhdCount(map);
    }


	
	
	/**
     * 查询待审核的工艺单
     * @param userBean
     * @return
     */
    public int queryTechorderAudit(UserBean userBean){
    	Map<String,String> map = new HashMap<String, String>();
    	StringBuffer sql = new StringBuffer();
    	sql.append("'").append("提交").append("'"); 
    	map.put("CHANNS_CHARG", userBean.getCHANNS_CHARG());
    	map.put("STATE", sql.toString());
    	map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
    	return drpFirpageMapper.queryTechorderAuditCount( map);
    }
    /**
     * 查询核价的工艺单
     * @param userBean
     * @return
     */
    public int queryTechorderPrice(UserBean userBean){
    	Map<String,String> map = new HashMap<String, String>();
    	StringBuffer sql = new StringBuffer();
    	sql.append("'").append("待核价").append("'"); 
    	map.put("CHANNS_CHARG", userBean.getCHANNS_CHARG());
    	map.put("STATE", sql.toString());
    	map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
    	return drpFirpageMapper.queryTechorderCount( map);
    }

}
