// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.counterorder.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.repository.query.Param;  // 正確的導入
import org.springframework.jdbc.core.BeanPropertyRowMapper;

public interface CounterOrderRepository extends JpaRepository<CounterOrderVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from counterorder where counterorderno =?1", nativeQuery = true)
	void deleteByCounterOrderNo(int counterOrderNo);

	@Query(value = "select counterorderno from counterorder where memno =?1 ORDER BY orderTime DESC LIMIT 1", nativeQuery = true)
	Integer findone(Integer memNo);
	//● (自訂)條件查詢
//	@Query(value = "from CounterOrderVO where counterorderno=?1 and ename like?2 and hiredate=?3 order by empno")
//	List<CounterOrderVO> findByOthers(int empno , String ename , java.sql.Date hiredate);
	
	
    // 新增：根據櫃位編號和會員編號查詢訂單-柏翔
    @Query("SELECT co FROM CounterOrderVO co WHERE co.counterNo = :counterNo AND co.memNo = :memNo AND co.orderStatus = 0")
    Optional<CounterOrderVO> findByCounterNoAndMemNoAndStatusPending(
        @Param("counterNo") Integer counterNo, 
        @Param("memNo") Integer memNo
    );
    
    // 如果需要查詢多筆訂單，可以使用-柏翔
    @Query("SELECT co FROM CounterOrderVO co WHERE co.counterNo = :counterNo AND co.memNo = :memNo")
    List<CounterOrderVO> findAllByCounterNoAndMemNo(
        @Param("counterNo") Integer counterNo, 
        @Param("memNo") Integer memNo
    );
//    柏翔
    @Query("SELECT co FROM CounterOrderVO co WHERE co.counterNo = :counterNo AND co.memNo = :memNo AND co.orderStatus = :status")
    Optional<CounterOrderVO> findByCounterNoAndMemNoAndOrderStatus(
        @Param("counterNo") Integer counterNo, 
        @Param("memNo") Integer memNo,
        @Param("status") Integer status
    );
	
    @Query("SELECT co FROM CounterOrderVO co WHERE co.orderStatus = 0 AND TIMESTAMPDIFF(MINUTE, co.ordertime, CURRENT_TIMESTAMP) > 1")
    List<CounterOrderVO> findExpiredOrders();
    
	
}