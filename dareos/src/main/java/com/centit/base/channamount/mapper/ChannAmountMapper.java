package com.centit.base.channamount.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.centit.base.channamount.model.MonthAcctNoteModel;

@Repository
public interface ChannAmountMapper {
	
	List<MonthAcctNoteModel> pageQuery(Map<String,Object> param);
	
	MonthAcctNoteModel loadById(Map<String,String> map);
}
