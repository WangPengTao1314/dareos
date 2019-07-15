package com.utils.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class ifModel {

	String test;
	
	String txt;

	@XmlAttribute(name="test") 
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	@XmlValue
	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
	
	
}
