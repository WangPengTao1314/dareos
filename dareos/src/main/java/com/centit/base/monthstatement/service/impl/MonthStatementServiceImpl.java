package com.centit.base.monthstatement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.base.monthstatement.mapper.MonthStatementMapper;
import com.centit.base.monthstatement.model.MonthAcctModel;
import com.centit.base.monthstatement.service.MonthStatementService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.core.utils.LogicUtil;
import com.centit.sys.model.UserBean;
@Service
public class MonthStatementServiceImpl implements MonthStatementService {
	@Autowired
	MonthStatementMapper mapper;

	@Override
	public List<MonthAcctModel> pageQuery(Map<String, Object> param) {
		return mapper.pageQuery( param);
	}

	@Override
	public void edit(String year) {
		// 根据年费获取月结信息，查看是否已生成过月结日期
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("year", year);
		List<MonthAcctModel> list = pageQuery(map);
		if(!list.isEmpty()){
			throw new ServiceException(year+"已生成过结算日期，不能重复生成");
		}
		//查询前一年是否已经生成数据
		Integer lastYear = Integer.parseInt(String.valueOf(year))-1;
		map.put("year", lastYear);
		 list = pageQuery(map);
		 if(list.isEmpty()){
				throw new ServiceException(lastYear+"未生成过结算日期，请优先生成上一年结算日期");
		}
		 //获取最后一条数据的日期
		 String date = list.get(list.size()-1).getEndDate();
		 //生成结算日期
		 mapper.statement(date);
	}

	@Override
	@Transactional
	public void statement(String year, String month,UserBean userBean) {
		// 查询该年月是否月结，如果没有。锁表
		month = LogicUtil.patchZero(month, 2, "0");
		Map<String,String> map = mapper.getStatementInfo(year,month);
		if(BusinessConsts.YJLBJ_FLAG_TRUE.equals(map.get("IS_MONTH_ACCT"))){
			throw new ServiceException(year+month+"已结算！不能再次结算！");
		}
		//获取所有渠道及渠道分管信息
		map.put("state", BusinessConsts.JC_STATE_ENABLE);//启用
		map.put("delFlag", BusinessConsts.DEL_FLAG_COMMON);//删除标记
		map.put("chaaLvl", BusinessConsts.CHANN_LVL);//经销商级别
		map.put("isBaseFlag", BusinessConsts.YJLBJ_FLAG_FALSE);//是否总部标记
		map.put("year", year);
		map.put("month", month);
		map.put("updator", userBean.getXTYHID());
		map.put("updName", userBean.getXM());
		
		//生成账单
		mapper.insertStatement(map);
		
		//修改月结表的月结标记
		mapper.updStatementFlag(map.get("MONTH_ACCT_ID"));
		
	}

}
