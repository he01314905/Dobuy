package com.counterorder.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.counterHome.counterOrderTest.model.NewCounterOrderVO;
import com.counterorderdetail.model.CounterOrderDetailVO;

@Entity
@Table(name = "counterorder")
public class CounterOrderVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer counterOrderNo;
	private Integer counterNo;
	private Integer memNo;
	private Integer orderTotalBefore;
	private Integer orderTotalAfter;
	private Integer orderStatus;
	private String receiverName;
	private String receiverAdr;
	private String receiverPhone;
	private Date ordertime;
	@Column(name = "couponNo", nullable = false)
	private Integer couponNo;
	
	
	public Integer getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(Integer couponNo) {
		this.couponNo = couponNo;
	}



	private Integer goodsNo;
	private List<CounterOrderDetailVO> counterOrderDatailVO;
	
	public CounterOrderVO(NewCounterOrderVO newCounterOrderVO) {
		this.counterNo = newCounterOrderVO.getCounterNo();
		this.memNo = newCounterOrderVO.getMemNo();
		this.orderTotalBefore = newCounterOrderVO.getOrderTotalPriceBefore();
		this.orderTotalAfter = newCounterOrderVO.getOrderTotalPriceAfter();
		this.orderStatus = newCounterOrderVO.getOrderStatus();
		this.receiverName = newCounterOrderVO.getReceiverName();
		this.receiverAdr = newCounterOrderVO.getReceiverAdr();
		this.receiverPhone = newCounterOrderVO.getReceiverPhone();
		this.couponNo = newCounterOrderVO.getCouponNo();
	}
	@OneToMany(mappedBy = "counterOrderVO", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<CounterOrderDetailVO> getCounterOrderDatailVO() {
		return counterOrderDatailVO;
	}

	public void setCounterOrderDatailVO(List<CounterOrderDetailVO> counterOrderDatailVO) {
		this.counterOrderDatailVO = counterOrderDatailVO;
	}

	@Transient
	public Integer getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(Integer goodsNo) {
		this.goodsNo = goodsNo;
	}



	//	private Integer disno;

//	柏翔改
//	private Integer disNo;

	public CounterOrderVO() {

	}

		
	
	@Id
	@Column(name = "counterOrderNo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getCounterOrderNo() {
		return counterOrderNo;
	}

	public void setCounterOrderNo(Integer counterOrderNo) {
		this.counterOrderNo = counterOrderNo;
	}

	@Column(name = "counterNo")
	public Integer getCounterNo() {
		return counterNo;
	}

	public void setCounterNo(Integer counterNo) {
		this.counterNo = counterNo;
	}

	@Column(name = "memNo")
	public Integer getMemNo() {
		return memNo;
	}

	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}

	@Column(name = "orderTotalBefore")
	public Integer getOrderTotalBefore() {
		return orderTotalBefore;
	}

	public void setOrderTotalBefore(Integer orderTotalBefore) {
		this.orderTotalBefore = orderTotalBefore;
	}

	@Column(name = "orderTotalAfter")
	public Integer getOrderTotalAfter() {
		return orderTotalAfter;
	}

	public void setOrderTotalAfter(Integer orderTotalAfter) {
		this.orderTotalAfter = orderTotalAfter;
	}

	@Column(name = "orderStatus")
	public Integer getOrderStatus() {
		return orderStatus;
	}

	@Transient
	public String getOrderDisplay() {
		if (orderStatus != null) {
			switch (orderStatus) {
			case 0:
				return "未出貨";
			case 1:
				return "已出貨";

			case 2:
				return "已完成";
			case 3:
				return "退貨";
			case 4:
				return "作廢";
			case 5:
				return "未付款";
			case 6:
				return "已通知發貨";
			
			}
		}
		return "訂單未成立";
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "receiverName")
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	@Column(name = "receiverAdr")
	public String getReceiverAdr() {
		return receiverAdr;
	}

	public void setReceiverAdr(String receiverAdr) {
		this.receiverAdr = receiverAdr;
	}

	@Column(name = "receiverPhone")
	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	@Column(name = "orderTime", insertable = false, updatable = false)
	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

//	@Column(name = "disNo")
//	public Integer getDisno() {
//		return disno;
//	}
//
//	public void setDisno(Integer disno) {
//		this.disno = disno;
//	}
	private Integer memCouponNo;
////	柏翔改

	
//	柏翔改

	@Column(name = "memCouponNo")
	public Integer getMemCouponNo() {
		return memCouponNo;
	}
//	柏翔改
	public void setMemCouponNo(Integer memCouponNo) {
		this.memCouponNo = memCouponNo;
	}
//	
//	柏翔改
//	@Column(name = "disNo")
//	public Integer getDisNo() {
//		return disNo;
//	}
//	柏翔改
//	public void setDisNo(Integer disNo) {
//		this.disNo = disNo;
//	}

	
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}