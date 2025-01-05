package com.customerservice.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "goodComplaint")
public class CustomerServiceVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "counterComplaintNo")
    private Integer counterComplaintNo;

    @Column(name = "counterNo")
    private Integer counterNo;
    
    @Column(name = "memNo")
    private Integer memNo;

    @NotNull(message = "訂單編號請勿空白")
    @Digits(integer = 10, fraction = 0, message = "訂單編號:必須要是個數字")
    @Column(name = "counterOrderNo")
    private Integer counterOrderNo;  // 修正拼寫

   

    @NotNull
    @NotEmpty(message = "客訴內容請勿空白。")
    @Size(max = 500)
    @Column(name = "complaintReason")
    private String complaintReason;

    @Column(name = "complaintPhotos")
    private byte[] complaintPhotos;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "complaintDate")
    private Timestamp complaintDate;

    @Column(name = "complaintStatus")
    private Byte complaintStatus;

    @Column(name = "complaintMSG")
    private String complaintMSG;

    public Integer getCounterComplaintNo() {
        return counterComplaintNo;
    }

    public void setCounterComplaintNo(Integer counterComplaintNo) {
        this.counterComplaintNo = counterComplaintNo;
    }

    public Integer getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(Integer counterNo) {
        this.counterNo = counterNo;
    }

    public Integer getCounterOrderNo() {
        return counterOrderNo;
    }

    public void setCounterOrderNo(Integer counterOrderNo) {
        this.counterOrderNo = counterOrderNo;
    }

    public Integer getMemNo() {
        return memNo;
    }

    public void setMemNo(Integer memNo) {
        this.memNo = memNo;
    }

    public String getComplaintReason() {
        return complaintReason;
    }

    public void setComplaintReason(String complaintReason) {
        this.complaintReason = complaintReason;
    }

    public byte[] getComplaintPhotos() {
        return complaintPhotos;
    }

    public void setComplaintPhotos(byte[] complaintPhotos) {
        this.complaintPhotos = complaintPhotos;
    }

    public Timestamp getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Timestamp complaintDate) {
        this.complaintDate = complaintDate;
    }

    public Byte getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(Byte complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public String getComplaintMSG() {
        return complaintMSG;
    }

    public void setComplaintMSG(String complaintMSG) {
        this.complaintMSG = complaintMSG;
    }

    // 新增的描述方法
    public String getComplaintStatusDescription() {
        switch (complaintStatus) {
            case 0:
                return "待處理";
            case 1:
                return "處理完畢";
            default:
                return "待處理";
        }
    }
}

