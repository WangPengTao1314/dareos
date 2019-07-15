package com.centit.common.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.centit.common.po.FlowTrackModel;
import com.centit.common.service.FlowService;

/**
 * 流程操作
 * @author liu_yg
 *
 */
@Controller
@RequestMapping("/sys/flow")
public class FlowController extends BaseController{
	@Autowired
	private FlowService service;
	
	private static final String webPath = "../../pages/common/flow";
//	private static final String webPath = "/base/area";

	@RequestMapping(value = { "/toList/{flowServiceId}"}, method = { RequestMethod.GET, RequestMethod.POST })
    public String list(HttpServletRequest request, HttpServletResponse response,@PathVariable String flowServiceId) {
		List<FlowTrackModel> list = service.getFlowTrackByIdOrder(flowServiceId);
		request.setAttribute("list", list);
        return view(webPath, "FlowTrack_List");
//        return view(webPath, "area_List");
    }
	
}
