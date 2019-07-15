package com.centit.system.optlog.service;

import java.util.Map;

import com.centit.core.exception.BizException;
import com.centit.core.po.PageDesc;
import com.centit.system.optlog.entity.SysLogEntity;

/**
 * @ClassName: OptLogService 
 * @Description: 操作日志-Service
 * @author: zhu_hj
 * @date: 2018年5月17日 上午10:21:01
 */
public interface OptLogService {
	
	void query(Map<String, Object> map, PageDesc pageDesc);
	
	void insert(SysLogEntity log);
	
	void saveServiceLog(Map<String, Object> map) throws BizException;
	
	void saveTerminalLog(Map<String, Object> map) throws BizException;
}
