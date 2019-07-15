/**
 * 项目名称：平台
 * 系统名：基础数据
 * 文件名：ChannServiceImpl.java
 */
package com.centit.base.brand.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.base.brand.mapper.BrandMapper;
import com.centit.base.brand.model.BrandModel;
import com.centit.base.brand.service.BrandService;

import com.centit.commons.model.BusinessConsts;

import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.mapper.XTYHMapper;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @module 系统管理
 * @func 品牌信息
 * @version 1.0
 */
@Service
public class BrandServiceImpl  implements BrandService {
	
	@Autowired
	private BrandMapper mapper;
	@Autowired
	private XTYHMapper xtyhMapper;

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

	public Map<String, Object> loadById(String param) {
		return mapper.loadById(param);
	}

    /**
     * 软删除.
     * 
     * @param userBean the user bean
     * 
     */
    @Transactional
	public void delete(String brandId, UserBean userBean) {
    	Map <String, Object> params = new HashMap <String, Object>();
        params.put("BRAND_ID", brandId);
        params.put("UPDATOR", userBean.getXTYHID());
        params.put("UPD_NAME", userBean.getXM());
        params.put("DEL_FLAG", Integer.parseInt(BusinessConsts.DEL_FLAG_DROP));
        params.put("BRAND_ID_UUID", StringUtil.uuid32len());
        mapper.delete(params);
	}

	@Override
	public void txEdit(String brandId, BrandModel model, UserBean userBean) {
		Map <String, Object> params = new HashMap <String, Object>();
        params.put("BRAND", model.getBRAND());				//品牌名称
        params.put("BRAND_EN", model.getBRAND_EN());		//英文名称
        params.put("UPDATOR", userBean.getXTYHID());		//更新人ID
        params.put("UPD_NAME", userBean.getXM());			//更新人名称
        params.put("REMARK", model.getREMARK());			//备注
        if (StringUtils.isEmpty(brandId)) {
        	params.put("BRAND_ID", model.getBRAND_ID());		    //品牌编号
            params.put("STATE", BusinessConsts.JC_STATE_DEFAULT);	//状态
            params.put("CREATOR", userBean.getXTYHID());			//制单人ID
            params.put("CRE_NAME", userBean.getXM());				//制单人名称
            params.put("DEPT_ID", userBean.getBMXXID());			//制单部门ID
            params.put("DEPT_NAME", userBean.getBMMC());			//制单部门名称
            params.put("ORG_ID", userBean.getJGXXID());				//制单机构ID
            params.put("ORG_NAME", userBean.getJGMC());				//制单机构名称
            mapper.insert(params);
        } else {
        	params.put("BRAND_ID", model.getBRAND_ID());		    //品牌编号
            txUpdateById(params);
        }
	}
	
    /**
     * @param params the params
     */
	@Transactional
    public void txUpdateById(Map <String, Object> params) {
		mapper.updateById(params);        
    }
	
}
