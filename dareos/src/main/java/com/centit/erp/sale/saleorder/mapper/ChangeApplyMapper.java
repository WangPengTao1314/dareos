package com.centit.erp.sale.saleorder.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.erp.sale.saleorder.model.ChangeApplyModel;
import com.centit.sys.model.UserBean;

/**
 *
 * @ClassName: ChangeApplyMapper
 * @Description: 销售订单变更申请Mapper
 * @author wang_dw
 * @date 2019年3月14日 下午2:15:46
 *
 */
@Repository
public interface ChangeApplyMapper {
	/**
	 *
	 * @Title: pageQuery
	 * @Description: 销售订单变更申请查询
	 * @author wang_dw
	 * @date 2019年3月14日 下午2:17:08
	 * @param @param pageQureyMap
	 * @param @return
	 * @return List<ChangeApplyModel>
	 * @throws
	 */
	public List<ChangeApplyModel>  pageQuery(Map<String, Object> pageQureyMap);

	public void insert(Map<String, String> params);

	/**
	 * @主从保存编辑
	 * @Description :
	 * @param CHANGE_APPLY_ID
	 * @param erpStoreIn
	 * @param userBean.
	 *
	 * @return
	 */
	public void txEdit(String CHANGE_APPLY_ID, ChangeApplyModel obj, UserBean userBean);

	/**
	 * @主表删除
	 * @param Map
	 *
	 * @return true, if tx delete
	 */
	public void delete(Map <String, String> params);

	public void updateById(Map<String,String> params);

	/**
	 * @主表详细页面
	 * @param param CHANGE_APPLY_ID
	 *
	 * @return the map< string, Object>
	 */
	public Map<String,Object> loadById(Map<String,String> param);

	/**
	 * 提交
	 * @param CHANGE_APPLY_ID 销售订单变更申请ID
	 */
	public void txToCommit(String CHANGE_APPLY_ID ,String SALE_ORDER_NO,UserBean userBean);


	/**
	 * * 根据主表Id查询生命周期子表记录
	 * @param CHANGE_APPLY_ID the CHANGE_APPLY_ID
	 * @return the list
	 */
	public List <Map<String,String>> queryTrace(String CHANGE_APPLY_ID);

}
