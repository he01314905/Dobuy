package com.member.model;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.memcoupon.model.MemCouponVO;

@Entity
@Table(name = "Member")
public class MemberVO {
	
	public interface LoginGroup {}
	public interface RegisterGroup {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memNo", nullable = false)
    private Integer memNo;

    @Column(name = "memAccount", nullable = false, length = 50)
    @NotEmpty(message="帳號: 請勿空白" , groups = {LoginGroup.class, RegisterGroup.class})
    private String memAccount;

    @Column(name = "memPassword", nullable = false, length = 100)
    @NotEmpty(message="密碼: 請勿空白" , groups = {LoginGroup.class, RegisterGroup.class})
    private String memPassword;
    
    @Transient // 表示该字段不会映射到数据库中
    @NotEmpty(message = "確認密碼: 請勿空白" , groups = {RegisterGroup.class})
    private String confirmPassword;

    @Column(name = "memName", nullable = false, length = 50)
    @NotEmpty(message="姓名: 請勿空白" , groups = {RegisterGroup.class})
    private String memName;

    @Column(name = "memAddress", length = 100)
    private String memAddress;

    @Column(name = "memPhone", length = 15)
    private String memPhone;

    @Column(name = "memUID", length = 10)
    private String memUID;

    @Column(name = "memEmail", length = 50)
    @NotEmpty(message="信箱: 請勿空白" , groups = {RegisterGroup.class})
    private String memEmail;

    @Column(name = "memSex")
    private Byte memSex;

    @Column(name = "memBirth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 指定日期格式
    @NotNull(message="生日: 請勿空白" , groups = {RegisterGroup.class})
    private Date memBirth;

    @Column(name = "memStatus")
    private Integer memStatus; 


    @Column(name = "createdAt", updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;


    @Column(name = "updatedAt")
    @CreationTimestamp
    private Timestamp updatedAt;

    @Column(name = "memPasswordHint", length = 100)
    private String memPasswordHint;

    @Column(name = "memPasswordHintAnswer", length = 100)
    private String memPasswordHintAnswer;

    @Column(name = "memPasswordChangedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date memPasswordChangedAt;

    @Column(name = "memEmailChecked")
    private Byte memEmailChecked;

//    winfred
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) //1130
    private List<MemCouponVO> memCoupons = new ArrayList<>();
    
    // Getters and Setters

    public Integer getMemNo() {
        return memNo;
    }

    public void setMemNo(Integer memNo) {
        this.memNo = memNo;
    }

    public String getMemAccount() {
        return memAccount;
    }

    public void setMemAccount(String memAccount) {
        this.memAccount = memAccount;
    }

    public String getMemPassword() {
        return memPassword;
    }

    public void setMemPassword(String memPassword) {
        this.memPassword = memPassword;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public String getMemAddress() {
        return memAddress;
    }

    public void setMemAddress(String memAddress) {
        this.memAddress = memAddress;
    }

    public String getMemPhone() {
        return memPhone;
    }

    public void setMemPhone(String memPhone) {
        this.memPhone = memPhone;
    }

    public String getMemUID() {
        return memUID;
    }

    public void setMemUID(String memUID) {
        this.memUID = memUID;
    }

    public String getMemEmail() {
        return memEmail;
    }

    public void setMemEmail(String memEmail) {
        this.memEmail = memEmail;
    }

    public Byte getMemSex() {
        return memSex;
    }

    public void setMemSex(Byte memSex) {
        this.memSex = memSex;
    }

    public Date getMemBirth() {
        return memBirth;
    }

    public void setMemBirth(Date memBirth) {
        this.memBirth = memBirth;
    }

    public Integer getMemStatus() {
        return memStatus;
    }

    public void setMemStatus(Integer memStatus) {
        this.memStatus = memStatus;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }


    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public String getMemPasswordHint() {
        return memPasswordHint;
    }

    public void setMemPasswordHint(String memPasswordHint) {
        this.memPasswordHint = memPasswordHint;
    }

    public String getMemPasswordHintAnswer() {
        return memPasswordHintAnswer;
    }

    public void setMemPasswordHintAnswer(String memPasswordHintAnswer) {
        this.memPasswordHintAnswer = memPasswordHintAnswer;
    }

    public Date getMemPasswordChangedAt() {
        return memPasswordChangedAt;
    }

    public void setMemPasswordChangedAt(Date memPasswordChangedAt) {
        this.memPasswordChangedAt = memPasswordChangedAt;
    }

    public Byte getMemEmailChecked() {
        return memEmailChecked;
    }

    public void setMemEmailChecked(Byte memEmailChecked) {
        this.memEmailChecked = memEmailChecked;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public boolean isPasswordConfirmed() {
        if (memPassword == null || confirmPassword == null) {
            return false; // 跳過空值校驗，交給@NotEmpty處理
        }
        return memPassword.equals(confirmPassword);
    }
    
    // winfred
    public List<MemCouponVO> getMemCoupons() { //1130
        return memCoupons;
    }
    // winfred
    public void setMemCoupons(List<MemCouponVO> memCoupons) { //1130
        this.memCoupons = memCoupons;
    }

}