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
	private Integer reservedAmount;
	
	private Integer goodsNo;
	private List<CounterOrderDetailVO> counterOrderDatailVO;
	
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

	@Column(name = "reservedAmount")
public Integer getReservedAmount() {
		return reservedAmount;
	}

	public void setReservedAmount(Integer reservedAmount) {
		this.reservedAmount = reservedAmount;
	}

	//	private Integer disno;
//	柏翔改
	private Integer memCouponNo;

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
				return "未付款";
			case 1:
				return "已付款";

			case 2:
				return "已取消";
			case 3:
				return "已發貨";
			case 4:
				return "已完成";
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
	
//	柏翔改
	@Column(name = "memCouponNo")
	public Integer getMemCouponNo() {
		return memCouponNo;
	}
//	柏翔改
	public void setMemCouponNo(Integer memCouponNo) {
		this.memCouponNo = memCouponNo;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}