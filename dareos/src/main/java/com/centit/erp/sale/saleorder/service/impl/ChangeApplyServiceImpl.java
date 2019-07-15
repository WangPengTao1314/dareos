/**
 * prjName:喜临门营销平台
 * ucName:标准订单
 * fileName:SaleorderServiceImpl
*/
package com.centit.erp.sale.saleorder.service.impl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.centit.common.service.FlowService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.StringUtil;
import com.centit.commons.webservice.NcWebserviceUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.erp.sale.saleorder.mapper.ChangeApplyMapper;
import com.centit.erp.sale.saleorder.mapper.SaleorderMapper;
import com.centit.erp.sale.saleorder.model.ChangeApplyModel;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.model.SaleorderModelChld;
import com.centit.erp.sale.saleorder.service.ChangeApplyService;
import com.centit.erp.sale.saleorder.service.SaleorderService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
 *
 * @ClassName: ChangeApplyServiceImpl
 * @Description: 销售订单变更申请service实现类
 * @author wang_dw
 * @date 2019年3月15日 下午2:00:34
 *
 */
@Service
public class ChangeApplyServiceImpl implements ChangeApplyService {

	@Autowired
	private ChangeApplyMapper mapper;
	@Autowired
	private SaleorderMapper saleorderMapper;

	@Autowired
	private SaleorderService saleorderService;
	@Autowired
	private FlowService flowService;

	/**
	 * @查询
	 * @param params map
	 * @param pageNo
	 *
	 * @return the ilistpage
	 */
	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		//变颜色的日期
		Page<Map<String, Object>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	/**
	 * 增加记录 Description :.
	 *
	 * @param params the params
	 *
	 * @return true, if tx insert
	 */
	@Transactional
	public boolean txInsert(Map <String, String> params) {
		mapper.insert(params);
		return true;
	}

	/**
	 * 更新记录 Description :.
	 *
	 * @param params the params
	 *
	 * @return true, if tx update by id
	 */
	@Transactional
	public boolean txUpdateById(Map <String, String> params) {
		mapper.updateById(params);
		return true;
	}

