package com.centit.common.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.centit.common.po.FlowModel;
import com.centit.common.po.FlowTrackModel;


@Repository
public interface FlowMapper {	
	
	/**
	 * 根据流程编号获取流程接点信息
	 * @param flowNo
	 * @return
	 */
	List<FlowModel> getFlowByNo(String flowNo);
	
	
	/**
	 * 新增流程跟踪
	 * @param model
	 */
	void insertFlowTrack(FlowTrackModel model);
	
	/**
	 * 根据流程服务ID获取流程跟踪信息
	 * @param flowServiceId
	 * @return
	 */
	List<FlowTrackModel> getFlowTrackById(String flowServiceId);
	
	/**
	 * 根据流程服务ID获取流程跟踪信息(增加业务单号排序)
	 * @param flowServiceId
	 * @return
	 */
	List<FlowTrackModel> getFlowTrackByIdOrder(String flowServiceId);
	
}
