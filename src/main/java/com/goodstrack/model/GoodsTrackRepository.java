package com.goodstrack.model;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GoodsTrackRepository extends JpaRepository<GoodsTrackVO, Integer> {
    // 自訂查詢：根據會員編號查詢追蹤清單
	@Query(value = "SELECT * FROM goodstrack WHERE memNo = ?1", nativeQuery = true)
    List<GoodsTrackVO> findByMemNo(Integer memNo);
	
	// 自訂查詢：根據會員編號和商品編號查詢（例如檢查是否已追蹤某商品）
	@Query(value = "SELECT * FROM goodstrack WHERE memNo = ?1 AND goodsNo = ?2", nativeQuery = true)
    GoodsTrackVO findByMemNoAndGoodsNo(Integer memNo, Integer goodsNo);

    // 自訂刪除：根據會員編號和商品編號刪除
	@Transactional
	@Modifying
    @Query(value = "DELETE FROM GoodsTrack WHERE memNo = ?1 AND goodsNo = ?2", nativeQuery = true)
    void deleteByMemNoAndGoodsNo(Integer memNo, Integer goodsNo);
}
