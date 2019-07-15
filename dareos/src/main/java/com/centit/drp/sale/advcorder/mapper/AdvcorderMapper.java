package com.centit.drp.sale.advcorder.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface AdvcorderMapper {
	void insertTrace(Map<String, String> map);
}
