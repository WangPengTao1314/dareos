package com.centit.sys.service;

import java.util.List;

public interface DictService {
	
	 List<Object> getDictList(String fileName, String selCommId,String condition);
}
