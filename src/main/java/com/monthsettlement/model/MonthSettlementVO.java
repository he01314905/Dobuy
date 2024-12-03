package com.monthsettlement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity  // 將此類別標註為 JPA 的實體類別
@Table(name = "monthsettlement")  // 對應資料庫中的 MONTH_SETTLEMENT 表
public class MonthSettlementVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer monthSettlementNo;  // 抽成月結編號
    private Integer counterNo;          // 櫃位編號
    private String month;               // 月份
    private Integer comm;               // 總金額

    public MonthSettlementVO() {  // 必需的無參數建構子
    }

    @Id  // 將 monthSettlementNo 設定為主鍵
    @Column(name = "monthSettlementNo",nullable = false)  // 對應資料庫中的 MONTH_SETTLEMENT_NO 欄位
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 自動遞增策略
    public Integer getMonthSettlementNo() {
        return monthSettlementNo;
    }

    public void setMonthSettlementNo(Integer monthSettlementNo) {
        this.monthSettlementNo = monthSettlementNo;
    }

    @Column(name = "counterNo",nullable = false)  // 對應資料庫中的 COUNTER_NO 欄位
    @NotNull(message = "櫃位編號: 請勿空白")
    public Integer getCounterNo() {
    	
        return counterNo;
    }

    public void setCounterNo(Integer counterNo) {
        this.counterNo = counterNo;
    }

    @Column(name = "month",nullable = false)  // 對應資料庫中的 MONTH 欄位
    @NotEmpty(message = "月份: 請勿空白")
  //  @Pattern(regexp = "^\\d{6}$", message = "月份: 格式必須為 YYYY-MM")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Column(name = "comm",nullable = false)  // 對應資料庫中的 COMM 欄位
    @NotNull(message = "總金額: 請勿空白")
    @Min(value = 0, message = "總金額: 不能小於{value}")
    public Integer getComm() {
        return comm;
    }

    public void setComm(Integer comm) {
        this.comm = comm;
    }
    
    
}
