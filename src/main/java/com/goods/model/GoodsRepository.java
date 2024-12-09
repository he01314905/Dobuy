package com.goods.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.countercarousel.model.CountercarouselVO;

import io.lettuce.core.dynamic.annotation.Param;

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
    
    
    //=============以下昱夆新增===============//
    
    @Query(value = "SELECT * FROM goods WHERE counterNo = ?1 ORDER BY goodsDate DESC", nativeQuery = true)
    List<GoodsVO> getOneCounter35(Integer counterNo);

    @Transactional
    @Modifying
    @Query(value = "update goods set goodsAmount=?2  where goodsNo =?1", nativeQuery = true)
    void upGoodsAmount(Integer goodsNo,Integer goodsAmount);

    
  //=============以上昱夆新增===============//
    
    
//=============以下柏翔新增===============//

    List<GoodsVO> findByCounterVO_CounterNo(Integer counterNo);
    @Query(value = "SELECT counterNo  FROM goods WHERE goodsNo = ?1 ", nativeQuery = true)
    GoodsVO getOneCounter(Integer goodsNo);
    
    List<GoodsVO> findByGoodsNameContaining(String goodsName);
  
    List<GoodsVO> findByCounterVO_CounterCName(String counterCName);
}
