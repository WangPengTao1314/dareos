/**
 * prjName:喜临门营销平台
 * ucName:标准订单
 * fileName:SaleorderServiceImpl
*/
package com.centit.erp.sale.saleorder.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.base.chann.mapper.ChannMapper;
import com.centit.base.product.mapper.ProductMapper;
import com.centit.common.service.BookkeepingService;
import com.centit.common.service.FlowService;
import com.centit.commons.exception.ServiceException;
import com.centit.commons.model.BusinessConsts;
import com.centit.commons.model.Consts;
import com.centit.commons.model.Numbers;
import com.centit.commons.util.ExcelUtil;
import com.centit.commons.util.InterUtil;
import com.centit.commons.util.StringUtil;
import com.centit.commons.webservice.NcFoWebserviceUtil;
import com.centit.commons.webservice.NcWebserviceUtil;
import com.centit.core.po.PageDesc;
import com.centit.core.utils.LogicUtil;
import com.centit.drp.main.project.management.mapper.ManageMapper;
import com.centit.erp.sale.saleorder.mapper.ChangeApplyMapper;
import com.centit.erp.sale.saleorder.mapper.SaleorderMapper;
import com.centit.erp.sale.saleorder.model.SaleDesignerModel;
import com.centit.erp.sale.saleorder.model.SaleorderConsts;
import com.centit.erp.sale.saleorder.model.SaleorderModel;
import com.centit.erp.sale.saleorder.model.SaleorderModelChld;
import com.centit.erp.sale.saleorder.service.SaleorderService;
import com.centit.sys.mapper.SJZDMXMapper;
import com.centit.sys.mapper.sqlCommonMapper;
import com.centit.sys.model.SJZDMxModel;
import com.centit.sys.model.UserBean;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
/**
 *
 * @ClassName: SaleorderServiceImpl
 * @Description: 销售订单service实现类
 * @author wang_dw
 * @date 2019年3月15日 下午2:00:34
 *
 */
@Service
public class SaleorderServiceImpl implements SaleorderService {
	//private static final String TABLE_DRP_GOODS_ORDER = "DRP_GOODS_ORDER";
	private static final String TABLE_ERP_SALE_ORDER = "ERP_SALE_ORDER";

	@Autowired
	private SaleorderMapper mapper;

	@Autowired
	private FlowService flowService;
	@Autowired
	private ChangeApplyMapper applyMapper;

	@Autowired
	private BookkeepingService bService;

	@Autowired
	private sqlCommonMapper commonMapper;
	@Autowired
	private ManageMapper manageMapper;
	@Autowired
	private ChannMapper channMapper;
	@Autowired
	private SJZDMXMapper sjzdmxMapper;
	@Autowired
	private ProductMapper prodMapper;

	/**
	 * @查询
	 * @param params map
	 * @param pageNo
	 *
	 * @return the ilistpage
	 */
	@Override
	public void pageQuery(Map<String, Object> params, PageDesc pageDesc) {
		//变颜色的日期
		Page<Map<String, Object>> p = PageHelper.startPage(pageDesc.getPageNo(), pageDesc.getPageSize(), true);
//		params.put("BILL_DIFF_DATE_FLAG", BusinessConsts.BILL_DIFF_DATE_FLAG);
    	mapper.pageQuery(params);
		LogicUtil.transPageHelper(pageDesc, p);
	}

	/**
	 * * 增加
	 * * @param params
	 *
	 * @return true, if tx insert
	 */
	@Transactional
	public void txInsert(Map<String,String> params) {
		mapper.insert(params);
	}
	/**
	 * @删除
	 * @param SALE_ORDER_ID
	 *
	 * @return true, if tx delete
	 */
	@Override
	@Transactional
	public void txDelete(Map <String, String> params) {
		bService.delBookkeeping(params.get("SALE_ORDER_ID"));
		 mapper.delete(params);
		 mapper.delChldByFkId(params);
	}

	/**
	 * * update data
	 * * @param params
	 *
	 *
	 * @return true, if tx update by id
	 */
	@Override
	@Transactional
	public void txUpdateById(Map<String,String> params) {
		mapper.updateById(params);
	}

