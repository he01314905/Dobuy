package com.storecarousel.model;

import java.sql.Timestamp;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "storecarousel")
public class storeCarouselVO {

    @Id
    @Column(name = "storeCarouselNo")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動生成主鍵
    private Integer id; // StoreCarousel_No

    @Column(name = "counterNo", nullable = false)
    private Integer counterNo; // 櫃位編號

    @Column(name = "disNo", nullable = false)
    private Integer disNo; // 平台優惠編號

    @Column(name = "carouselTime", nullable = false, updatable = false)
    @CreationTimestamp // 自動生成時間戳
    private Timestamp carouselTime; // 輪播時間

    @Transient // 標記為非持久化字段
    private MultipartFile upFile; // 用於接收上傳圖片

    @Column(name = "carouselPic")
    private byte[] carouselPic; // 輪播圖片

    @Transient // 非持久化字段
    private String base64Image; // 用於前端顯示的 Base64 圖片

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(Integer counterNo) {
        this.counterNo = counterNo;
    }

    public Integer getDisNo() {
        return disNo;
    }

    public void setDisNo(Integer disNo) {
        this.disNo = disNo;
    }

    public Timestamp getCarouselTime() {
        return carouselTime;
    }

    public void setCarouselTime(Timestamp carouselTime) {
        this.carouselTime = carouselTime;
    }

    public MultipartFile getUpFile() {
        return upFile;
    }

    public void setUpFile(MultipartFile upFile) {
        this.upFile = upFile;
    }

    public byte[] getCarouselPic() {
        return carouselPic;
    }

    public void setCarouselPic(byte[] carouselPic) {
        this.carouselPic = carouselPic;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public void convertToBase64() {
        if (carouselPic != null) {
            this.base64Image = Base64.getEncoder().encodeToString(this.carouselPic);
        }
    }
}
