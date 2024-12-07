package com.counterorder.model;

public class CounterOrderDTO {
	 	private Integer counterOrderNo;
		private Integer memNo;
	    private Integer disNo;
	    private Integer orderTotalPriceBefore;
	    private Integer orderTotalPriceAfter;
	    private String receiverName;
	    private String receiverAdr;
	    private String receiverPhone;
	    private Integer orderStatus;
	    
		public Integer getMemNo() {
			return memNo;
		}
		public void setMemNo(Integer memNo) {
			this.memNo = memNo;
		}
		public Integer getDisNo() {
			return disNo;
		}
		public void setDisNo(Integer disNo) {
			this.disNo = disNo;
		}
		public Integer getOrderTotalPriceBefore() {
			return orderTotalPriceBefore;
		}
		public void setOrderTotalPriceBefore(Integer orderTotalPriceBefore) {
			this.orderTotalPriceBefore = orderTotalPriceBefore;
		}
		public Integer getOrderTotalPriceAfter() {
			return orderTotalPriceAfter;
		}
		public void setOrderTotalPriceAfter(Integer orderTotalPriceAfter) {
			this.orderTotalPriceAfter = orderTotalPriceAfter;
		}
		public String getReceiverName() {
			return receiverName;
		}
		public void setReceiverName(String receiverName) {
			this.receiverName = receiverName;
		}
		public String getReceiverAdr() {
			return receiverAdr;
		}
		public void setReceiverAdr(String receiverAdr) {
			this.receiverAdr = receiverAdr;
		}
		public String getReceiverPhone() {
			return receiverPhone;
		}
		public void setReceiverPhone(String receiverPhone) {
			this.receiverPhone = receiverPhone;
		}
		public Integer getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(Integer orderStatus) {
			this.orderStatus = orderStatus;
		}
		public Integer getCounterOrderNo() {
			return counterOrderNo;
		}
		public void setCounterOrderNo(Integer counterOrderNo) {
			this.counterOrderNo = counterOrderNo;
		}
}