	/**
	 * @编辑
	 * @Description :
	 * @param SALE_ORDER_ID
	 * @param SaleorderModel
	 * @param userBean.
	 *
	 * @return true, if tx txEdit
	 */
	@Override
	@Transactional
	public String txEdit(String SALE_ORDER_ID, SaleorderModel model,List<SaleorderModelChld> chldList, UserBean userBean) {
		String bookkeepingType = "add";//记录流水的标记，如果是修改，则先删除原流水，再新增
		Map<String,String> params = new HashMap<String,String>();
		params.put("SALE_ORDER_NO", model.getSALE_ORDER_NO()==null?"":model.getSALE_ORDER_NO());
		params.put("ORDER_CHANN_ID", model.getORDER_CHANN_ID()==null?"":model.getORDER_CHANN_ID()); // 要货组织ID
		params.put("ORDER_CHANN_NO", model.getORDER_CHANN_NO()==null?"":model.getORDER_CHANN_NO()); // 要货组织编号
		params.put("ORDER_CHANN_NAME", model.getORDER_CHANN_NAME()==null?"":model.getORDER_CHANN_NAME()); // 要货组织名称
		params.put("ORDER_DATE", model.getORDER_DATE()==null?"":model.getORDER_DATE()); // 下单日期
		params.put("CHANN_ID", model.getCHANN_ID()==null?"":model.getCHANN_ID()); //经销商ID
		params.put("CHANN_NO", model.getCHANN_NO()==null?"":model.getCHANN_NO()); //经销商编号
		params.put("CHANN_NAME", model.getCHANN_NAME()==null?"":model.getCHANN_NAME()); //经销商名称
		params.put("PRINT_NAME", model.getPRINT_NAME()==null?"":model.getPRINT_NAME()); //标签打印名称
		params.put("TRANSPORT", model.getTRANSPORT()==null?"":model.getTRANSPORT()); //运输方式
		params.put("TRANSPORT_SETTLE", model.getTRANSPORT_SETTLE()==null?"":model.getTRANSPORT_SETTLE()); //运输结算方式
		params.put("FACTORY_NO", model.getFACTORY_NO()==null?"":model.getFACTORY_NO()); //厂编
		params.put("PROESS_TYPE", model.getPROESS_TYPE()==null?"":model.getPROESS_TYPE()); //处理类型
		params.put("URGENCY_TYPE", model.getURGENCY_TYPE()==""?BusinessConsts.NORMAL:model.getURGENCY_TYPE()); //紧急程度
		params.put("SALE_ID", model.getSALE_ID()==null?"":model.getSALE_ID()); //业务员ID
		params.put("SALE_NAME", model.getSALE_NAME()==null?"":model.getSALE_NAME()); //业务员名称
		params.put("SALEDEPT_NAME", model.getSALEDEPT_NAME()==null?"":model.getSALEDEPT_NAME()); //业务员部门名称
		params.put("SALEDEPT_ID", model.getSALEDEPT_ID()==null?"":model.getSALEDEPT_ID()); //业务员部门ID
		params.put("PRE_RECV_DATE", model.getPRE_RECV_DATE()==null?"":model.getPRE_RECV_DATE()); //预计交货日期
		params.put("CUST_NAME", model.getCUST_NAME()==null?"":model.getCUST_NAME()); //客户姓名
		params.put("RECV_CHANN_ID", model.getRECV_CHANN_ID()==null?"":model.getRECV_CHANN_ID()); //收货组织ID
		params.put("RECV_CHANN_NO", model.getRECV_CHANN_NO()==null?"":model.getRECV_CHANN_NO()); //收货组织编号
		params.put("RECV_CHANN_NAME", model.getRECV_CHANN_NAME()==null?"":model.getRECV_CHANN_NAME()); //收货组织名称
		params.put("PERSON_CON", model.getPERSON_CON()==null?"":model.getPERSON_CON()); // 收货联系人
		params.put("RECV_ADDR", model.getRECV_ADDR()==null?"":model.getRECV_ADDR()); // 收货地址
		params.put("RECV_ADDR_NO", model.getRECV_ADDR_NO()==null?"":model.getRECV_ADDR_NO()); // 收货地址编号
		params.put("TEL", model.getTEL()==null?"":model.getTEL()); // 收货电话
		params.put("REMARK", model.getREMARK()==null?"":model.getREMARK()); // 经销商备注
		params.put("REMARK2", model.getREMARK2()==null?"":model.getREMARK2()); // 订单员备注
		params.put("AREA_ID", model.getAREA_ID()==null?"":model.getAREA_ID());
		params.put("AREA_NO", model.getAREA_NO()==null?"":model.getAREA_NO());
		params.put("AREA_NAME", model.getAREA_NAME()==null?"":model.getAREA_NAME());
		params.put("ORDER_DELIVERY_DATE", model.getORDER_DELIVERY_DATE()==null?"":model.getORDER_DELIVERY_DATE()); //订单交期
		params.put("CUST_TEL", model.getCUST_TEL()==null?"":model.getCUST_TEL()); //客户电话
		params.put("CUST_ADDR", model.getCUST_ADDR()==null?"":model.getCUST_ADDR()); //客户住址
		params.put("REBATE_RATE", model.getREBATE_RATE()==null?"0":model.getREBATE_RATE()); //返利使用比例
		params.put("TOTAL_AMOUNT", StringUtil.nullToZero(model.getTOTAL_AMOUNT())); //订货总金额
		params.put("TOTAL_REBATE", StringUtil.nullToZero(model.getTOTAL_REBATE())); //订货总返利
		params.put("ORDER_TRACE_ID", model.getORDER_TRACE_ID()==null?"0":model.getORDER_TRACE_ID()); //流程跟踪ID

		params.put("FROM_BILL_ID", model.getFROM_BILL_ID()==null?"":model.getFROM_BILL_ID()); //来源单据ID
		params.put("FROM_BILL_NO", model.getFROM_BILL_NO()==null?"":model.getFROM_BILL_NO()); //来源单据NO
		params.put("BILL_TYPE2", model.getBILL_TYPE2()==null?"":model.getBILL_TYPE2()); // 来源单据类型

		params.put("BILL_TYPE", model.getBILL_TYPE()==null?"":model.getBILL_TYPE()); //销售类型
		params.put("LEDGER_ID", model.getLEDGER_ID());
		params.put("LEDGER_NAME", model.getLEDGER_NAME());

		params.put("CHANGE_FLAG", model.getCHANGE_FLAG());

		// 工程项目
		params.put("PROJECT_ID", model.getPROJECT_ID());
		params.put("PROJECT_NO", model.getPROJECT_NO());
		params.put("PROJECT_NAME", model.getPROJECT_NAME());

//		Map warMap = new HashMap();
//		warMap.put("AREA_ID", model.getAREA_ID());
////		HashMap areaWarMap = (HashMap)load("Saleorder.queryChannWarId", warMap);
//		HashMap areaWarMap = (HashMap)mapper.queryChannWarId(warMap);
//		if(areaWarMap!=null){
//			params.put("WAREA_ID", (String)areaWarMap.get("AREA_ID_P"));
//			params.put("WAREA_NO", (String)areaWarMap.get("AREA_NO_P"));
//			params.put("WAREA_NAME", (String)areaWarMap.get("AREA_NAME_P"));
//		}

		// 校验厂编是否已存在
		Map<String, Object> qureyMap = new HashMap<String, Object>();
		qureyMap.put("FACTORY_NO", model.getFACTORY_NO());
		qureyMap.put("NOT_SALE_ORDER_ID", SALE_ORDER_ID);
		qureyMap.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
		int cnt = mapper.pageCount(qureyMap);
		if (cnt > 0) {
			throw new ServiceException("厂编'"+model.getFACTORY_NO()+"'已存在，请修改后重试！");
		}

		if(StringUtil.isEmpty(SALE_ORDER_ID)){
			SALE_ORDER_ID= StringUtil.uuid32len();
			params.put("SALE_ORDER_ID", SALE_ORDER_ID);
			//是否使用唯一编号 ，使用唯一单号以订货订单编号
			/*if("1".equals(Consts.USER_ONLY_ORDER_NO)){
				params.put("SALE_ORDER_NO", LogicUtil.getBmgz("DRP_GOODS_ORDER_NO"));
			}else{
				params.put("SALE_ORDER_NO", LogicUtil.getBmgz("ERP_SALE_ORDER_NO"));
			}*/

			params.put("IS_USE_REBATE", BusinessConsts.YJLBJ_FLAG_FALSE);
			params.put("BASE_ADD_FLAG", BusinessConsts.YJLBJ_FLAG_TRUE); // 总部新增标记
			params.put("STATE",BusinessConsts.GOODSORDER_STATE_CG); // 草稿
			params.put("CREATOR",userBean.getXTYHID());
			params.put("CRE_NAME",userBean.getXM());
			params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
			params.put("DEPT_ID",userBean.getBMXXID());
			params.put("DEPT_NAME",userBean.getBMMC());
			params.put("ORG_ID",userBean.getJGXXID());
			params.put("ORG_NAME",userBean.getJGMC());
//			Map<String,String> map=(Map<String, String>) this.load("Saleorder.loadAreaInfo",userBean.getCHANN_ID());
//			if(!map.isEmpty()){
//				params.putAll(map);
//			}
			if (StringUtil.isEmpty(model.getFROM_BILL_ID())) {
				params.put("ORDER_TRACE_ID", StringUtil.uuid32len());
			}
			txInsert(params);

			// 流程跟踪
			//flowService.insertNextFlow(params.get("SALE_ORDER_ID"), params.get("FACTORY_NO"), params.get("ORDER_TRACE_ID"), BusinessConsts.GOOD_FLOW_NO, userBean, "");
			//flowService.insertNextFlowByIndexNo(params.get("SALE_ORDER_ID"), params.get("FACTORY_NO"), params.get("ORDER_TRACE_ID"), BusinessConsts.GOOD_FLOW_NO, userBean, BusinessConsts.WAIT_DEAL, "");
			if (StringUtil.isEmpty(model.getFROM_BILL_ID())) {
				String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), "0");
				flowService.insertFlowTrack(flowNo, params.get("ORDER_TRACE_ID"), params.get("FACTORY_NO"), userBean, "", "新增销售订单");
			}
		} else{
			bookkeepingType="upd";
			params.put("UPDATOR",userBean.getXTYHID());
			params.put("UPD_NAME",userBean.getXM());
			params.put("UPD_TIME","sysdate");
			params.put("SALE_ORDER_ID", SALE_ORDER_ID);
			txUpdateById(params);
		}

		// 保存上传的文件
		String attPath = model.getAttPath();
		if (!StringUtil.isEmpty(attPath)) {
			InterUtil.delByFromId(SALE_ORDER_ID);
			String[] arr = attPath.split(",");
			InterUtil.insertAttPath(Arrays.asList(arr), userBean, SALE_ORDER_ID);
		}

		//子表信息保存
		if (null != chldList && !chldList.isEmpty()) {
			txChildEdit(SALE_ORDER_ID, chldList, userBean, "edit",model.getBILL_TYPE());
		}

		if (!StringUtil.isEmpty(model.getFROM_BILL_ID())) {
			//转销售订单后冻结额度
			bService.saleOrderFrozenAmount(SALE_ORDER_ID, userBean.getXTYHID(),bookkeepingType);

			//
			SaleorderModel modelDb = (SaleorderModel) LogicUtil.tranMap2Bean(load(SALE_ORDER_ID), SaleorderModel.class);
			String content = modelDb.getNC_ID();
			NcFoWebserviceUtil.djSaleFactoryNO(BusinessConsts.INTEGER_1, content, modelDb);
		}
		return SALE_ORDER_ID;
	}

	@Override
	@Transactional
	public String txEditToCommit(String SALE_ORDER_ID, SaleorderModel model,List<SaleorderModelChld> chldList, UserBean userBean) {
		SALE_ORDER_ID = txEdit(SALE_ORDER_ID, model, chldList, userBean);
		Map<String, Object> entry = load(SALE_ORDER_ID);
		model = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
		txCommit(SALE_ORDER_ID, model, userBean);
		return SALE_ORDER_ID;
	}

	/**
	 * @详细
	 * @param param SALE_ORDER_ID
	 * @param param the param
	 *
	 * @return the map< string, Object>
	 */
	@Override
	public Map<String, Object> load(String param) {
		Map<String, String> params = new HashMap<String, String>();
        params.put("SALE_ORDER_ID", param);
        return mapper.loadById(params);
	}

	/**
     * * 明细数据编辑
     *
     * @param SALE_ORDER_ID the SALE_ORDER_ID
     * @param modelList the model list
     *
     * @return true, if tx child edit
     */
	@Transactional
    @Override
    public boolean txChildEdit(String SALE_ORDER_ID, List<SaleorderModelChld> chldList, UserBean userBean, String actionType,String BILL_TYPE) {

        //新增列表
        List <Map <String, String>> addList = new ArrayList <Map <String, String>>();
        //修改列表
        List <Map <String, String>> updateList = new ArrayList <Map <String, String>>();
        Map<String,String>paramMap = new HashMap<String,String>();
    	paramMap.put("SALE_ORDER_ID", SALE_ORDER_ID);
        for (SaleorderModelChld model : chldList) {
            Map <String, String> params = new HashMap <String, String>();
            params.put("SALE_ORDER_ID",SALE_ORDER_ID); // 销售订单ID
            params.put("GROUP_NO", model.getGROUP_NO()==null?"":model.getGROUP_NO());// 组号
            params.put("PRD_ID", model.getPRD_ID()==null?"":model.getPRD_ID());// 产品ID
            params.put("PRD_NO", model.getPRD_NO()==null?"":model.getPRD_NO());// 产品编号
            params.put("PRD_NAME", model.getPRD_NAME()==null?"":model.getPRD_NAME());// 产品名称
            params.put("HOLE_SPEC", model.getHOLE_SPEC()==null?"":model.getHOLE_SPEC()); // 门洞尺寸
            params.put("PRD_SPEC", model.getPRD_SPEC()==null?"":model.getPRD_SPEC());// 规格型号
            params.put("PRD_TOWARD", model.getPRD_TOWARD()==null?"":model.getPRD_TOWARD()); // 推向
            params.put("PRD_QUALITY", model.getPRD_QUALITY()==null?"":model.getPRD_QUALITY()); // 材质
            params.put("PRD_COLOR", model.getPRD_COLOR()==null?"":model.getPRD_COLOR());// 颜色
            params.put("PRD_GLASS", model.getPRD_GLASS()==null?"":model.getPRD_GLASS()); //玻璃
            params.put("PRD_OTHER", model.getPRD_OTHER()==null?"":model.getPRD_OTHER()); // 其他
            params.put("PRD_SERIES", model.getPRD_SERIES()==null?"":model.getPRD_SERIES()); // 系列
            params.put("PRD_SIZE", model.getPRD_SIZE()==null?"":model.getPRD_SIZE());// 转换尺寸
            params.put("PROJECTED_AREA",model.getPROJECTED_AREA());// 投影面积
            params.put("EXPAND_AREA",model.getEXPAND_AREA());// 展开面积
			params.put("PRD_PLACE",model.getPRD_PLACE());//工程位置
			params.put("IS_NO_REBATE_FLAG",model.getIS_NO_REBATE_FLAG());//是否返利
			params.put("IS_NO_LOCK_FLAG",model.getIS_NO_LOCK_FLAG());//是否开锁孔
			params.put("BRAND", model.getBRAND()==null?"":model.getBRAND()); // 品牌
			params.put("STD_UNIT", model.getSTD_UNIT()==null?"":model.getSTD_UNIT());//标准单位
			params.put("IS_NO_STAND_FLAG",model.getIS_NO_STAND_FLAG()==null?"":model.getIS_NO_STAND_FLAG()); //是否非标
			params.put("PRICE",model.getPRICE()==null?"":model.getPRICE());//单价
			params.put("DECT_RATE",model.getDECT_RATE()==null?"":model.getDECT_RATE());//折扣率
			params.put("DECT_PRICE",model.getDECT_PRICE()==null?"":model.getDECT_PRICE());//折后单价
			params.put("REBATE_PRICE", StringUtil.nullToZero(model.getREBATE_PRICE())); //返利单价
			params.put("REBATE_AMOUNT", StringUtil.nullToZero(model.getREBATE_AMOUNT()));// 返利金额
			params.put("ORDER_NUM",model.getORDER_NUM()==null?"":model.getORDER_NUM());//订货数量
			params.put("ORDER_AMOUNT",model.getORDER_AMOUNT()==null?"":model.getORDER_AMOUNT());//订货金额
			params.put("ADVC_SEND_DATE",model.getADVC_SEND_DATE()==null?"":model.getADVC_SEND_DATE());//预计发货日期
			params.put("REMARK",model.getREMARK()==null?"":model.getREMARK()); // 备注
			params.put("FROM_BILL_DTL_ID",model.getFROM_BILL_DTL_ID()==null?"":model.getFROM_BILL_DTL_ID()); //来源单据明细ID
			params.put("SENDED_NUM", model.getSENDED_NUM()==null?"":model.getSENDED_NUM());//已实际发货数量
			params.put("ROW_NO", StringUtil.nullToZero(model.getROW_NO()));//行号
			/** 附图号 **/
			params.put("FIGURE_NO", model.getFIGURE_NO()==null?"":model.getFIGURE_NO());//附图号

			String REBATE_AMOUNT = StringUtil.nullToZero(model.getREBATE_AMOUNT());
			if(!BusinessConsts.INTEGER_0.equals(REBATE_AMOUNT)){
				//返利金额
				BigDecimal amount = new BigDecimal(StringUtil.nullToZero(model.getREBATE_AMOUNT()));
				//订货数量
				BigDecimal num = new BigDecimal(StringUtil.nullToZero(model.getORDER_NUM()));
				if(BigDecimal.ZERO.equals(num)){
					break;
				}
				// 返利单价=返利金额÷订货数量
				BigDecimal REBATE_PRICE = amount.divide(num, BigDecimal.ROUND_HALF_UP);
				params.put("REBATE_PRICE",REBATE_PRICE.toString());//返利单价
			} else {
				params.put("DECT_RATE", model.getDECT_RATE());
				params.put("DECT_PRICE",model.getDECT_PRICE());
				params.put("ORDER_AMOUNT", model.getORDER_AMOUNT());
			}

			// 如果没有明细ID的则是新增，有的是修改
			if (StringUtil.isEmpty(model.getSALE_ORDER_DTL_ID())) {
				params.put("SALE_ORDER_DTL_ID", StringUtil.uuid32len());
				params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
				addList.add(params);
			} else {
				params.put("SALE_ORDER_DTL_ID", model.getSALE_ORDER_DTL_ID());
				updateList.add(params);
			}

			// 保存上传的文件
			String attPath = model.getAttPath();
			if (!StringUtil.isEmpty(attPath)) {
				InterUtil.delByFromId(StringUtil.nullToSring(params.get("SALE_ORDER_DTL_ID")));
				String[] arr = attPath.split(",");
				InterUtil.insertAttPath(Arrays.asList(arr), userBean, StringUtil.nullToSring(params.get("SALE_ORDER_DTL_ID")));
			}
		}
		if (!updateList.isEmpty()) {
			mapper.updateChldById(updateList);
		}
		if (!addList.isEmpty()) {
			mapper.insertChld(addList);
		}
		return true;
	}

	/**
     * * 根据主表Id查询子表记录
     * @param SALE_ORDER_ID the SALE_ORDER_ID
     *
     * @return the list< new master slavemx model>
     */
    @Override
    public List<Map<String, String>> childQuery(String SALE_ORDER_ID) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("SALE_ORDER_ID", SALE_ORDER_ID);
        //只查询0的记录。1为删除。0为正常
		params.put("DEL_FLAG",BusinessConsts.DEL_FLAG_COMMON);
		//查询 未取消预定的 0 正常
