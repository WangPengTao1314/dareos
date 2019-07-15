package com.centit.drp.main.firpage.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.sys.model.NoticeModel;
import com.centit.sys.model.UserBean;
import com.centit.sys.model.ZTWHModel;


@Repository
public interface DrpFirpageMapper {
	
	List<PrmtplanModel> queryPrmt(Map<String,Object> map);
	
	List<NoticeModel> queryNotice(Map<String,Object> map);
	
	List<NoticeModel> pageQueryNotice(Map<String,Object> map);

	
	List<Map<String,String>> pageQueryPrmt(Map<String,Object> map);
	
	List<Map<String,String>> queryStore(String LEDGER_ID);
	
	List<Map<String,String>> queryBar(Map<String,Object> map);
	
	List<Map<String,String>> queryDeliver(Map<String,String> map);
	
	Integer queryPrdRetuenReqCount(Map<String,String> map);
	
	Integer queryStoreIn(Map<String,String> map);
	
	Integer queryOrderCount(Map<String,String> map);
	
	List<Map<String,String>> queryCount();
	
	Integer queryGoodsCount(Map<String,String> map);
	
	Integer queryTurnoverplanCount(Map<String,String> map);
	
	Integer queryTurnoverhdCount(Map<String,String> map);
	
	Integer queryTechorderAuditCount(Map<String,String> map);
	
	Integer queryTechorderCount(Map<String,String> map);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
