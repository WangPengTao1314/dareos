package com.centit.base.att.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.centit.base.att.mapper.AttMapper;
import com.centit.base.att.service.AttService;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.util.StringUtil;
import com.centit.sys.model.UserBean;

@Service
public class AttServiceImpl implements AttService {
	@Autowired
	AttMapper mapper;
	
	@Override
	public List<Map<String, String>> findList(String FROM_BILL_ID) {
		return mapper.findList(FROM_BILL_ID);
	}

	@Override
	public void insert(List<String> list,UserBean userBean,String FROM_BILL_ID) {
		List<Map<String,String>> addList=new ArrayList<Map<String,String>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String,String> map=new HashMap<String, String>();
			map.put("ATT_ID", StringUtil.uuid32len());
			map.put("ATT_PATH", list.get(i));
			map.put("FROM_BILL_ID", FROM_BILL_ID);
			map.put("CREATOR", userBean.getRYXXID());
			map.put("CRE_NAME", userBean.getXM());
			map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
			addList.add(map);
		}
		mapper.insert(addList);
	}
	
	@Override
	public void insertInfo(List<Map<String,String>> list,UserBean userBean,String FROM_BILL_ID) {
		List<Map<String,String>> addList=new ArrayList<Map<String,String>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String,String> map=new HashMap<String, String>();
			map.put("ATT_ID", StringUtil.uuid32len());
			map.put("ATT_PATH", list.get(i).get("ATT_PATH"));
			map.put("SPARE1", list.get(i).get("SPARE1"));
			map.put("SPARE2", list.get(i).get("SPARE2"));
			map.put("SPARE3", list.get(i).get("SPARE3"));
			map.put("FROM_BILL_ID", FROM_BILL_ID);
			map.put("CREATOR", userBean.getRYXXID());
			map.put("CRE_NAME", userBean.getXM());
			map.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
			addList.add(map);
		}
		mapper.insert(addList);
	}

	@Override
	public void delByFromId(String FROM_BILL_ID) {
		mapper.delByFromId(FROM_BILL_ID);

	}

	@Override
	public void delByAttId(String ATT_ID) {
		mapper.delByAttId(ATT_ID);
	}

	@Override
	public String findStr(String FROM_BILL_ID) {
		return mapper.findStr(FROM_BILL_ID);
	}
	
	public List<String> checkAttrName(String names){
		return mapper.checkAttrName(names);
	}

}
