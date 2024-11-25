package com.goods.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.countercarousel.model.CountercarouselVO;

public interface GoodsRepository extends JpaRepository<GoodsVO, Integer> {

    /**
     * 自訂刪除功能，根據商品編號刪除資料
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM goods where goodsNo =?1", nativeQuery = true)
    void deleteByGoodsno(int goodsNo);

    /**
     * 自訂查詢功能，根據商品編號、商品名稱及價格篩選資料並排序
     */
    @Query(value = "from GoodsVO where goodsNo = ?1 and goodsName like %?2% and goodsPrice= ?3 order by goodsNo")
	List<GoodsVO> findByOthers(int goodsNo, String goodsName, int goodsPrice);
    
    @Query(value = "SELECT * FROM goods where counterNo = ?1  ORDER BY goodsDate desc", nativeQuery = true)
	List<GoodsVO> getOneCounter35(Integer counterNo);

}
