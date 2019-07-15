/**
  *@module 系统模块   
  *@func 系统模块   
  *@version 1.1
  *@author zhuxw      
*/
package com.centit.commons.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.core.utils.SpringContextHolder;
import com.centit.sys.service.BmgzService;

// TODO: Auto-generated Javadoc
/**
 * The Class BmgzHelper.
 * 
 * @module 系统模块
 * @func 系统基础类
 * @version 1.1
 * @author zhuxw
 */
@Service
public class BmgzHelper {
	
	/** The logger. */
	private static Logger logger = Logger.getLogger(BmgzHelper.class);
	
	/** The instance. */
	private static BmgzHelper instance;
	
	/** The common bmgz service. */
	@Autowired
	private BmgzService bmgzService;
	
	/**
	 * 获取BmgzHelper实例
	 * Description :.
	 * 
	 * @return the instance
	 */
	public static BmgzHelper getInstance(){
		if(null == instance){
			instance = SpringContextHolder.getBean(BmgzHelper.class);
		}
		return instance;
	}
	
	/**
	 * 生成编码.
	 * 
	 * @param gzmc String
	 * 
	 * @return String
	 * 
	 * @throws ServiceException the service exception
	 */
	public String createCode(String gzmc) throws ServiceException{
		List<Map<String, String>> bmgzMxList = queryBmgzMx(gzmc);
		
		if(!validate(bmgzMxList)) {
			throw new ServiceException("编码规则【"+gzmc+"】无效！");
		}
		
		return buildCode(bmgzMxList);
	}
	
	/**
	 * 验证编码规则明细数据的有效性.
	 * 
	 * @param bmgzMxList List
	 * 
	 * @return boolean
	 */
	private boolean validate(List<Map<String,String>> bmgzMxList) {
		if(bmgzMxList == null || bmgzMxList.size() != 2) {
			logger.error("bmgzMxList is null or bmgzMxList.size() != 2");
			return false;
		}
		
		if(bmgzMxList.get(0) == null || bmgzMxList.get(1) == null) {
			logger.error("bmgzMxList.get(0) == null || bmgzMxList.get(1) == null");
			return false;
		}
		
		//不同编码集会导致排序方式不一致
		Map<String,String> resultMap0 = bmgzMxList.get(0);
		Map<String,String> resultMap1 = bmgzMxList.get(1);
		
		if(!BusinessConsts.BMGZ_YYR_LSH.equals((String)resultMap0.get("DLX")+(String)resultMap1.get("DLX"))){
			if(!BusinessConsts.BMGZ_YYR_LSH.equals((String)resultMap1.get("DLX")+(String)resultMap0.get("DLX"))){
				logger.error("DLX is error.["+(String)resultMap0.get("DLX")+(String)resultMap1.get("DLX")+"]");
				return false;
			}
		}
		
        return true;
	}
	
	/**
	 * 构造段头.
	 * 
	 * @param nyrMap the nyr map
	 * 
	 * @return String
	 */
	private String buildDT(Map<String,String> nyrMap) {
		logger.info("Enter BmgzHelper.buildDT");
		return nyrMap.get("DT");
	}
	
	/**
	 * 构造年月日.
	 * 
	 * @param nyrMap the nyr map
	 * 
	 * @return String
	 */
	private String buildYYR(Map<String,String> nyrMap) {
		logger.info("Enter BmgzHelper.buildYYR");
		String formatStr = "yyyy-MM-dd";
		String nys = nyrMap.get("NYS");
		if(!StringUtil.isEmpty(nys)){
			int len = nys.trim().length();
			if(len == 2){
				formatStr = "yy-MM-dd"; 
			}
		}
		SimpleDateFormat df = new SimpleDateFormat(formatStr);
        df.setLenient(false);
        String formatString = df.format(new Date());
        
        return formatString.replaceAll("-", "");
	}
	
	/**
	 * 构造Sequence.
	 * 
	 * @param lshMap the lsh map
	 * 
	 * @return String
	 */
	private String buildSequence(Map<String,String> lshMap) {
		logger.info("Enter BmgzHelper.buildSequence");
		String sequencemc = (String)lshMap.get("SEQUENCEMC");
		String sequence = bmgzService.selectSequence(sequencemc);
		sequence = sequence.trim();
		int len = sequence.length();
		BigDecimal bc = new BigDecimal(String.valueOf(lshMap.get("BC")));
		int bcint = Integer.valueOf(bc.toString());
		if(len >= bcint){
			return sequence.substring(len-bcint,len);
		}else{
			do{
				sequence = "0"+sequence;
			}while(sequence.length() < bcint);
			return sequence;
		}
	}
	
	/**
	 * 构造编码.
	 * 
	 * @param bmgzMxList List
	 * 
	 * @return String
	 */    
	private String buildCode(List<Map<String, String>> bmgzMxList) {
		StringBuffer sb = new StringBuffer();
		Map<String,String> nyrMap = bmgzMxList.get(0);
		Map<String,String> lshMap = bmgzMxList.get(1);
		//不同字符集会导致排序方式不一致
		if(!BusinessConsts.BMGZ_YYR.equals((String)nyrMap.get("DLX"))){
			 nyrMap = bmgzMxList.get(1);
			 lshMap = bmgzMxList.get(0);
		}
		sb.append(buildDT(nyrMap));
		sb.append(buildYYR(nyrMap));
		sb.append(buildSequence(lshMap));
		return sb.toString();
	}
	
	/**
	 * 查询编码明细数据.
	 * 
	 * @param bmdx the bmdx
	 * 
	 * @return List
	 */
	private List<Map<String, String>> queryBmgzMx(String bmdx) {
		return bmgzService.selectBmgzMx(bmdx);
	}

	
}
