package com.msg.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "counterInform")
public class MsgVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counterInformNo")
    private Integer counterInformNo;
    
    @Column(name = "counterNo", nullable = false)
    private Integer counterNo;
    
    @Column(name = "memNo")
    private Integer memNo;

    @NotNull
    @NotEmpty(message = "訊息內文請勿空白。")
    @Size(max = 1000)
    @Column(name = "informMsg")
    private String informMsg;

    
    @Column(name = "informDate") 
    private Timestamp informDate;
    
    
    @Column(name = "informRead")
    private Byte informRead;


    public Integer getCounterInformNo() {
        return counterInformNo;
    }

    public void setCounterInformNo(Integer counterInformNo) {
        this.counterInformNo = counterInformNo;
    }
    

    public Integer getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(Integer counterNo) {
        this.counterNo = counterNo;
    }
    
    public Integer getMemNo() {
		return memNo;
	}

	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}

    public String getInformMsg() {
        return informMsg;
    }

    public void setInformMsg(String informMsg) {
        this.informMsg = informMsg;
    }

    public Timestamp getInformDate() {
        return informDate;
    }

    public void setInformDate(Timestamp informDate) {
        this.informDate = informDate;
    }

	public Byte getInformRead() {
		return informRead;
	}

	public void setInformRead(Byte informRead) {
		this.informRead = informRead;
	}
    
    
}

