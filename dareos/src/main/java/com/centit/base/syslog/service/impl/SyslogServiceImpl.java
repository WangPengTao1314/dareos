/**
 * 
 */
package com.centit.base.syslog.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.base.syslog.mapper.SyslogMapper;
import com.centit.base.syslog.service.SyslogService;
import com.centit.commons.model.Consts;
import com.centit.commons.util.DateUtil;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.StringUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author gu_hongxiu
 *
 */
@Service
public class SyslogServiceImpl  implements SyslogService {
	@Autowired
	SyslogMapper mapper;

	/**
     * 分页查询
     * Description :.
     * 
     * @param params the params
     * @param pageNo the page no
     * 
     * @return the i list page
     */
	@Override
	public void pageQuery(Map<String, String> params, PageDesc pageDesc) {
		Page<Map<String,String>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
		mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	/**
     * 详细信息.
     * 
     * @param param the param
     * 
     * @return the map< string, string>
     */
	@Override
	public Map<String, String> load(String param) {
		return mapper.loadById(param);
	}
	
	public boolean insertDataRecycle(Map<String,String> map){
		mapper.insertDataRecycle(map);
		return true;
	}
	
	/**
	 * 插入日志
	 * @param UC_NAME 模块名称
	 * @param ACT_NAME 操作名称
	 * @param OPRATOR 调用/被调用
	 * @param STATE 状态
	 * @param REMARK 备注
	 * @param APPCODE 调用方APPCODE
	 * @param APPID UID
	 * @param OPRCODE 服务码+操作码
	 * @param KEY 业务单据主键
	 */
	public boolean doActLog(String UC_NAME,String ACT_NAME,String OPRATOR,String STATE,String REMARK,String APPCODE,String APPID,String OPRCODE,String KEY,String optContent){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("SYSLOG_ID", StringUtil.uuid32len());
		map.put("UC_NAME", UC_NAME);
		map.put("ACT_NAME", ACT_NAME);
		map.put("OPRATOR", OPRATOR);
		map.put("STATE", STATE);
		map.put("REMARK", REMARK);
		map.put("APPCODE", APPCODE);
		map.put("APPID", APPID);
		map.put("OPRCODE", OPRCODE);
		map.put("KEY", KEY);
		map.put("OPT_CONTENT", optContent);
		mapper.insertActLog(map);
		return true;
	}
}
