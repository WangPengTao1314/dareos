/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannServiceImpl.java
 */
package com.centit.erp.sale.erpprmtprice.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.erp.sale.erpprmtprice.mapper.ErpprmtpriceMapper;
import com.centit.erp.sale.erpprmtprice.model.ErpprmtpriceModel;
import com.centit.erp.sale.erpprmtprice.service.ErpprmtpriceService;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * The Class ChannServiceImpl.
 * 
 * @module 系统管理
 * @func 渠道信息
 * @version 1.0
 * @author 刘曰刚
 */
@Service
public class ErpprmtpriceServiceImpl  implements ErpprmtpriceService {
	@Autowired
	private ErpprmtpriceMapper mapper;
	/**
     * 查询并分页.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}
	
	@Transactional
	public void txInsertPrice(List<ErpprmtpriceModel> ModelList,String PRMT_PLAN_ID,UserBean userBean){
		List<Map<String,Object>> addList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> upList=new ArrayList<Map<String,Object>>();
		for (int i = 0; i < ModelList.size(); i++) {
			ErpprmtpriceModel model=ModelList.get(i);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("PRMT_PRVD_PRICE", model.getPRMT_PRVD_PRICE());
			map.put("PRVD_PRICE", model.getPRVD_PRICE());
			map.put("DECT_RATE", model.getDECT_RATE());
			map.put("IS_USE_REBATE", model.getIS_USE_REBATE());
			if(StringUtil.isEmpty(model.getPRMT_PRICE_ID())){
				map.put("PRMT_PRICE_ID", StringUtil.uuid32len());
				map.put("PRMT_PLAN_ID", PRMT_PLAN_ID);
				map.put("PRD_ID", model.getPRD_ID());
				map.put("STATE", "启用");
				map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
				map.putAll(LogicUtil.sysFild(userBean));
				addList.add(map);
			}else{
				map.put("PRMT_PRICE_ID", model.getPRMT_PRICE_ID());
				map.put("UPDATOR", userBean.getRYXXID());
				map.put("UPD_NAME", userBean.getXM());
				upList.add(map);
			}
		}
		if (!addList.isEmpty()) {
			mapper.insert(addList);
		}
		if (!upList.isEmpty()) {
			mapper.update(upList);
		}
	}
	public void txUpdateState(String PRMT_PRICE_IDS,String STATE,UserBean userBean){
		Map<String,String> map=new HashMap<String, String>();
		map.put("PRMT_PRICE_IDS", PRMT_PRICE_IDS);
		map.put("STATE", STATE);
		map.put("UPDATOR", userBean.getRYXXID());
		map.put("UPD_NAME", userBean.getXM());
		mapper.updateState(map);
	}
}
