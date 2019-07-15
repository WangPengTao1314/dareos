package com.centit.commons.task;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.centit.common.service.BaseService;
import com.centit.common.service.BookkeepingService;
import com.centit.commons.webservice.NcQueryToOsUtil;
import com.centit.core.utils.SpringContextHolder;

@Component
public class SyncBaseDataTask {
	
	@Autowired
	private NcQueryToOsUtil ncToOsWebserviceUtil;
	
	@PostConstruct
	public void initDataFromNc(){
        //项目启动就会执行这个方法
		//同步物料分类
//		ncToOsWebserviceUtil.syncPrdc(null);
    	//同步物料
//		ncToOsWebserviceUtil.syncPrd(null);
		//同步客户信息/渠道信息
//		ncToOsWebserviceUtil.syncChann(null);
		//同步供应商信息
//		ncToOsWebserviceUtil.syncPrvd(null);
		//同步部门信息
//        ncToOsWebserviceUtil.syncDep(null);
		//同步人员信息
//        ncToOsWebserviceUtil.syncEmp(null);
    }
	
//	@Scheduled(cron = "0 30 9 * * ? ") // 每天定时9点30执行
//	@Scheduled(cron = "0 0 1 * * ?")// 每天凌晨1点触发
	@Scheduled(cron = "0/50 * * * * ? ") // 间隔50秒执行
	public void syncBaseDataFromNc(){
//		Date currentDate = new Date();
//		Date lastDate = DateUtil.beforeDate(currentDate, 1);
//		String syncTime = DateUtil.formatDateToStr(lastDate,"yyyy-MM-dd HH:mm:ss");
		
		//同步物料分类
//		ncToOsWebserviceUtil.syncPrdc("1");
    	//同步物料
//		ncToOsWebserviceUtil.syncPrd("1");
		//同步客户信息/渠道信息
//		ncToOsWebserviceUtil.syncChann("1");
		//同步供应商信息
//		ncToOsWebserviceUtil.syncPrvd("1");
		//同步部门信息
//        ncToOsWebserviceUtil.syncDep("1");
		//同步人员信息
//        ncToOsWebserviceUtil.syncEmp("1");
	}
	
	
	@Scheduled(cron = "0 0 1 * * ?")// 每天凌晨1点触发
//	@Scheduled(cron = "0 */1 * * * ?")//一分钟执行一次
	public void updChangeCredit(){ 
		BookkeepingService bService = SpringContextHolder.getBean(BookkeepingService.class);
		bService.updEffectCredit();//操作今天生效的信用
		bService.updExpireCredit();//操作昨天失效的信用
	}
	
	
	
	/**
	 * 月初清空销售订单sequence
	 */
	@Scheduled(cron = "0 10 0 1 * ?")//每月1号的0:10:00执行
//	@Scheduled(cron = "0 */1 * * * ?")//一分钟执行一次
	public void clearSaleSeq(){
		BaseService baseService = SpringContextHolder.getBean(BaseService.class);
		baseService.clearSeq("FACTORY_NO_SEQ");
	}

}
