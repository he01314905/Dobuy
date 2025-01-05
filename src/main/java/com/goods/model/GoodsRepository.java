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
    
    @Query(value = "SELECT * FROM goods WHERE counterNo = ?1 ", nativeQuery = true)
    List<GoodsVO> getOneCounter1(Integer counterNo);

    //=============以下昱夆新增===============//
    
    @Query(value = "SELECT * FROM goods WHERE counterNo = ?1 && goodsStatus=1 ORDER BY goodsDate DESC", nativeQuery = true)
    List<GoodsVO> getOneCounter35(Integer counterNo);


    @Modifying
    @Query(value="UPDATE goods  SET goodsAmount = ?1 WHERE goodsNo =?2",nativeQuery = true)
    void upGoodsAmount(Integer goodsAmount,Integer goodsNo);
    
    
    @Query(value = "select * from goods where goodsStatus=1",nativeQuery = true)
    List<GoodsVO> findAllUP();
    
  //=============以上昱夆新增===============//
    
    //=============以下gary新增===============//
    //搜尋所有上架商品
    @Query(value = "SELECT * FROM goods WHERE goodsstatus = 1 ", nativeQuery = true)
    List<GoodsVO> getAllGoodsStatus1();
    //=============以上GARY新增===============//
    
//=============以下柏翔新增===============//

    List<GoodsVO> findByCounterVO_CounterNo(Integer counterNo);
    @Query(value = "SELECT counterNo  FROM goods WHERE goodsNo = ?1 ", nativeQuery = true)
    GoodsVO getOneCounter(Integer goodsNo);
    
    @Query("SELECT g FROM GoodsVO g WHERE g.goodsName LIKE %:goodsName% AND g.goodsStatus = 1")
    List<GoodsVO> findByGoodsNameContainingAndStatus(@Param("goodsName") String goodsName);
  
    List<GoodsVO> findByCounterVO_CounterCName(String counterCName);
}
