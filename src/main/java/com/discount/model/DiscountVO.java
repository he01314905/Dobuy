package com.discount.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Discount") 
public class DiscountVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Integer disNo;         
    private String disTitle;       
    private String disContext;     
    private Double disRate;        
    private Integer disStatus = 1;     
    private String descLimit;      
    private Date createdAt;        
    private Date updatedAt;        
    private Date disStart;         
    private Date disEnd;           

    public DiscountVO() {
    }

    @Id
    @Column(name = "disNo")
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    public Integer getDisNo() {
        return this.disNo;
    }

    public void setDisNo(Integer disNo) {
        this.disNo = disNo;
    }

    @Column(name = "disTitle")
    @NotEmpty(message = "平台優惠名稱: 請勿空白")
    @Size(max = 255, message = "平台優惠名稱: 長度不能超過{max}")
    public String getDisTitle() {
        return this.disTitle;
    }

    public void setDisTitle(String disTitle) {
        this.disTitle = disTitle;
    }

    @Column(name = "disContext")
    @NotEmpty(message = "平台優惠內容: 請勿空白")
    @Size(max = 255, message = "平台優惠內容: 長度不能超過{max}")
    public String getDisContext() {
        return this.disContext;
    }

    public void setDisContext(String disContext) {
        this.disContext = disContext;
    }

    @Column(name = "disRate")
    @NotNull(message = "折扣率: 請勿空白")
    @DecimalMin(value = "0.01", message = "折扣率: 必須大於等於{value}")
    @DecimalMax(value = "1.00", message = "折扣率: 必須小於等於{value}")
    public Double getDisRate() {
        return this.disRate;
    }

    public void setDisRate(Double disRate) {
        this.disRate = disRate;
    }

    @Column(name = "disStatus")
    @NotNull(message = "優惠狀態: 請勿空白")
    @Min(value = 0, message = "優惠狀態: 必須是0或以上")
    @Max(value = 2, message = "優惠狀態: 必須是2或以下")
    public Integer getDisStatus() {
        return this.disStatus;
    }

    public void setDisStatus(Integer disStatus) {
        this.disStatus = disStatus;
    }

    @Column(name = "descLimit")
    @NotEmpty(message = "使用描述: 請勿空白")
    @Size(max = 255, message = "使用條件描述: 長度不能超過{max}")
    public String getDescLimit() {
        return this.descLimit;
    }

    public void setDescLimit(String descLimit) {
        this.descLimit = descLimit;
    }

    
    @PrePersist
    protected void onCreate() {
        createdAt = new Date(); // 當記錄第一次被保存時，設置 createdAt
        updatedAt = new Date(); // 同時設置 updatedAt
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date(); // 當記錄被更新時，自動更新 updatedAt
    }
    
    
    @Column(name = "createdAt")
//    @NotNull(message = "建立時間: 請勿空白")
    @PastOrPresent(message = "建立時間: 必須是過去或現在的時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "updatedAt")
//    @NotNull(message = "更新時間: 請勿空白")
    @PastOrPresent(message = "更新時間: 必須是過去或現在的時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "disStart")
    @NotNull(message = "優惠起始日: 請勿空白")
    @FutureOrPresent(message = "優惠起始日: 必須是現在或未來的時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDisStart() {
        return this.disStart;
    }

    public void setDisStart(Date disStart) {
        this.disStart = disStart;
    }

    @Column(name = "disEnd")
    @NotNull(message = "優惠結束日: 請勿空白")
    @Future(message = "優惠結束日: 必須是未來的時間")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date getDisEnd() {
        return this.disEnd;
    }

    public void setDisEnd(Date disEnd) {
        this.disEnd = disEnd;
    }
}
