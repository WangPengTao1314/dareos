package com.centit.erp.sale.prmtplan.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.erp.sale.prmtplan.model.PrmtplaAreaModel;
import com.centit.erp.sale.prmtplan.model.PrmtplanModel;
import com.centit.erp.sale.prmtplan.model.PrmtplanModelChld;
import com.centit.sys.model.NoticeModel;


@Repository
public interface PrmtplanMapper {
	
	void insert(Map<String,String> params);
	void delete(Map<String,String> params);
	void delChldByFkId(Map<String,String> params);
	void updateById(Map<String,String> params);
	List<Map<String,String>> pageQuery(Map<String,Object> map);
	
	List<PrmtplanModel> queryPrmt(Map<String,Object> map);
	
	List<NoticeModel> queryNotice(Map<String,Object> map);
	
	List<NoticeModel> queryNoticeForErp(Map<String,Object> map);
	
	List<NoticeModel> queryNoticers(String RYXXID);
	
	List<NoticeModel> queryAllNotices(Map<String,Object> map);
	
	List<NoticeModel> pageQueryNotice(Map<String,Object> map);
	
	List<NoticeModel> pageAllQueryNotice(Map<String,Object> map);
	
	List<Map<String,String>> pageQueryPrmt(Map<String,Object> map);
	
	List<Map<String,String>> queryStore(String LEDGER_ID);
	
	List<Map<String,String>> queryBar(Map<String,Object> map);
	
	List<Map<String,String>> queryDeliver(Map<String,String> map);
	
	int queryPrdRetuenReqCount(Map<String,String> map);
	
	int queryStoreIn(Map<String,String> map);
	
	int queryOrderCount(Map<String,String> map);
	
	List<Map<String,String>> queryCount();
	
	Map<String,String> loadById(String PRMT_PLAN_ID);
	void deleteChldByIds(Map<String,String> map);
	void updateChldById(List<Map<String,String>> list);
	void insertChld(List<Map<String,String>> list);
	
	List<PrmtplanModelChld> queryChldByFkId(Map<String,String> map);
	List<PrmtplanModelChld> loadChldByIds(Map <String, String> params);
	List<PrmtplaAreaModel> queryAreaByFkId(Map<String,String> params);
	void deleteAreaByIds(Map <String, String> params);
	List <PrmtplaAreaModel> loadAreaByIds(Map <String, String> params);
	
	void updateAreaById(List<Map<String,String>> list);
	void insertArea(List<Map<String,String>> list);
	List<Map<String,String>> findAreaByPid(Map <String, String> params);
	
	
	
	
	
	
	
	
	
	
	
	
}
