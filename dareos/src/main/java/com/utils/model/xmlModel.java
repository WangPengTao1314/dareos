package com.utils.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="list")
public class xmlModel {
	
	List<isNotEmptyModel> isNotEmptyList;

	@XmlElement(name = "isNotEmpty")
//	@XmlElement(name = "isNotNull")
	public List<isNotEmptyModel> getIsNotEmptyList() {
		return isNotEmptyList;
	}

	public void setIsNotEmptyList(List<isNotEmptyModel> isNotEmptyList) {
		this.isNotEmptyList = isNotEmptyList;
	}

	List<ifModel> ifList;

	@XmlElement(name = "if")
	public List<ifModel> getIfList() {
		return ifList;
	}

	public void setIfList(List<ifModel> ifList) {
		this.ifList = ifList;
	}

	
	
}
