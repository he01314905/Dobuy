package com.notice.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "notice")
public class NoticeVO implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memNoticeNo")
    private Integer counterInformNo;
    
    @Column(name= "memNo")
    private Integer memNo;

    
    @Size(max = 1000)
    @Column(name = "noticeContent")
    private String noticeContent;


    
    @Column(name = "noticeDate") 
    private Timestamp NoticeDate;
    
    
    @Column(name = "noticeRead")
    private Byte noticeRead;


	public Integer getCounterInformNo() {
		return counterInformNo;
	}


	public void setCounterInformNo(Integer counterInformNo) {
		this.counterInformNo = counterInformNo;
	}


	public Integer getMemNo() {
		return memNo;
	}


	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}


	public String getNoticeContent() {
		return noticeContent;
	}


	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}


	public Timestamp getNoticeDate() {
		return NoticeDate;
	}


	public void setNoticeDate(Timestamp noticeDate) {
		NoticeDate = noticeDate;
	}


	public Byte getNoticeRead() {
		return noticeRead;
	}


	public void setNoticeRead(Byte noticeRead) {
		this.noticeRead = noticeRead;
	}    
    
}
