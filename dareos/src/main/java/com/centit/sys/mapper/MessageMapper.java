package com.centit.sys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.centit.sys.model.MessageModel;


@Repository
public interface MessageMapper {
	
	 List<MessageModel> queryAllMessage(Map<String,String> map);
	 
	 List<Map<String,String>> queryOldMessById(String MESSAGEID);
	 
	 void insertCkztBydxxid(Map<String,String> map);
	 
	 void insertMessage(Map<String,String> map);
	 
	 void insertMessageByAudit(Map<String,String> map);
	 
	 List<Map<String,String>> queryDbsyByUser(@Param("SELECTSQL") String SELECTSQL);
	 
	 Map<String,String> queryFilterData(@Param("SELECTSQL") String SELECTSQL);
}
