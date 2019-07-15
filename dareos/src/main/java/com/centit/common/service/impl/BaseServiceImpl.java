package com.centit.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.common.mapper.BaseMapper;
import com.centit.common.service.BaseService;

@Service
public class BaseServiceImpl implements BaseService {
	@Autowired
	private BaseMapper mapper;

	@Override
	public Map<String, String> selcom(String sql) {
		List<Map<String,String>> list=mapper.selcomList(sql);
		if(list.isEmpty()){
			return new HashMap<String, String>();
		}else{
			return mapper.selcomList(sql).get(0);
		}
	}

	@Override
	public List<Map<String, String>> selcomList(String sql) {
		return mapper.selcomList(sql);
	}

	@Override
	public void insert(String sql) {
		mapper.insert(sql);
		
	}

	@Override
	public void update(String sql) {
		mapper.update(sql);
		
	}

	@Override
	public void delete(String sql) {
		mapper.update(sql);
	}

	@Override
	public int getRowNum(String tab, String con) {
		return mapper.getRowNum(tab,con);
	}

	@Override
	public int getSeqByNo(String no) {
		return mapper.getSeqByNo(no);
	}

	@Override
	@Transactional
	public void clearSeq(String no) {
		mapper.delSeq(no);
		mapper.insertSeq(no);
	}
	
	public int getIntBySql(String sql){
		return mapper.getIntBySql(sql);
	}

}
