/**
 * prjName:喜临门营销平台
 * ucName:人员信息人员信息
 * fileName:EmployeeServiceImpl
 */
package com.centit.base.employee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.base.employee.mapper.EmployeeMapper;
import com.centit.base.employee.model.EmployeeModel;
import com.centit.base.employee.model.EmployeeModelChld;
import com.centit.base.employee.service.EmployeeService;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * *@serviceImpl *@func *@version 1.1 *@author lyg *@createdate 2013-08-19
 * 14:35:52
 */
@Service
public class EmployeeServiceImpl  implements EmployeeService {
	
	@Autowired
	private EmployeeMapper mapper;
	/**
	 * @查询
	 * @param params
	 *            map
	 * @param pageNo
	 * 
	 * @return the ilistpage
	 */
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		 Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
	    	mapper.pageQuery(params);
			LogicUtil.transPageHelper(pageDesc, p);
	}

	/**
	 * * 增加 * @param params
	 * 
	 * @return true, if tx insert
	 */
	public boolean txInsert(Map<String, String> params) {
		mapper.insert(params);// 新增人员信息
		mapper.insertXTYH(params);// 新增用户信息
		mapper.insertXTYHZTDZ(params);// 新增系统用户帐套对照
		
		return true;
	}

	/**
	 * @删除
	 * @param RYXXID
	 * 
	 * @return true, if tx delete
	 */
	public boolean txDelete(Map<String, String> params) {
		// 删除人员主表
		mapper.updateById(params);
		// 删除子表用户表
		mapper.updateXTYH(params);
		
		return true;
	}

	/**
	 * * update data * @param params
	 * 
	 * 
	 * @return true, if tx update by id
	 */
	public boolean txUpdateById(Map<String, String> params) {
		mapper.updateXTYH(params);// 更新系统用户
		mapper.updateById(params);//  更新人员信息
		return true;
	}

    /* (non-Javadoc)
     * @see com.hoperun.sys.service.XTYHService#updatePassword(java.util.Map)
     */
    public void updatePassword(Map <String, String> params) {
    	mapper.updatePassword(params);
    }
	/**
	 * @编辑
	 * @Description :
	 * @param RYXXID
	 * @param MyorderModel
	 * @param userBean
	 *            .
	 * 
	 * @return true, if tx txEdit
	 */
	public String txEdit(String RYXXID, EmployeeModel model, UserBean userBean) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("XM", model.getXM());// 姓名
		params.put("XB", model.getXB());// 性别
		params.put("YHM", model.getYHM());// 用户名
		params.put("SJ", model.getSJ());// 手机
		params.put("GSDH", model.getGSDH());// 公司电话
		params.put("BMXXID", model.getBMXXID());// 部门信息id
		params.put("JGXXID", model.getJGXXID());// 机构信息id
		params.put("DZYJ", model.getDZYJ());// 电子邮件
		params.put("TERM_DECT_FROM", model.getTERM_DECT_FROM());// 可改终端销售折扣从
		params.put("TERM_DECT_END", model.getTERM_DECT_END());// 可改终端销售折扣到
		params.put("RYJB", model.getRYJB());// 人员级别
		params.put("RYLB", model.getRYLB());// 人员类别
		if (StringUtil.isEmpty(RYXXID)) {
			RYXXID = StringUtil.uuid32len();
			String prefix = userBean.getCHANN_NO();// 单头
			int length = 3;// 编号段长
			String RYBH = LogicUtil.getRYXXBillNo("RYBH", "T_SYS_RYXX", prefix,length, prefix, userBean);
			params.put("RYBH", RYBH);// 人员编号
			params.put("RYXXID", RYXXID);
			params.put("CRENAME", userBean.getXM());
			params.put("CREATER", userBean.getXTYHID());
			params.put("CRETIME", "系统时间");
			params.put("RYZT", BusinessConsts.JC_STATE_DEFAULT);
			params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
			params.put("YHZT", BusinessConsts.JC_STATE_DEFAULT);// 用户状态
			// 系统用户添加数据
			params.put("XTYHID", StringUtil.uuid32len());// 用户id
			params.put("ZTXXID", userBean.getLoginZTXXID());// 当前帐套
			params.put("YHKL", "e99a18c428cb38d5f260853678922e03");// 用户口令
			params.put("YHLB", "普通用户");// 用户类别
			params.put("IS_DRP_LEDGER", BusinessConsts.YJLBJ_FLAG_TRUE);
			// 系统用户帐套地址id
			params.put("XTYHZTDZID", StringUtil.uuid32len());// 系统用户帐套对照id
			txInsert(params);
		} else {
			params.put("UPDATER", userBean.getXTYHID());
			params.put("UPDTIME", "系统时间");
			params.put("RYXXID", RYXXID);
			txUpdateById(params);
		}
		return RYXXID;
	}

	/**
	 * @详细
	 * @param param
	 *            RYXXID
	 * @param param
	 *            the param
	 * 
	 * @return the map< string, string>
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> load(String RYXXID) {
		return mapper.loadById(RYXXID);
	}

	/**
	 * * 明细数据编辑
	 * 
	 * @param
	 * @param modelList
	 *            the model list
	 * 
	 * @return true, if tx child edit
	 */
	@Override
	public boolean txChildEdit(String RYXXID, List<EmployeeModelChld> chldList) {

		// 新增列表
		List<Map<String, String>> addList = new ArrayList<Map<String, String>>();
		// 修改列表
		List<Map<String, String>> updateList = new ArrayList<Map<String, String>>();
		for (EmployeeModelChld model : chldList) {
			Map<String, String> params = new HashMap<String, String>();
			// 如果没有明细ID的则是新增，有的是修改
			if (StringUtil.isEmpty(model.getXTYHJSID())
					|| model.getXTYHJSID().equals("undefined")) {
				params.put("XTYHJSID", StringUtil.uuid32len());
				params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
				params.put("STATE", BusinessConsts.JC_STATE_DEFAULT);
				params.put("XTJSID", model.getXTJSID());
				params.put("RYXXID", RYXXID);
				addList.add(params);
			} else {
				params.put("XTYHJSID", model.getXTYHJSID());
				params.put("XTJSID", model.getXTJSID());
				updateList.add(params);
			}
		}

		if (!updateList.isEmpty()) {
			mapper.updateChldById(updateList);
		}
		if (!addList.isEmpty()) {
			mapper.insertChld(addList);
		}
		return true;
	}

	/**
	 * * 根据主表Id查询子表记录
	 * 
	 * @param RYXXID
	 *            the RYXXID
	 * 
	 * @return the list< new master slavemx model>
	 */
	@Override
	public List<EmployeeModelChld> childQuery(String RYXXID) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("RYXXID", RYXXID);
		// 只查询0的记录。1为删除。0为正常
		params.put("DELFLAG", BusinessConsts.DEL_FLAG_COMMON);
		return mapper.queryChldByFkId(params);
	}

	/**
	 * * 根据子表Id批量加载子表信息
	 * 
	 * @param ADVC_ORDER_DTL_IDs
	 *            the ADVC_ORDER_DTL_IDs
	 * 
	 * @return the list< new master slavemx model>
	 */
	@Override
	public List<EmployeeModelChld> loadChilds(Map<String, String> params) {
		return mapper.loadChldByIds(params);
	}

	/**
	 * * 子表批量删除:软删除
	 * 
	 * @param ADVC_ORDER_DTL_IDs
	 *            the ADVC_ORDER_DTL_IDs
	 */
	@Override
	public void txBatch4DeleteChild(String XTYHJSIDS) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("XTYHJSIDS", XTYHJSIDS);
		params.put("DELFLAG", BusinessConsts.DEL_FLAG_DROP);
		mapper.deleteChldByIds(params);
	}

	/**
	 * 判断编号是否已经存在.
	 * 
	 * @param RYXXID
	 *            the rYXXID
	 * 
	 * @return the ryxx exits
	 */
	public int getRyxxExits(String RYBH) {
		return mapper.getExits(RYBH);
	}

	/**
	 * 通过当前登录人的渠道id获取终端销售折扣控制标记
	 */
	public String getTERM_DECT_CTR_FLAG(String CHANN_ID) {
		return mapper.getTERM_DECT_CTR_FLAG(CHANN_ID);
	}
	public int getRHMExits(String YHM){
		return mapper.getRHMExits(YHM);
	}
}