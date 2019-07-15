package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.base.product.model.ProductUserModel;
import com.centit.sys.model.XTYHBean;
import com.centit.sys.model.ZTWHModel;


@Repository
public interface XTYHMapper {
	
	/**
	 * 
	 * @Title: getZtxx 
	 * @Description: 根据用户名查询帐套信息
	 * @author: liu_yg
	 * @date: 2019年2月27日 下午2:27:05 
	 * @param userId
	 * @return
	 * @return: List<String>
	 */
	List<Map<String,String>> getZtxx(String userId);
	List<Map<String,String>> getNotices(String userId);
	Map<String,String> query(Map<String,String> map);
	
	Map<String,String> queryy(Map<String,String> map);
	
	List<Map<String,String>> pageQuery(Map<String,Object> map);
	
	void insert(Map<String,String> map);
	
	void insertJgfgMx(Map<String,String> map);
	
	void insertBmfgMx(Map<String,String> map);
	
	void insertZtfgMx(Map<String,String> map);
	
	void updateById(Map<String,String> map);
	
	void insertXTYH(Map<String,String> map);
	
	void updateOne(String XTYHID);
	
	Map<String,String> loadById(String XTYHID);
	
	void updateUserStatusById(Map<String,Object> map);
	
	void updatePassword(Map<String,Object> map);
	
	List<XTYHBean> selectAllYhbh();
	
	List<Map<String,String>> queryJgfgMxByXtyhid(String XTYHID);
	
	List<Map<String,String>> queryBmfgMxByXtyhid(String XTYHID);
	
	List<Map<String,String>> queryZtfgMxByXtyhid(String XTYHID);
	
	void deleteJgfgMx(@Param("XTYHJGFGID") String XTYHJGFGID);
	
	void deleteBmfgMx(@Param("XTYHJGFGID") String XTYHJGFGID);
	
	void deleteZtfgMx(@Param("XTYHJGFGID") String XTYHJGFGID);
	
	String getXTYHBH();
	
	String getRyxxidByXtyhid(String XTYHID);
	
	void deleteJgfg(Map<String,String> map);
	
	void deleteBmfg(Map<String,String> map);
	
	void deleteZtfg(Map<String,String> map);
	
	List<ProductUserModel> loadUserByIDS(@Param("USER_CHARG_PRD_IDS") String USER_CHARG_PRD_IDS);
	
	void delUserByIDS(@Param("USER_CHARG_PRD_IDS") String USER_CHARG_PRD_IDS);
	
	Integer checkPRD(Map<String,String> map);
	
	void addCharg(List<Map<String,String>> list);
	
	void upCharg(List<Map<String,String>> list);
	
	List<ProductUserModel> queryChld(@Param("XTYHID") String XTYHID);
	
	void upUserChannCharg(Map<String, String> params);
	
	void upXtyhChargFlag(Map<String, String> params);
	
	void delUserChannCharg(Map<String, String> params);
	
	List<Map<String,String>> queryUserList(Map<String,Object> params);
	
	void deleteStepUser(@Param("USER_IDS") String USER_IDS);
	
	void insertStepUser(List<Map<String,String>> list);
	
	void delChannChargByChangeChann(Map <String, String> params);
	
	void insertUserChannChargByChangeChann(Map <String, String> params);
		
	void delLedChrgByLedIds(Map<String,String> map);
	void insertLegerChrg(List<Map<String,String>> list);
	List<Map<String,String>> getLedgerChrgListByIds(@Param("XTYH_LEDGER_IDS")String XTYH_LEDGER_IDS);
	List<String> checkLadgerDup(String productId);
	void delLedChrgByLedChannId(Map<String,String> map);
	List<Map<String,String>> getLedgerChrgList(String XTYHID);
	
	
}
