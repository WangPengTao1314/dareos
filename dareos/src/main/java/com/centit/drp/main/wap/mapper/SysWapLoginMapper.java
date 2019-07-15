package com.centit.drp.main.wap.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.centit.erp.sale.marketcardsale.model.MarketcardSaleChldModel;

/**
 * 
 * @ClassName: RYXXMapper 
 * @Description: 人员信息
 * @author: liu_yg
 * @date: 2019年2月22日 下午3:28:07
 */
@Repository
public interface SysWapLoginMapper {
	
	List<MarketcardSaleChldModel> queryCradList(String SALE_PSON_ID);
	
	
}
