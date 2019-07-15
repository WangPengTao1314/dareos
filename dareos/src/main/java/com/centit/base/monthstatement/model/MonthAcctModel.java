package com.centit.base.monthstatement.model;

public class MonthAcctModel {
	/**
	 * 月结ID
	 */
	private String monthAcctId;
	/**
	 * 年份
	 */
	private String year;
	/**
	 * 月份
	 */
	private String month;
	/**
	 * 期初日期
	 */
	private String begDate;
	/**
	 * 期末日期
	 */
	private String endDate;
	/**
	 * 是否月结(1:是)
	 */
	private String isMonthAcct;
	public String getMonthAcctId() {
		return monthAcctId;
	}
	public void setMonthAcctId(String monthAcctId) {
		this.monthAcctId = monthAcctId;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getBegDate() {
		return begDate;
	}
	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getIsMonthAcct() {
		return isMonthAcct;
	}
	public void setIsMonthAcct(String isMonthAcct) {
		this.isMonthAcct = isMonthAcct;
	}
	
	
}
