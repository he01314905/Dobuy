package com.transactionmapping.model;

	import javax.persistence.*;
	import java.sql.Timestamp;

	@Entity
	@Table(name = "TransactionMapping")
	public class TransactionMappingVO {
	    
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @Column(name = "merchantTradeNo", nullable = false, length = 50)
	    private String merchantTradeNo;

	    @Column(name = "counterOrderNo", nullable = false)
	    private Integer counterOrderNo;

	    @Column(name = "createdTime", nullable = false, updatable = false, insertable = false)
	    private Timestamp createdTime;

	    // Constructors
	    public TransactionMappingVO() {}

	    public TransactionMappingVO(String merchantTradeNo, Integer counterOrderNo) {
	        this.merchantTradeNo = merchantTradeNo;
	        this.counterOrderNo = counterOrderNo;
	    }

	    // Getters and Setters
	    public Integer getId() {
	        return id;
	    }

	    public void setId(Integer id) {
	        this.id = id;
	    }

	    public String getMerchantTradeNo() {
	        return merchantTradeNo;
	    }

	    public void setMerchantTradeNo(String merchantTradeNo) {
	        this.merchantTradeNo = merchantTradeNo;
	    }

	    public Integer getCounterOrderNo() {
	        return counterOrderNo;
	    }

	    public void setCounterOrderNo(Integer counterOrderNo) {
	        this.counterOrderNo = counterOrderNo;
	    }

	    public Timestamp getCreatedTime() {
	        return createdTime;
	    }

	    public void setCreatedTime(Timestamp createdTime) {
	        this.createdTime = createdTime;
	    }
	
}