//		params.put("CANCEL_FLAG",BusinessConsts.INTEGER_0);
		return mapper.queryChldByFkId(params);
//        return this.findList("Saleorder.queryChldByFkId", params);
    }

    /**
     * * 根据子表Id批量加载子表信息
     * @param SALE_ORDER_DTL_IDs the SALE_ORDER_DTL_IDs
     *
     * @return the list< new master slavemx model>
     */
    @Override
    public List <Map <String, String>> loadChilds(Map <String, String> params) {
//       return findList("Saleorder.loadChldByIds",params);
       return mapper.loadChldByIds(params);
    }

    /**
     * * 子表批量删除:软删除
     *
     * @param SALE_ORDER_DTL_IDs the SALE_ORDER_DTL_IDs
     */
    @Transactional
    @Override
    public void txBatch4DeleteChild(String SALE_ORDER_DTL_IDs) {
	   Map<String,String> params = new HashMap<String,String>();
	   params.put("SALE_ORDER_DTL_IDS", SALE_ORDER_DTL_IDs);
	   params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_DROP);
//       update("Saleorder.deleteChldByIds", params);
       mapper.deleteChldByIds(params);
    }

	@Override
	@Transactional
	public void txCommit(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean) {
		String bookkeepingType = "add";//记录流水的标记，如果是修改，则先删除原流水，再新增

		// 流程流转
		String flowType = "0";//LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
		flowService.insertNextFlowByIndexNo(SALE_ORDER_ID, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, BusinessConsts.WAIT_DEAL, "");

		Map<String,String> params = new HashMap<String,String>();
		params.put("SALE_ORDER_ID", SALE_ORDER_ID);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		// 更新下单时间
		params.put("ORDER_DATE", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);

		//提交销售订单后冻结额度
		bService.saleOrderFrozenAmount(SALE_ORDER_ID, userBean.getXTYHID(),bookkeepingType);
		//
		String content = model.getNC_ID();
		NcFoWebserviceUtil.djSaleFactoryNO(BusinessConsts.INTEGER_1, content, model);
	}

	@Override
	public String getDECT_RATE(String CHANN_ID){
		Map<String,String> map=new HashMap<String, String>();
		map.put("CHANN_ID", CHANN_ID);
		map.put("DECT_TYPE", "赠品折扣");
//		Object DECT_RATE=this.load("Saleorder.getDECT_RATE", map);
		Object DECT_RATE=mapper.getDECT_RATE(map);
		if(null==DECT_RATE){
			return null;
		}
		return DECT_RATE.toString();
	}
	@Override
	public List<?> qrySaleOrderExp(Map<String,String> map){
//		return this.findList("Saleorder.qrySaleOrderExp", map);
		return mapper.qrySaleOrderExp(map);
	}

	@Override
	@Transactional
	public void back4gc(String selRowId, SaleorderModel model, UserBean userBean) {
		// 流程跟踪
		if (StringUtil.isEmpty(model.getFROM_BILL_ID())) {
			String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), "0");
			flowService.insertFlowTrack(flowNo, model.getORDER_TRACE_ID(), model.getFACTORY_NO(), userBean, model.getAuditContents(), "退回销售订单");
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", selRowId);
		params.put("STATE", BusinessConsts._BACK);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);

		//退回工程订单到草稿后释放冻结额度
		bService.releaseSaleFreezBookkeeping(selRowId, userBean.getXM());
	};

	@Override
	@Transactional
	public void txBack(String selRowId, SaleorderModel model, UserBean userBean) {
		// 流程流转
		String flowType = "0";//LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
		flowService.backFlowByTableName(selRowId, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, model.getAuditContents(), TABLE_ERP_SALE_ORDER);

		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);
	};

	@Override
	@Transactional
	public void txBack(String selRowId, SaleorderModel model, UserBean userBean, String TableName) {
		// 流程流转
		String flowType = "0";//LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
		flowService.backFlowByTableName(selRowId, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, model.getAuditContents(), TableName);

		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);
	};

	@Override
	@Transactional
	public void txERPBack(String selRowId, SaleorderModel model, UserBean userBean) {/*
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("flag", BusinessConsts.SUCCESS);*/
		Map<String, String> resultMap = NcWebserviceUtil.delSaleOrder(model);
		String flag = resultMap.get("flag");
		if (BusinessConsts.SUCCESS.equals(flag)) {
			// 流程流转
			String flowType = "0";//LogicUtil.getFlowType(userBean);
			String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
			flowService.backFlowByTableName(selRowId, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, BusinessConsts.WAIT_DEAL, model.getAuditContents(), TABLE_ERP_SALE_ORDER);

			Map<String, String> params = new HashMap<String, String>();
			params.put("SALE_ORDER_ID", selRowId);
			params.put("UPDATOR", userBean.getXTYHID());
			params.put("UPD_NAME", userBean.getXM());
			params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);

			params.put("NC_ID", "");
			mapper.updateById(params);
		}
	};

	@Override
	@Transactional
	@Deprecated
	public void turnDesigner(String selRowId, String no, String flowServiceId, String state, UserBean userBean) {
		// 流程流转
		//flowService.insertNextFlow(selRowId, no, flowServiceId, BusinessConsts.GOOD_FLOW_NO, userBean, "");
		flowService.insertNextFlowByIndexNo(selRowId, no, flowServiceId, BusinessConsts.GOOD_FLOW_NO, userBean, BusinessConsts.GOODSORDER_STATE_FPSJS, "");

		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);
	}

	@Override
	@Transactional
	public void turnDesigner(String selRowId, SaleorderModel model, UserBean userBean) {
		// 流程流转
		String flowType = "0";//LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
		flowService.insertNextFlowByIndexNo(selRowId, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, BusinessConsts.GOODSORDER_STATE_FPSJS, "");

		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);
	}

	@Override
	@Transactional
	@Deprecated
	public void turnERP(String selRowId, String no, String flowServiceId, UserBean userBean) {
		// 流程流转
		//flowService.insertNextFlow(selRowId, no, flowServiceId, BusinessConsts.GOOD_FLOW_NO, userBean, "");
		flowService.insertNextFlowByIndexNo(selRowId, no, flowServiceId, BusinessConsts.GOOD_FLOW_NO, userBean, BusinessConsts.INPRODUCTTION, "");

		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);
	}

	@Override
	@Transactional
	public void turnERP(String selRowId, SaleorderModel model, UserBean userBean) {
		List<Map<String, String>> childMapList = this.childQuery(selRowId);
		List<SaleorderModelChld> childList = new ArrayList<>();
		for (Map<String, String> map : childMapList) {
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.putAll(map);
			SaleorderModelChld child = (SaleorderModelChld) LogicUtil.tranMap2Bean(obj, SaleorderModelChld.class);
			childList.add(child);
		}

		// 调用NC接口
		String type = BusinessConsts.INTEGER_2;
		String content = model.getNC_ID();
		if (StringUtil.isEmpty(content)) {
			type = BusinessConsts.INTEGER_1;
		}/*
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("flag", BusinessConsts.SUCCESS);*/
		Map<String, String> resultMap = NcWebserviceUtil.djSaleOrder(type, content, model, childList);
		String flag = resultMap.get("flag");
		if (BusinessConsts.SUCCESS.equals(flag)) {
			// 流程流转
			String flowType = "0";//LogicUtil.getFlowType(userBean);
			String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
			flowService.insertNextFlowByIndexNo(selRowId, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, BusinessConsts.INPRODUCTTION, "");

			Map<String, String> params = new HashMap<String, String>();
			params.put("SALE_ORDER_ID", selRowId);
			params.put("UPDATOR", userBean.getXTYHID());
			params.put("UPD_NAME", userBean.getXM());
			params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
			params.put("NC_ID", resultMap.get("message"));//推送NC系统返回单据ID
			mapper.updateById(params);
		} else {
			//throw new ServiceException("与NC系统对接转换单据失败！");
			String message = resultMap.get("message");/*
			String errorMsg = "与NC系统对接转换单据失败！</br>";
			int i = message.lastIndexOf("异常信息:");
			if (i > 0) {
				errorMsg += message.substring(i);
			} else {
				i = message.lastIndexOf("处理错误:");
			}
			if (i > 0) {
				errorMsg += message.substring(i);
			}*/
			throw new ServiceException(message);
		}
	}


	@Override
	@Transactional
	public Map<String, Object> loadAssignByFkid(String param){
		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", param);
		return mapper.loadAssignById(params);
	}
	@Override
	@Transactional
	public Map<String, Object> loadAssignById(String param){
		Map<String, String> params = new HashMap<String, String>();
		params.put("DESIGNER_ID", param);
		return mapper.loadAssignById(params);
	}
	@Override
	@Transactional
	public List<Map<String, Object>> assignQueryTotal(Map<String, String> params) {

		return mapper.assignQueryTotal(params);
	}
	@Override
	@Transactional
	public List<Map<String, Object>> assignQuery(Map<String, String> params) {

		return mapper.assignQuery(params);
	}

	@Override
	@Transactional
	public String assignEdit(String DESIGNER_ID, SaleDesignerModel model, SaleorderModel ddModel, UserBean userBean) {
		String msg = "保存成功";
		Map<String,String> params = new HashMap<String,String>();
		if(null != model){
			params.put("DESIGNER_ID", model.getDESIGNER_ID());
			params.put("SALE_ORDER_ID", model.getSALE_ORDER_ID());
			params.put("SALE_ORDER_NO", model.getSALE_ORDER_NO());
			params.put("DESIGNER", model.getDESIGNER());
			params.put("DESIGNER_NAME", model.getDESIGNER_NAME());
			params.put("REMARK", model.getREMARK());
			if(StringUtil.isEmpty(DESIGNER_ID)){ // 新增
				DESIGNER_ID = StringUtil.uuid32len();
				params.put("DESIGNER_ID", DESIGNER_ID);
				params.put("ASSIGN_TIME", "sysdate");
				params.put("ASSIGNER", userBean.getXTYHID());
				params.put("FINISH_FLAG", BusinessConsts.YJLBJ_FLAG_FALSE);

				mapper.insertDesigner(params);
			} else{
				params.put("DESIGNER_ID", DESIGNER_ID);
				mapper.updateDesignerById(params);
				/* clearCaches(GOODS_ORDER_ID); */
			}

			String SALE_ORDER_ID = model.getSALE_ORDER_ID();
			if ("T".equals(model.getAuditStatus())) {
				String flowServiceId = model.getORDER_TRACE_ID();
				// 流程流转
				Map<String, Object> entry = this.load(SALE_ORDER_ID);
				SaleorderModel modelDb = (SaleorderModel) LogicUtil.tranMap2Bean(entry, SaleorderModel.class);
				String flowType = "0";//LogicUtil.getFlowType(userBean);
				String flowNo = flowService.getFlowNoByLedger(modelDb.getLEDGER_ID(), flowType);
				flowService.insertNextFlowByIndexNo(SALE_ORDER_ID, modelDb.getFACTORY_NO(), flowServiceId, flowNo, userBean, BusinessConsts.GOODSORDER_STATE_SJSCJ, "已分派给"+model.getDESIGNER_NAME());

				msg = "分派成功";
			}
			if ("F".equals(model.getAuditStatus())) {
				this.txBack(SALE_ORDER_ID, ddModel, userBean, TABLE_ERP_SALE_ORDER);
				mapper.deleteDesignerById(DESIGNER_ID);
				msg = "操作成功";
			}

			params = new HashMap<String, String>();
			params.put("SALE_ORDER_ID", SALE_ORDER_ID);
			params.put("UPDATOR", userBean.getXTYHID());
			params.put("UPD_NAME", userBean.getXM());
			params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
			mapper.updateById(params);

		}
		return msg;
	}

	@Override
	@Transactional
	public String txDesign(String selRowId, SaleDesignerModel model, SaleorderModel xsddModel, UserBean userBean) {
		String msg = "保存成功";
		// 保存上传的拆件计料表
		String no = xsddModel.getFACTORY_NO();
		String attPath_cj = xsddModel.getAttPath_cj();
		if (!StringUtil.isEmpty(attPath_cj)) {
			InterUtil.delByFromId(no);
			String[] arr = attPath_cj.split(",");
			InterUtil.insertAttPath(Arrays.asList(arr), userBean, no);
			// 判断是否存在同名文件
			InterUtil.checkAttrName(Arrays.asList(arr));
		}

		Map<String, String> params = new HashMap<String, String>();

		if (!"Z".equals(xsddModel.getAuditStatus())) {
			String flowServiceId = xsddModel.getORDER_TRACE_ID();
			// 流程流转
			String flowType = "0";//LogicUtil.getFlowType(userBean);
			String flowNo = flowService.getFlowNoByLedger(xsddModel.getLEDGER_ID(), flowType);
			if ("T".equals(model.getAuditStatus())) {
				flowService.insertNextFlowByIndexNo(selRowId, xsddModel.getFACTORY_NO(), flowServiceId, flowNo, userBean, BusinessConsts.GOODSORDER_STATE_DSC, "拆件完成");

				params.clear();
				params.put("DESIGNER_ID", model.getDESIGNER_ID());
				params.put("FINISH_FLAG", BusinessConsts.INTEGER_1);
				params.put("FINISH_TIME", BusinessConsts.UPDATE_TIME);
				mapper.updateDesignerById(params);
				msg = "拆件成功";
			} else {
				flowService.backFlow(selRowId, xsddModel.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, BusinessConsts.GOODSORDER_STATE_FPSJS, model.getAuditContents());
				msg = "操作成功";
			}
		}

		params.clear();
		params.put("SALE_ORDER_ID", selRowId);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);
		return msg;
	}

	@Override
	@Transactional
	public void txConfirm(String SALE_ORDER_ID, SaleorderModel model, UserBean userBean) {
		// 流程流转
		String flowType = "0";//LogicUtil.getFlowType(userBean);
		String flowNo = flowService.getFlowNoByLedger(model.getLEDGER_ID(), flowType);
		flowService.insertNextFlowByIndexNo(SALE_ORDER_ID, model.getFACTORY_NO(), model.getORDER_TRACE_ID(), flowNo, userBean, BusinessConsts.COMPLETED, "");

		Map<String,String> params = new HashMap<String,String>();
		params.put("SALE_ORDER_ID", SALE_ORDER_ID);
		params.put("UPDATOR", userBean.getXTYHID());
		params.put("UPD_NAME", userBean.getXM());
		params.put("UPD_TIME", BusinessConsts.UPDATE_TIME);
		mapper.updateById(params);
	}


	@Override
	@Transactional
	public Map<String, Object> loadApplyByFkid(String param){
		Map<String, String> params = new HashMap<String, String>();
		params.put("SALE_ORDER_ID", param);
		return applyMapper.loadById(params);
	}
	@Override
	@Transactional
	public Map<String, Object> loadApplyById(String param){
		Map<String, String> params = new HashMap<String, String>();
		params.put("CHANGE_APPLY_ID", param);
		return applyMapper.loadById(params);
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	@Override
	@Transactional
	public String txParseExcelToDb(Workbook workBook, List sheetModel, UserBean userBean, HttpServletRequest request) {
		String info = "";
		Sheet sheetFst = workBook.getSheetAt(0);
		int rowCount = sheetFst.getLastRowNum() + 1;
		if (rowCount < Numbers.TWO) {
			throw new ServiceException("没有数据，不能导入！");
		}

		int chldBeaginNum = Numbers.FOUR;
		Map<String, Map<String, String>> sheetMap = (Map) sheetModel.get(0);
		Map<String, String> entryFields = sheetMap.get(SaleorderConsts.ENTRY_FIELDS);
		Map<String, String> childFields = sheetMap.get(SaleorderConsts.CHILD_FIELDS);
		Map<String, String> entryFieldLabels = sheetMap.get(SaleorderConsts.ENTRY_FIELD_REQUIED_LABELS);
		Map<String, String> childFieldLabels = sheetMap.get(SaleorderConsts.CHILD_FIELD_REQUIED_LABELS);
		// 需要校验的数据字典项
		Map<String, String> childDictLabels = sheetMap.get(SaleorderConsts.CHILD_DICT_LABELS);
		Map<String, String> childDicts = sheetMap.get(SaleorderConsts.CHILD_DICTS);
		for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
			Sheet sheet = workBook.getSheetAt(i);
			rowCount = sheet.getLastRowNum() + 1;
			if (rowCount < Numbers.TWO) {
				continue;
			}
			String sheetName = sheet.getSheetName();
			boolean remarkFlag = false;
			Map<String, Object> rowMap = new HashMap<String, Object>();

			List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
			for(int j = 1; j < rowCount; j++){
				Map<String, String> params = new HashMap<String, String>();
				// 取EXCEL行数据
				Row row = sheet.getRow(j);

				if (j < chldBeaginNum) {
					// 主表信息行
					/*if (j==1) {
						rowMap.put("PROJECT_NAME", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("1"))));//[1,1]项目名称
						rowMap.put("FACTORY_NO", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("6"))));//[1,6]订单编号
						rowMap.put("ORDER_DATE", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("11"))));//[1,11]订单日期
						rowMap.put("PRE_RECV_DATE", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("13"))));//[1,13]交货日期
						rowMap.put("BILL_TYPE", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("15"))));//[1,15]订单类型
					}
					if (j==2) {
						rowMap.put("CHANN_NAME", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("1"))));//[2,1]挂账单位
						rowMap.put("PERSON_CON", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("6"))));//[2,6]收货人
						rowMap.put("RECV_ADDR", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("11"))));//[2,11]收货地址
						rowMap.put("PROESS_TYPE", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("15"))));//[2,15]处理类型
					}*/
					for (Object obj : entryFields.keySet()) {
						String tempStr = obj.toString();
						Object tempColKey = entryFields.get(tempStr);
						String rowKey = tempColKey.toString().split(",")[0];
						String colKey = tempColKey.toString().split(",")[1];
						if (!"n".equals(rowKey) && j == Integer.valueOf(rowKey)) {
							String cellValue = ExcelUtil.getCellValue(row.getCell(Integer.valueOf(colKey)));
							rowMap.put(tempStr, cellValue.trim());
						}
					}
					continue;
				}
				if (remarkFlag) {
					rowMap.put("REMARK2", ExcelUtil.getCellValue(row.getCell(Integer.parseInt("0"))));//[n,0]订单备注
					break;
				}
				String mark = ExcelUtil.getCellValue(row.getCell(Integer.parseInt("0")));
				if ("订单备注".equals(mark)) {
					remarkFlag = true;
				}

				// 明细信息行
				Map<String, Object> childMap = new HashMap<String, Object>();
				for (Object obj : childFields.keySet()) {
					String tempStr = obj.toString();
					Object tempColKey = childFields.get(tempStr);
					String cellValue = ExcelUtil.getCellValue(row.getCell(Integer.parseInt(tempColKey.toString())));
					childMap.put(tempStr, cellValue.trim());
				}
				rowList.add(childMap);
			}
			/* 校验部分 begin */
			String msg = "";
			// 主表字段非空校验
			for (String field : entryFieldLabels.keySet()) {
				Object value = rowMap.get(field);
				if (StringUtil.isEmpty(StringUtil.nullToSring(value))) {
					throw new ServiceException(entryFieldLabels.get(field)+"不能为空，请修改后重试！");
				}
			}

			SaleorderModel model = (SaleorderModel) LogicUtil.tranMap2Bean(rowMap, SaleorderModel.class);
			Map<String, Object> params = new HashMap<String, Object>();
			// 项目名称
			String PROJECT_NAME = model.getPROJECT_NAME();
			params.put("PROJECT_NAME_EQ", PROJECT_NAME);
			//map.put("STATE", BusinessConsts.JC_STATE_ENABLE);
			params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
			List<Map<String,Object>> res = manageMapper.pageQuery(params);
			if (null != res && res.size() == 1) {
				model.setPROJECT_ID(StringUtil.nullToSring(res.get(0).get("PROJECT_ID")));
				model.setPROJECT_NO(StringUtil.nullToSring(res.get(0).get("PROJECT_NO")));
				model.setPROJECT_NAME(PROJECT_NAME);
			} else {
				throw new ServiceException("项目名称不存在，请修改后重试！");
			}

			// 查询账套id
			String FACTORY_NO = model.getFACTORY_NO();
			String xtbs = FACTORY_NO.substring(0, 1);
			String LEDGER_ID = Consts.LEDGER_ID_CONF.getString(xtbs);//系统标识
			if (StringUtil.isEmpty(LEDGER_ID)) {
				throw new ServiceException("订单编号格式不正确，请修改后重试！");
			} else {
				model.setLEDGER_ID(LEDGER_ID);
				model.setSALE_ORDER_NO(FACTORY_NO);
			}

			// 查询经销商信息
			String CHANN_NAME = model.getCHANN_NAME();
			params.clear();
			params.put("CHANN_NAME_EQ", CHANN_NAME);
			params.put("STATE", BusinessConsts.JC_STATE_ENABLE);
			params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
			List<Map<String,String>> result = channMapper.pageQuery(params);
			if (null != result && result.size() == 1) {
				model.setORDER_CHANN_ID(result.get(0).get("CHANN_ID"));
				model.setORDER_CHANN_NO(result.get(0).get("CHANN_NO"));
				model.setORDER_CHANN_NAME(CHANN_NAME);
				model.setRECV_CHANN_ID(result.get(0).get("CHANN_ID"));
				model.setRECV_CHANN_NO(result.get(0).get("CHANN_NO"));
				model.setRECV_CHANN_NAME(CHANN_NAME);
				model.setCHANN_ID(result.get(0).get("CHANN_ID"));
				model.setCHANN_NO(result.get(0).get("CHANN_NO"));
				model.setCHANN_NAME(CHANN_NAME);
				model.setPRINT_NAME(result.get(0).get("CHANN_ABBR"));
			} else {
				throw new ServiceException("挂账单位不存在，请修改后重试！");
			}

			// 查询订单类型
			String BILL_TYPE = model.getBILL_TYPE();
			ifExists("订单类型", "SALE_TYPE", BILL_TYPE, true);

			// 查询处理类型
			String PROESS_TYPE = model.getPROESS_TYPE();
			ifExists("处理类型", "SALE_ORDER_TYPE", PROESS_TYPE, true);

			BigDecimal TOTAL_AMOUNT = BigDecimal.ZERO;
			BigDecimal TOTAL_REBATE = BigDecimal.ZERO;
			List<SaleorderModelChld> childList = new ArrayList<SaleorderModelChld>();
			int row_num = 0;
			for (Map<String, Object> childRowMap : rowList) {
				SaleorderModelChld child = (SaleorderModelChld) LogicUtil.tranMap2Bean(childRowMap, SaleorderModelChld.class);
				// 查询产品名称
				String PRD_NAME = child.getPRD_NAME();
				if(StringUtil.isEmpty(PRD_NAME)){
					continue;
				}
				params.clear();
				params.put("PRD_NAME_EQ", PRD_NAME);
				params.put("STATE", BusinessConsts.JC_STATE_ENABLE);
				params.put("DEL_FLAG", BusinessConsts.DEL_FLAG_COMMON);
				result = prodMapper.query(params);
				if (null != result && result.size() >= 1) {
					child.setPRD_ID(result.get(0).get("PRD_ID"));
					child.setPRD_NO(result.get(0).get("PRD_NO"));
				} else {
					String err = "产品名称'"+PRD_NAME+"'不存在，请修改后重试！";
					throw new ServiceException(err);
				}
				// 子表字段非空校验
				for (String field : childFieldLabels.keySet()) {
					Object value = childRowMap.get(field);
					if (StringUtil.isEmpty(StringUtil.nullToSring(value))) {
						throw new ServiceException(childFieldLabels.get(field)+"不能为空，请修改后重试！");
					}
				}

				// 子表字段数据字典校验
				for (String field : childDictLabels.keySet()) {
					Object value = childRowMap.get(field);
					if (StringUtil.isEmpty(StringUtil.nullToSring(value))) {
						value = "无";
					}
					String sjxz = ifExists(childDictLabels.get(field), childDicts.get(field), value.toString(), true);
					LogicUtil.tranMapValue2BeanSetter(field, sjxz, child);
				}
/*
				// 查询材质
				String PRD_QUALITY = child.getPRD_QUALITY();
				ifExists("材质", "PROD_MATER", PRD_QUALITY);

				// 查询颜色
				String PRD_COLOR = child.getPRD_COLOR();
				ifExists("颜色", "PROD_COLOR", PRD_COLOR);

				// 查询玻璃
				String PRD_GLASS = child.getPRD_GLASS();
				ifExists("玻璃", "PROD_GLASS", PRD_GLASS);

				// 查询推向
				String PRD_TOWARD = child.getPRD_TOWARD();
				ifExists("推向", "PROD_TOWARD", PRD_TOWARD);

				// 查询系列
				String PRD_SERIES = child.getPRD_SERIES();
				ifExists("系列", "PRO_SERIES", PRD_SERIES);

				// 查询其他
				String PRD_OTHER = child.getPRD_OTHER();
				ifExists("其他", "PROD_OTHER", PRD_OTHER);
*/
				// 订单数量
				String ORDER_NUM = child.getORDER_NUM();
				if(StringUtil.isEmpty(ORDER_NUM)){
					throw new ServiceException("数量不能为空，请修改后重试！");
				}

				// 订单单价
				String PRICE = child.getPRICE();
				if(StringUtil.isEmpty(PRICE)){
					throw new ServiceException("单价不能为空，请修改后重试！");
				} else {
					child.setDECT_RATE("100");
					child.setDECT_PRICE(PRICE);
				}

				// 订单金额
				String ORDER_AMOUNT = child.getORDER_AMOUNT();
				if(StringUtil.isEmpty(ORDER_AMOUNT)){
					throw new ServiceException("金额不能为空，请修改后重试！");
				} else {
					BigDecimal ORDER_AMOUNT_BD = new BigDecimal(ORDER_AMOUNT);
					TOTAL_AMOUNT = TOTAL_AMOUNT.add(ORDER_AMOUNT_BD);
				}
				if ("M".equals(xtbs)) {
					// 木门默认开锁孔
					child.setIS_NO_LOCK_FLAG(BusinessConsts.INTEGER_1);
				}
/*
				// 查询空间位置
				String PRD_PLACE = child.getPRD_PLACE();
				String value = ifExists("空间位置", "PRO_PLACE", PRD_PLACE);
				child.setPRD_PLACE(value);
*/
				child.setROW_NO(StringUtil.nullToZero(row_num));
				row_num++;
				childList.add(child);
			}
/*
			for (Map<String, Map<String, String>> map : sjzdxList) {
				for (Iterator it = map.keySet().iterator(); it.hasNext();) {
					Map<String, String> objMap = (Map<String, String>) it.next();

				}
			}
*/
			if (!"".equals(msg)) {
				throw new ServiceException(msg);
			} else {
				model.setTOTAL_AMOUNT(TOTAL_AMOUNT.toString());
				model.setSALE_ID( userBean.getXTYHID());
				model.setSALE_NAME( userBean.getXM());
				model.setSALEDEPT_NAME( userBean.getBMMC());
				model.setSALEDEPT_ID( userBean.getBMXXID());
			}
			/* 校验部分 end */
			txEdit("", model, childList, userBean);
		}

		return info;
	}

	@SuppressWarnings("unused")
	private String ifExists(String label, String SJZDID, String SJXMC) {
		return ifExists(label, SJZDID, SJXMC, false);
	}

	private String ifExists(String label, String SJZDID, String SJXMC, boolean required) {
		String SJXZ = "";
		if (required && StringUtil.isEmpty(SJXMC)) {
			throw new ServiceException(label + "不能为空，请修改后重试！");
		}
		SJXMC = StringUtil.isEmpty(SJXMC) ? "无" : SJXMC;
		Map<String,String> paramsMap = new HashMap<String, String>();
		paramsMap.put("SJZDID", SJZDID);
		paramsMap.put("SJXMCEQ", SJXMC);
		List <SJZDMxModel> result = sjzdmxMapper.query(paramsMap);
		if (null != result && result.size() >= 1) {
			SJZDMxModel sjzdmx = result.get(0);
			SJXZ = sjzdmx.getSJXZ();
		} else {
			throw new ServiceException(label + ":'"+SJXMC+"'不存在，请修改后重试！");
		}
		return SJXZ;
	}


	@Override
	public List<Map<String,String>> toSendFindSale(String saleOrderId){
		return mapper.toSendFindSale(saleOrderId);
	}
}