package com.centit.base.monthstatement.service;

import java.util.List;
import java.util.Map;

import com.centit.base.monthstatement.model.MonthAcctModel;
import com.centit.sys.model.UserBean;

public interface MonthStatementService {
	
	
	List<MonthAcctModel> pageQuery(Map<String,Object> param);
	
	void edit(String year);
	
	void statement(String year,String month,UserBean userBean);
}