	/**
	 * @详细
	 * @param param CHANGE_APPLY_ID
	 *
	 * @return the map< string, Object>
	 */
	@Override
	public Map<String, Object> load(String param) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("CHANGE_APPLY_ID", param);
		return mapper.loadById(params);
	}

	@Override
	@Transactional
	public String txEdit(String CHANGE_APPLY_ID, ChangeApplyModel model, UserBean userBean){

		Map <String, String> params = new HashMap <String, String>();

		params.put("CHANGE_APPLY_ID", model.getCHANGE_APPLY_ID());
		params.put("CHANGE_APPLY_NO", model.getCHANGE_APPLY_NO());
		params.put("SALE_ORDER_ID", model.getSALE_ORDER_ID());
		params.put("CHAN_REMARK", model.getCHAN_REMARK());
		params.put("ORDER_REMARK", model.getORDER_REMARK());
		params.put("TELL_PERSON", model.getTELL_PERSON());
		params.put("ORDER_HIS", model.getORDER_HIS());
		params.put("ORDER_DTL_HIS", model.getORDER_DTL_HIS());
		params.put("STATE", model.getSTATE());
		params.put("LEDGER_ID", model.getLEDGER_ID());
		params.put("LEDGER_NAME", model.getLEDGER_NAME());
		params.put("AUDIT_ID", model.getAUDIT_ID());
		params.put("AUDIT_NAME", model.getAUDIT_NAME());
		params.put("AUDIT_TIME", model.getAUDIT_TIME());

		if(StringUtil.isEmpty(CHANGE_APPLY_ID)){ // 新增
			CHANGE_APPLY_ID = StringUtil.uuid32len();
			params.put("CHANGE_APPLY_ID", CHANGE_APPLY_ID);
			params.put("CHANGE_APPLY_NO", LogicUtil.getBmgz("CHANGE_APPLY_NO_SEQ"));
			params.put("CREATOR", userBean.getXTYHID());
			params.put("CRE_NAME", userBean.getXM());
			params.put("CRE_TIME", "sysdate");
			params.put("UPDATOR", userBean.getXTYHID());
			params.put("UPD_NAME", userBean.getXM());
			params.put("UPD_TIME", "sysdate");
			params.put("DEPT_ID", userBean.getBMXXID());
			params.put("DEPT_NAME", userBean.getBMMC());
			params.put("ORG_ID", userBean.getJGXXID());
			params.put("ORG_NAME", userBean.getJGMC());

			params.put("STATE",BusinessConsts.GOODSORDER_STATE_CG);//草稿
			params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);

			mapper.insert(params);
		} else{
			params.put("CHANGE_APPLY_ID", CHANGE_APPLY_ID);
			params.put("UPDATOR", userBean.getXTYHID());
			params.put("UPD_NAME", userBean.getXM());
			params.put("UPD_TIME", "sysdate");
			mapper.updateById(params);
		}

		return CHANGE_APPLY_ID;
	}

	@Override
	@Transactional
	public String txCommit(String CHANGE_APPLY_ID, ChangeApplyModel model, UserBean userBean) throws Exception{
		if(StringUtil.isEmpty(CHANGE_APPLY_ID)){ // 新增
			CHANGE_APPLY_ID = this.txEdit(CHANGE_APPLY_ID, model, userBean);
		}
		model.setSTATE(BusinessConsts.COMMIT);
		this.txEdit(CHANGE_APPLY_ID, model, userBean);

		// 流程流转
		//flowService.insertNextFlow(model.getSALE_ORDER_ID(), model.getFACTORY_NO(), model.getORDER_TRACE_ID(), BusinessConsts.ORDER_CHANGE_FLOW_NO, userBean, model.getAuditContents());
		//flowService.insertNextFlowByIndexNo(model.getSALE_ORDER_ID(), model.getFACTORY_NO(), model.getORDER_TRACE_ID(), BusinessConsts.ORDER_CHANGE_FLOW_NO, userBean, "", model.getAuditContents());

		// 流程跟踪
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), BusinessConsts.TYPE_CHANGE);
		flowService.insertFlowTrack(flowNo, model.getORDER_TRACE_ID(), model.getFACTORY_NO(), userBean, "", "提交变更申请");

		return CHANGE_APPLY_ID;
	}

	@Override
	@Transactional
	public void txChange(String CHANGE_APPLY_ID, String SALE_ORDER_ID, ChangeApplyModel changeApplyModel, SaleorderModel model,List<SaleorderModelChld> chldList, UserBean userBean)throws Exception{
		if ("T".equals(changeApplyModel.getAuditStatus())) {
			// 同意变更

			// 保存销售订单历史数据
			Map<String, Object> order = saleorderService.load(SALE_ORDER_ID);
			List<Map<String, String>> orderDtl = saleorderService.childQuery(SALE_ORDER_ID);
			String ORDER_HIS = JSONObject.toJSONString(order);
			//System.err.println(ORDER_HIS);
			String ORDER_DTL_HIS = JSONObject.toJSONString(orderDtl);
			//System.err.println(ORDER_DTL_HIS);
			changeApplyModel.setORDER_HIS(ORDER_HIS);
			changeApplyModel.setORDER_DTL_HIS(ORDER_DTL_HIS);
			changeApplyModel.setAUDIT_ID(userBean.getXTYHID());
			changeApplyModel.setAUDIT_NAME(userBean.getXM());
			changeApplyModel.setAUDIT_TIME(BusinessConsts.UPDATE_TIME);
			changeApplyModel.setSTATE(BusinessConsts.PASS);//同意变更-审核通过
			txEdit(CHANGE_APPLY_ID, changeApplyModel, userBean);

			// 保存变更后的销售订单信息
			model.setCHANGE_FLAG(BusinessConsts.INTEGER_1);//变更中=1
			saleorderService.txEdit(SALE_ORDER_ID, model, chldList, userBean);

			// 调用NC接口
			SaleorderModel modelDb = (SaleorderModel) LogicUtil.tranMap2Bean(load(CHANGE_APPLY_ID), SaleorderModel.class);
			String type = BusinessConsts.INTEGER_2;
			String content = modelDb.getNC_ID();
			if (StringUtil.isEmpty(content)) {
				type = BusinessConsts.INTEGER_1;
			}
			Map<String, String> resultMap = NcWebserviceUtil.djSaleOrder(type, content, modelDb, chldList);
			String flag = resultMap.get("flag");
			if (BusinessConsts.SUCCESS.equals(flag)) {
				// 流程流转
				String nextState = BusinessConsts.GOODSORDER_STATE_STZ;
				//flowService.insertNextFlow(SALE_ORDER_ID, obj.getFACTORY_NO(), obj.getORDER_TRACE_ID(), BusinessConsts.ORDER_CHANGE_FLOW_NO, userBean, changeApplyModel.getAuditContents());
				String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), BusinessConsts.TYPE_CHANGE);
				flowService.insertNextFlowByIndexNo(SALE_ORDER_ID, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, nextState, changeApplyModel.getAuditContents());

			} else {
				String message = resultMap.get("message");
				throw new ServiceException(message);
			}
		} else {
			// 不同意变更
			changeApplyModel.setAUDIT_ID(userBean.getXTYHID());
			changeApplyModel.setAUDIT_NAME(userBean.getXM());
			changeApplyModel.setAUDIT_TIME(BusinessConsts.UPDATE_TIME);
			changeApplyModel.setSTATE(BusinessConsts.REJECT);//不同意变更-否决
			txEdit(CHANGE_APPLY_ID, changeApplyModel, userBean);

			// 流程跟踪
			String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), BusinessConsts.TYPE_CHANGE);
			flowService.insertFlowTrack(flowNo, changeApplyModel.getORDER_TRACE_ID(), changeApplyModel.getFACTORY_NO(), userBean, changeApplyModel.getAuditContents(), "退回变更申请");;

		}

	}


	@Override
	@Transactional
	public void txAudit(String SALE_ORDER_ID, SaleorderModel model,
			List<SaleorderModelChld> chldList, UserBean userBean,
			String option) throws Exception {
		if ("audit".equals(option) ) {
			// 保存主表上传的文件
			String attPath = model.getAttPath();
			if (!StringUtil.isEmpty(attPath)) {
				InterUtil.delByFromId(SALE_ORDER_ID);
				String[] arr = attPath.split(",");
				for (int i = 0; i < arr.length; i++) {
					InterUtil.insertAttPath(Arrays.asList(arr), userBean, SALE_ORDER_ID);
				}
			}
		}
		txChildAudit(SALE_ORDER_ID, chldList, userBean, option);

		// 流程跟踪
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), BusinessConsts.TYPE_CHANGE);
		if ("T".equals(model.getAuditStatus())) {
			flowService.insertNextFlow(SALE_ORDER_ID, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, model.getAuditContents());
		} else {
			flowService.backFlow(SALE_ORDER_ID, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, model.getAuditContents());
		}
	}

	@Transactional
	public boolean txChildAudit(String SALE_ORDER_ID,
			List<SaleorderModelChld> chldList, UserBean userBean, String option) {
		// 新增列表
		List<Map<String, String>> addList = new ArrayList<Map<String, String>>();
		// 修改列表
		List<Map<String, String>> updateList = new ArrayList<Map<String, String>>();
		for (SaleorderModelChld model : chldList) {
			Map<String, String> child = new HashMap<String, String>();
			child.put("GROUP_NO", model.getGROUP_NO());
			child.put("PRD_ID", model.getPRD_ID());
			child.put("PRD_NO", model.getPRD_NO());
			child.put("PRD_NAME", model.getPRD_NAME());
			child.put("PRD_SPEC", model.getPRD_SPEC());
			child.put("PRD_COLOR", model.getPRD_COLOR());
			child.put("BRAND", model.getBRAND());
			child.put("STD_UNIT", model.getSTD_UNIT());
			child.put("HOLE_SPEC", model.getHOLE_SPEC());
			//child.put("PRD_SIZE", model.getPRD_SIZE());
			child.put("PRD_QUALITY", model.getPRD_QUALITY());
			child.put("PRD_COLOR", model.getPRD_COLOR());
			child.put("PRD_TOWARD", model.getPRD_TOWARD());
			child.put("PRD_GLASS", model.getPRD_GLASS());
			child.put("PRD_OTHER", model.getPRD_OTHER());
			child.put("PRD_SERIES", model.getPRD_SERIES());
			child.put("PRICE", model.getPRICE());
			child.put("DECT_RATE", model.getDECT_RATE());
			child.put("DECT_PRICE", model.getDECT_PRICE());
			child.put("ORDER_NUM", model.getORDER_NUM());
			child.put("ORDER_AMOUNT", model.getORDER_AMOUNT());
			//child.put("ORDER_RECV_DATE", model.getORDER_RECV_DATE());// 要求到货日期
			child.put("REMARK", model.getREMARK());// 备注
			child.put("SALE_ORDER_ID", SALE_ORDER_ID);

			// 如果没有明细ID的则是新增，有的是修改
			if (StringUtil.isEmpty(model.getSALE_ORDER_DTL_ID())) {
				child.put("SALE_ORDER_DTL_ID", StringUtil.uuid32len());
				child.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
				child.put("IS_BACKUP_FLAG", BusinessConsts.INTEGER_0);// 是否抵库标记,默认设置未0
				addList.add(child);
			} else {
				child.put("SALE_ORDER_DTL_ID", model.getSALE_ORDER_DTL_ID());
				updateList.add(child);
			}

			if ("audit".equals(option)) {
				// 保存上传的文件
				String attPath = model.getAttPath();
				if (!StringUtil.isEmpty(attPath)) {
					String dtlId = StringUtil.nullToSring(child.get("SALE_ORDER_DTL_ID"));
					InterUtil.delByFromId(dtlId);
					String[] arr = attPath.split(",");
					InterUtil.insertAttPath(Arrays.asList(arr), userBean, dtlId);
				}
			}
		}

		if ("quote".equals(option)) {
			if (!updateList.isEmpty()) {
				saleorderMapper.updateChldById(updateList);
			}
			if (!addList.isEmpty()) {
				saleorderMapper.insertChld(addList);
			}
		}
		return true;
	}

	@Override
	@Transactional
	public void txConfirm(String SALE_ORDER_ID, SaleorderModel model, String option, UserBean userBean) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("AUDIT_ID", userBean.getXTYHID());
		params.put("AUDIT_NAME", userBean.getXM());
		params.put("AUDIT_TIME", BusinessConsts.UPDATE_TIME);

		// 流程跟踪
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), BusinessConsts.TYPE_CHANGE);
		if ("T".equals(model.getAuditStatus())) {
			flowService.insertNextFlow(SALE_ORDER_ID, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, model.getAuditContents());
			params.put("confirmstatus", option);

			if ("confirm".equals(option)) {
				params.put("CHANGE_FLAG", BusinessConsts.INTEGER_2);//领导审核通过，销售订单变更标记改为2

				//变更申请单状态改为
				params.put("CHANGE_APPLY_ID", model.getCHANGE_APPLY_ID());
				params.put("STATE", BusinessConsts.DONE);
				mapper.updateById(params);
			}
		} else {
			flowService.backFlow(SALE_ORDER_ID, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, model.getAuditContents());
		}

		params.remove("STATE");
		params.put("SALE_ORDER_ID", SALE_ORDER_ID);
		saleorderMapper.updateById(params);
	}

	@Override
	@Transactional
	public void txBack(String SALE_ORDER_ID,String no, String RETURN_RSON, String flowServiceId, UserBean userBean){
		// 流程流转
		flowService.backFlow(SALE_ORDER_ID, no, flowServiceId, BusinessConsts.ORDER_CHANGE_FLOW_NO, userBean, RETURN_RSON);

		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", SALE_ORDER_ID);
		params.put("AUDIT_ID", userBean.getXTYHID());
		params.put("AUDIT_NAME", userBean.getXM());
		params.put("AUDIT_TIME", BusinessConsts.UPDATE_TIME);
		params.put("RETURN_RSON", RETURN_RSON);
		saleorderMapper.updateById(params);
	}

}