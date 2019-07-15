package com.centit.system.optlog.entity;

import java.io.Serializable;

/**
 * @ClassName: SysLogEntity 
 * @Description: 操作日期-Entity
 * @author: zhu_hj
 * @date: 2018年5月17日 上午10:22:05
 */
public class SysLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
    private String logId; // 日志ID

    private String logType; // 日志类型

    private String userId; // 用户ID

    private String userName; // 用户名

    private String deptName; // 所属部门

    private String orgName; // 所属机构

    private String loginIp; // 登录IP

    private String actTime; // 记录时间

    private String sysName; // 来源系统

    private String actRemark; // 说明
    
    private String actArguments; // 参数
    
    private Integer isError = 0; // 是否抛出异常后记录的日志
    
    private String errMsg; // 异常信息
    
    private boolean ignoreArgs = false; // 是否需要保存参数

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getActTime() {
		return actTime;
	}

	public void setActTime(String actTime) {
		this.actTime = actTime;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getActRemark() {
		return actRemark;
	}

	public void setActRemark(String actRemark) {
		this.actRemark = actRemark;
	}

	public String getActArguments() {
		return actArguments;
	}

	public void setActArguments(String actArguments) {
		this.actArguments = actArguments;
	}

	public Integer getIsError() {
		return isError;
	}

	public void setIsError(Integer isError) {
		this.isError = isError;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public boolean isIgnoreArgs() {
		return ignoreArgs;
	}

	public void setIgnoreArgs(boolean ignoreArgs) {
		this.ignoreArgs = ignoreArgs;
	}
	
}