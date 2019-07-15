package com.centit.drp.main.sales.deliver.order.model;

import java.util.Date;

/**
 * 发货指令明细
 * @author wang_pt
 *
 */
public class SendDtlModel {
	
	 
	 String  send_order_dtl_id;//发货指令明细ID
	 String  row_no;//行号
     String  sale_order_dtl_id;//销售订单明细ID
     String  sale_order_id;//销售订单ID
     String  group_no;//组号
     String  prd_id;//产品ID
     String  prd_no;//产品编号
     String  prd_name;//产品名称
     String  hole_spec;//门洞尺寸
     String  prd_spec;//规格尺寸
     String  prd_toward;//推向
     String  prd_quality;//材质
     String  prd_color;//颜色
     String  prd_glass;//玻璃
     String  prd_other;//其它
     String  prd_series;//系列
     String  brand;//品牌
     String  std_unit;//标准单位
     String  is_no_stand_flag;//0=否,1=是
     String  price;//单价
     String  dect_rate;//折扣率
     String  dect_price;//折后单价
     String  rebate_amount;//返利金额
     String  order_num;//订货数量
     String  order_amount;//订货金额
     Date  advc_send_date;//预计发货日期
     String  remark;//备注
     Integer  del_flag;//0=否,1=是
     String  send_num;//发货数量
     String  send_order_id;//发货指令ID
     String projected_area ;//投影面积
     String expand_area;//展开面积
     String send_amount;//发货金额
     //实际发货数量
     String sended_num ;
     //工程位置
     String prd_place  ;
     //是否返利
     String is_no_rebate_flag ;
     //是否开锁孔
     String is_no_lock_flag   ;
     //附图号
     String figure_no ;
     String prd_size;//尺寸
     String rebate_price;//返利单价
     String  ATT_PATH;//附件路径
     String  from_bill_dtl_id;//来源单据明细ID  
     
	
     public String getSend_order_dtl_id() {
		return send_order_dtl_id;
	}
	public void setSend_order_dtl_id(String send_order_dtl_id) {
		this.send_order_dtl_id = send_order_dtl_id;
	}
	public String getRow_no() {
		return row_no;
	}
	public void setRow_no(String row_no) {
		this.row_no = row_no;
	}
	public String getSale_order_dtl_id() {
		return sale_order_dtl_id;
	}
	public void setSale_order_dtl_id(String sale_order_dtl_id) {
		this.sale_order_dtl_id = sale_order_dtl_id;
	}
	public String getSale_order_id() {
		return sale_order_id;
	}
	public void setSale_order_id(String sale_order_id) {
		this.sale_order_id = sale_order_id;
	}
	public String getGroup_no() {
		return group_no;
	}
	public void setGroup_no(String group_no) {
		this.group_no = group_no;
	}
	public String getPrd_id() {
		return prd_id;
	}
	public void setPrd_id(String prd_id) {
		this.prd_id = prd_id;
	}
	public String getPrd_no() {
		return prd_no;
	}
	public void setPrd_no(String prd_no) {
		this.prd_no = prd_no;
	}
	public String getPrd_name() {
		return prd_name;
	}
	public void setPrd_name(String prd_name) {
		this.prd_name = prd_name;
	}
	public String getHole_spec() {
		return hole_spec;
	}
	public void setHole_spec(String hole_spec) {
		this.hole_spec = hole_spec;
	}
	public String getPrd_spec() {
		return prd_spec;
	}
	public void setPrd_spec(String prd_spec) {
		this.prd_spec = prd_spec;
	}
	public String getPrd_toward() {
		return prd_toward;
	}
	public void setPrd_toward(String prd_toward) {
		this.prd_toward = prd_toward;
	}
	public String getPrd_quality() {
		return prd_quality;
	}
	public void setPrd_quality(String prd_quality) {
		this.prd_quality = prd_quality;
	}
	public String getPrd_color() {
		return prd_color;
	}
	public void setPrd_color(String prd_color) {
		this.prd_color = prd_color;
	}
	public String getPrd_glass() {
		return prd_glass;
	}
	public void setPrd_glass(String prd_glass) {
		this.prd_glass = prd_glass;
	}
	public String getPrd_other() {
		return prd_other;
	}
	public void setPrd_other(String prd_other) {
		this.prd_other = prd_other;
	}
	public String getPrd_series() {
		return prd_series;
	}
	public void setPrd_series(String prd_series) {
		this.prd_series = prd_series;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getStd_unit() {
		return std_unit;
	}
	public void setStd_unit(String std_unit) {
		this.std_unit = std_unit;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDect_rate() {
		return dect_rate;
	}
	public void setDect_rate(String dect_rate) {
		this.dect_rate = dect_rate;
	}
	public String getDect_price() {
		return dect_price;
	}
	public void setDect_price(String dect_price) {
		this.dect_price = dect_price;
	}
	
	public String getRebate_amount() {
		return rebate_amount;
	}
	public void setRebate_amount(String rebate_amount) {
		this.rebate_amount = rebate_amount;
	}
	public String getOrder_num() {
		return order_num;
	}
	public void setOrder_num(String order_num) {
		this.order_num = order_num;
	}
	public String getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(String order_amount) {
		this.order_amount = order_amount;
	}
	public Date getAdvc_send_date() {
		return advc_send_date;
	}
	public void setAdvc_send_date(Date advc_send_date) {
		this.advc_send_date = advc_send_date;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDel_flag() {
		return del_flag;
	}
	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}
	public String getSend_num() {
		return send_num;
	}
	public void setSend_num(String send_num) {
		this.send_num = send_num;
	}
	public String getSend_order_id() {
		return send_order_id;
	}
	public void setSend_order_id(String send_order_id) {
		this.send_order_id = send_order_id;
	}
	public String getProjected_area() {
		return projected_area;
	}
	public void setProjected_area(String projected_area) {
		this.projected_area = projected_area;
	}
	public String getExpand_area() {
		return expand_area;
	}
	public void setExpand_area(String expand_area) {
		this.expand_area = expand_area;
	}
	public String getSend_amount() {
		return send_amount;
	}
	public void setSend_amount(String send_amount) {
		this.send_amount = send_amount;
	}
	public String getSended_num() {
		return sended_num;
	}
	public void setSended_num(String sended_num) {
		this.sended_num = sended_num;
	}
	public String getPrd_place() {
		return prd_place;
	}
	public void setPrd_place(String prd_place) {
		this.prd_place = prd_place;
	}
	public String getIs_no_rebate_flag() {
		return is_no_rebate_flag;
	}
	public void setIs_no_rebate_flag(String is_no_rebate_flag) {
		this.is_no_rebate_flag = is_no_rebate_flag;
	}
	public String getIs_no_lock_flag() {
		return is_no_lock_flag;
	}
	public void setIs_no_lock_flag(String is_no_lock_flag) {
		this.is_no_lock_flag = is_no_lock_flag;
	}
	public String getFigure_no() {
		return figure_no;
	}
	public void setFigure_no(String figure_no) {
		this.figure_no = figure_no;
	}
	public String getPrd_size() {
		return prd_size;
	}
	public void setPrd_size(String prd_size) {
		this.prd_size = prd_size;
	}
	public String getRebate_price() {
		return rebate_price;
	}
	public void setRebate_price(String rebate_price) {
		this.rebate_price = rebate_price;
	}
	public String getATT_PATH() {
		return ATT_PATH;
	}
	public void setATT_PATH(String aTT_PATH) {
		ATT_PATH = aTT_PATH;
	}
	public String getIs_no_stand_flag() {
		return is_no_stand_flag;
	}
	public void setIs_no_stand_flag(String is_no_stand_flag) {
		this.is_no_stand_flag = is_no_stand_flag;
	}
	public String getFrom_bill_dtl_id() {
		return from_bill_dtl_id;
	}
	public void setFrom_bill_dtl_id(String from_bill_dtl_id) {
		this.from_bill_dtl_id = from_bill_dtl_id;
	}
    
	
	
}
