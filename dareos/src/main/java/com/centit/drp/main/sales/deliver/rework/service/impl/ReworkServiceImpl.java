package com.centit.drp.main.sales.deliver.rework.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.common.service.BookkeepingService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.ParamUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.project.management.service.ProjectConsts;
import com.centit.drp.main.sales.deliver.order.model.OrderModel;
import com.centit.drp.main.sales.deliver.order.model.SendDtlModel;
import com.centit.drp.main.sales.deliver.rework.mapper.ReworkMapper;
import com.centit.drp.main.sales.deliver.rework.service.ReworkService;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.service.SaleorderService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 售后管理 返修单Service
 * 
 * @author wang_pt
 *
 */
@Service
public class ReworkServiceImpl implements ReworkService {

	@Autowired
	private ReworkMapper reworkMapper;
	
	@Autowired
	private SaleorderService saleorderService;
	
	@Autowired
	private BookkeepingService bookkeepingService;
	
	/**
	 * 删除改变状态 del_flag (1：删除，0：新增)
	 */
	private static Integer PRO_del_flag = 1;

	/**
	 * 获取项目编码
	 * 
	 * @return
	 */
	public String getManager() {
		String str = LogicUtil.getBmgz("REWORK_ORDER_NO_SEQ");
		return str;
	}

