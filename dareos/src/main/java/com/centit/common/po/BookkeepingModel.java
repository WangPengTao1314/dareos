package com.centit.common.po;

import java.io.Serializable;

/**
 * 流水表
 * @author liu_yg
 *
 */
public class BookkeepingModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 流水ID
	 */
	private String bookkeepingId;
	/**
	 * 业务单据ID
	 */
	private String billId;
	/**
	 * 业务单据编号
	 */
	private String billNo;
	/**
	 * 业务类型
	 */
	private String billType;
	/**
	 * 流水类型
	 */
	private String bookkeepingType;
	/**
	 * 金额
	 */
	private String amountTotal;
	/**
	 * 返利
	 */
	private String rebateTotal;
	/**
	 * 信用
	 */
	private String creditTotal;
	/**
	 * 操作时间
	 */
	private String optionTime;
	/**
	 * 操作人
	 */
	private String optionPerson;
	/**
	 * 账套
	 */
	private String ledgerId;
	/**
	 * 账套名称
	 */
	private String ledgerName;
	/**
	 * 经销商ID
	 */
	private String channId;
	/**
	 * 经销商编号
	 */
	private String channNo;
	/**
	 * 经销商名称
	 */
	private String channName;
	
	/**
	 * 计算方向  0:加  1:减
	 */
	private String direction;
	
	/**
	 * 操作后总金额
	 */
	private String amountMoney;
	/**
	 * 操作后冻结金额
	 */
	private String freezMoney;
	/**
	 * 操作后总返利
	 */
	private String amountRebate;
	/**
	 * 操作后冻结返利
	 */
	private String freezRebate;
	/**
	 * 操作后总信用
	 */
	private String amuntCredit;
	/**
	 * 操作后冻结信用
	 */
	private String freezCredit;
	/**
	 * 操作后欠扣信用
	 */
	private String oweCredit;
	
	public String getBookkeepingId() {
		return bookkeepingId;
	}
	public void setBookkeepingId(String bookkeepingId) {
		this.bookkeepingId = bookkeepingId;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getBookkeepingType() {
		return bookkeepingType;
	}
	public void setBookkeepingType(String bookkeepingType) {
		this.bookkeepingType = bookkeepingType;
	}
	public String getAmountTotal() {
		return amountTotal;
	}
	public void setAmountTotal(String amountTotal) {
		this.amountTotal = amountTotal;
	}
	public String getRebateTotal() {
		return rebateTotal;
	}
	public void setRebateTotal(String rebateTotal) {
		this.rebateTotal = rebateTotal;
	}
	public String getCreditTotal() {
		return creditTotal;
	}
	public void setCreditTotal(String creditTotal) {
		this.creditTotal = creditTotal;
	}
	public String getOptionTime() {
		return optionTime;
	}
	public void setOptionTime(String optionTime) {
		this.optionTime = optionTime;
	}
	public String getOptionPerson() {
		return optionPerson;
	}
	public void setOptionPerson(String optionPerson) {
		this.optionPerson = optionPerson;
	}
	public String getLedgerId() {
		return ledgerId;
	}
	public void setLedgerId(String ledgerId) {
		this.ledgerId = ledgerId;
	}
	public String getLedgerName() {
		return ledgerName;
	}
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}
	public String getChannId() {
		return channId;
	}
	public void setChannId(String channId) {
		this.channId = channId;
	}
	public String getChannNo() {
		return channNo;
	}
	public void setChannNo(String channNo) {
		this.channNo = channNo;
	}
	public String getChannName() {
		return channName;
	}
	public void setChannName(String channName) {
		this.channName = channName;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getAmountMoney() {
		return amountMoney;
	}
	public void setAmountMoney(String amountMoney) {
		this.amountMoney = amountMoney;
	}
	public String getFreezMoney() {
		return freezMoney;
	}
	public void setFreezMoney(String freezMoney) {
		this.freezMoney = freezMoney;
	}
	public String getAmountRebate() {
		return amountRebate;
	}
	public void setAmountRebate(String amountRebate) {
		this.amountRebate = amountRebate;
	}
	public String getFreezRebate() {
		return freezRebate;
	}
	public void setFreezRebate(String freezRebate) {
		this.freezRebate = freezRebate;
	}
	public String getAmuntCredit() {
		return amuntCredit;
	}
	public void setAmuntCredit(String amuntCredit) {
		this.amuntCredit = amuntCredit;
	}
	public String getFreezCredit() {
		return freezCredit;
	}
	public void setFreezCredit(String freezCredit) {
		this.freezCredit = freezCredit;
	}
	public String getOweCredit() {
		return oweCredit;
	}
	public void setOweCredit(String oweCredit) {
		this.oweCredit = oweCredit;
	}
	
	
}
