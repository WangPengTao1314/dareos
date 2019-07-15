package com.centit.base.channchrg.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
@Repository
public interface ChannchrgMapper {
	void delete(Map<String,String> map);
	void deleteByIds(Map<String, String> map);
	void insert(List<Map<String, String>> listmap);
	List<Map<String,String>> queryChannByparams(Map<String, String> map);
	List<Map<String, String>> queryChann(Map<String, String> map);
	List<Map<String, String>> queryRyxxByparams(Map<String, String> map);
	void deleteByYhxxIds(Map<String, String> map);
}
