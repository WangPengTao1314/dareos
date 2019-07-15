package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.sys.model.SJZDMxModel;


@Repository
public interface SJZDMXMapper {

	List<SJZDMxModel> getMXBySJZDID(Map <String, String> paramsMx);

	List <SJZDMxModel> query(Map<String,String> params);

	int pageCount(Map<String,String> params);

	List <SJZDMxModel> loadByIds(@Param("SJZDMXIDS") String sjzdMxIds);

	void updateById(List<Map<String,String>> list);

	void insert(List<Map<String,String>> list);

	void insertSJZDMX(Map<String,String> params);

	void deleteByIds(@Param("sjzdmxids") String sjzdmxids);
}
