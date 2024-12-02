package com.goodstrack.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "goodstrack")
public class GoodsTrackVO implements Serializable{

	private static final long serialVersionUID = 1L;

		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "goodsTrackNum")
	    private Integer goodsTrackNum; // 商品追蹤清單編號

	    @Column(name = "memNo", nullable = false)
	    private Integer memNo; // 會員編號

	    @Column(name = "goodsNo", nullable = false)
	    private Integer goodsNo; // 商品編號

	    
		public GoodsTrackVO() {
			super();
			// TODO Auto-generated constructor stub
		}



		public Integer getGoodsTrackNum() {
			return goodsTrackNum;
		}

		public void setGoodsTrackNum(Integer goodsTrackNum) {
			this.goodsTrackNum = goodsTrackNum;
		}

		public Integer getMemNo() {
			return memNo;
		}

		public void setMemNo(Integer memNo) {
			this.memNo = memNo;
		}

		public Integer getGoodsNo() {
			return goodsNo;
		}

		public void setGoodsNo(Integer goodsNo) {
			this.goodsNo = goodsNo;
		}

	    // 若有需要，可以添加 @ManyToOne 關係到其他表 (例如 Mem, Goods)
	    // @ManyToOne
	    // @JoinColumn(name = "memNo", insertable = false, updatable = false)
	    // private Member member;

	    // @ManyToOne
	    // @JoinColumn(name = "goodsNo", insertable = false, updatable = false)
	    // private Goods goods;
}
