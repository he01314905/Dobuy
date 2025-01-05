package com.usedorder.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface UsedOrderRepository extends JpaRepository<UsedOrderVO, Integer> {


	
	  @Transactional
	  @Modifying
	  @Query(value = "Update usedorder SET deliveryStatus=?1 where usedOrderNo = ?2", nativeQuery = true)
	  void changeStatusByUsedOrderNo(Byte deliveryStatus,Integer usedOrderNo);
	  
	  //找尋會員身為買家的訂單  
	  @Query(value = "SELECT * FROM usedorder where BuyerNo = ?1 AND deliveryStatus != 6", nativeQuery = true)
	  List<UsedOrderVO> selectBuyerOrderByMemNo(Integer buyerNo);
	  
	  //找尋會員身為賣家的訂單(透過賣的商品編號)
	  @Query(value = "SELECT * FROM usedorder where usedNo = ?1 AND deliveryStatus != 6", nativeQuery = true)
	  List<UsedOrderVO> selectSellerOrderBySellerUsedNo(Integer usedNo);
	  
	  @Query(value = "SELECT uo.*, u.usedName FROM usedorder uo JOIN Used u ON uo.usedNo = u.usedNo WHERE uo.buyerNo = ?1 AND uo.deliveryStatus != 6", nativeQuery = true)
	  List<Object[]> findBuyerOrdersWithUsedNameByBuyerNo(Integer buyerNo);
	  
	  
}