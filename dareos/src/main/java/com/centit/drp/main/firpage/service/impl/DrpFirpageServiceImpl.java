package com.centit.drp.main.firpage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.commons.model.BusinessConsts;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.firpage.mapper.DrpFirpageMapper;
import com.centit.drp.main.firpage.service.DrpFirpageService;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.sys.mapper.NoticeMapper;
import com.centit.sys.model.NoticeModel;
import com.centit.sys.model.UserBean;
import com.centit.system.user.entity.RYXXModel;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
@Service
public class DrpFirpageServiceImpl implements DrpFirpageService {
	
	@Autowired
	private DrpFirpageMapper drpFirpageMapper;
	
	@Autowired
	private NoticeMapper noticeMapper;

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
		return noticeMapper.loadById(NOTICE_ID);
	}

	/**
	 * 公告加载更多
	 * 
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public List pageQueryNotice(Map<String, Object> map,PageDesc pageDesc) {
		//Page<NoticeModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		//LogicUtil.transPageHelper(pageDesc, p);
		return drpFirpageMapper.pageQueryNotice(map);
	}

	/**
	 * 加载促销
	 * 
	 * @param PRMT_PLAN_ID
	 * @return
	 */
//	public Map<String, String> loadPrmtModel(String PRMT_PLAN_ID) {
//		return (Map<String, String>) this.load("Prmtplan.loadById",
//				PRMT_PLAN_ID);
//	}

	/**
	 * 公告促销更多
	 * 
	 * @param params
	 * @param pageNo
	 * @return
	 */
	public void pageQueryPrmt(Map<String, Object> params,PageDesc pageDesc) {
		Page<RYXXModel> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
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
    	return drpFirpageMapper.queryStoreIn(params);
    }
    
    /**
	 *查询  待发货  状态 待发货
	 */
    public int queryOrderCount(Map<String,String> params){
    	return drpFirpageMapper.queryOrderCount( params);
    }
    
	/**
	 * 首页柱状图
	 * 
	 * @return
	 */
	public List<Map<String,String>> queryBar(UserBean userBean){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("STATE",BusinessConsts.HAVE_SEND_GOODS);
		paramMap.put("LEDGER_ID",userBean.getLoginZTXXID());//根据帐套过滤
	    return drpFirpageMapper.queryBar( paramMap);
	}
	
	
	/**
	    * 查找附件
	    * @param NOTICEID
	    * @return
	    */
//	   public Map<String,String> queryFilePath(String NOTICEID){
//		   Map<String,String> paramMap = new HashMap<String,String>();
//		   paramMap.put("FROM_BILL_ID", NOTICEID);
//		   List<Map<String,String>> fileList = this.findList("Techorder.queryUploadFile", paramMap);
//		   if(null != fileList && !fileList.isEmpty()){
//			   return fileList.get(0);
//		   }
//		   paramMap.clear();
//		   return paramMap;
//	   }
	   
	   /**
	     * 查询发运单
	     * @param userBean
	     * @return
	     */
	    public List<Map<String,String>> queryDeliver(UserBean userBean){
	    	 Map<String,String> paramMap = new HashMap<String,String>();
			 paramMap.put("ZTXXID", userBean.getLoginZTXXID());
			 paramMap.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
			 return drpFirpageMapper.queryDeliver(paramMap);
	    }

}