	/**
	 * 更新状态
	 */
	@Transactional
	public void delete(String sale_order_id, UserBean userBean) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("SALE_ORDER_ID", sale_order_id);
		params.put("updator", userBean.getXTYHID());
		params.put("upd_name", userBean.getXM());
		params.put("del_flag", PRO_del_flag);
		reworkMapper.updateByID(params);
		params.put("SQL","sale_order_id ='"+sale_order_id+"'");
		reworkMapper.updateSunByID(params);
	}
	
	@Transactional
	public void deleteDtl(String sale_order_dtl_ids, UserBean userBean) {
		Map<String, Object> params = null;
		// 批量删除时，id是用‘,’分开的字符串
		String[] ids = sale_order_dtl_ids.replace("'", "").trim().split(",");
		for (String p_id : ids) {
			params = new HashMap<String, Object>();
			params.put("del_flag", PRO_del_flag);
			params.put("SQL","sale_order_dtl_id ='"+p_id+"'");
			reworkMapper.updateSunByID(params);
		}
	}

	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		Page<Object> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		reworkMapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	@Override
	public Integer counts(Map<String, Object> params) {
		return reworkMapper.counts(params);
	}

	@Override
	public Map<String, Object> queryBack(Map<String, Object> params) {
		Map<String, Object> map = reworkMapper.toQueryBack(params);
		String attPathP = LogicUtil.getAttPath(params.get("SALE_ORDER_IDS").toString());
		map.put("ATT_PATH", attPathP);

		// 附件
		// List<Map<String,String>>listPath=InterUtil.findAttInfo(map.get("SALE_ORDER_ID").toString());
		// 子表数据
		map.put("entrySun", reworkMapper.toQuerySunBack(params));
		return map;
	}

	@Override
	public Map<String, Object> query(Map<String, Object> params) {
		Map<String, Object> map = reworkMapper.toQuery(params);
		String attPathP = LogicUtil.getAttPath(params.get("SALE_ORDER_ID").toString());
		map.put("ATT_PATH", attPathP);
		// 附件
		// List<Map<String,String>>listPath=InterUtil.findAttInfo(map.get("SALE_ORDER_ID").toString());
		List<Map<String, Object>> listMap = reworkMapper.toQuerySun(params);
		if(listMap!=null){
			for(Map<String, Object>listMaps:listMap) {
				listMaps.put("ATT_PATH", LogicUtil.getAttPath(listMaps.get("SALE_ORDER_DTL_ID").toString()));
			}
		}
		map.put("entrySun", listMap);
		return map;
	}

	@Transactional
	public void edit(OrderModel model, List<SendDtlModel> chldModelList, HttpServletRequest request) {
		// 获取用户信息
		UserBean userBean = ParamUtil.getUserBean(request);
		model.setDept_id(userBean.getBMXXID());// 所属部门id
		model.setDept_name(userBean.getBMMC());// 所属部门
		//统计总的订货金额
		Double ORDER_AMOUNT=0.0;
		if (chldModelList != null) {
			for (int i = 0; i < chldModelList.size(); i++) {
				SendDtlModel models = chldModelList.get(i);
				ORDER_AMOUNT +=Double.valueOf(StringUtils.isBlank(models.getOrder_amount())?"0.0":models.getOrder_amount());
			}
			model.setTotal_amount(ORDER_AMOUNT.toString());
		}
		//厂编：即是 返修单号
		if (StringUtils.isBlank(model.getFactory_no())) {
			model.setFactory_no(LogicUtil.getReworkNo(model.getLedger_id()));
		}
		if (StringUtils.isNotEmpty(model.getSale_order_id())) {
			model.setUpdator(userBean.getXTYHID());
			model.setUpd_name(userBean.getXM());
			reworkMapper.modify(model);
		} else {
			model.setSale_order_id(StringUtil.uuid32len());
			model.setCreator(userBean.getXTYHID());// 制单人ID
			model.setCre_name(userBean.getXM());
			model.setState(ProjectConsts.SENDORDER_DRAFT);
			reworkMapper.insert(model);
		}
		// 添加附件
		//if (StringUtils.isNotEmpty(model.getATT_PATH())) {
			InterUtil.delByFromId(model.getSale_order_id());
			List<String> listPath = Arrays.asList(model.getATT_PATH().split(","));
			InterUtil.insertAttPath(listPath, userBean, model.getSale_order_id());
		//}
		// 从表  
		List<SendDtlModel> addList = new ArrayList<SendDtlModel>();
		List<SendDtlModel> upList = new ArrayList<SendDtlModel>();
		if (chldModelList != null) {
			for (int i = 0; i < chldModelList.size(); i++) {
				SendDtlModel models = chldModelList.get(i);
				if (StringUtils.isNotEmpty(models.getSale_order_dtl_id())) {
					upList.add(models);
				} else {
					models.setSale_order_dtl_id(StringUtil.uuid32len());
					models.setSale_order_id(model.getSale_order_id());
					addList.add(models);
				}
			}
			List<String> listPaths = null;//
			
			if (!upList.isEmpty()) {
				for (SendDtlModel modelDel : upList) {
					reworkMapper.modifySun(modelDel);
					InterUtil.delByFromId(modelDel.getSale_order_dtl_id());
					if (StringUtils.isNotEmpty(modelDel.getATT_PATH())) {
						listPaths = Arrays.asList(modelDel.getATT_PATH().split(","));
						InterUtil.insertAttPath(listPaths, userBean, modelDel.getSale_order_dtl_id());
					}
				}
			}
			if (!addList.isEmpty()) {
				for (SendDtlModel modelDel : addList) {
					modelDel.setFrom_bill_dtl_id(modelDel.getSale_order_dtl_id());
					reworkMapper.insertSun(modelDel);
					InterUtil.delByFromId(modelDel.getSale_order_dtl_id());
					if (StringUtils.isNotEmpty(modelDel.getATT_PATH())) {
						listPaths = Arrays.asList(modelDel.getATT_PATH().split(","));
						InterUtil.insertAttPath(listPaths, userBean, modelDel.getSale_order_dtl_id());
					}
				}
			}
			
			//校验返修单号
			String factory_no="factory_no='"+model.getFactory_no().trim()+"'";
			if (reworkMapper.checkFactoryNO(factory_no) > 1) {
				throw new ServiceException("返修订单号："+model.getFactory_no()+" 已存在！！！");
			}
			//校验原订单编号
//			factory_no="from_bill_no='"+model.getFrom_bill_no().trim()+"'";
//			if (reworkMapper.checkFactoryNO(factory_no) > 1) {
//				throw new ServiceException("原订单编号："+model.getFrom_bill_no()+" 已存在！！！");
//			}
		}
	}

	@Transactional
	public void submit(OrderModel model,UserBean userBean) {
		reworkMapper.modify(model);
		//
		//转erp
		if("生产中".equals(model.getState())) {
			bookkeepingService.reworkAmount(model.getSale_order_id());
			//
			Map<String, Object> entry = saleorderService.load(model.getSale_order_id());
			SaleorderModel models = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
			models.setBILL_TYPE("售后订单");
			saleorderService.turnERP(model.getSale_order_id(), models, userBean);
		}
		
	}

}
