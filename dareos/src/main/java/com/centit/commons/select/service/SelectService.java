package com.centit.commons.select.service;

import java.util.Vector;

import com.centit.commons.model.DataObject;
import com.centit.commons.model.PageInfoVO;
import com.centit.commons.select.object.InitObject;
import com.centit.commons.select.object.TableObject;

public interface SelectService {
	
	public void setValues(PageInfoVO aPageInfoVO, TableObject aTableObject) throws Exception;
	
	public Vector<DataObject> getSpecialTreeChildren(String nodeValue,
			int level, String displayModel, int childrenNum,
			InitObject vInitObject) throws Exception ;
	
	public Vector<DataObject> getNormalTreeChildren(String nodeValue,
			int level, String displayModel, int childrenNum,
			InitObject vInitObject) throws Exception;

}
